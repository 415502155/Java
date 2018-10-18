package com.bestinfo.dao.business;

import com.bestinfo.bean.business.TmnPrivilege;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 投注终端特权
 *
 * @author
 */
public interface ITmnPrivilegeDao {

    /**
     * 查询投注终端特权的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    public List<TmnPrivilege> selectTmnPrivilege(JdbcTemplate jdbcTemplate);

    /**
     * 根据game_id、terminal_id获取某个终端某个游戏的特权信息
     *
     * @param jdbcTemplate
     * @param game_id
     * @param terminal_id
     * @return 列表数据集合
     */
    public TmnPrivilege getPrivilegeByGameAndTerminal(JdbcTemplate jdbcTemplate, int game_id, int terminal_id);

    /**
     * 根据game_id查询投注终端特权的数据列表
     *
     * @param jdbcTemplate
     * @param game_id
     * @return 列表数据集合
     */
    public List<TmnPrivilege> selectPrivilegeByGame(JdbcTemplate jdbcTemplate, int game_id);

    /**
     * 根据terminal_id查询投注终端特权的数据列表
     *
     * @param jdbcTemplate
     * @param terminal_id
     * @return 列表数据集合
     */
    public List<TmnPrivilege> selectPrivilegeByTerminal(JdbcTemplate jdbcTemplate, int terminal_id);

//    /**
//     * 修改投注终端特权数据
//     *
//     * @param jdbcTemplate
//     * @param tp
//     * @return
//     */
//    public int updateTmnPrivilege(JdbcTemplate jdbcTemplate, TmnPrivilege tp);

    /**
     * 批量插入终端特权数据
     *
     * @param jdbcTemplate
     * @param tpList
     * @return 0-成功 -1(失败)
     */
    public int insertTmnPrivilege(JdbcTemplate jdbcTemplate, final List<TmnPrivilege> tpList);

    /**
     * 批量修改某个终端的终端特权数据
     *
     * @param jdbcTemplate
     * @param tpList
     * @return 0-成功 -1(失败)
     */
    public int updateBatchTmnPrivilege(JdbcTemplate jdbcTemplate, final List<TmnPrivilege> tpList);

    /**
     * 根据投注机号删除它下面的特权数据
     *
     * @param jdbcTemplate
     * @param tmnId
     * @return >0(成功) -1（失败）
     */
    public int deleteTmnPrivilegeByTmnId(JdbcTemplate jdbcTemplate, int tmnId);

    /**
     * 更新投注机游戏特权开奖封机字段，根据游戏编号&&终端编号
     *
     * @param jdbcTemplate
     * @param game_id
     * @param gameStop
     * @return
     */
    public int updateGameStop(JdbcTemplate jdbcTemplate, int game_id, int gameStop);
}
