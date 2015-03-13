package com.wbh.mymvc.factory.support;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wbh.mymvc.bean.BeanDefinition;
import com.wbh.mymvc.factory.BeanFactory;

public class DefaultBeanFactory implements BeanFactory {

	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
	private final Map<String, Object> beanInstantiationMap = new ConcurrentHashMap<String, Object>();

	public void addBeanDefinition(BeanDefinition bd) {
		this.beanDefinitionMap.put(bd.getBeanName(), bd);
	}
	
	public void addBeanInstantiationMap(String beanName,Object bean){
		this.beanInstantiationMap.put(beanName, bean);
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

}
