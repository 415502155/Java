package com.shihy.springboot.dao;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class BaseDaoImpl extends SqlSessionDaoSupport implements BaseDao {

	@Override
	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
		
	}

}
