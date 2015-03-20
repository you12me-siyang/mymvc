package com.wbh.mymvc.resolver;

import java.util.List;

import javax.servlet.ServletContext;

import com.wbh.mymvc.bean.BeanDefinition;


public abstract class AbstractFileConfiguredBeanResolver extends
		AbstractConfiguredBeanResolver {
	
	
	protected abstract List<BeanDefinition> getBeanDefinitionsFromFile(ServletContext servletContext);


}
