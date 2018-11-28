package sng.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sng.entity.ChargeRecord;


/**
 * Title: ChargeService
 * Description: 支付教师端接口
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年1月4日下午1:22:21
 */
@Service
@Transactional
public interface ChargeRecordService {

	/**
	 * 新增
	 * @param cr
	 */
	void save(ChargeRecord cr);

	/**
	 * 查询
	 * @param query
	 * @return
	 */
	List<ChargeRecord> getList(ChargeRecord cr);

	/**
	 * 根据主键查询待审核的记录
	 * @param split
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
	
	/**
	 * @Title : getById 
	 * @功能描述:根据主键获取支付记录 
	 * @返回类型：ChargeRecord 
	 * @throws ：SNG
	 */
	ChargeRecord getById(int parseInt);
}
