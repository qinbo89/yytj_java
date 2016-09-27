package com.hongbao.service.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hongbao.service.PushService;
import com.hongbao.service.test.base.BaseServiceTest;

public class PushServiceTest extends BaseServiceTest{
	@Autowired
	private PushService pushService;
	@Test
	public void pushToTag(){
	    Map<String,String> map = new HashMap<String,String>();
	    map.put("message", "红包上架申请未通过审核");
	    map.put("type", "RUFUND_SUCCESS");
	    //pushService.pushToUser(1L, "title" + System.currentTimeMillis(),"message" + System.currentTimeMillis() , map);
	    while(true){
	        pushService.pushToTag("ALL","红包上架申请未通过审核" , map);
	        try {
                Thread.sleep(30000l);
            } catch (InterruptedException e) {
            }
	    }
	}
	
	@Test
    public void pushToUser(){
	    logger.info("push to user");
        Map<String,String> map = new HashMap<String,String>();
        map.put("message", "恭喜您，您已通过审核，欢迎入住云淘平台");
        map.put("type", "SHOP_AUDIT_SUCCESS");
        while(true){
            pushService.pushToUser(285l,"恭喜您，您已通过审核，欢迎入住云淘平台" , map);
            try {
                Thread.sleep(30000l);
            } catch (InterruptedException e) {
            }
        }
    }
}
