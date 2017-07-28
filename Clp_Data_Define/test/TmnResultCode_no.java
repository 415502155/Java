package com.bestinfo.define.returncode;

/**
 * 错误返回码枚举类
 *
 * @author hhhh
 */
public enum TmnResultCode {

    /**
     * 成功
     */
    SUCCESS(0, "成功"),
    NO_DATAS(500,"数据为空"),
    /**
     * 业务处理部分出现异常
     */
    ERROR(501, "数据异常"),
    QUERY_FAIL(502, "查询失败"),
    /**
     * 游戏校验码
     */
    NoGameInfo(600, "游戏查询失败"),
    GameInfo_UsedMark_Nouse(601, "游戏已停用"),
    GameInfo_SaleMark_NoWork(602, "游戏不允许销售"),
    GameInfo_gameType_Wrong(603, "游戏类型错误"),
    GameInfo_GameCheck_Wrong(604, "游戏校验失败"),
    GameInfo_cashMark_no(605, "游戏不允许兑奖"),
    GameInfo_UndoMark_no(606, "游戏不允许注销"),
    NoGamePlayInfo(607, "游戏玩法不存在"),
    WrongGamePlayInfo(608, "游戏玩法错误"),
    GamePlayInfo_WorkStatus_No(609, "游戏玩法不启用"),
    NoBetMode(610, "投注方式查询失败"),
    BetMode_WorkStatus_No(611, "投注方式不启用"),
    NoGameSingInfo(612, "游戏符号查询失败"),
    WrongGameSingInfo(613, "游戏符号错误"),
    /**
     * 投注机校验码
     */
    NoTmnInfo(700, "投注机查询失败"),
    NoTmnPrivilege(701, "投注机游戏特权查询失败"),
    TmnPrivilege_GamePermit_NotWork(702, "游戏特权工作状态值为不启用"),
    TmnPrivilege_SalePermit_NotWork(703, "游戏特权销售状态值为不启用"),
    TmnPrivilege_PreSalePermit_NotWork(704, "投注机不允许预售"),
    TmnPrivilege_GameStop_Ok(705, "该游戏正在开奖，不允许投注"),
    TmnPrivilege_notok(709, "终端不可用"),
    TmnPrivilege_betMoney(706, "投注机特权金额校验失败"),
    TmnPrivilege_UndoPermitMark(707, "投注机不允许注销"),
    TmnPrivilege_CashPermitMark(708, "投注机不允许兑奖"),
    /**
     * 省系统信息
     */
    SystemInfo_select(800, "省系统信息获取失败"),
    /**
     * 期校验
     */
    NoGameDrawInfo(900, "期信息不存在"),
    WrongDrawName(901, "期名不一致"),
    DrawProStatus_No(902, "期状态校验错误"),
    DrawSaleTimeWrong(903, "期销售时间校验错误"),
    DrawPreSaleWrong(904, "期不能预售"),
    WrongDraw(905, "期参数错误"),
    /**
     * 投注
     */
    Bet_stakes(3101001, "注数错误"),
    Bet_SplitNums(3101027, "拆号错误"),
    Bet_BetMultiple_Wrong(3101032, "投注倍数不正确"),
    Bet_Check_BetMoney(3101033, "投注金额有问题"),
    Bet_INTODB_Wrong(3101034, "入库错误"),
    Bet_CipherEncrypt_Fail(3101035, "彩票密码无效"),
    Bet_NoMoney(3101002, "余额不足"),
    Bet_NoSerial(3101003, "流水号错误"),
    Bet_noaccount(3101004, "帐户不可用"),
    /**
     * 注销(需要完善存储过程)
     */
    Undo_getDataSource_Fail(3102007, "访问数据库失败"),
    TicketCipherDecrypt_Fail(3102008, "票面密码无效"),
    CipherMelt_Fail(3102009, "彩票密码解析失败"),
    Undo_NoThisTicket(3102010, "该彩票不存在"),
    Undo_HaveUndoed(3102011, "彩票已注销"),
    Undo_cantUndo_IsNotThisTmn(3102013, "该彩票不是本投注机卖出去的，不允许注销"),
    Undo_cantUndo_IsNotCurDraw(3102014, "该彩票不是本期的票，不允许注销"),
    Undo_cantUndo_DrawIsEnd(3102015, "期已经结束销售，不允许注销"),
    Undo_cantUndo_TimeOver(3102016, "注销时间与销售时间间隔超出允许范围"),
    Undo_cantUndo_NoAccountInfo(3102017, "没有帐户信息"),
    
