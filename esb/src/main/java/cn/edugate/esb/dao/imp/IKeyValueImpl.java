package cn.edugate.esb.dao.imp;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IKeyValueDao;
import cn.edugate.esb.entity.KeyValue;

/**
 * 键值DAO实现类
 * Title:IKeyValueImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年7月17日上午9:59:29
 */
@Repository
public class IKeyValueImpl extends BaseDAOImpl<KeyValue> implements IKeyValueDao {

	@Override
	public List<KeyValue> getListByType(Integer type) {
		Session session=this.factory.getCurrentSession();
		Criteria c=session.createCriteria(KeyValue.class);
		c.add(Restrictions.eq("type", type));
		@SuppressWarnings("unchecked")
		List<KeyValue> ls = c.list();
		return ls;
	}

}
