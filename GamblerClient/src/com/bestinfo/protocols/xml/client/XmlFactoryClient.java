package com.bestinfo.protocols.xml.client;

import com.bestinfo.protocols.client.agents.PAgentAbstractQueryClient;
import com.bestinfo.protocols.client.agents.PAgentAbstractUserClient;
import com.bestinfo.protocols.client.agents.PAgentRechargeQueryClient;
import com.bestinfo.protocols.client.agents.PAgentRechargeUserClient;
import com.bestinfo.protocols.client.agents.PDealerSumAccountQueryClient;
import com.bestinfo.protocols.client.bet.PGameDrawInfoClient;
import com.bestinfo.protocols.client.bet.PGameRuleInfoDownClient;
import com.bestinfo.protocols.client.bet.POpenPrizeQueryClient;
import com.bestinfo.gambler.protocols.ActionID;
import com.bestinfo.protocols.client.bet.PBetSchemeClient;
import com.bestinfo.protocols.client.bet.PBetSchemeQueryClient;
import com.bestinfo.protocols.client.bet.PSysTimeClient;
import com.bestinfo.protocols.client.bet.PTicketUndoClient;
import com.bestinfo.protocols.client.dealer.PDealerGameDrawQueryClient;
import com.bestinfo.protocols.util.AppMessageUtil;
import com.bestinfo.protocols.client.users.PGamblerRegistClient;
import com.bestinfo.protocols.client.users.PRoleLoginClient;
import com.bestinfo.protocols.client.users.PRoleLogoutClient;
import com.bestinfo.protocols.client.users.PUserBindCardClient;
import com.bestinfo.protocols.client.users.PUserCancelClient;
import com.bestinfo.protocols.client.users.PUserInfoQueryClient;
import com.bestinfo.protocols.client.users.PUserModifyInfoClient;
import com.bestinfo.protocols.client.users.PUserModifyPwdClient;
import com.bestinfo.protocols.client.users.PUserRemoveBindCardClient;
import com.bestinfo.protocols.client.users.PUserSumQueryClient;

/**
 * Title:xml解析组装类的工厂类<br>
 * Description:具体实现类<br>
 * Copyright:Copyright(c)　2012<br>
 *
 * @version 1.0
 * @author zhen
 */
public class XmlFactoryClient implements XmlParserFactory {

//    private static XmlFactoryClient xmlParserFactoryImpl;
    static class Single {

        static XmlFactoryClient instance = new XmlFactoryClient();
    }

    public static XmlFactoryClient getInstance() {
//        if (xmlParserFactoryImpl == null) {
//            synchronized (XmlFactoryClient.class) {
//                if (xmlParserFactoryImpl == null) {
//                    System.out.println("----------create----------");
//                    xmlParserFactoryImpl = new XmlFactoryClient();
//                }
//            }
//        }
//        return xmlParserFactoryImpl;
        return Single.instance;
    }

    private XmlFactoryClient() {
    }

    @Override
    public AppMessageUtil getAppClientXF(String className) {
        return null;
    }

    @Override
    public AppMessageUtil getAppClientXF(Class Class) {
        return this.getAppClientXF(Class.getName());
    }

