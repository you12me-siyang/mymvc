package com.wbh.mymvc.context;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.servlet.ServletContext;

import com.wbh.mymvc.bean.BeanBody;
import com.wbh.mymvc.factory.support.DefaultBeanFactory;
import com.wbh.mymvc.resolver.ConfiguredBeanResolver;

public abstract class AbstractConfigWebContext implements
		ConfigurableWebContext {

	protected ServletContext servletContext;

	protected DefaultBeanFactory beanFactory;

	protected List<ConfiguredBeanResolver> cbrs;

	protected final void refreshBeanFactory() {
		beanFactory = new DefaultBeanFactory();
		loadBeanDefinitions(beanFactory);
	}

	protected  void loadBeanDefinitions(DefaultBeanFactory beanFactory) {

		if (null != cbrs && !cbrs.isEmpty()) {
			for (ConfiguredBeanResolver cbr : cbrs) {
				cbr.fillWithBeanDefinition(beanFactory, servletContext);
			}
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
	public List<Object> getBeansByAnnotation(Class<? extends Annotation> c) {
		return beanFactory.getBeansByAnnotation(c);
	}

	@Override
	public BeanBody getBeanBody(String beanName) {
		return beanFactory.getBeanBody(beanName);
	}

	@Override
	public List<BeanBody> getBeanBodysByAnnotation(Class<? extends Annotation> c) {
		return beanFactory.getBeanBodysByAnnotation(c);
	}
	@Override
	public List<Object> getBeanByClass(Class<?> c) {
		return beanFactory.getBeanByClass(c);
	}

	@Override
	public void removeAllBeanInstantiation() {
		beanFactory.removeAllBeanInstantiation();
	}

	@Override
	public void removeBeanInstantiation(String beanName) {
		beanFactory.removeBeanInstantiation(beanName);
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
		// 加载用户配置的所有BeanDefinition
		refreshBeanFactory();
		// 预处理（实例化）需缓存非懒加载的所有Bean
		pretreatNeedCacheNoLazyBean();
//		TestBean t = (TestBean) beanFactory.getBean("testbean");
//		t.printTestconfigured();
	}

	private void pretreatNeedCacheNoLazyBean() {
		beanFactory.initBeanInstantiationMap();
	}

}
