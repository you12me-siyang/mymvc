package com.wbh.mymvc.entity;

import java.lang.reflect.Method;

public class Handler {
	
	private Class<?> controllerClass;
	private Method mappingMethod;
	
	public Handler(Class<?> controllerClass,Method mappingMethod) {
		this.controllerClass = controllerClass;
		this.mappingMethod = mappingMethod;
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

}
