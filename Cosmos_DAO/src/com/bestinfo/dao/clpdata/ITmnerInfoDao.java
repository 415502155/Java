/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.clpdata;

import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.bean.sysUser.SystemInfo;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 
 * @author Administrator
 */
public interface ITmnerInfoDao {
    /**
     * 
     * @param jdbcTemplate
     * 
     * @return 
     */
//    public String getTicketInfoByAccountId(JdbcTemplate jdbcTemplate,String account_id);

    public List<TmnInfo> getTmnInfoList(JdbcTemplate jdbcTemplate);
    public int getTmnInfoSum(JdbcTemplate jdbcTemplate);
}
