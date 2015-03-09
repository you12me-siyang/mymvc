package com.wbh.mymvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyInterceptor {
	
	
	/**
	 * 默认全匹配，匹配规则如下
	 * "/*"项目根目录下匹配，不包含子目录
	 * "/**"项目下全匹配，包括所有子目录下的请求
	 * "/Example/*"即Example下的请求匹配，不包含子目录，以此类推
	 * "!:"后跟的目录为要排除的目录
	 * @return
	 */
	String[] mappingPath() default {"/**"};
	
	String interceptionMethod() default "both";//默认拦截GET,POST方法
	
	int index() ;
	
}
