package com.bestinfo.controller.terminal;

import com.bestinfo.service.task.ITaskPlanService;
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
 * 手动检测任务并执行接入子系统维护费和销售设备维护费
 *
 * @author lvchangrong
 */
@Controller
@RequestMapping(value = "/ebTaskPlanCtr")
public class EBTaskPlanCtr {

    private final Logger logger = Logger.getLogger(EBTaskPlanCtr.class);

    @Resource
    private ITaskPlanService taskPlanService;


    /**
     * 手动检测任务并执行接入子系统维护费和销售设备维护费
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/doTaskPlan", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doTaskPlan(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
         logger.info("=====doTaskPlan   Start");
        int re = taskPlanService.doTaskPlan();
        if (re == 0) {
            resMap.put("code", 0);
            resMap.put("msg", "执行成功！");
        } else {
            resMap.put("code", -1);
            resMap.put("msg", " 执行失败！");
        }
        return resMap;
    }
}
