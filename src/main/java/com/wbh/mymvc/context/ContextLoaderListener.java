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
import com.wbh.mymvc.util.ReflectUtil;

public class ContextLoaderListener implements ServletContextListener {

	public static final String CONTEXTCONFIGLOCATION = "contextConfigLocation";
	public static final String CLASS_CONTEXTCLASS = "class.contextclass";
	public static final String CLASS_RESOLVER = "class.resolver";

	private WebContext context;
	private Properties p;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		loadConfigFile(sc);
		initWebApplicationContext(sc);
	}

	private void loadConfigFile(ServletContext sc) {
		
		String contextConfigLocation = sc.getInitParameter(CONTEXTCONFIGLOCATION);
		InputStream inputStream = sc.getResourceAsStream(contextConfigLocation);

		p = new Properties();

		try {
			p.load(inputStream);
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
		WebContext wc = (WebContext) ReflectUtil.newinstance(contextName, new Object[]{});
		return wc;
	}

	private List<ConfiguredBeanResolver> loadBeanResolvers(
			ServletContext servletContext) {
		List<ConfiguredBeanResolver> cbrs = new ArrayList<ConfiguredBeanResolver>();
		String[] resolverName = p.getProperty(CLASS_RESOLVER).split(",");
		ConfiguredBeanResolver cbr = null;
		for(String str:resolverName){
			cbr = (ConfiguredBeanResolver)ReflectUtil.newinstance(str,new Object[]{});
			cbr.setProperties(p);
			cbrs.add(cbr);
		}
		return cbrs;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
