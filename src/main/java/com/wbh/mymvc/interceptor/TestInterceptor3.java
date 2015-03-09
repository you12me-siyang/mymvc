package com.wbh.mymvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wbh.mymvc.annotation.MyInterceptor;
import com.wbh.mymvc.ui.MyModelAndView;


@MyInterceptor(index = 3,mappingPath={"/member/*","!:/member/member"})
public class TestInterceptor3 extends InterceptorAdapt {
	
	@Override
	public boolean beforeHandler(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		System.out.println("===============TestInterceptor3:beforeHandler=================");
		return super.beforeHandler(request, response);
	}
	
	@Override
	public void afterHandler(HttpServletRequest request,
			HttpServletResponse response, MyModelAndView modelAndView) throws Exception {
		System.out.println("===============TestInterceptor2:afterHandler=================");
		super.afterHandler(request, response, modelAndView);
	}
	
	@Override
	public void afterViewLoad(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		System.out.println("===============TestInterceptor2:afterViewLoad=================");
		super.afterViewLoad(request, response);
	}

}
