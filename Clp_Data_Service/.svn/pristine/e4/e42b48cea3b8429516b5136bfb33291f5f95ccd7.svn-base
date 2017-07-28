package com.bestinfo.service.impl.terminal;

import com.bestinfo.define.Account.TradeTypeDefine;
import com.bestinfo.define.Draw.DrawProStatus;
import com.bestinfo.define.common.CodeCommon;
import com.bestinfo.define.sysUser.UserRole;
import com.bestinfo.define.terminal.AutoDeduct;
import com.bestinfo.define.terminal.DeductSwitch;
import com.bestinfo.define.terminal.StationRank;
import com.bestinfo.define.terminal.TmnAgentFeeType;
import com.bestinfo.define.terminal.TmnGameStop;
import com.bestinfo.define.terminal.TmnInitTime;
import com.bestinfo.define.terminal.TmnPrivelegeInfo;
import com.bestinfo.define.terminal.TmnStatus;
import com.bestinfo.bean.business.DealerUser;
import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.bean.business.TmnPrivilege;
import com.bestinfo.bean.encoding.TradeType;
import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.bean.game.GameFundsProportion;
import com.bestinfo.bean.game.GameInfo;
import com.bestinfo.bean.game.TmnSerialNo;
import com.bestinfo.bean.terminal.TmnAutoDeduction;
import com.bestinfo.dao.business.IDealerUserDao;
import com.bestinfo.dao.business.ITmnInfoDao;
import com.bestinfo.dao.business.ITmnPrivilegeDao;
import com.bestinfo.dao.encoding.ITradeType;
import com.bestinfo.dao.game.IGameDrawInfoDao;
import com.bestinfo.dao.game.IGameFundsProportionDao;
import com.bestinfo.dao.game.IGameInfoDao;
import com.bestinfo.dao.page.Pagination;
import com.bestinfo.dao.terminal.ITerminalRegisterDao;
import com.bestinfo.dao.terminal.ITmnAutoDeductionDao;
import com.bestinfo.define.system.WorkStatusDefine;
import com.bestinfo.define.terminal.TmnType;
import com.bestinfo.service.terminal.ITmnInfoSer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 投注机信息
 *
 * @author chenliping
 */
@Service
public class TmnInfoSerImpl implements ITmnInfoSer {

    private static final Logger logger = Logger.getLogger(TmnInfoSerImpl.class);

    @Resource
    private ITmnInfoDao itmn;

    @Resource
    private ITmnPrivilegeDao itmnPDao;

    @Resource
    private JdbcTemplate termJdbcTemplate;

    @Resource
    private JdbcTemplate metaJdbcTemplate;

    @Resource
    private IDealerUserDao idealerdao;

    @Resource
    private IGameFundsProportionDao gameFundsProportionDao;

    @Resource
    private ITerminalRegisterDao terminalRegisterDao;//终端注册DAO

    @Resource
    private IGameDrawInfoDao gameDrawInfoDao;

    @Resource
    private ITradeType tradeTypeDao;

    @Resource
    private ITmnAutoDeductionDao tmnAutoDeductionDao;
    @Resource
    private IGameInfoDao gameInfoDao;

    /**
     * 终端登记的时候，终端信息变量的默认值
     */
    private static final String defaultValue = "0";
    /**
     * 根据终端编号拼接用户Id时的分隔符
     */
    private static final String dealerUserIdSpace = "0";
    /**
     * 终端登记时软件版本的默认值
     */
    private static final String softVersionDefaultValue = "00.00";
    /**
     * 终端登记时默认初始化操作员的个数
     */
    private static final int initOperaterNum = 5;

