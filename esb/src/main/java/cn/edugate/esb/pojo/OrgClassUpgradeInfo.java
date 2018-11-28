package cn.edugate.esb.pojo;

public class OrgClassUpgradeInfo {

	// 机构ID
	private Integer org_id;

	// 机构中文名称
	private String org_name_cn;

	// 英文名称
	private String org_name_en;

	// 中文简称
	private String org_name_s_cn;

	// 英文简称
	private String org_name_s_en;

	// 省份
	private String province;

	// 城市
	private String city;

	// 区县
	private String district;

	// 年级类型
	private String gradeType;

	// 升级状态
	private String upgradeStatus;

	// 升级时间
	private String upgradeDate;
	
	

	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

	public String getOrg_name_cn() {
		return org_name_cn;
	}

	public void setOrg_name_cn(String org_name_cn) {
		this.org_name_cn = org_name_cn;
	}

	public String getOrg_name_en() {
		return org_name_en;
	}

	public void setOrg_name_en(String org_name_en) {
		this.org_name_en = org_name_en;
	}

	public String getOrg_name_s_cn() {
		return org_name_s_cn;
	}

	public void setOrg_name_s_cn(String org_name_s_cn) {
		this.org_name_s_cn = org_name_s_cn;
	}

	public String getOrg_name_s_en() {
		return org_name_s_en;
	}

	public void setOrg_name_s_en(String org_name_s_en) {
		this.org_name_s_en = org_name_s_en;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getGradeType() {
		return gradeType;
	}

	public void setGradeType(String gradeType) {
		this.gradeType = gradeType;
	}

	public String getUpgradeStatus() {
		return upgradeStatus;
	}

	public void setUpgradeStatus(String upgradeStatus) {
		this.upgradeStatus = upgradeStatus;
	}

	public String getUpgradeDate() {
		return upgradeDate;
	}

	public void setUpgradeDate(String upgradeDate) {
		this.upgradeDate = upgradeDate;
	}

}
