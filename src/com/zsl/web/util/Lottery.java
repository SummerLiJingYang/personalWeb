package com.zsl.web.util;

import java.util.Random;


public class Lottery {
	public Lottery(){
	}
	
	/*
	 * 抽奖并返回奖项
	 * 
	 * @param Object[][position【位置】,award【奖项ID】,rate【中奖率】] 
	 * @return Object[位置,奖项ID] 获得奖项
	 */
	public Object[] getAward(Object[][] awardArray){
		Integer[] obj = new Integer[awardArray.length]; //概率数组
		for(int i=0;i<awardArray.length;i++){
			obj[i] = (Integer) awardArray[i][2];
		}
		Integer awardId = getRand(obj); //根据概率获取奖项id
		if(awardId == null){
			return new Object[]{"-1","-1",""};
		}
		String position = awardArray[awardId][0].toString();//转盘所在的位置
		String prize = awardArray[awardId][1].toString();//奖项ID
		return new Object[]{position,prize};
	}
	
	public Object[] getAwardForOx(Object[][] awardArray, int prodId){
		Integer[] obj = new Integer[awardArray.length]; //概率数组
		int  sum = 0; //概率数组的总概率精度 
		for(int i=0;i<awardArray.length;i++){
			obj[i] = (Integer) awardArray[i][1];
			sum+= (Integer)awardArray[i][1];
		}
		
		if(0 == sum){
			obj = new Integer[awardArray.length];
			for(int i=0;i<awardArray.length;i++){
				int productID = (Integer) awardArray[i][0];
				if(productID >= prodId){
					obj[i] = 50;
				}else{
					obj[i] = 0;
				}
			}
		}
		Integer awardId = getRand(obj); //根据概率获取奖项id
		String prize = awardArray[awardId][0].toString();//奖项ID
		return new Object[]{prize};
	}
	
	/*
	 * 根据概率获取奖项
	 * 
	 * @param obj[] 概率数组
	 * @return 获得奖项
	 */
	public Integer getRand(Integer obj[]){
		Integer result = null;
		try {
			int  sum = 0; //概率数组的总概率精度 
			for(int i=0;i<obj.length;i++){
				sum+=obj[i];
			}
			for(int i=0;i<obj.length;i++){ //概率数组循环 
				int randomNum = new Random().nextInt(sum); //随机生成1到sum的整数
				if(randomNum<obj[i]){//中奖
					result = i;
					break;
				}else{
					sum -=obj[i];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}