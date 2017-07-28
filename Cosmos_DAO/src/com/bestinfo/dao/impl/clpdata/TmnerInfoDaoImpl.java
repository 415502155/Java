/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.impl.clpdata;

import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.dao.clpdata.ITmnerInfoDao;
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
public class TmnerInfoDaoImpl extends BaseDaoImpl implements ITmnerInfoDao{

    private static final String QUERY_TMNINFO_LIST = "select terminal_id,public_key  from t_tmn_info";
    private static final String QUERY_TMNINFO_SUM = "select count(*) from t_tmn_info";
    @Override
    public List<TmnInfo> getTmnInfoList(JdbcTemplate jdbcTemplate) {
        try {
            return this.queryForList(jdbcTemplate, QUERY_TMNINFO_LIST, null, TmnInfo.class);            
        } catch (Exception e) {
            logger.error("getTmnInfoList ex :", e);
            return Collections.emptyList();
        }
    }

    @Override
    public int getTmnInfoSum(JdbcTemplate jdbcTemplate) {
        try {
            return this.queryForInteger(jdbcTemplate, QUERY_TMNINFO_SUM, null);
        } catch (Exception e) {
            logger.error("getTmnInfoSum ex :", e);
            return 0;
        }
    }
    
}
