package com.hongbao.dal.base.consts;


public enum ResultCode {
    NOT_LOGIN("NOT_LOGIN", "请登录"), 
    ACCESS_LIMITED("ACCESS_LIMITED", "访问受限，请联系客服。"), 
    SYSTEM_BUSY("SYSTEM_BUSY", "系统繁忙，请重试。"),

    PROXY_NOT_OPEN("PROXY_NOT_OPEN","该区域代理商暂时未开通"),
    
    ORDER_PAY_CANCELED("ORDER_PAY_CANCELED","当前交易不能撤销"),
    
    ORDER_PAY_REFUNDE_FAIL("ORDER_PAY_REFUNDE_FAIL","当前交易退款失败"),
    
    PAY_PASSWD_ERROR("PAY_PASSWD_ERROR","支付密码错误"),
    
    SHOP_BUY_HONGBAO_NONE("SHOP_BUY_HONGBAO_NONE","该红包已被领完，请领其他红包"),

    ;

    private String code, msg;
    private ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public static ResultCode getByCode(String code) {
        for (ResultCode s : ResultCode.values()) {
            if (s.getCode().equals(code)) {
                return s;
            }
        }
        return null;
    }
    
}
