package com.zsl.web.util;

import org.apache.commons.configuration.Configuration;

import com.zsl.web.util.config.GlobalConfig;

/**
 * 公共参数
 * 
 * @author liujunqing
 * @version 1.0
 */
public final class CommonAttributes {

	/** 日期格式配比 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };
	
	/** 采购单过期天数 */
	public static final Integer PURCHASEXPIRETIMES = 7*24*3600;
	
	/**
	 * 不可实例化
	 */
	private CommonAttributes() {
		
	}
	
	public enum Area {
		HN("1","华南"),HD("2","华东"),HZ("3","华中"),HB("4","华北"),DB("5","东北"),XB("6","西北"),XN("7","西南");
		// 成员变量
		private String code;
		private String value;
		// 构造方法
		private Area(String code, String value) {
			this.code = code;
			this.value = value;
		}
		// 普通方法
		public static String getValue(String code) {
			for (Area c : Area.values()) {
				if (c.getCode().equals(code)) {
					return c.value;
				}
			}
			return null;
		}
		// get set 方法
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	public enum FloorCate {
		f1("1","IN ('PE','HDPE','LDPE','LLDPE')"),f2("2","='PP'"),f3("3","='PVC'"),all("100","NOT IN('PE','HDPE','LDPE','LLDPE','PP','PVC')");
		// 成员变量
		private String code;
		private String value;
		// 构造方法
		private FloorCate(String code, String value) {
			this.code = code;
			this.value = value;
		}
		// 普通方法
		public static String getValue(String code) {
			for (FloorCate c : FloorCate.values()) {
				if (c.getCode().equals(code)) {
					return c.value;
				}
			}
			return null;
		}
		// get set 方法
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	public static String fore_domain="";//前端服务器的域名
	static{
		Configuration cfg=GlobalConfig.getConfig("common_config");
		fore_domain = cfg.getString("fore.domain");
	}
}