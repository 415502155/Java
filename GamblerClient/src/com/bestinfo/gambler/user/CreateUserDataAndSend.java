package com.bestinfo.gambler.user;

import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.all.CommTool;
import com.bestinfo.gambler.all.HttpUtil;
import com.bestinfo.gambler.all.RhClientProperties;
import com.bestinfo.gambler.createBetNumber.Count;
import com.bestinfo.gambler.createBetNumber.SerialNo;
import com.bestinfo.gambler.bet.UserSelect;
import com.bestinfo.gambler.protocols.ActionID;
import com.bestinfo.protocols.message.APPMessage;
import com.bestinfo.protocols.message.AppHeader;
import com.bestinfo.protocols.users.GamblerRegistReq;
import com.bestinfo.protocols.users.GamblerRegistReqRes;
import com.bestinfo.protocols.xml.client.XmlFactoryClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

/**
 *
 * @author bestinfo-hq
 */
public class CreateUserDataAndSend {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CreateUserDataAndSend.class);

    /**
     * 产生六位字符串
     *
     * @return
     */
    private String getStringUser() {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    private void createother(AppHeader head, GamblerRegistReq grreq, boolean testif, ArrayList<String> ait) {
        if (head.getType() == SerialNo.SENDTYPETHREE) {//无物理终端机
//            String phone = String.format("%1$tM%1$tS%1$tL", new Date());
            String phone = Long.toString(System.nanoTime()).substring(6, 13);
            int a[] = CommTool.getRandInt(0, 9, 3);
            phone = "1" + phone + Integer.toString(a[0]) + Integer.toString(a[1]) + Integer.toString(a[2]);
            logger.info(phone);
            grreq.setMobilePhone(phone);
            int b[] = CommTool.getRandInt(0, 22, 1);//随机地市
            grreq.setCityId(b[0]);
        } else if (head.getType() == SerialNo.SENDTYPEFOUR) {
            int b[] = CommTool.getRandInt(0, ait.size() - 1, 1);
            head.setTerminal_id(Integer.parseInt(ait.get(b[0])));
            if (!testif) {//非测试，有物理终端的用户，扣款类型一定是后期结算
                grreq.setChargeTypeId(SerialNo.DEDUCETYPEFIVE);
            }
            grreq.setIsBindCard(1);
            grreq.setBankId("12");//银行编码
            int a[] = CommTool.getRandInt(0, 9, 4);
            String bankno = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date());
            bankno = Integer.toString(a[0]) + Integer.toString(a[1]) + Integer.toString(a[2]) + Integer.toString(a[3]) + bankno;
            grreq.setAccountCard(bankno);//银行卡号
        }
    }

    /**
     * 生成指定用户个数的注册xml报文
     *
     * @param head
     * @param grreq
     * @param key
     * @param userno 用户个数
     * @param testif 是否为注册
     * @return
     * @throws Exception
     */
    private ArrayList<String> getUserRegisterXml(AppHeader head, GamblerRegistReq grreq, String key, int userno, boolean testif, int NO) {
        int actionid;
        ArrayList<String> ait = null;
//        if (head.getType() == SerialNo.SENDTYPETWO) {
//            actionid = ActionID.GAMBLER_REGISTER2;
//        } else {
        actionid = ActionID.GAMBLER_REGISTER;
//        if (head.getType() == SerialNo.SENDTYPEFOUR) {
        //ait = UserSelect.checkandgetTmnPhy(head.getDealer_id());
//            if (ait == null) {
//                return null;
//            }
//        }
//        }
        head.setAction(actionid);
        ArrayList<String> xml = new ArrayList<String>();
        for (int i = 0; i < userno; i++) {
            // createother(head, grreq, testif, ait);
            //动态产生用户名
            String username = key + NO + getStringUser() + i;
//            logger.info(username);
            String time = new Date().getTime() + "";
            grreq.setMobilePhone("1" + time.substring(3));
            String str = grreq.getMobilePhone();
            StringBuilder sb = new StringBuilder(str);
            sb.setCharAt(sb.length() - 1, String.valueOf(ThreadLocalRandom.current().nextInt(9)).charAt(0));
            sb.setCharAt(sb.length() - 2, String.valueOf(ThreadLocalRandom.current().nextInt(9)).charAt(0));
            sb.setCharAt(sb.length() - 3, String.valueOf(ThreadLocalRandom.current().nextInt(9)).charAt(0));
            sb.setCharAt(sb.length() - 4, String.valueOf(ThreadLocalRandom.current().nextInt(9)).charAt(0));
            sb.setCharAt(sb.length() - 5, String.valueOf(ThreadLocalRandom.current().nextInt(9)).charAt(0));
            sb.setCharAt(sb.length() - 6, String.valueOf(ThreadLocalRandom.current().nextInt(9)).charAt(0));
            grreq.setMobilePhone(sb.toString());

            System.out.println("动态产生用户名" + username + grreq.getMobilePhone());
            grreq.setGamblerName(username);
            //生成xml数据
            String grxml = XmlFactoryClient.getInstance().getAppClientXF(actionid).generateXML(head, grreq);
            if (grxml == null) {
                logger.error("组装xml数据出错！");
                continue;
            }
            grxml = SerialNo.getxml(grxml, actionid);
            xml.add(grxml);
        }
        return xml;
    }

    /**
     * 获取用户xml数据，写入文件
     *
     * @param head
     * @param key 生成用户名所用关键字
     * @param userno 用户个数
     * @param eachfilenum 单个文件存放用户数
     */
    public void CreateUserXml(AppHeader head, GamblerRegistReq grreq, String key, int userno, int eachfilenum) throws Exception {
        ArrayList<String> xml = getUserRegisterXml(head, grreq, key, userno, false, 1);
        ArrayList<String> xmltemp = new ArrayList<String>();
        int xmllen = xml.size();
        String filename = null;

        int countfile = Integer.parseInt(RhClientProperties.getpro().getProperty("userfilenum"));
        for (int i = 0; i < xmllen; i++) {
            if (i % eachfilenum == 0) {
                if (!xmltemp.isEmpty()) {
                    countfile += 1;
                    filename = StaticVariable.USERDIRECTORY + countfile + ".txt";
//                    RhClientProperties.setpro(filename, "false");
                    CommTool.WriteStringTofile(xmltemp, filename);
                    xmltemp.clear();
                }
            }
            xmltemp.add(xml.get(i));
        }
        countfile += 1;
        filename = StaticVariable.USERDIRECTORY + countfile + ".txt";
//        RhClientProperties.setpro(filename, "false");
        CommTool.WriteStringTofile(xmltemp, filename);

//        RhClientProperties.setpro("userfilenum", Integer.toString(countfile));
    }

    /**
     * 发送批量用户数据
     */
