/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.encoding;

import com.bestinfo.bean.encoding.GameBetMode;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *  游戏投注方式---编码
 * @author hhhh
 */
public interface IGameBetMode {

    /**
     * 根据Id查询投注方式信息
     * @param jdbcTemplate
     * @param bet_mode
     * @return 
     */
    public GameBetMode selectGameBetModeById(JdbcTemplate jdbcTemplate,int bet_mode);

}
