package com.wbh.mymvc.util;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wbh.mymvc.servlet.Obstruct;

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
	
	public static boolean isIntercepted(HttpServletRequest req, Obstruct o, String appName){
		
		String url = req.getRequestURI().replaceFirst(appName+"/", "").trim();
		String[] urlPiece = url.split("/");
		String[] mappingPath =o.getMappingPath();
		boolean flag = false;
		for(String s:mappingPath){
			if(s.contains("!:")&&(s.replace("!:", "").trim().equals(url))){
			}else{
				String[] strPiece = (s.replaceFirst("/", "")).split("/");
				if(strPiece[0].equals("**")){
					flag = true;
				}else if(urlPiece.length == strPiece.length){
					boolean f = true;
					for(int i=0;i<urlPiece.length;i++){
						if(strPiece[i].equals("*")||strPiece[i].equals(urlPiece[i])){
						}else{
							f=false;
						}
					}
					flag = f;
				}
			}
		}
		return flag;
	}
	
}
