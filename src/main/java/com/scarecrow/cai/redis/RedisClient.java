package com.scarecrow.cai.redis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

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

	@PostConstruct
	private void init() {
		// Jedis Cluster will attempt to discover cluster nodes automatically
		jedisClusterNodes.add(new HostAndPort("127.0.0.1", 6379));
		jedisCluster = new JedisCluster(jedisClusterNodes);
	}

	@Override
	public void set(String key, Object value, int seconds) {
		// TODO Auto-generated method stub

	}

	@Override
	public void set(String key, Object value) {
		logger.info("set to cache, key = {}, value - {}", key, value);
		cacahe.put(key, value);
	}

	@Override
	public void expire(String key, int seconds) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fuzzyExpire(String key, int seconds) {
		Set<String> keys = jedisCluster.hkeys(key);
		for (String _key : keys) {
			expire(_key, seconds);
		}
	}

	@Override
	public Object get(String key) {
		logger.info("get from cache, key = {}", key);
		return cacahe.get(key);
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

}
