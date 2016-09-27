package com.hongbao.restapi.sys;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class KeepAliveChannel implements Serializable {

	private static final long serialVersionUID = 5282367404388445622L;
	private String name;
	private List<Map<String, String>> ipInfos;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Map<String, String>> getIpInfos() {
		return ipInfos;
	}

	public void setIpInfos(List<Map<String, String>> ipInfos) {
		this.ipInfos = ipInfos;
	}

}
