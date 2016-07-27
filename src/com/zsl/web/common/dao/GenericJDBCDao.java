package com.zsl.web.common.dao;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
/**
 * 采用jdbc进行数据库操作
 * @author JSBZJ
 *
 * @param <T>
 */
@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public abstract class GenericJDBCDao<T extends Serializable> implements IGenericDao<T> {
   
	 @Resource
	 private JdbcTemplate jdbcTemplate;
	 
	 public int delete(String sql){
	      return jdbcTemplate.update(sql);
	 }   

}
