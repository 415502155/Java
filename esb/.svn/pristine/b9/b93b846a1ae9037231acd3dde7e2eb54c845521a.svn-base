package cn.edugate.esb.redis.cache;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.Group;
import cn.edugate.esb.entity.GroupMember;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.im.api.ChatGroupAPI;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisGroupDao;
import cn.edugate.esb.redis.dao.RedisGroupMemberDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.service.GroupMemberService;

/**
 * 组成员缓存
 * Title:GroupMemberCacheProvider
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月23日下午6:06:31
 */
@Cache(name="组成员缓存",entities={GroupMember.class})
public class GroupMemberCacheProvider implements CacheProvider<GroupMember>,java.io.Serializable {

	private static final long serialVersionUID = 1098076108225687840L;

	static Logger logger=LoggerFactory.getLogger(GroupMemberCacheProvider.class);
	
	private RedisGroupMemberDao redisGroupMemberDao;
	private RedisTeacherDao redisTeacherDao;
	private RedisGroupDao redisGroupDao;
	
	@Autowired
	public void setRedisGroupDao(RedisGroupDao redisGroupDao) {
		this.redisGroupDao = redisGroupDao;
	}

	@Autowired
	public void setRedisTeacherDao(RedisTeacherDao redisTeacherDao) {
		this.redisTeacherDao = redisTeacherDao;
	}

	@Autowired
	public void setRedisGroupMemberDao(RedisGroupMemberDao redisGroupMemberDao) {
		this.redisGroupMemberDao = redisGroupMemberDao;
	}
	private GroupMemberService groupMemberService;
	@Autowired
	public void setGroupMemberService(GroupMemberService groupMemberService) {
		this.groupMemberService = groupMemberService;
	}
	private ChatGroupAPI chatGroupAPI;	
	@Autowired
	public void setChatGroupAPI(ChatGroupAPI chatGroupAPI) {
		this.chatGroupAPI = chatGroupAPI;
	}

	@Override
	public void add(GroupMember groupMember) {
		logger.info("groupMemberRedis===add====",groupMember);
		this.redisGroupMemberDao.addGroupMember(groupMember);
		Group g = this.redisGroupDao.getGroupById(groupMember.getGroup_id(), null);
		Teacher t = this.redisTeacherDao.getByTechId(groupMember.getMem_id().toString(), null);
		if(t!=null){		
			Object logresult = this.chatGroupAPI.addSingleUserToChatGroup(g.getHx_groupid(), t.getUser_id().toString());
			if(logresult!=null){
	        	logger.info(logresult.toString());
	        }
		}
	}

	@Override
	public void update(GroupMember groupMember) {
		logger.info("groupMemberRedis===update====",groupMember);
		GroupMember oldGroupMember = this.redisGroupMemberDao.getGroupMemberById(groupMember.getGroup_member_id(),false);
		if(oldGroupMember!=null && oldGroupMember.getGroup_member_id()!=null)
			this.redisGroupMemberDao.deleteGroupMember(oldGroupMember);
		this.redisGroupMemberDao.addGroupMember(groupMember);	
		
		Group g = this.redisGroupDao.getGroupById(groupMember.getGroup_id(), null);
		Teacher t = this.redisTeacherDao.getByTechId(groupMember.getMem_id().toString(), null);
		if(g!=null&&t!=null&&groupMember.getIs_del().equals(1)){
			Object result = chatGroupAPI.removeSingleUserFromChatGroup(g.getHx_groupid(), t.getUser_id().toString());
			if(result!=null){
	        	logger.info(result.toString());
	        }
		}
		
	}

	@Override
	public void delete(GroupMember groupMember) {
		logger.info("groupMemberRedis===delete====",groupMember);
		this.redisGroupMemberDao.deleteGroupMember(groupMember);
		
		Group g = this.redisGroupDao.getGroupById(groupMember.getGroup_id(), null);
		Teacher t = this.redisTeacherDao.getByTechId(groupMember.getMem_id().toString(), null);
		if(g!=null&&t!=null){		
			Object result = chatGroupAPI.removeSingleUserFromChatGroup(g.getHx_groupid(), t.getUser_id().toString());
			if(result!=null){
	        	logger.info(result.toString());
	        }
		}
	}

	@Override
	public void refreshAll() {
		this.redisGroupMemberDao.deleteAll();
		List<GroupMember> groupMembers = this.groupMemberService.getAll();
		this.redisGroupMemberDao.addGroupMembers(groupMembers);	
	}

	@Override
	public void refreshOrg(String org_id) {
		//refreshAll();
		Map<String,Group> list = redisGroupDao.getGroups(Integer.parseInt(org_id), null);
		if(null!=list&&!list.isEmpty()){
			for (Group g : list.values()) {  
				Map<Integer,GroupMember> groupMemberList = redisGroupMemberDao.getStudentGroupMembersById(g.getGroup_id()+""); 
				if(null!=groupMemberList&&!groupMemberList.isEmpty()){
					for (GroupMember gm : groupMemberList.values()) {  
						this.redisGroupMemberDao.deleteGroupMember(gm);
					}
				}
			}
		}
		
		List<GroupMember> groupMembers = this.groupMemberService.getAllOfOrg(Integer.parseInt(org_id));
		this.redisGroupMemberDao.addGroupMembers(groupMembers);	
		
	}

}
