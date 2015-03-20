package com.wbh.mymvc.context;

public interface WebContextInitializer<wc extends ConfigurableWebContext> {

	
	void initWebContext(wc x);
	
	
}
