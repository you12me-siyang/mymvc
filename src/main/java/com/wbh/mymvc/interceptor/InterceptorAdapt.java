package com.wbh.mymvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wbh.mymvc.servlet.Handler;
import com.wbh.mymvc.ui.RequestResult;

/**
 * 拦截器适配器
 * @author wbh
 *
 */
public abstract class InterceptorAdapt implements BaseInterceptor {
	
	@Override
	public boolean beforeHandler(HttpServletRequest request,
			HttpServletResponse response,Handler h) throws Exception {
		return true;  //默认允许
	}
	
	@Override
	public void afterHandler(HttpServletRequest request,
			HttpServletResponse response, RequestResult requestResult) throws Exception {
	}
	
	@Override
	public void afterViewLoad(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

}
