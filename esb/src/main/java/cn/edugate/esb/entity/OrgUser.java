package cn.edugate.esb.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import cn.edugate.esb.authentication.EduGrantedAuthority;
import cn.edugate.esb.redis.dao.RedisOrganizationDao;
import cn.edugate.esb.redis.dao.RedisParentDao;
import cn.edugate.esb.redis.dao.RedisRoleDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.redis.dao.RedisTeacherRoleDao;
import cn.edugate.esb.service.ParentService;
import cn.edugate.esb.service.StudentService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;

@Entity
@Table(name = "org_user", catalog = "`edugate_base`")
public class OrgUser implements java.io.Serializable {

	private Util util;

	private RedisTeacherRoleDao redisTeacherRoleDao;

	private RedisRoleDao redisRoleDao;

	private RedisTeacherDao redisTeacherDao;
	
	@Autowired
	private RedisParentDao redisParentDao;
	
	@Autowired
	private StudentService studentService;

	@Autowired
	private RedisOrganizationDao redisOrganizationDao;
	

	@Autowired
	public void setRedisTeacherDao(RedisTeacherDao redisTeacherDao) {
		this.redisTeacherDao = redisTeacherDao;
	}

	@Autowired
	public void setRedisRoleDao(RedisRoleDao redisRoleDao) {
		this.redisRoleDao = redisRoleDao;
	}

	@Autowired
	public void setRedisTeacherRoleDao(RedisTeacherRoleDao redisTeacherRoleDao) {
		this.redisTeacherRoleDao = redisTeacherRoleDao;
	}

	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	/**
	 * 用户
	 */
	private static final long serialVersionUID = 1L;

	// 用户ID
	private Integer user_id;

	private Integer org_id;

	private Integer identity;

	private Integer is_current;

	private String user_idnumber;

	private String user_number;

	private String user_loginname;

	private String user_loginpass;

	private String user_salt;

	private String user_udid;

	private Integer loginnum;

	//暂时用于学生卡号 显示
	private String user_mail;

	private String user_mobile;

	private Byte status;
	
	private String openid;

	private Date insert_time;

	private Date update_time;

	private Date del_time;

	private Boolean is_del;

	private List<Role> roles;

	private Byte user_mobile_type;

	private Integer user_type;
	
	private String rlids;

	// private List<TechRange> ranges;

	// private Map<String,EduGrantedAuthority> authorities= new LinkedHashMap<String,EduGrantedAuthority>();
	//
	// public void setAuthorities(Map<String, EduGrantedAuthority> authorities) {
	// this.authorities = authorities;
	// }
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

	public Integer getIdentity() {
		return identity;
	}

	public void setIdentity(Integer identity) {
		this.identity = identity;
	}

	public Integer getIs_current() {
		return is_current;
	}

	public void setIs_current(Integer is_current) {
		this.is_current = is_current;
	}

	public String getUser_idnumber() {
		return user_idnumber;
	}

	public void setUser_idnumber(String user_idnumber) {
		this.user_idnumber = user_idnumber;
	}

	public String getUser_number() {
		return user_number;
	}

	public void setUser_number(String user_number) {
		this.user_number = user_number;
	}

	public String getUser_loginname() {
		return user_loginname;
	}

	public void setUser_loginname(String user_loginname) {
		this.user_loginname = user_loginname;
	}

	public String getUser_loginpass() {
		return user_loginpass;
	}

	public void setUser_loginpass(String user_loginpass) {
		this.user_loginpass = user_loginpass;
	}

	public String getUser_salt() {
		return user_salt;
	}

	public void setUser_salt(String user_salt) {
		this.user_salt = user_salt;
	}

	public String getUser_udid() {
		return user_udid;
	}

	public void setUser_udid(String user_udid) {
		this.user_udid = user_udid;
	}

	public Integer getLoginnum() {
		return loginnum;
	}

	public void setLoginnum(Integer loginnum) {
		this.loginnum = loginnum;
	}

	public String getUser_mail() {
		return user_mail;
	}

	public void setUser_mail(String user_mail) {
		this.user_mail = user_mail;
	}

	public String getUser_mobile() {
		return user_mobile;
	}

	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Date getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Date getDel_time() {
		return del_time;
	}

	public void setDel_time(Date del_time) {
		this.del_time = del_time;
	}

	public Boolean getIs_del() {
		return is_del;
	}

	public void setIs_del(Boolean is_del) {
		this.is_del = is_del;
	}

	/**
	 * 授权集合
	 * 
	 * @return 授权集合
	 */
	// @Transient
	// public Map<String,EduGrantedAuthority> getAuthorities() {
	// // TODO Auto-generated method stub
	// authorities.clear();
	// Map<String,EduGrantedAuthority> data = this.obtionGrantedAuthorities();
	// if(data!=null){
	// authorities.putAll(data);
	// }
	// return authorities;
	// }

