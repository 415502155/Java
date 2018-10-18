/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.impl.exhibition;

import com.bestinfo.bean.exhibition.LuckyNo;
import com.bestinfo.bean.exhibition.WeekReportSales;
import com.bestinfo.dao.exhibition.IWeekreportSalesDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.Calendar;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *1.提供各区县本年累计销量（分电脑票，即开票，中福在线，频度为每周提供一次）数据查询接口（区县编号，年度，票种名称，累计销量）（静安区，闸北区销量合并）
 *2.提供各区县上周单周累计销量（分电脑票，即开票，中福在线，频度为每周提供一次）数据查询接口（区县编号，周数，票种名称，累计销量）（静安区，闸北区销量合并）
 * @author Administrator
 */
@Repository
public class WeekreportSalesDaoImpl extends BaseDaoImpl  implements IWeekreportSalesDao{
    private static final String QUERY_WEEK_REPORT_SALES = "select sale_money,city_id,year_id,game_name from (\n" +
                        "select sum(sale_money) as sale_money ,city_id,year_id,'即开票' AS GAME_NAME from t_weekreport_sales \n" +
                        "where year_id = ? and game_id = 101 and city_id not in (2,6) group by city_id,year_id \n" +
                        "UNION ALL\n" +
                        "select sum(sale_money) as sale_money ,2 As city_id,year_id,'即开票' AS GAME_NAME from(\n" +
                        "select sum(sale_money) AS sale_money,city_id,year_id,'即开票' AS GAME_NAME from t_weekreport_sales \n" +
                        "where year_id = ? and game_id = 101 and city_id  in (2,6) group by city_id,year_id \n" +
                        ") group  by year_id\n" +
                        "UNION  ALL\n" +
                        "select sum(sale_money) as sale_money ,city_id,year_id,'中福在线' AS GAME_NAME from t_weekreport_sales \n" +
                        "where year_id = ? and game_id = 102 and city_id not in (2,6) group by city_id,year_id \n" +
                        "UNION  ALL\n" +
                        "select sum(sale_money) as sale_money ,2 As city_id,year_id,'中福在线' AS GAME_NAME from(\n" +
                        "select sum(sale_money) AS sale_money,city_id,year_id,'中福在线' AS GAME_NAME from t_weekreport_sales \n" +
                        "where year_id = ? and game_id = 102 and city_id  in (2,6) group by city_id,year_id\n" +
                        ") group by year_id\n" +
                        "UNION ALL\n" +
                        "select sum(sale_money) as sale_money ,city_id,year_id,'电脑票' AS GAME_NAME from t_weekreport_sales \n" +
                        "where year_id = ? and game_id in (1,2,3,4,5,6,7,8,9) and city_id not in (2,6) group by city_id,year_id\n" +
                        "UNION ALL\n" +
                        "select sum(sale_money) AS sale_money ,city_id,year_id,'电脑票' AS GAME_NAME from t_weekreport_sales \n" +
                        "where year_id = ? and game_id in (1,2,3,4,5,6,7,8,9) and city_id in (2,6) group by city_id,year_id\n" +
                        "\n" +
                        ") order by city_id ,game_name";
    private static final String QUERY_WEEK_REPORT_SALES_BY_CITY = "select sale_money,city_id,year_id,game_name from (\n" +
                        "select sum(sale_money) as sale_money ,city_id,year_id,'即开票' AS GAME_NAME from t_weekreport_sales \n" +
                        "where city_id = ? and year_id = ? and game_id = 101 and city_id not in (2,6) group by city_id,year_id \n" +
                        "UNION ALL\n" +
                        "select sum(sale_money) as sale_money ,2 As city_id,year_id,'即开票' AS GAME_NAME from(\n" +
                        "select sum(sale_money) AS sale_money,city_id,year_id,'即开票' AS GAME_NAME from t_weekreport_sales \n" +
                        "where city_id = ? and year_id = ? and game_id = 101 and city_id  in (2,6) group by city_id,year_id \n" +
                        ") group  by year_id\n" +
                        "UNION  ALL\n" +
                        "select sum(sale_money) as sale_money ,city_id,year_id,'中福在线' AS GAME_NAME from t_weekreport_sales \n" +
                        "where city_id = ? and year_id = ? and game_id = 102 and city_id not in (2,6) group by city_id,year_id \n" +
                        "UNION  ALL\n" +
                        "select sum(sale_money) as sale_money ,2 As city_id,year_id,'中福在线' AS GAME_NAME from(\n" +
                        "select sum(sale_money) AS sale_money,city_id,year_id,'中福在线' AS GAME_NAME from t_weekreport_sales \n" +
                        "where city_id = ? and year_id = ? and game_id = 102 and city_id  in (2,6) group by city_id,year_id\n" +
                        ") group by year_id\n" +
                        "UNION ALL\n" +
                        "select sum(sale_money) as sale_money ,city_id,year_id,'电脑票' AS GAME_NAME from t_weekreport_sales \n" +
                        "where city_id = ? and year_id = ? and game_id in (1,2,3,4,5,6,7,8,9) and city_id not in (2,6) group by city_id,year_id\n" +
                        "UNION ALL\n" +
                        "select sum(sale_money) AS sale_money ,city_id,year_id,'电脑票' AS GAME_NAME from t_weekreport_sales \n" +
                        "where city_id = ? and year_id = ? and game_id in (1,2,3,4,5,6,7,8,9) and city_id in (2,6) group by city_id,year_id\n" +
                        "\n" +
                        ") order by city_id ,game_name";
    
