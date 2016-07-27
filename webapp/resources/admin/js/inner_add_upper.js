;;(function(window, $, url, Mustache, Typeahead, undefined){
	var TEMPLATE = {
		listItem: [
	        "{{#list}}",
	            "<li data-value=\"{{mid}}\" data-mid=\"{{mid}}\" data-name=\"{{name}}\" data-mobile=\"{{mobile}}\" data-linkman=\"{{linkman}}\" {{#is_black}}class=\"disabled\"{{/is_black}}>",
	                "<a href=\"#\">{{#is_black}}<span style=\"color:red;\">黑名单</span>{{/is_black}}{{name}}&nbsp;&nbsp;&nbsp;&nbsp;{{linkman}}：{{mobile}}</a>",
	            "</li>",   
	        "{{/list}}"
    	].join("")
	};
	var Main = {
		url: url,

		_components: {},

		//初始化
		init: function(){
			var that = this;
			that
				._cacheParam()
				._initValidate()
				._initComponent()
				._getRecentContact()
				._bindEventListener();
		},

		//缓存常用变量
        _cacheParam: function(){
            var that = this;
            $.each([
            	"#btnSubmit",
            	"#midHidden",
                ".recent-contact-list" 
            ], function(i, item){
                that["_$" + $.camelCase(item.replace(/\#|\./g, ""))] = $(item);
            }); 
            return that;
        },

		//初始化组件
		_initComponent: function(){
			var that = this,
				components = that._components;

			components.typeahead = Typeahead({
				target: "#companyName",
				remote: {
					paramName: "name",
					requestType: "get",
					tpl: TEMPLATE.listItem,
					url: that.url.getMembers,
					parse: function(data){
						return {data: data || []};	
					}			
				}
			});

			return that;
		},

		//初始化表单校验
		_initValidate: function(){
			var that = this;

			//扩展表单校验
			jQuery.validator.addMethod("isMTNumber", function(value, element) {
				value = $.trim(value);
				var length = value.length;
				var mobile =  /^(((13[0-9]{1})|(14[0-9]{1})|(18[0-9]{1})|(17[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
				var telephone = /^(?:[\(|（]?\s*\d{3,4}[\)|）]?\s*[-|－]?\s*)?[1-9]\d{6,7}(?:\s*-\s*\d+)?$/;
				return this.optional(element) || mobile.test(value) || telephone.test(value);
			}, "请正确填写手机或电话号码");
            jQuery.validator.addMethod("extension", function(value, element, param) {
                param = typeof param === "string" ? param.replace(/,/g, "|") : "png|jpe?g|gif";
                return this.optional(element) || value.match(new RegExp(".(" + param + ")$", "i"));
            }, $.validator.format("请上传正确格式文件")); 	

			$("#inputForm").validate({
				rules: {
					trader:{
						required: true
					},
					name: {
						required: true
					},
					linkman: {
						required: true
					},
					mobile: {
						isMTNumber: true
					},
					file:{
						required: true,
						extension: ["xls", "xlsx"].join("|")
					}
				},
				//消息提示
				messages:{
					file: {
						required: "请选择文件",
						extension: "请上传excel文件"	
					}
				},
				submitHandler: function(form){
					if(!that._isReset()){
						that._$midHidden.val("");	
					}
					that._$btnSubmit.removeClass("btn-primary").prop("disabled", true).val("提交中...");
					form.submit();
				}
			});

			return that;
		},

		_isReset: function(){
			var flag = true,
				that = this,
				$midHidden = that._$midHidden;

			$.each([
				"mid",
				"name",
				"mobile",
				"linkman"
			], function(i, item){
				if($.trim($("[data-target=\"" + item + "\"]").val()) !== $.trim($midHidden.attr("data-" + item))){
					//若有一项不同,则表明是新增联系人
					flag = false;	
					return;
				}
			});	

			return flag;
		},

		//获取最近联系人
		_getRecentContact: function(){
			var mid,
				name,
				mobile,
				linkman,
				contacts,
				data = [],
				that = this,

				html = "<li class=\"disabled\">没有最近联系人记录</li>",
				recentContact = getCookie("lastContactMemberCookieKey") || "";

			if(recentContact){
				$.each(recentContact.split(/_@_/) || [], function(i, item){
					contacts = item.split(/\|/);
					mid = contacts[0] || "";
					name = contacts[1] || "";
					linkman = contacts[2] || "";
					mobile = contacts[3] || "";
					data.push({
						is_black: false,
						mid: mid.toLowerCase() === "null" ? "" : mid,
						name: name.toLowerCase() === "null" ? "" : name,
						mobile: mobile.toLowerCase() === "null" ? "" : mobile,
						linkman: linkman.toLowerCase() === "null" ? "" : linkman
					});
				});			
			}
			if(data && data.length){
				html = Mustache.render(TEMPLATE.listItem, {
					list: data 
				});
			}
			that._$recentContactList.html(html);
			contacts = data = null;

			return that;
		},

		//绑定事件监听器
		_bindEventListener: function(){
			var that = this,
				components = that._components,
				eventCallback = that._eventCallback;

			//DOM事件
			$(document)
				.on("click", ".recent-contact-list li", function(e){
					eventCallback.selectRecentContact.call(that, e, $(this));
				});
			//自定义事件
			components.typeahead.on("selected", function(e, value, $target, component){
				eventCallback.selectContact.call(that, e, $target);	
			});

			return that;
		},

		_autoComplete: function($target){
			var value,
				that = this,
				$midHidden = that._$midHidden;

			$.each([
				"mid",
				"name",
				"mobile",
				"linkman"
			], function(i, item){
				value = $.trim($target.attr("data-" + item));
				$midHidden.attr("data-" + item, value);
				$("[data-target=\"" + item + "\"]").val(value);	
			});	

			return that;	
		},

		//事件回调
		_eventCallback: {
			//选中最近联系人
			selectRecentContact: function(e, $target){
				if(!$target.hasClass("disabled")){
					this._autoComplete($target);
				}
				e.preventDefault();
			},

			//选择联系人
			selectContact: function(e, $target){
				this._autoComplete($target);	
			}	
		}
	};
	$(function(){
		Main.init();
	});
})(this, jQuery, REQUEST_URL, Mustache, Typeahead);