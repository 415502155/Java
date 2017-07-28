package com.bestinfo.dao.game;

import com.bestinfo.bean.game.GameSignInfo;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 投注符号
 *
 * @author hhhh
 */
public interface IGameSignInfoDao {

    /**
     * 获取投注符号信息列表
     *
     * @param jdbcTemplate
     * @return
     */
    public List<GameSignInfo> selectGameSignInfo(JdbcTemplate jdbcTemplate);

    /**
     * 修改投注符号
     *
     * @param jdbcTemplate
     * @param gameSignInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    public int updateGameSignInfo(JdbcTemplate jdbcTemplate, GameSignInfo gameSignInfo);

}
