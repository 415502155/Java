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
 * 通知记录信息 
 * Title: Notice 
 * Description:记录通知记录
 * Company: 世纪伟业
 * @author 马国庆
 * @date 2018年10月23日上午10:51
 */
@Entity
@Table(name = "notice")
public class Notice implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6964052652781406544L;

	public Notice() {
		super();
	}

	public Notice(Integer noticeId, Integer orgId, Integer camId, String content, Integer techId, Integer num,
			Integer classes, Integer status, Date publishTime, Integer isDel, Date insertTime, Date updateTime,
			Date delTime) {
		super();
		this.noticeId = noticeId;
		this.orgId = orgId;
		this.camId = camId;
		this.content = content;
		this.techId = techId;
		this.num = num;
		this.classes = classes;
		this.status = status;
		this.publishTime = publishTime;
		this.isDel = isDel;
		this.insertTime = insertTime;
		this.updateTime = updateTime;
		this.delTime = delTime;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "notice_id")
	private Integer noticeId; // 通知记录id

	@Column(name = "org_id")
	private Integer orgId;// 机构ID

	@Column(name = "cam_id")
	private Integer camId;// 校区ID

	@Column(name = "content")
	private String content;// 消息内容

	@Column(name = "tech_id")
	private Integer techId;// 发布人

	@Column(name = "num")
	private Integer num;// 发布人数

	@Column(name = "classes")
	private Integer classes;// 班级数

	@Column(name = "status")
	private Integer status;// 通知状态（发送、撤回）

	@Column(name = "publish_time")
	private Date publishTime;// 发布时间

	@Column(name = "is_del")
	private Integer isDel;// 删除标记 是否删除(0:否；1：是)

	@Column(name = "insert_time")
	private Date insertTime;// 插入时间

	@Column(name = "update_time")
	private Date updateTime;// 更新时间

	@Column(name = "del_time")
	private Date delTime;// 删除时间

	public Integer getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public Integer getTechId() {
		return techId;
	}

	public void setTechId(Integer techId) {
		this.techId = techId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getClasses() {
		return classes;
	}

	public void setClasses(Integer classes) {
		this.classes = classes;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
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