    /**
     * 增加投注机的时候，初始化特权信息
     *
     * @param terminal_id
     * @param terminal_type
     * @return
     */
    private int initTmnPrivileges(int terminal_id, int terminal_type) {
        try {
            List<TmnPrivilege> tmnPl = new ArrayList<TmnPrivilege>();
            List<GameInfo> lg = gameInfoDao.selectGameInfo(metaJdbcTemplate);
            if (lg == null || lg.isEmpty()) {
                logger.error("select game info from DB error.");
                return -1;
            }
            List<Integer> gameIdList = new ArrayList<Integer>();
            for (GameInfo gi : lg) {
                gameIdList.add(gi.getGame_id());
            }

            Map<Integer, GameFundsProportion> GameFundsProportionMap = gameFundsProportionDao.selectGameFundsMapByGameIdList(metaJdbcTemplate, gameIdList);
            if (GameFundsProportionMap == null || GameFundsProportionMap.isEmpty()) {
                logger.warn("select GameFundsProportion map from DB error.");
                return -3;
            }

            int re = itmnPDao.insertTmnPrivilege(termJdbcTemplate, tmnPl);
            if (re < 0) {
                logger.error("insert tmn privilege list into DB error where terminal_id = " + terminal_id);
                return -5;
            }
            return 0;
        } catch (Exception e) {
            logger.error("init tmn privileges occur exception.", e);
            return -7;
        }
    }

    /**
     * 增加投注机的时候，初始化特权信息--用于新版的终端登记
     *
     * @param terminal_id
     * @param terminal_type
     * @return
     */
    private int initTmnPrivileges(int terminal_id, int terminal_type, BigDecimal agent_fee_rate) {
        try {
            List<TmnPrivilege> tmnPl = new ArrayList<TmnPrivilege>();
            // List<GameInfo> lg = gameInfoCache.getGameInfoListFrmCache();
            //add by lvchangrong   2014-12-02    游戏信息由缓存改为从数据库获取
            List<GameInfo> lg = gameInfoDao.selectGameInfo(metaJdbcTemplate);
            if (lg == null || lg.isEmpty()) {
                logger.error("select game info from DB error.");
                return -1;
            }
            List<Integer> gameIdList = new ArrayList<Integer>();
            for (GameInfo gi : lg) {
                gameIdList.add(gi.getGame_id());
            }

            //Map<Integer, GameFundsProportion>中，一个游戏Id对应一个游戏资金比例对象
            Map<Integer, GameFundsProportion> GameFundsProportionMap = gameFundsProportionDao.selectGameFundsMapByGameIdList(metaJdbcTemplate, gameIdList);
            if (GameFundsProportionMap == null || GameFundsProportionMap.isEmpty()) {
                logger.warn("select GameFundsProportion map from DB error.");
                return -3;
            }

            for (GameInfo gi : lg) {
                TmnPrivilege tmnP = new TmnPrivilege();
                tmnP.setTerminal_id(terminal_id);
                tmnP.setGame_id(gi.getGame_id());
                tmnP.setCur_draw_id(0);

                if (terminal_type == TmnType.specialCenterCashTerminal) {
                    tmnP.setSale_permit(WorkStatusDefine.notwork);
                } else {
                    if (gi.getUsed_mark() == WorkStatusDefine.notwork) {
                        tmnP.setSale_permit(WorkStatusDefine.notwork);
                    } else {
                        tmnP.setSale_permit(WorkStatusDefine.work);
                    }
                }

                if (gi.getUsed_mark() == WorkStatusDefine.notwork) {
                    tmnP.setCash_permit(WorkStatusDefine.notwork);
                } else {
                    tmnP.setCash_permit(WorkStatusDefine.work);
                }

                if (terminal_type == TmnType.specialCenterCashTerminal) {
                    tmnP.setUndo_permit(WorkStatusDefine.notwork);
                } else {
                    if (gi.getUsed_mark() == WorkStatusDefine.notwork) {
                        tmnP.setUndo_permit(WorkStatusDefine.notwork);
                    } else {
                        tmnP.setUndo_permit(WorkStatusDefine.work);
                    }
                }

                if (gi.getUsed_mark() == WorkStatusDefine.notwork) {
                    tmnP.setGame_permit(WorkStatusDefine.notwork);
                } else {
                    tmnP.setGame_permit(WorkStatusDefine.work);
                }

                if (terminal_type == TmnType.specialCenterCashTerminal) {
                    tmnP.setPresell_permit(WorkStatusDefine.notwork);
                } else {
                    if (gi.getUsed_mark() == WorkStatusDefine.notwork) {
                        tmnP.setPresell_permit(WorkStatusDefine.notwork);
                    } else {
                        tmnP.setPresell_permit(WorkStatusDefine.work);
                    }
                }

                tmnP.setGame_stop(TmnGameStop.gamestopno);
                if (terminal_type == TmnType.specialCenterCashTerminal) {
                    //如果为兑奖机时，则各比例和范围为0
                    tmnP.setAgent_fee_rate(BigDecimal.ZERO);
                    tmnP.setCash_fee_rate(BigDecimal.ZERO);
                    tmnP.setMin_bet_money(BigDecimal.ZERO);
                    tmnP.setMax_bet_money(BigDecimal.ZERO);
                    tmnP.setMax_sales_money(BigDecimal.ZERO);
                } else {
                    GameFundsProportion gfp = GameFundsProportionMap.get(gi.getGame_id());
                    if (gfp == null) {
                        logger.error("there is no game funds proportion info in DB where game id = " + gi.getGame_id());
                        tmnP.setAgent_fee_rate(BigDecimal.ZERO);
                        tmnP.setCash_fee_rate(BigDecimal.ZERO);
                    } else {
                        tmnP.setAgent_fee_rate(agent_fee_rate);
                        tmnP.setCash_fee_rate(gfp.getCash_fee_rate());
                    }
                    tmnP.setMin_bet_money(TmnPrivelegeInfo.min_bet_money);
                    tmnP.setMax_bet_money(gi.getTerminal_bet_money());
                    tmnP.setMax_sales_money(TmnPrivelegeInfo.max_sales_money);
                }

                tmnPl.add(tmnP);
            }
            int re = itmnPDao.insertTmnPrivilege(termJdbcTemplate, tmnPl);
            if (re < 0) {
                logger.error("insert tmn privilege list into DB error where terminal_id = " + terminal_id);
                return -5;
            }
            return 0;
        } catch (Exception e) {
            logger.error("init tmn privileges occur exception.", e);
            return -7;
        }
    }

