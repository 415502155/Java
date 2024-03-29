/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.clpdata;

import com.bestinfo.bean.ticket.TicketBet;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 
 * @author Administrator
 */
public interface ITicketInfoDao {
    /***
     * 
     * @param jdbcTemplate
     * @param game_id 游戏玩法
     * @param draw_id 奖期
     * @return 
     */
    public List<TicketBet> getTicketInfoList(JdbcTemplate jdbcTemplate,Integer game_id,Integer draw_id);
    public int getTicketInfoCount(JdbcTemplate jdbcTemplate,Integer game_id,Integer draw_id);
    public List<TicketBet> getTicketInfoPage(JdbcTemplate jdbcTemplate,Integer game_id,Integer draw_id,Integer ends,Integer starts);
    public int getTicketBetInfoMaxSequenceId(JdbcTemplate jdbcTemplate,Integer game_id,Integer draw_id);
}
