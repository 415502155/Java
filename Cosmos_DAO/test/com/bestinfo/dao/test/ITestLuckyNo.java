package com.bestinfo.dao.test;

import com.bestinfo.bean.test.TestLuckyNo;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 游戏投注方式接口
 *
 * @author shange
 */
public interface ITestLuckyNo {

    /**
     * 获取开奖号码
     *
     * @param jdbcTemplate
     * @return
     */
    public TestLuckyNo getLuckyNo(JdbcTemplate jdbcTemplate);
}
