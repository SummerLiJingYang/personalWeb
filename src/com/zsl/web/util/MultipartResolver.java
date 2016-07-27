package com.zsl.web.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 文件上传
 */
public class MultipartResolver extends CommonsMultipartResolver {  
    
    @Override  
    public boolean isMultipart(HttpServletRequest request) {  
        String dir = request.getParameter("dir");  
        if(dir!=null){  // kindeditor 上传图片的时候 不进行request 的转换  
            return false;  
        }  
        return super.isMultipart(request);  
    }  
  
}