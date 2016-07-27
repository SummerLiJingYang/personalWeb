package com.zsl.web.modules.personal_web.dao.impl;

import org.springframework.stereotype.Repository;

import com.zsl.web.common.dao.BaseDao;
import com.zsl.web.modules.personal_web.dao.IBlogDao;
import com.zsl.web.modules.personal_web.model.Blog;
import com.zsl.web.util.Page;
import com.zsl.web.util.Parameter;

/***
 * 日志dao实现
 * @author Administrator
 *
 */
@Repository("blogDao")
public class BlogDaoImpl extends BaseDao<Blog> implements IBlogDao {
	
	/**
	 * 查询所有日志
	 */
	@Override
	public Page<Blog> findPage(Page<Blog> page,Long userId) {
		StringBuilder hql = new StringBuilder(200);
		Parameter para = new Parameter();
		hql.append(" FROM Blog s where s.delFlag = 0 ");
		if(userId != null){
			hql.append(" AND s.userId = :userId ");
			para.put("userId", userId);
		}
		hql.append(" order by id desc ");
		return this.find(page, hql.toString(), para);
	}


}
