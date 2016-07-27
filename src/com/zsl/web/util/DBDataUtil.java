package com.zsl.web.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 通用工具类
 */
public class DBDataUtil {

	public static String resetValueToString(Object obj) {
		if (obj == null) { 
			return "";
		} else {
			return (String) obj;
		}
	}
	
	public static Integer resetValueToInteger(Object obj) {
		if (obj == null) { 
			return 0;
		} else {
			return (Integer) obj;
		}
	}
	
	public static Long resetValueToLong(Object obj) {
		if (obj == null) { 
			return (long)0;
		} else {
			return (Long) obj;
		}
	}
	
	/**
	 * 转换为BigDecimal类型，如果类型不符合返回0
	 * @param obj
	 * @return
	 */
	public static BigDecimal resetValueToBigDecimal(Object obj) {
		if (obj == null) { 
			return new BigDecimal(0);
		} else {
			if (obj instanceof BigDecimal) {
				return (BigDecimal) obj;
			} else if (obj instanceof Long) {
				return new BigDecimal((Long)obj);
			} else if (obj instanceof Integer) {
				return new BigDecimal((Integer)obj);
			} else {
				return new BigDecimal(0);
			}
		}
	}
	/**
	 * 根据品种和地区，找到指定的部门组
	 * @param cate x PP PE  y PVC  z 国际贸易
	 * @param area A 广州  B 上海  C东莞  D乐从  E茂名 F余姚 G成都
	 * @return
	 */
	public static List<Long> getDepartmentIds(String cate,String area){
		List<Long> list=new ArrayList<Long>();
		HashMap<String,String> d_map=new HashMap<String,String>();
		d_map.put("Ax", "10,11,19,");
		d_map.put("Ay", "12,13,18,");
		d_map.put("Az", "38,");
		d_map.put("Bx", "46,47,48,");
		d_map.put("By", "49,50,51,");
		d_map.put("Bz", "52,");
		d_map.put("Cx", "54,55,");
		d_map.put("Cy", "56,");
		d_map.put("Dx", "31,");
		d_map.put("Dy", "30,");
		d_map.put("Ex", "27,");
		d_map.put("Ey", "26,");
		d_map.put("Fx", "21,39,");
		d_map.put("Fy", "20,40,");
		d_map.put("Fz", "41,");
		d_map.put("Gx", "60,");
		d_map.put("Gy", "59,");
		String ids="";
		String[] cates=cate.split(",");
		String[] areas=area.split(",");
		for(int i=0;i<areas.length;i++){
			if(areas[i].length()>0){
				for(int j=0;j<cates.length;j++){
					if(cates[j].length()>0){
						String id=d_map.get(areas[i]+cates[j]);
						if(id!=null) ids=ids+id;
					}
				}
			}
		}
		String[] d_ids=ids.split(",");
		for(int k=0;k<d_ids.length;k++){
			if(d_ids[k].length()>0){	
				  list.add(new Long(d_ids[k]));
//			      System.out.println(d_ids[k]);
			}
		}
		return list;
	}
			   
}
