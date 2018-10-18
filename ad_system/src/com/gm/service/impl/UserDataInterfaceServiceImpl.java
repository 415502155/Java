package com.gm.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gm.dao.mysql.BaseDao;
import com.gm.service.UserDataInterfaceService;
import com.gm.utils.AddressUtils;
import com.gm.utils.DataSysConstant;
import com.gm.utils.MobileProvinceUtil;
import com.gm.utils.project.ProjectComment;

@Service
public class UserDataInterfaceServiceImpl implements UserDataInterfaceService {

	@Autowired
	BaseDao dao;

	@Override
	public String InsertAndUpdateUserInfo(JSONObject jsonObject)
			throws Exception {
		String reString="";
		int result=-2;
		String packagename=jsonObject.getString("user_app_package");
		String user_app=DataSysConstant.getAppIDByAppPackage(packagename, dao);
		String user_form=DataSysConstant.getAppFormByAppPackage(packagename, dao);
		long activetime=System.currentTimeMillis()/1000;
		String sys_version=jsonObject.getString("sys_version");
		String firm=ProjectComment.getFirm(sys_version);
		int operint=jsonObject.getInt("oper");
//		String operStr="中国移动";
//		if(operint==1){
//			operStr="中国移动";
//		}else if(operint==2){
//			operStr="中国联通";
//		}else if(operint==3){
//			operStr="中国电信";
//		}else{
//			operStr="其他";
//		}
		
		String mobile=jsonObject.getString("mobile");
		String province="未知";
		if(!mobile.equals("")){
			province=MobileProvinceUtil.request(mobile);
		}
		String ip=jsonObject.getString("ip");
		if(!ip.equals("")){
			province=AddressUtils.getAddresses(ip);
		}
		String imei=jsonObject.getString("imei");
		String imsi=jsonObject.getString("imsi");
		String iccid=jsonObject.getString("iccid");
		String user_app_list=jsonObject.getString("user_app_list");
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_ad_user_open_app (user_form,user_app,activatetime,firm,sys_version,oper,mobile,address,ip,imei,imsi,iccid,user_app_list) value (");
		sql.append("'"+user_form+"',");
		sql.append("'"+user_app+"',");
		sql.append("'"+activetime+"',");
		sql.append("'"+firm+"',");
		sql.append("'"+sys_version+"',");
		sql.append("'"+operint+"',");
		sql.append("'"+mobile+"',");
		sql.append("'"+province+"',");
		sql.append("'"+ip+"',");
		sql.append("'"+imei+"',");
		sql.append("'"+imsi+"',");
		sql.append("'"+iccid+"',");
		sql.append("'"+user_app_list+"'");
		sql.append(")");
		result=dao.excuteSQL(sql.toString());
		if(result!=-1){
			String user_type=DataSysConstant.getUserTypeByUserAppList(user_app_list, dao);
			
			sql.delete(0, sql.length());
			sql.append("UPDATE t_ad_user_open_app_distinct SET user_form='"+user_form+"',last_time='"+activetime+"',firm='"+firm+
					"',sys_version='"+sys_version+"',oper='"+operint+"',mobile='"+mobile+"',address='"+province+"',ip='"+ip+"',imei='"+imei+
					"',imsi='"+imsi+"',iccid='"+iccid+"',user_app_list='"+user_app_list+"',user_type='"+user_type+"' WHERE user_app="+user_app+" AND imei='"+imei+"'");
			dao.excuteSQL(sql.toString());
		}
		return reString;
	}

	@Override
	public String InsertUserUrl(JSONObject jsonObject) throws Exception {
		String reString="";
		long time=System.currentTimeMillis()/1000;
		String url=jsonObject.getString("url");
		Pattern p = Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE); 
		Matcher matcher = p.matcher(url);
		matcher.find();
		url=matcher.group();
		String imei=jsonObject.getString("imei");
		String imsi=jsonObject.getString("imsi");
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO t_ad_user_inter_url (url,imei,imsi,`time`) VALUE (");
		sql.append("'"+url+"',");
		sql.append("'"+imei+"',");
		sql.append("'"+imsi+"',");
		sql.append("'"+time+"'");
		sql.append(")");
		dao.excuteSQL(sql.toString());
		return reString;
	}

	@Override
	public String InsertUserSearch(JSONObject jsonObject) throws Exception {
		String reString="";
		long time=System.currentTimeMillis()/1000;
		String search_term=jsonObject.getString("search");
		String imei=jsonObject.getString("imei");
		String imsi=jsonObject.getString("imsi");
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO t_ad_user_search_term (search_term,imei,imsi,`time`) VALUE (");
		sql.append("'"+search_term+"',");
		sql.append("'"+imei+"',");
		sql.append("'"+imsi+"',");
		sql.append("'"+time+"'");
		sql.append(")");
		dao.excuteSQL(sql.toString());
		return reString;
	}
	
}
