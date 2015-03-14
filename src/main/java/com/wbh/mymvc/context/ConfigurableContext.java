package com.wbh.mymvc.context;

import java.util.List;

import com.wbh.mymvc.resolver.ConfiguredBeanResolver;

/**
 * 可配置的bean上下文
 * @author wbh
 *
 */
public interface ConfigurableContext {
	
	void setConfiguredBeanResolver(List<ConfiguredBeanResolver> cbrs);

}
