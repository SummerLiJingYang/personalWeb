package com.zsl.web.modules.personal_web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsl.web.common.dao.IOperations;
import com.zsl.web.common.service.AbstractService;
import com.zsl.web.modules.personal_web.dao.IUserDao;
import com.zsl.web.modules.personal_web.model.User;
import com.zsl.web.modules.personal_web.service.IUserService;
/**
 * 用户Service实现
 * @author Administrator
 *
 */
@Service("userService")
public class UserServiceImpl extends AbstractService<User> implements IUserService{
	
	@Autowired
	private IUserDao userDao;
	
	@Override
	protected IOperations<User> getDao() {
		return userDao;
	}
	
	/**
	 * 登录
	 */
	@Override
	public User findByUserNameAndPassword(String username, String password) {
		return userDao.findByUsernameAndPassword(username,password);
	}

}
