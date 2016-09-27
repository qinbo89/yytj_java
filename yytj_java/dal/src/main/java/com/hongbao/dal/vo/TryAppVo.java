package com.hongbao.dal.vo;

import com.hongbao.dal.model.TryApp;

public class TryAppVo extends TryApp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tried;

	public int getTried() {
		return tried;
	}

	public void setTried(int tried) {
		this.tried = tried;
	}
	

}
