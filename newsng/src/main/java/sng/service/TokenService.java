package sng.service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sng.dao.Org_WXDao;
import sng.pojo.Result;
import sng.pojo.WeiXinConfig;
import sng.pojo.WxResult;
import sng.util.Constant;
import sng.util.HttpClientUtil;
import sng.util.JsonUtil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class TokenService {

	@Autowired
	private Org_WXDao org_wxDao;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/*public OrgWXEntity getOrgWXEntityById(int org_id) {
		OrgWXEntity org_wxEntity = org_wxDao.get(OrgWXEntity.class, org_id);
		return org_wxEntity;
	}*/

	/**
	 * 根据机构ID查询基础支持中的access_token
	 * 
	 * @param org_id
	 * @return
	 * @throws Exception
	 */
	public String getAccessTokenByOrg_Id(int org_id) throws Exception {
		String accessToken = null;
		/*OrgWXEntity org_wxEntity = getOrgWXEntityById(org_id);
		if (org_wxEntity != null && StringUtils.isNotBlank(org_wxEntity.getApp_id())) {
			if (StringUtils.isNotBlank(org_wxEntity.getAccess_token()) && org_wxEntity.getAccess_token_expires_time() != null) {
				// 验证token是否在有效期内，超时则重新请求
				Date currentDate = new Date();
				if (currentDate.after(org_wxEntity.getAccess_token_expires_time())) {
					// 超时，重新获取token
					Map<String, Object> resultMap = requestAccessToken(org_id);
					if (resultMap != null && resultMap.containsKey("accessToken")) {
						accessToken = (String) resultMap.get("accessToken");
					}
				} else {
					accessToken = org_wxEntity.getAccess_token();
				}
			} else {
				// 任一为空值则重新请求token
				Map<String, Object> resultMap = requestAccessToken(org_id);
				if (resultMap != null && resultMap.containsKey("accessToken")) {
					accessToken = (String) resultMap.get("accessToken");
				}
			}
		}*/
		
		// 根据机构ID查询app_id和app_secret
		// 发送请求
		String resultJson = HttpClientUtil.get(Constant.GET_WEIXIN_CONFIG + org_id);
		ObjectMapper objectMapper = JsonUtil.getMapperInstance();
		JsonNode jsonNode = objectMapper.readTree(resultJson);

		if (jsonNode.get("success").asBoolean() && jsonNode.get("code").asInt() == 200) {
			Result<WeiXinConfig> result = JsonUtil.getObjectFromJson(resultJson, new TypeReference<Result<WeiXinConfig>>() {});
			WeiXinConfig wxConfig = result.getData();
			if (wxConfig != null && StringUtils.isNotBlank(wxConfig.getToken())) {
				accessToken = wxConfig.getToken();
			} else {
				logger.error("getAccessTokenByOrg_Id方法，微信配置为空：WeiXinConfig=[{}]", jsonNode.get("data"));
				throw new Exception("getAccessTokenByOrg_Id方法，微信配置为空：WeiXinConfig=[" + jsonNode.get("data") + "]");
			}
		} else {
			logger.error("getAccessTokenByOrg_Id方法，获取微信配置时失败：GET_WEIXIN_CONFIG=[{}]", Constant.GET_WEIXIN_CONFIG + org_id);
		}

		return accessToken;
	}
	
	/**
	 * 调用接口获取access_token及失效时间（不要随意在外部调用，除非请求时access_token失效）
	 * 
	 * @param app_id
	 * @param app_secret
	 * @return
	 * @throws Exception
	 */
	/*private Map<String, Object> requestAccessToken(int org_id) throws Exception {
		if (org_id > 0) {
			OrgWXEntity orgWXEntity = org_wxDao.get(OrgWXEntity.class, org_id);
			String app_id = orgWXEntity.getApp_id();
			String app_secret = orgWXEntity.getApp_secret();
			if (StringUtils.isNoneBlank(app_id, app_secret)) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				
				String url = String.format(GET_ACCESS_TOKEN_URL, app_id, app_secret);
				String responseStr = HttpClientUtil.get(url);
				logger.info("\nrequestAccessToken方法，responseStr=[{}]", responseStr);
				Map<String, Object> responseMap = JsonUtil.getObjectFromJson(responseStr, Map.class);
				if (responseMap.containsKey("access_token")) {
					// 返回正常
					String accessToken = (String) responseMap.get("access_token");
					Integer expires_in = (Integer) responseMap.get("expires_in");

					// 根据当前时间计算到期时间（expires_in秒数减5分钟，保证在即将超时5分钟之前就重新请求）
					int leftSeconds = expires_in.intValue() - 300;
					Calendar calendar = Calendar.getInstance();
					Date currentTime = new Date();
					calendar.setTime(currentTime);
					calendar.add(Calendar.SECOND, leftSeconds);
					// 获得超时的时间
					Date expiresTime = calendar.getTime();

					orgWXEntity.setAccess_token(accessToken);
					orgWXEntity.setAccess_token_expires_time(expiresTime);
					org_wxDao.update(orgWXEntity);
					
					resultMap.put("accessToken", accessToken);
					resultMap.put("expiresTime", expiresTime);
				} else {
					// 返回异常
				}
				
				return resultMap;
			}
		}
		
		return null;
	}*/
	
	
	/**
	 * 获取网页授权access_token
	 * @param org_id
	 * @param code
	 * @return { "access_token":"ACCESS_TOKEN", "expires_in":7200, "refresh_token":"REFRESH_TOKEN", "openid":"OPENID", "scope":"SCOPE" }
	 * @throws Exception 
	 */
	public Map<String, Object> requestWebAccessTokenAndOpenId(int org_id, String code) throws Exception {
		Map<String, Object> resultMap = null;

		if (StringUtils.isNoneBlank(String.valueOf(org_id), code)) {
			/*OrgWXEntity org_wxEntity = getOrgWXEntityById(org_id);
			if (org_wxEntity != null && StringUtils.isNotBlank(org_wxEntity.getApp_id())) {
				String url = String.format(GET_WEB_ACCESS_TOKEN_URL, org_wxEntity.getApp_id(), org_wxEntity.getApp_secret(), code);
				String responseStr = HttpClientUtil.get(url);
				logger.info("\nrequestWebAccessTokenAndOpenId方法，responseStr=[{}]", responseStr);
				Map<String, Object> responseMap = JsonUtil.getObjectFromJson(responseStr, Map.class);
				if (responseMap.containsKey("access_token")) {
					resultMap = responseMap;
				}
			}*/
			
			// 根据机构ID查询app_id和app_secret
			// 发送请求
			String resultJson = HttpClientUtil.get(Constant.GET_WEIXIN_CONFIG + org_id);
			ObjectMapper objectMapper = JsonUtil.getMapperInstance();
			JsonNode jsonNode = objectMapper.readTree(resultJson);

			if (jsonNode.get("success").asBoolean() && jsonNode.get("code").asInt() == 200) {
				Result<WeiXinConfig> result = JsonUtil.getObjectFromJson(resultJson, new TypeReference<Result<WeiXinConfig>>() {});
				WeiXinConfig wxConfig = result.getData();
				if (wxConfig != null && StringUtils.isNoneBlank(wxConfig.getApp_id(), wxConfig.getApp_secret())) {
					String url = String.format(Constant.GET_WEB_ACCESS_TOKEN_URL, wxConfig.getApp_id(), wxConfig.getApp_secret(), code);
					String responseStr = HttpClientUtil.get(url);

					Map<String, Object> responseMap = JsonUtil.getObjectFromJson(responseStr, Map.class);
					if (responseMap.containsKey("access_token")) {
						resultMap = responseMap;
					} else {
						logger.error("requestWebAccessTokenAndOpenId方法，responseStr=[{}]", responseStr);
					}
				} else {
					logger.error("requestWebAccessTokenAndOpenId方法，微信配置为空：WeiXinConfig=[{}]", jsonNode.get("data"));
				}
			} else {
				logger.error("requestWebAccessTokenAndOpenId方法，获取微信配置时失败：GET_WEIXIN_CONFIG=[{}]", Constant.GET_WEIXIN_CONFIG + org_id);
			}
		}

		return resultMap;
	}
	
	/**
	 * 
	 * @param access_token
	 * @return
	 * @throws Exception 
	 */
	public String getJsapiTicket(int org_id, String access_token) throws Exception {
		String jsapi_ticket = null;
		/*if (StringUtils.isNotBlank(access_token)) {
			OrgWXEntity org_wxEntity = getOrgWXEntityById(org_id);
			if (org_wxEntity != null && StringUtils.isNotBlank(org_wxEntity.getApp_id())) {
				if (StringUtils.isNotBlank(org_wxEntity.getJsapi_ticket()) && org_wxEntity.getJsapi_ticket_expires_time() != null) {
					// 查看是否超时
					Date currentDate = new Date();
					if (currentDate.after(org_wxEntity.getJsapi_ticket_expires_time())) {
						Map<String, Object> resultMap = requestJsapiTicket(access_token);
						if (resultMap != null && resultMap.containsKey("ticket")) {
							jsapi_ticket = (String) resultMap.get("ticket");
							Date expiresTime = (Date) resultMap.get("expiresTime");
							
							org_wxEntity.setJsapi_ticket(jsapi_ticket);
							org_wxEntity.setJsapi_ticket_expires_time(expiresTime);
							org_wxDao.save(org_wxEntity);
						}
					} else {
						jsapi_ticket = org_wxEntity.getJsapi_ticket();
					}
				} else {
					Map<String, Object> resultMap = requestJsapiTicket(access_token);
					if (resultMap != null && resultMap.containsKey("ticket")) {
						jsapi_ticket = (String) resultMap.get("ticket");
						Date expiresTime = (Date) resultMap.get("expiresTime");
						
						org_wxEntity.setJsapi_ticket(jsapi_ticket);
						org_wxEntity.setJsapi_ticket_expires_time(expiresTime);
						org_wxDao.save(org_wxEntity);
					}
				}
			}
		}*/
		
		
		// 根据机构ID查询app_id和app_secret
		// 发送请求
		String resultJson = HttpClientUtil.get(Constant.GET_WEIXIN_CONFIG + org_id);
		ObjectMapper objectMapper = JsonUtil.getMapperInstance();
		JsonNode jsonNode = objectMapper.readTree(resultJson);

		if (jsonNode.get("success").asBoolean() && jsonNode.get("code").asInt() == 200) {
			Result<WeiXinConfig> result = JsonUtil.getObjectFromJson(resultJson, new TypeReference<Result<WeiXinConfig>>() {});
			WeiXinConfig wxConfig = result.getData();
			if (wxConfig != null && StringUtils.isNotBlank(wxConfig.getTicket())) {
				jsapi_ticket = wxConfig.getTicket();
			} else {
				logger.error("getJsapiTicket方法，微信配置为空：WeiXinConfig=[{}]", jsonNode.get("data"));
				throw new Exception("getJsapiTicket方法，微信配置为空：WeiXinConfig=["+jsonNode.get("data")+"]");
			}
		} else {
			logger.error("getJsapiTicket方法，获取微信配置时失败：GET_WEIXIN_CONFIG=[{}]", Constant.GET_WEIXIN_CONFIG + org_id);
		}
		return jsapi_ticket;
	}
	
	public String getAppId(int org_id) throws Exception {
		String appId = null;
		
		// 根据机构ID查询app_id和app_secret
		// 发送请求
		String resultJson = HttpClientUtil.get(Constant.GET_WEIXIN_CONFIG + org_id);
		ObjectMapper objectMapper = JsonUtil.getMapperInstance();
		JsonNode jsonNode = objectMapper.readTree(resultJson);

		if (jsonNode.get("success").asBoolean() && jsonNode.get("code").asInt() == 200) {
			Result<WeiXinConfig> result = JsonUtil.getObjectFromJson(resultJson, new TypeReference<Result<WeiXinConfig>>() {});
			WeiXinConfig wxConfig = result.getData();
			if (wxConfig != null && StringUtils.isNotBlank(wxConfig.getApp_id())) {
				appId = wxConfig.getApp_id();
			} else {
				logger.error("getAppId方法，微信配置为空：WeiXinConfig=[{}]", jsonNode.get("data"));
				throw new Exception("getAppId方法，微信配置为空：WeiXinConfig=["+jsonNode.get("data")+"]");
			}
		} else {
			logger.error("getAppId方法，获取微信配置时失败：GET_WEIXIN_CONFIG=[{}]", Constant.GET_WEIXIN_CONFIG + org_id);
		}
		
		return appId;
	}
	
	
	/**
	 * 
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> requestJsapiTicket(String access_token) throws Exception {
		Map<String, Object> resultMap = null;
		String url = String.format(Constant.GET_JSAPI_TICKET_URL, access_token);
		String responseStr = HttpClientUtil.get(url);
		logger.info("\nrequestJsapiTicket方法，responseStr=[{}]", responseStr);
		// {"errcode":0,"errmsg":"ok","ticket":"kgt8ON7yVITDhtdwci0qeR6Cs_cPLlPAqtebyVVIluCaz_VnLfl4fpW3qJzWnJFXixaGonAz3Ksug5AClV1SUA","expires_in":7200}
		Map<String, Object> responseMap = JsonUtil.getObjectFromJson(responseStr, Map.class);
		if (responseMap.containsKey("ticket")) {
			// 返回正常
			String ticket = (String) responseMap.get("ticket");
			Integer expires_in = (Integer) responseMap.get("expires_in");

			// 根据当前时间计算到期时间（expires_in秒数减5分钟，保证在即将超时5分钟之前就重新请求）
			int leftSeconds = expires_in.intValue() - 300;
			Calendar calendar = Calendar.getInstance();
			Date currentTime = new Date();
			calendar.setTime(currentTime);
			calendar.add(Calendar.SECOND, leftSeconds);
			// 获得超时的时间
			Date expiresTime = calendar.getTime();

			resultMap = new HashMap<String, Object>();
			resultMap.put("ticket", ticket);
			resultMap.put("expiresTime", expiresTime);
		}
		
		return resultMap;
	}
	
	/**
	 * 调用接口获取模板id
	 * @param org_id
	 * @param templateName
	 * @return
	 * @throws Exception 
	 */
	public String getWxTemplateId(String org_id, String templateName) throws Exception {
		String template_id = null;
		
		if (StringUtils.isNoneBlank(org_id, templateName)) {
			String url = String.format(Constant.GET_WEIXIN_TEMPLATE_ID, org_id, templateName);
			String resultJson = HttpClientUtil.get(url);
			ObjectMapper objectMapper = JsonUtil.getMapperInstance();
			JsonNode jsonNode = objectMapper.readTree(resultJson);

			if (jsonNode.get("success").asBoolean() && jsonNode.get("code").asInt() == 200) {
				template_id = jsonNode.get("data").asText();
				if (StringUtils.isBlank(template_id)) {
					template_id = null;
					logger.error("getWxTemplateId方法，获取微信模板id为空：org_id=[{}],templateName=[{}]", org_id, templateName);
					throw new Exception("getWxTemplateId方法，获取微信模板id为空：org_id=["+org_id+"],templateName=["+templateName+"]");
				}
			} else {
				logger.error("getWxTemplateId方法，获取微信模板id时失败：org_id=[{}],templateName=[{}]", org_id, templateName);
			}
		}
		
		return template_id;
	}

	public List<String> getUserListByOrg_Id(String access_token,Integer org_id) {
		// TODO Auto-generated method stub
		List<String> openids = new ArrayList<String>();	
		try {	
			
			getUserList(access_token, openids,"");			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return openids;
	}

	private void getUserList(String access_token, List<String> openids,String next_openid)
			throws ConnectTimeoutException, SocketTimeoutException, Exception,
			IOException {
		String requestUrl = String.format(Constant.GET_USER_LIST_URL, access_token,next_openid);
		String result = HttpClientUtil.post(requestUrl,"", "application/json");
		WxResult aa = JsonUtil.getObjectFromJson(result, WxResult.class);
		if(aa!=null){
			if(aa.getCount()>0&&aa.getData().get("openid").size()>0){
				openids.addAll(aa.getData().get("openid"));
				getUserList(access_token,openids,aa.getNext_openid());
			}
		}
	}
}
