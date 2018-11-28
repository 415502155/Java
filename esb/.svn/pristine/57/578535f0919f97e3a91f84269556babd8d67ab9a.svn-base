package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.ActCode;

public interface IActCodeDao extends BaseDAO<ActCode>{

	List<ActCode> findAll();

	int add(ActCode parent);

	void update(ActCode parent);
	
	boolean isExit(String mobile,String code);
	
	ActCode getValidCodeByMobile(String mobile);

}
