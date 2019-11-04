package com.jcg.rest.jersey.client;

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
                new AuthScope(new HttpHost("dev63641.service-now.com")),
                new UsernamePasswordCredentials("admin", "ThisIsNotMyPassword"));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();

        try {
        	// https://dev63641.service-now.com/api/now/v1/table/incident
            // HttpGet httpget = new HttpGet("https://instance.service-now.com/api/now/table/incident");
            HttpGet httpget = new HttpGet("https://dev63641.service-now.com/api/now/v1/table/incident");

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