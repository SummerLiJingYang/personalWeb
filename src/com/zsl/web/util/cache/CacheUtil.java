package com.zsl.web.util.cache;


import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * 简易定时缓存器
 * @author yanfuqian
 *
 */
public class CacheUtil {
	private static Map<String, Object> cache = new HashMap<String, Object>();

	private CacheUtil() {
	}
	
	
	/**
	 * 放置缓存 
	 * @param key
	 * @param value
	 */
	public static void put(String key, Object value){
		cache.put(key, value);
	};
	
	/**
	 * 放置缓存 并设置时效性（单位：秒）
	 * @param key
	 * @param value
	 * @param duration
	 */
	public static void put(String key, Object value, int duration){
		put(key, value);
		
		new Timer().schedule(new CacheTimer(key), duration * 1000);
	};
	
	/**
	 * 获取缓存
	 * @param key
	 */
	public static Object get(String key){
		return cache.get(key);
	};
	
	/**
	 * 清理对应缓存
	 * @param key
	 */
	public static void flush(String key){
		cache.remove(key);
	}
	
	/**
	 * 清空缓存
	 */
	public static void flushAll(){
		cache = new HashMap<String, Object>();
	}
}
