package cn.edugate.esb.dao.imp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IModule2FunctionDao;
import cn.edugate.esb.entity.Module2Function;

/**
 * 模块和功能关系DAO实现
 * Title:IModule2FunctionImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月11日下午4:09:29
 */
@Repository
public class IModule2FunctionImpl extends BaseDAOImpl<Module2Function> implements IModule2FunctionDao {

	@Override
	public List<Module2Function> getList(Module2Function module2function) {
		Session session=this.factory.getCurrentSession();
		Criteria c=session.createCriteria(Module2Function.class);
		if(null!=module2function.getMod_id()){
			c.add(Restrictions.eq("mod_id", module2function.getMod_id()));
		}
		if(null!=module2function.getFun_id()){
			c.add(Restrictions.eq("fun_id", module2function.getFun_id()));
		}
		if(null==module2function.getIs_del()){
			c.add(Restrictions.eq("is_del", 0));
		}else{
			c.add(Restrictions.eq("is_del", module2function.getIs_del()));
		}
		@SuppressWarnings("unchecked")
		List<Module2Function> ls = c.list();
		return ls;
	}

	@Override
	public void deleteByIDs(Integer moduleID, Integer functionID) {
		Session session=this.factory.getCurrentSession();
		String sql = "update module2function mf SET mf.is_del=1 where mf.mod_id=:mod_id AND mf.fun_id=:fun_id";
		Query query = session.createSQLQuery(sql);
		query.setInteger("mod_id", moduleID);
		query.setInteger("fun_id", functionID);
		query.executeUpdate();		
	}

}
