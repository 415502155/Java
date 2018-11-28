package cn.edugate.esb.redis.dao;

import java.util.List;
import java.util.Map;

import cn.edugate.esb.entity.Clas2Student;
import cn.edugate.esb.entity.Classes;

public interface RedisClassStudentDao {

	boolean add(Clas2Student cs);
	
	boolean adds(List<Clas2Student> cs);
	
	boolean delete(Clas2Student cs);
	
	boolean deleteAll();

	Clas2Student getById(String clas2stud_id);

	Map<String, Clas2Student> getByStud_id(String stud_id);

	List<Clas2Student> getStudsByCid( String clas_id);
	
	List<Clas2Student> getStudsByCids(Map<String,Classes> clas_ids,Map<String,Object> stud_ids);
	
	List<Clas2Student> getStudsBySids(Map<String,Object> stud_ids);
	
	Integer getStudCountByCid(final String clas_id);

	/**
	 * 查询机构下全部关系
	 * @param org_id
	 * @return
	 */
	List<Clas2Student> getByOrgId(String org_id);
}