    /**
     * 兑奖(需要完善存储过程)
     */
    cashNoThisTicket(3103003, "该彩票不存在"),
    cashTicket_time_before(3103004, "兑奖开始时间还没到"),
    cashTicket_time_late(3103005, "兑奖结束时间已过"),
    cashNoPermit(3103006, "该投注机不允许兑奖"),
    cashHaveCashed(3103007, "本票已兑"),
    cashNolucky(3103008, "彩票未中奖"),
    cashfailed(3103009, "兑奖失败"),
    cashNoright(3103010, "没有兑奖权限"),
    /**
     * 文件下载
     */
    DownLoadFile_NoPath(3021002, "文件下载路径为空"),
    DownLoadFile_NoFile(3021003, "文件为空"),
    DownLoadFile_File_Error(3021005, "文件下载出现异常"),
    DownLoadFile_File_NotFind(3021006, "文件未找到"),
    DownLoadFile_File_Transform_Faile(3021007, "文件下载字节转换出现异常"),
    DownLoadFile_File_Exception(3021008, "文件下载出现异常"),
    /**
     * 注册
     */
    TerminalRegister_Fail(3010001, "注册失败"),
    TerminalRegister_exsits(301002, "已经注册过"),
    TerminalRegister_Noterminal(301003, "终端不存在"),
    TerminalRegister_NoterminalPubkey(301004, "读取公钥失败"),
    TerminalRegister_NoterminalPubkeySignature(301005, "读取公钥失败"),
    TerminalRegister_NosafeCardId(301005, "读取公钥失败"),
    TerminalRegister_AccountZero(3010006, "帐户为空"),//YR 2014.10.24 add
    TerminalRegister_VerifySignError(3010007, "签名校验失败"),
    /**
     * 登录请求
     */
    CanLogin_NO(3011001, "终端未注册，不可以登陆"),
    CanLogin_NOTerminalIdentity(3011002, "登录请求加密串为空"),
    Login_Fail(3011003, "登录失败"),
    /**
     * 登录认证
     */
    LoginCheck_Fail(3012001, "登录认证失败"),
    LoginCheck_NoTmnInfo(3012002, "终端不存在"),
    LoginCheck_NoDealerUser(3012003, "终端用户为空"),
    LoginCheck_Noidentity(3012005, "Identity为空"),
    LoginCheck_SaveSessionInfo_Fail(3012006, "保存失败Session"),
    LoginCheck_Noidentity_Redis(3012007, "redis缓存中Identity为空"),
    LoginCheck_Checkidentity_Fail(3012008, "解密字符串与Redis字符串不相同"),
    /**
     * 登出，没有session_id对用的会话
     */
    Logout_Save1(3013001, "登出失败"),
    Logout_NoSession(3013003, "该会话不存在"),
    Logout_Exception(3013004, "登出出现异常"),
    /**
     * 系统参数下载
     */
    SystemInfo_NoTmnInfo(3015001, "终端投注机信息为空"),
    SystemInfo_NoCenterInfo(3015003, "中心数据为空"),
    SystemInfo_NoDealerInfo(3015005, "代销商信息为空"),
    SystemInfo_NoCityInfo(3015006, "城市信息为空"),
    SystemInfo_NoSystemInfo(3015007, "省信息为空"),
    /**
     * 操作员信息下载
     */
    OperatorInfo_NoData(3019001, "操作员信息数据列表为空"),
    /**
     * 历史票查询
     */
    HistoryTicket_NoTicket(3105001, "该票不存在"),
    HistoryTicket_NoDrawInfo(3105002, "该游戏期信息不存在"),
    HistoryTicket_NoCipher(3105003, "彩票密码不存在"),
    HistoryTicket_TicketCipherIsNull(3105004, "上传票面密码为空"),
     HistoryTicket_NoGameInfo(3105005, "该游戏信息不存在"),
    /**
     * 开奖公告查询
     */
    GameInfoIsNull(3106001, "该游戏不存在"),
    OpenPrize_NoData(3106002, "开奖公告为空"),
    /**
     * 开奖号码查询
     */
    LuckyNumber_NoNumber(3107001, "开奖号码数据为空"),
    LuckyNumber_NoKdraw(3107003, "快开游戏开奖数据为空"),
    LuckyNumber_NoDraw(3107004, "普通游戏开奖数据为空"),
    LuckyNumber_NoGameInfo(3107005, "游戏不存在"),
    /**
     * 站点游戏日统计查询
     */
    StatisticQuery_NoTmnInfo(3202001, "没有该投注机信息"),
    StatisticQuery_NoData(3202002, "站点日统计数据为空"),
    StatisticQuery_NodealerUser(3202004, "操作员用户名为空"),
    StatisticQuery_BeginTime_After_EndTime(3202005, "开始时间在截止时间之后"),
    StatisticQuery_BeginTime_Incorrect(3202006, "开始时间数据不合法"),
    StatisticQuery_BeginTime_ISNULL(3202007, "开始时间为空"),
    StatisticQuery_EndTime_Incorrect(3202008, "截止时间数据不合法"),
    /**
     * 站点操作员日统计查询
     */
    StatisticOperatorQuery_NoData(3204001, "站点操作员日统计数据为空"),
    StatisticOperatorQuery_NodealerUser(3204002, "操作员用户名为空"),
    StatisticOperatorQuery_BeginTime_After_EndTime(3204003, "开始时间在截止时间之后"),
    StatisticOperatorQuery_BeginTime_Incorrect(3204004, "开始时间数据不合法"),
    StatisticOperatorQuery_BeginTime_ISNULL(3204005, "开始时间为空"),
    StatisticOperatorQuery_EndTime_Incorrect(3204006, "截止时间数据不合法"),
    
