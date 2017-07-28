package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.TerminalSoftware;
import com.bestinfo.dao.encoding.ITerminalSoftware;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hhhh
 */
@Repository
public class TerminalSoftwareImpl extends BaseDaoImpl implements ITerminalSoftware {

    /**
     * 根据软件类型编号获取软件类型信息sql
     */
    private static final String GET_TERMINALSOFTWARE_BY_ID = "select software_id,software_name,software_version,package_num,release_time,work_status from t_terminal_software where software_id = ?";

    /**
     * 查询软件类型列表数据sql
     */
    private static final String GET_TERMINALSOFTWARE_List = "select software_id,software_name,software_version,package_num,release_time,work_status from t_terminal_software";

    /**
     * 根据软件类型编号更新软件类型sql
     */
    private static final String UPDATE_TERMINALSOFTWARE = "update t_terminal_software set software_name = ?,software_version = ?,package_num = ?,release_time = ?,work_status = ?  where software_id = ?";

    /**
     * 查询软件类型数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据
     */
    @Override
    public List<TerminalSoftware> selectTerminalSoftware(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_TERMINALSOFTWARE_List, null, TerminalSoftware.class);
    }

    /**
     * 根据软件编号获取软件信息
     *
     * @param jdbcTemplate
     * @param softwareId
     * @return
     */
    @Override
    public TerminalSoftware selectTerminalSoftwareById(JdbcTemplate jdbcTemplate, int softwareId) {
        return this.queryForObject(jdbcTemplate, GET_TERMINALSOFTWARE_BY_ID, new Object[]{softwareId}, TerminalSoftware.class);
    }

    /**
     * 修改软件类型数据
     *
     * @param jdbcTemplate
     * @param ts
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int updateTerminalSoftware(JdbcTemplate jdbcTemplate, TerminalSoftware ts) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_TERMINALSOFTWARE, new Object[]{
                ts.getSoftware_name(),
                ts.getSoftware_version(),
                ts.getPackage_num(),
                ts.getRelease_time(),
                ts.getWork_status(),
                ts.getSoftware_id()
            });
        } catch (DataAccessException e) {
            logger.error("Software_id():"+ts.getSoftware_id(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

}
