package cn.edugate.esb.dao.imp;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.ITeacherDao;
import cn.edugate.esb.eduEnum.EnumRoleLabel;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.redis.dao.RedisTechRangeDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.LSHelper;
import cn.edugate.esb.util.Paging;

@Repository
public class TeacherDaoImpl  extends BaseDAOImpl<Teacher> implements ITeacherDao{

	@Autowired
    private RedisTechRangeDao redisTechRangeDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> getTeacherSearch(Integer org_id, String name) {

		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT  t.*,ou.user_mobile,d.dep_name FROM teacher t INNER JOIN org_user ou ON t.user_id=ou.user_id INNER JOIN department d ON t.dep_id=d.dep_id WHERE ou.identity!=99 and  1=1 AND t.org_id=:org_id AND t.is_del=0 AND ou.is_del=0  ";
		if(!"".equals(name)){
			sql+= "AND t.tech_name LIKE :name ORDER BY  t.tech_name ";
		}else{
			sql+= "ORDER BY  t.tech_name ";
		}
		Query query = session.createSQLQuery(sql);
		
		query.setInteger("org_id", org_id);

		if(!"".equals(name)){
			query.setString("name", "%"+name+"%");
		}		
		query.setResultTransformer(Transformers.aliasToBean(Teacher.class));
		List<Teacher> list = query.list();
		for (Teacher teacher : list) {
			LSHelper.processInjection(teacher);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> getAll() {

		Session session=this.factory.getCurrentSession();

		StringBuffer sql = new StringBuffer();
			sql.append("SELECT t.*,ou.user_mobile,o.org_name_s_cn AS org_name, ou.user_mail, ou.openid FROM teacher t INNER JOIN org_user ou ON t.user_id=ou.user_id LEFT JOIN organization o ON o.org_id = t.org_id AND ou.org_id = o.org_id AND o.is_del = 0   WHERE t.is_del=0 AND ou.is_del=0 ORDER BY t.tech_name");
		
		Query query = session.createSQLQuery(sql.toString());
		query.setResultTransformer(Transformers.aliasToBean(Teacher.class));		
		
		List<Teacher> list = query.list();
		for (Teacher teacher : list) {
			LSHelper.processInjection(teacher);
		}
		return list;
	}

	@Override
	public Teacher getByUserId(Integer user_id) {
		// TODO Auto-generated method stub
		Criterion criterion = Restrictions.and(Restrictions.eq("is_del", 0)).add(Restrictions.eq("user_id", user_id));
		List<Teacher> teachers = this.list(Teacher.class, criterion, Order.desc("tech_id"));		
		return teachers.size()>0?teachers.get(0):null;
	}

	@Override
	public int getTotalCount(int org_id, int type, String seach_name) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT COUNT(t.tech_id) FROM teacher t INNER JOIN org_user u ON t.user_id=u.user_id WHERE t.org_id=:org_id AND t.is_del=0 AND u.is_del=0 and u.identity!=99  ";
		
		if(Constant.TEACHER==type){
			sql += " AND t.tech_name like :name ";
		}
		if(Constant.MOBILE==type){
			sql += " AND u.user_mobile like :mobile ";
		}
		Query query = session.createSQLQuery(sql.toString());
		query.setInteger("org_id", org_id);
		if(Constant.TEACHER==type){
			query.setString("name", "%"+seach_name+"%");
		}
		if(Constant.MOBILE==type){
			query.setString("mobile", "%"+seach_name+"%");
		}
		BigInteger count = (BigInteger) query.uniqueResult();
		
		return count.intValue();
	}

	@Override
	public void getPaging(int org_id, int type, String seach_name,
			Paging<Teacher> paging) {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT tu.*,d.dep_name FROM (SELECT t.tech_name,  t.tech_nick,  t.tech_id, t.tech_note, u.user_mobile, t.dep_id FROM teacher t  INNER JOIN org_user u   ON t.user_id = u.user_id WHERE u.identity!=99 and  t.org_id = :org_id AND t.is_del = 0 AND u.is_del = 0 ";
		
		if(Constant.TEACHER==type){
			sql += " AND t.tech_name like '%"+seach_name+"%' ";
		}
		if(Constant.MOBILE==type){
			sql += " AND u.user_mobile like :mobile ";
		}	 
		sql+=" ) tu LEFT JOIN department d ON tu.dep_id = d.dep_id WHERE d.is_del=0 ORDER BY tu.tech_id DESC";
		
		Query query = session.createSQLQuery(sql.toString());
		query.setInteger("org_id", org_id);
//		if(Constant.TEACHER==type){
//			query.setString("name", "'%"+seach_name+"%'");
//		}
		if(Constant.MOBILE==type){
			query.setString("mobile", "%"+seach_name+"%");
		}
		query.setFirstResult((paging.getPage()-1)*paging.getLimit()+paging.getStart());
		query.setMaxResults(paging.getLimit());
		query.setResultTransformer(Transformers.aliasToBean(Teacher.class));
		@SuppressWarnings("unchecked")
		List<Teacher> ls = query.list();
		for (Teacher teacher : ls) {
			LSHelper.processInjection(teacher);
			List<Map<String,Object>> list = redisTechRangeDao.getIdentityById(teacher.getTech_id().toString(), EnumRoleLabel.部门管理员.getCode(), null);
			String managing_dep_name="";
			if(list!=null){
				for (Map<String,Object> map : list) {
					if(map.get("dep_name")!=null)
						managing_dep_name = managing_dep_name + map.get("dep_name")+",";
				}
			}
			if(StringUtils.isNotEmpty(managing_dep_name))
				managing_dep_name = managing_dep_name.substring(1, managing_dep_name.length()-1);
			teacher.setManaging_dep_name(managing_dep_name);
			
		}
		paging.setData(ls);
		
	}

	@Override
	public Teacher getTechAndUserId(int tech_id) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();

		StringBuffer sql = new StringBuffer();
			sql.append("SELECT  t.*,ou.user_type,ou.user_idnumber,ou.user_mobile,ou.user_mobile_type  FROM   teacher t INNER JOIN org_user ou ON t.user_id=ou.user_id where t.tech_id=:tech_id  ORDER BY t.tech_name");		
		Query query = session.createSQLQuery(sql.toString());	
		query.setInteger("tech_id", tech_id);
		query.setResultTransformer(Transformers.aliasToBean(Teacher.class));
		@SuppressWarnings("unchecked")
		List<Teacher> list = query.list();
		for (Teacher teacher : list) {
			LSHelper.processInjection(teacher);
		}
		return list.size()>0?list.get(0):null;
	}

	@Override
	public Long getTotalCount(String org_id, String role_id, String tech_name,
			String user_mobile) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT COUNT(*) FROM (SELECT "
				+ "t.tech_id "
				+ "FROM"
				+ "	teacher t "
				+ "LEFT JOIN org_user ou ON t.user_id = ou.user_id "
				+ "LEFT JOIN department d ON t.dep_id = d.dep_id "
				+ "AND d.is_del = 0 "
				+ "LEFT JOIN tech2role tr ON t.user_id = tr.tech_id "
				+ "AND tr.is_del = 0 "
				+ "LEFT JOIN role r ON tr.role_id = r.role_id "
				+ "AND r.is_del = 0 "
				+ "WHERE "
				+ "t.is_del = 0 AND ou.is_del = 0 and ou.identity!=99 ";
		if(org_id!=null&&!"".equals(org_id)){
			sql += " AND t.org_id=:org_id ";
		}
		if(role_id!=null&&!"".equals(role_id)){
			sql += " AND r.role_id=:role_id ";
		}
		if(tech_name!=null&&!"".equals(tech_name)){
			sql += " AND t.tech_name LIKE :tech_name ";
		}
		if(user_mobile!=null&&!"".equals(user_mobile)){
			sql += " AND ou.user_mobile LIKE :user_mobile ";
		}	
		sql+="GROUP BY t.tech_id ORDER BY t.tech_id DESC) tt";
		
		Query query = session.createSQLQuery(sql.toString());
		if(org_id!=null&&!"".equals(org_id)){
			query.setInteger("org_id", Integer.parseInt(org_id));
		}
		if(role_id!=null&&!"".equals(role_id)){
			query.setInteger("role_id", Integer.parseInt(role_id));
		}
		if(tech_name!=null&&!"".equals(tech_name)){
			query.setString("tech_name", "%"+tech_name+"%");
		}
		if(user_mobile!=null&&!"".equals(user_mobile)){
			query.setString("user_mobile", "%"+user_mobile+"%");
		}
		
		BigInteger count = (BigInteger) query.uniqueResult();
		
		return count.longValue();
	}

	@Override
	public void getPaging(String org_id, String role_id, String tech_name,
			String user_mobile, Paging<Teacher> pages) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT "
				+ "t.*, d.dep_name,ou.user_mobile,GROUP_CONCAT(r.role_name) role_name "
				+ "FROM"
				+ "	teacher t "
				+ "LEFT JOIN org_user ou ON t.user_id = ou.user_id "
				+ "LEFT JOIN department d ON t.dep_id = d.dep_id "
				+ "AND d.is_del = 0 "
				+ "LEFT JOIN tech2role tr ON t.user_id = tr.tech_id "
				+ "AND tr.is_del = 0 "
				+ "LEFT JOIN role r ON tr.role_id = r.role_id "
				+ "AND r.is_del = 0 "
				+ "WHERE "
				+ "t.is_del = 0 AND ou.is_del = 0 and ou.identity!=99  ";
		if(org_id!=null&&!"".equals(org_id)){
			sql += " AND t.org_id=:org_id ";
		}
		if(role_id!=null&&!"".equals(role_id)){
			sql += " AND r.role_id=:role_id ";
		}
		if(tech_name!=null&&!"".equals(tech_name)){
			sql += " AND t.tech_name LIKE :tech_name ";
		}
		if(user_mobile!=null&&!"".equals(user_mobile)){
			sql += " AND ou.user_mobile LIKE :user_mobile ";
		}	
		sql+="GROUP BY t.tech_id ORDER BY t.tech_id DESC";
		
		Query query = session.createSQLQuery(sql.toString());
		if(org_id!=null&&!"".equals(org_id)){
			query.setInteger("org_id", Integer.parseInt(org_id));
		}
		if(role_id!=null&&!"".equals(role_id)){
			query.setInteger("role_id", Integer.parseInt(role_id));
		}
		if(tech_name!=null&&!"".equals(tech_name)){
			query.setString("tech_name", "%"+tech_name+"%");
		}
		if(user_mobile!=null&&!"".equals(user_mobile)){
			query.setString("user_mobile", "%"+user_mobile+"%");
		}
		query.setFirstResult((pages.getPage()-1)*pages.getLimit()+pages.getStart());
		query.setMaxResults(pages.getLimit());
		query.setResultTransformer(Transformers.aliasToBean(Teacher.class));
		@SuppressWarnings("unchecked")
		List<Teacher> ls = query.list();
		for (Teacher tc : ls) {
			LSHelper.processInjection(tc);
		}
		pages.setData(ls);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getOrgTreeTeacherMembers(String org_id, String group_id) {
		Session session=this.factory.getCurrentSession();
		StringBuffer sql = new StringBuffer(" SELECT t.tech_id,t.tech_name,o.org_id,o.org_name_cn,d.dep_id,d.dep_name,gm.group_id FROM teacher t")
							.append(" INNER JOIN org_tree ot ON ot.lower_id=t.org_id AND ot.is_del=0 AND ot.org_id=:org_id")
							.append(" INNER JOIN department d ON t.dep_id=d.dep_id AND d.org_id = t.org_id AND d.org_id=ot.lower_id AND d.is_del=0 ")
							.append(" INNER JOIN organization o ON ot.lower_id = o.org_id AND d.org_id=o.org_id AND o.is_del = 0 ")
							.append(" LEFT JOIN group_member gm ON gm.mem_id=t.tech_id AND gm.type=:type AND gm.is_del=0 and gm.group_id=:group_id ")
							//.append(" LEFT JOIN `group` g ON g.org_id=t.org_id AND g.org_id=ot.lower_id AND g.group_id=gm.group_id AND g.org_id=d.org_id AND g.is_del=0 AND g.group_id=:group_id ") 
							.append(" WHERE t.is_del=0 ");
		Query query = session.createSQLQuery(sql.toString());
		query.setInteger("org_id", Integer.parseInt(org_id));
		query.setInteger("group_id", Integer.parseInt(group_id));
		//query.setInteger("org_id", 9);
		//query.setInteger("group_id", 161);
		query.setInteger("type", Constant.GROUPMEMBER_TYPE_TEACHER);
		return query.list();
	}

	@Override
	public String saveTeacher() {
		@SuppressWarnings("unused")
		Session session=this.factory.getCurrentSession();
		
		
		return null;
	}

	@Override
	public boolean getUserIsExist(String loginName, String phone, int identity,
			int org_id) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT count(ou.user_id) FROM org_user ou WHERE (ou.user_loginname =:loginName OR ou.user_mobile =:phone)  AND ou.org_id=:org_id AND ou.identity =:identity AND ou.is_del = 0";
		Query query = session.createSQLQuery(sql.toString());
		query.setString("loginName",loginName);
		query.setString("phone",phone);
		query.setInteger("org_id",org_id);
		query.setInteger("identity", identity);
		
		BigInteger count = (BigInteger) query.uniqueResult();
		
		return count.intValue()>0?false:true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getTeacherMembers(String orgId, String groupId) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT"
				+ "	t.tech_id,"
				+ "	t.tech_name,"
				+ "	o.org_id,"
				+ "	o.org_name_cn,"
				+ "	d.dep_id,"
				+ "	d.dep_name,"
				+ "	gm.group_id "
				+ "FROM"
				+ "	group_member gm "
				+ "LEFT JOIN teacher t ON gm.mem_id = t.tech_id "
				+ "AND gm.is_del = 0 "
				+ "AND gm.group_id = :groupId "
				+ "AND gm.type=:type "
				+ "INNER JOIN org_tree ot ON ot.lower_id = t.org_id "
				+ "AND ot.is_del = 0 "
				+ "AND ot.org_id = :orgId "
				+ "INNER JOIN department d ON t.dep_id = d.dep_id "
				+ "AND d.org_id = t.org_id "
				+ "AND d.org_id = ot.lower_id "
				+ "AND d.is_del = 0 "
				+ "INNER JOIN organization o ON ot.lower_id = o.org_id "
				+ "AND d.org_id = o.org_id "
				+ "AND o.is_del = 0";
		Query query = session.createSQLQuery(sql.toString());
		query.setInteger("orgId", Integer.parseInt(orgId));
		query.setInteger("groupId", Integer.parseInt(groupId));
		//query.setInteger("org_id", 9);
		//query.setInteger("group_id", 161);
		query.setInteger("type", Constant.GROUPMEMBER_TYPE_TEACHER);
		return query.list();
	}

