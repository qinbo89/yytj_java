package com.tencent.protocol.unified_order_protocol;

/**
 * User: rizenguo
 * Date: 2014/10/22
 * Time: 21:29
 */

import java.io.Serializable;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.tencent.common.config.WeiXinConfigure;

/**
 * 请求被扫支付API需要提交的数据
 */
public class UnifiedOrderClientReqData  implements Serializable{

    private static final long serialVersionUID = 1L;
    private String appId;
    private String partnerId;
    private String prepayId;
    private String packageValue;
    private String nonceStr;
    private String timeStamp;
    private String sign;

    public UnifiedOrderClientReqData(WeiXinConfigure weiXinConfigure,UnifiedOrderResData unifiedOrderResData) {

        this.appId = weiXinConfigure.getAppid();
        this.partnerId = weiXinConfigure.getMchid();
        this.prepayId = unifiedOrderResData.getPrepay_id();
        this.packageValue = "Sign=WXPay";
        this.nonceStr = genNonceStr();
        this.timeStamp = String.valueOf(genTimeStamp());

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", this.appId));
        signParams.add(new BasicNameValuePair("noncestr", this.nonceStr));
        signParams.add(new BasicNameValuePair("package", this.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", this.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", this.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", this.timeStamp));

        this.sign = genAppSign(signParams,weiXinConfigure);

    }

    private String genNonceStr() {
        Random random = new Random();
        return getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private String genAppSign(List<NameValuePair> params,WeiXinConfigure weiXinConfigure) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(weiXinConfigure.getKey());

        String appSign = getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return appSign;
    }

    public final static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if (obj != null) {
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "UnifiedOrderClientReqData [appId=" + appId + ", partnerId=" + partnerId + ", prepayId=" + prepayId
                + ", packageValue=" + packageValue + ", nonceStr=" + nonceStr + ", timeStamp=" + timeStamp + ", sign="
                + sign + "]";
    }
}
