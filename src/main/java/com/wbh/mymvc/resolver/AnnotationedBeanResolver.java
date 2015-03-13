package com.wbh.mymvc.resolver;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.wbh.mymvc.annotation.Bean;
import com.wbh.mymvc.factory.support.DefaultBeanFactory;
import com.wbh.mymvc.util.ClassScanUtil;

public class AnnotationedBeanResolver extends AbstractConfiguredBeanResolver {

	private static final String RESOLVER_PATH_SCAN = "resolver.path.scan";

	@Override
	public void fillWithBeanDefinition(DefaultBeanFactory beanFactory,
			ServletContext servletContext) {

		String controllerPath = propertie.getProperty(RESOLVER_PATH_SCAN);
		String classPath = this.getClass().getClassLoader().getResource("")
				.getPath();

		String filePath = classPath + controllerPath;
		List<Class<?>> beanClass = new ArrayList<Class<?>>();
		ClassScanUtil.getAnnotatedScanResultClass(classPath, filePath, beanClass, Bean.class);
		addBeanDefinitionList(beanFactory, beanClass);
		
	}

}
