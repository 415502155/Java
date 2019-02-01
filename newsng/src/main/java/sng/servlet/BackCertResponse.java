package sng.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import sng.constant.Consts;
import sng.entity.UserAuthentication;
import sng.entity.UserRegister;
import sng.service.MQService;
import sng.service.TokenService;
import sng.service.UserAuthService;
import sng.util.Constant;
import sng.util.HttpUtils;
import unionpay.acp.sdk.LogUtil;

/**
 * @类 名： BackCertResponse
 * @功能描述： 支付成功后调用认证接口
 * @作者信息： LiuYang
 * @创建时间： 2018年12月12日上午11:28:25
 */
@SuppressWarnings("serial")
public class BackCertResponse extends HttpServlet{

	@Autowired
	private MQService mqService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UserAuthService userAuthService;
	
    private static final String host = "http://idcard3.market.alicloudapi.com";
    private static final String path = "/idcardAudit";
    private static final String method = "GET";
    private static final String PASS = "0";
    private static final String appcode = "4e6853cf919243f1b475a99787b071e3";
    private static final Map<String, String> headers = new HashMap<String, String>();
    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 4e6853cf919243f1b475a99787b071e3
    static {
    	headers.put("Authorization", "APPCODE " + appcode);
    }
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
		super.init();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, String[]> reqParams = req.getParameterMap();
		
		Map<String, String> reqParam = new HashMap<String, String>();
		for (String key : reqParams.keySet()) {
			reqParam.put(key, reqParams.get(key)[0]);
	    }
		String respCode = reqParam.get("respCode");
		if("00".equals(respCode)||"A6".equals(respCode)){
			Map<String, String> map = new HashMap<String, String>();
			String reqReserved = reqParam.get("reqReserved");
			Base64 base64 = new Base64();
			String str = new String(base64.decode(reqReserved), "UTF-8");
			String[] params = str.split("\\|");
			String openid = params[0];
			String org_id = params[1];
			String user_register_id = params[2];
			String certificateCode = params[4];
			String name = params[3];
			map.put("idcard", certificateCode);
			map.put("name",  name);
			Boolean isCertified = userAuthService.isIdNumberExist(certificateCode, Integer.parseInt(org_id), name, Constant.IDENTITY_STUDENT);
			if(!isCertified){
				try {
			    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, map);
			    	String result = EntityUtils.toString(response.getEntity());
			    	JSONObject obj = new JSONObject(result);
			    	//{"showapi_res_error":"","showapi_res_id":"4d89fd8186ed4f349519a975262fb000","showapi_res_code":0,"showapi_res_body":{"birthday":"1990-01-01","msg":"匹配","code":0,"sex":"M","address":"天津市南开区","ret_code":0}}
			        //{"showapi_res_error":"","showapi_res_id":"07a10c1fc4cc40c1b136a242134d2c2d","showapi_res_code":0,"showapi_res_body":{"msg":"无此身份证号码","ret_code":0,"code":2}}
			        //{"showapi_res_error":"","showapi_res_id":"35ac58ff0dce46d8bbbf40b9301d2665","showapi_res_code":0,"showapi_res_body":{"msg":"身份证与姓名不匹配","ret_code":0,"code":1}}
					if(((JSONObject)obj.get("showapi_res_body")).get("code").toString().equals(PASS)){
						UserRegister ur = userAuthService.certify(user_register_id,name,certificateCode,Constant.CARD_TYPE_IDCARD,openid,null,Consts.AUTH_TYPE1);
						try {
							String template_id = tokenService.getSNGWXTemplateId(org_id, Constant.TEMPLATE_NAME_REAL_NAME_CERT_NOTICE);
							Map<String, Object> messageMap = new HashMap<String, Object>();
							messageMap.put("mobile", ur.getTelephone());
							messageMap.put("content", String.format(Constant.MOBILE_MESSAGE_CERTIFICATION_NOTICE,"已通过",name,certificateCode));
							if(StringUtils.isNotBlank(openid)&&StringUtils.isNotBlank(template_id)){//WX
								messageMap.put("open_id", openid);
								messageMap.put("templateId", template_id);
								messageMap.put("org_id", org_id);
								messageMap.put("first", "尊敬的家长，您已认证成功！");
								messageMap.put("keyword1", name);
								messageMap.put("keyword2", Constant.sdf.format(new Date()));
							}
							mqService.sendMessage("send_message_exchange", null, messageMap);
						} catch (Exception e) {
						}
					}else{
						//扣费成功，认证失败，只保存认证记录
						UserAuthentication ua = new UserAuthentication();
						ua.setAuthStatus(Constant.NO);
						ua.setAuthTime(new Date());
						ua.setTxnAmt(Constant.CERTIFY_FEE);
						ua.setType(Consts.AUTH_TYPE1);
						ua.setUserRegisterId(Integer.parseInt(user_register_id));
						userAuthService.saveUserAuthentication(ua);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
		}
		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
		//返回给银联服务器http 200  状态码
		resp.getWriter().print("ok");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		this.doPost(req, resp);
	}
}
