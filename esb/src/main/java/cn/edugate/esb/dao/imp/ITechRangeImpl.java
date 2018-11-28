package cn.edugate.esb.dao.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.ITechRangeDao;
import cn.edugate.esb.eduEnum.EnumRoleLabel;
import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.LSHelper;

@Repository
public class ITechRangeImpl extends BaseDAOImpl<TechRange> implements ITechRangeDao {

	@Override
	public String getAdminOrgsByTechId(Integer tech_id) {
		// TODO Auto-generated method stub
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT GROUP_CONCAT(DISTINCT(tr.org_id)) FROM tech_range tr WHERE tr.rl_id=:rl_id AND tr.is_del=0 AND tr.tech_id=:tech_id ";
		Query query = session.createSQLQuery(sql);
		query.setInteger("tech_id", tech_id);
		query.setInteger("rl_id", EnumRoleLabel.管理员.getCode());
		Object item = query.uniqueResult();
		return item != null ? item.toString() : "";
	}

	@Override
	public String getHeadmasterOrgsByTechId(Integer tech_id) {
		// TODO Auto-generated method stub
		Session session = this.factory.getCurrentSession();
		String sql = "SELECT GROUP_CONCAT(DISTINCT(tr.org_id)) FROM tech_range tr WHERE tr.rl_id=:rl_id AND tr.is_del=0 AND tr.tech_id=:tech_id ";
		Query query = session.createSQLQuery(sql);
		query.setInteger("tech_id", tech_id);
		query.setInteger("rl_id", EnumRoleLabel.校长.getCode());
		Object item = query.uniqueResult();
		return item != null ? item.toString() : "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getTeaching(String org_id, String tech_id, String grade_id, String cour_id, String clas_id) {
		// TODO Auto-generated method stub
		Session session = this.factory.getCurrentSession();
		StringBuffer sql = new StringBuffer();
		sql.append(
				" SELECT tr.tr_id,t.tech_id,t.tech_name,t.tech_nick,g.grade_id,g.grade_name,c.clas_id,c.clas_name,c1.cour_id,c1.cour_name ")
				.append(" FROM tech_range tr  INNER JOIN teacher t ON tr.tech_id=t.tech_id AND t.org_id = tr.org_id ")
				.append(" INNER JOIN grade g ON tr.grade_id=g.grade_id AND g.org_id = t.org_id AND tr.org_id = g.org_id INNER JOIN classes c ON tr.clas_id=c.clas_id AND c.org_id = g.org_id AND c.org_id = t.org_id AND tr.org_id = c.org_id INNER JOIN course c1 ON tr.cour_id=c1.cour_id AND c1.org_id = c.org_id and  c1.org_id = g.org_id AND c1.org_id = t.org_id AND tr.org_id = c1.org_id ")
				.append("  WHERE  tr.rl_id=3 AND tr.is_del=0 ");
		if (StringUtils.isNotEmpty(org_id)) {
			sql.append(" AND tr.org_id=:org_id ");
		}
		if (StringUtils.isNotEmpty(tech_id)) {
			sql.append(" AND tr.tech_id=:tech_id ");
		}
		if (StringUtils.isNotEmpty(grade_id)) {
			sql.append(" AND tr.grade_id=:grade_id ");
		}
		if (StringUtils.isNotEmpty(cour_id)) {
			sql.append(" AND tr.cour_id=:cour_id ");
		}
		if (StringUtils.isNotEmpty(clas_id)) {
			sql.append(" AND tr.clas_id=:clas_id ");
		}
		Query query = session.createSQLQuery(sql.toString());

		if (StringUtils.isNotEmpty(org_id)) {
			query.setString("org_id", org_id);
		}
		if (StringUtils.isNotEmpty(tech_id)) {
			query.setString("tech_id", tech_id);
		}
		if (StringUtils.isNotEmpty(grade_id)) {
			query.setString("grade_id", grade_id);
		}
		if (StringUtils.isNotEmpty(cour_id)) {
			query.setString("cour_id", cour_id);
		}
		if (StringUtils.isNotEmpty(clas_id)) {
			query.setString("clas_id", clas_id);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteRange(TechRange techRange) {
		if (null != techRange.getTr_id()) {
			techRange.setDel_time(new Date());
			techRange.setIs_del(Constant.IS_DEL_YES);
			update(techRange);
		} else {
			Integer rl_id = techRange.getRl_id();
			Session session = this.factory.getCurrentSession();
			Criteria c = session.createCriteria(TechRange.class);
			List<TechRange> ls = null;
			switch (rl_id) {
				case 3:// 任课教师
					c.add(Restrictions.eq("org_id", techRange.getOrg_id()));
					c.add(Restrictions.eq("tech_id", techRange.getTech_id()));
					c.add(Restrictions.eq("rl_id", EnumRoleLabel.任课教师.getCode()));
					c.add(Restrictions.eq("clas_id", techRange.getClas_id()));
					c.add(Restrictions.eq("grade_id", techRange.getGrade_id()));
					c.add(Restrictions.eq("cour_id", techRange.getCour_id()));
					c.add(Restrictions.eq("is_del", Constant.IS_DEL_NO));
					
				    ls = c.list();
					for (TechRange t : ls) {
						t.setDel_time(new Date());
						t.setIs_del(Constant.IS_DEL_YES);
						update(t);
					}
					break;
				case 4:// 班主任
					c.add(Restrictions.eq("org_id", techRange.getOrg_id()));
					c.add(Restrictions.eq("tech_id", techRange.getTech_id()));
					c.add(Restrictions.eq("rl_id", EnumRoleLabel.班主任.getCode()));
					c.add(Restrictions.eq("clas_id", techRange.getClas_id()));
					c.add(Restrictions.eq("is_del", Constant.IS_DEL_NO));
					
				    ls = c.list();
					for (TechRange t : ls) {
						t.setDel_time(new Date());
						t.setIs_del(Constant.IS_DEL_YES);
						update(t);
					}
					break;
				case 5:// 年级组长
					c.add(Restrictions.eq("org_id", techRange.getOrg_id()));
					c.add(Restrictions.eq("tech_id", techRange.getTech_id()));
					c.add(Restrictions.eq("rl_id", EnumRoleLabel.年级组长.getCode()));
					c.add(Restrictions.eq("grade_id", techRange.getGrade_id()));
					c.add(Restrictions.eq("is_del", Constant.IS_DEL_NO));
					
				    ls = c.list();
					for (TechRange t : ls) {
						t.setDel_time(new Date());
						t.setIs_del(Constant.IS_DEL_YES);
						update(t);
					}
					break;
				case 6:// 学生组管理员
					c.add(Restrictions.eq("org_id", techRange.getOrg_id()));
					c.add(Restrictions.eq("tech_id", techRange.getTech_id()));
					c.add(Restrictions.eq("rl_id", EnumRoleLabel.学生组管理员.getCode()));
					c.add(Restrictions.eq("group_id", techRange.getGroup_id()));
					c.add(Restrictions.eq("is_del", Constant.IS_DEL_NO));
					
				    ls = c.list();
					for (TechRange t : ls) {
						t.setDel_time(new Date());
						t.setIs_del(Constant.IS_DEL_YES);
						update(t);
					}
					break;
				case 7:// 部门管理员
					c.add(Restrictions.eq("org_id", techRange.getOrg_id()));
					c.add(Restrictions.eq("tech_id", techRange.getTech_id()));
					c.add(Restrictions.eq("rl_id", EnumRoleLabel.部门管理员.getCode()));
					c.add(Restrictions.eq("dep_id", techRange.getDep_id()));
					c.add(Restrictions.eq("is_del", Constant.IS_DEL_NO));
					ls = c.list();
					for (TechRange t : ls) {
						t.setDel_time(new Date());
						t.setIs_del(Constant.IS_DEL_YES);
						update(t);
					}
					break;
				case 8:// 教师组管理员
					c.add(Restrictions.eq("org_id", techRange.getOrg_id()));
					c.add(Restrictions.eq("tech_id", techRange.getTech_id()));
					c.add(Restrictions.eq("rl_id", EnumRoleLabel.教师组管理员.getCode()));
					c.add(Restrictions.eq("group_id", techRange.getGroup_id()));
					c.add(Restrictions.eq("is_del", Constant.IS_DEL_NO));
					
				    ls = c.list();
					for (TechRange t : ls) {
						t.setDel_time(new Date());
						t.setIs_del(Constant.IS_DEL_YES);
						update(t);
					}
					break;
				default:
					break;
			}
		}
	}

	@Override
	public void deleteOldTeachRange(String org_id, String dep_id, String grade_id, String clas_id, String cour_id,
			String group_id, String group_type) {
		Session session = this.factory.getCurrentSession();
		StringBuffer sql = new StringBuffer(
				"UPDATE tech_range t set t.del_time=SYSDATE(),t.is_del=1 where t.org_id=:org_id and t.is_del=0 ");
		if (StringUtils.isNotEmpty(dep_id)) {
			sql.append(" and t.rl_id = :rl_id ");
			sql.append(" and t.dep_id = :dep_id ");
		} else if (StringUtils.isNotEmpty(grade_id)) {
			sql.append(" and t.rl_id = :rl_id ");
			sql.append(" and t.grade_id = :grade_id ");
		} else if (StringUtils.isNotEmpty(clas_id) && StringUtils.isNotEmpty(cour_id)) {
			sql.append(" and t.rl_id = :rl_id ");
			sql.append(" and t.clas_id = :clas_id and t.cour_id = :cour_id ");
			if (StringUtils.isNotEmpty(grade_id)) {
				sql.append(" and t.grade_id = :grade_id ");
			}
		} else if (StringUtils.isNotEmpty(clas_id)) {
			sql.append(" and t.rl_id = :rl_id ");
			sql.append(" and t.clas_id = :clas_id ");
		} else if (StringUtils.isNotEmpty(group_id)) {
			if (StringUtils.isNotEmpty(group_type)) {
				sql.append(" and t.rl_id = :rl_id ");
			}
			sql.append(" and t.group_id = :group_id ");
		} else {
			return;
		}
		Query query = session.createSQLQuery(sql.toString());
		query.setInteger("org_id", Integer.parseInt(org_id));
		if (StringUtils.isNotEmpty(dep_id)) {
			query.setInteger("dep_id", Integer.parseInt(dep_id));
			query.setInteger("rl_id", EnumRoleLabel.部门管理员.getCode());
		} else if (StringUtils.isNotEmpty(grade_id)) {
			query.setInteger("grade_id", Integer.parseInt(grade_id));
			query.setInteger("rl_id", EnumRoleLabel.年级组长.getCode());
		} else if (StringUtils.isNotEmpty(clas_id) && StringUtils.isNotEmpty(cour_id)) {
			query.setInteger("clas_id", Integer.parseInt(clas_id));
			query.setInteger("cour_id", Integer.parseInt(cour_id));
			query.setInteger("rl_id", EnumRoleLabel.任课教师.getCode());
			if (StringUtils.isNotEmpty(grade_id)) {
				query.setInteger("grade_id", Integer.parseInt(grade_id));
			}
		} else if (StringUtils.isNotEmpty(clas_id)) {
			query.setInteger("clas_id", Integer.parseInt(clas_id));
			query.setInteger("rl_id", EnumRoleLabel.班主任.getCode());
		} else if (StringUtils.isNotEmpty(group_id)) {
			query.setInteger("group_id", Integer.parseInt(group_id));
			if (StringUtils.isNotEmpty(group_type)) {
				query.setInteger("rl_id", Integer.parseInt(group_type));
			}
		} else {
			return;
		}
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public TechRange[] getOldTechRange(String org_id, String rl_id, String clas_id, String grade_id, String dep_id,
			String group_id, String cour_id) {
		Session session = this.factory.getCurrentSession();
		StringBuffer sql = new StringBuffer(
				" SELECT tr.clas_id,tr.cour_id,tr.del_time,tr.dep_id,tr.grade_id,tr.group_id,tr.history,tr.insert_time,tr.is_del,tr.org_id,tr.rl_id,tr.tech_id,tr.tr_id,tr.update_time FROM tech_range tr WHERE tr.is_del = 0 AND tr.org_id =:org_id AND tr.rl_id =:rl_id ");
		if (EnumRoleLabel.任课教师.getCode().toString().equals(rl_id) && StringUtils.isNotEmpty(clas_id)
				&& StringUtils.isNotEmpty(cour_id)) {
			sql.append(" and tr.clas_id = :clas_id ");
			sql.append(" and tr.cour_id = :cour_id ");
		} else if (EnumRoleLabel.学生组管理员.getCode().toString().equals(rl_id) && StringUtils.isNotEmpty(group_id)) {
			sql.append(" and tr.group_id = :group_id ");
		} else if (EnumRoleLabel.年级组长.getCode().toString().equals(rl_id) && StringUtils.isNotEmpty(grade_id)) {
			sql.append(" and tr.grade_id = :grade_id ");
		} else if (EnumRoleLabel.教师组管理员.getCode().toString().equals(rl_id) && StringUtils.isNotEmpty(group_id)) {
			sql.append(" and tr.group_id = :group_id ");
		} else if (EnumRoleLabel.班主任.getCode().toString().equals(rl_id) && StringUtils.isNotEmpty(clas_id)) {
			sql.append(" and tr.clas_id = :clas_id ");
		} else if (EnumRoleLabel.部门管理员.getCode().toString().equals(rl_id) && StringUtils.isNotEmpty(dep_id)) {
			sql.append(" and tr.dep_id = :dep_id ");
		}
		Query query = session.createSQLQuery(sql.toString());
		query.setInteger("org_id", Integer.parseInt(org_id));
		if (EnumRoleLabel.任课教师.getCode().toString().equals(rl_id) && StringUtils.isNotEmpty(clas_id)
				&& StringUtils.isNotEmpty(cour_id)) {
			query.setInteger("rl_id", EnumRoleLabel.任课教师.getCode());
			query.setInteger("clas_id", Integer.parseInt(clas_id));
			query.setInteger("cour_id", Integer.parseInt(cour_id));
		} else if (EnumRoleLabel.学生组管理员.getCode().toString().equals(rl_id) && StringUtils.isNotEmpty(group_id)) {
			query.setInteger("rl_id", EnumRoleLabel.学生组管理员.getCode());
			query.setInteger("group_id", Integer.parseInt(group_id));
		} else if (EnumRoleLabel.年级组长.getCode().toString().equals(rl_id) && StringUtils.isNotEmpty(grade_id)) {
			query.setInteger("rl_id", EnumRoleLabel.年级组长.getCode());
			query.setInteger("grade_id", Integer.parseInt(grade_id));
		} else if (EnumRoleLabel.教师组管理员.getCode().toString().equals(rl_id) && StringUtils.isNotEmpty(group_id)) {
			query.setInteger("rl_id", EnumRoleLabel.教师组管理员.getCode());
			query.setInteger("group_id", Integer.parseInt(group_id));
		} else if (EnumRoleLabel.班主任.getCode().toString().equals(rl_id) && StringUtils.isNotEmpty(clas_id)) {
			query.setInteger("rl_id", EnumRoleLabel.班主任.getCode());
			query.setInteger("clas_id", Integer.parseInt(clas_id));
		} else if (EnumRoleLabel.部门管理员.getCode().toString().equals(rl_id) && StringUtils.isNotEmpty(dep_id)) {
			query.setInteger("rl_id", EnumRoleLabel.部门管理员.getCode());
			query.setInteger("dep_id", Integer.parseInt(dep_id));
		}
		List<TechRange> list = new ArrayList<TechRange>();
		query.setResultTransformer(Transformers.aliasToBean(TechRange.class));
		list = query.list();
		TechRange[] techRange = new TechRange[list.size()];
		if (null != list && list.size() != 0) {
			for (int i = 0; i < techRange.length; i++) {
				techRange[i] = list.get(i);
			}
			return techRange;
		} else {
			return techRange;
		}
	}

	@Override
	public List<TechRange> getTechRangeListByClassId(int classId) {
		String sql = "SELECT tr.* FROM tech_range tr WHERE tr.clas_id=? AND tr.is_del=0;";
		
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, classId);
		query.setResultTransformer(Transformers.aliasToBean(TechRange.class));
		
		List<TechRange> resultList = query.list();
		return resultList;
	}

	@Override
	public int checkIsExist(String org_id, String tech_id, int rl_id, String grade_id, String clas_id, int cour_id) {
		String sql = "SELECT COUNT(tr.tr_id) AS countNum FROM tech_range tr WHERE tr.org_id=? AND tr.tech_id=? AND tr.rl_id=? AND tr.grade_id=? AND tr.clas_id=? AND tr.cour_id=? AND tr.is_del=0;";
		
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql).addScalar("countNum", StandardBasicTypes.INTEGER);;
		query.setInteger(0, Integer.valueOf(org_id));
		query.setInteger(1, Integer.valueOf(tech_id));
		query.setInteger(2, rl_id);
		query.setInteger(3, Integer.valueOf(grade_id));
		query.setInteger(4, Integer.valueOf(clas_id));
		query.setInteger(5, cour_id);
		
		Integer countNum = (Integer) query.uniqueResult();
		return countNum.intValue();
	}

	@Override
	public List<TechRange> getTechRangeListIncludeDeleted(int org_id) {
		String sql = "SELECT tr.* FROM tech_range tr WHERE tr.org_id=?;";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(TechRange.class));
		query.setInteger(0, org_id);
		return query.list();
	}
	
