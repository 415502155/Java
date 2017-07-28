package com.bestinfo.controller.terminal;

import com.bestinfo.define.terminal.TmnGameStop;
import com.bestinfo.define.terminal.TmnPrivelegeInfo;
import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.bean.business.TmnPrivilege;
import com.bestinfo.service.terminal.ITmnInfoSer;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 投注机管理
 *
 * @author hhhh
 */
@Controller
@RequestMapping(value = "/ebtmninfo")
public class EBTmnInfoCtr {

    private final Logger logger = Logger.getLogger(EBTmnInfoCtr.class);

    @Resource
    private ITmnInfoSer tinfo;

    /**
     * 终端信息登记--旧版
     *
     * @param request
     * @param tin
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addTmnInfo(HttpServletRequest request, TmnInfo tin) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int re = tinfo.addTmn(tin);
        if (re == 0) {
            resMap.put("code", 0);
            resMap.put("msg", "新机登记 成功！");
        } else if (re == -2) {
            resMap.put("code", -1);
            resMap.put("msg", "该终端编号已经存在！");
        } else {
            resMap.put("code", -2);
            resMap.put("msg", "新机登记 失败！");
        }
        return resMap;
    }

    /**
     * 新版终端信息登记
     *
     * @param request
     * @param tin
     * @return
     */
    @RequestMapping(value = "/newAdd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> newAddTmnInfo(HttpServletRequest request, TmnInfo tin) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int stationRank = tin.getStation_rank();
        if (stationRank == 0) {
            resMap.put("code", -1);
            resMap.put("msg", "站点等级为空！");
            return resMap;
        }

        int re = tinfo.newAddTmn(tin);
        if (re == 0) {
            resMap.put("code", 0);
            resMap.put("msg", "新机登记 成功！");
        } else if (re == -1) {
            resMap.put("code", -2);
            resMap.put("msg", "该终端编号已经存在！");
        } else {
            resMap.put("code", -3);
            resMap.put("msg", "新机登记 失败！");
        }
        return resMap;
    }

