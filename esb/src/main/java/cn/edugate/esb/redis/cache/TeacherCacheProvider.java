package cn.edugate.esb.redis.cache;

import io.swagger.client.model.Nickname;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.edugate.esb.entity.Department;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.StudentParent;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.im.api.ChatGroupAPI;
import cn.edugate.esb.im.api.IMUserAPI;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisDepartmentDao;
import cn.edugate.esb.redis.dao.RedisOrgUserDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.service.TeacherService;

@Cache(name="教师缓存",entities={Teacher.class})
public class TeacherCacheProvider  implements CacheProvider<Teacher>,java.io.Serializable{

	static Logger logger=LoggerFactory.getLogger(TeacherCacheProvider.class);
	private static final long serialVersionUID = 1L;	
	@Autowired
	private RedisTeacherDao redisTeacherDao;	
	@Autowired
	private RedisOrgUserDao redisOrgUserDao;
	
	private RedisDepartmentDao redisDepartmentDao;
	
	@Autowired
	public void setRedisDepartmentDao(RedisDepartmentDao redisDepartmentDao) {
		this.redisDepartmentDao = redisDepartmentDao;
	}

	@Autowired
	private TeacherService teacherService;
	
	private IMUserAPI iMUserAPI;
	@Autowired
	public void setiMUserAPI(IMUserAPI iMUserAPI) {
		this.iMUserAPI = iMUserAPI;
	}
	
	private ChatGroupAPI chatGroupAPI;	
	@Autowired
	public void setChatGroupAPI(ChatGroupAPI chatGroupAPI) {
		this.chatGroupAPI = chatGroupAPI;
	}
	
	@Override
	public void add(Teacher tr) {
		if(null!=tr.getUser_id()){
			OrgUser user = redisOrgUserDao.getUserById(tr.getUser_id().toString());
			if(null!=user&&null!=user.getUser_mobile()){
				tr.setUser_mobile(user.getUser_mobile());
			}
		}
		redisTeacherDao.add(tr);
		
		RegisterUsers users = new RegisterUsers();
        User user = new User().username(tr.getUser_id().toString()).password("123456");
        users.add(user);
        if(tr.getIs_del().equals(0)){
			Object result = iMUserAPI.createNewIMUserSingle(users);
	    	if(result!=null){
	    		logger.info(result.toString());
	    		Nickname nick = new Nickname();
	        	nick.setNickname(tr.getTech_name());
	        	iMUserAPI.modifyIMUserNickNameWithAdminToken(tr.getUser_id().toString(), nick);
	    	}
	    	
	    	if(tr.getDep_id()!=null&&!"".equals(tr.getDep_id())){ 
	        	Department dtp = this.redisDepartmentDao.getByDepId(tr.getDep_id().toString(), null);
	        	if(dtp.getHx_groupid()!=null&&!"".equals(dtp.getHx_groupid())){
					Object logresult = this.chatGroupAPI.addSingleUserToChatGroup(dtp.getHx_groupid(), tr.getUser_id().toString());
					if(logresult!=null){
			        	logger.info(logresult.toString());
			        }
	        	}
	        }
		}
	}

	@Override
	public void update(Teacher tr) {
		Teacher oldTeacher = redisTeacherDao.getByTechId(tr.getTech_id().toString(), null);
		if(null!=oldTeacher.getUser_id()){
			OrgUser user = redisOrgUserDao.getUserById(oldTeacher.getUser_id().toString());
			if(null!=user&&null!=user.getUser_mobile()){
				tr.setUser_mobile(user.getUser_mobile());
			}
		}
		if(oldTeacher!=null){
			this.redisTeacherDao.delete(oldTeacher);			
		}
		this.redisTeacherDao.add(tr);
		
		if(tr.getIs_del().equals(0)){
	        Object result = iMUserAPI.getIMUserByUserName(tr.getUser_id().toString());
	        if(result==null){
	        	RegisterUsers users = new RegisterUsers();
		        User user = new User().username(tr.getUser_id().toString()).password("123456");
		        users.add(user);
	        	Object aresult = iMUserAPI.createNewIMUserSingle(users);
	        	if(aresult!=null){
	        		logger.info(aresult.toString());
	        		Nickname nick = new Nickname();
		        	nick.setNickname(tr.getTech_name());
		        	iMUserAPI.modifyIMUserNickNameWithAdminToken(tr.getUser_id().toString(), nick);
	        	}
	        }else{
	        	Nickname nick = new Nickname();
	        	nick.setNickname(tr.getTech_name());
	        	iMUserAPI.modifyIMUserNickNameWithAdminToken(tr.getUser_id().toString(), nick);
	        }
	        
	        if(tr.getDep_id()!=null&&oldTeacher.getDep_id()!=null){
				Department dtp = this.redisDepartmentDao.getByDepId(oldTeacher.getDep_id().toString(), null);
				this.chatGroupAPI.removeSingleUserFromChatGroup(dtp.getHx_groupid(), oldTeacher.getUser_id().toString());
			}
			if(tr.getDep_id()!=null&&!tr.getDep_id().equals(oldTeacher.getDep_id())){
				Department dtp = this.redisDepartmentDao.getByDepId(oldTeacher.getDep_id().toString(), null);
				if(!tr.getDep_id().equals(oldTeacher.getDep_id())){
					this.chatGroupAPI.removeSingleUserFromChatGroup(dtp.getHx_groupid(), oldTeacher.getUser_id().toString());
				}
				Object logresult = this.chatGroupAPI.addSingleUserToChatGroup(dtp.getHx_groupid(), tr.getUser_id().toString());
				if(logresult!=null){
		        	logger.info(logresult.toString());
		        }
			}
		}else{
			this.iMUserAPI.deleteIMUserByUserName(tr.getUser_id().toString());
			
			if(tr.getDep_id()!=null){
				Department dtp = this.redisDepartmentDao.getByDepId(tr.getDep_id().toString(), null);
				if(dtp.getHx_groupid()!=null){
					this.chatGroupAPI.removeSingleUserFromChatGroup(dtp.getHx_groupid(), tr.getUser_id().toString());
				}
			}
		}		
	}

	@Override
	public void delete(Teacher tr) {
		// TODO Auto-generated method stub
		this.redisTeacherDao.delete(tr);
		Object result = iMUserAPI.deleteIMUserByUserName(tr.getUser_id().toString());
    	if(result!=null){
    		logger.info(result.toString());
    	}
    	
    	if(tr.getDep_id()!=null){
			Department dtp = this.redisDepartmentDao.getByDepId(tr.getDep_id().toString(), null);
			if(dtp.getHx_groupid()!=null){
				this.chatGroupAPI.removeSingleUserFromChatGroup(dtp.getHx_groupid(), tr.getUser_id().toString());
			}
		}
	}

	@Override
	public void refreshAll() {
		// TODO Auto-generated method stub
		this.redisTeacherDao.deleteAll();
		List<Teacher> list = teacherService.getAll();
		this.redisTeacherDao.add(list);
		
	}

	@Override
	public void refreshOrg(String org_id) {
		List<Teacher> list = redisTeacherDao.getTeacherListByOrgId(org_id, null);
		for (Teacher t : list) {  
			redisTeacherDao.delete(t);
		}
		List<Teacher> teachers = teacherService.getTeachersByOrgId(Integer.parseInt(org_id));
		this.redisTeacherDao.add(teachers);
	}

}
