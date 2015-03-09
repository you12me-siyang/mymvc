package com.wbh.mymvc.util;

import com.wbh.mymvc.interceptor.BaseInterceptor;

public class InterceptorFactory {
	
	public static BaseInterceptor createInterceptor(Class<?> c){
		
		BaseInterceptor interceptor = null;
		
		try {
			interceptor = (BaseInterceptor) c.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return interceptor;
	}
	
}
