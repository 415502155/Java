package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.IdType;
import com.bestinfo.dao.encoding.IIdType;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author user
 */
@Repository
public class IdTypeImpl extends BaseDaoImpl implements IIdType {

    /**
     * 查询证件类型数据列表sql
     */
    private static final String GET_IDTYPE_LIST = "select  id_type_id,id_type_name,work_status  from t_id_type";
    /**
     * 根据证件类型编号更新证件类型sql
     */
    private static final String UPDATE_IDTYPE = "update t_id_type set  id_type_name = ?,work_status = ? where id_type_id = ?";

    /**
     * 查询证件类型数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据
     */
    @Override
    public List<IdType> selectIdType(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_IDTYPE_LIST, null, IdType.class);
    }

    /**
     * 修改证件类型
     *
     * @param jdbcTemplate
     * @param idType
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int updateIdType(JdbcTemplate jdbcTemplate, IdType idType) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_IDTYPE, new Object[]{
                idType.getId_type_name(),
                idType.getWork_status(),
                idType.getId_type_id()
            });
        } catch (DataAccessException e) {
            logger.error("Id_type_id:"+idType.getId_type_id(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }
}
