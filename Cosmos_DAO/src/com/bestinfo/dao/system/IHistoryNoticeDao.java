package com.bestinfo.dao.system;

import com.bestinfo.bean.business.TCmsInfo;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 历史通知
 *
 * @author hhhh
 */
public interface IHistoryNoticeDao {

    /**
     * 查询历史通知的数据列表 （ 返回结果要加“发布范围”的限制； 结果集要注意的地方： 第一个是这个权限：
     * 某个终端编号、某个地市下全部终端、某种终端类型、内容不能过期 第二个是时间，不能无限的全部都查 第三个受终端界面或者报文长度限制，每次只能N条 ）
     *
     * @param jdbcTemplate
     * @param startTime 开始时间
     * @param endTime 截止时间
     * @param cmsRange 发布范围
     * @param cmsType 通知类型
     * @return 符合条件的集合
     */
//    public List<TCmsInfo> selectHistoryNotice(JdbcTemplate jdbcTemplate, String startTime, String endTime, List<Integer> cmsRange, List<Integer> cmsType);

   /**
    * 查询某个终端有权限看到的在一定时间范围内的所有通知
    * @param jdbcTemplate
    * @param startTime
    * @param endTime
    * @param cmsRange1
    * @param cmsRange2
    * @param cmsType1
    * @param cmsType2
    * @param terminalId
    * @param workstatus
    * @param cmsRange3
    * @return 
    */
    public List<TCmsInfo> selectHistoryNoticeByConditions(JdbcTemplate jdbcTemplate, String startTime, String endTime, int cmsRange1, int cmsRange2, int cmsType1, int cmsType2, int terminalId,int workstatus, int cmsRange3);
    
    /**
     * 根据主键查询内容
     * @param jdbcTemplate
     * @param cmsId
     * @return 
     */
    public TCmsInfo getCmsInfoById(JdbcTemplate jdbcTemplate, int cmsId);
}
