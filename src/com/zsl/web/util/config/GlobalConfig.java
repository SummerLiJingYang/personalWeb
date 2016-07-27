
package com.zsl.web.util.config;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;

/**
 * 全局配置管理类,负责所有配置的载入 
 * 增加配置举例:
 *   1. 在global_config.xml文件中增加配置文件的设置,根据需要可以设置xml和properties文件配置类型,暂未提供ini的配置类型支持
 *    例如: <xml config-name='ivpcore_config' fileName="/WEB-INF/config/mysql/ivpcore.cfg.xml" throwExceptionOnMissing="true" config-optional="true"/>
 *   2. 在程序中,根据配置名称(config-name)即可获取到对应的配置实例
 *    Configuration config = GlobalConfig.getConfig("ivpcore_config");
 * 配置使用实例请参阅main方法.   
 * ps.: 1.使用该配置管理类可以方便转换配置文件的类型,实现配置的统一管理,建议将原来使用static类型的常量改为该种方式进行实现,以避免造成更新常量需要更新大量的调用类的情况.     
 *      2.目前的该配置类的灵活性有待提高,支持的配置类型也有待扩充.
 *      3.自动刷新properties类型支持两种方式设置,即在global_config.xml中设置或获取配置实例时,使用GlobalConfig.getConfig(config-name,deloadDelay)方法;
 *        xml类型只支持后一种方法.
 *        例如:  FileConfiguration config = GlobalConfig.getConfig("ivpcore_config",1000);
 */
public class GlobalConfig {
	
	//Logger for this class	
	public static final Logger logger = Logger.getLogger(GlobalConfig.class);
	
	//存放配置对象列表
	private static CombinedConfiguration configs = null;
	
	/**
	 * 初始化配置管理类,从指定的配置文件中读取需要载入的配置
	 * @param fileName
	 */
	public static void init(String basePath,String filePath) throws Exception{
		logger.info("init-global-config");
		try {
			DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder();
			builder.setBasePath(basePath);			
			builder.setFileName(filePath);				
			configs = builder.getConfiguration(true);			
			if(configs!=null){
				logger.info("common_config:"+configs.getConfiguration("common_config"));
			}

		} catch (ConfigurationException e) {			
			throw e;
		}	
		logger.info(" -- init-global-config finish");
		
	}	
	
	/**
	 * 根据配置名称获取相应的配置实例(如配置文件中未设置自动刷新,则不支持自动刷新)
	 * @param configName
	 * @return
	 */
	public static FileConfiguration getConfig(String configName){
		if(configs==null){
			configs= new CombinedConfiguration();
		}
		if(configName==null||configName.trim().length()==0) {
			return (FileConfiguration)configs;
		}
		return (FileConfiguration)configs.getConfiguration(configName);
	}

	/**
	 * 根据配置名称获取相应的配置实例,并设置自动刷新的时间
	 * @param configName
	 * @param reloadDelay 自动刷新时间,以毫秒为单位
	 * @return
	 */
	public static FileConfiguration getConfig(String configName,long reloadDelay){
		FileConfiguration config = getConfig(configName);
		if(config!=null){
			FileChangedReloadingStrategy fcrs = new FileChangedReloadingStrategy();
			if(reloadDelay>0) {
				fcrs.setRefreshDelay(reloadDelay);
			}
			config.setReloadingStrategy(fcrs);
		}
		return config;

	}

	/**
	 * 测试类
	 * @param args
	 */
	//public static void main(String[] args) {
	//	try {
			//初始化配置管理类,读取指定的全局配置文件内容
			//GlobalConfig.init("E:/MyWork/Source/Eclipse/WorkSpace3.2/ivpeim/webapp","/WEB-INF/config/global/global.cfg.xml");
			
			//获取配置名称为ivpcore_config的xml文件类型的配置对象(实际的配置文件对应为/WEB-INF/config/mysql/ivpcore.cfg.xml),
			//并设置自动刷新时间为1秒,即ivpcore.cfg.xml文件更新1秒钟后,程序中的xmlConfig对象中的相应配置将会更新.
//			FileConfiguration xmlConfig = getConfig("ivpcore_config",1000);

			
			//获取配置名称为log4j_config的properties文件类型的配置对象(实际的配置文件对应为/WEB-INF/config/log4j/log4j.properties),
			//由于在配置文件global.cfg.xml中,该配置设置了<reloadingStrategy refreshDelay="600000"> 的配置策略,故log4j.properties配置更新10分钟后,
			//程序中的propertiesConfig对象会自动更新.
//			Configuration propertiesConfig = getConfig("log4j_config");
			
//			long start = new Date().getTime();
//			long end = new Date().getTime();
//			
//			//在10秒钟内不停循环输出配置信息,用于观察配置文件是否会自动更新
//			while(end-start<10000){
//				end = new Date().getTime();
//				
//				//输出xml配置对象中,节点database下path节点的值
//				System.out.println("Test1:"+xmlConfig.getString("database.path"));
//				
//				//输出properties配置对象中,log4j.rootCategory节点的值
//				System.out.println("Test2:"+propertiesConfig.getString("log4j.rootCategory"));
//			}	

			
//			//保存xmlConfig配置
//			xmlConfig.setProperty("database.path", xmlConfig.getString("database.path")+"test");
//			try {
//				xmlConfig.save();
//			} catch (ConfigurationException e) {
//				logger.error("main-GlobalConfig", e);
//			}
//			//输出xml配置对象中,节点database下path节点的值
//			System.out.println("Test3:"+xmlConfig.getString("database.path"));	
			
			
//				Configuration constConfig = getConfig("const_config");
//				System.out.println("Test4:"+constConfig.getString("domain_name"));
			

	//	} catch (CippeException e) {
	//		e.printStackTrace();
	//	}		
	//}
	
}
