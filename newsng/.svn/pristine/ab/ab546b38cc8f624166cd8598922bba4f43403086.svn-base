package sng.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 通知明细信息实体 
 * Title: NoticeDetail 
 * Description:记录通知明细
 *  Company: 世纪伟业
 * @author 马国庆
 * @date 2018年10月23日上午10:51
 */
@Entity
@Table(name = "notice_detail")
public class NoticeDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8616529412917439002L;

	public NoticeDetail() {
		super();
	}

	public NoticeDetail(Integer ndId, Integer nId, Integer clasId, Integer studId, Integer isDel, Date insertTime,
			Date updateTime, Date delTime) {
		super();
		this.ndId = ndId;
		this.nId = nId;
		this.clasId = clasId;
		this.studId = studId;
		this.isDel = isDel;
		this.insertTime = insertTime;
		this.updateTime = updateTime;
		this.delTime = delTime;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "nd_id")
	private Integer ndId; // 通知明细id

	@Column(name = "n_id")
	private Integer nId; // 通知记录ID(通知记录表)

	@Column(name = "clas_id")
	private Integer clasId;// 班级id

	@Column(name = "stud_id")
	private Integer studId;// 学员id

	@Column(name = "is_del")
	private Integer isDel;// 删除标记 是否删除(0:否；1：是)

	@Column(name = "insert_time")
	private Date insertTime;// 插入时间

	@Column(name = "update_time")
	private Date updateTime;// 更新时间

	@Column(name = "del_time")
	private Date delTime;// 删除时间

	public Integer getNdId() {
		return ndId;
	}

	public void setNdId(Integer ndId) {
		this.ndId = ndId;
	}

	public Integer getnId() {
		return nId;
	}

	public void setnId(Integer nId) {
		this.nId = nId;
	}

	public Integer getClasId() {
		return clasId;
	}

	public void setClasId(Integer clasId) {
		this.clasId = clasId;
	}

	public Integer getStudId() {
		return studId;
	}

	public void setStudId(Integer studId) {
		this.studId = studId;
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
}