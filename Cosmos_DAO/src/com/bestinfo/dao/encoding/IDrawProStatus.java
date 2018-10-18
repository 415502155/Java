package com.bestinfo.dao.encoding;

import com.bestinfo.bean.encoding.DrawProcessStatus;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author hhhh
 */
public interface IDrawProStatus {
    /**
     * 查询游戏期进度状态数据列表
     * @param jdbcTemplate
     * @return 列表数据
     */
    public List<DrawProcessStatus> selectDrawProStatus(JdbcTemplate jdbcTemplate);
    
    /**
     * 修改游戏期进度状态数据
     * @param jdbcTemplate
     * @param kp
     * @return 
     */
    public int updateDrawProStatus(JdbcTemplate jdbcTemplate, DrawProcessStatus kp) ;

}
