package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.util.Paging;

public interface IOrganizationDao   extends BaseDAO<Organization>{
	
	
	/**
	 * 获取所有机构
	 * @return
	 */
	public abstract List<Organization> getOrgList();
	
	/**
	 * 根据类型获取所有机构
	 * @return
	 */
	public abstract List<Organization> getOrgByType(int type);
	
	
	/**
	 * 根据类型获取所有机构
	 * @return
	 */
	public abstract List<Organization> getOrgByFunID(int funId);
	
	/**
	 * 查询所有机构带分页
	 * @param paging
	 * @param organization
	 * @return
	 */
	public abstract Paging<Organization> getOrgListWithPaging(Paging<Organization> paging, Organization organization);
	
	public abstract int checkName(String orgName);
	
	public abstract int saveOrganization(Organization orgEntity);
	
	public abstract int updateOrganizationLogo(Organization orgEntity);
	
	public abstract int updateOrganizationBackground(Organization orgEntity);
	
	public abstract List<Organization> getLowerOrgList(int orgID);

	/**
	 * 根据功能主键获取当前所有使用该功能的机构列表(带分页)
	 * @param paging
	 * @param orgName 查询条件:机构名称
	 * @param functionId 功能主键
	 * @return
	 */
	public abstract Paging<Organization> getListWithPaging(Paging<Organization> paging, String orgName, Integer functionId);

	/**
	 * 删除机构，同时删除机构用户
	 * @param id
	 * @return
	 */
	public abstract Integer delete(int id);
	
	public abstract int updateOrganizationWelCome(Organization orgEntity);
	
	public abstract void executeBatchImport(Organization orgEntity);

	/**
	 * 查询school.foundation表中的school
	 * @param district
	 * @return
	 */
	public abstract List<Organization> getSchool(String district);

	/**
	 * 查询对应功能下的全部机构主键
	 * @param funId
	 * @return
	 */
	public abstract List<String> getOrgInFunctionByFunId(Integer funId);

	/**
	 * 查询对应模块下的全部机构主键
	 * @param funId
	 * @return
	 */
	public abstract List<String> getOrgInModuleByFunId(Integer funId);

	/**
	 * 根据机构主键查询机构
	 * @param org_id
	 * @return
	 */
	public abstract Organization getOrgByIdForRedis(int org_id);
	
	/**
	 * 根据机构类型、名称、所在区域查询
	 * @param type
	 * @param orgName
	 * @return
	 */
	public abstract List<Organization> getOrgList4Paging(int type, String orgName, String area, String upgradeStatus, int year, int offset, int rows);
	
	public abstract List<Organization> getOrgList(int type, String orgName, String area, String upgradeStatus, int year);
	
	/**
	 * 根据机构类型、名称、所在区域查询总记录条数
	 * @param type
	 * @param orgName
	 * @return
	 */
	public abstract int getTotalRecordNum4ClassUpgrade(int type, String orgName, String area, String upgradeStatus, int year);
}
