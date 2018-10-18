package com.bestinfo.controller.game;

import com.bestinfo.define.filepath.FilePath;
import com.bestinfo.service.notice.ITicketBulletinService;
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
 * 彩票公告xml生成
 *
 * @author YangRong
 */
@Controller
@RequestMapping(value = "/ticketBulletin")
public class TicketBulletinController{

    private final Logger logger = Logger.getLogger(TicketBulletinController.class);

    @Resource
    private ITicketBulletinService ticketBulletinService;

    /**
     * 跳转到文件生成页面
     *
     * @return
     */
    @RequestMapping(value = "/2makeFilePage")
    public String toMakeFilePage() {
        return "/game/ticketBulletinFileMake";
    }

    /**
     * 生成相应的XML文件
     *
     * @param request
     * @param resModel
     * @return
     */
    @RequestMapping(value = "/makeFile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> makeFile(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        String filePath = FilePath.getRootPath() + FilePath.getLotteryBulletinPath();
        filePath = filePath.substring(0, filePath.lastIndexOf("."));
        int re = ticketBulletinService.makeBulletinXml(filePath);
        logger.info("bulletin xml file make result : " + re);
        resMap.put("code", re);
        if (re == 0) {
            resMap.put("msg", "操作成功");
        } else {
            resMap.put("msg", "操作失败");
        }
        return resMap;
    }
}
