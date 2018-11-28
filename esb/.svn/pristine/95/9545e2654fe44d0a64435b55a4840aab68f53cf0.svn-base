package cn.edugate.esb.redis.dao;

import java.util.List;

import org.springframework.data.redis.connection.RedisConnection;

import cn.edugate.esb.entity.Department;

public interface RedisDepartmentDao {
	boolean add(Department tr);
	
	boolean add(List<Department> trs);
	
	boolean delete(Department tr);

	boolean deleteAll();

	Department getByDepId(String dep_id,RedisConnection connection);
	
	List<Department> getDepsByOrgId(String org_id);

	/**
	 * 根据机构主键获取部门详细信息的列表
	 * @param org_id
	 * @return
	 */
	List<Department> getDepsDetailList(String org_id);
}
