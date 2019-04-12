package com.shihy.springboot.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
public interface BaseDao {
	public void setSqlSessionFactory(@Qualifier("sqlSessionFactory")SqlSessionFactory sqlSessionFactory);
}
