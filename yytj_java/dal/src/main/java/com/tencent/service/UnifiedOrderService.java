package com.tencent.service;

import com.tencent.common.config.WeiXinConfigure;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderReqData;

public class UnifiedOrderService extends BaseService {

    public UnifiedOrderService(WeiXinConfigure weiXinConfigure) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(weiXinConfigure,WeiXinConfigure.UNIFIED_ORDER_API);
    }

    
    public String request(UnifiedOrderReqData unifiedOrderReqData) throws Exception {

        // --------------------------------------------------------------------
        // 发送HTTPS的Post请求到API地址
        // --------------------------------------------------------------------
        String responseString = sendPost(unifiedOrderReqData);
      
    
        return responseString;
    }

}
