package bestinfo.controller.test;

import com.bestinfo.bean.sysUser.AddressList;
import com.bestinfo.ehcache.system.ApplicationUrlCache;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author YangRong
 */
@Controller
public class EhcacheTest {

    private final Logger logger = Logger.getLogger(EhcacheTest.class);
    @Resource
    private ApplicationUrlCache applicationUrlCache;
    
    @Resource
    private JdbcTemplate metaJdbcTemplate; //基础库
    
//    @Resource
//    private GameInfoCache gameInfoCache;
//    @Resource
//    private TmnInfoRedisCache tmnInfo;
    
    @RequestMapping(value = "/getEhcache")
    public void Test1() {        
        List<AddressList> lal = applicationUrlCache.getAddressListByProvinceId(31);
        if (lal == null || lal.isEmpty()) {
            logger.error("ehcache error");
            return;
        }
        for (AddressList al : lal) {
            logger.info("address list: id=" + al.getApp_id() + " app_name=" + al.getApp_name() + " app_url=" + al.getApp_url() + " app_type=" + al.getApp_type() + " center_type=" + al.getCenter_type());
        }
    }

    @RequestMapping(value = "/testEhcache")
    public void Test() {
        if (applicationUrlCache.init(metaJdbcTemplate) != 0) {
           return;
        }
        List<AddressList> lal = applicationUrlCache.getAddressListByProvinceId(31);
        if (lal == null || lal.isEmpty()) {
            logger.error("ehcache error");
            return;
        }
        for (AddressList al : lal) {
            logger.info("address list: id=" + al.getApp_id() + " app_name=" + al.getApp_name() + " app_url=" + al.getApp_url() + " app_type=" + al.getApp_type() + " center_type=" + al.getCenter_type());
        }
    }

    @RequestMapping(value = "/testModifyEhcache")
    public void Test2() {
        List<AddressList> lal = applicationUrlCache.getAddressListByProvinceId(31);
        if (lal == null || lal.isEmpty()) {
            logger.info("address list is empty");
            return;
        }
        lal.get(0).setApp_type(123);
        int appre = applicationUrlCache.addObject(lal);
        if(appre < 0){
            logger.error("add error");
            return;
        }
        List<AddressList> lalr = applicationUrlCache.getAddressListByProvinceId(31);
        if (lalr == null || lalr.isEmpty()) {
            logger.error("ehcache error");
            return;
        }
        for (AddressList al : lalr) {
            logger.info("address list: id=" + al.getApp_id() + " app_name=" + al.getApp_name() + " app_url=" + al.getApp_url() + " app_type=" + al.getApp_type() + " center_type=" + al.getCenter_type());
        }
    }

}
