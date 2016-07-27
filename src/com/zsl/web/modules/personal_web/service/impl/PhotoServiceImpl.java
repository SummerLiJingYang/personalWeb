package com.zsl.web.modules.personal_web.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsl.web.common.dao.IOperations;
import com.zsl.web.common.service.AbstractService;
import com.zsl.web.modules.personal_web.dao.IPhotoDao;
import com.zsl.web.modules.personal_web.dao.IPictureDao;
import com.zsl.web.modules.personal_web.model.Photo;
import com.zsl.web.modules.personal_web.service.IPhotoService;
import com.zsl.web.util.Page;
import com.zsl.web.util.Parameter;
/**
 * 相册Service实现
 * @author Administrator
 *
 */
@Service("photoService")
public class PhotoServiceImpl extends AbstractService<Photo> implements IPhotoService{
	
	@Autowired
	private IPhotoDao photoDao;
	@Autowired
	private IPictureDao pictureDao;
	
	@Override
	protected IOperations<Photo> getDao() {
		return photoDao;
	}
	@Override
	public Page<Photo> findPage(Page<Photo> page, Long userId) {
		return photoDao.findPage(page,userId);
	}
	
	@Override
	public int deleteByDelFlag(Photo photo) {
		
		Parameter para = new Parameter();
		para.put("photoId", photo.getId());
		
		/**逻辑删除相册相关联的照片*/
		StringBuilder sql = new StringBuilder(200);
		sql.append("UPDATE picture p set p.del_flag = 1,p.modify_time = now() WHERE p.photo_id = :photoId ");
		pictureDao.updateBySql(sql.toString(), para);
		
		/**逻辑删除*/
		StringBuilder hql = new StringBuilder(200);
		hql.append(" UPDATE photo p set p.del_flag = 1,p.modify_time = now() WHERE p.id = :photoId ");
		return photoDao.updateBySql(hql.toString(), para);
	}
	
	/**
	 * 异步保存相册
	 */
	@Override
	public void addPhoto(Photo photo) {
		photo.setCreateTime(new Date());
		photo.setDelFlag(0);
		photo.setModifyTime(new Date());
		photo.setBackgroundUrl("/");/**系统默认图片*/
		photoDao.create(photo);
	}
	/**
	 * 跟新相册
	 */
	@Override
	public void updatePhoto(Photo photo) {
		Photo oldPhoto = photoDao.findOne(photo.getId());
		oldPhoto.setModifyTime(new Date());
		oldPhoto.setName(photo.getName());
		photoDao.update(oldPhoto);
	}

}
