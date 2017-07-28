package com.bestinfo.define.returncode;

/**
 * 错误返回码定义类
 *
 * @author hhhh
 */
public class TerminalResultCode {

    /**
     * 报文返回信息
     */
    public static final String msg = "";
    /**
     * 返回码 成功
     */
    public static final int success = 0;
    /**
     * dao层捕获try异常
     */
    public static final int daoExceError = 3000;
    /**
     * 未获得锁
     */
    public static final int lockerror = 3004;
    /**
     * 缓存捕获try异常
     */
    public static final int cacheExceError = 3010;
    /**
     * service层捕获try异常
     */
    public static final int serviceExceError = 3020;
   
    /**
     * Header中的加密芯片为空
     */
    public static final int tmnHeadSafeCardIsNull = 3025;
    /**
     * Header中的终端机口令为空
     */
    public static final int tmnHeadPwdIsNull = 3026;
    /**
     * Header中的终端编号不合法
     */
    public static final int tmnHeadTmnIdIsIncorrect = 3027;
    /**
     * 报文中公钥为空，不可以注册
     */
    public static final int tmnBodyPubkeyIsNull = 3036;
    /**
     * 报文中公钥签名为空，不可以注册
     */
    public static final int tmnBodyPubkeySignIsNull = 3037;
    /**
     * 校验报文中公钥签名失败，不可以注册
     */
    public static final int tmnCheckSignFail = 3038;
    /**
     * 终端服务器公钥pem字符串为空，没有初始化进去，不可以注册
     */
    public static final int tmnPemStrIsNull = 3039;
    /**
     * 注册报文中公钥转成字符串内容为空
     */
    public static final int tmnPublicKeyPemStrIsNull = 3040;
    /**
     * 报文上传票面密码为空
     */
    public static final int historyTicket_TicketCipherIsNull = 3181;

    /**
     * 注册时根据公钥转成的字符串获取publicKey对象为空
     */
    public static final int tmnPublicKeyObjIsNull = 3041;
    /**
     * 登录请求报文终端加密随机串为空
     */
    public static final int loginIdentityIsNull = 3046;
    /**
     * 登录认证报文中identity 为空
     */
    public static final int loginAtteIdentityIsNull = 3047;
    /**
     * 解密字符串和redis里字符串不相同
     */
    public static final int paseStrRedisStrDiff = 3048;
    /**
     * 把缓存中的串转成byte数组报错
     */
    public static final int getRedisStr2ByteFail = 3049;
    /**
     * 生成的会话密钥为空或长度不符合
     */
    public static final int decIdentityIsNullOrLenErr = 3050;
    /**
     * 彩票密码转化为票面密码失败
     */
    public static final int CIPHER_TO_TICKET_CIPHER_FAIL = 3062;
    /**
     * 登录产生的会话存入缓存失败
     */
    public static final int loginSessionSaveRedisFail = 3391;
    /**
     * 登录认证根据sessionid从redis里取数据为空
     */
    public static final int loginForRedisDataByKeyIsNull = 3392;
    /**
     * 3207终端上传的软件编号为空
     */
    public static final int tmnUploadSoftIdIsNull = 3186;
    /**
     * 3206终端上传参数中查询类型为空（站点/终端）
     */
    public static final int tmnUploadQueryTypeIsNull = 3055;
    /**
     * 3206终端上传参数中查询类型不正确（目前查询类型只能是站点的）
     */
    public static final int tmnUploadQueryTypeIsIncorrect = 3056;
    /**
     * 3206彩票终端中奖情况查询结果集为空（全库：T_STAT_KENO_DISTRIBUTION、T_STAT_KENO_PRIZE_ANN、T_STAT_PRIZE_ANN）
     */
    public static final int tmnPrizeQueryResultIsNull = 3199;
    /**
     * 终端上传参数中起始时间为空
     */
    public static final int tmnUploadStartTimeIsNull = 3051;
    /**
     * 终端上传参数中截止时间为空
     */
    public static final int tmnUploadEndTimeIsNull = 3052;
    /**
     * 终端上传参数中起始时间在截止时间之后
     */
    public static final int tmnUploadStartTimeIsAfterEndTime = 3053;
    /**
     * 终端上传参数中起始时间日期格式不正确
     */
    public static final int tmnUploadStartTimeIsFmtError = 3054;
    /**
     * 终端上传参数中截止时间日期格式不正确
     */
    public static final int tmnUploadeEndTimeIsFmtError = 3055;
    /**
     * 终端上传参数中起始时间或截止时间日期格式不正确
     */
    public static final int tmnUploadeStartTimeOrEndTimeIsFmtError = 3056;
    /**
     * 终端上传参数中截止时间超过起始时间30天
     */
    public static final int tmnUploadeEndTimeMoreThanStarTime30Days = 3057;
    /**
     * 文件路径为空
     */
    public static final int fileDownloadPathIsNull = 3066;
    /**
     * 文件不存在
     */
    public static final int FileNotExist = 3067;
    /**
     * 文件未发现异常
     */
    public static final int fileDownloadFileNotFoundException = 3068;
    /**
     * 文件操作异常
     */
    public static final int fileDownloadFileException = 3069;
    
