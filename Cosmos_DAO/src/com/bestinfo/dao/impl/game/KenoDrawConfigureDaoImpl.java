package com.bestinfo.dao.impl.game;

import com.bestinfo.bean.game.KenoDrawConfigure;
import com.bestinfo.dao.game.IKenoDrawConfigureDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 游戏-快开游戏期参数配置表(T_keno_draw_configure)
 *
 * @author yangyuefu
 */
@Repository
public class KenoDrawConfigureDaoImpl extends BaseDaoImpl implements IKenoDrawConfigureDao {

    /**
     * 根据game_id获取配置列表
     */
    private static final String GET_CONFIGRUE_BY_GAME = "select game_id,keno_draw_no,begin_time,end_time,draw_length,multi_keno_num,luckyno_time,bulletin_time,re_bulletin_time,work_status,note from t_keno_draw_configure where game_id=?  order by keno_draw_no asc";

    /**
     * 根据game_id获取快开游戏配置列表
     *
     * @param game_id
     * @return
     */
    @Override
    public List<KenoDrawConfigure> getConfigureByGame(JdbcTemplate jdbcTemplate, int game_id) {
        return queryForList(jdbcTemplate, GET_CONFIGRUE_BY_GAME, new Object[]{game_id}, KenoDrawConfigure.class);
    }
}
