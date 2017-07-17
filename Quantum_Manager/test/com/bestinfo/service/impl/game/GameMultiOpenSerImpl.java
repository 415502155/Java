//package com.bestinfo.service.impl.game;
//
//import com.bestinfo.bean.game.GameMultiOpen;
//import com.bestinfo.dao.game.IGameMultiOpenDao;
//import com.bestinfo.ehcache.game.GameMultiOpenCache;
//import com.bestinfo.service.game.IGameMultiOpenSer;
//import java.util.List;
//import javax.annotation.Resource;
//import org.apache.log4j.Logger;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//
///**
// * 游戏多次开奖
// *
// * @author chenliping
// */
//@Service
//public class GameMultiOpenSerImpl implements IGameMultiOpenSer {
//
//    private final Logger logger = Logger.getLogger(GameMultiOpenSerImpl.class);
//    @Resource
//    private IGameMultiOpenDao gmultiOpen;
//    @Resource
//    private JdbcTemplate metaJdbcTemplate;
//    @Resource
//    private GameMultiOpenCache gameMultiOpenCache;
//
//    @Override
//    public int addGameMultiOpen(GameMultiOpen gmo) {
//        try {
//            //首先添加到数据库
//            int result = gmultiOpen.insertGameMultiOpen(metaJdbcTemplate, gmo);
//            if (result < 0) {
//                logger.error("insert gameMultiOpen to db error");
//                return -1;
//            }
//            //添加到缓存
//            int res = gameMultiOpenCache.insertGameMultiOpenToCache(gmo);
//            if (res < 0) {
//                logger.error("insert gameMultiOpen to cache error");
//                return -3;
//            }
//            return 0;
//        } catch (Exception e) {
//            logger.error("", e);
//            return -2;
//        }
//    }
//
//    @Override
//    public List<GameMultiOpen> getGameMultiOpenByGameid(JdbcTemplate jdbcTemplate, int gameid) {
//        return gmultiOpen.selectGameMultiOpenByGameid(jdbcTemplate, gameid);
//    }
//
//    /**
//     * 修改开奖次数信息
//     *
//     * @param gameMultiOpen
//     * @return
//     */
//    @Override
//    public int updateGameMultiOpen(GameMultiOpen gameMultiOpen) {
//        int result = gameMultiOpenCache.updateGameMultiOpenById(metaJdbcTemplate, gameMultiOpen);
//        return result;
//    }
//}
