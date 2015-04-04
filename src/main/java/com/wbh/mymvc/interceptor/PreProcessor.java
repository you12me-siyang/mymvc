package com.wbh.mymvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wbh.mymvc.servlet.Handler;


public abstract class PreProcessor extends InterceptorAdapt {
	
	@Override
	public boolean beforeHandler(HttpServletRequest request,
			HttpServletResponse response, Handler h) throws Exception {
		
		
		return doProcess(request, h);
		
	}
	
	protected abstract boolean doProcess(HttpServletRequest request, Handler h);

}
