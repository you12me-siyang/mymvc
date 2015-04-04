package com.wbh.mymvc.dataobj;

import java.lang.reflect.AnnotatedElement;

public interface ElementValue extends AnnotatedElement {
	
	/**
	 * 获得element的值，对FieldBody而言是field的值，对Parameter而言是实参
	 * @return
	 */
	Object getValue();
	
	/**
	 * 获得element的别名，对FieldBody而言是field的名称，对Parameter而言是是注解@Param的value的值
	 * @return
	 */
	String getElementName();

}
