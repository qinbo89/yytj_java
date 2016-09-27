package com.hongbao.dal.log;

import ch.qos.logback.core.recovery.ResilientFileOutputStream;
import com.hongbao.dal.util.CustomizedPropertyConfigurer;
import com.hongbao.dal.util.SystemUtil;
import com.hongbao.utils.CompressUtils;
import com.hongbao.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

/**
 * Created by shengshan.tang on 9/14/2015 at 5:23 PM
 */
public class HbFileOutputStream extends ResilientFileOutputStream {

    private String ip = SystemUtil.getIp();

    public HbFileOutputStream(File file, boolean append) throws FileNotFoundException {
        super(file, append);
    }

    @Override
    public void write(byte[] b) throws IOException {
        //获取日志栈key
        LogContext logContext = HbLogContextMgr.getLogContext();
        if(logContext == null){
            return;
        }
        String stackId = logContext.getStackId();
        StringBuilder sb = new StringBuilder();
        String time = DateUtil.getFmtYMDHMS(new Date().getTime());
        sb.append(time);
        sb.append("^|^");
        sb.append(stackId);
        sb.append("^|^");
        String mobile = logContext.getMobile();
        Long userId = logContext.getUserId();
        sb.append(mobile);
        sb.append("^|^");
        sb.append(userId);
        sb.append("^|^");
        sb.append(logContext.getUrl());
        sb.append("^|^");

        //set bean
        HbLogBean logBean = new HbLogBean();
        logBean.setTime(time);
        logBean.setStackId(stackId);
        logBean.setUserId(userId);
        logBean.setMobile(mobile);
        logBean.setUrl(logContext.getUrl());
        //压缩
        String message = new String(b,"utf-8");
        if(StringUtils.startsWith(message,"Request From:")){
            logBean.setMaster(true);
        }
        byte compressByte [] = CompressUtils.compress(message);
        logBean.setMessage(compressByte);
        logBean.setIp(ip);
        logBean.setStatus(logContext.getStatus());
        logBean.setStartTime(logContext.getStartTime());
        logBean.setEndTime(logContext.getEndTime());
        logBean.setType(logContext.getLogLevel().name());
        HbLogContextMgr.offerLog(logBean);

        //合并byte
        byte [] destByte = sb.toString().getBytes();
        int byteLen = b.length + destByte.length+2;
        byte resultByte [] = new byte[byteLen];
        try{
            System.arraycopy(destByte,0,resultByte,0,destByte.length);
            System.arraycopy(b,0,resultByte,destByte.length,b.length);
        }catch(Exception e){
            e.printStackTrace();
        }
        resultByte[byteLen-2] = 13;  // /r
        resultByte[byteLen-1] = 10;  // /n
        super.write(resultByte);
    }

}
