package com.zsl.web.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 将List集合对象变为Set集合元素
 * @author gq
 *
 */
public class ConversionPropertySetUtils {
	/**
	 * 
	 * @param list
	 * @param propertyName 方法名
	 * @return
	 */
	public static Set conversionPropertySet(List list,String propertyName){
		Set setPropertys=new HashSet();
		for (Object obj : list) {
			try {
				Method method = obj.getClass().getMethod(propertyName);
				if(method!=null){
					Object result=method.invoke(obj);
					setPropertys.add(result);
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return setPropertys;
	}
	/**
	 * 
	 * @param list
	 * @param propertyName 方法名
	 * @return
	 */
	public static List conversionPropertyList(List list,String propertyName){
		List listPropertys=new ArrayList();
		for (Object obj : list) {
			try {
				Method method = obj.getClass().getMethod(propertyName);
				if(method!=null){
					Object result=method.invoke(obj);
					listPropertys.add(result);
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listPropertys;
	}
	/**
	 * 适用范围比较小(用于石油日期价格走势图)
	 * @param list 对返回的时间进行处理 返回String “yyyy-MM-dd”的时间
	 * @param propertyName 方法名
	 * @return
	 */
	public static List conversionPropertyDateList(List list,String propertyName){
		List listPropertys=new ArrayList();
		for (Object obj : list) {
			try {
				Method method = obj.getClass().getMethod(propertyName);
				if(method!=null){
					Object result=method.invoke(obj);
					if(result instanceof Timestamp){
					listPropertys.add("'"+DateUtils.formatDate((Timestamp)result)+"'");
					}
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listPropertys;
	}
}
