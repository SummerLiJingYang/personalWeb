package com.zsl.web.common.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.common.base.Preconditions;

@SuppressWarnings("unchecked")
public abstract class AbstractHibernateDao<T extends Serializable> implements IOperations<T> {
	
	private Class<T> clazz;
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	public AbstractHibernateDao() {
			Type type = getClass().getGenericSuperclass();
			Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
			clazz = (Class<T>) parameterizedType[0];
	}

	protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

	@Override
	public  T findOne(final long id) {
		return (T)getCurrentSession().get(clazz, id);
	}

	@Override
	public  List<T> findAll() {
		return getCurrentSession().createQuery("from " + clazz.getName()).setFlushMode(FlushMode.COMMIT).list();
	}

	@Override
	public  void create(final T entity) {
		 Preconditions.checkNotNull(entity);
	     // getCurrentSession().persist(entity);
	     getCurrentSession().saveOrUpdate(entity);
	     getCurrentSession().flush();
	}

	@Override
	public  T update(final T entity) {
		Preconditions.checkNotNull(entity);
		getCurrentSession().update(entity);
		getCurrentSession().flush();
		return entity;
		//return (T)getCurrentSession().merge(entity);
	}

	@Override
	public  void delete(final T entity) {
		 Preconditions.checkNotNull(entity);
	     getCurrentSession().delete(entity);
	     getCurrentSession().flush();
	}

	@Override
	
	public  void deleteById(final long entityId) {
		final T entity = findOne(entityId);
        Preconditions.checkState(entity != null);
        delete(entity);
        getCurrentSession().flush();
	}

	public Class<T> getClazz() {
		return this.clazz;
	}

	@Override
	public List<T> findBy(String name, String value,Integer first,Integer count) {
		Preconditions.checkState(name != null && !"".equals(name));
		Preconditions.checkState(value != null && !"".equals(value));
		Query query = getCurrentSession().createQuery("from " + clazz.getName() + " where "+name+"=:"+name).setFlushMode(FlushMode.COMMIT).setParameter(name, value);
		if(first != null){
			query.setFirstResult(first);
		}
		if(count != null){
			query.setMaxResults(count);
		}
		return query.list();
	}
	
	@Override
	public T findByOne(String name, Object value) {
		if(name == null || "".equals(name)|| value == null){
			return null;
		}
		Query query = getCurrentSession().createQuery("from " + clazz.getName() + " where "+name+"=:"+name).setFlushMode(FlushMode.COMMIT).setParameter(name, value);
		List<T> list = query.list();
		if(list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}
}
