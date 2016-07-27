package com.zsl.web.common.security;

import java.io.Serializable;

/**
 * 身份信息
 * 
 * @author framework Team
 * @version 3.0
 */
public class Principal implements Serializable {

	private static final long serialVersionUID = 5798882004228239559L;

	/** ID */
	private Long id;

	/** 用户名 */
	private String username;
	//areaId
	/** 所属地区 */
	private Integer areaId;
	/**
	 * 用户名称
	 */
	private String name;
	/**
	 * @param id
	 *            ID
	 * @param username
	 *            用户名
	 */
	public Principal(Long id, String username,Integer areaId,String name) {
		this.id = id;
		this.username = username;
		this.areaId = areaId;
		this.name = name;
	}

	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置ID
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取用户名
	 * 
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 * 
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return username;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}