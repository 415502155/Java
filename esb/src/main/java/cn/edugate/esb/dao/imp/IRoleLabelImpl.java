package cn.edugate.esb.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IRoleLabelDao;
import cn.edugate.esb.entity.RoleLabel;

@Repository
public class IRoleLabelImpl extends BaseDAOImpl<RoleLabel> implements IRoleLabelDao {

	@Override
	public RoleLabel getRoleLabel(int orgType, String roleName) {
		String sql = "SELECT rl.* FROM role_label rl WHERE rl.rl_type=? AND rl.rl_name=? AND rl.is_del=0;";
		
		Session session = this.factory.getCurrentSession();
		
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, orgType);
		query.setString(1, roleName);
		query.setResultTransformer(Transformers.aliasToBean(RoleLabel.class));
		
		List<RoleLabel> labelList = query.list();
		RoleLabel label = null;
		if (labelList != null && labelList.size() > 0) {
			label = labelList.get(0);
		}
		return label;
	}


}
