package com.masterdoc.plugin;

import static java.io.File.separator;
import static java.text.MessageFormat.format;
import static org.springframework.util.StringUtils.capitalize;

import java.beans.IntrospectionException;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.ws.rs.*;

import org.apache.camel.Consume;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.project.MavenProject;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.reflections.Reflections;

import com.googlecode.gentyref.GenericTypeReflector;
import com.masterdoc.pojo.*;
import com.masterdoc.pojo.Enumeration;

public class MasterDocGenerator {

  // ----------------------------------------------------------------------
  // Constants
  // ----------------------------------------------------------------------

  public static final String           CLASS                   = "class ";
  public static final SimpleDateFormat SDF                     = new SimpleDateFormat("dd-MM-yyyy HH:mm:sss");
  public static final String           MASTERDOC_JSON_FILENAME = "masterdoc.json";
  public static final String           PATH_PARAM              = "PathParam";
  public static final String           QUERY_PARAM             = "QueryParam";
  public static final String           JAVA_UTIL_HASH_MAP      = "java.util.HashMap";
  public static final String           GET                     = "GET";
  public static final String           POST                    = "POST";
  public static final String           PUT                     = "PUT";
  public static final String           DELETE                  = "DELETE";
  public static final String           OPTIONS                 = "OPTIONS";
  public static final String           NULL                    = "null";
  public static final String           VOID                    = "void";
  public static final String           IS_PREFIX               = "is";
  public static final String           GET_PREFIX              = "get";
  public static final String           INTERFACE               = "interface";
  public static final String           T                       = "T";
  public static final String           JAVA_LANG_OBJECT        = "java.lang.Object";
  public static final String           JAVA                    = "java";
  public static final String           BYTE                    = "B";
  public static final String           ARRAY                   = "[]";
  public static final String           DOT                     = ".";
  public static final String           COMMA                   = ",";

  // ----------------------------------------------------------------------
  // Variables
  // ----------------------------------------------------------------------
  /** Logger for maven plugin. */
  private ConsoleLogger                consoleLogger           = new ConsoleLogger();
  /** Resources found by MasterDoc. */
  private List<Resource>               resources;
  /** Entities found by MasterDoc. */
  private List<AbstractEntity>         entities;
  /** Metadata found by MasterDoc. */
  private MasterDocMetadata            metadata;
  /** Final MasterDoc. */
  private MasterDoc                    masterDoc;
  private Set<Serializable>            entityList;
  private MavenProject                 project;
  private ClassLoader                  originalClassLoader     = Thread.currentThread().getContextClassLoader();
  private ClassLoader                  newClassLoader;
  private String                       pathToGenerateFile;
  private HashSet<Serializable>        newEntities;

  // ----------------------------------------------------------------------
  // Constructors
  // ----------------------------------------------------------------------

  public MasterDocGenerator(MavenProject project, String pathToGenerateFile, String[] packageDocumentationResources)
      throws SecurityException,
      NoSuchFieldException,
      IllegalArgumentException, IllegalAccessException {
    long start = System.currentTimeMillis();
    consoleLogger.info("MasterDocGenerator started");
    resources = new ArrayList<Resource>();
    entities = new ArrayList<AbstractEntity>();
    entityList = new HashSet<Serializable>();
    this.project = project;
    this.pathToGenerateFile = pathToGenerateFile;
    // ////////////////////
    generateProjectClassLoader(project);
    // ////////////////////
    for (String packageDocumentationResource : packageDocumentationResources) {
      consoleLogger.info(format("Generate REST documentation on package {0} ...", packageDocumentationResource));
      startGeneration(new String[] { packageDocumentationResource });
    }
    //
    generateDocumentationFile();
    //
    // restore original classloader
    Thread.currentThread().setContextClassLoader(originalClassLoader);
    consoleLogger.info(format("Generation ended in {0} ms", (System.currentTimeMillis() - start)));
  }

