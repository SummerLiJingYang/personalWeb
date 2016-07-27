package com.zsl.web.util;

import java.math.BigDecimal;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

/**
 * 
 *     
 * 项目名称：gyl_zsl    
 * 类名称：MapSort    
 * 类描述：    
 * 创建人：yuandongyong 
 * 创建时间：2015年4月15日  
 * @version 1.0    
 *
 */
public class MapSort implements Comparator<Map> {
	private String sort;
	public final String getSort() {
		return sort;
	}
	public final void setSort(String sort) {
		this.sort = sort;
	}
	@Override
	public int compare( Map o1, final Map o2) {
		int flag = 0;
		final Map map1 = o1;
		final Map map2 = o2;
		if (map1.get(sort) instanceof BigDecimal) {
			BigDecimal fName1 = (BigDecimal) map1.get(sort);
			BigDecimal fName2 = (BigDecimal) map2.get(sort);
			return fName2.compareTo(fName1);
		} else if (map1.get(sort) instanceof Integer) {
			Integer fName1 = (Integer) map1.get(sort);
			Integer fName2 = (Integer) map2.get(sort);
			return fName2.compareTo(fName1);
		} else if (map1.get(sort) instanceof Long) {
			Long fName1 = (Long) map1.get(sort);
			Long fName2 = (Long) map2.get(sort);
			return fName2.compareTo(fName1);
		} else if (map1.get(sort) instanceof String) {
			String fName1 = (String) map1.get(sort);
			String fName2 = (String) map2.get(sort);
			return Collator.getInstance(Locale.CHINESE).compare(fName1, fName2);
		} else {
			return 0;	
		}
	}

}
