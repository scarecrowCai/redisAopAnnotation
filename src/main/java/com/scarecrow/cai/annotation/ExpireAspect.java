package com.scarecrow.cai.annotation;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.scarecrow.cai.annotation.ExpireAnnotation.ExpireType;
import com.scarecrow.cai.metadata.DataType;
import com.scarecrow.cai.redis.RedisClient;

@Aspect
@Component
public class ExpireAspect extends BaseAspect {

	@Resource
	private RedisClient redisClient;

	@Pointcut("execution(* com.scarecrow..*.*(..))")
	public void pointCut() {
	};

	@Around(value = "pointCut() && @annotation(com.scarecrow.cai.annotation.ExpireAnnotation)")
	public Object getCacheObj(ProceedingJoinPoint pointCut) throws Throwable {
		Method method = ((MethodSignature) pointCut.getSignature()).getMethod();
		Object[] args = pointCut.getArgs();
		Class<?>[] prifexes = method.getAnnotation(ExpireAnnotation.class).prefix();
		Class<?> clazz = method.getAnnotation(ExpireAnnotation.class).clazz();
		String domain = method.getAnnotation(ExpireAnnotation.class).domain();
		DataType dataType = method.getAnnotation(ExpireAnnotation.class).dataType();
		String[] params = method.getAnnotation(ExpireAnnotation.class).params();
		ExpireType expireType = method.getAnnotation(ExpireAnnotation.class).expireType();
		String cacheKey = getCacheKey(domain, dataType, prifexes, clazz, params, args);
		redisClient.expire(cacheKey, 0);
		if (!expireType.equals(ExpireType.SELF)) {
			String expireKey = getFuzzyExpireKey(expireType, domain, prifexes, clazz, params, args);
			redisClient.fuzzyExpire(expireKey, 0);
		}
		return pointCut.proceed();
	}

	private String getFuzzyExpireKey(ExpireType expireType, String domain, Class<?>[] prifexes, Class<?> clazz,
			String[] params, Object[] args) {
		StringBuffer key = new StringBuffer(domain).append("-").append(DataType.LIST);
		if (!expireType.equals(ExpireType.DOMAIN)) {
			for (Class<?> prifex : prifexes) {
				if (!prifex.getCanonicalName().equals(Object.class.getCanonicalName())) {
					key.append("-").append(prifex.getCanonicalName());
				}
			}
		} else {
			return key.toString();
		}
		if (!expireType.equals(ExpireType.PREFIX)) {
			key.append("-").append(clazz.getCanonicalName());
		} else {
			return key.toString();
		}
		if (!expireType.equals(ExpireType.CLAZZ)) {
			for (int i = 0, n = params.length; i < n; i++) {
				Object obj = getParamsValue(params[i], args);
				if (null != obj) {
					key.append("-").append(obj);
				}
			}
		} else {
			return key.toString();
		}
		return key.toString();
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
