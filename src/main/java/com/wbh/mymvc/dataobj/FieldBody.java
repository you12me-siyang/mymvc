package com.wbh.mymvc.dataobj;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class FieldBody  implements ElementValue {
	
	private Field field;
	private Object object;
	
	public FieldBody(Field field,Object object) {
		this.field = field;
		this.object = object;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public void setValue(Object value){
		field.setAccessible(true);
		try {
			field.set(object, value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getValue() {
		Object value = null;
		field.setAccessible(true);
		try {
			value = field.get(object);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return value;
	}

	@Override
	public boolean isAnnotationPresent(
			Class<? extends Annotation> annotationClass) {
		return field.isAnnotationPresent(annotationClass);
	}

	@Override
	public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return field.getAnnotation(annotationClass);
	}

	@Override
	public Annotation[] getAnnotations() {
		return field.getAnnotations();
	}

	@Override
	public Annotation[] getDeclaredAnnotations() {
		return field.getDeclaredAnnotations();
	}

	@Override
	public String getElementName() {
		return field.getName();
	}

}
