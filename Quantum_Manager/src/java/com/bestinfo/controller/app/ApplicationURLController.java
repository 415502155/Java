package com.bestinfo.controller.app;

import com.bestinfo.service.app.IApplicationUrlService;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * t_application_url表
 *
 * @author LiHui
 */
@Controller
@RequestMapping(value = "/applicationUrl")
public class ApplicationURLController {

    private final Logger logger = Logger.getLogger(ApplicationURLController.class);

    @Resource
    private IApplicationUrlService applicationUrlService;

//    @Resource
//    private ApplicationUrlCache applicationUrlCache;
    /**
     * 修改应用服务地址表信息
     *
     * @param request
     * @param resModel
     * @return
     */
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> change(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            int tag = applicationUrlService.reLoadApplicationUrlList();
            logger.info("modify applicationUrl list and syn code re:" + tag);
            if (tag < 0) {
                resMap.put("code", "-1");
                resMap.put("msg", "操作 失败！");
            } else {
                resMap.put("code", "0");
                resMap.put("msg", "操作 成功！");
            }
        } catch (Exception e) {
            logger.error("modify applicationUrl info error: ", e);
            resMap.put("code", "-2");
            resMap.put("msg", "操作 失败！");
        }
        return resMap;
    }

//    @RequestMapping(value = "/getAppListCache", method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String, Object> getAppListCache(HttpServletRequest request, Model resModel) {
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        List<AddressList> list = applicationUrlCache.getAddressListByProvinceId(31);
//        logger.info("list.size(): " + list.size() + "\n");
//        for (int i = 0; i < list.size(); i++) {
//            AddressList add = list.get(i);
//            logger.info(add.getApp_id() + " " + add.getApp_name() + " " + add.getApp_url() + "\n");
//        }
//        resMap.put("code", 0);
//        resMap.put("msg", "成功");
//        return resMap;
//    }
}
