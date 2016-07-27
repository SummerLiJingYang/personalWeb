package com.zsl.web.util;

import org.apache.log4j.Logger;

/**
 * 异常处理工具类
 * @author zhoukai
 *
 */
public class ExceptionUtils {

	private static class ExceptionUtilsHolder {  
        private static final ExceptionUtils INSTANCE = new ExceptionUtils();  
    }  
    private ExceptionUtils (){}  
    public static final ExceptionUtils getInstance() {  
        return ExceptionUtilsHolder.INSTANCE; 
    } 
    
    /**
     * 打印错误信息
     * @param classType
     * @param throwable
     */
    public void printOutErrorInfo(Class<?> classType, Throwable throwable) {
    	Logger logger = Logger.getLogger(classType);
    	for(StackTraceElement s: throwable.getStackTrace()) {
			logger.error(s);
		}
    }
}
