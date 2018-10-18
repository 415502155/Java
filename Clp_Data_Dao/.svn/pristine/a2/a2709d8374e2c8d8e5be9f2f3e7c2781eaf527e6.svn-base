package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.GameBetMode;
import com.bestinfo.dao.encoding.IGameBetMode;
import com.bestinfo.dao.impl.BaseDaoImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 游戏投注方式---编码
 *
 * @author hhhh
 */
@Repository
public class GameBetModeImpl extends BaseDaoImpl implements IGameBetMode {

    private static final String SELECT_GameBetMode_BY_ID = "select bet_mode,bet_mode_name,work_status from t_game_bet_mode where bet_mode = ?";

    /**
     * 根据Id查询投注方式信息
     *
     * @param jdbcTemplate
     * @param bet_mode
     * @return
     */
    @Override
    public GameBetMode selectGameBetModeById(JdbcTemplate jdbcTemplate, int bet_mode) {
        return this.queryForObject(jdbcTemplate, SELECT_GameBetMode_BY_ID, new Object[]{bet_mode}, GameBetMode.class);
    }

}
