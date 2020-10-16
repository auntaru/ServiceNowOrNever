package com.jcg.hibernate.maven;

// 2019-11.01

// to launch main of AppMain - from command line after << mvn package >>
// mvn exec:java -Dexec.mainClass="com.jcg.hibernate.maven.AppMain"

// Convert XML or JSON to Java Pojo Classes - Online
// http://pojo.sodhanalibrary.com/

// Hibernate Maven Example
// https://examples.javacodegeeks.com/enterprise-java/hibernate/hibernate-maven-example/
	
// Programmatically Generating Database Schema with Hibernate 5
// https://shekhargulati.com/2018/01/09/programmatically-generating-database-schema-with-hibernate-5/

// http://mrbool.com/how-to-create-database-table-using-hibernate/28269
// Drop and re-create the database schema on startup --> <property name="hbmdl.auto">update</property>

/*
 * https://github.com/auntaru/ServiceNowOrNever
 * https://www.youtube.com/watch?v=ngVxiJ78t3Q
 * cd C:\Java\Git\ServiceNowOrNever
 * 
mvn clean package
mvn exec:java -Dexec.mainClass="com.jcg.hibernate.maven.AppMain"
mvn exec:java -Dexec.mainClass="com.jcg.rest.jersey.client.RestClientToServiceNow"
 * 
 */

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class AppMain {

	static User userObj;
	static Session sessionObj;
	static SessionFactory sessionFactoryObj;

	private static SessionFactory buildSessionFactory() {
		// Creating Configuration Instance & Passing Hibernate Configuration File
		Configuration configObj = new Configuration();
		configObj.configure("hibernate.cfg.xml");
		// configObj.configure("hibernate.pgsql.cfg.xml");
		
		// Since Hibernate Version 4.x, ServiceRegistry Is Being Used
		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build(); 

		// Creating Hibernate SessionFactory Instance
		sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
		return sessionFactoryObj;
	}

	public static void main(String[] args) {
		System.out.println(".......Hibernate Maven Example.......\n");
		try {
			sessionObj = buildSessionFactory().openSession();
			sessionObj.beginTransaction();

			for(int i = 101; i <= 105; i++) {
				userObj = new User();
				userObj.setUserid(i);
				userObj.setUsername("Editor " + i);
				userObj.setCreatedBy("Administrator");
				userObj.setCreatedDate(new Date());

				sessionObj.save(userObj);
			}
			System.out.println("\n.......Records Saved Successfully To The Database.......\n");

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
		} catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(sessionObj != null) {
				sessionObj.close();
			}
		}
	}
}