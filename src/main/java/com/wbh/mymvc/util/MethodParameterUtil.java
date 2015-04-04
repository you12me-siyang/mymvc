package com.wbh.mymvc.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.wbh.mymvc.dataobj.Parameter;

public class MethodParameterUtil {
	
	public static Parameter[] getMethodParameters(Method m){
		Annotation[][] as = m.getParameterAnnotations();
		Parameter[] ps = new Parameter[as.length];
		for(int i=0;i<as.length;i++){
			ps[i] = new Parameter(i,as[i],m.getParameterTypes()[i]);
		}
		
		return ps;
	}

}
