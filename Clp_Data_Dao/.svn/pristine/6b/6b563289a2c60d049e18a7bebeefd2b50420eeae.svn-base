package com.bestinfo.dao.impl.union;

import com.bestinfo.bean.union.UnionStatPrize;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.union.IUnionStatPrizeDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author LH
 */
@Repository
public class UnionStatPrizeDaoImpl extends BaseDaoImpl implements IUnionStatPrizeDao {

    private static final String INSERT_USP = " merge into T_union_stat_prize usp using (select ? system_id, ? game_id, ? draw_id, ? class_id from dual ) src on (usp.system_id=src.system_id and usp.game_id=src.game_id and usp.draw_id=src.draw_id and usp.class_id=src.class_id) when matched then update set province_id=?,play_id=?,open_id=?,class_name=?,prize_num=?,stake_prize=? when not matched then insert (province_id,system_id,game_id,draw_id,play_id,open_id,class_id,class_name,prize_num,stake_prize) values(?,?,?,?,?,?,?,?,?,?)";

    private static final String QUERY_PRIZE_NUM= " select prize_num from t_union_stat_prize where system_id=? and game_id=? and  draw_id=? and open_id=? and class_id=? ";

    /**
     * 查询中奖数
     * @param jdbcTemplate
     * @param systemId
     * @param gameId
     * @param drawId
     * @param openId
     * @param classId
     * @return 
     */
    @Override
    public Integer queryPrizeNumByClassId(JdbcTemplate jdbcTemplate, int systemId, int gameId, int drawId, int openId, int classId) {
        return queryForInteger(jdbcTemplate, QUERY_PRIZE_NUM, new Object[]{systemId, gameId, drawId, openId, classId});
    }

    /**
     * 新增记录
     *
     * @param jdbcTemplate
     * @param uspList
     * @return 成功-受影响的记录数 失败-(-1)
     */
    @Override
    public int batchInsert(JdbcTemplate jdbcTemplate, final List<UnionStatPrize> uspList) {
        try {
            jdbcTemplate.batchUpdate(INSERT_USP, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    UnionStatPrize usp = uspList.get(i);
                    ps.setInt(1, usp.getSystem_id());
                    ps.setInt(2, usp.getGame_id());
                    ps.setInt(3, usp.getDraw_id());
                    ps.setInt(4, usp.getClass_id());
                    ps.setInt(5, usp.getProvince_id());
                    ps.setInt(6, usp.getPlay_id());
                    ps.setInt(7, usp.getOpen_id());
                    ps.setString(8, usp.getClass_name());
                    ps.setLong(9, usp.getPrize_num());
                    ps.setBigDecimal(10, usp.getStake_prize());
                    ps.setInt(11, usp.getProvince_id());
                    ps.setInt(12, usp.getSystem_id());
                    ps.setInt(13, usp.getGame_id());
                    ps.setInt(14, usp.getDraw_id());
                    ps.setInt(15, usp.getPlay_id());
                    ps.setInt(16, usp.getOpen_id());
                    ps.setInt(17, usp.getClass_id());
                    ps.setString(18, usp.getClass_name());
                    ps.setLong(19, usp.getPrize_num());
                    ps.setBigDecimal(20, usp.getStake_prize());
                }

                @Override
                public int getBatchSize() {
                    return uspList.size();
                }

            });
            return 0;
        } catch (Exception e) {
            logger.error("batch add UnionStatPrize error.", e);
            return -1;
        }
    }

}
