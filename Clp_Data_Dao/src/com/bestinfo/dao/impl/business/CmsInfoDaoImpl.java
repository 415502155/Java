package com.bestinfo.dao.impl.business;

import com.bestinfo.bean.business.TCmsInfo;
import com.bestinfo.dao.business.ICmsInfoDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lvchangrong
 */
@Repository
public class CmsInfoDaoImpl extends BaseDaoImpl implements ICmsInfoDao {

    /**
     * 查询
     */
    private static final String SELECT_CMSINFO_LIST = "select cms_id,release_time,cms_type,cms_title,bulletin_len,cms_filename,keys,work_status,deadline,version from t_cms_info";
    /**
     * 更新
     */
    private static final String UPDATE_CMSINFO = " update t_cms_info set release_time = ?, cms_type = ?, cms_title = ?, bulletin_len = ?, cms_filename = ?, keys = ?, work_status = ?, deadline = ?, version = ? where cms_id = ? ";
    /**
     * 根据keys查询
     */
    private static final String SELECT_CMSINFO_BYKEYS = " select keys from t_cms_info where keys = ?  ";

    /**
     * 查询内容管理的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    @Override
    public List<TCmsInfo> selectCmsInfo(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, SELECT_CMSINFO_LIST, null, TCmsInfo.class);
    }

    /**
     * 修改内容管理数据
     *
     * @param jdbcTemplate
     * @param ci
     * @return
     */
    @Override
    public int updateCmsInfo(JdbcTemplate jdbcTemplate, TCmsInfo ci) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_CMSINFO, new Object[]{
                ci.getRelease_time(),
                ci.getCms_type(),
                ci.getCms_title(),
                ci.getBulletin_len(),
                ci.getCms_filename(),
                ci.getKeys(),
                ci.getWork_status(),
                ci.getDeadline(),
                ci.getVersion(),
                ci.getCms_id()
            });
        } catch (DataAccessException e) {
            logger.error("Cms_id:"+ci.getCms_id(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 根据keys获取内容管理信息
     *
     * @param jdbcTemplate
     * @param keys
     * @return
     */
    @Override
    public TCmsInfo getCmsInfoByKeys(JdbcTemplate jdbcTemplate, String keys) {
        return this.queryForObject(jdbcTemplate, SELECT_CMSINFO_BYKEYS, new Object[]{keys}, TCmsInfo.class);
    }
}
