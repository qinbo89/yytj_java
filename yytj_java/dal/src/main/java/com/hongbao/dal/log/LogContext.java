package com.hongbao.dal.log;

/**
 * Created by shengshan.tang on 9/14/2015 at 9:06 PM
 */
public class LogContext {

    public enum LogLevel{
        SYS_ERROR,BIZ_ERROR,INFO
    }

    private Long userId;

    private String mobile;

    private String stackId;

    private String url;

    private LogLevel logLevel;

    private int status;

    private String startTime;

    private String endTime;

    private String respString;

    public LogContext(String stackId) {
        this.stackId = stackId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStackId() {
        return stackId;
    }

    public void setStackId(String stackId) {
        this.stackId = stackId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRespString() {
        return respString;
    }

    public void setRespString(String respString) {
        this.respString = respString;
    }
}
