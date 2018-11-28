package sng.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "import_record")
public class ImportRecord implements Serializable {
	@Id
	@Column(name="im_record_id")
	@GeneratedValue
    private Integer imRecordId;

	@Column(name="org_id")
    private Integer orgId;

	@Column(name="cam_id")
    private Integer camId;

    private Integer result;

    @Column(name="total_count")
    private Integer totalCount;

    @Column(name="correct_count")
    private Integer correctCount;

    @Column(name="error_count")
    private Integer errorCount;

    @Column(name="source_file_path")
    private String sourceFilePath;

    @Column(name="error_file_path")
    private String errorFilePath;

    @Column(name="is_del")
    private Integer isDel;

    @Column(name="insert_time")
    private Date insertTime;

    @Column(name="update_time")
    private Date updateTime;

    @Column(name="del_time")
    private Date delTime;

    private String creater;

    public Integer getImRecordId() {
        return imRecordId;
    }

    public void setImRecordId(Integer imRecordId) {
        this.imRecordId = imRecordId;
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

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(Integer correctCount) {
        this.correctCount = correctCount;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public void setSourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath == null ? null : sourceFilePath.trim();
    }

    public String getErrorFilePath() {
        return errorFilePath;
    }

    public void setErrorFilePath(String errorFilePath) {
        this.errorFilePath = errorFilePath == null ? null : errorFilePath.trim();
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