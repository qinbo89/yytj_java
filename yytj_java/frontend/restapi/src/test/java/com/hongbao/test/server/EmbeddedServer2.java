package com.hongbao.test.server;

import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class EmbeddedServer2 {
	public static void main(String[] args) throws Exception {
		System.setProperty("app","proxy");
		Tomcat tomcat = new Tomcat(); 
		tomcat.setPort(18080);  
		tomcat.setBaseDir("target/tomcat");
		tomcat.addWebapp("/", new File("D:\\workspace\\yytj\\frontend\\restapi\\src\\main\\webapp").getAbsolutePath());
 
		tomcat.start(); 
		tomcat.getServer().await();   
	}  
}


