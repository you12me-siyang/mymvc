package com.wbh.mymvc.bean;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BeanDefinition {
	
	private String BeanName;
	
	private boolean isLazyInit;
	
	private boolean isNeedCache;
	
	//需注入的引用
	private Set<BeanDefinition> injections = new HashSet<BeanDefinition>();
	//可配置变量 用于通过配置文件配置Bean,通过注解配置BeanDefinition时不使用
	private Map<String,Object> configurables = new HashMap<String ,Object>();
	
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

	public Map<String, Object> getConfigurables() {
		return configurables;
	}

	public void setConfigurables(Map<String, Object> configurables) {
		this.configurables = configurables;
	}
	
}