//    private int send(List<String> allxmlUser) throws Exception {
//        for (String string : allxmlUser) {
//            String responsexml = HttpUtil.httpSend(string);
//            if (responsexml != null) {
//                if (responsexml.trim().equals("null")) {
//                    System.out.println("response is null");
//                    return -2;
//                }
//                APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.GAMBLER_REGISTER).parseXML(responsexml);
//                System.out.println("" + ap.getHeader().getDealer_id());
//                GamblerRegistReqRes tbrr = (GamblerRegistReqRes) ap.getContent();//得到返回内容
//                int resultcode = tbrr.getAppResResult().getResultCode();
//                System.out.println("" + resultcode);
//                if (resultcode == 0) {
//                    System.out.println("返回解析得到用户名：" + tbrr.getGamblerName());
////                    将用户入库(先解析)(测试)
//                    APPMessage apreq = XmlFactoryClient.getInstance().getAppClientXF(ActionID.GAMBLER_REGISTER).parseXML(string);
//                    GamblerRegistReq grreq = (GamblerRegistReq) apreq.getContent();
//                    insertUser(grreq);
//                } else {
//                    //注册失败，写入文件，未做
//                    System.out.println("" + tbrr.getAppResResult().getResultDes());
//                    continue;
//                }
//            } else {
//                //                    将用户入库(先解析)(测试)
//                APPMessage apreq = XmlFactoryClient.getInstance().getAppClientXF(ActionID.GAMBLER_REGISTER).parseXML(string);
//                GamblerRegistReq grreq = (GamblerRegistReq) apreq.getContent();
//                insertUser(grreq);
//                System.out.println("response is null");
//                continue;
//            }
//        }
//        return 0;
//    }
    /**
     * 发送一个用户注册请求报文，并解析回复报文
     *
     * @param user
     * @return
     * @throws Exception
     */
    private int send(String user) throws Exception {
        String responsexml = HttpUtil.httpSend(user, StaticVariable.SERVERURL, false);
        if (responsexml == null) {
            return -2;
        } else if (responsexml.equals("sendError")) {
            return -5;
        }
        APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.GAMBLER_REGISTER).parseXML(responsexml);
        if (null == ap) {
            logger.error("parse error");
            return -1;
        }
        GamblerRegistReqRes tbrr = (GamblerRegistReqRes) ap.getContent();
        int resultcode = tbrr.getAppResResult().getResultCode();
        if (resultcode != 0) {
            logger.error("register failed:" + resultcode + "\t" + tbrr.getAppResResult().getResultDes() + "\t" + user);
            return -1;
        }
        //JOptionPane.showMessageDialog(null,"返回码:"+resultcode+"结果:"+tbrr.getAppResResult().getResultDes(), "操作", JOptionPane.INFORMATION_MESSAGE);
        return 0;
    }

    /**
     * 校验是否有未注册过的用户,主要通过查看配置文件中的文件的状态
     *
     * @param uerfilenum 文件个数
     * @throws Exception
     */
