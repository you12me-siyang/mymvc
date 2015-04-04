package com.wbh.mymvc.util;

import java.util.Map;

import com.wbh.mymvc.dataobj.FieldBody;
import com.wbh.mymvc.dataobj.Parameter;

public class RequestParameterUtil {
	
	public static void parameterToEntity(Map<String,String[]> m,Parameter p){
		
		Class<?> c = p.getParameterClass();
		FieldBody f = null;
		for(String str:m.keySet()){
			try {
				f= new FieldBody(c.getDeclaredField(str),p.getParameterValue());
				f.setValue(m.get(str)[0]);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} 
		}
	}

}
