package cn.edugate.esb.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.util.Util;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "organization", catalog = "`edugate_base`")
public class Organization {

	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

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

	// 地区
	private String area;

	// 类型（0学校，2培训机构，3教育局）
	private Integer type;

	// logo
	private String logo;
	
	/**
	 * 欢迎图
	 */
	private String welcome;
	/**
	 * 欢迎图地址
	 */
	private String welcomeUrl;

	// 背景图
	private String bg;

	// 样式
	private String css;

	// 1平铺格子 2树形菜单
	private Integer layout;

	private Integer sms_total;

	private Integer space_size;

	// 属性值JSON
	private String attribute;

	// 是否有校区（0 否 1是）
	private Integer is_campus;
	
	// 机构地址
	private String address;
	
	// 机构网址
	private String website;
	
	// 邮政编码
	private String postcode;
	
	// 联系人
	private String contact;
	
	// 联系人手机号码
	private String contact_mobile;
	
	// 备注
	private String remark;
	
	// 版权信息
	private String copyright_info;
	
	// 公安备案
	private String police_record;
	
	// 公安备案地址
	private String police_record_url;
	
	// ICP备案
	private String ICP_record;
	
	// ICP备案地址
	private String ICP_record_url;

	// 创建时间
	private Date insert_time;

	// 修改时间
	private Date update_time;

	// 是否删除
	private Boolean is_del;

	// 删除时间
	private Date del_time;

	// 上级机构数（页面显示用）
	@Transient
	private int parentOrgNum;

	// 下级机构数（页面显示用）
	@Transient
	private int childOrgNum;
	
	@Transient
	private List<Campus> campusList;
	
	@Transient
	private List<Grade> gradeList;
	
	@Transient
	private List<Organization> lowerOrgList;
	
	@Transient
	private String logoUrl;
	
	/**
	 * 技术支持
	 */
	private String support;
	/**
	 * 提示信息
	 */
	private String info;
	
	private BigInteger schoolType;
	@Transient
	public BigInteger getSchoolType() {
		return schoolType;
	}
	public void setSchoolType(BigInteger schoolType) {
		this.schoolType = schoolType;
	}

	@Transient
	public String getLogoUrl() {
		if (util!=null&&StringUtils.isNotBlank(this.logo)) {
			logoUrl = util.getPathByPicName(this.logo);
		} else {
			logoUrl = "";
		}
		return logoUrl;
	}
	
	@Transient
	private List<String> bgUrlList;
	
	@Transient
	public List<String> getBgUrlList() {
		bgUrlList = new ArrayList<String>();
		if (StringUtils.isNotBlank(this.bg)) {
			String[] bgNameArray = this.bg.split(";");
			for (String bgName : bgNameArray) {
				String bgUrl = util!=null?util.getPathByPicName(bgName):"";
				bgUrlList.add(bgUrl);
			}
		}
		return bgUrlList;
	}


	
	
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getOrg_id() {
		return org_id;
	}
//	@Transient
//	public String getLogoUrl() {
//		return logoUrl;
//	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
//	@Transient
//	public List<String> getBgUrlList() {
//		return bgUrlList;
//	}

	public void setBgUrlList(List<String> bgUrlList) {
		this.bgUrlList = bgUrlList;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	// 页面显示用
	@Transient
	@JsonIgnore
	public String getArea4Display() {
		String[] areaArray = this.area.split("_");
		if (areaArray[2] == null || "null".equals(areaArray[2])) {
			return areaArray[0] + "-" + areaArray[1];
		} else {
			return areaArray[0] + "-" + areaArray[1] + "-" + areaArray[2];
		}
	}
	
	public void setArea4Display(String area) {
		
	}

	public Integer getType() {
		return type;
	}

	// 页面显示用
	@Transient
	@JsonIgnore
	public String getType4Display() {
		if (this.type == 0) {
			return "学校";
		} else if (this.type == 2) {
			return "培训机构";
		} else if (this.type == 3) {
			return "教育局";
		} else {
			return "";
		}
	}
	
	public void setType4Display(String type) {
		
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getBg() {
		return bg;
	}

	public void setBg(String bg) {
		this.bg = bg;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public Boolean getIs_del() {
		return is_del;
	}

	public void setIs_del(Boolean is_del) {
		this.is_del = is_del;
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

	public Integer getSms_total() {
		return sms_total;
	}

	public void setSms_total(Integer sms_total) {
		this.sms_total = sms_total;
	}

	public Integer getSpace_size() {
		return space_size;
	}

	public void setSpace_size(Integer space_size) {
		this.space_size = space_size;
	}

	public Integer getLayout() {
		return layout;
	}

	public void setLayout(Integer layout) {
		this.layout = layout;
	}

	public Integer getIs_campus() {
		return is_campus;
	}

	public void setIs_campus(Integer is_campus) {
		this.is_campus = is_campus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContact_mobile() {
		return contact_mobile;
	}

	public void setContact_mobile(String contact_mobile) {
		this.contact_mobile = contact_mobile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCopyright_info() {
		return copyright_info;
	}

	public void setCopyright_info(String copyright_info) {
		this.copyright_info = copyright_info;
	}

	public String getPolice_record() {
		return police_record;
	}

	public void setPolice_record(String police_record) {
		this.police_record = police_record;
	}

	public String getPolice_record_url() {
		return police_record_url;
	}

	public void setPolice_record_url(String police_record_url) {
		this.police_record_url = police_record_url;
	}

	public String getICP_record() {
		return ICP_record;
	}

	public void setICP_record(String iCP_record) {
		ICP_record = iCP_record;
	}

	public String getICP_record_url() {
		return ICP_record_url;
	}

	public void setICP_record_url(String iCP_record_url) {
		ICP_record_url = iCP_record_url;
	}

	@Transient
	public int getParentOrgNum() {
		return parentOrgNum;
	}

	public void setParentOrgNum(int parentOrgNum) {
		this.parentOrgNum = parentOrgNum;
	}

	@Transient
	public int getChildOrgNum() {
		return childOrgNum;
	}

	public void setChildOrgNum(BigInteger childOrgNum) {
		this.childOrgNum = childOrgNum.intValue();
	}
	
	@Transient
	public List<Campus> getCampusList() {
		return campusList;
	}

	public void setCampusList(List<Campus> campusList) {
		this.campusList = campusList;
	}

	@Transient
	public List<Grade> getGradeList() {
		return gradeList;
	}

	public void setGradeList(List<Grade> gradeList) {
		this.gradeList = gradeList;
	}
	
	@Transient
	public List<Organization> getLowerOrgList() {
		return lowerOrgList;
	}

	public void setLowerOrgList(List<Organization> lowerOrgList) {
		this.lowerOrgList = lowerOrgList;
	}
	
	private Boolean checked;

	@Transient
	@JsonIgnore
	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public String getWelcome() {
		return welcome;
	}
	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}
	@Transient
	public String getWelcomeUrl() {
		if (util!=null&&this.welcome!=null&&!"".equals(this.welcome)) {
			this.welcomeUrl = util.getPathByPicName(this.welcome);
		} else {
			this.welcomeUrl = "";
		}
		return welcomeUrl;
	}
	public void setWelcomeUrl(String welcomeUrl) {
		this.welcomeUrl = welcomeUrl;
	}
	public String getSupport() {
		return support;
	}
	public void setSupport(String support) {
		this.support = support;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
