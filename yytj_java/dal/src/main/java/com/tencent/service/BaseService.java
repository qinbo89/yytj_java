package com.tencent.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tencent.common.HttpsRequest;
import com.tencent.common.config.WeiXinConfigure;

/**
 * User: rizenguo
 * Date: 2014/12/10
 * Time: 15:44
 * 服务的基类
 */
public class BaseService{
    public  Logger logger = LoggerFactory.getLogger(this.getClass()); 

    //API的地址
    private String apiURL;

    //发请求的HTTPS请求器
    private IServiceRequest serviceRequest;

    public BaseService(WeiXinConfigure weiXinConfigure,String api) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        apiURL = api;
        //Class c = Class.forName(WeiXinConfigure.HttpsRequestClassName);
        //serviceRequest = (IServiceRequest) c.newInstance();
        try {
            serviceRequest = new HttpsRequest(weiXinConfigure);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        
    }

    protected String sendPost(Object xmlObj) throws UnrecoverableKeyException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String responseString =  serviceRequest.sendPost(apiURL,xmlObj);
        logger.warn("responseString=" + responseString);
        return responseString;
    }

    /**
     * 供商户想自定义自己的HTTP请求器用
     * @param request 实现了IserviceRequest接口的HttpsRequest
     */
    public void setServiceRequest(IServiceRequest request){
        serviceRequest = request;
    }
}
