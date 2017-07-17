package com.bestinfo.controller.terminal;

import com.bestinfo.service.soft.ISoftUpdateService;
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
 * 软件下载--升级
 *
 * @author hhhh
 */
@Controller
@RequestMapping(value = "/tmnsoft4eb")
public class SoftController {

    private final Logger logger = Logger.getLogger(SoftController.class);

    @Resource
    private ISoftUpdateService softUpdateService;

    /**
     * 终端升级,根据二级区域、起止投注机号(包含)或投注机号串升级
     *
     * @param request
     * @param resModel
     * @return
     */
    @RequestMapping(value = "/upgrade", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upgrade(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();

        String city_id = ServletRequestUtils.getStringParameter(request, "city_id", "");//根据区域升级终端
        String begin_terminal_id = ServletRequestUtils.getStringParameter(request, "begin_terminal_id", "");//根据起止投注机号升级终端
        String end_terminal_id = ServletRequestUtils.getStringParameter(request, "end_terminal_id", "");
        String terminal_id_str = ServletRequestUtils.getStringParameter(request, "terminal_id_str", "");//根据投注机号串升级终端,用“,”分隔

        int re = softUpdateService.softUpdate(city_id, begin_terminal_id, end_terminal_id, terminal_id_str);
        if (re < 0) {
            resMap.put("code", -1);
            resMap.put("msg", "软件升级失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "软件升级成功！");
        }
        return resMap;
    }

    /**
     * 当终端信息里的软件版本在软件表里存在时，将此终端的升级标记改为不升级或 当终端信息里的软件版本在软件表里不存在时，将此终端的升级标记改为不升级
     *
     * @param request
     * @param resModel
     * @return
     */
    @RequestMapping(value = "/clearUpgradeMark", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> clearUpgradeMark(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();

        String city_id = ServletRequestUtils.getStringParameter(request, "city_id", "");
        String begin_terminal_id = ServletRequestUtils.getStringParameter(request, "begin_terminal_id", "");
        String end_terminal_id = ServletRequestUtils.getStringParameter(request, "end_terminal_id", "");
        String mode = ServletRequestUtils.getStringParameter(request, "mode", "");

        int re = softUpdateService.updateUpgradeMark(city_id, begin_terminal_id, end_terminal_id, mode.trim());
        logger.info("clearUpgradeMark result: " + re + ", where city_id = " + city_id + " and begin_terminal_id = " + begin_terminal_id + " and end_terminal_id = " + end_terminal_id + " and mode = " + mode);
        if (re == 0) {
            resMap.put("code", 0);
            resMap.put("msg", "清除升级标记成功！");
        } else if (re == -4) {
            resMap.put("code", -1);
            resMap.put("msg", "参数错误！");
        } else if (re == -1) {
            resMap.put("code", -2);
            resMap.put("msg", "没有数据！");
        } else {
            resMap.put("code", -3);
            resMap.put("msg", "清除升级标记失败！");
        }

        return resMap;
    }

}
