/*
 * Powered By [web_zsl]
 * Since 2015 - 2015
 */
package ${package_path}.service.impl;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zsl.web.common.dao.IOperations;
import com.zsl.web.common.service.AbstractService;
import ${package_path}.dao.I${model}Dao;
import ${package_path}.model.${model};
import ${package_path}.service.I${model}Service;

/**
 * 项目名称：web_zsl    
 * 类名称：${model}ServiceImpl   
 * 类描述：${model}Service
 * 创建人：${creater}    
 * 创建时间：${.now}  
 * @version 1.0    
 */
@Service("${repository}Service")
public class ${model}ServiceImpl extends AbstractService<${model}> implements I${model}Service {
	@Resource(name="${repository}Dao")
	private I${model}Dao ${repository}Dao;

	@Override
	protected IOperations<${model}> getDao() {
		return ${repository}Dao;
	}
}