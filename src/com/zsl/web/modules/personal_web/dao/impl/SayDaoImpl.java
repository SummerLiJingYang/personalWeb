package com.zsl.web.modules.personal_web.dao.impl;

import org.springframework.stereotype.Repository;

import com.zsl.web.common.dao.BaseDao;
import com.zsl.web.modules.personal_web.dao.ISayDao;
import com.zsl.web.modules.personal_web.model.Say;
import com.zsl.web.util.Page;
import com.zsl.web.util.Parameter;
/**
 * 说说dao实现
 * @author Administrator
 *
 */
@Repository("sayDao")
public class SayDaoImpl extends BaseDao<Say> implements ISayDao{
	
	/**
	 * 查询所有说说
	 */
	@Override
	public Page<Say> findPage(Page<Say> page,Long userId) {
		
		StringBuilder hql = new StringBuilder(200);
		Parameter para = new Parameter();
		hql.append(" FROM Say s where s.delFlag = 0 ");
		if(userId != null){
			hql.append(" AND s.userId = :userId ");
			para.put("userId", userId);
		}
		hql.append(" order by id desc ");
		return this.find(page, hql.toString(), para);
	}

}
