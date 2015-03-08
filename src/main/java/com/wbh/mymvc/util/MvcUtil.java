package com.wbh.mymvc.util;

import java.io.File;
import java.util.List;

public class MvcUtil {
	
	public static List<String> getAllClassName(String classPath ,String filePath ,List<String> allClassName) { 
		File dir = new File(filePath);
		File[] fs = dir.listFiles(); //包括子目录
		
		String className = "";
        
        for (File f : fs) { 
            if (f.isDirectory()) { 
            	getAllClassName(classPath,f.getAbsolutePath(),allClassName); 
            } else { 
            	className = f.getPath().replace(classPath.substring(1).replace("/", "\\"), "").replace("\\", ".").replace(".class", "");
            	allClassName.add(className);       
            } 
        } 
        return allClassName;
    }
	
}
