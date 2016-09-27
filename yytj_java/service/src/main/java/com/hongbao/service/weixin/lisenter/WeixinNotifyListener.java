package com.hongbao.service.weixin.lisenter;


public interface WeixinNotifyListener {
    void processWeiXinNotify(String tradeNo,String type);
}