package cn.edugate.esb.dao.imp;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IOrganizationDao;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.util.LSHelper;
import cn.edugate.esb.util.Paging;
import cn.edugate.esb.util.Utils;

@Repository
public class OrganizationDaoImpl extends BaseDAOImpl<Organization> implements IOrganizationDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> getOrgList() {
		Session session = this.factory.getCurrentSession();
		String sql = "select * from organization  u where u.is_del=0";
		Query query = session.createSQLQuery(sql).addEntity(Organization.class);
		//query.setResultTransformer(Transformers.aliasToBean(Organization.class));
		List<Organization> list = new ArrayList<Organization>();
		list = query.list();
		
		for (Organization organization : list) {
			LSHelper.processInjection(organization);
		}
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> getOrgByType(int type) {
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT o.* FROM organization o WHERE o.is_del=0 AND o.type=:type";
		Query query = session.createSQLQuery(sql);
		query.setInteger("type", type);
		query.setResultTransformer(Transformers.aliasToBean(Organization.class));
		List<Organization> list = new ArrayList<Organization>();
		list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> getOrgByFunID(int funId) {
		Session session = this.factory.getCurrentSession();
		String sql = "select u.org_id, u.org_name_cn from menu m left join organization u on m.org_id=u.org_id where u.is_del=0 and m.fun_id=:funId";
		Query query = session.createSQLQuery(sql);
		query.setInteger("fun_id", funId);
		query.setResultTransformer(Transformers.aliasToBean(Organization.class));
		List<Organization> list = new ArrayList<Organization>();
		list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paging<Organization> getOrgListWithPaging(Paging<Organization> paging, Organization organization) {
		StringBuffer countSql = new StringBuffer("SELECT COUNT(org.org_id) FROM edugate_base.organization org WHERE org.is_del=0");
		StringBuffer querySql = new StringBuffer(
				"SELECT org.*, COUNT(ot.lower_id) AS childOrgNum FROM edugate_base.organization org LEFT JOIN edugate_base.org_tree ot ON ot.org_id=org.org_id AND ot.is_del=0 WHERE org.is_del=0");

		// 检索输入了名称
		if (StringUtils.isNotBlank(organization.getOrg_name_cn())) {
			countSql.append(" AND org.org_name_cn LIKE :orgName ;");
			querySql.append(" AND org.org_name_cn LIKE :orgName");
		}

		querySql.append(" GROUP BY org.org_id ORDER BY org.insert_time DESC LIMIT :pageFrom, :pageCount ;");

		Session session = this.factory.getCurrentSession();

		Query countQuery = session.createSQLQuery(countSql.toString());

		if (StringUtils.isNotBlank(organization.getOrg_name_cn())) {
			countQuery.setString("orgName", "%" + organization.getOrg_name_cn() + "%");
		}

		BigInteger count = (BigInteger) countQuery.uniqueResult();

		paging.setTotal(count.longValue());

		List<Organization> orgList = new ArrayList<Organization>();
		if (count.longValue() > 0) {
			// 存在多条记录，进行分页查询
			Query query = session.createSQLQuery(querySql.toString());

			if (StringUtils.isNotBlank(organization.getOrg_name_cn())) {
				query.setString("orgName", "%" + organization.getOrg_name_cn() + "%");
			}

			int limitFrom = (paging.getPage() - 1) * paging.getLimit();
			query.setInteger("pageFrom", limitFrom);
			query.setInteger("pageCount", paging.getLimit());

			query.setResultTransformer(Transformers.aliasToBean(Organization.class));

			orgList = query.list();
		}

		paging.setData(orgList);

		return paging;
	}

	@Override
	public int checkName(String orgName) {
		String sql = "SELECT COUNT(org.org_id) FROM edugate_base.organization org WHERE org.org_name_cn = ? AND org.is_del=0;";
		Session session = this.factory.getCurrentSession();
		Query countQuery = session.createSQLQuery(sql);
		countQuery.setString(0, orgName.trim());

		BigInteger count = (BigInteger) countQuery.uniqueResult();
		return count.intValue();
	}

	@Override
	public int saveOrganization(Organization orgEntity) {
		orgEntity.setOrg_name_cn(Utils.getValue(orgEntity.getOrg_name_cn()));
		orgEntity.setOrg_name_s_cn(Utils.getValue(orgEntity.getOrg_name_s_cn()));
		orgEntity.setOrg_name_en(Utils.getValue(orgEntity.getOrg_name_en()));
		orgEntity.setOrg_name_s_en(Utils.getValue(orgEntity.getOrg_name_s_en()));
		orgEntity.setAddress(Utils.getValue(orgEntity.getAddress()));
		orgEntity.setWebsite(Utils.getValue(orgEntity.getWebsite()));
		orgEntity.setPostcode(Utils.getValue(orgEntity.getPostcode()));
		orgEntity.setContact(Utils.getValue(orgEntity.getContact()));
		orgEntity.setContact_mobile(Utils.getValue(orgEntity.getContact_mobile()));
		orgEntity.setRemark(Utils.getValue(orgEntity.getRemark()));
		orgEntity.setCss(Utils.getValue(orgEntity.getCss()));
		orgEntity.setCopyright_info(Utils.getValue(orgEntity.getCopyright_info()));
		orgEntity.setPolice_record(Utils.getValue(orgEntity.getPolice_record()));
		orgEntity.setPolice_record_url(Utils.getValue(orgEntity.getPolice_record_url()));
		orgEntity.setICP_record(Utils.getValue(orgEntity.getICP_record()));
		orgEntity.setICP_record_url(Utils.getValue(orgEntity.getICP_record_url()));
		orgEntity.setIs_del(false);
		orgEntity.setInsert_time(new Date());
		orgEntity.setUpdate_time(new Date());
		super.save(orgEntity);
		return orgEntity.getOrg_id();
	}

	@Override
	public int updateOrganizationLogo(Organization orgEntity) {
		String sql = "UPDATE edugate_base.organization org SET org.logo = ? WHERE org.org_id = ? ;";

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setString(0, StringUtils.isNotBlank(orgEntity.getLogo()) ? orgEntity.getLogo().trim() : null);
		query.setInteger(1, orgEntity.getOrg_id());

		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paging<Organization> getListWithPaging(Paging<Organization> paging, String orgName, Integer functionId) {
		Session session = this.factory.getCurrentSession();
		int limitFrom = (paging.getPage() - 1) * paging.getLimit();
		String limit = " limit " + limitFrom + "," + paging.getLimit();
		String order = " order by o.insert_time desc ";
		StringBuffer where = new StringBuffer(" where m.is_del=0 AND o.is_del=0 AND f.is_del=0 and f.fun_id = :fun_id");
		if (StringUtils.isNotEmpty(orgName)) {
			where.append(" and (o.org_name_cn like :orgName or o.org_name_en like :orgName or o.org_name_s_cn like :orgName or org_name_s_en like :orgName) ");
		}
		StringBuffer countTotal = new StringBuffer(
				"SELECT count(DISTINCT o.org_id) FROM `organization` o INNER JOIN menu m ON o.org_id=m.org_id INNER JOIN `function` f ON f.fun_id=m.fun_id ");
		StringBuffer sql = new StringBuffer(
				"SELECT	DISTINCT o.org_id,o.org_name_cn,o.org_name_en,o.org_name_s_cn,o.org_name_s_en,o.type,o.address,o.remark,o.area FROM `organization` o INNER JOIN menu m ON o.org_id=m.org_id INNER JOIN `function` f ON f.fun_id=m.fun_id ");
		Query countQuery = session.createSQLQuery(countTotal.append(where).append(order).toString());
		Query sqlQuery = session.createSQLQuery(sql.append(where).append(order).append(limit).toString());
		if (StringUtils.isNotEmpty(orgName)) {
			countQuery.setString("orgName", "%" + orgName + "%");
			sqlQuery.setString("orgName", "%" + orgName + "%");
		}
		countQuery.setInteger("fun_id", functionId);
		sqlQuery.setInteger("fun_id", functionId);
		BigInteger pageTotal = (BigInteger) countQuery.uniqueResult();
		paging.setTotal(pageTotal.intValue());
		sqlQuery = sqlQuery.setResultTransformer(Transformers.aliasToBean(Organization.class));
		List<Organization> list = new ArrayList<Organization>();
		list = sqlQuery.list();
		paging.setData(list);
		return paging;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> getLowerOrgList(int orgID) {
		String sql = "SELECT o.* FROM edugate_base.organization o WHERE o.org_id IN "
				+ "(SELECT ot.lower_id FROM edugate_base.org_tree ot WHERE ot.org_id=? AND ot.is_del=0) AND o.is_del=0;";

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, orgID);
		query.setResultTransformer(Transformers.aliasToBean(Organization.class));
		List<Organization> list = query.list();
		return list;
	}

	@Override
	public int updateOrganizationBackground(Organization orgEntity) {
		String sql = "UPDATE edugate_base.organization org SET org.bg = ? WHERE org.org_id = ? ;";

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setString(0, StringUtils.isNotBlank(orgEntity.getBg()) ? orgEntity.getBg().trim() : null);
		query.setInteger(1, orgEntity.getOrg_id());

		return query.executeUpdate();
	}

	@Override
	public Integer delete(int id) {
		return null;
	}

	@Override
	public int updateOrganizationWelCome(Organization orgEntity) {
		String sql = "UPDATE edugate_base.organization org SET org.welcome = ? WHERE org.org_id = ? ;";

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setString(0, StringUtils.isNotBlank(orgEntity.getWelcome()) ? orgEntity.getWelcome().trim() : null);
		query.setInteger(1, orgEntity.getOrg_id());

		return query.executeUpdate();
	}

	@Override
	public void executeBatchImport(final Organization orgEntity) {
		final String importTeacher = "{CALL importTech(?, ?)}";

		final String importTechGroup = "{CALL importTechGroup(?, ?)}";

		final String importCourse = "{CALL importCourse(?, ?)}";

		final String importGrade = "{CALL importGrade(?, ?)}";

		Session session = this.factory.getCurrentSession();
		session.doReturningWork(new ReturningWork<Integer>() {

			@Override
			public Integer execute(Connection connection) throws SQLException {
				CallableStatement cs = (CallableStatement) connection.prepareCall(importTeacher);
				cs.registerOutParameter(1, Types.INTEGER);
				cs.setString(2, orgEntity.getOrg_name_cn());
				cs.executeUpdate();
				return cs.getInt(1);

			}
		});

		session.doReturningWork(new ReturningWork<Integer>() {

			@Override
			public Integer execute(Connection connection) throws SQLException {
				CallableStatement cs = connection.prepareCall(importTechGroup);
				cs.registerOutParameter(1, Types.INTEGER);
				cs.setString(2, orgEntity.getOrg_name_cn());
				cs.executeUpdate();
				return cs.getInt(1);

			}
		});

		session.doReturningWork(new ReturningWork<Integer>() {

			@Override
			public Integer execute(Connection connection) throws SQLException {
				CallableStatement cs = connection.prepareCall(importCourse);
				cs.registerOutParameter(1, Types.INTEGER);
				cs.setString(2, orgEntity.getOrg_name_cn());
				cs.executeUpdate();
				return cs.getInt(1);

			}
		});

		session.doReturningWork(new ReturningWork<Integer>() {

			@Override
			public Integer execute(Connection connection) throws SQLException {
				CallableStatement cs = connection.prepareCall(importGrade);
				cs.registerOutParameter(1, Types.INTEGER);
				cs.setString(2, orgEntity.getOrg_name_cn());
				cs.executeUpdate();
				return cs.getInt(1);

			}
		});
	}

	@Override
	public List<Organization> getSchool(String district) {
		String sql =    " SELECT "+
							" s.schl_name as org_name_cn, "+
							" s.schl_engname as org_name_en, "+
							" s.schl_abbreviation as org_name_s_cn, "+
							" CASE s.schl_type  WHEN 1 THEN 0  "+
							" WHEN 2 THEN 2  "+
							" WHEN 3 THEN 3  "+
							" END as schoolType, "+
							" CONCAT(REPLACE(REPLACE(REPLACE(schl_district,'}{','_'),'{',''),'}',''),'_',GROUP_CONCAT(DISTINCT g.grade_type)) as area, "+
							" s.schl_address as address, "+
							" s.schl_weburl as website, "+
							" s.schl_note as remark "+
						" FROM `school.foundation`.school s "+
						" LEFT JOIN `school.foundation`.grade g ON g.schl_id=s.schl_id "+
						" WHERE s.schl_district LIKE ? AND s.deleted=0 "+
						" GROUP BY org_name_cn ";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setString(0, "%"+district+"%");
		query.setResultTransformer(Transformers.aliasToBean(Organization.class));
		@SuppressWarnings("unchecked")
		List<Organization> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getOrgInFunctionByFunId(Integer funId) {
		String sql = "SELECT m.org_id FROM menu m WHERE m.fun_id=:funId AND m.is_del=0 AND m.parent_id<>0";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger("funId", funId);
		List<Object> list = query.list();
		List<String> result = new ArrayList<String>();
		for (Object obj : list) {
			result.add(obj.toString());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getOrgInModuleByFunId(Integer funId) {
		String sql = "SELECT m.org_id FROM menu m INNER JOIN module2function mf ON mf.mod_id=m.mod_id AND mf.is_del=0 AND m.is_del=0 AND mf.fun_id=:funId AND m.parent_id = 0";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger("funId", funId);
		List<Object> list = query.list();
		List<String> result = new ArrayList<String>();
		for (Object obj : list) {
			result.add(obj.toString());
		}
		return result;
	}

	@Override
	public Organization getOrgByIdForRedis(int org_id) {
		Session session = this.factory.getCurrentSession();
		String sql = "select * from organization u where u.is_del=0 and u.org_id=?";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Organization.class));
		query.setInteger(0, org_id);
		Organization org = (Organization) query.uniqueResult();
		LSHelper.processInjection(org);
		return org;
	}

	@Override
	public List<Organization> getOrgList4Paging(int type, String orgName, String area, String upgradeStatus, int year, int offset, int rows) {
		String sql = "SELECT o.* FROM organization o WHERE o.type=? AND o.is_del=0 ";
		if (StringUtils.isNotBlank(orgName)) {
			sql += " AND o.org_name_cn LIKE ? ";
		}

		if (StringUtils.isNotBlank(area)) {
			sql += " AND o.area LIKE ? ";
		}
		
		if (StringUtils.isNotBlank(upgradeStatus)) {
			// 0 是未升级
			if ("0".equals(upgradeStatus)) {
				sql += " AND o.org_id NOT IN (SELECT uh.org_id FROM upgrade_history uh WHERE uh.`year`=?)";
			} else if ("1".equals(upgradeStatus)) {
				sql += " AND o.org_id IN (SELECT uh.org_id FROM upgrade_history uh WHERE uh.`year`=?)";
			}
		}
		
		sql += " ORDER BY o.org_id ASC LIMIT ?, ?";

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);

		int index = 0;
		query.setInteger(index, type);
		index++;
		if (StringUtils.isNotBlank(orgName)) {
			query.setString(index, "%"+ orgName + "%");
			index++;
		}
		if (StringUtils.isNotBlank(area)) {
			query.setString(index, area + "%");
			index++;
		}
		if (StringUtils.isNotBlank(upgradeStatus)) {
			query.setInteger(index, year);
			index++;
		}
		query.setInteger(index, offset);
		index++;
		query.setInteger(index, rows);
		index++;

		query.setResultTransformer(Transformers.aliasToBean(Organization.class));

		List<Organization> orgList = query.list();

		return orgList;
	}

	@Override
	public int getTotalRecordNum4ClassUpgrade(int type, String orgName, String area, String upgradeStatus, int year) {
		String sql = "SELECT COUNT(o.org_id) AS totalNum FROM organization o WHERE o.type=? AND o.is_del=0 ";
		if (StringUtils.isNotBlank(orgName)) {
			sql += " AND o.org_name_cn LIKE ? ";
		}

		if (StringUtils.isNotBlank(area)) {
			sql += " AND o.area LIKE ? ";
		}
		
		if (StringUtils.isNotBlank(upgradeStatus)) {
			// 0 是未升级
			if ("0".equals(upgradeStatus)) {
				sql += " AND o.org_id NOT IN (SELECT uh.org_id FROM upgrade_history uh WHERE uh.`year`=?)";
			} else if ("1".equals(upgradeStatus)) {
				sql += " AND o.org_id IN (SELECT uh.org_id FROM upgrade_history uh WHERE uh.`year`=?)";
			}
		}
		
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);

		int index = 0;
		query.setInteger(index, type);
		index++;
		if (StringUtils.isNotBlank(orgName)) {
			query.setString(index, "%"+ orgName + "%");
			index++;
		}
		if (StringUtils.isNotBlank(area)) {
			query.setString(index, area + "%");
			index++;
		}
		if (StringUtils.isNotBlank(upgradeStatus)) {
			query.setInteger(index, year);
			index++;
		}

		BigInteger total = (BigInteger) query.uniqueResult();
		
		return total.intValue();
	}

	@Override
	public List<Organization> getOrgList(int type, String orgName, String area, String upgradeStatus, int year) {
		String sql = "SELECT o.* FROM organization o WHERE o.type=? AND o.is_del=0 ";
		if (StringUtils.isNotBlank(orgName)) {
			sql += " AND o.org_name_cn LIKE ? ";
		}

		if (StringUtils.isNotBlank(area)) {
			sql += " AND o.area LIKE ? ";
		}
		
		if (StringUtils.isNotBlank(upgradeStatus)) {
			// 0 是未升级
			if ("0".equals(upgradeStatus)) {
				sql += " AND o.org_id NOT IN (SELECT uh.org_id FROM upgrade_history uh WHERE uh.`year`=?)";
			} else if ("1".equals(upgradeStatus)) {
				sql += " AND o.org_id IN (SELECT uh.org_id FROM upgrade_history uh WHERE uh.`year`=?)";
			}
		}
		
		sql += " ORDER BY o.org_id ASC";

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);

		int index = 0;
		query.setInteger(index, type);
		index++;
		if (StringUtils.isNotBlank(orgName)) {
			query.setString(index, "%"+ orgName + "%");
			index++;
		}
		if (StringUtils.isNotBlank(area)) {
			query.setString(index, area + "%");
			index++;
		}
		if (StringUtils.isNotBlank(upgradeStatus)) {
			query.setInteger(index, year);
			index++;
		}

		query.setResultTransformer(Transformers.aliasToBean(Organization.class));

		List<Organization> orgList = query.list();

		return orgList;
	}

}
