//自动补全
;;var Typeahead = (function(window, $, Mustache, undefined){
	var document = window.document,
		hasOwnProperty = ({}).hasOwnProperty;

	var KEY_CODE = {
		up: 38,//向上
		down: 40//向下
	};

	var GUID = 0;
	var PREFIX = "typeahead-selector";		

	//支持键盘导航的键值
	var KEY_BORAD_NAV = {};
	KEY_BORAD_NAV[KEY_CODE.up] = 1;
	KEY_BORAD_NAV[KEY_CODE.down] = 1;

	//延时时间
	var DELAY_TIME = {
		SEARCH: 200,
		HIDE_INPUT_BOX: 200
	};

	var ACTIVE_CLASS_NAME = "active",//下拉菜单选中样式
		DISABLED_CLASS_NAME = "disabled";//菜单选项不可用
	
	//模板
	var TEMPLATE = {
		menuEmpty: '<li class="disabled"><a href="#">{{text}}</a></li>',
		hiddenFiled: '<input type="hidden" />',
		autocomplete: '<div class="auto-complete-selector dropdown"><ul class="dropdown-menu"></ul></div>',
		menuItem: '{{#list}}<li data-value="{{value}}"><a href="#">{{text}}</a></li>{{/list}}'
	};

	//注入样式
	var styles = [
		"<style>",
			".auto-complete-selector{display:inline-block;}",
			".auto-complete-selector .dropdown-menu{display:none;}",
			".auto-complete-selector input[readonly]{background-color:transparent;cursor:default;}",
			".auto-complete-selector .dropdown-menu{-webkit-border-radius:0;-moz-border-radius:none;border-radius:0;padding:0;margin:1px 0 0;max-height:300px;overflow:hidden;overflow-y:auto;}",
			".auto-complete-selector .dropdown-menu>li>a{padding:6px 14px;background-image:none;}",
			".dropdown-menu>.disabled>a, .dropdown-menu>.disabled>a:hover, .dropdown-menu>.disabled>a:focus{cursor:default;}",
		"</style>"
	].join("");
	$("head", document).append(styles);

	var Typeahead = function(options){
		return new Typeahead.prototype._init(options);	
	};
	var prototypeFn = Typeahead.prototype;
	$.extend(prototypeFn, {
		/**
		 * 默认配置项
		 * @type {Object}
		 */
		options: {
			//必填,可传selector或jQuery对象
			target: null,
			//默认组件可用
			disabled: true,
			//必填,搜索有关配置项
			remote: {
				url: "",//搜索请求地址
				requestType: "get",
				parse: null,//结果数据的预处理函数,不同的后台返回的数据格式不同,该函数可以实现数据格式及字段一致
				paramName: "keyword"//参数名称
			},
			//选填,是否需要添加hidden表单 i.e.{name:"companyId"}
			hidden: null,			
			//选填,选中搜索结果项回调函数
			selectedCallback: null
		},

		/**
		 * 销毁组件,释放内存
		 * @return {[type]} [description]
		 */
		destroy: function(){
			var that = this;

			that._clearSearchTimer()
				._clearHideInputBoxTimer()
				._abortSearchPromise();
			that._$container.off(".typeahead");//移除事件
			$(document).off("click.typeahead.others", that._clickOthersEventCallback);
			that._events.off();//移除所有自定义事件
			that._revertBack();//还原
			that._$container.remove();//移除DOM
			for(var property in that){
				//移除属性
				if(hasOwnProperty.call(that, property)){
					that[property] = null;
					delete that[property];	
				}
			}

			return that;
		},

		/**
		 * 设置组件不可用
		 * @return {[type]} [description]
		 */
		disable: function(){
			var that = this;

			that._disbled = true;
			that._$targetInput.prop({
				disabled: true
			});

			return that;
		},

		/**
		 * 设置组件可用
		 * @return {[type]} [description]
		 */
		enable: function(){
			var that = this;

			that._disbled = false;
			that._$targetInput.prop({
				disabled: false
			});

			return that;
		},

		/**
		 * 获取缓存数据
		 * @return {[type]} [description]
		 */
		getCacheData: function(){
			return this._cacheData;
		},

		/**
		 * 通过id值获取缓存数据
		 * @return {[type]} [description]
		 */
		getCacheDataById: function(id){
			var cacheData,
				that = this;

			if(that._cacheData){
				cacheData = that._cacheData[id];
			}

			return cacheData;
		},		

		/**
		 * 获取组件jQuery对象
		 * @return {[type]} [description]
		 */
		getContainer: function(){
			return this._$container;
		},

		/**
		 * 获取文本输入框jQuery对象
		 * @return {[type]} [description]
		 */
		getInput: function(){
			return this._$targetInput;
		},

		/**
		 * 获取文本隐藏域jQuery对象
		 * @return {[type]} [description]
		 */
		getHiddenFiled: function(){
			return this._$hiddenFiled;
		},		

		/**
		 * 添加自定义事件
		 * @return {[type]} [description]
		 */
		on: function(){
			var that = this,
				events = that._events;
			events.on.apply(events, arguments);
			return that;
		},

		/**
		 * 移除自定义事件
		 * @return {[type]} [description]
		 */
		off: function(){
			var that = this,
				events = that._events;
			events.off.apply(events, arguments);
			return that;
		},

		/**
		 * 触发自定义事件
		 * @return {[type]} [description]
		 */
		trigger: function(){
			var that = this,
				events = that._events;
			events.trigger.apply(events, arguments);
			return that;
		},

		/**
		 * 初始化
		 * @param  {[type]} options 配置参数
		 * @return {[type]}         
		 */
		_init: function(options){
			var that = this;
			that._options = $.extend(true, {}, that.options, options);
			that
				._cacheParam()
				._initComponent()
				._bindEventListener();
		},

		/**
		 * 初始化组件
		 * @return {[type]} [description]
		 */
		_initComponent: function(){
			var id,
				$target,
				$container,	
				inputOffset,
				$targetInput,			
				$dropdownMenu,
				that = this,
				target = that._target;

			if(target.jquery){
				$target = target;
			}else{
				$target = $(target);	
			}
			if(!$target || !$target.length){
				$.error("初始化时，请传入$target参数");
			}else{
				$container = $(TEMPLATE.autocomplete).append($target.clone().addClass("auto-complete-target").attr("autocomplete", "off"));
				$target.replaceWith($container);
				id = PREFIX + "-" + (GUID++);
				that._containerId = id;	
				$container.attr({
					id: id
				});			
				that._$container = $container;
				that._$dropdownMenu = $container.find(".dropdown-menu");
				that._$targetInput = $container.find(".auto-complete-target");
				that._createHiddenFiled()
					._setDropDownMenuPosition();
			}	

			return that;
		},

		/**
		 * 还原input框		
		 * @return {[type]} [description]
		 */
		_revertBack: function(){
			var that = this,
				$targetInput = that._$targetInput;

			$targetInput
				.show()
				.removeClass("auto-complete-target");
			that._$container.replaceWith($targetInput);

			return that;
		},

		/**
		 * 设置下拉菜单位置
		 */
		_setDropDownMenuPosition: function(){
			var that = this,
				 $targetInput = that._$targetInput,
				 inputOffset = $targetInput.position();

			that._$dropdownMenu.css({
				"left": inputOffset.left + "px",
				"min-width": $targetInput.outerWidth() + "px",
				"top": ($targetInput.outerHeight() + inputOffset.top) + "px"
			});		

			return that;
		},

		/**
		 * 生成hidden表单域
		 * @return {[type]} [description]
		 */
		_createHiddenFiled: function(){
			var hiddenName,
				$hiddenFiled,
				that = this,
				hiddenConfig = that._hiddenFiled;

			if(hiddenConfig && (hiddenName = $.trim(hiddenConfig.name))){
				$hiddenFiled = that._$container.closest("form").find('input:hidden[name="' + hiddenName + '"]');	
				if(!$hiddenFiled.length){
					$hiddenFiled = $(TEMPLATE.hiddenFiled).attr({
						name: hiddenName
					});
					that._$targetInput.after($hiddenFiled);
				}
				that._$hiddenFiled = $hiddenFiled;
			}

			return that;
		},

		/**
		 * 缓存常用参数
		 * @return {[type]} [description]
		 */
		_cacheParam: function(){
			var property,
				that = this,
				options = that._options;

			for(property in options){
				that["_" + property] = options[property];
			}
			that._events = $({});

			return that;
		},

		/**
		 * 绑定事件
		 * @return {[type]} [description]
		 */
		_bindEventListener: function(){
			var that = this,
				dropdownMenu = ".dropdown-menu",
				dropdownMenuItem = ".dropdown-menu li",
				targetInput = ".auto-complete-target",
				autoComplete = ".auto-complete-selector",
				eventCallbacks = that._eventCallbacks;


			that._clickOthersEventCallback = function(e){
				that._eventCallbacks.clickOthers.call(that, e, $(e.target));
			};
			that._$container
				.on("click.typeahead.dropdownmenuitem", dropdownMenuItem, function(e){
					eventCallbacks.clickDropdownMenuItem.call(that, e, $(this));
				})
				.on("keydown.typeahead.inputbox", targetInput, function(e){
					eventCallbacks.keydownInputbox.call(that, e, $(this));
				})
				.on("focusin.typeahead.inputbox", targetInput, function(e){
					eventCallbacks.changeInputBox.call(that, e, $(this));		
				})
				/*.on("focusout.typeahead.inputbox", targetInput, function(e){
					eventCallbacks.focusoutInputBox.call(that);
				})*/
				.on("input.typeahead.inputbox", targetInput, function(e){
					eventCallbacks.changeInputBox.call(that, e, $(this));	
				});
			$(document).on("click.typeahead.others", that._clickOthersEventCallback);

			return that;
		},

		/**
		 * 选中搜索下拉项
		 * @param  {[type]} value [description]
		 * @return {[type]}       [description]
		 */
		_selectResult: function(value, $target){
			var that = this;

			if(that._$hiddenFiled){
				//若有hidden表单域则设置值	
				that._$hiddenFiled.val(value.value);
			}
			if(that._selectedCallback){
				that._selectedCallback(value);			
			}
			that.trigger("selected", [value, $target, that]);

			return that;
		},

		/**
		 * 回车键选中搜索下拉项
		 */
		_keyDownToSelectResult: function(){
			var that = this;

			if(that._isSearchNoEmpty()){
				//that._$targetInput.blur();//失去焦点
				that._eventCallbacks.focusoutInputBox.call(that);
			}	

			return that;
		},


		/**
		 * 键盘导航
		 * @param  {[type]} keyCode
		 * @return {[type]}
		 */
		_keyBoardNavigate: function(keyCode){
			var isUp,
				$current,
				scrollTop,
				that = this,
				$dropdownMenu,
				$dropdownMenuItems;

			if(KEY_BORAD_NAV[keyCode] && that._isSearchNoEmpty()){
				isUp = KEY_CODE.up === keyCode;
				$dropdownMenu = that._$dropdownMenu;
				$dropdownMenuItems = that._$dropdownMenu.children();
				$current = $dropdownMenu.find("." + ACTIVE_CLASS_NAME)
				if($current.length){
					$current.removeClass(ACTIVE_CLASS_NAME);	
					if(isUp){
						$current = $current.prev();
						while($current.length && $current.hasClass(DISABLED_CLASS_NAME)){
							$current = $current.prev();
						}
					}else{
						$current = $current.next();
						while($current.length && $current.hasClass(DISABLED_CLASS_NAME)){
							$current = $current.next();
						}											
					}						
				}else{
					$current = $dropdownMenu.children().not("." + DISABLED_CLASS_NAME).first();
				}
				if($current.length){
					$current.addClass(ACTIVE_CLASS_NAME);
					that._scrollIntoView($current);						
				}
			}

			return that;
		},

		/**
		 * 键盘导航时设置滚动条位置
		 * @param  {[type]} item [description]
		 * @return {[type]}      [description]
		 */
		_scrollIntoView: function(item) {
			var offset, 
				scroll, 				
				itemHeight,
				that = this,
				elementHeight,
				$dropdownMenu = that._$dropdownMenu;

			if (that._hasScroll()) {
				offset = item.offset().top - $dropdownMenu.offset().top;
				scroll = $dropdownMenu.scrollTop();
				elementHeight = $dropdownMenu.height();
				itemHeight = item.outerHeight();
				if (offset < 0) {
					$dropdownMenu.scrollTop(scroll + offset);
				} else if (offset + itemHeight > elementHeight) {
					$dropdownMenu.scrollTop(scroll + offset - elementHeight + itemHeight);
				}
			}

			return that;
		},	

		/**
		 * 判断是否有滚动条
		 * @return {Boolean} [description]
		 */
		_hasScroll: function() {
			var $dropdownMenu = this._$dropdownMenu;
			return $dropdownMenu.outerHeight() < $dropdownMenu.prop("scrollHeight");
		},			

		/**
		 * 执行搜索
		 * @param  {[type]} keyword 关键字
		 * @return {[type]} 
		 */
		_toSeach: function(keyword){
			var that = this;

			that._clearSearchTimer()
				._abortSearchPromise();
			if(keyword){
				that._searchTimer = setTimeout(function(){
					that._search(keyword);
				}, DELAY_TIME.SEARCH);				
			}else{
				that._$dropdownMenu.html("").hide();		
			}

			return that;
		},	

		/**
		 * 搜索
		 * @param  {[type]} keyword 关键字
		 * @return {[type]} 
		 */
		_search: function(keyword){
			var modal,
				promise,
				that = this,
				params = {},
				remote = that._remote,
				tpl = TEMPLATE.menuEmpty;

			params[remote.paramName] = keyword;
			promise = that._searchPromise  = $[remote.requestType](remote.url, params);
			promise
				.done(function(result){
					var data, cacheData = {};
					if($.type(remote.parse) === "function"){
						result = remote.parse(result);	
					}
					if((data = result.data) && data.length){
						tpl = remote.tpl || TEMPLATE.menuItem;
						modal = {
							list: data
						};
					}else{
						modal = {
							text: "没有匹配的内容"
						};						
					}
					$.each(data, function(i, item){
						cacheData[item[remote.idKey || "id"]] = item;	
					});
					that._cacheData = $.extend(true, {}, cacheData);
					cacheData = null;		
				})
				.fail(function(jqXHR, textStatus){
					if(textStatus !== "abort"){
						modal = {
							text: "搜索失败请稍后重试"
						};	
					}
					that._cacheData = {};
				})
				.always(function(){
					that._renderDropdownMenu(tpl, modal);
					modal = remote = tpl = null;
				});

			return that;
		},	

		/**
		 * 搜索结果是否失败或为空
		 * @return {Boolean} [description]
		 */
		_isSearchNoEmpty: function(){
			var $dropdownMenu = this._$dropdownMenu;
			return !!$dropdownMenu.children().not("." + DISABLED_CLASS_NAME).length;
		},

		/**
		 * 渲染下拉菜单
		 * @param  {[type]} tpl   模板
		 * @param  {[type]} modal 数据
		 * @return {[type]}       
		 */
		_renderDropdownMenu: function(tpl, modal){
			var length,
				that = this,
				$dropdownItems,
				$dropdownMenu = that._$dropdownMenu;

			if(modal){
				$dropdownMenu.html(Mustache.render(tpl, modal));
				$dropdownItems = $dropdownMenu.find("li").not("." + DISABLED_CLASS_NAME);
				if((length = $dropdownItems.length)){
					$dropdownMenu.show();	
				}else{
					that._hideInputBox(0);
				}
				if(length === 1){
					$dropdownItems.addClass(ACTIVE_CLASS_NAME);	
				}
			}

			return that;
		},

		/**
		 * 隐藏输入框
		 * @param  {[type]} delay 延时时长
		 */
		_hideInputBox: function(delay, excuteFunc){
			var that = this,
				delay = delay || 0,
				hide = function(){
					that._$dropdownMenu.html("").scrollTop(0).hide();	
				};

			that._clearHideInputBoxTimer();//先清空计时器
			if(delay){
				//若传入延时时长大于0则重新设定计时器	
				that._hideInputTimer = setTimeout(function(){
					excuteFunc && excuteFunc.call(that);
					hide();
				}, delay);
			}else{
				hide();//否则直接隐藏
			}

			return that;
		},		

		/**
		 * 清除隐藏输入框的计时器
		 * @return {[type]} [description]
		 */
		_clearHideInputBoxTimer: function(){
			var that = this;

			if(that._hideInputTimer){
				clearTimeout(that._hideInputTimer);
				that._hideInputTimer = null;	
			}

			return that;
		},	

		/**
		 * 清除搜索请求计时器
		 * @return {[type]} [description]
		 */
		_clearSearchTimer: function(){
			var that = this;

			if(that._searchTimer){
				clearTimeout(that._searchTimer);
				that._searchTimer = null;
			}	

			return that;
		},	

		/**
		 * 终止搜索请求
		 * @return {[type]} [description]
		 */
		_abortSearchPromise: function(){
			var that = this;

			if(that._searchPromise){
				that._searchPromise.abort();
				that._searchPromise = null;	
			}	

			return that;
		},	

		/**
		 * 事件处理回调
		 * @type {Object}
		 */
		_eventCallbacks: {
			/**
			 * 点击其他地方
			 * @return {[type]} [description]
			 */
			clickOthers: function(e, $target){
				var that = this;

				if(!$target.closest("#" + that._containerId).length){
					that._hideInputBox(0);//点击其他地方直接隐藏	
				}
			},

			/**
			 * 输入框失去焦点事件处理
			 * @return {[type]} [description]
			 */
			focusoutInputBox: function(){
				var that = this;
				that._hideInputBox(DELAY_TIME.HIDE_INPUT_BOX, function(){
					this._$dropdownMenu.find("." + ACTIVE_CLASS_NAME).click();					
				});						
			},

			/**
			 * 输入框按钮事件回调
			 * @param  {[type]} e       [description]
			 * @param  {[type]} $target [description]
			 * @return {[type]}         [description]
			 */
			keydownInputbox: function(e, $target){
				var isUp,
					keyCode = e.keyCode;

				//支持键盘操作
				if(keyCode === 13){
					this._keyDownToSelectResult();
					e.preventDefault();//阻止回车键默认行为		
				}else{
					this._keyBoardNavigate(keyCode);
				}
			},

			/**
			 * 下拉菜单单击事件处理
			 * @param  {[type]} e       [description]
			 * @param  {[type]} $target [description]
			 * @return {[type]}         [description]
			 */
			clickDropdownMenuItem: function(e, $target){
				var that = this;

				if(!$target.hasClass(DISABLED_CLASS_NAME)){
					that._$dropdownMenu
						.find("." + ACTIVE_CLASS_NAME)
						.removeClass(ACTIVE_CLASS_NAME);
					$target.addClass(ACTIVE_CLASS_NAME);
					that._selectResult({
						text: $.trim($target.text()),
						value: $.trim($target.attr("data-value"))
					}, $target);
				}
				that._hideInputBox(0);
				e.preventDefault();
			},

			/**
			 * 输入框input事件回调
			 * @param  {[type]} e       [description]
			 * @param  {[type]} $target [description]
			 * @return {[type]}         [description]
			 */
			changeInputBox: function(e, $target){
				var that = this;
				that._toSeach($.trim($target.val()));
			}			
		}
	});
	prototypeFn._init.prototype = prototypeFn;

	return Typeahead;
})(this, jQuery, Mustache);