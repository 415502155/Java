package com.shihy.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.shihy.springboot.constant.Constant;
import com.shihy.springboot.entity.User;
import com.shihy.springboot.service.UserService;
import com.shihy.springboot.utils.CommonUtils;
import com.shihy.springboot.utils.ReturnMsg;
import com.shihy.springboot.utils.ReturnResult;
import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 
 * @data 2019年3月27日 下午3:30:35
 *
 */
@Slf4j
@RestController
@RequestMapping(value = "/login")
public class LoginController {
	
	@Resource
	private UserService userService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/in")
	public ReturnResult login(String name, String pass) {
		log.info("登陆参数为 ： {用户名称 name：" + name + " 密码 pass ：" + pass + " }");
		//根据用户名称查询是否存在该用户
		Integer count = userService.getUserInfoByName(name);
		if (count < Constant.ONE) {
			return ReturnResult.error(ReturnMsg.LOGIN_IN_EX1.getCode(), ReturnMsg.LOGIN_IN_EX1.getMsg());
		}
		//用户和密码不能为空
		if (StringUtils.isBlank(name) || StringUtils.isBlank(pass)) {
			return ReturnResult.error(ReturnMsg.LOGIN_IN_EX3.getCode(), ReturnMsg.LOGIN_IN_EX3.getMsg());
		}
		//根据用户密码校验是否正确
		List<User> userList = userService.getUserList();
		if (userList != null && userList.size() > 0) {
			for (User user : userList) {
				String userName = user.getUser_name();
				String userPass = user.getUser_pass();
				Integer userId = user.getUser_id();
				if (name.equals(userName) && pass.equals(userPass)) {
					Map<String, Object> userRoleMenuInfo = userService.getUserRoleMenuInfo(userId);
					if (null != userRoleMenuInfo) {
						String roleId = String.valueOf(userRoleMenuInfo.get("role_id"));
						String roleName = (String) userRoleMenuInfo.get("role_name");
						String menuId = String.valueOf(userRoleMenuInfo.get("menu_id"));
						String menuName = (String) userRoleMenuInfo.get("menu_name");
						String userInfo = JSONObject.toJSONString(userRoleMenuInfo);
						log.info("登录成功》》》》》》》》》 用户信息 ： " + userInfo);
						//校验通过 //userId+uuid
						String token = userId + "_" + CommonUtils.getUUID();
						user.setToken(token);
						userService.updateUser(user);
						/***
						 * 将登陆信息存储到redis中  并设置信息的过期时间
						 */
						Map map = new HashMap(16);
						map.put("user_id", String.valueOf(user.getUser_id()));
						map.put("user_name", userName);
						map.put("role_id", roleId);
						map.put("role_name", roleName);
						map.put("menu_id", menuId);
						map.put("menu_name", menuName);
						map.put("token", token);
						userService.putToken(map);
						return ReturnResult.success(map);
					} else {//当前用户下没有分配权限，无法登陆成功
						log.info(ReturnMsg.LOGIN_IN_EX4.getMsg());
						return ReturnResult.error(ReturnMsg.LOGIN_IN_EX4.getCode(), ReturnMsg.LOGIN_IN_EX4.getMsg());
					}
					
				} else {
					continue;
				}
			}
			return ReturnResult.error(ReturnMsg.LOGIN_IN_EX2.getCode(), ReturnMsg.LOGIN_IN_EX2.getMsg());
		} else {
			return ReturnResult.error(ReturnMsg.LOGIN_IN_EX1.getCode(), ReturnMsg.LOGIN_IN_EX1.getMsg());
		}
	}

}
