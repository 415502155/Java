package tk.mybatis.springboot.serviceImpl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import tk.mybatis.springboot.mapper.TmnInfoMapper;
import tk.mybatis.springboot.model.t_tmn_info;
import tk.mybatis.springboot.service.IComponentsService;

/**
 * 监控Service （CMD 102）
 *
 * @author Administrator
 */
@Service("MonitorService")
public class MonitorService implements IComponentsService {

    private static final Logger logger = Logger.getLogger(MonitorService.class);
    
	@Autowired
	TmnInfoMapper tmnnfoMapper;
	
    @Resource
    private RedisTemplate<Serializable,Serializable> template;
   
    public JSONObject execute(JSONObject json, String ip) {
        int cmd = json.getIntValue("CMD");
        int type = json.getIntValue("TYPE");
        logger.info("cmd:" + cmd + "/" + type);
        System.out.println("cmd:" + cmd + "/" + type);
        JSONObject backJson = new JSONObject();
        backJson.put("CMD", cmd);
        backJson.put("TYPE", type);

        switch (type) {
            case 1:
                backJson = monitorArea(json, ip);
                break;       
            case 2:
                backJson = monitorArea2(json, ip);
                break;  
        }
        return backJson;
    }

    private JSONObject monitorArea2(JSONObject json, String ip) {
    	JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        String termnalId = json.getString("TERMINAL_ID");
        int id = Integer.parseInt(termnalId);
        t_tmn_info tmn = tmnnfoMapper.findTmnInfoByTerminal(id);
        ListOperations<Serializable, Serializable> list = template.opsForList();
        List<Serializable> list2 =list.range("TMNINFO", 0, 5);
		backJson.put("TMN", tmn);
		backJson.put("REDISTMN", list2);
		backJson.put("CODE", 0);
        backJson.put("RESULT", "success");

		return backJson;
	}

	private JSONObject monitorArea(JSONObject json, String ip) {
		// TODO Auto-generated method stub
    	JSONObject backJson = new JSONObject();
        backJson.put("CMD", json.get("CMD"));
        backJson.put("TYPE", json.get("TYPE"));
        try {
        	int pagesize = 8;
    		int pagenum = 1;
    		int end =pagenum*pagesize;
    		int start = (pagenum-1)*8;
    		int count = tmnnfoMapper.findTmnInfoNum();
    		List<t_tmn_info> tmn = tmnnfoMapper.getAll(end, start);
    		String tmninfo = tmn.toString();
            ListOperations<Serializable, Serializable> list = template.opsForList();
            list.rightPush("TMNINFO", tmninfo);
            
            
    		backJson.put("TMN", tmn);
    		backJson.put("COUNT", count);
    		backJson.put("PAGESIZE", pagesize);
    		backJson.put("PAGENUM", pagenum);
            backJson.put("CODE", 0);
            backJson.put("RESULT", "success");
            return backJson;
        } catch (Exception e) {
            logger.error("Class MnitorService's monitorGame() has an exception: ", e);
        }
        return backJson;
	}



}

