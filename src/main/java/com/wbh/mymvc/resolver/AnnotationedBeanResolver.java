package com.wbh.mymvc.resolver;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.wbh.mymvc.annotation.Bean;
import com.wbh.mymvc.annotation.DestroyMethod;
import com.wbh.mymvc.annotation.InitMethod;
import com.wbh.mymvc.annotation.Injection;
import com.wbh.mymvc.bean.BeanDefinition;
import com.wbh.mymvc.factory.support.AbstractBeanFactory;
import com.wbh.mymvc.util.Assert;
import com.wbh.mymvc.util.ClassScanUtil;

public class AnnotationedBeanResolver extends AbstractConfiguredBeanResolver {

	private static final String RESOLVER_PATH_SCAN = "annotationedbeanresolver.path.scan";

	@Override
	public void fillWithBeanDefinition(AbstractBeanFactory beanFactory,
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
			AbstractBeanFactory beanFactory, String resolverPath) {
		
		String classPath = this.getClass().getClassLoader().getResource("").getPath();
		String filePath = classPath + resolverPath;
		List<Class<?>> beanClass = new ArrayList<Class<?>>();
		ClassScanUtil.getAnnotatedScanResultClass(classPath, filePath,beanClass, Bean.class);
		addBeanDefinitionList(beanFactory, beanClass);

	}
	
	private void  addBeanDefinitionList(AbstractBeanFactory beanFactory ,List<Class<?>> beanClass){

		if(null == beanClass || beanClass.isEmpty()){
			return;
		}
		
		BeanDefinition bd = null;
		
		Method[] ms = null;
		Field[] fs = null;
		
		for (Class<?> c : beanClass) {
			bd = new BeanDefinition();
			ms = c.getMethods();
			fs = c.getFields();
			bd.setBeanClass(c);
			
			/*
			 * 默认为类名的全小写，可自行配置
			 * 注意：beanName大小写无关
			 */
			if(c.getAnnotation(Bean.class).value().trim().equals("")){
				bd.setBeanName(c.getSimpleName().toLowerCase());
			}else{
				bd.setBeanName(c.getAnnotation(Bean.class).value().trim());
			}
			
			bd.setLazyInit(c.getAnnotation(Bean.class).isLazyInit());
			bd.setNeedCache(c.getAnnotation(Bean.class).isNeedCache());
			
			for (Method m : ms) {// 只能注解一个InitMethod和一个PostMethod
				if (m.isAnnotationPresent(InitMethod.class)) {
					bd.setInitMethod(m);
				}
				if (m.isAnnotationPresent(DestroyMethod.class)) {
					bd.setDestroyMethod(m);
				}
			}

			for (Field f : fs) {
				
				if (f.isAnnotationPresent(Injection.class)) {
					
					bd.getInjections().add(new BeanDefinition(f.getName().toLowerCase().trim()));
				}
			}
			
			beanFactory.addBeanDefinition(bd);
		}
		
	}

}
