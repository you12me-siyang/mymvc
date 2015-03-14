package com.wbh.mymvc.bean;

public class BeanBody {
	
	private Class<?> beanClass;
	private Object bean;
	
	public BeanBody(Class<?> beanClass, Object bean) {
		super();
		this.beanClass = beanClass;
		this.bean = bean;
	}
	
	public Class<?> getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}
	public Object getBean() {
		return bean;
	}
	public void setBean(Object bean) {
		this.bean = bean;
	}
	
	

}
