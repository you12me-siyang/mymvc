package com.wbh.mymvc.bean;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class BeanDefinition {
	
	private String BeanName;
	
	private boolean isLazyInit;
	
	private boolean isNeedCache;
	
	private Set<BeanDefinition> injections = new HashSet<BeanDefinition>();
	
	private Method initMethod;
	
	private Method destroyMethod;
	
	private Class<?> BeanClass;
	
	public BeanDefinition() {
	}
	
	public BeanDefinition(String BeanName) {
		this.BeanName = BeanName;
	}

	public String getBeanName() {
		return BeanName;
	}

	public void setBeanName(String beanName) {
		BeanName = beanName;
	}

	public boolean isLazyInit() {
		return isLazyInit;
	}

	public void setLazyInit(boolean isLazyInit) {
		this.isLazyInit = isLazyInit;
	}

	public Set<BeanDefinition> getInjections() {
		return injections;
	}

	public void setInjections(Set<BeanDefinition> injections) {
		this.injections = injections;
	}

	public Method getInitMethod() {
		return initMethod;
	}

	public void setInitMethod(Method initMethod) {
		this.initMethod = initMethod;
	}

	public Method getDestroyMethod() {
		return destroyMethod;
	}

	public void setDestroyMethod(Method destroyMethod) {
		this.destroyMethod = destroyMethod;
	}

	public Class<?> getBeanClass() {
		return BeanClass;
	}

	public void setBeanClass(Class<?> beanClass) {
		BeanClass = beanClass;
	}

	public boolean isNeedCache() {
		return isNeedCache;
	}

	public void setNeedCache(boolean isNeedCache) {
		this.isNeedCache = isNeedCache;
	}
	

}
