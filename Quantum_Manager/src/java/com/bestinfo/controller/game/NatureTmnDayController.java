package com.bestinfo.controller.game;

import com.bestinfo.service.game.INatureTmnDayService;
import com.bestinfo.util.TimeUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipOutputStream;
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
 * 站点游戏销售统计文件批量下载controller
 *
 * @author hhhh
 */
@Controller
@RequestMapping(value = "/natureTmnDay")
public class NatureTmnDayController {

    private final Logger logger = Logger.getLogger(NatureTmnDayController.class);

    @Resource
    private INatureTmnDayService natureTmnDayService;

    /**
     * 根据日期时间段批量下载该时间段的csv文件，以zip压缩包的形式返回给客户端
     *
     * @param request
     * @param resModel
     * @return
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> download(HttpServletRequest request, HttpServletResponse response, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        String begin_time_str = ServletRequestUtils.getStringParameter(request, "begin_time_str", "");
        String end_time_str = ServletRequestUtils.getStringParameter(request, "end_time_str", "");
        if ("".equals(begin_time_str) || "".equals(end_time_str)) {
            logger.error("EB send begin_time_str or end_time_str  is null, begin_time_str: " + begin_time_str + ", end_time_str: " + end_time_str);
            resMap.put("code", "-1");
            resMap.put("msg", "开始时间或截止时间为空！");
            return resMap;
        }

        Date beginTime = null;
        Date endTime = null;
        try {
            beginTime = TimeUtil.parseDate_YMD(begin_time_str);
            endTime = TimeUtil.parseDate_YMD(end_time_str);
            if (beginTime.after(endTime)) {
                logger.error("beginTime is after endTime, begin_time_str: " + begin_time_str + ", end_time_str: " + end_time_str);
                resMap.put("code", "-2");
                resMap.put("msg", "开始时间在截止时间之后！");
                return resMap;
            }
        } catch (Exception e) {
            logger.error("date format error, begin_time_str: " + begin_time_str + ", end_time_str: " + end_time_str, e);
            resMap.put("code", "-3");
            resMap.put("msg", "时间格式转换异常！");
            return resMap;
        }

        String zipFileName = natureTmnDayService.getZipFileName(begin_time_str, end_time_str);

        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + zipFileName);
        logger.info("begin natureTmnDay download, begin_time_str: " + begin_time_str + ", end_time_str: " + end_time_str);
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(response.getOutputStream());
            int re = natureTmnDayService.zipFile(beginTime, endTime, zos);
            if (re < 0) {
                logger.error("zip natureTmnDay csv file error, begin_time_str: " + begin_time_str + ", end_time_str: " + end_time_str);
                resMap.put("code", "-4");
                resMap.put("msg", "下载失败！");
                return resMap;
            }
            logger.info("zip natureTmnDay csv file success, begin_time_str: " + begin_time_str + ", end_time_str: " + end_time_str);
            zos.flush();
            zos.close();
        } catch (Exception e) {
            logger.error("download natureTmnDay csv file error, begin_time_str: " + begin_time_str + ", end_time_str: " + end_time_str);
            resMap.put("code", "-5");
            resMap.put("msg", "下载失败！");
            return resMap;
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (Exception e) {
                    logger.error("close ZipOutputStream error", e);
                    resMap.put("code", "-6");
                    resMap.put("msg", "下载失败！");
                    return resMap;
                }
            }
        }

        resMap.put("code", "0");
        resMap.put("msg", "下载成功！");
        return resMap;
    }

    /**
     * 根据日期手工生成某一天的csv文件
     *
     * @param request
     * @param resModel
     * @return
     */
    @RequestMapping(value = "/makeFile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> makeFile(HttpServletRequest request, HttpServletResponse response, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        String day_str = ServletRequestUtils.getStringParameter(request, "day_str", "");
        if ("".equals(day_str)) {
            logger.error("EB send day_str  is null, day_str: " + day_str);
            resMap.put("code", "-1");
            resMap.put("msg", "日期为空！");
            return resMap;
        }

        Date day = null;
        try {
            day = TimeUtil.parseDate_YMD(day_str);
        } catch (Exception e) {
            logger.error("date format error, day_str: " + day_str, e);
            resMap.put("code", "-2");
            resMap.put("msg", "时间格式错误！");
            return resMap;
        }

        int re = natureTmnDayService.makeFile(day_str);
        logger.info("natureTmnDay csv file make result : " + re + " where day_str = " + day_str);
        if (re < 0) {
            resMap.put("code", "-3");
            resMap.put("msg", "生成文件失败！");
        } else {
            resMap.put("code", "0");
            resMap.put("msg", "生成文件成功！");
        }
        return resMap;
    }

}
