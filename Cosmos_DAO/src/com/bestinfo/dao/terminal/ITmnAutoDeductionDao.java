package com.bestinfo.dao.terminal;

import com.bestinfo.bean.terminal.TmnAutoDeduction;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 投注机-终端自动扣除资金设置表(T_tmn_auto_deduction)
 *
 * @author hhhh
 */
public interface ITmnAutoDeductionDao {

    /**
     * 批量插入
     *
     * @param jdbcTemplate
     * @param tadList
     * @return
     */
    public int batchAddTmnAutoDeduction(JdbcTemplate jdbcTemplate, final List<TmnAutoDeduction> tadList);
    
    /**
     * 批量更新状态
     *
     * @param jdbcTemplate
     * @param status
     * @param terminal_id
     * @return
     * @throws java.lang.Exception
     */
    public int batchUpdateTmnAutoDeduction(JdbcTemplate jdbcTemplate, final int status, final int terminal_id);

}
