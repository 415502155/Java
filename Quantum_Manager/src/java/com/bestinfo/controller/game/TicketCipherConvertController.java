package com.bestinfo.controller.game;

import com.bestinfo.arithmetic.Crypt3;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 票面密码转化成彩票密码
 *
 * @author YangRong
 */
@Controller
@RequestMapping(value = "/ticketCipher")
public class TicketCipherConvertController {

    private static final Logger logger = Logger.getLogger(TicketCipherConvertController.class);

    @RequestMapping(value = "/2Cipher", method = RequestMethod.GET)
    public void toCipher(HttpServletRequest request) {
        String ticketCipher = request.getParameter("ticketCipher");
        logger.info("ticketCipher:" + ticketCipher);

        String cipher = new Crypt3().decryptTicketCipherS(ticketCipher);
        if (cipher == null) {
            logger.info("cipher is null");
        } else {
            logger.info("cipher=" + cipher);
        }

    }

    @RequestMapping(value = "/getCipher", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCipher(HttpServletRequest request) {
        String ticketCipher = request.getParameter("ticketCipher");
        Map<String, Object> resMap = new HashMap<String, Object>();
        if (ticketCipher == null) {
            resMap.put("cipher", "");
            return resMap;
        }
        String cipher = new Crypt3().decryptTicketCipherS(ticketCipher);
        if (cipher == null) {
            resMap.put("cipher", "");
        } else {
            resMap.put("cipher", cipher);
        }
        return resMap;
    }

    /**
     * 批量转换票密码<br>
     * id1:0839c42a236c8561fc295c22,id2:64c2222fdd409b0924a10fd8,id3:3d5e3eff95633836b92b8fe1,id4:0839c42a236c8561fc295c11
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getBatchCipher", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getBatchCipher(HttpServletRequest request) {
        logger.warn("get batch cipher");
        Map<String, Object> resMap = new HashMap<String, Object>();
        Map<String, String> cipherMap = new HashMap<String, String>();
        String ticketCipherStr = ServletRequestUtils.getStringParameter(request, "ticketCipher", "");
        if ("".equals(ticketCipherStr)) {
            resMap.put("cipher", "");
            return resMap;
        }
        String cipher = "";
        String[] arr1 = ticketCipherStr.split(",");
        for (String ticketCipher : arr1) {
            String[] arr2 = ticketCipher.split(":");
            if (arr2.length == 2) {
                cipher = new Crypt3().decryptTicketCipherS(arr2[1]);
                if (cipher == null) {
                    cipherMap.put(arr2[0], "");
                } else {
                    cipherMap.put(arr2[0], cipher);
                }
            } else {
                cipherMap.put("", "");
            }
        }
        try {
            logger.warn("cipherMap:" + new ObjectMapper().writeValueAsString(cipherMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
        resMap.put("cipher", cipherMap);
        return resMap;
    }
}
