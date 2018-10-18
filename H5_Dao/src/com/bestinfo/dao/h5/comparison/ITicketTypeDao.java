package com.bestinfo.dao.h5.comparison;

import com.bestinfo.bean.h5.comparison.StatProvinceOther;
import com.bestinfo.bean.h5.comparison.TicketType;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Administrator
 */
public interface ITicketTypeDao {
    public List<TicketType> getTicketTypeList(JdbcTemplate jdbcTemplate);
}
