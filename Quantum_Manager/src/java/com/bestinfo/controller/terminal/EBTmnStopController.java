package com.bestinfo.controller.terminal;

import com.bestinfo.bean.terminal.TerminalStopLog;
import com.bestinfo.service.impl.terminal.TmnStopAndStartServiceImpl;
import com.bestinfo.service.terminal.ITmnStopAndStartService;
import com.bestinfo.util.TimeUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 投注机封机、解封controller
 *
 * @author hhhh
 */
@Controller
@RequestMapping(value = "/ebTmnStop")
public class EBTmnStopController {

    private final Logger logger = Logger.getLogger(EBTmnStopController.class);

    @Resource
    private ITmnStopAndStartService tmnStopService;

    @Resource
    private TmnStopAndStartServiceImpl tmnStartService;

    /**
     * 单个投注机封机
     *
     * @param request
     * @param resModel
     * @param tsl
     * @return
     */
    @RequestMapping(value = "/tmnStop", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> tmnStop(HttpServletRequest request, Model resModel, TerminalStopLog tsl) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            logger.debug("----" + tsl.getTerminal_id() + "-----" + tsl.getReason_type() + "----" + tsl.getStop_time() + "------" + tsl.getContinue_time_str() + "-----" + tsl.getUser_id() + "------" + tsl.getAuto_continue());
            //空值校验(假如不传user_id，默认处理为0)
            int user_id = ServletRequestUtils.getIntParameter(request, "user_id", 0);
            tsl.setUser_id(user_id);

            String continue_time = ServletRequestUtils.getStringParameter(request, "continue_time_str");
            if (continue_time == null || "null".equals(continue_time.trim()) || "".equals(continue_time.trim())) {
                logger.error("the cotinue_time is null where continue_time=" + continue_time);
                resMap.put("code", -4);
                resMap.put("msg", "解封时间为空!");
                return resMap;
            }

            Date date1 = TimeUtil.parseDate_YMDT(continue_time);
            Date date2 = new Date();
            int day = TimeUtil.daysBetween(date2, date1);
            if (day <= 0) {
                logger.error("the day of continueTime is <= currentDay where continue_time = " + continue_time);
                resMap.put("code", -3);
                resMap.put("msg", "解封时间应该大于当前天!");
                return resMap;
            }

            int result = tmnStopService.stopTmn(tsl);
            logger.info("tmn stop result :" + result);
            if (result == -2) {
                resMap.put("code", -2);
                resMap.put("msg", "该终端已撤销，不能进行封机操作！");
                logger.error("tmn stop failed where terminal_id = " + tsl.getTerminal_id());
            } else {
                if (result < 0) {
                    logger.error("tmn stop failed where terminal_id = " + tsl.getTerminal_id());
                    resMap.put("code", -1);
                    resMap.put("msg", "封机 失败！");
                } else {
                    resMap.put("code", 0);
                    resMap.put("msg", "封机 成功！");
                }
            }

            return resMap;
        } catch (Exception ex) {
            logger.error("", ex);
            resMap.put("code", -1);
            resMap.put("msg", "封机 失败!");
            return resMap;
        }
    }

    /**
     * 投注机批量封机
     *
     * @param request
     * @param resModel
     * @param tsl
     * @return
     */
    @RequestMapping(value = "/tmnStopBatch")
    @ResponseBody
    public Map<String, Object> tmnStopBatch(HttpServletRequest request, Model resModel, TerminalStopLog tsl) {
        //空值校验(假如不传user_id，默认处理为0)
        int user_id = ServletRequestUtils.getIntParameter(request, "user_id", 0);
        String city_id = ServletRequestUtils.getStringParameter(request, "city_id", "");//根据区域封机
        String begin_terminal_id = ServletRequestUtils.getStringParameter(request, "begin_terminal_id", "");//根据起止投注机号封机
        String end_terminal_id = ServletRequestUtils.getStringParameter(request, "end_terminal_id", "");
        String terminal_id_str = ServletRequestUtils.getStringParameter(request, "terminal_id_str", "");//根据投注机号串封机,用“,”分隔
        tsl.setUser_id(user_id);

        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            String continue_time = ServletRequestUtils.getStringParameter(request, "continue_time_str");
            if (continue_time == null || "null".equals(continue_time.trim()) || "".equals(continue_time.trim())) {
                logger.error("the cotinue_time is null where continue_time=" + continue_time);
                resMap.put("code", -3);
                resMap.put("msg", "解封时间为空!");
                return resMap;
            }

            Date date1 = TimeUtil.parseDate_YMDT(continue_time);
            Date date2 = new Date();
            int day = TimeUtil.daysBetween(date2, date1);
            if (day <= 0) {
                logger.error("the day of continueTime is <= currentDay where continue_time = " + continue_time);
                resMap.put("code", -2);
                resMap.put("msg", "解封时间应该大于当前天!");
                return resMap;
            }

            int result = tmnStopService.stopTmnBatch(city_id, begin_terminal_id, end_terminal_id, terminal_id_str, tsl);
            logger.info("tmn batch stop result :" + result);
            if (result < 0) {
                logger.error("batch stop tmn error");
                resMap.put("code", -1);
                resMap.put("msg", "批量封机 失败!");
            } else {
                resMap.put("code", 0);
                resMap.put("msg", "批量封机 成功!");
            }
            return resMap;
        } catch (Exception e) {
            logger.error("batch stop tmn occur exception", e);
            resMap.put("code", -1);
            resMap.put("msg", "批量封机 失败!");
            return resMap;
        }
    }

    /**
     * 投注机解封
     *
     * @param request
     * @param resModel
     * @return
     */
    @RequestMapping(value = "/tmnStart", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> tmnStart(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            int terminal_id = ServletRequestUtils.getIntParameter(request, "terminal_id", 0);
            String stop_time = ServletRequestUtils.getStringParameter(request, "stop_time", "");
            int unlock_user_id = ServletRequestUtils.getIntParameter(request, "unlock_user_id", 0);

            if (terminal_id == 0 || "".equals(stop_time) || unlock_user_id == 0) {
                logger.error("tmn start failed where terminal_id = " + terminal_id + " and stop_time = " + stop_time + " and unlock_user_id = " + unlock_user_id + ",EB send param is incorrect.");
                resMap.put("code", -1);
                resMap.put("msg", "解封 失败!");
            }

            int result = tmnStartService.startTmn(terminal_id, stop_time, unlock_user_id);
            logger.info("tmn start result :" + result);
            if (result < 0) {
                logger.error("tmn start failed where terminal_id = " + terminal_id);
                resMap.put("code", -1);
                resMap.put("msg", "解封 失败!");
            } else {
                resMap.put("code", 0);
                resMap.put("msg", "解封 成功!");
            }
            return resMap;
        } catch (Exception ex) {
            logger.error("", ex);
            resMap.put("code", -1);
            resMap.put("msg", "解封 失败!");
            return resMap;
        }
    }
}
