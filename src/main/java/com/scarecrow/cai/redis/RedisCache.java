package com.scarecrow.cai.redis;

public interface RedisCache {

	public void set(String key, Object value, int seconds);

	public void set(String key, Object value);

	public void expire(String key, int seconds);

	public void fuzzyExpire(String key, int seconds);

	public Object get(String key);

	public void flush();

}
