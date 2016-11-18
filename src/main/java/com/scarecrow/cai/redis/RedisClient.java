package com.scarecrow.cai.redis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

@Component
public class RedisClient implements RedisCache {
	private Logger logger = LoggerFactory.getLogger(RedisClient.class);
	private Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
	private JedisCluster jedisCluster;
	private Map<String, String> cacahe = new HashMap<String, String>();

	// @PostConstruct
	// private void init() {
	// // Jedis Cluster will attempt to discover cluster nodes automatically
	// jedisClusterNodes.add(new HostAndPort("127.0.0.1", 6379));
	// jedisCluster = new JedisCluster(jedisClusterNodes);
	// }

	@Override
	public void set(String key, String value, int seconds) {
		// TODO Auto-generated method stub
	}

	@Override
	public void set(String key, String value) {
		// TODO Auto-generated method stub
		logger.info("set to cache, key = {}, value - {}", key, value);
		cacahe.put(key, value);
	}

	@Override
	public void expire(String key, int seconds) {
		// TODO Auto-generated method stub
		Object _value = cacahe.get(key);
		if (null != _value) {
			logger.info("remove from cache, key = {}, Object = {}", key, _value);
			cacahe.remove(key);
		}
	}

	@Override
	public void fuzzyExpire(String key, int seconds) {
		// TODO Auto-generated method stub
		// Set<String> keys = jedisCluster.hkeys(key);
		// for (String _key : keys) {
		// expire(_key, seconds);
		// }
		Iterator<Entry<String, String>> it = cacahe.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String _key = entry.getKey();
			Object _value = entry.getValue();
			if (_key.startsWith(key)) {
				logger.info("remove from cache, key = {}, Object = {}", _key, _value);
				it.remove();
				cacahe.remove(_key);
			}
		}
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		String result = cacahe.get(key);
		if (null != result) {
			logger.info("get from cache, key = {}, Object = {}", key, result);
		}
		return result;
	}

	@Override
	public boolean exist(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void lpush(String key, String... values) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> lrange(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean lexist(String key, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void incr(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

}
