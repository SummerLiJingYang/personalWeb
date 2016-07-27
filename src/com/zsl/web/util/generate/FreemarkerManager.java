package com.zsl.web.util.generate;


import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerManager {
	private static Configuration cfg = new Configuration();
	
	static{
		//定义模版的位置，从类路径中，相对于FreemarkerManager所在的路径加载模版
		cfg.setTemplateLoader(new ClassTemplateLoader(FreemarkerManager.class,FreemarkerContents.TEMPLATES_NAME));
		
		//设置对象包装器
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		
		//设置异常处理器
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		
		cfg.setDefaultEncoding("UTF-8");
		
		cfg.setNumberFormat("#");
	}
	
	public static Configuration getConfiguration(){
		return cfg;
	}
}

