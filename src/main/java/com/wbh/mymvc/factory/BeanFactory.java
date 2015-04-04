package com.wbh.mymvc.factory;

import java.util.List;

import com.wbh.mymvc.bean.BeanBody;

public interface BeanFactory {

	Object getBean(String beanName);
	
	BeanBody getBeanBody(String beanName);
	
	List<Object> getBeanByClass(Class<?> c);
	
}