    /**
     * 增加投注机的时候，初始化彩票投注流水号信息
     *
     * @param terminal_id
     * @return
     */
    private int initTmnSerialNos(int terminal_id) {
        try {
            //add by lvchangrong   2014-12-02    游戏信息由缓存改为从数据库获取
            // List<GameInfo> giList = gameInfoCache.getGameInfoListFrmCache();
            List<GameInfo> lg = gameInfoDao.selectGameInfo(metaJdbcTemplate);
            if (lg == null || lg.isEmpty()) {
                logger.error("select game info from DB error.");
                return -1;
            }

            if (lg.isEmpty()) {
                logger.error("there is no game info in DB.");
                return -2;
            }

            List<Integer> gameIdList = new ArrayList<Integer>();
            for (GameInfo gi : lg) {
                gameIdList.add(gi.getGame_id());
            }

            //得到所有游戏的处于当前期和预售期的数据
            List<GameDrawInfo> gdList = gameDrawInfoDao.selectGameDrawInfoListByGameIds(metaJdbcTemplate, gameIdList, DrawProStatus.PRESALE, DrawProStatus.SALING);
            if (gdList == null || gdList.isEmpty()) {
                logger.warn("there is no game draw info in DB where processStatus in (" + DrawProStatus.PRESALE + "," + DrawProStatus.SALING + ") in all games");
                return 0;
            }

            List<TmnSerialNo> tsnList = new ArrayList<TmnSerialNo>();
            for (GameDrawInfo gdi : gdList) {
                int drawId = gdi.getDraw_id();
                TmnSerialNo tsn = new TmnSerialNo();
                tsn.setTerminal_id(terminal_id);
                tsn.setGame_id(gdi.getGame_id());
                tsn.setDraw_id(drawId);
                tsn.setSerial_no(0);
                tsn.setPartion_id(0);
                tsnList.add(tsn);
            }

            if (tsnList.isEmpty()) {
                logger.warn("all the game has no saled draw.");
                return 0;
            }

            return 0;
        } catch (Exception e) {
            logger.error("init tmn serial nos occur exception.", e);
            return -4;
        }
    }

