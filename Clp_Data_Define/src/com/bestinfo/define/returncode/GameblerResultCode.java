package com.bestinfo.define.returncode;

/**
 * 公共返回代码定义
 *
 * @author J
 */
public enum GameblerResultCode {

    /**
     * 校验
     */
    success(0, "操作成功"),
    GameblerResultCode_type(1400, "发送类型错误"),//报头type校验错误
    GameblerResultCode_action(1401, "请求失败"),//报头action校验错误
    GameblerResultCode_dealer_id(1402, "运营商信息异常"),//报头dealer_id校验错误
    GameblerResultCode_terminal_id(1403, "终端信息异常"),//报头terminal_id校验错误
    GameblerResultCode_md5parse(1404, "解密异常"),
    GameblerResultCode_count(1405, "摘要验证异常"),//报文体解密错误
    GameblerResultCode_parse(1406, "解析内容异常"),//报文解析错误
    GameblerResultCode_assemble(1407, "组装内容异常"),//报文组装错误
    GameblerResultCode_md5(1408, "加密异常"),//报文体加密错误
    GameblerResultCode_abstract(1409, "摘要异常"),//报文体做摘要错误
    GameblerResultCode_database(1410, "数据异常"),//数据库操作错误
    GameblerResultCode_cache(1411, "数据异常"),//缓存数据操作错误
    GameblerResultCode_nologin(1716, "用户未登录"),//发送数据使用的终端未登录
    GameblerResultCode_number(1719, "交易流水号类型不匹配"),//交易流水号交易类型字段与执行的操作流程不匹配
    GameblerResultCode_userstatus(1727, "用户状态异常"),//已注销用户/已锁定用户执行操作
    GameblerResultCode_signparse(1412, "签名数据异常"),//
    GameblerResultCode_Md5Err(1413, "加密异常"),//Md5加密异常
    GameblerResultCode_copyErr(1415, "数据克隆异常"),//Md5加密异常
    GameblerResultCode_cipherErr(1416, "彩票密码错误"),//Md5加密异常
    GameblerResultCode_checkCodeErr(1417,"生成校验码错误"),
    //用户注册
    GameblerRegister_namerepeat(1500, "用户名已注册"),//用户名重复
    GameblerRegister_email(1501, "请使用有效的电子邮箱"),//请使用有效的电子邮箱
    GameblerRegister_idType(1502, "请选择有效的证件类型"),
    GameblerRegister_idTypeError(1733, "暂不支持证件类型"),
    GameblerRegister_IdNo(1503, "请填写有效的证件号码"),
    GameblerRegister_city(1728, "请选择有效的地市编号"),//用户注册地市编号错误
    GameblerRegister_noCity(1732, "该城市已停用"),
    GameblerRegister_accountType(1504, "请选择有效的账户类型"),//见编码类型值表3表
    GameblerRegister_payType(1505, "请选择有效的扣款类型"),//见编码类型值表4表
    GameblerRegister_prizeType(1506, "请选择有效的奖金返奖类型"),//见编码类型值表5表
    GameblerRegister_bankcard(1507, "请选择有效的绑定卡类型"),//见编码类型值表6表
    GameblerRegister_bankcardError(1734, "该卡已停用"),
    GameblerRegister_bank(1729, "请选择有效的银行编码"),//见编码类型值表15表
    GameblerRegister_bankError(1735, "没有绑定该银行"),
    GameblerRegister_dealer(1508, "请填写正确的绑定运营商信息"),//绑定dealer_id与报文头不一致
    GameblerRegister_registerError(1509, "用户注册失败"),//插入用户注册数据失败
    GameblerRegister_MobilePhone(1671, "请填写有效的手机号"),//手机号格式不正确
    GameblerRegister_MobileRepeat(1672, "手机号重复"),//库里已存在该手机号(针对无物理终端)
    GameblerRegister_debitType(1673, "请选择正确扣款类型"),//如代理方有物理终端，则扣款类型必须为后期结算。
    GameblerRegister_bindCardRepeat(1674, "银行卡重复"),//库里已存在该银行卡号(针对有物理终端)
    GameblerRegister_bindDealerId(1510, "请绑定运营商"),
    GameblerRegister_accountTypeError(1511, "账户已停用"),
    //用户绑定绑定卡.
    GameblerbindCard_noRegister(1526, "用户未注册请先注册"),
    GameblerbindCard_role(1541, "用户角色错误"),
    GameblerbindCard_pwdError(1527, "用户密码错误"),
    GameblerbindCard_dealer(1528, "请填写正确的运营商"),
    GameblerbindCard_AccountCard(1529, "用户已绑定卡信息"),
    GameblerbindCard_idType(1530, "请选择有效证件类型"),
    GameblerbindCard_IdNo(1531, "请填写正确证件号码"),
    GameblerbindCard_bindCardType(1532, "请选择有效卡类型"),
    GameblerbindCard_bindCardTypeError(1736, "该卡类型暂时停用"),
    GameblerbindCard_bindCardError(1533, "用户绑定卡失败"),
    GameblerbindCard_bank(1730, "请选择有效的银行编码"),
    //用户解除绑定卡
    GameblerUnbindCard_noRegister(1534, "用户未注册请先注册"),
    GameblerUnbindCard_role(1542, "用户角色错误"),
    GameblerUnbindCard_pwdError(1535, "用户密码错误"),
    GameblerUnbindCard_dealer(1536, "请填写正确的运营商"),
    GameblerUnbindCard_bankcard(1537, "用户未绑定卡信息"),
    GameblerUnbindCard_idType(1538, "请选择有效证件类型"),
    GameblerUnbindCard_IdNo(1539, "请填写正确证件号码"),
    GameblerUnbindCard_unbindCardError(1540, "用户解除绑定卡失败"),
    //用户信息查询
    GameblerQuery_noRegister(1548, "用户未注册请先注册"),
    GameblerQuery_role(1543, "用户角色错误"),
    GameblerQuery_pwdError(1549, "用户密码错误"),
    GameblerQuery_dealer(1550, "请填写正确的运营商"),
    //用户信息修改
    GameblerModify_noRegister(1551, "用户未注册请先注册"),
    GameblerModify_role(1544, "用户角色错误"),
    GameblerModify_dealer(1552, "请填写正确的运营商"),
    GameblerModify_accountType(1553, "请选择有效的账户类型"),
    GameblerModify_payType(1554, "请选择有效的扣款类型"),
    GameblerModify_prizeType(1555, "请选择有效的奖金返奖类型"),
    GameblerModify_modifyGambler(1556, "用户信息修改失败"),
    //用户密码修改
    GameblerModifyPwd_noRegister(1557, "用户未注册请先注册"),
    GameblerModifyPwd_role(1545, "用户角色错误"),
    GameblerModifyPwd_dealer(1558, "请填写正确的运营商"),
    GameblerModifyPwd_OldPwd(1559, "请填写原密码"),
    GameblerModifyPwd_NewPwd(1560, "请填写新密码"),
    GameblerModifyPwd_OldPwdError(1561, "原密码填写错误"),
    GameblerModifyPwd_modifyPwdError(1562, "用户密码修改失败"),
    //用户登录
    GameblerLogin_noRegister(1563, "用户未注册请先注册"),
    GameblerLogin_error(1670, "登录异常"),
    GameblerLogin_pwderror(1602, "请填写正确的密码"),
    //用户注销
    GameblerCancelGambler_noRegister(1721, "用户未注册请先注册"),
    GameblerCancelGambler_role(1546, "用户角色错误"),
    GameblerCancelGambler_dealer(1722, "请填写正确的运营商"),
    GameblerCancelGambler_noPwd(1723, "请填写密码"),
    GameblerCancelGambler_pwdError(1724, "密码填写错误"),
    GameblerCancelGambler_accountError(1725, "用户注销失败"),
    GameblerCancelGambler_CancelGamblerError(1726, "用户注销失败"),
    GameblerCancelGambler_TicketBetError(1727, "用户还有票没注销"),
    GameblerCancelGambler_TicketPrizeError(1728, "用户还没兑奖"),
    GameblerCancel_gamblerAccountError(1737, "用户账户有余额"),
    //彩民帐户汇总信息查询
    GameblerAccount_noRegister(1650, "用户未注册请先注册"),
    GameblerAccount_role(1546, "用户角色错误"),
    GameblerAccount_dealer(1651, "请填写正确的运营商信息"),
    GameblerAccount_noPwd(1652, "请填写密码"),
    GameblerAccount_pwdError(1653, "请填写正确的密码"),
    GameblerAccount_GamblerAccount(1654, "查询帐户汇总信息失败"),
    GameblerAccount_noGamblerAccount(1692, "没有该用户的汇总信息"),
    GameblerAccount_GameblerError(1712, "查询用户信息失败"),
    // 运营商登录
    DealerLogin_noRegister(1563, "用户未注册请先注册"),
    DealerLogin_pwdError(1564, "请填写正确的密码"),
    DealerLogin_dealer(1565, "请填写正确的运营商"),
    DealerLogin_error(1670, "登录异常"),
    //运营商注销
    DealerCancel_noRegister(1566, "请先注册管理员"),
    DealerCancel_error(1567, "退出系统失败"),
    //运营商总帐户充值
    DealerAccount_noRegister(1568, "未注册管理员请先注册"),
    DealerAccount_pwdError(1569, "请填写正确的密码"),
    DealerAccount_dealer(1570, "请填写正确的运营商"),
    DealerAccount_name(1571, "请填写负责人姓名"),
    DealerAccount_nameError(1572, "请填写正确的负责人"),
    DealerAccount_IdNo(1573, "请填写负责人身份证号"),
    DealerAccount_IdNoError(1574, "请填写正确的负责人身份证号"),
    DealerAccount_rechargeType(1575, "请选择有效的充值类型"),
    DealerAccount_rechargeError(1576, "充值失败"),
    //运营商代理充值/代理充值查询/代理提款查询
    ProxyRecharge_dealerandroleerror(1743, "根据运营商，用户角色查不到用户"),
    ProxyRecharge_noRegister(1577, "未注册管理员请先注册"),
    ProxyRecharge_pwdError(1578, "请填写正确的密码"),
    ProxyRecharge_dealer(1579, "请填写正确的运营商"),
    ProxyRecharge_name(1580, "请填写负责人姓名"),
    ProxyRecharge_nameError(1581, "请填写正确的负责人"),
    ProxyRecharge_IdNo(1582, "请填写负责人身份证号"),
    ProxyRecharge_IdNoError(1583, "请填写正确的负责人身份证号"),
    ProxyRecharge_noRegisterGamebler(1584, "用户未注册请先注册"),
    ProxyRecharge_dealerGameblerError(1585, "用户绑定运营商错误"),
    ProxyRecharge_RechargeType(1586, "请选择有效的充值类型"),
    ProxyRecharge_RechargeError(1587, "充值失败"),
    ProxyRecharge_MoneyError(1741, "金额格式错误"),
    ProxyRecharge_SourceError(1677, "资金来源不匹配"),
    ProxyRecharge_RechargeGameblerError(1708, "充值失败"),
    ProxyRecharge_RechargedealerError(1709, "充值失败"),
    ProxyRecharge_SerialNumberError(1718, "交易不存在"),
    Gambler_not_exist(8000, "彩民不存在"),
    Gambler_transaction_exist(8102, "彩民交易已存在"),
    Dealer_transaction_exist(8131, "运营商交易已存在"),
    No_dealer_account_info(8112, "没有该运营商账户信息"),
    Dealer_account_not_balance(1648, "运营商账户金额不足以充值"),
    Update_dealer_account_infor_failed(8111, "更新运营商帐户信息失败"),
    Update_gambler_account_infor_failed(8061, "更新彩民帐户信息失败"),
    //运营商总帐户提款
    DealerAccountDrawing_noRegister(1588, "未注册管理员请先注册"),
    DealerAccountDrawing_pwdError(1589, "请填写正确的密码"),
    DealerAccountDrawing_dealer(1590, "请填写正确的运营商"),
    DealerAccountDrawing_name(1591, "请填写负责人姓名"),
    DealerAccountDrawing_nameError(1592, "请填写正确的负责人"),
    DealerAccountDrawing_IdNo(1593, "请填写负责人身份证号"),
    DealerAccountDrawing_IdNoError(1594, "请填写正确的负责人身份证号"),
    DealerAccountDrawing_bankcard(1595, "请填写正确的负责人银行卡"),
    DealerAccountDrawing_DrawingType(1596, "请选择有效的提款类型"),
    DealerAccountDrawing_Balance(1597, "账户余额不足"),
    DealerAccountDrawing_DrawingError(1598, "提款失败"),
    //运营商代理提款/代理提款查询
    DealerProxyDrawing_noRegister(1678, "未注册管理员请先注册"),
    DealerProxyDrawing_pwdError(1679, "请填写正确的密码"),
    DealerProxyDrawing_dealer(1680, "请填写正确的运营商"),
    DealerProxyDrawing_name(1681, "请填写负责人姓名"),
    DealerProxyDrawing_nameError(1682, "请填写正确的负责人"),
    DealerProxyDrawing_IdNo(1683, "请填写负责人身份证号"),
    DealerProxyDrawing_IdNoError(1684, "请填写正确的负责人身份证号"),
    DealerProxyDrawing_noRegisterGamebler(1685, "用户未注册请先注册"),
    DealerProxyDrawing_dealerGamebler(1686, "用户绑定运营商错误"),
    DealerProxyDrawing_DrawingType(1687, "请选择有效的提款类型"),
    DealerProxyDrawing_DrawingError(1688, "提款失败"),
    DealerProxyDrawing_SourceError(1689, "资金来源不匹配"),
    DealerProxyDrawing_overdraft(1690, "资金透支"),
    DealerProxyDrawing_MoneyError(1742, "金额格式错误"),
    DealerProxyDrawing_GameblerDrawing(1710, "提款失败"),
    DealerProxyDrawing_dealerDrawing(1711, "提款失败"),
    DealerProxyDrawing_error(1718, "交易不存在"),
    //彩民流水号批量下载
    DownloadGameblerNum_noRegister(1599, "用户未注册请先注册"),
    DownloadGameblerNum_dealer(1600, "请填写正确的运营商"),
    DownloadGameblerNum_error(1601, "申请用户流水号失败"),
    //方案投注
    Betting_noRegister(1610, "用户未注册请先注册"),
    Betting_dealer(1611, "请填写正确的运营商"),
    Betting_ProjectType(1612, "请选择有效的方案类型"),
    Betting_Projectlevel(1613, "请选择有效的方案等级"),
    Betting_game(1614, "请选择有效的游戏"),
    Betting_draw(1615, "请选择有效期"),
    Betting_ondraw(1616, "请选择正在销售期"),
    Betting_kdraw(1617, "请选择有效keno期"),
    Betting_okkdraw(1618, "请选择可以销售keno期"),
    Betting_Play(1619, "请选择有效的玩法"),
    Betting_BettingMode(1620, "请选择有效的投注方式"),
    Betting_Multiplebreakbounds(1621, "投注倍数超界"), //    Betting_Number(1622, "请选择有效号码投注"),
    Betting_Bettingformat(1623, "请选择正确投注格式"),
    Betting_Betting_Bettingformat2(1624, "请选择正确投注格式"),
    Betting_Betting_Bettingformat3(1625, "请选择正确投注格式"),
    Betting_over(1626, "投注金额超界"),
    Betting_CalculationError(1627, "投注金额计算错误"),
    Betting_BettingError(1628, "投注失败"),
    Betting_BettingError2(1629, "投注失败"),
    Betting_RiskControl(1630, "号码已经风险控制"),
    Betting_BettingError3(1631, "投注失败"),
    Betting_BettingError4(1632, "投注失败"),
    Betting_BettingResults(1633, "请查询本次投注结果"),
    Betting_ContactCenter(1634, "请联系中心进行处理"),
    Betting_BettingError5(1635, "投注失败"),
    Betting_CorrectNoteNumber(1642, "请选择正确的注数"),
    Betting_BettingNumberError(1643, "投注号码错误"),
    Betting_BettingNumberError2(1644, "投注号码错误"),
    Betting_BettingError6(1645, "投注失败"),
    Betting_BettingType(1646, "请选择正确投注格式"),
    Betting_creditlow(1647, "余额不足"),
    Betting_creditlow2(1648, "余额不足"),
    Betting_BettingError7(1649, "投注失败"),
    Betting_BettingError8(1669, "投注失败"),
    Betting_AccountType(1675, "账户类型错误"),
    Betting_SourceError(1676, "资金来源错误"),
    Betting_Game(1691, "请选择正确游戏"),
    Betting_City(1707, "请填写正确的地市"),
    Betting_SettlementMethod(1717, "请选择正确的结算方式"),
    Betting_BusinessEncoding(1720, "请选择正确的业务编码"),
    Betting_WrongNumber(1731, "投注号码错误"), //   
    Betting_RiskCtrlError(1740, "风控错误"),
    //方案投注查询
    BettingQuery_noRegister(1636, "用户未注册请先注册"),
    BettingQuery_dealer(1637, "请填写正确的运营商"),
    BettingQuery_SerialNumber(1638, "请填写查询的交易流水号"),
    BettingQuery_noInformation(1639, "没有符合条件的信息"),
    BettingQuery_UserInformation(1640, "请填写正确的用户信息"),
    BettingQuery_dealer2(1641, "请填写正确的运营商信息"),
    BettingQuery_QueryFailure(1667, "查询失败"),
    BettingQuery_LaterQuery(1714, "请稍后查询"),
    BettingQuery_cancellation(1715, "方案已撤单"),
    //    //游戏规则文件下载地址查询
    DownloadGameRules_game(1655, "请填写正确的游戏"),
    DownloadGameRules_privilege(8036, "没有游戏特权"),
    //    //奖期信息下载
    DownloadDraw_game(1656, "请填写正确的游戏"),
    //    //开奖公告下载
    DownloadNotice_game(1657, "请填写正确的游戏"),
    DownloadNotice_draw_id(1658, "请填写正确的期"),
    DownloadNotice_kdraw_id(1659, "请填写正确的keno期"),
    DownloadNotice_draw_idkdraw_id(1660, "中心未发布开奖公告"),
    DownloadNotice_game2(1657, "请填写正确的游戏"),
    DownloadNotice_game3(1657, "请填写正确的游戏"),
    //    //文件下载地址查询
    DownloadFile_game(1661, "请填写正确的游戏"),
    DownloadFile_draw_id(1662, "请填写正确的期"),
    DownloadFile_kdraw_id(1663, "请填写正确的keno期"),
    DownloadFile_NoLottery(1664, "奖期未开奖"),
    DownloadFile_NotSold(1665, "本期未销售"),
    DownloadFile_NoWinning(1666, "本期没有中奖"),
    DownloadFile_noExpiryDate(1668, "本期还没兑完奖"),
    DealerGame_noSell(8035, "运营商游戏不允许销售"),
    //    //文件下载 
    DownloadFileS_FileType(1693, "填写正确的文件类型"),
    DownloadFileS_noFile(1694, "文件不存在"),
    DownloadFileS_draw(1713, "本期未结束销售，检查期状态"),
    DownloadFileS_FileType2(1693, "填写正确的文件类型"),
    DownloadFileS_FileType3(1693, "填写正确的文件类型"),
    //    //运营商账户汇总信息查询
    DealerAccountQuery_NOdealer(1695, "没找到运营商管理员"),
    DealerAccountQuery_dealer(1696, "请填写正确的运营商信息"),
    DealerAccountQuery_pwd(1697, "请填写密码"),
    DealerAccountQuery_pwderrot(1698, "请填写正确的密码"),
    DealerAccountQuery_QueryError(1699, "查询帐户汇总信息失败"),
    //    //快速游戏中奖记录查询
    RecordQuery_noCatalog(1700, "没该目录"),
    RecordQuery_NoLottery(1701, "奖期未开奖"),
    RecordQuery_noFilecontent(1702, "文件内容不存在"),
    RecordQuery_draw(1703, "请填写正确的期"),
    RecordQuery_kdraw(1704, "请填写正确的keno期"),
    RecordQuery_game(1705, "请填写正确的游戏"),
    RecordQuery_noExpiryDate(1706, "本期还没兑完奖"),;

    private GameblerResultCode(int code, String msg) {
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
