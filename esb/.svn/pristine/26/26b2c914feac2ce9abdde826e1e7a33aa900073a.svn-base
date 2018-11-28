package cn.edugate.esb.dao.imp;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IOrgUserDao;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.util.LSHelper;
import cn.edugate.esb.util.Paging;
/**
 * 机构用户
 * Title:IUserImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年4月25日下午2:19:34
 */
@Repository
public class IOrgUserImpl extends BaseDAOImpl<OrgUser> implements IOrgUserDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<OrgUser> getAllByRoleId(Integer roleID) {
		Session session=this.factory.getCurrentSession();
		String sql = "select u.id,u.type,u.number,u.login_name,u.login_pass,u.lz_number,u.status,u.mail,u.mobile,u.card,u.identity,u.salt,u.head,u.sex,u.birthday,u.loginnum,u.insert_time,u.is_del,u.del_time,u.update_time,u.org_type,u.udid from user u inner join user2role ur on u.id=ur.sso_id where ur.role_id=:roleID order by u.insert_time desc";
		Query query = session.createSQLQuery(sql);
		query.setInteger("roleID", roleID);
		query.setResultTransformer(Transformers.aliasToBean(OrgUser.class));
		List<OrgUser> list = query.list();
		for (OrgUser orgUser : list) {
			LSHelper.processInjection(orgUser);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paging<OrgUser> getAllByRoleIdWithPaging(Paging<OrgUser> paging,String name,String mobile,String ssoType,String mail,String rid,String orgType,String udid) {
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
		sqlQuery = sqlQuery.setResultTransformer(Transformers.aliasToBean(OrgUser.class));
		List<OrgUser> list = sqlQuery.list();
		for (OrgUser orgUser : list) {
			LSHelper.processInjection(orgUser);
		}
		paging.setData(list);
		return paging;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paging<OrgUser> getAllWithPaging(Paging<OrgUser> paging, String name,
			String mobile, String ssoType, String mail,String orgType,String udid) {
		Session session=this.factory.getCurrentSession();
		int limitFrom = (paging.getPage()-1)*paging.getLimit();
		String limit =  " limit " + limitFrom +"," + paging.getLimit();
		String order = " order by u.insert_time desc";
		StringBuffer where = new StringBuffer(" where u.is_del=0 ");
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
		StringBuffer countTotal = new StringBuffer("select count(1) from user u");
		StringBuffer sql = new StringBuffer("select u.id,u.type,u.number,u.login_name,u.login_pass,u.lz_number,u.status,u.mail,u.mobile,u.card,u.identity,u.salt,u.head,u.sex,u.birthday,u.insert_time,u.is_del,u.del_time,u.update_time,u.org_type from user u");
		Query countQuery = session.createSQLQuery(countTotal.append(where).append(order).toString());
		Query sqlQuery = session.createSQLQuery(sql.append(where).append(order).append(limit).toString());
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
		sqlQuery.setResultTransformer(Transformers.aliasToBean(OrgUser.class));
		List<OrgUser> list = sqlQuery.list();
		for (OrgUser orgUser : list) {
			LSHelper.processInjection(orgUser);
		}
		paging.setData(list);
		return paging;
	}

	@Override
	public OrgUser getUserByLoginName(String login_name, Integer org_id) {
		Session session=this.factory.getCurrentSession();
		String hql = "SELECT u FROM OrgUser u WHERE u.user_loginname=:login_name AND u.org_id=:org_id";
		Query query = session.createQuery(hql);
		query.setString("login_name", login_name);
		query.setInteger("org_id", org_id);
		OrgUser list =  (OrgUser) query.uniqueResult();
		if(list!=null){
			LSHelper.processInjection(list);
		}
		return list;
	}	

	@Override
	public List<OrgUser> getList() {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT * FROM org_user u where u.is_del=0";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(OrgUser.class));
		@SuppressWarnings("unchecked")
		List<OrgUser> list =  query.list();
//		for(OrgUser t:list)
//		{
//			LSHelper.processInjection(t);
//		}		
		return list;
	}

	@Override
	public Long getTotalCount(String loginname, String org_id, String mobile, Integer identity) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql="SELECT count(*) FROM org_user u WHERE 1=1 ";
		if (!"".equals(loginname)) {
			sql+=" and u.user_loginname like :loginname";
		}
		if (!"".equals(org_id)) {			
			sql+= " and u.org_id=:org_id";
		}
		if (!"".equals(mobile)) {
			sql+= " and u.user_mobile like :mobile";
		}
		sql+= " and u.identity = :identity";
		sql+= " order by u.user_id desc";
		Query query = session.createSQLQuery(sql);
		if (!"".equals(loginname)) {
			query.setString("loginname","%"+loginname+"%");
		}
		if (!"".equals(org_id)) {
			query.setInteger("org_id", Integer.parseInt(org_id));
		}
		if (!"".equals(mobile)) {
			query.setString("mobile", "%"+mobile+"%");
		}
		query.setInteger("identity", identity);
		BigInteger count = (BigInteger) query.uniqueResult();
		return count.longValue();
	}

	@Override
	public void getOrgUserWithPaging(String loginname, String org_id,
			String mobile, Integer identity, Paging<OrgUser> pages) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql="SELECT * FROM org_user u WHERE 1=1";
		if (!"".equals(loginname)) {
			sql+=" and u.user_loginname like :loginname";
		}
		if (!"".equals(org_id)) {			
			sql+= " and u.org_id=:org_id";
		}
		if (!"".equals(mobile)) {
			sql+= " and u.user_mobile like :mobile";
		}
		sql+= " and u.identity = :identity and u.is_del=0";
		sql+= " order by u.user_id desc";
		Query query = session.createSQLQuery(sql);
		if (!"".equals(loginname)) {
			query.setString("loginname","%"+loginname+"%");
		}
		if (!"".equals(org_id)) {
			query.setInteger("org_id", Integer.parseInt(org_id));
		}
		if (!"".equals(mobile)) {
			query.setString("mobile", "%"+mobile+"%");
		}
		query.setInteger("identity", identity);
		query.setFirstResult((pages.getPage()-1)*pages.getLimit()+pages.getStart());
		query.setMaxResults(pages.getLimit());
		query.setResultTransformer(Transformers.aliasToBean(OrgUser.class));
		@SuppressWarnings("unchecked")
		List<OrgUser> ls = query.list();
		for (OrgUser orgUser : ls) {
			LSHelper.processInjection(orgUser);
		}
		pages.setData(ls);
	}

