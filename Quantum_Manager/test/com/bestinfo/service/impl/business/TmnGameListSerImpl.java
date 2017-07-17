//package com.bestinfo.service.impl.business;
//
//import com.bestinfo.bean.business.TmnGameList;
//import com.bestinfo.dao.business.ITmnGameListDao;
//import com.bestinfo.service.business.ITmnGameListSer;
//import java.util.List;
//import javax.annotation.Resource;
//import org.apache.log4j.Logger;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//
///**
// * 投注机支持游戏表
// *
// * @author hhhh
// */
//@Service
//public class TmnGameListSerImpl implements ITmnGameListSer {
//
//    private final Logger logger = Logger.getLogger(TmnGameListSerImpl.class);
//
//    @Resource
//    private ITmnGameListDao tmnGameListDao;
//
//    @Resource
//    private JdbcTemplate metaJdbcTemplate;
//
//    @Override
//    public List<TmnGameList> getTmnGameList() {
//        return tmnGameListDao.getTmnGameList(metaJdbcTemplate);
//    }
//   
//}
