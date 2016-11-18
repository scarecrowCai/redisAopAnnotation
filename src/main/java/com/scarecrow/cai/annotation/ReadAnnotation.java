package com.scarecrow.cai.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadAnnotation {
	String domain();

	Class<?>[] prifex() default Object.class;

	Class<?> clazz();

	long expire() default 30 * 60 * 1000;

	String[] params(); // index | index.key
}
