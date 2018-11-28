package sng.controller.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sng.pojo.Result;
import sng.util.CookieTool;
import sng.util.HttpClientUtil;
import sng.util.JsonUtil;

@Controller
@RequestMapping("/portal/login")
public class LoginController extends BaseAction {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	@RequestMapping(value = "/loginFromESB.htm")
	public void loginFromESB(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String login_name = request.getParameter("u");
		String login_pass = request.getParameter("p");
		String org_id_sel = request.getParameter("o");

		String managerInfo = null;
		String token = null;
		String udid = null;
		String dataStr = null;
		org.json.JSONObject resultObj = null;
		org.json.JSONObject dataObj = null;
		try {
			// 先判断缓存中是否有对应信息
			managerInfo = (String) redisUtil.get("OrgManagerLoginInfo:org=" + org_id_sel + ",login_name=" + login_name
					+ ",login_pass=" + login_pass);
			if (StringUtils.isNotBlank(managerInfo)) {
				resultObj = new org.json.JSONObject(managerInfo);
				dataStr = resultObj.get("data").toString();
				dataObj = new org.json.JSONObject(dataStr);
				// 验证是否还有效
				token = dataObj.get("token").toString();
				udid = dataObj.get("udid").toString();

				// 调用接口验证token和udid是否有效
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("token", token);
				paramMap.put("udid", udid);
				paramMap.put("version", "0");
				String resultStr = HttpClientUtil.post(ESB_REQUEST_URL + "no/validateToken", paramMap);
				org.json.JSONObject resultObject = new org.json.JSONObject(resultStr);
				String result = resultObject.getString("data").toString();
				if (StringUtils.isNotBlank(result) && "success".equals(result)) {
					// 未超时
				} else {
					// 重新登录
					Map<String, Object> documentMap = new HashMap<String, Object>();
					Map<Integer, Map<String, Integer>> orgAuthMap = new HashMap<Integer, Map<String, Integer>>();
					documentMap.put("login_name", login_name);
					documentMap.put("login_pass", login_pass);
					managerInfo = HttpClientUtil.post(ESB_API_REQUEST_URL + "/orglogin", documentMap);

					if (StringUtils.isNotBlank(managerInfo)) {
						redisUtil.set("OrgManagerLoginInfo:org=" + org_id_sel + ",login_name=" + login_name + ",login_pass="
								+ login_pass, managerInfo);
						
						resultObj = new org.json.JSONObject(managerInfo);
						dataStr = resultObj.get("data").toString();
						dataObj = new org.json.JSONObject(dataStr);
						// 验证是否还有效
						token = dataObj.get("token").toString();
						udid = dataObj.get("udid").toString();
					}
				}
			} else {
				// 新登录
				Map<String, Object> documentMap = new HashMap<String, Object>();
				Map<Integer, Map<String, Integer>> orgAuthMap = new HashMap<Integer, Map<String, Integer>>();
				documentMap.put("login_name", login_name);
				documentMap.put("login_pass", login_pass);
				managerInfo = HttpClientUtil.post(ESB_API_REQUEST_URL + "/orglogin", documentMap);

				if (StringUtils.isNotBlank(managerInfo)) {
					redisUtil.set("OrgManagerLoginInfo:org=" + org_id_sel + ",login_name=" + login_name + ",login_pass="
							+ login_pass, managerInfo);
					
					resultObj = new org.json.JSONObject(managerInfo);
					dataStr = resultObj.get("data").toString();
					dataObj = new org.json.JSONObject(dataStr);
					// 验证是否还有效
					token = dataObj.get("token").toString();
					udid = dataObj.get("udid").toString();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}
		
		CookieTool.setCookie(request, response, "u", login_name, true);
		CookieTool.setCookie(request, response, "p", login_pass, true);
		CookieTool.setCookie(request, response, "o", org_id_sel, true);
		CookieTool.setCookie(request, response, "wx_t", token, true);
		CookieTool.setCookie(request, response, "wx_u", udid, true);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("code", String.valueOf(200));
		resultMap.put("url", WX_PROJECT_URL + "webV2/index.html");

		response.getWriter().write(JsonUtil.toJson(resultMap));
		response.getWriter().flush();
	}
	
	
	@RequestMapping(value="/managerLogin.htm")
	@ResponseBody
	public Result<String> managerLogin(HttpServletRequest request) {
		Result<String> resultEntity = new Result<String>();
		String login_name = request.getParameter("u");
		String login_pass = request.getParameter("p");
		String org_id_sel = request.getParameter("o");
		
		
		String managerInfo = null;
		try {
			// 先判断缓存中是否有对应信息
			managerInfo = (String) redisUtil.get("OrgManagerLoginInfo:org=" + org_id_sel + ",login_name=" + login_name
					+ ",login_pass=" + login_pass);
			if (StringUtils.isNotBlank(managerInfo)) {
				org.json.JSONObject obj = new org.json.JSONObject(managerInfo);
				String data = obj.get("data").toString();
				org.json.JSONObject obj1 = new org.json.JSONObject(data);
				// 验证是否还有效
				String token = obj1.get("token").toString();
				String udid = obj1.get("udid").toString();

				// 调用接口验证token和udid是否有效
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("token", token);
				paramMap.put("udid", udid);
				paramMap.put("version", "0");
				String resultStr = HttpClientUtil.post(ESB_REQUEST_URL + "no/validateToken", paramMap);
				org.json.JSONObject resultObject = new org.json.JSONObject(resultStr);
				String result = resultObject.getString("data").toString();
				if (StringUtils.isNotBlank(result) && "success".equals(result)) {
					// 未超时

					resultEntity.setData(managerInfo);
				} else {
					// 重新登录
					Map<String, Object> documentMap = new HashMap<String, Object>();
					Map<Integer, Map<String, Integer>> orgAuthMap = new HashMap<Integer, Map<String, Integer>>();
					documentMap.put("login_name", login_name);
					documentMap.put("login_pass", login_pass);
					managerInfo = HttpClientUtil.post(ESB_API_REQUEST_URL + "/orglogin", documentMap);

					if (StringUtils.isNotBlank(managerInfo)) {
						redisUtil.set("OrgManagerLoginInfo:org=" + org_id_sel + ",login_name=" + login_name + ",login_pass="
								+ login_pass, managerInfo);
					}
					
					resultEntity.setData(managerInfo);
				}
			} else {
				// 新登录
				Map<String, Object> documentMap = new HashMap<String, Object>();
				Map<Integer, Map<String, Integer>> orgAuthMap = new HashMap<Integer, Map<String, Integer>>();
				documentMap.put("login_name", login_name);
				documentMap.put("login_pass", login_pass);
				managerInfo = HttpClientUtil.post(ESB_API_REQUEST_URL + "/orglogin", documentMap);

				if (StringUtils.isNotBlank(managerInfo)) {
					redisUtil.set("OrgManagerLoginInfo:org=" + org_id_sel + ",login_name=" + login_name + ",login_pass="
							+ login_pass, managerInfo);
				}

				resultEntity.setData(managerInfo);
			}
			
			resultEntity.setSuccess(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			resultEntity.setSuccess(false);
			resultEntity.setMessage(ex.getMessage());
		}
		
		return resultEntity;
	}

}
