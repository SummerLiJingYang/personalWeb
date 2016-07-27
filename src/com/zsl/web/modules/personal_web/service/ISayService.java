package com.zsl.web.modules.personal_web.service;

import com.zsl.web.common.dao.IOperations;
import com.zsl.web.modules.personal_web.model.Say;
import com.zsl.web.util.Page;

/**
 * 说说service
 * @author Administrator
 *
 */
public interface ISayService extends IOperations<Say>{
	
	/**
	 * 查询所有的说说
	 * @param page
	 * @return
	 */
	Page<Say> findPage(Page<Say> page,Long userId);
	
	/**
	 * 逻辑删除说说
	 * @param sayId
	 */
	int deleteByDelFlag(Say say);
	
	/**
	 * 添加说说
	 * @param say
	 */
	void addSay(Say say);
	
	/**
	 * 修改说说
	 * @param say
	 */
	void updateSay(Say say);

}
