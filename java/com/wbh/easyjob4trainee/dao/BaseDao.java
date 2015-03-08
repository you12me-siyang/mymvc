package com.wbh.easyjob4trainee.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;

public interface BaseDao <T,PK extends Serializable> {
	
	
	public Session getSession();
	
	public T get(PK id);
	
	public PK save(T entity);
	
	public T saveAndGet(T entity);
	
	public void del(PK id);
	
	public void update(T entity);
	
	public SessionFactory getSessionFactory();
	
	public void setSessionFactory(SessionFactory sessionFactory);
	
	public List<T> getList();
	public List<T> getList(int size);
	public List<T> getList(Order strategy);
	public List<T> getList(int size, Order strategy);

}
