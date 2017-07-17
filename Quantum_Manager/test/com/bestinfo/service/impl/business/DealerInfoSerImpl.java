//package com.bestinfo.service.impl.business;
//
//import com.bestinfo.bean.business.DealerInfo;
//import com.bestinfo.bean.business.DealerPrivilege;
//import com.bestinfo.dao.business.IDealerInfoDao;
//import com.bestinfo.dao.business.IDealerPrivilegeDao;
//import com.bestinfo.ehcache.business.DealerInfoCache;
//import com.bestinfo.ehcache.business.DealerPrivilegeCache;
//import com.bestinfo.service.business.IDealerInfoSer;
//import java.util.List;
//import javax.annotation.Resource;
//import org.apache.log4j.Logger;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//
///**
// *
// * @author chenliping
// */
//@Service
//public class DealerInfoSerImpl implements IDealerInfoSer {
//
//    private final Logger logger = Logger.getLogger(DealerInfoSerImpl.class);
//
//    @Resource
//    private DealerInfoCache dealerInfoCache;
//
//    @Resource
//    private DealerPrivilegeCache dealerPrivilegeCache;
//
//    @Resource
//    private IDealerInfoDao idealer;
//
//    @Resource
//    private JdbcTemplate metaJdbcTemplate;
//
//    @Resource
//    private IDealerPrivilegeDao dealerPrivilegeDao;
//
//    @Override
//    public int addDealerInfo(DealerInfo dealer, List<DealerPrivilege> dpList) {
//        DealerInfo di = idealer.getDealerInfoById(metaJdbcTemplate, dealer.getDealer_id());
//        if (di != null) {
//            logger.error("dealer info where dealer id = " + dealer.getDealer_id() + " has already exist");
//            return -5;
//        }
//        int re = idealer.insertDealerInfo(metaJdbcTemplate, dealer);
//        if (re < 0) {
//            logger.error("insert dealer info into DB error");
//            return -1;
//        }
//        int re1 = dealerPrivilegeDao.insertDealerPrivilege(metaJdbcTemplate, dpList);
//        if (re1 < 0) {
//            logger.error("insert dealer privilege into DB error");
//            return -2;
//        }
//        int re2 = dealerInfoCache.addDealerInfoToCache(dealer);
//        if (re2 < 0) {
//            logger.info("insert dealer info into cache failed");
//            return -3;
//        }
//        int re3 = dealerPrivilegeCache.addDealerPrivilegeToCache(dpList);
//        if (re3 < 0) {
//            logger.info("insert dealer privilege into cache failed");
//            return -4;
//        }
//        return 0;
//    }
//
//    /**
//     * 代销商修改
//     *
//     * @param dealer 代销商信息
//     * @param dpList 代销商游戏特权
//     * @return -1代销商修改数据库或缓存失败 -2特权数据库修改失败 -3特权修改缓存失败 -4未知错误 0成功
//     */
//    @Override
//    public int updateDealerInfo(DealerInfo dealer, List<DealerPrivilege> dpList) {
//        try {
//            //更新数据库及缓存里的代销商信息
//            int re = dealerInfoCache.updateDealerInfoById(metaJdbcTemplate, dealer);
//            if (re < 0) {
//                logger.error("update dealer info into DB or cache error");
//                return -1;
//            }
//            //更新数据库里的该代销商的游戏特权的信息（先删除后插入）
//            int re1 = dealerPrivilegeDao.deleteDealerPrivilegeByDealerId(metaJdbcTemplate, dealer.getDealer_id());
//            if (re1 < 0) {
//                logger.error("delete dealer privilege from DB where dealerId = " + dealer.getDealer_id() + " error");
//                return -2;
//            } else {
//                int re2 = dealerPrivilegeDao.insertDealerPrivilege(metaJdbcTemplate, dpList);
//                if (re2 < 0) {
//                    logger.info("insert dealer privilege into db failed");
//                    return -2;
//                }
//            }
//            //更新代销商的游戏特权的缓存（先删后放）
//            int re3 = dealerPrivilegeCache.deleteDealerPrivilegeCacheByDealerId(dealer.getDealer_id());
//            if (re3 < 0) {
//                logger.info("delete dealer privilege from cache where dealerId = " + dealer.getDealer_id() + " failed");
//                return -3;
//            } else {
//                int re4 = dealerPrivilegeCache.addDealerPrivilegeToCache(dpList);
//                if (re4 < 0) {
//                    logger.info("insert dealer privilege into cache failed");
//                    return -3;
//                }
//            }
//            return 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("", e);
//            return -4;
//        }
//    }
//
//}
