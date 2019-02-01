package sng.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import freemarker.ext.dom.Transform;
import sng.dao.UserAuthDao;
import sng.entity.UserAuthentication;
import sng.entity.UserRegister;
import sng.pojo.ParamObj;
import sng.util.Constant;
import sng.util.Paging;

/**
 * @desc 用户中心--用户详情认证
 * @author magq
 * @data 2018-11-27
 * @version 1.0
 */
@Repository
public class UserAuthDaoImpl extends BaseDaoImpl<UserAuthentication> implements UserAuthDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see sng.dao.UserAuthDao#queryStudentInfo(sng.pojo.ParamObj)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Paging queryUserAuthInfo(ParamObj obj) {

		Paging paging = new Paging();
		StringBuffer sql = new StringBuffer();
		StringBuffer countSql = new StringBuffer();
		List<Object> queryParam = new ArrayList<Object>();
		sql.append(
				"select user_register_id,org_id,stud_name,nick_name,open_id,head_url,card_type,card_num,DATE_FORMAT(brithday,'%Y-%m-%d') as brithday,telephone,relation,auth_status,login_salt,login_password");
		sql.append(" from user_register");
		sql.append(" where is_del=0");
		if (obj.getOrg_id() != null) {
			sql.append(" and org_id=?");
			queryParam.add(obj.getOrg_id());
		}

		if (obj.getUser_id() != null) {
			sql.append(" and user_register_id=?");
			queryParam.add(obj.getUser_id());
		}

		if (obj.getOpenId() != null) {
			sql.append(" and open_id=?");
			queryParam.add(obj.getOpenId());
		}

		if (StringUtils.isNotBlank(obj.getAuthQueryContent())) {
			sql.append(" and (");
			sql.append(" stud_name like ? ");
			queryParam.add("%" + obj.getAuthQueryContent() + "%");
			sql.append(" or telephone like ?");
			queryParam.add("%" + obj.getAuthQueryContent() + "%");
			sql.append(" or card_num like ?");
			queryParam.add("%" + obj.getAuthQueryContent() + "%");
			sql.append(" )");
		}
		if (StringUtils.isNotBlank(obj.getUser_mobile())) {
			sql.append(" and telephone=?");
			queryParam.add(obj.getUser_mobile());
		}
		if (obj.getAuth_status() != null) {
			sql.append(" and auth_status=?");
			queryParam.add(obj.getAuth_status());
		}

		sql.append(" order by auth_status");

		Session session = this.factory.getCurrentSession();

		Query query = null;
		int count = 0;
		if (obj.isNeedCount()) { // 是否分页
			countSql.append("select count(*) from ( " + sql.toString() + " ) T");
			query = session.createSQLQuery(countSql.toString());
			for (int i = 0; i < queryParam.size(); i++) {
				query.setParameter(i, queryParam.get(i));
			}
			count = Integer.parseInt(query.uniqueResult().toString());
			sql.append(" limit ?,?");
			queryParam.add(obj.getLimit() * (obj.getPage() - 1));
			queryParam.add(obj.getLimit());
		}

		query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < queryParam.size(); i++) {
			query.setParameter(i, queryParam.get(i));
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		paging.setPage(obj.getPage());
		paging.setLimit(obj.getLimit());
		paging.setSize(count % obj.getLimit() == 0 ? (count / obj.getLimit()) : (count / obj.getLimit() + 1));
		paging.setTotal(count);
		paging.setData(list);
		paging.setSuccess(true);
		return paging;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sng.dao.UserAuthDao#updateStudentInfo(sng.pojo.ParamObj)
	 */
	@Override
	public int updateUserAuthInfo(UserRegister userAuth) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		sql.append("update user_register set is_del=?,update_time=?");
		paramList.add(userAuth.getIsDel());
		paramList.add(userAuth.getUpdateTime());
		if (StringUtils.isNotBlank(userAuth.getStudName())) {
			sql.append(",stud_name=?");
			paramList.add(userAuth.getStudName());
		}
		if (userAuth.getCardType() != null) {
			sql.append(",card_type=?");
			paramList.add(userAuth.getCardType());
		}
		if (StringUtils.isNotBlank(userAuth.getCardNum())) {
			sql.append(",card_num=?");
			paramList.add(userAuth.getCardNum());
		}
		if (userAuth.getBrithday() != null) {
			sql.append(",brithday=?");
			paramList.add(userAuth.getBrithday());
		}
		if (userAuth.getAuthStatus() != null) {
			sql.append(",auth_status=?");
			paramList.add(userAuth.getAuthStatus());
		}
		if (StringUtils.isNotBlank(userAuth.getTelephone())) {
			sql.append(",telephone=?");
			paramList.add(userAuth.getTelephone());
			sql.append(",login_name=?");
			paramList.add(userAuth.getTelephone());
		}
		if (StringUtils.isNotBlank(userAuth.getNick_name())) {
			sql.append(",nick_name=?");
			paramList.add(userAuth.getNick_name());
		}
		if (StringUtils.isNotBlank(userAuth.getHead_url())) {
			sql.append(",head_url=?");
			paramList.add(userAuth.getHead_url());
		}
		if (StringUtils.isNotBlank(userAuth.getOpen_id())) {
			sql.append(",open_id=?");
			paramList.add(userAuth.getOpen_id());
		}
		if (userAuth.getRelation() != null) {
			sql.append(",relation=?");
			paramList.add(userAuth.getRelation());
		}
		if (StringUtils.isNotBlank(userAuth.getLoginPassword())) {
			sql.append(",login_password=?");
			paramList.add(userAuth.getLoginPassword());
		}

		sql.append(" where user_register_id=? ");
		paramList.add(userAuth.getUserRegisterId());
		if (userAuth.getOrg_id() != null) {
			sql.append(" and org_id=?");
			paramList.add(userAuth.getOrgId());
			
		}

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		for (int i = 0; i < paramList.size(); i++) {
			query.setParameter(i, paramList.get(i));
		}
		int count = query.executeUpdate();
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sng.dao.UserAuthDao#deltudentInfo(sng.pojo.ParamObj)
	 */
	@Override
	public int delUserAuthInfo(ParamObj obj) {
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		sql.append("delete from user_register");
		sql.append(" where is_del = 0");
		sql.append(" and auth_status=0"); // 未认证的才可以删除
		if (obj.getUser_id() != null) {
			sql.append(" and user_register_id=?");
			paramList.add(obj.getUser_id());
		}
		if (obj.getOrg_id() != null) {
			sql.append(" and org_id=?");
			paramList.add(obj.getOrg_id());
		}
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		int count = 0;
		for (int i = 0; i < paramList.size(); i++) {
			query.setParameter(i, paramList.get(i));
		}
		count = query.executeUpdate();
		return count;
	}

	@Override
	public int countIdNumber(String idNumber, Integer org_id, String name, Integer identity) {
		Session session = this.factory.getCurrentSession();
		String sql = " SELECT COUNT(1) FROM edugate_base.org_user u ";
		if (null != identity && StringUtils.isNotBlank(name)) {
			if (Constant.IDENTITY_TEACHER == identity) {
				sql += " INNER JOIN edugate_base.teacher t ON u.user_id=t.user_id AND u.org_id=t.org_id AND t.is_del=0 AND t.tech_name =:name ";
			}
			if (Constant.IDENTITY_PARENT == identity) {
				sql += " INNER JOIN edugate_base.parent p ON u.user_id=p.user_id AND u.org_id=p.org_id AND p.is_del=0 AND p.parent_name=:name ";
			}
			if (Constant.IDENTITY_STUDENT == identity) {
				sql += " INNER JOIN edugate_base.student s ON s.user_id=u.user_id AND s.org_id=u.org_id AND s.is_del=0 AND s.stud_name=:name ";
			}
		}
		sql += " WHERE u.user_idnumber =:user_idnumber AND u.is_del =:is_del ";
		if (null != org_id) {
			sql += " AND u.org_id =:org_id ";
		}
		if (null != identity) {
			sql += " AND u.identity =:identity ";
		}
		Query query = session.createSQLQuery(sql);
		if (null != org_id) {
			query.setInteger("org_id", org_id);
		}
		if (null != identity) {
			query.setInteger("identity", identity);
			if (StringUtils.isNotBlank(name)) {
				query.setString("name", name);
			}
		}
		query.setInteger("is_del", Constant.IS_DEL_NO);
		query.setString("user_idnumber", idNumber);
		BigInteger num = (BigInteger) query.uniqueResult();
		return num.intValue();
	}

	@Override
	public List queryUserAuthListInfo(ParamObj obj) {
		StringBuffer sql = new StringBuffer();
		List<Object> queryParam = new ArrayList<Object>();
		sql.append(
				"select auth_status as authStatus,card_num as cardNum,brithday,telephone,relation,card_type as cardType");
		sql.append(" from user_register");
		sql.append(" where is_del=0");
		if (obj.getOrg_id() != null) {
			sql.append(" and org_id=?");
			queryParam.add(obj.getOrg_id());
		}

		if (obj.getUser_id() != null) {
			sql.append(" and user_register_id=?");
			queryParam.add(obj.getUser_id());
		}

		if (obj.getOpenId() != null) {
			sql.append(" and open_id=?");
			queryParam.add(obj.getOpenId());
		}

		if (StringUtils.isNotBlank(obj.getAuthQueryContent())) {
			sql.append(" and (");
			sql.append(" stud_name like ? ");
			queryParam.add("%" + obj.getAuthQueryContent() + "%");
			sql.append(" or telephone like ?");
			queryParam.add("%" + obj.getAuthQueryContent() + "%");
			sql.append(" or card_num like ?");
			queryParam.add("%" + obj.getAuthQueryContent() + "%");
			sql.append(" )");
		}

		if (StringUtils.isNotBlank(obj.getCard_num())) {
			sql.append(" and card_num = ?");
			queryParam.add(obj.getCard_num());
		}

		if (StringUtils.isNotBlank(obj.getUser_mobile())) {
			sql.append(" and telephone=?");
			queryParam.add(obj.getUser_mobile());
		}
		if (obj.getAuth_status() != null) {
			sql.append(" and auth_status=?");
			queryParam.add(obj.getAuth_status());
		}

		sql.append(" order by auth_status desc");

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		query.setResultTransformer(Transformers.aliasToBean(UserRegister.class));
		for (int i = 0; i < queryParam.size(); i++) {
			query.setParameter(i, queryParam.get(i));
		}
		List<UserRegister> list = query.list();
		return list;

	}

}
