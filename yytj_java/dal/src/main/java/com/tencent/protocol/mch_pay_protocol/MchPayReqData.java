package com.tencent.protocol.mch_pay_protocol;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.tencent.common.RandomStringGenerator;
import com.tencent.common.Signature;
import com.tencent.common.config.WeiXinConfigure;

public class MchPayReqData {

    //每个字段具体的意思请查看API文档
    private String mchid = "";
    private String mch_appid = "";
    private String device_info = "";
    private String nonce_str = "";
    private String sign = "";
    private String partner_trade_no = "";
    private String openid="";
    private String check_name;
    private String re_user_name;
    private int amount;
    private String desc;
    private String spbill_create_ip = "";
    

    public MchPayReqData(WeiXinConfigure weiXinConfigure, String deviceInfo, String spBillCreateIP, String partner_trade_no, String openid, String check_name, String re_user_name,
            int amount, String desc) {

        // 微信分配的公众号ID（开通公众号之后可以获取到）
        setMch_appid(weiXinConfigure.getAppid());
        
        // 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
        setMchid(weiXinConfigure.getMchid());

        // 商户自己定义的扫码支付终端设备号，方便追溯这笔交易发生在哪台终端设备上
        setDevice_info(deviceInfo);

        // 订单生成的机器IP
        setSpbill_create_ip(spBillCreateIP);

        // 随机字符串，不长于32 位
        setNonce_str(RandomStringGenerator.getRandomStringByLength(32));

        setPartner_trade_no(partner_trade_no);
        setOpenid(openid);
        setCheck_name(check_name);
        setRe_user_name(re_user_name);
        setAmount(amount);
        setDesc(desc);

        // 根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap(), weiXinConfigure);
        setSign(sign);// 把签名数据设置到Sign这个属性中

    }


    public String getMch_appid() {
        return mch_appid;
    }


    public void setMch_appid(String mch_appid) {
        this.mch_appid = mch_appid;
    }



    public String getMchid() {
        return mchid;
    }


    public void setMchid(String mchid) {
        this.mchid = mchid;
    }


    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }


    public String getPartner_trade_no() {
        return partner_trade_no;
    }

    public void setPartner_trade_no(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCheck_name() {
        return check_name;
    }

    public void setCheck_name(String check_name) {
        this.check_name = check_name;
    }

    public String getRe_user_name() {
        return re_user_name;
    }

    public void setRe_user_name(String re_user_name) {
        this.re_user_name = re_user_name;
    }


    public int getAmount() {
        return amount;
    }


    public void setAmount(int amount) {
        this.amount = amount;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
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

}
