package com.zsl.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * Utils - Web相关的操作类
 * @author liujunqing
 */
public final class WebUtils {
	/**
	 * cookie 路径
	 */
	private final  static String COOKIE_PATH = "/";
	/**
	 * cookie 域名
	 */
	private final  static String COOKIE_DOMAIN = "";//"zhaosuliao.com";

	/**
	 * 不可实例化
	 */
	private WebUtils() {
	}

	/**
	 * 添加cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            有效期(单位: 秒)
	 * @param path
	 *            路径
	 * @param domain
	 *            域
	 * @param secure
	 *            是否启用加密
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge, String path, String domain, Boolean secure) {
		Assert.notNull(request);
		Assert.notNull(response);
		Assert.hasText(name);
		try {
			name = URLEncoder.encode(name, "UTF-8");
			value = URLEncoder.encode(value, "UTF-8");
			Cookie cookie = new Cookie(name, value);
			if (maxAge != null) {
				cookie.setMaxAge(maxAge);
			}
			if (StringUtils.isNotEmpty(path)) {
				cookie.setPath(path);
			}
			if (StringUtils.isNotEmpty(domain)) {
				cookie.setDomain(domain);
			}
			if (secure != null) {
				cookie.setSecure(secure);
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            有效期(单位: 秒)
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge) {
		addCookie(request, response, name, value, maxAge, COOKIE_PATH, COOKIE_DOMAIN, null);
	}

	/**
	 * 添加cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
		addCookie(request, response, name, value, null, COOKIE_PATH, COOKIE_DOMAIN, null);
	}

	/**
	 * 获取cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param name
	 *            cookie名称
	 * @return 若不存在则返回null
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		Assert.notNull(request);
		Assert.hasText(name);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			try {
				name = URLEncoder.encode(name, "UTF-8");
				for (Cookie cookie : cookies) {
					if (name.equals(cookie.getName())) {
						return URLDecoder.decode(cookie.getValue(), "UTF-8");
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 移除cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param path
	 *            路径
	 * @param domain
	 *            域
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name, String path, String domain) {
		Assert.notNull(request);
		Assert.notNull(response);
		Assert.hasText(name);
		try {
			name = URLEncoder.encode(name, "UTF-8");
			Cookie cookie = new Cookie(name, null);
			cookie.setMaxAge(0);
			if (StringUtils.isNotEmpty(path)) {
				cookie.setPath(path);
			}
			if (StringUtils.isNotEmpty(domain)) {
				cookie.setDomain(domain);
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移除cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		removeCookie(request, response, name, COOKIE_PATH, COOKIE_DOMAIN);
	}

	/**
	 * 获取参数
	 * 
	 * @param queryString
	 *            查询字符串
	 * @param encoding
	 *            编码格式
	 * @param name
	 *            参数名称
	 * @return 参数
	 */
	public static String getParameter(String queryString, String encoding, String name) {
		String[] parameterValues = getParameterMap(queryString, encoding).get(name);
		return parameterValues != null && parameterValues.length > 0 ? parameterValues[0] : null;
	}

	/**
	 * 获取参数
	 * 
	 * @param queryString
	 *            查询字符串
	 * @param encoding
	 *            编码格式
	 * @param name
	 *            参数名称
	 * @return 参数
	 */
	public static String[] getParameterValues(String queryString, String encoding, String name) {
		return getParameterMap(queryString, encoding).get(name);
	}

	/**
	 * 获取参数
	 * 
	 * @param queryString
	 *            查询字符串
	 * @param encoding
	 *            编码格式
	 * @return 参数
	 */
	public static Map<String, String[]> getParameterMap(String queryString, String encoding) {
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();
		Charset charset = Charset.forName(encoding);
		if (StringUtils.isNotEmpty(queryString)) {
			byte[] bytes = queryString.getBytes(charset);
			if (bytes != null && bytes.length > 0) {
				int ix = 0;
				int ox = 0;
				String key = null;
				String value = null;
				while (ix < bytes.length) {
					byte c = bytes[ix++];
					switch ((char) c) {
					case '&':
						value = new String(bytes, 0, ox, charset);
						if (key != null) {
							putMapEntry(parameterMap, key, value);
							key = null;
						}
						ox = 0;
						break;
					case '=':
						if (key == null) {
							key = new String(bytes, 0, ox, charset);
							ox = 0;
						} else {
							bytes[ox++] = c;
						}
						break;
					case '+':
						bytes[ox++] = (byte) ' ';
						break;
					case '%':
						bytes[ox++] = (byte) ((convertHexDigit(bytes[ix++]) << 4) + convertHexDigit(bytes[ix++]));
						break;
					default:
						bytes[ox++] = c;
					}
				}
				if (key != null) {
					value = new String(bytes, 0, ox, charset);
					putMapEntry(parameterMap, key, value);
				}
			}
		}
		return parameterMap;
	}

