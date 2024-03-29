package sng.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sng.entity.Account;
import sng.entity.Charge;
import sng.entity.ChargeDetail;
import sng.entity.ChargeRecord;
import sng.entity.Classes;


/**
 * Title: ChargeService
 * Description: 支付接口
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年1月4日下午1:22:21
 */
@Service
@Transactional
public interface ChargeService {
	
	/**
	 * @功能描述:为班级创建学费支付项目 
	 * @返回类型：int 成功返回charge主键，失败返回0 
	 */
	int createChargeForNewClass(Classes classes);
	
	/**
	 * 更新支付状态
	 */
	Charge updateStatus(List<ChargeRecord> list, Charge charge);

	/**
	 * 查询
	 */
	Charge getById(Integer cid);

	/**
	 * 将支付结果记入数据库，并修改对应各表的状态
	 * 处理支付结果的最终方法
	 * 学员状态，根据学员状态对学员班级关系表和是否占坑进行更新
	 * @param stu_status 传入要修改的8种学员状态，null表示不修改学员班级关闭表状态且不对班级可用名额进行复核
	 * @param isHold 传1表示入坑并同步更新班级可用名额，传0表示脱坑并调用随机脱坑MQ更新班级可用名额，传null不对班级可用名额进行复核
	 * @param immediateRelease 【isHold传0时有效】是否立即释放，true立即释放而不调用MQ，false调用MQ释放
	 */
	Boolean chargeFinally(Charge charge, ChargeDetail cd, ChargeRecord cr, Integer stu_status, Integer isHold, boolean immediateRelease);
	
	/**
	 * 获取光大账户
	 */
	List<Account> getCEBList();

	/**
	 * 获取当前学期下的所有学费支付项
	 */
	List<Charge> getTList(Integer term_id,Integer teacher_id);
}
