package com.hongbao.dal.log;

import com.hongbao.dal.base.exception.BizException;
import com.hongbao.dal.util.CustomizedPropertyConfigurer;
import com.hongbao.dal.util.SystemUtil;
import com.hongbao.utils.DateUtil;
import com.hongbao.utils.ExceptionUtils;
import com.hongbao.utils.JsonUtil;
import com.hongbao.utils.LogUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by shengshan.tang on 9/14/2015 at 5:48 PM
 */
public class HbLogContextMgr {

    private final static Logger log = LoggerFactory.getLogger(HbLogContextMgr.class);
    private final static Logger stackLog = LoggerFactory.getLogger("stackLog");

    private static String ip = SystemUtil.getIp();

    private static boolean isMatchApp = false;

    private static boolean isOfferLog = true;

    private static String appName = null;

    private static ThreadLocal<LogContext> statckThreadLocal = new ThreadLocal<LogContext>();

    private static Queue<HbLogBean> logQueue = new ConcurrentLinkedQueue<HbLogBean>();

    public static void errorLog(Throwable e){
        if(!isMatchApp){
            return;
        }
        LogContext context = getLogContext();
        if(context == null){
            return;
        }
        if(e instanceof BizException){
            context.setLogLevel(LogContext.LogLevel.BIZ_ERROR);
        }else{
            context.setLogLevel(LogContext.LogLevel.SYS_ERROR);
            String errMsg = ExceptionUtils.getPrintStackTrace(e);
            stackLog.info(errMsg);
        }
    }


    public static LogContext getLogContext(){
        return statckThreadLocal.get();
    }
    public static String getStackId(){
        return statckThreadLocal.get().getStackId();
    }

    private static String genStatckId(){
        StringBuilder sb = new StringBuilder();
        sb.append(appName);
        sb.append("-");
        sb.append(ip);
        sb.append("-");
        sb.append(new Date().getTime());
        sb.append(RandomStringUtils.random(4,false,true));
        return sb.toString();

    }

    public static void startStack(HttpServletRequest request){
        if(!isMatchApp || !isMatchUrl(request)){
            return;
        }
        LogContext context = new LogContext(genStatckId());
        context.setStartTime(DateUtil.getFmtyMdHmsSSSNoSymbol(new Date().getTime()));
        context.setLogLevel(LogContext.LogLevel.INFO);
        context.setUrl(request.getRequestURI());
        statckThreadLocal.set(context);

    }

    public static void endStack(HttpServletRequest request,HttpServletResponse response){
        if(!isMatchApp || !isMatchUrl(request)){
            return;
        }
        StringBuilder sb = new StringBuilder();
        String reqLog = LogUtils.getRequestLog(request);
        sb.append(reqLog);
        LogContext context = getLogContext();
        if(context == null){
            return;
        }
        int status = response.getStatus();
        if(status == HttpServletResponse.SC_NOT_FOUND || status == HttpServletResponse.SC_INTERNAL_SERVER_ERROR){
            context.setLogLevel(LogContext.LogLevel.SYS_ERROR);
        }
        context.setStatus(status);
        String resStr = " Response:{}";
        if(StringUtils.isNotEmpty(context.getRespString())){
            resStr = " Response:" + context.getRespString();
        }
        sb.append(resStr);
        try{
            Collection<String> headerNames = response.getHeaderNames();
            if(CollectionUtils.isNotEmpty(headerNames)){
                sb.append(" RseHeaders[");
                for(String headerName : headerNames){
                    String headerValue = response.getHeader(headerName);
                    sb.append(headerName);
                    sb.append("=");
                    sb.append(headerValue);
                    sb.append("^|^");
                }
                sb.append("]");
            }
            context.setEndTime(DateUtil.getFmtyMdHmsSSSNoSymbol(new Date().getTime()));
            stackLog.info(sb.toString());
        }catch (Exception e){
        }
    }

    public static void setUser(Long userId,String mobile){
        if(!isMatchApp){
            return;
        }
        LogContext context = getLogContext();
        if(context == null){
            return;
        }
        context.setUserId(userId);
        context.setMobile(mobile);
        statckThreadLocal.set(context);
    }

    public static void setResponse(Object respString){
        if(respString == null){
            return;
        }
        if(!isMatchApp){
            return;
        }
        LogContext context = getLogContext();
        if(context == null){
            return;
        }
        context.setRespString(JsonUtil.object2Json(respString));
        statckThreadLocal.set(context);
    }

    public static void offerLog(HbLogBean bean){
        if(!isMatchApp || !isOfferLog){
            return;
        }
        bean.setAppName(appName);
        logQueue.offer(bean);
    }

    public static List<HbLogBean> [] dispatchLog(){
        if(logQueue.isEmpty()){
            return null;
        }
        List<HbLogBean> masterLogList = new ArrayList<HbLogBean>();
        List<HbLogBean> msgLogList = new ArrayList<HbLogBean>();
        int size = logQueue.size();
        int len = 0;
        if(!isOfferLog && size < 500){  //open
            isOfferLog = true;
            log.info("offer log open!");
        }
        if(size <= 20){
            len = size;
        }else if(size > 20 && size <= 100){
            len = (int)(size * 0.8);
        }else if(size > 100 && size <= 5000){
            len = 60;
        }else if(size > 5000 && size <= 10000){  //丢弃部分
            int takeLen = (int)(size * 0.2);
            while(takeLen > 0){
                logQueue.poll();
                takeLen--;
            }
            len = 60;
        }else{  //大于10000，自动关闭offer log queue
            len = 60;
            isOfferLog = false;
            log.info("offer log closed!");
        }
        while(len > 0 && !logQueue.isEmpty()){
            HbLogBean logBean = logQueue.poll();
            if(logBean.isMaster()){
                masterLogList.add(logBean);
            }
            msgLogList.add(logBean);
            len--;
        }
        log.info("current logQueue size=" + size + ",get real master log size=" + masterLogList.size()+",msg log size="+msgLogList.size());
        return new List []{masterLogList,msgLogList};
    }


    public static boolean getAndInitMatchApp(){
        Object app = CustomizedPropertyConfigurer.getContextProperty("app");
        Object devType = CustomizedPropertyConfigurer.getContextProperty("spring.profiles.active");
        if(devType != null && devType.equals("dev")){
            app = System.getProperty("app");
        }
        if(app == null){
            isMatchApp = false;
            return isMatchApp;
        }
        appName = app.toString();
        log.info("appName=" + appName);
        if(StringUtils.equals(appName, "bos") || StringUtils.equals(appName,"biz") ||
                StringUtils.equals(appName,"proxy")  || StringUtils.equals(appName,"user") ){
            isMatchApp = true;
            return isMatchApp;
        }
        return isMatchApp;
    }

    public static boolean isMatchApp(){
        return isMatchApp;
    }

    public static boolean isMatchUrl(HttpServletRequest request){
        String url = request.getRequestURI();
        if(StringUtils.indexOf(url, "_resources") != -1){  //静态文件
            return false;
        }
        if(StringUtils.endsWithIgnoreCase(request.getRequestURL(), "checkServerStatus")){
            return false;     //过滤心跳
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new Date().getTime()+RandomStringUtils.random(4,false,true));
    }


}
