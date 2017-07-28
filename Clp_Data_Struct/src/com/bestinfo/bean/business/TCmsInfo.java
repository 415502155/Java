package com.bestinfo.bean.business;

import java.io.Serializable;
import java.util.Date;

/**
 * 内容管理
 *
 * @author hhhh
 */
public class TCmsInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5414763593469871831L;
	private Integer cms_id;//内容编号
    private Date release_time;//发布时间
    private Integer cms_type;//内容类型
    private String cms_title;//内容标题
    private Integer bulletin_len;//内容长度
    private String cms_filename;//内容文件名
    private String keys;//关键字
    private Integer work_status;//使用状态
    private Date deadline;//有效时间 
    private String version;//内容版本 

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
     * @return the release_time
     */
    public Date getRelease_time() {
        return release_time;
    }

    /**
     * @param release_time the release_time to set
     */
    public void setRelease_time(Date release_time) {
        this.release_time = release_time;
    }

    /**
     * @return the cms_type
     */
    public Integer getCms_type() {
        return cms_type;
    }

    /**
     * @param cms_type the cms_type to set
     */
    public void setCms_type(Integer cms_type) {
        this.cms_type = cms_type;
    }

    /**
     * @return the cms_title
     */
    public String getCms_title() {
        return cms_title;
    }

    /**
     * @param cms_title the cms_title to set
     */
    public void setCms_title(String cms_title) {
        this.cms_title = cms_title;
    }

    /**
     * @return the bulletin_len
     */
    public Integer getBulletin_len() {
        return bulletin_len;
    }

    /**
     * @param bulletin_len the bulletin_len to set
     */
    public void setBulletin_len(Integer bulletin_len) {
        this.bulletin_len = bulletin_len;
    }

    /**
     * @return the cms_filename
     */
    public String getCms_filename() {
        return cms_filename;
    }

    /**
     * @param cms_filename the cms_filename to set
     */
    public void setCms_filename(String cms_filename) {
        this.cms_filename = cms_filename;
    }

    /**
     * @return the keys
     */
    public String getKeys() {
        return keys;
    }

    /**
     * @param keys the keys to set
     */
    public void setKeys(String keys) {
        this.keys = keys;
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

    /**
     * @return the deadline
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * @param deadline the deadline to set
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }
    
}