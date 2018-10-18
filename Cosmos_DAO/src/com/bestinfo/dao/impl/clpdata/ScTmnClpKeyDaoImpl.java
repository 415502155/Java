/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.impl.clpdata;

import com.bestinfo.bean.terminal.TmnClpKey;
import com.bestinfo.dao.clpdata.ScITmnClpKeyDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.Date;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public class ScTmnClpKeyDaoImpl extends BaseDaoImpl implements ScITmnClpKeyDao{
    /**
     * 查询t_tmn_clpkey信息sql
     */
    //select * from  t_tmn_clpkey where terminal_id=? order by create_time desc
    private static final String QUERY_TMN_CLPKEY_LIST ="select  terminal_id,public_key from t_tmn_clpkey";
    private static final String QUERY_TMN_CLPKEY_LIST_BY_C ="select * from T_TMN_CLPKEY t where rownum between ? and ?";
    private static final String QUERY_TMN_CLPKEY_LIST_BY_DATE ="select * from T_TMN_CLPKEY t where to_char(t.create_time,'YYYY-MM-DD') = ?";
    private static final String QUERY_TMN_CLPKEY_ENTITY = "select public_key from (select public_key from  t_tmn_clpkey where terminal_id=? order by create_time desc) where rownum=1";
    private static final String QUERY_TMN_CLPKEY_COUNT ="select count(*) from (select terminal_id from t_tmn_clpkey group by terminal_id)";
    private static final String QUERY_ALL_TMN_CLPKEY_COUNT ="select count(*) from T_TMN_CLPKEY t";
    private static final String QUERY_ONE_DAY_TMN_CLPKEY_COUNT ="select count(*) from T_TMN_CLPKEY t where to_char(t.create_time,'YYYY-MM-DD') = ?";
    @Override
    public TmnClpKey getTmnClpKey(JdbcTemplate jdbcTemplate, Integer terminalId) {
        try {
            return  this.queryForObject(jdbcTemplate, QUERY_TMN_CLPKEY_ENTITY, new Object[]{terminalId}, TmnClpKey.class);
        } catch (Exception e) {
            logger.error("getTmnClpKey ex :", e);
            return null;
        }
    }

    @Override
    public int getTmnClpKeyCount(JdbcTemplate jdbcTemplate) {
        try {
            return  this.queryForInteger(jdbcTemplate, QUERY_TMN_CLPKEY_COUNT, null);
        } catch (Exception e) {
            logger.error("getTmnClpKeyCount ex :", e);
            return 0;
        }    
    }

    @Override
    public List<TmnClpKey> getTmnClpkeyList(JdbcTemplate jdbcTemplate) {
        try {
            return  this.queryForList(jdbcTemplate, QUERY_TMN_CLPKEY_LIST, null,TmnClpKey.class);
        } catch (Exception e) {
            logger.error("getTmnClpkeyList ex :", e);
            return null;
        } 
    }

    @Override
    public List<TmnClpKey> getTmnClpkeyListByDate(JdbcTemplate jdbcTemplate, String time) {
        try {
            return  this.queryForList(jdbcTemplate, QUERY_TMN_CLPKEY_LIST_BY_DATE, new Object[]{time}, TmnClpKey.class);
        } catch (Exception e) {
            logger.error("getTmnClpkeyListByDate ex :", e);
            return null;
        }
    }

    @Override
    public int getAllTnmClpKeyCount(JdbcTemplate jdbcTemplate) {
        try {
            return  this.queryForInteger(jdbcTemplate, QUERY_ALL_TMN_CLPKEY_COUNT, null);
        } catch (Exception e) {
            logger.error("getAllTnmClpKeyCount ex :", e);
            return 0;
        }
    }

    @Override
    public int getOneDayTnmClpKeyCount(JdbcTemplate jdbcTemplate, String time) {
        try {
            logger.info("shijian:"+time+"QUERY_SQL:"+QUERY_ONE_DAY_TMN_CLPKEY_COUNT);
            return  this.queryForInteger(jdbcTemplate, QUERY_ONE_DAY_TMN_CLPKEY_COUNT, new Object[]{time});
        } catch (Exception e) {
            logger.error("getOneDayTnmClpKeyCount ex :", e);
            return 0;
        }    }

    @Override
    public List<TmnClpKey> getTmnClpkeyListByC(JdbcTemplate jdbcTemplate, Integer start, Integer end) {
        try {
            return  this.queryForList(jdbcTemplate, QUERY_TMN_CLPKEY_LIST_BY_C, new Object[]{start,end}, TmnClpKey.class);
        } catch (Exception e) {
            logger.error("getTmnClpkeyListByC ex :", e);
            return null;
        }
    }

    @Override
    public int insertTmnClpKey(JdbcTemplate jdbcTemplate, Integer terminal_id, Integer key_type, Date create_time, Date end_time, String public_key, String key_sign) {
        int num=0;
        String sSqlCmdFormat = "INSERT INTO t_tmn_clpkey (terminal_id, key_type, create_time,end_time,public_key,key_sign) VALUES (?,?,?,?,?,?)";
        try {
            num = jdbcTemplate.update(sSqlCmdFormat, new Object[]{terminal_id, key_type, create_time,end_time,public_key,key_sign});
            System.out.println("mun:"+num);
        } catch (Exception e) {
            logger.error("insert t_tmn_clpkey info error,error info(" + e.getMessage() + ")!");
            System.out.println("insert t_tmn_clpkey info error,error info(" + e.getMessage() + ")!");
            return -1;
        }
        return num;
    }
    
}
