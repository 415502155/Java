package com.bestinfo.dao.impl.terminal;

import com.bestinfo.bean.terminal.TerminalSubSoftware;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.terminal.ITerminalSubSoftwareDao;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 信息-终端软件分包信息表
 *
 * @author hhhh
 */
@Repository
public class TerminalSubSoftwareDaoImpl extends BaseDaoImpl implements ITerminalSubSoftwareDao {

//    private final Logger logger = Logger.getLogger(TerminalSubSoftwareDaoImpl.class);
    /**
     * 根据软件编号获取该软件下的所有子包sql
     */
    public static final String GET_TERMINALSUBSOFTWARELIST_BY_ID = "select sub_id,sub_package_version,package_size,package_dir from t_terminal_sub_software where software_id = ? and work_status=?";
    public static final String GET_TMN_SUBSOFTWARE_BY_ID = "select * from t_terminal_sub_software where software_id = ? and work_status=?";
    /**
     * 根据软件编号获取该软件下的所有子包
     *
     * @param jdbcTemplate
     * @param softwareId
     * @return
     */
    @Override
    public List<TerminalSubSoftware> getTerminalSubSoftwareListById(JdbcTemplate jdbcTemplate, int softwareId, int status) {
        return this.queryForList(jdbcTemplate, GET_TERMINALSUBSOFTWARELIST_BY_ID, new Object[]{softwareId, status}, TerminalSubSoftware.class);
    }
 /**
     * 根据软件编号获取该软件下的所有子包
     *
     * @param jdbcTemplate
     * @param softwareId
     * @param status 软件状态
     * @return
     */
    @Override
    public List<TerminalSubSoftware> getTmnSubSoftwareById(JdbcTemplate jdbcTemplate, int softwareId, int status) {
        return this.queryForList(jdbcTemplate, GET_TMN_SUBSOFTWARE_BY_ID, new Object[]{softwareId, status}, TerminalSubSoftware.class);
    }
}
