package com.bestinfo.cache.keys;

/**
 * redis缓存key
 *
 * @author yangyuefu
 */
public class RedisKeysUtil {

    public static final String lockkey = "lock:";

    /**
     * 终端登录缓存key
     *
     * @param sessionId 会话id
     * @return
     */
    public static String getIdentityKey(String sessionId) {
        StringBuilder sb = new StringBuilder();
        sb.append("session_id:");
        sb.append(sessionId);
        return sb.toString();
    }

    /**
     * 获取存放登陆验证用字符串的redis key名称
     *
     * @param sessionId
     * @return
     */
    public static String getLoginStrKey(String sessionId) {
        StringBuilder sb = new StringBuilder();
        sb.append("loginStr_id:");
        sb.append(sessionId);
        return sb.toString();
    }

    /**
     * 获取期信息对象的缓存key
     *
     * @param gameId 游戏编号
     * @param drawId 大期总期号
     * @param kdrawId 快开期编号 普通游戏期key设置此值为0
     * @return
     */
    public static String getGameDrawKey(int gameId, int drawId, int kdrawId) {
        StringBuilder sb = new StringBuilder();
        sb.append("gamedrawinfo:game:");
        sb.append(gameId);
        sb.append(":draw:");
        sb.append(drawId);
        if (kdrawId != 0) {
            sb.append(":kdraw:");
            sb.append(kdrawId);
        }
        return sb.toString();
    }

    /**
     * 根据期名获取期信息对象的缓存key
     *
     * @param gameId 游戏编号
     * @param drawName
     * @param kdrawName
     * @return
     */
    public static String getGameDrawKeyByDrawName(int gameId, String drawName, String kdrawName) {
        StringBuilder sb = new StringBuilder();
        sb.append("gamedrawinfo:game:");
        sb.append(gameId);
        sb.append(":drawname:");
        sb.append(drawName);
        if (kdrawName != null && !"".equals(kdrawName)) {
            sb.append(":kdrawname:");
            sb.append(kdrawName);
        }
        return sb.toString();
    }

    /**
     * 获取终端命令key
     *
     * @param terminalId
     * @return
     */
    public static String getTmnCmdKey(int terminalId) {
        StringBuilder sb = new StringBuilder();
        sb.append("terminalCommand:terminalId:");
        sb.append(terminalId);
        return sb.toString();
    }

    public static String getTmnHistoryCmdKey(int terminalId, String cmdId) {
        StringBuilder sb = new StringBuilder();
        sb.append("terminalCommand:terminalId:");
        sb.append(terminalId);
        sb.append(":historyCommand:");
        sb.append(cmdId);
        return sb.toString();
    }

    /**
     * 根据期名获取快开期信息对象的缓存key
     *
     * @param gameId 游戏编号
     * @param kdrawName
     * @return
     */
    public static String getGameKDrawKeyByKDrawName(int gameId, String kdrawName) {
        StringBuilder sb = new StringBuilder();
        sb.append("gamedrawinfo:game:");
        sb.append(gameId);
        if (kdrawName != null && !"".equals(kdrawName)) {
            sb.append(":kdrawname:");
            sb.append(kdrawName);
        }
        return sb.toString();
    }

    /**
     * 获取游戏信息的缓存key
     *
     * @param gameId
     * @return
     */
    public static String getGameInfoKey(int gameId) {
        StringBuilder sb = new StringBuilder();
        sb.append("gameInfo:");
        if (gameId != 0) {
            sb.append(gameId);
        }
        return sb.toString();
    }

    /**
     * 获取游戏列表信息的缓存key
     *
     * @return
     */
    public static String getGameInfoListKey() {
        StringBuilder sb = new StringBuilder();
        sb.append("gameInfoList:0");
        return sb.toString();
    }

//    public static String getTmnSerialNoKey(int terminalId, int gameId, int drawId) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("terminal:");
//        sb.append(terminalId);
//        sb.append(":game:");
//        sb.append(gameId);
//        sb.append(":draw:");
//        sb.append(drawId);
//        return sb.toString();
//    }
    /**
     * 获取终端信息对象的缓存key
     *
     * @param terminalId
     * @return
     */
    public static String getTmnInfoKey(int terminalId) {
        StringBuilder sb = new StringBuilder();
        sb.append("terminal:");
        sb.append(terminalId);
        return sb.toString();
    }

