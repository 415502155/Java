package com.bestinfo.controller.game;

import com.bestinfo.bean.game.GameRiskRule;
import com.bestinfo.service.game.IGameRiskRuleService;
import com.bestinfo.util.StringUtil;
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
 * EB修改游戏-风险控制规则表controller
 *
 * @author hhhh
 */
@Controller
@RequestMapping(value = "/ebGameRiskRule")
public class EBGameRiskRuleController {

    private final Logger logger = Logger.getLogger(EBGameRiskRuleController.class);

    @Resource
    private IGameRiskRuleService gameRiskRuleService;

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modify(HttpServletRequest request, Model resModel) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            String riskRuleStr = ServletRequestUtils.getStringParameter(request, "RiskRuleStr", "");
            logger.info("riskRuleStr: " + riskRuleStr + "\n");
            if ("".equals(riskRuleStr)) {
                logger.error("EB send riskRuleStr param is null.");
                resMap.put("code", "-1");
                resMap.put("msg", "参数错误！");
                return resMap;
            }
            JSONArray riskRuleJson = JSONArray.fromObject(riskRuleStr);
            List<GameRiskRule> grrList = new ArrayList<GameRiskRule>();
            int riskRuleJsonSize = riskRuleJson.size();
            for (int i = 0; i < riskRuleJsonSize; i++) {
                JSONObject obj = riskRuleJson.getJSONObject(i);
                GameRiskRule grr = new GameRiskRule();
                grr.setGame_id((Integer) obj.get("game_id"));
                grr.setGroup_id((Integer) obj.get("group_id"));
                grr.setControl_type((Integer) obj.get("control_type"));
                grr.setIncrement_money(StringUtil.parseString(obj.get("increment_money").toString()));
                grr.setRation_prize(StringUtil.parseString(obj.get("ration_prize").toString()));
                grr.setIncrement_prize(StringUtil.parseString(obj.get("increment_prize").toString()));
                grrList.add(grr);
            }
            if (grrList.isEmpty()) {
                logger.error("the grrList is null");
                resMap.put("code", "-2");
                resMap.put("msg", "操作 失败！");
                return resMap;
            }

            int tag = gameRiskRuleService.modifyGameRiskRuleList(grrList);
            logger.info("modify gameRiskRule list and syn fs re:" + tag);
            if (tag < 0) {
                resMap.put("code", "-3");
                resMap.put("msg", "操作 失败！");
            } else {
                resMap.put("code", "0");
                resMap.put("msg", "操作 成功！");
            }
        } catch (Exception e) {
            logger.error("modify gameRiskRule info error: ", e);
            resMap.put("code", "-4");
            resMap.put("msg", "操作 失败！");
        }
        return resMap;
    }

}
