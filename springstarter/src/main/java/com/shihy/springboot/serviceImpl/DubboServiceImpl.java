/*package com.shihy.springboot.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.shihy.springboot.service.DubboService;
@Service(version = "1.0.0")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DubboServiceImpl implements DubboService {

	@Override
	public Map<String, Object> getDubboInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "dubbo");
		map.put("version", "version-3.4.14");
		map.put("size", "1234");
		return map;
	}

}
*/