    private static final String QUERY_WEEK_REPORT_SALES_NEAR_WEEK ="select sale_money,city_id,week_id,game_name from\n" +
                        "(\n" +
                        "select NVL(sum(sale_money),0) as sale_money ,city_id,week_id,'即开票' as game_name from t_weekreport_sales where\n" +
                        "            year_id=( select to_char(sysdate,'yyyy') from dual) \n" +
                        "            and \n" +
                        "            week_id=(             \n" +
                        "            select nvl(max(week_id),0) as max_week_id from    t_weekreport_sales \n" +
                        "            where year_id=(select extract(year from sysdate)　from dual)\n" +
                        "            group by year_id\n" +
                        "            )\n" +
                        "            and game_id = 101\n" +
                        "            and city_id not in (2,6)\n" +
                        "            group by city_id,week_id \n" +
                        "UNION\n" +
                        "select sum(sale_money),2 as city_id,week_id,'即开票' as game_name from(\n" +
                        "select NVL(sum(sale_money),0) as sale_money ,city_id,week_id,'即开票' as game_name from t_weekreport_sales where\n" +
                        "            year_id=( select to_char(sysdate,'yyyy') from dual) \n" +
                        "            and \n" +
                        "            week_id=(             \n" +
                        "            select nvl(max(week_id),0) as max_week_id from    t_weekreport_sales \n" +
                        "            where year_id=(select extract(year from sysdate)　from dual)\n" +
                        "            group by year_id\n" +
                        "            )\n" +
                        "            and game_id = 101\n" +
                        "            and city_id  in (2,6)\n" +
                        "            group by city_id,week_id \n" +
                        ") group by week_id          \n" +
                        "UNION ALL\n" +
                        "select NVL(sum(sale_money),0) as sale_money ,city_id,week_id,'中福在线' as game_name from t_weekreport_sales where\n" +
                        "            year_id=( select to_char(sysdate,'yyyy') from dual) \n" +
                        "            and \n" +
                        "            week_id=(             \n" +
                        "            select nvl(max(week_id),0) as max_week_id from    t_weekreport_sales \n" +
                        "            where year_id=(select extract(year from sysdate)　from dual)\n" +
                        "            group by year_id\n" +
                        "            )\n" +
                        "            and game_id = 102\n" +
                        "            and city_id not in (2,6)\n" +
                        "            group by city_id,week_id \n" +
                        "UNION\n" +
                        "select sum(sale_money),2 as city_id,week_id,'中福在线' as game_name from(\n" +
                        "select NVL(sum(sale_money),0) as sale_money ,city_id,week_id,'中福在线' as game_name from t_weekreport_sales where\n" +
                        "            year_id=( select to_char(sysdate,'yyyy') from dual) \n" +
                        "            and \n" +
                        "            week_id=(             \n" +
                        "            select nvl(max(week_id),0) as max_week_id from    t_weekreport_sales \n" +
                        "            where year_id=(select extract(year from sysdate)　from dual)\n" +
                        "            group by year_id\n" +
                        "            )\n" +
                        "            and game_id = 102\n" +
                        "            and city_id  in (2,6)\n" +
                        "            group by city_id,week_id \n" +
                        ") group by week_id  \n" +
                        "UNION ALL\n" +
                        "select NVL(sum(sale_money),0) as sale_money ,city_id,week_id,'电脑票' as game_name from t_weekreport_sales where\n" +
                        "            year_id=( select to_char(sysdate,'yyyy') from dual) \n" +
                        "            and \n" +
                        "            week_id=(             \n" +
                        "            select nvl(max(week_id),0) as max_week_id from    t_weekreport_sales \n" +
                        "            where year_id=(select extract(year from sysdate)　from dual)\n" +
                        "            group by year_id\n" +
                        "            )\n" +
                        "            and game_id in (1,2,3,4,5,6,7,8,9)\n" +
                        "            and city_id not in (2,6)\n" +
                        "            group by city_id,week_id \n" +
                        "UNION\n" +
                        "select sum(sale_money),2 as city_id,week_id,'电脑票' as game_name from(\n" +
                        "select NVL(sum(sale_money),0) as sale_money ,city_id,week_id,'中福在线' as game_name from t_weekreport_sales where\n" +
                        "            year_id=( select to_char(sysdate,'yyyy') from dual) \n" +
                        "            and \n" +
                        "            week_id=(             \n" +
                        "            select nvl(max(week_id),0) as max_week_id from    t_weekreport_sales \n" +
                        "            where year_id=(select extract(year from sysdate)　from dual)\n" +
                        "            group by year_id\n" +
                        "            )\n" +
                        "            and game_id in (1,2,3,4,5,6,7,8,9)\n" +
                        "            and city_id  in (2,6)\n" +
                        "            group by city_id,week_id \n" +
                        ") group by week_id\n" +
                        ")";
    
