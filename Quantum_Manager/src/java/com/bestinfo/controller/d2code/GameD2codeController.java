package com.bestinfo.controller.d2code;

import com.bestinfo.bean.ticket.d2code.D2codeInfo;
import com.bestinfo.bean.ticket.d2code.GameAddInfo;
import com.bestinfo.service.d2code.IGameD2codeService;
import com.bestinfo.util.TimeUtil;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 二维码信息
 */
@Controller
@RequestMapping(value = "/ebGameD2code")
public class GameD2codeController {

    private static final Logger logger = Logger.getLogger(GameD2codeController.class);

    @Resource
    private IGameD2codeService gameD2codeService;

    /**
     * 二维码-游戏外加信息新增
     *
     * @param request
     * @param gameAddInfo
     * @return
     */
    @RequestMapping(value = "/insertGame", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertGameAddInfo(HttpServletRequest request, GameAddInfo gameAddInfo) {
        logger.info("insert gameAddInfo");
        Map<String, Object> resMap = new HashMap<String, Object>();

        int result = gameD2codeService.ebInsertGameAddInfo(gameAddInfo);
        if (result < 0) {
            resMap.put("code", -1);
            resMap.put("msg", "保存失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "保存成功！");
        }

        return resMap;
    }

    /**
     * 二维码-游戏外加信息修改
     *
     * @param request
     * @param gameAddInfo
     * @return
     */
    @RequestMapping(value = "/modifyGame", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyGameAddInfo(HttpServletRequest request, GameAddInfo gameAddInfo) {
        logger.info("modify gameAddInfo");
        Map<String, Object> resMap = new HashMap<String, Object>();

        int result = gameD2codeService.ebmodifyGameAddInfo(gameAddInfo);
        if (result < 0) {
            resMap.put("code", -1);
            resMap.put("msg", "保存失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "保存成功！");
        }
        return resMap;
    }

    /**
     * 二维码编码描述新增
     *
     * @param request
     * @param d2codeInfo
     * @return
     */
    @RequestMapping(value = "/insertD2code", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertGameD2codeInfo(HttpServletRequest request, D2codeInfo d2codeInfo) {
        logger.info("insert d2code info ");
        Map<String, Object> resMap = new HashMap<String, Object>();

        String begin_date = ServletRequestUtils.getStringParameter(request, "begin_date_str", "");
        String end_date = ServletRequestUtils.getStringParameter(request, "begin_date_str", "");

        try {
            d2codeInfo.setBegin_date(TimeUtil.parseDate_YMD(begin_date));
            d2codeInfo.setEnd_date(TimeUtil.parseDate_YMD(end_date));
            int result = gameD2codeService.ebInsertD2codeInfo(d2codeInfo);
            if (result < 0) {
                resMap.put("code", -1);
                resMap.put("msg", "保存失败！");
            } else {
                resMap.put("code", 0);
                resMap.put("msg", "保存成功！");
            }
        } catch (Exception e) {
            logger.error("insert d2code info error", e);
            resMap.put("code", -1);
            resMap.put("msg", "保存失败！");
        }
        return resMap;
    }

    /**
     * 二维码编码描述修改
     *
     * @param request
     * @param d2codeInfo
     * @return
     */
    @RequestMapping(value = "/modifyD2code", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyGameD2codeInfo(HttpServletRequest request, D2codeInfo d2codeInfo) {
        logger.info("modify d2code info");
        Map<String, Object> resMap = new HashMap<String, Object>();
        String begin_date = ServletRequestUtils.getStringParameter(request, "begin_date_str", "");
        String end_date = ServletRequestUtils.getStringParameter(request, "end_date_str", "");

        try {
            d2codeInfo.setBegin_date(TimeUtil.parseDate_YMD(begin_date));
            d2codeInfo.setEnd_date(TimeUtil.parseDate_YMD(end_date));
            int result = gameD2codeService.ebmodifyD2codeInfo(d2codeInfo);
            if (result < 0) {
                resMap.put("code", -1);
                resMap.put("msg", "保存失败！");
            } else {
                resMap.put("code", 0);
                resMap.put("msg", "保存成功！");
            }
        } catch (Exception e) {
            logger.error("modify d2code info error", e);
            resMap.put("code", -1);
            resMap.put("msg", "保存失败！");
        }

        return resMap;
    }

}
