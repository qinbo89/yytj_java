package com.tencent.business;

import com.hongbao.dal.base.exception.BizException;
import com.tencent.common.Util;
import com.tencent.common.config.WeiXinConfigure;
import com.tencent.protocol.mch_pay_protocol.MchPayReqData;
import com.tencent.protocol.mch_pay_protocol.MchPayResData;
import com.tencent.service.MchPayService;

public class MchPayBusiness {
    
    private WeiXinConfigure weiXinConfigure;
    public MchPayBusiness(WeiXinConfigure weiXinConfigure) {
        super();
        this.weiXinConfigure = weiXinConfigure;
    }

    public MchPayResData run(MchPayReqData mchPayReqData) throws Exception {
        MchPayService mchPayService = new MchPayService(weiXinConfigure);
        String responseString = mchPayService.request(mchPayReqData);

        // 将从API返回的XML数据映射到Java对象
        MchPayResData mchPayResData = (MchPayResData) Util.getObjectFromXML(responseString,
                MchPayResData.class);
        if (mchPayResData.getResult_code().equals("FAIL")) {
            throw new BizException(mchPayResData.getErr_code_des());
        }
        return mchPayResData;
    }
}
