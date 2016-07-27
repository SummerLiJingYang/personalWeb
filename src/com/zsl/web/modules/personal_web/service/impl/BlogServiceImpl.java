package com.zsl.web.modules.personal_web.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsl.web.common.dao.IOperations;
import com.zsl.web.common.service.AbstractService;
import com.zsl.web.modules.personal_web.dao.IBlogDao;
import com.zsl.web.modules.personal_web.dao.IPictureDao;
import com.zsl.web.modules.personal_web.model.Blog;
import com.zsl.web.modules.personal_web.model.Picture;
import com.zsl.web.modules.personal_web.model.Say;
import com.zsl.web.modules.personal_web.service.IBlogService;
import com.zsl.web.util.Page;
import com.zsl.web.util.Parameter;
/**
 * 日志Service实现
 * @author Administrator
 *
 */
@Service("blogService")
public class BlogServiceImpl extends AbstractService<Blog> implements IBlogService{
	
	@Autowired
	private IBlogDao blogDao;
	
	@Autowired
	private IPictureDao pictureDao;
	
	@Override
	protected IOperations<Blog> getDao() {
		return blogDao;
	}

	@Override
	public Page<Blog> findPage(Page<Blog> page,Long userId) {
		return blogDao.findPage(page,userId);
	}
	
	/**
	 * 异步删除日志
	 */
	@Override
	public int deleteByDelFlag(Blog blog) {
		StringBuilder hql = new StringBuilder(200);
		Parameter para = new Parameter();
		hql.append(" UPDATE blog b set b.del_flag = 1,b.modify_time = now() WHERE b.id = :blogId ");
		para.put("blogId", blog.getId());
		return blogDao.updateBySql(hql.toString(), para);
	}
	
	/**
	 * 异步保存日志
	 */
	@Override
	public void addBlog(Blog blog) {
		Date now = Calendar.getInstance().getTime();
		
		/**保存日志的时候相应的增加相关的照片*/
		if(blog.getPicture() != null){
			Picture picture = new Picture();
			picture.setBlogId(blog.getId());
			picture.setCreateTime(now);
			picture.setModifyTime(now);
			picture.setPictureName("blog"+UUID.randomUUID());
			picture.setPictureUrl(blog.getPicture().getPictureUrl());
			picture.setDelFlag(0);
			pictureDao.create(picture);
			blog.setPictureId(picture.getId());
		}
		blog.setCreateTime(now);
		blog.setDelFlag(0);
		blog.setModifyTime(now);
		blogDao.create(blog);
	}
	
	/**
	 * 异步修改日志
	 */
	@Override
	public void updateBlog(Blog blog) {
		Blog oldBlog = blogDao.findOne(blog.getId());
		
		/**先删除之前相关的照片*/
		pictureDao.deleteBySayId(oldBlog.getId());
		
		/**保存新的照片*/
		if(blog.getPicture() != null){
			Picture picture = new Picture();
			picture.setBlogId(blog.getId());
			picture.setCreateTime(new Date());
			picture.setModifyTime(new Date());
			picture.setPictureName("blog"+UUID.randomUUID());
			picture.setPictureUrl(blog.getPicture().getPictureUrl());
			picture.setDelFlag(0);
			pictureDao.create(picture);
			blog.setPictureId(picture.getId());
		}
		oldBlog.setModifyTime(new Date());
		oldBlog.setContent(blog.getContent());
		oldBlog.setTitle(blog.getTitle());
		blogDao.update(oldBlog);
	}

}
