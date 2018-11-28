package cn.edugate.esb.dao.imp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IUcUserDao;
import cn.edugate.esb.entity.Group;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.UcUser;
import cn.edugate.esb.entity.UcuserOrguser;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.LSHelper;
import cn.edugate.esb.util.Paging;
/**
 * base用户
 * Title:IUserImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年4月25日下午2:19:34
 */
@Repository
public class IUcUserImpl extends BaseDAOImpl<UcUser> implements IUcUserDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<UcUser> getAllByRoleId(Integer roleID) {
		Session session=this.factory.getCurrentSession();
		String sql = "select u.id,u.type,u.number,u.login_name,u.login_pass,u.lz_number,u.status,u.mail,u.mobile,u.card,u.identity,u.salt,u.head,u.sex,u.birthday,u.loginnum,u.insert_time,u.is_del,u.del_time,u.update_time,u.org_type,u.udid from user u inner join user2role ur on u.id=ur.sso_id where ur.role_id=:roleID order by u.insert_time desc";
		Query query = session.createSQLQuery(sql);
		query.setInteger("roleID", roleID);
		query.setResultTransformer(Transformers.aliasToBean(UcUser.class));
		List<UcUser> list = new ArrayList<UcUser>();
		list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paging<UcUser> getAllByRoleIdWithPaging(Paging<UcUser> paging,String name,String mobile,String ssoType,String mail,String rid,String orgType,String udid) {
		Session session=this.factory.getCurrentSession();
		int limitFrom = (paging.getPage()-1)*paging.getLimit();
		String limit =  " limit " + limitFrom +"," + paging.getLimit();
		String order = " order by u.insert_time desc";
		StringBuffer where = new StringBuffer(" where u.is_del=0");
		if(!StringUtils.isEmpty(rid)){
			where.append(" and ur.role_id=:role_id");
		}
		if(!StringUtils.isEmpty(name)){
			where.append(" and u.login_name like '%:login_name% ");
		}
		if(!StringUtils.isEmpty(mobile)){
			where.append(" and u.mobile like '%:mobile%' ");
		}
		if(!StringUtils.isEmpty(ssoType)){
			where.append(" and u.type =:type");
		}
		if(!StringUtils.isEmpty(mail)){
			where.append(" and u.mail like '%:mail%' ");
		}
		if(!StringUtils.isEmpty(orgType)){
			where.append(" and u.org_type =:org_type");
		}
		if(!StringUtils.isEmpty(udid)){
			where.append(" and u.udid like '%:udid%' ");
		}
		StringBuffer countTotal = new StringBuffer("select count(distinct(u.id)) from user u inner join user2role ur on u.id=ur.sso_id ");
		StringBuffer sql = new StringBuffer("select distinct u.id,u.type,u.number,u.login_name,u.login_pass,u.lz_number,u.status,u.mail,u.mobile,u.card,u.identity,u.salt,u.head,u.sex,u.birthday,u.insert_time,u.is_del,u.del_time,u.update_time,u.org_type from user u inner join user2role ur on u.id=ur.sso_id ");
		Query countQuery = session.createSQLQuery(countTotal.append(where).append(order).toString());
		Query sqlQuery = session.createSQLQuery(sql.append(where).append(order).append(limit).toString());
		if(!StringUtils.isEmpty(rid)){
			countQuery.setInteger("role_id", Integer.parseInt(rid));
			sqlQuery.setInteger("role_id", Integer.parseInt(rid));
		}
		if(!StringUtils.isEmpty(name)){
			countQuery.setString("login_name", name);
			sqlQuery.setString("login_name", name);
		}
		if(!StringUtils.isEmpty(mobile)){
			countQuery.setString("mobile", mobile);
			sqlQuery.setString("mobile", mobile);
		}
		if(!StringUtils.isEmpty(ssoType)){
			countQuery.setInteger("type", Integer.parseInt(ssoType));
			sqlQuery.setInteger("type", Integer.parseInt(ssoType));
		}
		if(!StringUtils.isEmpty(mail)){
			countQuery.setString("mail", mail);
			sqlQuery.setString("mail", mail);
		}
		if(!StringUtils.isEmpty(orgType)){
			countQuery.setInteger("org_type", Integer.parseInt(orgType));
			sqlQuery.setInteger("org_type", Integer.parseInt(orgType));
		}
		if(!StringUtils.isEmpty(udid)){
			countQuery.setString("udid", udid);
			sqlQuery.setString("udid", udid);
		}
		BigInteger pageTotal = (BigInteger) countQuery.uniqueResult();
		paging.setTotal(pageTotal.intValue());
		sqlQuery = sqlQuery.setResultTransformer(Transformers.aliasToBean(UcUser.class));
		List<UcUser> list = new ArrayList<UcUser>();
		list = sqlQuery.list();
		paging.setData(list);
		return paging;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paging<UcUser> getAllWithPaging(Paging<UcUser> paging, String name,
			String mobile, String ssoType, String mail,String orgType,String udid) {
		Session session=this.factory.getCurrentSession();
		int limitFrom = (paging.getPage()-1)*paging.getLimit();
		String limit =  " limit " + limitFrom +"," + paging.getLimit();
		String order = " order by u.insert_time desc";
		StringBuffer where = new StringBuffer(" where u.is_del=0 ");
		if(!StringUtils.isEmpty(name)){
			where.append(" and u.uc_loginname like '%:login_name% ");
		}
		if(!StringUtils.isEmpty(mobile)){
			where.append(" and u.uc_mobile like '%:mobile%' ");
		}
		if(!StringUtils.isEmpty(ssoType)){
			where.append(" and u.uc_type =:type");
		}
		if(!StringUtils.isEmpty(mail)){
			where.append(" and u.uc_mail like '%:mail%' ");
		}
		
		StringBuffer countTotal = new StringBuffer("select count(1) from uc_user u");
		StringBuffer sql = new StringBuffer("select * from uc_user u");
		Query countQuery = session.createSQLQuery(countTotal.append(where).append(order).toString());
		Query sqlQuery = session.createSQLQuery(sql.append(where).append(order).append(limit).toString());
		if(!StringUtils.isEmpty(name)){
			countQuery.setString("uc_loginname", name);
			sqlQuery.setString("uc_loginname", name);
		}
		if(!StringUtils.isEmpty(mobile)){
			countQuery.setString("uc_mobile", mobile);
			sqlQuery.setString("uc_mobile", mobile);
		}
		if(!StringUtils.isEmpty(ssoType)){
			countQuery.setInteger("uc_type", Integer.parseInt(ssoType));
			sqlQuery.setInteger("uc_type", Integer.parseInt(ssoType));
		}
		if(!StringUtils.isEmpty(mail)){
			countQuery.setString("uc_mail", mail);
			sqlQuery.setString("uc_mail", mail);
		}
		
		BigInteger pageTotal = (BigInteger) countQuery.uniqueResult();
		paging.setTotal(pageTotal.intValue());
		sqlQuery.setResultTransformer(Transformers.aliasToBean(UcUser.class));
		List<UcUser> list = new ArrayList<UcUser>();
		list = sqlQuery.list();
		paging.setData(list);
		return paging;
	}

	@Override
	public UcUser getUserByLoginName(String login_name, Integer org_type) {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT * FROM `user` u WHERE u.login_name=:login_name AND u.org_type=:org_type";
		Query query = session.createSQLQuery(sql);
		query.setString("login_name", login_name);
		query.setInteger("org_type", org_type);
		query.setResultTransformer(Transformers.aliasToBean(UcUser.class));
		UcUser list =  (UcUser) query.uniqueResult();
		return list;
	}

	@Override
	public List<UcUser> getUserList() {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT * FROM uc_user u";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(UcUser.class));
		@SuppressWarnings("unchecked")
		List<UcUser> list =  query.list();
		for (UcUser ucUser : list) {
			LSHelper.processInjection(ucUser);
		}
		return list;
	}

	@Override
	public List<UcuserOrguser> getOrgByUCID(String uc_id) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT u.uc_id,u.user_id,ou.identity,ou.user_mobile,o.org_id,o.org_name_cn,o.org_name_en,o.org_name_s_cn,o.org_name_s_en,o.logo,t.tech_id,t.tech_name,t.dep_id,t.tech_nick,t.sex,t.birthday,t.tech_head FROM org_user ou inner JOIN   ucuser2orguser u ON ou.user_id=u.user_id INNER JOIN organization o ON ou.org_id=o.org_id  INNER JOIN teacher t ON ou.user_id=t.user_id WHERE u.uc_id=:uc_id AND ou.is_del=0 AND u.is_del=0 AND o.is_del=0  AND t.is_del=0";
		Query query = session.createSQLQuery(sql);
		query.setString("uc_id", uc_id);
		query.setResultTransformer(Transformers.aliasToBean(UcuserOrguser.class));
		//List<UcuserOrguser> list = new ArrayList<UcuserOrguser>();
		List<UcuserOrguser> list = query.list();
		for (UcuserOrguser ucUser : list) {
			LSHelper.processInjection(ucUser);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrgUser getUserForManager(Integer org_id) {
		Session session=this.factory.getCurrentSession();
		String sql = " SELECT u.user_loginname,u.user_idnumber,u.org_id FROM org_user u WHERE u.identity=99 AND u.is_del=0 AND u.org_id=:org_id ";
		Query query = session.createSQLQuery(sql);
		query.setInteger("org_id", org_id);
		query.setResultTransformer(Transformers.aliasToBean(OrgUser.class));
		List<OrgUser> list =  query.list();
		if(null!=list&&list.size()!=0){
			return list.get(0);
		}else{
			return new OrgUser();
		}
	}
}
