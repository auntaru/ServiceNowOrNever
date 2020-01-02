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

public class RestClientToServiceNow {

    public static void main(String[] args) throws IOException, HttpException {
		RestClientToServiceNow restAction = new RestClientToServiceNow();
 		restAction.getRequest();
 	}


    public void getRequest() throws HttpException, IOException {

 		CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(new HttpHost("dev61830.service-now.com")),
                new UsernamePasswordCredentials("admin", "e2pPznbQYXE1"));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();

        try {
        	// https://dev63641.service-now.com/api/now/v1/table/incident
            // HttpGet httpget = new HttpGet("https://instance.service-now.com/api/now/table/incident");
            HttpGet httpget = new HttpGet("https://dev61830.service-now.com/api/now/v1/table/incident");

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
                for(int i=0;i<jsonArray.length();i++)
                {

                String short_Description=jsonArray.getJSONObject(i).getString("short_description");
                jsonList.add(short_Description);

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