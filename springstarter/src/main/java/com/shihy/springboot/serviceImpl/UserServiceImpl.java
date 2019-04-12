package com.shihy.springboot.serviceImpl;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.shihy.springboot.constant.Constant;
import com.shihy.springboot.dao.ChargeMapper;
import com.shihy.springboot.dao.UserMapper;
import com.shihy.springboot.datasource.DS;
import com.shihy.springboot.entity.ChargeDetail;
import com.shihy.springboot.entity.Role2Menu;
import com.shihy.springboot.entity.User;
import com.shihy.springboot.entity.UserExample;
import com.shihy.springboot.entity.UserExample.Criteria;
import com.shihy.springboot.excelpojo.UserPojo;
import com.shihy.springboot.redis.RedisUtil;
import com.shihy.springboot.service.UserService;
import com.shihy.springboot.task.UserTask;
import com.shihy.springboot.utils.CommonUtils;
import com.shihy.springboot.utils.Page;
import com.shihy.springboot.utils.ReturnResult;

import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 
 * @data 2019年3月29日 下午4:36:26
 *
 */
@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
@PropertySource({"classpath:config.properties"})
public class UserServiceImpl implements UserService, RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
	@Resource
	private UserMapper userMapper;
	@Resource
	private ChargeMapper chargeMapper;
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private RabbitTemplate delayrabbitTemplate;
	@Resource
	private RedisUtil redisUtil;
    @Resource
    private ExecutorService executorService;
	
	@Value("${TOKEN_EXPIR_TIME}")
	private String TOKEN_EXPIR_TIME;
	@DS("master")
	@Override
	public Integer add(User user) throws Exception {
		return userMapper.insert(user);
	}
	@DS("master")
	@Override
	public List<User> getUserList() {
		return userMapper.getUserList();
	}
	@DS("master")
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
	@DS("master")
	@Override
	public void addUser(User user) throws Exception {
		Map<String, Object> messageMap = new HashMap<String, Object>(16);
		messageMap.put("user_name", user.getUser_name());
		messageMap.put("user_pass", user.getUser_pass());
		messageMap.put("sex", user.getSex());
		Date birthday = user.getBirthday();
		String birthdayStr = CommonUtils.dateFormat(birthday, Constant.YYMMDD);
		messageMap.put("birthday", birthdayStr);
		
		JSONObject json = (JSONObject) JSON.toJSON(messageMap);
		log.info("-----------------------------------------rabbitTemplate.convertAndSend----------------------------------------------------");
		log.info("convertAndSend fanoutExchange message :" + json);
		JSONObject messageJson = new JSONObject();
		messageJson.put("key", messageMap);
		rabbitTemplate.convertAndSend("fanoutExchange","", user);
	}
	@DS("master")
	@Override
	public void insertUser(User user) {
		//Entity转JSON to String
		String jsonString = JSONObject.toJSONString(user);
		String userId = String.valueOf(user.getUser_id());
		boolean flag = redisUtil.hset(userId, "name", jsonString);
		log.info("insertUser flag :" + flag);
	}
	@DS("master")
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
	@DS("master")
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
	@DS("master")
	@Override
	public Integer getUserInfoByName(String name) {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo(name);
		int count = userMapper.countByExample(example);
		return count;
	}
	@DS("master")
	@Override
	public Integer updateUser(User user) {
		int count = userMapper.updateByPrimaryKey(user);
		return count;
	}
	@DS("master")
	@Override
	public Map<String, Object> getUserInfoByUserId(Integer userId) {
		return userMapper.getUserInfoByUserId(userId);
	} 	 
	@DS("master")
	@SuppressWarnings("rawtypes")
	@Override
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
	@DS("master")
	@Override
	public Map<String, Object> getUserRoleMenuInfo(Integer userId) {
		return userMapper.getUserRoleMenuInfo(userId);
	}
	@DS("master")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<String, Object>> getList(String name) throws InterruptedException, ExecutionException {
		ExecutorService cachedThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), new ThreadPoolExecutor.DiscardOldestPolicy());
		//for(int i = 0; i < 5; i++ ) {
			UserTask task = new UserTask(name);
			Future future = cachedThreadPool.submit(task);
			List<Map<String, Object>> list = (List<Map<String, Object>>) future.get();
		//}
		return list;
	}
	@DS("master")
	@Override
	public void insertByBatch(List<UserPojo> list) throws Exception {
		userMapper.insertByBatch(list);
	}

	@Override
	public boolean expire(String key, long time) {
		return redisUtil.expire(key, Long.parseLong(TOKEN_EXPIR_TIME));
	}
	@Override
	public User getUserByUser(Integer userId, String userName, Integer sex, Date birthday) {
		return userMapper.getUserByUserId(userId, userName, sex, birthday);
	} 
	
	@Override
	public Map<String, Object> getUserMapByUser(Integer userId, String userName, Integer sex, Date birthday) {
		return userMapper.getUserMapByUserId(userId, userName, sex, birthday);
	}
	@Override
	public List<Map<String, Object>> getUserRoleMenuListInfo(Integer userId) {
		/***
		 * SQL没有日期格式化
		 */
		List<Map<String,Object>> userRoleMenuList = userMapper.getUserRoleMenuListInfo(userId);
		if (null != userRoleMenuList && userRoleMenuList.size() > 0) {
			for (Map<String, Object> map : userRoleMenuList) {
				 Timestamp createTime = (Timestamp) map.get("create_time"); 
				 String time = CommonUtils.dateFormat(createTime, null);
				 map.put("create_time", time);
			}
		}
		return userMapper.getUserRoleMenuListInfo(userId);
	}
	@Override
	public List<Map<String, Object>> getUserRoleMenuByRoleId(Integer roleId) {
		return userMapper.getUserRoleMenuByRoleId(roleId);
	}
	@Override
	public void updateUserMenuInfo(Integer isDel, Integer roleId, Integer menuId) {
		userMapper.updateUserMenuInfo(isDel, roleId, menuId);
		
	}
	@Override
	public ReturnResult getUpdateUserRoleMenuInfo(Integer roleId, String menuIds) {
		List<Map<String, Object>> userRoleMenu = userMapper.getUserRoleMenuByRoleId(roleId);
		/***
		 * 方法一:
		 * 将原来角色对应的菜单删除，将要重新添加菜单逐条添加，如果添加的菜单在之前被删除过
		 * 那么将原来删除的菜单恢复（即：将is_del的值 1变为0）
		 */
		if (null != userRoleMenu && userRoleMenu.size() > 0) {
			for (Map<String, Object> menu : userRoleMenu) {
				Integer menuId = (Integer) menu.get("menu_id");
				//ids.add(menuId);
				//删除原有的菜单
				userMapper.updateUserMenuInfo(Constant.IS_DEL_YES, roleId, menuId);
			}
		}
		List<Integer> menuIdList = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(menuIds)) {
			if (menuIds.contains(Constant.COMMA)) {
				String[] split = menuIds.split(Constant.COMMA);
				for (int i = 0; i < split.length; i++) {
					menuIdList.add(Integer.parseInt(split[i]));
				}
			} else {
				menuIdList.add(Integer.parseInt(menuIds));
			}
		}
		if (menuIdList != null && menuIdList.size() > 0) {
			for (Integer id : menuIdList) {
				List<Map<String, Object>> list = userMapper.getRoleMenuInfoByRoleIdAndMenuId(Constant.IS_DEL_YES, roleId, id);
				if (list != null && list.size() > 0) {
					//表示存在以前存在改菜单，之后被删除了，还原原来菜单
					userMapper.updateUserMenuInfo(Constant.IS_DEL_NO, roleId, id);
				} else {
					//表示不存在该菜单，添加
					Role2Menu role2Menu = new Role2Menu();
					role2Menu.setMenu_id(id);
					role2Menu.setRole_id(roleId);
					role2Menu.setIs_del(Constant.IS_DEL_NO);
					role2Menu.setCreate_time(new Date());
					role2Menu.setUpdate_time(new Date());
					userMapper.insertRoleAndMenuInfo(role2Menu);
				}
			}
		}
		/***
		 * 方法二:
		 * 将要重新添加的菜单数组和修改前的菜单数组两者求交集，交集中的菜单不需要添加删除操作，保持不变
		 * 获取添加菜单数组中有的元素但是原来的菜单不存在（即：添加1,2  原有的为2,3 那么1为原来菜单没有的）
		 * 将不存在的菜单进行添加。
		 *获取原来菜单数组中存在但是添加菜单数组中不存在（即：为3），将不存在的菜单进行删除
		 *这样重复修改某几个菜单会出现垃圾数据
		 */
		/*
		//要修改的数组
		List<Integer> list1 = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(menuIds)) {
			if (menuIds.contains(Constant.COMMA)) {
				String[] split = menuIds.split(Constant.COMMA);
				for (int i = 0; i < split.length; i++) {
					list1.add(Integer.parseInt(split[i]));
				}
			} else {
				list1.add(Integer.parseInt(menuIds));
			}
		}
		//原有的数组
		List<Integer> list2 = new ArrayList<Integer>();
		if (null != userRoleMenu && userRoleMenu.size() > 0) {
			for (Map<String, Object> menu : userRoleMenu) {
				Integer menuId = (Integer) menu.get("menu_id");
				list2.add(menuId);
			}
		}
		//原有数组中有，修改的数组没有
		List<Integer> delList = CommonUtils.existence(list2, list1);
		//将delList中的菜单删除
		//TODO
		//修改的数组有，原有数组中没有
		List<Integer> addList = CommonUtils.existence(list1, list2);
		//将addList中的菜单添加
		//TODO
		*/		
		return ReturnResult.success();
	}
	/***
	 * exchange the name of the exchange
	 * routingKey the routing key
	 * message a message to send
	 * messagePostProcessor a processor to apply to the message before it is sentThrows:
	 * "x-delay",10000  延迟10秒接收消息
	 */
	@Override
	public Map<String, Object> delaySendMsg() throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", "0");
		map.put("msg", "Hello World!");
		map.put("success", "true");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("消息发送时间》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》:"+sdf.format(new Date()));
        delayrabbitTemplate.convertAndSend(Constant.DELAY_TEST_EXCHANGE, Constant.DELAY_TEST_QUEUE, map, new MessagePostProcessor() {
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader(Constant.DELAY_HEADER_KEY,10000);
                return message;
            }
        });
		return map;
	}
	@Override
	public void createUnpaidDetail(ChargeDetail chargeDetail, Long addTime) throws Exception {
		//CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
		//创建待支付订单
		chargeMapper.createUnpaidDetail(chargeDetail);
		//缴费时间超时，更改支付状态
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("缴费时间超时，根据待支付订单修改状态 时间为 :"+sdf.format(new Date()));
        log.info("延迟推送信息》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》 订单ID ：" + chargeDetail.getCd_id());
        delayrabbitTemplate.convertAndSend(Constant.DELAY_ORDER_STATUS_EXCHANGE, Constant.DELAY_ORDER_STATUS_QUEUE, chargeDetail.getCd_id(), new MessagePostProcessor() {
        public Message postProcessMessage(Message message) throws AmqpException {
            message.getMessageProperties().setHeader(Constant.DELAY_HEADER_KEY,addTime);
            return message;
        }
    	});
    	/*delayrabbitTemplate.convertSendAndReceive(Constant.DELAY_ORDER_STATUS_EXCHANGE, Constant.DELAY_ORDER_STATUS_QUEUE, chargeDetail.getCd_id(), new MessagePostProcessor() {
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader(Constant.DELAY_HEADER_KEY,10000);
                return message;
            }
        }, correlationId);*/
	}
	
	@PostConstruct
    public void init() {
		//delayrabbitTemplate.setConfirmCallback(this);
		//delayrabbitTemplate.setReturnCallback(this);
    }
	
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		 //System.out.println(message.getMessageProperties().getCorrelationIdString() + " 发送失败");
		
	}
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		/*if (ack) {  
            System.out.println("消息发送成功:" + correlationData);  
        } else {  
            System.out.println("消息发送失败:" + cause);  
        } */
	}
}
