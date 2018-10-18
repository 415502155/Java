package com.bestinfo.dao.business;

import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.dao.page.Pagination;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 投注机信息
 */
public interface ITmnInfoDao {

    /**
     * 投注机增加
     *
     * @param jdbcTemplate
     * @param tin
     * @return
     */
    public int insertTmnInfo(JdbcTemplate jdbcTemplate, TmnInfo tin);

    /**
     * 查询投注机信息，条件可为投注机号 或 投注机物理卡号 或 地市编号 或 站点名称 或 户主名 或 代销商编号
     *
     * @param jdbcTemplate
     * @param params
     * @return
     */
    public Pagination<TmnInfo> getTmnInfoPageList(JdbcTemplate jdbcTemplate, Map<String, Object> params);

    /**
     * 查询投注终端的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    public List<TmnInfo> selectTmnInfo(JdbcTemplate jdbcTemplate);

    /**
     * 修改投注终端数据
     *
     * @param jdbcTemplate
     * @param ti
     * @return
     */
    public int updateTmnInfo(JdbcTemplate jdbcTemplate, TmnInfo ti);

    /**
     * 根据地市和终端机号查询终端信息列表
     *
     * @param jdbcTemplate
     * @param cityid
     * @param tmnid
     * @return
     */
    public List<TmnInfo> getTmnListByCityIdAndTmnId(JdbcTemplate jdbcTemplate, int cityid, int tmnid);

    /**
     * 根据地市、终端机号、代销商编号查询终端信息列表
     *
     * @param jdbcTemplate
     * @param cityid
     * @param tmnid
     * @param dealerid
     * @return
     */
    public List<TmnInfo> getTmnListByCityIdAndTmnIdAndDealerId(JdbcTemplate jdbcTemplate, int cityid, int tmnid, String dealerid);

    /**
     * 得到最大的投注机序号
     *
     * @param jdbcTemplate
     * @return
     */
    public int getMaxTerminalSerialNo(JdbcTemplate jdbcTemplate);

    /**
     * 获取投注机信息列表
     *
     * @param jdbcTemplate
     * @param dealer_id 代销商编号
     * @return
     */
    public List<TmnInfo> getTmnInfoListByDealerId(JdbcTemplate jdbcTemplate, String dealer_id);

    /**
     * 根据投注机号查询投注机信息
     *
     * @param jdbcTemplate
     * @param terminalId
     * @return
     */
    public TmnInfo getTmnInfoByTerminalId(JdbcTemplate jdbcTemplate, int terminalId);

    /**
     * 批量修改终端的升级标记
     *
     * @param jdbcTemplate
     * @param tiList
     * @return
     */
    public int batchUpdateUpgrade(JdbcTemplate jdbcTemplate, final List<TmnInfo> tiList);

    /**
     * 批量修改终端的通讯方式(电子辅助)
     *
     * @param jdbcTemplate
     * @param tiList
     * @return
     */
    public int batchUpdateAssist(JdbcTemplate jdbcTemplate, final List<TmnInfo> tiList);

    /**
     * 批量修改终端的升级标记及终端同步字
     *
     * @param jdbcTemplate
     * @param tiList
     * @return
     */
    public int batchUpdateUpgradeSynNo(JdbcTemplate jdbcTemplate, final List<TmnInfo> tiList);

    /**
     * 批量修改终端新通知、终端同步字
     *
     * @param jdbcTemplate
     * @param tiList
     * @return
     */
    public int batchUpdateNoticeTmnSynNo(JdbcTemplate jdbcTemplate, final List<TmnInfo> tiList);

    /**
     * 获取某地市下的所有终端
     *
     * @param jdbcTemplate
     * @param cityid
     * @return
     */
    public List<TmnInfo> getTmnListByCityId(JdbcTemplate jdbcTemplate, int cityid);

    /**
     * 获取某星级的所有终端
     *
     * @param jdbcTemplate
     * @param stationRank
     * @return
     */
    public List<TmnInfo> getTmnListByStationRank(JdbcTemplate jdbcTemplate, int stationRank);

