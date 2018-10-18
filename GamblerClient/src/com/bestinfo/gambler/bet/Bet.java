package com.bestinfo.gambler.bet;

import com.bestinfo.define.system.SendType;
import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.createBetNumber.Count;
//import com.bestinfo.gambler.batch.BatchBetSend;
import com.bestinfo.gambler.createBetNumber.SerialNo;
import com.bestinfo.gambler.entity.BetNumber;
import com.bestinfo.gambler.protocols.ActionID;
import com.bestinfo.protocols.bet.PBetSchemeRequst;
import com.bestinfo.protocols.message.AppHeader;
import com.bestinfo.protocols.xml.client.XmlFactoryClient;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 * 投注公用方法及入口
 *
 * @author chenliping
 */
public class Bet {

    private static final Logger logger = Logger.getLogger(Telephone.class);

    /**
     * 随机多线程卖票共用
     *
     * @param tbr
     * @return
     */
    public static PBetSchemeRequst copyPBetSchemeRequst(PBetSchemeRequst tbr) {
        PBetSchemeRequst tbrs = new PBetSchemeRequst();
        tbrs.setScheme_type(tbr.getScheme_type());
        tbrs.setScheme_title(tbr.getScheme_title());
        tbrs.setSecrecy_level(tbr.getSecrecy_level());
        tbrs.setCity_id(tbr.getCity_id());
        tbrs.setGame_id(tbr.getGame_id());
        tbrs.setDraw_id(tbr.getDraw_id());
        tbrs.setKeno_draw_id(tbr.getKeno_draw_id());
        tbrs.setPlay_id(tbr.getPlay_id());
        tbrs.setBet_method(tbr.getBet_method());
        tbrs.setBet_mode(tbr.getBet_mode());
        tbrs.setGamblerName(tbr.getGamblerName());
//        tbrs.setIsChasing(tbr.getIsChasing());
//        tbrs.setStopWin(tbr.getStopWin());
//        tbrs.setDrawNum(tbr.getDrawNum());
//        tbrs.setCurrentDraw(tbr.getCurrentDraw());
        tbrs.setCharge_type_id(tbr.getCharge_type_id());
        return tbrs;
    }

