package com.bestinfo.service.game;

import com.bestinfo.bean.game.GameKDrawInfo;
import com.bestinfo.dao.page.Pagination;
import java.util.Map;

/**
 *
 * @author yangyuefu
 */
public interface IGameKDrawInfoService {

    /**
     * 生成快开期次
     *
     * @param game_id 游戏id
     * @return
     */
    public int preScheduleKDrawAuto(int game_id);

    public Pagination<GameKDrawInfo> getGameKDrawInfoPageList(Map<String, Object> params);

}
