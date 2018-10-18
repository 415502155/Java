package com.bestinfo.dao.business;

import com.bestinfo.bean.game.TmnSerialNo;
import com.bestinfo.bean.game.TmnSerialNoG;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 投注机彩票投注流水号
 */
public interface ITmnSerialNoDao {

    /**
     * 获取指定游戏指定期的销售票数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    public long getSumTicketNo(JdbcTemplate jdbcTemplate, int gameid, int drawid);

    /**
     * 得到某个游戏某期，所有终端的彩票流水号
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    public List<TmnSerialNo> getSerialNoByGameIdAndDrawId(JdbcTemplate jdbcTemplate, int gameid, int drawid);

    /**
     * 得到某个终端中，某个游戏某个期的彩票流水号
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param terminal_id
     * @return
     */
    public TmnSerialNo getTmnSerialNoByPrimary(JdbcTemplate jdbcTemplate, int gameid, int drawid, int terminal_id);

    /**
     * 批量添加彩票流水号信息
     *
     * @param jdbcTemplate
     * @param tsnList
     * @return
     */
    public int batchAddTmnSerialNos(JdbcTemplate jdbcTemplate, final List<TmnSerialNo> tsnList);

    /**
     * 得到某个游戏某期，所有代销商终端的彩票流水号
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    public List<TmnSerialNoG> getSerialNoForDealer(JdbcTemplate jdbcTemplate, int gameid, int drawid);

    /**
     * 得到某个终端，所有代销商的彩票流水号
     *
     * @param jdbcTemplate
     * @param terminalId
     * @return
     */
    public List<TmnSerialNoG> getTmnSerialNoForDealer(JdbcTemplate jdbcTemplate, int terminalId);
    
     /**
     * 获取大厅某个游戏期的所有终端彩票流水号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param tmnType
     * @return
     */
    public List<TmnSerialNo> getAppTmnSerialNo(JdbcTemplate jdbcTemplate, int gameId, int drawId, int tmnType);
}
