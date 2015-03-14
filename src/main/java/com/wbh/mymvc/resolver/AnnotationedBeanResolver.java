package com.wbh.mymvc.resolver;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.wbh.mymvc.annotation.Bean;
import com.wbh.mymvc.factory.support.DefaultBeanFactory;
import com.wbh.mymvc.util.Assert;
import com.wbh.mymvc.util.ClassScanUtil;

public class AnnotationedBeanResolver extends AbstractConfiguredBeanResolver {

	private static final String RESOLVER_PATH_SCAN = "resolver.path.scan";

	@Override
	public void fillWithBeanDefinition(DefaultBeanFactory beanFactory,
			ServletContext servletContext) {

		String resolverPathValue = propertie.getProperty(RESOLVER_PATH_SCAN)
				.trim();
		Assert.isBlankOrNull(resolverPathValue, "AnnotationedBean解析器没有配置扫描路径！");

		String[] resolverPaths = resolverPathValue.split(",");

		for (String str : resolverPaths) {
			if (("").equals(str.trim())) {
				continue;
			} else {
				fillWithMatchingPathBeanDefinition(beanFactory, str.trim());
			}
		}
	}

	private void fillWithMatchingPathBeanDefinition(
			DefaultBeanFactory beanFactory, String resolverPath) {
		
		String classPath = this.getClass().getClassLoader().getResource("").getPath();
		String filePath = classPath + resolverPath;
		List<Class<?>> beanClass = new ArrayList<Class<?>>();
		ClassScanUtil.getAnnotatedScanResultClass(classPath, filePath,beanClass, Bean.class);
		addBeanDefinitionList(beanFactory, beanClass);

	}

}
