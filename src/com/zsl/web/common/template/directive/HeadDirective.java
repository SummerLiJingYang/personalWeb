package com.zsl.web.common.template.directive;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zsl.web.util.FreemarkerUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 网站头部
 */
@Component("headDirective")
public class HeadDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "hover";
	/**页面title*/
	private static final String VARIABLE_NAME1 = "title";
	/**页面keywords*/
	private static final String VARIABLE_NAME2 = "keywords";
	/**页面description*/
	private static final String VARIABLE_NAME3 = "description";
	/**页面seotext*/
	private static final String VARIABLE_NAME4 = "seotext";


	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(VARIABLE_NAME, FreemarkerUtils.getParameter(VARIABLE_NAME, String.class, params));
		variables.put(VARIABLE_NAME1, FreemarkerUtils.getParameter(VARIABLE_NAME1, String.class, params));
		variables.put(VARIABLE_NAME2, FreemarkerUtils.getParameter(VARIABLE_NAME2, String.class, params));
		variables.put(VARIABLE_NAME3, FreemarkerUtils.getParameter(VARIABLE_NAME3, String.class, params));
		variables.put(VARIABLE_NAME4, FreemarkerUtils.getParameter(VARIABLE_NAME4, String.class, params));
		setLocalVariables(variables, env, body);
	}

}