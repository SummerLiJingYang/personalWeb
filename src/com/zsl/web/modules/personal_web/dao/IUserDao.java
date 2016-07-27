package com.zsl.web.modules.personal_web.dao;

import com.zsl.web.common.dao.IBaseDao;
import com.zsl.web.common.dao.IOperations;
import com.zsl.web.modules.personal_web.model.User;
/**
 * 用户dao
 * @author Administrator
 *
 */
public interface IUserDao extends IBaseDao<User>, IOperations<User>{
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	User findByUsernameAndPassword(String username, String password);

}
