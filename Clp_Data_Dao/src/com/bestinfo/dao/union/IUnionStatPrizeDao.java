package com.bestinfo.dao.union;

import com.bestinfo.bean.union.UnionStatPrize;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author LH
 */
public interface IUnionStatPrizeDao {

    /**
     * 新增记录
     *
     * @param jdbcTemplate
     * @param uspList
     * @return 成功-受影响的记录数 失败-(-1)
     */
    public int batchInsert(JdbcTemplate jdbcTemplate, List<UnionStatPrize> uspList);

    /**
     * 查询中奖数
     *
     * @param jdbcTemplate
     * @param systemId
     * @param gameId
     * @param drawId
     * @param openId
     * @param classId
     * @return
     */
    public Integer queryPrizeNumByClassId(JdbcTemplate jdbcTemplate, int systemId, int gameId, int drawId, int openId, int classId);

}
