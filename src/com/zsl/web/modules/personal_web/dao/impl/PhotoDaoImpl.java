package com.zsl.web.modules.personal_web.dao.impl;

import org.springframework.stereotype.Repository;

import com.zsl.web.common.dao.BaseDao;
import com.zsl.web.modules.personal_web.dao.IPhotoDao;
import com.zsl.web.modules.personal_web.model.Photo;
import com.zsl.web.util.Page;
import com.zsl.web.util.Parameter;

/**
 * 相册dao实现
 * @author Administrator
 *
 */
@Repository("photoDao")
public class PhotoDaoImpl extends BaseDao<Photo> implements IPhotoDao{
	
	/**
	 * 相册列表
	 */
	@Override
	public Page<Photo> findPage(Page<Photo> page, Long userId) {
		StringBuilder hql = new StringBuilder(200);
		Parameter para = new Parameter();
		hql.append(" FROM Photo s where s.delFlag = 0 ");
		if(userId != null){
			hql.append(" AND s.userId = :userId ");
			para.put("userId", userId);
		}
		hql.append(" ordery by id desc ");
		return this.find(page, hql.toString(), para);
	}

}
