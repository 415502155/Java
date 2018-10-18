package com.bestinfo.dao.impl.task;

import com.bestinfo.bean.task.TaskPlan;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.task.ITaskPlanDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

/**
 * 结算统计表--任务计划及执行结果
 *
 * @author zyk
 */
@Repository
public class TaskPlanDaoImpl extends BaseDaoImpl implements ITaskPlanDao {

//    private final Logger logger = Logger.getLogger(TaskPlanDaoImpl.class);
    private static final String SELECT_TASKPLAN_BY_TASKIDPLANID = "select task_id,plan_id,plan_name,begin_date,end_date,stat_date,plan_status,user_id,terminal_count,total_money from t_task_plan where task_id = ? and plan_id = ? ";

    private static final String SELECT_FIRSTDAY_LASTDAY = "select yearmonth_id,begin_date,end_date from t_month_info where yearmonth_id = to_char(sysdate,'yyyymm')";

    private static final String SELECT_FIRSTDAY_LASTDAY_NEXT_MONTH = "select yearmonth_id,begin_date,end_date from t_month_info where yearmonth_id = to_char(add_months(sysdate,1),'yyyymm')";

    private static final String INSERT_TASK_PLAN = "insert into t_task_plan(task_id,plan_id,plan_name,begin_date,end_date,stat_date,plan_status,user_id,terminal_count,total_money) values(?,?,?,?,?,?,?,?,?,?)";

    private static final String UPDATE_TASK_INFO_CUR_PLAN_ID = "update t_task_info set cur_plan_id=? where task_id=?";

    private static final String UPDATE_TASK_PLAN_STATUS = "update t_task_plan set plan_status=1, terminal_count=?, total_money=?,stat_date=? where task_id=? and plan_id=?";

    private static final String SELECT_TRADE_TYPE = "select  trade_type from  t_task_info  where task_id=?";

    private static final String SELECT_TASK_NAME = "select  task_name from  t_task_info  where task_id=?";

    /**
     * 扣款账户的项目信息，终端号，扣款账户，账户余额，扣除金额，账户序列号*
     */
    private static final String SELECT_TERMINAL_ACC_INFO_WORK0 = "select c.account_serial_no,b.account_id,a.trade_type,c.acc_balance,a.deduct_money,a.terminal_id from t_tmn_auto_deduction a,t_tmn_info b ,t_account_info c where a.terminal_id = b.terminal_id and a.trade_type = ? and b.terminal_status = 0 and c.account_id = b.account_id and a.work_status = 1 ";

    private static final String UPDATE_ACCOUNT_SERIAL_NO = " update  t_account_info set account_serial_no=? where account_id=? ";

    private static final String UPDATE_ACCOUNT_ACC_BALANCE = " update  t_account_info set acc_balance=? where account_id=? ";

    private static final String INSERT_ACCOUNT_DETAIL = "insert into t_account_detail(account_serial_no,account_id,operator_id,trade_time,trade_type,source_type,recharge_source,pre_money,income_money,out_money,acc_balance,bank_serial_no,scheme_id,trade_note) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * 定时扣款查询语句，帐号类型是在用或冻结的 账户id、扣款金额、扣款类型
     */
    private final String SELECT_TERMINAL_ACC_INFO_WORK1 = "select b.account_id,a.deduct_money,a.trade_type, b.terminal_id from t_tmn_auto_deduction a,t_tmn_info b ,t_account_info c where a.terminal_id = b.terminal_id and c.account_id = b.account_id and a.trade_type = ? and a.work_status = 1 and (c.account_status = 1 or c.account_status = 2)";

    /**
     * 根据任务编号计划编号获取任务计划
     *
     * @param jdbcTemplate
     * @param taskId
     * @param planId
     * @return
     */
    @Override
    public TaskPlan getTaskPlanByTaskIdAndPlanId(JdbcTemplate jdbcTemplate, int taskId, int planId) {
        return this.queryForObject(
                jdbcTemplate,
                SELECT_TASKPLAN_BY_TASKIDPLANID,
                new Object[]{taskId, planId},
                TaskPlan.class);
    }