    /**
     * 终端操作员修改密码失败
     */
    MODIFY_PWD_INCORRECTDATA(3014001, "终端数据不正确"),
    MODIFY_PWD_NoDealerUser(3014002, "该用户不存在"),
    MODIFY_PWD_CheckOldPaswFail(3014003, "旧密码错误"),
    MODIFY_PWD_Faill(3014004, "修改失败"),
    /**
     * 通信参数下载----没有通信参数数据
     */
    NO_COMM_INFO_DATA(3018001, "没有通信参数数据"),
    /**
     * 内容列表下载----没有内容列表数据
     */
    NO_CONTENT_DATA(3020001, "没有内容列表数据"),
    /**
     * 地址列表下载----没有地址列表数据
     */
    NO_ADDRESS_DATA(3023001, "没有地址列表数据"),
    /**
     * 地址列表下载----没有符合条件的地址列表数据
     */
    NO_MATCHED_ADDRESS_DATA(3023002, "没有符合条件的地址列表数据"),
    /**
     * 地址列表下载----没有省系统参数数据
     */
    NO_SYSTEMINFO_DATA(3023003, "没有省系统参数数据"),
    /**
     * 期信息下载----游戏编号为空
     */
    GAMEID_IS_NULL(3024001, "游戏编号为空"),
    /**
     * 彩票流水号下载----上传的终端机号错误
     */
    TERMINAL_ID_ERROR(3025004, "上传的终端机号错误"),
    /**
     * 彩票流水号下载-----上传的游戏编号错误
     */
    SEND_GAMEID_ERROR(3025005, "上传的游戏编号错误"),
    /**
     * 彩票流水号下载------彩票流水号下载失败
     */
    LOTTERY_LSH_DOWNLOAD_ERROR(3025001, "彩票流水号下载失败"),
    /**
     * 彩票流水号下载------下载失败
     */
    DOWNLOAD_FAIL(3025002, "下载失败"),
    /**
     * 投注查询
     */
    NO_THIS_GAME(3104001, "该游戏不存在"),
    CIPHER_TO_TICKET_CIPHER_FAIL(3104002, "彩票密码转化为票面密码失败"),
    NO_BET_INFO(3104003, "没有投注信息"),
    THIS_GAME_HAS_NO_DRAW_INFO(3104004, "该游戏没有该期信息"),
    THIS_TMN_IS_NOT_EXIST(3104005, "该终端不存在"),
    THIS_TMN_IS_NOT_BIND_ACCOUNT(3104006, "该终端没有绑定账户"),
    THIS_ACCOUNT_IS_NOT_EXIST(3104007, "该账户不存在"),
    /**
     * 历史通知查询
     */
    NO_HISTORY_NOTICE_DATA(3108001, "没有符合条件的历史通知数据"),
    BEGIN_TIME_AFTER_ENDTIME(3108002, "起始时间在截止时间之后"),
    BEGIN_TIME_OR_ENDTIME_INCORRECT(3108003, "起始时间或截止时间数据不合法"),
    HN_BEGIN_TIME_IS_NULL(3108004, "起始时间为空"),
    HN_END_TIME_IS_NULL(3108005, "截止时间为空"),
    /**
     * 站点操作员期统计查询-----没有数据
     */
    NO_DATA(3203001, "没有数据"),
    /**
     * 站点操作员期统计查询-----查询异常
     */
    QUERY_ERROR(3203002, "查询异常"),
    /**
     * 没有操作员信息
     */
    SiteOpTermStatisQuery_NodealerUser(32030003, "操作员信息为空"),
    /**
     * 站点缴款明细查询---没有此终端信息
     */
    NO_THE_TMN_INFO(3205001, "没有此终端信息"),
    /**
     * 站点缴款明细查询---没有符合条件的数据
     */
    NO_MATCHING_DATA(3205003, "没有符合条件的数据"),
    PayInDetailQuery_NodealerUser(3205004, "操作员信息为空"),
    PayInDetailQuery_ParaError(3205005, "参数有误"),
    PayInDetailQuery_BeginTimeIsnull(3205006, "开始时间为空"),
    PayInDetailQuery_EndTimeIsnull(3205007, "截止时间为空"),
    /**
     * 彩票终端中奖情况查询
     */
    TmnQueryPrize_NoThisGame(3206001, "没有此游戏信息"),
    TmnQueryPrize_NoTerminalInfo(3206002, "没有此终端信息"),
    TmnQueryPrize_NoTerminalListInfo(3206003, "未查询到所属代理商所有终端列表信息"),
    TmnQueryPrize_NoData(3206005, "没有符合条件的数据"),
    TmnQueryPrize_QueryTypeIsNull(3206007, "查询类型为空"),
    TmnQueryPrize_QueryTypeIsIncorrect(3206008, "查询类型不正确"),
    /**
     * 游戏参数路径下载
     */
    GameInfoPath_ListNull(3017001, "游戏id列表为空"),
    GameInfoPath_GetFail(3017002, "游戏参数路径获取失败"),
    GameInfoPath_NoExist(3017003, "文件不存在"),
    GameInfoPaht_NoPrevilege(3017004, "终端没有游戏特权"),
    /**
     * 彩票公告路径下载
     */
    BulletinPath_PathNull(3026001, "彩票公告路径为空"),
    BulletinPath_FileNoExist(3026002, "彩票公告文件不存在"),
    /**
     * 同步字列表
     */
    SynCodeList_ListNull(3027001, "同步字列表为空"),
    SynCodeList_NoTmnInfo(3027007, "终端信息为空"),
    /**
     * 余额查询
     */
    BalanceQuery_NoTmnInfo(3208001, "终端信息为空"),
    BalanceQuery_QueryFail(3208002, "余额查询失败"),
    /**
     * 终端期统计查询
     */
    TmnDrawStat_NoData(3201002, "期统计查询没有符合条件的数据"),
    TmnDrawStat_NoGameId(3201003, "上传游戏编号为空"),
    TmnDrawStat_NoDrawIdDrawName(3201004, "上传期号和期名为空"),
    TmnDrawStat_NoDrawByDrawName(3201005, "根据期名获取期信息失败"),
    /**
     * 软件列表下载
     */
    SoftListDownLoad_SoftwareIdIsNull(3207001, "上传软件编号为空"),
    SoftListDownLoad_TmnInfoIsNull(3207002, "该终端信息不存在"),
    SoftListDownLoad_SoftwareIdIsIncorrect(3207003, "软件编号不正确或者软件不启用"),
    SoftListDownLoad_SoftwareIsNull(3207004, "软件信息不存在"),
    SoftListDownLoad_SubSoftwareIsNull(3207005, "软件子包信息不存在"),
    /**
     * 游戏期与期名对应表
     */
    DrawidAndDrawName_GAMEID_IS_NULL(3209001, "上传游戏编号为空"),
    /**
     * 资金统计查询 -- 按时间和交易类型统计
     */
    AccountQuerySummaryIsNull(3210001, "未找到符合条件的统计信息"),
    AccountQuerySummary_ParaError(3210002, "参数有误"),
    AccountQuerySummary_DaysMore(3210003, "截止时间超过开始时间30天"),
    AccountQuerySummary_IncorrectTmnId(3210004, "终端编号不合法"),
    AccountQuerySummary_TmnIsNotExist(3210005, "终端不存在"),
    AccountQuerySummary_TmnIsNotBindAccount(3210006, "该终端没有绑定账户"),
    AccountQuerySummary_AccountIsNotExist(3210007, "账户信息不存在"),
    AccountQuerySummary_BeginTime_Or_EndTime_IsIncorrect(3210008, "开始时间或截止时间数据不合法");

    private TmnResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    /**
     * 返回信息
     */
    public final String msg;
    /**
     * 返回码
     */
    public final int code;

}
