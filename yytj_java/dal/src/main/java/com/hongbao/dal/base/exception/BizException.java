package com.hongbao.dal.base.exception;

import com.hongbao.dal.base.consts.ResultCode;
import com.hongbao.error.GlobalErrorCode;

 
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private ResultCode resultCode;
    private Object data;  //扩展数据
    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public BizException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }
    
    public BizException(GlobalErrorCode code,String message){
    	
    }

    public BizException(ResultCode resultCode,Object data) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
        this.data = data;
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public BizException(String message, ResultCode resultCode) {
        super(message);
        this.resultCode = resultCode;
    }

    public Object getData() {
        return data;
    }
}
