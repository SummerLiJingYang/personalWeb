package com.zsl.web.modules.personal_web.dao;

import com.zsl.web.common.dao.IBaseDao;
import com.zsl.web.common.dao.IOperations;
import com.zsl.web.modules.personal_web.model.Blog;
import com.zsl.web.util.Page;

/***
 * 日志dao
 * @author Administrator
 *
 */
public interface IBlogDao extends IBaseDao<Blog>, IOperations<Blog>{
	
	/**
	 * 查询所有日志
	 * @param page
	 * @return
	 */
	Page<Blog> findPage(Page<Blog> page,Long userId);

}
