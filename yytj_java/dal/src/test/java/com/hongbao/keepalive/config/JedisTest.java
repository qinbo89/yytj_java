/*
 * Copyright 2010-2013 idonoo.com All right reserved. This software is the
 * confidential and proprietary information of idonoo.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with idonoo.com.
 */
package com.hongbao.keepalive.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hongbao.dal.config.ApplicationConfig;
import com.hongbao.dal.config.JedisConfig;
import com.hongbao.dal.redis.JedisUtil;

/**
 * 类Jedis.java的实现描述：TODO 类实现描述
 * 
 * @author lv 2014年9月23日 下午5:06:57
 */
@Configuration
@ImportResource("classpath:/META-INF/applicationContext-jedis-dataSource.xml")
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {  ApplicationConfig.class, JedisConfig.class })
public class JedisTest {

	@Autowired
	private JedisUtil jedisUtil;

	@Test
	public void setData() {
		// jedisUtil.setData("lyl", "111");
		// System.out.println(jedisUtil.getData("lyl"));
	}

	@Test
	public void increase() {
		//for (int i = 0; i < 100; i++) {
			
		//	 jedisUtil.setData("lyl"+i, "222");
		//}
		
		for (int i = 0; i < 100; i++) {
			
			 System.out.println(jedisUtil.getData("lyl"+i));
		}
	}
}
