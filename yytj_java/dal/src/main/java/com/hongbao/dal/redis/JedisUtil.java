package com.hongbao.dal.redis;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.hongbao.utils.DateUtil;
import com.hongbao.utils.SerializeUtil;

/**
 * 类JedisUtil.java的实现描述：TODO 类实现描述
 * 
 * @author tatos 2014年9月23日 下午3:39:08
 */
@Component
public class JedisUtil {
	private static Logger LOG = LoggerFactory.getLogger(JedisUtil.class);

	private ShardedJedisPool shardedJedisPool;

	private int period;

	private String nameSpace = "";

	/**
	 * 应用的名字作用域
	 */
	@Value("${appName}")
	private String appName = "";

	public boolean setObj(String key, Object object) {
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			byte[] bs = SerializeUtil.serialize(object);
			key = appName + "_" + nameSpace + "_" + key;
			if (period == 0) {
				jedis.set(key.getBytes(), bs);
			} else {
				jedis.setex(key.getBytes(), period, bs);
			}
			return true;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			shardedJedisPool.returnResource(jedis);
		}
		return false;
	}

	public boolean setData(String key, String value) {
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			if (period == 0) {
				jedis.set(appName + "_" + nameSpace + "_" + key, value);
			} else {
				jedis.setex(appName + "_" + nameSpace + "_" + key, period,
						value);
			}

			return true;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			shardedJedisPool.returnResource(jedis);
		}
		return false;
	}

	public String getData(String key) {
		String value = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			value = jedis.get(appName + "_" + nameSpace + "_" + key);

			return value;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);

		} finally {
			shardedJedisPool.returnResource(jedis);
		}
		return value;
	}

	/**
	 * 没有key名字域的key
	 * <p>
	 * 取cleankey 对应的的map中的field
	 * <p/>
	 * 
	 * @param cleankey
	 * @param field
	 * @return
	 */
	public String getNoAppNameHValue(String cleankey, String feild) {
		String value = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			value = jedis.hget(cleankey, feild);
			return value;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);

		} finally {
			shardedJedisPool.returnResource(jedis);
		}
		return value;
	}

	public void delete(String key) {
		ShardedJedis jedis = null;
		try {
			LOG.info("delete key=" + key);
			jedis = shardedJedisPool.getResource();
			jedis.del(appName + "_" + nameSpace + "_" + key);

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			shardedJedisPool.returnResource(jedis);
		}
	}

	/**
	 * 存入对象
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public Boolean setObject(String key, int period, Object obj) {
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			byte[] bs = SerializeUtil.serialize(obj);
			key = appName + "_" + nameSpace + "_" + key;
			jedis.setex(key.getBytes(), period, bs);
			return true;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			shardedJedisPool.returnResource(jedis);
		}
		return false;
	}

	/**
	 * 以hash的方式存入field value
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public Boolean hset(String key, String field, String value) {
		if (StringUtils.isBlank(value) || StringUtils.isBlank(key)
				|| StringUtils.isBlank(field)) {
			// LOG.warn("invalid param,key="+key +" field=" + field +
			// " value="+value);
			return false;
		}
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			key = appName + "_" + nameSpace + "_" + key;
			jedis.hset(key, field, value);
			return true;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			shardedJedisPool.returnResource(jedis);
		}
		return false;
	}

	/**
	 * 以hash的方式存入field value
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public String hget(String key, String field) {
		if (StringUtils.isBlank(field) || StringUtils.isBlank(key)) {
			// LOG.warn("invalid param,key="+key +" field=" + field +
			// " value="+value);
			return null;
		}
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			key = appName + "_" + nameSpace + "_" + key;
			String value = jedis.hget(key, field);
			return value;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			shardedJedisPool.returnResource(jedis);
		}
		return null;
	}

	/**
	 * 以hash的方式存入field value
	 * 
	 * @param key
	 * @param obj
	 * @return
	 */
	public Set<String> keys(String partten) {
		ShardedJedis jedis = null;
		Set<String> set = new HashSet<String>();
		try {
			partten = appName + "_" + nameSpace + "_" + partten;
			jedis = shardedJedisPool.getResource();
			return jedis.hkeys(partten);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			shardedJedisPool.returnResource(jedis);
		}
		return set;
	}

	/**
	 * 取出对象
	 * 
	 * @param key
	 * @return
	 */
	public Object getObj(String key) {

		byte[] bs = null;
		Object obj = null;
		ShardedJedis jedis = null;
		try {
			jedis = shardedJedisPool.getResource();
			key = appName + "_" + nameSpace + "_" + key;
			bs = jedis.get(key.getBytes());
			obj = SerializeUtil.unserialize(bs);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			shardedJedisPool.returnResource(jedis);
		}
		return obj;
	}

	public interface IfNull {
		Object get();
	}

	public Object getObj(String key, IfNull ifNull) {
		Object obj = null;
		try {
			obj = getObj(key);
			if (obj == null) {
				obj = ifNull.get();
				setObject(key, DateUtil.DAY_MILLIS, obj);
			}
		} catch (Exception e) {
			delete(key);
			LOG.error(e.getMessage(), e);
		}
		return obj;
	}

	/**
	 * 取出对象
	 * 
	 * @param key
	 * @return
	 */
	public Long increase(String key) {

		ShardedJedis jedis = null;
		Long count = 0L;
		try {
			jedis = shardedJedisPool.getResource();
			key = appName + "_" + nameSpace + "_" + key;
			count = jedis.incr(key);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			shardedJedisPool.returnResource(jedis);
		}
		return count;
	}

	/**
	 * 取出对象
	 * 
	 * @param key
	 * @return
	 */
	public Long clear(String key) {

		ShardedJedis jedis = null;
		Long count = 0L;
		try {
			jedis = shardedJedisPool.getResource();
			key = appName + "_" + nameSpace + "_" + key;
			count = jedis.del(key);
			LOG.info("delete key=" + key);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			shardedJedisPool.returnResource(jedis);
		}
		return count;
	}

	/**
	 * @return the shardedJedisPool
	 */
	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}

	/**
	 * @param shardedJedisPool
	 *            the shardedJedisPool to set
	 */
	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the period
	 */
	public int getPeriod() {
		return period;
	}

	/**
	 * @param period
	 *            the period to set
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	/**
	 * @return the nameSpace
	 */
	public String getNameSpace() {
		return nameSpace;
	}

	/**
	 * @param nameSpace
	 *            the nameSpace to set
	 */
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

}
