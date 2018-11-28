package sng.controller.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import sng.pojo.base.OrgUser;
import sng.pojo.base.Organization;
import sng.pojo.base.Parent;
import sng.pojo.base.Role;
import sng.pojo.base.Teacher;
import sng.exception.EnumException;
import sng.exception.EsbException;
import sng.pojo.SessionInfo;
import sng.pojo.TechRange;
import sng.util.CookieTool;
import sng.util.ESBPropertyReader;
import sng.util.HttpClientUtil;
import sng.util.JsonUtil;
import sng.util.RedisUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseAction {

	protected static String ESB_REQUEST_URL = ESBPropertyReader.getProperty("esbRequestUrl");
	
	protected static String ESB_API_REQUEST_URL = ESBPropertyReader.getProperty("esbApiRequestUrl");
	
	protected static String WX_PROJECT_URL = ESBPropertyReader.getProperty("WXProjectUrl");

	private static String EDUWX_REQUEST_URL = ESBPropertyReader.getProperty("eduWeixinRequestUrl");

	private String token = ESBPropertyReader.getProperty("token");

	private String udid = ESBPropertyReader.getProperty("udid");

	private String userjson = ESBPropertyReader.getProperty("userjson");

	private String orgjson = ESBPropertyReader.getProperty("orgjson");
	

	
	@Autowired
	RedisUtil redisUtil;
	
	/***
	 * 获取token
	 * **/
	/*
	 * protected String getToken(HttpServletRequest request) { String tokenstr = CookieTool.getCookieValue(request, token); return
	 * tokenstr; }
	 *//***
	 * 获取udid
	 * **/
	/*
	 * protected String getUDID(HttpServletRequest request) { String udidstr = CookieTool.getCookieValue(request, udid); return
	 * udidstr; }
	 */

	/***
	 * 获取机构信息列表
	 * **/
	protected Map<String, Object> getOrgMap(HttpServletRequest request) throws Exception {
		Map<String, Object> usermap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			String tokenValue = CookieTool.getCookieValue(request, token, true);
			String udidValue = CookieTool.getCookieValue(request, udid, true);
			params.put("token", tokenValue);
			params.put("udid", udidValue);
			// TODO
			String user_id = null;
			params.put("user_id", user_id);
			params.put("version", 0);	
			String documentResult = HttpClientUtil.post(ESB_API_REQUEST_URL+"getorgusers", params);
			ObjectMapper objectMapper = JsonUtil.getMapperInstance();
			JsonNode jsonNode = objectMapper.readTree(documentResult);
			JsonNode dataNode = jsonNode.get("data");
			for (int i = 0; i < dataNode.size(); i++) {
				JsonNode orgUserNode = dataNode.get(i);
				String organizationString = orgUserNode.get("organization").toString();
				Organization org = JsonUtil.getObjectFromJson(organizationString, Organization.class);
				if (org != null && StringUtils.isNoneBlank(org.getOrg_name_cn())) {
					String org_name_cn = org.getOrg_name_cn();
					int org_id = org.getOrg_id().intValue();
					usermap.put(org_id + "", org_name_cn);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return usermap;
	}

	protected Map<String, String> getOrgUserMap(HttpServletRequest request) throws Exception {
		Map<String, String> usermap = new HashMap<String, String>();
		Map<String, Object> documentMap = new HashMap<String, Object>();
		String documentResult = this.callUrl(request, "/getorgusers", documentMap);
		ObjectMapper objectMapper = JsonUtil.getMapperInstance();
		JsonNode jsonNode = objectMapper.readTree(documentResult);
		JsonNode dataNode = jsonNode.get("data");
		for (int i = 0; i < dataNode.size(); i++) {
			JsonNode orgUserNode = dataNode.get(i);
			String organizationString = orgUserNode.get("organization").toString();
			Organization org = JsonUtil.getObjectFromJson(organizationString, Organization.class);
			if (org != null && StringUtils.isNoneBlank(org.getOrg_name_cn(), orgUserNode.get("user_id").asText())) {
				int org_id = org.getOrg_id().intValue();
				usermap.put(org_id + "", orgUserNode.get("user_id").asText());
			}
		}
		return usermap;
	}

	protected OrgUser getOrgUser(HttpServletRequest request) throws Exception {
		Map<String, Object> documentMap = new HashMap<String, Object>();
		String documentResult = this.callUrl(request, "/getuserinfo", documentMap);
		ObjectMapper objectMapper = JsonUtil.getMapperInstance();
		JsonNode jsonNode = objectMapper.readTree(documentResult);
		JsonNode dataNode = jsonNode.get("data");
		OrgUser user = JsonUtil.getObjectFromJson(dataNode.toString(), OrgUser.class);
		return user;
	}

	/**
	 * @throws Exception *
	 * 
	 * **/
	protected String callUrl(HttpServletRequest request, String url, Map<String, Object> map) throws Exception {
		// 为请求加入token和udid
		map.put("token", (String) request.getParameter("token"));
		map.put("udid", (String) request.getParameter("udid"));
		map.put("version", (String) request.getParameter("version"));

		// 发送请求
		String documentResult = HttpClientUtil.get(ESB_REQUEST_URL + url, map);
		ObjectMapper objectMapper = JsonUtil.getMapperInstance();
		JsonNode jsonNode = objectMapper.readTree(documentResult);
		switch (jsonNode.get("code").asInt()) {
			case 400:
				throw new EsbException(EnumException.user_esb_error);
			case 9003:
				throw new EsbException(EnumException.common_must_login);
			case 9004:
				throw new EsbException(EnumException.common_another_device_login);
			case 9005:
				throw new EsbException(EnumException.common_permission_denied);
			case 9009:
				throw new EsbException(EnumException.common_token_expire);
			case 1011:
				throw new EsbException(EnumException.user_password_wrong);
			default:
				break;
		}
		return documentResult;
	}

	protected String callPostUrl(HttpServletRequest request, String url, Map<String, Object> map) throws Exception {
		map.put("token", (String) request.getParameter("token"));
		map.put("udid", (String) request.getParameter("udid"));
		map.put("version", (String) request.getParameter("version"));

		String documentResult = HttpClientUtil.post(ESB_REQUEST_URL + url, map);
		ObjectMapper objectMapper = JsonUtil.getMapperInstance();
		JsonNode jsonNode = objectMapper.readTree(documentResult);
		switch (jsonNode.get("code").asInt()) {
			case 9003:
				throw new EsbException(EnumException.common_must_login);
			case 9004:
				throw new EsbException(EnumException.common_another_device_login);
			case 9005:
				throw new EsbException(EnumException.common_permission_denied);
			case 9009:
				throw new EsbException(EnumException.common_token_expire);
			default:
				break;
		}
		return documentResult;
	}

	protected String callPostUrlJson(HttpServletRequest request, String url, String map) throws Exception {

		String token = request.getParameter("token");
		String udid = request.getParameter("udid");
		String version = request.getParameter("version");

		// ObjectMapper mapper = new ObjectMapper();
		// String jsonString = mapper.writeValueAsString(map);

		String documentResult = HttpClientUtil.post(ESB_REQUEST_URL + url + "?token=" + token + "&udid=" + udid + "&version="
				+ version, map, "application/json");
		ObjectMapper objectMapper = JsonUtil.getMapperInstance();
		JsonNode jsonNode = objectMapper.readTree(documentResult);
		switch (jsonNode.get("code").asInt()) {
			case 9003:
				throw new EsbException(EnumException.common_must_login);
			case 9004:
				throw new EsbException(EnumException.common_another_device_login);
			case 9005:
				throw new EsbException(EnumException.common_permission_denied);
			case 9009:
				throw new EsbException(EnumException.common_token_expire);
			default:
				break;
		}
		return documentResult;
	}

	protected String callPostUrlWX(HttpServletRequest request, String url, Map<String, Object> map) throws Exception {
		map.put("token", (String) request.getParameter("token"));
		map.put("udid", (String) request.getParameter("udid"));
		map.put("version", (String) request.getParameter("version"));

		String documentResult = HttpClientUtil.post(EDUWX_REQUEST_URL + url, map);
		ObjectMapper objectMapper = JsonUtil.getMapperInstance();
		JsonNode jsonNode = objectMapper.readTree(documentResult);
		switch (jsonNode.get("code").asInt()) {
			case 9003:
				throw new EsbException(EnumException.common_must_login);
			case 9004:
				throw new EsbException(EnumException.common_another_device_login);
			case 9005:
				throw new EsbException(EnumException.common_permission_denied);
			case 9009:
				throw new EsbException(EnumException.common_token_expire);
			default:
				break;
		}
		return documentResult;
	}

	
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public String getTokenName() {
		return token;
	}

	public String getUdidName() {
		return udid;
	}

	public String getUserjsonName() {
		return userjson;
	}

	public String getOrgjsonName() {
		return orgjson;
	}

	/**
	 * 获取教师role
	 * 
	 * **/
	public List<Role> callRoles(int tech_id, HttpServletRequest request) throws Exception {
		String documentResult = "";
		List<Role> list = new ArrayList<Role>();
		try {
			Map<String, Object> documentMap = new HashMap<String, Object>();
			documentMap.put("tech_id", tech_id);
			documentResult = this.callUrl(request, "api/tech/getRoles", documentMap);
			if (StringUtils.isNotEmpty(documentResult)) {
				ObjectMapper objectMapper = JsonUtil.getMapperInstance();
				JsonNode jsonNode = objectMapper.readTree(documentResult);
				JsonNode dataNode = jsonNode.get("data");

				for (int i = 0; i < dataNode.size(); i++) {
					// jsonArray里的每一项都是JsonObject
					JsonNode childNode = dataNode.get(i);
					Role role = new Role();
					String role_id = childNode.get("role_id").toString();
					String rl_id = childNode.get("rl_id").toString();
					String role_name = childNode.get("role_name").toString();
					String authorities = childNode.get("authorities").toString();
					role.setRole_id(Integer.parseInt(role_id));
					role.setRole_name(role_name);
					role.setRl_id(Integer.parseInt(rl_id));
					role.setAuthorities(authorities);
					list.add(role);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	
	
//	request.setAttribute("user_id", user_id);
//	request.setAttribute("org_id", user.getOrg_id());
//	request.setAttribute("org_name", user.getOrganization().getOrg_name_cn());
//	request.setAttribute("open_id", user.getOpenid());
//	request.setAttribute("identity", user.getIdentity());
	
//	String user_id = request.getAttribute("user_id").toString();
//	Teacher teacher = this.getTechByUId(user_id);
//	Parent parent = this.getParentByUId(user_id);
	
	
	
	public Teacher getTechByUId(String id) throws IOException {
		// TODO Auto-generated method stub
		Teacher user = null;
		String key = "techinfo_user:"+id;
		Object obj = redisUtil.get(key);
		if(obj!=null)
			user = JsonUtil.getObjectFromJson(obj.toString(), Teacher.class);
		return user;
	}
	
	public Organization getOrgByUId(String id) throws IOException {
		// TODO Auto-generated method stub
		Organization user = null;
		String key = "orginfo:"+id;
		Object obj = redisUtil.get(key);
		if(obj!=null)
			user = JsonUtil.getObjectFromJson(obj.toString(), Organization.class);
		return user;
	}
	
	public Parent getParentByUId(String id) throws IOException {
		// TODO Auto-generated method stub
		Parent user = null;
		String key = "parentuser:"+id;
		Object obj = redisUtil.get(key);
		if(obj!=null)
			user = JsonUtil.getObjectFromJson(obj.toString(), Parent.class);
		return user;
	}
	
	//通过教师id获取 教师 为班主任的班级id集合   ，分隔 
	public String getCIDSById(String tech_id) throws IOException {
		// TODO Auto-generated method stub
		String key = "techrange:"+tech_id;
		Map<String, String> obj = redisUtil.hgetall(key);
		TechRange range = null;
		String clasids = "";
		for (Map.Entry<String, String> entry : obj.entrySet()) {  
		    String mapkey = entry.getKey().toString();  
		    String mapvalue = entry.getValue().toString();  
		    
		    if(StringUtils.isNotEmpty(mapvalue)){
				range = JsonUtil.getObjectFromJson(mapvalue, TechRange.class);
				if(range.getRl_id()==4){
					clasids = clasids+range.getClas_id()+",";
				}
			}
		} 
		 System.out.println("clasids=" + clasids);
		return clasids;
	}

/*	protected SessionInfo getSessionInfo(HttpServletRequest request) {
		Integer userId=(Integer)request.getAttribute("user_id");
		Integer orgId=(Integer)request.getAttribute("org_id");
		String token=(String)request.getAttribute("token");
		Integer camId=(Integer)request.getAttribute("cam_id");
		Integer identity=(Integer)request.getAttribute("identity");
		SessionInfo sessionInfo=new SessionInfo();
		sessionInfo.setUserId(userId);
		sessionInfo.setOrgId(orgId);
		sessionInfo.setToken(token);
		sessionInfo.setCamId(camId);
		sessionInfo.setIdentity(identity);
		return sessionInfo;
	}*/
	
	protected SessionInfo getSessionInfo(HttpServletRequest request) {
		SessionInfo sessionInfo=new SessionInfo();
		sessionInfo.setUserId(11497);
		sessionInfo.setOrgId(46);
		sessionInfo.setToken(token);
		sessionInfo.setCamId(20);
		sessionInfo.setIdentity(1);
		sessionInfo.setLoginName("admin");
		sessionInfo.setIsAuth(1);
		return sessionInfo;
	}
}