    public static final int FileContentIsNull = 3070;
   
    /**
     * 获取游戏参数文件路径为空
     */
    public static final int gameParaPathIsNull = 3087;
   
    /**
     * 终端期统计查询-报文中上传期号和期名为空
     */
    public static final int drawStatDrawidAndDrawNameIsNull = 3192;

    /**
     * T_tmn_info
     */
    /**
     * 根据终端编号在数据库中没查到数据
     */
    public static final int tmnInfoForDBDotNot = 3200;
    /**
     * 更新数据库投注机信息失败
     */
    public static final int tmnUpdateForDBFail = 3201;
    /**
     * 更新数据库中投注机软件版本失败
     */
    public static final int tmnVersionUpdateForDBFail = 3202;
    /**
     * 投注机信息存入缓存失败
     */
    public static final int tmnForRedisUpdateFail = 3300;
    /**
     * 根据终端编号在 Redis缓存中 没查到数据
     */
    public static final int noTmnInfo = 3301;
    /**
     * 终端上传参数中大期名为空
     */
    public static final int noDrawname = 3071;
    /**
     * 终端上传参数中keno期名为空
     */
    public static final int noKenoDrawName = 3072;
    public static final int noGameid = 3073;
    /**
     * 终端已经注册，不可再注册
     */
    public static final int tmnAlreadyReg = 3400;
    /**
     * 终端没有绑定帐号
     */
    public static final int tmnNotAccount = 3401;
    /**
     * 终端在用状态为不可用
     */
    public static final int notWorkMark = 3404;
    /**
     * 终端状态为撤销
     */
    public static final int tmnStatusUndo = 3405;
    /**
     * 终端未注册
     */
    public static final int tmnNoRegStatus = 3406;
    /**
     * 终端类型不支持投注
     */
    public static final int tmnTypenotPermitBet = 3409;
    /**
     * 终端类型不支持注销
     */
    public static final int tmnTypenotPermitUndo = 3410;
    /**
     * 更新数据失败
     */
    public static final int tmnUpdateDataFail = 3507;
    /**
     * 终端上传的软件编号与Redis缓存里的软件编号不一致
     */
    public static final int tmnUploadSoftIdIsUnequalWithRedis = 3403;

    //SessionInfo
    /**
     * 根据sessionId获取SessionInfo对象为空
     */
    public static final int getSessionInfoByKeyIsNull = 3600;
    /**
     * 存入SessionInfo对象失败
     */
    public static final int saveSessionInfo2RedisFail = 3601;
    /**
     * T_account_info
     */
    /**
     * 在终端库中根据accountId没有查到账户信息
     */
    public static final int noAccountInfo = 3800;
    /**
     * 投注终端对应账户不可用
     */
    public static final int bet_Noaccount = 4100;

    /**
     * 投注终端余额不足
     */
    public static final int bet_NoMoney = 4101;

    /**
     * T_game_info
     */
    /**
     * 在终端中根据game_id从缓存中没有查到游戏信息
     */
    public static final int noGameInfo = 4300;

