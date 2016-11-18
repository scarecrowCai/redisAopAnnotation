package com.scarecrow.cai.redis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
	private Map<String, Object> cacahe = new HashMap<String, Object>();

	// @PostConstruct
	// private void init() {
	// // Jedis Cluster will attempt to discover cluster nodes automatically
	// jedisClusterNodes.add(new HostAndPort("127.0.0.1", 6379));
	// jedisCluster = new JedisCluster(jedisClusterNodes);
	// }

	@Override
	public void set(String key, Object value, int seconds) {
		// TODO use jedisCluster
	}

	@Override
	public void set(String key, Object value) {
		// TODO use jedisCluster
		logger.info("set to cache, key = {}, value - {}", key, value);
		cacahe.put(key, value);
	}

	@Override
	public void expire(String key, int seconds) {
		// TODO use jedisCluster
		Object _value = cacahe.get(key);
		if (null != _value) {
			logger.info("remove from cache, key = {}, Object = {}", key, _value);
			cacahe.remove(key);
		}
	}

	@Override
	public void fuzzyExpire(String key, int seconds) {
		// TODO use jedisCluster
		// Set<String> keys = jedisCluster.hkeys(key);
		// for (String _key : keys) {
		// expire(_key, seconds);
		// }
		Iterator<Entry<String, Object>> it = cacahe.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
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
	public Object get(String key) {
		// TODO use jedisCluster
		Object result = cacahe.get(key);
		if (null != result) {
			logger.info("get from cache, key = {}, Object = {}", key, result);
		}
		return result;
	}

	@Override
	public void flush() {
		// TODO use jedisCluster
	}

}
