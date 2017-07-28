package com.bestinfo.dao.impl.d2code;

import com.bestinfo.bean.ticket.d2code.D2codeInfo;
import com.bestinfo.bean.ticket.d2code.GameAddInfo;
import com.bestinfo.bean.ticket.d2code.TProvKey;
import com.bestinfo.dao.d2code.IGameD2codeInfoDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public class GameD2codeDaoImpl extends BaseDaoImpl implements IGameD2codeInfoDao {

    /**
     * EB修改游戏规则sql
     */
    private static final String EB_MODIFY_GAME_ADD_INFO = "update T_game_add_info set END_DRAW_ID=?,D2CODE_ID=?,IF_ENC=?,KEY_ID=? where game_id=? and BEGIN_DRAW_ID=?";

    private static final String EB_MODIFY_D2CODE_INFO = "update T_d2code_info set d2code_name=?, code_method = ?, buf_len = ?, module_size = ?, err_correct_level = ?, begin_date = ?, end_date = ?, work_status = ?, code_version = ? where d2code_id=?";

    /**
     * 查询游戏附加信息列表
     */
    private static final String GET_GAMEADDINFO_LIST = "select * from t_game_add_info order by game_id ";

    /**
     * 查询二维码信息列表
     */
    private static final String GET_D2CODEINFO_LIST = "select * from t_d2code_info order by d2code_id ";

    /**
     * 新增游戏附加信息表sql
     */
    private static final String INSERT_GAMEADDINFO = "insert into t_game_add_info (game_id,begin_draw_id,END_DRAW_ID,d2code_id,IF_ENC,KEY_ID) values(?,?,?,?,?,?)";

    /**
     * 新增二维码信息表sql
     */
    private static final String INSERT_D2CODEINFO = "insert into t_d2code_info (d2code_id, d2code_name, code_method, buf_len, module_size, err_correct_level, begin_date, end_date, work_status, code_version) values(?,?,?,?,?,?,?,?,?,?)";

    public static final String LIST_GAME_ADD_INFO = "select * from t_game_add_info where game_id=? and ? BETWEEN begin_draw_id AND end_draw_id order by end_draw_id desc";

    /**
     * 通过key_id查询t_prov_key中的private_key用于二维码串做des3的加密
     */
    private static final String LIST_PROV_KEY_INFO = "select * from t_prov_key where key_id = ?";

    /**
     * 获取draw_id
     */
    private static final String GET_D2CODE_DRAW_ID = "SELECT A.* "
            + "FROM "
            + "  (SELECT *"
            + "  FROM t_game_add_info"
            + "  WHERE game_id=?"
            + "  AND ? BETWEEN begin_draw_id AND end_draw_id"
            + "  ORDER BY end_draw_id DESC"
            + "  ) A,"
            + "  (select * from t_game_add_info where game_id=?"
            + "  and ? between begin_draw_id and end_draw_id"
            + "  order by begin_draw_id desc"
            + "  ) B "
            + "WHERE A.begin_draw_id = b.begin_draw_id "
            + "and a.end_draw_id = b.end_draw_id";

    /**
     * 修改游戏附加信息
     *
     * @param jdbcTemplate
     * @param gameAddInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int ebmodifyGameAddInfo(JdbcTemplate jdbcTemplate, GameAddInfo gameAddInfo) {
        int result;
        try {
            result = jdbcTemplate.update(EB_MODIFY_GAME_ADD_INFO, new Object[]{
                gameAddInfo.getEnd_draw_id(),
                gameAddInfo.getD2code_id(),
                gameAddInfo.getIf_enc(),
                gameAddInfo.getKey_id(),
                gameAddInfo.getGame_id(),
                gameAddInfo.getBegin_draw_id()
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
     * 修改二维码信息
     *
     * @param jdbcTemplate
     * @param d2codeInfo
     * @return
     */
    @Override
    public int ebmodifyD2codeInfo(JdbcTemplate jdbcTemplate, D2codeInfo d2codeInfo) {
        int result;
        try {
            result = jdbcTemplate.update(EB_MODIFY_D2CODE_INFO, new Object[]{
                d2codeInfo.getD2code_name(),
                d2codeInfo.getCode_method(),
                d2codeInfo.getBuf_len(),
                d2codeInfo.getModule_size(),
                d2codeInfo.getErr_correct_level(),
                d2codeInfo.getBegin_date(),
                d2codeInfo.getEnd_date(),
                d2codeInfo.getWork_status(),
                d2codeInfo.getCode_version(),
                d2codeInfo.getD2code_id()
            });
            return result;
        } catch (Exception ex) {
            logger.error("ex :", ex);
            SQLException sqle = (SQLException) ex.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -1;
        }
    }

    /**
     * 查询游戏附加信息数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据
     */
    @Override
    public List<GameAddInfo> selectGameAddInfo(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_GAMEADDINFO_LIST, null, GameAddInfo.class);
    }

    /**
     * 查询二维码信息数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据
     */
    @Override
    public List<D2codeInfo> selectD2codeInfo(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_D2CODEINFO_LIST, null, D2codeInfo.class);
    }

    /**
     * 新增游戏附加信息
     *
     * @param jdbcTemplate
     * @param gameaddInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int insertGameAddInfo(JdbcTemplate jdbcTemplate, GameAddInfo gameaddInfo) {
        int result;
        try {
            result = jdbcTemplate.update(INSERT_GAMEADDINFO, new Object[]{
                gameaddInfo.getGame_id(),
                gameaddInfo.getBegin_draw_id(),
                gameaddInfo.getEnd_draw_id(),
                gameaddInfo.getD2code_id(),
                gameaddInfo.getIf_enc(),
                gameaddInfo.getKey_id()
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
     * 新增二维码信息
     *
     * @param jdbcTemplate
     * @param d2codeInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int insertD2codeInfo(JdbcTemplate jdbcTemplate, D2codeInfo d2codeInfo) {
        int result;
        try {
            result = jdbcTemplate.update(INSERT_D2CODEINFO, new Object[]{
                d2codeInfo.getD2code_id(),
                d2codeInfo.getD2code_name(),
                d2codeInfo.getCode_method(),
                d2codeInfo.getBuf_len(),
                d2codeInfo.getModule_size(),
                d2codeInfo.getErr_correct_level(),
                d2codeInfo.getBegin_date(),
                d2codeInfo.getEnd_date(),
                d2codeInfo.getWork_status(),
                d2codeInfo.getCode_version()
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
     * 获取draw_id
     *
     * @param jdbcTemplate
     * @param betDraw
     * @return
     */
    @Override
    public GameAddInfo getDrawId(JdbcTemplate jdbcTemplate, int betDraw, int gameId) {
        return this.queryForObject(jdbcTemplate, GET_D2CODE_DRAW_ID, new Object[]{gameId, betDraw, gameId, betDraw}, GameAddInfo.class);
    }

    /**
     * 根据投注期号获取游戏二维码信息列表
     *
     * @param jdbcTemplate
     * @param gameId
     * @param betDraw
     * @return
     */
    @Override
    public List<GameAddInfo> listGameAddByBetDrawId(JdbcTemplate jdbcTemplate, int gameId, int betDraw) {
        return queryForList(jdbcTemplate, LIST_GAME_ADD_INFO, new Object[]{gameId, betDraw}, GameAddInfo.class);
    }

    @Override
    public List<TProvKey> listProvKeyInfoByKeyId(JdbcTemplate jdbcTemplate, int keyId) {
        return queryForList(jdbcTemplate, LIST_PROV_KEY_INFO, new Object[]{keyId}, TProvKey.class);
    }
}
