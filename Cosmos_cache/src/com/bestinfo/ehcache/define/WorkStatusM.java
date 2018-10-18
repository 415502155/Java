//package com.bestinfo.ehcache.define;
//
//import bestinfo.system.WorkStatusDefine;
//import com.bestinfo.bean.encoding.WorkStatus;
//import com.bestinfo.dao.encoding.IWorkStatus;
//import com.bestinfo.cache.keys.GetCacheCommon;
//import com.bestinfo.ehcache.annotation.EhcacheInit;
//import java.util.List;
//import javax.annotation.Resource;
//import org.apache.log4j.Logger;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
///**
// * 工作状态
// *
// * @author chenliping
// */
//@Repository
//@EhcacheInit(name = "WorkStatusM")
//public class WorkStatusM extends GetCacheCommon {
//
//    Logger logger = Logger.getLogger(WorkStatusM.class);
//
//    @Resource
//    private IWorkStatus wosta;
//
//    /**
//     * 初始化工作状态
//     *
//     * @param jdbcTemplate
//     * @return
//     */
//    @Override
//    public int init(JdbcTemplate jdbcTemplate) {
//        List<WorkStatus> lws = wosta.getAllWorkStatus(jdbcTemplate);
//        if (lws == null || lws.isEmpty()) {
//            logger.error("can't get work status from db");
//            return -1;
//        }
//        for (WorkStatus ws : lws) {
//            if (ws.getWork_status_name().equals("启用")) {
//                WorkStatusDefine.work = ws.getWork_status();
//            } else {
//                WorkStatusDefine.notwork = ws.getWork_status();
//            }
//        }
//        logger.info("workstatus:" + WorkStatusDefine.work + "\tworkstatus:" + WorkStatusDefine.notwork);
//        return 0;
//    }
//
//}
