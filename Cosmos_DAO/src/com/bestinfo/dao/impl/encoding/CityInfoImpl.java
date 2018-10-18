package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.CityInfo;
import com.bestinfo.dao.encoding.ICityInfo;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CityInfoImpl extends BaseDaoImpl implements ICityInfo {

    /**
     * 根据省编号和地市编号获取二级城市编号信息sql
     */
    public static final String GET_CITYINFO_BY_ID = "select city_id,city_name,province_id,long_city_id,city_address,city_phone,terminal_password,work_status from t_city_info where province_id = ? and city_id = ?";

    /**
     * 查询二级城市编号列表数据sql
     */
    public static final String GET_CITYINFO_List = "select city_id,city_name,province_id,long_city_id,city_address,city_phone,terminal_password,work_status from t_city_info";

    /**
     * 根据省编号和地市编号更新二级城市编号信息sql
     */
    public static final String UPDATE_CITYINFO = "update t_city_info set city_name = ?,long_city_id = ?,city_address = ?,city_phone = ?,terminal_password = ?,work_status = ?  where province_id = ? and city_id = ?";

    /**
     * 批量根据地市编号修改中心地址、联系电话、终端维护口令
     */
    public static final String UPDATE_CITYINFO_BY_ID = "update t_city_info set city_address = ?, city_phone = ?, terminal_password = ? where city_id = ? ";

    /**
     * 查询二级城市编号数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据
     */
    @Override
    public List<CityInfo> selectCityInfo(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_CITYINFO_List, null, CityInfo.class);
    }

    /**
     * 修改二级城市编号数据
     *
     * @param jdbcTemplate
     * @param ci
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int updateCityInfo(JdbcTemplate jdbcTemplate, CityInfo ci) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_CITYINFO, new Object[]{
                ci.getCity_name(),
                ci.getLong_city_id(),
                ci.getCity_address(),
                ci.getCity_phone(),
                ci.getTerminal_password(),
                ci.getWork_status(),
                ci.getProvince_id(),
                ci.getCity_id()
            });
        } catch (DataAccessException e) {
            logger.error("Province_id:" + ci.getProvince_id() + ",City_id:" + ci.getCity_id(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 批量修改二级城市编号数据
     *
     * @param jdbcTemplate
     * @param ciList
     * @return
     */
    @Override
    public int updateBatchCityInfo(JdbcTemplate jdbcTemplate, final List<CityInfo> ciList) {
        try {
            jdbcTemplate.batchUpdate(UPDATE_CITYINFO_BY_ID, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    CityInfo ci = ciList.get(i);
                    ps.setString(1, ci.getCity_address());
                    ps.setString(2, ci.getCity_phone());
                    ps.setString(3, ci.getTerminal_password());
                    ps.setInt(4, ci.getCity_id());
                }

                @Override
                public int getBatchSize() {
                    return ciList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("when batch modify city info error.", e);
            return -1;
        }
    }

}
