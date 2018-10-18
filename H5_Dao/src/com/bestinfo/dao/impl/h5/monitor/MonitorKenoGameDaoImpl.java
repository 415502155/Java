/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.impl.h5.monitor;

import com.bestinfo.bean.h5.monitor.HMonitorGame;
import com.bestinfo.bean.h5.monitor.HMonitorKenoGame;
import com.bestinfo.bean.h5.monitor.HMonitorKenoGamePrize;
import com.bestinfo.dao.h5.monitor.IMonitorKenoGameDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 快开游戏监控实现类
 *
 * @author Administrator
 */
@Repository
public class MonitorKenoGameDaoImpl extends BaseDaoImpl implements IMonitorKenoGameDao {

    private static final String GET_MONITOR_KENO_GAME_DATA = "SELECT A.GAME_ID,--游戏编号"
            + "B.GAME_NAME,--游戏名称"
            + "A.KENO_DRAW_ID,--当前快开期号"
            + "A.KENO_DRAW_NAME,--当前快开期名"
            + "TO_CHAR(C.CASH_BEGIN,'hh24:mi') AS CASH_BEGIN,--开奖时间"
            + "D.LUCKY_NO AS LAST_LUCKY_NO--开奖号码"
            + "FROM T_GAME_KDRAW_INFO A,T_GAME_INFO B,T_GAME_DRAW_INFO C,T_LUCKY_NO D"
            + "WHERE A.GAME_ID = B.GAME_ID"
            + "AND A.GAME_ID IN (7,9)"
            + "AND A.KENO_PROCESS_STATUS = 30"
            + "AND SYSDATE BETWEEN A.SALES_BEGIN AND A.SALES_END"
            + "AND A.GAME_ID = C.GAME_ID"
            + "AND A.DRAW_ID = C.DRAW_ID"
            + "AND SUBSTR(A.KENO_DRAW_NAME,1,8) = C.DRAW_NAME"
            + "AND A.GAME_ID = D.GAME_ID"
            + "AND A.KENO_DRAW_ID -1 = D.KENO_DRAW_ID";
    private static final String GET_MINITOR_KENO_GAME_PRIZE_DATA_SCATTER = "SELECT a.game_id as game_id,"
            + "a.draw_id,"
            + "a.terminal_id,"
            + "b.city_id,"
            + "b.city_name,"
            + "a.total_prize,"
            + "a.keno_draw_id,"
            + "d.game_name "
            + "FROM t_ticket_prize a,t_city_info b,t_tmn_info c,t_game_info d "
            + "where a.terminal_id=c.terminal_id "
            + "and b.city_id=c.city_id "
            + "and a.game_id=d.game_id "
            + "and a.game_id in (7,9)"
            + "and a.keno_draw_id in "
            + "(select keno_draw_id "
            + "from  t_game_kdraw_info "
            + "where sales_begin >to_date('2016-11-01 17:00:00','yyyy-mm-dd hh24:mi:ss') "
            + "and sales_end<to_date('2016-11-01 18:00:00','yyyy-mm-dd hh24:mi:ss') "
            + "and game_id in (7,9)) "
            + "and a.total_prize>100 "
            + "and rownum<=5 "
            + "order by total_prize desc";
    //游戏7或9今天是否有异常
    private static final String GAME_7_9_IS_EXCEPTION = "select count(*) from T_GAME_KDRAW_INFO  where"
            + "  sysdate>sales_end and sales_end>(select trunc(sysdate)+1/(24*60*60) early_time from dual)  and keno_process_status<500 and game_id =?";
    //当前期的期信息
    private static final String CUR_DRAW_INFO_7_9 = "select TO_CHAR(CASH_BEGIN,'hh24:mi') AS CASH_BEGIN,"
            + "            KENO_DRAW_NAME,"
            + "            game_id,"
            + "            draw_id,"
            + "            keno_draw_id  "
            + " from T_GAME_KDRAW_INFO where game_id in(7,9) AND SYSDATE BETWEEN SALES_BEGIN AND SALES_END ";
    //游戏7或9当前期的上一期
    private static final String LAST_DRAW_INFO = "select keno_draw_id-2 as keno_draw_id   from T_GAME_KDRAW_INFO where game_id=? AND SYSDATE BETWEEN SALES_BEGIN AND SALES_END ";
    //上一期的幸运号码
    private static final String LUCKY_NO = " select game_id,NVL(lucky_no,'0') as lucky_no from  T_LUCKY_NO  where keno_draw_id=? and game_id=?";

