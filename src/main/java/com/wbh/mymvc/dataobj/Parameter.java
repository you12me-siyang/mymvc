package com.wbh.mymvc.dataobj;

import java.lang.annotation.Annotation;

import com.wbh.mymvc.annotation.Param;


public class Parameter implements ElementValue{
	
	private int parameterIndex;
	private Annotation[] annotations;
	private Class<?> parameterClass;
	private Object parameterValue;
	


	public Parameter(int parameterIndex, Annotation[] annotations,
			Class<?> parameterClass) {
		super();
		this.parameterIndex = parameterIndex;
		this.annotations = annotations;
		this.parameterClass = parameterClass;
	}
	
	
	public Parameter(int parameterIndex, Annotation[] annotations,
			Class<?> parameterClass, Object parameterValue) {
		super();
		this.parameterIndex = parameterIndex;
		this.annotations = annotations;
		this.parameterClass = parameterClass;
		this.parameterValue = parameterValue;
	}


	@Override
	public Object getValue() {
		return parameterValue;
	}

	@Override
	public boolean isAnnotationPresent(
			Class<? extends Annotation> annotationClass) {
		for(Annotation a:annotations){
			if(annotationClass.isInstance(a)){
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		for(Annotation a:annotations){
			if((a.annotationType().getName()).equals(annotationClass.getName())){
				return  (T) a;
			}
		}
		return null;
	}

	@Override
	public Annotation[] getDeclaredAnnotations() {
		return annotations;
	}

	@Override
	public String getElementName() {
		return getAnnotation(Param.class).value();
	}
	
	public Class<?> getParameterClass() {
		return parameterClass;
	}
	public void setParameterClass(Class<?> parameterClass) {
		this.parameterClass = parameterClass;
	}
	public Annotation[] getAnnotations() {
		return annotations;
	}
	public void setAnnotations(Annotation[] annotations) {
		this.annotations = annotations;
	}
	public int getParameterIndex() {
		return parameterIndex;
	}
	public void setParameterIndex(int parameterIndex) {
		this.parameterIndex = parameterIndex;
	}
	public Object getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(Object parameterValue) {
		this.parameterValue = parameterValue;
	}


}
