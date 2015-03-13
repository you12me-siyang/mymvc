package com.wbh.mymvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被该注解注解的类将被IOC托管
 * @author wbh
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Bean {
	
	//默认为空时，将用类名的全小写作为value，在IOC中的存在具有唯一性
	String value() default "";
	//是否懒加载，默认懒加载
	boolean isLazyInit() default true;
	//是否被缓存，默认为true，true时生命周期托管给IOC容器
	boolean isNeedCache() default true;
	
}