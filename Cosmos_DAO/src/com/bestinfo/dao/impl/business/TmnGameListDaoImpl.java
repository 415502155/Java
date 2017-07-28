package com.bestinfo.dao.impl.business;

import com.bestinfo.bean.business.TmnGameList;
import com.bestinfo.dao.business.ITmnGameListDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.define.system.WorkStatusDefine;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 投注机支持游戏表
 *
 * @author hhhh
 */
@Repository
public class TmnGameListDaoImpl extends BaseDaoImpl implements ITmnGameListDao {

    private static final String SELECT_TmnGameList = "select software_id,game_id,work_status from t_tmn_game_list";
    private static final String GET_GAME_BY_SOFT = "select * from t_tmn_game_list where software_id=? and work_status=" + WorkStatusDefine.work + " order by game_id";

    /**
     * 查询软件类型列表
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<TmnGameList> getTmnGameList(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, SELECT_TmnGameList, null, TmnGameList.class);
    }
  /**
     * 查询软件类型列表
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<TmnGameList> getTmnGameList(JdbcTemplate jdbcTemplate, int softId) {
        return this.queryForList(jdbcTemplate, GET_GAME_BY_SOFT, new Object[]{softId}, TmnGameList.class);
    }

}
