///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package com.bestinfo.gambler.batch;
//
//import com.bestinfo.arithmetic.NewSign;
//import com.bestinfo.gambler.all.BetNo;
//import com.bestinfo.gambler.all.CommTool;
//import com.bestinfo.gambler.bet.SelfTerminals;
//import com.bestinfo.gambler.cache.CacheManager;
//import com.bestinfo.gambler.createBetNumber.SerialNo;
//import com.bestinfo.gambler.entity.AgentSerialReqRes;
//import com.bestinfo.gambler.protocols.ActionID;
//import com.bestinfo.protocols.bet.PBetSchemeRequst;
//import com.bestinfo.protocols.client.bet.PBetSchemeClient;
//import com.bestinfo.protocols.message.AppHeader;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.logging.Level;
//import javax.swing.JOptionPane;
//import org.apache.http.client.CookieStore;
//import org.apache.http.impl.client.BasicCookieStore;
//import org.apache.log4j.Logger;
//
///**
// * 批量投注时使用 发送投注信息，多线程调用
// * 
// * @author liyongjia
// */
//public class BatchBetSend {
//    
//    private static final Logger logger = Logger.getLogger(SelfTerminals.class);
//    
//    /**
//     * 140 141 运营商
//     * 无自助终端
//     * @param head
//     * @param tbr
//     * @param ticketnum
//     * @param isuser 
//     */
//    public void SendAppointBetXml140(AppHeader head, PBetSchemeRequst tbr, int ticketnum, boolean isuser) {
//        try {
//            
//            ArrayList<String> als = getMuchMoreBetXml140(head, tbr, ticketnum);
//            if (als == null) {
//                return;
//            }
//            int successticketnum = 0;
//            int failticketnum = 0;
//            BetNo bn = new BetNo();
//            long beforeTime = System.currentTimeMillis();
//            for (String string : als) {
//                if (bn.send(string) == 0) {//发送数据
//                    successticketnum++;
//                } else {
//                    failticketnum++;
//                }
//            }
//            long endTime = System.currentTimeMillis();
//            long difference = endTime - beforeTime;
//            logger.info("用户名：" + tbr.getGamblerName() + "\t成功票数：" + successticketnum + "\t失败票数：" + failticketnum + "\t总共用时：" + CommTool.getTime(difference));
//            System.out.println("用户名：" + tbr.getGamblerName() + "\t成功票数：" + successticketnum + "\t失败票数：" + failticketnum + "\t总共用时：" + CommTool.getTime(difference));
//            JOptionPane.showMessageDialog(null, "用户名：" + tbr.getGamblerName() + " \t成功票数：" + successticketnum + " \t失败票数：" + failticketnum + " \t总共用时：" + CommTool.getTime(difference), "操作", JOptionPane.INFORMATION_MESSAGE);
//
//        } catch (Exception e) {
//            logger.error("", e);
//        }
//    }
//    
//    /**
//     * 140 141 运营商
//     * 创建投注发送的xml报文，获取流水号,用户定制号码
//     *
//     * @param head
//     * @param tbr
//     * @return
//     * @throws Exception
//     */
//    private ArrayList<String> getMuchMoreBetXml140(AppHeader head, PBetSchemeRequst tbr, int ticketnum) {
//        //取流水号        
//        AgentSerialReqRes serial = SerialNo.getSerialNo(head, tbr, ticketnum);
//        long startserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_startNo());
//        long endserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_endNo());
//        if (endserial < startserial) {
//            logger.error("用户未注册，流水号获取失败！");
//            return null;
//        }
//        head.setAction(ActionID.SCHEMES_BET);
//        ArrayList<String> xml = new ArrayList<String>();//存放xml报文
//        for (int i = 0; i < ticketnum; i++) {
//            try {
//                // tbr.setGambler_serial_no(String.format("%1$tS%1$tL", new Date()) + Long.toString(startserial));
//                tbr.setGambler_serial_no(new Date().getTime() + Long.toString(startserial));
//                //String Resultxml = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET).generateXML(head, tbr);
//                PBetSchemeClient betScheme = new PBetSchemeClient();
//                String requestxml = betScheme.generateXML(head, tbr);
//                if (requestxml == null || requestxml.isEmpty()) {
//                    logger.error("组装xml数据出错！");
//                    continue;
//                }
//                String sessionKey = (String) CacheManager.getCacheInfo(String.valueOf(head.getTerminal_id())).getValue();
//                logger.info("sessionKey is: " + sessionKey);
//                if (sessionKey == null) {
//                    logger.info("sessionKey is null");
//                }
//                byte[] ret_key = NewSign.GetSign(requestxml, "d:/gdclient.p12", "gdtest@2015", "cosmos", sessionKey);
//                //byte[] ret_key = NewSign.GetS(requestxml, "d:/gdtestp12.cer", str_key);
//                //byte[] ret_key = NewSign.GetSign(s, FilePath.getStorePath(), FilePath.getAliaspwd(), FilePath.getAlias(), str_key);
//                head.setSign(ret_key);
//                requestxml = betScheme.generateXML(head, tbr);
//                if (requestxml == null || requestxml.isEmpty()) {
//                    logger.error("组装xml数据出错！");
//                    continue;
//                }
//                requestxml = SerialNo.getxml(requestxml, ActionID.SCHEMES_BET);
//                xml.add(requestxml);
//                startserial += 1;
//                if (startserial > endserial) {
//                    break;
//                }
//            } catch (Exception ex) {
//                java.util.logging.Logger.getLogger(BatchBetSend.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return xml;
//    }
//    
//    
//     /**
//      * 145 146 运营商
//     * 发送投注的xml报文,单张报文，可以批量发送，用户定制号码
//     *
//     * @param head
//     * @param tbr
//     * @param isuser
//     * @param ticketnum
//     */
//    public void SendAppointBetXml145(AppHeader head, PBetSchemeRequst tbr, int ticketnum, boolean isuser) {
//        CookieStore cookieStore = new BasicCookieStore();
//        
//        ArrayList<String> als = getMuchMoreBetXml145(head, tbr, ticketnum);
//        if (als == null) {
//            return;
//        }
//        int successticketnum = 0;
//        int failticketnum = 0;
//        BetNo bn = new BetNo();
//        long beforeTime = System.currentTimeMillis();
////        String filename1 = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date()) + ".txt";
////        String filenameReq = StaticVariable.NUMBERREQXML + filename1;
////        String filenameRes = StaticVariable.NUMBERRESXML + filename1;
//
//        for (String string : als) {
//            if (bn.send(string, cookieStore) == 0) {//发送数据
//                successticketnum++;
//            } else {
//                failticketnum++;
//            }
//        }
//
//        long endTime = System.currentTimeMillis();
//        long difference = endTime - beforeTime;
//        logger.error("successful:" + successticketnum + "\tfailed:" + failticketnum + "\ttotalTime:" + CommTool.getTime(difference));
//        System.out.println("successful:" + successticketnum + "\tfailed:" + failticketnum + "\ttotalTime:" + CommTool.getTime(difference));
//        JOptionPane.showMessageDialog(null, "用户名：" + tbr.getGamblerName() + " \t成功 :" + successticketnum + " \t失败: " + failticketnum + " \t共用时间: " + CommTool.getTime(difference), "操作", JOptionPane.INFORMATION_MESSAGE);
//
//    }
//    
//    private ArrayList<String> getMuchMoreBetXml145(AppHeader head, PBetSchemeRequst tbr, int ticketnum) {
//        AgentSerialReqRes serial = SerialNo.getSerialNo(head, tbr, ticketnum);
//        if (serial == null) {
//            return null;
//        }
//        long startserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_startNo());
//        long endserial = Long.parseLong(serial.getApplySerials().get(0).getSerial_endNo());
//        if (endserial < startserial) {
//            logger.error("用户未注册，流水号获取失败！");
//            return null;
//        }
//        head.setAction(ActionID.SCHEMES_BET);
//        ArrayList<String> xml = new ArrayList<String>();//存放xml报文
//        for (int i = 0; i < ticketnum; i++) {
//            try {
//                if (tbr.getGamblerName() == null || tbr.getGamblerName().length() <= 0) {
//                    int a[] = CommTool.getRandInt(0, 9, 4);
//                    String bankno = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date());
//                    bankno = Integer.toString(a[0]) + Integer.toString(a[1]) + Integer.toString(a[2]) + Integer.toString(a[3]) + bankno;
//                    tbr.setGamblerName(bankno);//卡号
//                }
//                tbr.setGambler_serial_no(new Date().getTime() + Long.toString(startserial));
//                // tbr.setGambler_serial_no(String.format("%1$tS%1$tL", new Date()) + Long.toString(startserial));
//                // String Resultxml = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET).generateXML(head, tbr);
//
//                PBetSchemeClient betScheme = new PBetSchemeClient();
//                String requestxml = betScheme.generateXML(head, tbr);
//                if (requestxml == null || requestxml.isEmpty()) {
//                    logger.error("组装xml数据出错！");
//                    continue;
//                }
//                 String sessionKey = (String) CacheManager.getCacheInfo(String.valueOf(head.getTerminal_id())).getValue();
//                logger.info("sessionKey is: " + sessionKey);
//                if (sessionKey == null) {
//                    logger.info("sessionKey is null");
//                }
//                byte[] ret_key = NewSign.GetSign(requestxml, "d:/gdclient.p12", "gdtest@2015", "cosmos", sessionKey);
//                //byte[] ret_key = NewSign.GetS(requestxml, "d:/gdtestp12.cer", str_key);
//                //byte[] ret_key = NewSign.GetSign(s, FilePath.getStorePath(), FilePath.getAliaspwd(), FilePath.getAlias(), str_key);
//                head.setSign(ret_key);
//                requestxml = betScheme.generateXML(head, tbr);
//                if (requestxml == null || requestxml.isEmpty()) {
//                    logger.error("组装xml数据出错！");
//                    continue;
//                }
////            Resultxml = StaticVariable.ACTION + ActionID.SCHEMES_BET + "&" + Resultxml;
//                requestxml = SerialNo.getxml(requestxml, ActionID.SCHEMES_BET);
//                xml.add(requestxml);
//                startserial += 1;
//                if (startserial > endserial) {
//                    break;
//                }
//            } catch (Exception ex) {
//                java.util.logging.Logger.getLogger(SelfTerminals.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return xml;
//    }
//}