    /**
     * 获取某地市下在用的所有终端
     *
     * @param jdbcTemplate
     * @param cityid
     * @return
     */
    public List<TmnInfo> getTmnListByCityId(JdbcTemplate jdbcTemplate, int cityid, int tmnStatus);

    /**
     * 获取终端号在指定范围内的所有终端信息
     *
     * @param jdbcTemplate
     * @param begin_terminal_id
     * @param end_terminal_id
     * @return
     */
    public List<TmnInfo> getTmnListByAreaTmnId(JdbcTemplate jdbcTemplate, int begin_terminal_id, int end_terminal_id);

    /**
     * 获取终端号在指定范围内的所有在用终端信息
     *
     * @param jdbcTemplate
     * @param begin_terminal_id
     * @param end_terminal_id
     * @param tmnStatus
     * @return
     */
    public List<TmnInfo> getTmnListByAreaTmnId(JdbcTemplate jdbcTemplate, int begin_terminal_id, int end_terminal_id, int tmnStatus);

    /**
     * 修改详细信息
     *
     * @param jdbcTemplate
     * @param tmnInfo
     * @return
     */
    public int modifyDetai(JdbcTemplate jdbcTemplate, TmnInfo tmnInfo);

    /**
     * 修改详细信息--新版
     *
     * @param jdbcTemplate
     * @param tmnInfo
     * @return
     */
    public int newModifyDetai(JdbcTemplate jdbcTemplate, TmnInfo tmnInfo);

    /**
     * 修改通讯参数
     *
     * @param jdbcTemplate
     * @param tmnInfo
     * @return
     */
    public int commParaModify(JdbcTemplate jdbcTemplate, TmnInfo tmnInfo);

    /**
     * 绑定账户
     *
     * @param jdbcTemplate
     * @param tmnInfo
     * @return
     */
    public int bindAccount(JdbcTemplate jdbcTemplate, TmnInfo tmnInfo);

    /**
     * 根据区县、起始终端编号更新升级标记
     *
     * @param jdbcTemplate
     * @param city_id
     * @param begin_terminal_id
     * @param end_terminal_id
     * @return
     */
    public int updateUpgradeMark(JdbcTemplate jdbcTemplate, String city_id, String begin_terminal_id,
            String end_terminal_id, int upgradeMark);

    /**
     * 根据区县、起始终端编号查询符合条件的终端
     *
     * @param jdbcTemplate
     * @param city_id
     * @param begin_terminal_id
     * @param end_terminal_id
     * @return
     */
    public List<TmnInfo> getTmnListByConditions(JdbcTemplate jdbcTemplate, String city_id,
            String begin_terminal_id, String end_terminal_id);

    /**
     * 查询软件版本一致的所有终端
     *
     * @param jdbcTemplate
     * @param city_id
     * @param begin_terminal_id
     * @param end_terminal_id
     * @return
     */
    public List<TmnInfo> getTmnListVersionEqual(JdbcTemplate jdbcTemplate, String city_id,
            String begin_terminal_id, String end_terminal_id);

    /**
     * 查询软件版本不一致的所有终端
     *
     * @param jdbcTemplate
     * @param city_id
     * @param begin_terminal_id
     * @param end_terminal_id
     * @return
     */
    public List<TmnInfo> getTmnListVersionNotEqual(JdbcTemplate jdbcTemplate, String city_id,
            String begin_terminal_id, String end_terminal_id);

    /**
     * 根据物理投注机号查询投注机信息
     *
     * @param jdbcTemplate
     * @param terminalPhyId
     * @return
     */
    public TmnInfo getTmnInfoByPhyId(JdbcTemplate jdbcTemplate, int terminalPhyId);

    /**
     * 注册电话投注投注机
     *
     * @param jdbcTemplate
     * @param tmn
     * @param agentFeeRate
     * @return
     */
    public int registGambTmn(JdbcTemplate jdbcTemplate, final TmnInfo tmn, final BigDecimal agentFeeRate);
}
