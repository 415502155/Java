/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.clpdata;

import com.bestinfo.bean.game.GameDrawInfo;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 
 * @author Administrator
 */
public interface IGameDrawInfosDao {
    /**
     * 
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * 
     * @return 
     */
    public List<GameDrawInfo> getGameDrawInfoList(JdbcTemplate jdbcTemplate,Integer game_id,Integer draw_id);
    public int getGameDrawInfoStatus(JdbcTemplate jdbcTemplate,Integer game_id,Integer draw_id);
}
