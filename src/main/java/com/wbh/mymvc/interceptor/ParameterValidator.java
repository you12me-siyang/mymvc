package com.wbh.mymvc.interceptor;

import java.util.List;

import com.wbh.mymvc.dataobj.ElementValue;

public interface ParameterValidator {
	
	boolean doValidate(List<Object> vs, ElementValue ev);

}
