package com.bestinfo.dao.impl.h5.monitor;

import com.bestinfo.bean.h5.monitor.CityTmnCount;
import com.bestinfo.bean.h5.monitor.EveryCityTmnSaleInfo;
import com.bestinfo.bean.h5.monitor.TmnMaxSaleCountTop5;
import com.bestinfo.dao.h5.monitor.IBettingMachineMonitoringDao;
import com.bestinfo.dao.impl.BaseDaoImpl;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 全省投注机监控
 * @author Administrator
 */
@Repository
public class BettingMachineMonitoringDaoImpl extends BaseDaoImpl implements IBettingMachineMonitoringDao{
    /**
     * 查询各个城市的投注机总数量
     */
    private static final String QUERY_EVERY_CITY_TMNCOUNT_LIST ="select count(city_id) as tmn_num,s.city_name from (select a.city_id,b.city_name  from t_tmn_info a,t_city_info b where a.city_id=b.city_id  order by a.city_id ) s group by s.city_name ,s.city_id order by s.city_id";
    /***
     * 查询所有投注机的总量
     */
    private static final String QUERY_TOTAL_TMN_COUNT="select count(*) from t_city_info a,t_tmn_info b where a.city_id=b.city_id";
    /***
     * 查询每一个城市每一个终端机的前200的销售量
     */
    private static final String EVERY_CITY_TMN_SALE_TICKET_NUM="select distinct terminal_id,terminal_address,NVL(sale_money,0) as sale_money,trade_time,trade_type from\n" +
                                                                "(select a.terminal_id,a.terminal_address,sum(b.sale_money) as sale_money,to_char(c.trade_time,'yyyymmdd') as trade_time,to_char(c.trade_type,'999') as trade_type from term.t_tmn_info a,t_current_tmn_date_stat b,t_account_detail c\n" +
                                                                "where to_char(b.operator_date,'yyyymmdd')=to_char(sysdate,'yyyymmdd') \n" +
                                                                "and a.terminal_id=b.terminal_id and a.city_id=?\n" +
                                                                "and a.terminal_id = c.account_id\n" +
                                                                "group by a.terminal_address, a.terminal_id,trade_time,trade_type\n" +
                                                                "union \n" +
                                                                "select terminal_id,terminal_address,0 as sale_money,'-' as trade_time,'-' as trade_type from t_tmn_info where city_id=? \n" +
                                                                "and terminal_id not in\n" +
                                                                "(select a.terminal_id as terminal_id from term.t_tmn_info a,t_current_tmn_date_stat b \n" +
                                                                "where to_char(b.operator_date,'yyyymmdd')=to_char(sysdate,'yyyymmdd')\n" +
                                                                "and a.terminal_id=b.terminal_id and a.city_id=?)           \n" +
                                                                ") where rownum<18 order by sale_money desc";
    
    private static final String TMN_MAX_SALES_TOP5="select * from (\n" +
                                                    "select * from (select a.terminal_id,b.city_name,a.sale_money from t_current_tmn_date_stat a,t_city_info b, t_tmn_info c  where a.terminal_id=c.terminal_id and c.city_id=b.city_id \n" +
                                                    "and  to_char(a.operator_date,'YYYY-MM-DD')=to_char(sysdate,'YYYY-MM-DD') order by a.sale_money desc\n" +
                                                    " ) \n" +
                                                    "union\n" +
                                                    "select terminal_id,'0' as city_name, 0 as sale_money from t_tmn_info \n" +
                                                    ") where rownum<=5";
    private static final String ACTIVE_TMN_COUNT="select  NVL(count(distinct(terminal_id)),0) from t_ticket_bet where bet_time>sysdate-5/(60*24)";
    @Override
    public List<CityTmnCount> getCityTmnCount(JdbcTemplate jdbcTemplate) {
        try {
            return  this.queryForList(jdbcTemplate, QUERY_EVERY_CITY_TMNCOUNT_LIST, null,CityTmnCount.class);
        } catch (Exception e) {
            logger.error("getCityTmnCount ex :", e);
            return null;
        } 
    }

    @Override
    public int getTotalTmnCount(JdbcTemplate jdbcTemplate) {
        int tatalTmnCount=0;
        try {
            tatalTmnCount =this.queryForInteger(jdbcTemplate, QUERY_TOTAL_TMN_COUNT, null);
        } catch (Exception e) {
            logger.error("getTotalTmnCount ex :", e);
        }
        return tatalTmnCount;
    }

    @Override
    public List<EveryCityTmnSaleInfo> getEveryCityTmnSaleInfos(JdbcTemplate jdbcTemplate, Integer city_id) {
        try {
            return this.queryForList(jdbcTemplate, EVERY_CITY_TMN_SALE_TICKET_NUM, new Object[]{city_id,city_id,city_id}, EveryCityTmnSaleInfo.class);
        } catch (Exception e) {
            logger.error("getEveryCityTmnSaleInfos ex :", e);
        }
        return null;
    }

    @Override
    public List<Map> getCitySalesTop5(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, QUERY_TOTAL_TMN_COUNT, null, null);
    }

    @Override
    public List<TmnMaxSaleCountTop5> getTmnMaxSaleCountTop5(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, TMN_MAX_SALES_TOP5, null, TmnMaxSaleCountTop5.class);
    }

    @Override
    public int getActiveTmnCount(JdbcTemplate jdbcTemplate) {
        return this.queryForInteger(jdbcTemplate, ACTIVE_TMN_COUNT, null);
    }


}
