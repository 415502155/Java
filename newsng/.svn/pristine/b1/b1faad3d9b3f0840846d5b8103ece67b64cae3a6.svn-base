package sharding.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sng_notice")
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;

	private Integer org_id;

	/*
	 * 发送类型：0-学生通知；1-教师通知
	 */
	private Integer type;
	
	/*
	 * 状态（0：待发送；1：已撤回；2：已发送）
	 */
	private Integer status;

	// 发送人员的userid
	private Integer sender_id;

	private String sender_name;
	
	private String target;

	private Integer target_status;
	
	private String title;
	
	private String content;
	
	private String media;
	
	private Integer total_num;
	
	private Integer total_class_num;
	
	private Timestamp insert_time;
	
	private Timestamp update_time;
	
	private Integer is_del;
	
	private Timestamp delete_time;
	
	
	
	//@Transient
	//private BigInteger ORDER_BY_DERIVED_0;
	
	//@Transient
	//private Date GROUP_BY_DERIVED_0;
	
	@Transient
	private String id_str;
	
	
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSender_id() {
		return sender_id;
	}

	public void setSender_id(Integer sender_id) {
		this.sender_id = sender_id;
	}

	public String getSender_name() {
		return sender_name;
	}

	public void setSender_name(String sender_name) {
		this.sender_name = sender_name;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getTarget_status() {
		return target_status;
	}

	public void setTarget_status(Integer target_status) {
		this.target_status = target_status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public Integer getTotal_num() {
		return total_num;
	}

	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}

	public Integer getTotal_class_num() {
		return total_class_num;
	}

	public void setTotal_class_num(Integer total_class_num) {
		this.total_class_num = total_class_num;
	}

	public Timestamp getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(Timestamp insert_time) {
		this.insert_time = insert_time;
	}

	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public Timestamp getDelete_time() {
		return delete_time;
	}

	public void setDelete_time(Timestamp delete_time) {
		this.delete_time = delete_time;
	}
	
	/*public BigInteger getORDER_BY_DERIVED_0() {
    	return ORDER_BY_DERIVED_0;
    }
    
    public void setORDER_BY_DERIVED_0(BigInteger oRDER_BY_DERIVED_0) {
    	ORDER_BY_DERIVED_0 = oRDER_BY_DERIVED_0;
    }
    
    public Date getGROUP_BY_DERIVED_0() {
    	return GROUP_BY_DERIVED_0;
    }
    
    public void setGROUP_BY_DERIVED_0(Date gROUP_BY_DERIVED_0) {
    	GROUP_BY_DERIVED_0 = gROUP_BY_DERIVED_0;
    }*/


	public String getId_str() {
		if (this.id != null && this.id.longValue() > 0) {
			return id.toString();
		} else {
			return "";
		}
	}

}
