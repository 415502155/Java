package com.bestinfo.dao.task;

import com.bestinfo.bean.task.TaskPlan;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 结算统计表--任务计划及执行结果
 * @author zyk
 */
public interface ITaskPlanDao {

    /**
     * 根据任务编号计划编号获取任务计划
     * 
     * @param jdbcTemplate
     * @param taskId
     * @param planId
     * @return 
     */
    public TaskPlan getTaskPlanByTaskIdAndPlanId(JdbcTemplate jdbcTemplate, int taskId, int planId);
    /**
     * 取当月的第一天和最后一天
     * @param jdbcTemplate
     * @return  返回三列，一行三列的一维数组，第一列是年月，第二列是当月第一天，第三列是当月最后一天
     */
    public Object [] getFirstAndLastDay(JdbcTemplate jdbcTemplate,boolean nextMonth);
    /**
     * 插入数据 任务计划及执行结果表
     * @param jdbcTemplate
     * @param args
     * @return 
     */
    public int insertIntoTaskPlan(JdbcTemplate jdbcTemplate,Object[] args);
    /**
     * 根据任务id更改当前要执行的计划编号
     * @param jdbcTemplate
     * @param args
     * @return 
     */
    public int updateTaskInfoCurPlanID(JdbcTemplate jdbcTemplate,Object[] args);
    /**
     * 根据计划编号更新 执行状态、影响投注机数量和扣除金额
     * @param jdbcTemplate
     * @param args
     * @return 
     */
    public int updateTaskPlanStauts(JdbcTemplate jdbcTemplate, Object[] args);
    /**
     * 根据任务id查询交易类型
     * @param jdbcTemplate
     * @param args
     * @return 
     */
    public int selectTradeType(JdbcTemplate jdbcTemplate, Object[] args);
    /**
     * 根据任务id查询任务名称
     * @param jdbcTemplate
     * @param args
     * @return 
     */
    public String selectTaskName(JdbcTemplate jdbcTemplate, Object[] args);
    
     /** 扣款账户的项目信息，终端号，扣款账户，账户余额，扣除金额，账户序列号**/
    public List<Object[]> selectTmnAccInfoWork0(JdbcTemplate jdbcTemplate, Object[] args);
    /**  更新账户流水号**/
    public int  updateAccoutSerialNo(JdbcTemplate jdbcTemplate, Object[] args);
    /**
     * 批量根据账户id更新账户流水号
     * @param jdbcTemplate
     * @param list 
     */
    public void batchUpdateAccoutSerialNo(JdbcTemplate jdbcTemplate, final List<Object[]> list);
     
    /**  更新账户余额***/
    public int  updateAccoutAccBalance(JdbcTemplate jdbcTemplate, Object[] args);
    /**
     * 批量根据账户id更新余额
     * @param jdbcTemplate
     * @param list 
     */
    public void batchUpdateAccoutAccBalance(JdbcTemplate jdbcTemplate, final List<Object[]> list);
   
    /**
     * 插入账户明细记录
     * @param jdbcTemplate
     * @param args
     * @return 
     */
    public int  insertAccDetail(JdbcTemplate jdbcTemplate, Object[] args);
    /**
     * 批量插入账户明细记录
     * @param jdbc
     * @param list
     * @param taskName 
     */
    public void batchInsertAccDetail(JdbcTemplate jdbc,List<Object[]> list,String taskName );
    /**
     *  查询需要扣费的账户信息
     * @param jdbcTemplate
     * @param args
     * @return 
     */
    public List<Object[]> selectTmnAccInfoWork1(JdbcTemplate jdbcTemplate, Object[] args);
}
