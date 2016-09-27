package com.hongbao.dal.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import redis.clients.jedis.ShardedJedisPool;

import com.hongbao.dal.redis.JedisUtil;
import com.hongbao.utils.DateUtil;

/**
 * 类JedisConfig.java的实现描述：TODO 类实现描述
 * 
 * @author tatos 2014年9月23日 下午3:27:33
 */
@Configuration
@ImportResource("classpath:/META-INF/applicationContext-jedis-dataSource.xml")
public class JedisConfig {
	private static Logger LOG = LoggerFactory.getLogger(JedisConfig.class);

	@Autowired
	private ShardedJedisPool shardedJedisPool;

	/**
	 * 应用的名字作用域
	 */
	@Value("${appName}")
	private String appName;

	@Bean
	public JedisUtil jedisUtil() {
		JedisUtil jedisUtil = new JedisUtil();
		jedisUtil.setAppName(appName);
		jedisUtil.setNameSpace("jedisUtil");
		jedisUtil.setShardedJedisPool(shardedJedisPool);
		return jedisUtil;
	}

	@Bean(name = "hongbaoCache")
	public JedisUtil hongbaoCache() {
		JedisUtil jedisUtil = new JedisUtil();
		jedisUtil.setAppName(appName);
		jedisUtil.setPeriod(DateUtil.HOUR_SECONDS);
		jedisUtil.setNameSpace("hongbaoCache");
		jedisUtil.setShardedJedisPool(shardedJedisPool);
		return jedisUtil;
	}

	@Bean(name = "userByLoginNameCache")
	public JedisUtil userByLoginNameCache() {
		JedisUtil jedisUtil = new JedisUtil();
		jedisUtil.setAppName(appName);
		jedisUtil.setPeriod(DateUtil.HOUR_SECONDS);
		jedisUtil.setNameSpace("userByLoginNameCache");
		jedisUtil.setShardedJedisPool(shardedJedisPool);
		return jedisUtil;
	}

	@Bean(name = "userByIdCache")
	public JedisUtil userByIdCache() {
		JedisUtil jedisUtil = new JedisUtil();
		jedisUtil.setAppName(appName);
		jedisUtil.setPeriod(DateUtil.HOUR_SECONDS);
		jedisUtil.setNameSpace("userByIdCache");
		jedisUtil.setShardedJedisPool(shardedJedisPool);
		return jedisUtil;
	}

	@Bean(name = "userScoreTopTenCache")
	public JedisUtil userScoreTopTenCache() {
		JedisUtil jedisUtil = new JedisUtil();
		jedisUtil.setAppName(appName);
		jedisUtil.setPeriod(DateUtil.HOUR_SECONDS);
		jedisUtil.setNameSpace("userScoreTopTenCache");
		jedisUtil.setShardedJedisPool(shardedJedisPool);
		return jedisUtil;
	}

	@Bean(name = "accessTokenCacheShike")
	public JedisUtil accessTokenCacheShike() {
		JedisUtil jedisUtil = new JedisUtil();
		jedisUtil.setAppName(appName);
		jedisUtil.setPeriod(DateUtil.HOUR_SECONDS);
		jedisUtil.setNameSpace("accessTokenCacheShike");
		jedisUtil.setShardedJedisPool(shardedJedisPool);
		return jedisUtil;
	}

	@Bean(name = "userHeadImgCache")
	public JedisUtil userHeadImgCache() {
		JedisUtil jedisUtil = new JedisUtil();
		jedisUtil.setAppName(appName);
		jedisUtil.setPeriod(DateUtil.HOUR_SECONDS);
		jedisUtil.setNameSpace("userHeadImgCache");
		jedisUtil.setShardedJedisPool(shardedJedisPool);
		return jedisUtil;
	}

	@Bean(name = "qrCodeTicketCache")
	public JedisUtil qrCodeTicketCache() {
		JedisUtil jedisUtil = new JedisUtil();
		jedisUtil.setAppName(appName);
		jedisUtil.setPeriod(DateUtil.HOUR_SECONDS);
		jedisUtil.setNameSpace("qrCodeTicketCache");
		jedisUtil.setShardedJedisPool(shardedJedisPool);
		return jedisUtil;
	}

	@Bean(name = "qrTicketUserIdCache")
	public JedisUtil qrTicketUserIdCache() {
		JedisUtil jedisUtil = new JedisUtil();
		jedisUtil.setAppName(appName);
		jedisUtil.setPeriod(DateUtil.HOUR_SECONDS);
		jedisUtil.setNameSpace("qrTicketUserIdCache");
		jedisUtil.setShardedJedisPool(shardedJedisPool);
		return jedisUtil;
	}

	@Bean(name = "loginSiginCache")
	public JedisUtil loginSiginCache() {
		JedisUtil jedisUtil = new JedisUtil();
		jedisUtil.setAppName(appName);
		jedisUtil.setPeriod(DateUtil.HOUR_SECONDS);
		jedisUtil.setNameSpace("loginSiginCache");
		jedisUtil.setShardedJedisPool(shardedJedisPool);
		return jedisUtil;
	}

	@Bean(name = "ticketCache")
	public JedisUtil ticketCache() {
		JedisUtil jedisUtil = new JedisUtil();
		jedisUtil.setAppName(appName);
		jedisUtil.setPeriod(DateUtil.HOUR_SECONDS);
		jedisUtil.setNameSpace("ticketCache");
		jedisUtil.setShardedJedisPool(shardedJedisPool);
		return jedisUtil;
	}
	
	@Bean(name = "countAddCache")
	public JedisUtil countAddCache() {
		JedisUtil jedisUtil = new JedisUtil();
		jedisUtil.setAppName(appName);
		jedisUtil.setPeriod(DateUtil.HOUR_SECONDS);
		jedisUtil.setNameSpace("countAddCache");
		jedisUtil.setShardedJedisPool(shardedJedisPool);
		return jedisUtil;
	}
}
