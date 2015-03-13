package com.wbh.mymvc.context;

import java.util.List;

import javax.servlet.ServletContext;

import com.wbh.mymvc.factory.support.DefaultBeanFactory;
import com.wbh.mymvc.resolver.ConfiguredBeanResolver;

public abstract class AbstractConfigWebContext implements
		ConfigurableWebContext {

	private ServletContext servletContext;
	
	private DefaultBeanFactory beanFactory;

	private List<ConfiguredBeanResolver> cbrs;

	protected final void refreshBeanFactory() {
		beanFactory = new DefaultBeanFactory();
		loadBeanDefinitions(beanFactory);
	}

	private void loadBeanDefinitions(DefaultBeanFactory beanFactory) {

		for (ConfiguredBeanResolver cbr : cbrs) {
			cbr.fillWithBeanDefinition(beanFactory,servletContext);
		}

	}

	public DefaultBeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(DefaultBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public List<ConfiguredBeanResolver> getConfiguredBeanResolver() {
		return cbrs;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@Override
	public void setConfiguredBeanResolver(List<ConfiguredBeanResolver> cbrs) {
		
		this.cbrs = cbrs;

	}
	@Override
	public Object getBean(String beanName) {
		return beanFactory.getBean(beanName);
	}
	
	@Override
	public void refresh() {
		//加载用户配置的所有BeanDefinition
		refreshBeanFactory();
		//预处理（实例化）需缓存非懒加载的所有Bean
		pretreatNeedCacheNoLazyBean();
	}

	private void pretreatNeedCacheNoLazyBean() {
		beanFactory.initBeanInstantiationMap();
	}

}
