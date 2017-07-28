/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.encoding;

import com.bestinfo.bean.encoding.SourceType;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 资金信息
 *
 * @author user
 */
public interface ISourceType {

    /**
     * 查询资金来源类型的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    public List<SourceType> selectSourceType(JdbcTemplate jdbcTemplate);

    /**
     * 修改资金来源类型数据
     *
     * @param jdbcTemplate
     * @param st
     * @return
     */
    public int updateSourceType(JdbcTemplate jdbcTemplate, SourceType st);
}
