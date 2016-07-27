package com.zsl.web.modules.personal_web.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zsl.web.common.dao.IOperations;
import com.zsl.web.common.service.AbstractService;
import com.zsl.web.modules.personal_web.dao.IPictureDao;
import com.zsl.web.modules.personal_web.model.Picture;
import com.zsl.web.modules.personal_web.service.IPictureService;
import com.zsl.web.util.Page;
import com.zsl.web.util.Parameter;
import com.zsl.web.util.UploadUtils;
/**
 * 照片Service实现
 * @author Administrator
 *
 */
@Service("pictureService")
public class PictureServiceImpl extends AbstractService<Picture> implements IPictureService{
	
	@Autowired
	private IPictureDao pictureDao;
	
	@Override
	protected IOperations<Picture> getDao() {
		return pictureDao;
	}

	@Override
	public Page<Picture> findPage(Page<Picture> page, Long photoId) {
		if(photoId == null) return null;
		return pictureDao.findPage(page,photoId);
	}

	@Override
	public int deleteByDelFlag(Picture picture) {
		StringBuilder hql = new StringBuilder(200);
		Parameter para = new Parameter();
		hql.append(" UPDATE picture p set p.del_flag = 1,p.modify_time = now() WHERE p.id = :pictureId ");
		para.put("pictureId", picture.getId());
		return pictureDao.updateBySql(hql.toString(), para);
	}

	@Override
	public void updatePicture(Picture oldPicture, String pictureName) {
		if(StringUtils.isNotBlank(pictureName)){
			oldPicture.setPictureName(pictureName);
		}
		oldPicture.setModifyTime(new Date());
		pictureDao.update(oldPicture);
	}

	@Override
	public String uploadPictureAndsavePicture(HttpServletRequest request,MultipartFile file, String uploadModule, String format,Long photoId) {
		String filePath = UploadUtils.saveMartipartFile(request, file,uploadModule,format);
		Picture picture = new Picture();
		Date now = Calendar.getInstance().getTime();
		picture.setPictureName("photo"+UUID.randomUUID());
		picture.setCreateTime(now);
		picture.setModifyTime(now);
		picture.setDelFlag(0);
		picture.setPhotoId(photoId);
		picture.setPictureUrl(filePath);
		pictureDao.create(picture);
		
		return filePath;
	}

}
