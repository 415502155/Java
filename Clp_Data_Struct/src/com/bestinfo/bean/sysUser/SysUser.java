package com.bestinfo.bean.sysUser;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户基本信息表
 *
 * @author yangyuefu
 */
public class SysUser implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5605454778790952126L;

	private Integer user_id;  //用户编号

    private String user_name;   //用户名

    private String user_pwd;  //用户密码

    private Integer force_change_pwd;  //首次强制修改密码

    private Integer city_id; // 地市编号

    private String real_name;  //真实姓名

    private Date regist_date;  //注册时间

    private Integer department_id; // 部门编号

    private Integer role_id;  //系统用户角色编号

    private Integer work_status;  //使用状态

    private String user_accno;

    private String user_accstatus;

    private String user_tel;

    // 获取部门名称的中文名称
    private String department_name;
    //获取角色名称的中文名称
    private String role_name;

    /**
     * 用户id
     *
     * @return
     */
    public Integer getUser_id() {
        return user_id;
    }

    /**
     * 用户id
     *
     * @param user_id
     */
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    /**
     * 用户账号
     *
     * @return
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * 用户账号
     *
     * @param user_name
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * 用户密码
     *
     * @return
     */
    public String getUser_pwd() {
        return user_pwd;
    }

    /**
     * 用户密码
     *
     * @param user_pwd
     */
    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    /**
     * 是否强制用户修改密码
     *
     * @return
     */
    public Integer getForce_change_pwd() {
        return force_change_pwd;
    }

    /**
     * 是否强制用户修改密码
     *
     * @param force_change_pwd
     */
    public void setForce_change_pwd(Integer force_change_pwd) {
        this.force_change_pwd = force_change_pwd;
    }

    /**
     * 用户省市id
     *
     * @return
     */
    public Integer getCity_id() {
        return city_id;
    }

    /**
     * 用户省市id
     *
     * @param city_id
     */
    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    /**
     * 用户真实姓名
     *
     * @return
     */
    public String getReal_name() {
        return real_name;
    }

    /**
     * 用户真实姓名
     *
     * @param real_name
     */
    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    /**
     * 用户注册时间
     *
     * @return
     */
    public Date getRegist_date() {
        return regist_date;
    }

    /**
     * 用户注册时间
     *
     * @param regist_date
     */
    public void setRegist_date(Date regist_date) {
        this.regist_date = regist_date;
    }

    /**
     * 用户部门id
     *
     * @return
     */
    public Integer getDepartment_id() {
        return department_id;
    }

    /**
     * 用户部门id
     *
     * @param department_id
     */
    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    /**
     * 用户角色id
     *
     * @return
     */
    public Integer getRole_id() {
        return role_id;
    }

    /**
     * 用户角色id
     *
     * @param role_id
     */
    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    /**
     * 用户工作状态
     *
     * @return
     */
    public Integer getWork_status() {
        return work_status;
    }

    /**
     * 用户工作状态
     *
     * @param work_status
     */
    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }

    /**
     * @return the department_name
     */
    public String getDepartment_name() {
        return department_name;
    }

    /**
     * @param department_name the department_name to set
     */
    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    /**
     * @return the role_name
     */
    public String getRole_name() {
        return role_name;
    }

    /**
     * @param role_name the role_name to set
     */
    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getUser_accno() {
        return user_accno;
    }

    public void setUser_accno(String user_accno) {
        this.user_accno = user_accno;
    }

    public String getUser_accstatus() {
        return user_accstatus;
    }

    public void setUser_accstatus(String user_accstatus) {
        this.user_accstatus = user_accstatus;
    }

    public String getUser_tel() {
        return user_tel;
    }

    public void setUser_tel(String user_tel) {
        this.user_tel = user_tel;
    }

}
