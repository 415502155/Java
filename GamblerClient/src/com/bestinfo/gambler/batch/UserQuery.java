/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.gambler.batch;

import com.bestinfo.arithmetic.NewSign;
import com.bestinfo.gambler.all.BetNo;
import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.createBetNumber.Count;
import com.bestinfo.gambler.createBetNumber.SerialNo;
import com.bestinfo.gambler.protocols.ActionID;
import com.bestinfo.protocols.client.users.PUserInfoQueryClient;
import com.bestinfo.protocols.message.AppHeader;
import com.bestinfo.protocols.users.UserQueryReq;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.DecoderException;

/**
 *
 * @author jone
 */
public class UserQuery implements Callable<Object>{
     private static final String KEY_STORE = "PKCS12";//keystore格式
    private Count c = null;   //记录数
    private final Logger logger = Logger.getLogger(UserSum.class);
    private UserQueryReq req = null;   //投注上传报文
    private AppHeader head = null;  //终端
    private int doNum = 1;   //没终端执行次数

    @SuppressWarnings("unused")
    private UserQuery() {
    }

    public UserQuery(UserQueryReq reqBean, AppHeader head, Count c, int doNum) {
        this.req = reqBean;
        this.head = head;
        this.c = c;
        this.doNum = doNum;
    }

    @Override
    public Object call() throws Exception {
        return execUserQuery(req, head, c, doNum);
    }

    /**
     * 投注 组装 发送 解析操作
     *
     * @param req 投注报文数据结构
     * @param counter 成功和失败计数
     * @param tmnBean 终端
     * @param doNum 每个线程执行次数
     * @param kk_draw_id 快开游戏大期号
     * @return
     * @throws DecoderException
     */
    private int execUserQuery(final UserQueryReq tbr, final AppHeader head, final Count c, final int doNum) throws DecoderException {
        try {
            PUserInfoQueryClient betScheme = new PUserInfoQueryClient();
            String requestxml = betScheme.generateXML(head, tbr);
            if (requestxml == null || requestxml.isEmpty()) {
                logger.error("组装xml数据出错！");
                // continue;
            }
            long sTime = System.currentTimeMillis();
            logger.info("sesseionKey is: " + StaticVariable.SESSION);
            byte[] ret_key = NewSign.GetSign(requestxml, StaticVariable.privateKey, StaticVariable.SESSION);
            // byte[] ret_key = NewSign.GetSign(requestxml, "d:/gdclient.p12", "gdtest@2015", "cosmos", sessionKey);
            long eTime = System.currentTimeMillis();
            logger.info("生成签名时间： " + (eTime - sTime) / 1000F);
            head.setBody_sign(ret_key);
            long ssTime = System.currentTimeMillis();
            requestxml = betScheme.generateXML(head, tbr);
            long pTime = System.currentTimeMillis();
            logger.info("组装报文时间： " + (pTime - ssTime) / 1000F);
            if (requestxml == null || requestxml.isEmpty()) {
                logger.error("组装xml数据出错！");
                // continue;
            }
            requestxml = SerialNo.getxml(requestxml, ActionID.GAMBLER_QUERY);
            BetNo bn = new BetNo();
            // bn.send(requestxml);
            if (bn.UserQuerysend(requestxml) != 0) {//发送数据
                c.addfailcount();
                // continue;
            } else {
                c.addCount();
            }
//            }
            return 0;
        } catch (Exception ex) {
            logger.error("ex :", ex);
            return -1;
        }
    }
}
