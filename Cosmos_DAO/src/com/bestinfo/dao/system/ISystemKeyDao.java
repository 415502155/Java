package com.bestinfo.dao.system;

import com.bestinfo.bean.business.SystemKey;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 查询系统密钥
 * @author YangRong
 */
public interface ISystemKeyDao {
    public String getKey(JdbcTemplate jdbcTemplate, String keyName, int keyStatus); 
    public SystemKey getSystemKey(JdbcTemplate jdbcTemplate, String keyName);
}