  // ----------------------------------------------------------------------
  // Public methods
  // ----------------------------------------------------------------------
  public void startGeneration(String[] args) {
    String packageDocumentationResource = args[0];

    if (null != packageDocumentationResource
        && packageDocumentationResource.length() > 0) {
      try {
        getMetadata();
        getDocResource(packageDocumentationResource);
        getEntities(entityList);
        consoleLogger.info(format("Entities : {0}", entityList.size()));
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (IntrospectionException e) {
        e.printStackTrace();
      }
    } else {
      consoleLogger.error("packageDocumentationResources not defined in plugin configuration");
    }
  }

  // ----------------------------------------------------------------------
  // Private methods
  // ----------------------------------------------------------------------
  private void getEntities(Set list) throws ClassNotFoundException,
      IntrospectionException {

    newEntities = new HashSet<Serializable>();
    for (Iterator<Serializable> iterator = list.iterator(); iterator
        .hasNext();) {
      Serializable entity = iterator.next();
      final Class<?> entityClass;
      try {
        entityClass = Class.forName(entity.toString(), true, newClassLoader);
      } catch (Exception e) {
        consoleLogger.debug(format("{0} is not forNamable", entity.toString()));
        continue;
      }
      Entity newEntity = new Entity();
      if (!entity.toString().startsWith(JAVA)) {

        newEntity.setName(entity.toString());

        if (entityClass.isEnum()) {
          extractEnumFields(entity);
        } else {
          newEntity.setFields(extractFields(entity));
          final Class<?> superclass = entityClass.getSuperclass();
          if (!JAVA_LANG_OBJECT.equals(superclass.getName())) {
            newEntity.setSuperClass(superclass);
            if (!entityList.contains(superclass.getName()) && !newEntities.contains(superclass.getName())) {
              newEntities.add(superclass.getName());
            }
          }

          entities.add(newEntity);
        }
      }
    }
    if (newEntities.size() > 0) {
      entityList.addAll(newEntities);
      getEntities((Set) newEntities.clone());
    }
  }

  /**
   * Get all @Path class and export all methods
   * 
   * @throws NoSuchFieldException
   * @throws SecurityException
   */

  private void getDocResource(String packageDocumentationResource) {
    String mediaTypeProduces = null, mediaTypeConsumes = null;

    Reflections reflections = new Reflections(packageDocumentationResource);
    Set<Class<?>> reflectionResources = reflections
        .getTypesAnnotatedWith(Path.class);
    consoleLogger.info(format("Ressources : {0}", reflectionResources));
    for (Iterator<Class<?>> iterator = reflectionResources.iterator(); iterator
        .hasNext();) {
      Class<?> resource = iterator.next();
      if (!resource.isInterface()) {
        Resource res = new Resource();

        Annotation[] annotations = resource.getAnnotations();
        Method[] declaredMethods = resource.getDeclaredMethods();

        res.setEntryList(new TreeMap<String, ResourceEntry>());
        // Annotations for resource
        for (int i = 0; i < annotations.length; i++) {
          Annotation annotation = annotations[i];
          if (annotation instanceof Path) {
            res.setRootPath(((Path) annotation).value());
          }
          if (annotation instanceof Produces) {
            mediaTypeProduces = ((Produces) annotation).value()[0];
          }
          if (annotation instanceof Consumes) {
            mediaTypeConsumes = ((Consumes) annotation).value()[0];
          }
        }

        // Check methods
        Class<?> superclass = resource.getSuperclass();
        if (null != superclass
            && !superclass.getCanonicalName().equals(
                JAVA_LANG_OBJECT)) {
          Method[] superclassDeclaredMethods = superclass
              .getDeclaredMethods();
          for (int i = 0; i < superclassDeclaredMethods.length; i++) {
            Method superclassDeclaredMethod = superclassDeclaredMethods[i];
            ResourceEntry resourceEntry = createResourceEntryFromMethod(
                superclassDeclaredMethod, mediaTypeConsumes,
                mediaTypeProduces, resource);
            if (null != resourceEntry) {
              resourceEntry.setFullPath(res.getRootPath() + "/"
                  + resourceEntry.getPath());
              res.getEntryList().put(
                  resourceEntry.calculateUniqKey(),
                  resourceEntry);
            }
          }
        }
        for (int i = 0; i < declaredMethods.length; i++) {
          Method declaredMethod = declaredMethods[i];
          ResourceEntry resourceEntry = createResourceEntryFromMethod(
              declaredMethod, mediaTypeConsumes,
              mediaTypeProduces, null);
          if (null != resourceEntry) {
            if (res.getEntryList().containsKey(
                resourceEntry.calculateUniqKey())) {
              res.getEntryList().remove(
                  resourceEntry.calculateUniqKey());
            }
            resourceEntry.setFullPath(res.getRootPath() + "/"
                + resourceEntry.getPath());
            res.getEntryList()
                .put(resourceEntry.calculateUniqKey(),
                    resourceEntry);
          }
        }

        consoleLogger.debug(">> " + resource.getCanonicalName());
        resources.add(res);
        extractEntityFromResourceEntries(res);
      } else {
        consoleLogger.debug(">>skip " + resource.getCanonicalName());
      }
    }
  }

  private void getMetadata() {
    metadata = new MasterDocMetadata();
    metadata.setGenerationDate(SDF.format(new Date()));
    metadata.setGroupId(project.getGroupId());
    metadata.setArtifactId(project.getArtifactId());
    metadata.setVersion(project.getVersion());

  }

  /**
   * Method to switch from class OR enum
   * 
   * @param entity
   * @return
   * @throws ClassNotFoundException
   * @throws IntrospectionException
   */
  private Map<String, AbstractEntity> extractFieldsSwitcher(
      Serializable entity) throws ClassNotFoundException,
      IntrospectionException {
    final Class<?> entityClass;
    try {
      entityClass = Class.forName(entity.toString(), true, newClassLoader);
    } catch (Exception e) {
      consoleLogger.debug(format("{0} is not forNamable", entity.toString()));
      return null;
    }
    if (entityClass.isEnum()) {
      return extractEnumFields(entity);
    } else {
      return extractFields(entity);
    }
  }

  /**
   * Method to extract the class values.
   * 
   * @param entity
   * @return
   * @throws ClassNotFoundException
   * @throws IntrospectionException
   */
  private Map<String, AbstractEntity> extractFields(Serializable entity)
      throws ClassNotFoundException, IntrospectionException {
    Map<String, AbstractEntity> fields = new HashMap<String, AbstractEntity>();
    final Class<?> entityClass = Class.forName(entity.toString(), true, newClassLoader);
    consoleLogger.debug(format(">>Extract fields for class {0} ...", entityClass));

    final java.lang.reflect.Field[] declaredFields = entityClass
        .getDeclaredFields();
    for (int i = 0; i < declaredFields.length; i++) {
      java.lang.reflect.Field declaredField = declaredFields[i];
      consoleLogger.debug(format(">>Extract fields  {0} ...", declaredField.getName()));
      Type typeOfField = declaredField.getGenericType();
      if (!typeOfField.toString().startsWith(CLASS) &&
          !typeOfField.toString().startsWith(INTERFACE) &&
          typeOfField.toString().indexOf(DOT) > -1) {
        typeOfField = (ParameterizedType) typeOfField;
      }
      String type = extractTypeFromType(typeOfField);
      String typeDisplay = type;
      if (type.startsWith("[")) {
        if (type.startsWith("[L") && type.endsWith(";")) {
          type = type.substring(2, type.length() - 1);
        } else {
          type = type.substring(1);
          if (BYTE.equals(type)) {
            type = byte.class.getName();
          }
        }
        typeDisplay = type + ARRAY;
      }
      if (type.indexOf("<") > -1) {
        type = type.substring(type.indexOf("<") + 1, type.indexOf(">"));
      }
      String[] types = type.split(COMMA);
      try {
        entityClass.getDeclaredMethod(GET_PREFIX + capitalize(declaredField.getName())); // GET OR IS
        createEntityFromField(fields, declaredField, typeDisplay, types);

      } catch (NoSuchMethodException e) {
        try {
          entityClass.getDeclaredMethod(IS_PREFIX
              + capitalize(declaredField.getName())); // GET OR IS
          createEntityFromField(fields, declaredField, typeDisplay, types);

        } catch (NoSuchMethodException ex) {
          consoleLogger.debug(format(">>>>Bypass : {0}.{1}", entityClass.toString(), declaredField.getName()));
        }

      }
    }
    return fields;
  }

  private void createEntityFromField(Map<String, AbstractEntity> fields, Field declaredField, String typeDisplay, String[] types) {
    Class<?> currEntityClass = null;
    for (String t : types) {
      try {
        t = t.trim();
        currEntityClass = Class.forName(t, true, newClassLoader);
        if (!entityList.contains(t) && !newEntities.contains(t)) {
          newEntities.add(t);
        }
      } catch (Exception e) {
        consoleLogger.debug(format("{0} is not forNamable", t.toString()));
        continue;
      }
    }
    AbstractEntity field;
    if (null != currEntityClass && currEntityClass.isEnum()) {
      field = new Enumeration();
      field.setName(typeDisplay);
    } else {
      field = new Entity();
      field.setName(typeDisplay);
    }
    fields.put(declaredField.getName(), field);
  }

  /**
   * Method to extract the enuration values.
   * 
   * @param entity
   * @return
   * @throws ClassNotFoundException
   * @throws IntrospectionException
   */
  private Map<String, AbstractEntity> extractEnumFields(Serializable entity)
      throws ClassNotFoundException, IntrospectionException {
    Map<String, AbstractEntity> fields = new HashMap<String, AbstractEntity>();
    List<String> values = new ArrayList<String>();
    String entityString = entity.toString();
    if (entityString.startsWith(CLASS)) {
      entityString = entityString.substring(entityString.indexOf(CLASS)
          + CLASS.length());
    }
    consoleLogger.debug(">>>Extract enum " + entityString + " ...");
    final Class<?> entityClass = Class.forName(entityString, true, newClassLoader);
    final Object[] declaredEnumConstants = entityClass.getEnumConstants();
    Enumeration newEnumeration = new Enumeration();
    newEnumeration.setName(entityString);
    for (int i = 0; i < declaredEnumConstants.length; i++) {
      values.add(declaredEnumConstants[i].toString());
    }
    newEnumeration.setValues(values);
    // do not add an enum if its already in the entities list
    if (!entities.contains(newEnumeration)) {
      entities.add(newEnumeration);
    }
    return fields;
  }

  private void extractEntityFromResourceEntries(Resource res) {
    Map<String, ResourceEntry> entryList = res.getEntryList();
    Set<String> set = entryList.keySet();
    for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
      String key = iterator.next();
      ResourceEntry resourceEntry = entryList.get(key);
      Serializable requestEntity = resourceEntry.getRequestEntity();
      Serializable responseEntity = resourceEntry.getResponseEntity();
      if (null != requestEntity && !NULL.equals(requestEntity)
          && !VOID.equals(requestEntity)) {
        entityList.add(removeList(requestEntity));
      }
      if (null != responseEntity && !NULL.equals(responseEntity)
          && !VOID.equals(responseEntity)) {
        entityList.add(removeList(responseEntity));
      }
    }
  }

