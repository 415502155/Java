/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.bean.fs;

import java.io.Serializable;

/**
 * 对应表T_monserver_info
 * @author YangRong
 */
public class MonServerInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -419636549976946899L;
	private Integer server_id;
    private String server_name;
    private String server_ip;
    private Integer server_port;
    private Integer server_type;
    private Integer monitor_port;
    private String monitor_command;
    private Integer work_status;
    private String monitor_type_name;
    private String server_type_name;
    private Integer server_status;

    public Integer getServer_status() {
        return server_status;
    }

    public void setServer_status(Integer server_status) {
        this.server_status = server_status;
    }
    

    public Integer getServer_id() {
        return server_id;
    }

    public void setServer_id(Integer server_id) {
        this.server_id = server_id;
    }

    public String getServer_name() {
        return server_name;
    }

    public void setServer_name(String server_name) {
        this.server_name = server_name;
    }

    public String getServer_ip() {
        return server_ip;
    }

    public void setServer_ip(String server_ip) {
        this.server_ip = server_ip;
    }

    public Integer getServer_port() {
        return server_port;
    }

    public void setServer_port(Integer server_port) {
        this.server_port = server_port;
    }

    public Integer getServer_type() {
        return server_type;
    }

    public void setServer_type(Integer server_type) {
        this.server_type = server_type;
    }

    public Integer getMonitor_port() {
        return monitor_port;
    }

    public void setMonitor_port(Integer monitor_port) {
        this.monitor_port = monitor_port;
    }

    public String getMonitor_command() {
        return monitor_command;
    }

    public void setMonitor_command(String monitor_command) {
        this.monitor_command = monitor_command;
    }

    public Integer getWork_status() {
        return work_status;
    }

    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }

    public String getMonitor_type_name() {
        return monitor_type_name;
    }

    public void setMonitor_type_name(String monitor_type_name) {
        this.monitor_type_name = monitor_type_name;
    }

    public String getServer_type_name() {
        return server_type_name;
    }

    public void setServer_type_name(String server_type_name) {
        this.server_type_name = server_type_name;
    }
    
    
    
}
