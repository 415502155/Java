package com.bestinfo.dao.impl.h5.comparison;

import com.bestinfo.bean.h5.comparison.StatProvinceOther;
import com.bestinfo.bean.h5.comparison.TicketType;
import com.bestinfo.dao.h5.comparison.IProvincesSalesComparisonDao;
import com.bestinfo.dao.h5.comparison.ITicketTypeDao;
import com.bestinfo.dao.impl.BaseDaoImpl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 获取票种列表
 * @author Administrator
 */
@Repository
public class TicketTypeDaoImpl extends BaseDaoImpl implements ITicketTypeDao{
    private static final String QUERY_TicketTypeList_LIST ="select ticket_type,ticket_type_name from t_ticket_type";

    @Override
    public List<TicketType> getTicketTypeList(JdbcTemplate jdbcTemplate) {
        try {
            return this.queryForList(jdbcTemplate, QUERY_TicketTypeList_LIST, null, TicketType.class);
        } catch (Exception e) {
            logger.info("getTicketType ex:"+e);
            return null;
        }
    }

}
