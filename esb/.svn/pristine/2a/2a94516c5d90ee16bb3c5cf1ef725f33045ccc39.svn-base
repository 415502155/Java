package cn.edugate.esb.redis.cache;

import io.swagger.client.model.ModifyGroup;
import io.swagger.client.model.Nickname;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import cn.edugate.esb.entity.Group;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisGroupDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.service.GroupService;
import cn.edugate.esb.service.UserService;
import cn.edugate.esb.im.api.ChatGroupAPI;
import cn.edugate.esb.im.api.IMUserAPI;

/**
 * 组缓存
 * Title:GroupCacheProvider
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月23日下午6:06:14
 */
@Cache(name="组缓存",entities={Group.class})
public class GroupCacheProvider implements CacheProvider<Group>,java.io.Serializable {

	private static final long serialVersionUID = 1098076108225687840L;

	static Logger logger=LoggerFactory.getLogger(GroupCacheProvider.class);
	
	private ChatGroupAPI chatGroupAPI;
	private IMUserAPI iMUserAPI;
	
	@Autowired
	public void setiMUserAPI(IMUserAPI iMUserAPI) {
		this.iMUserAPI = iMUserAPI;
	}

	@Autowired
	public void setChatGroupAPI(ChatGroupAPI chatGroupAPI) {
		this.chatGroupAPI = chatGroupAPI;
	}
	private RedisGroupDao redisGroupDao;
	@Autowired
	public void setRedisGroupDao(RedisGroupDao redisGroupDao) {
		this.redisGroupDao = redisGroupDao;
	}
	private GroupService groupService;
	@Autowired
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	private RedisTeacherDao redisTeacherDao;
	@Autowired
	public void setRedisTeacherDao(RedisTeacherDao redisTeacherDao) {
		this.redisTeacherDao = redisTeacherDao;
	}
	
//	@Autowired
//	@Qualifier("syncGroupChannel")
//	MessageChannel messageChannel;	
	

	@Override
	public void add(Group gp) {
		logger.info("groupRedis===add====",gp);
		this.redisGroupDao.addGroup(gp);
		
		if(gp.getIs_del().equals(0)){
			creatGroup(gp);
		}
//		else{
//			if(gp.getHx_groupid()!=null&&!"".equals(gp.getHx_groupid())){
//				Object result = this.chatGroupAPI.deleteChatGroup(gp.getHx_groupid());    
//				if(result!=null){
//			        org.json.JSONObject obj = new org.json.JSONObject(result.toString());
//					String dataString = obj.get("data").toString();
//					org.json.JSONObject dataobj = new org.json.JSONObject(dataString);
//					String groupid = dataobj.get("groupid").toString();
//					logger.info(groupid);
//			        logger.info(result.toString());
//				}
//		        Message<String> message = MessageBuilder.withPayload(gp.getGroup_id()+"_"+0+"_"+"delete").build();
//				this.userService.messageChannelsend(message);
//			}
//		}
	}

	@Override
	public void update(Group gp) {
		logger.info("groupRedis===update====",gp);
		Group oldGroup = this.redisGroupDao.getGroupById(gp.getGroup_id(),null);
		if(oldGroup!=null)
			this.redisGroupDao.deleteGroup(oldGroup);
		this.redisGroupDao.addGroup(gp);
		
		if(gp.getIs_del().equals(0)){
			if(gp.getHx_groupid()!=null&&!"".equals(gp.getHx_groupid())){
				ModifyGroup group = new ModifyGroup();
				group.description(gp.getNote()).groupname(gp.getGroup_name()).maxusers(500);
				Object result = this.chatGroupAPI.modifyChatGroup(gp.getHx_groupid(),group);
				if(result==null){
//					Message<String> message = MessageBuilder.withPayload(gp.getGroup_id()+"_"+0+"_"+"deletegp").build();
//					this.userService.messageChannelsend(message);
				}
			}else{
				creatGroup(gp);
			}
		}else{
			if(gp.getHx_groupid()!=null&&!"".equals(gp.getHx_groupid())){
				Object result = this.chatGroupAPI.deleteChatGroup(gp.getHx_groupid());     
				if(result!=null){
			        org.json.JSONObject obj = new org.json.JSONObject(result.toString());
					String dataString = obj.get("data").toString();
					org.json.JSONObject dataobj = new org.json.JSONObject(dataString);
					String groupid = dataobj.get("groupid").toString();
					logger.info(groupid);
			        logger.info(result.toString());
				}
		        Message<String> message = MessageBuilder.withPayload(gp.getGroup_id()+"_"+0+"_"+"deletegp").build();
				this.userService.messageChannelsend(message);
			}
		}
	}