  private String removeList(Serializable entity) {
    String string = String.valueOf(entity);
    if (string.indexOf("<") > 0) {
      string = string.substring(string.indexOf("<") + 1,
          string.length() - 1);
    }
    return string;
  }

  private ResourceEntry createResourceEntryFromMethod(Method declaredMethod,
      String mediaTypeConsumes, String mediaTypeProduces,
      Class childResourceClass) {
    ResourceEntry resourceEntry = new ResourceEntry(mediaTypeConsumes,
        mediaTypeProduces);
    resourceEntry.setPath("/");
    Annotation[] declaredAnnotations = declaredMethod
        .getDeclaredAnnotations();
    for (int i = 0; i < declaredAnnotations.length; i++) {
      Annotation declaredAnnotation = declaredAnnotations[i];
      if (declaredAnnotation instanceof Path) {
        resourceEntry.setPath(((Path) declaredAnnotation).value());
      }
      if (declaredAnnotation instanceof Produces) {
        resourceEntry
            .setMediaTypeProduces(((Produces) declaredAnnotation)
                .value()[0]);
      }
      if (declaredAnnotation instanceof Consumes) {
        resourceEntry
            .setMediaTypeConsumes(((Consumes) declaredAnnotation)
                .value()[0]);
      }
      if (declaredAnnotation instanceof Consume) {
        CamelConsumeAnnotation camelConsumeAnnotation = new CamelConsumeAnnotation();
        camelConsumeAnnotation
            .setContext((((Consume) declaredAnnotation)).context());
        camelConsumeAnnotation.setUri((((Consume) declaredAnnotation))
            .uri());
        resourceEntry.setCamelConsume(camelConsumeAnnotation);
      }
      if (declaredAnnotation instanceof GET) {
        resourceEntry.setVerb(GET);
      }
      if (declaredAnnotation instanceof POST) {
        resourceEntry.setVerb(POST);
      }
      if (declaredAnnotation instanceof PUT) {
        resourceEntry.setVerb(PUT);
      }
      if (declaredAnnotation instanceof DELETE) {
        resourceEntry.setVerb(DELETE);
      }
      if (declaredAnnotation instanceof OPTIONS) {
        resourceEntry.setVerb(OPTIONS);
      }
    }

    if (null == resourceEntry.getVerb()) {
      return null;
    }

    Object[] pType;
    if (null != childResourceClass) {
      pType = GenericTypeReflector.getExactParameterTypes(declaredMethod,
          childResourceClass);
    } else {
      pType = declaredMethod.getParameterTypes();
    }

    Annotation[][] pAnnot = declaredMethod.getParameterAnnotations();
    for (int i = 0; i < pType.length; i++) {
      String typeName = ((Class) pType[i]).getName();
      if (JAVA_UTIL_HASH_MAP.equals(typeName)) {
        Type[] types = declaredMethod.getGenericParameterTypes();
        ParameterizedType paramType = (ParameterizedType) types[i];
        typeName = extractTypeFromType(paramType);
      }
      if (pAnnot[i].length == 0) {
        resourceEntry.setRequestEntity(typeName);
      }
      for (int j = 0; j < pAnnot[i].length; j++) {
        Annotation annotation = pAnnot[i][j];
        if (annotation instanceof PathParam) {
          Param param = new Param();
          param.setType(PATH_PARAM);
          param.setClassName(typeName);
          param.setName(((PathParam) annotation).value());
          resourceEntry.getPathParams().add(param);
        }
        if (annotation instanceof QueryParam) {
          Param param = new Param();
          param.setType(QUERY_PARAM);
          param.setClassName(typeName);
          param.setName(((QueryParam) annotation).value());
          resourceEntry.getQueryParams().add(param);
        }
      }
    }

    if (null != childResourceClass) {
      Type exactReturnType = GenericTypeReflector.getExactReturnType(
          declaredMethod, childResourceClass);
      resourceEntry
          .setResponseEntity(extractTypeFromType(exactReturnType));
    } else {
      resourceEntry.setResponseEntity(extractTypeFromType(declaredMethod
          .getGenericReturnType()));
    }

    return resourceEntry;
  }