	private static void putMapEntry(Map<String, String[]> map, String name, String value) {
		String[] newValues = null;
		String[] oldValues = map.get(name);
		if (oldValues == null) {
			newValues = new String[] { value };
		} else {
			newValues = new String[oldValues.length + 1];
			System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
			newValues[oldValues.length] = value;
		}
		map.put(name, newValues);
	}

	private static byte convertHexDigit(byte b) {
		if ((b >= '0') && (b <= '9')) {
			return (byte) (b - '0');
		}
		if ((b >= 'a') && (b <= 'f')) {
			return (byte) (b - 'a' + 10);
		}
		if ((b >= 'A') && (b <= 'F')) {
			return (byte) (b - 'A' + 10);
		}
		throw new IllegalArgumentException();
	}
		  
	     
     //将127.0.0.1形式的IP地址转换成十进制整数，这里没有进行任何错误处理
     public static long ipToLong(String strIp){
    	 long[] ip = new long[4];
    	 try{
	         //先找到IP地址字符串中.的位置
	         int position1 = strIp.indexOf(".");
	         if(position1<0){
	        	 strIp="127.0.0.1";
	        	 position1 = strIp.indexOf(".");
	         }
	         int position2 = strIp.indexOf(".", position1 + 1);
	         int position3 = strIp.indexOf(".", position2 + 1);
	         //将每个.之间的字符串转换成整型
	         ip[0] = Long.parseLong(strIp.substring(0, position1));
	         ip[1] = Long.parseLong(strIp.substring(position1+1, position2));
	         ip[2] = Long.parseLong(strIp.substring(position2+1, position3));
	         ip[3] = Long.parseLong(strIp.substring(position3+1));
         }catch(Exception e){
        	 e.printStackTrace();
        	 return 0L;
         }
         return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
     }
     
     //将十进制整数形式转换成127.0.0.1形式的ip地址
     public static String longToIP(long longIp){
         StringBuffer sb = new StringBuffer("");
         //直接右移24位
         sb.append(String.valueOf((longIp >>> 24)));
         sb.append(".");
         //将高8位置0，然后右移16位
         sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
         sb.append(".");
         //将高16位置0，然后右移8位
         sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
         sb.append(".");
         //将高24位置0
         sb.append(String.valueOf((longIp & 0x000000FF)));
         return sb.toString();
     }

     /**
 	 * 获得用户远程地址
 	 */
 	public static String getRemoteAddr(HttpServletRequest request){
 		String remoteAddr = request.getHeader("X-Real-IP");
         if (StringUtils.isNotBlank(remoteAddr)) {
         	remoteAddr = request.getHeader("X-Forwarded-For");
         }else if (StringUtils.isNotBlank(remoteAddr)) {
         	remoteAddr = request.getHeader("Proxy-Client-IP");
         }else if (StringUtils.isNotBlank(remoteAddr)) {
         	remoteAddr = request.getHeader("WL-Proxy-Client-IP");
         }
         return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
 	}

 	/** 
     * 主动推送到百度收录
     * @param postUrl 
     * @param Parameters 
     * @return 
     */  
 	public static String BaiDuPost(List<String> urlList) {
 		String postUrl = "http://data.zz.baidu.com/urls?site=www.zhaosuliao.com&token=R5KCgv45blJK1l7J&type=original";//网站的服务器连接  
 		return WebUtils.BaiDuPost(postUrl, urlList);
 	}
 	 /** 
     * 主动推送到百度收录
     * @param postUrl 
     * @param Parameters 
     * @return 
     */  
    public static String BaiDuPost(String postUrl, List<String> urlList){  
        if(null == postUrl || null == urlList || urlList.size() ==0) {  
            return null;  
        }  
        String result="";  
        PrintWriter out=null;  
        BufferedReader in=null;  
        try {  
            //建立URL之间的连接  
        	HttpURLConnection conn=(HttpURLConnection) new URL(postUrl).openConnection();  
            //设置通用的请求属性  
            conn.setRequestProperty("Host","data.zz.baidu.com");  
            conn.setRequestProperty("User-Agent", "curl/7.12.1");  
            conn.setRequestProperty("Content-Length", "83");  
            conn.setRequestProperty("Content-Type", "text/plain");  

            //发送POST请求必须设置如下两行  
            conn.setDoInput(true);  
            conn.setDoOutput(true);  
               
            //获取conn对应的输出流  
            out=new PrintWriter(conn.getOutputStream());  
            //发送请求参数  
            String param = "";  
            for (String s : urlList) {
            	param += s+"\n";
			}
            out.print(param.trim());  
            //进行输出流的缓冲  
            out.flush();  
            //通过BufferedReader输入流来读取Url的响应  
            in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;  
            while((line=in.readLine())!= null){  
                result += line;  
            }  
               
        } catch (Exception e) {  
            System.out.println("发送post请求出现异常！"+e);  
            e.printStackTrace();  
        } finally{  
            try{  
                if(out != null){  
                    out.close();  
                }  
                if(in!= null){  
                    in.close();  
                }  
                   
            }catch(IOException ex){  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
}