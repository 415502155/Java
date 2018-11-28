package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.entity.KeyValue;

/**
 * 键值服务接口
 * Title:KeyValueService
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年7月17日上午10:02:26
 */
public interface KeyValueService {

	/**
	 * 根据类型获取全部键值对集合
	 * @param type
	 * @return
	 */
	public abstract List<KeyValue> getListByType(Integer type);
	
}
