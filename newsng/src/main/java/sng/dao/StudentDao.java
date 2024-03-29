package sng.dao;

import java.util.List;
import java.util.Map;

import sng.entity.StudentClass;
import sng.pojo.Clas2Student;
import sng.pojo.ParamObj;
import sng.pojo.StudentPojo;
import sng.pojo.base.Student;

/**
 * @类 名： StudentDao
 * @功能描述：学生Dao接口 @作者信息： LiuYang @创建时间： 2018年11月20日下午4:02:11
 */
public interface StudentDao extends BaseDao<Student> {

	/**
	 * 根据条件查询学生列表
	 */
	public Map<String, Object> queryList(Integer org_id, String term_id, String category_id, String subject_id, String clas_type,
			String cam_id, String stu_status, String pay_method, String is_print, String keyword,
			Integer currentPageNum, Integer pageSize);

	/**
	 * 获取待财务人员审核的退费列表
	 */
	Map<String, Object> getRefundApplyList(Integer org_id, String starttime, String endtime, String category_id, String subject_id,
			String class_type, String cam_id, String term_id, String keywords, Integer currentPageNum,
			Integer pageSize);

	/**
	 * 获取待财务人员审核的退费列表
	 */
	public List<StudentPojo> getRefundApplyList(String ids);

	/**
	 * 根据学生班级关系表主键获取待教务退费审核的退费列表
	 */
	public List<StudentPojo> getRefundList(String stu_clas_ids);

	/**
	 * 根据条件查询学生列表
	 */
	public List<StudentPojo> getList(Integer org_id, String term_id, String category_id, String subject_id, String clas_type,
			String cam_id, String stu_status, String pay_method, String is_print, String keyword);

	/**
	 * 根据条件查询学生列表
	 */
	public List<StudentPojo> getList(String ids);

	/**
	 * 根据条件查询支付信息记录
	 *  @param txn_type 1缴费2退费
	 */
	public Map<String, Object> getRecordList(Integer org_id, String starttime, String endtime, String category_id, String subject_id,
			String class_type, String cam_id, String term_id, String pay_method, String txn_type, String keywords,
			Integer currentPageNum, Integer pageSize);

	/**
	 * 根据条件查询支付信息记录
	 */
	public List<StudentPojo> getRecordList(String ids);

	/**
	 * 根据家长user_id查询学生信息
	 */
	public List<Student> getStudentByParentUserId(String parent_user_id);

	/**
	 * @Title : queryStuAndClassInfo
	 * @author magq
	 * @desc 学员管理--根据班级ID,学员IDS以及org_id查询班级和学员班级关系信息
	 * @param obj
	 */
	public List<Clas2Student> queryStuAndClassInfo(ParamObj obj);

	/**
	 * @Title queryMoveClassListInfo
	 * @desc 学员管理--根据不同学员不同状态获取班级信息
	 * @author magq
	 * @param cs
	 * @return
	 */
	public List<Clas2Student> queryMoveClassListInfo(Clas2Student cs);

	/**
	 * @Title queryStuManageTelInfo
	 * @desc 学员管理--点击学员管理中每条学员信息后面操作的按钮"手机"
	 * @author magq
	 * @param obj
	 * @return
	 */
	public List<Clas2Student> queryStuManageTelInfo(ParamObj obj);

	
	
	/**
	 * @desc 学员管理--根据学院班级关系ID批量更新学院班级关系表信息
	 * @author magq
	 * @Title updateStuClassTabInfoByStuClasId
	 * @param studentPojos
	 * @return
	 */
	public int updateStuClassTabInfoByStuClasId(List<StudentPojo> studentPojos);

	/**
	 * 查询当前条件下的所有主键
	 */
	public List<Integer> getidList(Integer org_id, String term_id, String category_id,
			String subject_id, String clas_type, String cam_id,
			String stu_status, String pay_method, String is_print,
			String keyword);
	
	/**
	 * 获取财务人员查看的待审核退费列表
	 * @author magq
	 * @date 2018-12-17
	 */
	List getRefundApplyAllPageIds(Integer org_id, String starttime, String endtime,
			String category_id, String subject_id, String class_type,
			String cam_id, String term_id, String keywords);

	/**
	 * 根据主键查询学生
	 */
	public Student getById(Integer stu_id);
	
	
	List getRecordIdsList(Integer org_id, String starttime, String endtime, String category_id, String subject_id,
			String class_type, String cam_id, String term_id, String pay_method, String txn_type, String keywords,
			Integer currentPageNum, Integer pageSize);




}
