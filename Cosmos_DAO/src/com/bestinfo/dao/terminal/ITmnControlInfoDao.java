/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.terminal;

import com.bestinfo.bean.terminal.TmnControlInfo;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 终端控制参数下载
 *
 * @author Administrator
 */
public interface ITmnControlInfoDao {

    TmnControlInfo getTmnControlInfo(JdbcTemplate jdbcTemplate, int termialId);
}
