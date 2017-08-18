package tk.mybatis.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import tk.mybatis.springboot.mapper.RoleMapper;
import tk.mybatis.springboot.mapper.UserInfoMapper;
import tk.mybatis.springboot.model.MenuInfo;
import tk.mybatis.springboot.model.RoleInfo;
import tk.mybatis.springboot.model.RoleMenuInfo;
import tk.mybatis.springboot.model.UserInfo;
import tk.mybatis.springboot.util.ReturnMsg;

@RestController
@RequestMapping(value="/role")
public class RoleController {
	Logger logger = Logger.getLogger(RoleController.class);
	@Autowired
	RoleMapper roleMapper;
	@Autowired
	UserInfoMapper userInfo;
	
	@RequestMapping(value="/info")
	@ResponseBody
	public JSONObject getRole() {
		JSONObject backJson = new JSONObject();
		List<RoleInfo> rList = roleMapper.getRoleList();
		JSONArray array = new JSONArray();
		for(RoleInfo r : rList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("role_id", r.getRole_id());
			jsonObject.put("role_name", r.getRole_name());
			array.add(jsonObject);
		}
		backJson.put("ROLE", array);
		logger.info("backJson:"+backJson);
		return backJson;
	}
	
	@RequestMapping(value="/user")
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
	/***
	 * 更改某用户的角色
	 */
	@RequestMapping(value="/updaterole")
	@ResponseBody
	public JSONObject updateRole() {
		JSONObject backJson = new JSONObject();
		UserInfo userInfo = new UserInfo();
		userInfo.setRole_id(3);
		userInfo.setUser_name("Java");
		int status = roleMapper.updateRole(userInfo);
		backJson.put("STATUS", status);
		logger.info("backJson:"+backJson);
		return backJson;
	}
	/***
	 * update menu
	 * @return
	 */
	@RequestMapping(value="/updateall")
	@ResponseBody
	public JSONObject updateMenu() {
		JSONObject backJson = new JSONObject();
		List<RoleMenuInfo> roleMenu = new ArrayList<RoleMenuInfo>();
		List<MenuInfo> menu = roleMapper.getMenuId();
		//获取所有的菜单，根据
		for(MenuInfo m:menu) {
			RoleMenuInfo param = new RoleMenuInfo();
			param.setMenu_id(m.getMenu_id());
			param.setRole_id(1);
			List<RoleMenuInfo> rMenuInfos = roleMapper.getMRId(param);
			if(rMenuInfos.size() == 0) {
				RoleMenuInfo rm = new RoleMenuInfo();
				rm.setMenu_id(m.getMenu_id());
				rm.setRole_id(1);
				roleMenu.add(rm);
				logger.info("ID:"+m.getMenu_id());
			}			
		}
		
		try {
			int status = roleMapper.addAll(roleMenu);
			logger.info("STATUS:"+status);
			backJson.put("STATUS", status);
			backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
			backJson.put("MSG", ReturnMsg.SUCCESS.getMsg());
		} catch (Exception e) {
			backJson.put("CODE", ReturnMsg.SALE_TICKET_EXCEPTION.getCode());
			backJson.put("MSG", ReturnMsg.SALE_TICKET_EXCEPTION.getMsg());
			logger.info("EX:",e);
			return backJson;
		}
		
		return backJson;
	}
}
