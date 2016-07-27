package com.zsl.web.modules.personal_web.service;

import com.zsl.web.common.dao.IOperations;
import com.zsl.web.modules.personal_web.model.Photo;
import com.zsl.web.util.Page;

/**
 * 相册service
 * @author Administrator
 *
 */
public interface IPhotoService extends IOperations<Photo>{
	
	/**
	 * 相册列表
	 * @param page
	 * @param userId
	 * @return
	 */
	Page<Photo> findPage(Page<Photo> page, Long userId);
	
	/**
	 * 异步删除相册
	 * @param photo
	 * @return
	 */
	int deleteByDelFlag(Photo photo);
	
	/**
	 * 异步保存相册
	 * @param photo
	 */
	void addPhoto(Photo photo);
	
	/**
	 * 修改相册
	 * @param photo
	 */
	void updatePhoto(Photo photo);

}
