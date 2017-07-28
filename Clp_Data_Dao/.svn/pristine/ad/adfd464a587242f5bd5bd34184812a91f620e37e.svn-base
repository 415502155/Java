package com.bestinfo.dao.impl.game;

import com.bestinfo.bean.game.GameInfo;
import com.bestinfo.dao.game.IGameInfoDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.page.Pagination;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author yangyuefu
 */
@Repository
public class GameInfoDaoImpl extends BaseDaoImpl implements IGameInfoDao {

//    private final Logger logger = Logger.getLogger(GameInfoDaoImpl.class);
    /**
     * 根据游戏id获取游戏sql
     */
    private static final String GET_GAMEINFO_BY_GAME_ID = "select game_id,game_type,game_name,short_name,game_code,play_num,repeat_select,open_max_no,open_min_blue_no,open_max_blue_no,open_min_no,open_basic_num,open_special_num,open_blue_num,lucky_no_group,open_num,prize_class_number,fix_prize_class_number,center_max_cash_class,center_max_cash_money,department_max_cash_class,department_max_cash_money,terminal_max_cash_class,terminal_max_cash_money,cur_draw_id,draw_period_type,draw_period,draw_time,cash_period_day,bet_line_way,single_stake_num,multi_draw_number,union_type,used_mark,undo_permit,sale_mark,cash_mark,data_save_day,game_version,terminal_bet_money,game_control_type,control_group_num,bind_game_id,cash_method,prize_precision,init_time,stat_time,begin_time,end_time,keno_game,keno_draw_num,draw_length,multi_keno_num,next_draw_time,luckyno_time,bulletin_time,re_bulletin_time,calc_method,jackpot_method,openprize_method,prep_draw_num,open_configure_id  from t_game_info where game_id=?";

    /**
     * 根据游戏code获取游戏sql
     */
    private static final String GET_GAMEINFO_BY_GAME_CODE = "select game_id,game_type,game_name,short_name,game_code,play_num,repeat_select,open_max_no,open_min_blue_no,open_max_blue_no,open_min_no,open_basic_num,open_special_num,open_blue_num,lucky_no_group,open_num,prize_class_number,fix_prize_class_number,center_max_cash_class,center_max_cash_money,department_max_cash_class,department_max_cash_money,terminal_max_cash_class,terminal_max_cash_money,cur_draw_id,draw_period_type,draw_period,draw_time,cash_period_day,bet_line_way,single_stake_num,multi_draw_number,union_type,used_mark,undo_permit,sale_mark,cash_mark,data_save_day,game_version,terminal_bet_money,game_control_type,control_group_num,bind_game_id,cash_method,prize_precision,init_time,stat_time,begin_time,end_time,keno_game,keno_draw_num,draw_length,multi_keno_num,next_draw_time,luckyno_time,bulletin_time,re_bulletin_time,calc_method,jackpot_method,openprize_method,prep_draw_num,open_configure_id  from t_game_info where game_code=?";

    /**
     * 查询游戏信息列表sql 缓存同步使用
     */
    private static final String GET_GAMEINFO_LIST = "select game_id,game_type,game_name,short_name,game_code,play_num,repeat_select,open_max_no,open_min_blue_no,open_max_blue_no,open_min_no,open_basic_num,open_special_num,open_blue_num,lucky_no_group,open_num,prize_class_number,fix_prize_class_number,center_max_cash_class,center_max_cash_money,department_max_cash_class,department_max_cash_money,terminal_max_cash_class,terminal_max_cash_money,cur_draw_id,draw_period_type,draw_period,draw_time,cash_period_day,bet_line_way,single_stake_num,multi_draw_number,union_type,used_mark,undo_permit,sale_mark,cash_mark,data_save_day,game_version,terminal_bet_money,game_control_type,control_group_num,bind_game_id,cash_method,prize_precision,init_time,stat_time,begin_time,end_time,keno_game,keno_draw_num,draw_length,multi_keno_num,next_draw_time,luckyno_time,bulletin_time,re_bulletin_time,calc_method,jackpot_method,openprize_method,prep_draw_num,open_configure_id,auto_open  from t_game_info order by game_id asc";

