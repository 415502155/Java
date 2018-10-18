package com.bestinfo.dao.business;

import com.bestinfo.bean.business.TCmsInfo;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 内容管理
 *
 * @author
 */
public interface ICmsInfoDao {

    /**
     * 查询内容管理的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    public List<TCmsInfo> selectCmsInfo(JdbcTemplate jdbcTemplate);

    /**
     * 修改内容管理数据
     *
     * @param jdbcTemplate
     * @param ci
     * @return
     */
    public int updateCmsInfo(JdbcTemplate jdbcTemplate, TCmsInfo ci);
    /**
     * 根据关键字查询内容管理公告信息
     * @param keys
     * @param jdbcTemplate
     * @return 
     */
    public TCmsInfo getCmsInfoByKeys(JdbcTemplate jdbcTemplate,String keys);
}
