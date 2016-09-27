package com.hongbao.service;



public interface ResourceFacade {

	/**
	 * 系统自带的静态资源的key的前缀
	 */
	static final String RES_PREFIX_SYS = "/_resource";
	public String uploadFile(byte data[]);
	
	public String uploadFile(byte data[],String fileName);
}
