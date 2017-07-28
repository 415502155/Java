package com.bestinfo.dao.terminal;

import com.bestinfo.bean.terminal.TmnClpKey;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 投注机--中彩密钥数据 T_TMN_CLPKEY
 *
 */
public interface ITmnClpKeyDao {

    /**
     *
     * @param jdbcTemplate
     * @param clpKey
     * @return
     */
    public int addClpKey(JdbcTemplate jdbcTemplate, TmnClpKey clpKey);
    
    /**
     *
     * @param jdbcTemplate
     * @param clpKey
     * @return
     */
    public int updateClpKey(JdbcTemplate jdbcTemplate, TmnClpKey clpKey);

    /**
     *
     * @param jdbcTemplate
     * @param terminal_id
     * @return
     */
    public List<TmnClpKey> getClpKeyByTerminalId(JdbcTemplate jdbcTemplate, int terminal_id);

}
