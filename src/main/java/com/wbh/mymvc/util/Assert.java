package com.wbh.mymvc.util;

import java.util.Map;

public abstract class Assert {
	
	public static void isNull(Object o,String msg){
		if(null == o){
			throw new IllegalArgumentException(msg);
		}
	}
	public static void isBlankOrNull(String str,String msg){
		if(null == str || ("").equals(str.trim())){
			throw new IllegalArgumentException(msg);
		}
	}
	public static void isKeyExist(Object key,Map<?,?> map,String msg){
		if(map.containsKey(key)){
			throw new IllegalArgumentException(msg);
		}
	}

}
