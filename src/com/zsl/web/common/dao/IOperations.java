package com.zsl.web.common.dao;

import java.io.Serializable;
import java.util.List;

/*
 * 通用的操作接口
 */
public interface IOperations<T extends Serializable> {
	 
		T findOne(final long id);

	    List<T> findAll();

	    void create(final T entity);

	    T update(final T entity);

	    void delete(final T entity);

	    void deleteById(final long entityId);
	    
	    List<T> findBy(String name,String value,Integer first,Integer count);
	    
	    T findByOne(String name,Object value);

}
