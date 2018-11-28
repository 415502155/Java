package cn.edugate.esb.dao.imp;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.DepartmentDao;
import cn.edugate.esb.entity.Department;
import cn.edugate.esb.util.Utils;

@Repository
public class DepartmentDaoImpl  extends BaseDAOImpl<Department> implements DepartmentDao{

	@Override
	public Integer getDefaultId(Integer depId) {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT d.dep_id FROM department d INNER JOIN department dp ON dp.org_id=d.org_id AND dp.is_del=d.is_del WHERE dp.dep_id=:dep_id AND d.type=1";
		Query query = session.createSQLQuery(sql);
		query.setInteger("dep_id", depId);
		@SuppressWarnings("unchecked")
		List<Integer> list = query.list();
		if(null!=list){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public int saveDefaultDP(Department dp) {
		dp.setDep_name(Utils.getValue(dp.getDep_name()));
		dp.setIs_del(0);
		dp.setInsert_time(new Date());
		dp.setUpdate_time(new Date());
		super.save(dp);
		return dp.getDep_id();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> getDepsDetailList(String org_id) {
		Session session=this.factory.getCurrentSession();
		String sql = " SELECT d.org_id,d.dep_id,d.dep_name,d.dep_officephone,( SELECT count(1) FROM teacher t INNER JOIN org_user u ON t.user_id=u.user_id AND t.org_id=u.org_id AND u.is_del=0 AND u.identity=1 WHERE t.dep_id = d.dep_id AND t.is_del = 0 ) AS mem_num,GROUP_CONCAT(t1.tech_name) as manager_name,d.dep_note,d.type FROM department d "+
					 " LEFT JOIN tech_range tr ON d.org_id=tr.org_id AND d.dep_id=tr.dep_id AND tr.rl_id=7 AND tr.is_del=0 "+
				 	 " LEFT JOIN teacher t1 ON t1.tech_id=tr.tech_id AND t1.org_id=tr.org_id AND t1.org_id=d.org_id "+
				 	 " where d.org_id=:org_id AND d.is_del=0 GROUP BY d.dep_id ORDER BY d.type DESC,d.insert_time DESC ";
		Query query = session.createSQLQuery(sql);
		query.setString("org_id", org_id);
		query.setResultTransformer(Transformers.aliasToBean(Department.class));
		List<Department> list = query.list();
		return list;
	}
	
	/**
	 * 在非默认部门中检查名称是否存在
	 */
	@Override
	public int checkName(int orgID, String depName) {
		String sql = "SELECT COUNT(d.dep_id) FROM edugate_base.department d WHERE d.org_id=? AND d.dep_name=? AND d.type=0 AND d.is_del=0;";
		
		Session session=this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, orgID);
		query.setString(1, Utils.getValue(depName));
		
		BigInteger count = (BigInteger) query.uniqueResult();
		return count.intValue();
	}

	/**
	 * 通过名称在非默认部门中查询
	 */
	@Override
	public Department getNonDefaultDeptEntity(int orgID, String depName) {
		String sql = "SELECT d.* FROM edugate_base.department d WHERE d.org_id=? AND d.dep_name=? AND d.type=0 AND d.is_del=0;";

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, orgID);
		query.setString(1, Utils.getValue(depName));
		query.setResultTransformer(Transformers.aliasToBean(Department.class));

		@SuppressWarnings("unchecked")
		List<Department> dpList = query.list();
		Department dp = null;
		if (dpList != null && dpList.size() > 0) {
			dp = dpList.get(0);
		}
		return dp;
	}
	
	/**
	 * 查询机构下 的默认部门
	 */
	@Override
	public Department getDefaultDeptEntity(int orgID) {
		String sql = "SELECT d.* FROM edugate_base.department d WHERE d.org_id=? AND d.type=1 AND d.is_del=0;";

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, orgID);
		query.setResultTransformer(Transformers.aliasToBean(Department.class));

		@SuppressWarnings("unchecked")
		List<Department> dpList = query.list();
		Department dp = null;
		if (dpList != null && dpList.size() > 0) {
			dp = dpList.get(0);
		}
		return dp;
	}

}
