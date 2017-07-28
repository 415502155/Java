package com.bestinfo.service.terminal;

import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.bean.business.TmnPrivilege;
import com.bestinfo.bean.game.GameInfo;
import com.bestinfo.dao.page.Pagination;
import java.util.List;
import java.util.Map;

/**
 * 投注机信息
 *
 * @author chenliping
 */
public interface ITmnInfoSer {

    /**
     * 查询投注机 条件可为投注机号 或 投注机物理卡号 或 地市编号 或 站点名称 或 户主名 或 代销商编号
     *
     * @param params
     * @return
     */
    public Pagination<TmnInfo> getTmnInfoPageList(Map<String, Object> params);

    /**
     * 根据地市、终端机号、代销商编号查询终端信息列表
     *
     * @param cityid
     * @param tmnid
     * @param dealerid
     * @return
     */
    public List<TmnInfo> getTmnListByCondition(int cityid, int tmnid, String dealerid);

    /**
     * 更新终端信息
     *
     * @param ti
     * @return 成功（0）-- 更新数据库失败（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
     */
    public int updateTmnInfo(TmnInfo ti);

    /**
     * 先将终端特权插入数据库，再插入缓存
     *
     * @param tpList
     * @return
     */
    public int addTmnPrivilege(List<TmnPrivilege> tpList);

    /**
     * 修改某个终端的所有终端特权信息
     *
     * @param tpList
     * @return
     */
    public int modifyTmnPrivilege(List<TmnPrivilege> tpList, int terminalId);

    /**
     * 从缓存中得到游戏信息列表，并给每个游戏信息增加cash_fee_rate字段信息
     *
     * @return
     */
    public List<GameInfo> resetGameInfoList();

    public List<TmnInfo> selectTmnInfoList();

    /**
     * ---EB----*
     */
    /**
     * 旧版新机登记
     *
     * @param tmnInfo
     * @return
     */
    public int addTmn(TmnInfo tmnInfo);

    /**
     * 新版新机登记
     *
     * @param tmnInfo
     * @return
     */
    public int newAddTmn(TmnInfo tmnInfo);

    /**
     * 修改详细信息
     *
     * @param tmnInfo
     * @return
     */
    public int modifyDetai(TmnInfo tmnInfo);

    /**
     * 修改详细信息--新版
     *
     * @param tmnInfo
     * @return
     */
    public int newModifyDetai(TmnInfo tmnInfo);

    /**
     * 修改通讯参数
     *
     * @param tmnInfo
     * @return
     */
    public int commParaModify(TmnInfo tmnInfo);

    /**
     * 绑定账户
     *
     * @param tmnInfo
     * @return
     */
    public int bindAccount(TmnInfo tmnInfo);

    /**
     * 新机初始化
     *
     * @param tmnInfo
     * @return
     */
    public int newTmnInit(TmnInfo tmnInfo);

    /**
     * 根据起始终端号删除对应的Redis锁
     *
     * @param begin_terminal_id
     * @param end_terminal_id
     * @return
     */
    public int delRedisLock(String begin_terminal_id, String end_terminal_id);

}
