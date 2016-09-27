package com.hongbao.service.test;

import com.hongbao.service.ResourceFacade;
import com.hongbao.service.test.base.BaseServiceTest;
import com.hongbao.service.user.impl.WeixinService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

public class WeixinServiceTest extends BaseServiceTest{
	@Autowired
    private WeixinService weixinService;
	@Test
	public void test(){
	    String openId = weixinService.getOpenId("0314349aa615312085f7e7f46be6d1c4");
        System.out.println("openId="+openId);
    }
}
