package com.wbh.mymvc.servlet;

import java.lang.reflect.Method;

import com.wbh.mymvc.dataobj.Parameter;


public class Handler {
	
	private Object controller;
	private Method mappingMethod;
	private String requestMethod;
	private Parameter[] parameters;
	
	public Handler(Object controller,Method mappingMethod,String requestMethod) {
		this.controller = controller;
		this.mappingMethod = mappingMethod;
		this.requestMethod = requestMethod;
	}
	
	public Object[] getParameterValues(){
		
		Object[] os = new Object[parameters.length];
		for(int i=0;i<os.length;i++){
			os[i] = parameters[i].getParameterValue();
		}
		return os;
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
	public Parameter[] getParameters() {
		return parameters;
	}
	public void setParameters(Parameter[] parameters) {
		this.parameters = parameters;
	}
}
