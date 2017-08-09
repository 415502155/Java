package tk.mybatis.springboot.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.MapAccessor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import tk.mybatis.springboot.dao.vani.TmnInfoDao;

//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;

import tk.mybatis.springboot.mapper.TmnInfoMapper;
import tk.mybatis.springboot.mapper.UserInfoMapper;
import tk.mybatis.springboot.model.UserInfo;
import tk.mybatis.springboot.model.t_tmn_info;
//import tk.mybatis.springboot.service.TmnInfoService;
//import tk.mybatis.springboot.repository.TmnInfoRepository;

@RestController
@RequestMapping("/tmn")
public class TmnInfoController {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	TmnInfoMapper tmnnfoMapper;
	
	@Autowired
	TmnInfoDao tmninfoDao;
	
	@Autowired
	UserInfoMapper userInfo;
	
//	@Autowired
//	TmnPageInfoMapper tmnpageInfoMapper;
//  @Autowired
//  TmnInfoService tmninfoService;
//	@Autowired
//	TmnInfoRepository tmnInfoRepository;
//    @Resource
//    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private RedisTemplate<Serializable,Serializable> template;

    public RedisTemplate<Serializable, Serializable> getTemplate() {
        return template;
    }

    public void setTemplate(RedisTemplate<Serializable, Serializable> template) {
        this.template = template;
    }
	
	@RequestMapping(value = "/info")
	@ResponseBody
	public JSONObject tmnList(HttpServletRequest request, HttpServletResponse response){
	   	 response.setContentType("text/xml;charset=UTF-8");
	     /**
	      * 设置响应头允许ajax跨域访问*
	      */
	     response.setHeader("Access-Control-Allow-Origin", "*");//'Access-Control-Allow-Origin:*'
	     /*星号表示所有的异域请求都可以接受，*/
	     response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		JSONObject backJson = new JSONObject();
		String s = "select * from t_tmn_info";
		int pagesize = 8;
		int pagenum = 1;
		int end =pagenum*pagesize;
		int start = (pagenum-1)*8;
		int count = tmnnfoMapper.findTmnInfoNum();

		List<t_tmn_info> tmn = tmnnfoMapper.getAll(end, start);
		backJson.put("TMN", tmn);
		backJson.put("COUNT", count);
		backJson.put("PAGESIZE", pagesize);
		backJson.put("PAGENUM", pagenum);
		return backJson;
	}
	
	
	@RequestMapping(value = "/redis")
	@ResponseBody
	public String tmnPageList(){


        HashOperations<Serializable, Object, Object>  hash = template.opsForHash();
		Map map = new LinkedHashMap<>();
		map.put("name", "zhangsan");
		map.put("age", 10);
		map.put("sex", 1);
		map.put("address", "china");
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("name", "lp");
//        map.put("age", "26");
        hash.putAll("lpMap", map);
        //获取 map
        System.out.println(hash.entries("lpMap"));
        //添加 一个 list 列表
        ListOperations<Serializable, Serializable> list = template.opsForList();
        //list.rightPush("lpList", "lp");
        //list.rightPush("lpList", "26");
        //输出 list
        list.remove("lp", 5, "lp");
        System.out.println("List:"+list.range("lpList", 0, list.size("lpList")));
        System.out.println("size:"+list.size("lpList"));
        System.out.println("lp:"+list.size("lp"));
        System.out.println("26:"+list.size("26"));
        //添加 一个 set 集合
        SetOperations<Serializable, Serializable> set = template.opsForSet();
        set.add("lpSet", "lp");
        set.add("lpSet", "26");
        set.add("lpSet", "178cm");
        //输出 set 集合
        System.out.println(set.members("lpSet"));
        //添加有序的 set 集合
        ZSetOperations<Serializable, Serializable> zset = template.opsForZSet();
        zset.add("lpZset", "lp", 0);
        zset.add("lpZset", "26", 1);
        zset.add("lpZset", "178cm", 2);
        //输出有序 set 集合
        System.out.println(zset.rangeByScore("lpZset", 0, 2));
		return "success";
	}
	
//	@RequestMapping(value = "/params", method=RequestMethod.GET)
//	@ResponseBody
//	public Page<t_tmn_info> getEntryByParams() {
//		int page = 1;
//		int size = 10;
//	    Sort sort = new Sort(Direction.DESC, "terminal_id");
//	    Pageable pageable = new PageRequest(page, size, sort);
//	    return tmnInfoRepository.findAll(pageable);
//	}

	
//	@RequestMapping(value = "/findone/{id}")
//	@ResponseBody
//	public String tmnInfoByTerminalId(@PathVariable Integer id){
//		String result="";
//		JSONObject backJson = new JSONObject();	
//		t_tmn_info tmn = tmnnfoMapper.findTmnInfoByTerminal(id);
//		Map<String,Object> returnmap=new HashMap<String,Object>();
//		returnmap.put("TERMINALID", tmn.getTerminal_id());
//		returnmap.put("ACCOUNTID", tmn.getAccount_id());
//		returnmap.put("ADDRESS", tmn.getTerminal_address());
//		result=net.sf.json.JSONObject.fromObject(returnmap).toString();
//		logger.info("res:"+result);
//		return result;
//	}
	
	@RequestMapping(value="city")
	@ResponseBody
	public String queryAll(){
		int cityId = 5;
		List<t_tmn_info> list = tmnnfoMapper.queryAll(cityId);
		System.out.println(list.get(0).getLinkman());
		return "success";
	}
	@RequestMapping(value="getinfo")
	@ResponseBody
	public String getTmnInfoList() {
		int start = 1;
		int end = 10;
		List<t_tmn_info> tmnList = tmninfoDao.getAll(end, start);
		return "suc";
	}
	
