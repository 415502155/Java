//package com.bestinfo.controller.department;
//
//import com.bestinfo.bean.department.DePartMent;
//import com.bestinfo.ehcache.system.DePartMentCache;
//import java.util.List;
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * 部门信息操作类
// *
// * @author lvchangrong
// */
//@Controller
//@RequestMapping(value = "/department")
//public class DePartMentController {
//
//    private final Logger logger = Logger.getLogger(DePartMentController.class);
//
//    @Resource
//    private DePartMentCache dePartMentCache;
//
//    /**
//     * 从缓存中获取所有部门信息用于下拉列表数据 js调用
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/getDePartMentSel", method = RequestMethod.POST)
//    @ResponseBody
//    public List<DePartMent> getGameInfoFromCache(HttpServletRequest request, Model resModel) {
//        logger.info("get department from cache");
//        List<DePartMent> listDePartMent = dePartMentCache.getDePartMentList();
//        return listDePartMent;
//    }
//
//}
