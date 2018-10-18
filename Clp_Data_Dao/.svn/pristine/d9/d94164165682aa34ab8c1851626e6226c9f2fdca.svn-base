/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.impl.clpdata;

import com.bestinfo.bean.sysUser.SystemInfo;
import com.bestinfo.bean.ticket.TicketBet;
import com.bestinfo.dao.clpdata.ISystemInfoDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.Collections;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public class SystemInfoDaoImpl extends BaseDaoImpl implements ISystemInfoDao{

    private static final String QUERY_SYSTEM_INFO_LIST = "select province_id from t_system_info";
    @Override
    public List<SystemInfo> getSystemInfoList(JdbcTemplate jdbcTemplate) {
        try {
            return  this.queryForList(jdbcTemplate, QUERY_SYSTEM_INFO_LIST, null, SystemInfo.class);
        } catch (Exception e) {
            logger.error("GetTicketInfoList ex :", e);
            return Collections.emptyList();
        }
    }
    
}
