package cn.edugate.esb.imp;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.dao.IGroupMemberDao;
import cn.edugate.esb.entity.Group;
import cn.edugate.esb.entity.GroupMember;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.redis.dao.RedisGroupDao;
import cn.edugate.esb.redis.dao.RedisStudentDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.service.GroupMemberService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Paging;

/**
 * 组成员服务实现类
 * Title:GroupMemberImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午9:33:24
 */
@Component
@Transactional("transactionManager")
public class GroupMemberImpl implements GroupMemberService {

	private static Logger logger = Logger.getLogger(GroupMemberImpl.class);
	
	@Autowired
	private IGroupMemberDao groupMemberDao;
	@Autowired
	private RedisTeacherDao redisTeacherDao;
	@Autowired
	private RedisStudentDao redisStudentDao;
	@Autowired
	private RedisGroupDao redisGroupDao;
	
	@Override
	public void add(GroupMember groupMember) {
		//调用时需要为orgId赋值
		groupMember.setInsert_time(new Date());
		groupMember.setIs_del(Constant.IS_DEL_NO);
		try {
			this.groupMemberDao.save(groupMember);
		} catch (Exception e) {
			logger.error("GroupMember Add error:"+e.getMessage());
		}
	}

	@Override
	public Integer delete(Integer groupMemberId) {
		GroupMember groupMember = new GroupMember();
		groupMember = getById(groupMemberId);
		groupMember.setIs_del(Constant.IS_DEL_YES);
		groupMember.setDel_time(new Date());
		try {
			groupMemberDao.update(groupMember);
			return groupMemberId;
		} catch (Exception e) {
			logger.error("GroupMember delete error:"+e.getMessage());
			return null;
		}
	}

	@Override
	public GroupMember getById(Integer groupMemberId) {
		GroupMember groupMember = new GroupMember();
		try {
			groupMember = groupMemberDao.get(GroupMember.class,groupMemberId);
			return groupMember;
		} catch (Exception e) {
			logger.error("GroupMember getById error:"+e.getMessage());
			return null;
		}
	}

	@Override
	public List<GroupMember> getAllList(GroupMember groupMember) {
		try {
			return groupMemberDao.getAllList(groupMember);
		} catch (Exception e) {
			logger.error("GroupMember getAllList error:"+e.getMessage());
			return null;
		}
	}

	@Override
	public Paging<GroupMember> getAllList(GroupMember groupMember, Paging<GroupMember> paging) {
		try {
			return groupMemberDao.getAllList(groupMember, paging);
		} catch (Exception e) {
			logger.error("GroupMember getAllList(Paging) error:"+e.getMessage());
			return null;
		}
	}

	@Override
	public List<GroupMember> getAll() {
		try {
			return groupMemberDao.getList();
		} catch (Exception e) {
			logger.error("GroupMember getAll error:"+e.getMessage());
			return null;
		}
	}

	@Override
	public void replaceMembersOfGroup(Integer groupId, Integer type, String memIds, Integer orgId) {
		String[] memIdArr = memIds.split(",");
		try {
			Group group = redisGroupDao.getGroupById(groupId,null);
			if(null!=group){
				groupMemberDao.deleteByGroupId(groupId);
				if(null!=type&&StringUtils.isNotEmpty(memIds)){
					if(Constant.GROUPMEMBER_TYPE_STUDENT==type){
						for (int i = 0; i < memIdArr.length; i++) {
							Student student = redisStudentDao.getByStudentId(Integer.parseInt(memIdArr[i]));
							if(null!=student){
								GroupMember gm = new GroupMember();
								gm.setMem_id(Integer.parseInt(memIdArr[i]));
								gm.setType(type);
								gm.setGroup_id(groupId);
								gm.setOrg_id(orgId);
								add(gm);
							}
						}
					}else if(Constant.GROUPMEMBER_TYPE_TEACHER==type){
						for (int i = 0; i < memIdArr.length; i++) {
							Teacher teacher = redisTeacherDao.getByTechId(memIdArr[i],null);
							if(null!=teacher){
								GroupMember gm = new GroupMember();
								gm.setMem_id(Integer.parseInt(memIdArr[i]));
								gm.setType(type);
								gm.setGroup_id(groupId);
								gm.setOrg_id(orgId);
								add(gm);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("GroupMember replaceMembersOfGroup error:"+e.getMessage());
		}
	}

	@Override
	public void deleteByMemberId(String mem_ids, Integer mem_type) {
		try {
			groupMemberDao.deleteByMemberId(mem_ids,mem_type);
		} catch (Exception e) {
			logger.error("GroupMember deleteByMemberId error:"+e.getMessage());
		}
	}

	@Override
	public void addGroupMembers(Integer group_id, String mem_ids, Integer mem_type) {
		try {
			groupMemberDao.addGroupMembers(group_id,mem_ids,mem_type);
		} catch (Exception e) {
			logger.error("GroupMember deleteByMemberId error:"+e.getMessage());
		}
	}

	@Override
	public void deleteGroupMembers(Integer group_id, String mem_ids, Integer mem_type) {
		try {
			groupMemberDao.deleteGroupMembers(group_id,mem_ids,mem_type);
		} catch (Exception e) {
			logger.error("GroupMember deleteByMemberId error:"+e.getMessage());
		}
	}

	@Override
	public void updateGroupMembers(Integer group_id, String mem_ids, Integer group_type) {
		try {
			groupMemberDao.updateGroupMembers(group_id,mem_ids,group_type);
		} catch (Exception e) {
			logger.error("GroupMember updateGroupMembers error:"+e.getMessage());
		}
	}

	@Override
	public List<GroupMember> getAllOfOrg(Integer orgId) {
		// TODO Auto-generated method stub
		try {
			return groupMemberDao.getListOfOrg(orgId);
		} catch (Exception e) {
			logger.error("GroupMember getAllOfOrg error:"+e.getMessage());
			return null;
		}
	}

}
