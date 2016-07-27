package com.zsl.web.init;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.danga.MemCached.SockIOPool;
import com.zsl.web.util.config.GlobalConfig;

/**
 * 项目初始化类
 *  
 */
public class StartInit extends GenericServlet {
	private static final long	serialVersionUID	= -6240815990725451578L;
	
	private static final Logger logger = Logger.getLogger(StartInit.class);	
	
	@Override
	public void init(ServletConfig config) throws ServletException {		
		super.init(config);
		
		/**
		 * 从配置文件读入一些项目全局变量
		 * 这种方式与使用静态变量定义相比，如果有修改，只需要修改配置文件，不需要重编译和重启
		 */
		try {	
			String globalCfgPath = config.getInitParameter("global-config-path");
			logger.warn("config.getServletContext().getRealPath(/):"+config.getServletContext().getRealPath("/"));
			logger.warn("globalCfgPath:"+globalCfgPath);
			GlobalConfig.init(config.getServletContext().getRealPath("/"),globalCfgPath);
		} catch (Exception e) {
			e.printStackTrace();
		}			

       //---------------系统缓存初始化----------------
		try{
			Configuration cfg=GlobalConfig.getConfig("common_config");
			String MenCache_host=cfg.getString("MenCache.host");
			String MenCache_port=cfg.getString("MenCache.port");
			logger.info("host:"+MenCache_host+";port:"+MenCache_port);
			if(MenCache_host.length()>0 && MenCache_port.length()>0){
				String[] serverlist = {MenCache_host + ":"+MenCache_port};
				SockIOPool pool = SockIOPool.getInstance();
	        	pool.setServers(serverlist);
	        	pool.initialize();	
			}
		}catch(Exception ept){
			ept.printStackTrace();
		}
		
		
	}
	
	public void service(ServletRequest req, ServletResponse res)
		throws IOException, ServletException {		
	}
}