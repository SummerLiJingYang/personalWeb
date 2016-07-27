package com.zsl.web.util.cache;


import java.util.TimerTask;

/**
 * 缓存清理定时器
 * @author yanfuqian
 *
 */
public class CacheTimer extends TimerTask {
	
	// 缓存键
	private String key;

	public CacheTimer(String key) {
		this.key = key;
	}

	@Override
	public void run() {
		CacheUtil.flush(getKey());
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
