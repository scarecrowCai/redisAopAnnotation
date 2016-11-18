package com.scarecrow.cai.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.scarecrow.cai.metadata.DataType;

/**
 * Read Annotation: Completed cache key is domain, prefixes, clazz, parameter
 * value combined with '-'
 * 
 * @author cailx
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadAnnotation {
	/**
	 * cache key domain
	 */
	String domain();

	/**
	 * cache data type info
	 */
	DataType dataType() default DataType.OBJECT;

	/**
	 * class chain after domain before self
	 */
	Class<?>[] prefix() default Object.class;

	/**
	 * return class
	 */
	Class<?> clazz();

	/**
	 * cache expire seconds
	 */
	long expire() default 30 * 60 * 1000;

	/**
	 * part of cache key : parameter index | index.key.key...
	 */
	String[] params();
}
