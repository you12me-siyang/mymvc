package com.wbh.mymvc.servlet;

import java.lang.reflect.Method;


public class Handler {
	
	private Class<?> controllerClass;
	private Method mappingMethod;
	private String requestMethod;
	
	public Handler(Class<?> controllerClass,Method mappingMethod,String requestMethod) {
		this.controllerClass = controllerClass;
		this.mappingMethod = mappingMethod;
		this.requestMethod = requestMethod;
	}
	
	public Class<?> getControllerClass() {
		return controllerClass;
	}
	public void setControllerClass(Class<?> controllerClass) {
		this.controllerClass = controllerClass;
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