    /**
     * 新增游戏sql
     */
    private static final String INSERT_GAMEINFO = "insert into t_game_info (game_id,game_type,game_name,short_name,game_code,play_num,repeat_select,open_max_no,open_min_blue_no,open_max_blue_no,open_min_no,open_basic_num,open_special_num,open_blue_num,lucky_no_group,open_num,prize_class_number,fix_prize_class_number,center_max_cash_class,center_max_cash_money,department_max_cash_class,department_max_cash_money,terminal_max_cash_class,terminal_max_cash_money,cur_draw_id,draw_period_type,draw_period,draw_time,cash_period_day,bet_line_way,single_stake_num,multi_draw_number,union_type,used_mark,undo_permit,sale_mark,cash_mark,data_save_day,game_version,terminal_bet_money,game_control_type,control_group_num,bind_game_id,cash_method,prize_precision,init_time,stat_time,begin_time,end_time,keno_game,keno_draw_num,draw_length,multi_keno_num,next_draw_time,luckyno_time,bulletin_time,re_bulletin_time,calc_method,jackpot_method,openprize_method,prep_draw_num,open_configure_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * 根据游戏id更新游戏sql
     */
    private static final String UPDATE_GAMEINFO = "update t_game_info set game_type=?,game_name=?,short_name=?,game_code=?,play_num=?,repeat_select=?,open_min_no=?,open_max_no=?,open_min_blue_no=?,open_max_blue_no=?,open_basic_num=?,open_special_num=?,open_blue_num=?,lucky_no_group=?,open_num=?,prize_class_number=?,fix_prize_class_number=?,center_max_cash_class=?,center_max_cash_money=?,department_max_cash_class=?,department_max_cash_money=?,terminal_max_cash_class=?,terminal_max_cash_money=?,cur_draw_id=?,draw_period_type=?,draw_period=?,draw_time=?,cash_period_day=?,bet_line_way=?,single_stake_num=?,multi_draw_number=?,union_type=?,used_mark=?,undo_permit=?,sale_mark=?,cash_mark=?,data_save_day=?,game_version=?,terminal_bet_money=?,game_control_type=?,control_group_num=?,bind_game_id=?,cash_method=?,prize_precision=?,init_time=?,stat_time=?,begin_time=?,end_time=?,keno_game=?,keno_draw_num=?,draw_length=?,multi_keno_num=?,next_draw_time=?,bulletin_time=?,re_bulletin_time=?,calc_method=?,jackpot_method=?,openprize_method=?,prep_draw_num=?,open_configure_id=? where game_id=?";

    /**
     * 根据游戏Id获取keno期数sql
     */
    private static final String GET_KENO_DRAW_NUM_BYID = "select keno_draw_num from t_game_info where game_id = ?";

    /**
     * EB修改游戏规则sql
     */
    private static final String EB_MODIFY_GAME_INFO = "update t_game_info set terminal_max_cash_money = ?,draw_period=?,draw_time=?,cash_period_day=?,multi_draw_number=?,data_save_day=?,terminal_bet_money=?,prep_draw_num=?,luckyno_time=?,open_configure_id=?,game_control_type=?,used_mark=? where game_id = ?";
  
    private static final String UPDATE_GAMEINFO_CurDrawId = "update t_game_info set cur_draw_id=? where game_id=?";
    /**
     * 根据游戏id获取游戏
     *
     * @param jdbcTemplate
     * @param gameId
     * @return
     */
    @Override
    public GameInfo getGameInfoByGameId(JdbcTemplate jdbcTemplate, int gameId) {
        return queryForObject(jdbcTemplate, GET_GAMEINFO_BY_GAME_ID, new Object[]{gameId}, GameInfo.class);
    }

    /**
     * 根据游戏code获取游戏
     *
     * @param jdbcTemplate
     * @param gameCode
     * @return
     */
    @Override
    public GameInfo getGameInfoByGameCode(JdbcTemplate jdbcTemplate, String gameCode) {
        return queryForObject(jdbcTemplate, GET_GAMEINFO_BY_GAME_CODE, new Object[]{gameCode}, GameInfo.class);
    }

