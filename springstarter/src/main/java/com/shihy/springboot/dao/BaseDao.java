package com.shihy.springboot.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;

public interface BaseDao {
	public void setSqlSessionFactory(@Qualifier("sqlSessionFactory")SqlSessionFactory sqlSessionFactory);
}