    /**
     * 修改详细信息
     *
     * @param request
     * @param resModel
     * @param tin
     * @return
     */
    @RequestMapping(value = "/modifyDetai", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyDetai(HttpServletRequest request, Model resModel, TmnInfo tin) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int stationRank = tin.getStation_rank();
        if (stationRank == 0) {
            resMap.put("code", -1);
            resMap.put("msg", "站点等级为空！");
            return resMap;
        }

        int re = tinfo.modifyDetai(tin);
        if (re < 0) {
            resMap.put("code", -2);
            resMap.put("msg", "保存 失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "保存 成功！");
        }
        return resMap;
    }

    /**
     * 修改终端详细信息--新版
     *
     * @param request
     * @param resModel
     * @param tin
     * @return
     */
    @RequestMapping(value = "/newModifyDetai", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> newModifyDetai(HttpServletRequest request, Model resModel, TmnInfo tin) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int stationRank = tin.getStation_rank();
        if (stationRank == 0) {
            resMap.put("code", -1);
            resMap.put("msg", "站点等级为空！");
            return resMap;
        }

        int re = tinfo.newModifyDetai(tin);
        if (re < 0) {
            resMap.put("code", -2);
            resMap.put("msg", "保存 失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "保存 成功！");
        }
        return resMap;
    }

    /**
     * 修改通讯参数
     *
     * @param request
     * @param resModel
     * @param tin
     * @return
     */
    @RequestMapping(value = "/commParaModify", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> commParaModify(HttpServletRequest request, Model resModel, TmnInfo tin) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int re = tinfo.commParaModify(tin);
        if (re < 0) {
            resMap.put("code", -1);
            resMap.put("msg", "保存 失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "保存 成功！");
        }
        return resMap;
    }

    /**
     * 账户绑定
     *
     * @param request
     * @param resModel
     * @param tin
     * @return
     */
    @RequestMapping(value = "/bindAccount", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> bindAccount(HttpServletRequest request, Model resModel, TmnInfo tin) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int re = tinfo.bindAccount(tin);
        if (re < 0) {
            resMap.put("code", -2);
            resMap.put("msg", "保存 失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "保存 成功！");
        }

        return resMap;
    }

    @RequestMapping(value = "/modifyTmnPrivilege", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyTmnPrivilege(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            String tmnPrivilegeStr = request.getParameter("tmnPrivilegeStr");
            logger.info("tmnPrivilegeStr: " + tmnPrivilegeStr + "\n");
            String terminalId = request.getParameter("terminalId");
            logger.info("terminalId: " + terminalId + "\n");
            JSONArray tmnPrivilegeJson = JSONArray.fromObject(tmnPrivilegeStr);
            List<TmnPrivilege> tpList = new ArrayList<TmnPrivilege>();
            int tmnPrivilegeJsonSize = tmnPrivilegeJson.size();
            for (int i = 0; i < tmnPrivilegeJsonSize; i++) {
                JSONObject obj = tmnPrivilegeJson.getJSONObject(i);
                TmnPrivilege tp = new TmnPrivilege();
                tp.setTerminal_id((Integer) obj.get("terminalId"));
                tp.setGame_id((Integer) obj.get("game_id"));
                tp.setCur_draw_id(0);//当前期号暂时设置为0
                tp.setSale_permit((Integer) obj.get("sale_permit"));
                tp.setCash_permit((Integer) obj.get("cash_permit"));
                tp.setGame_permit((Integer) obj.get("game_permit"));
                tp.setUndo_permit((Integer) obj.get("undo_permit"));
                tp.setPresell_permit((Integer) obj.get("presell_permit"));
                tp.setGame_stop(TmnGameStop.gamestopno);//默认开机
                tp.setAgent_fee_rate(BigDecimal.valueOf(Double.parseDouble(obj.get("agent_fee_rate").toString())));
                tp.setMin_bet_money(BigDecimal.valueOf(Double.parseDouble(obj.get("min_bet_money").toString())));
                tp.setMax_bet_money(BigDecimal.valueOf(Double.parseDouble(obj.get("max_bet_money").toString())));
                tp.setMax_sales_money(TmnPrivelegeInfo.max_sales_money);
                tp.setCash_fee_rate(BigDecimal.valueOf(Double.parseDouble(obj.get("cash_fee_rate").toString())));
                tpList.add(tp);
            }
            if (tpList.isEmpty()) {
                logger.error("the TmnPrivilegeList is null");
                resMap.put("code", "-1");
                resMap.put("msg", "操作 失败！");
            }
            int re = tinfo.modifyTmnPrivilege(tpList, Integer.parseInt(terminalId));
            logger.info("update tmn privilege list from DB and cache re:" + re);
            if (re < 0) {
                resMap.put("code", "-1");
                resMap.put("msg", "操作 失败！");
            } else {
                resMap.put("code", "0");
                resMap.put("msg", "操作 成功！");
            }
        } catch (Exception e) {
            logger.error("modifyTmnPrivilege Ctr occur exception.", e);
            resMap.put("code", "-1");
            resMap.put("msg", "操作 失败！");
        }
        return resMap;
    }

    /**
     * 新机初始化
     *
     * @param request
     * @param resModel
     * @param tin
     * @return
     */
    @RequestMapping(value = "/newTmnInit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> newTmnInit(HttpServletRequest request, Model resModel, TmnInfo tin) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        int re = tinfo.newTmnInit(tin);
        if (re < 0) {
            logger.error("new tmn init error.");
            resMap.put("code", -1);
            resMap.put("msg", "新机初始化 失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "新机初始化 成功！");
        }
        return resMap;
    }

    /**
     * 根据起始终端号删除对应的Redis锁
     *
     * @param request
     * @param resModel
     * @return
     */
    @RequestMapping(value = "/delRedisLock", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delRedisLock(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        String begin_terminal_id = ServletRequestUtils.getStringParameter(request, "begin_terminal_id", "");
        String end_terminal_id = ServletRequestUtils.getStringParameter(request, "end_terminal_id", "");
        if ("".equals(begin_terminal_id) || "".equals(end_terminal_id)) {
            logger.error("EB send begin_terminal_id or end_terminal_id is null where begin_terminal_id=" + begin_terminal_id + ", end_terminal_id=" + end_terminal_id);
            resMap.put("code", -1);
            resMap.put("msg", "起始或截止终端编号为空！");
            return resMap;
        }

        int re = tinfo.delRedisLock(begin_terminal_id, end_terminal_id);
        if (re == -3) {
            logger.error("there is no tmn info where begin_terminal_id = " + begin_terminal_id + ", end_terminal_id=" + end_terminal_id);
            resMap.put("code", -2);
            resMap.put("msg", "没有终端信息！");
        } else if (re < 0) {
            logger.error("delete Tmn Redis Lock error where begin_terminal_id = " + begin_terminal_id + ", end_terminal_id=" + end_terminal_id);
            resMap.put("code", -3);
            resMap.put("msg", "清除Redis锁失败！");
        } else {
            resMap.put("code", 0);
            resMap.put("msg", "清除Redis锁成功！");
        }
        return resMap;
    }

}
