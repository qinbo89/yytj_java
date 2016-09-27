package com.tencent.common.config;


/**
 * User: rizenguo Date: 2014/10/29 Time: 14:40 这里放置各种配置数据
 */
public class WeiXinUserConfigure implements WeiXinConfigure {
    private static final String appType = "user";
    // sdk的版本号
    private static final String sdkVersion = "java sdk 1.0.1";

    // 这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
    // 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
    // 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改

    private static String key = "724f058bb26c411ba0f9d8fd75d1e602";

    // 微信分配的公众号ID（开通公众号之后可以获取到）
    private static String appID = "wxb64240a9d753253f";

    // 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
    private static String mchID = "1248780301";

    // 受理模式下给子商户分配的子商户号
    private static String subMchID = "";

    // HTTPS证书的本地路径
    private static String certLocalPath = "/com/tencent/common/user/cert/apiclient_cert.p12";

    // HTTPS证书密码，默认密码等于商户号MCHID
    private static String certPassword = "1248780301";

    // 是否使用异步线程的方式来上报API测速，默认为异步模式
    private static boolean useThreadToDoReport = false;

    // 机器IP
    private static String ip = "";

    /*
     * static{ certLocalPath =
     * Configure.class.getResource("user/cert/apiclient_cert.p12").getPath(); }
     */

    /* (non-Javadoc)
     * @see com.tencent.common.WeiXinConfigure#isUseThreadToDoReport()
     */
    @Override
    public  boolean isUseThreadToDoReport() {
        return useThreadToDoReport;
    }


    /* (non-Javadoc)
     * @see com.tencent.common.WeiXinConfigure#getKey()
     */
    @Override
    public String getKey() {
        return key;
    }

    /* (non-Javadoc)
     * @see com.tencent.common.WeiXinConfigure#getAppid()
     */
    @Override
    public String getAppid() {
        return appID;
    }

    /* (non-Javadoc)
     * @see com.tencent.common.WeiXinConfigure#getMchid()
     */
    @Override
    public String getMchid() {
        return mchID;
    }

    /* (non-Javadoc)
     * @see com.tencent.common.WeiXinConfigure#getSubMchid()
     */
    @Override
    public String getSubMchid() {
        return subMchID;
    }

    /* (non-Javadoc)
     * @see com.tencent.common.WeiXinConfigure#getCertLocalPath()
     */
    @Override
    public String getCertLocalPath() {
        return certLocalPath;
    }

    /* (non-Javadoc)
     * @see com.tencent.common.WeiXinConfigure#getCertPassword()
     */
    @Override
    public String getCertPassword() {
        return certPassword;
    }

    /* (non-Javadoc)
     * @see com.tencent.common.WeiXinConfigure#getIP()
     */
    @Override
    public String getIP() {
        return ip;
    }

    /* (non-Javadoc)
     * @see com.tencent.common.WeiXinConfigure#getSdkVersion()
     */
    @Override
    public String getSdkVersion() {
        return sdkVersion;
    }


    @Override
    public String getAppType() {
        return appType;
    }


    @Override
    public String toString() {
        return "WeiXinUserConfigure [isUseThreadToDoReport()=" + isUseThreadToDoReport() + ", getKey()=" + getKey() + ", getAppid()=" + getAppid() + ", getMchid()=" + getMchid()
                + ", getSubMchid()=" + getSubMchid() + ", getAppType()=" + getAppType() + "]";
    }
}
