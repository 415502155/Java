package cn.edugate.esb.service;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edugate.esb.dao.AttendanceConfDao;
import cn.edugate.esb.dao.AttendanceMachineDao;
import cn.edugate.esb.entity.AttendanceConf;
import cn.edugate.esb.entity.AttendanceMachine;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.util.JsonUtil;
import cn.edugate.esb.util.RedisUtil;

/**
 * 
 * 
 * @author:sunwei
 * @Date:2018年8月1日下午1:57:54
 */
@Service
public class AttnMachineManageService {
	
	@Autowired
	private AttendanceMachineDao attnMachineDao;
	
	@Autowired
	private AttendanceConfDao attnConfDao;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private GradeService gradeService;
	
	
	public int getCountOfDeviceCode(String deviceCode) {
		return attnMachineDao.getCountOfDeviceCode(deviceCode);
	}
	
	
	/**
	 * 获取机构下考勤机列表
	 * @param orgId
	 * @return
	 */
	public List<AttendanceMachine> getAttnMachineList(int orgId) {
		return attnMachineDao.getAttendanceMachineList(orgId);
	}
	
	
	/**
	 * 新增考勤机
	 * @param machine
	 * @param existedAttnMachine
	 * @return
	 */
	public int saveAttnMachine(AttendanceMachine machine, boolean existedAttnMachine) {
		// 如果之前不存在考勤机，则保存考勤机记录后要新增机构下各年级的考勤设置，并添加到redis缓存中
		// 目前默认考勤机状态为在线
		machine.setStatus(1);
		Date currentDate = new Date();
		machine.setInsert_time(currentDate);
		
		if (!existedAttnMachine) {
			attnMachineDao.saveAttnMachine(machine);
			
			// 查询机构下的年级，生成默认考勤设置
			List<Grade> gradeList = gradeService.getGradeList4AttnMachine(machine.getOrg_id());
			if (gradeList != null && gradeList.size() > 0) {
				
				List<AttendanceConf> confList = new ArrayList<AttendanceConf>();
				for (Grade g : gradeList) {
					AttendanceConf ac = new AttendanceConf();
					
					ac.setOrg_id(machine.getOrg_id());
					ac.setAttnd_num(2);
					ac.setType(0);
					ac.setGrade_id(g.getGrade_id());
					ac.setGrade_name(g.getGrade_name());
					
					ac.setAM_in_scope_begin(new Time(6, 0, 0));
					ac.setAM_in_scope_end(new Time(10, 0, 0));
					ac.setAM_in_time(new Time(8, 0, 0));
					
					ac.setPM_out_scope_begin(new Time(15, 0, 0));
					ac.setPM_out_scope_end(new Time(19, 0, 0));
					ac.setPM_out_time(new Time(17, 0, 0));
					ac.setInsert_time(currentDate);
					
					attnConfDao.insert(ac);;
					
					confList.add(ac);
				}
				setAttendanceConfToRedis(confList);
			}
			
			return 1;
		} else {
			return attnMachineDao.saveAttnMachine(machine);
		}
	}
	
	
	/**
	 * 设置考勤设置到redis缓存
	 * @param confList
	 */
	private void setAttendanceConfToRedis(List<AttendanceConf> confList) {
		if (confList != null && confList.size() > 0) {
			for (AttendanceConf ac : confList) {
				try {
					redisUtil.set("AttendanceConf:org=" + ac.getOrg_id() + ",gradeId=" + ac.getGrade_id(),
							JsonUtil.toJson(ac, Inclusion.ALWAYS));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 删除机构下的考勤机
	 * @param orgId
	 * @param recordId
	 * @return
	 */
	public int deleteAttnMachine(int orgId, int recordId) {
		// 查询要删除的考勤机记录
		AttendanceMachine machine = attnMachineDao.getAttendanceMachine(orgId, recordId);
		if (machine != null && machine.getId() != null && machine.getId().intValue() > 0) {
			// 记录存在，进行删除
			attnMachineDao.deleteAttnMachine(orgId, recordId);

			// 查询机构下是否还有考勤机记录
			List<AttendanceMachine> machineList = attnMachineDao.getAttendanceMachineList(orgId);
			if (machineList == null || machineList.isEmpty()) {
				// 没有考勤机，将所有考勤设置删除，机构变为软考勤
				attnConfDao.deleteAttnConfAndAttnConfModify(orgId);

				// 删除缓存中年级对应考勤设置
				List<Grade> gradeList = gradeService.getGradeList(orgId);
				if (gradeList != null && gradeList.size() > 0) {
					for (Grade g : gradeList) {
						redisUtil.del("AttendanceConf:org=" + orgId + ",gradeId=" + g.getGrade_id().intValue());
					}
				}
			}
		}

		return 1;
	}
}
