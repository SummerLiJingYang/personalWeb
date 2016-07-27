package com.zsl.web.common.template.method;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.zsl.web.util.SpringUtils;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 模板方法 - 多语言（国际化做准备）
 * 1.前端调方法
 * 		${message("admin.error.message")} 国际化文件里面配置的name（键）,详情见WEB-INF/language下的属性文件
 * @author lijingyang
 * @version 1.0
 */
@SuppressWarnings("deprecation")
@Component("dateConvertMethod")
public class DateConvertMethod implements TemplateMethodModel {
	//date_convert("createTime","yyyy-MM-dd HH:mm:ss")
	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null && StringUtils.isNotEmpty(arguments.get(0).toString())
				&& arguments.get(1) != null && StringUtils.isNotEmpty(arguments.get(1).toString())) {
			String stime = arguments.get(0).toString();
			Long time = Long.parseLong(stime);
			if(stime.length()==10){
				time = time*1000;
			}
			String pattern = (String)arguments.get(1);
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(time);
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date date =  calendar.getTime();
			return new SimpleScalar(sdf.format(date));
		}
		return null;
	}
	
	

}