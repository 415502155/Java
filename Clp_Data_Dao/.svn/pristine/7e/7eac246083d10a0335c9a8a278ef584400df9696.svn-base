package com.bestinfo.dao.encoding;

import com.bestinfo.bean.encoding.TerminalSoftware;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 终端软件信息表
 * @author hhhh
 */
public interface ITerminalSoftware {

    /**
     * 查询软件类型数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据
     */
    public List<TerminalSoftware> selectTerminalSoftware(JdbcTemplate jdbcTemplate);

    /**
     * 根据软件编号获取软件信息
     *
     * @param jdbcTemplate
     * @param softwareId
     * @return
     */
    public TerminalSoftware selectTerminalSoftwareById(JdbcTemplate jdbcTemplate, int softwareId);

    /**
     * 修改软件类型数据
     *
     * @param jdbcTemplate
     * @param ts
     * @return
     */
    public int updateTerminalSoftware(JdbcTemplate jdbcTemplate, TerminalSoftware ts);

}
