package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.stat.LuckyNo;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.stat.ILuckyNoDao;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 开奖号码
 *
 * @author chenliping
 */
@Repository
public class LuckyNoDaoImpl extends BaseDaoImpl implements ILuckyNoDao {

//    private final Logger logger = Logger.getLogger(LuckyNoDaoImpl.class);
    /**
     * 获取开奖号码
     */
    private static final String GET_LUCKNO = "select game_id,draw_id,draw_name,keno_draw_id,keno_draw_name,open_id, lucky_no,lucky_no_echo,prize_no_num,prize_no,special_no_num,special_no,blue_no_num,blue_no,generate_time from t_lucky_no where game_id=? and draw_id=? and keno_draw_id=? and open_id=?";
    /**
     * 插入开奖号码
     */
    private static final String INSERT_LUCKYNO = "insert into t_lucky_no(game_id,draw_id,draw_name,keno_draw_id,keno_draw_name,open_id, lucky_no,lucky_no_echo,prize_no_num,prize_no,special_no_num,special_no,blue_no_num,blue_no,generate_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_LUCKYNO_MERGE = "merge into t_lucky_no t using (select ? game_id,? draw_id,? keno_draw_id,? open_id from dual) s on (t.game_id=s.game_id and t.draw_id=s.draw_id and t.keno_draw_id=s.keno_draw_id and t.open_id=s.open_id) when matched then update set draw_name=?,keno_draw_name=?, lucky_no=?,lucky_no_echo=?,prize_no_num=?,prize_no=?,special_no_num=?,special_no=?,blue_no_num=?,blue_no=?,generate_time=? when not matched then insert (game_id,draw_id,keno_draw_id,open_id,draw_name,keno_draw_name, lucky_no,lucky_no_echo,prize_no_num,prize_no,special_no_num,special_no,blue_no_num,blue_no,generate_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String GET_LUCKYNO_LIST = "select game_id,draw_id,draw_name,keno_draw_id,keno_draw_name,open_id, lucky_no,lucky_no_echo,prize_no_num,prize_no,special_no_num,special_no,blue_no_num,blue_no,generate_time from t_lucky_no  where game_id = ? and draw_id = ? and keno_draw_id = ? ";

    private static final String GET_LUCKYNO_LIST_BYDRAWNAME = "select game_id,draw_id,draw_name,keno_draw_id,keno_draw_name,open_id, lucky_no,lucky_no_echo,prize_no_num,prize_no,special_no_num,special_no,blue_no_num,blue_no,generate_time from t_lucky_no  where game_id = ? and draw_name between ? and ?";
    /**
     * 获取开奖号码对象
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @param open_id
     * @return
     */
    @Override
    public LuckyNo getLuckyNo(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id, int open_id) {
        return queryForObject(jdbcTemplate, GET_LUCKNO, new Object[]{game_id, draw_id, keno_draw_id, open_id}, LuckyNo.class);
    }

    /**
     * 获取开奖号码信息列表
     *
     * @param jdbcTemplate
     * @param ln
     * @return
     */
    @Override
    public int addLuckyNo(JdbcTemplate jdbcTemplate, LuckyNo ln) {
        int result;
        try {
            result = jdbcTemplate.update(INSERT_LUCKYNO, new Object[]{
                ln.getGame_id(),
                ln.getDraw_id(),
                ln.getDraw_name(),
                ln.getKeno_draw_id(),
                ln.getKeno_draw_name(),
                ln.getOpen_id(),
                ln.getLucky_no(),
                ln.getLucky_no_echo(),
                ln.getPrize_no_num(),
                ln.getPrize_no(),
                ln.getSpecial_no_num(),
                ln.getSpecial_no(),
                ln.getBlue_no_num(),
                ln.getBlue_no(),
                ln.getGenerate_time()
            });
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
     * 获取开奖号码信息列表
     *
     * @param jdbcTemplate
     * @param ln
     * @return
     */
    @Override
    public int addLuckyNoMerge(JdbcTemplate jdbcTemplate, LuckyNo ln) {
        int result;
        try {
            result = jdbcTemplate.update(INSERT_LUCKYNO_MERGE, new Object[]{
                ln.getGame_id(),
                ln.getDraw_id(),
                ln.getKeno_draw_id(),
                ln.getOpen_id(),
                ln.getDraw_name(),
                ln.getKeno_draw_name(),
                ln.getLucky_no(),
                ln.getLucky_no_echo(),
                ln.getPrize_no_num(),
                ln.getPrize_no(),
                ln.getSpecial_no_num(),
                ln.getSpecial_no(),
                ln.getBlue_no_num(),
                ln.getBlue_no(),
                ln.getGenerate_time(),     //add by lvchanrong  2015-02-02
                ln.getGame_id(),
                ln.getDraw_id(),
                ln.getKeno_draw_id(),
                ln.getOpen_id(),
                ln.getDraw_name(),
                ln.getKeno_draw_name(),
                ln.getLucky_no(),
                ln.getLucky_no_echo(),
                ln.getPrize_no_num(),
                ln.getPrize_no(),
                ln.getSpecial_no_num(),
                ln.getSpecial_no(),
                ln.getBlue_no_num(),
                ln.getBlue_no(),
                ln.getGenerate_time()
            });
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
     * 根据gameId、draw_id和kdraw_id获取开奖号码信息列表
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param kdraw_id
     * @return
     */
    @Override
    public List<LuckyNo> getLuckNoList(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int kdraw_id) {
        return this.queryForList(jdbcTemplate, GET_LUCKYNO_LIST, new Object[]{game_id, draw_id, kdraw_id}, LuckyNo.class);
    }
    /**
     * 根据game_id  开始期名和结束期名获取开奖号码列表
     * @param jdbcTemplate
     * @param game_id
     * @param begin_draw_name
     * @param end_draw_name
     * @return 
     */
    @Override
    public List<LuckyNo> getLuckyNoList(JdbcTemplate jdbcTemplate, int game_id, String begin_draw_name, String end_draw_name){
        return this.queryForList(jdbcTemplate, GET_LUCKYNO_LIST_BYDRAWNAME, new Object[]{game_id , begin_draw_name , end_draw_name}, LuckyNo.class);
    }
}
