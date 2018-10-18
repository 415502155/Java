/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.encoding;

import com.bestinfo.bean.encoding.IdType;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author user
 */
public interface IIdType {
    /**
     * 查询证件类型的数据列表
     * @param jdbcTemplate
     * @return  列表数据集合
     */
    public List<IdType> selectIdType(JdbcTemplate jdbcTemplate);
        /**
     * 修改证件类型数据
     * @param jdbcTemplate
     * @param idType
     * @return 
     */
    public int updateIdType(JdbcTemplate jdbcTemplate, IdType idType) ;
}
