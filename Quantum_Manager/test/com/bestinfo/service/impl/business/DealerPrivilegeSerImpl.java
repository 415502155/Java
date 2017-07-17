//package com.bestinfo.service.impl.business;
//
//import com.bestinfo.bean.business.DealerPrivilege;
//import com.bestinfo.dao.business.IDealerPrivilegeDao;
//import com.bestinfo.service.business.IDealerPrivilegeSer;
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
//public class DealerPrivilegeSerImpl implements IDealerPrivilegeSer {
//
//    private final Logger logger = Logger.getLogger(DealerPrivilegeSerImpl.class);
//
//    @Resource
//    private IDealerPrivilegeDao dealerPrivilegeDao;
//    
//    @Resource
//    private JdbcTemplate metaJdbcTemplate;
//
//    @Override
//    public int insertDealerPrivilege(List<DealerPrivilege> dpList) {
//        return dealerPrivilegeDao.insertDealerPrivilege(metaJdbcTemplate, dpList);
//    }
//
//}
