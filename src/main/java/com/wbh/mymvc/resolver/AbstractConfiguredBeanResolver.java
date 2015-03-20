package com.wbh.mymvc.resolver;

import java.util.Properties;


public abstract class AbstractConfiguredBeanResolver implements
		ConfiguredBeanResolver {

	protected Properties propertie;
	
	

	@Override
	public void setProperties(Properties properties) {
		this.propertie = properties;
	}
	
}