    /**
     * 获取终端特权信息对象的缓存key
     *
     * @param terminalId
     * @param gameId
     * @return
     */
    public static String getTmnPrivilegeKey(int terminalId, int gameId) {
        StringBuilder sb = new StringBuilder();
        sb.append("terminal:");
        sb.append(terminalId);
        sb.append(":game:");
        if (gameId != 0) {
            sb.append(gameId);
        }
        return sb.toString();
    }

    /**
     * 获取内容管理的缓存key
     *
     * @param cms_id
     * @return
     */
    public static String getTcmsInfoKey(long cms_id) {
        StringBuilder sb = new StringBuilder();
        sb.append("cms:");
        sb.append(cms_id);
        return sb.toString();
    }

    /**
     * 获取开奖号码对象的缓存key
     *
     * @param gameId
     * @param drawId
     * @param kdrawId
     * @return
     */
    public static String getLuckyNoKey(int gameId, int drawId, int kdrawId) {
        StringBuilder sb = new StringBuilder();
        sb.append("LuckyNo:game:");
        sb.append(gameId);
        sb.append(":draw:");
        sb.append(drawId);
        if (kdrawId != 0) {
            sb.append(":kdraw:");
            sb.append(kdrawId);
        }
        return sb.toString();
    }

    /**
     * 通过期名获取开奖号码对象的缓存key
     *
     * @param gameId
     * @param drawName
     * @param kdrawName
     * @return
     */
    public static String getLuckyNoKeyByDrawName(int gameId, String drawName, String kdrawName) {
        StringBuilder sb = new StringBuilder();
        sb.append("LuckyNo:game:");
        sb.append(gameId);
        sb.append(":drawname:");
        sb.append(drawName);
        if (kdrawName != null && !"".equals(kdrawName)) {
            sb.append(":kdrawname:");
            sb.append(kdrawName);
        }
        return sb.toString();
    }

    /**
     * 开奖公告缓存key
     *
     * @param gameId
     * @param drawId
     * @param kdrawId
     * @return
     */
    public static String getOpenPrizeKey(int gameId, int drawId, int kdrawId) {
        StringBuilder sb = new StringBuilder();
        sb.append("openprize_info:game:");
        sb.append(gameId);
        sb.append(":draw:");
        sb.append(drawId);
        sb.append(":kdraw:");
        sb.append(kdrawId);
        return sb.toString();
    }

    /**
     * 根据期名获取开奖公告缓存key
     *
     * @param gameId
     * @param drawName
     * @param kdrawName
     * @return
     */
    public static String getOpenPrizeKeyByDrawName(int gameId, String drawName, String kdrawName) {
        StringBuilder sb = new StringBuilder();
        sb.append("openprize_info:game:");
        sb.append(gameId);
        sb.append(":drawname:");
        sb.append(drawName);
        if (kdrawName != null && !"".equals(kdrawName)) {
            sb.append(":kdrawname:");
            sb.append(kdrawName);
        }
        return sb.toString();
    }

    /**
     * 普通游戏中奖汇总key
     *
     * @param gameId 游戏编号
     * @param drawId 期号
     * @param classId 奖级编号
     * @return
     */
    public static String getStatPrizeAnnKey(int gameId, int drawId, int classId) {
        StringBuilder sb = new StringBuilder();
        sb.append("statPrizeAnn:gameid:");
        sb.append(gameId);
        sb.append(":drawid:");
        sb.append(drawId);
        sb.append(":classid:");
        sb.append(classId);
        return sb.toString();
    }