	@Override
	public List<OrgUser> getListByPhone(String phone, Integer org_id) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql="SELECT * FROM org_user u WHERE 1=1 AND u.user_mobile=:phone ";
		if (org_id>0) {
			sql+=" and u.org_id = :org_id";
		}
		
		sql+= " order by u.user_id desc";
		Query query = session.createSQLQuery(sql);
		
		if (org_id>0) {
			query.setInteger("org_id", org_id);
		}
		query.setString("phone", phone);
		query.setResultTransformer(Transformers.aliasToBean(OrgUser.class));
		@SuppressWarnings("unchecked")
		List<OrgUser> ls = query.list();
		for (OrgUser orgUser : ls) {
			LSHelper.processInjection(orgUser);
		}
		return ls;
	}

	@Override
	public OrgUser getOrgUser(int orgID, int identity, String mobile) {
		String sql = "SELECT ou.* FROM edugate_base.org_user ou WHERE ou.org_id=? AND ou.identity=? AND ou.user_mobile=? AND ou.is_del=0;";
		
		Session session=this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, orgID);
		query.setInteger(1, identity);
		query.setString(2, mobile);
		query.setResultTransformer(Transformers.aliasToBean(OrgUser.class));		
		@SuppressWarnings("unchecked")
		List<OrgUser> ls = query.list();
		OrgUser ou = null;
		if (ls != null && ls.size() > 0) {
			ou = ls.get(0);
			LSHelper.processInjection(ou);
		}
		
		return ou;
	}

	@Override
	public boolean getUserIsExist(String loginName, Integer identity,
			Integer org_id) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT count(ou.user_id) FROM org_user ou WHERE ou.user_loginname =:loginName AND ou.org_id=:org_id AND ou.identity =:identity AND ou.is_del = 0";
		Query query = session.createSQLQuery(sql.toString());
		query.setString("loginName",loginName);
		query.setInteger("org_id",org_id);
		query.setInteger("identity", identity);		
		BigInteger count = (BigInteger) query.uniqueResult();
		
		return count.intValue()>0?true:false;
	}

	@Override
	public void deleteOrgUser(List<OrgUser> list) {
		for (OrgUser orgUser : list) {
			orgUser.setIs_del(true);
			orgUser.setDel_time(new Date());
			super.update(orgUser);
		}
	}

	@Override
	public OrgUser getAdminByOrgid(Integer org_id) {
		// TODO Auto-generated method stub
		String sql = "SELECT ou.* FROM edugate_base.org_user ou WHERE ou.org_id=:org_id AND ou.identity=:identity AND ou.is_del=0;";		
		Session session=this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger("org_id", org_id);
		query.setInteger("identity", 99);
		query.setResultTransformer(Transformers.aliasToBean(OrgUser.class));		
		@SuppressWarnings("unchecked")
		List<OrgUser> ls = query.list();
		OrgUser ou = null;
		if (ls != null && ls.size() > 0) {
			ou = ls.get(0);
		}		
		return ou;
	}

	@Override
	public Paging<OrgUser> getOrgStudentByParentWithPaging(String card, String org_id, String mobile, Paging<OrgUser> pages) {
		Session session=this.factory.getCurrentSession();
		int limitFrom = (pages.getPage()-1)*pages.getLimit();
		String limit =  " LIMIT " + limitFrom +"," + pages.getLimit();
		String order = "GROUP BY s.stud_id ORDER BY suser.insert_time DESC ";
		StringBuffer where = new StringBuffer(" where 1=1 ");
		if (StringUtils.isNotBlank(card)) {
			where.append(" AND suser.user_loginname like :loginname ");
		}
		if (StringUtils.isNotBlank(org_id)) {	
			where.append(" AND suser.org_id=:org_id ");
		}
		if (StringUtils.isNotBlank(mobile)) {
			where.append(" AND puser.user_mobile like :mobile ");
		}
		//StringBuffer countTotal = new StringBuffer("SELECT count(1) FROM org_user suser LEFT JOIN student s ON suser.user_id=s.user_id AND suser.org_id=s.org_id AND suser.identity=2 AND suser.is_del=0 AND s.is_del=0 LEFT JOIN student2parent sp ON sp.stud_id=s.stud_id AND sp.is_del=0 LEFT JOIN parent p ON p.parent_id=sp.parent_id AND p.is_del=0 AND p.org_id=s.org_id AND p.org_id=suser.org_id  LEFT JOIN org_user puser ON p.org_id=puser.org_id AND p.user_id=puser.user_id AND puser.org_id=suser.org_id AND puser.org_id=s.org_id AND puser.is_del=0 AND puser.identity=0 ");
		StringBuffer sql = new StringBuffer("SELECT  suser.user_id,suser.org_id,suser.identity,suser.user_loginname,suser.user_mobile,suser.insert_time,GROUP_CONCAT(c.card_no) user_mail FROM org_user suser LEFT JOIN student s ON suser.user_id=s.user_id AND suser.org_id=s.org_id AND suser.identity=2 AND suser.is_del=0 AND s.is_del=0 LEFT JOIN card c  ON s.stud_id = c.stud_id LEFT JOIN student2parent sp ON sp.stud_id=s.stud_id AND sp.is_del=0 LEFT JOIN parent p ON p.parent_id=sp.parent_id AND p.is_del=0 AND p.org_id=s.org_id AND p.org_id=suser.org_id  LEFT JOIN org_user puser ON p.org_id=puser.org_id AND p.user_id=puser.user_id AND puser.org_id=suser.org_id AND puser.org_id=s.org_id AND puser.is_del=0 AND puser.identity=0 ");
		//Query countQuery = session.createSQLQuery(countTotal.append(where).toString());
		Query sqlQuery = session.createSQLQuery(sql.append(where).append(order).append(limit).toString());
		if (StringUtils.isNotBlank(card)) {
			//countQuery.setString("loginname", "%"+loginname+"%");
			sqlQuery.setString("loginname", "%"+card+"%");
		}
		if (StringUtils.isNotBlank(org_id)) {	
			//countQuery.setString("org_id", org_id);
			sqlQuery.setString("org_id", org_id);
		}
		if (StringUtils.isNotBlank(mobile)) {
			//countQuery.setString("mobile", "%"+mobile+"%");
			sqlQuery.setString("mobile", "%"+mobile+"%");
		}
		int pageTotal = 100;//(BigInteger) countQuery.uniqueResult();
		pages.setTotal(pageTotal);
		sqlQuery = sqlQuery.setResultTransformer(Transformers.aliasToBean(OrgUser.class));
		@SuppressWarnings("unchecked")
		List<OrgUser> list = sqlQuery.list();
		for (OrgUser orgUser : list) {
			LSHelper.processInjection(orgUser);
		}
		pages.setData(list);
		return pages;
	}

	@Override
	public List<OrgUser> getOrgUserListIncludeDeleted(int org_id) {
		//String sql = "SELECT ou.* FROM org_user ou WHERE ou.org_id=? AND (ou.identity=0 OR ou.identity=1 OR ou.identity=2);";
		String sql = "SELECT ou.* FROM org_user ou WHERE ou.org_id=? AND (ou.identity=0 OR ou.identity=2);";
		Session session=this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(OrgUser.class));
		query.setInteger(0, org_id);
		
		return query.list();
	}

	@Override
	public List<OrgUser> getOrguserByOrgId(int org_id) {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT * FROM org_user u where u.is_del=0 and u.org_id=?";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(OrgUser.class));
		query.setInteger(0, org_id);
		@SuppressWarnings("unchecked")
		List<OrgUser> list =  query.list();
		return list;
	}

	@Override
	public Paging<OrgUser> getOrgStudentByCard(String card, String org_id,
			Paging<OrgUser> pages) {
		Session session=this.factory.getCurrentSession();
		int limitFrom = (pages.getPage()-1)*pages.getLimit();
		String limit =  " LIMIT " + limitFrom +"," + pages.getLimit();
		String order = "GROUP BY s.stud_id ORDER BY suser.insert_time DESC ";
		StringBuffer where = new StringBuffer(" where 1=1 ");
		
		if (StringUtils.isNotBlank(card)) {	
			where.append(" AND c.card_no LIKE :card ");
		}
		
		if (StringUtils.isNotBlank(org_id)) {	
			where.append(" AND suser.org_id=:org_id ");
		}
		
		StringBuffer sql = new StringBuffer(" SELECT suser.user_id,suser.org_id,suser.identity, suser.user_loginname, suser.user_mobile,suser.insert_time, c.card_no user_mail FROM org_user suser INNER JOIN student s ON suser.user_id = s.user_id AND suser.org_id = s.org_id AND suser.identity = 2 AND suser.is_del = 0 AND s.is_del = 0 INNER JOIN card c ON s.stud_id = c.stud_id");
	
		Query sqlQuery = session.createSQLQuery(sql.append(where).append(order).append(limit).toString());
	
		if (StringUtils.isNotBlank(card)) {
			sqlQuery.setString("card", "%"+card+"%");
		}
		
		if (StringUtils.isNotBlank(org_id)) {
			sqlQuery.setString("org_id", org_id);
		}
		
		
		int pageTotal = 100;
		pages.setTotal(pageTotal);
		sqlQuery = sqlQuery.setResultTransformer(Transformers.aliasToBean(OrgUser.class));
		@SuppressWarnings("unchecked")
		List<OrgUser> list = sqlQuery.list();
		for (OrgUser orgUser : list) {
			LSHelper.processInjection(orgUser);
		}
		pages.setData(list);
		return pages;
	}

}
