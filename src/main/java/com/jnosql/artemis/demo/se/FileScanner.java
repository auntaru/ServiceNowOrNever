
// https://projects.eclipse.org/projects/technology.jnosql
// https://www.eclipse.org/community/eclipse_newsletter/2018/april/jnosql.php
	
// https://www.predic8.de/couchdb-tutorial.htm
// https://dzone.com/articles/relax-java-and-nosql-with-couchdb

// Using Java to access MongoDB, CouchDB, Cassandra
// https://dzone.com/articles/using-java-access-mongodb

// Artemis examples code
// 
// https://github.com/JNOSQL/artemis-demo/tree/master/artemis-demo-java-se/couchdb
// https://github.com/auntaru/artemis-demo
// Artemis demonstrations using several provider and the NoSQL API and its specializations
// artemis-demo-java-se: The bunch of projects that are using Artemis with CDI 2.0
// https://github.com/JNOSQL/jnosql.github.io/blob/master/images/duke-artemis.png
// https://github.com/JNOSQL/jnosql.github.io
// The Eclipse JNoSQL is a framework whose goal is to help Java developers to create Java EE applications with NoSQL, 
// whereby they can make scalable application enjoying polyglot persistence.

// https://github.com/JNOSQL/artemis-demo/tree/master/artemis-demo-java-se/couchdb

// https://www.predic8.de/couchdb/demo-project.zip
// https://www.w3spoint.com/difference-between-couchdb-and-mongodb
	
/* 
 *  https://www.centlinux.com/2020/08/install-apache-couchdb-on-centos-8.html
    To create a CouchDB database, we can use following command
    [root@couchdb-01 ~]# curl -u admin:Str0ngP@ssw0rd -X PUT http://127.0.0.1:5984/prod
       {"ok":true}
    To list all the databases on our CouchDB server, we can send following command.
    [root@couchdb-01 ~]# curl -u admin:Str0ngP@ssw0rd -X GET http://127.0.0.1:5984/_all_dbs
       ["_replicator","_users","prod","test"]



Maven BuiLD : 
https://cwiki.apache.org/confluence/display/MAVEN/LifecyclePhaseNotFoundException
http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html
http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html#Lifecycle_Reference

The default lifecycle comprises of the following phases (for a complete list of the lifecycle phases, refer to the Lifecycle Reference):

validate - validate the project is correct and all necessary information is available
compile - compile the source code of the project
test - test the compiled source code using a suitable unit testing framework. These tests should not require the code be packaged or deployed
package - take the compiled code and package it in its distributable format, such as a JAR.
verify - run any checks on results of integration tests to ensure quality criteria are met
install - install the package into the local repository, for use as a dependency in other projects locally
deploy - done in the build environment, copies the final package to the remote repository for sharing with other developers and projects.
These lifecycle phases (plus the other lifecycle phases not shown here) are executed sequentially to complete the default lifecycle. Given the lifecycle phases above, this means that when the default lifecycle is used, Maven will first validate the project, then will try to compile the sources, run those against the tests, package the binaries (e.g. jar), run integration tests against that package, verify the integration tests, install the verified package to the local repository, then deploy the installed package to a remote repository.

# mvn install
mvn compiler:compile
mvn clean package
RuN : 
mvnw spring-boot:run
http://localhost:8080/
  => Spring Boot Rocks in Java too!
#  
#  
mvn exec:java -Dexec.mainClass="com.jnosql.artemis.demo.se.FileScanner"
# mvn exec:java -Dexec.mainClass="com.jcg.hibernate.maven.AppMain"
#
On Linux: 
mvn -e -X exec:java -Dexec.mainClass="com.jcg.rest.jersey.client.RestClientToServiceNow"
https://stackoverflow.com/questions/64299956/unknown-lifecycle-phase-on-maven
on Windows : 
mvn exec:java -D"exec.mainClass"="com.jcg.rest.jersey.client.RestClientToServiceNow"
#
#
*/
package com.jnosql.artemis.demo.se;

import java.io.File;

import jakarta.nosql.mapping.document.DocumentTemplate;
import jakarta.nosql.document.DocumentQuery;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.nosql.document.DocumentQuery.select;


public class FileScanner {

  /* 
   * Connecting to a database, using the 
   * server address and the name of the database.
   * Note that the database have to be created before.
   */


  public static void main(String[] args) {
	System.out.println(" FileScanner.main ");  
    FileScanner fs = new FileScanner();
    /* 
     * Choose the directory to be analyzed.
     * It can take few minutes to analyze large directories.
     */
    fs.processDir(new File("C:\\CouchDB\\bin\\lib"));
    // fs.processDir(new File("C:\\CouchDB"));
  }
  
  
  /* 
   * .withAge(f.length()) 
   */
  
  void processDir(File f) {
    if (f.isFile()) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            FileObject ironMan = FileObject.builder().withRealName(f.getAbsolutePath()).withName(f.getName()).build();
                    
            DocumentTemplate template = container.select(DocumentTemplate.class).get();

            template.insert(ironMan);
            System.out.println(" FileScanner.processDir " + ironMan.getName());

        }    	
    	
      
    } else {
      File[] fileList = f.listFiles();
      if (fileList == null) return;
      for (int i = 0; i < fileList.length; i++) {
        try {
          processDir(fileList[i]);
        } catch (Exception e) {
          System.out.println(e);
        }
      }
    }
  }
}