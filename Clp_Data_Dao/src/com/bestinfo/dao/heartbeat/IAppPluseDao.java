package com.bestinfo.dao.heartbeat;

import com.bestinfo.bean.heartbeat.AppPluse;
import org.springframework.jdbc.core.JdbcTemplate;

public interface IAppPluseDao {

    public AppPluse getAppPluseById(JdbcTemplate jdbcTemplate, int appId);

    public int updateAppPluseById(JdbcTemplate jdbcTemplate, AppPluse appPluse);
}
