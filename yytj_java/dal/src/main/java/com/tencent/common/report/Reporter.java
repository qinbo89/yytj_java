package com.tencent.common.report;

import com.tencent.common.config.WeiXinConfigure;
import com.tencent.common.report.protocol.ReportReqData;
import com.tencent.common.report.service.ReportService;

/**
 * User: rizenguo
 * Date: 2014/12/3
 * Time: 11:42
 */
public class Reporter {

    private ReportRunable r;
    private Thread t;
    private ReportService rs;
    private WeiXinConfigure weiXinConfigure;
    /**
     * 请求统计上报API
     * @param reportReqData 这个数据对象里面包含了API要求提交的各种数据字段
     */
    public Reporter(WeiXinConfigure weiXinConfigure,ReportReqData reportReqData){
        rs = new ReportService(reportReqData);
        this.weiXinConfigure = weiXinConfigure;
    }

    public void run(){
        r = new ReportRunable(weiXinConfigure,rs);
        t = new Thread(r);
        t.setDaemon(true);  //后台线程
        t.start();
    }
}
