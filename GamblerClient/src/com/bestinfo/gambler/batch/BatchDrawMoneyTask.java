package com.bestinfo.gambler.batch;

import com.bestinfo.arithmetic.NewSign;
import com.bestinfo.gambler.all.HttpSend;
import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.cache.CacheManager;
import com.bestinfo.protocols.agents.AgentAbstractUserReqList;
import com.bestinfo.protocols.agents.AgentAbstractUserResList;
import com.bestinfo.protocols.client.agents.PAgentAbstractUserClient;
import com.bestinfo.protocols.message.APPMessage;
import com.bestinfo.protocols.message.AppHeader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class BatchDrawMoneyTask implements Runnable {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(BatchDrawMoneyTask.class);

    private AppHeader head = null;
    private AgentAbstractUserReqList req = null;

    public BatchDrawMoneyTask(AppHeader head, AgentAbstractUserReqList req) {
        this.head = head;

        this.req = req;
    }

    @Override
    public void run() {
        PAgentAbstractUserClient userAbstract = new PAgentAbstractUserClient();
        String requestxml = userAbstract.generateXML(head, req);
        int dealerid = Integer.parseInt(head.getDealer_id());
        String sessionKey = "";
        if (dealerid == 145 || dealerid == 146) {
            sessionKey = (String) CacheManager.getCacheInfo(String.valueOf(head.getTerminal_id())).getValue();
        } else {
            sessionKey = (String) CacheManager.getCacheInfo("sessionKey").getValue();
        }

        logger.info("sessionKey is: " + sessionKey);
        if (sessionKey == null) {
            logger.info("sessionKey is null");
        }
        byte[] ret_key = null;
        try {
            ret_key = NewSign.GetSign(requestxml, "d:/gdclient.p12", "gdtest@2015", "cosmos", sessionKey);
        } catch (Exception ex) {
            Logger.getLogger(BatchDrawMoneyTask.class.getName()).log(Level.SEVERE, null, ex);
        }

        head.setBody_sign(ret_key);
        requestxml = userAbstract.generateXML(head, req);

        //拼接发送XML
        String xml = "action=" + head.getAction() + "&" + requestxml;
        System.out.println(xml);
        //发送xml并取回回复XML
        String responsexml = new HttpSend().httpSendThread(StaticVariable.SERVERURL, xml);
        // String responsexml = HttpUtil.httpSend(xml, StaticVariable.SERVERURL, true);//StaticVariable.SERVERURL服务器地址
        //解析XML
        APPMessage ap = userAbstract.parseXML(responsexml);

        //APPMessage ap =  XmlFactoryClient.getInstance().getAppClientXF(actionID).parseXML(responsexml);
        //回复报文
        AgentAbstractUserResList tbrr = (AgentAbstractUserResList) ap.getContent();//得到返回内容
        int resultcode = tbrr.getResult().getResultCode();
        //输出结果
        if (resultcode == 0) {
            System.out.println("send successfull");
        } else {
            System.out.println(resultcode + "\t" + tbrr.getResult().getResultDes());
        }
        logger.info("返回码:" + resultcode + "结果:" + tbrr.getResult().getResultDes());
        //JOptionPane.showMessageDialog(this, "返回码:" + resultcode + "结果:" + tbrr.getResult().getResultDes(), "操作", JOptionPane.INFORMATION_MESSAGE);

    }
}
