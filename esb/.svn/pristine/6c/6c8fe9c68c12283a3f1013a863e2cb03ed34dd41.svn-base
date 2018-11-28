package cn.edugate.esb.redis.dao;

import java.util.List;

import cn.edugate.esb.entity.Grade2Clas;

/**
 * 年级班级关系缓存
 */
public interface RedisGrade2ClasDao {
	/**
	 * 添加年级
	 * @param Grade2Clas
	 * @return
	 */
	boolean add(Grade2Clas grade);
	/**
	 * 批量添加年级(For Redis)
	 * @param Grade2Clas
	 * @return 
	 */
	boolean add(List<Grade2Clas> grades);
	/**
	 * 删除年级
	 * @param Grade2Clas
	 * @return
	 */
	boolean delete(Grade2Clas grade);
	/**
	 * 无条件的删除全部(For Redis)
	 * @return 
	 */
	boolean deleteAll();
	Grade2Clas getById(Integer gra2cls_id);
	Integer getGradeIdByClassId(Integer classid);
	
}
