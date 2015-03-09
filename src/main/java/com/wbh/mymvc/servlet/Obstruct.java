package com.wbh.mymvc.servlet;

import javax.servlet.http.HttpServletRequest;

import com.wbh.mymvc.interceptor.BaseInterceptor;

/**
 * 对拦截器进行封装
 * @author wbh
 */
public class Obstruct {

	private BaseInterceptor interceptor;
	private String[] mappingPath;
	private String interceptorMethod;
	private Integer index;
	

	public Obstruct(BaseInterceptor interceptor, String[] mappingPath,
			String interceptorMethod, Integer index) {
		super();
		this.interceptor = interceptor;
		this.mappingPath = mappingPath;
		this.interceptorMethod = interceptorMethod;
		this.index = index;
	}

	
	public boolean pathMatch(HttpServletRequest req){
		
		String appPath = req.getContextPath();
		String url = req.getRequestURI().replaceFirst(appPath+"/", "").trim();
		String[] urlPiece = url.split("/");
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
	
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	

	public BaseInterceptor getInterceptor() {
		return interceptor;
	}


	public void setInterceptor(BaseInterceptor interceptor) {
		this.interceptor = interceptor;
	}


	public String[] getMappingPath() {
		return mappingPath;
	}

	public void setMappingPath(String[] mappingPath) {
		this.mappingPath = mappingPath;
	}

	public String getInterceptorMethod() {
		return interceptorMethod;
	}

	public void setInterceptorMethod(String interceptorMethod) {
		this.interceptorMethod = interceptorMethod;
	}
	
	
	
	
}
