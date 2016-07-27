package com.zsl.web.util.generate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class Generate {
	private final static String fullPath = "F:\\gener_code\\ssh\\";
	private final static String baseDao = "com.framework.authority.dao.BaseDao";
	private final static String baseDaoImpl = "com.framework.authority.dao.impl.BaseDaoImpl";
	private final static String baseService = "com.framework.authority.service.BaseService";
	private final static String baseServiceImpl = "com.framework.authority.service.impl.BaseServiceImpl";
	private final static String baseAction = "com.framework.common.action.base.BaseAdminAction";
	
	public final static String PACKAGE_NAME = "package";
	public final static String PACKAGE_Path = "package_path";
	public final static String GENERATOR_PACKAGE_PATH = "com.framework.";//成功包路径
	public static  void createFile(Map data,String path,String fileName,String templateName) {
		try{
		String filePath = path + fileName;
		Configuration cfg = FreemarkerManager.getConfiguration();//初始化状态
		Template template = cfg.getTemplate(templateName);//获取模板..
		File file = new File(filePath);
		File directory = file.getParentFile();
		if (!directory.exists()) {
			directory.mkdirs();
		}
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
		template.process(data, out);
		out.flush();
		out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 需要设置包名和实体名
	 * @param params
	 * "dao.ftl"
	 */
	public static void  genDao(List<Map> params,String fltPath,boolean isNeedBase) {
		for (int i = 0; i < params.size(); i++) {
			Map<String,String> data = params.get(i);
			if(StringUtils.isEmpty(data.get(Generate.PACKAGE_Path))) {//如果无提供包名.使用实体名
			data.put(Generate.PACKAGE_NAME, GENERATOR_PACKAGE_PATH+data.get("modelName").toLowerCase()+".dao");
			data.put("importModel", GENERATOR_PACKAGE_PATH+data.get("modelName").toLowerCase()+".entity");
			}else {
			data.put(Generate.PACKAGE_NAME, data.get(Generate.PACKAGE_Path)+".dao");
			data.put("importModel", data.get(Generate.PACKAGE_Path)+".entity");
			}
			if(isNeedBase)
			data.put("importBaseDAO", baseDao);
			data.put("model", data.get("modelName"));
			createFile(data, fullPath+"\\"+data.get("modelName").toLowerCase()+"\\dao\\", "I"+data.get("modelName")+"Dao.java", fltPath);
		}
			
	}
	//"daoImpl.ftl"
	public static void  genDaoImpl(List<Map> params,String fltPath,boolean isNeedBase) {
		for (int i = 0; i < params.size(); i++) {
			Map<String,String> data = params.get(i);
			String modelName = data.get("modelName");
			if(StringUtils.isEmpty(data.get(Generate.PACKAGE_Path))) {
			data.put(Generate.PACKAGE_NAME, GENERATOR_PACKAGE_PATH+data.get("modelName").toLowerCase()+".dao.impl");
			data.put("importDao", GENERATOR_PACKAGE_PATH+data.get("modelName").toLowerCase()+".dao");
			data.put("importModel", GENERATOR_PACKAGE_PATH+data.get("modelName").toLowerCase()+".entity");
			}else {
			data.put(Generate.PACKAGE_NAME, data.get(Generate.PACKAGE_Path)+".dao.impl");
			data.put("importDao",  data.get(Generate.PACKAGE_Path)+".dao");
			data.put("importModel", data.get(Generate.PACKAGE_Path)+".entity");
			}
			if(isNeedBase)
			data.put("importAbstractDaoImpl", baseDaoImpl);
			
			data.put("model", modelName);
			String lowerModelName = modelName.toLowerCase();
			data.put("repository", lowerModelName.substring(0, 1)+modelName.substring(1));
			createFile(data, fullPath+"\\"+data.get("modelName").toLowerCase()+"\\dao\\impl\\", modelName+"DaoImpl.java", fltPath);
		}
	}
	//"service.ftl"
	public static void  genService(List<Map> params,String fltPath,boolean isNeedBase) {
		for (int i = 0; i < params.size(); i++) {
			Map<String,String> data = params.get(i);
			String modelName = data.get("modelName");
			if(StringUtils.isEmpty(data.get(Generate.PACKAGE_Path))) {
			data.put(Generate.PACKAGE_NAME, GENERATOR_PACKAGE_PATH+data.get("modelName").toLowerCase()+".service");
			data.put("importModel", GENERATOR_PACKAGE_PATH+data.get("modelName").toLowerCase()+".entity");
			}else {
			data.put(Generate.PACKAGE_NAME, data.get(Generate.PACKAGE_Path)+".service");
			data.put("importModel", data.get(Generate.PACKAGE_Path)+".entity");
			}
			if(isNeedBase)
			data.put("importBaseService", baseService);
			data.put("model", modelName);
			data.put("upperModel", modelName.toUpperCase());
			createFile(data, fullPath+"\\"+data.get("modelName").toLowerCase()+"\\service\\", "I"+modelName+"Service.java", fltPath);
		}
	}
	//"serviceImpl.ftl"
	public static void  genServiceImpl(List<Map> params,String fltPath,boolean isNeedBase) {
		for (int i = 0; i < params.size(); i++) {
			Map<String,String> data = params.get(i);
			String modelName = data.get("modelName");
			if(StringUtils.isEmpty(data.get(Generate.PACKAGE_Path))) {
			data.put(Generate.PACKAGE_NAME, GENERATOR_PACKAGE_PATH+data.get("modelName").toLowerCase()+".service.impl");
			data.put("importDao", GENERATOR_PACKAGE_PATH+data.get("modelName").toLowerCase()+".dao");
			data.put("imorptService", GENERATOR_PACKAGE_PATH+data.get("modelName").toLowerCase()+".service");
			data.put("importModel", GENERATOR_PACKAGE_PATH+data.get("modelName").toLowerCase()+".entity");
			} else {
			data.put(Generate.PACKAGE_NAME, data.get(Generate.PACKAGE_Path)+".service.impl");
			data.put("importDao", data.get(Generate.PACKAGE_Path)+".dao");
			data.put("importService", data.get(Generate.PACKAGE_Path)+".service");
			data.put("importModel", data.get(Generate.PACKAGE_Path)+".entity");
			}
			if(isNeedBase)
			data.put("imorptAbstractServiceImpl", baseServiceImpl);
			data.put("model", modelName);
			String lowerModelName = modelName.toLowerCase();
			data.put("lowerModel", lowerModelName);
			data.put("repository", lowerModelName.substring(0, 1)+modelName.substring(1));
			createFile(data, fullPath+"\\"+data.get("modelName").toLowerCase()+"\\service\\impl\\", modelName+"ServiceImpl.java",fltPath );
		}
	}
	public static void  genController(List<Map> params,String fltPath,boolean isNeedBase) {
		for (int i = 0; i < params.size(); i++) {
			Map<String,String> data = params.get(i);
			String modelName = data.get("modelName");
			if(StringUtils.isEmpty(data.get(Generate.PACKAGE_Path))) {
			data.put(Generate.PACKAGE_NAME, GENERATOR_PACKAGE_PATH+data.get("modelName").toLowerCase()+".controller.admin");
			data.put("imorptService", GENERATOR_PACKAGE_PATH+data.get("modelName").toLowerCase()+".service");
			data.put("importModel", GENERATOR_PACKAGE_PATH+data.get("modelName").toLowerCase()+".entity");
			} else {
			data.put(Generate.PACKAGE_NAME, data.get(Generate.PACKAGE_Path)+".controller.admin");
			data.put("importService", data.get(Generate.PACKAGE_Path)+".service");
			data.put("importModel", data.get(Generate.PACKAGE_Path)+".entity");
			}
			if(isNeedBase)
			data.put("model", modelName);
			String lowerModelName = modelName.toLowerCase();
			data.put("lowerModel", lowerModelName);
			data.put("repository", lowerModelName.substring(0, 1)+modelName.substring(1));
			createFile(data, fullPath+"\\"+data.get("modelName").toLowerCase()+"\\controller\\admin\\", modelName+"Controller.java",fltPath );
		}
	}
	public static void genAction(List<Map> params) {
		for (int i = 0; i < params.size(); i++) {
			Map<String,String> data = params.get(i);
			String modelName = data.get("modelName");
			if(StringUtils.isEmpty(data.get(Generate.PACKAGE_Path))) {
			data.put(Generate.PACKAGE_NAME, GENERATOR_PACKAGE_PATH+data.get("modelName").toLowerCase()+".action");
			data.put("importModel", GENERATOR_PACKAGE_PATH+data.get("modelName").toLowerCase()+".entity");
			data.put("imorptService", GENERATOR_PACKAGE_PATH+data.get("modelName").toLowerCase()+".service");
			} else {
			data.put(Generate.PACKAGE_NAME, data.get(Generate.PACKAGE_Path)+".action");
			data.put("importModel", data.get(Generate.PACKAGE_Path)+".entity");
			data.put("importService", data.get(Generate.PACKAGE_Path)+".service");
			}
			data.put("imorptBaseAction", baseAction);
			data.put("model", modelName);
			String lowerModelName = firstLetterLower(modelName);
			String upperModelName = modelName.toUpperCase();
			data.put("lowerModel", lowerModelName);
			data.put("upperModel", upperModelName);
			createFile(data, fullPath+"\\"+data.get("modelName").toLowerCase()+"\\action\\", modelName+"Action.java", "action.ftl");
		}
	}
	public static String firstLetterUpper(String str) {
		if(str==null) return null;
		char [] charArray = str.toCharArray();
		if(charArray.length>0) {
			charArray[0] = Character.toUpperCase(charArray[0]);
		}
		return new String(charArray);
	}
	public static String firstLetterLower(String str) {
		if(str==null) return null;
		char [] charArray = str.toCharArray();
		if(charArray.length>0) {
			charArray[0] = Character.toLowerCase(charArray[0]);
			if(charArray.length>1) {
				charArray[1] = Character.toLowerCase(charArray[1]);
			}
		}
		return new String(charArray);
	}
	}



