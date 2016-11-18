package com.scarecrow.cai.redis;

import java.util.List;

public interface RedisCache {

	public void set(String key, String value, int seconds);

	public void set(String key, String value);

	public void expire(String key, int seconds);

	public void fuzzyExpire(String key, int seconds);

	public String get(String key);

	public boolean exist(String key);

	public void lpush(String key, String... values);

	public List<String> lrange(String key, int start, int end);

	public boolean lexist(String key, String value);

	public void incr(String key);
	
	public void flush();
}
