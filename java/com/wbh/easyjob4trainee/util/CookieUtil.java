package com.wbh.easyjob4trainee.util;

public class CookieUtil {
	
	public static final int DEFAULT_AGE = 60 * 60 * 24 * 7;//默认cookie保存一周
	public static final String DEFAULT_PATH = "/";//默认保存在根目录下
	
	private int maxAge = DEFAULT_AGE;
	
	private String path = DEFAULT_PATH;

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
