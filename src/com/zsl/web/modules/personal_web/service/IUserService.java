package com.zsl.web.modules.personal_web.service;

import com.zsl.web.common.dao.IOperations;
import com.zsl.web.modules.personal_web.model.User;

/**
 * 用户service
 * @author Administrator
 *
 */
public interface IUserService extends IOperations<User>{
	
	/**
	 * 登录
	 * @param username
	 * @param md5Hex
	 * @return
	 */
	User findByUserNameAndPassword(String username, String md5Hex);

}
