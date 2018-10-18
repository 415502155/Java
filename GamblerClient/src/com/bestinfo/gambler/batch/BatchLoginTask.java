package com.bestinfo.gambler.batch;

import com.bestinfo.arithmetic.CaTool;
import com.bestinfo.arithmetic.DataChange;
import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.all.HttpSend;
import com.bestinfo.gambler.cache.Cache;
import com.bestinfo.gambler.cache.CacheManager;
import com.bestinfo.gambler.createBetNumber.Count;
import com.bestinfo.gambler.protocols.ActionID;
import com.bestinfo.protocols.client.users.PRoleLoginClient;
import com.bestinfo.protocols.message.APPMessage;
import com.bestinfo.protocols.message.AppHeader;
import com.bestinfo.protocols.users.UserLoginReq;
import com.bestinfo.protocols.users.UserLoginReqRes;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.awt.Component;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 登陆任务
 */
public class BatchLoginTask implements Runnable {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(BatchLoginTask.class);
    private Count counter = null;
    private AppHeader head = null;
    private byte[] bname = null;
    private byte[] bpwd = null;
    private Component component = null;

    public BatchLoginTask(AppHeader head, byte[] bname, byte[] bpwd, Component component, Count c) {
        this.head = head;
        this.bname = bname;
        this.bpwd = bpwd;
        this.component = component;
        this.counter = c;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        //组装报文内容<pkgC>
        UserLoginReq ur = new UserLoginReq();
        CaTool ct = new CaTool();
        DataChange dc = new DataChange();
        PublicKey pk = ct.getPublicKey("d:/gdtestp12.cer");
        byte[] name_enc = ct.encryptByPublicKey(bname, pk);
        byte[] pwd_enc = ct.encryptByPublicKey(bpwd, pk);
        String sname = dc.bytes2HexString(name_enc);
        String spwd = dc.bytes2HexString(pwd_enc);
        ur.setGamblerName(sname);
        ur.setGamblerPwd(spwd);
        PRoleLoginClient login = new PRoleLoginClient();
        //把报文转换成XML 
        String userxml = login.generateXML(head, ur);
        String xml = "action=" + ActionID.GAMBLER_LOGIN + "&" + userxml;
        // System.out.println("拼接发送XML:" + xml);
        //发送xml并取回回复XML
        String responsexml = new HttpSend().httpSendThread(StaticVariable.SERVERURL, xml);
        if (responsexml == null || responsexml.isEmpty()) {
            return;
        }
        //解析XML
        APPMessage ap = login.parseXML(responsexml);
        //回复报文
        UserLoginReqRes tbrr = (UserLoginReqRes) ap.getContent();//得到返回内容
        int resultcode = tbrr.getAppResResult().getResultCode();
        // System.out.println("KEY:" + tbrr.getSession_key());
        Cache cache = new Cache();
        int success = 0;
        int faile = 0;
        //输出结果
        if (resultcode == 0) {
            counter.addCount();
            logger.info("成功：" + success);
            byte[] b_s_key = Base64.decode(tbrr.getSession_key());//dc.hexString2Bytes(tbrr.getSession_key()) ;
            PrivateKey prk = ct.getPrivateKey("D:\\gdclient.p12", "gdtest@2015", "cosmos", "gdtest@2015");
            byte[] pwd_dec = ct.decryptByPrivateKey(b_s_key, prk);
            //  logger.info("返回sessionKey: "+Base64.encode(pwd_dec));
            cache.setValue(Base64.encode(pwd_dec));

            int dealerid = Integer.parseInt(head.getDealer_id());
            if (dealerid == 145 || dealerid == 146 || dealerid == 149) {
                String terminal_id = String.valueOf(head.getTerminal_id());//请求的终端机号
                CacheManager.putCache(terminal_id, cache);//将响应信息 以一个终端机号对应一个sessionkey的形式缓存
                //  System.out.println(CacheManager.getCacheInfo(terminal_id).getValue()) ;
            } else {
                CacheManager.putCache("sessionKey", cache);//将响应信息 以一个终端机号对应一个sessionkey的形式缓存
                //  System.out.println(CacheManager.getCacheInfo("sessionKey").getValue()) ;
            }
            System.out.println("操作成功" + resultcode + "\t" + tbrr.getAppResResult().getResultDes());
        } else {
            counter.addfailcount();
            logger.info("失败： " + faile);
            System.out.println(resultcode + "\t" + tbrr.getAppResResult().getResultDes());
        }
        // JOptionPane.showMessageDialog(component, "返回码:" + resultcode + "结果:" + tbrr.getAppResResult().getResultDes(), "操作", JOptionPane.INFORMATION_MESSAGE);
//    long endTime=System.currentTimeMillis();
//            logger.info("run共用时间:"+(endTime-startTime)/1000F);
    }

}
