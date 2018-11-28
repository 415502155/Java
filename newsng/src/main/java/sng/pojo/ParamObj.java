package sng.pojo;

import java.util.List;

/**
 * 请求参数对象
 *
 * @author magq
 */
public class ParamObj {
	// 校区主键
	private Integer cam_id;
	private String cam_name;
	// 机构主键
	private Integer org_id;
	// 合作机构
	private Integer cooperative_id;
	private String cooperative_name;

	private Integer user_id; // 用户ID

	// ----------------学期 begin------------------------------------
	private Integer term_id;// 学期id
	private String term_name;// 学期名称
	private Integer term_type;// 学期类型
	private String cur_year;// 所属年份
	// -----------------学期 end------------------------------------

	// ----------------科目类目begin-------------------------------
	private Integer subject_id;// 科目id
	private String subject_name;// 科目名称
	private Integer category_id; // 类目ID
	private String category_name;// 类目名称

	// ----------------科目类目begin-------------------------------

	// 教室
	private Integer classroom_id;
	private String classroom_name;// 教室名称
	private String building; //所在教学楼
	private String floor; //所在楼层

	private Integer clas_id; // 班级ID

	// ---------------------教师begin----------------------
	private Integer tech_id; // 教师id
	private String tech_name; // 教师名称
	private String user_mobile;// 电话
	private Integer tech_type;// 教师类型
	private String tech_note;// 备注

	// ---------------------教师end----------------------

	// ---------------------学员begin----------------------
	private String stud_id; // 学员ids
	private Integer is_main;// 是否主家长 0否 1是
	// ---------------------学员end----------------------

	// ----------------分页begin------------------------------

	private Integer start = 0;
	private Integer page = 1;// 当前页
	private Integer limit = 20;// 每页多少条
	private String orderType;// 排序类型
	private String orderContent;// 排序内容
	private boolean isNeedCount = true;// 是否分页
	private boolean isBlurQuery; // 是否模糊查询

	// ----------------分页end------------------------------

	private Integer isDel; // 删除
	private Integer status;// 状态
	private String isQueryConditions; // 'true' 'false' //针对搜索条件科目类目联动而定义此字段
	private String key;
	private String val;
	private List list;
	private Integer size;
	// ================================================================================================================

	
	public String getCur_year() {
		return cur_year;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getCam_name() {
		return cam_name;
	}

	public void setCam_name(String cam_name) {
		this.cam_name = cam_name;
	}

	public void setCur_year(String cur_year) {
		this.cur_year = cur_year;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCooperative_name() {
		return cooperative_name;
	}

	public void setCooperative_name(String cooperative_name) {
		this.cooperative_name = cooperative_name;
	}

	public Integer getCam_id() {
		return cam_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public void setCam_id(Integer cam_id) {
		this.cam_id = cam_id;
	}

	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

	public Integer getTerm_id() {
		return term_id;
	}

	public String getTerm_name() {
		return term_name;
	}

	public void setTerm_name(String term_name) {
		this.term_name = term_name;
	}

	public Integer getTerm_type() {
		return term_type;
	}

	public void setTerm_type(Integer term_type) {
		this.term_type = term_type;
	}

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public void setTerm_id(Integer term_id) {
		this.term_id = term_id;
	}

	public Integer getClassroom_id() {
		return classroom_id;
	}

	public void setClassroom_id(Integer classroom_id) {
		this.classroom_id = classroom_id;
	}

	public String getClassroom_name() {
		return classroom_name;
	}

	public void setClassroom_name(String classroom_name) {
		this.classroom_name = classroom_name;
	}

	public Integer getTech_id() {
		return tech_id;
	}

	public void setTech_id(Integer tech_id) {
		this.tech_id = tech_id;
	}

	public String getTech_name() {
		return tech_name;
	}

	public void setTech_name(String tech_name) {
		this.tech_name = tech_name;
	}

	public String getUser_mobile() {
		return user_mobile;
	}

	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}

	public Integer getTech_type() {
		return tech_type;
	}

	public void setTech_type(Integer tech_type) {
		this.tech_type = tech_type;
	}

	public String getTech_note() {
		return tech_note;
	}

	public void setTech_note(String tech_note) {
		this.tech_note = tech_note;
	}

	public Integer getCooperative_id() {
		return cooperative_id;
	}

	public void setCooperative_id(Integer cooperative_id) {
		this.cooperative_id = cooperative_id;
	}

	public Integer getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(Integer subject_id) {
		this.subject_id = subject_id;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		if (page != null) {
			this.page = page;
		} else {
			this.page = 1;
		}

	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		if (limit != null) {
			this.limit = limit;
		} else {
			this.limit = 20;
		}

	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderContent() {
		return orderContent;
	}

	public void setOrderContent(String orderContent) {
		this.orderContent = orderContent;
	}

	public boolean isNeedCount() {
		return isNeedCount;
	}

	public void setNeedCount(boolean isNeedCount) {
		this.isNeedCount = isNeedCount;
	}

	/**
	 * 是否支持模糊查询
	 *
	 * @return
	 */
	public boolean isBlurQuery() {
		return isBlurQuery;
	}

	public void setBlurQuery(boolean isBlurQuery) {
		this.isBlurQuery = isBlurQuery;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		if (isDel == null) {
			this.isDel = 0;
		} else {
			this.isDel = isDel;
		}

	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStud_id() {
		return stud_id;
	}

	public void setStud_id(String stud_id) {
		this.stud_id = stud_id;
	}

	public Integer getIs_main() {
		return is_main;
	}

	public void setIs_main(Integer is_main) {
		if (is_main != null) {
			this.is_main = is_main;
		} else {
			this.is_main = 1;
		}

	}

	public Integer getClas_id() {
		return clas_id;
	}

	public void setClas_id(Integer clas_id) {
		this.clas_id = clas_id;
	}

	public String getIsQueryConditions() {
		return isQueryConditions;
	}

	public void setIsQueryConditions(String isQueryConditions) {
		this.isQueryConditions = isQueryConditions;
	}

	@Override
	public String toString() {
		return "ParamObj{" + "cam_id=" + cam_id + ", org_id=" + org_id + ", cooperative_id=" + cooperative_id
				+ ", cooperative_name='" + cooperative_name + '\'' + ", user_id=" + user_id + ", term_id=" + term_id
				+ ", term_name='" + term_name + '\'' + ", term_type=" + term_type + ", cur_year='" + cur_year + '\''
				+ ", subject_id=" + subject_id + ", subject_name='" + subject_name + '\'' + ", category_id="
				+ category_id + ", category_name='" + category_name + '\'' + ", classroom_id=" + classroom_id
				+ ", classroom_name='" + classroom_name + '\'' + ", clas_id=" + clas_id + ", tech_id=" + tech_id
				+ ", tech_name='" + tech_name + '\'' + ", user_mobile='" + user_mobile + '\'' + ", tech_type="
				+ tech_type + ", tech_note='" + tech_note + '\'' + ", stud_id='" + stud_id + '\'' + ", is_main="
				+ is_main + ", start=" + start + ", page=" + page + ", limit=" + limit + ", orderType='" + orderType
				+ '\'' + ", orderContent='" + orderContent + '\'' + ", isNeedCount=" + isNeedCount + ", isBlurQuery="
				+ isBlurQuery + ", isDel=" + isDel + ", status=" + status + ", isQueryConditions='" + isQueryConditions
				+ '\'' + ", key='" + key + '\'' + ", val='" + val + '\'' + ", list=" + list + ", size=" + size + '}';
	}
}