    private static final String QUERY_WEEK_REPORT_SALES_BY_WEEK = "select sale_money,city_id,week_id,game_name from\n" +
                        "(\n" +
                        "select NVL(sum(sale_money),0) as sale_money ,city_id,week_id,'即开票' as game_name from t_weekreport_sales where\n" +
                        "            year_id=( select to_char(sysdate,'yyyy') from dual) \n" +
                        "            and \n" +
                        "            week_id= ?\n" +
                        "            and game_id = 101\n" +
                        "            and city_id not in (2,6)\n" +
                        "            group by city_id,week_id \n" +
                        "UNION\n" +
                        "select sum(sale_money),2 as city_id,week_id,'即开票' as game_name from(\n" +
                        "select NVL(sum(sale_money),0) as sale_money ,city_id,week_id,'即开票' as game_name from t_weekreport_sales where\n" +
                        "            year_id=( select to_char(sysdate,'yyyy') from dual) \n" +
                        "            and \n" +
                        "            week_id= ?\n" +
                        "            and game_id = 101\n" +
                        "            and city_id  in (2,6)\n" +
                        "            group by city_id,week_id \n" +
                        ") group by week_id          \n" +
                        "UNION ALL\n" +
                        "select NVL(sum(sale_money),0) as sale_money ,city_id,week_id,'中福在线' as game_name from t_weekreport_sales where\n" +
                        "            year_id=( select to_char(sysdate,'yyyy') from dual) \n" +
                        "            and \n" +
                        "            week_id= ?\n" +
                        "            and game_id = 102\n" +
                        "            and city_id not in (2,6)\n" +
                        "            group by city_id,week_id \n" +
                        "UNION\n" +
                        "select sum(sale_money),2 as city_id,week_id,'中福在线' as game_name from(\n" +
                        "select NVL(sum(sale_money),0) as sale_money ,city_id,week_id,'中福在线' as game_name from t_weekreport_sales where\n" +
                        "            year_id=( select to_char(sysdate,'yyyy') from dual) \n" +
                        "            and \n" +
                        "            week_id= ?\n" +
                        "            and game_id = 102\n" +
                        "            and city_id  in (2,6)\n" +
                        "            group by city_id,week_id \n" +
                        ") group by week_id  \n" +
                        "UNION ALL\n" +
                        "select NVL(sum(sale_money),0) as sale_money ,city_id,week_id,'电脑票' as game_name from t_weekreport_sales where\n" +
                        "            year_id=( select to_char(sysdate,'yyyy') from dual) \n" +
                        "            and \n" +
                        "            week_id= ?\n" +
                        "            and game_id in (1,2,3,4,5,6,7,8,9)\n" +
                        "            and city_id not in (2,6)\n" +
                        "            group by city_id,week_id \n" +
                        "UNION\n" +
                        "select sum(sale_money),2 as city_id,week_id,'电脑票' as game_name from(\n" +
                        "select NVL(sum(sale_money),0) as sale_money ,city_id,week_id,'中福在线' as game_name from t_weekreport_sales where\n" +
                        "            year_id=( select to_char(sysdate,'yyyy') from dual) \n" +
                        "            and \n" +
                        "            week_id= ? \n" +
                        "            and game_id in (1,2,3,4,5,6,7,8,9)\n" +
                        "            and city_id  in (2,6)\n" +
                        "            group by city_id,week_id \n" +
                        ") group by week_id\n" +
                        ") ";
    
