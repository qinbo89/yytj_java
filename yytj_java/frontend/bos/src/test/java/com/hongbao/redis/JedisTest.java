package com.hongbao.redis;

import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hongbao.keepalive.config.WebAppConfigurationAware;
import com.hongbao.dal.redis.JedisUtil;

public class JedisTest extends WebAppConfigurationAware {

    @Autowired
    private JedisUtil jedisUtil;
	@Test
	public void insertForwardInfo() throws Exception {
	    int actualVal = jedisUtil.increase(UUID.randomUUID().toString()).intValue();
	    System.out.println(actualVal);
	}
}
