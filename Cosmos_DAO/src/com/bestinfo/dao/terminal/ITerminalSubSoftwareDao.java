package com.bestinfo.dao.terminal;

import com.bestinfo.bean.terminal.TerminalSubSoftware;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 信息-终端软件分包信息表
 *
 * @author hhhh
 */
public interface ITerminalSubSoftwareDao {

    /**
     * 根据软件编号获取该软件下的所有子包
     *
     * @param jdbcTemplate
     * @param softwareId
     * @param status 软件状态
     * @return
     */
    public List<TerminalSubSoftware> getTerminalSubSoftwareListById(JdbcTemplate jdbcTemplate, int softwareId, int status);
      /**
     * 根据软件编号获取该软件下的所有子包
     *
     * @param jdbcTemplate
     * @param softwareId
     * @param status 软件状态
     * @return
     */
    public List<TerminalSubSoftware> getTmnSubSoftwareById(JdbcTemplate jdbcTemplate, int softwareId, int status);

}
