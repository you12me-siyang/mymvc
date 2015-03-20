package com.wbh.mymvc.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtil {
	
	public static  Object newinstance(String className,Object[] args){
		Class<?> c = null;
		Object o = null;
		try {
			c = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			o = c.getDeclaredConstructor(argsToTypes(args)).newInstance(args);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	public static Class<?>[] argsToTypes(Object[] args){
		List<Class<?>> cs = new ArrayList<>();
		for(Object o:args){
			cs.add(o.getClass());
		}
		return cs.toArray(new Class<?>[cs.size()]);
	}

}
