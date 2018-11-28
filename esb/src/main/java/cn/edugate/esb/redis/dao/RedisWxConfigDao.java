package cn.edugate.esb.redis.dao;

import java.util.List;
import cn.edugate.esb.entity.WxConfig;

public interface RedisWxConfigDao {
	boolean add(WxConfig tr);
	
	boolean add(List<WxConfig> trs);
	
	boolean delete(WxConfig tr);

	boolean deleteAll();
}
