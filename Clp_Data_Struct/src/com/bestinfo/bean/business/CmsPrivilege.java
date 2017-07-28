package com.bestinfo.bean.business;

import java.io.Serializable;

/**
 * 内容发布权限
 *
 * @author 
 */
public class CmsPrivilege implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6465577454550027024L;
	private Integer cms_id;//内容编号
    private String receiving_object;//发布对象
    private Integer cms_range;//发布范围
    private Integer work_status;//使用状态

    /**
     * @return the cms_id
     */
    public Integer getCms_id() {
        return cms_id;
    }

    /**
     * @param cms_id the cms_id to set
     */
    public void setCms_id(Integer cms_id) {
        this.cms_id = cms_id;
    }

    /**
     * @return the receiving_object
     */
    public String getReceiving_object() {
        return receiving_object;
    }

    /**
     * @param receiving_object the receiving_object to set
     */
    public void setReceiving_object(String receiving_object) {
        this.receiving_object = receiving_object;
    }

    /**
     * @return the cms_range
     */
    public Integer getCms_range() {
        return cms_range;
    }

    /**
     * @param cms_range the cms_range to set
     */
    public void setCms_range(Integer cms_range) {
        this.cms_range = cms_range;
    }

    /**
     * @return the work_status
     */
    public Integer getWork_status() {
        return work_status;
    }

    /**
     * @param work_status the work_status to set
     */
    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }

}
