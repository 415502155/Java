package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.AttendanceMachine;


/**
 * 考勤机DAO接口
 * Title: AccountDao
 * Description: 
 * Company: 世纪伟业
 * @author sunwei
 * @date 2018年1月24日下午2:37:57
 */
public interface AttendanceMachineDao extends BaseDAO<AttendanceMachine>{
	
	public abstract int saveAttnMachine(AttendanceMachine machine);

	public abstract List<AttendanceMachine> getAttendanceMachineList(int org_id);
	
	public abstract AttendanceMachine getAttendanceMachine(int org_id, int record_id);
	
	public abstract int deleteAttnMachine(int orgId, int recordId);
	
	public abstract int getCountOfDeviceCode(String deviceCode);
	
}
