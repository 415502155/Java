package tk.mybatis.springboot.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import tk.mybatis.springboot.mapper.UserInfoMapper;
import tk.mybatis.springboot.model.UserInfo;
import tk.mybatis.springboot.model.UserRoleMenuInfo;
import tk.mybatis.springboot.util.MD5;
import tk.mybatis.springboot.util.ReturnMsg;

@RestController
@RequestMapping(value="/user")
public class UserController {
	
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	UserInfoMapper userInfo;
	
	/***
	 * login
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public JSONObject login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
//		ServletInputStream inputStream = request.getInputStream();
//		String param = IOUtils.toString(inputStream);
//		JSONObject json = JSONObject.parseObject(param);//转为json
//		String nString = String.valueOf(json.get("USERNAME"));
		JSONObject backJson = new JSONObject();
		String pass = "123456789";
		String mpass = new MD5().digest(pass, "MD5");
		String userName = "shy";
		if(null == pass || userName == null) {
			return backJson;
		}
		List<UserInfo> list = userInfo.findUserInfo();
		if(list.isEmpty()) {
			return backJson;
		}
		for(UserInfo user: list) {
			if(userName.equals(user.getUser_name())  && mpass.equals(user.getUser_pass()) ) {
				
				session.setAttribute("user", user.getUser_name());
				session.setAttribute("pass", user.getUser_pass());
				logger.info("session:"+session.getAttribute("user")+",pass:"+session.getAttribute("pass"));
				backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
				backJson.put("MSG", ReturnMsg.SUCCESS.getMsg());
				return backJson;
			}
		}
		return backJson;
	}
	
	@RequestMapping(value="/getinfo")
	@ResponseBody
	public JSONObject getUserInfo(HttpServletRequest request) {
		JSONObject backJson = new JSONObject();
		String userName=(String) request.getSession().getAttribute("user");
		logger.info("get session value:"+userName);
		List<UserInfo> urMenuInfos = userInfo.getUserRoleMenu1(userName);
		JSONArray array = new JSONArray();
		for(UserInfo urm : urMenuInfos) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("user_id", urm.getUser_id());
			jsonObject.put("user_name", urm.getUser_name());
			jsonObject.put("address", urm.getAddress());
			jsonObject.put("sex", urm.getSex());
			jsonObject.put("role_id", urm.getRole_id());
			jsonObject.put("user_pass", urm.getUser_pass());
			array.add(jsonObject);
		}
		backJson.put("LIST", array);
		logger.info("BackJson:"+backJson);
		return backJson;
	}
	
	@RequestMapping(value="/getinfo1")
	@ResponseBody
	public JSONObject getUserRoleMenuInfo(HttpServletRequest request) {
		JSONObject backJson = new JSONObject();
		//String userName=(String) request.getSession().getAttribute("user");
		//logger.info("get session value:"+userName);
		String userName = "shy"; 
		List<UserRoleMenuInfo> urMenuInfos = null;
		try {
			urMenuInfos = userInfo.getUserRoleMenu(userName);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("EX:",e);
		}
		
		logger.info("size:"+urMenuInfos.size());
		if(urMenuInfos.isEmpty() || urMenuInfos.size() == 0) {
			backJson.put("MSG", "urMenuInfos is null");
			backJson.put("CODE", 0);
			return backJson;
		}
		JSONArray array = new JSONArray();
		for(UserRoleMenuInfo urm : urMenuInfos) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("role_id", urm.getRole_id());
			jsonObject.put("menu_id", urm.getMenu_id());
			jsonObject.put("mr_id", urm.getMr_id());
			jsonObject.put("p_id", urm.getP_id());
			jsonObject.put("menu_name", urm.getMenu_name());
			array.add(jsonObject);
		}
		backJson.put("LIST", array);
		logger.info("BackJson:"+backJson);
		return backJson;
	}
}
