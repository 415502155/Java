package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.SchemeType;
import com.bestinfo.dao.encoding.ISchemeType;
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
public class SchemeTypeImpl extends BaseDaoImpl implements ISchemeType {

    /**
     * 根据方案类型编号获取方案类型信息sql
     */
    private static final String GET_SCHEMETYPE_BY_ID = "select scheme_type,scheme_type_name,work_status from t_scheme_type where scheme_type = ?";

    /**
     * 查询方案类型列表数据sql
     */
    private static final String GET_SCHEMETYPE_List = "select scheme_type,scheme_type_name,work_status from t_scheme_type";

    /**
     * 根据方案类型编号更新方案类型sql
     */
    private static final String UPDATE_SCHEMETYPE = "update t_scheme_type set scheme_type_name = ?,work_status = ?  where scheme_type = ?";

    /**
     * 查询方案类型数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据
     */
    @Override
    public List<SchemeType> selectSchemeType(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_SCHEMETYPE_List, null, SchemeType.class);
    }

    /**
     * 修改方案类型
     *
     * @param jdbcTemplate
     * @param st
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int updateSchemeType(JdbcTemplate jdbcTemplate, SchemeType st) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_SCHEMETYPE, new Object[]{
                st.getScheme_type_name(),
                st.getWork_status(),
                st.getScheme_type()
            });
        } catch (DataAccessException e) {
            logger.error("Scheme_type:"+st.getScheme_type(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

}
