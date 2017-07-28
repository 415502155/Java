package com.bestinfo.dao.impl.system;

import com.bestinfo.bean.business.TCmsInfo;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.system.IHistoryNoticeDao;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 历史通知
 *
 * @author hhhh
 */
@Repository
public class HistoryNoticeDaoImpl extends BaseDaoImpl implements IHistoryNoticeDao {

//    private static  Logger logger = Logger.getLogger(HistoryNoticeDaoImpl.class);
    /**
     * 查询某个终端有权限看到的在一定时间范围内的所有通知
     */
    private static final String selectNoticeByConditions = "select cms_id,release_time from (select distinct a.cms_id,a.release_time from t_cms_info a, t_cms_privilege b, t_tmn_info f where a.cms_type in (?,?) and a.cms_id = b.cms_id and ((b.cms_range = ? and b.receiving_object = to_char(f.city_id) and f.terminal_id = ?) or (b.cms_range = ? and b.receiving_object = ?) or b.cms_range = ?) and b.work_status = ? and a.release_time between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_char(a.deadline,'yyyy-mm-dd') >= to_char(sysdate,'yyyy-mm-dd') order by a.release_time desc) WHERE ROWNUM <= 30";

    /**
     * 根据主键查询内容
     */
    private static final String selectNoticeById = "select cms_id,release_time,cms_type,cms_title,bulletin_len,cms_filename,keys,work_status,deadline,version from t_cms_info where cms_id = ?";

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
//    @Override
//    public List<TCmsInfo> selectHistoryNotice(JdbcTemplate jdbcTemplate, String startTime, String endTime, List<Integer> cmsRange, List<Integer> cmsType) {
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("SELECT a.cms_id,a.cms_type,a.version,a.release_time,a.bulletin_len,a.cms_filename FROM T_CMS_INFO a,T_CMS_PRIVILEGE b "
//                + "where RELEASE_TIME between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd') "
//                + "AND a.cms_id = b.cms_id ");
//        if (cmsRange != null && !cmsRange.isEmpty()) {
//            if (cmsRange.size() <= 1000) {
//                sb.append("AND b.cms_range in (");
//                sb.append(StringUtils.join(cmsRange, ","));
//                sb.append(") ");
//            }
//        }
//
//        if (cmsType != null && !cmsType.isEmpty()) {
//            if (cmsType.size() <= 1000) {
//                sb.append(" AND a.cms_type in (");
//                sb.append(StringUtils.join(cmsType, ","));
//                sb.append(") ");
//            }
//        }
//
//        return this.queryForList(jdbcTemplate, sb.toString(), new Object[]{startTime, endTime}, TCmsInfo.class);
//    }
    /**
     * 查询某个终端有权限看到的在一定时间范围内的所有通知
     * @param jdbcTemplate
     * @param startTime
     * @param endTime
     * @param cmsRange1 地市
     * @param cmsRange2 单个终端的
     * @param cmsType1
     * @param cmsType2
     * @param terminalId
     * @param workstatus
     * @param cmsRange3 全系统
     * @return 
     */
    @Override
    public List<TCmsInfo> selectHistoryNoticeByConditions(JdbcTemplate jdbcTemplate, String startTime, String endTime, int cmsRange1, int cmsRange2, int cmsType1, int cmsType2, int terminalId, int workstatus, int cmsRange3) {
        return this.queryForList(jdbcTemplate, selectNoticeByConditions, new Object[]{cmsType1, cmsType2, cmsRange1, terminalId, cmsRange2, terminalId + "", cmsRange3, workstatus, startTime, endTime}, TCmsInfo.class);
    }

    /**
     * 根据主键查询内容
     *
     * @param jdbcTemplate
     * @param cmsId
     * @return
     */
    @Override
    public TCmsInfo getCmsInfoById(JdbcTemplate jdbcTemplate, int cmsId) {
        return this.queryForObject(jdbcTemplate, selectNoticeById, new Object[]{cmsId}, TCmsInfo.class);
    }

}
