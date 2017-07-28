package com.bestinfo.dao.business;

import com.bestinfo.bean.business.DealerPrivilege;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 代销商游戏特权
 */
public interface IDealerPrivilegeDao {

    /**
     * 查询代销商游戏特权的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    public List<DealerPrivilege> selectDealerPrivilege(JdbcTemplate jdbcTemplate);

    /**
     * 修改代销商游戏特权数据
     *
     * @param jdbcTemplate
     * @param dp
     * @return
     */
    public int updateDealerPrivilege(JdbcTemplate jdbcTemplate, DealerPrivilege dp);

    /**
     * 批量插入代销商游戏特权数据
     *
     * @param jdbcTemplate
     * @param dpList
     * @return 0-成功 -1(失败)
     */
    public int insertDealerPrivilege(JdbcTemplate jdbcTemplate, final List<DealerPrivilege> dpList);

    /**
     * 根据代销商编号删除它下的所有代销商游戏特权数据
     *
     * @param jdbcTemplate
     * @param dealerId
     * @return 0-成功 -1(失败)
     */
    public int deleteDealerPrivilegeByDealerId(JdbcTemplate jdbcTemplate, String dealerId);

    /**
     * 批量修改代理商游戏特权数据
     *
     * @param jdbcTemplate
     * @param privilegeList
     * @return
     */
    public int mergeDealerPrivilege(JdbcTemplate jdbcTemplate, final List<DealerPrivilege> privilegeList);
}
