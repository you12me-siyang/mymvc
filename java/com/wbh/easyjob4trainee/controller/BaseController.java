package com.wbh.easyjob4trainee.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.support.RequestContext;

import com.wbh.easyjob4trainee.util.Constants;

@Controller
public class BaseController {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected String basePath;
	protected String referer;
	protected String url;
	protected Logger logger;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
		// this.response.setHeader("Access-Control-Allow-Origin","*");//处理跨域请求
		this.session = request.getSession();
		this.referer = request.getHeader("Referer");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		this.url = "http://" + request.getServerName() + ":"
				+ request.getServerPort() 
				+ httpRequest.getContextPath()
				+ httpRequest.getServletPath();
		this.basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath()
				+ "/";
		request.setAttribute(Constants.URL, url);
		sessionPut(Constants.BASE_PATH, basePath);
	}

	public String getMessage(String msgKey) {
		return new RequestContext(request).getMessage(msgKey);
	}

	// 获取session中的对象
	public Object sessionGet(String key) {
		return session.getAttribute(key);
	}

	// 设置Session
	public void sessionPut(String key, Object value) {
		session.setAttribute(key, value);
	}

	// 删除session中的对象
	public void sessionDel(String key) {
		sessionDel(key);
	}

	public void addPageMSG(String message) {
		request.setAttribute(Constants.PAGE_MSG, getMessage(message));
	}

}