package com.jcg.rest.jersey.client;

// JSON simple Sample : 
// https://www.codevoila.com/post/65/java-json-tutorial-and-example-json-java-orgjson
// Example of how to parse JSON using JSON-Java (org.json) library 
// install org.json in a Java project
//    if your project is a Maven project, just add a dependency to the Maven pom.xml file of your project.

/*    <!-- pom.xml -->

<dependencies>
    <!-- ... -->
    <dependency>
        <groupId>org.json</groupId>
        <artifactId>json</artifactId>
        <version>20160810</version>
    </dependency>
    <!-- ... -->
</dependencies>


The default lifecycle comprises of the following phases (for a complete list of the lifecycle phases, refer to the Lifecycle Reference):

validate - validate the project is correct and all necessary information is available
compile - compile the source code of the project
test - test the compiled source code using a suitable unit testing framework. These tests should not require the code be packaged or deployed
package - take the compiled code and package it in its distributable format, such as a JAR.
verify - run any checks on results of integration tests to ensure quality criteria are met
install - install the package into the local repository, for use as a dependency in other projects locally
deploy - done in the build environment, copies the final package to the remote repository for sharing with other developers and projects.
These lifecycle phases (plus the other lifecycle phases not shown here) are executed sequentially to complete the default lifecycle. Given the lifecycle phases above, this means that when the default lifecycle is used, Maven will first validate the project, then will try to compile the sources, run those against the tests, package the binaries (e.g. jar), run integration tests against that package, verify the integration tests, install the verified package to the local repository, then deploy the installed package to a remote repository.

# git clone https://github.com/JNOSQL/artemis-demo.git
# git clone https://github.com/deniswsrosa/couchbase-spring-data-sample.git
# git clone https://github.com/ramazancoool/helloRepo.git
# git clone https://github.com/icha024/cloudant-spring-boot-starter-example.git
# mvn spring-boot:run
# git clone https://github.com/gangchen03/refarch-cloudnative-micro-socialreview.git
# mvn spring-boot:run -Dspring.profiles.active=local

# C:\Java\spring-2.3.4\bin\spring run DemoApplication.java

# mvn install
mvn compiler:compile
mvn clean package
RuN : 
mvnw spring-boot:run
http://localhost:8080/
  => Spring Boot Rocks in Java too!
#  
#          & 'C:\Users\A634538\AppData\Local\Far Manager x64\Far.exe'
#  
mvn exec:java -Dexec.mainClass="com.jnosql.artemis.demo.se.FileScanner"
# mvn exec:java -Dexec.mainClass="com.jcg.hibernate.maven.AppMain"
#
On Linux: 
mvn -e -X exec:java -Dexec.mainClass="com.jcg.rest.jersey.client.RestClientToServiceNow"
https://stackoverflow.com/questions/64299956/unknown-lifecycle-phase-on-maven
on Windows : 
mvn exec:java -D"exec.mainClass"="com.jcg.rest.jersey.client.RestClientToServiceNow"


*/

// 
// https://camel.apache.org/components/latest/servicenow-component.html

// Apache HttpClient Basic Authentication Examples
// https://www.mkyong.com/java/apache-httpclient-basic-authentication-examples/

// How to retrieve data from ServiceNow using Camel framework?
// https://stackoverflow.com/questions/58491395/how-to-retrieve-data-from-servicenow-using-camel-framework

// https://github.com/apache/camel/blob/master/components/camel-servicenow/camel-servicenow-component/src/test/java/org/apache/camel/component/servicenow/ServiceNowTest.java
// https://github.com/apache/camel/blob/master/components/camel-servicenow/camel-servicenow-component/src/test/java/org/apache/camel/component/servicenow/ServiceNowTestSupport.java
// https://github.com/apache/camel/blob/master/components/camel-servicenow/camel-servicenow-component/pom.xml


// https://stackoverflow.com/questions/50813361/servicenow-attachments-in-camel




import java.io.IOException;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.jnosql.artemis.demo.se.FileObject;

import jakarta.nosql.mapping.document.DocumentTemplate;

public class RestClientToServiceNow {

    public static void main(String[] args) throws IOException, HttpException {
		RestClientToServiceNow restAction = new RestClientToServiceNow();
 		restAction.getRequest();
 	}


    public void getRequest() throws HttpException, IOException {

 		CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(new HttpHost("dev81676.service-now.com")),
                new UsernamePasswordCredentials("admin", "2020.Service-Now"));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();

        // https://dev81676.service-now.com/api/now/v1/table/cmdb_ci
        // https://dev81676.service-now.com/api/now/v1/table/incident
        
        // caller_id / opened_by / resolved_by
        // https://dev81676.service-now.com/api/now/v1/table/sys_user
        
        // assignment_group
        // https://dev81676.service-now.com/api/now/v1/table/sys_user_group
        
        // business_service
        // https://dev81676.service-now.com/api/now/v1/table/cmdb_ci_service
        
        
        try {
            // HttpGet httpget = new HttpGet("https://instance.service-now.com/api/now/table/incident");
            HttpGet httpget = new HttpGet("https://dev81676.service-now.com/api/now/v1/table/incident");
            
            httpget.setHeader("Accept", "application/json");
            System.out.println("Executing request " + httpget.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println(responseBody);

// to DEBUG for SyntBots  ->               
                
                org.json.JSONObject jsonObject=new org.json.JSONObject(responseBody);
                org.json.JSONArray jsonArray=new org.json.JSONArray();
                jsonArray=(org.json.JSONArray)jsonObject.get("result");
                java.util.List<String> jsonList=new java.util.ArrayList<String>();
                
                try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

                for(int i=0;i<jsonArray.length();i++)
                {
                String short_Description=jsonArray.getJSONObject(i).getString("short_description");
                String description=jsonArray.getJSONObject(i).getString("description");              
                String inc_number=jsonArray.getJSONObject(i).getString("number");
                String priority=jsonArray.getJSONObject(i).getString("priority");
                String close_code=jsonArray.getJSONObject(i).getString("close_code");
                jsonList.add(description);
                jsonList.add(short_Description);
                jsonList.add(inc_number);
                jsonList.add(priority);
               
                    FileObject incident = FileObject.builder().withRealName(description).withName(inc_number).withAge(short_Description).build();
                    DocumentTemplate template = container.select(DocumentTemplate.class).get();
                    template.insert(incident);
                    // System.out.println(" RestClientToServiceNow " + ironMan.getName());

                }  
                
                }
                System.out.println("Short Description"+jsonList);
// < - to DEBUG for SyntBots                
                
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
 	}

}