	private void creatGroup(Group gp) {
		OrgUser user = this.userService.getAdminByOrgid(gp.getOrg_id());
		io.swagger.client.model.Group group = new io.swagger.client.model.Group();
		if(user!=null){
			Teacher teacher = this.redisTeacherDao.getByUserId(user.getUser_id().toString());
			if(teacher!=null){
				creatHxUser(user.getUser_id().toString(),teacher.getTech_name());
				group.groupname(gp.getGroup_name()).desc(gp.getNote())._public(true).maxusers(500).approval(false).owner(user.getUser_id().toString());
				Object result = this.chatGroupAPI.createChatGroup(group);
				if(result!=null){
					org.json.JSONObject obj = new org.json.JSONObject(result.toString());
					String dataString = obj.get("data").toString();
					org.json.JSONObject dataobj = new org.json.JSONObject(dataString);
					String groupid = dataobj.get("groupid").toString();
					logger.info(groupid);
					logger.info(result.toString());
					Message<String> message = MessageBuilder.withPayload(gp.getGroup_id()+"_"+groupid+"_"+"addgp").build();
					this.userService.messageChannelsend(message);
				}
			}
		}
	}

	@Override
	public void delete(Group gp) {
		logger.info("groupRedis===delete====",gp);
		this.redisGroupDao.deleteGroup(gp);
		if(gp.getHx_groupid()!=null&&!"".equals(gp.getHx_groupid())){
			Object result = this.chatGroupAPI.deleteChatGroup(gp.getHx_groupid());    
			if(result!=null){
		        org.json.JSONObject obj = new org.json.JSONObject(result.toString());
				String dataString = obj.get("data").toString();
				org.json.JSONObject dataobj = new org.json.JSONObject(dataString);
				String groupid = dataobj.get("groupid").toString();
				logger.info(groupid);
		        logger.info(result.toString());
			}
	        Message<String> message = MessageBuilder.withPayload(gp.getGroup_id()+"_"+0+"_"+"deletegp").build();
			this.userService.messageChannelsend(message);
		}
	}

	@Override
	public void refreshAll() {
		this.redisGroupDao.deleteAllGroups();
		List<Group> groups = this.groupService.getAll();
		this.redisGroupDao.addGroups(groups);	

//		for (Group gp : groups) {
//			if(gp.getIs_del().equals(0)){
//				if(gp.getHx_groupid()!=null&&!"".equals(gp.getHx_groupid())){
//					ModifyGroup group = new ModifyGroup();
//					group.description(gp.getNote()).groupname(gp.getGroup_name()).maxusers(500);
//					Object result = this.chatGroupAPI.modifyChatGroup(gp.getHx_groupid(),group);
//					if(result==null){
//						Message<String> message = MessageBuilder.withPayload(gp.getGroup_id()+"_"+0+"_"+"delete").build();
//						this.userService.messageChannelsend(message);
//					}
//				}else{
//					creatGroup(gp);
//				}
//			}else{
//					if(gp.getHx_groupid()!=null&&!"".equals(gp.getHx_groupid())){
//						Object result = this.chatGroupAPI.deleteChatGroup(gp.getHx_groupid());    
//						if(result!=null){
//				        org.json.JSONObject obj = new org.json.JSONObject(result.toString());
//						String dataString = obj.get("data").toString();
//						org.json.JSONObject dataobj = new org.json.JSONObject(dataString);
//						String groupid = dataobj.get("groupid").toString();
//						logger.info(groupid);
//				        logger.info(result.toString());
//					}
//			        Message<String> message = MessageBuilder.withPayload(gp.getGroup_id()+"_"+0+"_"+"delete").build();
//					this.userService.messageChannelsend(message);
//				}
//			}
//		}

	}
	
	private void creatHxUser(String user_id,String name) {
		Object iresult = iMUserAPI.getIMUserByUserName(user_id);
		if(iresult==null){
			RegisterUsers users = new RegisterUsers();
		    User user = new User().username(user_id).password("123456");
		    users.add(user);
			Object aresult = iMUserAPI.createNewIMUserSingle(users);
			if(aresult!=null){
				logger.info(aresult.toString());
				Nickname nick = new Nickname();
		    	nick.setNickname(name);
		    	iMUserAPI.modifyIMUserNickNameWithAdminToken(user_id, nick);
			}
		}else{
			Nickname nick = new Nickname();
			nick.setNickname(name);
			iMUserAPI.modifyIMUserNickNameWithAdminToken(user_id, nick);
		}
	}

	@Override
	public void refreshOrg(String org_id) {
		Map<String,Group> list = redisGroupDao.getGroups(Integer.parseInt(org_id), null);
		if(null!=list&&!list.isEmpty()){
			for (Group g : list.values()) {  
				redisGroupDao.deleteGroup(g); 
			}
		}
		Group group = new Group();
		group.setOrg_id(Integer.parseInt(org_id));
		List<Group> groups = groupService.getAllList(group);
		this.redisGroupDao.addGroups(groups);
	}

}
