/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.clpdata;

import com.bestinfo.bean.terminal.TmnClpKey;
import java.util.Date;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 
 * @author Administrator
 */
public interface ScITmnClpKeyDao {
    /***
     * 
     * @param jdbcTemplate
     * @return 
     */
    public List<TmnClpKey> getTmnClpkeyList(JdbcTemplate jdbcTemplate);
    public List<TmnClpKey> getTmnClpkeyListByDate(JdbcTemplate jdbcTemplate,String time);
    public int getOneDayTnmClpKeyCount(JdbcTemplate jdbcTemplate,String time);
    public TmnClpKey getTmnClpKey(JdbcTemplate jdbcTemplate,Integer terminalId);
    public int getTmnClpKeyCount(JdbcTemplate jdbcTemplate);
    public int getAllTnmClpKeyCount(JdbcTemplate jdbcTemplate);
    public List<TmnClpKey> getTmnClpkeyListByC(JdbcTemplate jdbcTemplate,Integer start,Integer end);
    
     public int insertTmnClpKey(JdbcTemplate jdbcTemplate,Integer terminal_id,Integer key_type,Date create_time,Date end_time, String public_key, String key_sign);
}
