/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.impl.game;

import com.bestinfo.bean.game.TicketBulletin;
import com.bestinfo.dao.game.ITicketBulletinDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 彩票公告 查询t_ticket_bulletin表
 *
 * @author YangRong
 */
@Repository
public class TicketBulletinDaoImpl extends BaseDaoImpl implements ITicketBulletinDao {

    private static final String GET_BULLETIN_BY_WORKSTATUS = "select game_id,line_no,bulletin,bulletin_len,work_status from t_ticket_bulletin where work_status = ?  order by game_id,line_no ";

    /**
     * 获取work_status =1 的彩票公告列表 ,order by game_id,line_no
     *
     * @param jdbcTemplate
     * @param workStaus
     * @return
     */
    @Override
    public List<TicketBulletin> getWorkBulletinWithOrder(JdbcTemplate jdbcTemplate, int workStaus) {
        return this.queryForList(jdbcTemplate, GET_BULLETIN_BY_WORKSTATUS, new Object[]{workStaus}, TicketBulletin.class);
    }
}
