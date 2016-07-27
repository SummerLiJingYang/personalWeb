package com.zsl.web.modules.personal_web.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsl.web.common.dao.IOperations;
import com.zsl.web.common.service.AbstractService;
import com.zsl.web.modules.personal_web.dao.IPictureDao;
import com.zsl.web.modules.personal_web.dao.ISayDao;
import com.zsl.web.modules.personal_web.model.Picture;
import com.zsl.web.modules.personal_web.model.Say;
import com.zsl.web.modules.personal_web.service.ISayService;
import com.zsl.web.util.Page;
import com.zsl.web.util.Parameter;
/**
 * 用户Service实现
 * @author Administrator
 *
 */
@Service("sayService")
public class SayServiceImpl extends AbstractService<Say> implements ISayService{
	
	@Autowired
	private ISayDao sayDao;
	@Autowired
	private IPictureDao pictureDao;
	
	@Override
	protected IOperations<Say> getDao() {
		return sayDao;
	}
	
	/**
	 * 查询所有说说
	 */
	@Override
	public Page<Say> findPage(Page<Say> page,Long userId) {
		
		return sayDao.findPage(page,userId);
	}
	
	/**
	 * 逻辑删除说说
	 */
	@Override
	public int deleteByDelFlag(Say say) {
		StringBuilder hql = new StringBuilder(200);
		Parameter para = new Parameter();
		hql.append(" UPDATE say s set s.del_flag = 1,s.modify_time = now() WHERE s.id = :sayId ");
		para.put("sayId", say.getId());
		return sayDao.updateBySql(hql.toString(), para);
	}
	
	/**
	 * 添加说说
	 */
	@Override
	public void addSay(Say say) {
		/**保存说说的时候保存相关联的照片对象*/
		Date now = Calendar.getInstance().getTime();
		List<Picture> pictureList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(say.getPictureList())){
			for (Picture picture : say.getPictureList()) {
				Picture newPic = new Picture();
				newPic.setSayId(say.getId());
				newPic.setPictureUrl(picture.getPictureUrl());
				newPic.setPictureName("say"+UUID.randomUUID());
				newPic.setCreateTime(now);
				newPic.setModifyTime(now);
				newPic.setDelFlag(0);
				pictureDao.create(newPic);
				pictureList.add(newPic);
			}
		}
		say.setCreateTime(now);
		say.setModifyTime(now);
		say.setDelFlag(0);
		say.setPictureList(pictureList);
		sayDao.create(say);
	}
	
	/***
	 * 修改说说
	 */
	@Override
	public void updateSay(Say say) {
		
		Say oldSay = sayDao.findOne(say.getId());
		
		/**先删除之前相关的照片*/
		pictureDao.deleteBySayId(oldSay.getId());
		
		/**保存新的照片*/
		List<Picture> pictureList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(say.getPictureList())){
			for (Picture picture : say.getPictureList()) {
				Picture newPic = new Picture();
				newPic.setSayId(oldSay.getId());
				newPic.setPictureUrl(picture.getPictureUrl());
				newPic.setPictureName("say"+UUID.randomUUID());
				newPic.setCreateTime(new Date());
				newPic.setModifyTime(new Date());
				newPic.setDelFlag(0);
				pictureDao.create(newPic);
				pictureList.add(newPic);
			}
		}
		oldSay.setWords(say.getWords());
		oldSay.setModifyTime(new Date());
		oldSay.setPictureList(pictureList);
		
		
	}

}
