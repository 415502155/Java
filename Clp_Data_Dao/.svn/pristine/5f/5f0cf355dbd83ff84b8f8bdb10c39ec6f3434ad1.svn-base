package com.bestinfo.dao.system;

import com.bestinfo.bean.fs.MonServerInfo;
import com.bestinfo.bean.fs.MonServerType;
import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.bean.game.GameKDrawInfo;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author YangRong
 */
public interface IServerMonitorDao {

    public List<MonServerInfo> getMonServerInfo(JdbcTemplate jdbcTemplate);

    public List<MonServerInfo> getMonServerInfoByType(JdbcTemplate jdbcTemplate, int serverType);

    public List<MonServerType> selectMonServerType(JdbcTemplate jdbcTemplate);

    public Integer getKenoTotalTicket(JdbcTemplate jdbcTemplate, int gameId, int drawId, int kdrawId);

    public Integer getTotalTicket(JdbcTemplate jdbcTemplate, int gameId, int drawId);

    public List<GameDrawInfo> getGameDrawListBetween2Status(JdbcTemplate jdbcTemplate, int statusBegin, int statusEnd);

    public List<GameKDrawInfo> getGameKdrawListBetween2Status(JdbcTemplate jdbcTemplate, int statusBegin, int statusEnd);

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
    public List<GameKDrawInfo> getKdrawByGameAndDrawBetween2Status(JdbcTemplate jdbcTemplate, int gameId, int drawId,
            int statusBegin, int statusEnd);
}
