package com.bestinfo.controller.autotmn;

import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.dao.business.ITmnInfoDao;
import com.bestinfo.redis.business.TmnInfoRedisCache;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.io.IOUtils;

/**
 * 更新终端公钥
 */
@Controller
@RequestMapping(value = "/update")
public class UpdateTerminalPublicKeyCtr {

    private final Logger logger = Logger.getLogger(UpdateTerminalPublicKeyCtr.class);

    @Resource
    private JdbcTemplate gamblerTemplate;//gamblerTemplate
    @Resource
    private ITmnInfoDao iTmnInfoDao;
    @Resource
    private TmnInfoRedisCache tmnInfoRedisCache;

    /**
     * *
     * 根据终端ID来更新PublicKey
     *
     * @param request
     * @param response
     * @return
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/publickey")
    @ResponseBody
    public int updatePublicKey(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String pkt = IOUtils.toString(inputStream);
        logger.info("receive  message:" + pkt);
        int code = 10;
        String terminalId = "31970184";
        String publickey = pkt;
        int id = Integer.parseInt(terminalId);
        TmnInfo tmnInfo = iTmnInfoDao.getTmnInfoByTerminalId(gamblerTemplate, id);
        if (tmnInfo == null) {
            logger.info("get TmnInfo By TerminalId is null");
            code = -1;
            return code;
        }
        try {
            code = iTmnInfoDao.updateTmnInfoById(gamblerTemplate, id, pkt);
            logger.info("update tmn key code:" + code);
            tmnInfo.setPublic_key(publickey);
            //添加到Redis
            int re1 = tmnInfoRedisCache.addTmnInfoIntoCache(tmnInfo);
            if (re1 < 0) {
                logger.error("insert tmn info into Redis error.");
                return -6;
            }
        } catch (Exception e) {
            logger.error("terminalId:" + terminalId + ",publicKey" + publickey, e);
            code = -2;
        }

        return code;
    }
}
