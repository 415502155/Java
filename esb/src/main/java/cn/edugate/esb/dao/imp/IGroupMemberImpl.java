package cn.edugate.esb.dao.imp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.dao.IGroupMemberDao;
import cn.edugate.esb.entity.GroupMember;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.redis.dao.RedisGroupDao;
import cn.edugate.esb.redis.dao.RedisGroupMemberDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Paging;

/**
 * 组成员DAO实现类
 * Title:IGroupMemberImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午9:15:18
 */
@Repository
public class IGroupMemberImpl extends BaseDAOImpl<GroupMember> implements IGroupMemberDao {
	@Autowired
	private RedisGroupMemberDao redisGroupMemberDao;
	@Override
	public List<GroupMember> getAllList(GroupMember groupMember) {
		Session session=this.factory.getCurrentSession();
		Criteria c=session.createCriteria(GroupMember.class);
		if(null!=groupMember.getGroup_id()){
			c.add(Restrictions.eq("group_id", groupMember.getGroup_id()));
		}
		if(null!=groupMember.getType()){
			c.add(Restrictions.eq("type", groupMember.getType()));
		}
		if(null==groupMember.getIs_del()){
			c.add(Restrictions.eq("is_del", Constant.IS_DEL_NO));
		}else{
			c.add(Restrictions.eq("is_del", groupMember.getIs_del()));
		}
		@SuppressWarnings("unchecked")
		List<GroupMember> ls = c.list();
		return ls;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paging<GroupMember> getAllList(GroupMember groupMember, Paging<GroupMember> paging) {
		Session session=this.factory.getCurrentSession();
		int limitFrom = (paging.getPage()-1)*paging.getLimit();
		String limit =  " limit " + limitFrom +"," + paging.getLimit();
		String order = " order by gm.insert_time desc ";
		StringBuffer where = new StringBuffer(" where gm.is_del=0 ");
		if(null!=groupMember.getGroup_id()){
			where.append(" and gm.group_id = :group_id ");
		}
		if(null!=groupMember.getType()){
			where.append(" and gm.type = :type ");
		}
		StringBuffer countTotal = new StringBuffer("SELECT count(1) FROM `group_member` gm");
		StringBuffer sql = new StringBuffer("select gm.group_member_id,gm.group_id,gm.type,gm.mem_id,gm.insert_time,gm.del_time,gm.is_del from `group` gm ");
		Query countQuery = session.createSQLQuery(countTotal.append(where).append(order).toString());
		Query sqlQuery = session.createSQLQuery(sql.append(where).append(order).append(limit).toString());
		if(null!=groupMember.getGroup_id()){
			countQuery.setInteger("group_id", groupMember.getGroup_id());
			sqlQuery.setInteger("group_id", groupMember.getGroup_id());
		}
		if(null!=groupMember.getType()){
			countQuery.setInteger("type", groupMember.getType());
			sqlQuery.setInteger("type", groupMember.getType());
		}
		BigInteger pageTotal = (BigInteger) countQuery.uniqueResult();
		paging.setTotal(pageTotal.intValue());
		sqlQuery.setResultTransformer(Transformers.aliasToBean(GroupMember.class));
		List<GroupMember> list = new ArrayList<GroupMember>();
		list = sqlQuery.list();
		paging.setData(list);
		return paging;
	}

	@Override
	public void deleteByGroupId(Integer groupId) {
		Session session=this.factory.getCurrentSession();
		Criteria c=session.createCriteria(GroupMember.class);
		c.add(Restrictions.eq("group_id", groupId));
		c.add(Restrictions.eq("is_del", Constant.IS_DEL_NO));
		@SuppressWarnings("unchecked")
		List<GroupMember> ls = c.list();
		for (GroupMember groupMember : ls) {
			groupMember.setIs_del(Constant.IS_DEL_YES);
			groupMember.setDel_time(new Date());
			super.update(groupMember);
		}
	}
	/**
	 * 刷新缓存  用到此方法
	 * 
	 * **/
	@SuppressWarnings("unchecked")
	@Override
	public List<GroupMember> getList() {
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT gm.group_member_id,gm.group_id,gm.type,gm.mem_id,gm.insert_time,gm.del_time,gm.is_del,g.org_id,g.hx_groupid FROM group_member gm "
				+ "inner join `group` g on g.group_id = gm.group_id "
				+ "WHERE gm.is_del=0 and g.is_del=0";
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(GroupMember.class));
		List<GroupMember> list = new ArrayList<GroupMember>(); 
		list = query.list();
		return list;
	}

	@Override
	public void deleteByMemberId(String mem_ids, Integer mem_type) {
		Session session=this.factory.getCurrentSession();
		Criteria c=session.createCriteria(GroupMember.class);
		if(mem_ids.indexOf(",")!=-1){
			String ss[] = mem_ids.split(",");
			Integer mun[] = new Integer[ss.length];
			for(int i=0;i<ss.length;i++){
				mun[i]=Integer.parseInt(ss[i]);
			}
			c.add(Restrictions.in("mem_id", mun));
		}
		else
			c.add(Restrictions.eq("mem_id", Integer.parseInt(mem_ids)));
		c.add(Restrictions.eq("is_del", Constant.IS_DEL_NO));
		c.add(Restrictions.eq("type", mem_type));
		@SuppressWarnings("unchecked")
		List<GroupMember> ls = c.list();
		for (GroupMember groupMember : ls) {
			groupMember.setIs_del(Constant.IS_DEL_YES);
			groupMember.setDel_time(new Date());
			super.update(groupMember);
		}
	}