    /**
     * T_TERMINAL_SOFTWARE
     */
    /**
     * 在基础库中根据软件编号查询不出软件信息
     */
    public static final int noterminalSoftwareInfo = 4500;

    /**
     * 该软件工作状态为不启用
     */
    public static final int thisSoftwareIsnotWork = 4700;

    /**
     * T_TERMINAL_SUB_SOFTWARE
     */
    /**
     * 在基础库中根据软件编号和工作状态查询出子包信息为空
     */
    public static final int noTmnSubSoftInfoByIdAndStatus = 5500;

    /**
     * t_tmn_Privilege
     */
    /**
     * 投注机对应该游戏的特权不存在
     */
    public static final int noGameTmnPrivilege = 4900;
    /**
     * 投注机对应游戏特权工作状态值为不启用
     */
    public static final int gamePermit_NotWork = 5002;
    /**
     * 投注机对应游戏特权销售状态值为不启用
     */
    public static final int salePermit_NotWork = 5003;
    /**
     * 该游戏正在开奖，不允许投注
     */
    public static final int gameStop_Ok = 5004;
    /**
     * 投注机特权金额校验失败
     */
    public static final int bet_Money_CheckFail = 5005;
    /**
     * 投注机状态为不允许预售
     */
    public static final int pre_SalePermit_NotWork = 5006;
    /**
     * 投注机注销标识为不允许注销
     */
    public static final int tmnPrivilege_UndoPermitMark = 5007;

    /**
     * T_account_bank_record
     */
    /**
     * 3205站点缴款明细查询结果集为空
     */
    public static final int payInDetailQueryResultIsNull = 5100;

    /**
     * Ehcache没有内容列表数据
     */
    public static final int cmsInfoCacheNoData = 5200;
    /**
     * Ehcache没有地址列表数据
     */
    public static final int addressListCacheNoData = 5400;
    /**
     * Ehcache没有匹配终端类型的主备中心地址列表数据
     */
    public static final int addressListCacheNoListDataWithTerminalType = 5401;

    /**
     * T_current_tmn_date_stat
     */
    /**
     * 终端操作员日统计查询结果集为空
     */
    public static final int tmnOperatorDayStaticsQueryResultIsNull = 5600;
    /**
     * 终端游戏日统计查询结果集为空
     */
    public static final int tmnGameDayStaticsQueryResultIsNull = 5601;

    /**
     * T_CURRENT_TMN_DRAW_STAT
     */
    /**
     * 终端操作员期统计查询结果集为空
     */
    public static final int tmnOperatorDrawStaticsQueryResultIsNull = 5700;
    /**
     * 数据库没有终端流水号列表数据
     */
    public static final int serialNoDatabaseNoData = 6000;

    /**
     * 没有查询到SystemInfo对象
     */
    public static final int systemInfoCacheNoData = 6131;
    /**
     * t_account_detail
     */
    /**
     * 维护费统计查询结果集为空
     */
    public static final int accountSummaryQueryResultIsNull = 5800;

    /**
     * t_trade_type
     */
    /**
     * 在Ehcache中根据交易类型编号查询交易类型信息为空
     */
    public static final int queryTradeTypeFromEhcacheByIdResultIsNull = 5900;
    /**
     * T_SYSTEM_INFO
     */
    /**
     * 在缓存中没有省系统信息
     */
    public static final int no_SystemInfo = 5300;
    /**
     * T_game_kdraw_info
     */
    /**
     * 在基础库中根据游戏编号和快开小期名查不到快开期信息
     */
    public static final int no_GameKDrawInfo_MetaDB = 6593;
    /**
     * 缓存中期信息不存在
     */
    public static final int no_GameKDrawInfo = 6631;
    /**
     * 期名与缓存中期名不一致
     */
    public static final int wrong_KDrawName = 6651;

