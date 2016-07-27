package com.zsl.web.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用工具类
 */
public class Util {

	private static Logger log = LoggerFactory.getLogger(Util.class);
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            字符串
	 * @return true：为空； false：非空
	 */
	public static boolean isNull(String str) {
		if (str != null && !str.trim().equals("")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * MD5 加密
	 */
	public static String encryptMD5(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

	/**
	 * 得到抛异常的信息
	 * 
	 * @param t
	 * @return
	 */
	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}

	
	public static boolean isNumeric(String num) {
	   try {   
		   Double.parseDouble(num);
	       return true;
	   } catch (Exception e) {
	       return false;
	   }
	}
	
	
	/**
     * 加密解密
     * @param string $string
     * @param string $operation
     * @param string $key
     * @param int $expiry
     * @return string
     */
    public static String authcode(String string,String operation,String key,int expiry)
    {
		String authkey = "U7yDkO72HmAq92Lw";
		operation = StringUtils.isEmpty(operation) ? "DECODE" : operation;
		
		if (operation.equals("DECODE")) {
			try {
				SecureRandom random = new SecureRandom();
				DESKeySpec desKey = new DESKeySpec(authkey.getBytes());
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				SecretKey securekey = keyFactory.generateSecret(desKey);
				Cipher cipher = Cipher.getInstance("DES");
				cipher.init(Cipher.DECRYPT_MODE, securekey, random);
				return new String(cipher.doFinal(Base64.getDecoder().decode(string.getBytes())));
			} catch (Throwable e) {
				e.printStackTrace();
			}
		} else {
			try {
				SecureRandom random = new SecureRandom();
				DESKeySpec desKey = new DESKeySpec(authkey.getBytes());
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				SecretKey securekey = keyFactory.generateSecret(desKey);
				Cipher cipher = Cipher.getInstance("DES");
				cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
				return Base64.getEncoder().encodeToString(cipher.doFinal(string.getBytes()));
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return "";
        
    }
    
    /**
     * 加密解密
     * @param string $string
     * @param string $operation
     * @param string $key
     * @param int $expiry
     * @return string
     */
    public static String crypto(String string,String operation,String key,int expiry)
    {
    	try{
    		string = String.valueOf(StringUtils.trimToEmpty(string)).replace(" ", "+");
	    	String authkey="Q8JH7TGF6M93UH0C";
	    	operation=StringUtils.isEmpty(operation) ? "DECODE" : operation;
	        int ckey_length=4;//动态密匙长度
	        key=DigestUtils.md5Hex(StringUtils.isNotBlank(key) ? key : authkey);//密匙
	        String keya=DigestUtils.md5Hex(StringUtils.substring(key,0,16));//密匙a，参与加解密
	        String keyb=DigestUtils.md5Hex(StringUtils.substring(key,16,32));//密匙b，验证数据完整性
	        String rndstr=DigestUtils.md5Hex(String.valueOf(System.currentTimeMillis()));
	        String keyc=ckey_length>0 ? (operation.equals("DECODE") ? StringUtils.substring(string,0,ckey_length) : StringUtils.substring(rndstr,-ckey_length)) : "";//密匙c，变化生成的密文
	
	        String cryptkey=keya+DigestUtils.md5Hex(keya+keyc);//加解密密匙
	        int key_length=cryptkey.length();//加解密密匙长度
	        //明文前10位用来保存时间戳，解密时验证数据有效性，10到26位用来保存密匙b，解密时验证数据完整性，密文前部保存动态密匙，解密时从动态密匙后面开始
	        string=operation.equals("DECODE") ? new String(Base64.getDecoder().decode(StringUtils.substring(string,ckey_length))) : String.format("%010d",expiry>0 ? expiry+(int)System.currentTimeMillis()/1000 : 0)+StringUtils.substring(DigestUtils.md5Hex(string+keyb),0,16)+string;
	        int string_length=string.length(); 
	
	        //产生密匙簿
	        StringBuffer sb=new StringBuffer();
	        int box[] =new int[256];
	        for(int i=0;i<256;i++){
	            box[i]=i;
	        }
	        int rndkey[]= new int[256];
	        for(int i=0; i<=255; i++) {
	            rndkey[i]=(int)(cryptkey.charAt(i%key_length));
	        }
	
	        //用固定的算法，打乱密匙簿，增加随机性
	        for(int j=0,i=0; i<256; i++) {
	            j=(j+box[i]+rndkey[i])%256;
	            int tmp=box[i];
	            box[i]=box[j];
	            box[j]=tmp;
	        }
	
	        //从密匙簿得出密匙进行异或，再转成字符
	        for(int a=0,j=0,i=0; i<string_length; i++) {
	            a=(a+1)%256;
	            j=(j+box[a])%256;
	            int tmp=box[a];
	            box[a]=box[j];
	            box[j]=tmp;
	            sb.append((char)(((int)(string.charAt(i)))^(box[(box[a]+box[j])%256])));
	        }
	        String result=sb.toString();
	
	        //返回加解密结果，解密时验证数据有效性，加密时把动态密匙保存在密文里，结果用base64编码
	        if(operation.equals("DECODE")) {
	        	int time=StringUtils.isNumeric(StringUtils.substring(result,0,10)) ? Integer.parseInt(StringUtils.substring(result,0,10)) : 0;
	            return (time==0 || time-(int)(System.currentTimeMillis()/1000)>0) && StringUtils.substring(result,10,26).equals(StringUtils.substring(DigestUtils.md5Hex(StringUtils.substring(result,26)+keyb),0,16)) ? StringUtils.substring(result,26) : "";
	        } else {
	            return keyc+Base64.getEncoder().encodeToString(result.getBytes()).replace("=","");
	        }
    	}catch(Exception e){
    		log.error(e.getMessage(), e);
    		return "";
    	}
    }
    
    /** 华东常用地区 去除山东 "临沂","青岛","黄岛","淄博","菏泽","德州"*/
	public static final String[] CITY_HUADONG = new String[] {"上海","余姚","宁波","青浦","嘉定","杭州","金华","苏州","常州","诸暨","温州","绍兴","太仓","南昌","义乌","安庆","台州","昆山","桐庐","宝山","闵行","乐清","湖州","奉贤","嘉兴","瑞安","黄浦","慈溪","江苏","华东","浙江","苍南","海宁","无锡","南京","金山","合肥","扬州","宜春","桐城"};
	
	/**
	 * 判断该城市是不是华东
	 * @param city
	 * @return 返回该城市（数组中的一个）
	 */
	public static String isHuaDong(String city){
		if(StringUtils.isNotBlank(city)){
			for(String c:CITY_HUADONG){
				if(StringUtils.contains(city, c)){
					return c;
				}
			}
		}
		return null;
	}
	
	 /**
     * 复制对象
     */
    @SuppressWarnings("unchecked")  
    public static <T extends Serializable> T clone(T obj){  
        T cloneObj = null;  
        try {  
            //写入字节流  
            ByteArrayOutputStream out = new ByteArrayOutputStream();  
            ObjectOutputStream obs = new ObjectOutputStream(out);  
            obs.writeObject(obj);  
            obs.close();  
              
            //分配内存，写入原始对象，生成新对象  
            ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());  
            ObjectInputStream ois = new ObjectInputStream(ios);
            
            //返回生成的新对象  
            cloneObj = (T) ois.readObject();  
            ois.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return cloneObj;  
    }  
    
}
