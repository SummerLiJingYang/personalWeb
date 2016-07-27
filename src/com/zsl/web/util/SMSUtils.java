package com.zsl.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 短信模型
 * @author linjiande
 *
 */
public class SMSUtils {
	
	private static Logger log = LoggerFactory.getLogger(SMSUtils.class);
	
	public static final String corpId = "301729";
	public static final String loginName = "Admin";
	public static final String passwd = "310415";
	
	public static final String sendUrl = "http://sms3.mobset.com/SDK/Sms_Send.asp?CorpID="+corpId+"&LoginName="+loginName+"&Passwd="+passwd+"&send_no={0}&Timer=&LongSms=0&msg={1}";
	
	/**
	 * 信息类型枚举
	 *  状态    1：注册验证码   2：修改登录账户验证码   3：修改登录账户后的新账户验证码  4：找回密码的验证码   5：注册成功的短信信息
	 *       6：修改账号成功的短信信息   7：找回密码成功后的短信信息  8：客服发送给注册会员属于他的跟进交易员信息  9:短信发送app下载链接
	 */ 
	public static enum SMS_TYPE_ENUM{
		st1(1,"注册验证码"),st2(2,"修改登录账户验证码"),st3(3,"修改登录账户后的新账户验证码"),
		st4(4,"找回密码的验证码"),st5(5,"注册成功的短信信息"),st6(6,"修改账号成功的短信信息"),
		st7(7,"找回密码成功后的短信信息"),st8(8,"客服发送给注册会员属于他的跟进交易员信息"),
		st9(9,"短信发送app下载链接");
		private Integer code;
		private String value;
		private SMS_TYPE_ENUM(Integer code,String value) {
			this.code = code;
			this.value = value;
		}
		public Integer getCode() {
			return this.code;
		}
		public void setCode(Integer code) {
			this.code = code;
		}
		public String getValue() {
			return this.value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	
	private SMSUtils() {
	}

	/**
	 * 手机信息发送
	 * @param mobile  手机号码
	 * @param verifyCode   验证码，如果没有可以为空
	 * @param content   手机内容
	 * @return
	 */
	public synchronized static boolean sendSms(String mobile,String content){
		InputStream in = null;
		try {
			String content1= URLEncoder.encode(content, "GBK");
	        URL url = new URL(sendUrl.replace("{0}", mobile).replace("{1}", content1));
	        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
	        urlConnection.setConnectTimeout(3000);//超时设置，防止 网络异常的情况下，可能会导致程序僵死而不继续往下执行
	        urlConnection.setReadTimeout(30000);  
	        in = urlConnection.getInputStream();
	        log.warn("发送短信内容："+content+",发送者："+mobile);
            return true;
	     }catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error("发送短信失败："+e.getMessage(),e);
	     }catch(IOException e){
	        e.printStackTrace();
	        log.error("发送短信失败："+e.getMessage(),e);
	     }catch(Exception e){
	    	 e.printStackTrace();
	     }finally{
	    	 if(in != null){
	    		 try {
					in.close();
				} catch (IOException e) {
					 log.error(e.getMessage(),e);
				}
	    	 }
	     }
		return false;
	}
    
    
    /** 
     * @param 短信模板选择
     * @return 短信内容
     */
    public static String getSmsTemplate(SMS_TYPE_ENUM type,String... paras){
        String content = null;
    	switch(type){
            case st1:
                //注册验证码
                content="欢迎注册找塑料网会员，本次注册验证码：{0}，有效期30分钟，过期将失效，感谢您的注册。";
                break;
            case st2:
                //修改登录账户验证码
                content="尊敬的找塑料网会员，您本次修改登录账号的验证码：{0}，有效期30分钟，如非本人操作请联系020-83939808。";
                break;
            case st3:
                //修改登录账户后的新账户验证码
                content="尊敬的找塑料网会员，您绑定新登录账号的验证码：{0}，有效期30分钟，如非本人操作请联系020-83939808。";
                break;
            case st4:
                //找回密码的验证码
                content="尊敬的找塑料网会员，您本次修改登录密码的手机验证码：{0}，有效期30分钟，如非本人操作请联系020-83939808。";
                break;
            case st5:
                //注册成功的短信信息
                content="感谢您注册找塑料网，您的账号：{0}，密码：{1}。客服热线:020-83939808。";
                break;
            case st6:
                //修改账号成功的短信信息
                content="您在找塑料网注册的登录账号已修改成功,新登录账号：{0}，请妥善保管您的注册信息。";
                break;
            case st7:
                //找回密码成功后的短信信息
                content="尊敬的找塑料网会员，您已成功修改了账号名为：{0}的登录密码，新登录密码是：{1}。";
                break;
            case st8:
                //客服发送给注册会员属于他的跟进交易员信息
                content="尊敬的找塑料网会员，找塑料网为您分配专职交易服务人员{0}，{1}。一个电话，买卖搞定！";
                break;
            case st9:
                //短信发送app下载链接
                content="感谢您下载找塑料网手机客户端，请点击以下链接进行下载：http://url.cn/SkymOa。";
                break;
            default:
                //注册验证码
                content="欢迎注册找塑料网会员，本次注册验证码：{0}，有效期30分钟，过期将失效，感谢您的注册。";
        }
    	for(int i = 0 ; i < paras.length ; i++){
    		content = content.replace("{"+i+"}", paras[i]);
        }
    	return content;
    }

    public static String getResult(Integer code){
        String errorMessage ="";
        if(code==0){
        	return "发送成功";
        }else{
           switch(code){
           case 0:
               case -1:
                    errorMessage="参数不全";
                    break;
               case -2:
                    errorMessage="用户名或密码验证错误";
                    break;
               case -3:
                    errorMessage="发送短信余额不足";
                    break;
               case -4:
                    errorMessage="没有手机号码或手机号码超过100个";
                    break;
               case -5:
                    errorMessage="发送手机里含有错误号码";
                    break;
               case -6:
                    errorMessage="内容超长";
                    break;
               case -7:
                    errorMessage="短信中含有非法字符或非法词汇";
                    break;
              // case "非法字":$error='短信中含有非法字符或非法词汇';break;
               case -8:
                    errorMessage="未开放HTTP接口";
                    break;
               case -9:
                    errorMessage="IP地址认证失败";
                    break;
               case -10:
                    errorMessage="发送时间限制";
                    break;
               case -11:
                    errorMessage="短信类别ID不存在或不正确";
                    break;
               case -12:
                    errorMessage="提交的短信条数不正确";
                    break;
               default:
                    errorMessage="未知的错误";
           } 
        }
        return errorMessage;
    }

    /**
     * 群发短信
     */
    /*public function send_sms_batch($data)
    {
        $para=$this->_config;
        $para['Timer']='';
        $para['LongSms']=1;

        foreach($data as $k=>$v)
        {
            $para['send_no']=$k;
            $para['msg']=rawurlencode(iconv('UTF-8', 'GBK', $v));
            $url = $this->_url.'?'.x::query_string($para);
            $string = file_get_contents($url);
            $log_file=ROOT_PATH.'/temp/log/sms.log';
            error_log(date('Y-m-d H:i:s').' | '.$string.' | '.$k.' | '.$v."\n",3,$log_file);
        }

    }*/
    
}
