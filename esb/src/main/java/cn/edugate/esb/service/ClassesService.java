package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Grade2Clas;
import cn.edugate.esb.util.Paging;

/**
 * 班级服务接口
 * Title:ClassesService
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午9:21:36
 */
public interface ClassesService {
	
	/**
	 * 创建新班级
	 * @param classes
	 */
	public abstract void add(Classes classes);
	
	/**
	 * 删除班级
	 * @param classesId
	 * @return
	 */
	public abstract Integer delete(Classes classes);
	
	/**
	 * 更新班级
	 * @param classes
	 * @return
	 */
	public abstract Classes update(Classes classes);
	
	/**
	 * 根据班级主键获取班级
	 * @param classesId
	 * @return
	 */
	public abstract Classes getById(Integer classesId);
	
	/**
	 * 根据条件获取全部班级列表
	 * @param classes
	 * @return
	 */
	public abstract List<Classes> getAllList(int orgId,int camId,int isGraduated,int gradeNumber);
	
	/**
	 * 无条件的查询全部数据(For Redis)
	 * @return
	 */
	public abstract List<Classes> getAll();
	
	/**
	 * 获取班级分页
	 * @param is_graduated
	 * @param cam_id
	 * @param grade_id
	 * @param pages
	 */
	public abstract void getPaging(Integer org_id,Integer is_graduated, Integer cam_id,
			Integer grade_id, Paging<Classes> pages);
	
	/**
	 * 添加班级年级关系
	 * @param grade2Clas
	 */
	public abstract void addGrade2Class(Grade2Clas grade2Clas);
	
	/**
	 * 删除班级
	 * @param clas_id
	 */
	public abstract void deleteById(Integer clas_id);
	
	/**
	 * 获取机构 校区 年级班级
	 * @param org_id
	 * @param cam_id
	 * @param grade_id
	 * @return
	 */
	public abstract List<Classes> getClassByOrgIdCamIdGradeId(Integer org_id,
			Integer cam_id, Integer grade_id);
	
	/**
	 * 通过年级id+班级名获取班级
	 * @param grade_id
	 * @param clas_name
	 * @return
	 */
	public abstract Classes getClassByName(Integer grade_id, String clas_name);
	
	/**
	 * 查询机构下班级
	 * @param org_id
	 * @return
	 */
	public abstract List<Classes> getClassesOfOrg(Integer org_id);
	
	/**
	 * 保存修改的班级信息
	 * @param classEntity
	 * @param gradeId 修改后的年级Id
	 * @return
	 */
	public abstract Classes modifyClassInfo(Classes classEntity, int gradeId);

	public abstract Grade getGradeByClassId(Integer clas_id);
	
	/**
	 * 通过班级名获取班级
	 * @param grade_id
	 * @param clas_name
	 * @return
	 */
	public abstract Classes getClassByNameOrg(Integer org_id,String clas_name);
	
}
