package com.hongbao.test.server;

import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class EmbeddedServer2 {
	public static void main(String[] args) throws Exception {
		Tomcat tomcat = new Tomcat(); 
		tomcat.setPort(28080);
		tomcat.setBaseDir("target/tomcat");
		tomcat.addWebapp("/", new File("D:\\workspace\\yytj\\frontend\\web\\src\\main\\webapp").getAbsolutePath());
 
		tomcat.start(); 
		tomcat.getServer().await();   
	}  
}


