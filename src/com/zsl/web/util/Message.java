package com.zsl.web.util;

import java.io.Serializable;


/**
 * 消息
 * 
 * @author liujunqing
 * @version 1.0
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 3703273818413218489L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 成功 */
		success,

		/** 警告 */
		warning,

		/** 错误 */
		error
	}

	/** 类型 */
	private Type type;

	/** 内容 */
	private String content;

	/**
	 * 初始化一个新创建的 Message 对象，使其表示一个空消息。
	 */
	public Message() {

	}

	/**
	 * 初始化一个新创建的 Message 对象
	 * 
	 * @param type
	 *            类型
	 * @param content
	 *            内容
	 */
	public Message(Type type, String content) {
		this.type = type;
		this.content = content;
	}

	/**
	 * @param type
	 *            类型
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 */
	public Message(Type type, String content, Object... args) {
		this.type = type;
		this.content = SpringUtils.getMessage(content, args);
	}
	
	/**
	 * 返回成功消息
	 * 
	 * @param content(无须使用国际化)
	 *            内容
	 * @param args
	 *            参数
	 * @return 成功消息
	 */
	public static Message xsuccess(String content) {
		return new Message(Type.success, content);
	}

	/**
	 * 返回警告消息(无须使用国际化)
	 * 
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 * @return 警告消息
	 */
	public static Message xwarn(String content) {
		return new Message(Type.warning, content);
	}

	/**
	 * 返回错误消息
	 * 
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 * @return 错误消息
	 */
	public static Message xerror(String content) {
		return new Message(Type.error, content);
	}

	/**
	 * 返回成功消息
	 * 
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 * @return 成功消息
	 */
	public static Message success(String content, Object... args) {
		return new Message(Type.success, content, args);
	}

	/**
	 * 返回警告消息
	 * 
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 * @return 警告消息
	 */
	public static Message warn(String content, Object... args) {
		return new Message(Type.warning, content, args);
	}

	/**
	 * 返回错误消息
	 * 
	 * @param content
	 *            内容
	 * @param args
	 *            参数
	 * @return 错误消息
	 */
	public static Message error(String content, Object... args) {
		return new Message(Type.error, content, args);
	}

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

//	@Override
//	public String toString() {
//		return SpringUtils.getMessage(content);
//	}

}