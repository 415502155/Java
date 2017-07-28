package com.bestinfo.cache.keys;

/**
 *
 * @author hhhh
 */
public class CacheKeysUtil {

    /**
     * 代销商类型Key前缀
     */
    public static final String dealerTypeKey = "dealer_type:dealer_type:";
    /**
     * 省系统Key前缀
     */
    public static final String systemInfoKey = "system_info:province_id:";
    /**
     * 二级城市Key前缀
     */
    public static final String cityInfoKey = "city_info:province:";
    /**
     * 三级区县Key前缀
     */
    public static final String districtInfoKey = "district_info:province:";
    /**
     * 证件类型Key前缀
     */
    public static final String idTypeKey = "id_type:id_type_id:";
    /**
     * 银行编号Key前缀
     */
    public static final String bankCodeKey = "bank_code:bank_id:";
    /**
     * 游戏信息key前缀
     */
    public static final String gameInfoKey = "game_info:game_id:";
    /**
     * 游戏玩法信息前缀
     */
    public static final String gamePlayInfoKey = "play_info:game:";
    /**
     * 游戏奖级信息前缀
     */
    public static final String gameClassInfo = "class_info:game:";

    /**
     * 代销商游戏特权前缀
     */
    public static final String dealerPrivilege = "dealer_ privilege:dealer:";
    /**
     * 代销商信息前缀
     */
    public static final String dealerInfoKey = "dealer_info:dealer:dealer_id:";

    /**
     * 开奖次数信息前缀
     */
    public static final String gameMultiOpenKey = "multiplt_open:game_id:";
    /**
     * 终端信息前缀
     */
    public static final String tmnInfoKey = "terminal_info:terminal_id:";
    /**
     * 账户类型前缀
     */
    public static final String accountInfoKey = "account_type:account_type_id:";
    /**
     * 终端特权前缀
     */
    public static final String tmnPrivilegeKey = "terminal_privilege:terminal:";
    /**
     * 部门信息前缀
     */
    public static final String departmentKey = "depart_ment:department_id:";
    /**
     * 角色信息前缀
     */
    public static final String userroleKey = "user_role:role_id:";
    /**
     * 通讯参数前缀
     */
    public static final String commParaKey = "user_role:role_id:";//待确认
    /**
     * 内容管理前缀
     */
    public static final String cmsInfoKey = "cms_info:cms_id:";
    /**
     * 软件类型前缀
     */
    public static final String softwareKey = "software:software_id:";

    /**
     * 地址表前缀
     */
    public static final String addressKey = "province_id:";

    /**
     * 开奖公告查询前缀
     */
    public static final String openPrizeKey = "openprize_info:game:";

    /**
     * 站点星级前缀
     */
    public static final String agentRateRankKey = "agentRateRank:stationRank:";

    /**
     * 得到快开游戏期进度状态的缓存Key
     *
     * @param kenoProcessStatus 进度编号
     * @return Key
     */
    public static String getKDrawProStatusKey(int kenoProcessStatus) {
        StringBuilder b = new StringBuilder();
        b.append("kdraw_status:keno_process_status:");
        b.append(kenoProcessStatus);
        return b.toString();
    }

    /**
     * 得到游戏期进度状态的缓存Key
     *
     * @param processStatus 进度编号
     * @return Key
     */
    public static String getDrawProStatusKey(int processStatus) {
        StringBuilder b = new StringBuilder();
        b.append("draw_status:process_status:");
        b.append(processStatus);
        return b.toString();
    }

    /**
     * 得到方案类型的缓存Key
     *
     * @param schemeType 方案类型编号
     * @return Key
     */
    public static String getSchemeTypeKey(int schemeType) {
        StringBuilder b = new StringBuilder();
        b.append("scheme_type:scheme_type:");
        b.append(schemeType);
        return b.toString();
    }

    /**
     * 得到省系统参数的缓存Key
     *
     * @param provinceId 省系统编号
     * @return Key
     */
    public static String getSystemInfoKey(int provinceId) {
        StringBuilder b = new StringBuilder();
        b.append(systemInfoKey);
        b.append(provinceId);
        return b.toString();
    }

    /**
     * 得到省系统参数的缓存Key
     *
     * @return Key
     */
    public static String getSystemInfoKey() {
        StringBuilder b = new StringBuilder();
        b.append(systemInfoKey);
        b.append(0);
        return b.toString();
    }

    /**
     * 得到二级城市编号对象的缓存Key
     *
     * @param provinceId 省编号
     * @param cityId 地市编号
     * @return Key
     */
    public static String getCityInfoKey(int provinceId, int cityId) {
        StringBuilder b = new StringBuilder();
        b.append(cityInfoKey);
        if (provinceId != 0) {
            b.append(provinceId).append(":");
            if (cityId != 0) {
                b.append("city:");
                b.append(cityId);
            }
        }
        return b.toString();
    }

