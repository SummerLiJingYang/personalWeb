package com.zsl.web.modules.personal_web.dao;

import com.zsl.web.common.dao.IBaseDao;
import com.zsl.web.common.dao.IOperations;
import com.zsl.web.modules.personal_web.model.Say;
import com.zsl.web.util.Page;
/**
 * 说说dao
 * @author Administrator
 *
 */
public interface ISayDao extends IBaseDao<Say>, IOperations<Say>{
	
	/**
	 * 查询所有说说
	 * @param page
	 * @return
	 */
	Page<Say> findPage(Page<Say> page,Long userId);

}
