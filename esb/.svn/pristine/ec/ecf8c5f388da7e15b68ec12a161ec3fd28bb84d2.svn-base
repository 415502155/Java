package cn.edugate.esb.dao.imp;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IRoleCodeDao;
import cn.edugate.esb.entity.RoleCode;

/**
 * 权限编码DAO实现类
 * Title: IRoleCodeImpl
 * Description: 
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年3月21日上午9:19:00
 */
@Repository
public class IRoleCodeImpl extends BaseDAOImpl<RoleCode> implements IRoleCodeDao {

	@Override
	public List<RoleCode> getList(RoleCode roleCode) {
		Session session=this.factory.getCurrentSession();
		String sql = " SELECT rc.role_code_id,rc.`code`,rc.`value`,rc.`name`,rc.parent_id FROM role_code rc WHERE rc.is_del=0 ";
		if(StringUtils.isNotBlank(roleCode.getCode())){
			sql += " AND rc.`code` LIKE :code ";
		}
		if(StringUtils.isNotBlank(roleCode.getName())){
			sql += " AND rc.`name` LIKE :name ";
		}
		sql += " ORDER BY rc.`code`,rc.parent_id ASC,rc.role_code_id DESC ";
		Query query = session.createSQLQuery(sql);
		if(StringUtils.isNotBlank(roleCode.getCode())){
			query.setString("code", "%"+roleCode.getCode()+"%");
		}
		if(StringUtils.isNotBlank(roleCode.getName())){
			query.setString("name", "%"+roleCode.getName()+"%");
		}
	    query.setResultTransformer(Transformers.aliasToBean(RoleCode.class));
	    @SuppressWarnings("unchecked")
		List<RoleCode> ls =query.list();
		return ls;
	}

}
