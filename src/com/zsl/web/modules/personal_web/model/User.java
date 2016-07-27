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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/***
 * 用户表
 * @author Administrator
 *
 */
@Entity
@Table(name="user")
public class User implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="username")
	private String username;/**用户名*/
	
	@Column(name="password")
	private String password;/**用户密码*/
	
	@Column(name="sex")
	private Integer sex;/**用户性别*/
	
	@Column(name="hometown")
	private String hometown;/**家乡*/
	
	@Column(name="sign_words")
	private String signWords;/**用户个性签名*/
	
	@Column(name="introduce")
	private String introduce;/**用户描述*/
	
	@Column(name="create_time")
	private Date createTime;/**创建时间*/
	
	@Column(name="modify_time")
	private Date modifyTime;/**修改时间*/
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	private List<Say> sayList;/**用户说说集合*/
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	private List<Photo> photoList;/**用户相册集合*/
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	private List<Blog>  bolgList;/**用户日志集合*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getSignWords() {
		return signWords;
	}

	public void setSignWords(String signWords) {
		this.signWords = signWords;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
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

	public List<Say> getSayList() {
		return sayList;
	}

	public void setSayList(List<Say> sayList) {
		this.sayList = sayList;
	}

	public List<Photo> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<Photo> photoList) {
		this.photoList = photoList;
	}

	public List<Blog> getBolgList() {
		return bolgList;
	}

	public void setBolgList(List<Blog> bolgList) {
		this.bolgList = bolgList;
	}
	
	
	
	
	

}