//    private List<String> checkuserinfo(int uerfilenum) throws Exception {
//        List<String> fileList = new ArrayList<String>();
//        for (int i = 1; i <= uerfilenum; i++) {
//            String filename = StaticVariable.USERDIRECTORY + i + ".txt";
//            String fileresult = RhClientProperties.getpro().getProperty(filename);
//            if (fileresult.equals("false")) { // 文件没有注册过
//                fileList.add(filename);
//            }
//        }
//        return fileList;
//    }
    /**
     * 串行发送多包
     *
     * @param allxmlUser
     * @throws Exception
     */
    private void lineSendXml(List<String> allxmlUser) throws Exception {
        int successcount = 0;
        int failedcount = 0;
        long beforeTime = System.currentTimeMillis();
        for (String string : allxmlUser) {
            if (send(string) == 0) {//发送数据包，正确后
                successcount++;
            } else {
                failedcount++;
            }
        }
        long endTime = System.currentTimeMillis();
        long difference = endTime - beforeTime;
        logger.error("successful:" + successcount + "\tfailed:" + failedcount + "\ttotalTime:" + CommTool.getTime(difference));
        System.out.println("successful:" + successcount + "\tfailed:" + failedcount + "\ttotalTime:" + CommTool.getTime(difference));
        JOptionPane.showMessageDialog(null, "successful:" + successcount + "\tfailed:" + failedcount + "\ttotalTime:" + CommTool.getTime(difference), "操作", JOptionPane.INFORMATION_MESSAGE);

    }

    private GamblerRegistReq changeOb(GamblerRegistReq grreq) {
        GamblerRegistReq grreq1 = new GamblerRegistReq();
        grreq1.setEmail(grreq.getEmail());
        grreq1.setCityId(grreq.getCityId());
        grreq1.setIdTypeId(grreq.getIdTypeId());//证件类型
        grreq1.setIdNo(grreq.getIdNo());//证件编号
        grreq1.setAccountTypeId(grreq.getAccountTypeId());//账户类型
        grreq1.setChargeTypeId(grreq.getChargeTypeId());//扣款类型
        grreq1.setIsBindCard(grreq.getIsBindCard());
        grreq1.setCardType(grreq.getCardType());//银行卡类型
        grreq1.setBankId(grreq.getBankId());//银行编码
        grreq1.setAccountCard(grreq.getAccountCard());//银行卡号
        grreq1.setPrizeTypeId(grreq.getPrizeTypeId());
        grreq1.setRealName(grreq.getRealName());
        grreq1.setSex(grreq.getSex());
        grreq1.setBirthday(grreq.getBirthday());
        grreq1.setMobilePhone(grreq.getMobilePhone());
        grreq1.setPhoneNo(grreq.getPhoneNo());
        grreq1.setIsBandDealer(grreq.getIsBandDealer());//是否绑定运营商
        grreq1.setDealerId(grreq.getDealerId());//绑定运营商编号
        grreq1.setGamblerPwd(grreq.getGamblerPwd());
        return grreq1;
    }

    /**
     * 多线程注册
     *
     * @param head
     * @param grreq
     * @param key
     * @param userno 线程数
     * @param eachno 每线程个数
     * @param testif
     * @throws Exception
     */
    public void morethread(final AppHeader head, final GamblerRegistReq grreq, final String key, int userno, final int eachno, final boolean testif) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();//创造一个管理非固定数量的线程池,线程一旦结束一段时间,则销毁.         
        final Semaphore semp = new Semaphore(userno);// n个线程可以同时访问    
        final Count c = new Count();
        long beforeTime = System.currentTimeMillis();
        for (int index = 0; index < userno; index++) {
            final int NO = index;
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        semp.acquire();// 获取许可
                        GamblerRegistReq grreq1 = changeOb(grreq);
                        ArrayList<String> xml = getUserRegisterXml(head, grreq1, key, eachno, testif, NO);
                        for (int i = 0; i < eachno; i++) {
                            if (send(xml.get(i)) == 0) {
                                c.addCount();
                            } else {
                                c.addfailcount();
                            }
                        }
                        semp.release();
                    } catch (Exception e) {
                        logger.error(e);
                        return;
                    }
                }
            };
            exec.execute(run);
        }
        // 退出线程池
        exec.shutdown();
        while (!exec.isTerminated()) {
            exec.awaitTermination(500, TimeUnit.MILLISECONDS);
        }
        long endTime = System.currentTimeMillis();
        long difference = endTime - beforeTime;
        logger.error("successful:" + c.getCount() + "\tfailed:" + c.getFailcount() + "\tsendfailed:" + c.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference));
        System.out.println("successful:" + c.getCount() + "\tfailed:" + c.getFailcount() + "\tsendfailed:" + c.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference));
        JOptionPane.showMessageDialog(null, "successful:" + c.getCount() + "\tfailed:" + c.getFailcount() + "\tsendfailed:" + c.getSendFail() + "\ttotalTime:" + CommTool.getTime(difference), "操作", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * 对所有未注册过的用户进行注册，发送xml报文
     *
     * @param moreflag 是否启用多线程
     * @throws Exception
     */
//    public void SendXmlUserInfo(boolean moreflag) throws Exception {
////        int uerfilenum = Integer.parseInt(RhClientProperties.getpro().getProperty("userfilenum"));
////        List<String> fileList = checkuserinfo(uerfilenum);
////        if (fileList.isEmpty()) {
////            return;
////        }
//        ArrayList<String> fileList = new ArrayList<String>();
//        CommTool.refreshFileList(StaticVariable.USERDIRECTORY, fileList);
//        if (fileList.isEmpty()) {
//            return;
//        }
//        List<String> allxmlUser = new ArrayList<String>();//存放所有用户的xml数据
//        for (String filename : fileList) {
//            List<String> filecontent = CommTool.getIndicateLines(filename, 1, Integer.MAX_VALUE);//读取文件            
//            allxmlUser.addAll(filecontent);
//        }
//        if (allxmlUser.isEmpty()) {
//            logger.error("封装xml数据为空！");
//            return;
//        }
//
//        if (moreflag) {
//            //多线程
//            morethread(allxmlUser);
//        } else {//串行
//            lineSendXml(allxmlUser);
//        }
//        //更改文件状态
////        for (int i = 1; i <= uerfilenum; i++) {
////            String filename = StaticVariable.USERDIRECTORY + i + ".txt";
////            RhClientProperties.setpro(filename, "true");
////        }
//    }
    /**
     * 对指定文件中的用户进行注册，发送xml报文
     *
     * @param filename 文件名的号
     * @param moreflag 是否启用多线程
     * @throws Exception
     */
//    public void SendXmlUserInfo(String filename, boolean moreflag) throws Exception {
//        filename = StaticVariable.USERDIRECTORY + filename + ".txt";
//        List<String> filecontent = null;
////        String fileresult = RhClientProperties.getpro().getProperty(filename);//从配置文件中读取该文件状态
////        if (fileresult == null) {
//////            System.out.println("文件名错误！");
////            logger.error("文件名错误！");
////            return;
////        }
////        if (fileresult.equals("false")) { // 文件没有注册过
//        filecontent = CommTool.getIndicateLines(filename, 1, Integer.MAX_VALUE);//读取文件内的xml数据
////        } else {
//////            System.out.println("用户已经注册过！");
////            logger.error("用户已经注册过！");
////            return;
////        }
//        if (filecontent == null) {
//            logger.error("获取用户xml失败！");
////            System.out.println("获取用户xml失败！");
//            return;
//        }
//
//        //多线程
//        if (moreflag) {
//            morethread(filecontent);
////            RhClientProperties.setpro(filename, "true");
//        } else {
//            lineSendXml(filecontent);
////            RhClientProperties.setpro(filename, "true");
//        }
//    }
    /**
     * 获取现生成xml，发送xml数据
     *
     * @param head 头部
     * @param grreq 用户对象
     * @param key 关键字
     * @param userno 用户个数
     * @param moreflag true 使用多线程
     * @param testif 是否是测试
     */
    public void sendUserXml(AppHeader head, GamblerRegistReq grreq, String key, int userno, boolean moreflag, boolean testif, int eachno) {
        try {
            if (moreflag) { //多线程               
                morethread(head, grreq, key, userno, eachno, testif);
            } else {//串行
                ArrayList<String> xml = getUserRegisterXml(head, grreq, key, userno, testif, 1);
                lineSendXml(xml);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * 对指定的用户进行注册，发送xml报文
     *
     * @param head
     * @param grreq
     * @param testif
     * @throws Exception
     */
    public void SendXmlUserInfo(AppHeader head, GamblerRegistReq grreq, boolean testif) throws Exception {
        ArrayList<String> ait = null;
        int actionid;
//        if (head.getType() == SerialNo.SENDTYPETWO) {
//            actionid = ActionID.GAMBLER_REGISTER2;
//        } else {
        actionid = ActionID.GAMBLER_REGISTER;
//        if (head.getType() == SerialNo.SENDTYPEFOUR) {
//            ait = UserSelect.checkandgetTmnPhy(head.getDealer_id());
//            if (ait == null) {
//                return;
//            }
//        }
//        }
        head.setAction(actionid);
        createother(head, grreq, testif, ait);
        String grxml = XmlFactoryClient.getInstance().getAppClientXF(actionid).generateXML(head, grreq);
        if (grxml == null) {
            logger.error("组装xml，返回为空！");
            return;
        }
        grxml = SerialNo.getxml(grxml, actionid);
        send(grxml);
    }

    public static void main(String[] awe) throws Exception {
    }
}
