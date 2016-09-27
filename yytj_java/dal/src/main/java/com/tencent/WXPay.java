package com.tencent;

import com.tencent.business.DownloadBillBusiness;
import com.tencent.business.MchPayBusiness;
import com.tencent.business.RefundBusiness;
import com.tencent.business.RefundQueryBusiness;
import com.tencent.business.ScanPayBusiness;
import com.tencent.business.UnifiedOrderBusiness;
import com.tencent.common.config.WeiXinBizConfigure;
import com.tencent.common.config.WeiXinConfigure;
import com.tencent.common.config.WeiXinUserConfigure;
import com.tencent.protocol.downloadbill_protocol.DownloadBillReqData;
import com.tencent.protocol.mch_pay_protocol.MchPayReqData;
import com.tencent.protocol.mch_pay_protocol.MchPayResData;
import com.tencent.protocol.pay_protocol.ScanPayReqData;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryReqData;
import com.tencent.protocol.refund_protocol.RefundReqData;
import com.tencent.protocol.refund_query_protocol.RefundQueryReqData;
import com.tencent.protocol.reverse_protocol.ReverseReqData;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderClientReqData;
import com.tencent.protocol.unified_order_protocol.UnifiedOrderReqData;
import com.tencent.service.DownloadBillService;
import com.tencent.service.RefundQueryService;
import com.tencent.service.RefundService;
import com.tencent.service.ReverseService;
import com.tencent.service.ScanPayQueryService;
import com.tencent.service.ScanPayService;
import com.tencent.service.UnifiedOrderService;

/**
 * SDK总入口
 */
public class WXPay {
    public static WeiXinConfigure getConfigureByType(String type) {
        if (new WeiXinBizConfigure().getAppType().equals(type)) {
            return new WeiXinBizConfigure();
        } else if (new WeiXinUserConfigure().getAppType().equals(type)) {
            return new WeiXinUserConfigure();
        }
        return null;
    }
    /**
     * 请求支付服务
     * @param downloadBillReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public static String requestUnifiedOrderService(WeiXinConfigure weiXinConfigure,UnifiedOrderReqData unifiedOrderService) throws Exception{
        return new UnifiedOrderService(weiXinConfigure).request(unifiedOrderService);
    }
    /**
     * 请求支付服务
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public static String requestScanPayService(WeiXinConfigure weiXinConfigure ,ScanPayReqData scanPayReqData) throws Exception{
        return new ScanPayService(weiXinConfigure).request(scanPayReqData);
    }

    /**
     * 请求支付查询服务
     * @param scanPayQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
	public static String requestScanPayQueryService(WeiXinConfigure weiXinConfigure,ScanPayQueryReqData scanPayQueryReqData) throws Exception{
		return new ScanPayQueryService(weiXinConfigure).request(scanPayQueryReqData);
	}

    /**
     * 请求退款服务
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public static String requestRefundService(WeiXinConfigure weiXinConfigure,RefundReqData refundReqData) throws Exception{
        return new RefundService(weiXinConfigure).request(refundReqData);
    }

    /**
     * 请求退款查询服务
     * @param refundQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
	public static String requestRefundQueryService(WeiXinConfigure weiXinConfigure,RefundQueryReqData refundQueryReqData) throws Exception{
		return new RefundQueryService(weiXinConfigure).request(refundQueryReqData);
	}

    /**
     * 请求撤销服务
     * @param reverseReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
	public static String requestReverseService(WeiXinConfigure weiXinConfigure,ReverseReqData reverseReqData) throws Exception{
		return new ReverseService(weiXinConfigure).request(reverseReqData);
	}

    /**
     * 请求对账单下载服务
     * @param downloadBillReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public static String requestDownloadBillService(WeiXinConfigure weiXinConfigure,DownloadBillReqData downloadBillReqData) throws Exception{
        return new DownloadBillService(weiXinConfigure).request(downloadBillReqData);
    }

    /**
     * 直接执行被扫支付业务逻辑（包含最佳实践流程）
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws Exception
     */
    public static void doScanPayBusiness(WeiXinConfigure weiXinConfigure,ScanPayReqData scanPayReqData, ScanPayBusiness.ResultListener resultListener) throws Exception {
        new ScanPayBusiness(weiXinConfigure).run(scanPayReqData, resultListener);
    }

    /**
     * 调用退款业务逻辑
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 业务逻辑可能走到的结果分支，需要商户处理
     * @throws Exception
     */
    public static void doRefundBusiness(WeiXinConfigure weiXinConfigure,RefundReqData refundReqData, RefundBusiness.ResultListener resultListener) throws Exception {
        new RefundBusiness(weiXinConfigure).run(refundReqData,resultListener);
    }

    /**
     * 运行退款查询的业务逻辑
     * @param refundQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws Exception
     */
    public static void doRefundQueryBusiness(WeiXinConfigure weiXinConfigure,RefundQueryReqData refundQueryReqData,RefundQueryBusiness.ResultListener resultListener) throws Exception {
        new RefundQueryBusiness(weiXinConfigure).run(refundQueryReqData,resultListener);
    }

    /**
     * 请求对账单下载服务
     * @param downloadBillReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @return API返回的XML数据
     * @throws Exception
     */
    public static void doDownloadBillBusiness(WeiXinConfigure weiXinConfigure,DownloadBillReqData downloadBillReqData,DownloadBillBusiness.ResultListener resultListener) throws Exception {
        new DownloadBillBusiness(weiXinConfigure).run(downloadBillReqData,resultListener);
    }

    public static UnifiedOrderClientReqData doUnifiedOrderBusiness(WeiXinConfigure weiXinConfigure,UnifiedOrderReqData unifiedOrderReqData) throws Exception {
        return new UnifiedOrderBusiness(weiXinConfigure).run(unifiedOrderReqData);
    }
    public static MchPayResData doMchPayBusiness(WeiXinConfigure weiXinConfigure, MchPayReqData mchPayReqData) throws Exception {
        return new MchPayBusiness(weiXinConfigure).run(mchPayReqData);
    }

}
