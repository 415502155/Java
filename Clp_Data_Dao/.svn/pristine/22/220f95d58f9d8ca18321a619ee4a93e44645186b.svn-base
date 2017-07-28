package com.bestinfo.dao.impl.game;

import com.bestinfo.bean.encoding.GameBetMode;
import com.bestinfo.dao.game.IGameBetModeDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.Collections;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 游戏投注方式实现类
 *
 * @author shange
 */
@Repository
public class GameBetModeDaoImpl extends BaseDaoImpl implements IGameBetModeDao {

//    private final Logger logger = Logger.getLogger(GameBetModeDaoImpl.class);
    private static final String SELECT_GameBetMode = "select bet_mode,bet_mode_name,work_status from t_game_bet_mode";

    /**
     * 获取所有投注方式
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<GameBetMode> findAllGameBetMode(JdbcTemplate jdbcTemplate) {
        try {
            return this.queryForList(jdbcTemplate, SELECT_GameBetMode, null, GameBetMode.class);
        } catch (Exception e) {
            logger.error("GameBetModeDaoImpl findAllGameBetMode ex :", e);
            return Collections.emptyList();
        }
    }

}
