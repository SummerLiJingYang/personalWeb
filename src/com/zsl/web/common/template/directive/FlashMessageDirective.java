package com.zsl.web.common.template.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;



import com.zsl.web.util.Message;
import com.zsl.web.util.Message.Type;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 瞬时消息
 * 
 * @author lijingyang
 * @version 1.0
 * 调用说明
 * 1.前端调用
 * $().ready(function() {
		[@flash_message /]//最终会生成js代码：$.jBox.tip(\"" + message.getContent() + "\", \"" + message.getType() + "\");
	}
	2.后端设置值SUCCESS_MESSAGE
	/**
	 * 保存
	 *
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(RedirectAttributes redirectAttributes) {
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list";
	}
 */
@Component("flashMessageDirective")
public class FlashMessageDirective extends BaseDirective {

	/** "瞬时消息"属性名称 */
	public static final String FLASH_MESSAGE_ATTRIBUTE_NAME = FlashMessageDirective.class.getName() + ".FLASH_MESSAGE";

	/** 变量名称 */
	private static final String VARIABLE_NAME = "flashMessage";

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			Message message = (Message) requestAttributes.getAttribute(FLASH_MESSAGE_ATTRIBUTE_NAME, RequestAttributes.SCOPE_REQUEST);
			if (body != null) {
				setLocalVariable(VARIABLE_NAME, message, env, body);
			} else {
				Writer out = env.getOut();
				if (message != null) {
					String icon="1";
					Type type=message.getType();
					if(type==Type.error){
						icon="0";
					}else if(type==Type.success){
						icon="1";
					}else{
						icon="2";
					}
					out.write(" layer.msg(\"" + message.getContent() + "\", {icon:"+icon +"});");
				}
			}
		}
	}

}