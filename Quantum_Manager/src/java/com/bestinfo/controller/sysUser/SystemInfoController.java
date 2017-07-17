package com.bestinfo.controller.sysUser;

import com.bestinfo.bean.sysUser.SystemInfo;
import com.bestinfo.ehcache.system.SystemInfoCache;
import com.bestinfo.service.sysUser.ISystemInfoService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 省系统信息
 *
 * @author YangRong
 */
@Controller
@RequestMapping(value = "/systemInfo")
public class SystemInfoController {

    private final Logger logger = Logger.getLogger(SystemInfoController.class);

    @Resource
    private SystemInfoCache systemInfoCache;
    @Resource
    private ISystemInfoService systemInfoService;

    /**
     * 根据Id获取系统信息表
     *
     * @param request
     * @param resModel
     * @return
     */
    @RequestMapping(value = "/getSystemInfo")
    public String getSystemInfoById(HttpServletRequest request, Model resModel) {
        logger.info("get systeminfo from cache");
        List<SystemInfo> systemInfo = systemInfoCache.getSystemInfoList();
        if (systemInfo != null && !systemInfo.isEmpty()) {
            resModel.addAttribute("systemInfo", systemInfo.get(0));
            return "/sysUser/systemInfo";
        }
        return "/sysUser/systemInfo";
    }

    /**
     * 修改系统信息表
     *
     * @param request
     * @param systemInfo
     * @return
     */
    @RequestMapping(value = "/modifySystemInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifySystemInfo(HttpServletRequest request, SystemInfo systemInfo) {
        logger.info("modify system info");
        logger.info(request.getParameter("province_name"));
        logger.info(systemInfo.getProvince_name());
        logger.info(systemInfo.getProvince_address());
        logger.info(systemInfo.getProvince_phone());

        Map<String, Object> resMap = new HashMap<String, Object>();
        int result = systemInfoService.updateSystemInfo(systemInfo);
        if (result == 0) {
            resMap.put("result", "success");
            resMap.put("msg", "修改省系统信息成功！");
        } else if (result < 0) {
            resMap.put("result", "fail");
            resMap.put("msg", "修改省系统信息失败！");
        }

        return resMap;
    }

    /**
     * 修改系统信息表--testEB
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifySystemInfo4EB", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> modifySystemInfo4EB(HttpServletRequest request) {
        logger.info("modify system info");

        String province_name = ServletRequestUtils.getStringParameter(request, "province_name", "");
        String province_address = ServletRequestUtils.getStringParameter(request, "province_address", "");
        String province_phone = ServletRequestUtils.getStringParameter(request, "province_phone", "");

        logger.info("province_name:" + province_name);
        logger.info("province_address:" + province_address);
        logger.info("province_phone:" + province_phone);

        Map<String, Object> resMap = new HashMap<String, Object>();

        int result = 0;//systemInfoService.updateSystemInfo(systemInfo);
        if (result == 0) {
            resMap.put("result", "success");
            resMap.put("msg", "修改省系统信息成功！");
        } else if (result < 0) {
            resMap.put("result", "fail");
            resMap.put("msg", "修改省系统信息失败！");
        }

        return resMap;
    }

    @RequestMapping(value = "/modifySystemInfo4EB2", method = {RequestMethod.POST, RequestMethod.GET})
    public void modifySystemInfo4EB2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("modify system info 4 eb 2");

        String province_name = ServletRequestUtils.getStringParameter(request, "province_name", "");
        String province_address = ServletRequestUtils.getStringParameter(request, "province_address", "");
        String province_phone = ServletRequestUtils.getStringParameter(request, "province_phone", "");

        logger.info("province_name:" + province_name);
        logger.info("province_address:" + province_address);
        logger.info("province_phone:" + province_phone);

        Map<String, Object> resMap = new HashMap<String, Object>();

        int result = 0;//systemInfoService.updateSystemInfo(systemInfo);
        if (result == 0) {
            resMap.put("result", "success");
            resMap.put("msg", "修改省系统信息成功！");
        } else if (result < 0) {
            resMap.put("result", "fail");
            resMap.put("msg", "修改省系统信息失败！");
        }

        response.setContentType("text/plain;charset=gbk");
        PrintWriter out = response.getWriter();
        //out.println("我很好！");
        out.println(resMap);

    }
}
