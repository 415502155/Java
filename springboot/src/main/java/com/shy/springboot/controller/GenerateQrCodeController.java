package com.shy.springboot.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.shy.springboot.dao.UserDao;
import com.shy.springboot.entity.User;
import com.shy.springboot.thread.InsertUserCallable;
import com.shy.springboot.utils.CommonUtils;
import com.shy.springboot.utils.QRCodeUtil;
import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;
import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @author sjwy-0001
 *
 */
@Slf4j
@RestController
@RequestMapping(value = "/qrcode")
public class GenerateQrCodeController {
	
	@Resource
	private UserDao userDao;
	
	@RequestMapping(value = "/info")
	@ResponseBody
	public Map<String, Object> getQrCode() throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String udid = UUID.randomUUID().toString();
		// 存放在二维码中的内容
		String text = "http://localhost:8080/login/in?udid=" + udid;
		// 嵌入二维码的图片路径
		String imgPath = "C:/sjwy/testimg/3.jpg";
		// 生成的二维码的路径及名称
		String destPath = "C:/sjwy/testimg/qrcode.jpg";
		//生成二维码
		QRCodeUtil.encode(text, imgPath, destPath, true);
		// 解析二维码
		String str = QRCodeUtil.decode(destPath);
		returnMap.put("code", 200);
		returnMap.put("msg", "success");
		returnMap.put("info", str);
		returnMap.put("url", destPath);
		String returnJson = JSONObject.toJSONString(returnMap);
		//推送消息
		System.out.println("消息為 ：" + returnJson);
		GoEasy goEasy = new GoEasy("https://rest-hangzhou.goeasy.io", "BC-d9feaa2278fb4bd6b93737176f5e1608");//1.rest host 2.common key
	    goEasy.publish("test_channel", returnJson , new PublishListener(){
			@Override
	        public void onSuccess() {
	            System.out.print("消息发布成功。");
	            log.info("消息发布成功。");
	        }
	        @Override
	        public void onFailed(GoEasyError error) {
	            System.out.print("消息发布失败, 错误编码：" + error.getCode() + " 错误信息： " + error.getContent());
	            log.info("消息发布失败, 错误编码：" + error.getCode() + " 错误信息： " + error.getContent());
	        }
		});
		return returnMap;
	}
	
	/***
	 * 生成qrcode并有唯一udid，掃碼之後獲取udid和當前用戶下的信息
	 */
	@RequestMapping(value = "/test")
	@ResponseBody
	public Map<String, Object> getMap() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		return returnMap;
	}
	@RequestMapping(value= "/all")
	@ResponseBody
	public List<User> getUserList() {
		List<User> userList = userDao.getAll();
		return userList;
	}
	
	
	@RequestMapping(value= "/batchinsert")
	@ResponseBody
	public Map<String, Object> batchInsert() {
		List<User> userList = new ArrayList<User>();
		for (int i = 0; i < 10000; i++) {
			User user = new User();
			user.setUser_name("batch" + i);
			user.setUser_pass("administrator" + i);
			user.setSex(1);
			String uuid = UUID.randomUUID().toString().substring(0, 32);
			user.setToken(uuid);
			user.setBirthday(new Date());
			userList.add(user);
		}
		int[] batchInsert = userDao.batchInsert(userList);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("code", 200);
		int failCount = 10000 - batchInsert.length;
		returnMap.put("msg", "添加成功" + batchInsert.length + "条， 失败" + failCount + "条！");
		return returnMap;
	}
	
	@RequestMapping(value= "/insert")
	@ResponseBody
	public Map<String, Object> insertReturnPrimaryKey() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		User user = new User();
		user.setUser_name("企鹅");
		user.setUser_pass("11223344");
		user.setSex(1);
		user.setBirthday(new Date());
		user.setToken(UUID.randomUUID().toString().substring(0, 32));
		int insertReturnPrimaryKey = userDao.insertReturnPrimaryKey(user);
		returnMap.put("code", 200);
		returnMap.put("msg", "添加用户的id为：" + insertReturnPrimaryKey);
		return returnMap;
	}
	
	@RequestMapping(value= "/batchadd")
	@ResponseBody
	public Map<String, Object> batchAdd() throws ParseException {
		List<User> userList = new ArrayList<User>();
		for (int i = 0; i < 80000; i++) {
			User user = new User();
			user.setUser_name("JAVA" + i);
			user.setUser_pass(CommonUtils.getRandomStr(12, 1));
			user.setSex(1);
			user.setToken(CommonUtils.getUUID());
			String birthday = "1999-08-01";
			Date birthdayDate = CommonUtils.stringToDate(birthday, "yyyy-MM-dd");
			user.setBirthday(birthdayDate);
			userList.add(user);
		}
		int[] batchInsert = userDao.batchAdd(userList);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("code", 200);
		int failCount = 80000 - batchInsert.length;
		returnMap.put("msg", "添加成功" + batchInsert.length + "条， 失败" + failCount + "条！");
		//returnMap.put("info", batchInsert);
		return returnMap;
	}
	
	@RequestMapping(value= "/callinfo")
	@ResponseBody
	public Map<String, Object> getUserInfo() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> userInfo = userDao.getUserInfo(111);
		Integer id = (Integer) userInfo.get("id");
		String name = (String) userInfo.get("name");
		returnMap.put("id", id);
    	returnMap.put("name", name);
		return returnMap;
	}
	
	@RequestMapping(value= "/callrecharge")
	@ResponseBody
	public Map<String, Object> callRecharge() {
		//Map<String, Object> returnMap = new HashMap<String, Object>();
		String p_card_no = "787266664567789";
		String p_draw_money = "10000";
		Map<String, Object> userInfo = userDao.callRecharge(p_card_no, p_draw_money);
		/*String card_no = (String) userInfo.get("p_card_no");
		Integer p_result = (Integer) userInfo.get("p_result");
		String p_msg = (String) userInfo.get("p_msg");
		String draw_money = (String) userInfo.get("draw_money");*/
		String str = String.format("Hi,%1$s是个管理员，家庭住址在%2$s， 今年%3$d岁", "admin","天津市南开区咸阳路",30); 
		return userInfo;
	}
	
	@RequestMapping(value= "/crlist")
	@ResponseBody
	public Map<String, Object> crList() throws ParseException, InterruptedException, ExecutionException {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		userDao.queryUserRecord();
		String str = String.format("Hi,%1$s是个管理员，家庭住址在%2$s， 今年%3$d岁", "admin","天津市南开区咸阳路",30); 
		returnMap.put("info", str);
		/***
		 * 添加用户数据
		 */
		List<User> list = new ArrayList<User>();
		for (int i = 0; i < 40000; i++) {
			User user = new User();
			user.setUser_name(CommonUtils.getRandomStr(8, 2));
			user.setUser_pass(CommonUtils.getRandomStr(12, 1));
			user.setSex(1);
			user.setToken(CommonUtils.getUUID());
			String birthday = "1999-08-01";
			Date birthdayDate = CommonUtils.stringToDate(birthday, "yyyy-MM-dd");
			user.setBirthday(birthdayDate);
			list.add(user);
		}
		/***
		 * shaole 4ci xunhuan
		 * 100
		 */
		List<List<User>> averageAssign = averageAssign(list, 100);
		System.out.println("平分份数为 ：" +averageAssign.size());
		int corePoolSize = 100;
		int maximumPoolSize = 100;
		int keepAliveTime = 10;
		TimeUnit unit = TimeUnit.SECONDS;
		ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingDeque<Runnable>());
		for (int i = 0; i < 100; i++) {
			InsertUserCallable userCallable = new InsertUserCallable();
			String name = Thread.currentThread().getName();
			userCallable.setName(name);
			userCallable.setList(averageAssign.get(i));
			Future<Object> future = executor.submit(userCallable);
			//Integer primary = (Integer) future.get();
			//log.info("添加用户返回的主键 ：" + primary);
		}
		return returnMap;
	}
	/***
	 * @deprecated    将一组数据平均分成n组
	 * @param source  要分组的数据源
	 * @param n       平均分成n组
	 * @return
	 */
	public static <T> List<List<T>> averageAssign(List<T> source, int n) {
	    List<List<T>> result = new ArrayList<List<T>>();
	    //  总数：1001  分成 10    余数（remainder）：1       商（number）：100   0304   0607
	    int remainder = source.size() % n;  //(先计算出余数)
	    int number = source.size() / n;  //然后是商
	    int offset = 0;//偏移量
	    for (int i = 0; i < n; i++) {
	        List<T> value = null;
	        if (remainder > 0) {//0*100 + 0, 1*100+1      1*100+1, 2*100+2
	            value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
	            remainder--;
	            offset++;
	        } else {//  1*100+1  2*100+1
	            value = source.subList(i * number + offset, (i + 1) * number + offset);
	        }
	        result.add(value);
	    }
	    return result;
	}

	/**
	 * 将一组数据固定分组，每组n个元素
	 * @param source 要分组的数据源
	 * @param n      每组n个元素
	 * @param <T>
	 * @return
	 */
	public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {

	    if (null == source || source.size() == 0 || n <= 0)
	        return null;
	    List<List<T>> result = new ArrayList<List<T>>();
	    int sourceSize = source.size();
	    int size = (source.size() / n) + 1;
	    for (int i = 0; i < size; i++) {
	        List<T> subset = new ArrayList<T>();
	        for (int j = i * n; j < (i + 1) * n; j++) {
	            if (j < sourceSize) {
	                subset.add(source.get(j));
	            }
	        }
	        result.add(subset);
	    }
	    return result;
	}

	/**
	 * 将一组数据固定分组，每组n个元素
	 * @deprecated 已知总条数和每组的元素数，算出每组满的有多少组（总条数/每组元素数），组中没满的（总条数/每组元素数 余数的）
	 * 			     先将组中满的按照每组数量分隔，没满的分一组
	 * @param source 要分组的数据源
	 * @param n      每组n个元素
	 * @param <T>
	 * @return
	 */
	public static <T> List<List<T>> fixedGrouping2(List<T> source, int n) {

	    if (null == source || source.size() == 0 || n <= 0)
	        return null;
	    List<List<T>> result = new ArrayList<List<T>>();
	    int remainder = source.size() % n;
	    int size = (source.size() / n);
	    for (int i = 0; i < size; i++) {
	        List<T> subset = null;
	        subset = source.subList(i * n, (i + 1) * n);
	        result.add(subset);
	    }
	    if (remainder > 0) {
	        List<T> subset = null;
	        subset = source.subList(size * n, size * n + remainder);
	        result.add(subset);
	    }
	    return result;
	}
}
