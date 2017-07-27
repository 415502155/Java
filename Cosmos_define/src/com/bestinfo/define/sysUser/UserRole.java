package com.bestinfo.define.sysUser;

/**
 * 用户角色
 *
 * @author chenliping
 */
public class UserRole {

    /**
     * 系统超级管理员
     */
    public static int syssuperuser = 1;
    /**
     * 系统管理员
     */
    public static int sysmanager = 5;
    /**
     * 终端超级管理员
     */
    public static int tmnsuperuser = 101;
    /**
     * 终端操作员
     */
    public static int tmnoperator = 102;
    /**
     * 银行接入用户
     */
    public static int bankuser = 11;
    /**
     * 运营商操作员
     */
    public static int operator = 2;
    
    public static int HALL_MANAGER = 111;//大厅经理
    public static int HALL_OPERATOR = 112;//大厅操作员
    public static int HALL_FINANCE = 113;//大厅财务
    public static int HALL_TECH = 114;//大厅技术
}