    @Override
    public AppMessageUtil getAppClientXF(int actionId) {
        if (actionId == ActionID.SYN_TIME) {
            return new PSysTimeClient();
        } else if (actionId == ActionID.GAMBLER_REGISTER) {//用户注册
            return new PGamblerRegistClient();
//        } else if (actionId == ActionID.GAMBLER_BINDDEALER) {//用户绑定运营商
//            return new PUserBindDealerClient();
//        } else if (actionId == ActionID.GAMBLER_UNBINDDEALER) {//用户解除绑定运营商
//            return new PUserRemoveBindDealerClient();
//        } else if (actionId == ActionID.GAMBLER_ACTIVITY) {//用户激活
//            return new PUserActivatingClient();
//        } else if (actionId == ActionID.GAMBLER_RECHARGE) {//用户充值
//            return new PUserRechargeClient();
        } else if (actionId == ActionID.GAMBLER_BINDCARD) {//用户绑定卡
            return new PUserBindCardClient();
        } else if (actionId == ActionID.GAMBLER_TICKET_UNDO) {//注销票
            return new PTicketUndoClient();
        } else if (actionId == ActionID.DEALER_GAMEDRAW_QUERY) {
            return new PDealerGameDrawQueryClient();
        }else if (actionId == ActionID.GAMBLER_UNBINDCARD) {//用户解除绑定卡
            return new PUserRemoveBindCardClient();
//        } else if (actionId == ActionID.GAMBLER_ABSTRACT) {//用户提款
//            return new PUserAbstractClient();
        } else if (actionId == ActionID.GAMBLER_QUERY) {//用户信息查询
            return new PUserInfoQueryClient();
        } else if (actionId == ActionID.GAMBLER_UPDATE) {//用户信息修改
            return new PUserModifyInfoClient();
        } else if (actionId == ActionID.GAMBLER_UDPPWD
                || actionId == ActionID.GAMBLER_UDPPWD_PAY) {//用户登录密码/支付密码修改
            return new PUserModifyPwdClient();
        } else if (actionId == ActionID.GAMBLER_LOGIN) {//角色登录
            return new PRoleLoginClient();
        } else if (actionId == ActionID.GAMBLER_LOGOUT) {//角色注销（登出）
            return new PRoleLogoutClient();
//            return null;
//        } else if (actionId == ActionID.GAMBLER_MONEYIN_QUERY) {//彩民充值明细查询
//            return new PGamblerRechargeQueryClient();
//        } else if (actionId == ActionID.GAMBLER_GETMONEY_QUERY) {//彩民提款明细查询
//            return new PGamblerAbstructQueryClient();
//        } else if (actionId == ActionID.GAMBLER_ACCOUNT_QUERY) {//彩民帐户明细查询
//            return new PGamblerAccountQueryClient();
//        } else if (actionId == ActionID.SCHEMES_BET_MORE_QUERY) {//方案投注明细查询
//            return new PBetDetailQueryClient();
        } else if (actionId == ActionID.GAMBLER_CANCEL) {//用户注销
            return new PUserCancelClient();
//        } else if (actionId == ActionID.DEALER_RECHARGE) {//运营商总账户充值
//            return new PAgentRechargeClient();
        } else if (actionId == ActionID.DEALER_AGENTRECHARGE) {//运营商代理充值
            return new PAgentRechargeUserClient();
        } else if (actionId == ActionID.DEALER_AGENTRECHARGE_QUERY) {//代理充值结果查询
            return new PAgentRechargeQueryClient();
//        } else if (actionId == ActionID.DEALER_ABSTRACT) {//运营商提款
//            return new PAgentAbstractClient();
        } else if (actionId == ActionID.DEALER_PROXYABSTRACT) {//运营商代理提款
            return new PAgentAbstractUserClient();
        } else if (actionId == ActionID.DEALER_AGENTABSTRACT_QUERY) {//代理提款结果查询
            return new PAgentAbstractQueryClient();
        } else if (actionId == ActionID.SCHEMES_BET) {//方案投注
            return new PBetSchemeClient();
        } else if (actionId == ActionID.SCHEMES_QUERY) {//方案查询
            return new PBetSchemeQueryClient();
        } else if (actionId == ActionID.ACCOUNT_QUERY) {//用户账户汇总查询
            return new PUserSumQueryClient();
        } else if (actionId == ActionID.GAMERULE_DOWNLOAD) {//游戏规则文件列表查询
            return new PGameRuleInfoDownClient();
            // return new PGameRuleFileClient();
        } else if (actionId == ActionID.DRAW_DOWNLOAD) {//奖期下载
            return new PGameDrawInfoClient();
//        } else if (actionId == ActionID.FILENAME_QUERY) {//下载文件列表查询
//            return new PFileNameQueryClient();
        } else if (actionId == ActionID.OPENPRIZE_DOWNLOAD) {
            return new POpenPrizeQueryClient();
//        } else if (actionId == ActionID.FILE_DOWNLOAD) {
//            return new PFileDownloadClient();
        } else if (actionId == ActionID.DEALERACCOUNT_QUERY) {//运营商汇总信息查询
            return new PDealerSumAccountQueryClient();
//        } else if (actionId == ActionID.PRIZE_KENO_QUERY) {//快开游戏下载中奖记录
//            return new PKenoWinRecordClient();
//        } else if (actionId == ActionID.LUCKYNO_QUERY) {//开奖号码查询
//            return new PLuckyNoQueryClient();
//        } else if (actionId == ActionID.VERSION_INFO_QUERY) {//系统版本信息查询
//            return new PSysVersionQueryClient();
//        } else if (actionId == ActionID.SCHEMES_BET_SINGLE_QUERY) {//方案查询,将期id改成期name
//            return new PBetSchemeSingleQueryClient();
//        } else if (actionId == ActionID.BET_CHASE) {//追号投注
//            return new PBetChase();
//        }else if (actionId == ActionID.BET_CHASE_QUERY) {//追号投注查询
//            return new XmlUtilImplForTBetChaseQuery();
//        }else if (actionId == ActionID.RECHARGE_SYN) {//充值同步
//            return new PChargeSyn();
//        }else if (actionId == ActionID.ABSTRACT_SYN) {//提款同步
//            return new PAbstractMoneySyn();
        } 
        return null;
    }

}
