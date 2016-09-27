package com.tencent.service;

import com.tencent.common.config.WeiXinConfigure;
import com.tencent.protocol.mch_pay_protocol.MchPayReqData;
public class MchPayService extends BaseService{

    public MchPayService(WeiXinConfigure weiXinConfigure) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(weiXinConfigure,WeiXinConfigure.MCH_PAY_API);
    }

    public String request(MchPayReqData mchPayReqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(mchPayReqData);

        return responseString;
    }
}
