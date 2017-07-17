package com.bestinfo.controller.fsMon;

import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.bean.game.GameInfo;
import com.bestinfo.bean.session.SessionInfo;
import com.bestinfo.dao.business.ITmnInfoDao;
import com.bestinfo.dao.game.IGameDrawInfoDao;
import com.bestinfo.dao.game.IGameInfoDao;
import com.bestinfo.redis.login.TerminalLoginRedis;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 光盘刻录下载软件用,查询游戏id和游戏名称,游戏期名和期号
 */
@Controller
@RequestMapping(value = "/app")
public class AppController {

    @Resource
    private IGameInfoDao gameInfoDao;

    @Resource
    private IGameDrawInfoDao gameDrawInfoDao;

    @Resource
    private ITmnInfoDao tmnInfoDao;

    @Resource
    private TerminalLoginRedis loginRedis;

    @Resource
    private JdbcTemplate termJdbcTemplate;

    @Resource
    private JdbcTemplate metaJdbcTemplate;

    @RequestMapping(value = "/getGameNameWithId")
    public @ResponseBody
    Map<String, Integer> getGameNameWithId(HttpServletRequest request, HttpServletResponse response) {
        List<GameInfo> lgi = gameInfoDao.selectGameInfo(metaJdbcTemplate);
        Map<String, Integer> msi = new HashMap<String, Integer>();
        for (GameInfo gi : lgi) {
            msi.put(gi.getGame_name(), gi.getGame_id());
        }
        return msi;
    }

    @RequestMapping(value = "/getGameKenoProp")
    public @ResponseBody
    Map<Integer, Integer> getGameKenoProp(HttpServletRequest request, HttpServletResponse response) {
        List<GameInfo> lgi = gameInfoDao.selectGameInfo(metaJdbcTemplate);
        Map<Integer, Integer> msi = new HashMap<Integer, Integer>();
        for (GameInfo gi : lgi) {
            msi.put(gi.getGame_id(), gi.getKeno_game());
        }
        return msi;
    }

    @RequestMapping(value = "/getGameDrawIdByName")
    public @ResponseBody
    Integer getGameDrawIdByName(HttpServletRequest request, String drawName, Integer gameId) {
        GameDrawInfo gdi = gameDrawInfoDao.getDrawByGameIdAndDrawName(metaJdbcTemplate, gameId, drawName);
        if (gdi == null) {
            return -1;
        }
        return gdi.getDraw_id();
    }

    @RequestMapping(value = "/whoIsOffLine")
    public @ResponseBody
    String whoIsOffLine(HttpServletRequest request) {
        List<TmnInfo> tmnList = tmnInfoDao.selectTmnInfo(termJdbcTemplate);
        if (tmnList == null || tmnList.isEmpty()) {
            return "终端列表为空";
        }
        Integer tmnSum = tmnList.size();
        StringBuilder tmnOffLineMsg = new StringBuilder();
        Integer tmnOffLine = 0;
        for (TmnInfo tmn : tmnList) {
            SessionInfo session = loginRedis.getSessionInfo(tmn.getTerminal_id().toString());
            if (session == null) {
                tmnOffLine++;
                tmnOffLineMsg.append(tmn.getTerminal_id());
                if (tmnOffLine % 10 == 0) {
                    tmnOffLineMsg.append("\n");
                } else {
                    tmnOffLineMsg.append(",");
                }

            }
        }
        String returnMsg = "tmn sum:" + tmnSum.toString() + "\n" + "offline tmn:"
                + tmnOffLine.toString() + "\n" + tmnOffLineMsg.toString();
        return returnMsg;
    }

}
