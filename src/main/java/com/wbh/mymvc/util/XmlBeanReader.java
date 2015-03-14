package com.wbh.mymvc.util;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.wbh.mymvc.bean.BeanDefinition;

/**
 * xml bean文件 工具类
 * @author wbh
 *
 */
public class XmlBeanReader extends DefaultHandler {

	private List<BeanDefinition> beanDefinitions;

	private BeanDefinition beanDefinition;

	private String preTag;

	@Override
	public void startDocument() throws SAXException {
		beanDefinitions = new ArrayList<BeanDefinition>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if ("bean".equals(qName)) {
			Class<?> c = null;
			preTag = qName; // 记录标识，用来区分相同节点的不同父节点
			beanDefinition = new BeanDefinition();
			beanDefinition.setBeanName(attributes.getValue("name"));
			try {
				c = Class.forName(attributes.getValue("class"));
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			beanDefinition.setBeanClass(c);
			if (null != attributes.getValue("isLazyInit")
					&& !("").equals(attributes.getValue("isLazyInit"))) {
				beanDefinition.setLazyInit(Boolean.parseBoolean(attributes
						.getValue("isLazyInit")));
			} else {
				beanDefinition.setLazyInit(true);
			}
			if (null != attributes.getValue("isNeedCache")
					&& !("").equals(attributes.getValue("isNeedCache"))) {
				beanDefinition.setNeedCache(Boolean.parseBoolean(attributes
						.getValue("isNeedCache")));
			} else {
				beanDefinition.setNeedCache(true);
			}
			if (null != attributes.getValue("initMethod")
					&& !("").equals(attributes.getValue("initMethod"))) {
				try {
					beanDefinition.setInitMethod(c.getMethod(attributes
							.getValue("initMethod")));
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
			if (null != attributes.getValue("destroyMethod")
					&& !("").equals(attributes.getValue("destroyMethod"))) {
				try {
					beanDefinition.setDestroyMethod(c.getMethod(attributes
							.getValue("destroyMethod")));
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		if ("property".equals(qName)) {
			if (null != attributes.getValue("ref")
					&& ("true").equals(attributes.getValue("ref"))) {
				beanDefinition.getInjections().add(
						new BeanDefinition(attributes.getValue("name")));
			}
			if (null != attributes.getValue("value")
					&& !("").equals(attributes.getValue("ref"))) {
				beanDefinition.getConfigurables().put(
						attributes.getValue("name"),
						attributes.getValue("value"));
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if ("bean".equals(qName)) {
			beanDefinitions.add(beanDefinition);
			beanDefinition = null;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}

	public List<BeanDefinition> getBeanDefinitions() {
		return beanDefinitions;
	}

	public void setBeanDefinitions(List<BeanDefinition> beanDefinitions) {
		this.beanDefinitions = beanDefinitions;
	}

	public BeanDefinition getBeanDefinition() {
		return beanDefinition;
	}

	public void setBeanDefinition(BeanDefinition beanDefinition) {
		this.beanDefinition = beanDefinition;
	}

	public String getPreTag() {
		return preTag;
	}

	public void setPreTag(String preTag) {
		this.preTag = preTag;
	}

}
