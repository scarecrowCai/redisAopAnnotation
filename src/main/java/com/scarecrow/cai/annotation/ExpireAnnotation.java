package com.scarecrow.cai.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Expire Annotation: Completed cache key is domain, prefixes, clazz, parameter
 * value combined with '-'
 * 
 * @author cailx
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpireAnnotation {
	/**
	 * cache key domain
	 */
	String domain();

	/**
	 * class chain after domain before self
	 */
	Class<?>[] prefix() default Object.class;

	/**
	 * return class
	 */
	Class<?> clazz();

	/**
	 * cache expire in seconds
	 */
	long expire() default 0;

	/**
	 * part of cache key : parameter index | index.key.key...
	 */
	String[] params(); // index | index.key

	/**
	 * expire data scope
	 */
	ExpireType expireType();

	/**
	 * Expire Type:
	 * 
	 * <pre>
	 * DOMAIN : expire whole domain data
	 * PREFIX : expire same prefix class data under domain
	 * CLAZZ  : expire same prefix class and class data under domain
	 * SELF   : only expire self
	 * </pre>
	 */
	public enum ExpireType {
		DOMAIN, PREFIX, CLAZZ, SELF
	}
}
