package com.bestinfo.gambler.batch;

import com.bestinfo.arithmetic.NewSign;
import com.bestinfo.gambler.all.HttpSend;
import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.cache.CacheManager;
import com.bestinfo.protocols.client.users.PUserSumQueryClient;
import com.bestinfo.protocols.message.APPMessage;
import com.bestinfo.protocols.message.AppHeader;
import com.bestinfo.protocols.users.UserSummaryInfoReq;
import com.bestinfo.protocols.users.UserSummaryInfoReqRes;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BatchAccountQueryTask implements Runnable {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(BatchAccountQueryTask.class);
    private AppHeader head = null;
    private UserSummaryInfoReq req = null;

    public BatchAccountQueryTask(AppHeader head, UserSummaryInfoReq req) {
        this.head = head;

        this.req = req;
    }

    @Override
    public void run() {
        logger.info("任务接收到的head ==" + head.toString());
        logger.info("任务接收到的head.terminal_id ==" + head.getTerminal_id());
        //把报文转换成XML
        PUserSumQueryClient recharge = new PUserSumQueryClient();
        String requestxml = recharge.generateXML(head, req);

        int dealerid = Integer.parseInt(head.getDealer_id());
        String sessionKey = null;
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
            Logger.getLogger(BatchAccountQueryTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        head.setBody_sign(ret_key);
        requestxml = recharge.generateXML(head, req);

        //拼接发送XML
        String xml = "action=" + head.getAction() + "&" + requestxml;
        System.out.println("xml is ===============   " + xml);
        //发送xml并取回回复XML
        String responsexml = new HttpSend().httpSendThread(StaticVariable.SERVERURL, xml);
        // String responsexml = HttpUtil.httpSend(xml, StaticVariable.SERVERURL, true);//StaticVariable.SERVERURL服务器地址
        System.out.println("responsexml is ===============   " + responsexml);
        //解析XML
        APPMessage ap = recharge.parseXML(responsexml);
        UserSummaryInfoReqRes tbrr = (UserSummaryInfoReqRes) ap.getContent();//得到返回内容
        int resultcode = tbrr.getAppResResult().getResultCode();
        //输出结果
        if (resultcode == 0) {
            System.out.println("send successfull");
        } else {
            System.out.println(resultcode + "\t" + tbrr.getAppResResult().getResultDes());
        }
        logger.info("返回码:" + resultcode + "结果:" + tbrr.getAppResResult().getResultDes());
    }
}