    /**
     * 得到三级区县编号对象的缓存Key
     *
     * @param provinceId 省编号
     * @param cityId 地市编号
     * @param districtId 区县编号
     * @return Key
     */
    public static String getDistrictInfoKey(int provinceId, int cityId, int districtId) {
        StringBuilder b = new StringBuilder();
        b.append(districtInfoKey);
        b.append(provinceId);
        b.append(":city:");
        b.append(cityId);
        b.append(":district:");
        b.append(districtId);
        return b.toString();
    }

    /**
     * 得到软件类型的缓存key
     *
     * @param softwareId 软件类型编号
     * @return key
     */
    public static String getTerminalSoftwareKey(int softwareId) {
        StringBuilder sb = new StringBuilder();
        sb.append(softwareKey);
        sb.append(softwareId);
        return sb.toString();
    }

    /**
     * 得到游戏信息的缓存key
     *
     * @param gameId 游戏编号
     * @return key
     */
    public static String getGameInfoKey(int gameId) {
        StringBuilder sb = new StringBuilder();
        sb.append(gameInfoKey);
        sb.append(gameId);
        return sb.toString();
    }

    /**
     * 得到玩法信息的缓存key
     *
     * @param gameId 游戏编号
     * @param playId 玩法编号
     * @return key
     */
    public static String getGamePlayInfoKey(int gameId, int playId) {
        StringBuilder sb = new StringBuilder();
        sb.append(gamePlayInfoKey);
        if (gameId != 0) {
            sb.append(gameId);
            sb.append(":play:");
            if (playId != 0) {
                sb.append(playId);
            }
        }
        return sb.toString();
    }

    /**
     * 得到投注符号的缓存key
     *
     * @param gameId 游戏编号
     * @param playId 玩法编号
     * @param signId 符号编号
     * @return key
     */
    public static String getGameSignInfoKey(int gameId, int playId, int signId) {
        StringBuilder sb = new StringBuilder();
        sb.append("sign_info:game:");
        sb.append(gameId);
        sb.append(":play:");
        sb.append(playId);
        sb.append(":sign:");
        if (signId != 0) {
            sb.append(signId);
        }
        return sb.toString();
    }

