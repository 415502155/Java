package com.bestinfo.bean.encoding;

import java.io.Serializable;

/**
 *
 * @author hhhh
 */
public class DistrictInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8309053016654952860L;
	private Integer province_id;//省编号
    private Integer city_id;//地市编号
    private Integer district_id;//区县编号
    private String district_name;//区县名称
    private String district_address;//中心地址
    private String district_phone;//联系电话
    private Integer district_work_status;//工作状态

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

    public Integer getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(Integer district_id) {
        this.district_id = district_id;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getDistrict_address() {
        return district_address;
    }

    public void setDistrict_address(String district_address) {
        this.district_address = district_address;
    }

    public String getDistrict_phone() {
        return district_phone;
    }

    public void setDistrict_phone(String district_phone) {
        this.district_phone = district_phone;
    }

    public Integer getDistrict_work_status() {
        return district_work_status;
    }

    public void setDistrict_work_status(Integer district_work_status) {
        this.district_work_status = district_work_status;
    }
    
}
