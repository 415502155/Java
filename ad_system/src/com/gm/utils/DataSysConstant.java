package com.gm.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.gm.dao.mysql.BaseDao;
import com.gm.dao.mysql.impl.BaseDaoImpl;


/**
 * 系统配置文件的缓存
 * @author fyr
 *
 */
public class DataSysConstant { 
	
	/**
	 * 区分是单服还是全服，传进来的是999 就是全服  ，其他是单服
	 */
	public final static String SIDS="9999";
	

	/**
	 * 全局全服，目前只用于 日报，插件，留存率
	 */
	public final static String Global_Sid="100000";
	
	/**
	 * 配置游戏id
	 */
	public final static String  GLOBLE_GAME_ID = getConfigResource("GLOBLE_GAME_ID");
	
	
	/**
	 * 配置游戏id
	 */
	public final static String  LOG_GAME_ID = getConfigResource("LOG_GAME_ID");
	/**
	 * 前端地址,用于添加游戏区服连接，前端数据库也添加区服
	 */
	public final static String  WEB_GM = getConfigResource("WEB_GM");
	
	/**
	 * 日志发送地址
	 */
	public final static String  HTTP_POST_LOG = getConfigResource("HTTP_POST_LOG");
	
	/**
	 * GAME_Key和游戏定义的MD5加密的key
	 */
	public final static String  GAME_Key = getConfigResource("GAME_Key");
	
	/**
	 * 插件首包资源下载步骤
	 */
	public final static String  WATCH_STEP_PG = getConfigResource("WATCH_STEP_PG");
	
	
	/**
	 * 插件首包资源下载步骤下一步
	 */
	public final static String  WATCH_STEP_NEXT_PG = getConfigResource("WATCH_STEP_NEXT_PG");
	/**
	 * 提供给游戏管理系统的接口op字符串,用逗号分隔多个op
	 */
	private static Properties sysProperties = null;//sys.properties 属性文件全局静态变量

