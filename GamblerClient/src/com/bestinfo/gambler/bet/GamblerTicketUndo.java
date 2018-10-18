/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.gambler.bet;

import com.bestinfo.arithmetic.NewSign;
import com.bestinfo.gambler.all.HttpUtil;
import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.createBetNumber.SerialNo;
import com.bestinfo.gambler.protocols.ActionID;
import com.bestinfo.protocols.bet.PBetSchemeQueryRes;
import com.bestinfo.protocols.bet.PBetSchemeRequst;
import com.bestinfo.protocols.client.bet.PTicketUndoClient;
import com.bestinfo.protocols.message.APPMessage;
import com.bestinfo.protocols.message.AppHeader;
import com.bestinfo.protocols.xml.client.XmlFactoryClient;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class GamblerTicketUndo {
    private static final Logger logger = Logger.getLogger(GamblerTicketUndo.class);

    public int sendRequest(AppHeader head, PBetSchemeRequst req) {
        String requestXml = createXml(head, req);
        try {
            String responseXml = HttpUtil.httpSend(requestXml, StaticVariable.SERVERURL, false);
            logger.info("这个是responseXml：" + responseXml);
            if (responseXml == null) {
                logger.error("Response xml is null");
                return -1;
            }
            APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.GAMBLER_TICKET_UNDO).parseXML(responseXml);
            if (ap == null) {
                logger.error("AppMessage is null.");
                return -2;
            }
            PBetSchemeQueryRes grres = (PBetSchemeQueryRes) ap.getContent();
            if (grres.getResult().getResultCode() != 0) {
                logger.error(grres.getResult().getResultDes());
                return -3;
            }
            JOptionPane.showMessageDialog(null, "返回码:" + grres.getResult().getResultCode() + "结果:" + grres.getResult().getResultDes(), "操作", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            logger.error("", e);
            return -4;
        }
        return 0;
    }
    private String createXml(AppHeader head, PBetSchemeRequst req){
        String resultXml = null;
        try {
            head.setAction(ActionID.GAMBLER_TICKET_UNDO);
            PTicketUndoClient ticketUndo = new PTicketUndoClient();
            resultXml = ticketUndo.generateXML(head, req);
            if (resultXml == null || resultXml.isEmpty()) {
                logger.error("组装xml数据出错1！");
                return "-1";
            }

            byte[] ret_key = NewSign.GetSign(resultXml, StaticVariable.privateKey, StaticVariable.SESSION);
            head.setBody_sign(ret_key);
            resultXml = ticketUndo.generateXML(head, req);
            if (resultXml == null || resultXml.isEmpty()) {
                logger.error("组装xml数据出错2！");
                return "-1";
            }
            return SerialNo.getxml(resultXml, ActionID.GAMBLER_TICKET_UNDO);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SelfTerminals.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultXml;
    }
}
