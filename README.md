masterdoc-maven-plugin
======================

Plugin Maven to generate documentation for JAX-RS API

Just add in your <i>pom.xml</i> with : 
```

<plugin>
    <groupId>com.masterdoc</groupId>
    <artifactId>masterdoc-maven-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <executions>
     <execution>
      <phase>install</phase>
      <goals>
       <goal>masterdoc</goal>
      </goals>
     </execution>
    </executions>
    <configuration>
      <packageDocumentationResources>
          <param>com.masterdoc</param>
          <param>...</param>
      </packageDocumentationResources>
    </configuration>
</plugin>


This plugin generate a JSON file containing some metadatas (build date, groupId, artifactId and version of the project), all the services exposed with JAX-RS annotations and all the entities used by all services.
