/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package tk.mybatis.springboot.controller;

import com.alibaba.fastjson.JSONObject;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.springboot.dao.gamb.TicketBetPrizeDao;
import tk.mybatis.springboot.model.ReturnMsg;
import tk.mybatis.springboot.model.TicketBetPrize;
import tk.mybatis.springboot.util.MD5;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/***
 * 
 * @author Administrator
 * 彩票数据明细
 *
 */
@RestController
@RequestMapping("/ticket")
public class TicketBetPrizeController {
    Logger logger = Logger.getLogger(TicketBetPrizeController.class);
    @Autowired
    private TicketBetPrizeDao ticketBetPrizeDao;
    
    @RequestMapping("/1")
    public JSONObject getTicketBetPrize(){
    	JSONObject backJson = new JSONObject();
    	int terminalId = 31992010;
    	List<TicketBetPrize> tblist = ticketBetPrizeDao.getTicketBetPrizeByTmnid(terminalId);
    	backJson.put("LIST", tblist);
    	System.out.println(backJson);
    	return backJson;
    }
    
    @RequestMapping("/info")
    public JSONObject getTicketBetPrizeInfo() throws Exception {
    	JSONObject backJson = new JSONObject();
		String cipher = "42ed2b85811c80bab6ab485c";
		TicketBetPrize ticketBetPrize = ticketBetPrizeDao.getTicketBetPrizeByCipher(cipher);
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
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("betTime", betTime);
        map.put("betMoney", betMoney);
        map.put("terminal", terminalId);
        map.put("drawName", drawName);
        map.put("betLine", betLine);
        map.put("prizeDetail", prizeDetail);
        map.put("caHash", caHash);
        backJson.put("content", map);
    	logger.info("BackJson:"+backJson);
        return backJson;
    }
}
