package com.shihy.springboot.datasource;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 多数据源配置类
 * @data 2019年3月28日 下午3:56:13
 *
 */
@Configuration
public class DataSourceConfig {
	/***
	 * @Description: master数据源
	 * @param @return
	 * @return DataSource  
	 * @throws @throws
	 */
	// application.properteis中对应属性的前缀
	@Bean(name = "master")
	@ConfigurationProperties(prefix = "spring.datasource.master") 
	public DataSource dataSourceMaster() {
		return DataSourceBuilder.create().build();
	}
	/***
	 * @Description: slave数据源
	 * @param @return
	 * @return DataSource  
	 * @throws @throws
	 */
	@Bean(name = "slave")
	@ConfigurationProperties(prefix = "spring.datasource.slave")
	public DataSource dataSourceSlave() {
		return DataSourceBuilder.create().build();
	}

	/***
	 * 
	 * @Description: 通过AOP在不同数据源之间动态切换
	 * @param @return
	 * @return DataSource
	 * @throws @throws
	 */
	@Primary
	@Bean(name = "dynamicDataSource")
	public DataSource dynamicDataSource() {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		// 默认数据源
		dynamicDataSource.setDefaultTargetDataSource(dataSourceMaster());
		// 配置多数据源
		Map<Object, Object> dsMap = new HashMap<Object, Object>(16);
		dsMap.put("master", dataSourceMaster());
		dsMap.put("slave", dataSourceSlave());
		dynamicDataSource.setTargetDataSources(dsMap);
		return dynamicDataSource;
	}

	/**
	 * 配置@Transactional注解事物
	 * 
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dynamicDataSource());
	}

}
