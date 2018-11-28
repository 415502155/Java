package cn.edugate.esb.dao.imp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IRoleDao;
import cn.edugate.esb.eduEnum.EnumRoleLabel;
import cn.edugate.esb.entity.Role;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Paging;
/**
 * 角色DAO实现
 * Title:IRoleImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年4月24日下午1:40:53
 */
@Repository
public class IRoleImpl extends BaseDAOImpl<Role> implements IRoleDao {

	@Override
	public List<Role> getByUserId(Integer userId, Integer orgId) {
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT r.* FROM role r INNER JOIN tech2role t2r INNER JOIN teacher t "
				+ "WHERE r.role_id= t2r.role_id AND r.is_del=0 AND t2r.tech_id=t.user_id AND t2r.is_del=0 "
				+ "AND t.user_id=? AND t.org_id=? AND t.is_del=0 ORDER BY r.insert_time DESC;";
		Query query = session.createSQLQuery(sql.toString());
		query.setInteger(0, userId);
		query.setInteger(1, orgId);
		query.setResultTransformer(Transformers.aliasToBean(Role.class));
		@SuppressWarnings("unchecked")
		List<Role> ls = query.list();
		return ls;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paging<Role> getAllWithPaging(Paging<Role> paging,String roleName) {
		Session session=this.factory.getCurrentSession();
		int limitFrom = (paging.getPage()-1)*paging.getLimit();
		String limit =  " limit " + limitFrom +"," + paging.getLimit();
		String order = " order by r.insert_time desc";
		StringBuffer where = new StringBuffer(" where r.is_del=0 ");
		if(!StringUtils.isEmpty(roleName)){
			where.append(" and r.role_name like '%:role_name%' ");
		}
		StringBuffer countTotal = new StringBuffer("select count(1) from role r ");
		StringBuffer sql = new StringBuffer("select r.role_id,r.role_name,r.role_note,r.insert_time,r.is_del,r.del_time,r.update_time from role r ");
		Query countQuery = session.createSQLQuery(countTotal.append(where).append(order).toString());
		Query sqlQuery = session.createSQLQuery(sql.append(where).append(order).append(limit).toString());
		if(!StringUtils.isEmpty(roleName)){
			countQuery.setString("role_name", roleName);
			sqlQuery.setString("role_name", roleName);
		}
		BigInteger pageTotal = (BigInteger) countQuery.uniqueResult();
		paging.setTotal(pageTotal.intValue());
		sqlQuery.setResultTransformer(Transformers.aliasToBean(Role.class));
		List<Role> list = new ArrayList<Role>();
		list = sqlQuery.list();
		paging.setData(list);
		return paging;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paging<Role> getByUserIdWithPaging(Integer userId,Paging<Role> paging) {
		Session session=this.factory.getCurrentSession();
		int limitFrom = (paging.getPage()-1)*paging.getLimit();
		StringBuffer countTotalSQL = new StringBuffer();
		countTotalSQL.append("select count(1) from role r")
					.append(" inner join user2role ur")
					.append(" on r.role_id = ur.role_id")
					.append(" and ur.is_del=0")
					.append(" inner join `user` u")
					.append(" on u.sso_id = ur.sso_id")
					.append(" and u.is_del=0")
					.append(" where u.sso_id=:sso_id");
		StringBuffer sql = new StringBuffer();
		sql.append("select r.role_id,r.role_name,r.role_note,r.insert_time,r.is_del,r.del_time,r.update_time from role r")
			.append(" inner join user2role ur")
			.append(" on r.role_id = ur.role_id")
			.append(" and ur.is_del=0")
			.append(" inner join `user` u")
			.append(" on u.sso_id = ur.sso_id")
			.append(" and u.is_del=0")
			.append(" where u.sso_id=:sso_id")
			.append(" order by r.insert_time desc ")
			.append(" limit ")
			.append(limitFrom)
			.append(",")
			.append(paging.getLimit());
		Query query = session.createSQLQuery(countTotalSQL.toString());
		query.setInteger("sso_id", userId);
		int pageTotal = (int) query.uniqueResult();
		paging.setTotal(pageTotal);
		query = session.createSQLQuery(sql.toString());
		query.setInteger("sso_id", userId);
		query.setResultTransformer(Transformers.aliasToBean(Role.class));
		List<Role> list = new ArrayList<Role>();
		list = query.list();
		paging.setData(list);
		return paging;
	}

	@Override
	public String createManagerRole(Integer orgId,Integer orgType) {
//		if(Constant.FUR_TYPE_SCHOOLS.equals(orgType)){
//			Role master = new Role();
//			master.setOrg_id(orgId);
//			master.setRl_id(EnumRoleLabel.校长.getCode());
//			master.setRole_name(EnumRoleLabel.校长.name());
//			master.setRole_note("系统自动创建的机构默认校长角色");
//			master.setAuthorities("{\""+Constant.ALL_AUTH+"\":262143}");
//			master.setIs_del(false);
//			master.setInsert_time(new Date());
//			super.save(master);
//			Role role = new Role();
//			role.setOrg_id(orgId);
//			role.setRl_id(EnumRoleLabel.管理员.getCode());
//			role.setRole_name(EnumRoleLabel.管理员.name());
//			role.setAuthorities("{\""+Constant.ALL_AUTH+"\":262143}");
//			role.setRole_note("系统自动创建的最高管理员权限");
//			role.setInsert_time(new Date());
//			role.setIs_del(false);
//			super.save(role);
//			return role.getRole_id().toString();
//		}else if(Constant.FUR_TYPE_BUREAU.equals(orgType)){
//			Role master = new Role();
//			master.setOrg_id(orgId);
//			master.setRl_id(EnumRoleLabel.局长.getCode());
//			master.setRole_name(EnumRoleLabel.局长.name());
//			master.setRole_note("系统自动创建的机构默认局长角色");
//			master.setAuthorities("{\""+Constant.ALL_AUTH+"\":262143}");
//			master.setIs_del(false);
//			master.setInsert_time(new Date());
//			super.save(master);
//			Role role = new Role();
//			role.setOrg_id(orgId);
//			role.setRl_id(EnumRoleLabel.局管理员.getCode());
//			role.setRole_name(EnumRoleLabel.局管理员.name());
//			role.setAuthorities("{\""+Constant.ALL_AUTH+"\":262143}");
//			role.setRole_note("系统自动创建的最高管理员权限");
//			role.setInsert_time(new Date());
//			role.setIs_del(false);
//			super.save(role);
//			return role.getRole_id().toString();
//		}
//		return null;
		Role role = new Role();
		role.setOrg_id(orgId);
		role.setRl_id(EnumRoleLabel.管理员.getCode());
		role.setRole_name(EnumRoleLabel.管理员.name());
		role.setAuthorities("{\""+Constant.ALL_AUTH+"\":262143}");
		role.setRole_note("系统自动创建的最高管理员权限");
		role.setInsert_time(new Date());
		role.setIs_del(false);
		super.save(role);
		return role.getRole_id().toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getOrgRoleCode(Integer org_id) {
		Session session=this.factory.getCurrentSession();
		/*String sql = "SELECT rc.role_code_id AS id, rc.`code`, rc.`value`, rc.`name`, rc.parent_id, m.menu_name  "+
					   " FROM role_code rc "+
					   " INNER JOIN `function` f ON rc.`code` = f.fun_code AND f.is_del = 0 "+
					   " INNER JOIN menu m ON m.fun_id = f.fun_id AND m.is_del = 0 AND m.org_id=:org_id "+
					   " UNION ALL "+
					   " SELECT rc.role_code_id AS id, rc.`code`, rc.`value`, rc.`name`, rc.parent_id, '其他' as menu_name "+
					   " FROM role_code rc "+
					   " WHERE rc.`code` = '999' ";*/
		String sql = "SELECT rc.role_code_id AS id, rc.`code`, rc.`value`, rc.`name`, rc.parent_id, m.menu_name ,mm.mod_code, mm.mod_name "+
				   " FROM role_code rc "+
				   " INNER JOIN `function` f ON rc.`code` = f.fun_code AND f.is_del = 0 "+
				   " INNER JOIN module2function mf ON f.fun_id=mf.fun_id AND mf.is_del=0 "+
				   " INNER JOIN module mm ON mm.mod_id=mf.mod_id AND mm.is_del=0 "+
				   " INNER JOIN menu m ON m.fun_id = f.fun_id AND m.is_del = 0 AND m.org_id=:org_id AND m.menu_type<>1 WHERE rc.is_del=0 "+
				   " UNION ALL "+
				   " SELECT rc.role_code_id AS id, rc.`code`, rc.`value`, rc.`name`, rc.parent_id, '其他' as menu_name , 'others' as mod_code, '未归类权限' as mod_name "+
				   " FROM role_code rc "+
				   " WHERE rc.`code` = '999' AND rc.is_del=0";
//		String sql = "	SELECT rc.role_code_id AS id, rc.`code`, rc.`value`, rc.`name`, rc.parent_id FROM role_code rc INNER JOIN organization o ON o.type=rc.type WHERE o.is_del=0 and rc.is_del=0 AND o.org_id=:org_id";
		Query query = session.createSQLQuery(sql.toString());
		query.setInteger("org_id", org_id);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getOrgRole(Integer org_id) {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT r.rl_id,r.role_name,r.authorities,rl.rl_name,rl.permissionsAll,r.role_id FROM role r INNER JOIN role_label rl ON r.rl_id=rl.rl_id WHERE r.org_id=:org_id";
		Query query = session.createSQLQuery(sql.toString());
		query.setInteger("org_id", org_id);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRoleListByOrgID(int orgID) {
		String sql = "SELECT r.* FROM edugate_base.role r WHERE r.org_id=? AND r.is_del=0;";
		Session session = this.factory.getCurrentSession();
		
		Query q = session.createSQLQuery(sql);
		q.setInteger(0, orgID);
		q.setResultTransformer(Transformers.aliasToBean(Role.class));
		
		List<Role> result = q.list();
		return result;
	}

	@Override
	public Role getRole(int orgID, String roleName) {
		String sql = "SELECT r.* FROM role r WHERE r.org_id=? AND r.role_name=? AND r.is_del=0;";

		Session session = this.factory.getCurrentSession();

		Query q = session.createSQLQuery(sql);
		q.setResultTransformer(Transformers.aliasToBean(Role.class));
		q.setInteger(0, orgID);
		q.setString(1, roleName);

		List<Role> resultList = q.list();
		Role r = null;

		if (resultList != null && resultList.size() > 0) {
			r = resultList.get(0);
		}
		return r;
	}

}