	@Override
	public List<TechRange> getTechRangeList(int orgId, int classId) {
		String sql = "SELECT tr.* FROM tech_range tr WHERE tr.org_id=? AND tr.clas_id=? AND tr.is_del=0;";
		
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, orgId);
		query.setInteger(1, classId);
		query.setResultTransformer(Transformers.aliasToBean(TechRange.class));
		
		List<TechRange> resultList = query.list();
		return resultList;
	}

	@Override
	public TechRange[] getOldTechRangeOfClas(String org_id,String clas_id) {
		Session session = this.factory.getCurrentSession();
		StringBuffer sql = new StringBuffer(
				" SELECT tr.clas_id,tr.cour_id,tr.del_time,tr.dep_id,tr.grade_id,tr.group_id,tr.history,tr.insert_time,tr.is_del,tr.org_id,tr.rl_id,tr.tech_id,tr.tr_id,tr.update_time FROM tech_range tr WHERE tr.is_del = 0  AND tr.tech_id IN ( SELECT trr.tech_id FROM tech_range trr WHERE trr.org_id = :org_id AND trr.rl_id = 4 AND trr.clas_id = :clas_id ) AND tr.clas_id=:clas_id");
	
		Query query = session.createSQLQuery(sql.toString());
		query.setInteger("org_id", Integer.parseInt(org_id));
		query.setInteger("clas_id", Integer.parseInt(clas_id));
		//query.setString("techIds", techIds);
		
		List<TechRange> list = new ArrayList<TechRange>();
		query.setResultTransformer(Transformers.aliasToBean(TechRange.class));
		list = query.list();
		TechRange[] techRange = new TechRange[list.size()];
		if (null != list && list.size() != 0) {
			for (int i = 0; i < techRange.length; i++) {
				techRange[i] = list.get(i);
			}
			return techRange;
		} else {
			return techRange;
		}
	}

	@Override
	public List<TechRange> getTechRangeList(int org_id, int gradeId, int classId) {
		String sql = "SELECT tr.* FROM tech_range tr WHERE tr.org_id=? AND tr.grade_id=? AND tr.clas_id=? AND tr.is_del=0;";

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(TechRange.class));
		query.setInteger(0, org_id);
		query.setInteger(1, gradeId);
		query.setInteger(2, classId);

		return query.list();
	}

	@Override
	public List<TechRange> getTechRangeList(int org_id) {
		String sql = "SELECT tr.* FROM tech_range tr WHERE tr.org_id=? AND tr.is_del=0;";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(TechRange.class));
		query.setInteger(0, org_id);
		return query.list();
	}
	
	@Override
	public List<TechRange> getTechSkRangeListById(int tech_id) {
		String sql = "SELECT * FROM tech_range tr WHERE tr.tech_id=? AND tr.rl_id IN (3,4,5) AND tr.is_del=0;";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(TechRange.class));
		query.setInteger(0, tech_id);
		return query.list();
	}

	@Override
	public List<TechRange> getTechRangesByOrg(int org_id) {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT tr.* FROM tech_range tr WHERE tr.org_id = :org_id AND tr.is_del = 0;";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(TechRange.class));		
		query.setInteger("org_id",org_id);
		@SuppressWarnings("unchecked")
		List<TechRange> list = query.list();
		for (TechRange teacher : list) {
			LSHelper.processInjection(teacher);
		}
		return list;
	}

}
