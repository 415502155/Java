package sng.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 凭证模板表 
 * Title: Vouchermodel 
 * Description: 凭证模板详细信息表 
 * Company: 世纪伟业
 */
@Entity
@Table(name = "vouchermodel")
public class Vouchermodel implements Serializable {

	private static final long serialVersionUID = -1266534923446197481L;

	/**
	 * 凭证模板表主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer voucher_model_id;
	/**
	 * 机构主键
	 */
	private Integer org_id;
	/**
	 * 凭证名称
	 */
	private String voucher_name;
	/***
	 * 凭证内容（json字符串，格式如：（NO1{x：y：val：} ，z1{x：y：val：}））
	 */
	private String voucher_content;
	/***
	 * 编码格式
	 */
	private String serial_number_format;
	/***
	 * 背景长度
	 */
	private Integer background_length;
	/***
	 * 背景宽度
	 */
	private Integer background_width;
	/***
	 * 凭证图片地址
	 */
	private String img_url;
	/***
	 * 新增时间
	 */
	private Date insert_time;
	/***
	 * 修改时间
	 */
	private Date update_time;
	/***
	 * 删除时间
	 */
	private Date del_time;
	/***
	 * 是否删除 (0:否；1：是)
	 */
	private Integer is_del;

	public Integer getVoucher_model_id() {
		return voucher_model_id;
	}

	public void setVoucher_model_id(Integer voucher_model_id) {
		this.voucher_model_id = voucher_model_id;
	}

	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

	public String getVoucher_name() {
		return voucher_name;
	}

	public void setVoucher_name(String voucher_name) {
		this.voucher_name = voucher_name;
	}

	public String getVoucher_content() {
		return voucher_content;
	}

	public void setVoucher_content(String voucher_content) {
		this.voucher_content = voucher_content;
	}

	public String getSerial_number_format() {
		return serial_number_format;
	}

	public void setSerial_number_format(String serial_number_format) {
		this.serial_number_format = serial_number_format;
	}

	public Integer getBackground_length() {
		return background_length;
	}

	public void setBackground_length(Integer background_length) {
		this.background_length = background_length;
	}

	public Integer getBackground_width() {
		return background_width;
	}

	public void setBackground_width(Integer background_width) {
		this.background_width = background_width;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public Date getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Date getDel_time() {
		return del_time;
	}

	public void setDel_time(Date del_time) {
		this.del_time = del_time;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
