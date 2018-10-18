/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.game;

import com.bestinfo.bean.game.TicketBulletin;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 查询彩票公告 t_ticket_bulletin
 *
 * @author YangRong
 */
public interface ITicketBulletinDao {

    /**
     * 获取work_status =1 的彩票公告列表 ,order by game_id,line_no
     *
     * @param jdbcTemplate
     * @return
     */
    public List<TicketBulletin> getWorkBulletinWithOrder(JdbcTemplate jdbcTemplate,int workStaus);
}
