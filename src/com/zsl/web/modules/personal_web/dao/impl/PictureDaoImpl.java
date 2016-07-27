package com.zsl.web.modules.personal_web.dao.impl;

import org.springframework.stereotype.Repository;

import com.zsl.web.common.dao.BaseDao;
import com.zsl.web.modules.personal_web.dao.IPictureDao;
import com.zsl.web.modules.personal_web.model.Picture;
import com.zsl.web.util.Page;
import com.zsl.web.util.Parameter;

/***
 * 照片dao实现
 * @author Administrator
 *
 */
@Repository("pictureDao")
public class PictureDaoImpl extends BaseDao<Picture> implements IPictureDao{
	
	/**
	 * 照片列表
	 */
	@Override
	public Page<Picture> findPage(Page<Picture> page, Long photoId) {
		StringBuilder hql = new StringBuilder(200);
		Parameter para = new Parameter();
		hql.append(" FROM Picture s where s.delFlag = 0 ");
		if(photoId != null){
			hql.append(" AND s.userId = :photoId ");
			para.put("photoId", photoId);
		}
		hql.append(" order by id desc ");
		return this.find(page, hql.toString(), para);
	}
	
	/**
	 * 删除照片
	 */
	@Override
	public int deleteBySayId(Long id) {
		StringBuilder sql = new StringBuilder(200);
		Parameter para = new Parameter();
		sql.append(" update picture p set p.del_flag = 1 ,p.modify_time = now() where p.say_id = :sayId");
		para.put("sayId", id);
		return this.updateBySql(sql.toString(), para);
	}

}
