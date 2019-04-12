package com.shihy.springboot.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shihy.springboot.dao.LoggerMapper;
import com.shihy.springboot.datasource.DS;
import com.shihy.springboot.entity.Logger;
import com.shihy.springboot.service.LoggerService;
import com.shihy.springboot.utils.Page;
import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 日志管理
 * @data 2019年3月27日 下午3:47:26
 *
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class LoggerServiceImpl implements LoggerService {
	@Resource
	private LoggerMapper loggerMapper;
	@DS("slave")
	@Override
	public void save(Logger logger) {
		log.info("save logger ......");
		loggerMapper.insert(logger);
	}
	@DS("slave")
	@Override
	public List<Logger> getLoggerList(String serchName, Integer action, int page, int size) {
		//获取开始条目数
		Page pageInfo = new Page();
		Integer startSize = pageInfo.getStartSize(page, size);
		//构造模糊查询参数
		StringBuilder sb = new StringBuilder("%");
		sb.append(serchName);
		sb.append("%");
		serchName = sb.toString();
		return loggerMapper.getLoggerList(serchName, action, startSize, size);
	}
	@DS("slave")
	@Override
	public Integer getLoggerTotalPage(String serchName, Integer action) {
		//构造模糊查询参数
		StringBuilder sb = new StringBuilder("%");
		sb.append(serchName);
		sb.append("%");
		serchName = sb.toString();
		return loggerMapper.getLoggerTotalPage(serchName, action);
	}
	@DS("slave")
	@Override
	public Integer delLoggerByIds(String ids) {
		log.info("------------------delLoggerByIds--------------------");
		Integer result = null;
		if (StringUtils.isNotBlank(ids)) {
			boolean flag = ids.contains(",");
			//是否包含“，”
			if (flag) {
				String[] delId = ids.split(",");
				result = loggerMapper.delLoggerByIds(delId);
			} else {
				//不包含“，”
				String [] delId = new String[0];
				delId[0] = ids;
				result = loggerMapper.delLoggerByIds(delId);
			}
		}
		return result;
	}
	@DS("slave")
	@Override
	public Integer delLoggerByIdList(String ids) {
		log.info("------------------delLoggerByIdList--------------------");
		Integer result = null;
		List<Integer> list = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(ids)) {
			boolean flag = ids.contains(",");
			//是否包含“，”
			if (flag) {
				String[] delId = ids.split(",");
				for (int i = 0; i < delId.length; i++) {
					list.add(Integer.parseInt(delId[i]));
				}
				result = loggerMapper.delLoggerByIdList(list);
			} else {
				//不包含“，”
				list.add(Integer.parseInt(ids));
				result = loggerMapper.delLoggerByIdList(list);
			}
		}
		return result;
	}
	@DS("slave")
	@Override
	public int batchAddLogger(List<Logger> logger) {		
		return loggerMapper.batchAddLogger(logger);
	}
	@DS("slave")
	@Override
	public List<Map<String, Object>> getLoggerInfoList(String targetName, Integer action, Page page) {
		Integer startSize = page.getStartSize(page.getPageNum(), page.getPageSize());
		List<Map<String, Object>> loggerInfoList = loggerMapper.getLoggerInfoList(targetName, action, startSize, page.getPageSize());
		return loggerInfoList;
	}
	
}
