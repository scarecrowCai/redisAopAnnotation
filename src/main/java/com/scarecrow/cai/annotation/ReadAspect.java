package com.scarecrow.cai.annotation;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.scarecrow.cai.metadata.DataType;
import com.scarecrow.cai.redis.RedisClient;

@Aspect
@Component
public class ReadAspect extends BaseAspect {

	@Resource
	private RedisClient redisClient;

	@Pointcut("execution(* com.scarecrow..*.*(..))")
	public void pointCut() {
	};

	@Around(value = "pointCut() && @annotation(com.scarecrow.cai.annotation.ReadAnnotation)")
	public Object getCacheObj(ProceedingJoinPoint pointCut) {
		Object[] args = pointCut.getArgs();
		Method method = ((MethodSignature) pointCut.getSignature()).getMethod();
		Class<?>[] prifexes = method.getAnnotation(ReadAnnotation.class).prefix();
		Class<?> clazz = method.getAnnotation(ReadAnnotation.class).clazz();
		String domain = method.getAnnotation(ReadAnnotation.class).domain();
		DataType dataType = method.getAnnotation(ReadAnnotation.class).dataType();
		String[] params = method.getAnnotation(ReadAnnotation.class).params();
		String cacheKey = getCacheKey(domain, dataType, prifexes, clazz, params, args);
		String result = redisClient.get(cacheKey);
		if (null != result) {
			if (!clazz.isPrimitive()) {
				Object obj = JSON.parse(result);
				if (obj.getClass() == JSONArray.class) {
					return JSON.parseArray(result, clazz);
				}
				return JSON.toJavaObject(JSON.parseObject(result), clazz);
			}
			return result;
		}
		try {
			Object returnValue = pointCut.proceed();
			if (null != returnValue) {
				redisClient.set(cacheKey, JSON.toJSONString(returnValue));
			}
			return returnValue;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getCacheKey(String domain, DataType dataType, Class<?>[] prifexes, Class<?> clazz, String[] params,
			Object[] args) {
		StringBuffer key = new StringBuffer(domain).append("-").append(dataType);
		for (Class<?> prifex : prifexes) {
			if (!prifex.getCanonicalName().equals(Object.class.getCanonicalName())) {
				key.append("-").append(prifex.getCanonicalName());
			}
		}
		key.append("-").append(clazz.getCanonicalName());
		for (int i = 0, n = params.length; i < n; i++) {
			Object obj = getParamsValue(params[i], args);
			if (null != obj) {
				key.append("-").append(obj);
			}
		}
		return key.toString();
	}

}
