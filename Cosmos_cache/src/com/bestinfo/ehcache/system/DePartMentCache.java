//package com.bestinfo.ehcache.system;
//
//import com.bestinfo.bean.department.DePartMent;
//import com.bestinfo.cache.keys.GetCacheCommon;
//import com.bestinfo.ehcache.annotation.EhcacheInit;
//import com.bestinfo.cache.keys.CacheKeysUtil;
//import com.bestinfo.dao.system.IDepartmentDao;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import javax.annotation.Resource;
//import net.sf.ehcache.Cache;
//import net.sf.ehcache.Element;
//import org.apache.log4j.Logger;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
///**
// * 部门信息缓存操作类
// *
// * @author lvchangrong
// */
//@Repository
//@EhcacheInit(name = "DePartMentCache")
//public class DePartMentCache extends GetCacheCommon {
//
//    Logger logger = Logger.getLogger(DePartMentCache.class);
//
//    @Resource
//    private IDepartmentDao departmentDao;
//
//    /**
//     * 查询部门信息数据，departmentDao并全部放入缓存
//     *
//     * @return 成功（0）-- 无数据（-1）-- 未知错误（-2）-- 未配置缓存空间（-3）
//     */
//    @Override
//    public int init(JdbcTemplate jdbcTemplate) {
//        Cache quantumCache = getQuantumCache();
//        if (quantumCache == null) {
//            logger.error("no cache configuration");
//            return -3;
//        }
//        try {
//            List<DePartMent> ldp = departmentDao.getDepartmentList(jdbcTemplate);
//            if (ldp == null || ldp.isEmpty()) {
//                logger.error("there is no data in db");
//                return -1;
//            }
//
//            for (DePartMent dp : ldp) {
//                String key = CacheKeysUtil.getDePartMentKey(dp.getDepartment_id());
//                Element e = new Element(key, dp);
//                quantumCache.put(e);
//            }
//            logger.info("放入缓存的数据条数：" + ldp.size());
//            return 0;
//        } catch (Exception e) {
//            logger.error("", e);
//            return -2;
//        }
//    }
//
//    /**
//     * 根据Key查询对应的缓存数据对象
//     *
//     * @param departmentId 部门编号
//     * @return 部门信息
//     */
//    public DePartMent getDePartMentById(int departmentId) {
//        Cache quantumCache = getQuantumCache();
//        String key = CacheKeysUtil.getDePartMentKey(departmentId);
//        if (quantumCache == null) {
//            logger.error("no cache configuration");
//            return null;
//        }
//        try {
//            Element e = quantumCache.get(key);
//            if (e == null) {
//                logger.error("get department from ehcache error");
//                return null;
//            }
//            return (DePartMent) e.getObjectValue();
//        } catch (Exception e) {
//            logger.error("", e);
//            return null;
//        }
//    }
//
//    /**
//     * 获取全部部门列表信息
//     *
//     * @return
//     */
//    public List<DePartMent> getDePartMentList() {
//        try {
//            Cache quantumCache = getQuantumCache();
//            if (quantumCache == null) {
//                logger.error("no cache configuration");
//                 return Collections.emptyList();
//            }
//            List<DePartMent> ldp = new ArrayList<DePartMent>();
//            List listAllkeys = quantumCache.getKeys();
//              if(listAllkeys==null || listAllkeys.isEmpty()){
//                logger.error("the allKeysList is null");
//                return Collections.emptyList();
//            }
//            for (Object objKey : listAllkeys) {
//                int skey = ((String) objKey).indexOf(CacheKeysUtil.departmentKey);
//                if (skey > -1) {
//                    Element e = quantumCache.get(objKey);
//                    if (e == null) {
//                        logger.error("get department from ehcache error");
//                         return Collections.emptyList();
//                    }
//                    DePartMent dpt = (DePartMent) e.getObjectValue();
//                    ldp.add(dpt);
//                }
//            }
//            return ldp;
//        } catch (Exception e) {
//            logger.error("", e);
//             return Collections.emptyList();
//        }
//    }
//
//}
