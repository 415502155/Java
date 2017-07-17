package com.bestinfo.controller.clpfile;

import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.cache.keys.RedisKeysUtil;
import com.bestinfo.dao.game.IGameDrawInfoDao;
import com.bestinfo.redis.lock.RedisLock;
import com.bestinfo.service.clpfile.IClpFileService;
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
 * eb手工fix功能
 */
@Controller
@RequestMapping(value = "/manual")
public class ClpFileCreateController {

    private static final Logger logger = Logger.getLogger(ClpFileCreateController.class);

    @Resource
    private IClpFileService clpFileService;

    @Resource
    private IGameDrawInfoDao drawInfoDao;

    @Resource
    private JdbcTemplate metaJdbcTemplate;

    @Resource
    private RedisLock redisLock;

    /**
     * 生成中彩开奖后上传的文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/clpFileAfterLucky")
    @ResponseBody
    public Map<String, Object> clpFileAfterLucky(HttpServletRequest request) {
        logger.info("manual create clp file after lucky");

        Map<String, Object> resMap = new HashMap<String, Object>();
        String gameIdDrawName = ServletRequestUtils.getStringParameter(request, "gameIdDrawName", "");
        if ("".equals(gameIdDrawName)) {
            logger.error("data error,gameIdDrawName:" + gameIdDrawName);
            resMap.put("code", "-1");
            resMap.put("msg", "页面数据错误！");
            return resMap;
        }
        List<GameDrawInfo> drawList = new ArrayList<GameDrawInfo>();
        JSONArray gameDrawJson = JSONArray.fromObject(gameIdDrawName);
        for (int i = 0; i < gameDrawJson.size(); i++) {
            JSONObject obj = gameDrawJson.getJSONObject(i);
            GameDrawInfo gameDraw = new GameDrawInfo();
            gameDraw.setGame_id((Integer) obj.get("game_id"));
            gameDraw.setDraw_name(obj.get("draw_name").toString());
            drawList.add(gameDraw);
        }

        boolean fail = false;
        for (GameDrawInfo drawInfo : drawList) {
            int gameId = drawInfo.getGame_id();
            String drawName = drawInfo.getDraw_name();
            GameDrawInfo dbDrawInfo = drawInfoDao.getDrawByGameIdAndDrawName(metaJdbcTemplate, gameId, drawName);
            if (dbDrawInfo == null) {
                logger.error("GameDrawInfo is null,game_id:" + gameId + ",draw_name:" + drawName);
                resMap.put("code", "-1");
                resMap.put("msg", "没有期信息！");
                return resMap;
            }
            int drawId = dbDrawInfo.getDraw_id();

            int re = clpFileService.clpCashFile(gameId, drawId);
            if (re == 0) {
                logger.info("create clp cash file success,game_id:" + gameId + ",draw_id:" + drawId + ",draw_name:" + drawName);
            } else {
                fail = true;
                logger.error("create clp cash file error,game_id:" + gameId + ",draw_id:" + drawId + ",draw_name:" + drawName);
            }

            re = clpFileService.clpPoolFile(gameId, drawId);
            if (re == 0) {
                logger.info("create clp pool file success,game_id:" + gameId + ",draw_id:" + drawId + ",draw_name:" + drawName);
            } else {
                fail = true;
                logger.error("create clp pool file error,game_id:" + gameId + ",draw_id:" + drawId + ",draw_name:" + drawName);
            }

            re = clpFileService.clpWinnFile(gameId, drawId);
            if (re == 0) {
                logger.info("create clp winn file success,game_id:" + gameId + ",draw_id:" + drawId + ",draw_name:" + drawName);
            } else {
                fail = true;
                logger.error("create clp winn file error,game_id:" + gameId + ",draw_id:" + drawId + ",draw_name:" + drawName);
            }
        }

        if (fail) {
            logger.info("manual create clp file after lucky error");
            resMap.put("code", "-2");
            resMap.put("msg", "生成中彩文件失败！");
        } else {
            logger.info("manual create clp file after lucky success");
            resMap.put("code", "0");
            resMap.put("msg", "生成中彩文件成功！");
        }

        return resMap;
    }

    /**
     * 生成某个游戏开奖后上传的中彩文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/clpFileAfterLuckyPerGame")
    @ResponseBody
    public String clpFileAfterLuckyPerGame(HttpServletRequest request) {
        logger.info("manual create clp file after lucky for one game");

        int gameId = ServletRequestUtils.getIntParameter(request, "gameId", 0);
        String drawName = ServletRequestUtils.getStringParameter(request, "drawName", "");
        if (gameId <= 0 || "".equals(drawName)) {
            logger.error("data error,game_id:" + gameId + ",draw_name:" + drawName);
            //return "数据错误！";
            return "-1";
        }
        logger.info("game_id:" + gameId + ",draw_name:" + drawName);

        //added by yfyang,增加redis锁，同一时间只能一个下载器调用接口
        boolean canLock = false;
        List<String> keys = null;
        List<String> args = null;
        String lockKey = "";
        try {
            if (RedisLock.islock) {
                keys = new ArrayList<String>();
                args = new ArrayList<String>();
                lockKey = RedisKeysUtil.lockkey + "clpFileAfterLucky:" + gameId + ":" + drawName;
                keys.add(lockKey);
                canLock = redisLock.acquire(null, keys, args);
                if (!canLock) {
                    logger.error("can not receive the redis lock,lockKey----" + lockKey);
                    return "-3";
                }
                logger.info("received the redis lock,lockKey----" + lockKey);
            }

            boolean fail = false;
            GameDrawInfo dbDrawInfo = drawInfoDao.getDrawByGameIdAndDrawName(metaJdbcTemplate, gameId, drawName);
            if (dbDrawInfo == null) {
                logger.error("GameDrawInfo is null,game_id:" + gameId + ",draw_name:" + drawName);
                //return "没有期信息！";
                return "-1";
            }
            int drawId = dbDrawInfo.getDraw_id();

            //中彩兑奖文件
            int re = clpFileService.clpCashFile(gameId, drawId);
            if (re == 0) {
                logger.info("create clp cash file success,game_id:" + gameId + ",draw_id:" + drawId + ",draw_name:" + drawName);
            } else {
                fail = true;
                logger.error("create clp cash file error,game_id:" + gameId + ",draw_id:" + drawId + ",draw_name:" + drawName);
            }

            //中彩奖池文件
            re = clpFileService.clpPoolFile(gameId, drawId);
            if (re == 0) {
                logger.info("create clp pool file success,game_id:" + gameId + ",draw_id:" + drawId + ",draw_name:" + drawName);
            } else {
                fail = true;
                logger.error("create clp pool file error,game_id:" + gameId + ",draw_id:" + drawId + ",draw_name:" + drawName);
            }

            //生成中彩中奖文件
            re = clpFileService.clpWinnFile(gameId, drawId);
            if (re == 0) {
                logger.info("create clp winn file success,game_id:" + gameId + ",draw_id:" + drawId + ",draw_name:" + drawName);
            } else {
                fail = true;
                logger.error("create clp winn file error,game_id:" + gameId + ",draw_id:" + drawId + ",draw_name:" + drawName);
            }

            if (fail) {
                logger.info("manual create clp file after lucky error");
                //return "生成中彩 " + msg + " 失败！";
                return "-2";
            } else {
                logger.info("manual create clp file after lucky success");
                //return "生成中彩文件成功！";
                return "0";
            }
        } catch (Exception e) {
            logger.error("", e);
            return "-3";
        } finally {
            if (canLock) {
                logger.info("release redis lock,lockKey----" + lockKey);
                redisLock.release(keys, args);
            }
        }

    }

    /**
     * 生成快开游戏的中彩文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/clpKenoFile")
    @ResponseBody
    public Map<String, Object> clpKenoFile(HttpServletRequest request) {
        logger.info("manual create keno clp file");
        Map<String, Object> resMap = new HashMap<String, Object>();
        int gameId = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        String drawName = ServletRequestUtils.getStringParameter(request, "draw_name", "");
        if (gameId <= 0 || "".equals(drawName)) {
            logger.error("data error,game_id:" + gameId + ",draw_name:" + drawName);
            resMap.put("code", "-1");
            resMap.put("msg", "数据错误！");
            return resMap;
        }

        GameDrawInfo drawInfo = drawInfoDao.getDrawByGameIdAndDrawName(metaJdbcTemplate, gameId, drawName);
        if (drawInfo == null) {
            logger.error("GameDrawInfo is null,game_id:" + gameId + ",draw_name:" + drawName);
            resMap.put("code", "-1");
            resMap.put("msg", "没有期信息！");
            return resMap;
        }

        int re = clpFileService.kenoClpFile(gameId, drawInfo.getDraw_id(), drawInfo.getDraw_name());
        if (re == 0) {
            resMap.put("code", "0");
            resMap.put("msg", "生成快十中彩文件成功！");
        } else {
            logger.info("manual create keno clp file fail");
            resMap.put("code", "-2");
            resMap.put("msg", "生成快十中彩文件失败！");
        }
        return resMap;
    }

    /**
     * 生成中彩销售文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/clpSaleFile")
    @ResponseBody
    public Map<String, Object> clpSaleFile(HttpServletRequest request) {
        logger.info("manual create clp sale file");
        Map<String, Object> resMap = new HashMap<String, Object>();
        int gameId = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        String drawName = ServletRequestUtils.getStringParameter(request, "draw_name", "");
        if (gameId <= 0 || "".equals(drawName)) {
            logger.error("data error,game_id:" + gameId + ",draw_name:" + drawName);
            resMap.put("code", "-1");
            resMap.put("msg", "数据错误！");
            return resMap;
        }

        GameDrawInfo drawInfo = drawInfoDao.getDrawByGameIdAndDrawName(metaJdbcTemplate, gameId, drawName);
        if (drawInfo == null) {
            logger.error("GameDrawInfo is null,game_id:" + gameId + ",draw_name:" + drawName);
            resMap.put("code", "-1");
            resMap.put("msg", "没有期信息！");
            return resMap;
        }

        int re = clpFileService.clpSaleFile(gameId, drawInfo.getDraw_id());
        if (re == 0) {
            resMap.put("code", "0");
            resMap.put("msg", "生成中彩销售文件成功！");
        } else {
            logger.info("manual create clp sale file fail");
            resMap.put("code", "-2");
            resMap.put("msg", "生成中彩销售文件失败！");
        }
        return resMap;
    }

    /**
     * 生成中彩弃奖文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/clpForgetFile")
    @ResponseBody
    public Map<String, Object> clpForgetFile(HttpServletRequest request) {
        logger.info("manual create clp forget file");
        Map<String, Object> resMap = new HashMap<String, Object>();
        int gameId = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        String drawName = ServletRequestUtils.getStringParameter(request, "draw_name", "");
        if (gameId <= 0 || "".equals(drawName)) {
            logger.error("data error,game_id:" + gameId + ",draw_name:" + drawName);
            resMap.put("code", "-1");
            resMap.put("msg", "数据错误！");
            return resMap;
        }

        GameDrawInfo drawInfo = drawInfoDao.getDrawByGameIdAndDrawName(metaJdbcTemplate, gameId, drawName);
        if (drawInfo == null) {
            logger.error("GameDrawInfo is null,game_id:" + gameId + ",draw_name:" + drawName);
            resMap.put("code", "-1");
            resMap.put("msg", "没有期信息！");
            return resMap;
        }
        int re = clpFileService.clpForgetFile(gameId, drawInfo.getDraw_id());
        if (re == 0) {
            resMap.put("code", "0");
            resMap.put("msg", "生成弃奖文件成功！");
        } else if (re == -11) {
            logger.info("this draw does not have clp forget file");
            resMap.put("code", "-11");
            resMap.put("msg", "此期没有弃奖文件！");
        } else {
            logger.info("manual create clp sale file fail");
            resMap.put("code", "-2");
            resMap.put("msg", "生成弃奖文件失败！");
        }
        return resMap;
    }

    /**
     * 生成中彩兑奖文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/clpCashFile")
    @ResponseBody
    public Map<String, Object> clpCashFile(HttpServletRequest request) {
        logger.info("manual create clp cash file");
        Map<String, Object> resMap = new HashMap<String, Object>();
        int gameId = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        String drawName = ServletRequestUtils.getStringParameter(request, "draw_name", "");
        if (gameId <= 0 || "".equals(drawName)) {
            logger.error("data error,game_id:" + gameId + ",draw_name:" + drawName);
            resMap.put("code", "-1");
            resMap.put("msg", "数据错误！");
            return resMap;
        }

        GameDrawInfo drawInfo = drawInfoDao.getDrawByGameIdAndDrawName(metaJdbcTemplate, gameId, drawName);
        if (drawInfo == null) {
            logger.error("GameDrawInfo is null,game_id:" + gameId + ",draw_name:" + drawName);
            resMap.put("code", "-1");
            resMap.put("msg", "没有期信息！");
            return resMap;
        }

        int re = clpFileService.clpCashFile(gameId, drawInfo.getDraw_id());
        if (re == 0) {
            resMap.put("code", "0");
            resMap.put("msg", "生成中彩兑奖文件成功！");
        } else {
            logger.info("manual create clp winn file fail");
            resMap.put("code", "-2");
            resMap.put("msg", "生成中彩兑奖文件失败！");
        }
        return resMap;
    }

    /**
     * 生成中彩奖池文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/clpPoolFile")
    @ResponseBody
    public Map<String, Object> clpPoolFile(HttpServletRequest request) {
        logger.info("manual create clp pool file");
        Map<String, Object> resMap = new HashMap<String, Object>();
        int gameId = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        String drawName = ServletRequestUtils.getStringParameter(request, "draw_name", "");
        if (gameId <= 0 || "".equals(drawName)) {
            logger.error("data error,game_id:" + gameId + ",draw_name:" + drawName);
            resMap.put("code", "-1");
            resMap.put("msg", "数据错误！");
            return resMap;
        }

        GameDrawInfo drawInfo = drawInfoDao.getDrawByGameIdAndDrawName(metaJdbcTemplate, gameId, drawName);
        if (drawInfo == null) {
            logger.error("GameDrawInfo is null,game_id:" + gameId + ",draw_name:" + drawName);
            resMap.put("code", "-1");
            resMap.put("msg", "没有期信息！");
            return resMap;
        }

        int re = clpFileService.clpPoolFile(gameId, drawInfo.getDraw_id());
        if (re == 0) {
            resMap.put("code", "0");
            resMap.put("msg", "生成中彩奖池文件成功！");
        } else {
            logger.info("manual create clp pool file fail");
            resMap.put("code", "-2");
            resMap.put("msg", "生成中彩池文件失败！");
        }
        return resMap;
    }

    /**
     * 生成中彩中奖文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/clpWinnFile")
    @ResponseBody
    public Map<String, Object> clpWinnFile(HttpServletRequest request) {
        logger.info("manual create clp winn file");
        Map<String, Object> resMap = new HashMap<String, Object>();
        int gameId = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        String drawName = ServletRequestUtils.getStringParameter(request, "draw_name", "");
        if (gameId <= 0 || "".equals(drawName)) {
            logger.error("data error,game_id:" + gameId + ",draw_name:" + drawName);
            resMap.put("code", "-1");
            resMap.put("msg", "数据错误！");
            return resMap;
        }

        GameDrawInfo drawInfo = drawInfoDao.getDrawByGameIdAndDrawName(metaJdbcTemplate, gameId, drawName);
        if (drawInfo == null) {
            logger.error("GameDrawInfo is null,game_id:" + gameId + ",draw_name:" + drawName);
            resMap.put("code", "-1");
            resMap.put("msg", "没有期信息！");
            return resMap;
        }

        int re = clpFileService.clpWinnFile(gameId, drawInfo.getDraw_id());
        if (re == 0) {
            resMap.put("code", "0");
            resMap.put("msg", "生成中彩中奖文件成功！");
        } else {
            logger.info("manual create clp winn file fail");
            resMap.put("code", "-2");
            resMap.put("msg", "生成中彩中奖文件失败！");
        }
        return resMap;
    }

    /**
     * 生成光盘抽奖文件
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/fsBurnCdFile")
    @ResponseBody
    public Map<String, Object> fsBurnCdFile(HttpServletRequest request) {
        logger.info("manual create burncd file");
        Map<String, Object> resMap = new HashMap<String, Object>();
        int gameId = ServletRequestUtils.getIntParameter(request, "game_id", 0);
        String drawName = ServletRequestUtils.getStringParameter(request, "draw_name", "");
        if (gameId <= 0 || "".equals(drawName)) {
            logger.error("data error,game_id:" + gameId + ",draw_name:" + drawName);
            resMap.put("code", "-1");
            resMap.put("msg", "数据错误！");
            return resMap;
        }

        GameDrawInfo drawInfo = drawInfoDao.getDrawByGameIdAndDrawName(metaJdbcTemplate, gameId, drawName);
        if (drawInfo == null) {
            logger.error("GameDrawInfo is null,game_id:" + gameId + ",draw_name:" + drawName);
            resMap.put("code", "-1");
            resMap.put("msg", "没有期信息！");
            return resMap;
        }

//        int re = clpFileService.fsBurnCdFile(gameId, drawInfo.getDraw_id());
//        if (re == 0) {
//            resMap.put("code", "0");
//            resMap.put("msg", "生成光盘抽奖文件成功！");
//        } else {
//            logger.info("manual create burncd file fail");
//            resMap.put("code", "-2");
//            resMap.put("msg", "生成光盘抽奖文件失败！");
//        }
        return resMap;
    }

}
