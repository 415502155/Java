//package com.bestinfo.service.impl.business;
//
//import com.bestinfo.bean.business.TmnPrivilege;
//import com.bestinfo.dao.business.ITmnPrivilegeDao;
//import com.bestinfo.service.business.ITmnPrivilegeSer;
//import java.util.List;
//import javax.annotation.Resource;
//import org.apache.log4j.Logger;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//
///**
// *
// * @author hhhh
// */
//@Service
//public class TmnPrivilegeSerImpl implements ITmnPrivilegeSer {
//
//    private final Logger logger = Logger.getLogger(TmnPrivilegeSerImpl.class);
//
//    @Resource
//    private ITmnPrivilegeDao tmnPrivilegeDao;
//    
//    @Resource
//    private JdbcTemplate metaJdbcTemplate;
//
//    @Override
//    public int insertTmnPrivilege(List<TmnPrivilege> tpList) {
//        return tmnPrivilegeDao.insertTmnPrivilege(metaJdbcTemplate, tpList);
//    }
//
//}
