/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.service.impl.clpdata;

import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.dao.clpdata.IGameDrawInfosDao;
import com.bestinfo.service.clpdata.IGameDrawInfoSer;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class GameDrawInfoSerImpl implements IGameDrawInfoSer{
    private static final Logger logger = Logger.getLogger(GameDrawInfoSerImpl.class);
    @Resource
    private JdbcTemplate termJdbcTemplate;
    @Resource
    private IGameDrawInfosDao igdid;

    @Override
    public List<GameDrawInfo> getGameDrawInfoList(Integer game_id, Integer draw_id) {
           return igdid.getGameDrawInfoList(termJdbcTemplate, game_id, draw_id);
    }

    @Override
    public int getGameDrawInfoStatus(Integer game_id, Integer draw_id) {
        return igdid.getGameDrawInfoStatus(termJdbcTemplate, game_id, draw_id);
    }
 
}
