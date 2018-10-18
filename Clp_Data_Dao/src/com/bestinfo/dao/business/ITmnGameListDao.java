package com.bestinfo.dao.business;

import com.bestinfo.bean.business.TmnGameList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 投注机支持游戏表
 *
 * @author hhhh
 */
public interface ITmnGameListDao {

    /**
     * 查询软件类型列表
     *
     * @param jdbcTemplate
     * @return
     */
    public List<TmnGameList> getTmnGameList(JdbcTemplate jdbcTemplate);
}
