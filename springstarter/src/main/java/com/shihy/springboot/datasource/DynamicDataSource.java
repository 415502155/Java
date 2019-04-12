package com.shihy.springboot.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 动态数据源切换；
 * @data 2019年3月28日 下午3:50:19
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDB();
	}
}
