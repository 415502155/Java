package cn.edugate.esb.dao.imp;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IFunctionUseRangeDao;
import cn.edugate.esb.entity.FunctionUseRange;
/**
 * 功能适用范围DAO实现类
 * Title:IFunctionUseRangeImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月16日下午3:49:03
 */
@Repository
public class IFunctionUseRangeImpl extends BaseDAOImpl<FunctionUseRange> implements IFunctionUseRangeDao {

	@Override
	public String getGradeNumStringByFunID(Integer id) {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT GROUP_CONCAT(fur.grade_number) FROM fun_use_range fur where fur.is_del=0 and fur.fun_id=:fun_id";
		Query query = session.createSQLQuery(sql);
		query.setInteger("fun_id", id);
		String result = (String) query.uniqueResult();
		return result;
	}

	@Override
	public void deleteByFunctionID(Integer functionID) {
		Session session=this.factory.getCurrentSession();
		String sql = "UPDATE fun_use_range f SET f.is_del=1 WHERE f.fun_id=:fun_id";
		Query query = session.createSQLQuery(sql);
		query.setInteger("fun_id", functionID);
		query.executeUpdate();
	}

}
