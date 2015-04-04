package com.wbh.mymvc.controller;

import com.wbh.mymvc.annotation.Bean;
import com.wbh.mymvc.annotation.validator.InLengthRange;
import com.wbh.mymvc.dataobj.ElementValue;
import com.wbh.mymvc.validator.ValidatorAdapt;

@Bean
public class InLenghRangeValidator extends ValidatorAdapt {

	@Override
	public boolean validate(ElementValue ev) {
		
		if (!ev.isAnnotationPresent(InLengthRange.class))
			return true;
		
		InLengthRange a = ev.getAnnotation(InLengthRange.class);
		int min = a.minLengh();
		int max = a.maxLengh();
		int length =((String)ev.getValue()).trim().length();
		if(length>max||length<min){
			logger.info("参数"+ev.getElementName()+"长度为："+length+"不在范围："+min+"-"+max+"之中");
			return false;
		}
		
		return true;
	}

}
