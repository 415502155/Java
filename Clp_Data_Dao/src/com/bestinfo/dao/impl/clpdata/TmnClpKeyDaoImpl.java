/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.impl.clpdata;

import com.bestinfo.bean.clpdata.TmnClpKey;
import com.bestinfo.dao.clpdata.ITmnClpKeyDao;
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
public class TmnClpKeyDaoImpl extends BaseDaoImpl implements ITmnClpKeyDao{
    /**
     * 查询t_tmn_clpkey信息sql
     */
    //select * from  t_tmn_clpkey where terminal_id=? order by create_time desc
    private static final String QUERY_TMN_CLPKEY_LIST ="select terminal_id, public_key from t_tmn_clpkey";
    private static final String QUERY_TMN_CLPKEY_LIST_BY_DATE ="select terminal_id, public_key from T_TMN_CLPKEY t where to_char(t.create_time,'YYYY-MM-DD') = ?";
    private static final String QUERY_TMN_CLPKEY_ENTITY = "select public_key from (select public_key from  t_tmn_clpkey where terminal_id=? order by create_time desc) where rownum=1";
    private static final String QUERY_TMN_CLPKEY_COUNT ="select count(*) from (select terminal_id from t_tmn_clpkey group by terminal_id)";
    private static final String QUERY_ALL_TMN_CLPKEY_COUNT ="select count(*) from T_TMN_CLPKEY t";
    private static final String QUERY_ONE_DAY_TMN_CLPKEY_COUNT ="select count(*) from T_TMN_CLPKEY t where to_char(t.create_time,'YYYY-MM-DD') = ?";
     private static final String QUERY_TMN_CLPKEY_LIST_BY_C ="select terminal_id, public_key from (select terminal_id, public_key,rownum as rn from t_tmn_clpkey ) where rn> ? and rn<? order by terminal_id";
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
            logger.error("getTmnClpKeyCount ex :", e);
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
            return  this.queryForInteger(jdbcTemplate, QUERY_ONE_DAY_TMN_CLPKEY_COUNT, new Object[]{time});
        } catch (Exception e) {
            logger.error("getOneDayTnmClpKeyCount ex :", e);
            return 0;
        }    }

    @Override
    public List<TmnClpKey> getTmnClpkeyList(JdbcTemplate jdbcTemplate, Integer start, Integer end) {
        try {
            return  this.queryForList(jdbcTemplate, QUERY_TMN_CLPKEY_LIST_BY_C, new Object[]{start,end}, TmnClpKey.class);
        } catch (Exception e) {
            logger.error("getTmnClpkeyList ex :", e);
            return null;
        }
    }

    @Override
    public List<TmnClpKey> getTmnClpkeyListByTms(JdbcTemplate jdbcTemplate, Integer start, Integer end) {
        try {
            return  this.queryForList(jdbcTemplate, QUERY_TMN_CLPKEY_LIST_BY_C, new Object[]{start,end}, TmnClpKey.class);
        } catch (Exception e) {
            logger.error("getTmnClpkeyListByTms ex :", e);
            return null;
        }
    }
    
}