    private static final String LUCKY_NO1 = "select game_id, NVL(lucky_no, '0') as last_lucky_no"
            + "  from T_LUCKY_NO a"
            + " where a.keno_draw_id = (select keno_draw_id"
            + "                           from (select keno_draw_id"
            + "                                   from T_GAME_KDRAW_INFO"
            + "                                  where game_id = ?"
            + "                                    AND keno_process_status = 500"
            + "                                  order by keno_draw_id desc)"
            + "                          where rownum = 1)"
            + "   and game_id = ?";
    //当前天游戏ID为7或9的中奖信息
    private static final String CUR_DAY_DRAW_LIST="select b.keno_draw_id,nvl(a.sale_money,-1) as sale_money,\n" +
            "TO_CHAR(b.sales_begin,'hh24:mi') AS sales_begin,TO_CHAR(b.sales_end,'hh24:mi') AS sales_end,\n" +
            "b.draw_id from (select t.keno_draw_id,sum(sale_money)  sale_money  from T_CURRENT_KDRAW_STAT t  \n" +
            "where game_id= ? and draw_id=? group by t.keno_draw_id order by t.keno_draw_id) a \n" +
            ",(select sales_begin,sales_end,draw_id,keno_draw_id from  \n" +
            "T_GAME_KDRAW_INFO  where game_id=? and draw_id=?    ) b\n" +
            "where a.keno_draw_id(+)=b.keno_draw_id order by sales_begin";
   //今日累计销量(快乐十分7和快乐彩9)
    private static final String CUR_DAY_DRAW_SUM_SALE="select sum(sale_money) as salemoney from (select t.keno_draw_id,sum(sale_money)  sale_money  from T_CURRENT_KDRAW_STAT t  where game_id= ? and draw_id=? group by t.keno_draw_id order by t.keno_draw_id) a \n" +
            ",(select sales_begin,sales_end,draw_id,keno_draw_id from    T_GAME_KDRAW_INFO  where game_id=? and draw_id=?   and keno_process_status=500) b\n" +
            "where a.keno_draw_id=b.keno_draw_id ";
    
    
    @Override
    public List<HMonitorKenoGame> getMonitorKenoGameData(JdbcTemplate jdbcTemplate) {
        return queryForList(jdbcTemplate, GET_MONITOR_KENO_GAME_DATA, new Object[]{}, HMonitorKenoGame.class);
    }

    @Override
    public List<HMonitorKenoGamePrize> getMonitorKenoGamePrizeData(JdbcTemplate jdbcTemplate) {
        return queryForList(jdbcTemplate, GET_MINITOR_KENO_GAME_PRIZE_DATA_SCATTER, new Object[]{}, HMonitorKenoGamePrize.class);
    }

    @Override
    public List<HMonitorKenoGame> getMonitorKenoGameCurDrawData(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, CUR_DRAW_INFO_7_9, null, HMonitorKenoGame.class);
    }

    @Override
    public int getMonitorKenoGameCurDrawIsException(JdbcTemplate jdbcTemplate, Integer game_id) {
        return this.queryForInteger(jdbcTemplate, GAME_7_9_IS_EXCEPTION, new Object[]{game_id});
    }

    @Override
    public int getMonitorKenoGameLastDraw(JdbcTemplate jdbcTemplate, Integer game_id) {
        return this.queryForInteger(jdbcTemplate, LAST_DRAW_INFO, new Object[]{game_id});
    }

    @Override
    public List<HMonitorKenoGame> getMonitorKenoGameLuckyNo(JdbcTemplate jdbcTemplate, Integer game_id) {
        return this.queryForList(jdbcTemplate, LUCKY_NO1, new Object[]{game_id, game_id}, HMonitorKenoGame.class);
    }

    @Override
    public List<HMonitorGame> getHMonitorGameList(JdbcTemplate jdbcTemplate, Integer drawId, Integer gameId) {
        return this.queryForList(jdbcTemplate, CUR_DAY_DRAW_LIST, new Object[]{gameId,drawId,gameId,drawId}, HMonitorGame.class);
    }

    @Override
    public Integer getHMonitorGameCurDaySumSale(JdbcTemplate jdbcTemplate, Integer drawId, Integer gameId) {
        return this.queryForInteger(jdbcTemplate, CUR_DAY_DRAW_SUM_SALE, new Object[]{gameId,drawId,gameId,drawId});
    }

}
