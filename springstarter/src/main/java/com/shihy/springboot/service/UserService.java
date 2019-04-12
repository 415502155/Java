package com.shihy.springboot.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.shihy.springboot.entity.ChargeDetail;
import com.shihy.springboot.entity.User;
import com.shihy.springboot.excelpojo.UserPojo;
import com.shihy.springboot.utils.Page;
import com.shihy.springboot.utils.ReturnResult;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 
 * @data 2019年3月27日 下午3:30:35
 *
 */
public interface UserService {
	/***
	 * @param @param user
	 * @param @return
	 * @param @throws Exception   
	 * @return Integer  
	 * @throws
	 * @date 2019年1月14日
	 */
	Integer add(User user) throws Exception;
	/***
	 * @param @param user
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 * @date 2019年1月14日
	 */
	void addUser(User user) throws Exception;
	/***
	 * @param @return   
	 * @return List<User>  
	 * @throws
	 * @date 2019年1月14日
	 */
	List<User> getUserList();
	/**
	 * @param page
	 * @return
	 * @throws Exception   
	 * @return List<User>  
	 * @throws @throws
	 * @date 2019年1月14日
	 */
	List<User> getUserListByPage(Page page)  throws Exception;
	/***
	 * @param @param userId
	 * @param @return
	 * @return User  
	 * @throws @throws
	 */
	User getUserInfo(Integer userId);
	/***
	 * @param @param user
	 * @return void  
	 * @throws @throws
	 */
	void insertUser(User user);
	/***
	 * @param @param userId
	 * @param @return
	 * @return User  
	 * @throws @throws
	 */
	User getUserInfoByRedis(Integer userId);
	/***
	 * @param @throws Exception
	 * @return void  
	 * @throws @throws
	 */
	void putUserListToRedis() throws Exception;
	/***
	 * @param @return
	 * @param @throws Exception
	 * @return List<User>  
	 * @throws @throws
	 */
	List<User> getUserListFromRedis() throws Exception;
	/***
	 * @param @param name
	 * @param @param pass
	 * @param @return
	 * @return User  
	 * @throws @throws
	 */
	User getUserInfoByNameAndPass(String name, String pass);
	/***
	 * @param @param name
	 * @param @return
	 * @return Integer  
	 * @throws @throws
	 */
	Integer getUserInfoByName(String name);
	/***
	 * @param @param user
	 * @param @return
	 * @return Integer  
	 * @throws @throws
	 */
	Integer updateUser(User user);
	/***
	 * @param @param userId
	 * @param @return
	 * @return Map<String,Object>  
	 * @throws @throws
	 */
	Map<String, Object> getUserInfoByUserId(Integer userId);
	/***
	 * @param @param map
	 * @param @return
	 * @return Map<String,Object>  
	 * @throws @throws
	 */
	@SuppressWarnings("rawtypes")
	Map<String, Object> getUserInfoByMap(Map map);
	/***
	 * 将用户登录token存放redis
	 */
	@SuppressWarnings("rawtypes")
	void putToken(Map map);
	/***
	 * @param @param userId
	 * @param @return
	 * @return Map  
	 * @throws @throws
	 */
	@SuppressWarnings("rawtypes")
	Map getUserInfo(String userId);
	/***
	 * @param @param userId
	 * @param @return
	 * @return Map<String,Object>  
	 * @throws @throws
	 */
	Map<String, Object> getUserRoleMenuInfo(Integer userId);
	/***
	 * @param @throws InterruptedException
	 * @param @throws ExecutionException
	 * @return List<Map<String,Object>>  
	 * @throws @throws
	 */
	List<Map<String, Object>> getList(String name) throws InterruptedException, ExecutionException;
	/***
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
	/***
	 * @Description: 调用存储过程
	 * @param @param userId
	 * @param @param userName
	 * @param @param sex
	 * @param @param birthday
	 * @param @return
	 * @return User  
	 * @throws @throws
	 */
	User getUserByUser(Integer userId, String userName, Integer sex, Date birthday);
	/***
	 * @Description: 调用存储过程
	 * @param @param userId
	 * @param @param userName
	 * @param @param sex
	 * @param @param birthday
	 * @param @return
	 * @return Map<String,Object>  
	 * @throws @throws
	 */
	Map<String, Object> getUserMapByUser(Integer userId, String userName, Integer sex, Date birthday);
	/***
	 * @param userId
	 * @param @return
	 * @return List<Map<String,Object>>  
	 * @throws @throws
	 */
	List<Map<String, Object>> getUserRoleMenuListInfo(Integer userId);


	List<Map<String, Object>> getUserRoleMenuByRoleId(Integer roleId);
    
    void updateUserMenuInfo(Integer isDel, Integer roleId, Integer menuId);

    ReturnResult getUpdateUserRoleMenuInfo(Integer roleId, String menuIds);

    Map<String, Object> delaySendMsg() throws Exception;
    /***
     * @Description: 创建待支付订单，将超过缴费时间的订单推送到延迟队列中
     * @param chargeDetail
     * @param addTime
     * @param @throws Exception
     * @return void  
     * @throws
     */
    void createUnpaidDetail(ChargeDetail chargeDetail, Long addTime) throws Exception;
    
}
