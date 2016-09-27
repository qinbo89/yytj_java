package com.vdlm.test.server;

import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class EmbeddedServer2 {

	public static void main(String[] args) throws Exception {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(9001);
		tomcat.setBaseDir("target/tomcat");
		tomcat.addWebapp("/", new File("D:\\workspace\\yytj\\frontend\\keepalive-net\\src\\main\\webapp").getAbsolutePath());

		tomcat.start();
		tomcat.getServer().await();
	}
}