	@Override
	public List<Map<String,Object>> getTechsOfRlId(String org_id,Integer code) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();

		StringBuffer sql = new StringBuffer();
			sql.append("SELECT t.user_id,t.user_mobile,t1.tech_name,t1.tech_id FROM tech2role u "
					+ "LEFT JOIN org_user t ON u.tech_id = t.user_id "
					+ "LEFT JOIN role r ON u.role_id=r.role_id   "
					+ "LEFT JOIN teacher t1 ON t.user_id=t1.user_id "
					+ "WHERE 1 = 1 AND t.org_id=:org_id AND r.rl_id=:code AND u.is_del = 0 AND t.is_del=0 AND r.is_del=0 AND t1.is_del=0");
		
		Query query = session.createSQLQuery(sql.toString());
		query.setString("org_id",org_id);
		query.setInteger("code",code);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);		
		
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list = query.list();
		
		return list;
	}

	@Override
	public List<Teacher> getTeacherListIncludeDeleted(int org_id) {
		String sql = "SELECT t.* FROM teacher t WHERE t.org_id=? AND t.tech_name NOT LIKE '%edugate%';";
		Session session=this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Teacher.class));
		query.setInteger(0, org_id);
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> getPhoneBook(Integer org_id) {
		Session session=this.factory.getCurrentSession();
		String sql = " SELECT t.tech_name,u.user_mobile,d.dep_name,IF(u.openid IS NULL OR TRIM(u.openid) = '', 0, 1) AS is_selected FROM teacher t "+
					 " INNER JOIN org_user u ON t.org_id = u.org_id AND u.is_del=0 AND t.user_id = u.user_id AND u.identity=1 "+
					 " INNER JOIN department d ON d.org_id=t.org_id AND d.org_id=u.org_id AND d.is_del=0 AND d.dep_id=t.dep_id WHERE t.org_id=:org_id ";
		Query query = session.createSQLQuery(sql.toString()).addScalar("tech_name", StandardBasicTypes.STRING).addScalar("user_mobile", StandardBasicTypes.STRING).addScalar("dep_name", StandardBasicTypes.STRING).addScalar("is_selected", StandardBasicTypes.INTEGER);
		query.setInteger("org_id",org_id);
		query.setResultTransformer(Transformers.aliasToBean(Teacher.class));	
		return query.list();
	}

	@Override
	public List<Teacher> getTeachersByOrgId(Integer org_id) {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT t.*,ou.user_mobile,o.org_name_s_cn AS org_name, ou.user_mail, ou.openid FROM teacher t INNER JOIN org_user ou ON t.user_id=ou.user_id INNER JOIN organization o ON o.org_id = t.org_id AND ou.org_id = o.org_id AND o.is_del = 0   WHERE t.is_del=0 AND ou.is_del=0 AND o.org_id=:org_id  ";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Teacher.class));		
		query.setInteger("org_id",org_id);
		@SuppressWarnings("unchecked")
		List<Teacher> list = query.list();
		for (Teacher teacher : list) {
			LSHelper.processInjection(teacher);
		}
		return list;
	}

	@Override
	public List<TechRange> getTechRanges(Integer org_id) {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT tr.tr_id,tr.org_id,tr.rl_id,t.tech_id,t.tech_name,t.user_mobile,tr.grade_id,g.grade_name,tr.clas_id,c.clas_name,tr.cour_id,c1.cour_name FROM tech_range tr INNER JOIN (SELECT ou.user_id,ou.user_mobile,tt.tech_id,tt.tech_name FROM org_user ou INNER JOIN teacher tt ON ou.user_id=tt.user_id WHERE ou.is_del=0 AND tt.is_del=0) t ON tr.tech_id=t.tech_id LEFT JOIN grade g ON tr.grade_id=g.grade_id LEFT JOIN classes c ON tr.clas_id=c.clas_id LEFT JOIN course c1 ON tr.cour_id=c1.cour_id WHERE tr.rl_id IN (3,4,5) and tr.org_id=:org_id AND tr.is_del=0;";
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

	@Override
	public List<Map<String, String>> getTechsOforg(int org_id) {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT ou.user_id,ou.org_id,ou.user_mobile,t.tech_id,t.tech_name,GROUP_CONCAT(r.role_name) as role_name FROM org_user ou INNER JOIN teacher t ON ou.user_id=t.user_id LEFT JOIN tech2role t1 ON ou.user_id=t1.tech_id AND t1.is_del=0 LEFT JOIN role r ON t1.role_id=r.role_id AND r.is_del=0 WHERE  ou.org_id=:org_id AND ou.identity!=99 and ou.is_del=0 AND t.is_del=0  GROUP BY ou.user_id";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);		
		query.setInteger("org_id",org_id);
		@SuppressWarnings("unchecked")
		List<Map<String, String>> list = query.list();

		return list;
	}
}
