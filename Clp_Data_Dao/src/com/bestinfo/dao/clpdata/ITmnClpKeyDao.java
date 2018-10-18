/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.clpdata;

import com.bestinfo.bean.clpdata.TmnClpKey;
import java.util.Date;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 
 * @author Administrator
 */
public interface ITmnClpKeyDao {
    /***
     * 
     * @param jdbcTemplate
     * @param terminalId
     * @return 
     */
    public List<TmnClpKey> getTmnClpkeyList(JdbcTemplate jdbcTemplate);
    public List<TmnClpKey> getTmnClpkeyListByDate(JdbcTemplate jdbcTemplate,String time);
    public int getOneDayTnmClpKeyCount(JdbcTemplate jdbcTemplate,String time);
    public TmnClpKey getTmnClpKey(JdbcTemplate jdbcTemplate,Integer terminalId);
    public int getTmnClpKeyCount(JdbcTemplate jdbcTemplate);
    public int getAllTnmClpKeyCount(JdbcTemplate jdbcTemplate);
    
    public List<TmnClpKey> getTmnClpkeyList(JdbcTemplate jdbcTemplate,Integer start,Integer end);       
    public List<TmnClpKey> getTmnClpkeyListByTms(JdbcTemplate jdbcTemplate,Integer start,Integer end);
}
