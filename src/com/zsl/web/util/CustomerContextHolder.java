package com.zsl.web.util;

/**
 * <b>function:</b> 多数据源
 * 用线程实现
 */
public abstract class CustomerContextHolder {
 
    public final static String DATA_SOURCE_ORACLE = "dataSource1";
    public final static String DATA_SOURCE_MYSQL = "dataSource2";
    
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
    
    public static void setCustomerType(String customerType) {  
    	synchronized (customerType) {
    		 contextHolder.set(customerType);
		}
    }  
      
    public static String getCustomerType() {  
        return contextHolder.get();  
    }  
      
    public static void clearCustomerType() {  
        contextHolder.remove();  
    }  
}