	@Override
	public void addGroupMembers(Integer group_id, String mem_ids,Integer mem_type) {
		if (StringUtils.isNotBlank(mem_ids)) {
			String[] mem_idsArr = mem_ids.split(",");
			for (String mem_id : mem_idsArr) {
				GroupMember gm = new GroupMember();
				gm.setInsert_time(new Date());
				gm.setIs_del(Constant.IS_DEL_NO);
				gm.setMem_id(Integer.parseInt(mem_id));
				gm.setGroup_id(group_id);
				gm.setType(mem_type);
				super.save(gm);
			}
		}
	}

	@Override
	public void deleteGroupMembers(Integer group_id, String mem_ids,Integer mem_type) {
		Session session=this.factory.getCurrentSession();
		Criteria c=session.createCriteria(GroupMember.class);
		if(StringUtils.isNotBlank(mem_ids)){
			if(mem_ids.indexOf(",")!=-1){
				String ss[] = mem_ids.split(",");
				Integer mun[] = new Integer[ss.length];
				for(int i=0;i<ss.length;i++){
					mun[i]=Integer.parseInt(ss[i]);
				}
				c.add(Restrictions.in("mem_id", mun));
			}
			else{
		
				c.add(Restrictions.eq("mem_id", Integer.parseInt(mem_ids)));
			}
		}

		c.add(Restrictions.eq("is_del", Constant.IS_DEL_NO));
		c.add(Restrictions.eq("type", mem_type));
		c.add(Restrictions.eq("group_id", group_id));
		@SuppressWarnings("unchecked")
		List<GroupMember> ls = c.list();
		for (GroupMember groupMember : ls) {
			groupMember.setIs_del(Constant.IS_DEL_YES);
			groupMember.setDel_time(new Date());
			super.update(groupMember);
		}
	}

	@Override
	public int getGroupMemberCount(int groupID, int type, int memberID) {
		String sql ="SELECT COUNT(gm.group_member_id) FROM edugate_base.group_member gm WHERE gm.group_id=? AND gm.type=? AND gm.mem_id=? AND gm.is_del=0;";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, groupID);
		query.setInteger(1, type);
		query.setInteger(2, memberID);
		BigInteger count = (BigInteger) query.uniqueResult();
		return count.intValue();
	}

	@Override
	public void updateGroupMembers(Integer group_id, String mem_ids, Integer group_type) {
		String sql ="SELECT gm.* FROM edugate_base.group_member gm WHERE gm.group_id=? AND gm.type=? AND gm.is_del=0;";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setInteger(0, group_id);
		// 根据组的类型来确定组成员的类型
		int gmType = 0;
		if (group_type == 2 || group_type ==3) {
			// 教师
			gmType = 2;
		} else if (group_type == 4) {
			gmType = 1;
		}
		query.setInteger(1, gmType);
		query.setResultTransformer(Transformers.aliasToBean(GroupMember.class));
		
		List<String> curList = Arrays.asList(mem_ids.split(","));
		List<String> delList = new ArrayList<String>();
		List<String> addList = new ArrayList<String>();
		boolean isExsit = false;
		
		@SuppressWarnings("unchecked")
		List<GroupMember> gmMember = query.list();
		//删除去掉的
		for (GroupMember gm : gmMember) {
			isExsit = false;
			for (String id : curList) {
				if(gm.getMem_id().toString().equals(id)){
					isExsit = true;
					break;
				}
			}
			if(!isExsit){
				delList.add(gm.getMem_id().toString());
			}
		}
		//创建新增的
		for (String id : curList) {
			isExsit = false;
			for (GroupMember gm : gmMember) {
				if(gm.getMem_id().toString().equals(id)){
					isExsit = true;
					break;
				}
			}
			if(!isExsit){
				addList.add(id);
			}
		}
		if(addList.size()>0)
			this.addGroupMembers(group_id, StringUtils.join(addList.toArray(), ",") , gmType);
		if(delList.size()>0)
			this.deleteGroupMembers(group_id, StringUtils.join(delList.toArray(), ",") , gmType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupMember> getGroupMemberListIncludeDeleted(int org_id) {
		String sql = " SELECT gm.* FROM group_member gm INNER JOIN `group` g ON gm.group_id=g.group_id AND g.org_id=? ";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(GroupMember.class));
		query.setInteger(0, org_id);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupMember> getGroupMemberByMid(int mem_id) {
		String sql = " SELECT gm.* FROM group_member gm WHERE gm.mem_id=? AND gm.is_del=0";
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(GroupMember.class));
		query.setInteger(0, mem_id);
		return query.list();
	}

	@Override
	public List<GroupMember> getListOfOrg(Integer org_id) {
		// TODO Auto-generated method stub
		Session session=this.factory.getCurrentSession();
		String sql = "SELECT gm.group_member_id,gm.group_id,gm.type,gm.mem_id,gm.insert_time,gm.del_time,gm.is_del,g.org_id,g.hx_groupid FROM group_member gm "
				+ "inner join `group` g on g.group_id = gm.group_id "
				+ "WHERE gm.is_del=0 and g.is_del=0 and g.org_id=:org_id ";
		Query query = session.createSQLQuery(sql);
		query.setInteger("org_id", org_id);
		query.setResultTransformer(Transformers.aliasToBean(GroupMember.class));
		List<GroupMember> list = new ArrayList<GroupMember>(); 
		list = query.list();
		return list;
	}

	@Override
	public List<GroupMember> getGroupMemberListByClassId(int orgId, int classId) {
		String sql = "SELECT gm.* FROM group_member gm WHERE gm.type=1 AND gm.mem_id IN "
				+ "(SELECT c2s.stud_id FROM class2student c2s WHERE c2s.clas_id=? AND c2s.is_del=0) AND gm.is_del=0;";
		
		Session session = this.factory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(GroupMember.class));
		query.setInteger(0, classId);
		
		return query.list();
	}
	
}
