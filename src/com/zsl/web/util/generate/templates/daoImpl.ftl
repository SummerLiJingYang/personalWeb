/*
 * Powered By [web_zsl]
 * Since 2015 - 2015
 */
package ${package_path}.dao.impl;
import org.springframework.stereotype.Repository;

import com.zsl.web.common.dao.BaseDao;
import ${package_path}.dao.I${model}Dao;
import ${package_path}.model.${model};

/**
 * 项目名称：web_zsl    
 * 类名称：I${model}DaoImpl  
 * 类描述：${model}dao实现
 * 创建人：${creater}    
 * 创建时间：${.now} 
 * @version 1.0    
 */
@Repository(value="${repository}Dao")
public class ${model}DaoImpl extends BaseDao<${model}> implements I${model}Dao{
}