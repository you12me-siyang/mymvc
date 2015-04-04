package com.wbh.mymvc.util;

import java.lang.reflect.Field;
import java.util.Map;

public class RequestParameterUtil {
	
	public static Object parameterToEntity(Map<String,String[]> m,Object o){
		
		Class<?> c = o.getClass();
		Field f = null;
		for(String str:m.keySet()){
			try {
				f = c.getDeclaredField(str);
				f.setAccessible(true);
				f.set(o, m.get(str)[0]);
			} catch (NoSuchFieldException e) {
				return null;
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return o;
	}

}
