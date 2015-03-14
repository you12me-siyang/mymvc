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
import com.wbh.mymvc.util.Assert;

public class DefaultBeanFactory implements WebContextBeanFactory {

	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
	private final Map<String, Object> beanInstantiationMap = new ConcurrentHashMap<String, Object>();

	public void addBeanDefinition(BeanDefinition bd) {
		Assert.isKeyExist(bd.getBeanName(), beanDefinitionMap,
				"已存在同名BeanDefinition，可以尝试重新自定义BeanName！");
		this.beanDefinitionMap.put(bd.getBeanName(), bd);
		System.out.println("load BeanDefinition:" + bd.getBeanName());
	}

	public void addBeanInstantiationMap(String beanName, Object bean) {
		Assert.isKeyExist(beanName, beanInstantiationMap,
				"已存在同名Bean实例，可以尝试重新自定义BeanName！");
		this.beanInstantiationMap.put(beanName, bean);
	}

	@Override
	public void removeBeanInstantiation(String beanName) {
		if (beanInstantiationMap.containsKey(beanName)) {
			beanInstantiationMap.remove(beanName);
		}
	}

	@Override
	public void removeAllBeanInstantiation() {
		beanInstantiationMap.clear();
	}

	@Override
	public List<Object> getBeansByAnnotation(Class<? extends Annotation> c) {
		List<Object> os = new ArrayList<Object>();
		for (BeanDefinition bd : beanDefinitionMap.values()) {
			if (bd.getBeanClass().isAnnotationPresent(c)) {
				os.add(getBean(bd.getBeanName()));
			}
		}
		return os;
	}

	@Override
	public List<BeanBody> getBeanBodysByAnnotation(Class<? extends Annotation> c) {
		List<BeanBody> bds = new ArrayList<BeanBody>();
		for (BeanDefinition bd : beanDefinitionMap.values()) {
			if (bd.getBeanClass().isAnnotationPresent(c)) {
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

	public void initBeanInstantiationMap() {
		for (BeanDefinition bd : beanDefinitionMap.values()) {
			if ((!bd.isLazyInit()) && bd.isNeedCache()) {
				getBean(bd.getBeanName());
			}
		}
	}

	private Object doBeanInstance(String beanName) {

		BeanDefinition bd = beanDefinitionMap.get(beanName);
		Class<?> c = bd.getBeanClass();
		Method initM = bd.getInitMethod();
		Object o = null;
		Field[] fs = c.getDeclaredFields();// 包括私有变量

		try {
			o = c.newInstance(); // 无参构造函数

			// 调用自定义初始化方法
			if (null != initM) {
				initM.invoke(o, new Object[] {}); // initMethod 无参
			}
			// 注入依赖
			if (null != bd.getInjections() && (!bd.getInjections().isEmpty())) {
				for (BeanDefinition b : bd.getInjections()) {
					for (Field f : fs) {
						if (b.getBeanName().equals(f.getName())) {
							f.set(o, getBean(b.getBeanName())); // 递归
						}
					}
				}
			}
			// 注入配置文件中直接配置的变量
			if (null != bd.getConfigurables()
					&& !bd.getConfigurables().isEmpty()) {
				for (String str : bd.getConfigurables().keySet()) {
					for (Field f : fs) {
						f.setAccessible(true);// 无视访问权限
						//支持常用基本类型的注入，以后将支持集合
						if (str.equals(f.getName())) {
							switch (f.getGenericType().toString().toLowerCase()) {
							case "int":
								f.set(o, Integer.parseInt((String) bd.getConfigurables().get(str)));
								break;
							case "string":
								f.set(o, (String) bd.getConfigurables().get(str));
								break;
							case "boolean":
								f.set(o, Boolean.parseBoolean((String) bd.getConfigurables().get(str)));
								break;
							case "float":
								f.set(o, Float.parseFloat((String) bd.getConfigurables().get(str)));
								break;
							case "long":
								f.set(o, Long.parseLong((String) bd.getConfigurables().get(str)));
								break;
							default:;
							}
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

		if (bd.isNeedCache()) {
			addBeanInstantiationMap(bd.getBeanName(), o);
		}
		return o;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {

	}

	@Override
	public BeanBody getBeanBody(String beanName) {
		if (beanDefinitionMap.containsKey(beanName)) {
			return new BeanBody(beanDefinitionMap.get(beanName).getBeanClass(),
					getBean(beanName));
		}
		return null;
	}

}
