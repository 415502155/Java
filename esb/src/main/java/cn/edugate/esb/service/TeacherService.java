package cn.edugate.esb.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.util.Paging;

public interface TeacherService {

	/**
	 * 添加新的教师
	 * 
	 * @param function
	 */
	public abstract int add(Teacher teacher);

	/**
	 * 批量添加教师
	 * 
	 * @param teacherList
	 */
	public abstract void batchAdd(List<Teacher> teacherList);

	/**
	 * 根据主键删除教师
	 * 
	 * @param id
	 * @return
	 */
	public abstract Integer delete(int id);

	/**
	 * 更新教师
	 * 
	 * @param function
	 * @return
	 */
	public abstract void update(Teacher teacher);
	/**
	 * 更新教师
	 * 
	 * @param function
	 * @return
	 */
	public abstract Teacher update(List<Teacher> teacherList);

	/**
	 * 根据主键获取教师
	 * 
	 * @param id
	 * @return
	 */
	public abstract Teacher getTechById(int id);

	/**
	 * 获得所有教师列表
	 * 
	 * @param function
	 * @return
	 */
	public abstract List<Teacher> getAll();

	/**
	 * 获取教师列表（带分页）
	 * 
	 * @param paging
	 * @param function
	 * @return
	 */
	public abstract Paging<Teacher> getTechListWithPaging(Paging<Teacher> paging, int org_id, int type, String seach_name);

	/**
	 * 获得所有教师列表
	 * 
	 * @param function
	 * @return
	 */
	public abstract List<Teacher> getTeacherSearch(int org_id, String sname);

	public abstract Teacher getTechAndUserId(int id);

	/**
	 * 教师条件查询分页
	 * 
	 * @param org_id
	 * @param role_id
	 * @param tech_name
	 * @param user_mobile
	 * @param pages
	 */
	public abstract void getPaging(String org_id, String role_id, String tech_name, String user_mobile, Paging<Teacher> pages);

	/**
	 * 查询机构树下的教师，按机构部门分组，带组成员信息
	 * 
	 * @param org_id
	 * @param group_id
	 * @return
	 */
	public abstract List<Object> getOrgTreeTeacherMembers(String org_id, String group_id);
	
	/**
	 * 对上传的Excel内容进行验证并保存到Mysql数据库（每验证通过一条，写入数据库一次，验证未通过的写入错误集合中并返回）
	 * @param excelRowArray
	 * @return
	 */
	public List<String[]> validateAndSaveTeacher(Integer orgID, List<String[]> excelRowArray);

	public abstract List<Object> getTeacherMembers(String orgId, String groupId);
	
	public abstract List<Map<String,Object>> getTechsOfRlId(String org_id,Integer code);

	public abstract void batchAddTeacher(String fileID);

	/**
	 * 获取通讯录
	 * @param org_id
	 * @return
	 */
	public abstract List<Teacher> getPhoneBook(Integer org_id);

	/**
	 * 获取机构下全部教师
	 * @param parseInt
	 * @return
	 */
	public abstract List<Teacher> getTeachersByOrgId(Integer org_id);
	
	
	/**
	 * 获得所有教师（授课、班主任、年级组长）范围
	 * 
	 * @param function
	 * @return
	 */
	public abstract List<TechRange> getTechRanges(int org_id);
	
	public abstract List<Map<String,String>> getTechsOforg(int org_id);
	
	public abstract Map<String,Object> batchTechRange(int org_id,MultipartFile multiFile,Map<String,String> map);
	
	/**
	 * 对上传的Excel内容进行验证并保存到Mysql数据库（每验证通过一条，写入数据库一次，验证未通过的写入错误集合中并返回）
	 * @param excelRowArray
	 * @return
	 */
	public abstract List<String[]> validateBatchTechRange(Integer orgID, List<String[]> excelRowArray,Map<String,String> courMap);
	
	public String saveErrorExcel(String tempPath,String completeFileName,int org_id); 
}
