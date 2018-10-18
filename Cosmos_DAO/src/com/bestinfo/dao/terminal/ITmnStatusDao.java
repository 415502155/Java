package com.bestinfo.dao.terminal;

import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.bean.terminal.SyncTmnRank;
import com.bestinfo.bean.terminal.TerminalRank;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 投注机表 T_TMN_INFO
 *
 */
public interface ITmnStatusDao {

    /**
     *
     * @param jdbcTemplate
     * @param executeStatus
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<SyncTmnRank> getSyncTmnRanks(JdbcTemplate jdbcTemplate, String beginTime, String endTime, int executeStatus);

    public int batchUpdateTmnExecuteStatus(JdbcTemplate jdbcTemplate, List<SyncTmnRank> strList);
 
    public List<TmnInfo> getTmnInfos(JdbcTemplate jdbcTemplate, String beginTime, String endTime);
    
    public int batchUpdateTmnStatus(JdbcTemplate jdbcTemplate, List<TmnInfo> tiList);

    public List<SyncTmnRank> getCurDaySyncTmnRanks(JdbcTemplate jdbcTemplate, String beginTime, String endTime, int executeStatus);

    public List<TmnInfo> getCurDayTmnInfos(JdbcTemplate jdbcTemplate, String beginTime, String endTime);

    public int batchUpdateTmnStationRank(JdbcTemplate jdbcTemplate,List<SyncTmnRank> strList);
    
    public int batchInsertTmnStationRank(JdbcTemplate jdbcTemplate, TerminalRank tr, int t_len);
}
