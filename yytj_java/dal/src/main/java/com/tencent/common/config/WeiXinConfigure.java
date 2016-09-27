package com.tencent.common.config;

public interface WeiXinConfigure {

    // 以下是几个API的路径：
    // 1）被扫支付API
    public static String PAY_API = "https://api.mch.weixin.qq.com/pay/micropay";

    // 2）被扫支付查询API
    public static final String PAY_QUERY_API = "https://api.mch.weixin.qq.com/pay/orderquery";

    // 3）退款API
    public static final String REFUND_API = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    // 4）退款查询API
    public static final String REFUND_QUERY_API = "https://api.mch.weixin.qq.com/pay/refundquery";

    // 5）撤销API
    public static final String REVERSE_API = "https://api.mch.weixin.qq.com/secapi/pay/reverse";

    // 6）下载对账单API
    public static final String DOWNLOAD_BILL_API = "https://api.mch.weixin.qq.com/pay/downloadbill";

    // 7) 统计上报API
    public static final String REPORT_API = "https://api.mch.weixin.qq.com/payitil/report";

    // 8) 统计上报API
    public static final String UNIFIED_ORDER_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    //public static String HttpsRequestClassName = "com.tencent.common.HttpsRequest";
    public static final String MCH_PAY_API = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    
    public abstract boolean isUseThreadToDoReport();
    
    public abstract String getAppType();
    
    public abstract String getKey();

    public abstract String getAppid();

    public abstract String getMchid();

    public abstract String getSubMchid();

    public abstract String getCertLocalPath();

    public abstract String getCertPassword();

    public abstract String getIP();

    public abstract String getSdkVersion();

}