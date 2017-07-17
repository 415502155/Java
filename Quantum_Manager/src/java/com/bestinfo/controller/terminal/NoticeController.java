package com.bestinfo.controller.terminal;

import com.bestinfo.service.notice.INoticeIssuedService;
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
 * 通知管理
 *
 * @author hhhh
 */
@Controller
@RequestMapping(value = "/ebtmnnotice")
public class NoticeController {
    
    private final Logger logger = Logger.getLogger(NoticeController.class);
    
    @Resource
    private INoticeIssuedService noticeIssuedService;

    /**
     * 通知下发,根据二级区域、起止投注机号(包含)或投注机号串下发
     *
     * @param request
     * @param resModel
     * @return
     */
    @RequestMapping(value = "/noticeIssued", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> noticeIssued(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        
        String cms_id = ServletRequestUtils.getStringParameter(request, "cms_id", "");
        String city_id = ServletRequestUtils.getStringParameter(request, "city_id", "");//根据区域下发通知
        String begin_terminal_id = ServletRequestUtils.getStringParameter(request, "begin_terminal_id", "");//根据起止投注机号下发通知
        String end_terminal_id = ServletRequestUtils.getStringParameter(request, "end_terminal_id", "");
        String terminal_id_str = ServletRequestUtils.getStringParameter(request, "terminal_id_str", "");//根据投注机号串下发通知,用“,”分隔

        int re = noticeIssuedService.noticeIssued(cms_id, city_id, begin_terminal_id, end_terminal_id, terminal_id_str);
        logger.info("notice issued result :" + re+" where cms_id ="+cms_id+" and city_id="+city_id+" and begin_terminal_id="+begin_terminal_id+" and end_terminal_id = "+end_terminal_id+" and terminal_id_str = "+terminal_id_str);
        if (re == 0) {
            resMap.put("code", 0);
            resMap.put("msg", "通知下发 成功！");
        } else if (re == -1) {
            resMap.put("code", -1);
            resMap.put("msg", "已下发过！");
        } else {
            resMap.put("code", -2);
            resMap.put("msg", "通知下发 失败！");
        }
        return resMap;
    }
    
}
