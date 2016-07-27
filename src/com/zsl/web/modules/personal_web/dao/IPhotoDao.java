package com.zsl.web.modules.personal_web.dao;

import com.zsl.web.common.dao.IBaseDao;
import com.zsl.web.common.dao.IOperations;
import com.zsl.web.modules.personal_web.model.Photo;
import com.zsl.web.util.Page;

/**
 * 相册dao
 * @author Administrator
 *
 */
public interface IPhotoDao extends IBaseDao<Photo>, IOperations<Photo>{
	
	/**
	 * 相册列表
	 * @param page
	 * @param userId
	 * @return
	 */
	Page<Photo> findPage(Page<Photo> page, Long userId);

}
