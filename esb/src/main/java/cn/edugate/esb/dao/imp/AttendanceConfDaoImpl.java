package cn.edugate.esb.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.AttendanceConfDao;
import cn.edugate.esb.entity.AttendanceConf;
import cn.edugate.esb.entity.AttendanceMachine;



/**
 * 考勤DAO实现 Title: AttendanceMachineDaoImpl Description: Company: 世纪伟业
 * 
 * @author sunwei
 * @date 2018年4月24日下午2:38:48
 */
@Repository
public class AttendanceConfDaoImpl extends BaseDAOImpl<AttendanceConf> implements AttendanceConfDao {

	@Override
	public void insert(AttendanceConf ac) {
		String sql = "INSERT INTO shijiwxy.`attendance_conf` "
				+ "(`org_id`, `attnd_num`, `type`, `grade_id`, `grade_name`, `AM_in_time`, `AM_in_scope_begin`, `AM_in_scope_end`, "
				+ "`AM_out_time`, `AM_out_scope_begin`, `AM_out_scope_end`, `PM_in_time`, `PM_in_scope_begin`, `PM_in_scope_end`, "
				+ "`PM_out_time`, `PM_out_scope_begin`, `PM_out_scope_end`, `insert_time`) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		
		query.setInteger(0, ac.getOrg_id());
		query.setInteger(1, ac.getAttnd_num());
		query.setInteger(2, ac.getType());
		query.setInteger(3, ac.getGrade_id());
		query.setString(4, ac.getGrade_name());
		
		query.setTime(5, ac.getAM_in_time());
		query.setTime(6, ac.getAM_in_scope_begin());
		query.setTime(7, ac.getAM_in_scope_end());
		
		query.setTime(8, ac.getAM_out_time());
		query.setTime(9, ac.getAM_out_scope_begin());
		query.setTime(10, ac.getAM_out_scope_end());
		
		query.setTime(11, ac.getPM_in_time());
		query.setTime(12, ac.getPM_in_scope_begin());
		query.setTime(13, ac.getPM_in_scope_end());
		
		query.setTime(14, ac.getPM_out_time());
		query.setTime(15, ac.getPM_out_scope_begin());
		query.setTime(16, ac.getPM_out_scope_end());
		
		query.setTimestamp(17, ac.getInsert_time());
		
		query.executeUpdate();
		
	}

	@Override
	public List<AttendanceConf> getAttnConfList(int orgId) {
		String sql = "SELECT ac.* FROM shijiwxy.attendance_conf ac WHERE ac.org_id=?;";

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);

		query.setInteger(0, orgId);
		query.setResultTransformer(Transformers.aliasToBean(AttendanceConf.class));

		return query.list();
	}

	@Override
	public int deleteAttnConfAndAttnConfModify(int orgId) {
		String deleteConfSql = "DELETE ac FROM shijiwxy.attendance_conf ac WHERE ac.org_id=?;";
		String deleteConfModifySql = "DELETE acm FROM shijiwxy.attendance_conf_modify acm WHERE acm.org_id=?;";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(deleteConfSql);
		query.setInteger(0, orgId);
		query.executeUpdate();
		
		query = session.createSQLQuery(deleteConfModifySql);
		query.setInteger(0, orgId);
		query.executeUpdate();
		
		return 1;
	}


}
