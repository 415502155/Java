package cn.edugate.esb.entity;

import java.util.Date;




import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.util.Util;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "teacher", catalog = "`edugate_base`")
public class Teacher {
	
		private Util util;
		@Autowired
		public void setUtil(Util util) {
			this.util = util;
		}

		private Integer tech_id;
	
		private Integer org_id;
		
		private Integer user_id;
		
		private String tech_name;
		
		private String tech_nick;
		
		private Integer dep_id;
		
		private String tech_card;
		
		private String tech_note;
		
		//头像
		private String tech_head;
		
		private Integer is_del;
	
		private Date insert_time;
		
		private Date update_time;
		
		private Date del_time;
		
		

		private String org_name;
		
		
		
		private String json;
		
		private Byte user_mobile_type;
		
		private Integer user_type;
		
		private String user_idnumber;
		
		private String user_mail;
		/**
		 * 是否是部门管理员Transient
		 */
		private Integer is_selected;
		/**
		 * 管理部门名称
		 */
		private String managing_dep_name;
		
		private OrgUser user;
		 
		private String openid;
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		public Integer getTech_id() {
			return tech_id;
		}

		public void setTech_id(Integer tech_id) {
			this.tech_id = tech_id;
		}

		public Integer getOrg_id() {
			return org_id;
		}

		public void setOrg_id(Integer org_id) {
			this.org_id = org_id;
		}

		public Integer getUser_id() {
			return user_id;
		}

		public void setUser_id(Integer user_id) {
			this.user_id = user_id;
		}

		public String getTech_name() {
			return tech_name;
		}

		public void setTech_name(String tech_name) {
			this.tech_name = tech_name;
		}

		public String getTech_nick() {
			return tech_nick;
		}

		public void setTech_nick(String tech_nick) {
			this.tech_nick = tech_nick;
		}

		public Integer getDep_id() {
			return dep_id;
		}

		public void setDep_id(Integer dep_id) {
			this.dep_id = dep_id;
		}

		public String getTech_card() {
			return tech_card;
		}

		public void setTech_card(String tech_card) {
			this.tech_card = tech_card;
		}

		public String getTech_note() {
			return tech_note;
		}

		public void setTech_note(String tech_note) {
			this.tech_note = tech_note;
		}

		public Integer getIs_del() {
			return is_del;
		}

		public void setIs_del(Integer is_del) {
			this.is_del = is_del;
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

		public String getTech_head() {
			return tech_head;
		}

		public void setTech_head(String tech_head) {
			this.tech_head = tech_head;
		}
		

		@Transient
		public String getOrg_name() {
			return org_name;
		}

		public void setOrg_name(String org_name) {
			this.org_name = org_name;
		}
		

		public String getJson() {
			return json;
		}

		public void setJson(String json) {
			this.json = json;
		}
		@Transient
		public Byte getUser_mobile_type() {
			return user_mobile_type;
		}

		public void setUser_mobile_type(Byte user_mobile_type) {
			this.user_mobile_type = user_mobile_type;
		}
		@Transient
		public Integer getIs_selected() {
			return is_selected;
		}

		public void setIs_selected(Integer is_selected) {
			this.is_selected = is_selected;
		}

		@Transient
		public String getManaging_dep_name() {
			return managing_dep_name;
		}

		public void setManaging_dep_name(String managing_dep_name) {
			this.managing_dep_name = managing_dep_name;
		}
		
		private String headurl;
		@Transient
		public String getHeadurl() {
			if(this.util!=null){
				if (this.tech_head!=null&&!"".equals(this.tech_head)) {
					this.headurl = util.getPathByPicName(this.tech_head);
				} else {
					this.headurl = "";
				}
			}
			return headurl;
		}
		public void setHeadurl(String headurl) {
			this.headurl = headurl;
		}
		
		//出生日期
		private Date birthday;
		public Date getBirthday() {
			return birthday;
		}
		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}
		//性别  用户性别(0:男，1:女，NULL:保密)
		private Byte sex;
		public Byte getSex() {
			return sex;
		}
		public void setSex(Byte sex) {
			this.sex = sex;
		}
		@Transient
		public OrgUser getUser() {
			return user;
		}

		public void setUser(OrgUser user) {
			this.user = user;
		}
		@Transient
		public Integer getUser_type() {
			return user_type;
		}

		public void setUser_type(Integer user_type) {
			this.user_type = user_type;
		}
		@Transient
		public String getUser_idnumber() {
			return user_idnumber;
		}

		public void setUser_idnumber(String user_idnumber) {
			this.user_idnumber = user_idnumber;
		}
		@Transient
		public String getUser_mail() {
			return user_mail;
		}

		public void setUser_mail(String user_mail) {
			this.user_mail = user_mail;
		}
		
		//部门名称
		private String dep_name;
		@Transient
		public String getDep_name() {
			return dep_name;
		}

		public void setDep_name(String dep_name) {
			this.dep_name = dep_name;
		}
		//教师手机
		private String user_mobile;
		@Transient
		public String getUser_mobile() {
			return user_mobile;
		}

		public void setUser_mobile(String user_mobile) {
			this.user_mobile = user_mobile;
		}
		//教师角色
		private String role_name;
		@Transient
		public String getRole_name() {
			return role_name;
		}

		public void setRole_name(String role_name) {
			this.role_name = role_name;
		}
		
		@Transient
		public String getOpenid() {
			return openid;
		}

		public void setOpenid(String openid) {
			this.openid = openid;
		}		
		
}
