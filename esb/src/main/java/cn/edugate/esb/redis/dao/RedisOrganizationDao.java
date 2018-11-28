package cn.edugate.esb.redis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.connection.RedisConnection;

import cn.edugate.esb.entity.Organization;

public interface RedisOrganizationDao {
	boolean add(Organization tr);
	
	boolean add(List<Organization> trs);
	
	boolean delete(Organization tr);

	boolean deleteAll();

	Organization getByOrgId(String org_id,RedisConnection connection);
	
	List<Organization> getOrgsByType(String type);
	
    /**
     * 获取机构下子机构
     * @param orgId 父级机构主键
     * @return 子级机构列表
     */
    Map<String,Organization> getChildOrg(Integer orgId);

	Organization getByOrgName(String org_name_cn);
}
