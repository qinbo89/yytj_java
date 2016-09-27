package com.hongbao.test.server;

import java.io.File;

import org.apache.catalina.startup.Tomcat;

public class LshServer {
	public static void main(String[] args) throws Exception {
		Tomcat tomcat = new Tomcat(); 
		tomcat.setPort(18080);  
		tomcat.setBaseDir("target/tomcat");
		tomcat.addWebapp("/", new File("src/main/webapp").getAbsolutePath());
 
		tomcat.start(); 
		tomcat.getServer().await();   
	}  
}

