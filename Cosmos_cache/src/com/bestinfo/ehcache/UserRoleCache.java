//package com.bestinfo.ehcache;
//
//import com.bestinfo.bean.sysUser.UserRole;
//import com.bestinfo.cache.keys.GetCacheCommon;
//import com.bestinfo.ehcache.annotation.EhcacheInit;
//import com.bestinfo.cache.keys.CacheKeysUtil;
//import com.bestinfo.dao.sysUser.IUserRoleDao;
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
// * 角色信息缓存操作类
// *
// * @author lvchangrong
// */
//@Repository
//@EhcacheInit(name = "UserRoleCache")
//public class UserRoleCache extends GetCacheCommon {
//
//    Logger logger = Logger.getLogger(UserRoleCache.class);
//
//    @Resource
//    private IUserRoleDao userRoleDao;
//
//    /**
//     * 查询角色信息数据，userRoleDao并全部放入缓存
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
//            List<UserRole> lur = userRoleDao.getUserRoleList(jdbcTemplate);
//            if (lur == null || lur.isEmpty()) {
//                logger.error("there is no data in db");
//                return -1;
//            }
//
//            for (UserRole ur : lur) {
//                String key = CacheKeysUtil.getUserRoleKey(ur.getRole_id());
//                Element e = new Element(key, ur);
//                quantumCache.put(e);
//            }
//            logger.info("放入缓存的数据条数：" + lur.size());
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
//     * @param roleId 角色ID
//     * @return 角色信息
//     */
//    public UserRole getUserRoleById(int roleId) {
//        Cache quantumCache = getQuantumCache();
//        String key = CacheKeysUtil.getUserRoleKey(roleId);
//        if (quantumCache == null) {
//            logger.error("no cache configuration");
//            return null;
//        }
//        try {
//            Element e = quantumCache.get(key);
//            if (e == null) {
//                logger.error("get userrol from ehcache error");
//                return null;
//            }
//            return (UserRole) e.getObjectValue();
//        } catch (Exception e) {
//            logger.error("", e);
//            return null;
//        }
//    }
//
//    /**
//     * 获取全部角色信息列表
//     *
//     * @return
//     */
//    public List<UserRole> getUserRoleList() {
//        try {
//            Cache quantumCache = getQuantumCache();
//            if (quantumCache == null) {
//                logger.error("no cache configuration");
//                return Collections.emptyList();
//            }
//            List<UserRole> lur = new ArrayList<UserRole>();
//            List listAllkeys = quantumCache.getKeys();
//            if(listAllkeys==null || listAllkeys.isEmpty()){
//                logger.error("the allKeysList is null");
//                return Collections.emptyList();
//            }
//            for (Object objKey : listAllkeys) {
//                int skey = ((String) objKey).indexOf(CacheKeysUtil.userroleKey);
//                if (skey > -1) {
//                    Element e = quantumCache.get(objKey);
//                    if (e == null) {
//                        logger.error("get userrole list from ehcache error");
//                        return Collections.emptyList();
//                    }
//                    UserRole ur = (UserRole) e.getObjectValue();
//                    lur.add(ur);
//                }
//            }
//            return lur;
//        } catch (Exception e) {
//            logger.error("", e);
//            return Collections.emptyList();
//        }
//    }
//
//}
