//package com.bestinfo.controller.game;
//
//import com.bestinfo.arithmetic.Crypt3;
//import java.util.HashMap;
//import java.util.Map;
//import javax.servlet.http.HttpServletRequest;
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * 票面密码转化成彩票密码
// *
// * @author YangRong
// */
//@Controller
//@RequestMapping(value = "/ticketCipher")
//public class TicketCipherConvertController {
//
//    private final Logger logger = Logger.getLogger(TicketCipherConvertController.class);
//
//    @RequestMapping(value = "/2Cipher", method = RequestMethod.GET)
//    public void toCipher(HttpServletRequest request) {
//        String ticketCipher = request.getParameter("ticketCipher");
//        logger.info("ticketCipher=" + ticketCipher);
//
//        String cipher = new Crypt3().decrypt_ticket_cipherS(ticketCipher);
//        if (cipher == null) {
//            logger.info("cipher is null");
//        } else {
//            logger.info("cipher="+cipher);
//        }
//
//    }
//
//    @RequestMapping(value = "/getCipher", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> getCipher(HttpServletRequest request) {
//        String ticketCipher = request.getParameter("ticketCipher");
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        if (ticketCipher == null) {
//            resMap.put("cipher", "");
//            return resMap;
//        }
//        String cipher = new Crypt3().decrypt_ticket_cipherS(ticketCipher);
//        if (cipher == null) {
//            resMap.put("cipher", "");
//        } else {
//            resMap.put("cipher", cipher);
//        }
//        return resMap;
//
//    }
//}
