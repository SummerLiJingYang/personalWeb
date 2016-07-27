package com.zsl.web.modules.personal_web.dao;

import com.zsl.web.common.dao.IBaseDao;
import com.zsl.web.common.dao.IOperations;
import com.zsl.web.modules.personal_web.model.Picture;
import com.zsl.web.util.Page;

/**
 * 照片dao
 * @author Administrator
 *
 */
public interface IPictureDao extends IBaseDao<Picture>, IOperations<Picture>{
	
	/**
	 * 照片列表
	 * @param page
	 * @param photoId
	 * @return
	 */
	Page<Picture> findPage(Page<Picture> page, Long photoId);
	
	/**
	 * 删除照片
	 * @param id
	 */
	int deleteBySayId(Long id);

}