	// 取得用户的权限
	@SuppressWarnings("unused")
	private Map<String, EduGrantedAuthority> obtionGrantedAuthorities() {
		Map<String, EduGrantedAuthority> auths = new LinkedHashMap<String, EduGrantedAuthority>();
		List<Role> roles = this.getRoles();
		if (roles != null) {
			for (Role role : roles) {
				// GrantedAuthority role_find=auths.get(role.getRole_id().toString());
				// if(role_find==null)
				// {
				// role_find=new SimpleGrantedAuthority(role.getRole_id().toString());
				// auths.put(role.getRole_id().toString(), role_find);
				String s = role.getAuthorities();
				if (s == null || s.isEmpty())
					s = "{}";
				Map<String, Integer> authorities = this.util.getPojoFromJSON(s, new TypeReference<HashMap<String, Integer>>() {
				});
				if (authorities != null) {
					for (String authority : authorities.keySet()) {
						int code = authorities.get(authority);
						GrantedAuthority find = auths.get(authority);
						if (find != null) {
							if (find instanceof EduGrantedAuthority) {
								EduGrantedAuthority ga = (EduGrantedAuthority) find;
								ga.setCode(ga.getCode() | code);
							}
						} else {
							EduGrantedAuthority ga = new EduGrantedAuthority();
							ga.setAuthority(authority);
							ga.setCode(code);
							auths.put(authority, ga);
						}
					}

				}
				// }
			}
		}

		return auths;
	}

	@Transient
	public List<Role> getRoles() {
		List<Role> data = new ArrayList<Role>();
		if (redisTeacherRoleDao != null&&this.roles==null) {
			if(Constant.IDENTITY_TEACHER==this.getIdentity()||Constant.IDENTITY_MANAGER==this.getIdentity()){
				Map<String, TeacherRole> maps = this.redisTeacherRoleDao.getByTeacherId(this.getUser_id().toString());
				if (maps != null) {
					for (String roleid : maps.keySet()) {
						Role role = this.redisRoleDao.getByRoleId(roleid);
						if (role != null && role.getOrg_id().equals(this.getOrg_id())) {
							data.add(role);
						}
					}
					this.roles = data;
				}
			}
		}

		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		 this.roles = roles;
	}

	// @Transient
	// public List<TechRange> getRanges() {
	// List<TechRange> data = new ArrayList<TechRange>();
	// Map<String, TechRange> maps =
	// this.redisTechRangeDao.getByTeacherId(this.getOrg_id().toString(),this.getUser_id().toString());
	// if(maps!=null){
	// for (String keystr : maps.keySet()) {
	// TechRange tr = maps.get(keystr);
	// if(tr!=null){
	// RoleLabel trl = this.redisRoleLabelDao.getById(tr.getRl_id().toString());
	// if(trl!=null){
	// tr.setRl_name(trl.getRl_name());
	// }
	// data.add(tr);
	// }
	// }
	// this.ranges = data;
	// }
	// return this.ranges;
	// }
	// public void setRanges(List<TechRange> ranges) {
	// this.ranges = ranges;
	// }
	private Teacher teacher;

	@Transient
	public Teacher getTeacher() {
		if (redisTeacherDao != null && this.teacher==null) {
			if(Constant.IDENTITY_TEACHER==this.getIdentity()||Constant.IDENTITY_MANAGER==this.getIdentity()){
				this.teacher = this.redisTeacherDao.getByUserId(this.getUser_id().toString());
			}
		}
		return teacher;
	}
	
	public void setTeacher(Teacher teacher) {
		 this.teacher = teacher;
	}
	
	@Transient
	private Parent parent;
	
	@Transient
	public Parent getParent() {
//		if (parentService != null && parent == null) {
//			this.parent = this.parentService.getParentByUserID(this.getOrg_id().intValue(), this.getUser_id().intValue());
//		}
		if (redisParentDao != null && this.parent==null) {
			if(Constant.IDENTITY_PARENT==this.getIdentity()){
				this.parent = this.redisParentDao.getParentsByUserId(this.getUser_id().toString());
			}
		}
		return parent;
	}

	@Transient
	public void setParent(Parent parent) {
		this.parent = parent;
	}
	
	@Transient
	private Student student;
	
	@Transient
	public Student getStudent() {
//		if (studentService != null && student == null) {
//			this.student = studentService.getStudentByUserID(this.getOrg_id().intValue(), this.getUser_id().intValue());
//		}
		return student;
	}

	@Transient
	public void setStudent(Student student) {
		this.student = student;
	}

	private Organization organization;

	@Transient
	public Organization getOrganization() {
		if (redisOrganizationDao != null && this.organization==null) {
			this.organization = redisOrganizationDao.getByOrgId(String.valueOf(getOrg_id().intValue()), null);
		}
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Byte getUser_mobile_type() {
		return user_mobile_type;
	}

	public void setUser_mobile_type(Byte user_mobile_type) {
		this.user_mobile_type = user_mobile_type;
	}

	public Integer getUser_type() {
		return user_type;
	}

	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}
	
	
	
	private List<Map<String,Object>> identityDatas;
	@Transient
	public List<Map<String, Object>> getIdentityDatas() {
		return identityDatas;
	}

	public void setIdentityDatas(List<Map<String, Object>> identityDatas) {
		this.identityDatas = identityDatas;
	}
	
	private List<Student> childrens;

	@Transient
	public List<Student> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<Student> childrens) {
		this.childrens = childrens;
	}
	@Transient
	public String getRlids() {
		return rlids;
	}

	public void setRlids(String rlids) {
		this.rlids = rlids;
	}
	
	
	

}
