/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.h5.monitor;

import com.bestinfo.bean.h5.monitor.HMonitorGame;
import com.bestinfo.bean.h5.monitor.HMonitorKenoGame;
import com.bestinfo.bean.h5.monitor.HMonitorKenoGamePrize;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 监控 接口
 *
 * @author Administrator
 */
public interface IMonitorKenoGameDao {

    /**
     * 快开游戏监控--游戏基本信息
     *
     * @return
     */
    public List<HMonitorKenoGame> getMonitorKenoGameData(JdbcTemplate jdbcTemplate);
    /***
     * 当前期名和开奖时间
     * @param jdbcTemplate
     * @return 
     */
    public List<HMonitorKenoGame> getMonitorKenoGameCurDrawData(JdbcTemplate jdbcTemplate);
    /***
     * 检查当天的游戏是否有异常
     * @param jdbcTemplate
     * @param game_id
     * @return 
     */
    public int getMonitorKenoGameCurDrawIsException(JdbcTemplate jdbcTemplate,Integer game_id);
    /***
     * 检查当前期的上一期id
     * @param jdbcTemplate
     * @param game_id
     * @return 
     */
    public int getMonitorKenoGameLastDraw(JdbcTemplate jdbcTemplate,Integer game_id);
    /***
     * 查询上一期的开奖号码
     * @param jdbcTemplate
     * @param game_id
     * @param keno_draw_id
     * @return 
     */
    public List<HMonitorKenoGame> getMonitorKenoGameLuckyNo(JdbcTemplate jdbcTemplate,Integer game_id);
    /**
     * 快开游戏监控--快开游戏中大奖区域分布
     *
     * @return
     */
    public List<HMonitorKenoGamePrize> getMonitorKenoGamePrizeData(JdbcTemplate jdbcTemplate);
    /**
     * 获取当天（7,9）游戏的中奖信息
     */
    public List<HMonitorGame> getHMonitorGameList(JdbcTemplate jdbcTemplate,Integer drawId,Integer gameId);
    /**
     * 获取当天（快乐10分或快乐彩）的总销售量
     * @param jdbcTemplate
     * @param drawId
     * @param gameId
     * @return 
     */
    public Integer getHMonitorGameCurDaySumSale(JdbcTemplate jdbcTemplate,Integer drawId,Integer gameId);
}
