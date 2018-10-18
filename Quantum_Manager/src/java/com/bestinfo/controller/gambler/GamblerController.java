package com.bestinfo.controller.gambler;

import com.bestinfo.arithmetic.MD5;
import com.bestinfo.bean.business.DealerPrivilege;
import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.redis.business.TmnSerialNoCache;
import com.bestinfo.service.gambler.IGamblerService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 电话投注controller
 */
@Controller
@RequestMapping(value = "/gambler")
public class GamblerController {

    private static final Logger logger = Logger.getLogger(GamblerController.class);

    @Resource
    private IGamblerService gameblerService;

    @Resource
    private TmnSerialNoCache tmnSerialNoRedis;

    @Resource
    private JdbcTemplate gamblerTemplate; //彩民库

    /**
     * 修改代理商特权
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/dealerPrivilege")
    @ResponseBody
    public Map<String, Object> modifyDealerPrivilege(HttpServletRequest request) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        logger.info("gambler update dealer privilege");
        try {
            int dealerId = ServletRequestUtils.getIntParameter(request, "dealer_id", 0);
            String privilegeStr = ServletRequestUtils.getStringParameter(request, "privilegeStr", "");
            if ("".equals(privilegeStr) || 0 == dealerId) {
                logger.error("eb data error,dealer_id:" + dealerId + ",privilegeStr:" + privilegeStr);
                resMap.put("code", "-1");
                resMap.put("msg", "eb数据错误！");
                return resMap;
            }
            List<DealerPrivilege> privilegeList = new ArrayList<DealerPrivilege>();
            JSONArray tmnPrivilegeJson = JSONArray.fromObject(privilegeStr);
            for (int i = 0; i < tmnPrivilegeJson.size(); i++) {
                JSONObject obj = tmnPrivilegeJson.getJSONObject(i);
                DealerPrivilege dealerPrivilege = new DealerPrivilege();
                dealerPrivilege.setGame_id((Integer) obj.get("game_id"));
                dealerPrivilege.setService_proportion(new BigDecimal(obj.get("service_proportion").toString()));
                dealerPrivilege.setGame_permit((Integer) obj.get("game_permit"));
                privilegeList.add(dealerPrivilege);
            }
            if (privilegeList.isEmpty()) {
                logger.error("dealer privilege list is null");
                resMap.put("code", "-1");
                resMap.put("msg", "eb数据错误！");
                return resMap;
            }
            int re = gameblerService.updateDealerPrivilege(dealerId, privilegeList);
            if (re < 0) {
                resMap.put("code", "-2");
                resMap.put("msg", "更新代理商游戏特权失败！");
            } else {
                resMap.put("code", "0");
                resMap.put("msg", "更新代理商游戏特权成功！");
            }
        } catch (Exception e) {
            logger.error("modify dealer privilege controller occur exception.", e);
            resMap.put("code", "-2");
            resMap.put("msg", "更新代理商游戏特权失败！");
        }

        return resMap;
    }

    /**
     * 密码加密
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/encryptPwd")
    @ResponseBody
    public Map<String, Object> encryptPwd(HttpServletRequest request) {
        logger.info("gambler encrypt pwd");
        Map<String, Object> resMap = new HashMap<String, Object>();
        String pwd = ServletRequestUtils.getStringParameter(request, "pwd", "");
        if ("".equals(pwd)) {
            logger.error("eb data error,pwd:" + pwd);
            resMap.put("code", "-1");
            resMap.put("msg", "eb数据错误！");
            return resMap;
        }
        try {
            String encryptPwd = new MD5().digest(pwd, "MD5");
            logger.info("pwd:" + pwd + ",encryptPwd:" + encryptPwd);
            if ("".equals(encryptPwd)) {
                resMap.put("code", "-2");
                resMap.put("msg", "加密错误！");
                return resMap;
            }

            resMap.put("code", "0");
            resMap.put("msg", encryptPwd);
            return resMap;
        } catch (Exception ex) {
            logger.error("gambler encrypt pwd exception:" + ex);
            resMap.put("code", "-2");
            resMap.put("msg", "加密错误！");
            return resMap;
        }
    }

    /**
     * 注册电话终端
     *
     * @param request
     * @param tmnInfo
     * @return
     */
    @RequestMapping(value = "/tmnRegist")
    @ResponseBody
    public Map<String, Object> registTmn4Gamb(HttpServletRequest request, TmnInfo tmnInfo) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        String agentFeeStr = ServletRequestUtils.getStringParameter(request, "agent_fee_rate", "");
        if ("".equals(agentFeeStr)) {
            resMap.put("code", -1);
            resMap.put("msg", "eb数据错误！");
            return resMap;
        }
        int re = gameblerService.registTmn4Gamb(tmnInfo, new BigDecimal(agentFeeStr));
        if (re == 0) {
            resMap.put("code", 0);
            resMap.put("msg", "投注机注册成功！");
        } else if (re == -1) {
            resMap.put("code", -1);
            resMap.put("msg", "该投注机注册编号已经存在！");
        } else if (re == -2) {
            resMap.put("code", -1);
            resMap.put("msg", "该投注机物理编号已经存在！");
        } else {
            resMap.put("code", -2);
            resMap.put("msg", "投注机注册失败！");
        }
        return resMap;
    }

    /**
     * 手动同步电话投注终端流水号，先删除再插入
     *
     * @param request
     * @return
     */
    //http://192.168.26.221:8751/manager/gambler/syncGambSerialNo?game_id=1&draw_id=105
    @RequestMapping(value = "/syncGambSerialNo")
    @ResponseBody
    public Map<String, Object> syncGambSerialNo(HttpServletRequest request) {
        logger.info("sync gamb serial no");
        Map<String, Object> resMap = new HashMap<String, Object>();
        int gameid = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        int drawid = ServletRequestUtils.getIntParameter(request, "draw_id", 0);
        int re = tmnSerialNoRedis.clearGambSerialNo(gamblerTemplate, gameid, drawid);
        if (re < 0) {
            logger.error("clear gamb serial no fail.");
            resMap.put("code", -1);
            resMap.put("msg", "清除电话投注终端流水号失败！");
        }
        re = tmnSerialNoRedis.syncGambSerialNo(gamblerTemplate, gameid, drawid);
        if (re < 0) {
            logger.error("sync gamb serial no fail.");
            resMap.put("code", -1);
            resMap.put("msg", "同步电话投注终端流水号失败！");
        } else {
            logger.info("sync gamb serial no success.");
            resMap.put("code", 0);
            resMap.put("msg", "同步电话投注终端流水号成功！");
        }
        return resMap;
    }
}