	@RequestMapping(value="test")
	@ResponseBody
	public JSONObject getTest() {
		logger.info("111111111111111111111111111111");
		JSONObject backJson = new JSONObject();
		List<UserInfo> userInfos = userInfo.findUserInfo();
		backJson.put("user", userInfos);
		logger.info("BackJson:"+backJson);
		return backJson;
	}
	
	@RequestMapping(value="test1")
	@ResponseBody
	public JSONObject getTest1() {
		JSONObject backJson = new JSONObject();
		int pagenum = 2;
		int pagesize = 5;
		int start = (pagenum-1)*pagesize;
		int end = pagesize;
		logger.info("start:"+start+",end:"+end);
		List<UserInfo> userInfos = userInfo.getPageUserInfo(start, end);
		if(userInfos.isEmpty()) {
			logger.info("userinfo is null!");
		}
		backJson.put("user", userInfos);
		logger.info("BackJson:"+backJson);
		return backJson;
	}
	
	/***
	 * Mysql Query
	 * @return
	 */
	@RequestMapping(value="test2")
	@ResponseBody
	public JSONObject getTest2() {
		JSONObject backJson = new JSONObject();
		int pagenum = 2;
		int pagesize = 5;
		int start = (pagenum-1)*pagesize;
		int end = pagesize;
		logger.info("start:"+start+",end:"+end);
		List<UserInfo> userInfos = userInfo.getUserRolePageInfo(start, end); //userInfo.getUserRoleInfo();
		if(userInfos.isEmpty()) {
			logger.info("userroleinfo is null!");
		}
		int count = userInfo.CountUserRoleInfo();
		backJson.put("PAGENUM", pagenum);
		backJson.put("PAGESIZE", pagesize);
		backJson.put("COUNT", count);
		backJson.put("user", userInfos);
		logger.info("BackJson:"+userInfos.size());
		return backJson;
	}
	/***
	 * 修改
	 * @return
	 */
	@RequestMapping(value="test3")
	@ResponseBody
	public JSONObject getTest3() {
		JSONObject backJson = new JSONObject();
		String userName = "hanni";
		int userId = 2;
		int status = userInfo.updateUserInfo(userName, userId);
		int count = userInfo.CountUserRoleInfo();
		backJson.put("COUNT", count);
		backJson.put("STATUS", status);
		logger.info("BackJson:"+backJson);
		return backJson;
	}
	
	/***
	 * 批量修改或添加
	 * @return
	 */
	@RequestMapping(value="test4")
	@ResponseBody
	public JSONObject getTest4() {
		JSONObject backJson = new JSONObject();
		List<UserInfo> ulist = new ArrayList<UserInfo>();
		UserInfo user1 = new UserInfo();
		user1.setAddress("china1");
		user1.setRole_id(1);
		user1.setSex(1);
		//user1.setUser_id(1);
		user1.setUser_name("ningyi1");
		ulist.add(user1);
		UserInfo user2 = new UserInfo();
		user2.setAddress("china2");
		user2.setRole_id(1);
		user2.setSex(1);
		user2.setUser_name("ningyi2");
		ulist.add(user2);
		UserInfo user3 = new UserInfo();
		user3.setAddress("china3");
		user3.setRole_id(1);
		user3.setSex(1);
		user3.setUser_name("ningyi3");
		ulist.add(user3);
		int sts = userInfo.addUserInfo(ulist);		
		backJson.put("STATUS", sts);
		logger.info("BackJson:"+backJson);
		return backJson;
	}
	
	/***
	 * ThreadPoolExecutor
	 * 
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@RequestMapping(value="test5")
	@ResponseBody
	public JSONObject getTest5() throws InterruptedException, ExecutionException {
		JSONObject backJson = new JSONObject();
		ThreadPoolExecutor threadPoolExecutor =new ThreadPoolExecutor(5, 100, 5000, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(50),new ThreadPoolExecutor.AbortPolicy());
		int count = userInfo.CountUserInfo();//userinfo count
		logger.info("userinfo count: "+count);
		//thread num
		int threadnum = count;
		int num = 3;
		if(threadnum%3 == 0) {
			threadnum = threadnum/num;
		}else {
			threadnum = threadnum/num +1;
		}
		logger.info("ThreadNum :"+threadnum);
		StringBuilder sBuilder = new StringBuilder();
		List<UserInfo> list = new ArrayList<UserInfo>();
		for(int i = 0; i < threadnum ; i++) {
			logger.info("cishu:"+i);
			UserTask userTask = new UserTask(threadnum);
			userTask.setStart(i*num);
			userTask.setEnd(num);
			Future future = threadPoolExecutor.submit(userTask);
			List<UserInfo> ulist= (List<UserInfo>) future.get();
			list.addAll(ulist);
			
		}
        JSONArray jsonArray = new JSONArray();
        for (UserInfo user : list) {
            JSONObject tempJson = new JSONObject();
            tempJson.put("user_id", user.getUser_id());
            tempJson.put("user_name", user.getUser_name());
            tempJson.put("address", user.getAddress());
            tempJson.put("sex", user.getSex());            
            jsonArray.add(tempJson);
        }
		backJson.put("List", jsonArray);
		logger.info("BackJson:"+backJson);
		return backJson;
	}
	
}
