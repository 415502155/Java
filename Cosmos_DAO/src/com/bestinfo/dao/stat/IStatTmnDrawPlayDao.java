package com.bestinfo.dao.stat;

import com.bestinfo.bean.stat.StatTmnDrawPlay;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 结算统计表-站点玩法期统计(T_stat_tmn_draw_play)
 */
public interface IStatTmnDrawPlayDao {

    /**
     * 批量插入统计记录
     *
     * @param jdebTemplate
     * @param statTmnDrawList
     * @return
     */
    public int mergeStatTmnDrawPlay(JdbcTemplate jdebTemplate, List<StatTmnDrawPlay> statTmnDrawList);

    /**
     * 从t_ticket_bet中统计每个终端每个玩法的销量
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    public List<StatTmnDrawPlay> getStatFromTicketBet(JdbcTemplate jdbcTemplate, int game_id, int draw_id);

}
