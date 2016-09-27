package com.hongbao.service.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hongbao.dal.redis.JedisUtil;
import com.hongbao.service.test.base.BaseServiceTest;
import com.hongbao.utils.JsonUtil;

public class JedisTest extends BaseServiceTest{
	@Autowired
	private JedisUtil jedisUtil;
	@Test
	public void insert(){
        Object result = jedisUtil.getObj("d0c6ba6c-2ae7-48be-92de-a26f9ea825ea");
        System.err.println(JsonUtil.object2Json(result));
	}
}