    /**
     * 投注期只能是当前期
     */
    public static final int wrong_DrawProStatus = 3098;
    /**
     * 期的销售结束时间已经到了
     */
    public static final int wrong_DrawSaleTime = 6653;
    /**
     * 投注时间必须在期销售开始时间之后
     */
    public static final int wrong_BeforeDrawSaleBeginTime = 6652;
    /**
     * 投注时间必须在期销售结束时间之前
     */
    public static final int wrong_AfterDrawSaleEndTime = 6654;
    /**
     * 期不能预售
     */
    public static final int wrong_DrawCanNoSale = 6655;
    /**
     * 预售期不能大于最大预售期号
     */
    public static final int wrong_DrawNoBigThanMaxDraw = 6656;
    /**
     * 期状态大于兑奖期的状态（500）
     */
    public static final int cashTicket_draw_Late = 6558;
    /**
     * 期状态小于兑奖期的状态(500)
     */
    public static final int cashTicket_draw_Before = 6559;
    /**
     * 兑奖时间已经结束
     */
    public static final int cashTicket_Time_Late = 6557;
    /**
     * 起始期号和结束期号内快开游戏数据列表为空
     */
    public static final int gameKDrawInfo_NoKdraw = 6632;
    /**
     * T_game_draw_info
     */
    /**
     * 根据期名获取期信息为空
     */
    public static final int getDrawInfoByNameIsNull = 6491;
    /**
     * 存储过程未查到期信息，条件主键
     */
    public static final int procNoDrawInfoByPk = 6492;
    /**
     * 存储过程未找到当前期
     */
    public static final int procNoCurrDrawInfo = 6493;
    /**
     * 预售期必须在当前期之后
     */
    public static final int wrong_SALINGBigThanPRESALE = 6551;
    /**
     * 期状态不是预售期
     */
    public static final int wrong_DrawNotPRESALE = 6552;
    /**
     * 期状态不为正在销售并且不是预售
     */
    public static final int wrong_DrawNotSalingAndPRESALE = 6553;
    /**
     * 期结束时间已过
     */
    public static final int wrong_DrawEntTimePassed = 6554;
    /**
     * 投注时间已过期销售结束时间
     */
    public static final int wrong_BetTimeAfterDrawEndTime = 6555;
    /**
     * 期未开始销售时间
     */
    public static final int wrong_DrawTimeNoBeginSale = 6556;
    /**
     * 缓存中不存在该游戏对应的该期号
     */
    public static final int THIS_GAME_HAS_NO_DRAW_INFO = 6532;
    /**
     * 该起始期号和结束期号内普通游戏列表数据为空
     */
    public static final int gameDrawInfo_NoDraw = 6531;

    /**
     * 投注模块
     */
    /**
     * 游戏类型错误
     */
    public static final int wrong_GameType = 3099;
    /**
     * 终端上传参数-投注号码拆号错误
     */
    public static final int bet_SplitNums = 3100;
    /**
     * 终端上传参数-一张票多玩法,对玩法校验错误
     */
    public static final int WrongGamePlayInfo = 3101;
    /**
     * 终端上传参数-投注号码投注倍数不正确
     */
    public static final int bet_BetMultiple_Wrong = 3102;
    /**
     * 终端上传参数-投注号码投注注数不正确
     */
    public static final int bet_stakes_Wrong = 3103;
    /**
     * 终端上传参数-投注号码投注金额不正确
     */
    public static final int bet_Check_BetMoney = 3104;
    /**
     * 风控号码查询失败
     */
    public static final int riskcontrolNumber = 3105;
    /**
     * 存在风控号码
     */
    public static final int riskcontrolNumberFind=3106;
    /**
     * 投注彩票密码无效
     */
    public static final int bet_CipherEncrypt_Fail = 3096;
    /**
     * 投注入库错误
     */
    public static final int bet_INTODB_Wrong = 3097;

    /**
     * T_game_play_info
     */
    /**
     * 缓存中不存在该游戏玩法
     */
    public static final int no_GamePlayInfo = 6231;
    /**
     * 缓存中该游戏玩法状态为不启用
     */
    public static final int gamePlayInfo_NoWork = 6252;
    /**
     * T_PLAY_BET_MODE
     */
    /**
     * 缓存中不存在该投注方式
     */
    public static final int no_BetMode = 6331;
    /**
     * 缓存中该投注方式为不启用
     */
    public static final int BetMode_WorkStatus_No = 6352;
    /**
     * T_GAME_SIGN_INFO
     */
    /**
     * 缓存中不存在该游戏符号
     */
    public static final int noNameSingInfo = 6431;
    /**
     * 投注号码不在缓存中符合定义的范围之内
     */
    public static final int wrongGameSingInfo = 6452;
    /**
     * T_TMN_SERIAL_NO
     */
    /**
     * 投注终端流水号错误
     */
    public static final int bet_NoSerial = 6021;

