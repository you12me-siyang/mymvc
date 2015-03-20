package com.wbh.mymvc.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.List;

public class ClassScanUtil {

	public static void getScanResultClass(String classPath, String filePath,
			List<Class<?>> allClass) {

		File dir = new File(filePath);
		File[] fs = dir.listFiles(); // 包括子目录

		String className = "";

		for (File f : fs) {
			if (f.isDirectory()) {
				getScanResultClass(classPath, f.getAbsolutePath(), allClass);
			} else {
				className = f.getPath()
						.replace(classPath.substring(1).replace("/", "\\"), "")
						.replace("\\", ".").replace(".class", "");
				try {
					allClass.add(Class.forName(className));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
public static void getScanResultInstances(String classPath ,String filePath ,List<Object> instances) {
		
		File dir = new File(filePath);
		File[] fs = dir.listFiles(); //包括子目录
		
		String className = "";
        
        for (File f : fs) { 
            if (f.isDirectory()) { 
            	getScanResultInstances(classPath,f.getAbsolutePath(),instances); 
            } else { 
            	className = f.getPath().replace(classPath.substring(1).replace("/", "\\"), "").replace("\\", ".").replace(".class", "");
            	instances.add(ReflectUtil.newinstance(className, new Object[]{}));       
            } 
        } 
    }
	
public static void getAnnotatedScanResultClass(String classPath ,String filePath ,List<Class<?>> annotatedClass,Class<? extends Annotation> annotation) {
		
		File dir = new File(filePath);
		File[] fs = dir.listFiles(); //包括子目录
		
		String className = "";
        Class<?> c = null;
        for (File f : fs) { 
        	
            if (f.isDirectory()) { 
            	getAnnotatedScanResultClass(classPath, f.getAbsolutePath(), annotatedClass, annotation);; 
            } else { 
            	className = f.getPath().replace(classPath.substring(1).replace("/", "\\"), "").replace("\\", ".").replace(".class", "");
            	try {
            		c = Class.forName(className);
            		if(c.isAnnotationPresent(annotation)){
            			
            			annotatedClass.add(c);
            		}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}       
            } 
        } 
    }
}
