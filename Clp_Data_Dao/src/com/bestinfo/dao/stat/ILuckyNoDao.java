package com.bestinfo.dao.stat;

import com.bestinfo.bean.stat.LuckyNo;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 开奖号码
 *
 * @author chenliping
 */
public interface ILuckyNoDao {

    /**
     * 获取开奖号码对象
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @param open_id
     * @return
     */
    public LuckyNo getLuckyNo(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id, int open_id);

    /**
     * 增加开奖号码
     *
     * @param jdbcTemplate
     * @param ln
     * @return
     */
    public int addLuckyNo(JdbcTemplate jdbcTemplate, LuckyNo ln);
    /**
     * 增加开奖号码
     *
     * @param jdbcTemplate
     * @param ln
     * @return
     */
    public int addLuckyNoMerge(JdbcTemplate jdbcTemplate, LuckyNo ln);
    
    /**
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    public List<LuckyNo> getLuckNoList(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id);
    /**
     * 获取开奖号码列表
     * @param jdbcTemplate
     * @param game_id
     * @param begin_draw_name
     * @param end_draw_name
     * @return 
     */
    public List<LuckyNo> getLuckyNoList(JdbcTemplate jdbcTemplate, int game_id, String begin_draw_name, String end_draw_name);
 }
