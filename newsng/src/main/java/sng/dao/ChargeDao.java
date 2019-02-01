package sng.dao;

import java.util.List;

import sng.entity.Account;
import sng.entity.Charge;

/**
 * 支付DAO
 * Title: ChargeDao
 * Description:支付总表DAO接口 
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年1月17日下午3:36:48
 */
public interface ChargeDao extends BaseDao<Charge>{

	/**
	 * 查询支付项目
	 */
	Charge getCharge(Integer org_id, Integer cam_id, Integer clas_id);
	
	/**
	 * 获取光大账户列表
	 */
	List<Account> getCEBList();

	/**
	 * 获取当前学期下的所有学费支付项
	 */
	List<Charge> getTList(Integer term_id,Integer teacher_id);

}
