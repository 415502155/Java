package com.bestinfo.dao.impl.terminal;

import com.bestinfo.bean.terminal.SynCodeInfo;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.terminal.ISynCodeInfoDao;
import com.bestinfo.define.terminal.SynType;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 操作信息-同步字列表T_syn_list
 *
 * @author YangRong
 */
@Repository
public class SynCodeInfoDaoImpl extends BaseDaoImpl implements ISynCodeInfoDao {

//    private final Logger logger = Logger.getLogger(SynCodeInfoDaoImpl.class);
    private static final String SELECT_SYNCODE_LIST = "select syn_type,item_no,syn_code from t_syn_list";
    private static final String SELECT_SYNCODE_LISTA = "select syn_type,item_no,syn_code from t_syn_list where SYN_TYPE in(" + SynType.CRYPT_KEY + "," + SynType.SYS_PARM + "," + SynType.COMM_ADDR + ")";

    private static final String SELECT_SYNCODE_BY_ID = "select syn_type,item_no,syn_code,last_syn_time,work_status from t_syn_list where syn_type = ? and item_no = ?";

    private static final String UPDATE_SYN_INFO = "update t_syn_list set syn_code = ?,last_syn_time=? where syn_type = ? and item_no = ?";

    private static final String UPDATESYNINFO = "update t_syn_list set syn_code=syn_code+1,last_syn_time=sysdate where syn_type = ? and item_no = ?";

    /**
     * 得到同步字列表数据
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<SynCodeInfo> getSynCodeList(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, SELECT_SYNCODE_LIST, null, SynCodeInfo.class);
    }

    @Override
    public List<SynCodeInfo> getSynCodeListA(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, SELECT_SYNCODE_LISTA, null, SynCodeInfo.class);
    }

    /**
     * 更新同步字信息
     *
     * @param jdbcTemplate
     * @param sci
     * @return
     */
    @Override
    public int updateSynCode(JdbcTemplate jdbcTemplate, SynCodeInfo sci) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_SYN_INFO, new Object[]{
                sci.getSyn_code(),
                sci.getLast_syn_time(),
                sci.getSyn_type(),
                sci.getItem_no()
            });
        } catch (DataAccessException e) {
            logger.error("when update t_syn_list occur error where syn_type = " + sci.getSyn_type() + " and item_no = " + sci.getItem_no(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    @Override
    public int updateSynCode(JdbcTemplate jdbcTemplate, int syntype, int itemno) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATESYNINFO, new Object[]{syntype, itemno});
        } catch (DataAccessException e) {
            logger.error("", e);
            result = -1;
        }
        return result;
    }

    /**
     * 根据主键查询同步字信息
     *
     * @param jdbcTemplate
     * @param syn_type
     * @param item_no
     * @return
     */
    @Override
    public SynCodeInfo getSynInfoByPrimary(JdbcTemplate jdbcTemplate, int syn_type, int item_no) {
        return this.queryForObject(jdbcTemplate, SELECT_SYNCODE_BY_ID, new Object[]{syn_type, item_no}, SynCodeInfo.class);
    }

}
