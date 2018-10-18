/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.bean.fs;

import java.io.Serializable;

/**
 *
 * @author YangRong
 */
public class MonServerType implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6733990502980351694L;
    private Integer server_type;
    private String type_name;
    private Integer work_status;

    public Integer getServer_type() {
        return server_type;
    }

    public void setServer_type(Integer server_type) {
        this.server_type = server_type;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public Integer getWork_status() {
        return work_status;
    }

    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }

}
