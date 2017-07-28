package com.bestinfo.dao.impl.game;

import com.bestinfo.bean.game.GameRiskRule;
import com.bestinfo.dao.game.IGameRiskRuleDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author hhhh
 */
@Repository
public class GameRiskRuleDaoImpl extends BaseDaoImpl implements IGameRiskRuleDao {

    /**
     * 批量根据游戏编号,控制分组修改初始风险奖金、每增加金额、每增加奖金
     */
    public static final String UPDATE_GAMERISKRULE_BY_ID = "update T_game_risk_rule set control_type = ?,ration_prize = ?, increment_money = ?, increment_prize = ? where game_id = ? and group_id = ?";

    /**
     * 批量修改游戏-风险控制规则表信息
     *
     * @param jdbcTemplate
     * @param grrList
     * @return
     */
    @Override
    public int updateBatchGameRiskRule(JdbcTemplate jdbcTemplate, final List<GameRiskRule> grrList) {
        try {
            jdbcTemplate.batchUpdate(UPDATE_GAMERISKRULE_BY_ID, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    GameRiskRule grr = grrList.get(i);
                    ps.setInt(1, grr.getControl_type());
                    ps.setBigDecimal(2, grr.getRation_prize());
                    ps.setBigDecimal(3, grr.getIncrement_money());
                    ps.setBigDecimal(4, grr.getIncrement_prize());
                    ps.setInt(5, grr.getGame_id());
                    ps.setInt(6, grr.getGroup_id());
                }

                @Override
                public int getBatchSize() {
                    return grrList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("when batch modify game risk rule error.", e);
            return -1;
        }
    }

}
