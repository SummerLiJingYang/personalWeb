package com.zsl.web.util;

import java.io.Serializable;
import java.text.DecimalFormat;

import org.apache.commons.lang.math.RandomUtils;

public class SnUtils implements Serializable {
	
		private static final long serialVersionUID = 7921724644078006L;
		private static SnUtils primaryGenerater = null;
	  /*  private SimpleDateFormat sdFormat = new SimpleDateFormat("yyMMddhhmmssSSS");*/
	    private long salt = 79217246440780L;
	    private SnUtils() {
	    }
	 
	    /**
	     * 取得SnUtils的单例实现
	     *
	     * @return
	     */
	    public static SnUtils getInstance() {
	        if (primaryGenerater == null) {
	            synchronized (SnUtils.class) {
	                if (primaryGenerater == null) {
	                    primaryGenerater = new SnUtils();
	                }
	            }
	        }
	        return primaryGenerater;
	    }
	 
	    /**
	     * 生成编号
	     */
	    public synchronized String genSn() {
    		long myTime = System.currentTimeMillis();
    		int nextInt = RandomUtils.nextInt(999);
    		 DecimalFormat df = new DecimalFormat("000");
    		 String format = df.format(nextInt);
    		 String sn = String.valueOf(myTime).concat(format);
    		 Long lsn =Long.valueOf(sn)+salt;
    		return String.valueOf(lsn);
	    }
	    
	    public static void main(String[] args) {
			for (int i = 0; i < 1000; i++) {
				String sn = SnUtils.getInstance().genSn();
				System.out.println(sn);
			}
		}
	    
}
