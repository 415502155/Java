package cn.edugate.esb.redis.cache;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.entity.GroupMember;
import cn.edugate.esb.entity.StudentParent;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TeacherRole;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.redis.dao.RedisTeacherRoleDao;
import cn.edugate.esb.service.TeacherRoleService;
import cn.edugate.esb.service.UserService;

/**
 * 
 * 
 * @Name: 用户缓存
 * @Description:  
 * @date  2017-5-9 
 * @version V1.0
 */
@Cache(name="教师权限关系缓存",entities={TeacherRole.class})
public class TeacherRoleCacheProvider implements CacheProvider<TeacherRole>,java.io.Serializable {
	/**
	 * 
	 */
	static Logger logger=LoggerFactory.getLogger(TeacherRoleCacheProvider.class);
	private static final long serialVersionUID = 1L;
	private RedisTeacherRoleDao redisTeacherRoleDao;
	@Autowired
	public void setRedisTeacherRoleDao(RedisTeacherRoleDao redisTeacherRoleDao) {
		this.redisTeacherRoleDao = redisTeacherRoleDao;
	}
	@Autowired
	private RedisTeacherDao redisTeacherDao;
	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}	
	@Autowired
	private TeacherRoleService teacherRoleService;
	
	@Override
	public void update(TeacherRole user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===update====",user);
		TeacherRole olduser = this.redisTeacherRoleDao.getByTeach2role_id(user.getTech2role_id().toString());
		if(olduser!=null){
			this.redisTeacherRoleDao.delete(olduser);
		}
		this.redisTeacherRoleDao.add(user);
	}

	@Override
	public void add(TeacherRole user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===add====",user);
		this.redisTeacherRoleDao.add(user);
	}

	@Override
	public void delete(TeacherRole user) {
		// TODO Auto-generated method stub
		logger.info("aaaaaa===delete====",user);
		this.redisTeacherRoleDao.delete(user);
	}

	@Override
	public void refreshAll() {
		// TODO Auto-generated method stub
		this.redisTeacherRoleDao.deleteAll();
		List<TeacherRole> trs = this.userService.getTeacherRoles();
		//List<TeacherRole> trs = this.userService.getTeacherRolesOfSql();
		this.redisTeacherRoleDao.add(trs);
	}

	@Override
	public void refreshOrg(String org_id) {
		
		List<Teacher> listTech = redisTeacherDao.getTeacherListByOrgId(org_id, null);
		for (Teacher t : listTech) {  
			Map<String,TeacherRole> map = this.redisTeacherRoleDao.getByTeacherId(t.getUser_id()+"");
			
			if(null!=map&&!map.isEmpty()){
				for (TeacherRole tr : map.values()) {  
					this.redisTeacherRoleDao.delete(tr);
				}
			}
		}
		List<TeacherRole> list = teacherRoleService.getTeachRoleByOrg(Integer.parseInt(org_id));
		this.redisTeacherRoleDao.add(list);
		//refreshAll();
	}

}
