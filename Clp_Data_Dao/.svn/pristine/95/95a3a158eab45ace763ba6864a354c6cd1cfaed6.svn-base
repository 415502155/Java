package com.bestinfo.dao.impl.system;

import com.bestinfo.bean.fs.MonServerInfo;
import com.bestinfo.bean.fs.MonServerType;
import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.bean.game.GameKDrawInfo;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.system.IServerMonitorDao;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 文件系统监视用DAO
 *
 * @author YangRong
 */
@Repository
public class ServerMonitorDaoImpl extends BaseDaoImpl implements IServerMonitorDao {

    private static final String SELECT_ALL_MONSERVERINFO_LIST = "select server_id,server_name,server_ip,  server_port,server_type,monitor_port,monitor_type,monitor_command,a.work_status as work_status , b.type_name as monitor_type_name,c.type_name as server_type_name from  t_monserver_info as a,t_monitor_type as b,t_monserver_type as c where  a.server_type=c.server_type and b.monitor_type=a.monitor_type   and   b.work_status=1 and c.work_status=1";

    private static final String SELECT_MONSERVERINFO_LIST = "select server_id,server_name,server_ip, server_port,server_type,monitor_port,monitor_type,monitor_command,a.work_status as work_status , b.type_name as monitor_type_name,c.type_name as server_type_name from  t_monserver_info as a,t_monitor_type as b,t_monserver_type as c where  a.server_type=c.server_type and b.monitor_type=a.monitor_type and a.server_type=? and   b.work_status=1 and c.work_status=1";
    private static final String SELECT_MONSERVER_TYPE = "select server_type ,type_name,work_status from t_monserver_type ";

    private static final String SELECT_GAME_DRAW_INFO = "select game_id,draw_id,draw_name,process_status from t_game_draw_info where process_status>=? and process_status<=?  and keno_draw_num=0 order by game_id,draw_id";

    private static final String SELECT_GAME_KDRAW_INFO = "select game_id,draw_id,keno_draw_id,keno_draw_name,keno_process_status  from t_game_kdraw_info where keno_process_status>=? and keno_process_status<=?  order by game_id,draw_id,keno_draw_id ";

    private static final String SUM_BY_GAME_DRAW_KDRAW = "select  sum(sale_ticket_num)+sum(undo_ticket_num)   from t_current_kdraw_stat where game_id=? and draw_id=? and keno_draw_id=? ";

    private static final String SUM_BY_GAME_DRAW = "select  sum(sale_ticket_num)+sum(undo_ticket_num)   from t_current_tmn_draw_stat where game_id=? and draw_id=?   ";

    private static final String GET_KDRAW_BY_GAME_DRAW_BETWEEN_STATUS = "select game_id,draw_id,keno_draw_id,keno_draw_name,keno_process_status from t_game_kdraw_info where game_id=? and draw_id=? and keno_process_status>=? and keno_process_status<=? order by game_id,draw_id,keno_draw_id";

    @Override
    public List<MonServerInfo> getMonServerInfo(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, SELECT_ALL_MONSERVERINFO_LIST, null, MonServerInfo.class);
    }

    @Override
    public List<MonServerInfo> getMonServerInfoByType(JdbcTemplate jdbcTemplate, int serverType) {
        return this.queryForList(jdbcTemplate, SELECT_MONSERVERINFO_LIST, new Object[]{serverType}, MonServerInfo.class);
    }

    @Override
    public List<MonServerType> selectMonServerType(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, SELECT_MONSERVER_TYPE, null, MonServerType.class);
    }

    @Override
    public Integer getKenoTotalTicket(JdbcTemplate jdbcTemplate, int gameId, int drawId, int kdrawId) {
//        return this.queryForInteger(jdbcTemplate, SUM_BY_GAME_DRAW_KDRAW, new Object[]{gameId, drawId, kdrawId});
        try {
            Integer reti = (Integer) jdbcTemplate.queryForObject(SUM_BY_GAME_DRAW_KDRAW, new Object[]{gameId, drawId, kdrawId}, Integer.class);
            if (reti == null) {
                reti = 0;
            }
            return reti;
        } catch (Exception e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return null;
        }
    }

    @Override
    public Integer getTotalTicket(JdbcTemplate jdbcTemplate, int gameId, int drawId) {
        try {
            Integer reti = (Integer) jdbcTemplate.queryForObject(SUM_BY_GAME_DRAW, new Object[]{gameId, drawId}, Integer.class);
            if (reti == null) {
                reti = 0;
            }
            return reti;
        } catch (Exception e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return null;
        }
//        return this.queryForInteger(jdbcTemplate, SUM_BY_GAME_DRAW, new Object[]{gameId, drawId});
//        return this.queryForObject(jdbcTemplate, SUM_BY_GAME_DRAW, new Object[]{gameId,drawId},Integer.class );
    }

    @Override
    public List<GameDrawInfo> getGameDrawListBetween2Status(JdbcTemplate jdbcTemplate, int statusBegin, int statusEnd) {
        return this.queryForList(jdbcTemplate, SELECT_GAME_DRAW_INFO, new Object[]{statusBegin, statusEnd}, GameDrawInfo.class);
    }

    @Override
    public List<GameKDrawInfo> getGameKdrawListBetween2Status(JdbcTemplate jdbcTemplate, int statusBegin, int statusEnd) {
        return this.queryForList(jdbcTemplate, SELECT_GAME_KDRAW_INFO, new Object[]{statusBegin, statusEnd}, GameKDrawInfo.class);
    }

    /**
     * 根据game_id和draw_id获取两个状态之间的快开期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param statusBegin
     * @param statusEnd
     * @return
     */
    @Override
    public List<GameKDrawInfo> getKdrawByGameAndDrawBetween2Status(JdbcTemplate jdbcTemplate, int gameId, int drawId, int statusBegin, int statusEnd) {
        return this.queryForList(jdbcTemplate, GET_KDRAW_BY_GAME_DRAW_BETWEEN_STATUS,
                new Object[]{gameId, drawId, statusBegin, statusEnd}, GameKDrawInfo.class);
    }
}
