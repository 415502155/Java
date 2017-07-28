/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.impl.terminal;

import com.bestinfo.bean.terminal.TmnControlInfo;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.terminal.ITmnControlInfoDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public class TmnControlInfoDaoImpl extends BaseDaoImpl implements ITmnControlInfoDao{

    private static final String GET_TMN_CONTROL_INFO = "SELECT * FROM T_TMN_CONTROL WHERE TERMINAL_ID = ?";
    
    @Override
    public TmnControlInfo getTmnControlInfo(JdbcTemplate jdbcTemplate, int termialId) {
        return queryForObject(jdbcTemplate, GET_TMN_CONTROL_INFO, new Object[]{termialId}, TmnControlInfo.class);
    }
    
}
