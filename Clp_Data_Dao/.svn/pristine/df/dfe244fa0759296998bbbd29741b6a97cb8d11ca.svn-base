package com.bestinfo.dao.terminal;

import com.bestinfo.bean.terminal.SynCodeInfo;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 信息-同步字列表(T_syn_list)
 *
 * @author YangRong
 */
public interface ISynCodeInfoDao {

    /**
     * 得到同步字列表数据
     *
     * @param jdbcTemplate
     * @return
     */
    public List<SynCodeInfo> getSynCodeList(JdbcTemplate jdbcTemplate);

    public List<SynCodeInfo> getSynCodeListA(JdbcTemplate jdbcTemplate);

    /**
     * 更新同步字信息
     *
     * @param jdbcTemplate
     * @param sci
     * @return
     */
    public int updateSynCode(JdbcTemplate jdbcTemplate, SynCodeInfo sci);

    /**
     * 更新同步字信息
     *
     * @param jdbcTemplate
     * @param syntype 
     * @param itemno
     * @return
     */
    public int updateSynCode(JdbcTemplate jdbcTemplate, int syntype, int itemno);

    /**
     * 根据主键查询同步字信息
     *
     * @param jdbcTemplate
     * @param syn_type
     * @param item_no
     * @return
     */
    public SynCodeInfo getSynInfoByPrimary(JdbcTemplate jdbcTemplate, int syn_type, int item_no);
}
