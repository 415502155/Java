/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.h5.monitor;

import com.bestinfo.bean.h5.monitor.HMonitorBetsByCity;
import com.bestinfo.bean.h5.monitor.HMonitorGame;
import com.bestinfo.bean.h5.monitor.HMonitorTmnInfo;
import com.bestinfo.dao.page.Pagination;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 监控 接口
 * @author Administrator
 */
public interface IMonitorGameDao {
    /**
     * 游戏监控
     * @return 
     */
    public List<HMonitorGame> getMonitorGameData(JdbcTemplate jdbcTemplate);

    public List<HMonitorBetsByCity> getBetsByCity(JdbcTemplate jdbcTemplate ,int gameId ,int drawId );    
    
    public Pagination<HMonitorTmnInfo> getTmnInfoByCityAndGameIdAndDrawId(JdbcTemplate jdbcTemplate, int gameId, int drawId, int cityId, int terminalId, int sortField, int pageSize, int pageNum);
   
    public int getCurDraw(JdbcTemplate jdbcTemplate);
}
