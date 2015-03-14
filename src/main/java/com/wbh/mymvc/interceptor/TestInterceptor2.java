package com.wbh.mymvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wbh.mymvc.annotation.Bean;
import com.wbh.mymvc.annotation.MyInterceptor;
import com.wbh.mymvc.ui.MyModelAndView;

@Bean
@MyInterceptor(index = 2,mappingPath={"/member/*"})
public class TestInterceptor2 extends InterceptorAdapt {
	
	@Override
	public boolean beforeHandler(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		System.out.println("===============TestInterceptor2:beforeHandler=================");
		return super.beforeHandler(request, response);
	}
	
	@Override
	public void afterHandler(HttpServletRequest request,
			HttpServletResponse response, MyModelAndView myModelAndView) throws Exception {
		System.out.println("===============TestInterceptor2:afterHandler=================");
		super.afterHandler(request, response, myModelAndView);
	}
	
	@Override
	public void afterViewLoad(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("===============TestInterceptor2:afterViewLoad=================");
		super.afterViewLoad(request, response);
	}

}
