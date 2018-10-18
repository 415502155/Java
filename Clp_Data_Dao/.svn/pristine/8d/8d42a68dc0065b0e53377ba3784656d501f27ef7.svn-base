package com.bestinfo.dao.business;

import com.bestinfo.bean.business.CmsPrivilege;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 内容发布权限
 *
 * @author
 */
public interface ICmsPrivilegeDao {

    /**
     * 查询内容发布权限数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    public List<CmsPrivilege> selectCmsPrivilege(JdbcTemplate jdbcTemplate);

    /**
     * 修改内容发布权限数据
     *
     * @param jdbcTemplate
     * @param cp
     * @return
     */
    public int updateCmsPrivilege(JdbcTemplate jdbcTemplate, CmsPrivilege cp);

    /**
     * 增加内容发布权限数据
     *
     * @param jdbcTemplate
     * @param cp
     * @return
     */
    public int addCmsPrivilege(JdbcTemplate jdbcTemplate, CmsPrivilege cp);

    /**
     * 批量插入内容发布权限数据
     *
     * @param jdbcTemplate
     * @param cmsPrivilegeList
     * @return
     */
    public int batchAddCmsPrivilege(JdbcTemplate jdbcTemplate, final List<CmsPrivilege> cmsPrivilegeList);
    
    /**
     * 根据主键查询
     * @return 
     */
    public CmsPrivilege getCmsPrivilegeByPrimary(JdbcTemplate jdbcTemplate, int cms_id, int cms_range, String receiving_object);
}
