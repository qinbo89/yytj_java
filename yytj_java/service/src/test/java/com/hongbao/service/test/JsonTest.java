package com.hongbao.service.test;

import org.junit.Assert;
import org.junit.Test;

import com.hongbao.dal.model.User;
import com.hongbao.service.test.base.BaseServiceTest;
import com.hongbao.utils.JsonUtil;

public class JsonTest extends BaseServiceTest{
	
	@Test
	public void tset(){
	    User user = new User();
	    user.setPassword("xxxxxxxxxxxxxxxxxx");
	    String json = JsonUtil.object2Json(user);
	    System.out.println("jsonStr = " + json);
	    User jsonUser = JsonUtil.json2Object(json, User.class);
        Assert.assertTrue(jsonUser.getPassword() == null);
	    System.out.println("jsonObj=" + jsonUser.getPassword());
	}
	
}
