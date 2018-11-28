package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.AttendanceConf;

/**
 * 考勤机DAO接口 Title: AttendanceConfDao Description: Company: 世纪伟业
 * 
 * @author sunwei
 * @date 2018年1月24日下午2:37:57
 */
public interface AttendanceConfDao extends BaseDAO<AttendanceConf> {

	public abstract void insert(AttendanceConf ac);
	
	public abstract List<AttendanceConf> getAttnConfList(int orgId);
	
	public abstract int deleteAttnConfAndAttnConfModify(int orgId);
}
