package com.shihy.springboot.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.shihy.springboot.constant.Constant;
import com.shihy.springboot.dao.UserMapper;
import com.shihy.springboot.entity.User;
import com.shihy.springboot.entity.UserExample;
import com.shihy.springboot.entity.UserExample.Criteria;
import com.shihy.springboot.excelpojo.UserPojo;
import com.shihy.springboot.redis.RedisUtil;
import com.shihy.springboot.service.UserService;
import com.shihy.springboot.task.UserTask;
import com.shihy.springboot.utils.CommonUtils;
import com.shihy.springboot.utils.Page;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@PropertySource({"classpath:config.properties"})
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
    @Autowired
    private AmqpTemplate rabbitTemplate;
	@Resource
	private RedisUtil redisUtil;
    @Resource
    private ExecutorService executorService;
	
	@Value("${TOKEN_EXPIR_TIME}")
	private String TOKEN_EXPIR_TIME;
	
	@Override
	public Integer add(User user) throws Exception {
		return userMapper.insert(user);
	}

	@Override
	public List<User> getUserList() {
		return userMapper.getUserList();
	}

	@Override
	public List<User> getUserListByPage(Page page)  throws Exception {
		Integer pageSatrtSize = page.getStartSize(page.getPageNum(), page.getPageSize());
		Integer pageSize = page.getPageSize();
		String order = "user_id";
		UserExample example = new UserExample();
		//Criteria criteria = example.createCriteria();
		int count = userMapper.countByExample(example);
		page.setTotalSize(count);
		List<User> userList = userMapper.getUserListByPage(pageSatrtSize, pageSize, order);
		String sexStr = "";
		if (userList != null && userList.size() > 0) {
			for (User user : userList) {
				Date birthday = user.getBirthday();
				String birthdayStr = CommonUtils.dateFormat(birthday, Constant.YYMMDD);
				user.setBirthdayStr(birthdayStr);
				Integer sex = user.getSex() == null ? Constant.SECRECY_SEX : user.getSex();
				if (sex.equals(Constant.SECRECY_SEX)) {
					sexStr = Constant.SECRECY;
				} else if (sex.equals(Constant.MAN_SEX)) {
					sexStr = Constant.MAN;
				} else if (sex.equals(Constant.WOMAN_SEX)) {
					sexStr = Constant.WOMAN;
				}
				user.setSexStr(sexStr);
			}
		}
		return userList;
	}

	@Override
	public void addUser(User user) throws Exception {
		Map<String, Object> messageMap = new HashMap<String, Object>();
		messageMap.put("user_name", user.getUser_name());
		messageMap.put("user_pass", user.getUser_pass());
		messageMap.put("sex", user.getSex());
		Date birthday = user.getBirthday();
		String birthdayStr = CommonUtils.dateFormat(birthday, Constant.YYMMDD);
		messageMap.put("birthday", birthdayStr);
		JSONObject json = CommonUtils.mapToJson(messageMap);
		log.info("-----------------------------------------rabbitTemplate.convertAndSend----------------------------------------------------");
		log.info("convertAndSend fanoutExchange message :" + json);
		JSONObject messageJson = new JSONObject();
		messageJson.put("key", messageMap);
		rabbitTemplate.convertAndSend("fanoutExchange","", user);
	}

	@Override
	public void insertUser(User user) {
		//Entity转JSON to String
		String jsonString = JSONObject.toJSONString(user);
		String userId = String.valueOf(user.getUser_id());
		boolean flag = redisUtil.hset(userId, "name", jsonString);
		log.info("insertUser flag :" + flag);
	}

	@Override
	public User getUserInfo(Integer userId) {
		/*UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);*/
		User user = userMapper.selectByPrimaryKey(userId);
		this.insertUser(user);
		return user;
	}

	@Override
	public User getUserInfoByRedis(Integer userId) {
		String userInfo = (String) redisUtil.hget(String.valueOf(userId), "name");
		User user = JSON.parseObject(userInfo, User.class);
		log.info("key:user  item:name ---- userInfo :" + userInfo);
		return user;
	}

	@Override
	public void putUserListToRedis() throws Exception {
		List<User> userList = this.getUserList();
		String jsonString = JSONObject.toJSONString(userList);
		boolean flag = redisUtil.hset("user", "list", jsonString);
		log.info("putUserListToRedis flag 添加用户列表信息到redis是否成功  :" + flag);
	}

	@Override
	public List<User> getUserListFromRedis() throws Exception {
		String userList = (String) redisUtil.hget("user", "list");
		List<User> list = JSON.parseObject(userList, new TypeReference<List<User>>(){});
		return list;
	}

	@Override
	public User getUserInfoByNameAndPass(String name, String pass) {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo(name).andUserPassEqualTo(pass);
		List<User> userList = userMapper.selectByExample(example);
		User user = new User();
		if (userList != null && userList.size() > 0) {
			user = userList.get(0);
		} else {
			user = null;
		}
		return user;
	}

	@Override
	public Integer getUserInfoByName(String name) {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo(name);
		int count = userMapper.countByExample(example);
		return count;
	}

	@Override
	public Integer updateUser(User user) {
		int count = userMapper.updateByPrimaryKey(user);
		return count;
	}

	@Override
	public Map<String, Object> getUserInfoByUserId(Integer userId) {
		return userMapper.getUserInfoByUserId(userId);
	} 	 
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getUserInfoByMap(Map map) {
		return userMapper.getUserInfoByMap(map);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void putToken(Map map) {
		String userId = (String) map.get("user_id");
		redisUtil.hmset(userId, map, Long.parseLong(TOKEN_EXPIR_TIME));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getUserInfo(String userId) {
		Map map = redisUtil.hmget(userId);
		return map;
	}

	@Override
	public Map<String, Object> getUserRoleMenuInfo(Integer userId) {
		return userMapper.getUserRoleMenuInfo(userId);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<String, Object>> getList(String name) throws InterruptedException, ExecutionException {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		//for(int i = 0; i < 5; i++ ) {
			UserTask task = new UserTask(name);
			Future future = cachedThreadPool.submit(task);
			List<Map<String, Object>> list = (List<Map<String, Object>>) future.get();
		//}
		return list;
	}

	@Override
	public void insertByBatch(List<UserPojo> list) throws Exception {
		userMapper.insertByBatch(list);
	}

	@Override
	public boolean expire(String key, long time) {
		return redisUtil.expire(key, Long.parseLong(TOKEN_EXPIR_TIME));
	} 
	

}
