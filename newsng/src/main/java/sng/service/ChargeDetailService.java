package sng.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sng.entity.ChargeDetail;
import sng.pojo.Classes;
import sng.util.Paging;

/**
 * Title: ChargeService
 * Description: 支付学生端接口
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年1月4日下午1:22:21
 */
@Service
@Transactional
public interface ChargeDetailService {
	
	/**
	 * @Title : createChargeDetail 
	 * @功能描述: 创建支付详情
	 * @返回类型：成功返回主键，失败返回0
	 * @throws ：
	 */
	int createChargeDetail(Integer org_id,Integer stud_id,Integer clas_id,Integer cam_id);
	
	/**
	 * 获取学生支付列表
	 * @param cid
	 * @param classIds
	 * @return
	 */
	List<ChargeDetail> getListByCIdForTeacher(Integer cid, String classIds);

	/**
	 * 获取学生支付详细信息
	 * @param stud_id
	 * @param cid
	 * @return
	 */
	ChargeDetail getDetailForTeacher(Integer stud_id, Integer cid);

	/**
	 * 获取需要提醒的订单信息
	 * @param cid
	 * @param classIds
	 * @return
	 */
	List<ChargeDetail> getListForRemind(Integer cid, String classIds);

	/**
	 * 修改
	 * @param cd
	 */
	Integer update(ChargeDetail cd);

	/**
	 * @Title : getById 
	 * @功能描述:查询单个 
	 * @返回类型：ChargeDetail 
	 * @throws ：SNG
	 */
	ChargeDetail getById(Integer cd_id);

	/**
	 * 为待支付订单加上乐观锁
	 * @param lockDate
	 * @param cd_id
	 * @return
	 */
	Integer lockChargeDetail(Date lockDate, ChargeDetail cd);

	/**
	 * 支付
	 * @param cd
	 * @param lockDate
	 * @return
	 */
	Integer updateWithLock(ChargeDetail cd, Date lockDate);

	/**
	 * 获取带账户信息的支付详情信息
	 * @param cd_id
	 * @return
	 */
	ChargeDetail getByIdWithAccInfo(Integer cd_id, String txnType);

	/**
	 * 获取学生支付详情
	 * @param stud_id
	 * @return
	 */
	List<ChargeDetail> getDetailForParent(Integer stud_id, Integer max, Integer min, Integer num);

	/**
	 * 根据主键查询可退款账单
	 * @param cd_id
	 * @return
	 */
	ChargeDetail getByIdForRefund(Integer cd_id);

	/**
	 * 根据主键查询可支付的账单
	 * @param cd_id
	 * @return
	 */
	ChargeDetail getByIdForPay(Integer cd_id);

	/**
	 * 获取当前未支付的订单总数
	 * @param cid
	 * @return
	 */
	Integer countNoPay(Integer cid);

	/**
	 * 保存
	 * @param cd
	 */
	void save(ChargeDetail cd);

	/**
	 * 获取带分页的列表
	 * @param page
	 * @param cd
	 * @return
	 */
	Paging<ChargeDetail> getList(Paging<ChargeDetail> page, ChargeDetail cd);

	/**
	 * 获取当前收费单下所有班级的列表
	 * @param cid
	 * @return
	 */
	List<Classes> getClasses(Integer cid);

	/**
	 * 获取可退费列表
	 * @param cid
	 * @return
	 */
	List<ChargeDetail> getListForRefund(Integer cid);

	/**
	 * 根据订单编号获取支付详情
	 * @param string
	 * @return
	 */
	ChargeDetail getByOrderId(String orderId);

	/**
	 * 获取详情列表
	 * @param cid
	 * @return
	 */
	List<ChargeDetail> getList(Integer cid);

	/**
	 * 查看待审核的退费申请
	 * @param page
	 * @param cd
	 * @return
	 */
	List<ChargeDetail> getRefundApplyList(ChargeDetail cd);

	/**
	 * 更新
	 * @param toSaveDetail
	 */
	void update(List<ChargeDetail> toSaveDetail);

	/**
	 * 更新信息发送状态
	 * @param cd
	 */
	void updateSendStatus(ChargeDetail cd);

	/**
	 * 根据订单号查询已支付的支付详情
	 * @param orderId
	 * @return
	 */
	ChargeDetail getByOrderIdForQuery(String orderId);
	
	
	/**
	 * 获取当前未支付的类型（光大or银联）
	 * @param cid
	 * @return
	 */
	String getPayType(String cdid);
	
	/**
	 * 获取用户认证支付信息
	 */
	ChargeDetail getForAuthentication(Integer user_id,Integer org_id);
	
	/**
	 * @Title : getByIdForPayWithCEBUrl 
	 * @功能描述: 获取带支付链接的支付详情信息
	 * @返回类型：ChargeDetail 
	 * @throws ：SNG
	 */
	public ChargeDetail getByIdForPayWithCEBUrl(Integer cd_id);

	/**
	 * @Title : getByIdForCorrect 
	 * @功能描述: 获取支付信息（用于纠正错误的支付信息）
	 * @返回类型：ChargeDetail 
	 * @throws ：SNG
	 */
	ChargeDetail getByIdForCorrect(Integer cd_id);

	/**
	 * @Title : getByStuClaId 
	 * @功能描述: 根据学生班级关系主键获取支付详情
	 * @返回类型：ChargeDetail 
	 * @throws ：
	 */
	ChargeDetail getByStuClaId(Integer stu_class_id);
	
	/**
	 * @Title : getRecordList 
	 * @功能描述: 获取当前日期的所有支付记录（光大支付记录）
	 * @返回类型：List<ChargeDetail> 用ChargeDetail而非ChargeRecord是为了detail既包含了record的主要字段也有一些必要的状态字段
	 * @throws ：
	 */
	List<ChargeDetail> getRecordList(Integer accType, String date, String txnType);
}