    /**
     * 初始化 投注机-终端自动扣除资金设置表(T_tmn_auto_deduction)
     *
     * @param terminalId
     * @return
     */
    public int initTmnAutoDeductions(int terminalId) {
        //从T_trade_type表查询出batch_item=批量处理 的记录
        List<TradeType> ttList = tradeTypeDao.selectTradeTypeListByBatchItem(metaJdbcTemplate, CodeCommon.yes);
        if (ttList == null || ttList.isEmpty()) {
            logger.error("select trade type list from DB error where batch_item = " + CodeCommon.yes);
            return -1;
        }

        if (ttList == null || ttList.isEmpty()) {
            logger.warn("there is no trade type info in DB where batch_item = " + CodeCommon.yes);
            return 0;
        }

        //将查询出的数据插入到T_tmn_auto_deduction表中，扣款金额初始化为0
        List<TmnAutoDeduction> tadList = new ArrayList<TmnAutoDeduction>();
        for (TradeType tt : ttList) {
            TmnAutoDeduction tad = new TmnAutoDeduction();
            tad.setTerminal_id((long) terminalId);
            tad.setTrade_type(tt.getTrade_type());
            if (tt.getTrade_type() == TradeTypeDefine.ELECTRIC_MAINTAIN_FEE) {
                tad.setDeduct_money(BigDecimal.valueOf(AutoDeduct.netmoney));
            } else if (tt.getTrade_type() == TradeTypeDefine.DEVICE_DEPRECIATION) {
                tad.setDeduct_money(BigDecimal.valueOf(AutoDeduct.usemoney));
            }
            tad.setWork_status(WorkStatusDefine.work);
            tadList.add(tad);
        }
        if (tadList.isEmpty()) {
            logger.error("the tadList is null ");
            return -3;
        }
        int re = tmnAutoDeductionDao.batchAddTmnAutoDeduction(termJdbcTemplate, tadList);
        if (re < 0) {
            logger.error("batch add tmnAutoDeduction failed when add terminal, terminal_id :" + terminalId);
            return -2;
        }

        return 0;
    }

    @Override
    public Pagination<TmnInfo> getTmnInfoPageList(Map<String, Object> params) {
        return itmn.getTmnInfoPageList(termJdbcTemplate, params);
    }

    /**
     * 根据地市、终端机号、代销商编号查询终端信息列表
     *
     * @param cityid
     * @param tmnid
     * @param dealerid
     * @return
     */
    @Override
    public List<TmnInfo> getTmnListByCondition(int cityid, int tmnid, String dealerid) {
        if ("".equals(dealerid) || dealerid == null) {
            return itmn.getTmnListByCityIdAndTmnId(termJdbcTemplate, cityid, tmnid);
        } else {
            return itmn.getTmnListByCityIdAndTmnIdAndDealerId(termJdbcTemplate, cityid, tmnid, dealerid);
        }
    }

    /**
     * 更新终端信息(先更新库，再更新Ehcache，再更新redis)
     *
     * @param ti
     * @return 成功（0）-- 更新数据库或Ehcache失败（-1）-- 更新redis失败（-2）
     */
    @Override
    public int updateTmnInfo(TmnInfo ti) {
        int re = itmn.updateTmnInfo(termJdbcTemplate, ti);
        if (re < 0) {
            logger.error("update kdrawProStatus db error");
            return -1;
        }
        return 0;
    }

