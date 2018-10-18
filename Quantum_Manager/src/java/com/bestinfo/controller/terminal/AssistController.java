package com.bestinfo.controller.terminal;

import com.bestinfo.service.assist.IAssistService;
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
 * 终端电子辅助控制
 */
@Controller
@RequestMapping(value = "/terminal")
public class AssistController {

    private static final Logger logger = Logger.getLogger(AssistController.class);

    @Resource
    private IAssistService assistService;

    /**
     * 设置终端电子辅助标记
     *
     * @param request
     * @param resModel
     * @return
     */
    @RequestMapping(value = "/assist", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> tmnAssistSet(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();

        String city_id = ServletRequestUtils.getStringParameter(request, "city_id", "");//根据区域
        String station_rank = ServletRequestUtils.getStringParameter(request, "station_rank", "");//根据投注机等级
        String terminal_id_str = ServletRequestUtils.getStringParameter(request, "terminal_id_str", "");//根据投注机号串,用“,”分隔
        int work = ServletRequestUtils.getIntParameter(request, "work", 0);//开通与否,1开通，0不开通

        logger.info("tmn assist set,city_id:" + city_id + ",station_rank:" + station_rank + ",terminal_id_str:" + terminal_id_str + ",work:" + work);
        int re = assistService.assistControl(city_id, station_rank, terminal_id_str, work);
        if (re < 0) {
            resMap.put("code", -1);
            resMap.put("msg", "更新电子辅助标记失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "更新电子辅助标记成功！");
        }
        return resMap;
    }

}
