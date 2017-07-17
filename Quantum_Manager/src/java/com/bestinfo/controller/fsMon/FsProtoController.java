package com.bestinfo.controller.fsMon;

import com.bestinfo.service.fsMonitor.IFsProtoService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import org.springframework.web.bind.ServletRequestUtils;

/**
 * 文件系统协议
 *
 * @author LiHui
 */
@Controller
@RequestMapping(value = "/fsProto")
public class FsProtoController {

    private final Logger logger = Logger.getLogger(FsProtoController.class);

    @Resource
    private IFsProtoService fsProtoService;

    /**
     * 开新期补救措施---重新向文件系统发送协议
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/fsNewDrawProto")
    public @ResponseBody
    Map<String, Object> fsNewDrawProto(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            int game_id = ServletRequestUtils.getIntParameter(request, "game_id", 0);
            String draw_name = ServletRequestUtils.getStringParameter(request, "draw_name", "0");
            logger.info("game_id:" + game_id + ",draw_name:" + draw_name);
            if (game_id <= 0 || "0".equals(draw_name)) {
                logger.error("data error,game_id:" + game_id + ",draw_name:" + draw_name);
                resMap.put("code", -1);
                resMap.put("msg", "参数错误！");
                return resMap;
            }
            int re = fsProtoService.fsProto(game_id, draw_name);
            if (re == 0) {
                resMap.put("code", 0);
                resMap.put("msg", "操作成功！");
            } else {
                logger.error("fs new draw failed where game_id=" + game_id + " and draw_name=" + draw_name);
                resMap.put("code", -2);
                resMap.put("msg", "操作失败！");
            }

            return resMap;
        } catch (Exception ex) {
            logger.error("'ex :", ex);
            resMap.put("code", -3);
            resMap.put("msg", "操作失败！");
            return resMap;
        }
    }

}
