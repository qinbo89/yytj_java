package com.hongbao.test.server;

import com.hongbao.dal.util.CustomizedPropertyConfigurer;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class EmbeddedServer2 {
	public static void main(String[] args) throws Exception {
		System.setProperty("app","bos");
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(38080);
		tomcat.setBaseDir("target/tomcat");
		tomcat.addWebapp("/", new File("D:\\workspace\\yytj\\frontend\\bos\\src\\main\\webapp").getAbsolutePath());
 
		tomcat.start(); 
		tomcat.getServer().await();   
	}  
}


