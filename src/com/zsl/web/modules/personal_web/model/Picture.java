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
/***
 * 照片
 * @author Administrator
 *
 */
@Entity
@Table(name="picture")
public class Picture implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="say_id")
	private Long sayId;/**说说id*/
	
	@Column(name="blog_id")
	private Long blogId;/**日志id*/
	
	@Column(name="photo_id")
	private Long photoId;/**相册id*/
	
	@Column(name="picture_url")
	private String pictureUrl;/**图片保存路径*/
	
	@Column(name="picture_name")
	private String pictureName;/**图片名称*/
	
	@Column(name="create_time")
	private Date createTime;/**创建时间*/
	
	@Column(name="modify_time")
	private Date modifyTime;/**修改时间*/
	
	@Column(name="del_flag")
	private Integer delFlag;/**删除标志   0:未删除  1:删除*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "photo_id",insertable=false,updatable=false)
	private Photo photo;/**相册*/
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "say_id",insertable=false,updatable=false)
	private Say say;/**说说*/
	
	
	@OneToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "blog_id",insertable=false,updatable=false)
	private Blog blog;/**日子*/


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getSayId() {
		return sayId;
	}


	public void setSayId(Long sayId) {
		this.sayId = sayId;
	}


	public Long getBlogId() {
		return blogId;
	}


	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}


	public Long getPhotoId() {
		return photoId;
	}


	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}


	public String getPictureUrl() {
		return pictureUrl;
	}


	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}


	public String getPictureName() {
		return pictureName;
	}


	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
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


	public Photo getPhoto() {
		return photo;
	}


	public void setPhoto(Photo photo) {
		this.photo = photo;
	}


	public Say getSay() {
		return say;
	}


	public void setSay(Say say) {
		this.say = say;
	}


	public Blog getBlog() {
		return blog;
	}


	public void setBlog(Blog blog) {
		this.blog = blog;
	}
	
	

}
