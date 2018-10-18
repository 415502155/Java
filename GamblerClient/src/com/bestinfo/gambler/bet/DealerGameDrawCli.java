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
import com.bestinfo.protocols.client.dealer.PDealerGameDrawQueryClient;
import com.bestinfo.protocols.dealer.DealerGameDrawQueryReq;
import com.bestinfo.protocols.dealer.DealerGameDrawQueryRes;
import com.bestinfo.protocols.message.APPMessage;
import com.bestinfo.protocols.message.AppHeader;
import com.bestinfo.protocols.xml.client.XmlFactoryClient;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;

/**
 *
 * @author
 */
public class DealerGameDrawCli {

    private static final Logger logger = Logger.getLogger(DealerGameDrawCli.class);

    public int sendRequest(AppHeader head, DealerGameDrawQueryReq req, JTextArea a) {
        String requestXml = createXml(head, req);
        try {
            String responseXml = HttpUtil.httpSend(requestXml, StaticVariable.SERVERURL, false);
            logger.info("这个是responseXml：" + responseXml);
            if (responseXml == null) {
                logger.error("Response xml is null");
                return -1;
            }
            APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.DEALER_GAMEDRAW_QUERY).parseXML(responseXml);
            if (ap == null) {
                logger.error("AppMessage is null.");
                return -2;
            }
            DealerGameDrawQueryRes grres = (DealerGameDrawQueryRes) ap.getContent();
            if (grres.getResult().getResultCode() != 0) {
                logger.error(grres.getResult().getResultDes());
                return -3;
            }
            JOptionPane.showMessageDialog(null, "返回码:" + grres.getResult().getResultCode() + "结果:" + grres.getResult().getResultDes(), "操作", JOptionPane.INFORMATION_MESSAGE);
            a.setText(responseXml);
        } catch (Exception e) {
            logger.error("", e);
            return -4;
        }
        return 0;
    }

    private String createXml(AppHeader head, DealerGameDrawQueryReq req) {
        String resultXml = null;
        try {
            head.setAction(ActionID.DEALER_GAMEDRAW_QUERY);
            PDealerGameDrawQueryClient gamedrawcli = new PDealerGameDrawQueryClient();
            resultXml = gamedrawcli.generateXML(head, req);
            if (resultXml == null || resultXml.isEmpty()) {
                logger.error("组装xml数据出错1！");
                return "-1";
            }

            byte[] ret_key = NewSign.GetSign(resultXml, StaticVariable.privateKey, StaticVariable.SESSION);
            head.setBody_sign(ret_key);
            resultXml = gamedrawcli.generateXML(head, req);
            if (resultXml == null || resultXml.isEmpty()) {
                logger.error("组装xml数据出错2！");
                return "-1";
            }
            return SerialNo.getxml(resultXml, ActionID.DEALER_GAMEDRAW_QUERY);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SelfTerminals.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultXml;
    }
}
