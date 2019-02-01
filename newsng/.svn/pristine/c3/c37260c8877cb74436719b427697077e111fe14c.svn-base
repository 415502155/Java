package sng.controller.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sng.entity.UserRegister;
import sng.pojo.Result;
import sng.pojo.WeiXinJSConfig;
import sng.pojo.base.OrgUser;
import sng.service.MQService;
import sng.service.TokenService;
import sng.service.UserRegisterService;
import sng.util.CookieTool;
import sng.util.HttpClientUtil;
import sng.util.JsonUtil;
import sng.util.RedisUtil;
import sng.util.SHA1SignUtil;

@Controller
@RequestMapping("/wechat/portal")
public class PageController extends BaseAction{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserRegisterService userRegisterService;
	
	@Autowired
	private MQService mqService;
	
	@Autowired
	RedisUtil redisUtil;

	@RequestMapping(value = "/wxCallback.htm")
	public String wxCallback(@RequestParam(name = "org_id", required = true) String org_id, @RequestParam(name = "identity", required = true) String identity,
			@RequestParam(name = "code", required = true) String code,
			@RequestParam(name = "state", required = false) String state, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		// 根据code获取网页授权access_token
		Map<String, Object> resultMap = tokenService.requestWebAccessTokenAndOpenId(Integer.valueOf(org_id), code);
		if (resultMap != null && resultMap.containsKey("access_token")) {
			String openid = (String) resultMap.get("openid");
			System.out.println("openid"+openid);
			String token = tokenService.getAccessTokenByOrg_Id(Integer.valueOf(org_id));
			try {
				String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+token+"&openid="+openid+"&lang=zh_CN";
				String userInfo = HttpClientUtil.get(url);
				JSONObject json = new JSONObject(userInfo);
				
				CookieTool.setCookie(request, response, "headimgurl", json.getString("headimgurl").toString(), true);
				CookieTool.setCookie(request, response, "nickname", json.getString("nickname").toString(), true);
			} catch (Exception e) {
			}
			CookieTool.setCookie(request, response, "org_id", org_id, true);
			CookieTool.setCookie(request, response, "identity", identity, true);
			CookieTool.setCookie(request, response, "openid", openid, true);
			
			return "weixin/jsp/load";
		} else {
			return "weixin/jsp/error";
		}
	}
	
	@RequestMapping(value="/index.htm")
	public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		String org_id = CookieTool.getCookieValue(request, "org_id", true);
		String identity = CookieTool.getCookieValue(request, "identity", true);
		String openid = CookieTool.getCookieValue(request, "openid", true);
		
		logger.info("\nindex.htm：org_id：[{}],identity：[{}],openid：[{}]", org_id, identity, openid);
		
		if (StringUtils.isNoneBlank(org_id, identity, openid)) {
			// 根据openid查询是否关联了人员
			OrgUser orgUser = null;

			String key = "orguserOpenid:" + openid + ":" + org_id + ":" + identity;
			Object obj = redisUtil.get(key);
			if (obj != null)
				orgUser = JsonUtil.getObjectFromJson(obj.toString(), OrgUser.class);
					
			if (orgUser != null && StringUtils.isNotBlank(orgUser.getOpenid())) {
				CookieTool.setCookie(request, response, "org_id", org_id, true);
				CookieTool.setCookie(request, response, "identity", identity, true);
				CookieTool.setCookie(request, response, "openid", openid, true);
				if ("1".equals(identity)) {
					return "weixin/html/teacher/login/index";
				} else if ("0".equals(identity)) {
					return "weixin/html/parent/login/index";
				} else {
					request.setAttribute("errorInfo", "1人员信息不是教师也不是家长！");
					return "weixin/jsp/error";
				}
			} else {
				if("1".equals(identity)){
					return "weixin/html/teacher/login/login_byMob";
				}
				int countuser  = userRegisterService.getUserByOpenid(org_id, openid);
				
				if(countuser>0){
					CookieTool.setCookie(request, response, "org_id", org_id, true);
					CookieTool.setCookie(request, response, "identity", identity, true);
					CookieTool.setCookie(request, response, "openid", openid, true);
					if ("0".equals(identity)) {
						return "weixin/html/parent/login/index";
					} else {
						request.setAttribute("errorInfo", "1人员信息不是教师也不是家长！");
						return "weixin/jsp/error";
					}
				}else{
					// 未关联人员则跳往绑定手机号页面
					CookieTool.setCookie(request, response, "org_id", org_id, true);
					CookieTool.setCookie(request, response, "identity", identity, true);
					CookieTool.setCookie(request, response, "openid", openid, true);
					if ("1".equals(identity)) {
						return "weixin/html/teacher/login/login_byMob";
					} else if ("0".equals(identity)) {
						return "weixin/html/parent/login/login_byMob";
					} else {
						request.setAttribute("errorInfo", "2人员信息不是教师也不是家长！");
						return "weixin/jsp/error";
					}
				}
			}
		} else {
			request.setAttribute("errorInfo", "org_id="+org_id+",identity="+identity+",openid="+openid);
			return "weixin/jsp/error";
		}
	}
	
	
	@RequestMapping(value="/getWxJsConfig.json")
	@ResponseBody
	public Result<WeiXinJSConfig> getWxJsConfig(@RequestParam(name = "org_id", required = true) String org_id,
			@RequestParam(name = "url", required = true) String url, HttpServletRequest request, HttpSession session)
			throws Exception {
		Result<WeiXinJSConfig> resultEntity = new Result<WeiXinJSConfig>();
		if (StringUtils.isNoneBlank(org_id, url)) {
			// 获取基础支持的access_token
			String access_token = tokenService.getAccessTokenByOrg_Id(Integer.valueOf(org_id));
			// logger.info("\naccess_token：[{}]", access_token);

			// 获取jsapi_ticket
			String jsapi_ticket = tokenService.getJsapiTicket(Integer.valueOf(org_id), access_token);
			// logger.info("\njsapi_ticket：[{}]", jsapi_ticket);

			// 签名
			/*
			 * Map<String, String> jsconfigParamMap = SHA1SignUtil.sign(jsapi_ticket, Constant.DOMAIN_NAME + "/shijiwxy/wechat/portal/index.htm");
			 */
			Map<String, String> jsconfigParamMap = SHA1SignUtil.sign(jsapi_ticket, url);
			// 增加一个appid参数
			String appId = tokenService.getAppId(Integer.valueOf(org_id));

			// logger.info("\njsconfigParamMap：[{}]", jsconfigParamMap);

			WeiXinJSConfig wxJsConfig = new WeiXinJSConfig();
			wxJsConfig.setAppId(appId);
			wxJsConfig.setUrl(url);
			wxJsConfig.setJsapi_ticket(jsapi_ticket);
			wxJsConfig.setNonceStr(jsconfigParamMap.get("nonceStr"));
			wxJsConfig.setTimestamp(jsconfigParamMap.get("timestamp"));
			wxJsConfig.setSignature(jsconfigParamMap.get("signature"));

			resultEntity.setData(wxJsConfig);
			resultEntity.setSuccess(true);
		} else {
			resultEntity.setSuccess(false);
			resultEntity.setMessage("参数不完整！");
		}

		return resultEntity;
	}
	
}
