package cn.edugate.esb.redis.cache;

import io.swagger.client.model.ModifyGroup;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import cn.edugate.esb.entity.Department;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.im.api.ChatGroupAPI;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisDepartmentDao;
import cn.edugate.esb.service.DepartmentService;
import cn.edugate.esb.service.UserService;

@Cache(name="部门缓存",entities={Department.class})
public class DepartmentCacheProvider  implements CacheProvider<Department>,java.io.Serializable {
	static Logger logger=LoggerFactory.getLogger(DepartmentCacheProvider.class);
	private static final long serialVersionUID = 1L;	
	
	private RedisDepartmentDao redisDepartmentDao;	
	@Autowired
	public void setRedisDepartmentDao(RedisDepartmentDao redisDepartmentDao) {
		this.redisDepartmentDao = redisDepartmentDao;
	}

	
	private DepartmentService departmentService;
	@Autowired
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	private ChatGroupAPI chatGroupAPI;	
	@Autowired
	public void setChatGroupAPI(ChatGroupAPI chatGroupAPI) {
		this.chatGroupAPI = chatGroupAPI;
	}
	
	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void add(Department en) {
		this.redisDepartmentDao.add(en);
		if(en.getIs_del().equals(0)){
			creatDepartment(en);
		}
	}

	@Override
	public void update(Department dt) {
		Department oldDepartment = redisDepartmentDao.getByDepId(dt.getDep_id().toString(), null);
		if(oldDepartment!=null)
			this.redisDepartmentDao.delete(oldDepartment);
		this.redisDepartmentDao.add(dt);
		
		if(dt.getIs_del().equals(0)){
			if(dt.getHx_groupid()!=null&&!"".equals(dt.getHx_groupid())){
				ModifyGroup group = new ModifyGroup();
				group.description(dt.getDep_note()).groupname(dt.getDep_name()).maxusers(500);
				Object result = this.chatGroupAPI.modifyChatGroup(dt.getHx_groupid(),group);
				if(result==null){
//					Message<String> message = MessageBuilder.withPayload(dt.getDep_id()+"_"+0+"_"+"deletedt").build();
//					this.userService.messageChannelsend(message);
				}
			}else{
				creatDepartment(dt);
			}
		}else{
			if(dt.getHx_groupid()!=null&&!"".equals(dt.getHx_groupid())){
				Object result = this.chatGroupAPI.deleteChatGroup(dt.getHx_groupid());     
				if(result!=null){
			        org.json.JSONObject obj = new org.json.JSONObject(result.toString());
					String dataString = obj.get("data").toString();
					org.json.JSONObject dataobj = new org.json.JSONObject(dataString);
					String groupid = dataobj.get("groupid").toString();
					logger.info(groupid);
			        logger.info(result.toString());
				}
		        Message<String> message = MessageBuilder.withPayload(dt.getDep_id()+"_"+0+"_"+"deletedt").build();
				this.userService.messageChannelsend(message);
			}
		}
	}

	private void creatDepartment(Department dt) {
		// TODO Auto-generated method stub
		OrgUser user = this.userService.getAdminByOrgid(dt.getOrg_id());
		io.swagger.client.model.Group group = new io.swagger.client.model.Group();
		if(user!=null){
			group.groupname(dt.getDep_name()).desc(dt.getDep_note())._public(true).maxusers(500).approval(false).owner(user.getUser_id().toString());
			Object result = this.chatGroupAPI.createChatGroup(group);
			if(result!=null){
				org.json.JSONObject obj = new org.json.JSONObject(result.toString());
				String dataString = obj.get("data").toString();
				org.json.JSONObject dataobj = new org.json.JSONObject(dataString);
				String groupid = dataobj.get("groupid").toString();
				logger.info(groupid);
				logger.info(result.toString());
				Message<String> message = MessageBuilder.withPayload(dt.getDep_id()+"_"+groupid+"_"+"adddt").build();
				this.userService.messageChannelsend(message);
			}
		}
	}

	@Override
	public void delete(Department en) {
		this.redisDepartmentDao.delete(en);
		if(en.getHx_groupid()!=null&&!"".equals(en.getHx_groupid())){
			Object resultg = this.chatGroupAPI.deleteChatGroup(en.getHx_groupid());    
			if(resultg!=null){
		        org.json.JSONObject obj = new org.json.JSONObject(resultg.toString());
				String dataString = obj.get("data").toString();
				org.json.JSONObject dataobj = new org.json.JSONObject(dataString);
				String groupid = dataobj.get("groupid").toString();
				logger.info(groupid);
		        logger.info(resultg.toString());
			}
		}
	}

	@Override
	public void refreshAll() {
		this.redisDepartmentDao.deleteAll();
		List<Department> list = departmentService.getAll();
		this.redisDepartmentDao.add(list);
	}

	@Override
	public void refreshOrg(String org_id) {
		try {
			List<Department> list = redisDepartmentDao.getDepsByOrgId(org_id);
			for (Department d : list) {
				redisDepartmentDao.delete(d);
			}
		} catch (Exception e) {
		}
		List<Department> departments = departmentService.getListByOrgId(Integer.parseInt(org_id));
		this.redisDepartmentDao.add(departments);
	}
}
