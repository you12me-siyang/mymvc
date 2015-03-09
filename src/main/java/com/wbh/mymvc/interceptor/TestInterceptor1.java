package com.wbh.mymvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wbh.mymvc.annotation.MyInterceptor;
import com.wbh.mymvc.ui.MyModelAndView;


@MyInterceptor(index = 1,interceptionMethod="GET")
public class TestInterceptor1 extends InterceptorAdapt {
	
	@Override
	public boolean beforeHandler(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		System.out.println("===============TestInterceptor1:beforeHandler=================");
		return super.beforeHandler(request, response);
	}
	
	@Override
	public void afterHandler(HttpServletRequest request,
			HttpServletResponse response, MyModelAndView myModelAndView) throws Exception {
		
		System.out.println("===============TestInterceptor1:afterHandler=================");
		super.afterHandler(request, response, myModelAndView);
	}
	
	@Override
	public void afterViewLoad(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("===============TestInterceptor1:afterViewLoad=================");
		super.afterViewLoad(request, response);
	}

}
