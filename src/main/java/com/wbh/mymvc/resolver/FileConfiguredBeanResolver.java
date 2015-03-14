package com.wbh.mymvc.resolver;

import java.util.List;

import javax.servlet.ServletContext;

import com.wbh.mymvc.bean.BeanDefinition;


public abstract class FileConfiguredBeanResolver extends
		AbstractConfiguredBeanResolver {
	
	
	public abstract List<BeanDefinition> getBeanDefinitionsFromFile(ServletContext servletContext);


}
