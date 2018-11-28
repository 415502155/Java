package cn.edugate.esb.dao.imp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IGroupDao;
import cn.edugate.esb.eduEnum.EnumRoleLabel;
import cn.edugate.esb.entity.Function;
import cn.edugate.esb.entity.Group;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Paging;
import cn.edugate.esb.util.Utils;

/**
 * 组DAO实现类
 * Title:IGroupImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午9:15:18
 */
@Repository
public class IGroupImpl extends BaseDAOImpl<Group> implements IGroupDao {

	@Override
	public List<Group> getAllList(Group group) {
		Session session=this.factory.getCurrentSession();
		Criteria c=session.createCriteria(Group.class);
		if(StringUtils.isNotEmpty(group.getGroup_name())){
			c.add(Restrictions.like("group_name", group.getGroup_name()));
		}
		if(null!=group.getGroup_type()){
			c.add(Restrictions.eq("group_type", group.getGroup_type()));
		}
		if(null!=group.getOrg_id()){
			c.add(Restrictions.eq("org_id", group.getOrg_id()));
		}
		if(null==group.getIs_del()){
			c.add(Restrictions.eq("is_del", Constant.IS_DEL_NO));
		}else{
			c.add(Restrictions.eq("is_del", group.getIs_del()));
		}
		@SuppressWarnings("unchecked")
		List<Group> ls = c.list();
		return ls;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Group> getGroupListByUserID(Integer currentUserId) {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT g.group_id,g.group_name,g.org_id,g.group_type,g.note,g.tech_id,g.insert_time,g.update_time,g.del_time,g.is_del FROM `group` g INNER JOIN group_member gm ON g.group_id=gm.group_id and gm.is_del=0 AND gm.mem_id=:userID where g.is_del=0";
		Query query = session.createSQLQuery(sql);
		query.setInteger("userID", currentUserId);
		query.setResultTransformer(Transformers.aliasToBean(Function.class));
		List<Group> list = new ArrayList<Group>(); 
		list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paging<Group> getAllList(Group group, Paging<Group> paging) {
		Session session=this.factory.getCurrentSession();
		int limitFrom = (paging.getPage()-1)*paging.getLimit();
		String limit =  " limit " + limitFrom +"," + paging.getLimit();
		String order = " order by g.insert_time desc";
		StringBuffer where = new StringBuffer(" where g.is_del=0 ");
		if(StringUtils.isNotEmpty(group.getGroup_name())){
			where.append(" and g.group_name like :group_name ");
		}
		if(null!=group.getGroup_type()){
			where.append(" and g.group_type = :group_type ");
		}
		if(null!=group.getOrg_id()){
			where.append(" and g.org_id = :org_id ");
		}
		StringBuffer countTotal = new StringBuffer("SELECT count(1) FROM `group` g");
		StringBuffer sql = new StringBuffer("select g.group_id,g.group_name,g.org_id,g.group_type,g.note,g.tech_id,g.insert_time,g.update_time,g.del_time,g.is_del from `group` g ");
		Query countQuery = session.createSQLQuery(countTotal.append(where).append(order).toString());
		Query sqlQuery = session.createSQLQuery(sql.append(where).append(order).append(limit).toString());
		if(StringUtils.isNotEmpty(group.getGroup_name())){
			countQuery.setString("group_name", "%"+group.getGroup_name()+"%");
			sqlQuery.setString("group_name", "%"+group.getGroup_name()+"%");
		}
		if(null!=group.getGroup_type()){
			countQuery.setInteger("group_type", group.getGroup_type());
			sqlQuery.setInteger("group_type", group.getGroup_type());
		}
		if(null!=group.getOrg_id()){
			countQuery.setInteger("org_id", group.getOrg_id());
			sqlQuery.setInteger("org_id", group.getOrg_id());
		}
		BigInteger pageTotal = (BigInteger) countQuery.uniqueResult();
		paging.setTotal(pageTotal.intValue());
		sqlQuery.setResultTransformer(Transformers.aliasToBean(Group.class));
		List<Group> list = new ArrayList<Group>();
		list = sqlQuery.list();
		paging.setData(list);
		return paging;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paging<Group> getGroupListByUserID(Integer currentUserId, Paging<Group> paging) {
		Session session=this.factory.getCurrentSession();
		int limitFrom = (paging.getPage()-1)*paging.getLimit();
		StringBuffer countTotalSQL = new StringBuffer("SELECT count(1) FROM `group` g INNER JOIN group_member gm ON g.group_id=gm.group_id and gm.is_del=0 AND gm.mem_id=:userID where g.is_del=0 ");
		StringBuffer sql = new StringBuffer("SELECT g.group_id,g.group_name,g.org_id,g.group_type,g.note,g.tech_id,g.insert_time,g.update_time,g.del_time,g.is_del FROM `group` g INNER JOIN group_member gm ON g.group_id=gm.group_id and gm.is_del=0 AND gm.mem_id=:userID where g.is_del=0 ");
		sql.append(" order by g.insert_time desc limit ")
			.append(limitFrom)
			.append(",")
			.append(paging.getLimit());
		Query query = session.createSQLQuery(countTotalSQL.toString());
		query.setInteger("userID", currentUserId);
		int pageTotal = (int) query.uniqueResult();
		paging.setTotal(pageTotal);
		query = session.createSQLQuery(sql.toString());
		query.setInteger("userID", currentUserId);
		query.setResultTransformer(Transformers.aliasToBean(Group.class));
		List<Group> list = new ArrayList<Group>();
		list = query.list();
		paging.setData(list);
		return paging;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getGroupList(Integer orgId, Integer groupType) {
		Session session=this.factory.getCurrentSession();
		StringBuffer sql = new StringBuffer(" SELECT GROUP_CONCAT(t.tech_name) as manager,g.group_id,g.group_name,g.note,date(g.insert_time) as createdate, ")
				.append(" (SELECT count(1) FROM group_member gm WHERE gm.group_id=g.group_id AND gm.is_del=0) as mem_num FROM `group` g ")
				.append(" LEFT JOIN tech_range tr ON tr.rl_id=:rl_id AND tr.org_id=g.org_id AND tr.group_id=g.group_id AND tr.is_del=0 ")
				.append(" LEFT JOIN teacher t ON t.tech_id=tr.tech_id AND t.org_id=g.org_id AND t.org_id=tr.org_id AND t.is_del=0 ")
				.append(" WHERE g.is_del=0 AND g.org_id=:org_id AND g.group_type=:group_type ")
				.append(" GROUP BY g.group_id ");
		Query query = session.createSQLQuery(sql.toString());
		query.setInteger("org_id", orgId);
		query.setInteger("group_type", groupType);
		if(Constant.GROUP_TYPE_STUDENT==groupType){
			query.setInteger("rl_id", EnumRoleLabel.学生组管理员.getCode());
		}else{
			query.setInteger("rl_id", EnumRoleLabel.教师组管理员.getCode());
		}
		return query.list();
	}

	@Override
	public int checkName(int orgID, String groupName) {
		String sql = "SELECT COUNT(g.group_id) FROM edugate_base.`group` g WHERE g.org_id=? AND g.group_name=? AND g.is_del=0;";

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, orgID);
		query.setString(1, Utils.getValue(groupName));
		BigInteger count = (BigInteger) query.uniqueResult();
		return count.intValue();
	}

	@Override
	public Group getGroupEntity(int orgID, String name) {
		String sql = "SELECT g.* FROM edugate_base.`group` g WHERE g.org_id=? AND g.group_name=? AND g.is_del=0;";

		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, orgID);
		query.setString(1, Utils.getValue(name));
		query.setResultTransformer(Transformers.aliasToBean(Group.class));
		@SuppressWarnings("unchecked")
		List<Group> ls = query.list();
		if (ls != null && ls.size() > 0) {
			Group g = ls.get(0);
			return g;
		} else {
			return null;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getGroupsWithMembers(Integer orgId, Integer groupType, Integer memberId, Integer type) {
		Session session=this.factory.getCurrentSession();
		String sql = "";
		String where = " WHERE 1=1 AND g.is_del=0 ";
		if(orgId!=-1){
			where += " AND g.org_id=:org_id ";
		}
		if(groupType!=-1){
			where += " AND g.group_type=:group_type ";
		}
		if(Constant.GROUPMEMBER_TYPE_STUDENT.equals(type)){
			sql = "SELECT o.org_name_s_cn,g.group_id,g.group_name,g.group_type,g.note as group_note,g.hx_groupid,gm.group_member_id,gm.mem_id,s.stud_name as mem_name,u.user_mobile as mobile,'' as mem_nick,u.user_id,s.head,o.org_id,o.org_name_cn FROM `group` g "+ 
					" INNER JOIN group_member gm ON g.group_id=gm.group_id AND gm.type=:type AND gm.is_del=0 "+
					" INNER JOIN student s ON s.stud_id=gm.mem_id  AND s.is_del=0 "+
					" INNER JOIN org_user u ON s.user_id=u.user_id AND u.org_id=s.org_id AND u.is_del=0 "+ 
					" INNER JOIN organization o ON o.org_id=s.org_id AND o.org_id=u.org_id AND o.is_del=0 "+
					where ;
		}else if(Constant.GROUPMEMBER_TYPE_TEACHER.equals(type)){
			sql = "SELECT o.org_name_s_cn,g.group_id,g.group_name,g.group_type,g.note as group_note,g.hx_groupid,gm.group_member_id,gm.mem_id,t.tech_name as mem_name,u.user_mobile as mobile,t.tech_nick as mem_nick,u.user_id,t.tech_head,o.org_id,o.org_name_cn FROM `group` g "+
					" INNER JOIN group_member gm ON g.group_id=gm.group_id AND gm.type=:type AND gm.is_del=0 "+
					" INNER JOIN teacher t ON t.tech_id=gm.mem_id AND t.is_del=0 "+
					" INNER JOIN org_user u ON t.user_id=u.user_id AND u.org_id=t.org_id AND u.is_del=0 "+ 
					" INNER JOIN organization o ON t.org_id=o.org_id AND u.org_id=o.org_id AND o.is_del=0 "+
					where ;
		}else{
			sql = "(SELECT o.org_name_s_cn,g.group_id,g.group_name,g.group_type,g.note as group_note,g.hx_groupid,gm.group_member_id,gm.mem_id,t.tech_name as mem_name,u.user_mobile as mobile,t.tech_nick as mem_nick,u.user_id,t.tech_head,o.org_id,o.org_name_cn FROM `group` g "+
					" INNER JOIN group_member gm ON g.group_id=gm.group_id AND gm.type=2 AND gm.is_del=0 "+
					" INNER JOIN teacher t ON t.tech_id=gm.mem_id AND t.is_del=0 "+
					" INNER JOIN org_user u ON t.user_id=u.user_id AND u.org_id=t.org_id AND u.is_del=0 "+ 
					" INNER JOIN organization o ON t.org_id=o.org_id AND u.org_id=o.org_id AND o.is_del=0 "+
					where + " ) UNION ALL "+
					"(SELECT o.org_name_s_cn,g.group_id,g.group_name,g.group_type,g.note as group_note,g.hx_groupid,gm.group_member_id,gm.mem_id,s.stud_name as mem_name,u.user_mobile as mobile,'' as mem_nick,u.user_id,s.head,o.org_id,o.org_name_cn FROM `group` g "+ 
					" INNER JOIN group_member gm ON g.group_id=gm.group_id AND gm.type=1 AND gm.is_del=0 "+
					" INNER JOIN student s ON s.stud_id=gm.mem_id  AND s.is_del=0 "+
					" INNER JOIN org_user u ON s.user_id=u.user_id AND u.org_id=s.org_id AND u.is_del=0 "+ 
					" INNER JOIN organization o ON o.org_id=s.org_id AND o.org_id=u.org_id AND o.is_del=0 "+
					where + " ) ";
		}
		Query query = session.createSQLQuery(sql.toString());
		if(orgId!=-1){
			query.setInteger("org_id", orgId);
		}
		if(groupType!=-1){
			query.setInteger("group_type", groupType);
		}
		if(Constant.GROUPMEMBER_TYPE_STUDENT.equals(type)||Constant.GROUPMEMBER_TYPE_TEACHER.equals(type)){
			query.setInteger("type", type);
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getGroupsWithoutMembers(Integer orgId, Integer groupType) {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT o.org_name_s_cn,g.group_id,g.group_name,g.group_type,g.note,g.hx_groupid FROM `group` g INNER JOIN organization o ON g.org_id=o.org_id AND o.is_del=0 WHERE g.is_del=0 ";
		if(null!=orgId){
			sql += " AND g.org_id=:org_id ";
		}
		if(null!=orgId){
			sql += " AND g.group_type=:group_type ";
		}
		Query query = session.createSQLQuery(sql.toString());
		if(null!=orgId){
			query.setInteger("org_id", orgId);
		}
		if(null!=groupType){
			query.setInteger("group_type", groupType);
		}
		return query.list();
	}

	@Override
	public List<Group> getGroupListIncludeDeleted(int org_id) {
		String sql = "SELECT g.* FROM `group` g WHERE g.org_id=?";
		
		Session session=this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Group.class));
		query.setInteger(0, org_id);
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getGroupsWithMembers(String user_ids) {
//		String sql = " SELECT o.org_name_s_cn,g.group_id,g.group_name,g.group_type,g.note as group_note,g.hx_groupid,gm.group_member_id,gm.mem_id,t.tech_name as mem_name,u.user_mobile as mobile,t.tech_nick as mem_nick,u.user_id,t.tech_head,o.org_id,o.org_name_cn FROM `group` g "+
//					" INNER JOIN group_member gm ON g.group_id=gm.group_id AND gm.type=:type AND gm.is_del=0 "+
//					" INNER JOIN teacher t ON t.tech_id=gm.mem_id AND t.is_del=0 "+
//					" INNER JOIN org_user u ON t.user_id=u.user_id AND u.org_id=t.org_id AND u.is_del=0 "+ 
//					" INNER JOIN organization o ON t.org_id=o.org_id AND u.org_id=o.org_id AND o.is_del=0 "+
//					" WHERE g.is_del=0  AND u.user_id in  (:ids) " ;
		String sql = " SELECT u.user_id, GROUP_CONCAT(g.group_id) FROM `group` g "+
				" INNER JOIN group_member gm ON g.group_id=gm.group_id AND gm.type=:type AND gm.is_del=0 "+
				" INNER JOIN teacher t ON t.tech_id=gm.mem_id AND t.is_del=0 "+
				" INNER JOIN org_user u ON t.user_id=u.user_id AND u.org_id=t.org_id AND u.is_del=0 "+ 
				" INNER JOIN organization o ON t.org_id=o.org_id AND u.org_id=o.org_id AND o.is_del=0 "+
				" WHERE g.is_del=0  AND u.user_id in  (:ids) GROUP BY u.user_id" ;
		Session session=this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql.toString());
		query.setInteger("type", Constant.GROUPMEMBER_TYPE_TEACHER);
		query.setParameterList("ids", Arrays.asList(user_ids.split(",")));
		return query.list();
	}

	
	
	
	@Override
	public List<Map<String, String>> getTechGroupsOfOrg(int org_id) {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT t.tech_id,t.tech_name,t.user_mobile, GROUP_CONCAT(gmm.group_name) group_names "+
				" FROM (SELECT ou.user_id,ou.user_mobile, tt.tech_id,tt.tech_name "+
				" FROM org_user ou INNER JOIN teacher tt ON ou.user_id = tt.user_id "+
				"  WHERE ou.is_del = 0 AND tt.is_del = 0 AND ou.org_id = :org_id AND ou.identity = 1) t "+
				" LEFT JOIN (SELECT g.group_id, g.group_name,  gm.mem_id "+
				" FROM group_member gm INNER JOIN `group` g "+
				"  ON gm.group_id = g.group_id AND g.org_id = :org_id AND gm.type = 2 AND g.is_del = 0  AND gm.is_del = 0 ) gmm "+
				" ON t.tech_id = gmm.mem_id GROUP BY t.user_mobile";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);		
		query.setInteger("org_id",org_id);
		@SuppressWarnings("unchecked")
		List<Map<String, String>> list = query.list();

		return list;
	}
	
	
	
}
