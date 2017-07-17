package tk.mybatis.springboot.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.alibaba.fastjson.JSONObject;


//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;

import tk.mybatis.springboot.mapper.TmnInfoMapper;
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
//	TmnPageInfoMapper tmnpageInfoMapper;
//  @Autowired
//  TmnInfoService tmninfoService;
//	@Autowired
//	TmnInfoRepository tmnInfoRepository;
//    @Resource
//    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private RedisTemplate<Serializable,Serializable> template;

//    public RedisTemplate<Serializable, Serializable> getTemplate() {
//        return template;
//    }
//
//    public void setTemplate(RedisTemplate<Serializable, Serializable> template) {
//        this.template = template;
//    }
	
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
	public UserInfo tmnPageList(){
		UserInfo uiInfo = new UserInfo();
		uiInfo.setEmail("zhansan@126.com");
		uiInfo.setId(1);
		uiInfo.setTel("10086");
		uiInfo.setUsername("zhangsan");
//	    ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
//      valueOperations.set("admin", "random1="+Math.random());      
        ValueOperations<Serializable,Serializable> valueOperations1 = template.opsForValue();
        UserInfo userInfo = (UserInfo) valueOperations1.get(uiInfo.getUsername());
        HashOperations<Serializable, Object, Object>  hash = template.opsForHash();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name", "lp");
        map.put("age", "26");
        hash.putAll("lpMap", map);
        //获取 map
        System.out.println(hash.entries("lpMap"));
        //添加 一个 list 列表
        ListOperations<Serializable, Serializable> list = template.opsForList();
        list.rightPush("lpList", "lp");
        list.rightPush("lpList", "26");
        //输出 list
        System.out.println(list.range("lpList", 0, list.size("26")));
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
		return userInfo;
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

	
	@RequestMapping(value = "/findone/{id}")
	@ResponseBody
	public String tmnInfoByTerminalId(@PathVariable Integer id){
		String result="";
		JSONObject backJson = new JSONObject();	
		t_tmn_info tmn = tmnnfoMapper.findTmnInfoByTerminal(id);
		Map<String,Object> returnmap=new HashMap<String,Object>();
		returnmap.put("TERMINALID", tmn.getTerminal_id());
		returnmap.put("ACCOUNTID", tmn.getAccount_id());
		returnmap.put("ADDRESS", tmn.getTerminal_address());
		result=net.sf.json.JSONObject.fromObject(returnmap).toString();
		logger.info("res:"+result);
		return result;
	}
	
	@RequestMapping(value="city")
	@ResponseBody
	public String queryAll(){
		int cityId = 5;
		List<t_tmn_info> list = tmnnfoMapper.queryAll(cityId);
		System.out.println(list.get(0).getLinkman());
		return "success";
	}
	
}
