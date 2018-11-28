package cn.edugate.esb.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.AttendanceMachineDao;
import cn.edugate.esb.entity.AttendanceMachine;



/**
 * 考勤DAO实现 Title: AttendanceMachineDaoImpl Description: Company: 世纪伟业
 * 
 * @author sunwei
 * @date 2018年4月24日下午2:38:48
 */
@Repository
public class AttendanceMachineDaoImpl extends BaseDAOImpl<AttendanceMachine> implements AttendanceMachineDao {

	@Override
	public List<AttendanceMachine> getAttendanceMachineList(int org_id) {
		String sql = "SELECT am.* FROM shijiwxy.attendance_machine am WHERE am.org_id=? ORDER BY am.insert_time DESC;";
		
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(AttendanceMachine.class));
		query.setInteger(0, org_id);
		
		return query.list();
	}

	@Override
	public AttendanceMachine getAttendanceMachine(int org_id, int record_id) {
		String sql = "SELECT am.* FROM shijiwxy.attendance_machine am WHERE am.id=? AND am.org_id=?;";
		
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(AttendanceMachine.class));
		query.setInteger(0, record_id);
		query.setInteger(1, org_id);
		
		List<AttendanceMachine> list = query.list();
		AttendanceMachine machineRecord = null;
		if (list != null && list.size() > 0) {
			machineRecord = list.get(0);
		}
		return machineRecord;
	}

	@Override
	public int saveAttnMachine(AttendanceMachine machine) {
		String sql = "INSERT INTO shijiwxy.`attendance_machine` "
				+ "(`org_id`, `serial_number`, `type`, `place`, `status`, `remarks`, `insert_time`) " + "VALUES (?, ?, ?, ?, ?, ?, ?);";

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);

		query.setInteger(0, machine.getOrg_id());
		query.setString(1, machine.getSerial_number().trim());
		query.setInteger(2, machine.getType());
		query.setString(3, machine.getPlace().trim());
		query.setInteger(4, machine.getStatus());
		query.setString(5, machine.getRemarks());
		//query.setDate(6, machine.getInsert_time());
		query.setTimestamp(6, machine.getInsert_time());
		return query.executeUpdate();
	}

	@Override
	public int deleteAttnMachine(int orgId, int recordId) {
		String sql = "DELETE am FROM shijiwxy.attendance_machine am WHERE am.id=? AND am.org_id=?;";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, recordId);
		query.setInteger(1, orgId);
		return query.executeUpdate();
	}

	@Override
	public int getCountOfDeviceCode(String deviceCode) {
		String sql = "SELECT COUNT(am.id) AS countNum FROM shijiwxy.attendance_machine am WHERE am.serial_number =?;";
		
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql).addScalar("countNum", StandardBasicTypes.INTEGER);
		query.setString(0, deviceCode.trim());
		
		int count = (Integer) query.uniqueResult();
		return count;
	}

}