    /**
     * 取当月的第一天和最后一天
     *
     * @param jdbcTemplate
     * @param nextMonth
     * @return
     */
    @Override
    public Object[] getFirstAndLastDay(JdbcTemplate jdbcTemplate, boolean nextMonth) {
//        Object[] args = {};

        String sql = "";
        if (nextMonth) {
            sql = SELECT_FIRSTDAY_LASTDAY_NEXT_MONTH;
        } else {
            sql = SELECT_FIRSTDAY_LASTDAY;
        }
        List rows = jdbcTemplate.queryForList(sql);
        Iterator it = rows.iterator();
        if (it.hasNext()) {
            Map result = (Map) it.next();
            Object[] firstAndLastDay = new Object[result.size()];
            firstAndLastDay[0] = result.get("YEARMONTH_ID");
            firstAndLastDay[1] = result.get("BEGIN_DATE");
            firstAndLastDay[2] = result.get("END_DATE");
            return firstAndLastDay;
        }

//        ResultBean rb = DBManager.executeSQL(sql, args, jdbcTemplate);
//        Object[][] datas = rb.getColumnValuesArray();
//        Object[] firstAndLastDay = new Object[datas[0].length];
//        firstAndLastDay[0] = datas[0][0];
//        firstAndLastDay[1] = datas[0][1];
//        firstAndLastDay[2] = datas[0][2];
        return new Object[0];
    }

    /**
     * 插入数据 任务计划及执行结果表
     *
     * @param jdbcTemplate
     * @param args
     * @return
     */
    @Override
    public int insertIntoTaskPlan(JdbcTemplate jdbcTemplate, Object[] args) {
        return jdbcTemplate.update(INSERT_TASK_PLAN, args);
    }

    /**
     * 根据任务id更改当前要执行的计划编号
     *
     * @param jdbcTemplate
     * @param args
     * @return
     */
    @Override
    public int updateTaskInfoCurPlanID(JdbcTemplate jdbcTemplate, Object[] args) {
        return jdbcTemplate.update(UPDATE_TASK_INFO_CUR_PLAN_ID, args);
    }

    /**
     * 根据计划编号更新 执行状态、影响投注机数量和扣除金额
     *
     * @param jdbcTemplate
     * @param args
     * @return
     */
    @Override
    public int updateTaskPlanStauts(JdbcTemplate jdbcTemplate, Object[] args) {
        int re = 0;
        try{
            re = jdbcTemplate.update(UPDATE_TASK_PLAN_STATUS, args);
            return re;
        }catch(Exception ex){
            logger.error("ex :",ex);
            return -1;
        }
    }

    /**
     * 根据任务id查询交易类型
     *
     * @param jdbcTemplate
     * @param args
     * @return
     */
    @Override
    public int selectTradeType(JdbcTemplate jdbcTemplate, Object[] args) {
        String trade_type = jdbcTemplate.queryForObject(SELECT_TRADE_TYPE, args, String.class);
        return Integer.parseInt(trade_type);
//        ResultBean rb = DBManager.executeSQL(TaskPlanSql.SELECT_TRADE_TYPE, args, jdbcTemplate);
//        return Integer.parseInt(rb.getFirstObject().toString());
    }

    /**
     * 根据任务id查询任务名称
     *
     * @param jdbcTemplate
     * @param args
     * @return
     */
    @Override
    public String selectTaskName(JdbcTemplate jdbcTemplate, Object[] args) {
//        ResultBean rb = DBManager.executeSQL(TaskPlanSql.SELECT_TASK_NAME, args, jdbcTemplate);
        return jdbcTemplate.queryForObject(SELECT_TASK_NAME, args, String.class);
    }

    /**
     * 根据交易类型，查询需要扣费的账户信息等
     *
     * @param jdbcTemplate
     * @param args
     * @return
     */
    @Override
    public List<Object[]> selectTmnAccInfoWork0(JdbcTemplate jdbcTemplate, Object[] args) {
        List rows = jdbcTemplate.queryForList(SELECT_TERMINAL_ACC_INFO_WORK0, args);
        Iterator it = rows.iterator();
        List<Object[]> tmnAccInfos = new ArrayList();
        while (it.hasNext()) {
            Map result = (Map) it.next();
            Object[] firstAndLastDay = new Object[result.size()];
            firstAndLastDay[0] = result.get("ACCOUNT_SERIAL_NO");
            firstAndLastDay[1] = result.get("ACCOUNT_ID");
            firstAndLastDay[2] = result.get("TRADE_TYPE");
            firstAndLastDay[3] = result.get("ACC_BALANCE");
            firstAndLastDay[4] = result.get("DEDUCT_MONEY");
            firstAndLastDay[5] = result.get("TERMINAL_ID");
//            return firstAndLastDay;
            tmnAccInfos.add(firstAndLastDay);
        }
        return tmnAccInfos;
//        return DBManager.executeSQL(TaskPlanSql.SELECT_TERMINAL_ACC_INFO_WORK0, args, jdbcTemplate).getColumnValues();
    }

