package com.bestinfo.controller.terminal;

import com.bestinfo.service.terminal.ITmnStopAndStartService;
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
 * 投注机封机controller
 *
 * @author hhhh
 */
@Controller
@RequestMapping(value = "/tmnStop")
public class TmnStopController {

    private final Logger logger = Logger.getLogger(TmnStopController.class);

    @Resource
    private ITmnStopAndStartService tmnStopService;

    /**
     * 投注机封机
     *
     * @param request
     * @param resModel
     * @return
     */
    @RequestMapping(value = "/tmnStop", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> tmnStop(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int user_id = ServletRequestUtils.getIntParameter(request, "user_id", 0);
        int terminal_id = ServletRequestUtils.getIntParameter(request, "terminal_id", 0);
        int reason_type = ServletRequestUtils.getIntParameter(request, "reason_type", 0);
        String stop_reason = ServletRequestUtils.getStringParameter(request, "stop_reason", "");

        int result = tmnStopService.stopTmn(user_id, terminal_id, reason_type, stop_reason);
        logger.info("tmn stop result :" + result);
        if (result < 0) {
            logger.error("tmn stop failed where terminal_id = " + terminal_id);
            resMap.put("code", -1);
            resMap.put("msg", "封机失败");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "封机成功");
        }
        return resMap;
    }

}
