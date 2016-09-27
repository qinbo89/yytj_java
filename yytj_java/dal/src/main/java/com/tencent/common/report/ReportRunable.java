package com.tencent.common.report;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import com.tencent.common.config.WeiXinConfigure;
import com.tencent.common.report.service.ReportService;

/**
 * User: rizenguo
 * Date: 2014/12/3
 * Time: 16:34 
 */
public class ReportRunable implements Runnable {

    private ReportService reportService ;
    private WeiXinConfigure weiXinConfigure;
    ReportRunable(WeiXinConfigure weiXinConfigure,ReportService rs){
        reportService = rs;
        this.weiXinConfigure = weiXinConfigure;
    }

    public void run() {
        try {
            reportService.request(weiXinConfigure);
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
