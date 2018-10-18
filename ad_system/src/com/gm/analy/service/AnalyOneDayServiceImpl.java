package com.gm.analy.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gm.bean.AdSearchBean;
import com.gm.bean.AdUrlBean;
import com.gm.dao.mysql.BaseDao;
import com.gm.utils.DateUtil;

@Service
@Transactional
public class AnalyOneDayServiceImpl {
	
	@Autowired
	BaseDao dao;
	
	Logger logger = LoggerFactory.getLogger(AnalyOneDayServiceImpl.class);
	
	//@Scheduled(cron = "0 0/1 * * * ?") //每分钟执行
	@Scheduled(cron="0 0 1 * * ?") //每天凌晨1点执行
	public void analyOneDayData() {
		logger.debug("每日开始执行");
		this.analyOneDayUrlImeiNum();// 访问站点排名前100的站点用户累计排行
		this.analyOneDaySearchImeiNum();// 搜索关键词排名前100的站点用户累计排行
		logger.debug("每日执行结束");
	}
	
	/**
	 * 访问站点排名前100的站点用户累计排行
	 */
	public void analyOneDayUrlImeiNum(){
		Date day=new Date();
		String now=DateUtil.DateToString(day, DateUtil.DATE_FORMAT);
		String nowbefore1=DateUtil.dateAdd(now, -1,DateUtil.DATE_TYPE_DAY);//前1天日期
		Date day_1=DateUtil.StringTodate(nowbefore1, DateUtil.DATE_FORMAT);
		long starttime=DateUtil.dayToTimestampStart(day_1);
		long endtime=DateUtil.dayToTimestampEnd(day_1);
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT url,COUNT(DISTINCT imei) imeinum FROM t_ad_user_inter_url");
			sql.append(" WHERE `time` BETWEEN '").append(starttime).append("' AND '").append(endtime).append("'");
			sql.append(" GROUP BY url ORDER BY imeinum DESC LIMIT 100 ");
			List<Map> rst = dao.getlisMaps(sql.toString());
			AdUrlBean entity = null;
			List insert_rst = new ArrayList();
			for (Map map : rst) {
				entity = new AdUrlBean();
				String url = map.get("url") + "";
				long imeinum = Long.parseLong(map.get("imeinum")+"");
				entity.setUrl(url);
				entity.setImeinum(imeinum);
				entity.setTrandate(nowbefore1);
				insert_rst.add(entity);
			}
			dao.batchSaveOrUpdate(insert_rst);
		} catch (Exception e2) {
			
			e2.printStackTrace();
		}
	}
	
	/**
	 * 访问站点排名前100的站点用户累计排行
	 */
	public void analyOneDaySearchImeiNum(){
		Date day=new Date();
		String now=DateUtil.DateToString(day, DateUtil.DATE_FORMAT);
		String nowbefore1=DateUtil.dateAdd(now, -1,DateUtil.DATE_TYPE_DAY);//前1天日期
		Date day_1=DateUtil.StringTodate(nowbefore1, DateUtil.DATE_FORMAT);
		long starttime=DateUtil.dayToTimestampStart(day_1);
		long endtime=DateUtil.dayToTimestampEnd(day_1);
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT search_term,COUNT(DISTINCT imei) imeinum FROM t_ad_user_search_term");
			sql.append(" WHERE `time` BETWEEN '").append(starttime).append("' AND '").append(endtime).append("'");
			sql.append(" GROUP BY search_term ORDER BY imeinum DESC LIMIT 100 ");
			List<Map> rst = dao.getlisMaps(sql.toString());
			AdSearchBean entity = null;
			List insert_rst = new ArrayList();
			for (Map map : rst) {
				entity = new AdSearchBean();
				String search_term = map.get("search_term") + "";
				long imeinum = Long.parseLong(map.get("imeinum")+"");
				entity.setSearch_term(search_term);
				entity.setImeinum(imeinum);
				entity.setTrandate(nowbefore1);
				insert_rst.add(entity);
			}
			dao.batchSaveOrUpdate(insert_rst);
		} catch (Exception e2) {
			
			e2.printStackTrace();
		}
	}
}
