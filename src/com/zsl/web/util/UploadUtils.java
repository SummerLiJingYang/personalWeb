package com.zsl.web.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.zsl.web.util.config.GlobalConfig;


/**
 * 	上传工具类
 * @author liujunqing
 * @version 1.0
 */
public class UploadUtils {
	
	public static final String allowUploadImageType = "jpg,jpge,bmp,gif,png";
	
//	private static String img_domain="";//图片服务器的域名
//	static{
//		Configuration cfg=GlobalConfig.getConfig("common_config");
//		img_domain = cfg.getString("img.domain");
//	}

	public static String bytes2file(HttpServletRequest request, byte[] bytes) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			String dateString = simpleDateFormat.format(new Date());
			File dir = new File(request.getSession().getServletContext()
					.getRealPath("/" )+"upload/"+ dateString);
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					throw new Exception("创建保存目录失败");
				}
			}
			String fileName = UUID.randomUUID().toString() + ".jpg";

			File file = new File(dir, fileName);
			BufferedOutputStream bos = null;
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos);
				bos.write(bytes);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			return "/upload/" + dateString + "/" + fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String saveMartipartFile(HttpServletRequest request,MultipartFile file) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			String dateString = simpleDateFormat.format(new Date());
			File dir = new File(request.getSession().getServletContext()
					.getRealPath("/" )+"upload/"+ dateString);
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					throw new Exception("创建保存目录失败");
				}
			}
			String fileName = UUID.randomUUID().toString() + "."
					+ FilenameUtils.getExtension(file.getOriginalFilename());
			file.transferTo(new File(dir, fileName));
			return "/upload/" + dateString + "/" + fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String saveMartipartFile(HttpServletRequest request,MultipartFile file,String module) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			String dateString = simpleDateFormat.format(new Date());
			File dir = new File(request.getSession().getServletContext()
					.getRealPath("/")+"upload/" + (StringUtils.isNotEmpty(module) == true ? module:dateString));
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					throw new Exception("创建保存目录失败");
				}
			}
			String fileName = UUID.randomUUID().toString() + "."
					+ FilenameUtils.getExtension(file.getOriginalFilename());
			file.transferTo(new File(dir, fileName));
			return "/upload/" + (StringUtils.isNotEmpty(module) == true ? module:dateString) + "/" + fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String saveMartipartFile(HttpServletRequest request,MultipartFile file,String module,String format) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			String dateString = simpleDateFormat.format(new Date());
			File dir = new File(request.getSession().getServletContext()
					.getRealPath("/")+"upload/" + (StringUtils.isNotEmpty(module) == true ? module+"/"+dateString:dateString));
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					throw new Exception("创建保存目录失败");
				}
			}
			String fileName = UUID.randomUUID().toString() + "."
					+ FilenameUtils.getExtension(file.getOriginalFilename());
			file.transferTo(new File(dir, fileName));
			return "/upload/" +(StringUtils.isNotEmpty(module) == true ? module+"/"+dateString:dateString) + "/" + fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//上传文件
	public static String  uploadFile(File sourceFile,String classPath, String fileName) throws IOException {
		String targetFilePath = getTargetFilePath(classPath,fileName,null);
		
		File targetFile = new File(targetFilePath);

		if (!targetFile.exists()) {
			try {
				targetFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//  将临时文件拷贝到目标目录,delete 是否删除临时文件默认为true
		FileUtils.copyFile(sourceFile, targetFile,true);
		return targetFilePath;
	}
	
	
	
	
	
	//上传图片并剪切
	public static void uploadAndCutFile(String classPath,String fileName,String moduleFlag,Integer x,Integer y, Integer w,Integer h){
		
		String sourcePath = getSourceFilePath(classPath,fileName, moduleFlag);
		
		String targetPath = getTargetFilePath(classPath,fileName, moduleFlag);
		
		ImageUtils.abscut(sourcePath, targetPath, x, y, w, h);
		
	}
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 创建目标文件路径
	 * @param classPath
	 * @param fileName
	 * @param clazz
	 * @return
	 */
	public static String getTargetFilePath(String classPath,String fileName, String module) {
		
		// 上传文件后缀名
		String ext = FileUtils.getFileExt(fileName);

		// 上传文件资源类型
		String type = FileUtils.getSourceFileType(ext);
		type = StringUtils.isNotEmpty(type) == true ? type : "UNKWON";

		if (type.equalsIgnoreCase("IMG") || type.equalsIgnoreCase("VID")
				|| type.equalsIgnoreCase("DOC")) {
			//目录路径
			String direct = classPath + File.separator + "upload"
					+ File.separator + type.toLowerCase() + File.separator
					+ module.toLowerCase() + File.separator
					+ DateTimeUtils.getFormatDate(new Date(), "yyyyMMdd") + File.separator ;


			File filedirect = new File(direct);
			//判断是否目录存在,不存在则创建目录
			if (!filedirect.isDirectory()) {
				filedirect.mkdirs();
			}
		
			return direct + UUID.randomUUID() + "." + ext;
		}
		
		return null ;
	}
	/**
	 * 创建源文件路径
	 * @param classPath
	 * @param fileName
	 * @param clazz
	 * @return
	 */
	private static String getSourceFilePath(String classPath ,String fileName, String module) {
		// 上传文件后缀名
		String ext = FileUtils.getFileExt(fileName);

		// 上传文件资源类型
		String type = FileUtils.getSourceFileType(ext);
		type = StringUtils.isNotEmpty(type) == true ? type : "UNKWON";

		if (type.equalsIgnoreCase("IMG") || type.equalsIgnoreCase("VID")
				|| type.equalsIgnoreCase("DOC")) {
			//目录路径
			String direct = classPath + File.separator + "resource"
					+ File.separator + type.toLowerCase() + File.separator
					+ module.toLowerCase() + File.separator
					+ DateTimeUtils.getFormatDate(new Date(), "yyyyMMdd") + File.separator ;

			File filedirect = new File(direct);
			//判断是否目录存在,不存在则创建目录
			if (!filedirect.isDirectory()) {
				filedirect.mkdirs();
			}
		
			return direct + fileName;
		}
		return null ;
	}
	
	/**
	 * 保存到临时文件，用于xml读取
	 */
	public static File saveTempMartipartFile(MultipartFile file) {
		try {
			//写入temp文件
			File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + ".tmp");
			if (!tempFile.getParentFile().exists()) {
				tempFile.getParentFile().mkdirs();
			}
			file.transferTo(tempFile);
			return tempFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 压缩图片至10几k，用于手机显示
	 * @return 压缩成功返回true
	 */
	public static boolean saveZipImage(HttpServletRequest request, String srcImgPath, int outImgLength) {
		try {
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			String outImgPath = FilenameUtils.getFullPath(srcImgPath)+FilenameUtils.getBaseName(srcImgPath)+"_zip.jpg";
			return ImageUtils.zipImage(rootPath+srcImgPath, rootPath+outImgPath,outImgLength);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

	public static Map<String,String> upload(MultipartFile file, String basePath,String module) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			String dateString = simpleDateFormat.format(new Date());
			Map<String,String> map = new HashMap<>();
			File dir = new File(basePath+"/" + (StringUtils.isNotEmpty(module) == true ? module:dateString));
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					throw new Exception("创建保存目录失败");
				}
			}
			String fileName = UUID.randomUUID().toString() + "."
					+ FilenameUtils.getExtension(file.getOriginalFilename());
			file.transferTo(new File(dir, fileName));
			map.put("fileName", file.getOriginalFilename());
			map.put("filePath", "/upload/" + (StringUtils.isNotEmpty(module) == true ? module:dateString) + "/" + fileName);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
