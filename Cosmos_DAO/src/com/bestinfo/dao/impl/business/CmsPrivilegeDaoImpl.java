package com.bestinfo.dao.impl.business;

import com.bestinfo.bean.business.CmsPrivilege;
import com.bestinfo.dao.business.ICmsPrivilegeDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author user
 */
@Repository
public class CmsPrivilegeDaoImpl extends BaseDaoImpl implements ICmsPrivilegeDao {

    /**
     * 查询内容发布权限
     */
    private static final String SELECT_CMSPRIVILEGE_LIST = "select cms_id,cms_range,receiving_object,work_status from t_cms_privilege";
    /**
     * 查询
     */
    private static final String SELECT_CMSPRIVILEGE_BY_PRIMARY = "select cms_id,cms_range,receiving_object,work_status from t_cms_privilege where cms_id = ? and cms_range = ? and receiving_object = ?";
    /**
     * *
     * 修改
     */
    private static final String UPDATE_CMSPRIVILEGE = " update t_cms_privilege set cms_range = ?, receiving_object = ?, work_status = ?  where cms_id = ? ";
    /**
     * 新增
     */
    private static final String INSERT_CMSPRIVILEGE = "insert into t_cms_privilege(cms_id,cms_range,receiving_object,work_status) values (?,?,?,?)";

    /**
     * 查询内容发布权限数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    @Override
    public List<CmsPrivilege> selectCmsPrivilege(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, SELECT_CMSPRIVILEGE_LIST, null, CmsPrivilege.class);
    }

    /**
     * 修改内容发布权限数据
     *
     * @param jdbcTemplate
     * @param cp
     * @return
     */
    @Override
    public int updateCmsPrivilege(JdbcTemplate jdbcTemplate, CmsPrivilege cp) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_CMSPRIVILEGE, new Object[]{
                cp.getCms_range(),
                cp.getReceiving_object(),
                cp.getWork_status(),
                cp.getCms_id()
            });
        } catch (DataAccessException e) {
            logger.error("Cms_id:"+cp.getCms_id()+",Receiving_object:"+cp.getReceiving_object()+",Cms_range:"+cp.getCms_range(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 增加内容发布权限数据
     *
     * @param jdbcTemplate
     * @param cp
     * @return
     */
    @Override
    public int addCmsPrivilege(JdbcTemplate jdbcTemplate, CmsPrivilege cp) {
        int result = -1;
        try {
            result = jdbcTemplate.update(INSERT_CMSPRIVILEGE, new Object[]{
                cp.getCms_id(),
                cp.getCms_range(),
                cp.getReceiving_object(),
                cp.getWork_status()
            });
        } catch (DataAccessException e) {
            logger.error("Cms_id:"+cp.getCms_id()+",Receiving_object:"+cp.getReceiving_object()+",Cms_range:"+cp.getCms_range(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -2;
        }
        return result;
    }

    /**
     * 批量插入内容发布权限数据
     *
     * @param jdbcTemplate
     * @param cmsPrivilegeList
     * @return
     */
    @Override
    public int batchAddCmsPrivilege(JdbcTemplate jdbcTemplate, final List<CmsPrivilege> cmsPrivilegeList) {
        try {
            jdbcTemplate.batchUpdate(INSERT_CMSPRIVILEGE, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    CmsPrivilege cp = cmsPrivilegeList.get(i);
                    ps.setInt(1, cp.getCms_id());
                    ps.setInt(2, cp.getCms_range());
                    ps.setString(3, cp.getReceiving_object());
                    ps.setInt(4, cp.getWork_status());
                }

                @Override
                public int getBatchSize() {
                    return cmsPrivilegeList.size();
                }

            });
            return 0;
        } catch (Exception e) {
            logger.error("batch add cms privilege error.", e);
            return -1;
        }
    }

    /**
     * 根据主键查询
     *
     * @param jdbcTemplate
     * @param cms_id
     * @param cms_range
     * @param receiving_object
     * @return
     */
    @Override
    public CmsPrivilege getCmsPrivilegeByPrimary(JdbcTemplate jdbcTemplate, int cms_id, int cms_range, String receiving_object) {
        return this.queryForObject(jdbcTemplate, SELECT_CMSPRIVILEGE_BY_PRIMARY, new Object[]{cms_id, cms_range, receiving_object}, CmsPrivilege.class);
    }

}
