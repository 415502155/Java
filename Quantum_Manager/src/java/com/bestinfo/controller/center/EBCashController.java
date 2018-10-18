package com.bestinfo.controller.center;

import com.bestinfo.arithmetic.Crypt3;
import com.bestinfo.bean.cash.CashRequest;
import com.bestinfo.service.center.ICenterCash4TransService;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 电话投注中心兑奖
 */
@Controller
@RequestMapping(value = "/ebCash")
public class EBCashController {

    private static final Logger logger = Logger.getLogger(EBCashController.class);

    @Resource
    private ICenterCash4TransService cashService;

    /**
     * 查询中心是否可以兑奖
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/canCash", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> canCash(HttpServletRequest request) {
        logger.info("center cash check");
        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            //获取票面密码
            String cipher = request.getParameter("cipher").trim();
            logger.info("eb cipher:" + cipher);
            Crypt3 crypt = new Crypt3();
            String decryptCipher = crypt.decryptTicketCipherS(cipher);
            logger.info("ticket cipher:" + decryptCipher);

            CashRequest cashRequest = new CashRequest();
            cashRequest.setCipher(decryptCipher);
            cashRequest.setOperator_id(Integer.parseInt(request.getParameter("operator_id").trim()));
            cashRequest.setPlatform(Integer.parseInt(request.getParameter("platform").trim()));//兑奖平台（需要兑奖的彩票是哪个平台投的注）

            logger.info("request from eb:" + cashRequest.toString());
            resMap = cashService.cashCheck(cashRequest);
            return resMap;
        } catch (Exception e) {
            logger.error("center cash check exception", e);
            resMap.put("code", "-1");
            resMap.put("msg", "兑奖检查异常");
            return resMap;
        }
    }

    /**
     * 中心兑奖
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/cash", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> cash(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            //获取票面密码
            String cipher = request.getParameter("cipher").trim();
            logger.info("center cash,eb cipher:" + cipher);
            Crypt3 crypt = new Crypt3();
            String decryptCipher = crypt.decryptTicketCipherS(cipher);
            logger.info("center cash,ticket cipher:" + decryptCipher);

            CashRequest cashRequest = new CashRequest();
            cashRequest.setCipher(decryptCipher);
            cashRequest.setOperator_id(Integer.parseInt(request.getParameter("operator_id").trim()));
            cashRequest.setName(request.getParameter("name").trim());
            cashRequest.setId_card(request.getParameter("id_card").trim());
            cashRequest.setAddress(request.getParameter("address").trim());
            cashRequest.setPlatform(0);//兑奖平台（需要兑奖的彩票是哪个平台投的注）
            cashRequest.setIdImgStr("");//证件照片路径
            cashRequest.setLotteryImgStr("");//彩票照片路径
            cashRequest.setTicket_type(Integer.parseInt(request.getParameter("ticket_type").trim()));
            logger.info("request from eb:" + cashRequest.toString());
            resMap = cashService.cash(cashRequest);
            return resMap;
        } catch (Exception e) {
            logger.error("center cash exception", e);
            resMap.put("code", "-1");
            resMap.put("msg", "兑奖异常");
            return resMap;
        }
    }

}
