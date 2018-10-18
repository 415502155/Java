//package com.bestinfo.controller.game;
//
//import com.bestinfo.controller.BasicController;
//import com.bestinfo.bean.game.GameRaffleRule;
//import com.bestinfo.ehcache.game.GameClassInfoCache;
//import com.bestinfo.ehcache.game.GameInfoCache;
//import com.bestinfo.ehcache.game.GamePlayInfoCache;
//import com.bestinfo.service.game.IGameLuckyRuleSer;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.ServletRequestUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * 游戏抽奖规则
// *
// * @author chenliping
// */
//@Controller
//@RequestMapping(value = "/gameluckyRule")
//public class GameLuckyRuleCtr extends BasicController {
//
//    private final Logger logger = Logger.getLogger(GameLuckyRuleCtr.class);
//
//    @Resource
//    private IGameLuckyRuleSer gamelucky;
//    @Resource
//    private GameInfoCache gameInfoCache;
//    @Resource
//    private GamePlayInfoCache gamePlayInfoCache;
//    @Resource
//    private GameClassInfoCache gameClassInfoCache;
//
//    /**
//     * 跳转到添加页面
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/2add")
//    public String toadd(HttpServletRequest request, Model resModel) {
//        return "/game/gameLuckyRuleAdd";
//    }
//
//    /**
//     * 添加抽奖规则
//     *
//     * @param request
//     * @param gameRaffleRule
//     * @return
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> addGameLuckyRule(HttpServletRequest request, GameRaffleRule gameRaffleRule) {
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        int re = gamelucky.addGameRaffleRule(gameRaffleRule);
//        if (re < 0) {
//            resMap.put("result", "error");
//            resMap.put("msg", "添加失败");
//        } else {
//            resMap.put("result", "success");
//            resMap.put("msg", "添加成功");
//        }
//        return resMap;
//    }
//
//    /**
//     * 跳转到查询页面
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/2select", method = RequestMethod.GET)
//    public String toSelect(HttpServletRequest request, Model resModel) {
//        return "/game/gameLuckyRuleList";
//    }
//
//    /**
//     * 获取抽奖规则数据
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public String getGameLuckyRule(HttpServletRequest request, Model resModel) {
//        logger.info("get gameinfo by game_id");
//        int gameid = ServletRequestUtils.getIntParameter(request, "gameid", 0);
//        List<GameRaffleRule> lgrff = gamelucky.getGameRaffleRuleByGameid(gameid);
//        if (lgrff == null) {
//            logger.error("can't get raffle rule from cache");
//            return "/game/gameLuckyRuleList";
//        }
//        Collections.sort(lgrff, new Comparator() {
//            @Override
//            public int compare(Object o1, Object o2) {
//                GameRaffleRule arg0 = (GameRaffleRule) o1;
//                GameRaffleRule arg1 = (GameRaffleRule) o2;
//                return arg0.getClass_id().compareTo(arg1.getClass_id());
//            }
//        });
//        for (GameRaffleRule gameRaffleRule : lgrff) {
//            String game_name = gameInfoCache.getGameInfoByid(gameRaffleRule.getGame_id()).getGame_name();
//            String play_name = gamePlayInfoCache.getGamePlayInfoByid(gameRaffleRule.getGame_id(), gameRaffleRule.getPlay_id()).getPlay_name();
//            String class_name = gameClassInfoCache.getGameClassInfoByid(gameRaffleRule.getGame_id(), gameRaffleRule.getPlay_id(), gameRaffleRule.getClass_id()).getClass_name();
//            gameRaffleRule.setGame_name(game_name);
//            gameRaffleRule.setPlay_name(play_name);
//            gameRaffleRule.setClass_name(class_name);
//        }
//        resModel.addAttribute("gameluckylist", lgrff);
//        return "/game/gameLuckyRuleList";
//    }
//
//    /**
//     * 跳转到编辑页面
//     *
//     * @param request
//     * @param resModel
//     * @return
//     */
//    @RequestMapping(value = "/2modify", method = RequestMethod.GET)
//    public String toModify(HttpServletRequest request, Model resModel) {
//        logger.info("go to modify");
//        int gameid = ServletRequestUtils.getIntParameter(request, "game_id", 0);
//        int playid = ServletRequestUtils.getIntParameter(request, "play_id", 0);
//        int ruleid = ServletRequestUtils.getIntParameter(request, "rule_id", 0);
//        GameRaffleRule gameRaffleRule = gamelucky.getGameRaffleRuleByRuleId(gameid, playid, ruleid);
//        //取得游戏编号对应的游戏名称
//        String game_name; //游戏名称
//        String play_name;  //玩法名称
//        game_name = gameInfoCache.getGameInfoByid(gameRaffleRule.getGame_id()).getGame_name();
//        play_name = gamePlayInfoCache.getGamePlayInfoByid(gameRaffleRule.getGame_id(), gameRaffleRule.getPlay_id()).getPlay_name();
//        gameRaffleRule.setPlay_name(play_name);
//        gameRaffleRule.setGame_name(game_name);
//        resModel.addAttribute("gameRaffleRule", gameRaffleRule);
//        return "/game/gameLuckyRuleModify";
//    }
//
//    /**
//     * 编辑操作
//     *
//     * @param request
//     * @param gameRaffleRule
//     * @return
//     */
//    @RequestMapping(value = "/modify", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> updateGameRaffleRule(HttpServletRequest request, GameRaffleRule gameRaffleRule) {
//        Map<String, Object> resMap = new HashMap<String, Object>();
//        int re = gamelucky.updateGameRaffleRule(gameRaffleRule);
//        if (re < 0) {
//            resMap.put("result", "error");
//            resMap.put("msg", "修改失败");
//        } else {
//            resMap.put("result", "success");
//            resMap.put("msg", "修改成功");
//        }
//        return resMap;
//    }
//}
