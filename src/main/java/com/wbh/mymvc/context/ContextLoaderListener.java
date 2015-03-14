package com.wbh.mymvc.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.wbh.mymvc.resolver.ConfiguredBeanResolver;
import com.wbh.mymvc.util.Assert;
import com.wbh.mymvc.util.ReflectUtil;

public class ContextLoaderListener implements ServletContextListener {

	public static final String CONTEXTCONFIGLOCATION = "contextConfigLocation";
	public static final String WEBCONTEXT = "webContext";
	public static final String CLASS_CONTEXTCLASS = "class.contextclass";
	public static final String CLASS_RESOLVER = "class.resolver";

	private WebContext context;
	private Properties p;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		loadConfigFile(sc);
		sc.setAttribute(WEBCONTEXT, initWebApplicationContext(sc));
	}

	private void loadConfigFile(ServletContext sc) {
		
		String contextConfigLocation = sc.getInitParameter(CONTEXTCONFIGLOCATION);
		
		Assert.isBlankOrNull(contextConfigLocation, "上下文配置文件未配置！");
		
		InputStream inputStream = sc.getResourceAsStream(contextConfigLocation);

		p = new Properties();

		try {
			p.load(inputStream);
			inputStream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

	private WebContext initWebApplicationContext(ServletContext servletContext) {
		if (this.context == null) {
			this.context = createWebApplicationContext(servletContext);
			configureAndRefreshWebContext(servletContext);
		}
		return this.context;
	}

	private void configureAndRefreshWebContext(ServletContext servletContext) {
		ConfigurableWebContext cwc = (ConfigurableWebContext) this.context;
		List<ConfiguredBeanResolver> cbrs = loadBeanResolvers(servletContext);
		cwc.setConfiguredBeanResolver(cbrs);
		cwc.setServletContext(servletContext);
		cwc.refresh();
	}

	private ConfigurableWebContext createWebApplicationContext(
			ServletContext servletContext) {

		ConfigurableWebContext context = (ConfigurableWebContext) determineContext(servletContext);
		
		return context;
	}

	private WebContext determineContext(ServletContext servletContext) {
		String contextName = p.getProperty(CLASS_CONTEXTCLASS);
		Assert.isBlankOrNull(contextName, "上下文类未指定！");
		WebContext wc = (WebContext) ReflectUtil.newinstance(contextName, new Object[]{});
		return wc;
	}

	private List<ConfiguredBeanResolver> loadBeanResolvers(
			ServletContext servletContext) {
		List<ConfiguredBeanResolver> cbrs = new ArrayList<ConfiguredBeanResolver>();
		String resolvers = p.getProperty(CLASS_RESOLVER).trim();
		if(null == resolvers || ("").equals(resolvers) ){
			return null;
		}
		String[] resolverClazz = resolvers.split(",");
		ConfiguredBeanResolver cbr = null;
		for(String str:resolverClazz){
			if(("").equals(str.trim())){
				continue;
			}
			cbr = (ConfiguredBeanResolver)ReflectUtil.newinstance(str.trim(),new Object[]{});
			cbr.setProperties(p);
			cbrs.add(cbr);
		}
		return cbrs;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
