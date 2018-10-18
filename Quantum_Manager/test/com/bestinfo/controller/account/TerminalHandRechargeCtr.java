//package com.bestinfo.controller.account;
//
//import com.bestinfo.service.account.IAccountHandRechargeSer;
//import javax.annotation.Resource;
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * 投注机手工充值控制器
// *
// * @author shange
// */
//@Controller
//@RequestMapping(value = "/handRecharge")
//public class TerminalHandRechargeCtr {
//
//    private final Logger logger = Logger.getLogger(TerminalHandRechargeCtr.class);
//    @Resource
//    private IAccountHandRechargeSer accountHandReServ;
//
//    @RequestMapping(value = "/terminalRecharge", method = RequestMethod.POST)
//    @ResponseBody
//    public int handRecharge(@RequestParam(value = "terminalId", required = false) String terminalId, @RequestParam(value = "rechargeMoney", required = false) double rechargeMoney) {
//        return accountHandReServ.procHandRecharge(terminalId, rechargeMoney);
//    }
//}
