<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@page import="org.apache.shiro.SecurityUtils" %>
<%@page import="org.apache.shiro.subject.Subject" %>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.zsl.web.util.SpringUtils"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String base = request.getContextPath();
ApplicationContext applicationContext = SpringUtils.getApplicationContext();
if (applicationContext != null) {
	
%>
<shiro:authenticated>
<%
response.sendRedirect(base + "/admin/common/main");
%>
</shiro:authenticated>
<%
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<%
if (applicationContext != null) {
	String message = null;
	String loginFailure = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	if (loginFailure != null) {
		if (loginFailure.equals("org.apache.shiro.authc.pam.UnsupportedTokenException") || loginFailure.equals("com.zsl.web.common.security.CaptchaException")) {
			message = "admin.captcha.invalid";
		} else if (loginFailure.equals("org.apache.shiro.authc.UnknownAccountException")) {
			message = "admin.login.unknownAccount";
		} else if (loginFailure.equals("org.apache.shiro.authc.AuthenticationException")) {
			message = "admin.login.authentication";
		}
	}
%>
<title><%=SpringUtils.getMessage("admin.login.title")%></title>
<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta name="author" content="zhaosuliao Team" />
<meta name="copyright" content="zhaosuliao" />
<link rel="icon" href="<%=base%>/favicon.ico" type="image/x-icon" /> 
<link rel="shortcut icon" href="<%=base%>/favicon.ico" type="image/x-icon" />  
<script type="text/javascript" src="<%=base%>/resources/admin/plugin/jquery-1.9.1.min.js"></script>
<link href="<%=base%>/resources/admin/css/css.css" rel="stylesheet" type="text/css"/>
<link href="<%=base%>/resources/admin/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="<%=base%>/resources/admin/js/common.js"></script>
<script type="text/javascript" src="<%=base%>/resources/admin/plugin/layer/layer.js"></script>
<script type="text/javascript">
	$().ready( function() {
		
		var $loginForm = $("#loginForm");
		var $username = $("#username");
		var $password = $("#password");
		var $captcha = $("#captcha");
		var $captchaImage = $("#captchaImage");
		var $isRememberUsername = $("#isRememberUsername");
		
		// 记住用户名
		if(getCookie("adminUsername") != null) {
			$isRememberUsername.prop("checked", true);
			$username.val(getCookie("adminUsername"));
			$password.focus();
		} else {
			$isRememberUsername.prop("checked", false);
			$username.focus();
		}
		
		// 更换验证码
		$captchaImage.click( function() {
			$captchaImage.attr("src", "<%=base%>/yzm/captcha-image?timestamp=" + (new Date()).valueOf());
		});
		
		// 表单验证、记住用户名
		$loginForm.submit( function() {
			if ($username.val() == "") {
				layer.msg("请输入用户名", {icon: 0});
				return false;
			}
			if ($password.val() == "") {
				layer.msg("输入密码", {icon: 0});
				return false;
			}
			if ($captcha.val() == "") {
				layer.msg("填写验证码", {icon: 0});
				//$.jBox.tip("填写验证码","error");
				return false;
			}
			
			if ($isRememberUsername.prop("checked")) {
				addCookie("adminUsername", $username.val(), {expires: 7 * 24 * 60 * 60});
			} else {
				removeCookie("adminUsername");
			}
			
			
		});
		<%if (message != null) {%>
			layer.msg("<%=SpringUtils.getMessage(message)%>", {icon: 0});
		<%}%>
	});
</script>
<%} %>
</head>
<body style="background: #2e3740;">
	<div id="login">
    <form action=" <%=base %>/admin/login.jsp" id="loginForm" method="post" class="form-horizontal">
    	<div class="form-group">
        	<span class="icon1"></span>
        	<input type="text" class="form-control" id="username" name="username" placeholder="用户名" />
        </div>
        <div class="form-group">
        	<span class="icon2"></span>
            <input type="password" class="form-control" id="password" name="password" placeholder="密码" />
        </div>
        <div class="form-group">
        	<span class="icon3"></span>
       		
			<img id="captchaImage" class="captchaImage" width="80" height="30" src="<%=base%>/yzm/captcha-image" title="请输入验证码" alt="请输入验证码" />
            <input type="text" class="form-control yzm" id="captcha" name="captcha" placeholder="验证码" />
       		</span>
         </div>
         <!--  <p>
        	<span  class="login_text"></span>
            <span class="login_inp">记住用户名：<input type="checkbox" id="isRememberUsername" name="isRememberUsername" /></span>
        </p>--> 
        <div class="login_btnd">
        	<input type="submit" class="btn btn-default login_btn" value="登录" />
        </div> 
     </form>
    </div>
</body>
</html>