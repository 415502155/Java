package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.DistrictInfo;
import com.bestinfo.dao.encoding.IDistrictInfo;
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
public class DistrictInfoImpl extends BaseDaoImpl implements IDistrictInfo {

    /**
     * 根据省编号、地市编号、区县编号获取三级区县编号信息sql
     */
    private static final String GET_DISTRICTINFO_BY_ID = "select province_id,city_id,district_id,district_name,district_address,district_phone,work_status from t_district_info where province_id = ? and city_id = ? and district_id = ?";

    /**
     * 查询三级区县编号列表数据sql
     */
    private static final String GET_DISTRICTINFO_List = "select province_id,city_id,district_id,district_name,district_address,district_phone,work_status from t_district_info";

    /**
     * 根据省编号、地市编号、区县编号更新三级区县编号信息sql
     */
    public static final String UPDATE_DISTRICTINFO = "update t_district_info set district_name = ?,district_address = ?,district_phone = ?,district_work_status = ?  where province_id = ? and city_id = ? and district_id = ?";

    /**
     * 查询三级区县编号数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据
     */
    @Override
    public List<DistrictInfo> selectDistrictInfo(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_DISTRICTINFO_List, null, DistrictInfo.class);
    }

    /**
     * 修改三级区县编号数据
     *
     * @param jdbcTemplate
     * @param di
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int updateDistrictInfo(JdbcTemplate jdbcTemplate, DistrictInfo di) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_DISTRICTINFO, new Object[]{
                di.getDistrict_name(),
                di.getDistrict_address(),
                di.getDistrict_phone(),
                di.getDistrict_work_status(),
                di.getProvince_id(),
                di.getCity_id(),
                di.getDistrict_id()
            });
        } catch (DataAccessException e) {
            logger.error("Province_id:"+di.getProvince_id()+",City_id:"+di.getCity_id()+",District_id:"+di.getDistrict_id(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

}
