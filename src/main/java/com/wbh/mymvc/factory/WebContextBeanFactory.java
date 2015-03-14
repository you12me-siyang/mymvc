package com.wbh.mymvc.factory;

import java.lang.annotation.Annotation;
import java.util.List;

import com.wbh.mymvc.bean.BeanBody;
import com.wbh.mymvc.context.WebContext;

public interface WebContextBeanFactory extends BeanFactory , WebContext {
	
	List<Object> getBeansByAnnotation(Class<? extends Annotation> c);
	
	void removeBeanInstantiation(String beanName);
	
	void removeAllBeanInstantiation();

	List<BeanBody> getBeanBodysByAnnotation(Class<? extends Annotation> c);
	

}
