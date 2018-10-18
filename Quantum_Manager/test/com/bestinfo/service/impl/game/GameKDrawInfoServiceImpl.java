//package com.bestinfo.service.impl.game;
//
//import com.bestinfo.define.Draw.DrawProStatus;
//import com.bestinfo.define.Draw.KDrawProStatus;
//import com.bestinfo.define.fs.ServerName;
//import com.bestinfo.fsclient.protocols.body.CtrlSrvStatImpl;
//import com.bestinfo.fsclient.protocols.body.control.NewDrawImpl;
//import com.bestinfo.fsclient.protocols.struct.AllMessage;
//import com.bestinfo.fsclient.protocols.struct.MsgHead;
//import com.bestinfo.define.sysUser.UserRole;
//import com.bestinfo.define.terminal.SynType;
//import com.bestinfo.bean.business.DealerUser;
//import com.bestinfo.bean.game.GameDrawInfo;
//import com.bestinfo.bean.game.GameInfo;
//import com.bestinfo.bean.game.GameKDrawInfo;
//import com.bestinfo.bean.game.KenoDrawConfigure;
//import com.bestinfo.bean.terminal.SynCodeInfo;
//import com.bestinfo.dao.business.IDealerUserDao;
//import com.bestinfo.dao.game.IGameDrawInfoDao;
//import com.bestinfo.dao.game.IGameInfoDao;
//import com.bestinfo.dao.game.IGameKDrawInfoDao;
//import com.bestinfo.dao.game.IGamePlayInfoDao;
//import com.bestinfo.dao.game.IKenoDrawConfigureDao;
//import com.bestinfo.dao.page.Pagination;
//import com.bestinfo.dao.stat.ICurrentKDrawStatDao;
//import com.bestinfo.dao.stat.ICurrentTmnDrawStatDao;
//import com.bestinfo.dao.terminal.ISynCodeInfoDao;
//import com.bestinfo.define.fs.SocketConfig;
//import com.bestinfo.ehcache.game.GameInfoCache;
//import com.bestinfo.redis.gamedraw.GameDrawInfoCache;
//import com.bestinfo.redis.gamedraw.GameKDrawInfoRedis;
//import com.bestinfo.service.game.IGameKDrawInfoService;
//import com.bestinfo.util.TimeUtil;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import javax.annotation.Resource;
//import org.apache.log4j.Logger;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//
///**
// *
// * @author yangyuefu
// */
//@Service
//public class GameKDrawInfoServiceImpl implements IGameKDrawInfoService {
//
//    private final Logger logger = Logger.getLogger(GameKDrawInfoServiceImpl.class);
//
//    @Resource
//    private IGameInfoDao gameInfoDao;
//
//    @Resource
//    private GameInfoCache gameInfoCache;
//
//    @Resource
//    private IGameDrawInfoDao drawInfoDao;
//
//    @Resource
//    private GameDrawInfoCache drawInfoCache;
//
//    @Resource
//    private IGameKDrawInfoDao kdrawInfoDao;
//
//    @Resource
//    private GameKDrawInfoRedis kdrawInfoCache;
//
//    @Resource
//    private IGamePlayInfoDao gamePlayInfoDao;
//
//    @Resource
//    private ICurrentKDrawStatDao currkdrawdao;
//
//    @Resource
//    private JdbcTemplate metaJdbcTemplate;
//
//    @Resource
//    private JdbcTemplate termJdbcTemplate;
//
//    /**
//     * 游戏-快开游戏期参数配置表(T_keno_draw_configure)
//     *
//     */
//    @Resource
//    private IKenoDrawConfigureDao kenoDrawConfigureDao;
//
//    /**
//     * 实时统计-快开期玩法统计表(T_current_kdraw_play_stat)
//     */
////    @Resource
////    private ICurrentKDrawPlayStatDao currentKDrawPlayStatDao;
//
//    @Resource
//    private IDealerUserDao dealerUserdao;
//
//    @Resource
//    private ICurrentTmnDrawStatDao currentTmnDrawStatDao;
//
//    /**
//     * 信息-同步字列表(T_syn_list)
//     */
//    @Resource
//    private ISynCodeInfoDao synListDao;
//
//    /**
//     * 生成当天快开期次
//     *
//     * @param game_id 游戏id
//     * @return
//     */
//    @Override
//    public int preScheduleKDrawAuto(int game_id) {
//        logger.info("preScheduleKDrawAuto start");
//        try {
//            //校验是否已经排了当天的期
//            String drawName = TimeUtil.formatDate_YMD8(new Date());//大期期名20140929
//            GameDrawInfo drawInfo = drawInfoDao.getDrawByGameIdAndDrawName(metaJdbcTemplate, game_id, drawName);
//            if (drawInfo != null) {//当天已排期
//                logger.error("current day's draw info is already exist.");
//                return -1;
//            }
//            int result = -1;
//
//            //获取游戏记录，获取一些期参数
//            GameInfo gameInfo = gameInfoDao.getGameInfoByGameId(metaJdbcTemplate, game_id);
//            if (gameInfo == null) {
//                logger.error("GameInfo is null,game_id:" + game_id);
//                return -2;
//            }
//            String init_time = gameInfo.getInit_time();//自动新期时间
//            String draw_time = gameInfo.getDraw_time();//期结束时间
//            int cash_period_day = gameInfo.getCash_period_day();//兑奖期天数
//
//            //大期参数值初始化
//            int maxDrawId = 40001;//最大总期号
//            Date today = new Date();//当天时间
//
//            //获取最大大期记录，获取最大期次号
//            GameDrawInfo maxDraw = drawInfoDao.getMaxDrawByGameId(metaJdbcTemplate, game_id);
//            if (maxDraw != null) {
//                maxDrawId = maxDraw.getDraw_id() + 1;//最大总期号
//            }
//
//            //快开期参数初始化
//            int maxKedoDrawId = 1;//最大快开期号
//            //获取游戏最大快开期记录，获取最大快开号
//            GameKDrawInfo maxKDraw = kdrawInfoDao.getMaxKDrawByGameId(metaJdbcTemplate, game_id);
//            if (maxKDraw != null) {
//                maxKedoDrawId = maxKDraw.getKeno_draw_id() + 1;//最大快开期号
//            }
//
//            List<GameDrawInfo> drawList = new ArrayList<GameDrawInfo>();//大期列表
//            List<GameKDrawInfo> kDrawList = new ArrayList<GameKDrawInfo>();//快开期列表
//
//            Calendar salesBeginCalendar = Calendar.getInstance();
//            salesBeginCalendar.setTime(today);
//
//            Calendar cashEndCalendar = Calendar.getInstance();
//            cashEndCalendar.setTime(today);//兑奖结束时间
//
//            String yearMonthDay = TimeUtil.simple_formatter_date.format(today);//获取当天的年月日
//
//            logger.info("-----------begin pre kdraw---------------");
//            logger.info("which day:" + TimeUtil.simple_formatter_date.format(salesBeginCalendar.getTime()));
//
//            //大期
//            GameDrawInfo bigDraw = new GameDrawInfo();
//
//            //销售开始时间
//            String saleb = yearMonthDay + " " + init_time;
//            Date salesBeginDate = TimeUtil.simple_formatter_date_time.parse(saleb);//游戏表自动新期时间(init_time)
//            logger.info("sales_begin:" + saleb);
//            bigDraw.setSales_begin(salesBeginDate);
//
//            //销售结束时间
//            String saleend = yearMonthDay + " " + draw_time;
//            Date salesEndDate = TimeUtil.simple_formatter_date_time.parse(saleend);//游戏表期结束时间(draw_time)
//            logger.info("sales_end:" + saleend);
//            bigDraw.setSales_end(salesEndDate);
//
//            //兑奖开始时间
//            bigDraw.setCash_begin(salesBeginDate);//与销售开始时间相同
//            logger.info("cash_begin:" + TimeUtil.simple_formatter_date_time.format(salesBeginDate));
//
//            //兑奖结束时间,兑奖开始时间 + 兑奖天数，结束时刻为23：59：59
//            //此处第二天为兑奖第一日，如果兑奖开始时间当天为第一天，数据库配置兑奖天数-1
//            cashEndCalendar.setTime(salesEndDate);
//            cashEndCalendar.add(Calendar.DATE, cash_period_day);//兑奖开始时间+兑奖天数
//            //判断是否为周六周日，如果是，延期到周一
//            if (cashEndCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {//如果为周六
//                cashEndCalendar.add(Calendar.DATE, 2);//延期到周一
//            }
//            if (cashEndCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {//如果为周日
//                cashEndCalendar.add(Calendar.DATE, 1);//延期到周一
//            }
//            cashEndCalendar.set(Calendar.HOUR_OF_DAY, 23);//小时
//            cashEndCalendar.set(Calendar.MINUTE, 59);//分钟
//            cashEndCalendar.set(Calendar.SECOND, 59);//秒
//            bigDraw.setCash_end(cashEndCalendar.getTime());
//            logger.info("cash_end:" + TimeUtil.simple_formatter_date_time.format(cashEndCalendar.getTime()));
//
//            bigDraw.setGame_id(game_id);
//            bigDraw.setDraw_id(maxDrawId);
//            bigDraw.setDraw_name(drawName);
//            bigDraw.setDraw_type(1);
//            bigDraw.setProcess_status(DrawProStatus.SALING);
//
//            //每天的快开期
//            //得到快开游戏期参数配置列表，基础库
//            List<KenoDrawConfigure> configureList = kenoDrawConfigureDao.getConfigureByGame(metaJdbcTemplate, game_id);
//            if (configureList == null || configureList.isEmpty()) {
//                logger.error("KenoDrawConfigure is null,game_id:" + game_id);
//                return -3;
//            }
//            String Time_YMD8 = TimeUtil.formatDate_YMD8(salesBeginCalendar.getTime());//快开期名的前8位，20140928
//            int beginKenoDrawId = 0;//本期开始keno期号
//            int endKenoDrawId = 0;//本期结束keno期号
//            int configureListSize = configureList.size();
//            for (int i = 0; i < configureListSize; i++) {//遍历快开期配置列表
//                KenoDrawConfigure curConfigure = configureList.get(i);
//                GameKDrawInfo kDraw = new GameKDrawInfo();
//                kDraw.setGame_id(game_id);
//                kDraw.setDraw_id(bigDraw.getDraw_id());
//                kDraw.setKeno_draw_id(maxKedoDrawId++);//快开期号
//                kDraw.setKeno_draw_name(Time_YMD8 + String.format("%03d", curConfigure.getKeno_draw_no()));//快开期名
//                kDraw.setDraw_type(1);
//                kDraw.setSales_begin(TimeUtil.simple_formatter_date_time.parse(yearMonthDay + " " + curConfigure.getBegin_time()));//销售开始时间
//                kDraw.setSales_end(TimeUtil.simple_formatter_date_time.parse(yearMonthDay + " " + curConfigure.getEnd_time()));//销售结束时间
//                kDraw.setCash_begin(TimeUtil.simple_formatter_date_time.parse(yearMonthDay + " " + curConfigure.getEnd_time()));//兑奖开始时间，与销售结束时间相同
//                kDraw.setCash_end(cashEndCalendar.getTime());//兑奖结束时间，与大期兑奖结束时间相同
//                kDraw.setKeno_process_status(KDrawProStatus.SALING);
//                kDraw.setKdraw_no(curConfigure.getKeno_draw_no());//keno期序号
//                kDraw.setMulti_kdraw_num(curConfigure.getMulti_keno_num());//本期多期期数
//
//                if (i == 0) {
//                    beginKenoDrawId = kDraw.getKeno_draw_id();
//                }
//                if (i == (configureListSize - 1)) {
//                    endKenoDrawId = kDraw.getKeno_draw_id();
//                }
//                kDrawList.add(kDraw);
//            }
//            bigDraw.setKeno_draw_num(configureListSize);//大期 keno期数
//            bigDraw.setBegin_keno_draw_id(beginKenoDrawId);//大期 本期开始keno期号
//            bigDraw.setEnd_keno_draw_id(endKenoDrawId);//大期 本期结束keno期号
//            drawList.add(bigDraw);
//
//            int redraw = dataIntoDB(drawList, kDrawList, gameInfo, maxDrawId, game_id);
//            if (redraw < 0) {
//                logger.error("insert drawinfo into DB error," + redraw);
//                return -4;
//            }
//            //同步term库
//            syncMeta2Team();
//
//            int cachere = dataIntoCache(drawList, kDrawList, gameInfo, maxDrawId, game_id);
//            if (cachere < 0) {
//                logger.error("insert drawinfo into DB error," + redraw);
//                return -5;
//            }
//            //给文件系统发送开新期协议（3713）
//            result = fsNewDraw(game_id, maxDrawId);
//            if (result < 0) {
//                logger.error("fs protocol(3713 NewDraw) error,game_id:" + game_id + ",draw_id:" + maxDrawId);
//                return -6;
//            }
//            logger.info("-----------end pre kdraw--------------");
//        } catch (ParseException e) {
//            logger.error("", e);
//            return -10;
//        }
//
//        logger.info("preScheduleKDrawAuto success");
//        return 0;
//    }
//
//    /**
//     * 新期数据入库
//     *
//     * @param drawList 期列表
//     * @param kDrawList keno期列表
//     * @param gameInfo 游戏信息
//     * @param maxDrawId 当前期
//     * @param game_id 游戏id
//     * @return
//     */
//    private int dataIntoDB(List<GameDrawInfo> drawList, List<GameKDrawInfo> kDrawList, GameInfo gameInfo, int maxDrawId, int game_id) {
//        int result = -1;
//        //把大期和快开期插入oracle数据库
//        for (GameDrawInfo curDraw : drawList) {
//            result = drawInfoDao.insertGameDrawInfo(metaJdbcTemplate, curDraw);
//            if (result < 0) {
//                logger.error("insert GameDrawInfo into db error,game_id:" + game_id);
//                return -3;
//            }
//        }
//        for (GameKDrawInfo curKDraw : kDrawList) {
//            int re = this.insertGameKDrawInfo(curKDraw);
//            if (re < 0) {
//                logger.error("inert GameKDrawInfo into db error:" + re);
//                break;
//            }
//        }
//        //更新游戏当前期字段，包括数据库和ehcache缓存
//        gameInfo.setCur_draw_id(maxDrawId);//更新数据库中游戏当前期字段，基础库
//        int gameInfoRe = gameInfoDao.updateGameInfo(metaJdbcTemplate, gameInfo);
//        if (gameInfoRe < 0) {
//            logger.error("update db game cur_draw_id error:" + gameInfoRe + ",game_id:" + game_id);
//            return -13;
//        }
//        //初始化终端流水号表和终端期统计表
//        //初始化 投注机彩票投注流水号表(T_tmn_serial_no)，终端库
//        int tmnre1 = currentTmnDrawStatDao.initTmnSerialNo(termJdbcTemplate, game_id, maxDrawId);
//        if (tmnre1 <= 0) {
//            logger.error("init T_tmn_serial_no error:" + tmnre1);
//            return -7;
//        }
//        //查询代销商-运营商管理员用户表(T_dealer_user)，得到所有终端用户,终端库
//        List<DealerUser> dealerUserList = dealerUserdao.getAllRoleUSer(termJdbcTemplate, UserRole.tmnoperator);
//        if (dealerUserList == null || dealerUserList.isEmpty()) {
//            logger.error("T_dealer_user is null");
//            return -8;
//        }
//        //初始化 投注机期统计表(T_current_tmn_draw_stat)，跟终端用户相关，终端库
//        int tmnre = currentTmnDrawStatDao.initCurrentTmnDarwStat(termJdbcTemplate, game_id, maxDrawId, dealerUserList);
//        if (tmnre < 0) {
//            logger.error("init T_current_tmn_draw_stat error:" + tmnre);
//            return -9;
//        }
//
//        int cre = currkdrawdao.insertCurrentKdraw(termJdbcTemplate, kDrawList);
//        if (cre < 0) {
//            logger.error("init T_current_kdraw_stat error," + cre);
//            return -10;
//        }
//
//        //初始化实时统计-快开期玩法统计表(T_current_kdraw_play_stat)，跟玩法相关
//        //从数据库中获取游戏所有玩法列表
////        List<GamePlayInfo> playList = gamePlayInfoDao.getGamePlayInfoByGameId(metaJdbcTemplate, game_id);
////        if (playList == null || playList.isEmpty()) {
////            logger.error("GamePlayInfo is null,game_id:" + game_id);
////            return -10;
////        }
////        //拼装插入数据库的CurrentKDrawPlayStat列表，终端库
////        List<CurrentKDrawPlayStat> kdrawPlayStatList = new ArrayList<CurrentKDrawPlayStat>();
////        for (GameKDrawInfo curKDraw : kDrawList) {
////            for (GamePlayInfo curPlay : playList) {
////                CurrentKDrawPlayStat newStat = new CurrentKDrawPlayStat();
////                newStat.setGame_id(game_id);
////                newStat.setDraw_id(maxDrawId);
////                newStat.setKeno_draw_id(curKDraw.getKeno_draw_id());
////                newStat.setPlay_id(curPlay.getPlay_id());
////                newStat.setSale_money(BigDecimal.ZERO);
////                newStat.setSale_ticket_num(0);
////                newStat.setSale_stake_num(0);
////                newStat.setUndo_money(BigDecimal.ZERO);
////                newStat.setUndo_ticket_num(0);
////                newStat.setUndo_stake_num(0);
////                kdrawPlayStatList.add(newStat);
////            }
////        }
////        int statRe = currentKDrawPlayStatDao.batctInitCurrentStat(termJdbcTemplate, kdrawPlayStatList);
////        if (statRe < 0) {
////            logger.error("batch insert CurrentKDrawPlayStat into db error,game_id:" + game_id + ",draw_id:" + maxDrawId);
////            return -11;
////        }
//        //更新新期同步字，加1
//        result = updateSynList(game_id);
//        if (result < 0) {
//            logger.error("update SynList error,game_id:" + game_id);
//            return -31;
//        }
//        return 0;
//    }
//
//    /**
//     * 新期数据入缓存
//     *
//     * @param drawList
//     * @param kDrawList
//     * @param gameInfo
//     * @param maxDrawId
//     * @param game_id
//     * @return
//     */
//    private int dataIntoCache(List<GameDrawInfo> drawList, List<GameKDrawInfo> kDrawList, GameInfo gameInfo, int maxDrawId, int game_id) {
//        int result = -1;
//        int re2 = gameInfoCache.updateCurDraw(game_id, maxDrawId);//更新ehcache中游戏当前期字段
//        if (re2 < 0) {
//            logger.error("update ehcache game cur_draw_id error:" + re2 + ",game_id:" + game_id);
//            return -14;
//        }
//        //把大期和快开期插入redis缓存
//        for (GameDrawInfo curDraw : drawList) {
//            result = drawInfoCache.addGameDrawInfoIntoCache(curDraw);
//            if (result < 0) {
//                logger.error("insert GameDrawInfo into redis error,game_id:" + game_id + ",draw_id:" + maxDrawId);
//                return -5;
//            }
//        }
//        for (GameKDrawInfo curKDraw : kDrawList) {
//            result = kdrawInfoCache.addGameKdrawIntoCache(curKDraw);
//            if (result < 0) {
//                logger.error("insert GameKDrawInfo into redis error,game_id:" + game_id + ",draw_id:" + maxDrawId
//                        + ",keno_draw_id:" + curKDraw.getKeno_draw_id());
//                return -6;
//            }
//        }
//        //把游戏当前期信息放到redis缓存中，current:game_id--->draw_id
//        int garedi = drawInfoCache.setCurDrawIdToCache(game_id, maxDrawId);
//        if (garedi < 0) {
//            logger.error("save current game draw info into redis error:" + garedi);
//            return -12;
//        }
//        return 0;
//    }
//
//    /**
//     * 更新期次同步字
//     *
//     * @param game_id
//     * @return
//     */
//    private int updateSynList(int game_id) {
//        SynCodeInfo synCode = synListDao.getSynInfoByPrimary(metaJdbcTemplate, SynType.DRAW_SYN, game_id);
//        if (synCode == null) {
//            logger.error("SynCodeInfo is null,syn_type:" + SynType.DRAW_SYN + ",item_no:" + game_id);
//            return -1;
//        }
//        synCode.setSyn_code(synCode.getSyn_code() + 1);
//        int re = synListDao.updateSynCode(metaJdbcTemplate, synCode);
//        if (re < 0) {
//            logger.error("update SynList error,syn_type:" + SynType.DRAW_SYN + ",item_no:" + game_id);
//            return -2;
//        }
//
//        return 0;
//    }
//
//    /**
//     * meta库同步term库
//     */
//    private void syncMeta2Team() {
//        if (drawInfoDao.syncMeta2Term(metaJdbcTemplate) < 0) {
//            logger.error("sync GameDrawInfo from meta to term error");
//        }
//
//        if (kdrawInfoDao.syncMeta2Term(metaJdbcTemplate) < 0) {
//            logger.error("sync GameKDrawInfo from meta to term error");
//        }
//    }
//
//    /**
//     * 给文件系统发送开新期协议（3713）
//     *
//     * @param game_id
//     * @param draw_id
//     * @return
//     */
//    private int fsNewDraw(int game_id, int draw_id) {
//        NewDrawImpl protocal = new NewDrawImpl();
//        protocal.setGame_id(game_id);
//        protocal.setDraw_id(draw_id);
//
//        MsgHead mhead = new MsgHead();
//        mhead.setPkt_type(0);//报文分类
//        mhead.setPkt_id(3713);//报文编号
//        mhead.setMajor_ver_num(0);//协议版本信息
//        mhead.setMinor_ver_num(0);//协议版本信息
//        mhead.setIdt_type(0);//身份类型
//        mhead.setIdt_id(0);//身份编号
//        mhead.setCred_id(0);//证书编号
//        mhead.setSend_time(TimeUtil.formatDate_YMDT14(new Date()));//报文发送时间
//        mhead.setResp_code(0);//返回代码
//        mhead.setReserve(new byte[1]);//保留域
//        AllMessage am = protocal.call(mhead, SocketConfig.MANAGER_HOST, SocketConfig.MANAGER_PORT);
//        if (am == null) {
//            logger.error("AllMessage from fs is null");
//            return -1;
//        }
//        if (am.getHeader().getResp_code() != 0) {
//            logger.error("fs response error,resp_code:" + am.getHeader().getResp_code());
//            return -2;
//        }
//        return 0;
//    }
//
//    /**
//     * 给文件系统发送控制服务状态协议（3801），通知文件系统开始当前期的ETL进程
//     *
//     * @param game_id
//     * @param draw_id
//     * @return
//     */
//    private int fsStartETL(int game_id, int draw_id, int keno_draw_id, int action) {
//        CtrlSrvStatImpl protocol = new CtrlSrvStatImpl();
//        protocol.setServer_name(ServerName.ETL);
//        protocol.setControl_action(action);
//        protocol.setPara1(game_id);
//        protocol.setPara2(draw_id);
//        protocol.setPara3(keno_draw_id);
//
//        MsgHead mhead = new MsgHead();
//        mhead.setPkt_type(0);//报文分类
//        mhead.setPkt_id(3801);//报文编号
//        mhead.setMajor_ver_num(0);//协议版本信息
//        mhead.setMinor_ver_num(0);//协议版本信息
//        mhead.setIdt_type(0);//身份类型
//        mhead.setIdt_id(0);//身份编号
//        mhead.setCred_id(0);//证书编号
//        mhead.setSend_time(TimeUtil.formatDate_YMDT14(new Date()));//报文发送时间
//        mhead.setResp_code(0);//返回代码
//        mhead.setReserve(new byte[1]);//保留域
//        AllMessage am = protocol.call(mhead, SocketConfig.MANAGER_HOST, SocketConfig.MANAGER_PORT);
//        if (am == null) {
//            logger.error("AllMessage from fs is null");
//            return -1;
//        }
//        if (am.getHeader().getResp_code() != 0) {
//            logger.error("fs response error,resp_code:" + am.getHeader().getResp_code());
//            return -2;
//        }
//        return 0;
//    }
//
//    /**
//     * 快开期信息入库的操作
//     *
//     * @param metaJdbcTemplate
//     * @param kDrawInfo
//     * @return 0成功 -1期已存在 -2未知错误
//     */
//    private int insertGameKDrawInfo(GameKDrawInfo kDrawInfo) {
//        GameKDrawInfo kDraw = kdrawInfoDao.getKDrawByPrimary(metaJdbcTemplate, kDrawInfo.getGame_id(),
//                kDrawInfo.getDraw_id(), kDrawInfo.getKeno_draw_id());
//        if (kDraw != null) {
//            logger.info("kdraw info already exists,game_id:" + kDraw.getGame_id() + ",draw_id:"
//                    + kDraw.getDraw_id() + ",keno_draw_id:" + kDraw.getKeno_draw_id());
//            return -1;
//        }
//        int result = kdrawInfoDao.insertGameKDrawInfo(metaJdbcTemplate, kDrawInfo);
//        return result > 0 ? 0 : -2;
//    }
//
//    /**
//     * 获取快开期分页列表
//     *
//     * @param params
//     * @return
//     */
//    @Override
//    public Pagination<GameKDrawInfo> getGameKDrawInfoPageList(Map<String, Object> params) {
//        return kdrawInfoDao.getGameKDrawInfoPageList(metaJdbcTemplate, params);
//    }
//
//}
