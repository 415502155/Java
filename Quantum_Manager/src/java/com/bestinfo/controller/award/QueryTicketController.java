/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.controller.award;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bestinfo.arithmetic.MD5;
import com.bestinfo.bean.cash.CashRequest;
import com.bestinfo.bean.ticket.TicketBetPrize;
import com.bestinfo.dao.ticket.ITicketBetPrizeDao;
import com.bestinfo.define.Ticket.ReturnMsg;
import com.bestinfo.service.center.IAwardCashService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 彩票查询、电话投注兑奖
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/reward")
public class QueryTicketController {
    private final Logger logger = Logger.getLogger(QueryTicketController.class);
    
    @Resource
    private JdbcTemplate gamblerTemplate;
    @Resource
    private ITicketBetPrizeDao iTicketBetPrizeDao;
    @Resource
    private IAwardCashService cashService;
    
    /**
    * 彩票查询
    * param cipher
    * @author Administrator
    * @param request
    * @param response
    * @return 
    * @throws java.io.IOException
    */
    @RequestMapping(value = "/query")
    @ResponseBody
    public JSONObject queryTicketByCipher(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        JSONObject backJson = new JSONObject();
        ServletInputStream inputStream = request.getInputStream();
        String requestmsg = IOUtils.toString(inputStream);
        logger.info("receive  message:"+requestmsg);
        if(null == requestmsg || "".equals(requestmsg)){
            logger.info("request json :"+requestmsg);
            backJson.put("returnCode",ReturnMsg.REQUEST_PARAM_EXCEPTION.getCode());
            backJson.put("returnMsg",ReturnMsg.REQUEST_PARAM_EXCEPTION.getMsg());
            return backJson;
        }
        JSONObject jsonDataObj = JSON.parseObject(requestmsg);
        String cipher = jsonDataObj.getString("cipher");
        try {
            TicketBetPrize ticketBetPrize = iTicketBetPrizeDao.getTicketBetPrizeByCipher(gamblerTemplate, cipher);
            if(ticketBetPrize == null){
                logger.info("getTicketBetPrizeByCipher :"+ticketBetPrize);
                backJson.put("returnCode",ReturnMsg.QUERY_TICKETBETPRIZE_EXCEPTION.getCode());
                backJson.put("returnMsg",ReturnMsg.QUERY_TICKETBETPRIZE_EXCEPTION.getMsg());
                return backJson;
            }
            Date date = ticketBetPrize.getBet_time();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String betTime = sdf.format(date);        
            String betMoney = ticketBetPrize.getBet_money().toString();
            String terminalId = String.valueOf(ticketBetPrize.getTerminal_id());
            String drawName = ticketBetPrize.getDraw_name();
            String betLine = ticketBetPrize.getBet_line();
            String prizeDetail = ticketBetPrize.getPrize_detail();
            String str = betTime+betMoney+terminalId+drawName+betLine+prizeDetail; 
            String caHash = new MD5().digest(str, "MD5");
            backJson.put("returnCode", ReturnMsg.SUCCESS.getCode());
            backJson.put("returnMsg",ReturnMsg.SUCCESS.getMsg());
            Map map = new LinkedHashMap();
            map.put("betTime", betTime);
            map.put("betMoney", betMoney);
            map.put("terminal", terminalId);
            map.put("drawName", drawName);
            map.put("betLine", betLine);
            map.put("prizeDetail", prizeDetail);
            map.put("caHash", caHash);
            backJson.put("content", map);
            return backJson;
        } catch (Exception e) {
            logger.info("getTicketBetPrizeByCipher",e);
            backJson.put("returnCode",ReturnMsg.QUERY_DATA_EXCEPTION.getCode());
            backJson.put("returnMsg",ReturnMsg.QUERY_DATA_EXCEPTION.getMsg());
            return backJson;
        }
    }
    