    /**
     * 生成投注机号（省2+市2+投注机序号4）
     *
     * @param provinceId
     * @param cityId
     * @param terminal_serial_no
     * @return
     */
    private int getTerminalId(int provinceId, int cityId, int terminalId) {
        StringBuffer sb = new StringBuffer();
        sb.append(provinceId);
        sb.append(String.format("%02d", cityId));
        sb.append(String.format("%04d", terminalId));
        return Integer.parseInt(sb.toString());
    }

    /**
     * 更新db中的投注机特权信息，再更新缓存
     *
     * @param tmnPList
     * @return
     */
    @Override
    public int addTmnPrivilege(List<TmnPrivilege> tmnPList) {
        //修改库
        int re = itmnPDao.updateBatchTmnPrivilege(termJdbcTemplate, tmnPList);
        if (re < 0) {
            logger.error("insert tmn privilege list into DB error" + re);
            return -1;
        }
        return 0;
    }

    /**
     * 修改某个终端的所有终端特权信息
     *
     * @param tmnPList
     * @return
     */
    @Override
    public int modifyTmnPrivilege(List<TmnPrivilege> tmnPList, int terminalId) {
        try {
            //先修改库
            int re = itmnPDao.updateBatchTmnPrivilege(termJdbcTemplate, tmnPList);
            if (re < 0) {
                logger.error("batch modify tmn privilege failed where terminalId = " + terminalId);
                return -2;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("modify tmn privilege list failed where terminalId = " + terminalId, e);
            return -4;
        }
    }

    /**
     * 从缓存中得到游戏信息列表，并给每个游戏信息增加cash_fee_rate字段信息
     *
     * @return
     */
    @Override
    public List<GameInfo> resetGameInfoList() {
        List<GameInfo> giList = gameInfoDao.selectGameInfo(metaJdbcTemplate);
        for (GameInfo gameInfo : giList) {
            int gameId = gameInfo.getGame_id();
            List<GameFundsProportion> gfpList = gameFundsProportionDao.selectFundsByGameid(termJdbcTemplate, gameId);
            if (gfpList == null || gfpList.isEmpty()) {
                logger.error("there is no game funds proportion data in DB where gameId = " + gameId);
                gameInfo.setCash_fee_rate(BigDecimal.ZERO);
                continue;
            }
            GameFundsProportion gfp = gfpList.get(0);
            gameInfo.setCash_fee_rate(gfp.getCash_fee_rate());
        }
        return giList;
    }

    @Override
    public List<TmnInfo> selectTmnInfoList() {
        return itmn.selectTmnInfo(termJdbcTemplate);
    }

    /**
     * 旧版EB新机登记
     *
     * @param tmnInfo
     * @return
     */
    @Override
    public int addTmn(TmnInfo tmnInfo) {
        String local_terminal_id = tmnInfo.getLocal_terminal_id();
        if (local_terminal_id == null || "null".equals(local_terminal_id.trim()) || "".equals(local_terminal_id.trim())) {
            local_terminal_id = defaultValue;
        }
        tmnInfo.setLocal_terminal_id(local_terminal_id);

        //投注机序号设置，顺序增加1
        int maxserNo = itmn.getMaxTerminalSerialNo(termJdbcTemplate);
        if (maxserNo == -2) {
            logger.error("can't get max TerminalSerialNo !");
            return -1;
        }
        tmnInfo.setTerminal_serial_no(maxserNo + 1);
        tmnInfo.setTerminal_phy_id(tmnInfo.getTerminal_id());
        tmnInfo.setTerminal_initial_time(TmnInitTime.NOTREGISTERED);//终端初始化时间初始化为"未注册"
        tmnInfo.setSafe_card_id(defaultValue);//加密芯片设置初始值
        tmnInfo.setDistrict_id(0);
        tmnInfo.setOwner_name(defaultValue);
        tmnInfo.setOwner_phone(defaultValue);
        tmnInfo.setLinkman(defaultValue);
        tmnInfo.setLinkman_phone(defaultValue);
        tmnInfo.setRegist_date(new Date());//与文档中不一致（保存时，系统自动设置：'00:00:00'）
        tmnInfo.setUpgrade_mark(WorkStatusDefine.work);//升级标志
        tmnInfo.setSoftware_version(softVersionDefaultValue);
        tmnInfo.setTerminal_status(TmnStatus.inuse);//默认在用
        tmnInfo.setAgentfee_type(TmnAgentFeeType.agentfee);
        tmnInfo.setTmn_sale_deduct(DeductSwitch.deductSwitch);
        tmnInfo.setTmn_cash_deduct(DeductSwitch.deductSwitch);
        tmnInfo.setComm_type(0);
        tmnInfo.setDial_name(defaultValue);
        tmnInfo.setDial_pwd(defaultValue);
        tmnInfo.setAccount_id(defaultValue);
        tmnInfo.setDealer_id(defaultValue);
        tmnInfo.setTerminal_syn_no(1);//登记时终端同步字设置为1
        tmnInfo.setTerminal_value(new BigDecimal(0));
        tmnInfo.setPublic_key(defaultValue);
        tmnInfo.setNotice_syn_no(1);
        tmnInfo.setStation_rank(StationRank.noRank);

        List<DealerUser> ldu = new ArrayList<DealerUser>();
        //初始化五个终端操作员
        for (int i = 0; i < initOperaterNum; i++) {
            DealerUser du = new DealerUser();
            String userid = tmnInfo.getTerminal_id() + dealerUserIdSpace + (i + 1);
            du.setUser_id(Long.parseLong(userid));
            du.setUser_name(userid);
            du.setUser_pwd(defaultValue);//加密否？
            du.setForce_change_pwd(0);
            du.setReal_name(userid);
            du.setRegist_date(new Date());
            du.setRole_id(UserRole.tmnoperator);
            du.setWork_status(WorkStatusDefine.work);
            du.setDealer_id(tmnInfo.getDealer_id());
            du.setTerminal_id(tmnInfo.getTerminal_id());
            du.setOperator_id(i + 1);
            du.setCity_id(tmnInfo.getCity_id());
            ldu.add(du);
        }

        //添加到库
        int re = itmn.insertTmnInfo(termJdbcTemplate, tmnInfo);
        if (re < 0) {
            logger.error("insert tmn info into DB error.");
            return -3;
        }

        if (ldu.isEmpty()) {
            logger.error("the ldu is null");
            return -5;
        }
        int operre = idealerdao.addTmnOperator(termJdbcTemplate, ldu);
        if (operre < 0) {
            logger.error("insert tmn operator into DB error");
            return -6;
        }

        //初始化终端特权数据（所有游戏）
        int tmnre = initTmnPrivileges(tmnInfo.getTerminal_id(), tmnInfo.getTerminal_type());
        if (tmnre < 0) {
            logger.error("tmn init error:" + tmnre);
            return -7;
        }

        //初始化彩票投注流水号（所有游戏的当前期和预售期）
        int tmnre1 = initTmnSerialNos(tmnInfo.getTerminal_id());
        if (tmnre1 < 0) {
            logger.error("tmn serial nos init error:" + tmnre1);
            return -8;
        }

        //初始化投注机-终端自动扣除资金设置表（T_trade_type表所有batch_item=批量处理 的记录）
        int tmnre2 = initTmnAutoDeductions(tmnInfo.getTerminal_id());
        if (tmnre2 < 0) {
            logger.error("tmn auto deduction init error:" + tmnre2);
            return -9;
        }

        return 0;
    }

    /**
     * 新版新机登记
     *
     * @param tmnInfo
     * @return
     */
    @Override
    public int newAddTmn(TmnInfo tmnInfo) {
        String local_terminal_id = tmnInfo.getLocal_terminal_id();
        if (local_terminal_id == null || "null".equals(local_terminal_id.trim()) || "".equals(local_terminal_id.trim())) {
            local_terminal_id = defaultValue;
        }
        tmnInfo.setLocal_terminal_id(local_terminal_id);

        //投注机序号设置，顺序增加1
        int maxserNo = itmn.getMaxTerminalSerialNo(termJdbcTemplate);
        if (maxserNo == -2) {
            logger.error("can't get max TerminalSerialNo !");
            return -4;
        }
        tmnInfo.setTerminal_serial_no(maxserNo + 1);
        tmnInfo.setTerminal_phy_id(tmnInfo.getTerminal_id());
        tmnInfo.setTerminal_initial_time(TmnInitTime.NOTREGISTERED);//终端初始化时间初始化为"未注册"
        tmnInfo.setSafe_card_id(defaultValue);//加密芯片设置初始值
        tmnInfo.setDistrict_id(0);
        tmnInfo.setRegist_date(new Date());//与文档中不一致（保存时，系统自动设置：'00:00:00'）
        tmnInfo.setUpgrade_mark(WorkStatusDefine.work);//升级标志
        tmnInfo.setSoftware_version(softVersionDefaultValue);
        tmnInfo.setTerminal_status(TmnStatus.inuse);//默认在用
        tmnInfo.setAgentfee_type(TmnAgentFeeType.agentfee);
        tmnInfo.setTmn_sale_deduct(DeductSwitch.deductSwitch);
        tmnInfo.setTmn_cash_deduct(DeductSwitch.deductSwitch);
        tmnInfo.setComm_type(0);
        tmnInfo.setDial_name(defaultValue);
        tmnInfo.setDial_pwd(defaultValue);
        tmnInfo.setAccount_id(defaultValue);
        tmnInfo.setDealer_id(defaultValue);
        tmnInfo.setTerminal_syn_no(1);//登记时终端同步字设置为1
        tmnInfo.setTerminal_value(new BigDecimal(0));
        tmnInfo.setPublic_key(defaultValue);
        tmnInfo.setNotice_syn_no(1);

        List<DealerUser> ldu = new ArrayList<DealerUser>();
        //初始化五个终端操作员
        for (int i = 0; i < initOperaterNum; i++) {
            DealerUser du = new DealerUser();
            String userid = tmnInfo.getTerminal_id() + dealerUserIdSpace + (i + 1);
            du.setUser_id(Long.parseLong(userid));
            du.setUser_name(userid);
            du.setUser_pwd(defaultValue);//加密否？
            du.setForce_change_pwd(0);
            du.setReal_name(userid);
            du.setRegist_date(new Date());
            du.setRole_id(UserRole.tmnoperator);
            du.setWork_status(WorkStatusDefine.work);
            du.setDealer_id(tmnInfo.getDealer_id());
            du.setTerminal_id(tmnInfo.getTerminal_id());
            du.setOperator_id(i + 1);
            du.setCity_id(tmnInfo.getCity_id());
            ldu.add(du);
        }

        //添加到库
        int re = itmn.insertTmnInfo(termJdbcTemplate, tmnInfo);
        if (re < 0) {
            logger.error("insert tmn info into DB error.");
            return -5;
        }

        //添加到Redis
        if (ldu.isEmpty()) {
            logger.error("the ldu is null");
            return -7;
        }
        int operre = idealerdao.addTmnOperator(termJdbcTemplate, ldu);
        if (operre < 0) {
            logger.error("insert tmn operator into DB error");
            return -8;
        }

        //初始化彩票投注流水号（所有游戏的当前期和预售期）
        int tmnre1 = initTmnSerialNos(tmnInfo.getTerminal_id());
        if (tmnre1 < 0) {
            logger.error("tmn serial nos init error:" + tmnre1);
            return -10;
        }

        //初始化投注机-终端自动扣除资金设置表（T_trade_type表所有batch_item=批量处理 的记录）
        int tmnre2 = initTmnAutoDeductions(tmnInfo.getTerminal_id());
        if (tmnre2 < 0) {
            logger.error("tmn auto deduction init error:" + tmnre2);
            return -11;
        }

        return 0;
    }

    /**
     * 修改详细信息
     *
     * @param tmnInfo
     * @return
     */
    @Override
    public int modifyDetai(TmnInfo tmnInfo) {
        return 0;
    }

    /**
     * 修改详细信息--新版
     *
     * @param tmnInfo
     * @return
     */
    @Override
    public int newModifyDetai(TmnInfo tmnInfo) {
        int starank = tmnInfo.getStation_rank();

        //修改库
        int re = itmn.newModifyDetai(termJdbcTemplate, tmnInfo);
        if (re < 0) {
            logger.error("modify detail info error from DB.");
            return -3;
        }

        int terminal_id = tmnInfo.getTerminal_id();

        //修改Redis
        TmnInfo ti = itmn.getTmnInfoByTerminalId(termJdbcTemplate, terminal_id);
        if (ti == null) {
            logger.error("there is no tmn info in DB where terminalId = " + terminal_id);
            return -4;
        }

        //查询库中该终端的所有游戏的特权信息
        List<TmnPrivilege> tpList = itmnPDao.selectPrivilegeByTerminal(termJdbcTemplate, terminal_id);
        if (tpList == null || tpList.isEmpty()) {
            logger.error("there is no tmn privilege list in term DB where terminal_id= " + terminal_id);
            return -6;
        }

        //修改库和Redis
        re = itmnPDao.updateBatchTmnPrivilege(termJdbcTemplate, tpList);
        if (re < 0) {
            logger.error("update tmn privilege list error in term DB where terminal_id= " + terminal_id);
            return -7;
        }

        return 0;
    }

    /**
     * 修改通讯参数
     *
     * @param tmnInfo
     * @return
     */
    @Override
    public int commParaModify(TmnInfo tmnInfo) {
        return 0;
    }

    /**
     * 绑定账户
     *
     * @param tmnInfo
     * @return
     */
    @Override
    public int bindAccount(TmnInfo tmnInfo) {
        return 0;
    }

    /**
     * 新机初始化
     *
     * @param tmnInfo
     * @return
     */
    @Override
    public int newTmnInit(TmnInfo tmnInfo) {
        logger.info("init tmn info,terminal_id:" + tmnInfo.getTerminal_id());
        //更新投注机号表T_TMN_INFO的投注机初始化时间和加密芯片两个字段
        //更新升级标记为升级
        int res = terminalRegisterDao.updateTerminalInfo(termJdbcTemplate, TmnInitTime.NOTREGISTERED, defaultValue, defaultValue, WorkStatusDefine.work, tmnInfo.getTerminal_id());
        if (res < 0) {
            logger.error("newTmnInit error where terminal_id = " + tmnInfo.getTerminal_id());
            return -1;
        }
        //查询更新后的终端信息，更新缓存数据
        TmnInfo ti = terminalRegisterDao.getTmnInfoByTmnId(termJdbcTemplate, tmnInfo.getTerminal_id());
        if (ti == null) {
            logger.error("there is no tmn info in DB where terminalId = " + tmnInfo.getTerminal_id());
            return -2;
        }
        return 0;
    }

    /**
     * 根据起始终端号删除对应的Redis锁
     *
     * @param begin_terminal_id
     * @param end_terminal_id
     * @return
     */
    @Override
    public int delRedisLock(String begin_terminal_id, String end_terminal_id) {
        return 0;
    }

}
