package com.zsl.web.util.interceptor;

import java.lang.reflect.Proxy;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.zsl.web.util.CustomerContextHolder;

@Component
@Aspect
public class MultipleDataSourceInterceptor{
	@Pointcut("execution(public * com.zsl.web.*.service..*.*(..))")
    public void dynamicSetDataSoruce(){}
     
	private static final Logger logger = Logger.getLogger(MultipleDataSourceInterceptor.class);	
	
    @Before("dynamicSetDataSoruce()")
    public void before(JoinPoint joinPoint) {
    	 Class<?> clazz = joinPoint.getTarget().getClass();
         String className = clazz.getName();
         if (ClassUtils.isAssignable(clazz, Proxy.class)) {
             className = joinPoint.getSignature().getDeclaringTypeName();
         }
         String methodName = joinPoint.getSignature().getName();
         Object[] arguments = joinPoint.getArgs();
         logger.info("method:=="+methodName);
         if (methodName.contains("create") || methodName.contains("update") || methodName.contains("delete") || methodName.contains("add")) {
             CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_MYSQL);
         } else if (methodName.contains("get") || methodName.contains("find")) {
             CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_ORACLE);
         } else {
        	 logger.error("------------------------------------配置的service层方法，必须包含（create|update|delete|add）或者（get|find）-----------------------");
             CustomerContextHolder.clearCustomerType();
         } 
        logger.info("datasource:"+CustomerContextHolder.getCustomerType());
       
    }
     
}
