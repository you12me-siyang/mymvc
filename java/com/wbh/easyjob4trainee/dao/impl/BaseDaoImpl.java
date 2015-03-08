package com.wbh.easyjob4trainee.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.wbh.easyjob4trainee.dao.BaseDao;

public class BaseDaoImpl <T,PK extends Serializable> implements BaseDao<T, PK> {
	
	private Class<T> entityClass;
	@Resource
	protected SessionFactory sessionFactory;
	@Resource
	protected StringRedisTemplate stringRedisTemplate;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl(){
		this.entityClass = null;
        Class<?> c = getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.entityClass = (Class<T>) p[0];
            System.out.println(this.entityClass.getName());
        }
	}  
	
	public Session getSession(){
		return this.getSessionFactory().getCurrentSession();
	}
	@SuppressWarnings("unchecked")
	public T get(PK id){
		return (T) getSession().get(entityClass, id);
	}
	
	@SuppressWarnings("unchecked")
	public PK save(T entity){
		return (PK) getSession().save(entity);
	}
	
	public T saveAndGet(T entity){
		return this.get(save(entity));
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@SuppressWarnings("unchecked")
	public List<T> getList(){
		Criteria c = getSession().createCriteria(this.entityClass);
		List<T> ts = c.list(); 
		return ts.isEmpty()?null:ts;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getList(int size){
		Criteria c = getSession().createCriteria(this.entityClass).setMaxResults(size);
		List<T> ts = c.list(); 
		return ts.isEmpty()?null:ts;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getList(Order strategy){
		Criteria c = getSession().createCriteria(this.entityClass).addOrder(strategy);
		List<T> ts = c.list(); 
		return ts.isEmpty()?null:ts;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getList(int size, Order strategy){
		Criteria c = getSession().createCriteria(this.entityClass).setMaxResults(size).addOrder(strategy);
		List<T> ts = c.list(); 
		return ts.isEmpty()?null:ts;
	}

	public void del(PK id) {
		getSession().delete(get(id));
	}

	public void update(T entity) {
		getSession().update(entity);
	}
}
