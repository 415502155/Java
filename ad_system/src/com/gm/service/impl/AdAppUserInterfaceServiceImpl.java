package com.gm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gm.dao.mysql.BaseDao;
import com.gm.service.AdAppUserInterfaceService;
import com.gm.utils.DataSysConstant;
import com.gm.utils.DateUtil;

@Service
public class AdAppUserInterfaceServiceImpl implements
		AdAppUserInterfaceService {

	@Autowired
	BaseDao dao;
	
	@Override
	public String getUserList(JSONObject jsonObject) throws Exception {
		String startdate =  jsonObject.getString("start_time");
		String enddate = jsonObject.getString("end_time");
		String id="";
		if(jsonObject.containsKey("id")){
			id=jsonObject.getString("id");
		}
		String user_from="";
		if(jsonObject.containsKey("user_from")){
			user_from=jsonObject.getString("user_from");
		}
		String user_app="";
		if(jsonObject.containsKey("user_app")){
			user_app=jsonObject.getString("user_app");
		}
		String mobile="";
		if(jsonObject.containsKey("mobile")){
			mobile=jsonObject.getString("mobile");
		}
		String firm="";
		if(jsonObject.containsKey("firm")){
			firm=jsonObject.getString("firm");
		}
		String sys_version="";
		if(jsonObject.containsKey("sys_version")){
			sys_version=jsonObject.getString("sys_version");
		}
		String oper="";
		if(jsonObject.containsKey("oper")){
			oper=jsonObject.getString("oper");
		}
		String address="";
		if(jsonObject.containsKey("address")){
			address=jsonObject.getString("address");
		}
		String user_app_type="";
		if(jsonObject.containsKey("user_app_type")){
			user_app_type=jsonObject.getString("user_app_type");
		}
		String imei="";
		if(jsonObject.containsKey("imei")){
			imei=jsonObject.getString("imei");
		}
		String imsi="";
		if(jsonObject.containsKey("imsi")){
			imsi=jsonObject.getString("imsi");
		}
		String iccid="";
		if(jsonObject.containsKey("iccid")){
			iccid=jsonObject.getString("iccid");
		}
		String result="";
		StringBuilder sql = new StringBuilder();
		List<Object> objects=new ArrayList<Object>();
		List<Map> rst=new ArrayList<Map>();
		Map<String,Object> returnmap=new HashMap<String,Object>();
		Map<String,String> mapuser=new HashMap<String,String>();
		try {
			startdate =  DateUtil.dayToTimestampStart(DateUtil.cTimestampStr2Date(startdate)).toString();
			enddate = DateUtil.dayToTimestampEnd(DateUtil.cTimestampStr2Date(enddate)).toString();
			long nowtime=System.currentTimeMillis()/1000;
			sql.append(" SELECT id,user_form,user_app,activatetime,activatetime,("+nowtime+"-last_time)/24/60/60 as days,firm,sys_version,"
					+ "oper,mobile,address,ip,imei,imsi,iccid,user_app_list,user_type FROM t_ad_user_open_app_distinct ");
			sql.append(" WHERE last_time BETWEEN '").append(startdate).append("' AND '").append(enddate).append("'");
			if(!id.equals("0") && id!=null && !id.equals("null") && !id.equals("")){
				sql.append(" and id = ").append(id).append(" ");
			}
//			if(!user_from.equals("0") && user_from!=null && !user_from.equals("null") && !user_from.equals("")){
//				sql.append(" and user_from = ").append(user_from).append(" ");
//			}
			if(!user_app.equals("0") && user_app!=null && !user_app.equals("null") && !user_app.equals("")){
				sql.append(" and user_app = ").append(user_app).append(" ");
			}
			if(!mobile.equals("0") && mobile!=null && !mobile.equals("null") && !mobile.equals("")){
				sql.append(" and mobile like '%").append(user_app).append("%' ");
			}
			if(!firm.equals("0") && firm!=null && !firm.equals("null") && !firm.equals("")){
				sql.append(" and firm = '").append(firm).append("' ");
			}
			if(!sys_version.equals("0") && sys_version!=null && !sys_version.equals("null") && !sys_version.equals("")){
				sql.append(" and sys_version = '").append(sys_version).append("' ");
			}
			if(!oper.equals("0") && oper!=null && !oper.equals("null") && !oper.equals("")){
				sql.append(" and oper = ").append(oper).append(" ");
			}
			if(!address.equals("0") && address!=null && !address.equals("null") && !address.equals("")){
				sql.append(" and address = '").append(address).append("' ");
			}
			if(!user_app_type.equals("0") && user_app_type!=null && !user_app_type.equals("null") && !user_app_type.equals("")){
				sql.append(" and user_type like '%,").append(user_app_type).append(",%' ");
			}
			if(!imei.equals("0") && imei!=null && !imei.equals("null") && !imei.equals("")){
				sql.append(" and imei = '").append(imei).append("' ");
			}
			if(!imsi.equals("0") && imsi!=null && !imsi.equals("null") && !imsi.equals("")){
				sql.append(" and imsi = '").append(imsi).append("' ");
			}
			if(!iccid.equals("0") && iccid!=null && !iccid.equals("null") && !iccid.equals("")){
				sql.append(" and iccid = '").append(iccid).append("' ");
			}
			rst = dao.getlisMaps(sql.toString());
			long allactiveuser=0;
			for(Map map : rst){ 
				Map<String,Object> mapobj=new HashMap<String,Object>();
//				user_form,user_app,activatetime,("+nowtime+"-last_time)/24/60/60 as days,firm,sys_version,"
//						+ "oper,mobile,address,ip,imei,imsi,iccid,user_app_list,keyword
				String idstr=map.get("id").toString();
				String user_formstr=map.get("user_form").toString();
				String user_appstr=map.get("user_app").toString();
				double daysdou=Double.parseDouble(map.get("days").toString());
				int days=(int)daysdou;
				String status="活跃";
				if(days<=3){
					status="活跃";
					allactiveuser++;
				}
				else if(days>3 && days<=7){
					status="沉默";
				}else if(days>7){
					status="流失";
				}
				String sys_versionstr=map.get("sys_version").toString();
				String firmstr=map.get("firm").toString();
				int operint=Integer.parseInt(map.get("oper").toString());
				String operStr="中国移动";
				if(operint==1){
					operStr="中国移动";
				}else if(operint==2){
					operStr="中国联通";
				}else if(operint==3){
					operStr="中国电信";
				}else{
					operStr="其他";
				}
				String mobilestr=map.get("mobile").toString();
				String addressstr=map.get("address").toString();
				String ip=map.get("ip").toString();
				String imeistr=map.get("imei").toString();
				String imsistr=map.get("imsi").toString();
				String iccidstr=map.get("iccid").toString();
				String activatetime=map.get("activatetime").toString();
				activatetime=DateUtil.cTimestampStr2String(activatetime, DateUtil.DEFAULT_DATE_FORMAT2);
				mapobj.put("id", idstr);
				mapobj.put("user_form", user_formstr);
				mapobj.put("user_app", user_appstr);
				mapobj.put("status", status);
				mapobj.put("firm", firmstr);
				mapobj.put("sys_version", sys_versionstr);
				mapobj.put("oper", operStr);
				mapobj.put("mobile", mobilestr);
				mapobj.put("address", addressstr);
				mapobj.put("ip", ip);
				mapobj.put("imei", imeistr);
				mapobj.put("imsi", imsistr);
				mapobj.put("iccid", iccidstr);
				mapobj.put("activetime", activatetime);
				objects.add(mapobj);
				mapuser.put(imeistr, imeistr);
			}
			Map<String,Object> allmap=new HashMap<String,Object>();
			allmap.put("alluser", mapuser.size());
			allmap.put("allactiveuser", allactiveuser);
			returnmap.put("list", objects);
			returnmap.put("all", allmap);
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		result=JSONObject.fromObject(returnmap).toString();
		return result;
	}

	@Override
	public String getUserInfo(JSONObject jsonObject) throws Exception {
		String result="";
		String id="";
		if(jsonObject.containsKey("id")){
			id=jsonObject.getString("id");
		}
		StringBuilder sql = new StringBuilder();
		List<Object> objects=new ArrayList<Object>();
		List<Map> rst=new ArrayList<Map>();
		Map<String,Object> mapobj=new HashMap<String,Object>();
		long nowtime=System.currentTimeMillis()/1000;
		sql.append(" SELECT id,user_form,user_app,activatetime,activatetime,("+nowtime+"-last_time)/24/60/60 as days,last_time,firm,sys_version,"
				+ "oper,mobile,address,ip,imei,imsi,iccid,user_app_list,user_type FROM t_ad_user_open_app_distinct ");
		sql.append(" WHERE id = ").append(id);
		try {
			rst = dao.getlisMaps(sql.toString());
			long allactiveuser=0;
			for(Map map : rst){ 
				String idstr=map.get("id").toString();
				String user_formstr=map.get("user_form").toString();
				String user_appstr=map.get("user_app").toString();
				double daysdou=Double.parseDouble(map.get("days").toString());
				int days=(int)daysdou;
				String status="活跃";
				if(days<=3){
					status="活跃";
					allactiveuser++;
				}
				else if(days>3 && days<=7){
					status="沉默";
				}else if(days>7){
					status="流失";
				}
				String sys_versionstr=map.get("sys_version").toString();
				String firmstr=map.get("firm").toString();
				int operint=Integer.parseInt(map.get("oper").toString());
				String operStr="中国移动";
				if(operint==1){
					operStr="中国移动";
				}else if(operint==2){
					operStr="中国联通";
				}else if(operint==3){
					operStr="中国电信";
				}else{
					operStr="其他";
				}
				String mobilestr=map.get("mobile").toString();
				String addressstr=map.get("address").toString();
				String ip=map.get("ip").toString();
				String imeistr=map.get("imei").toString();
				String imsistr=map.get("imsi").toString();
				String iccidstr=map.get("iccid").toString();
				String activatetime=map.get("activatetime").toString();
				activatetime=DateUtil.cTimestampStr2String(activatetime, DateUtil.DEFAULT_DATE_FORMAT2);
				String last_time=map.get("last_time").toString();
				last_time=DateUtil.cTimestampStr2String(last_time, DateUtil.DEFAULT_DATE_FORMAT2);
				String user_app_list=map.get("user_app_list").toString();
				//获取用户属性值
				String user_type=map.get("user_type")+"";
				if(!user_type.equals("") && user_type!=null && !user_type.equals("null")){
					user_type=user_type.substring(1,user_type.length()-1);
				}
				String [] user_type_ids=user_type.split(",");
				String app_group_name="";
				for (String user_type_id : user_type_ids) {
					app_group_name+=DataSysConstant.getAppGroupNameByAppGroupId(user_type_id, dao)+",";
				}
				if(app_group_name.length()>0){
					app_group_name=app_group_name.substring(0,app_group_name.length()-1);
				}
				mapobj.put("id", idstr);
				mapobj.put("user_form", user_formstr);
				mapobj.put("user_app", user_appstr);
				mapobj.put("status", status);
				mapobj.put("firm", firmstr);
				mapobj.put("sys_version", sys_versionstr);
				mapobj.put("oper", operStr);
				mapobj.put("mobile", mobilestr);
				mapobj.put("address", addressstr);
				mapobj.put("ip", ip);
				mapobj.put("imei", imeistr);
				mapobj.put("imsi", imsistr);
				mapobj.put("iccid", iccidstr);
				mapobj.put("activetime", activatetime);
				mapobj.put("last_time", last_time);
				mapobj.put("user_app_list", user_app_list);
				mapobj.put("app_group_name", app_group_name);
				sql.delete(0, sql.length());
				sql.append("SELECT DISTINCT url FROM t_ad_user_inter_url WHERE imei='"+imeistr+"'");
				rst = dao.getlisMaps(sql.toString());
				String user_url_list="";
				for(Map map1 : rst){ 
					user_url_list+=map1.get("url")+",";
				}
				if(user_url_list.length()>1){
					user_url_list=user_url_list.substring(0,user_url_list.length()-1);
				}
				sql.delete(0, sql.length());
				sql.append("SELECT DISTINCT search_term FROM t_ad_user_search_term WHERE imei='"+imeistr+"'");
				rst = dao.getlisMaps(sql.toString());
				String user_search_list="";
				for(Map map1 : rst){ 
					user_search_list+=map1.get("search_term")+",";
				}
				if(user_search_list.length()>1){
					user_search_list=user_search_list.substring(0,user_search_list.length()-1);
				}
				mapobj.put("user_url_list", user_url_list);
				mapobj.put("user_search_list", user_search_list);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		result=JSONObject.fromObject(mapobj).toString();
		return result;
	}

	@Override
	public String getUserUrlRank(JSONObject jsonObject) throws Exception {
		String tablename="";
		if(jsonObject.containsKey("tablename")){
			tablename=jsonObject.getString("tablename");
		}
		String result="";
		Date day=new Date();
		String now=DateUtil.DateToString(day, DateUtil.DATE_FORMAT);
		String trandate=DateUtil.dateAdd(now, -1,DateUtil.DATE_TYPE_DAY);//前1天日期
		StringBuilder sql = new StringBuilder();
		List<Map> rst=new ArrayList<Map>();
		sql.append(" SELECT * FROM "+tablename+" WHERE trandate='"+trandate+"'; ");
		try {
			rst = dao.getlisMaps(sql.toString());
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		result=JSONArray.fromObject(rst).toString();
		return result;
	}

	@Override
	public String getUserSearchRank(JSONObject jsonObject) throws Exception {
		Date day=new Date();
		String now=DateUtil.DateToString(day, DateUtil.DATE_FORMAT);
		String nowbefore1=DateUtil.dateAdd(now, -1,DateUtil.DATE_TYPE_DAY);//前1天日期
		return null;
	}
}
