package com.shihy.springboot.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.netflix.infix.lang.infix.antlr.EventFilterParser.boolean_expr_return;
import com.shihy.springboot.entity.User;
import com.shihy.springboot.excelpojo.UserPojo;
import com.shihy.springboot.utils.Page;

public interface UserService {
	/***
	 * 
	 * @Description: TODO
	 * @param @param user
	 * @param @return
	 * @param @throws Exception   
	 * @return Integer  
	 * @throws
	 * @date 2019年1月14日
	 */
	Integer add(User user) throws Exception;
	/***
	 * 
	 * @Description: TODO
	 * @param @param user
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 * @date 2019年1月14日
	 */
	void addUser(User user) throws Exception;
	/***
	 * 
	 * @Description: TODO
	 * @param @return   
	 * @return List<User>  
	 * @throws
	 * @date 2019年1月14日
	 */
	List<User> getUserList();
	/**
	 * 
	 * @Description: TODO
	 * @param page
	 * @return
	 * @throws Exception   
	 * @return List<User>  
	 * @throws @throws
	 * @date 2019年1月14日
	 */
	List<User> getUserListByPage(Page page)  throws Exception;
	
	User getUserInfo(Integer userId);
	
	void insertUser(User user);
	
	User getUserInfoByRedis(Integer userId);
	
	void putUserListToRedis() throws Exception;
	
	List<User> getUserListFromRedis() throws Exception;
	
	User getUserInfoByNameAndPass(String name, String pass);
	
	Integer getUserInfoByName(String name);
	
	Integer updateUser(User user);
	
	Map<String, Object> getUserInfoByUserId(Integer userId);
	
	@SuppressWarnings("rawtypes")
	Map<String, Object> getUserInfoByMap(Map map);
	
	/***
	 * 将用户登录token存放redis
	 */
	@SuppressWarnings("rawtypes")
	void putToken(Map map);
	
	@SuppressWarnings("rawtypes")
	Map getUserInfo(String userId);
	
	Map<String, Object> getUserRoleMenuInfo(Integer userId);

	List<Map<String, Object>> getList(String name) throws InterruptedException, ExecutionException;
	/***
	 * 
	 * @Description: EXCEL 批量导入用户
	 * @param list   
	 * @return void  
	 * @throws @throws
	 */
	void insertByBatch(List<UserPojo> list) throws Exception;
	/***
	 * 
	 * @Description: 设置redis的key过期时间
	 * @param key
	 * @param time (秒)
	 * @return   
	 * @return boolean  
	 * @throws @throws
	 */
	boolean expire(String key, long time);
}
