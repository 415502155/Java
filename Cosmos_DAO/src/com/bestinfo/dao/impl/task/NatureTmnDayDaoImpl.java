package com.bestinfo.dao.impl.task;

import com.bestinfo.bean.task.NatureTmnDay;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.task.INatureTmnDayDao;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 统计汇总表-自然日站点统计(T_natural_tmn_day)
 *
 * @author LH
 */
@Repository
public class NatureTmnDayDaoImpl extends BaseDaoImpl implements INatureTmnDayDao {

    private static final String SELECT_NATURETMNDAY_LIST = "select city_name,terminal_id,terminal_id as equipment_id,'GDS' as equipment_type,case a.game_id when 2 then '3D(天天彩选3)' when 3 then '快3' else game_name end as game_name,sale_money,cash_money,undo_money from t_natural_tmn_day a,t_game_info b,t_city_info c where a.game_id = b.game_id and a.city_id = c.city_id and to_char(natural_day,'yyyy-mm-dd') = to_char(sysdate-1,'yyyy-mm-dd') order by a.terminal_id,a.game_id";

    private static final String SELECT_NATURETMNDAY_LIST_DAY = "select city_name,terminal_id,terminal_id as equipment_id,'GDS' as equipment_type,case a.game_id when 2 then '3D(天天彩选3)' when 3 then '快3' else game_name end as game_name,sale_money,cash_money,undo_money from t_natural_tmn_day a,t_game_info b,t_city_info c where a.game_id = b.game_id and a.city_id = c.city_id and to_char(natural_day,'yyyy-mm-dd') = ? order by a.terminal_id,a.game_id";

    /**
     * 查询自然日站点统计数据集合
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<NatureTmnDay> getNatureTmnDayList(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate,
                SELECT_NATURETMNDAY_LIST,
                new Object[]{},
                NatureTmnDay.class);
    }

    /**
     * 查询某天的站点统计数据集合
     *
     * @param jdbcTemplate
     * @param day_str
     * @return
     */
    @Override
    public List<NatureTmnDay> getNatureTmnDayListDay(JdbcTemplate jdbcTemplate, String day_str) {
        return this.queryForList(jdbcTemplate,
                SELECT_NATURETMNDAY_LIST_DAY,
                new Object[]{day_str},
                NatureTmnDay.class);
    }

}
