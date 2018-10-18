package com.bestinfo.bean.session;

import java.io.Serializable;
import java.util.Date;

/**
 * 会话缓存数据结构
 *
 * @author YangRong
 */
public class SessionInfo implements Serializable {

    private static final long serialVersionUID = 7507671730562402791L;
    /**
     * 代销商编号（渠道代销商/投注站点）
     */
    private String dealer_id;
    /**
     * 角色编号
     */
    private String role_id;
    /**
     * 登录用户编号
     */
    private String custom_id;
    /**
     * 登录用户名
     */
    private String custom_name;
    /**
     * 登录终端编号
     */
    private String login_terminal;
    /**
     * 会话密钥
     */
    private byte[] session_key;//
    /**
     * 会话密钥初始化向量
     */
    private byte[] session_inv;

    private Integer logoutTyoe;  //登出类型

    private Date loginTime; //登录时间
    private Date loginOutTime; //登出时间

    /**
     * 消息包计数器
     */
    private Integer counter;

    /**
     * 协议编号
     */
    private Integer actionId;

    /**
     * 消息发送时间
     */
    private Date transTime;

    public byte[] getSession_inv() {
        return session_inv;
    }

    public void setSession_inv(byte[] session_inv) {
        this.session_inv = session_inv;
    }

    /**
     * 登录时间
     *
     * @return
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 登录时间
     *
     * @param loginTime
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 登出时间
     *
     * @return
     */
    public Date getLoginOutTime() {
        return loginOutTime;
    }

    /**
     * 登出时间
     *
     * @param loginOutTime
     */
    public void setLoginOutTime(Date loginOutTime) {
        this.loginOutTime = loginOutTime;
    }

    /**
     * 代销商编号
     *
     * @return
     */
    public String getDealer_id() {
        return dealer_id;
    }

    /**
     *
     * @return
     */
    public String getRole_id() {
        return role_id;
    }

    /**
     *
     * @return
     */
    public String getCustom_id() {
        return custom_id;
    }

    /**
     *
     * @return
     */
    public String getCustom_name() {
        return custom_name;
    }

    /**
     *
     * @return
     */
    public String getLogin_terminal() {
        return login_terminal;
    }

    /**
     *
     * @return
     */
    public byte[] getSession_key() {
        return session_key;
    }

    /**
     * 代销商编号
     *
     * @param dealer_id
     */
    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }

    /**
     *
     * @param role_id
     */
    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    /**
     *
     * @param custom_id
     */
    public void setCustom_id(String custom_id) {
        this.custom_id = custom_id;
    }

    /**
     *
     * @param custom_name
     */
    public void setCustom_name(String custom_name) {
        this.custom_name = custom_name;
    }

    /**
     *
     * @param login_terminal
     */
    public void setLogin_terminal(String login_terminal) {
        this.login_terminal = login_terminal;
    }

    /**
     *
     * @param session_key
     */
    public void setSession_key(byte[] session_key) {
        this.session_key = session_key;
    }

    /**
     * @return the logoutTyoe
     */
    public Integer getLogoutTyoe() {
        return logoutTyoe;
    }

    /**
     *
     * @param logoutTyoe
     */
    public void setLogoutTyoe(Integer logoutTyoe) {
        this.logoutTyoe = logoutTyoe;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

}
