package com.tencent.business;

import com.hongbao.dal.base.exception.BizException;
import com.tencent.common.Util;
import com.tencent.common.config.WeiXinConfigure;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderClientReqData;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderReqData;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderResData;
import com.tencent.service.UnifiedOrderService;

public class UnifiedOrderBusiness {
    
    private WeiXinConfigure weiXinConfigure;
    public UnifiedOrderBusiness(WeiXinConfigure weiXinConfigure) {
        super();
        this.weiXinConfigure = weiXinConfigure;
    }

    public UnifiedOrderClientReqData run(UnifiedOrderReqData unifiedOrderReqData) throws Exception {
        UnifiedOrderService unifiedOrderService = new UnifiedOrderService(weiXinConfigure);
        String responseString = unifiedOrderService.request(unifiedOrderReqData);

        // 将从API返回的XML数据映射到Java对象
        UnifiedOrderResData unifiedOrderResData = (UnifiedOrderResData) Util.getObjectFromXML(responseString,
                UnifiedOrderResData.class);
        if (unifiedOrderResData.getResult_code().equals("FAIL")) {
            throw new BizException(unifiedOrderResData.getErr_code_des());
        }
        return new UnifiedOrderClientReqData(weiXinConfigure,unifiedOrderResData);
    }
}
