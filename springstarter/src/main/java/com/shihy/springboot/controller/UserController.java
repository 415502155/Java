package com.shihy.springboot.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shihy.springboot.constant.Constant;
import com.shihy.springboot.entity.ResourceConfig;
import com.shihy.springboot.entity.User;
import com.shihy.springboot.excelpojo.UserPojo;
import com.shihy.springboot.service.UserService;
import com.shihy.springboot.utils.CommonUtils;
import com.shihy.springboot.utils.EasyExcelUtil;
import com.shihy.springboot.utils.Page;
import com.shihy.springboot.utils.ReturnMsg;
import com.shihy.springboot.utils.ReturnResult;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 查询、分页、添加、redis、resourceConfig
 * @data 2019年1月17日 上午11:11:12
 *
 */
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	@Resource
	private UserService userService;
	@Resource
	private ResourceConfig resourceConfig;
	/***
	 * 
     * @param pageNum  页码
     * @param pageSize 每页显示数量
	 * @param userName 排序字段
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/list")
	public ReturnResult getUserList(Integer pageNum, Integer pageSize, String userName) {
		PageHelper.startPage(pageNum, pageSize);
		PageHelper.orderBy(userName);
		List<User> userList = null;
		long total = -1;
		try {
			userList = userService.getUserList();
			if (userList != null && userList.size() > 0) {
				for (User user : userList) {
					Date birthday = user.getBirthday();
					String birthdayStr = CommonUtils.dateFormat(birthday, Constant.YYMMDD);
					log.info("birthdayStr :" + birthdayStr);
					Date birthdayDate = CommonUtils.stringToDate(birthdayStr, Constant.YYMMDD);
					log.info("birthdayDate :" + birthdayDate);
					user.setBirthday(birthdayDate);
				}
				PageInfo<User> pageUser = new PageInfo<User>(userList);
				total = pageUser.getTotal();
				userList = pageUser.getList();
				log.info("total :" + total);
			}
		} catch (Exception e) {
			ReturnResult.error(ReturnMsg.ERROR.getCode(), ReturnMsg.ERROR.getMsg());
		}
		return ReturnResult.success(total, userList);
	}
	
	/***
	 * 
     * @param pageNum  页码
     * @param pageSize 每页显示数量
	 * @param userName 排序字段
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/page")
	public ReturnResult getUserPage(Integer pageNum, Integer pageSize) {
		Page page = new Page();
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		List<User> userList = null;
		try {
			userList = userService.getUserListByPage(page);
		} catch (Exception e) {
			log.info("getUserPage Exception : " + e);
			e.printStackTrace();
			return ReturnResult.error(ReturnMsg.ERROR.getCode(), ReturnMsg.ERROR.getMsg());
		}
		return ReturnResult.success(page.getTotalSize(), page.getTotalPage(page.getTotalSize(), pageSize), userList);
	}
	
	/***
	 * 
	 * @param userName
	 * @param userPass
	 * @param sex
	 * @param birthday
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/add")
	public ReturnResult addUser(String userName, String userPass, Integer sex, String birthday) throws ParseException {
		User user = new User();
		user.setUser_name(userName);
		user.setUser_pass(userPass);
		user.setSex(sex);
		Date birthdayDate = CommonUtils.stringToDate(birthday, "yyyy-MM-dd");
		user.setBirthday(birthdayDate);
		user.setIs_del(Constant.IS_DEL_NO);
		user.setCreate_time(new Date());
		user.setUpdate_time(new Date());
		try {
			userService.add(user);
			Integer userId = user.getUser_id();
			log.info("userId :" + userId);
		} catch (Exception e) {
			log.info("addUser Exception : " + e);
			e.printStackTrace();
			return ReturnResult.error(ReturnMsg.ERROR.getCode(), ReturnMsg.ERROR.getMsg());
		}
		return ReturnResult.success();
	}
	
	/***
	 * @deprecated 添加用户  将添加的用户信息添加到队列中fanout.user,key(fanoutExchange)
	 * @param userName
	 * @param userPass
	 * @param sex
	 * @param birthday
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/addu")
	public ReturnResult addUserQueue(String userName, String userPass, Integer sex, String birthday) throws ParseException {
		User user = new User();
		try {
			user.setUser_name(userName);
			user.setUser_pass(userPass);
			user.setSex(sex);
			Date birthdayDate = CommonUtils.stringToDate(birthday, "yyyy-MM-dd");
			user.setBirthday(birthdayDate);
			userService.addUser(user);
		} catch (Exception e) {
			log.info("addUsers Exception : " + e);
			e.printStackTrace();
			return ReturnResult.error(ReturnMsg.ERROR.getCode(), ReturnMsg.ERROR.getMsg());
		}
		return ReturnResult.success();
	}
	
	/***
	 * @param
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/info")
	public ReturnResult insertUser(Integer userId) {
		User userInfo = userService.getUserInfo(userId);
		log.info("----------------------------------------------user :" + userInfo.getUser_id() + "----- param :" + userId);
		return ReturnResult.success(userInfo);
	}
	
	/***
	 * @param
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/info1")
	public ReturnResult getUserByRedis(Integer userId) {
		User user = userService.getUserInfoByRedis(userId);
		return ReturnResult.success(user);
	}
	/***
	 * @Description: 存储用户信息 to redis
	 * @param
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/putUserList")
	public ReturnResult putUserListToRedis(Integer userId) {
		try {
			userService.putUserListToRedis();
		} catch (Exception e) {
			e.printStackTrace();
			log.info("putUserListToRedis Ex :" + e);
			return ReturnResult.error(ReturnMsg.ERROR.getCode(), ReturnMsg.ERROR.getMsg());
		}
		return ReturnResult.success();
	}
	/***
	 * 
	 * @Description: 获取用户信息 from redis
	 * @param userId
	 * @return   
	 * @return ReturnResult  
	 * @throws @throws
	 * @date 2019年1月17日
	 */
	@RequestMapping(value = "/getUserList")
	public ReturnResult getUserListFromRedis(Integer userId) {
		List<User> userList = null;
		try {
			userList = userService.getUserListFromRedis();
		} catch (Exception e) {
			e.printStackTrace();
			log.info("getUserListFromRedis Ex :" + e);
			return ReturnResult.error(ReturnMsg.ERROR.getCode(), ReturnMsg.ERROR.getMsg());
		}
		return ReturnResult.success(userList);
	}
	/***
	 * 
	 * @Description: 获取classpath：resource.properties属性文件内容
	 * @return   
	 * @return ReturnResult  
	 * @throws @throws
	 * @date 2019年1月17日
	 */
	@RequestMapping(value = "/getResource")
	public ReturnResult getResource() {
		if (resourceConfig == null) {
			return ReturnResult.error(ReturnMsg.ERROR.getCode(), ReturnMsg.ERROR.getMsg());
		}
		
		String res = "";
		try {
			String username = resourceConfig.getUsername();
			String usermobile = resourceConfig.getUsermobile();
			String useraddress = resourceConfig.getUseraddress();
			Integer usersex = resourceConfig.getUsersex();
			String sex = Constant.SECRECY;
			if (usersex.equals(Constant.MAN_SEX)) {
				sex = Constant.MAN;
			}
			if (usersex.equals(Constant.WOMAN_SEX)) {
				sex = Constant.WOMAN;
			}
			if (usersex.equals(Constant.SECRECY_SEX)) {
				sex = Constant.SECRECY;
			}
			log.info("Resource info : { username :" + username + " usermobile :" + usermobile + " useraddress :" + useraddress + " sex :" + sex + " }");
			res = resourceConfig.toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.info("getResource Ex :" + e);
			return ReturnResult.error(ReturnMsg.ERROR.getCode(), ReturnMsg.ERROR.getMsg());
		}
		return ReturnResult.success(res);
	}
	
	//
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/info2")
	public ReturnResult getUserByMap(Integer userId) {
		Map map = new HashMap();
		map.put("user_id", userId);
		Map user = userService.getUserInfoByMap(map);
		return ReturnResult.success(user);
	}
	
	@RequestMapping(value = "/info3")
	public ReturnResult getUserListByThread(String name) throws InterruptedException, ExecutionException {
		log.info("param :" + name);
		List<Map<String, Object>> list = userService.getList(name);
		return ReturnResult.success(list);
	}
	
	@RequestMapping(value = "/export")
	public ReturnResult exportExcel(HttpServletRequest request, HttpServletResponse response) throws InterruptedException, ExecutionException {
		List<User> userList = userService.getUserList();
		List<UserPojo> list = new ArrayList<UserPojo>();
		if (userList != null && userList.size() > 0) {
			for (User user : userList) {
				UserPojo pojo = new UserPojo();
				pojo.setUser_name(user.getUser_name());
				pojo.setSex(user.getSex());
				pojo.setBirthday(CommonUtils.dateFormat(user.getBirthday(), Constant.YYMMDDHHMMSS));
				list.add(pojo);
			}
		}
		EasyExcelUtil.exportExcel(list, "用户列表", "用户列表", UserPojo.class, "用户列表.xls", request, response);
		return ReturnResult.success();
	}
	
	@RequestMapping(value = "/import")
	public ReturnResult importExcel(HttpServletRequest request, HttpServletResponse response, MultipartFile file) throws Exception {
		try {
			List<UserPojo> importExcel = EasyExcelUtil.importExcel(file, 1, 1, UserPojo.class);
			for (UserPojo pojo : importExcel) {
				pojo.setUser_pass(Constant.INITIALIZE_PASSWORD);
				pojo.setIs_del(Constant.IS_DEL_NO);
				pojo.setCreate_time(new Date());
				/***
				 * 根据用户名称查询是否存在该用户
				 */
				Integer flag = userService.getUserInfoByName(pojo.getUser_name());
				if (flag > Constant.ZERO) {
					return ReturnResult.error(ReturnMsg.EXCEL_IMPORT_EX1.getCode(), pojo.getUser_name() + " :" + ReturnMsg.EXCEL_IMPORT_EX1.getMsg());
				}
				/***
				 * 注： 可以将Excel的list分为 ErrorList和CurrentList
				 *    当导入的用户存在，将该条信息add到ErrorList，不存在的add到CurrentList
				 * 将CurrentList添加到数据库中，将ErrorList中的数据写入到Excel中，并将该条的错误信息写入并下载
				 */
				
			}
			log.info("导入的行数为 ：" + importExcel.size());
			userService.insertByBatch(importExcel);
			userService.expire("user_id", Constant.ZERO);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("importExcel Exception :" + e);
		}
		return ReturnResult.success();
	}
}