    private static final String LUCKY_NO ="select c.game_id,c.draw_id,c.lucky_no,c.lucky_no_echo,c.prize_no,d.game_name,c.draw_name,c.blue_no,c.special_no,c.sales_begin,c.sales_end from\n" +
                        "(\n" +
                        "select a.game_id,a.draw_id,a.lucky_no,a.lucky_no_echo,a.prize_no,a.draw_name,a.blue_no,a.special_no,b.sales_begin,b.sales_end from t_lucky_no a,t_game_draw_info b \n" +
                        "where a.draw_id = b.draw_id \n" +
                        "and a.game_id = b.game_id \n" +
                        "and b.process_status = 500 \n" +
                        "and a.game_id = 1 \n" +
                        "and a.draw_id = (select max(a.draw_id) from t_lucky_no a,t_game_draw_info b where a.game_id = b.game_id and  a.draw_id = b.draw_id and b.process_status = 500 and a.game_id = 1)\n" +
                        "UNION\n" +
                        "select a.game_id,a.draw_id,a.lucky_no,a.lucky_no_echo,a.prize_no,a.draw_name,a.blue_no,a.special_no,b.sales_begin,b.sales_end from t_lucky_no a,t_game_draw_info b \n" +
                        "where a.draw_id = b.draw_id \n" +
                        "and a.game_id = b.game_id \n" +
                        "and b.process_status = 500 \n" +
                        "and a.game_id = 2 \n" +
                        "and a.draw_id = (select max(a.draw_id) from t_lucky_no a,t_game_draw_info b where a.game_id = b.game_id and  a.draw_id = b.draw_id and b.process_status = 500 and a.game_id = 2)\n" +
                        "UNION\n" +
                        "select a.game_id,a.draw_id,a.lucky_no,a.lucky_no_echo,a.prize_no,a.draw_name,a.blue_no,a.special_no,b.sales_begin,b.sales_end from t_lucky_no a,t_game_draw_info b \n" +
                        "where a.draw_id = b.draw_id \n" +
                        "and a.game_id = b.game_id \n" +
                        "and b.process_status = 500 \n" +
                        "and a.game_id = 4 \n" +
                        "and a.draw_id = (select max(a.draw_id) from t_lucky_no a,t_game_draw_info b where a.game_id = b.game_id and  a.draw_id = b.draw_id and b.process_status = 500 and a.game_id = 4)\n" +
                        "UNION\n" +
                        "select a.game_id,a.draw_id,a.lucky_no,a.lucky_no_echo,a.prize_no ,a.draw_name,a.blue_no,a.special_no,b.sales_begin,b.sales_end from t_lucky_no a,t_game_draw_info b \n" +
                        "where a.draw_id = b.draw_id \n" +
                        "and a.game_id = b.game_id \n" +
                        "and b.process_status = 500 \n" +
                        "and a.game_id = 5 \n" +
                        "and a.draw_id = (select max(a.draw_id) from t_lucky_no a,t_game_draw_info b where a.game_id = b.game_id and  a.draw_id = b.draw_id and b.process_status = 500 and a.game_id = 5)\n" +
                        "UNION\n" +
                        "select a.game_id,a.draw_id,a.lucky_no,a.lucky_no_echo,a.prize_no,a.draw_name,a.blue_no,a.special_no,b.sales_begin,b.sales_end from t_lucky_no a,t_game_draw_info b \n" +
                        "where a.draw_id = b.draw_id \n" +
                        "and a.game_id = b.game_id \n" +
                        "and b.process_status = 500 \n" +
                        "and a.game_id = 6 \n" +
                        "and a.draw_id = (select max(a.draw_id) from t_lucky_no a,t_game_draw_info b where a.game_id = b.game_id and  a.draw_id = b.draw_id and b.process_status = 500 and a.game_id = 6)\n" +
                        "UNION\n" +
                        "select a.game_id,a.draw_id,a.lucky_no,a.lucky_no_echo,a.prize_no,a.draw_name,a.blue_no,a.special_no,b.sales_begin,b.sales_end from t_lucky_no a,t_game_draw_info b \n" +
                        "where a.draw_id = b.draw_id \n" +
                        "and a.game_id = b.game_id \n" +
                        "and b.process_status = 500 \n" +
                        "and a.game_id = 7 \n" +
                        "and a.draw_id = (select max(a.draw_id) from t_lucky_no a,t_game_draw_info b where a.game_id = b.game_id and  a.draw_id = b.draw_id and b.process_status = 500 and a.game_id = 7)\n" +
                        ") c,t_game_info d\n" +
                        "where c.game_id = d.game_id\n" +
                        "order by game_id";