  private String extractTypeFromType(Type type) {
    String returnType = null;
    if (T.equals(type.toString())) {
      return JAVA_LANG_OBJECT;
    }
    if (type instanceof ParameterizedType) {
      returnType = type.toString();
      if (returnType.startsWith(CLASS)) {
        returnType = returnType.substring(returnType.indexOf(CLASS));
      }
    } else {
      returnType = ((Class) type).getName();
    }
    return returnType;
  }

  /**
   * @param project
   * @throws NoSuchFieldException
   * @throws IllegalAccessException
   */
  private void generateProjectClassLoader(MavenProject project) throws NoSuchFieldException, IllegalAccessException {
    List<URL> urls = new ArrayList<URL>();

    // get all the dependencies which are hidden in resolvedArtifacts of project object
    Field dependencies = MavenProject.class.
        getDeclaredField("resolvedArtifacts");

    dependencies.setAccessible(true);

    LinkedHashSet<Artifact> artifacts = (LinkedHashSet<Artifact>) dependencies.get(project);

    // noinspection unchecked
    for (Artifact artifact : artifacts) {
      try {
        urls.add(artifact.getFile().toURI().toURL());
      } catch (MalformedURLException e) {
        // logger.error(e);
      }
    }

    try {
      urls.add(new File(project.getBuild().getOutputDirectory()).toURI().toURL());
    } catch (MalformedURLException e) {
      consoleLogger.error(e.getMessage());
    }

    consoleLogger.debug("urls = \n" + urls.toString().replace(",", "\n"));

    newClassLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]), originalClassLoader);
    Thread.currentThread().setContextClassLoader(newClassLoader);
  }

  /**
   * 
   */
  private void generateDocumentationFile() {
    JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
    ObjectMapper mapper = jsonProvider.getObjectMapper();
    MasterDoc masterDoc = new MasterDoc();
    masterDoc.setEntities(entities);
    masterDoc.setResources(resources);
    masterDoc.setMetadata(metadata);
    consoleLogger.info(format("Generate files in {0} ...", pathToGenerateFile));

    File theDir = new File(pathToGenerateFile);
    // if the directory does not exist, create it
    if (!theDir.exists()) {
      boolean ret = theDir.mkdirs();
    }

    try {
      File fileEntities = new File(pathToGenerateFile + separator + MASTERDOC_JSON_FILENAME);
      BufferedWriter output = new BufferedWriter(new FileWriter(
          fileEntities));
      output.write(mapper.defaultPrettyPrintingWriter()
          .writeValueAsString(masterDoc));
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
