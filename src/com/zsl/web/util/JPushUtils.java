package com.zsl.web.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.schedule.ScheduleResult;


/**
 * 极光推送工具
 */
public class JPushUtils {

    protected static final Logger LOG = LoggerFactory.getLogger(JPushUtils.class);

    //提塑
	private static final String tsAppKey ="551659a15070b8ee65ec0fbd";
	private static final String tsMasterSecret = "5c5693f45bf735dd49b5fc34";
	//塑购正式
	private static final String zslAppKey ="698d3a856cdd786370882fd9";
	private static final String zslMasterSecret = "5da2ac6f3cd5bc4b0202b7d1";
	//塑购测试
	private static final String zslCeshiAppKey ="7abaf73fc51e0135ae037fb0";
	private static final String zslCeshiMasterSecret = "c29a4962a6042a093799af22";
	
    
	/**
	 * 推送给某个registerId
	 * @param content
	 * @param registrationId
	 * @param extras
	 * @return
	 */
    public static boolean send(String content,String registrationId,Map<String, String> extras) {
        JPushClient jpushClient = new JPushClient(tsMasterSecret, tsAppKey, 3);
        PushPayload payload = buildPushObject_register_id_alert(content,registrationId,extras==null?new HashMap<String,String>():extras);
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Jpush Got result - " + result);
            return true;
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            return false;
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            return false;
        }catch(Exception e){
        	LOG.info("推送给某个registerId报错",e);
        	return false;
        }
	}
    
    /**
     * 推送给某个设备
     * @param content
     * @param registrationId
     * @param extras
     * @return
     */
	private static PushPayload buildPushObject_register_id_alert(String content,String registrationId,Map<String, String> extras) {
	    return PushPayload.newBuilder()
	    		.setPlatform(Platform.all())
	    		.setAudience(Audience.registrationId(registrationId))
	    	    .setNotification(Notification.ios(content, extras))
                .setMessage(cn.jpush.api.push.model.Message.newBuilder()
                        .setMsgContent(content)
                        .setTitle("找塑料福牛助手")
                        .addExtras(extras)
                        .build())
	    	    .setOptions(Options.newBuilder()
                         .setApnsProduction(true)//正式环境需要放开（true）
                         .build())
	    		.build();
	}
	
	/**
	 * 定时推送
	 * @param name
	 * @param time
	 * @param androidTitle
	 * @param content
	 * @param extras
	 * @return
	 */
	public static String createSingleSchedule(String name,String time,String title,String content,Map<String, String> extras) {
		JPushClient jpushClient = null;
		if("on".equals(SwicthSMSUtil.getZslJpushStatus())){
			jpushClient = new JPushClient(zslMasterSecret,zslAppKey,3);
		}else{
			jpushClient = new JPushClient(zslCeshiMasterSecret,zslCeshiAppKey,3);
		}
        PushPayload push = singleSchedulePush(title,content,extras);
        try {
            ScheduleResult result = jpushClient.createSingleSchedule(name, time, push);
            LOG.info("schedule result is " + result);
            return result.getSchedule_id();
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            return null;
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            return null;
        }catch(Exception e){
        	LOG.info("定时推送报错",e);
        	return null;
        }
    }
	
    /**
     * 定时推送
     * @param content
     * @param registrationId
     * @param extras
     * @return
     */
	private static PushPayload singleSchedulePush(String title,String content,Map<String, String> extras) {
	    return PushPayload.newBuilder()
	    		.setPlatform(Platform.all())
	    		.setAudience(Audience.all())
	    	    .setNotification(Notification.ios(title, extras))
                .setMessage(cn.jpush.api.push.model.Message.newBuilder()
                        .setMsgContent(content)
                        .setTitle(title)
                        .addExtras(extras)
                        .build())
	    	    .setOptions(Options.newBuilder()
                         .setApnsProduction("on".equals(SwicthSMSUtil.getZslJpushStatus())?true:false)//正式环境需要放开（true）
                         .build())
	    		.build();
	}
	
	/**
	 * 删除定时任务
	 * @param scheduleId
	 * @return
	 */
	public static boolean deleteSchedule(String scheduleId) {
		JPushClient jpushClient = null;
		if("on".equals(SwicthSMSUtil.getZslJpushStatus())){
			jpushClient = new JPushClient(zslMasterSecret,zslAppKey,3);
		}else{
			jpushClient = new JPushClient(zslCeshiMasterSecret,zslCeshiAppKey,3);
		}
        try {
            jpushClient.deleteSchedule(scheduleId);
            return true;
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            return false;
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            return false;
        }catch(Exception e){
        	LOG.info("删除推送定时任务报错",e);
        	return false;
        }
    }
	
	public static void main(String[] args) {
		Map<String,String> pushParameter = new HashMap<>(10);
		pushParameter.put("view", "funiuView");
		String schedule_id = JPushUtils.createSingleSchedule("找塑料网福牛视点推送"+System.currentTimeMillis(), "2015-12-31 11:39:59", "找塑料网福牛视点推送", "找塑料网福牛视点来了来了", pushParameter);
		System.out.println(schedule_id);
	}
}
