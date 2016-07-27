package com.zsl.web.modules.personal_web.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 日志
 * @author Administrator
 *
 */
@Entity
@Table(name="blog")
public class Blog implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="user_id")
	private Long userId;/**用户id*/
	
	@Column(name="picture_id")
	private Long pictureId;/**照片id*/
	
	@Column(name="title")
	private String title;/**日志标题*/
	
	@Column(name="content")
	private String content;/**日志内容*/
	
	@Column(name="create_time")
	private Date createTime;/**创建时间*/
	
	@Column(name="modify_time")
	private Date modifyTime;/**修改时间*/
	
	@Column(name="del_flag")
	private Integer delFlag;/**删除标志*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "user_id",insertable=false,updatable=false)
	private User user;/**用户对象*/
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "picture_id",insertable=false,updatable=false)
	private Picture picture;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Long getPictureId() {
		return pictureId;
	}


	public void setPictureId(Long pictureId) {
		this.pictureId = pictureId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getModifyTime() {
		return modifyTime;
	}


	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}


	public Integer getDelFlag() {
		return delFlag;
	}


	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Picture getPicture() {
		return picture;
	}


	public void setPicture(Picture picture) {
		this.picture = picture;
	}
	
	

}
