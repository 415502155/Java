package com.bestinfo.gambler.batch;

import com.bestinfo.arithmetic.NewSign;
import com.bestinfo.gambler.all.BetNo;
import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.cache.CacheManager;
import com.bestinfo.gambler.createBetNumber.Count;
import com.bestinfo.gambler.createBetNumber.SerialNo;
import com.bestinfo.gambler.protocols.ActionID;
import com.bestinfo.protocols.bet.PBetSchemeRequst;
import com.bestinfo.protocols.client.bet.PBetSchemeClient;
import com.bestinfo.protocols.message.AppHeader;
import java.util.Random;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.DecoderException;

/**
 *
 * @author jone
 */
public class TicketBetCallable implements Callable<Object> {

    private static final String KEY_STORE = "PKCS12";//keystore格式
    private Count c = null;   //记录数
    private final Logger logger = Logger.getLogger(TicketBetCallable.class);
    private PBetSchemeRequst req = null;   //投注上传报文
    private AppHeader head = null;  //终端
    private int doNum = 1;   //没终端执行次数

    @SuppressWarnings("unused")
    private TicketBetCallable() {
    }

    public TicketBetCallable(PBetSchemeRequst reqBean, AppHeader head, Count c, int doNum) {
        this.req = reqBean;
        this.head = head;
        this.c = c;
        this.doNum = doNum;
    }

    @Override
    public Object call() throws Exception {
        return execTicketBet(req, head, c, doNum);
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
    private int execTicketBet(final PBetSchemeRequst tbr, final AppHeader head, final Count c, final int doNum) throws DecoderException {
        try {
            //logger.info("票数是："+doNum);
            for (int j = 0; j < doNum; j++) {
                if (tbr.getGamblerName() == null || tbr.getGamblerName().length() <= 0) {
                    /*int a[] = CommTool.getRandInt(0, 9, 4);
                     String bankno = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date());
                     bankno = Integer.toString(a[0]) + Integer.toString(a[1]) + Integer.toString(a[2]) + Integer.toString(a[3]) + bankno;*/
                    String bankno = head.getDealer_id() + "-" + head.getTerminal_id();
                    tbr.setGamblerName(bankno);//卡号
                }
                //tbr.setGambler_serial_no((new Date().getTime()+"").substring(8,(new Date().getTime()+"").length() ) + Long.toString(startserial)+head.getTerminal_id());
                String time = System.currentTimeMillis() + "";
                String tm = head.getTerminal_id() + "";
                tbr.setGambler_serial_no("" + time.substring(5) + new Random().nextInt(999) + tm.substring(4));
                //logger.info("流水号：" + tbr.getGambler_serial_no());
                // tbr.setGambler_serial_no(String.format("%1$tS%1$tL", new Date()) + Long.toString(startserial));
                // String Resultxml = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET).generateXML(head, tbr);

                PBetSchemeClient betScheme = new PBetSchemeClient();
                String requestxml = betScheme.generateXML(head, tbr);
                if (requestxml == null || requestxml.isEmpty()) {
                    logger.error("组装xml数据出错！");
                    continue;
                }
                String key = String.valueOf(head.getTerminal_id());
                String sessionKey = (String) CacheManager.getCacheInfo(key).getValue();
                if (sessionKey == null) {
                    logger.info("sessionKey is null");
                }
                long sTime = System.currentTimeMillis();
//                Cache d = new Cache();
//                PrivateKey privateKey = null;
//                if (CacheManager.getCacheInfo("privateKey").getValue() == null || CacheManager.getCacheInfo("PrivateKey").getValue().equals("")) {
//                    privateKey = this.getPrivateKey("d:/gdclient.p12", "gdtest@2015", "cosmos","gdtest@2015");
//                    d.setValue(PrivateKey + "");
//                    CacheManager.putCache("privateKey", d);
//                }else{
//                    privateKey=(PrivateKey)CacheManager.getCacheInfo(sessionKey).getValue();
//                }
                byte[] ret_key = NewSign.GetSign(requestxml, StaticVariable.privateKey, sessionKey);
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
                    continue;
                }
                requestxml = SerialNo.getxml(requestxml, ActionID.SCHEMES_BET);
                BetNo bn = new BetNo();
                // bn.send(requestxml);
                if (bn.send(requestxml) != 0) {//发送数据
                    c.addfailcount();
                    continue;
                } else {
                    c.addCount();
                }
            }
            return 0;
        } catch (Exception ex) {
            logger.error("ex :", ex);
            return -1;
        }
    }
}
