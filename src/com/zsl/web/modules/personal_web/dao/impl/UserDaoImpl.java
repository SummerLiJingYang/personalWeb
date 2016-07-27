package com.zsl.web.modules.personal_web.dao.impl;

import org.springframework.stereotype.Repository;

import com.zsl.web.common.dao.BaseDao;
import com.zsl.web.modules.personal_web.dao.IUserDao;
import com.zsl.web.modules.personal_web.model.User;
import com.zsl.web.util.Parameter;
/**
 * 用户dao实现
 * @author Administrator
 *
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDao<User> implements IUserDao{
	
	/**
	 * 登录
	 */
	@Override
	public User findByUsernameAndPassword(String username, String password) {
		StringBuilder hql = new StringBuilder(200);
		Parameter para = new Parameter();
		hql.append(" FROM User u WHERE u.username = :username AND u.password = :password ");
		para.put("username", username);
		para.put("password", password);
		return this.findOne(hql.toString(), para);
	}

}
