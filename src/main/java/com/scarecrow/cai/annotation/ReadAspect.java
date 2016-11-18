package com.scarecrow.cai.annotation;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

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
		Class<?>[] prifexes = method.getAnnotation(ReadAnnotation.class).prifex();
		Class<?> clazz = method.getAnnotation(ReadAnnotation.class).clazz();
		String domain = method.getDeclaredAnnotation(ReadAnnotation.class).domain();
		String[] params = method.getDeclaredAnnotation(ReadAnnotation.class).params();
		StringBuffer key = new StringBuffer(domain);
		for (Class<?> prifex : prifexes) {
			if (!prifex.getClass().isInstance(Object.class)) {
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
		Object result = redisClient.get(key.toString());
		if (null != result) {
			return result;
		}
		try {
			result = pointCut.proceed();
			if (null != result) {
				redisClient.set(key.toString(), result);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}

}
