package com.wbh.mymvc.resolver;

import java.util.Properties;

import javax.servlet.ServletContext;

import com.wbh.mymvc.factory.support.DefaultBeanFactory;

public interface ConfiguredBeanResolver {
	
	void setProperties(Properties properties);
	
	void fillWithBeanDefinition(DefaultBeanFactory beanFactory,ServletContext servletContext);

}
