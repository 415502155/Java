package com.bestinfo.dao.ticket;

import com.bestinfo.bean.ticket.TicketPrizeCheck;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author shijf
 */
public interface ITicketPrizeCheckDao {

    /**
     * 插入记录
     *
     * @param jdbcTemplate
     * @param ticket
     * @return -1标识插入失败 大于0表示成功
     */
    public int add(JdbcTemplate jdbcTemplate, TicketPrizeCheck ticket);

    /**
     * 根据票面密码查询验票记录
     *
     * @param jdbcTemplate
     * @param cipher
     * @return
     */
    public TicketPrizeCheck getTicketPrizeCheckByCipher(JdbcTemplate jdbcTemplate, String cipher);
}
