package com.zsl.web.common.security;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 登录令牌
 * 
 * @author framework Team
 * @version 3.0
 */
public class AuthenticationUsernameToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 5898441540965086534L;


	/** 验证码 */
	private String captcha;

	/**
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param captchaId
	 *            验证码ID
	 * @param captcha
	 *            验证码
	 * @param rememberMe
	 *            记住我
	 * @param host
	 *            IP
	 */
	public AuthenticationUsernameToken(String username, String password,  String captcha, boolean rememberMe, String host) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}


	/**
	 * 获取验证码
	 * 
	 * @return 验证码
	 */
	public String getCaptcha() {
		return captcha;
	}

	/**
	 * 设置验证码
	 * 
	 * @param captcha
	 *            验证码
	 */
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

}