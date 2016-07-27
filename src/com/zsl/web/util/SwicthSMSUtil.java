package com.zsl.web.util;

import java.util.ResourceBundle;
/**
 * 为了方便测试 短信验证码弄一个开关出来 on 打开 off 关闭
 * @author gq
 *
 */
public class SwicthSMSUtil {
	public static String getOnOFF(){
		ResourceBundle.clearCache();
		ResourceBundle rb=ResourceBundle.getBundle("switchSMS");
		return rb.getString("sms");
	}
	
	public static String getZslJpushStatus(){
		ResourceBundle.clearCache();
		ResourceBundle rb=ResourceBundle.getBundle("switchSMS");
		return rb.getString("zsljpush");
	} 
}