    /**
     * 根据账户id更新账户流水号
     *
     * @param jdbcTemplate
     * @param args
     * @return
     */
    @Override
    public int updateAccoutSerialNo(JdbcTemplate jdbcTemplate, Object[] args) {
        return jdbcTemplate.update(UPDATE_ACCOUNT_SERIAL_NO, args);
    }

    /**
     * 批量根据账户id更新账户流水号
     *
     * @param jdbcTemplate
     * @param list
     */
    @Override
    public void batchUpdateAccoutSerialNo(JdbcTemplate jdbcTemplate, final List<Object[]> list) {
        final int count = list.size();
        String sql = UPDATE_ACCOUNT_SERIAL_NO;
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                // ACCOUNT_SERIAL_NO    ACCOUNT_ID  TRADE_TIME   TRADE_TYPE  PRE_MONEY    ACC_BALANCE
                //   number 18           char 24     date          number 8    number20    number20
                //  update  t_account_info set account_serial_no=? where account_id=?
                //   SELECT C.ACCOUNT_SERIAL_NO, B.ACCOUNT_ID, A.TRADE_TYPE, C.ACC_BALANCE,  A.DEDUCT_MONEY,  A.TERMINAL_ID

                Object[] obj = list.get(i);

                ps.setLong(1, Long.parseLong(obj[0].toString()) + 1L);
                ps.setString(2, obj[1].toString());
            }

