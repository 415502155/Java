/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.impl.h5.monitor;

import com.bestinfo.bean.h5.monitor.CurrentTmnDrawStat;
import com.bestinfo.dao.h5.monitor.ICurrentTmnDrawStatDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 9彩种游戏当期销量
 *
 * @author Administrator
 */
@Repository
public class MonitorCurrentTmnDrawStatDaoImpl extends BaseDaoImpl implements ICurrentTmnDrawStatDao {

    private static final String NINE_LOTTERY_CUR_DRAW_SALES = "select sum(a.sale_money) as sale_money, a.game_id, c.draw_name, b.game_name"
            + "  from t_current_tmn_draw_stat a, T_game_info b, T_game_draw_info c"
            + " where a.game_id = b.game_id"
            + "   and a.draw_id = b.cur_draw_id"
            + "   and b.game_id = c.game_id"
            + "   and b.cur_draw_id = c.draw_id"
            + " group by a.game_id, c.draw_name, b.game_name"
            + " order by a.game_id, c.draw_name, b.game_name";

    @Override
    public List<CurrentTmnDrawStat> getCurrentTmnDrawStatSales(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, NINE_LOTTERY_CUR_DRAW_SALES, null, CurrentTmnDrawStat.class);
    }

}
