package com.zsl.web.modules.personal_web.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;

/**
 * 说说
 * @author Administrator
 *
 */
@Entity
@Table(name="say")
public class Say implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="user_id")
	private Long userId;/**用户id*/
	
	@Column(name="words")
	private String words;/**说说内容*/
	
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
	
	
	@OneToMany(mappedBy = "say", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@WhereJoinTable(clause =" del_flag = 0 ")
	private List<Picture> pictureList ;


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


	public String getWords() {
		return words;
	}


	public void setWords(String words) {
		this.words = words;
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


	public List<Picture> getPictureList() {
		return pictureList;
	}


	public void setPictureList(List<Picture> pictureList) {
		this.pictureList = pictureList;
	}
	
	

}
