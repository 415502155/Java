package com.bestinfo.dao.h5.comparison;

import com.bestinfo.bean.h5.comparison.WeekReportGameAndSales;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Administrator
 */
public interface IGameContrastDao {
        public List<WeekReportGameAndSales> getWeekReportGameAndSalesList(JdbcTemplate jdbcTemplate);//getWeekReportGameAndSalesList
        public List<WeekReportGameAndSales> getWeekReportGameAndSalesListByLastYear(JdbcTemplate jdbcTemplate);
        public List<WeekReportGameAndSales> getWeekReportGameAndSalesListByWeek(JdbcTemplate jdbcTemplate,int week);
        public List<WeekReportGameAndSales> getWeekReportGameAndSalesComputerList(JdbcTemplate jdbcTemplate,Integer start,Integer end,Integer type);
        public List<WeekReportGameAndSales> getWeekReportGameAndSalesesWeekList(JdbcTemplate jdbcTemplate,Integer start,Integer end,Integer type);
        public Integer getCurWeek(JdbcTemplate jdbcTemplate);
        public List<WeekReportGameAndSales> getLastWeekSales(JdbcTemplate jdbcTemplate,int week);
        
        public List<WeekReportGameAndSales> getWeekReportGameAndSalese20(JdbcTemplate jdbcTemplate);
}
