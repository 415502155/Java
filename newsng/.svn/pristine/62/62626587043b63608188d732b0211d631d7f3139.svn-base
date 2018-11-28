package sng.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "voucher")
public class Voucher implements Serializable {
	@Id
	@Column(name="voucher_id")
	@GeneratedValue
    private Integer voucherId;

	@Column(name="org_id")
    private Integer orgId;

	@Column(name="cam_id")
    private Integer camId;

	@Column(name="stu_class_id")
    private Integer stuClassId;

	@Column(name="voucher_model_id")
    private Integer voucherModelId;

	@Column(name="is_del")
    private Integer isDel;

	@Column(name="insert_time")
    private Date insertTime;

	@Column(name="update_time")
    private Date updateTime;

	@Column(name="del_time")
    private Date delTime;

    private String creater;

    private String content;

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getCamId() {
        return camId;
    }

    public void setCamId(Integer camId) {
        this.camId = camId;
    }

    public Integer getStuClassId() {
        return stuClassId;
    }

    public void setStuClassId(Integer stuClassId) {
        this.stuClassId = stuClassId;
    }

    public Integer getVoucherModelId() {
        return voucherModelId;
    }

    public void setVoucherModelId(Integer voucherModelId) {
        this.voucherModelId = voucherModelId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}