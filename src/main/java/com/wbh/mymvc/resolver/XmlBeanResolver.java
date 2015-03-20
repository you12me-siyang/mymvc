package com.wbh.mymvc.resolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.wbh.mymvc.bean.BeanDefinition;
import com.wbh.mymvc.factory.support.AbstractBeanFactory;
import com.wbh.mymvc.util.XmlBeanReader;

public class XmlBeanResolver extends AbstractFileConfiguredBeanResolver {

	public static final String RESOLVER_FILE_XML = "xmlbeanresolver.file";

	@Override
	public void fillWithBeanDefinition(AbstractBeanFactory beanFactory,
			ServletContext servletContext) {
		for (BeanDefinition bd : getBeanDefinitionsFromFile(servletContext)) {
			beanFactory.addBeanDefinition(bd);
		}
	}

	@Override
	public List<BeanDefinition> getBeanDefinitionsFromFile(ServletContext servletContext) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;
		XmlBeanReader handle= new XmlBeanReader();;
		try {
			parser = factory.newSAXParser();
			InputStream is =  servletContext.getResourceAsStream(propertie.getProperty(RESOLVER_FILE_XML).trim());
			parser.parse(is, handle);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return handle.getBeanDefinitions();
	}

}
