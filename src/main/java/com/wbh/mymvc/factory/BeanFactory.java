package com.wbh.mymvc.factory;

import com.wbh.mymvc.bean.BeanBody;

public interface BeanFactory {

	Object getBean(String beanName);
	
	BeanBody getBeanBody(String beanName);
	
}