	//初始加载
	public static void loadSysProperties(){
		try {
			if(new File("../classes/sys.properties").isFile()){
				FileInputStream fis = new FileInputStream("../classes/sys.properties");
				sysProperties = new Properties();
				sysProperties.load(fis);
				fis.close();
			}else {
				InputStream inCfg = DataSysConstant.class.getClassLoader().getResourceAsStream("sys.properties");
				sysProperties = new Properties();
				sysProperties.load(inCfg);
				inCfg.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/** 返回给定在"sys"属性文件中KEY对应的值 */
	public static String getConfigResource(String keyName) {
		
		if(sysProperties == null){
			loadSysProperties();
		}
		String value = (String) sysProperties.getProperty(keyName);
		return value;
	}
	
	//应用包名对应的id列表
	private static Map<String, String> APP_PACKAGE_APP_ID_MAP=null;
	
	/**
	 * 初始化应用包名对应id列表
	 * @return
	 * @throws Exception
	 */
	public static  Map<String,String> getAppPackageAppIDMap(BaseDao dao) throws Exception {
		APP_PACKAGE_APP_ID_MAP = new HashMap<String,String>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT id,app_package,app_form FROM t_ad_apply ");
		List<Map> rst=new ArrayList<Map>();
		try {
			rst = dao.getlisMaps(sql.toString());
			for(Map map : rst){ 
				String id = map.get("id")+"";
				String app_package = map.get("app_package")+"";
				APP_PACKAGE_APP_ID_MAP.put(app_package, id);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return APP_PACKAGE_APP_ID_MAP;
	}
	
	/**
	 * 根据应用包名获取应用id
	 * @param packagename
	 * @param dao
	 * @return
	 * @throws Exception
	 */
	public static String getAppIDByAppPackage(String packagename,BaseDao dao) throws Exception{
		String id="";
		if(APP_PACKAGE_APP_ID_MAP==null){
			APP_PACKAGE_APP_ID_MAP=getAppPackageAppIDMap(dao);
			id=APP_PACKAGE_APP_ID_MAP.get(packagename);
		}else{
			if(APP_PACKAGE_APP_ID_MAP.containsKey(packagename)){
				id=APP_PACKAGE_APP_ID_MAP.get(packagename);
			}else{
				APP_PACKAGE_APP_ID_MAP = new HashMap<String,String>();
				APP_PACKAGE_APP_ID_MAP=getAppPackageAppIDMap(dao);
				if(APP_PACKAGE_APP_ID_MAP.containsKey(packagename)){
					id=APP_PACKAGE_APP_ID_MAP.get(packagename);
				}
			}
		}
		return id;
	}
	
	//应用包名对应的应用来源id列表
	private static Map<String, String> APP_PACKAGE_APP_FROM_MAP=null;
	/**
	 * 初始化应用包名对应id列表
	 * @return
	 * @throws Exception
	 */
	public static  Map<String,String> getAppPackageAppFormMap(BaseDao dao) throws Exception {
		APP_PACKAGE_APP_FROM_MAP = new HashMap<String,String>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT id,app_form,app_package FROM t_ad_apply ");
		List<Map> rst=new ArrayList<Map>();
		try {
			rst = dao.getlisMaps(sql.toString());
			for(Map map : rst){ 
				String id = map.get("id")+"";
				String app_package = map.get("app_package")+"";
				String app_form = map.get("app_form")+"";
				APP_PACKAGE_APP_FROM_MAP.put(app_package, app_form);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return APP_PACKAGE_APP_FROM_MAP;
	}
	
	/**
	 * 根据应用包名获取应用id
	 * @param packagename
	 * @param dao
	 * @return
	 * @throws Exception
	 */
	public static String getAppFormByAppPackage(String packagename,BaseDao dao) throws Exception{
		String app_form="";
		if(APP_PACKAGE_APP_FROM_MAP==null){
			APP_PACKAGE_APP_FROM_MAP=getAppPackageAppFormMap(dao);
			app_form=APP_PACKAGE_APP_FROM_MAP.get(packagename);
		}else{
			if(APP_PACKAGE_APP_FROM_MAP.containsKey(packagename)){
				app_form=APP_PACKAGE_APP_FROM_MAP.get(packagename);
			}else{
				APP_PACKAGE_APP_FROM_MAP = new HashMap<String,String>();
				APP_PACKAGE_APP_FROM_MAP=getAppPackageAppFormMap(dao);
				if(APP_PACKAGE_APP_FROM_MAP.containsKey(packagename)){
					app_form=APP_PACKAGE_APP_FROM_MAP.get(packagename);
				}
			}
		}
		return app_form;
	}
	
	/**
	 * 获取应用分类列表信息，比对用户属于的用户类型
	 * @param user_app_list
	 * @param dao
	 * @return
	 * @throws Exception
	 */
	public static String getUserTypeByUserAppList(String user_app_list,BaseDao dao) throws Exception{
		String user_type=",";
		String[] appnames=user_app_list.split(",");
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT id,app_group_name,rulenum,app_list_id FROM t_ad_app_group ");
		List<Map> rst=new ArrayList<Map>();
		try {
			rst = dao.getlisMaps(sql.toString());
			for(Map map : rst){ 
				String id = map.get("id")+"";
				String app_group_name = map.get("app_group_name")+"";
				int rulenum = Integer.parseInt(map.get("rulenum")+"");
				String app_list_id = map.get("app_list_id")+"";
				JSONObject jsonObject=JSONObject.fromObject(app_list_id);
				Iterator it = jsonObject.keys();  
				int index=0;
		        while(it.hasNext()){  
		        	String key = (String) it.next();
		            String value=jsonObject.getString(key);
		            for (String appname : appnames) {
		            	if(value.indexOf(appname)!=-1){
		            		index++;
			            }
					}
		            
		        }
		        if(index>=rulenum){
		        	user_type+=id+",";
		        }
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		return user_type;
	}
	
	//应用id对应应用名称列表
	private static Map<String, String> APP_ID_APP_GROUP_NAME_MAP=null;
	
	/**
	 * 初始化应用包名对应id列表
	 * @return
	 * @throws Exception
	 */
	public static  Map<String,String> getAppIdAppGroupNameMap(BaseDao dao) throws Exception {
		APP_ID_APP_GROUP_NAME_MAP = new HashMap<String,String>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT id,app_group_name FROM t_ad_app_group ");
		List<Map> rst=new ArrayList<Map>();
		try {
			rst = dao.getlisMaps(sql.toString());
			for(Map map : rst){ 
				String id = map.get("id")+"";
				String app_group_name = map.get("app_group_name")+"";
				APP_ID_APP_GROUP_NAME_MAP.put(id, app_group_name);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return APP_ID_APP_GROUP_NAME_MAP;
	}
	
	/**
	 * 根据应用包名获取应用id
	 * @param packagename
	 * @param dao
	 * @return
	 * @throws Exception
	 */
	public static String getAppGroupNameByAppGroupId(String appgroupid,BaseDao dao) throws Exception{
		String app_group_name="";
		if(APP_ID_APP_GROUP_NAME_MAP==null){
			APP_ID_APP_GROUP_NAME_MAP=getAppIdAppGroupNameMap(dao);
			app_group_name=APP_ID_APP_GROUP_NAME_MAP.get(appgroupid);
		}else{
			if(APP_ID_APP_GROUP_NAME_MAP.containsKey(appgroupid)){
				app_group_name=APP_ID_APP_GROUP_NAME_MAP.get(appgroupid);
			}else{
				APP_ID_APP_GROUP_NAME_MAP = new HashMap<String,String>();
				APP_ID_APP_GROUP_NAME_MAP=getAppIdAppGroupNameMap(dao);
				if(APP_ID_APP_GROUP_NAME_MAP.containsKey(appgroupid)){
					app_group_name=APP_ID_APP_GROUP_NAME_MAP.get(appgroupid);
				}
			}
		}
		return app_group_name;
	}
	
	
}
