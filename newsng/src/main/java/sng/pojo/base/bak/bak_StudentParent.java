package sng.pojo.base.bak;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student_parent")
public class bak_StudentParent implements Serializable {
	@Id
	@Column(name="stu_parent_id")
	@GeneratedValue
    private Integer stuParentId;

	@Column(name="parent_id")
    private Integer parentId;

	@Column(name="stu_id")
    private Integer studId;

	@Column(name="is_main")
    private Integer isMain;

    private Integer relation;

    @Column(name="is_del")
    private Integer isDel;

    @Column(name="insert_time")
    private Date insertTime;

    @Column(name="update_time")
    private Date updateTime;

    @Column(name="del_time")
    private Date delTime;

    private String creater;

    public Integer getStuParentId() {
        return stuParentId;
    }

    public void setStuParentId(Integer stuParentId) {
        this.stuParentId = stuParentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getStudId() {
        return studId;
    }

    public void setStudId(Integer studId) {
        this.studId = studId;
    }

    public Integer getIsMain() {
        return isMain;
    }

    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
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