            @Override
            public int getBatchSize() {
                return count;
            }
        });
    }

    /**
     * 根据账户id更新余额
     *
     * @param jdbcTemplate
     * @param args
     * @return
     */
    @Override
    public int updateAccoutAccBalance(JdbcTemplate jdbcTemplate, Object[] args) {
        return jdbcTemplate.update(UPDATE_ACCOUNT_ACC_BALANCE, args);
    }

    /**
     * 批量根据账户id更新余额
     *
     * @param jdbcTemplate
     * @param list
     */
    @Override
    public void batchUpdateAccoutAccBalance(JdbcTemplate jdbcTemplate, final List<Object[]> list) {
        final int count = list.size();
        String sql = UPDATE_ACCOUNT_ACC_BALANCE;
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                // ACCOUNT_SERIAL_NO    ACCOUNT_ID  TRADE_TIME   TRADE_TYPE  PRE_MONEY    ACC_BALANCE
                //   number 18           char 24     date          number 8    number20    number20
                //  update  t_account_info set acc_balance=? where account_id=?
                //   SELECT C.ACCOUNT_SERIAL_NO, B.ACCOUNT_ID, A.TRADE_TYPE, C.ACC_BALANCE,  A.DEDUCT_MONEY,  A.TERMINAL_ID

                Object[] obj = list.get(i);

                BigDecimal ACC_BALANCE = new BigDecimal(Double.valueOf(obj[3].toString()));
                BigDecimal DEDUCT_MONEY = new BigDecimal(Double.valueOf(obj[4].toString()));
                BigDecimal ACC_BALANCE_NEW = ACC_BALANCE.subtract(DEDUCT_MONEY);

                ps.setBigDecimal(1, ACC_BALANCE_NEW);
                ps.setString(2, obj[1].toString());
            }

            @Override
            public int getBatchSize() {
                return count;
            }
        });
    }

    /**
     * 插入账户明细记录
     *
     * @param jdbcTemplate
     * @param args
     * @return
     */
    @Override
    public int insertAccDetail(JdbcTemplate jdbcTemplate, Object[] args) {
        return jdbcTemplate.update(INSERT_ACCOUNT_DETAIL, args);
        //update  t_account_info set acc_balance=? where account_id=?
    }

    /**
     * 批量插入账户明细记录
     *
     * @param jdbcTemplate
     * @param list
     * @param taskName
     */
    @Override
    public void batchInsertAccDetail(JdbcTemplate jdbcTemplate, final List<Object[]> list, String taskName) {
        final String taskNameNew = taskName;
        final int count = list.size();
        logger.info("count = " + count);
        for (Object[] datas : list) {
            logger.info(datas[0] + " " + datas[1] + " " + datas[2] + " " + datas[3] + " " + datas[4] + " " + datas[5]);
        }
        String sql = INSERT_ACCOUNT_DETAIL;
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                // ACCOUNT_SERIAL_NO    ACCOUNT_ID  TRADE_TIME   TRADE_TYPE  PRE_MONEY    ACC_BALANCE
                //   number 18           char 24     date          number 8    number20    number20
                //   SELECT C.ACCOUNT_SERIAL_NO,B.ACCOUNT_ID,A.TRADE_TYPE,C.ACC_BALANCE,A.DEDUCT_MONEY,A.TERMINAL_ID
//                           ACCOUNT_SERIAL_NO,  ACCOUNT_ID,
//                            OPERATOR_ID,  TRADE_TIME, TRADE_TYPE,
//                            SOURCE_TYPE,  RECHARGE_SOURCE,
//                            PRE_MONEY, INCOME_MONEY,
//                            OUT_MONEY, ACC_BALANCE,BANK_SERIAL_NO,
//                            SCHEME_ID,  TRADE_NOTE
                Object[] obj = list.get(i);
                logger.info(Long.parseLong(obj[0].toString()) + " " + obj[1].toString() + " " + obj[2] + " " + obj[3] + " " + obj[4] + " " + obj[5]);

                BigDecimal ACC_BALANCE = new BigDecimal(Double.valueOf(obj[3].toString()));
                BigDecimal DEDUCT_MONEY = new BigDecimal(Double.valueOf(obj[4].toString()));
                BigDecimal ACC_BALANCE_NEW = ACC_BALANCE.subtract(DEDUCT_MONEY);

                ps.setLong(1, Long.parseLong(obj[0].toString()));
                ps.setString(2, obj[1].toString());
                ps.setInt(3, 0);
                ps.setDate(4, new Date(System.currentTimeMillis()));
                ps.setInt(5, Integer.parseInt(obj[2].toString()));
                ps.setString(6, "0");
                ps.setString(7, "0");
                ps.setBigDecimal(8, BigDecimal.valueOf(Double.valueOf(obj[3].toString())));
                ps.setInt(9, 0);
                ps.setBigDecimal(10, DEDUCT_MONEY);
                ps.setBigDecimal(11, ACC_BALANCE_NEW);
                ps.setString(12, "0");
                ps.setString(13, "0");
                ps.setString(14, taskNameNew);
            }

            @Override
            public int getBatchSize() {
                return count;
            }

        });
    }

    /**
     * 查询需要扣费的账户信息
     *
     * @param jdbcTemplate
     * @param args
     * @return
     */
    @Override
    public List<Object[]> selectTmnAccInfoWork1(JdbcTemplate jdbcTemplate, Object[] args) {
        List rows = jdbcTemplate.queryForList(SELECT_TERMINAL_ACC_INFO_WORK1, args);
        Iterator it = rows.iterator();
        List<Object[]> tmnAccInfos = new ArrayList();
        while (it.hasNext()) {
            Map result = (Map) it.next();
            Object[] firstAndLastDay = new Object[result.size()];
            firstAndLastDay[0] = result.get("ACCOUNT_ID");
            firstAndLastDay[1] = result.get("DEDUCT_MONEY");
            firstAndLastDay[2] = result.get("TRADE_TYPE");
            firstAndLastDay[3] = result.get("TERMINAL_ID");
//            return firstAndLastDay;
            tmnAccInfos.add(firstAndLastDay);
        }
        return tmnAccInfos;
//        return DBManager.executeSQL(TaskPlanSql.SELECT_TERMINAL_ACC_INFO_WORK1, args, jdbcTemplate).getColumnValues();
    }
}
