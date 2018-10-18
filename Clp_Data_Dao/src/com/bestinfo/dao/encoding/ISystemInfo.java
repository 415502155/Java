package com.bestinfo.dao.encoding;

import com.bestinfo.bean.sysUser.SystemInfo;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 省系统数据
 *
 * @author hhhh
 */
public interface ISystemInfo {

    /**
     * 查询省系统参数数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据
     */
    public List<SystemInfo> selectSystemInfo(JdbcTemplate jdbcTemplate);

    /**
     * 根据系统ID查询系统信息表
     *
     * @param jdbcTemplate
     * @param systemId
     * @return 系统信息数据
     */
    public SystemInfo getSystemInfoBySystemId(JdbcTemplate jdbcTemplate, int systemId);

    /**
     * 修改省系统参数数据
     *
     * @param jdbcTemplate
     * @param si
     * @return
     */
    public int updateSystemInfo(JdbcTemplate jdbcTemplate, SystemInfo si);

}
