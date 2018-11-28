package cn.edugate.esb.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.authentication.FP;
import cn.edugate.esb.authentication.FPSet;
import cn.edugate.esb.dao.IRightDao;
import cn.edugate.esb.dao.IRoleDao;
import cn.edugate.esb.dao.IRoleLabelDao;
import cn.edugate.esb.entity.Right;
import cn.edugate.esb.entity.Role;
import cn.edugate.esb.entity.RoleLabel;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TeacherRole;
import cn.edugate.esb.redis.dao.RedisRightDao;
import cn.edugate.esb.redis.dao.RedisRoleDao;
import cn.edugate.esb.redis.dao.RedisRoleLabelDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.redis.dao.RedisTeacherRoleDao;
import cn.edugate.esb.service.RoleService;
import cn.edugate.esb.util.Paging;
import cn.edugate.esb.util.PropertyFilter;
import cn.edugate.esb.util.Select;
import cn.edugate.esb.util.Selector;
import cn.edugate.esb.util.Util;
/**
 * 权限Service实现
 * Title:RoleImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年4月24日下午1:39:51
 */
@Component
@Transactional("transactionManager")
public class RoleImpl implements RoleService {
	
	private static Logger logger = Logger.getLogger(RoleImpl.class);
	
	private IRoleDao roleDao;
	private Util util;
	private IRightDao rightDao;
	private RedisRightDao redisRightDao;
	private IRoleLabelDao roleLabelDao;
	private RedisRoleDao redisRoleDao;
	private RedisRoleLabelDao redisRoleLabelDao;
	private RedisTeacherRoleDao redisTeacherRoleDao;
	private RedisTeacherDao redisTeacherDao;
	
	@Autowired
	public void setRedisTeacherDao(RedisTeacherDao redisTeacherDao) {
		this.redisTeacherDao = redisTeacherDao;
	}

	@Autowired
	public void setRedisTeacherRoleDao(RedisTeacherRoleDao redisTeacherRoleDao) {
		this.redisTeacherRoleDao = redisTeacherRoleDao;
	}

	@Autowired
	public void setRedisRoleLabelDao(RedisRoleLabelDao redisRoleLabelDao) {
		this.redisRoleLabelDao = redisRoleLabelDao;
	}

	@Autowired
	public void setRedisRoleDao(RedisRoleDao redisRoleDao) {
		this.redisRoleDao = redisRoleDao;
	}

	@Autowired
	public void setRoleLabelDao(IRoleLabelDao roleLabelDao) {
		this.roleLabelDao = roleLabelDao;
	}

	@Autowired
	public void setRedisRightDao(RedisRightDao redisRightDao) {
		this.redisRightDao = redisRightDao;
	}

	@Autowired
	public void setRightDao(IRightDao rightDao) {
		this.rightDao = rightDao;
	}
	
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Autowired
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public void add(Role role) {
		role.setInsert_time(new Date());
		roleDao.save(role);
		logger.info("create Role success");
	}

	@Override
	public Integer deleteById(Integer roleId) {
		Role role = getById(roleId);
		role.setIs_del(true);
		role.setDel_time(new Date());
		roleDao.saveOrUpdate(role);
		return roleId;
	}
	
	@Override
	public Integer update(Role role) {
		role.setUpdate_time(new Date());
		roleDao.update(role);
		return 0;
	}

	@Override
	public Role getById(Integer roleId) {
		Role role = roleDao.get(Role.class,roleId);
		return role;
	}

	@Override
	public List<Role> getAll() {
		Criterion criterion = Restrictions.ne("is_del", true);
		return roleDao.list(Role.class, criterion);
	}

	@Override
	public Paging<Role> getAllWithPaging(Paging<Role> paging,String roleName) {
		paging = this.roleDao.getAllWithPaging(paging,roleName);
		return paging;
	}

	@Override
	public List<Role> getByUserId(Integer userId,Integer orgId) {
		List<Role> list = this.roleDao.getByUserId(userId,orgId);
		return list;
	}

	@Override
	public Paging<Role> getByUserIdWithPaging(Paging<Role> paging, Integer userId) {
		paging = roleDao.getByUserIdWithPaging(userId, paging);
		return paging;
	}

	@Override
	public boolean checkRoleName(String roleName,String id) {
		Criterion criterion = Restrictions.eq("role_name", roleName);
		List<Role> list = this.roleDao.list(Role.class, criterion);
		if(list.size()==0){
			return true;
		}else{
			for (Role role : list) {
				if(!role.getRole_id().toString().equals(id)){
					return false;
				}
			}
			return true;
		}
	}

	@Override
	public List<FPSet> getFPSetsByRole(Integer roleId) {
		// TODO Auto-generated method stub
		Role role = this.getById(roleId);
		String s = null;
		if (role != null)
			s = role.getAuthorities();
		if (s == null || s.isEmpty())
			s = "{}";
		final Map<String, Integer> authorities = this.util.getPojoFromJSON(s, new TypeReference<HashMap<String, Integer>>() {});
		List<FPSet> fpsets = Selector.from(this.getfps()).select(new Select<FPSet, FP>() {

			@Override
			public FPSet apply(FP fp) {
				// TODO Auto-generated method stub
				FPSet node = new FPSet(fp);
				Integer v = authorities.get(node.getName());
				if (v != null)
					node.setValue(v);
				return node;
			}
		});
		return fpsets;
	}

