package com.zsl.web.common.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

import com.zsl.web.util.SpringUtils;

/**
 * 过滤掉getParameter方法中的特殊字符
 * 
 * @author lijingyang
 *
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private static Logger log  = LoggerFactory.getLogger(XssHttpServletRequestWrapper.class);
	public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			String value =  values[i];
			if(log.isDebugEnabled()){
				log.debug("过滤xss之前,"+"parameter="+value);
			}
			encodedValues[i] = cleanXSS(value);
			if(log.isDebugEnabled()){
				log.debug("过滤xss之后,"+"parameter="+value);
			}
		}
		return encodedValues;
	}

	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}
		return cleanXSS(value);
	}

	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value == null)
			return null;
		return cleanXSS(value);
	}

	private String cleanXSS(String value) {
		// You'll need to remove the spaces from the html entities below
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
				"\"\"");
		value = value.replaceAll("script", "");
		value = HtmlUtils.htmlEscape(value);
		return value;
	}
	
	public static void main(String[] args) {
		String value="<a》88888<script>alert(42873)</script>";
		//&lt;a》88888&lt;&gt;alert(42873)&lt;/&gt;
		//&amp; lt;a》88888&amp; lt;&amp; gt;alert(42873)&amp; lt;/&amp; gt;
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
				"\"\"");
		value = value.replaceAll("script", "");
		value = HtmlUtils.htmlEscape(value);
		System.out.println(value);
	}
}
