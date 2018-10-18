package com.bestinfo.gambler.all;


import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.all.CommTool;
import com.bestinfo.gambler.all.HttpUtil;
import com.bestinfo.gambler.createBetNumber.Count;
import com.bestinfo.gambler.protocols.ActionID;
import com.bestinfo.protocols.bet.SynSysTimeReqRes;
import com.bestinfo.protocols.message.APPMessage;
import com.bestinfo.protocols.message.AppHeader;
import com.bestinfo.protocols.xml.client.XmlFactoryClient;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author chenliping
 */
public class SysTime {

    private static final Logger logger = Logger.getLogger(SysTime.class);

      private void getSys(String xml, Count c) {
        long bf = System.currentTimeMillis();
        String responsexml = HttpUtil.httpSend(xml, StaticVariable.SERVERURL, false);
        long af = System.currentTimeMillis();
        int tm = (int)((af - bf) / 1000) % 60;
        if(tm >= 30){
            c.addOutthirty();
            System.out.print(tm+" ");
        }
        if (responsexml == null) {
            c.addfailcount();
            return;
        } else if (responsexml.equals("sendError")) {
            c.addSendFail();
            return;
        }
        APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SYN_TIME).parseXML(responsexml);
        if (null == ap) {
            logger.error("parser xml error");
            return;
        }
        SynSysTimeReqRes tbrr = (SynSysTimeReqRes) ap.getContent();//得到返回内容
        int resultcode = tbrr.getResult().getResultCode();
        if (resultcode != 0) {
            logger.error("systime failed," + "\t" + resultcode + "\t" + tbrr.getResult().getResultDes());
            c.addfailcount();
            return;
        }
        c.addCount();
    }

    public void morethread(AppHeader header, int size, final int eachSize) {
        header.setAction(ActionID.SYN_TIME);
        final String xml = StaticVariable.ACTION + ActionID.SYN_TIME + "&" + XmlFactoryClient.getInstance().getAppClientXF(ActionID.SYN_TIME).generateXML(header, null);
        ExecutorService exec = Executors.newCachedThreadPool();//创造一个管理非固定数量的线程池,线程一旦结束一段时间,则销毁.         
        final Semaphore semp = new Semaphore(size);// n个线程可以同时访问 
        final Count c = new Count();
        long beforeTime = System.currentTimeMillis();
        for (int index = 0; index < size; index++) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        semp.acquire();// 获取许可
                        for (int i = 0; i < eachSize; i++) {
                            getSys(xml, c);
                        }
                        semp.release();
                    } catch (InterruptedException e) {
                        logger.error("", e);
                    }
                }
            };
            exec.execute(run);
        }
        // 退出线程池
        exec.shutdown();
        while (!exec.isTerminated()) {
            try {
                exec.awaitTermination(500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }
        long endTime = System.currentTimeMillis();
        long difference = endTime - beforeTime;
        logger.error("successful:" + c.getCount() + "\tfailed:" + c.getFailcount() + "\tsendfailed:" + c.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference)+"\toutthirty:"+c.getOutthirty());
        System.out.println("successful:" + c.getCount() + "\tfailed:" + c.getFailcount() + "\tsendfailed:" + c.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference)+"\toutthirty:"+c.getOutthirty());
            JOptionPane.showMessageDialog(null, "成功个数: " + c.getCount() + "\t失败个数: " + c.getFailcount() + "\t发送失败个数: " + c.getSendFail() + "\t总共时间 :" + CommTool.getTime(difference), "操作", JOptionPane.INFORMATION_MESSAGE);

    }

    public static void main(String[] args) throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><pkg><pkgH><type>3</type><action>200</action><version>0</version><dealer_id>140</dealer_id><terminal_id></terminal_id><mobile>13552287032</mobile>"
                + "<phone>010-65663215</phone><sent_time>2014-01-09 15:29:53</sent_time></pkgH>"
                + "<pkgC><return resultCode=\"0\" resultDes=\"操作成功\"/><sys_time>2014-01-09 15:29:53</sys_time></pkgC></pkg>";

        APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SYN_TIME).parseXML(xml);
        SynSysTimeReqRes tbrr = (SynSysTimeReqRes) ap.getContent();//得到返回内容
        int resultcode = tbrr.getResult().getResultCode();
        if (resultcode != 0) {
            logger.error("systime failed," + "\t" + resultcode + "\t" + tbrr.getResult().getResultDes());
        }
    }
}
