package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.sysUser.SystemInfo;
import com.bestinfo.dao.encoding.ISystemInfo;
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
public class SystemInfoImpl extends BaseDaoImpl implements ISystemInfo {

    /**
     * 根据省系统编号获取省系统参数信息sql
     */
    private static final String GET_SYSTEMINFO_BY_ID = "select province_id,province_name,province_address,province_phone,agentfee_type,deduct_switch,cash_fee_type,online_report,center_status,max_terminal,cur_max_game,online_cash_mark,system_version,system_syn_no,center_type from t_system_info where province_id = ?";

    /**
     * 查询省系统参数列表数据sql
     */
    private static final String GET_SYSTEMINFO_List = "select province_id,province_name,province_address,province_phone,agentfee_type,deduct_switch,cash_fee_type,online_report,center_status,max_terminal,cur_max_game,online_cash_mark,system_version,system_syn_no,center_type from t_system_info";

    /**
     * 根据省系统编号更新省系统参数sql
     */
    private static final String UPDATE_SYSTEMINFO = "update t_system_info set province_name = ?,province_address = ?,province_phone = ?,agentfee_type = ?,deduct_switch = ?,cash_fee_type=?,online_report = ?,center_status = ?,max_terminal = ?,cur_max_game = ?,online_cash_mark = ?,system_version = ?,system_syn_no = ?,center_type=?  where province_id = ?";

    /**
     * 根据系统ID查询系统信息表
     *
     * @param jdbcTemplate
     * @param systemId
     * @return 系统信息数据
     */
    @Override
    public SystemInfo getSystemInfoBySystemId(JdbcTemplate jdbcTemplate, int systemId) {
        return queryForObject(jdbcTemplate, GET_SYSTEMINFO_BY_ID, new Object[]{systemId}, SystemInfo.class);

    }

    /**
     * 查询省系统参数数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据
     */
    @Override
    public List<SystemInfo> selectSystemInfo(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_SYSTEMINFO_List, null, SystemInfo.class);
    }

    /**
     * 修改省系统参数数据
     *
     * @param jdbcTemplate
     * @param si
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int updateSystemInfo(JdbcTemplate jdbcTemplate, SystemInfo si) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_SYSTEMINFO, new Object[]{
                si.getProvince_name(),
                si.getProvince_address(),
                si.getProvince_phone(),
                si.getAgentfee_type(),//
                si.getDeduct_switch(),
                si.getCash_fee_type(),//
                si.getOnline_report(),
                si.getCenter_status(),
                si.getMax_terminal(),
                si.getCur_max_game(),
                si.getOnline_cash_mark(),
                si.getSystem_version(),
                si.getSystem_syn_no(),
                si.getCenter_type(),//
                si.getProvince_id()
            });
        } catch (DataAccessException e) {
            logger.error("Province_id:"+si.getProvince_id(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

}
