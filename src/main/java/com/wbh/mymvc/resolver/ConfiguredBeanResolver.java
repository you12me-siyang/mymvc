package com.wbh.mymvc.resolver;

import java.util.Properties;

import javax.servlet.ServletContext;

import com.wbh.mymvc.factory.support.AbstractBeanFactory;

public interface ConfiguredBeanResolver {
	
	void setProperties(Properties properties);
	
	void fillWithBeanDefinition(AbstractBeanFactory beanFactory,ServletContext servletContext);

}
