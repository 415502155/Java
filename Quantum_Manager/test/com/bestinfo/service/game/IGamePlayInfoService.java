package com.bestinfo.service.game;

import com.bestinfo.bean.game.GamePlayInfo;
import com.bestinfo.dao.page.Pagination;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yangyuefu
 */
public interface IGamePlayInfoService {

    public List<GamePlayInfo> getGamePlayInfoByGameId(int gameId);

    public GamePlayInfo getGamePlayInfoByPlayId(int playId);

    public GamePlayInfo getPlayByGameIdAndPlayId(int gameId, int playId);

    public int insertGamePlayInfo(GamePlayInfo gamePlayInfo);

    public int updateGamePlayInfo(GamePlayInfo gamePlayInfo);

    public Pagination<GamePlayInfo> getGamePlayInfoPageList(Map<String, Object> params);
}
