package com.bestinfo.bean.encoding;

import java.io.Serializable;

public class CityInfo implements Serializable {

    private static final long serialVersionUID = -1789745495505904968L;
    private Integer province_id;//省编号
    private Integer city_id;//地市编号
    private String city_name;//地市名称
    private Integer long_city_id;//
    private String city_address;//中心地址
    private String city_phone;//联系电话
    private String terminal_password;//终端维护口令
    private Integer work_status;//工作状态

    public Integer getProvince_id() {
        return province_id;
    }

    public void setProvince_id(Integer province_id) {
        this.province_id = province_id;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public Integer getLong_city_id() {
        return long_city_id;
    }

    public void setLong_city_id(Integer long_city_id) {
        this.long_city_id = long_city_id;
    }

    public String getCity_address() {
        return city_address;
    }

    public void setCity_address(String city_address) {
        this.city_address = city_address;
    }

    public String getCity_phone() {
        return city_phone;
    }

    public void setCity_phone(String city_phone) {
        this.city_phone = city_phone;
    }

    public String getTerminal_password() {
        return terminal_password;
    }

    public void setTerminal_password(String terminal_password) {
        this.terminal_password = terminal_password;
    }

    public Integer getWork_status() {
        return work_status;
    }

    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }

}