    /**
     * 快开游戏中奖汇总key
     *
     * @param gameId 游戏编号
     * @param drawId 期号
     * @param kenoDrawId 快开期号
     * @param classId 奖级编号
     * @return
     */
    public static String getStatKenoPrizeAnnKey(int gameId, int drawId, int kenoDrawId, int classId) {
        StringBuilder sb = new StringBuilder();
        sb.append("statKenoPrizeAnn:gameid:");
        sb.append(gameId);
        sb.append(":drawid:");
        sb.append(drawId);
        sb.append(":kenoDrawId:");
        sb.append(kenoDrawId);
        sb.append(":classid:");
        sb.append(classId);
        return sb.toString();
    }

    /**
     * 获取银行登录 会话密钥的缓存key
     *
     * @param identity
     * @return
     */
    public static String getBankSessionKey(String identity) {
        StringBuilder sb = new StringBuilder();
        sb.append("bank:sessionId:");
        sb.append(identity);
        return sb.toString();
    }

    /**
     * 获取当前期号的缓存key
     *
     * @param gameId
     * @return
     */
//    public static String getCurDrawIdKey(int gameId) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("current:");
//        sb.append(gameId);
//        return sb.toString();
//    }
    //-------------------------电话投注代码----------------------------
    /**
     * 逻辑终端物理编号分配规则缓存key
     *
     * @param gameId 游戏编号
     * @param drawId 期编号
     * @param dealerId 代销商编号
     * @param physicsId 物理编号
     * @return
     */
    public static String getTmnPhyKey(int gameId, int drawId, String dealerId, int physicsId) {
        StringBuilder sb = new StringBuilder();
        sb.append("gambSerial:game:");
        sb.append(gameId);
        sb.append(":draw:");
        sb.append(drawId);
        sb.append(":dealer:");
        sb.append(dealerId);
        sb.append(":phy:");
        sb.append(physicsId);
        return sb.toString();
    }

    /**
     * 逻辑终端地市分配规则缓存key
     *
     * @param gameId 游戏编号
     * @param drawId 期编号
     * @param dealerId 代销商编号
     * @param cityId 地市编号
     * @return
     */
    public static String getTmnCityKey(int gameId, int drawId, String dealerId, int cityId) {
        StringBuilder sb = new StringBuilder();
        sb.append("gambSerial:game:");
        sb.append(gameId);
        sb.append(":draw:");
        sb.append(drawId);
        sb.append(":dealer:");
        sb.append(dealerId);
        sb.append(":city:");
        sb.append(cityId);
        return sb.toString();
    }

    /**
     * 运营商登录前缀
     */
    public static final String DealerLoginKey = "login:dealerid:";

    /**
     * 生成电话投注登录信息
     *
     * @param Dealerid
     * @param Terminalid
     * @return
     */
    public static String getDealerLogin(int Dealerid, int Terminalid) {
        StringBuilder b = new StringBuilder();
        b.append(DealerLoginKey);
        b.append(Dealerid);
        b.append("terminalid");
        b.append(":");
        b.append(Terminalid);
        return b.toString();
    }

    /**
     * 获取物理终端信息对象的缓存key
     *
     * @param terminal_phy_id
     * @return
     */
    public static String getPhyTmnInfokey(int terminal_phy_id) {
        StringBuilder sb = new StringBuilder();
        sb.append("terminal_phy:");
        sb.append(terminal_phy_id);
        return sb.toString();
    }

    /**
     * 获取游戏附加信息的缓存key
     *
     * @param gameId
     * @return
     */
    public static String getGameAddInfoKey(int gameId, int drawId) {
        StringBuilder sb = new StringBuilder();
        sb.append("gameAddInfo:game:");
        sb.append(gameId);
        sb.append(":drawId:");
        sb.append(drawId);
        return sb.toString();
    }

    /**
     * 获取二维码信息的缓存key
     *
     * @param d2code_id
     * @return
     */
    public static String getD2codeInfoKey(int d2code_id) {
        StringBuilder sb = new StringBuilder();
        sb.append("d2codeInfo:");
        sb.append(d2code_id);
        return sb.toString();
    }

    /**
     * 获取当前期号的缓存key
     *
     * @param date
     * @return
     */
    public static String getTerminalLoginKey(String date) {
        StringBuilder sb = new StringBuilder();
        sb.append("terLoginDate:");
        sb.append(date);
        return sb.toString();
    }

}
