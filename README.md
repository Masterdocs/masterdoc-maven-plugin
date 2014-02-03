masterdoc-maven-plugin
======================

Plugin Maven to generate documentation for JAX-RS API

Just add in your api application's <i>pom.xml</i> this plugin : 
```
<plugin>
    <groupId>fr.masterdocs</groupId>
    <artifactId>masterdoc-maven-plugin</artifactId>
    <version>1.2</version>
    <executions>
     <execution>
      <phase>install</phase>
      <goals>
       <goal>masterdoc</goal>
      </goals>
     </execution>
    </executions>
    <configuration>
      <!-- MANDATORY :  List of package to scan -->
      <packageDocumentationResources>
          <param>com.masterdoc</param> 
      </packageDocumentationResources>
      <!-- OPTIONAL : path where the file and the site are generated -->
      <pathToGenerateFile>
        <!-- default is ${project.reporting.outputDirectory} -->
      </pathToGenerateFile>
      <!-- OPTIONAL : Activate or not the site generation -->
      <generateHTMLSite>
        <!-- default is true -->
      </generateHTMLSite>
      <!-- OPTIONAL : maximum depth when you have cyclic entities -->
      <maxDepth>
        <!-- default is 1 -->
      </maxDepth>
    </configuration>
</plugin>
```

This plugin generate a JSON file containing some metadatas (build date, groupId, artifactId and version of the project), all the services exposed with JAX-RS annotations and all the entities used by all services.

This plugin is running when you type this command : 
```
mvn install
```
