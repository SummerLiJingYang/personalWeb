package com.zsl.web.common.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.transform.ResultTransformer;

import com.zsl.web.util.Page;
import com.zsl.web.util.Parameter;

public interface  IBaseDao<T> {
	
	// -------------- QL Query --------------

	/**
	 * QL 分页查询
	 * 
	 * @param page
	 * @param qlString
	 * @return
	 */
	public <E> Page<E> find(Page<E> page, String qlString);

	/**
	 * QL 分页查询
	 * 
	 * @param page
	 * @param qlString
	 * @param parameter
	 * @return
	 */
	
	public <E> Page<E> find(Page<E> page, String qlString, Parameter parameter);

	/**
	 * QL 查询
	 * 
	 * @param qlString
	 * @return
	 */
	public <E> List<E> find(String qlString);

	/**
	 * QL 查询
	 * 
	 * @param qlString
	 * @param parameter
	 * @return
	 */
	
	public <E> List<E> find(String qlString, Parameter parameter);
	
	/**
	 * QL 查询
	 * 
	 * @param qlString
	 * @param parameter
	 * @return
	 */
	
	public T findOne(String qlString, Parameter parameter);


	/**
	 * 获取实体
	 * 
	 * @param id
	 * @return
	 */
	
	public T get(Serializable id);

	/**
	 * 获取实体
	 * 
	 * @param qlString
	 * @return
	 */
	public T getByHql(String qlString);

	/**
	 * 获取实体
	 * 
	 * @param qlString
	 * @param parameter
	 * @return
	 */
	public T getByHql(String qlString, Parameter parameter) ;

	/**
	 * 创建 QL 查询对象
	 * 
	 * @param qlString
	 * @param parameter
	 * @return
	 */
	public Query createQuery(String qlString, Parameter parameter);

	// -------------- QL Query end --------------

	// -------------- SQL Query --------------

	/**
	 * SQL 分页查询
	 * 
	 * @param page
	 * @param sqlString
	 * @return
	 */
	public <E> Page<E> findBySql(Page<E> page, String sqlString);

	/**
	 * SQL 分页查询
	 * 
	 * @param page
	 * @param sqlString
	 * @param parameter
	 * @return
	 */
	public <E> Page<E> findBySql(Page<E> page, String sqlString,Parameter parameter) ;

	/**
	 * SQL 分页查询
	 * 
	 * @param page
	 * @param sqlString
	 * @param resultClass
	 * @return
	 */
	public <E> Page<E> findBySql(Page<E> page, String sqlString,Class<?> resultClass);

	/**
	 * SQL 分页查询
	 * 
	 * @param page
	 * @param sqlString
	 * @param resultClass
	 * @param parameter
	 * @return
	 */
	
	public <E> Page<E> findBySql(Page<E> page, String sqlString,
			Parameter parameter, Class<?> resultClass) ;

	/**
	 * SQL 查询
	 * 
	 * @param sqlString
	 * @return
	 */
	public <E> List<E> findBySql(String sqlString) ;
	/**
	 * SQL 查询
	 * 
	 * @param sqlString
	 * @param parameter
	 * @return
	 */
	public <E> List<E> findBySql(String sqlString, Parameter parameter);

	/**
	 * SQL 查询
	 * 
	 * @param qlString
	 * @param parameter
	 * @return
	 */
	
	public Object findBySqlOne(String sqlString, Parameter parameter,Class<?> resultClass);
	
	/**
	 * SQL 查询
	 * 
	 * @param sqlString
	 * @param resultClass
	 * @param parameter
	 * @return
	 */
	
	public <E> List<E> findBySql(String sqlString, Parameter parameter,
			Class<?> resultClass) ;

	/**
	 * SQL 更新
	 * 
	 * @param sqlString
	 * @param parameter
	 * @return
	 */
	public int updateBySql(String sqlString, Parameter parameter);


	

	// -------------- Criteria --------------

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 */
	public Page<T> find(Page<T> page) ;

	/**
	 * 使用检索标准对象分页查询
	 * 
	 * @param page
	 * @param detachedCriteria
	 * @return
	 */
	public Page<T> find(Page<T> page, DetachedCriteria detachedCriteria);

	/**
	 * 使用检索标准对象分页查询
	 * 
	 * @param page
	 * @param detachedCriteria
	 * @param resultTransformer
	 * @return
	 */
	
	public Page<T> find(Page<T> page, DetachedCriteria detachedCriteria,ResultTransformer resultTransformer) ;

	/**
	 * 使用检索标准对象查询
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	public List<T> find(DetachedCriteria detachedCriteria) ;

	/**
	 * 使用检索标准对象查询
	 * 
	 * @param detachedCriteria
	 * @param resultTransformer
	 * @return
	 */
	
	public List<T> find(DetachedCriteria detachedCriteria,
			ResultTransformer resultTransformer) ;

	/**
	 * 使用检索标准对象查询记录数
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	
	public long count(DetachedCriteria detachedCriteria);

	/**
	 * 创建与会话无关的检索标准对象
	 * 
	 * @param criterions
	 *            Restrictions.eq("name", value);
	 * @return
	 */
	public DetachedCriteria createDetachedCriteria(Criterion... criterions) ;
	
	
	
}