    @Override
    public List<WeekReportSales> findWeekReportSaleses(JdbcTemplate jdbcTemplate, String cityId, String yearId) {
        try {
            
            if((null == cityId || cityId.length()==0) && (null == yearId || yearId.length()==0)){
                logger.info("cityId:"+cityId+",yearId:"+yearId);
                Calendar c=Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                return this.queryForList(jdbcTemplate,QUERY_WEEK_REPORT_SALES,new Object[]{year, year, year, year, year, year}, WeekReportSales.class);
            }else if(null != cityId && null == yearId){
                logger.info("cityId:"+cityId+",yearId:"+yearId);
                Calendar c=Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int city = Integer.parseInt(cityId);
                return this.queryForList(jdbcTemplate, QUERY_WEEK_REPORT_SALES_BY_CITY, new Object[]{city, year, city, year, city, year, city, year, city, year, city, year  }, WeekReportSales.class);
            }else if(null != yearId && null == cityId){
                logger.info("cityId:"+cityId+",yearId:"+yearId);
                int year = Integer.parseInt(yearId);
                return this.queryForList(jdbcTemplate, QUERY_WEEK_REPORT_SALES, new Object[]{year, year, year,year,year,year}, WeekReportSales.class);
            }else{
                logger.info("cityId:"+cityId+",yearId:"+yearId);
                int year = Integer.parseInt(yearId);
                int city = Integer.parseInt(cityId);
                return this.queryForList(jdbcTemplate, QUERY_WEEK_REPORT_SALES_BY_CITY, new Object[]{city, year, city, year, city, year, city, year, city, year, city, year  }, WeekReportSales.class);
            }
        } catch (NumberFormatException e) {
            logger.info("findWeekReportSaleses ex:",e);
            return null;    
        }           
    }
    /***
     * 
     * @param jdbcTemplate
     * @param cityId
     * @param type 分为3类（1->电脑票 2->即开票 3->中福在线）
     * @return 
     */
    @Override
    public List<WeekReportSales> findWeekReportSalesesNearWeek(JdbcTemplate jdbcTemplate, String cityId, String type, String weekId) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(QUERY_WEEK_REPORT_SALES_BY_WEEK);
            logger.info("cityId:"+cityId+",type:"+type);
            if(null != cityId && null != type && null != weekId){ 
                int t = Integer.parseInt(type);
                int city = Integer.parseInt(cityId);
                int week = Integer.parseInt(weekId);
                if(t == 1){
                    sql.append(" where city_id = ? and game_name ='电脑票' order by city_id ,game_name");//
                }else if(t == 2){
                    sql.append(" where city_id = ? and game_name ='即开票' order by city_id ,game_name");//
                }else if(t == 3){
                    sql.append(" where city_id = ? and game_name ='中福在线' order by city_id ,game_name");//
                }else{
                    logger.info("request param type is error");
                    return null;
                }
                logger.info("city:"+city+",week:"+week);
                return this.queryForList(jdbcTemplate, sql.toString(), new Object[]{week,week,week,week,week,week,city}, WeekReportSales.class);
            }
            if(null == cityId && null == type && null != weekId){
                int week = Integer.parseInt(weekId);
                sql.append(" order by city_id ,game_name"); 
                logger.info("cityId:"+cityId+",type:"+type);
                return this.queryForList(jdbcTemplate, sql.toString(), new Object[]{week,week,week,week,week,week}, WeekReportSales.class);
            }
            if(null != cityId && null == type){
                int week = Integer.parseInt(weekId);
                logger.info("cityId:"+cityId+",type:"+type);
                sql.append(" where city_id = ? order by city_id ,game_name");
                int city = Integer.parseInt(cityId);
                return this.queryForList(jdbcTemplate, sql.toString(), new Object[]{week,week,week,week,week,week,city}, WeekReportSales.class);
            }
            if(null == cityId && null != type){
                int week = Integer.parseInt(weekId);
                int t = Integer.parseInt(type);
                if(t == 1){
                    sql.append(" where game_name ='电脑票' order by city_id ,game_name");//
                }else if(t == 2){
                    sql.append(" where game_name ='即开票' order by city_id ,game_name");//
                }else if(t == 3){
                    sql.append(" where game_name ='中福在线' order by city_id ,game_name");//
                }else{
                    logger.info("request param type is error");
                }
                logger.info("cityId:"+cityId+",type:"+type+",t:"+t);
                return this.queryForList(jdbcTemplate, sql.toString(), new Object[]{week,week,week,week,week,week,t}, WeekReportSales.class);
            }         
            return null;
       
        } catch (NumberFormatException e) {
            logger.info("findWeekReportSalesesNearWeek ex:",e);
            return null;
        }
    }
    
    /***
     * 提供各彩种（非快开类游戏）最近一期开奖号码
     * @param jdbcTemplate
     * @return 
     */
    @Override
    public List<LuckyNo> findLuckyNo(JdbcTemplate jdbcTemplate) {
        try {
            return this.queryForList(jdbcTemplate, LUCKY_NO, null, LuckyNo.class);
        } catch (Exception e) {
            logger.info("findLuckyNo ex:",e);
            return null;
        }        
    }
    
}