    /**
     * 设置投注信息，组装投注报文，随机卖票多线程使用
     *
     * @param head
     * @param tbr
     * @param betNumber
     * @return
     */
    public String getBetXml(AppHeader head, PBetSchemeRequst tbr, BetNumber betNumber) {
        tbr.setBuy_time(new Timestamp(new Date().getTime()));

        tbr.setGame_id(betNumber.getGameid());
        tbr.setPlay_id(betNumber.getPlaytype());
        tbr.setBet_mode(betNumber.getBetmod());
        //游戏倍数
        int multi = 1 + new Random().nextInt(betNumber.getMaxmulti());
        tbr.setBet_multiple(multi);
        //游戏金额
        tbr.setBet_money(BigDecimal.valueOf(betNumber.getMoney() * tbr.getBet_multiple()));
        //总有效号码个数
        tbr.setBet_num(betNumber.getOnlynum());
        //号码组个数
        tbr.setBet_section(betNumber.getStake());
        tbr.setBet_line(betNumber.getNumber());
        head.setAction(ActionID.SCHEMES_BET);
        String Resultxml = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET).generateXML(head, tbr);
        if (Resultxml == null) {
            logger.error("投注组装出错！");
            return null;
        }
//        Resultxml = StaticVariable.ACTION + ActionID.SCHEMES_BET + "&" + Resultxml;
        Resultxml = SerialNo.getxml(Resultxml, ActionID.SCHEMES_BET);
        return Resultxml;
    }

    /**
     * 设置投注信息，组装投注报文，销售中奖票和错误票共用
     *
     * @param head
     * @param tbr
     * @param betNumber
     * @return
     */
    public String getBetXmlLW(AppHeader head, PBetSchemeRequst tbr, BetNumber betNumber) {
        tbr.setBuy_time(new Timestamp(new Date().getTime()));

        tbr.setGame_id(betNumber.getGameid());
        tbr.setPlay_id(betNumber.getPlaytype());
        tbr.setBet_mode(betNumber.getBetmod());
        //游戏倍数
//        int multi = 1 + new Random().nextInt(betNumber.getMaxmulti());
        tbr.setBet_multiple(betNumber.getMaxmulti());
        //游戏金额
        tbr.setBet_money(BigDecimal.valueOf(betNumber.getMoney()));
        //总有效号码个数
        tbr.setBet_num(betNumber.getOnlynum());
        //号码组个数
        tbr.setBet_section(betNumber.getStake());
        tbr.setBet_line(betNumber.getNumber());
        tbr.setScheme_title(betNumber.getTestdesc());
        head.setAction(ActionID.SCHEMES_BET);
        String Resultxml = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET).generateXML(head, tbr);
        if (Resultxml == null) {
            logger.error("投注组装出错！");
            return null;
        }
        Resultxml = StaticVariable.ACTION + ActionID.SCHEMES_BET + "&" + Resultxml;
        return Resultxml;
    }

    /**
     * 销售中奖票
     *
     * @param head
     * @param tbr
     * @param caseid
     * @param serNo
     */
    public void SaleLuckyTicket(AppHeader head, PBetSchemeRequst tbr, int caseid, String serNo) {
        if (head.getType() == SendType.PHONE) {
            new Telephone().SendLuckyXml(head, tbr, caseid, serNo);;
        } else if (head.getType() == SendType.HELPUS) {
            new SelfTerminals().SaleLuckyNO(head, tbr, caseid, serNo);
        } else if (head.getType() == SendType.MOBLICLLI) {
            logger.error("发送方不支持");
        } else {
            logger.error("发送方类型错误");
        }
    }

    /**
     * 销售错误票
     *
     * @param head
     * @param tbr
     * @param serNo
     */
    public void SaleWrongTicket(AppHeader head, PBetSchemeRequst tbr, String serNo) {
        if (head.getType() == SendType.PHONE) {
            new Telephone().SendErrorXml(head, tbr, serNo);;
        } else if (head.getType() == SendType.HELPUS) {
            new SelfTerminals().SendErrorXml(head, tbr, serNo);
        } else if (head.getType() == SendType.MOBLICLLI) {
            logger.error("发送方不支持");
        } else {
            logger.error("发送方类型错误");
        }
    }

    /**
     * 销售随机票
     *
     * @param client_num
     * @param head
     * @param tbr
     * @param eachnum
     * @param controlfilename
     * @param serno
     */
    public void SaleRandTicket(int client_num, AppHeader head, PBetSchemeRequst tbr, int eachnum, String controlfilename, String serno) {
        if (head.getType() == SendType.PHONE) {
            new Telephone().SendRandXml(client_num, head, tbr, eachnum, controlfilename, serno);
        } else if (head.getType() == SendType.HELPUS) {
            new SelfTerminals().SendRandXml(client_num, head, tbr, eachnum, controlfilename, serno);
        } else if (head.getType() == SendType.MOBLICLLI) {
            logger.error("发送方不支持");
        } else {
            logger.error("发送方类型错误");
        }
    }

    /**
     * 销售指定票
     *
     * @param head
     * @param tbr
     * @param ticketnum
     * @param isuser
     */
    public void SaleAppointTicket(AppHeader head, PBetSchemeRequst tbr, int ticketnum, boolean isuser,Count c) {
        if (head.getType() == SendType.PHONE) {
            new Telephone().SendAppointBetXml(head, tbr, ticketnum, isuser,c);
        } else if (head.getType() == SendType.HELPUS) {
            new SelfTerminals().SendAppointBetXml(head, tbr, ticketnum, isuser,c);
        } else if (head.getType() == SendType.MOBLICLLI) {
//            SendBetingXmlPhone(head, tbr, ticketnum);
            logger.error("发送方不支持");
        }
//        else if (head.getType() == 5) {//批量投注140141
//            new BatchBetSend().SendAppointBetXml140(head, tbr, ticketnum, isuser);
//        } else if (head.getType() == 6) {//批量投注145146
//            new BatchBetSend().SendAppointBetXml145(head, tbr, ticketnum, isuser);
//        } 
        else {
            logger.error("发送方类型错误");
        }
    }
    
    /**
     * 销售指定票
     *
     * @param head
     * @param tbr
     * @param ticketnum
     * @param isuser
     */
    public void SaleAppointTicket1(AppHeader head, PBetSchemeRequst tbr, int ticketnum, boolean isuser) {
        if (head.getType() == SendType.PHONE) {
            new Telephone().SendAppointBetXml1(head, tbr, ticketnum, isuser);
        } else if (head.getType() == SendType.HELPUS) {
            new SelfTerminals().SendAppointBetXml1(head, tbr, ticketnum, isuser);
        } else if (head.getType() == SendType.MOBLICLLI) {
//            SendBetingXmlPhone(head, tbr, ticketnum);
            logger.error("发送方不支持");
        }
//        else if (head.getType() == 5) {//批量投注140141
//            new BatchBetSend().SendAppointBetXml140(head, tbr, ticketnum, isuser);
//        } else if (head.getType() == 6) {//批量投注145146
//            new BatchBetSend().SendAppointBetXml145(head, tbr, ticketnum, isuser);
//        } 
        else {
            logger.error("发送方类型错误");
        }
    }

}
