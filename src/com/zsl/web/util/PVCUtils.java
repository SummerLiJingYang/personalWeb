package com.zsl.web.util;

public class PVCUtils {
	public static final String[] pvcs=new String[]{"PVC","DOP","DOTP","CPE","烧碱","片碱"};
	/**
	 * 是否属于pvc
	 * @return
	 */
	public static boolean isPvc(String cate){
		for (String pvc : pvcs) {
			if(cate.equalsIgnoreCase(pvc)){
				return true;
			}
		}
		return false;
	}
	/**
	 * 是否不属于pvc
	 * @return
	 */
	public static boolean isNotPvc(String cate){
		for (String pvc : pvcs) {
			if(cate.equalsIgnoreCase(pvc)){
				return false;
			}
		}
		return true;
	}
}
