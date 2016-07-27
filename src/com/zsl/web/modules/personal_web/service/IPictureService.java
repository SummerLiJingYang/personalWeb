package com.zsl.web.modules.personal_web.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.zsl.web.common.dao.IOperations;
import com.zsl.web.modules.personal_web.model.Picture;
import com.zsl.web.util.Page;

/**
 * 照片service
 * @author Administrator
 *
 */
public interface IPictureService extends IOperations<Picture>{
	
	/**
	 * 照片列表
	 * @param page
	 * @param photoId
	 * @return
	 */
	Page<Picture> findPage(Page<Picture> page, Long photoId);
	
	/**
	 * 异步删除照片
	 * @param picture
	 * @return
	 */
	int deleteByDelFlag(Picture picture);
	
	/**
	 * 修改照片(只能修改名称)
	 * @param oldPicture
	 * @param pictureName
	 */
	void updatePicture(Picture oldPicture, String pictureName);
	
	/**
	 * 异步上传照片的同时,新增一条picture对象
	 * @param request
	 * @param file
	 * @param uploadModule
	 * @param string
	 * @return
	 */
	String uploadPictureAndsavePicture(HttpServletRequest request,MultipartFile file, String uploadModule, String format,Long photoId);

}
