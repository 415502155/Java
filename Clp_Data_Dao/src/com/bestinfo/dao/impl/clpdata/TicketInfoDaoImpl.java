/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.impl.clpdata;

import com.bestinfo.bean.ticket.TicketBet;
import com.bestinfo.dao.clpdata.ITicketInfoDao;
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
public class TicketInfoDaoImpl extends BaseDaoImpl implements ITicketInfoDao{
/**
     * 查询ticketBet信息sql
     */
    private static final String QUERY_TACKET_BET_INFO_LIST = "select * from t_ticket_bet where game_id=? and draw_id=?";
    private static final String QUERY_TACKET_BET_INFO_COUNT = "select count(*) from t_ticket_bet where game_id=? and draw_id=?";
    private static final String QUERY_TACKET_BET_INFO_PAGE="SELECT *  FROM (SELECT ROWNUM AS rowno, t.* FROM t_ticket_bet t WHERE game_id=? and draw_id=? and  ROWNUM <= ?  ) table_alias WHERE table_alias.rowno >= ?";
    private static final String QUERY_TACKET_BET_INFO_MAX_SEQUENCEID="select max(sequence_id) from t_ticket_bet where game_id=? and draw_id=?";
    
    private static final String QUERY_TACKET_BET_INFO_LIST_BY_NUM = "select game_id,draw_id,play_id,bet_money from t_ticket_bet where rownum between ? and ?";
    @Override
    public List<TicketBet> getTicketInfoList(JdbcTemplate jdbcTemplate,Integer game_id, Integer draw_id) {
        try {
            return  this.queryForList(jdbcTemplate, QUERY_TACKET_BET_INFO_LIST, new Object[]{game_id,draw_id}, TicketBet.class);
        } catch (Exception e) {
            logger.error("GetTicketInfoList ex :", e);
            return Collections.emptyList();
        }
    }

//    @Override
//    public String getTicketInfoByAccountId(JdbcTemplate jdbcTemplate, String account_id) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public List<TicketBet> getTicketInfoPage(JdbcTemplate jdbcTemplate, Integer game_id, Integer draw_id, Integer ends, Integer starts) {
        try {
            return  this.queryForList(jdbcTemplate, QUERY_TACKET_BET_INFO_PAGE, new Object[]{game_id,draw_id,ends,starts}, TicketBet.class);
        } catch (Exception e) {
            logger.error("GetTicketInfoList ex :", e);
            return Collections.emptyList();
        }
    }

    @Override
    public int getTicketInfoCount(JdbcTemplate jdbcTemplate, Integer game_id, Integer draw_id) {
        try {
            logger.info("youxi  banhao:"+game_id+"jiag qi"+draw_id);
            return  this.queryForInteger(jdbcTemplate, QUERY_TACKET_BET_INFO_COUNT, new Object[]{game_id,draw_id});
        } catch (Exception e) {
            logger.error("GetTicketInfoList ex :", e);
            return 0;
        }
    }

    @Override
    public int getTicketBetInfoMaxSequenceId(JdbcTemplate jdbcTemplate, Integer game_id, Integer draw_id) {
        try {
                    return  this.queryForInteger(jdbcTemplate, QUERY_TACKET_BET_INFO_MAX_SEQUENCEID, new Object[]{game_id,draw_id});
                } catch (Exception e) {
                    logger.error("GetTicketInfoList ex :", e);
                    return 0;
            }    
    }

    @Override
    public List<TicketBet> getTicketInfoLists(JdbcTemplate jdbcTemplate,Integer start, Integer rownum) {
        try {
            logger.info("jin dao :"+start+"-"+rownum);
            return this.queryForList(jdbcTemplate, QUERY_TACKET_BET_INFO_LIST_BY_NUM, new Object[]{start,rownum}, TicketBet.class);
        } catch (Exception e) {
            logger.info("getTicketInfoList exception:"+e);
           return Collections.emptyList();
        }
         
    }
    
}
