package com.bestinfo.dao.impl.game;

import com.bestinfo.bean.game.PlayBetMode;
import com.bestinfo.dao.game.IPlayBetModeDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 游戏开奖次数
 *
 * @author chenliping
 */
@Repository
public class PlayBetModeDaoImpl extends BaseDaoImpl implements IPlayBetModeDao {

//    private final Logger logger = Logger.getLogger(PlayBetModeDaoImpl.class);
    private static final String SELECT_PLAYBETMODE_LIST_BY_ID = "select game_id,play_id,bet_mode,work_status from t_play_bet_mode where game_id = ? and play_id = ?";

    private static final String SELECT_PLAYBETMODE_LIST = "select game_id,play_id,bet_mode,work_status from t_play_bet_mode";

    private static final String UPDATE_PLAYBETMODE = "update t_play_bet_mode set  terminal_serial_no = ?, terminal_phy_id = ?, terminal_initial_time = ? , safe_card_id = ? , city_id = ?, district_id = ?, station_name = ?, terminal_address = ?, station_phone = ?, owner_name = ?, owner_phone = ?, linkman = ?, linkman_phone = ?, regist_date = ?, software_id = ?, upgrade_mark = ?, software_version = ?, terminal_type = ?, terminal_status = ?, abstract_type = ?, tmn_sale_deduct = ?, tmn_cash_deduct = ?, comm_type = ?, dial_name = ?, dial_pwd = ?, account_id = ?, dealer_id = ?, terminal_syn_no = ?  where terminal_id = ?";

    /**
     * 更新玩法投注方式配置
     *
     * @param jdbcTemplate
     * @param pm
     */
    @Override
    public int updatePlayBetMode(JdbcTemplate jdbcTemplate, PlayBetMode pm) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_PLAYBETMODE, new Object[]{
                pm.getPlay_id(),
                pm.getBet_mode(),
                pm.getWork_status(),
                pm.getGame_id()
            });
            return result;
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 查询玩法投注方式配置数据列表
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<PlayBetMode> selectPlayBetModeList(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, SELECT_PLAYBETMODE_LIST, null, PlayBetMode.class);
    }

    /**
     * 根据游戏Id和玩法Id查询玩法投注方式配置数据列表
     *
     * @param jdbcTemplate
     * @param gameId
     * @param playId
     * @return
     */
    @Override
    public List<PlayBetMode> selectPlayBetModeListById(JdbcTemplate jdbcTemplate, int gameId, int playId) {
        return this.queryForList(jdbcTemplate, SELECT_PLAYBETMODE_LIST_BY_ID, new Object[]{gameId, playId}, PlayBetMode.class);
    }

}
