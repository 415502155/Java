package sng.pojo.base.bak;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "teacher")
public class bak_Teacher implements Serializable {
	@Id
	@Column(name="tech_id")
	@GeneratedValue
    private Integer techId;

	@Column(name="org_id")
    private Integer orgId;

	@Column(name="cooperative_id")
    private Integer cooperativeId;

	@Column(name="teacher_name")
    private String teacherName;

    private String type;

    private String mobile;

    private Integer status;

    @Column(name="wx_id")
    private String wxId;

    @Column(name="wx_head")
    private String wxHead;

    @Column(name="wx_name")
    private String wxName;

    private String note;

    @Column(name="is_del")
    private Integer isDel;

    @Column(name="insert_time")
    private Date insertTime;

    @Column(name="update_time")
    private Date updateTime;

    @Column(name="del_time")
    private Date delTime;

    private String creater;

    public Integer getTechId() {
        return techId;
    }

    public void setTechId(Integer techId) {
        this.techId = techId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getCooperativeId() {
        return cooperativeId;
    }

    public void setCooperativeId(Integer cooperativeId) {
        this.cooperativeId = cooperativeId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName == null ? null : teacherName.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId == null ? null : wxId.trim();
    }

    public String getWxHead() {
        return wxHead;
    }

    public void setWxHead(String wxHead) {
        this.wxHead = wxHead == null ? null : wxHead.trim();
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName == null ? null : wxName.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getDelTime() {
        return delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }
}