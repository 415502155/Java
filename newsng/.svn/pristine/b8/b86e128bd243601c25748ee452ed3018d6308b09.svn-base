package sng.dao;

import java.util.List;

import sng.entity.ChargeRecord;


/**
 * 支付记录DAO
 * Title: ChargeRecordDao
 * Description:支付记录DAO接口 
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年1月17日下午3:37:30
 */
public interface ChargeRecordDao extends BaseDao<ChargeRecord>{

	/**
	 * 查询
	 * @param query
	 * @return
	 */
	List<ChargeRecord> getList(ChargeRecord cr);

	/**
	 * 根据主键获取待审核的记录
	 * @param ids
	 * @return
	 */
	List<ChargeRecord> getList(String[] ids);

	/**
	 * 获取支付记录
	 * @param cd_id
	 * @return
	 */
	ChargeRecord getPayRecord(Integer cd_id);

	/**
	 * 根据cd_id获取申请退款信息
	 * @param cd_id
	 * @return
	 */
	ChargeRecord getRefundByCdid(Integer cd_id);

	/**
	 * 根据cd_id获取申请退款信息
	 * @param cd_id
	 * @return
	 */
	ChargeRecord getRefundByCdidForCEB(Integer cd_id);

	
}
