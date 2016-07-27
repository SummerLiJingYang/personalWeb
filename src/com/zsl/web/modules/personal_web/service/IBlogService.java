package com.zsl.web.modules.personal_web.service;

import com.zsl.web.common.dao.IOperations;
import com.zsl.web.modules.personal_web.model.Blog;
import com.zsl.web.modules.personal_web.model.Photo;
import com.zsl.web.util.Page;

/**
 * 日志service
 * @author Administrator
 *
 */
public interface IBlogService extends IOperations<Blog>{
	
	/**
	 * 查询所有日志
	 * @param page
	 * @return
	 */
	Page<Blog> findPage(Page<Blog> page,Long userId);
	
	/**
	 * 异步删除日志
	 * @param blog
	 * @return
	 */
	int deleteByDelFlag(Blog blog);
	
	/**
	 * 异步保存日志
	 * @param blog
	 */
	void addBlog(Blog blog);
	
	/**
	 * 异步修改日志
	 * @param blog
	 */
	void updateBlog(Blog blog);
	
	

}
