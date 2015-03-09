package com.wbh.mymvc.servlet;

import java.lang.reflect.Method;


public class Handler {
	
	private Object controller;
	private Method mappingMethod;
	private String requestMethod;
	
	public Handler(Object controller,Method mappingMethod,String requestMethod) {
		this.controller = controller;
		this.mappingMethod = mappingMethod;
		this.requestMethod = requestMethod;
	}
	
	
	public Object getController() {
		return controller;
	}
	public void setController(Object controller) {
		this.controller = controller;
	}
	public Method getMappingMethod() {
		return mappingMethod;
	}
	public void setMappingMethod(Method mappingMethod) {
		this.mappingMethod = mappingMethod;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

}
