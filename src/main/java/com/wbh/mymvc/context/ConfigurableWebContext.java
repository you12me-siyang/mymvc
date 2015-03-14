package com.wbh.mymvc.context;

import com.wbh.mymvc.factory.WebContextBeanFactory;

public interface ConfigurableWebContext extends ConfigurableContext,WebContextBeanFactory{

	void refresh();
	
}
