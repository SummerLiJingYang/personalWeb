/**
 * 通用js
 */

//全局变量
var SITE_URL='http://'+window.location.host+'/';
var STATIC_URL=SITE_URL;
var IMAGE_URL=SITE_URL;
var FILE_URL=SITE_URL;

// 添加Cookie
function addCookie(name, value, options) {
	if (arguments.length > 1 && name != null) {
		if (options == null) {
			options = {};
		}
		if (value == null) {
			options.expires = -1;
		}
		if (typeof options.expires == "number") {
			var time = options.expires;
			var expires = options.expires = new Date();
			expires.setTime(expires.getTime() + time * 1000);
		}
		document.cookie = encodeURIComponent(String(name)) + "=" + encodeURIComponent(String(value)) + (options.expires ? "; expires=" + options.expires.toUTCString() : "") + (options.path ? "; path=" + options.path : "") + (options.domain ? "; domain=" + options.domain : ""), (options.secure ? "; secure" : "");
	}
}

// 获取Cookie
function getCookie(name) {
	if (name != null) {
		var value = new RegExp("(?:^|; )" + encodeURIComponent(String(name)) + "=([^;]*)").exec(document.cookie);
		return value ? decodeURIComponent(value[1]) : null;
	}
}

// 移除Cookie
function removeCookie(name, options) {
	addCookie(name, null, options);
}

(function($) {
	
	// 令牌	
	$(document).ajaxSend(function(event, request, settings) {
		if (!settings.crossDomain && settings.type != null && settings.type.toLowerCase() == "post") {
			var token = getCookie("token");
			if (token != null) {
				request.setRequestHeader("token", token);
			}
		}
	});
	
	/*$(document).ajaxComplete(function(event, request, settings) {
		var loginStatus = request.getResponseHeader("loginStatus");
		var tokenStatus = request.getResponseHeader("tokenStatus");
		
		if (loginStatus == "accessDenied") {
			$.redirectLogin(location.href, "服务端拒绝访问");
		} else if (tokenStatus == "accessDenied") {
			var token = getCookie("token");
			if (token != null) {
				$.extend(settings, {
					global: false,
					headers: {token: token}
				});
				$.ajax(settings);
			}
		}
	});*/
	$(document).ajaxComplete(function(event, request, settings) {
		try {
			if(typeof request === 'function'){
				var loginStatus = request.getResponseHeader("loginStatus");
				var tokenStatus = request.getResponseHeader("tokenStatus");
				
				if (loginStatus == "accessDenied") {
					$.redirectLogin(location.href, "服务端拒绝访问");
				} else if (tokenStatus == "accessDenied") {
					var token = getCookie("token");
					if (token != null) {
						$.extend(settings, {
							global: false,
							headers: {token: token}
						});
						$.ajax(settings);
					}
				}
			}
		} catch (e) {
			//console.log(e);
		}
	});

})(jQuery);

// 令牌
$().ready(function() {

	$("form").submit(function() {
		var $this = $(this);
		if ($this.attr("method") != null && $this.attr("method").toLowerCase() == "post" && $this.find("input[name='token']").size() == 0) {
			var token = getCookie("token");
			if (token != null) {
				$this.append('<input type="hidden" name="token" value="' + token + '" \/>');
			}
		}
	});
	
	$(".deleteConfirm").click(function(){
		var _href = $(this).attr("_href");
		layer.confirm('确定是否确定删除？', {
		    btn: ['确定','取消']
		}, function(){
		    layer.msg('正在提交，请稍等...', {icon: 1});
		    window.location.href=_href;
		});
	});
	
	$(".loading").click(function(){
		var _href = $(this).attr("_href");
		layer.msg('正在提交，请稍等...', {icon: 1});
		window.location.href=_href;
	});


});



if($.validator){
	//设置validate的默认值
	$.validator.setDefaults({
		 submitHandler: function(form) { 
			 layer.msg('正在提交，请稍等...', {icon: 1});
			 form.submit();
		 }
	});

	$.extend($.validator.messages, {
	    required: "必填",
		email: "E-mail格式错误",
		url: "网址格式错误",
		date: "日期格式错误",
		dateISO: "日期格式错误",
		pointcard: "信用卡格式错误",
		number: "只允许输入数字",
		digits: "只允许输入零或正整数",
		minlength: "长度不允许小于{0}",
		maxlength: $.validator.format("长度不允许大于{0}"),
		rangelength: $.validator.format("长度必须在{0}-{1}之间"),
		min: $.validator.format("不允许小于{0}"),
		max: $.validator.format("不允许大于{0}"),
		range: $.validator.format("必须在{0}-{1}之间"),
		accept:  "输入后缀错误",
		equalTo:  "两次输入不一致",
		remote: "输入错误",
		integer:"只允许输入整数",
		positive: "只允许输入正数",
		negative: "只允许输入负数",
		decimal: "数值超出了允许范围",
		pattern: "格式错误",
		extension: "文件格式错误"
	});
}

//判断是否为数字
$.isnumber=function(string){
    return string.match(/^[0-9]+$/);
};


//添加弹窗校验
$.fn.addTipSubmit=function(formId,input1,input2,uri){
    var btn=formId.find(".btn");
    btn.click(function(){
    	var name=input1.val();
    	var seq=input2.val();

        if(name==""||name==null||seq==""||seq==null){
            layer.msg("不能为空");
        }else if(!$.isnumber(seq)){
        	layer.msg("请填写正确的排序");
        	input2.focus();
        }else{
        	$.ajax({
				url : uri,
				type : 'GET',
				dataType : 'json',
				data: {
			        name:name
			    },
				success : function(data) {
					if(data==true){
						formId.submit()
					}else{
						layer.msg("名称已存在");
						input1.focus();
					}
				}
			});
        }
    });
};

//修改弹窗校验
$.fn.editTipSubmit=function(formId,id,input1,input2,uri){
    var btn=formId.find(".btn");
    btn.click(function(){
    	var name=input1.val();
    	var seq=input2.val();

        if(name==""||name==null||seq==""||seq==null){
            layer.msg("不能为空");
        }else if(!$.isnumber(seq)){
        	layer.msg("请填写正确的排序");
        	input2.focus();
        }else{
        	$.ajax({
				url : uri,
				type : 'GET',
				dataType : 'json',
				data: {
			        name:name,id:id
			    },
				success : function(data) {
					if(data==true){
						formId.submit()
					}else{
						layer.msg("名称已存在");
						input1.focus();
					}
				}
			});
        }
    });
};
