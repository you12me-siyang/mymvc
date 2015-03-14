package com.wbh.mymvc.resolver;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import com.wbh.mymvc.annotation.Bean;
import com.wbh.mymvc.annotation.DestroyMethod;
import com.wbh.mymvc.annotation.InitMethod;
import com.wbh.mymvc.annotation.Injection;
import com.wbh.mymvc.bean.BeanDefinition;
import com.wbh.mymvc.factory.support.DefaultBeanFactory;

public abstract class AbstractConfiguredBeanResolver implements
		ConfiguredBeanResolver {

	public Properties propertie;
	
	

	@Override
	public void setProperties(Properties properties) {
		this.propertie = properties;
	}
	
	protected void  addBeanDefinitionList(DefaultBeanFactory beanFactory ,List<Class<?>> beanClass){

		BeanDefinition bd = null;
		
		Method[] ms = null;
		Field[] fs = null;
		
		for (Class<?> c : beanClass) {
			bd = new BeanDefinition();
			ms = c.getMethods();
			fs = c.getFields();
			bd.setBeanClass(c);
			
			if(c.getAnnotation(Bean.class)
					.value().trim().equals("")){
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
			System.out.println("load Bean:"+bd.getBeanName());
		}
		
	}
}
