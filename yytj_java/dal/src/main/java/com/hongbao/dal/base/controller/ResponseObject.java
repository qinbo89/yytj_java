package com.hongbao.dal.base.controller;


import com.hongbao.dal.base.consts.ResultCode;
import com.hongbao.error.GlobalErrorCode;

public class ResponseObject<T> {
	private String moreInfo;
	private T data;
	private Boolean success  = true;
	
	private GlobalErrorCode status = GlobalErrorCode.SUCESS;
	
    private String 	message ;
    
    private ResultCode resultCode;

	/**
	 * 正常返回，有数据
	 * 
	 * @param data
	 */
	public ResponseObject(T data) {
		this.data = data;
	}

	/**
	 * 正常返回，无数据
	 */
	public ResponseObject() {
	}

	/**
	 * 错误状态返回
	 * 
	 * @param status
	 */
	public ResponseObject(GlobalErrorCode status) {
		this.status = status;
	}

	public ResponseObject(T data, GlobalErrorCode status) {
		this.data = data;
		this.status = status;
	}

	/**
	 * 错误状态返回
	 * 
	 * @param moreInfo
	 * @param status
	 */
	public ResponseObject(String moreInfo, GlobalErrorCode status) {
		this.moreInfo = moreInfo;
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getErrorCode() {
		return status.getErrorCode();
	}

	public String getError() {
		return status.getError();
	}

	public String getMoreInfo() {
		return moreInfo;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public GlobalErrorCode getStatus() {
		return status;
	}

	public void setStatus(GlobalErrorCode status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResultCode getResultCode() {
		return resultCode;
	}

	public void setResultCode(ResultCode code) {
		this.resultCode = resultCode;
	}
	
	
	
	
}
