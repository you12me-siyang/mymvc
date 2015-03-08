package com.wbh.mymvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyRequestMapping {
	
	String value() default "/";
	MyRequestMethod method() default MyRequestMethod.GET; //默认请求方式为GET
	
}