package com.shihy.springboot.dao;

import com.shihy.springboot.entity.ChargeDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
@Mapper
@Repository
@Component
public interface ChargeMapper {
	
	void createUnpaidDetail(ChargeDetail chargeDetail);
	
	void updatePayTimeOut(@Param("cdId")Integer cdId);
}