	private List<FP> getfps() {
		// TODO Auto-generated method stub
		Map<String,FP> map = this.redisRightDao.getAllFp();
		if(map!=null){
			List<FP> ls = new ArrayList<FP>(map.values());		
			return ls;
		}else{
			return null;
		}
	}

	@Override
	public FPSet getFPSetByRole(Integer roleId, String authority) {
		// TODO Auto-generated method stub
		Role role=this.getById(roleId);
		String s = null;
		if(role!=null)s=role.getAuthorities(); 
        if(s==null||s.isEmpty())s="{}";
        final Map<String,Integer> authorities=this.util.getPojoFromJSON(s, 
        		new TypeReference<HashMap<String,Integer>>() {
				});
        FPSet nodes=Selector
        		.from(this.getfps())
        		.where(PropertyFilter.eq("name", authority))
        		.first(new Select<FPSet, FP>() {
					@Override
					public FPSet apply(FP fp) {
						// TODO Auto-generated method stub
						FPSet node=new FPSet(fp);
						Integer v=authorities.get(node.getName());
						if(v!=null)node.setValue(v);
						return node;
					}
			});
		return nodes;
	}

	@Override
	public void setRoleAuthority(Integer roleId, String authority, int code) {
		// TODO Auto-generated method stub
		Role role=this.getById(roleId);
		String s = null;
		if(role!=null)s=role.getAuthorities(); 
        if(s==null||s.isEmpty())s="{}";
		final Map<String,Integer> authorities=this.util.getPojoFromJSON(s, 
        		new TypeReference<HashMap<String,Integer>>() {
				});
		authorities.put(authority, code);
		role.setAuthorities(this.util.getJSONFromPOJO(authorities));
		this.roleDao.update(role);
	}

	@Override
	public void getAllRightWithPaging(Paging<Right> paging,
			String searchName) {
		// TODO Auto-generated method stub
		Long total = this.rightDao.getTotalCountByName(searchName);
		paging.setTotal(total);
		this.rightDao.getAllWithPaging(paging,searchName);
	}

	@Override
	public void addright(Right right) {
		// TODO Auto-generated method stub
		this.rightDao.saveOrUpdate(right);
	}

	@Override
	public Right getRightById(Integer rid) {
		// TODO Auto-generated method stub
		Right right = this.rightDao.get(Right.class, rid);
		return right;
	}

	@Override
	public List<RoleLabel> getRoleLabels(Integer type) {
		// TODO Auto-generated method stub
		Criterion criterion = Restrictions.and(Restrictions.eq("is_del", false))
				.add(Restrictions.eq("rl_type", type));
		List<RoleLabel> ls = this.roleLabelDao.list(RoleLabel.class, criterion, Order.asc("rl_id"));
		return ls;
	}

	@Override
	public List<Role> getByOrgId(Integer org_id) {
		// TODO Auto-generated method stub
		Map<String,Role> ls = this.redisRoleDao.getByOrgId(org_id);	
		if(ls!=null){
			List<Role> mapValuesList = new ArrayList<Role>(ls.values()); 
			for (Role role : mapValuesList) {
				RoleLabel roleLabel = this.redisRoleLabelDao.getById(role.getRl_id().toString());
				role.setRl_name(roleLabel.getRl_name());
			}			
			return mapValuesList;
		}else{
			return null;
		}
	}

	@Override
	public Role getRoleById(Integer role_id) {
		return this.roleDao.get(Role.class, role_id);
	}

	@Override
	public void removeRole(Role role) {
		this.roleDao.delete(role);
	}

	@Override
	public String createManagerRole(Integer orgId,Integer orgType) {
		return this.roleDao.createManagerRole(orgId,orgType);
	}

	@Override
	public List<Object> getOrgRoleCode(Integer org_id) {
		return this.roleDao.getOrgRoleCode(org_id);
	}

	@Override
	public List<Object> getOrgRole(Integer org_id) {
		return this.roleDao.getOrgRole(org_id);
	}

	@Override
	public List<Role> getRoleListByOrgID(int orgID) {
		return roleDao.getRoleListByOrgID(orgID);
	}

	@Override
	public List<Role> getByRoleName(String role_name, Integer org_id) {
		// TODO Auto-generated method stub
		Criterion criterion = Restrictions.and(Restrictions.eq("role_name", role_name))
				.add(Restrictions.eq("org_id", org_id));
		List<Role> ls = this.roleDao.list(Role.class, criterion, Order.desc("role_id"));
		return ls;
	}

	@Override
	public List<Teacher> getTeachersByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		Map<String, TeacherRole> teacherMap = this.redisTeacherRoleDao.getByRole_id(roleId);
		List<Teacher> teachers = new ArrayList<Teacher>();
		if(teacherMap!=null){
			for (String teacherid : teacherMap.keySet()) {
				Teacher teacher = this.redisTeacherDao.getByUserId(teacherid);
				if(teacher!=null){
					teachers.add(teacher);
				}
			}
		}
		return teachers;
	}
}
