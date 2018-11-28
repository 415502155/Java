package cn.edugate.esb.dao.imp;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IFieldDao;
import cn.edugate.esb.entity.Field;

/**
 * 场地DAO实现类
 * Title:IFieldImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月27日上午10:15:06
 */
@Repository
public class IFieldImpl extends BaseDAOImpl<Field> implements IFieldDao{

	@Override
	public List<Field> getList(Field field) {
		Session session=this.factory.getCurrentSession();
		Criteria c=session.createCriteria(Field.class);
		if(null!=field.getOrg_id()){
			c.add(Restrictions.eq("org_id", field.getOrg_id()));
		}
		if(StringUtils.isNotEmpty(field.getField_name())){
			c.add(Restrictions.like("field_name", field.getField_name()));
		}
		if(null!=field.getField_type()){
			c.add(Restrictions.eq("field_type", field.getField_type()));
		}
		if(StringUtils.isNotEmpty(field.getField_number())){
			c.add(Restrictions.like("field_number", field.getField_number()));
		}
		if(null==field.getIs_del()){
			c.add(Restrictions.eq("is_del", 0));
		}else{
			c.add(Restrictions.eq("is_del", field.getIs_del()));
		}
		@SuppressWarnings("unchecked")
		List<Field> ls = c.list();
		return ls;
	}

}
