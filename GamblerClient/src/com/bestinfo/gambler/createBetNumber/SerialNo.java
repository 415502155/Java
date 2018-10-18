/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.gambler.createBetNumber;

import com.bestinfo.define.system.SendType;
import com.bestinfo.gambler.all.RhClientProperties;
import com.bestinfo.gambler.all.StaticVariable;

import com.bestinfo.gambler.entity.AgentSerialReqRes;
import com.bestinfo.protocols.bet.PBetSchemeRequst;
import com.bestinfo.protocols.message.AppHeader;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author chenliping
 */
public class SerialNo {

    /**
     * * 编码表---扣款类型
     */
//    public static int DEDUCETYPEONE = 1;
//    public static int DEDUCETYPETWO = 2;
//    public static int DEDUCETYPETHREE = 3;
//    public static int DEDUCETYPEFOUR = 4;
    public static int DEDUCETYPEFIVE = 5;

    /**
     * *发送方类型 普通投注机
     */
//    public static int SENDTYPEONE = 1;
    /**
     * *发送方类型 手机客户端
//     */
//    public static int SENDTYPETWO = 2;
    /**
     * *发送方类型 电话投注运营商
     */
    public static int SENDTYPETHREE = 3;
    /**
     * *发送方类型 自助终端运营商
     */
    public static int SENDTYPEFOUR = 4;

    /**
     * 运营商代理充值类型
     */
    public static int chargeA = 12;
    /**
     * 运营商代理提款类型
     */
    public static int chargeB = 2;

    //用户角色定义
    /**
     * * * 管理员
     */
    public static int USERMANAGER = 1;
    /**
     * * * 普通彩民
     */
    public static int USER = 11;

    private static final Logger logger = Logger.getLogger(SerialNo.class);

    /**
     * 申请流水号,组装报文，发送报文，解析报文
     *
     * @param head
     * @param username
     * @param num
     * @return
     * @throws Exception
     */
//    private static AgentSerialReqRes applySerial(AppHeader head, String username, int num) throws Exception {
//        head.setAction(ActionID.SERIALNO_DOWNLOAD);
//        AgentSerialReq asReq = new AgentSerialReq();
//        asReq.setGambler_num(1);
//        List<AgentSerialReq.ApplySerial> las = new ArrayList<AgentSerialReq.ApplySerial>();
//        AgentSerialReq.ApplySerial as = asReq.new ApplySerial();
//        as.setGambler_name(username);
//        as.setNum(num);
//        las.add(as);
//        asReq.setApplySerials(las);
//        AppXmlUtil axu = XmlFactoryClient.getInstance().getAppXmlUtil(head.getAction());
//        String xml = axu.generateXML(head, asReq);
//        String responsexml = HttpUtil.httpSend(xml, StaticVariable.SERVERURL,false);
//        
//        AgentSerialReqRes arr = null;
//        if (responsexml == null) {
//            return null;
//        }
//        APPMessage am = XmlFactoryClient.getInstance().getAppXmlUtil(head.getAction()).parseXML(responsexml);
//        if (am == null) {
//            logger.error("回复流水号解析出错！");
//            return null;
//        }
//        arr = (AgentSerialReqRes) am.getContent();
//        if (arr.getAppResResult().getResultCode() == 0) {
//        } else {
//            logger.error("流水号获取失败！" + arr.getAppResResult().getResultDes());
//            return null;
//        }
//        
//        return arr;
//    }
    /**
     * 自己记流水号，从配置文件中取流水号
     *
     * @param num
     * @param proname 属性名
     * @return
     * @throws Exception
     */
    public static AgentSerialReqRes applySerial(int num, String proname) throws Exception {
        AgentSerialReqRes arr = new AgentSerialReqRes();
        AgentSerialReqRes.ApplySerial as = arr.new ApplySerial();
        String startnum = RhClientProperties.getpro().getProperty(proname);
        if (startnum == null) {
            logger.error("流水号获取失败！");
            return null;
        }
        int inum = Integer.parseInt(startnum);
        int ienum = inum + num;
        String endnum = Integer.toString(ienum - 1);
        RhClientProperties.setpro(proname, Integer.toString(ienum));
        as.setSerial_startNo(startnum);
        as.setSerial_endNo(endnum);
        List<AgentSerialReqRes.ApplySerial> las = new ArrayList<AgentSerialReqRes.ApplySerial>();
        las.add(as);
        arr.setApplySerials(las);
        return arr;
    }

    public static AgentSerialReqRes getSerialNo(AppHeader head, PBetSchemeRequst tbr, int size) {
        AgentSerialReqRes serial = null;
        try {
            serial = applySerial(size, "ticketserial");
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
        return serial;
    }

    /**
     * 获取普通用户角色id
     *
     * @param head
     * @return
     */
    public static int getuserrole(AppHeader head) {
        if (head.getType() == SendType.PHONE) {
            return 11;
        } else if (head.getType() == SendType.HELPUS) {
            return 1;
        } else if (head.getType() == SendType.MOBLICLLI) {
            return 21;
        } else {
            logger.error("发送方类型错误");
            return -1;
        }
    }

    public static String getxml(String xml, int protocol) {
        return StaticVariable.ACTION + protocol + "&" + xml;
    }

}
