package com.hongbao.error;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GlobalErrorCode {
	//
	SUCESS(200, "Success"),
	//
	UNAUTHORIZED(401, "Unauthorized"),
	//
	NOT_FOUND(404, "Resource not found"),
	//
	INTERNAL_ERROR(500, "Server internal error"),

	INVALID_ARGUMENT(11001, "Invalid argument"),

	NOAPP(11002, "no this app"),

	PHONE_REGISTERED(11100, "phone_registered"),

	ONDUTYED(11101, "ONDUTYED"),

	HASBIND(11102, "HASBIND"),

	NOATTEN(11103, "NOATTEN"), MOBILE_REGISTERED(11100, "mobile_registered"), TOKEN_VALIDATE_FAIL(
			11104, "TOKEN_VALIDATE_FAIL"), STATUS_UPDATED(11104,
			"STATUS_UPDATED"),
	
	WEIPAY(11201, "WEIOAY"),
	//
	UNKNOWN(-1, "Unknown error"), BIZ_ERROR(11103, "BIZ_ERROR");

	private static final Map<Integer, GlobalErrorCode> values = new HashMap<Integer, GlobalErrorCode>();
	static {
		for (GlobalErrorCode ec : GlobalErrorCode.values()) {
			values.put(ec.errorCode, ec);
		}
	}

	private int errorCode;
	private String error;

	private GlobalErrorCode(int errorCode, String error) {
		this.errorCode = errorCode;
		this.error = error;
	}

	public static GlobalErrorCode valueOf(int code) {
		GlobalErrorCode ec = values.get(code);
		if (ec != null)
			return ec;
		return UNKNOWN;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getError() {
		return error;
	}
}