    /**
     * 得到奖级的缓存key
     *
     * @param gameId 游戏编号
     * @param playId 玩法编号
     * @param classId 奖级编号
     * @return key
     */
    public static String getGameClassInfoKey(int gameId, int playId, int classId) {
        StringBuilder sb = new StringBuilder();
        sb.append(gameClassInfo);
        if (gameId != 0) {
            sb.append(gameId).append(":");
            if (playId != 0) {
                sb.append("play:");
                sb.append(playId);
                sb.append(":");
                if (classId != 0) {
                    sb.append("class:");
                    sb.append(classId);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 得到证件类型的缓存key
     *
     * @param idTypeId 证件类型编号
     * @return key
     */
    public static String getIdTypeKey(int idTypeId) {
        StringBuilder sb = new StringBuilder();
        sb.append(idTypeKey);
        sb.append(idTypeId);
        return sb.toString();
    }

    /**
     * 得到账户充值方式的缓存key
     *
     * @param rechargeType 账户充值方式编号
     * @return key
     */
    public static String getAccountRechargeTypeKey(int rechargeType) {
        StringBuilder sb = new StringBuilder();
        sb.append("account_recharge_type:recharge_type:");
        sb.append(rechargeType);
        return sb.toString();
    }

    /**
     * 得到账户奖金返奖类型的缓存key
     *
     * @param prizeType 账户奖金返奖类型编号
     * @return key
     */
    public static String getPrizeTypeKey(int prizeType) {
        StringBuilder sb = new StringBuilder();
        sb.append("prize_type:prize_type:");
        sb.append(prizeType);
        return sb.toString();
    }

    /**
     * 得到账户扣款类型的缓存key
     *
     * @param payTypeId 账户扣款类型编号
     * @return key
     */
    public static String getPayTypeKey(int payTypeId) {
        StringBuilder sb = new StringBuilder();
        sb.append("pay_type:pay_type_id:");
        sb.append(payTypeId);
        return sb.toString();
    }

    /**
     * 得到账户类型的缓存key
     *
     * @param accountTypeId 账户类型编号
     * @return key
     */
    public static String getAccountTypeKey(int accountTypeId) {
        StringBuilder sb = new StringBuilder();
        sb.append(accountInfoKey);
        sb.append(accountTypeId);
        return sb.toString();
    }

    /**
     * 得到资金交易类型的缓存key
     *
     * @param tradeType 资金交易类型编号
     * @return key
     */
    public static String getTradeTypeKey(int tradeType) {
        StringBuilder sb = new StringBuilder();
        sb.append("trade_type:trade_type:");
        sb.append(tradeType);
        return sb.toString();
    }

    /**
     * 得到资金来源类型的缓存key
     *
     * @param tradeType 资金来源类型编号
     * @return key
     */
    public static String getSourceTypeKey(String tradeType) {
        StringBuilder sb = new StringBuilder();
        sb.append("source_type:source_type:");
        sb.append(tradeType);
        return sb.toString();
    }

    /**
     * 得到银行卡类型的缓存key
     *
     * @param cardType 银行卡类型编号
     * @return key
     */
    public static String getBankcardTypeKey(int cardType) {
        StringBuilder sb = new StringBuilder();
        sb.append("card_type:card_type:");
        sb.append(cardType);
        return sb.toString();
    }

    /**
     * 得到银行编码的缓存key
     *
     * @param bankId 银行编码表编号
     * @return key
     */
    public static String getBankCodeKey(String bankId) {
        StringBuilder sb = new StringBuilder();
        sb.append(bankCodeKey);
        sb.append(bankId);
        return sb.toString();
    }

    /**
     * 得到代销商类型的缓存key
     *
     * @param dealerType 代销商类型编号
     * @return key
     */
    public static String getDealerTypeKey(int dealerType) {
        StringBuilder sb = new StringBuilder();
        sb.append(dealerTypeKey);
        sb.append(dealerType);
        return sb.toString();
    }

    /**
     * 得到开奖次数的缓存key
     *
     * @param gameId 游戏编号
     * @param openId 开奖次数
     * @return key
     */
    public static String getGameMultiOpenKey(int gameId, int openId) {
        StringBuilder sb = new StringBuilder();
        sb.append(gameMultiOpenKey);
        if (gameId != 0) {
            sb.append(gameId).append(":");
            if (openId != 0) {
                sb.append("multiplt_open:").append(openId);
            }
        }
        return sb.toString();
    }

    /**
     * 得到玩法投注方式配置的缓存key
     *
     * @param gameId 游戏编号
     * @param play_id 玩法编号
     * @param bet_mode 投注方式
     * @return key
     */
    public static String getPlayBetModeKey(int gameId, int play_id, int bet_mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("play_bet_mode:game:");
        sb.append(gameId);
        sb.append(":play:");
        sb.append(play_id);
        sb.append(":betmode:");
        if (bet_mode != 0) {
            sb.append(bet_mode);
        }
        return sb.toString();
    }

    /**
     * 得到投注终端的缓存key
     *
     * @param terminalId 投注终端编号
     * @return key
     */
    public static String getTmnInfoKey(int terminalId) {
        StringBuilder sb = new StringBuilder();
        sb.append(tmnInfoKey);
        sb.append(terminalId);
        return sb.toString();
    }

    /**
     * 得到投注终端特权的缓存key
     *
     * @param terminalId
     * @param gameId 不为0才有效
     * @return
     */
    public static String getTmnPrivilegeKey(int terminalId, int gameId) {
        StringBuilder sb = new StringBuilder();
        sb.append(tmnPrivilegeKey);
        if (terminalId != 0) {
            sb.append(terminalId);
            sb.append(":");
            if (gameId != 0) {
                sb.append("game:");
                sb.append(gameId);
            }
        }
        return sb.toString();
    }

    /**
     * 得到代销商信息的缓存key
     *
     * @param dealerId 代销商编号
     * @return key
     */
    public static String getDealerInfoKey(String dealerId) {
        StringBuilder sb = new StringBuilder();
        sb.append(dealerInfoKey);
        sb.append(dealerId);
        return sb.toString();
    }

    /**
     * 得到代销商游戏特权的缓存key
     *
     * @param dealerId 代销商游戏特权编号
     * @param gameid
     * @return key
     */
    public static String getDealerPrivilegeKey(String dealerId, int gameid) {
        StringBuilder sb = new StringBuilder();
        sb.append(dealerPrivilege);
        sb.append(dealerId);
        sb.append("game:");
        sb.append(gameid);
        return sb.toString();
    }

    /**
     * 得到内容管理的缓存key
     *
     * @param cmsId 内容管理编号
     * @return key
     */
    public static String getCmsInfoKey(int cmsId) {
        StringBuilder sb = new StringBuilder();
        sb.append(cmsInfoKey);
        sb.append(cmsId);
        return sb.toString();
    }

    /**
     * 得到内容发布权限的缓存key
     *
     * @param cmsId 内容编号
     * @return key
     */
    public static String getCmsPrivilegeKey(int cmsId) {
        StringBuilder sb = new StringBuilder();
        sb.append("cms_privilege:cms_id:");
        sb.append(cmsId);
        return sb.toString();
    }

    /**
     * 得到部门信息的缓存key
     *
     * @param departmentId
     * @return
     */
    public static String getDePartMentKey(int departmentId) {
        StringBuilder sb = new StringBuilder();
        sb.append(departmentKey);
        sb.append(departmentId);
        return sb.toString();
    }

    /**
     * 得到角色信息的缓存key
     *
     * @param roleId
     * @return
     */
    public static String getUserRoleKey(int roleId) {
        StringBuilder sb = new StringBuilder();
        sb.append(userroleKey);
        sb.append(roleId);
        return sb.toString();
    }

    /**
     * 得到通讯参数的缓存key
     *
     * @param roleId
     * @return
     */
    public static String getCommParaKey(int roleId) {
        StringBuilder sb = new StringBuilder();
        sb.append(commParaKey);
        sb.append(roleId);
        return sb.toString();
    }

    /**
     * 得到地址表的缓存key
     *
     * @param provinceId
     * @param appId
     * @return
     */
    public static String getAddressListKey(int provinceId, int appId) {
        StringBuilder sb = new StringBuilder();
        sb.append(addressKey);
        sb.append(provinceId);
        sb.append(":app_id:");
        sb.append(appId);
        return sb.toString();
    }

    /**
     * 得到开奖公告的缓存key
     *
     * @param gameId
     * @param drawId
     * @param kdrawId
     * @return
     */
    public static String getOpenPrizeKey(int gameId, int drawId, int kdrawId) {
        StringBuilder sb = new StringBuilder();
        sb.append(openPrizeKey);
        sb.append(gameId);
        sb.append(":draw:");
        sb.append(drawId);
        sb.append(":kdraw:");
        sb.append(kdrawId);
        return sb.toString();
    }

    /**
     * 生成游戏投注方式缓存key
     *
     * @param betMode 投注方式
     * @return
     */
    public static String genGameBetMode(int betMode) {
        StringBuilder sb = new StringBuilder();
        sb.append("betmode:");
        sb.append(betMode);
        return sb.toString();
    }

    /**
     * 根据站点等级编号获取站点等级对象
     *
     * @param stationRank
     * @return
     */
    public static String getAgentRateRankKey(int stationRank) {
        StringBuilder sb = new StringBuilder();
        sb.append(agentRateRankKey);
        sb.append(stationRank);
        return sb.toString();
    }

    //-----------------------------------增加电话投注代码-------------------------------------
    public static final String gameBetModeKey = "game_bet_mode:";

    /**
     * 游戏玩法信息前缀
     */
    public static final String gamePlayInfoGamblerKey = "play_info:gamb:game:";

    /**
     * 生成游戏投注方式无纸化缓存key
     *
     * @param betMode 投注方式
     * @return
     */
    public static String genGameBetModeGamb(int betMode) {
        StringBuilder sb = new StringBuilder();
        sb.append(gameBetModeKey);
        sb.append("gamb:betmode:");
        sb.append(betMode);
        return sb.toString();
    }

    /**
     * 得到玩法信息的缓存key
     *
     * @param gameId 游戏编号
     * @param playId 玩法编号
     * @return key
     */
    public static String getGamePlayInfoGamblerKey(int gameId, int playId) {
        StringBuilder sb = new StringBuilder();
        sb.append(gamePlayInfoGamblerKey);
        if (gameId != 0) {
            sb.append(gameId);
            sb.append(":play:");
            if (playId != 0) {
                sb.append(playId);
            }
        }
        return sb.toString();
    }

    /**
     * 得到玩法投注方式配置的缓存key
     *
     * @param gameId 游戏编号
     * @param play_id 玩法编号
     * @param bet_mode 投注方式
     * @return key
     */
    public static String getPlayBetModeGamblerKey(int gameId, int play_id, int bet_mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("play_bet_mode:gamb:game:");
        sb.append(gameId);
        sb.append(":play:");
        sb.append(play_id);
        sb.append(":betmode:");
        if (bet_mode != 0) {
            sb.append(bet_mode);
        }
        return sb.toString();
    }

    /**
     * 获取运营商公钥名称
     *
     * @param dealerid
     * @return
     */
    public static String getGamblerKeyName(String dealerid) {
        StringBuilder sb = new StringBuilder();
        sb.append("dealerkey:");
        sb.append(dealerid);
        return sb.toString();
    }

    /**
     * 获取公司私钥
     *
     * @return
     */
    public static String getPrivateKeyName() {
        StringBuilder sb = new StringBuilder();
        sb.append("privatekey");
        return sb.toString();
    }
    
    /**
     * 得到中彩二维码key的缓存Key
     *
     * @param keyName
     * @return Key
     */
    public static String getSystemKeyKey(String keyName) {
        StringBuilder b = new StringBuilder();
        b.append("systemkey:");
        b.append(keyName);
        return b.toString();
    }
}
