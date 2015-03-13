package com.wbh.mymvc.context;

import javax.servlet.ServletContext;

import com.wbh.mymvc.factory.BeanFactory;


/**
 * 
 * 基于web应用的bean上下文
 * @author wbh
 *
 */
public interface WebContext extends BeanFactory {

	void setServletContext(ServletContext servletContext);

}
