/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.impl.clpdata;

import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.dao.clpdata.IGameDrawInfosDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public class GameDrawInfosDaoImpl extends BaseDaoImpl implements IGameDrawInfosDao{
/**
     * 查询ticketBet信息sql
     */
    private static final String QUERY_GAME_DRAW_INFO_LIST = "select * from t_game_draw_info where game_id=? and draw_id=?";
    private static final String QUERY_GAME_DRAW_INFO_STATUS = "select process_status from t_game_draw_info where game_id=? and draw_id=?";
    @Override
    public List<GameDrawInfo> getGameDrawInfoList(JdbcTemplate jdbcTemplate, Integer game_id, Integer draw_id) {
        List<GameDrawInfo> gdi= this.queryForList(jdbcTemplate, QUERY_GAME_DRAW_INFO_LIST, new Object[]{game_id,draw_id}, GameDrawInfo.class);
        return gdi;
    }

    @Override
    public int getGameDrawInfoStatus(JdbcTemplate jdbcTemplate, Integer game_id, Integer draw_id) {
        int DrawStatus =this.queryForInteger(jdbcTemplate, QUERY_GAME_DRAW_INFO_STATUS, new Object[]{game_id,draw_id});
        return DrawStatus;
    }
    
}
