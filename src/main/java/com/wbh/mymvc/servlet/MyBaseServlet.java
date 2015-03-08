package com.wbh.mymvc.servlet;

import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract  class MyBaseServlet extends HttpServlet {

	private static final long serialVersionUID = 6606173928603167760L;
	
	protected final Log logger = LogFactory.getLog(getClass());
}