    /***
     * 电话投注兑奖
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws Exception 
     */
    @RequestMapping(value = "/cash")
    @ResponseBody
    public JSONObject cashTicket(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        JSONObject backJson = new JSONObject();
        ServletInputStream inputStream = request.getInputStream();
        String requestmsg = IOUtils.toString(inputStream);
        logger.info("receive  message:"+requestmsg);
        if(null == requestmsg || "".equals(requestmsg)){
            logger.info("request json :"+requestmsg);
            backJson.put("returnCode",ReturnMsg.REQUEST_PARAM_EXCEPTION.getCode());
            backJson.put("returnMsg",ReturnMsg.REQUEST_PARAM_EXCEPTION.getMsg());
            return backJson;
        }
        JSONObject jsonDataObj = JSON.parseObject(requestmsg);
        String cipher = jsonDataObj.getString("cipher");
        String gamblerName = jsonDataObj.getString("gamblerName");
        String gamblerIdNo = jsonDataObj.getString("gamblerIdNo");
        String gamblerAddress = jsonDataObj.getString("gamblerAddress");
        String caHash = jsonDataObj.getString("caHash");
        String str = cipher+gamblerName+gamblerIdNo+gamblerAddress;
        String MD5str = new MD5().digest(str, "MD5");
        if(caHash != null){
            if(!caHash.equals(MD5str)){
                backJson.put("returnCode",ReturnMsg.CHECK_DATA_MD5_EXCEPTION.getCode());
                backJson.put("returnMsg",ReturnMsg.CHECK_DATA_MD5_EXCEPTION.getMsg());
                return backJson;
            }
        }
        CashRequest cashRequest = new CashRequest();
        cashRequest.setCipher(cipher);
        cashRequest.setOperator_id(Integer.parseInt("160"));
        cashRequest.setName(gamblerName);
        cashRequest.setId_card(gamblerIdNo);
        cashRequest.setAddress(gamblerAddress);
        cashRequest.setPlatform(0);//兑奖平台（需要兑奖的彩票是哪个平台投的注）
        cashRequest.setIdImgStr("");//证件照片路径
        cashRequest.setLotteryImgStr("");//彩票照片路径
        cashRequest.setTicket_type(Integer.parseInt("3"));
        logger.info("request msg:" + cashRequest.toString());
        
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap = cashService.cash(cashRequest);
        String code = (String)resMap.get("code");
        String msg = (String)resMap.get("msg");
        if("-100".equals(code)){
            backJson.put("returnCode", ReturnMsg.INIT_BACK_MSG.getCode());
            backJson.put("returnMsg",  ReturnMsg.INIT_BACK_MSG.getMsg());
        }
        if("-2".equals(code)){
            backJson.put("returnCode", ReturnMsg.QUERY_CIPHER.getCode());
            backJson.put("returnMsg",  ReturnMsg.QUERY_CIPHER.getMsg());
        }
        if("-3".equals(code)){
            backJson.put("returnCode", ReturnMsg.QUERY_TICKET_PRIZE.getCode());
            backJson.put("returnMsg",  ReturnMsg.QUERY_TICKET_PRIZE.getMsg());
        }
        if("-15".equals(code)){
            backJson.put("returnCode", ReturnMsg.QUERY_CASH_YES.getCode());
            backJson.put("returnMsg",  ReturnMsg.QUERY_CASH_YES.getMsg());
        }
        if("-6".equals(code)){
            backJson.put("returnCode", ReturnMsg.CHECK_CIPHER.getCode());
            backJson.put("returnMsg",  ReturnMsg.CHECK_CIPHER.getMsg());
        }
        if("-21".equals(code)){
            backJson.put("returnCode", ReturnMsg.CHECK_GAMEINFO.getCode());
            backJson.put("returnMsg",  ReturnMsg.CHECK_GAMEINFO.getMsg());
        }
        if("-8".equals(code) || "-9".equals(code) || "-10".equals(code) || "-11".equals(code)){
            backJson.put("returnCode", ReturnMsg.CHECK_GAME_DRAW.getCode());
            backJson.put("returnMsg",  ReturnMsg.CHECK_GAME_DRAW.getMsg());
        }
        if("-17".equals(code)){
            backJson.put("returnCode", ReturnMsg.CASH_CHECK_GAME.getCode());
            backJson.put("returnMsg",  ReturnMsg.CASH_CHECK_GAME.getMsg());
        }
        if("-19".equals(code)){
            backJson.put("returnCode", ReturnMsg.CASH_SERVICE_EXCEPTION.getCode());
            backJson.put("returnMsg",  ReturnMsg.CASH_SERVICE_EXCEPTION.getMsg());
        }
        if("-20".equals(code)){
            backJson.put("returnCode", ReturnMsg.CENTER_CASH_EXCEPTION.getCode());
            backJson.put("returnMsg",  ReturnMsg.CENTER_CASH_EXCEPTION.getMsg());
        }
        if("3155".equals(code)){
            backJson.put("returnCode", ReturnMsg.PROCEDURE_3155.getCode());
            backJson.put("returnMsg",  ReturnMsg.PROCEDURE_3155.getMsg());
        }
        if("6492".equals(code)){
            backJson.put("returnCode", ReturnMsg.PROCEDURE_6492.getCode());
            backJson.put("returnMsg",  ReturnMsg.PROCEDURE_6492.getMsg());
        }
        if("6493".equals(code)){
            backJson.put("returnCode", ReturnMsg.PROCEDURE_6493.getCode());
            backJson.put("returnMsg",  ReturnMsg.PROCEDURE_6493.getMsg());
        }
        if("6494".equals(code)){
            backJson.put("returnCode", ReturnMsg.PROCEDURE_6494.getCode());
            backJson.put("returnMsg",  ReturnMsg.PROCEDURE_6494.getMsg());
        }
        if("6495".equals(code)){
            backJson.put("returnCode", ReturnMsg.PROCEDURE_6495.getCode());
            backJson.put("returnMsg",  ReturnMsg.PROCEDURE_6495.getMsg());
        }
        if("6496".equals(code)){
            backJson.put("returnCode", ReturnMsg.PROCEDURE_6496.getCode());
            backJson.put("returnMsg",  ReturnMsg.PROCEDURE_6496.getMsg());
        }
        if("6591".equals(code)){
            backJson.put("returnCode", ReturnMsg.PROCEDURE_6591.getCode());
            backJson.put("returnMsg",  ReturnMsg.PROCEDURE_6591.getMsg());
        }
        if("6658".equals(code)){
            backJson.put("returnCode", ReturnMsg.PROCEDURE_6658.getCode());
            backJson.put("returnMsg",  ReturnMsg.PROCEDURE_6658.getMsg());
        }
        if("6659".equals(code)){
            backJson.put("returnCode", ReturnMsg.PROCEDURE_6659.getCode());
            backJson.put("returnMsg",  ReturnMsg.PROCEDURE_6659.getMsg());
        }
        if("6660".equals(code)){
            backJson.put("returnCode", ReturnMsg.PROCEDURE_6660.getCode());
            backJson.put("returnMsg",  ReturnMsg.PROCEDURE_6660.getMsg());
        }
        if("6911".equals(code)){
            backJson.put("returnCode", ReturnMsg.PROCEDURE_6911.getCode());
            backJson.put("returnMsg",  ReturnMsg.PROCEDURE_6911.getMsg());
        }
        if("6931".equals(code)){
            backJson.put("returnCode", ReturnMsg.PROCEDURE_6931.getCode());
            backJson.put("returnMsg",  ReturnMsg.PROCEDURE_6931.getMsg());
        }
        if("3003".equals(code)){
            backJson.put("returnCode", ReturnMsg.PROCEDURE_3003.getCode());
            backJson.put("returnMsg",  ReturnMsg.PROCEDURE_3003.getMsg());
        }       
        
        return backJson;
    }
}
