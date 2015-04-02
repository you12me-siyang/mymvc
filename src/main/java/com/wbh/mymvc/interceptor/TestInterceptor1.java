package com.wbh.mymvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wbh.mymvc.annotation.Bean;
import com.wbh.mymvc.annotation.MyInterceptor;
import com.wbh.mymvc.servlet.Handler;
import com.wbh.mymvc.ui.RequestResult;

@Bean
@MyInterceptor(index = 1,interceptionMethod="GET")
public class TestInterceptor1 extends InterceptorAdapt {
	
	@Override
	public boolean beforeHandler(HttpServletRequest request,
			HttpServletResponse response, Handler h) throws Exception {
		
		System.out.println("===============TestInterceptor1:beforeHandler=================");
		return super.beforeHandler(request, response, h);
	}
	
	@Override
	public void afterHandler(HttpServletRequest request,
			HttpServletResponse response, RequestResult requestResult) throws Exception {
		
		System.out.println("===============TestInterceptor1:afterHandler=================");
		super.afterHandler(request, response, requestResult);
	}
	
	@Override
	public void afterViewLoad(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("===============TestInterceptor1:afterViewLoad=================");
		super.afterViewLoad(request, response);
	}

}