    /**
     * 查询游戏信息数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据
     */
    @Override
    public List<GameInfo> selectGameInfo(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_GAMEINFO_LIST, null, GameInfo.class);
    }

    /**
     * 新增游戏
     *
     * @param jdbcTemplate
     * @param gameInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int insertGameInfo(JdbcTemplate jdbcTemplate, GameInfo gameInfo) {
        int result;
        try {
            result = jdbcTemplate.update(INSERT_GAMEINFO, new Object[]{
                gameInfo.getGame_id(),
                gameInfo.getGame_type(),
                gameInfo.getGame_name(),
                gameInfo.getShort_name(),
                gameInfo.getGame_code(),
                gameInfo.getPlay_num(),
                gameInfo.getRepeat_select(),
                gameInfo.getOpen_min_no(),
                gameInfo.getOpen_max_no(),
                gameInfo.getOpen_min_blue_no(),
                gameInfo.getOpen_max_blue_no(),
                gameInfo.getOpen_basic_num(),
                gameInfo.getOpen_special_num(),
                gameInfo.getOpen_blue_num(),
                gameInfo.getLucky_no_group(),
                gameInfo.getOpen_num(),
                gameInfo.getPrize_class_number(),
                gameInfo.getFix_prize_class_number(),
                gameInfo.getCenter_max_cash_class(),
                gameInfo.getCenter_max_cash_money(),
                gameInfo.getDepartment_max_cash_class(),
                gameInfo.getDepartment_max_cash_money(),
                gameInfo.getTerminal_max_cash_class(),
                gameInfo.getTerminal_max_cash_money(),
                gameInfo.getCur_draw_id(),
                gameInfo.getDraw_period_type(),
                gameInfo.getDraw_period(),
                gameInfo.getDraw_time(),
                gameInfo.getCash_period_day(),
                gameInfo.getBet_line_way(),
                gameInfo.getSingle_stake_num(),
                gameInfo.getMulti_draw_number(),
                gameInfo.getUnion_type(),
                gameInfo.getUsed_mark(),
                gameInfo.getUndo_permit(),
                gameInfo.getSale_mark(),
                gameInfo.getCash_mark(),
                gameInfo.getData_save_day(),
                gameInfo.getGame_version(),
                gameInfo.getTerminal_bet_money(),
                gameInfo.getGame_control_type(),
                gameInfo.getControl_group_num(),
                gameInfo.getBind_game_id(),
                gameInfo.getCash_method(),
                gameInfo.getPrize_precision(),
                gameInfo.getInit_time(),
                gameInfo.getStat_time(),
                gameInfo.getBegin_time(),
                gameInfo.getEnd_time(),
                gameInfo.getKeno_game(),
                gameInfo.getKeno_draw_num(),
                gameInfo.getDraw_length(),
                gameInfo.getMulti_keno_num(),
                gameInfo.getNext_draw_time(),
                gameInfo.getBulletin_time(),
                gameInfo.getRe_bulletin_time(),
                gameInfo.getCalc_method(),
                gameInfo.getJackpot_method(),
                gameInfo.getOpenprize_method(),
                gameInfo.getPrep_draw_num(),
                gameInfo.getOpen_configure_id()
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
     * 修改游戏
     *
     * @param jdbcTemplate
     * @param gameInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int updateGameInfo(JdbcTemplate jdbcTemplate, GameInfo gameInfo) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_GAMEINFO, new Object[]{
                gameInfo.getGame_type(),
                gameInfo.getGame_name(),
                gameInfo.getShort_name(),
                gameInfo.getGame_code(),
                gameInfo.getPlay_num(),
                gameInfo.getRepeat_select(),
                gameInfo.getOpen_min_no(),
                gameInfo.getOpen_max_no(),
                gameInfo.getOpen_min_blue_no(),
                gameInfo.getOpen_max_blue_no(),
                gameInfo.getOpen_basic_num(),
                gameInfo.getOpen_special_num(),
                gameInfo.getOpen_blue_num(),
                gameInfo.getLucky_no_group(),
                gameInfo.getOpen_num(),
                gameInfo.getPrize_class_number(),
                gameInfo.getFix_prize_class_number(),
                gameInfo.getCenter_max_cash_class(),
                gameInfo.getCenter_max_cash_money(),
                gameInfo.getDepartment_max_cash_class(),
                gameInfo.getDepartment_max_cash_money(),
                gameInfo.getTerminal_max_cash_class(),
                gameInfo.getTerminal_max_cash_money(),
                gameInfo.getCur_draw_id(),
                gameInfo.getDraw_period_type(),
                gameInfo.getDraw_period(),
                gameInfo.getDraw_time(),
                gameInfo.getCash_period_day(),
                gameInfo.getBet_line_way(),
                gameInfo.getSingle_stake_num(),
                gameInfo.getMulti_draw_number(),
                gameInfo.getUnion_type(),
                gameInfo.getUsed_mark(),
                gameInfo.getUndo_permit(),
                gameInfo.getSale_mark(),
                gameInfo.getCash_mark(),
                gameInfo.getData_save_day(),
                gameInfo.getGame_version(),
                gameInfo.getTerminal_bet_money(),
                gameInfo.getGame_control_type(),
                gameInfo.getControl_group_num(),
                gameInfo.getBind_game_id(),
                gameInfo.getCash_method(),
                gameInfo.getPrize_precision(),
                gameInfo.getInit_time(),
                gameInfo.getStat_time(),
                gameInfo.getBegin_time(),
                gameInfo.getEnd_time(),
                gameInfo.getKeno_game(),
                gameInfo.getKeno_draw_num(),
                gameInfo.getDraw_length(),
                gameInfo.getMulti_keno_num(),
                gameInfo.getNext_draw_time(),
                gameInfo.getBulletin_time(),
                gameInfo.getRe_bulletin_time(),
                gameInfo.getCalc_method(),
                gameInfo.getJackpot_method(),
                gameInfo.getOpenprize_method(),
                gameInfo.getPrep_draw_num(),
                gameInfo.getOpen_configure_id(),
                gameInfo.getGame_id()
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
     * 获取游戏分页列表
     *
     * @param jdbcTemplate
     * @param params
     * @return
     */
    @Override
    public Pagination<GameInfo> getGameInfoPageList(JdbcTemplate jdbcTemplate, Map<String, Object> params) {
        Map<String, Object> whereMap = getWhereStr(params);

        //sql语句
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM t_game_info WHERE 1=1 ");
        sql.append(whereMap.get("whereSql"));

        StringBuilder countSql = new StringBuilder();
        countSql.append("SELECT COUNT(*) FROM t_game_info WHERE 1=1 ");
        countSql.append(whereMap.get("whereSql"));

        //参数列表
        Object[] args = null;
        String paramStr = "";
        if (whereMap.get("whereParam") != null) {
            paramStr += whereMap.get("whereParam").toString();
        }
        if (!"".equals(paramStr)) {
            args = paramStr.split(",");
        }

        Pagination<GameInfo> page = null;
        try {
            page = queryForPage(
                                                              jdbcTemplate,
                                                              sql.toString(),
                                                              countSql.toString(),
                                                              Integer.parseInt(params.get("pageNumber").toString()),
                                                              Integer.parseInt(params.get("pageSize").toString()),
                                                              args,
                                                              GameInfo.class);
        } catch (NumberFormatException e) {
            logger.error("", e);
        }

        return page;
    }

    /**
     * 根据条件列表拼查询sql
     *
     * @param params
     * @return
     */
    private Map<String, Object> getWhereStr(Map<String, Object> params) {
        StringBuilder whereSql = new StringBuilder("");
        StringBuilder whereParam = new StringBuilder("");

        Object game_id = params.get("game_id");
        if (null != game_id) {
            whereSql.append(" AND game_id = ?");
            whereParam.append(game_id).append(",");
        }

        Object game_name = params.get("game_name");
        if (null != game_name) {
            whereSql.append(" AND game_name = ?");
            whereParam.append(game_name).append(",");
        }

        Object short_name = params.get("short_name");
        if (null != short_name) {
            whereSql.append(" AND short_name = ?");
            whereParam.append(short_name).append(",");
        }

        Object game_type = params.get("game_type");
        if (null != game_type) {
            whereSql.append(" AND game_type = ?");
            whereParam.append(game_type).append(",");
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("whereSql", whereSql.toString());
        map.put("whereParam", whereParam.toString());

        return map;
    }

    /**
     * 根据游戏Id获取该游戏的keno期数
     *
     * @param jdbcTemplate
     * @return keno期数值
     */
    @Override
    public int selectKenoDrawNumById(JdbcTemplate jdbcTemplate, int gameId) {
        return queryForObject(
                                                          jdbcTemplate,
                                                          GET_KENO_DRAW_NUM_BYID,
                                                          new Object[]{gameId},
                                                          Integer.class);
    }

    /**
     * 修改游戏eb
     *
     * @param jdbcTemplate
     * @param gameInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int ebupdateGameInfo(JdbcTemplate jdbcTemplate, GameInfo gameInfo) {
        int result;
        try {
            result = jdbcTemplate.update(EB_MODIFY_GAME_INFO, new Object[]{
                gameInfo.getTerminal_max_cash_money(),
                gameInfo.getDraw_period(),
                gameInfo.getDraw_time(),
                gameInfo.getCash_period_day(),
                gameInfo.getMulti_draw_number(),
                gameInfo.getData_save_day(),
                gameInfo.getTerminal_bet_money(),
                gameInfo.getPrep_draw_num(),
                gameInfo.getLuckyno_time(),
                gameInfo.getOpen_configure_id(),
                gameInfo.getGame_control_type(),
                gameInfo.getUsed_mark(),
                gameInfo.getGame_id()
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
   * 更新游戏当前期号
   * @param jdbcTemplate
   * @param game_id
   * @param draw_id
   * @return 
   */
    @Override
    public int updateCurDrawId(JdbcTemplate jdbcTemplate, int game_id ,int draw_id) {
        int result ;
        try{
           result = jdbcTemplate.update(UPDATE_GAMEINFO_CurDrawId, new Object[]{draw_id,game_id});
           return result;
       }catch(Exception ex){
           logger.error("ex :" ,ex);
           return -1;
       }
    }
}
