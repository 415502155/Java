/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.d2code;

import com.bestinfo.bean.ticket.d2code.D2codeInfo;
import com.bestinfo.bean.ticket.d2code.GameAddInfo;
import com.bestinfo.bean.ticket.d2code.TProvKey;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Administrator
 */
public interface IGameD2codeInfoDao {

    /**
     * 修改游戏附加信息
     *
     * @param jdbcTemplate
     * @param gameAddInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    public int ebmodifyGameAddInfo(JdbcTemplate jdbcTemplate, GameAddInfo gameAddInfo);

    /**
     * 修改二维码信息
     *
     * @param jdbcTemplate
     * @param d2codeInfo
     * @return
     */
    public int ebmodifyD2codeInfo(JdbcTemplate jdbcTemplate, D2codeInfo d2codeInfo);

    /**
     * 查询游戏附加信息数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据
     */
    public List<GameAddInfo> selectGameAddInfo(JdbcTemplate jdbcTemplate);

    /**
     * 查询二维码信息数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据
     */
    public List<D2codeInfo> selectD2codeInfo(JdbcTemplate jdbcTemplate);

    /**
     * 新增游戏附加信息
     *
     * @param jdbcTemplate
     * @param gameaddInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    public int insertGameAddInfo(JdbcTemplate jdbcTemplate, GameAddInfo gameaddInfo);

    /**
     * 新增二维码信息
     *
     * @param jdbcTemplate
     * @param d2codeInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    public int insertD2codeInfo(JdbcTemplate jdbcTemplate, D2codeInfo d2codeInfo);

    /**
     * 获取draw_id
     *
     * @param jdbcTemplate
     * @param betDraw
     * @param gameId
     * @return
     */
    public GameAddInfo getDrawId(JdbcTemplate jdbcTemplate, int betDraw, int gameId);

    /**
     * 根据投注期号获取游戏二维码信息列表
     *
     * @param jdbcTemplate
     * @param gameId
     * @param betDraw
     * @return
     */
    public List<GameAddInfo> listGameAddByBetDrawId(JdbcTemplate jdbcTemplate, int gameId, int betDraw);

    /**
     * 根据key_id查询t_prov_key中的private_key用于二维码加密，使用des3进行加密
     *
     * @param jdbcTemplate
     * @param keyId
     * @return
     */
    public List<TProvKey> listProvKeyInfoByKeyId(JdbcTemplate jdbcTemplate, int keyId);
}
