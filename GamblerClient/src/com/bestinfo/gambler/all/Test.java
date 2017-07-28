///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package com.bestinfo.gambler.all;
//
//import com.bestinfo.protocols.bet.FileNameQueryReq;
//import com.bestinfo.protocols.client.bet.PFileNameQueryClient;
//import com.bestinfo.protocols.message.AppHeader;
//
///**
// *
// * @author jone
// */
//public class Test {
//    public static void main(String[] args) {
//        // TODO code application logic here
//        String url = "http://localhost:8080/Quantum_Gambler/gambler";
//        
//        AppHeader head = new AppHeader();
//        head.setAction(237);
//        head.setDealer_id("140");
//        head.setMobile("15101105612");
//        head.setPhone("15101105612");
//        head.setSent_time("2015-07-29 12:12:12");
//        head.setTerminal_id(0);
//        head.setType(3);
//        head.setVersion(0);
//        head.setSign(new byte[0]);
//        FileNameQueryReq req=new FileNameQueryReq();
//        req.setGameId(1);
//        req.setDrawId(63);
//        req.setFileType(2);
//        PFileNameQueryClient ps = new PFileNameQueryClient();
//        String s = ps.generateXML(head, req);
//        System.out.println(s);
//        String re = httpSend.httpSend(url, "action=237&"+s);
//        System.out.println(re);
//        
//    }
//}
