package com.wbh.mymvc.factory.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import com.wbh.mymvc.bean.BeanBody;
import com.wbh.mymvc.bean.BeanDefinition;
import com.wbh.mymvc.factory.WebContextBeanFactory;

public class DefaultBeanFactory implements WebContextBeanFactory {

	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
	private final Map<String, Object> beanInstantiationMap = new ConcurrentHashMap<String, Object>();

	public void addBeanDefinition(BeanDefinition bd) {
		this.beanDefinitionMap.put(bd.getBeanName(), bd);
	}
	
	public void addBeanInstantiationMap(String beanName,Object bean){
		this.beanInstantiationMap.put(beanName, bean);
	}
	
	@Override
	public void removeBeanInstantiation(String beanName){
		if(beanInstantiationMap.containsKey(beanName)){
			beanInstantiationMap.remove(beanName);
		}
	}
	@Override
	public void removeAllBeanInstantiation(){
		beanInstantiationMap.clear();
	}
	@Override
	public List<Object> getBeansByAnnotation(Class<? extends Annotation> c){
		List<Object> os = new ArrayList<Object>();
		for(BeanDefinition bd:beanDefinitionMap.values()){
			if(bd.getBeanClass().isAnnotationPresent(c)){
				os.add(getBean(bd.getBeanName()));
			}
		}
		return os;
	}
	
	@Override
	public List<BeanBody> getBeanBodysByAnnotation(Class<? extends Annotation> c){
		List<BeanBody> bds = new ArrayList<BeanBody>();
		for(BeanDefinition bd:beanDefinitionMap.values()){
			if(bd.getBeanClass().isAnnotationPresent(c)){
				bds.add(getBeanBody(bd.getBeanName()));
			}
		}
		return bds;
	}
	
	@Override
	public Object getBean(String beanName) {

		if (beanInstantiationMap.containsKey(beanName)) {
			return beanInstantiationMap.get(beanName);
		} else {

			if (beanDefinitionMap.containsKey(beanName)) {
				return doBeanInstance(beanName);
			} else {
				return null;
			}

		}
	}
	
	public void initBeanInstantiationMap(){
		for(BeanDefinition bd:beanDefinitionMap.values()){
			if((!bd.isLazyInit())&&bd.isNeedCache()){
				getBean(bd.getBeanName());
			}
		}
	}

	private Object doBeanInstance(String beanName) {

		BeanDefinition bd = beanDefinitionMap.get(beanName);
		Class<?> c = bd.getBeanClass();
		Method initM = bd.getInitMethod();
		Object o = null;
		Field[] fs = c.getFields();
		
		try {
			
			o = c.newInstance();     //无参构造函数
			
			if (null != initM) {
				initM.invoke(o, new Object[]{});  //initMethod  无参
			}
			if (null != bd.getInjections()&&(!bd.getInjections().isEmpty())){
				
				for(BeanDefinition b:bd.getInjections()){
					for(Field f:fs){
						
						if(b.getBeanName().equals(f.getName())){
							
							f.set(o, getBean(b.getBeanName())); //递归
						}
					}
				}
			}
			
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		if(bd.isNeedCache()){
			addBeanInstantiationMap(bd.getBeanName(), o);
		}
		return o;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BeanBody getBeanBody(String beanName) {
		if(beanDefinitionMap.containsKey(beanName)){
			return new BeanBody(beanDefinitionMap.get(beanName).getBeanClass(),getBean(beanName));
		}
		return null;
	}

}