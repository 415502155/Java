package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.entity.OrgTree;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.util.Paging;


/**
 * 机构服务接口
 * Title:UserService
 * Description:
 * Company:SJWY
 * @author:wg
 * @Date:2017年4月25日下午1:57:54
 */
public interface OrganizationService {
	/**
	 * 添加新的机构
	 * @param function
	 */
	public abstract void add(Organization organization);
	/**
	 * 更新机构
	 * @param function
	 * @return
	 */
	public abstract Organization update(Organization organization);
	/**
	 * 根据主键获取机构
	 * @param id
	 * @return
	 */
	public abstract Organization getOrgById(int id);
	/**
	 * 获得所有机构列表
	 * @param function
	 * @return
	 */
	public abstract List<Organization> getOrgList();
	/**
	 * 获取机构列表（带分页）
	 * @param paging
	 * @param function
	 * @return
	 */
	public abstract Paging<Organization> getOrgListWithPaging(Paging<Organization> paging,Organization organization);
	/**
	 * 根据类型获得所有机构列表
	 * @param function
	 * @return
	 */
	public abstract List<Organization> getOrgByType(int type);
	/**
	 * 根据功能ID获得所有引用机构列表
	 * @param function
	 * @return
	 */
	public abstract List<Organization> getOrgByFunID(int type);
	
	/**
	 * 新增组织
	 * @param orgEntity
	 * @return
	 * @throws Exception 
	 */
	public abstract String saveOrganization(Organization orgEntity);
	
	/**
	 * 编辑更新组织信息
	 * @param orgEntity
	 * @return
	 */
	public abstract String updateOrganization(Organization orgEntity);
	
	/**
	 * 根据机构ID更新机构的Logo
	 * @param orgEntity
	 * @return
	 */
	public abstract int updateOrganizationLogo(Organization orgEntity);
	
	/**
	 * 根据机构ID更新机构背景图
	 * @param orgEntity
	 * @return
	 */
	public abstract int updateOrganizationBackground(Organization orgEntity);
	
	/**
	 * 获取机构下的年级和校区信息
	 * @param orgID
	 * @return
	 */
	public abstract Organization getOrgAllInfo(int orgID);
	
	/**
	 * 通过机构主键获得下级机构
	 * @param orgID
	 * @return
	 */
	public abstract List<Organization> getLowerOrgList(int orgID);
	
	/**
	 * 保存当前机构的下级机构
	 * @param orgID
	 * @param lowerOrgList
	 * @return
	 */
	public abstract void saveLowerOrg(int orgID, List<OrgTree> lowerOrgList);
	
	/**
	 * 根据功能主键获取当前所有使用该功能的机构列表(带分页)
	 * @param paging
	 * @param orgName 查询条件:机构名称
	 * @param functionId 功能主键
	 * @return
	 */
	public abstract Paging<Organization> getOrgListByUsingFunctionWithPaging(
			Paging<Organization> paging, String orgName, Integer functionId);
	
	
	public abstract int updateOrganizationWelCome(Organization orgEntity);
	
	/**
	 * 执行存储过程，从老的数据库导入数据
	 * @param orgEntity
	 */
	public abstract void executeBatchImport(Organization orgEntity);
	/**
	 * 查询school.foundation表中的school
	 * @param district
	 * @return
	 */
	public abstract List<Organization> getSchool(String district);
	/**
	 * 查询对应功能下的全部机构
	 * @param funId
	 * @return
	 */
	public abstract List<String> getOrgInFunctionByFunId(Integer funId);
	/**
	 * 查询对应模块下的全部机构
	 * @param funId
	 * @return
	 */
	public abstract List<String> getOrgInModuleByFunId(Integer funId);
	
	public abstract void deleteOrgData(int org_id);
	/**
	 * 根据机构主键查询机构
	 * @param parseInt
	 * @return
	 */
	public abstract Organization getOrgByIdForRedis(int org_id);
	
	/**
	 * 初始化学校中的新增三种年级类型
	 * @param org_id
	 */
	public abstract void initGradeType4Schools(String org_id);
	
}