    /**
     * 注销模块
     */
    /**
     * 票面密码错误
     */
    public static final int ticketCipherDecrypt_Fail = 3121;
    /**
     * 注销时间与销售时间间隔超出允许范围
     */
    public static final int undo_cantUndo_TimeOver = 3123;
    /**
     * 该彩票不是本投注机卖出去的，不允许注销
     */
    public static final int undo_cantUndo_IsNotThisTmn = 3122;
    /**
     * 访问连接数据库失败
     */
    public static final int undo_getDataSource_Fail = 3127;
    /**
     * 彩票不是本期的票，不允许注销
     */
    public static final int undo_cantUndo_IsNotCurDraw = 3124;

    /**
     * 该期已经结束销售，不允许注销
     */
    public static final int undo_cantUndo_DrawIsEnd = 3126;

    /**
     * T_ticket_bet
     */
    /**
     * 终端库中该票不存在
     */
    public static final int undo_NoThisTicket = 6691;
    /**
     * 终端库中没有投注信息
     */
//    public static final int NO_BET_INFO = 6692;

    /**
     * 终端库中该彩票已注销
     */
    public static final int undo_HaveUndoed = 6751;
    /**
     * 票投注号码md5错误
     */
    public static final int ticketmd5Error = 6752;

    //T_CITY_INFO
    /**
     * 根据省市id没有查询到CityInfo对象
     */
    public static final int noCityInfoById = 6171;

    //T_STAT_TMN_DRAW
    /**
     * 期统计查询没有符合条件的数据
     */
    public static final int drawStatNoInfo = 6851;
    /**
     * 兑奖模块
     */

    /**
     * 兑奖时间还没到
     */
    public static final int cashTicket_time_before = 3158;
    /**
     * 兑奖失败
     */
    public static final int cashFailed = 3154;
    /**
     * 没有中奖
     */
    public static final int cash_NoLucky = 3155;

    /**
     * 本票已兑奖
     */
    public static final int cash_HaveCashed = 3156;
    /**
     * 投注机所兑奖的票，计算出来是需要交税
     */
    public static final int cash_NeedPayTaxes = 3157;
    /**
     * 缓存中开奖公告数据为空
     */
    public static final int openPrize_NoData = 6791;

    /**
     * T_lucky_no
     */
    /**
     * 缓存中开奖号码数据为空
     */
    public static final int luckyNumber_NoNumber = 6950;

    public static final int synCodeListDatabaseNoData = 6970;

    /**
     * 该终端没有要接收的通知
     */
    public static final int thisTmnHasnoHistoryNoticeData = 5230;
    /**
     * t_ticket_prize_class
     */
    /**
     * 终端库中奖详情数据为空
     */
    public static final int NoTicketPrizeClass = 6912;
    /**
     * Redis里没有游戏中奖汇总数据(普通游戏：StatPrizeAnnRedis ; 快开游戏 ：StatKenoPrizeAnnRedis)
     */
    public static final int noStatPrizeAnn = 7011;

    /**
     * 游戏在用状态为不可用
     */
    public static final int noUsedMark = 4400;
    /**
     * 游戏销售状态为不允许销售
     */
    public static final int noSaleMark = 4401;
    /**
     * 游戏注销标识为不允许注销
     */
    public static final int gameInfo_UndoMark_no = 4402;
    /**
     * 游戏兑奖标识为不允许兑奖
     */
    public static final int gameInfo_cashMark_no = 4403;
    /**
     * 报文中游戏类型与缓存中游戏类型不一致
     */
    public static final int gameTypeNoSame = 4404;
    /**
     * 投注机兑奖标识为不允许兑奖
     */
    public static final int tmnPrivilege_CashPermitMark = 4407;
    
    

}
