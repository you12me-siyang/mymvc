package com.wbh.mymvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wbh.mymvc.ui.MyModelAndView;

/**
 * 基本的拦截器接口
 * @author wbh
 * 
 */
public interface BaseInterceptor {
	
	/**
	 * 先于获得handler
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	boolean beforeHandler(HttpServletRequest request, HttpServletResponse response)
		    throws Exception;
	
	/**
	 * handler执行后执行，先于view加载
	 * @param request
	 * @param response
	 * @param handler
	 * @throws Exception
	 */
	void afterHandler(HttpServletRequest request, HttpServletResponse response, MyModelAndView modelAndeView)
		    throws Exception;
	
	/**
	 * view加载后执行
	 * @param request
	 * @param response
	 * @param handler
	 * @throws Exception
	 */
	void afterViewLoad(HttpServletRequest request, HttpServletResponse response)
		    throws Exception;
	
}
