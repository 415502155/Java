package com.bestinfo.dao.impl.business;

import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.dao.business.ITmnInfoDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.page.Pagination;
import com.bestinfo.define.returncode.TerminalResultCode;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 投注机号
 */
@Repository
public class TmnInfoDaoImpl extends BaseDaoImpl implements ITmnInfoDao {

    private static final String INSERT_TMNINFO = "insert into t_tmn_info(terminal_serial_no, terminal_id, terminal_phy_id,terminal_initial_time, safe_card_id, city_id, district_id, station_name, terminal_address, station_phone, owner_name, owner_phone, linkman, linkman_phone, regist_date, software_id, upgrade_mark, software_version, terminal_type, terminal_status, agentfee_type, tmn_sale_deduct, tmn_cash_deduct, comm_type, dial_name, dial_pwd, account_id, dealer_id, terminal_syn_no,terminal_value,local_terminal_id,public_key,notice_syn_no,station_rank) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SELECT_TMNINFO_BY_TERMINALID = "select terminal_serial_no,terminal_id,terminal_phy_id,terminal_initial_time,safe_card_id,city_id,district_id,station_name,terminal_address,station_phone,owner_name,owner_phone,linkman,linkman_phone,regist_date,software_id,upgrade_mark,software_version,terminal_type,terminal_status,agentfee_type,tmn_sale_deduct,tmn_cash_deduct,comm_type,dial_name,dial_pwd,account_id,dealer_id,terminal_syn_no from t_tmn_info where 1=1 ";

    private static final String SELECT_COUNT_TMNINFO = "select count(1) from t_tmn_info where 1=1 ";
    /**
     * 缓存同步使用
     */
    private static final String SELECT_TMNINFO_LIST = "select terminal_serial_no, terminal_id, terminal_phy_id,terminal_initial_time, safe_card_id, city_id, district_id, station_name, terminal_address, station_phone, owner_name, owner_phone, linkman, linkman_phone, regist_date, software_id, upgrade_mark, software_version, terminal_type, terminal_status, agentfee_type, tmn_sale_deduct, tmn_cash_deduct, comm_type, dial_name, dial_pwd, account_id, dealer_id, terminal_syn_no,terminal_value,local_terminal_id,public_key,notice_syn_no,station_rank from t_tmn_info";
    private static final String SELECT_TMNINFO_BY_ID = "select terminal_serial_no, terminal_id, terminal_phy_id,terminal_initial_time, safe_card_id, city_id, district_id, station_name, terminal_address, station_phone, owner_name, owner_phone, linkman, linkman_phone, regist_date, software_id, upgrade_mark, software_version, terminal_type, terminal_status, agentfee_type, tmn_sale_deduct, tmn_cash_deduct, comm_type, dial_name, dial_pwd, account_id, dealer_id, terminal_syn_no,terminal_value,local_terminal_id,public_key,notice_syn_no,station_rank from t_tmn_info where terminal_id = ?";
    private static final String UPDATE_TMNINFO = "update t_tmn_info set terminal_serial_no =?, terminal_phy_id=?, terminal_initial_time = ?, safe_card_id = ?, city_id = ?, district_id = ?, station_name = ?, terminal_address = ?, station_phone = ?, owner_name = ?, owner_phone = ?, linkman = ?, linkman_phone = ?, regist_date = ?, software_id = ?, upgrade_mark = ?, software_version = ?, terminal_type = ?, terminal_status = ?, agentfee_type = ?, tmn_sale_deduct = ?, tmn_cash_deduct = ?, comm_type = ?, dial_name = ?, dial_pwd = ?, account_id = ?, dealer_id = ?, terminal_syn_no = ?, public_key = ? where  terminal_id = ?";

    private static final String SELECT_TMNINFO_BY_CITYID_TMNID = "select terminal_serial_no, terminal_id, terminal_phy_id,terminal_initial_time, safe_card_id, city_id, district_id, station_name, terminal_address, station_phone, owner_name, owner_phone, linkman, linkman_phone, regist_date, software_id, upgrade_mark, software_version, terminal_type, terminal_status, agentfee_type, tmn_sale_deduct, tmn_cash_deduct, comm_type, dial_name, dial_pwd, account_id, dealer_id, terminal_syn_no,terminal_value,local_terminal_id,public_key,notice_syn_no,station_rank from t_tmn_info where city_id = ? and terminal_id = ?";
    private static final String SELECT_TMNINFO_BY_CITYID_TMNID_DEALID = "select terminal_serial_no, terminal_id, terminal_phy_id,terminal_initial_time, safe_card_id, city_id, district_id, station_name, terminal_address, station_phone, owner_name, owner_phone, linkman, linkman_phone, regist_date, software_id, upgrade_mark, software_version, terminal_type, terminal_status, agentfee_type, tmn_sale_deduct, tmn_cash_deduct, comm_type, dial_name, dial_pwd, account_id, dealer_id, terminal_syn_no,terminal_value,local_terminal_id,public_key,notice_syn_no,station_rank from t_tmn_info where city_id = ? and terminal_id = ? and dealer_id = ?";

    private static final String SELECT_MAX_terminal_serial_no = "select max(terminal_serial_no) a from t_tmn_info";
    private static final String SELECT_TMNINFO_BY_dealer_id = " select terminal_id from  t_tmn_info where dealer_id = ?";

    private static final String UPDATE_UPGRADE_MARK = "update t_tmn_info set upgrade_mark = ? where terminal_id = ?";
    private static final String UPDATE_UPGRADE_MARK_SYN_NO = "update t_tmn_info set upgrade_mark = ?,terminal_syn_no=? where terminal_id = ?";
    private static final String UPDATE_ASSIST = "update t_tmn_info set comm_type = ?,terminal_syn_no=? where terminal_id = ?";
    private static final String UPDATE_NOTICE_TMN_SYN_NO = "update t_tmn_info set terminal_syn_no=?,notice_syn_no=? where terminal_id = ?";
    private static final String SELECT_TMNINFO_BY_CITYID = "select terminal_serial_no, terminal_id, terminal_phy_id,terminal_initial_time, safe_card_id, city_id, district_id, station_name, terminal_address, station_phone, owner_name, owner_phone, linkman, linkman_phone, regist_date, software_id, upgrade_mark, software_version, terminal_type, terminal_status, agentfee_type, tmn_sale_deduct, tmn_cash_deduct, comm_type, dial_name, dial_pwd, account_id, dealer_id, terminal_syn_no,terminal_value,local_terminal_id,public_key,notice_syn_no,station_rank from t_tmn_info where city_id = ?";

    private static final String SELECT_TMNINFO_BY_STATION_RANK = "select * from t_tmn_info where station_rank = ?";

    private static final String SELECT_TMNINFO_BY_CITYID_STATUS = "select terminal_serial_no, terminal_id, terminal_phy_id,terminal_initial_time, safe_card_id, city_id, district_id, station_name, terminal_address, station_phone, owner_name, owner_phone, linkman, linkman_phone, regist_date, software_id, upgrade_mark, software_version, terminal_type, terminal_status, agentfee_type, tmn_sale_deduct, tmn_cash_deduct, comm_type, dial_name, dial_pwd, account_id, dealer_id, terminal_syn_no,terminal_value,local_terminal_id,public_key,notice_syn_no,station_rank from t_tmn_info where city_id = ? and terminal_status not in (?)";

    private static final String SELECT_TMNINFO_BY_AREA_TMNID = "select terminal_serial_no, terminal_id, terminal_phy_id,terminal_initial_time, safe_card_id, city_id, district_id, station_name, terminal_address, station_phone, owner_name, owner_phone, linkman, linkman_phone, regist_date, software_id, upgrade_mark, software_version, terminal_type, terminal_status, agentfee_type, tmn_sale_deduct, tmn_cash_deduct, comm_type, dial_name, dial_pwd, account_id, dealer_id, terminal_syn_no,terminal_value,local_terminal_id,public_key,notice_syn_no,station_rank from t_tmn_info where terminal_id between ? and ?";
    private static final String SELECT_TMNINFO_BY_AREA_TMNID_STATUS = "select terminal_serial_no, terminal_id, terminal_phy_id,terminal_initial_time, safe_card_id, city_id, district_id, station_name, terminal_address, station_phone, owner_name, owner_phone, linkman, linkman_phone, regist_date, software_id, upgrade_mark, software_version, terminal_type, terminal_status, agentfee_type, tmn_sale_deduct, tmn_cash_deduct, comm_type, dial_name, dial_pwd, account_id, dealer_id, terminal_syn_no,terminal_value,local_terminal_id,public_key,notice_syn_no,station_rank from t_tmn_info where terminal_id between ? and ? and terminal_status not in (?)";

    private static final String UPDATE_DETAIL_INFO = "update t_tmn_info set  owner_name = ?, owner_phone = ?, linkman = ?, linkman_phone = ?, local_terminal_id = ?, station_rank = ?,terminal_address = ? where terminal_id = ?";

    private static final String NEW_UPDATE_DETAIL_INFO = "update t_tmn_info set station_name = ?, station_phone = ?, owner_name = ?, owner_phone = ?, linkman = ?, linkman_phone = ?, local_terminal_id = ?, station_rank = ?,terminal_address = ? where terminal_id = ?";

    private static final String UPDATE_COMM_PARA = "update t_tmn_info set  comm_type = ?, dial_name = ?, dial_pwd = ? where terminal_id = ?";

    private static final String UPDATE_ACCOUNT_ID = "update t_tmn_info set  account_id = ? where terminal_id = ?";

    private static final String CLEAR_UPGRADE_MARK = "update t_tmn_info set upgrade_mark = ? where (? is null or ?='-99999' or a.city_id = to_number(?)) and (? is null or  a.terminal_id >= to_number(?)) and (? is null or  a.terminal_id <= to_number(?))";

    private static final String SELECT_TMN_INFO_LIST_BY_CONDITIONS = "select terminal_serial_no, terminal_id, terminal_phy_id,terminal_initial_time, safe_card_id, city_id, district_id, station_name, terminal_address, station_phone, owner_name, owner_phone, linkman, linkman_phone, regist_date, software_id, upgrade_mark, software_version, terminal_type, terminal_status, agentfee_type, tmn_sale_deduct, tmn_cash_deduct, comm_type, dial_name, dial_pwd, account_id, dealer_id, terminal_syn_no,terminal_value,local_terminal_id,public_key,notice_syn_no,station_rank from t_tmn_info a  where (? is null or ?='-99999' or a.city_id = to_number(?)) and (? is null or  a.terminal_id >= to_number(?))  and (? is null or  a.terminal_id <= to_number(?))";

    private static final String SELECT_TMN_INFO_LIST_VERSION_EQUAL = "select a.terminal_serial_no, a.terminal_id, a.terminal_phy_id,a.terminal_initial_time, a.safe_card_id, a.city_id, a.district_id, a.station_name, a.terminal_address, a.station_phone, a.owner_name, a.owner_phone, a.linkman, a.linkman_phone, a.regist_date, a.software_id, a.upgrade_mark, a.software_version, a.terminal_type, a.terminal_status, a.agentfee_type, a.tmn_sale_deduct, a.tmn_cash_deduct, a.comm_type, a.dial_name, a.dial_pwd, a.account_id, a.dealer_id, a.terminal_syn_no,a.terminal_value,a.local_terminal_id,a.public_key,a.notice_syn_no,a.station_rank from t_tmn_info a, t_city_info b, t_terminal_type c, t_terminal_software d where a.city_id = b.city_id and a.terminal_type = c.terminal_type and a.software_id = d.software_id and a.software_version = d.software_version and (? is null or ?='-99999' or a.city_id = to_number(?)) and (? is null or  a.terminal_id >= to_number(?)) and (? is null or  a.terminal_id <= to_number(?))";

    private static final String SELECT_TMN_INFO_LIST_VERSION_NOT_EQUAL = "select a.terminal_serial_no, a.terminal_id, a.terminal_phy_id,a.terminal_initial_time, a.safe_card_id, a.city_id, a.district_id, a.station_name, a.terminal_address, a.station_phone, a.owner_name, a.owner_phone, a.linkman, a.linkman_phone, a.regist_date, a.software_id, a.upgrade_mark, a.software_version, a.terminal_type, a.terminal_status, a.agentfee_type, a.tmn_sale_deduct, a.tmn_cash_deduct, a.comm_type, a.dial_name, a.dial_pwd, a.account_id, a.dealer_id, a.terminal_syn_no,a.terminal_value,a.local_terminal_id,a.public_key,a.notice_syn_no,a.station_rank from t_tmn_info a, t_city_info b, t_terminal_type c, t_terminal_software d where a.city_id = b.city_id and a.terminal_type = c.terminal_type and a.software_id = d.software_id and a.software_version <> d.software_version and (? is null or ?='-99999' or a.city_id = to_number(?)) and (? is null or  a.terminal_id >= to_number(?)) and (? is null or  a.terminal_id <= to_number(?))";

    private static final String SELECT_TMNINFO_BY_PHY_ID = "select * from t_tmn_info where terminal_phy_id = ?";

    /**
     * 新增投注机信息
     *
     * @param jdbcTemplate
     * @param tin
     * @return
     */
    @Override
    public int insertTmnInfo(JdbcTemplate jdbcTemplate, TmnInfo tin) {
        String sql = INSERT_TMNINFO;
        int re = -1;
        try {
            re = jdbcTemplate.update(sql, new Object[]{
                tin.getTerminal_serial_no(),
                tin.getTerminal_id(),
                tin.getTerminal_phy_id(),
                tin.getTerminal_initial_time(),
                tin.getSafe_card_id(),
                tin.getCity_id(),
                tin.getDistrict_id(),
                tin.getStation_name(),
                tin.getTerminal_address(),
                tin.getStation_phone(),
                tin.getOwner_name(),
                tin.getOwner_phone(),
                tin.getLinkman(),
                tin.getLinkman_phone(),
                tin.getRegist_date(),
                tin.getSoftware_id(),
                tin.getUpgrade_mark(),
                tin.getSoftware_version(),
                tin.getTerminal_type(),
                tin.getTerminal_status(),
                tin.getAgentfee_type(),
                tin.getTmn_sale_deduct(),
                tin.getTmn_cash_deduct(),
                tin.getComm_type(),
                tin.getDial_name(),
                tin.getDial_pwd(),
                tin.getAccount_id(),
                tin.getDealer_id(),
                tin.getTerminal_syn_no(),
                tin.getTerminal_value(),
                tin.getLocal_terminal_id(),
                tin.getPublic_key(),
                tin.getNotice_syn_no(),
                tin.getStation_rank()
            });
            return re;
        } catch (DataAccessException e) {
            logger.error("insert tmn info into DB error.Terminal_id:" + tin.getTerminal_id(), e);
            return -2;
        }
    }

    /**
     * 获取投注机分页信息列表
     *
     * @param jdbcTemplate
     * @param params
     * @return
     */
    @Override
    public Pagination<TmnInfo> getTmnInfoPageList(JdbcTemplate jdbcTemplate, Map<String, Object> params) {
        Map<String, Object> whereMap = getWhereStr(params);
        //sql语句
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT_TMNINFO_BY_TERMINALID);
        sql.append(whereMap.get("whereSql"));
        StringBuilder countSql = new StringBuilder();
        countSql.append(SELECT_COUNT_TMNINFO);
        countSql.append(whereMap.get("whereSql"));
        //参数列表
        Object[] args = null;
        String paramStr = "";
        if (whereMap.get("whereParam") != null) {
            paramStr += whereMap.get("whereParam").toString();
        }
        if (!paramStr.isEmpty() && !"".equals(paramStr)) {
            args = paramStr.split(",");
        }
        Pagination<TmnInfo> page = null;
        try {
            page = queryForPage(jdbcTemplate, sql.toString(), countSql.toString(), Integer.parseInt(params.get("pageNumber").toString()), Integer.parseInt(params.get("pageSize").toString()), args, TmnInfo.class);
        } catch (NumberFormatException e) {
            logger.error("", e);
        }

        return page;
    }

    /**
     * 根据条件列表拼查询sql
     *
     * @param params
     * @return
     */
    private Map<String, Object> getWhereStr(Map<String, Object> params) {
        StringBuilder whereSql = new StringBuilder("");
        StringBuilder whereParam = new StringBuilder("");
        Object terid = params.get("terminal_id");
        if (null != terid) {
            whereSql.append(" AND TERMINAL_ID = ?");
            whereParam.append(terid).append(",");
        }
        Object terphyid = params.get("terminal_phy_id");
        if (null != terphyid) {
            whereSql.append(" AND terminal_phy_id = ?");
            whereParam.append(terphyid).append(",");
        }
        Object cityid = params.get("city_id");
        if (null != cityid) {
            whereSql.append(" AND city_id = ?");
            whereParam.append(cityid).append(",");
        }
        Object stationName = params.get("station_name");
        if (null != stationName) {
            whereSql.append(" AND station_name = ?");
            whereParam.append(stationName).append(",");
        }
        Object ownerName = params.get("owner_name");
        if (null != ownerName) {
            whereSql.append(" AND owner_name = ?");
            whereParam.append(ownerName).append(",");
        }
        Object dealid = params.get("dealer_id");
        if (null != dealid) {
            whereSql.append(" AND dealer_id = ?");
            whereParam.append(dealid).append(",");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("whereSql", whereSql.toString());
        map.put("whereParam", whereParam.toString());
        return map;
    }

    /**
     * 查询投注终端的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    @Override
    public List<TmnInfo> selectTmnInfo(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, SELECT_TMNINFO_LIST, null, TmnInfo.class);
    }

    /**
     * 修改投注终端数据
     *
     * @param jdbcTemplate
     * @param ti
     * @return
     */
    @Override
    public int updateTmnInfo(JdbcTemplate jdbcTemplate, TmnInfo ti) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_TMNINFO, new Object[]{
                ti.getTerminal_serial_no(),
                ti.getTerminal_phy_id(),
                ti.getTerminal_initial_time(),
                ti.getSafe_card_id(),
                ti.getCity_id(),
                ti.getDistrict_id(),
                ti.getStation_name(),
                ti.getTerminal_address(),
                ti.getStation_phone(),
                ti.getOwner_name(),
                ti.getOwner_phone(),
                ti.getLinkman(),
                ti.getLinkman_phone(),
                ti.getRegist_date(),
                ti.getSoftware_id(),
                ti.getUpgrade_mark(),
                ti.getSoftware_version(),
                ti.getTerminal_type(),
                ti.getTerminal_status(),
                ti.getAgentfee_type(),
                ti.getTmn_sale_deduct(),
                ti.getTmn_cash_deduct(),
                ti.getComm_type(),
                ti.getDial_name(),
                ti.getDial_pwd(),
                ti.getAccount_id(),
                ti.getDealer_id(),
                ti.getTerminal_syn_no(),
                ti.getPublic_key(),
                ti.getTerminal_id()
            });
        } catch (DataAccessException e) {
            logger.error("Terminal_id:" + ti.getTerminal_id(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 根据地市和终端机号查询终端信息列表
     *
     * @param jdbcTemplate
     * @param cityid
     * @param tmnid
     * @return
     */
    @Override
    public List<TmnInfo> getTmnListByCityIdAndTmnId(JdbcTemplate jdbcTemplate, int cityid, int tmnid) {
        return this.queryForList(jdbcTemplate, SELECT_TMNINFO_BY_CITYID_TMNID, new Object[]{cityid, tmnid}, TmnInfo.class);
    }

    /**
     * 根据地市、终端机号、代销商编号查询终端信息列表
     *
     * @param jdbcTemplate
     * @param cityid
     * @param tmnid
     * @param dealerid
     * @return
     */
    @Override
    public List<TmnInfo> getTmnListByCityIdAndTmnIdAndDealerId(JdbcTemplate jdbcTemplate, int cityid, int tmnid, String dealerid) {
        return this.queryForList(jdbcTemplate, SELECT_TMNINFO_BY_CITYID_TMNID_DEALID, new Object[]{cityid, tmnid, dealerid}, TmnInfo.class);
    }

    /**
     * 获取流水号信息
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public int getMaxTerminalSerialNo(JdbcTemplate jdbcTemplate) {
        int max;
        try {
            Integer maxSer = jdbcTemplate.queryForObject(SELECT_MAX_terminal_serial_no, Integer.class);
            if (maxSer == null) {
                max = -1;
            } else {
                max = maxSer.intValue();
            }
            return max;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 获取投注机信息列表
     *
     * @param jdbcTemplate
     * @param dealer_id 代销商编号
     * @return
     */
    @Override
    public List<TmnInfo> getTmnInfoListByDealerId(JdbcTemplate jdbcTemplate, String dealer_id) {
        return this.queryForList(jdbcTemplate, SELECT_TMNINFO_BY_dealer_id, new Object[]{dealer_id}, TmnInfo.class);
    }

    /**
     * 根据投注机号查询投注机信息
     *
     * @param jdbcTemplate
     * @param terminalId
     * @return
     */
    @Override
    public TmnInfo getTmnInfoByTerminalId(JdbcTemplate jdbcTemplate, int terminalId) {
        return this.queryForObject(jdbcTemplate, SELECT_TMNINFO_BY_ID, new Object[]{terminalId}, TmnInfo.class);
    }

    /**
     * 批量修改终端的升级标记
     *
     * @param jdbcTemplate
     * @param tiList
     * @return
     */
    @Override
    public int batchUpdateUpgrade(JdbcTemplate jdbcTemplate, final List<TmnInfo> tiList) {
        try {
            jdbcTemplate.batchUpdate(UPDATE_UPGRADE_MARK, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    TmnInfo ti = tiList.get(i);
                    ps.setInt(1, ti.getUpgrade_mark());
                    ps.setInt(2, ti.getTerminal_id());
                }

                @Override
                public int getBatchSize() {
                    return tiList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("batch update upgrade mark error.", e);
            return -1;
        }
    }

    /**
     * 批量修改终端的升级标记及终端同步字
     *
     * @param jdbcTemplate
     * @param tiList
     * @return
     */
    @Override
    public int batchUpdateUpgradeSynNo(JdbcTemplate jdbcTemplate, final List<TmnInfo> tiList) {
        try {
            jdbcTemplate.batchUpdate(UPDATE_UPGRADE_MARK_SYN_NO, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    TmnInfo ti = tiList.get(i);
                    ps.setInt(1, ti.getUpgrade_mark());
                    ps.setInt(2, ti.getTerminal_syn_no());
                    ps.setInt(3, ti.getTerminal_id());
                }

                @Override
                public int getBatchSize() {
                    return tiList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("batch update upgrade mark and terminal syn no error.", e);
            return -1;
        }
    }

    /**
     * 批量修改终端的通讯方式(电子辅助)
     *
     * @param jdbcTemplate
     * @param tiList
     * @return
     */
    @Override
    public int batchUpdateAssist(JdbcTemplate jdbcTemplate, final List<TmnInfo> tiList) {
        try {
            jdbcTemplate.batchUpdate(UPDATE_ASSIST, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    TmnInfo ti = tiList.get(i);
                    ps.setInt(1, ti.getComm_type());
                    ps.setInt(2, ti.getTerminal_syn_no());
                    ps.setInt(3, ti.getTerminal_id());
                }

                @Override
                public int getBatchSize() {
                    return tiList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("batch update assist error.", e);
            return -1;
        }
    }

    /**
     * 批量修改终端新通知、终端同步字
     *
     * @param jdbcTemplate
     * @param tiList
     * @return
     */
    @Override
    public int batchUpdateNoticeTmnSynNo(JdbcTemplate jdbcTemplate, final List<TmnInfo> tiList) {
        try {
            jdbcTemplate.batchUpdate(UPDATE_NOTICE_TMN_SYN_NO, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    TmnInfo ti = tiList.get(i);
                    ps.setInt(1, ti.getTerminal_syn_no());
                    ps.setInt(2, ti.getNotice_syn_no());
                    ps.setInt(3, ti.getTerminal_id());
                }

                @Override
                public int getBatchSize() {
                    return tiList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("batch update notice and terminal syn no error.", e);
            return -1;
        }
    }

    /**
     * 获取某地市下的所有终端
     *
     * @param jdbcTemplate
     * @param cityid
     * @return
     */
    @Override
    public List<TmnInfo> getTmnListByCityId(JdbcTemplate jdbcTemplate, int cityid) {
        return this.queryForList(jdbcTemplate, SELECT_TMNINFO_BY_CITYID, new Object[]{cityid}, TmnInfo.class);
    }

    /**
     * 获取某星级的所有终端
     *
     * @param jdbcTemplate
     * @param stationRank
     * @return
     */
    @Override
    public List<TmnInfo> getTmnListByStationRank(JdbcTemplate jdbcTemplate, int stationRank) {
        return this.queryForList(jdbcTemplate, SELECT_TMNINFO_BY_STATION_RANK, new Object[]{stationRank}, TmnInfo.class);
    }

    /**
     * 获取某地市下所有可封机的终端
     *
     * @param jdbcTemplate
     * @param cityid
     * @param tmnStatus
     * @return
     */
    @Override
    public List<TmnInfo> getTmnListByCityId(JdbcTemplate jdbcTemplate, int cityid, int tmnStatus) {
        return this.queryForList(jdbcTemplate, SELECT_TMNINFO_BY_CITYID_STATUS, new Object[]{cityid, tmnStatus}, TmnInfo.class);
    }

    /**
     * 获取终端号在指定范围内的所有终端信息
     *
     * @param jdbcTemplate
     * @param begin_terminal_id
     * @param end_terminal_id
     * @return
     */
    @Override
    public List<TmnInfo> getTmnListByAreaTmnId(JdbcTemplate jdbcTemplate, int begin_terminal_id, int end_terminal_id) {
        return this.queryForList(jdbcTemplate, SELECT_TMNINFO_BY_AREA_TMNID, new Object[]{begin_terminal_id, end_terminal_id}, TmnInfo.class);
    }

    /**
     * 获取终端号在指定范围内的所有可封机的终端信息
     *
     * @param jdbcTemplate
     * @param begin_terminal_id
     * @param end_terminal_id
     * @param tmnStatus
     * @return
     */
    @Override
    public List<TmnInfo> getTmnListByAreaTmnId(JdbcTemplate jdbcTemplate, int begin_terminal_id, int end_terminal_id, int tmnStatus) {
        return this.queryForList(jdbcTemplate, SELECT_TMNINFO_BY_AREA_TMNID_STATUS, new Object[]{begin_terminal_id, end_terminal_id, tmnStatus}, TmnInfo.class);
    }

    /**
     * 修改详细信息
     *
     * @param jdbcTemplate
     * @param ti
     * @return
     */
    @Override
    public int modifyDetai(JdbcTemplate jdbcTemplate, TmnInfo ti) {
        int result = -1;
        try {
            result = jdbcTemplate.update(UPDATE_DETAIL_INFO, new Object[]{
                ti.getOwner_name(),
                ti.getOwner_phone(),
                ti.getLinkman(),
                ti.getLinkman_phone(),
                ti.getLocal_terminal_id(),
                ti.getStation_rank(),
                ti.getTerminal_address(),
                ti.getTerminal_id()
            });
        } catch (DataAccessException e) {
            logger.error("Terminal_id:" + ti.getTerminal_id(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -2;
        }
        return result;
    }

    /**
     * 修改详细信息--新版
     *
     * @param jdbcTemplate
     * @param ti
     * @return
     */
    @Override
    public int newModifyDetai(JdbcTemplate jdbcTemplate, TmnInfo ti) {
        int result = -1;
        try {
            result = jdbcTemplate.update(NEW_UPDATE_DETAIL_INFO, new Object[]{
                ti.getStation_name(),
                ti.getStation_phone(),
                ti.getOwner_name(),
                ti.getOwner_phone(),
                ti.getLinkman(),
                ti.getLinkman_phone(),
                ti.getLocal_terminal_id(),
                ti.getStation_rank(),
                ti.getTerminal_address(),
                ti.getTerminal_id()
            });
        } catch (DataAccessException e) {
            logger.error("Terminal_id:" + ti.getTerminal_id(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -2;
        }
        return result;
    }

    /**
     * 修改通讯参数
     *
     * @param jdbcTemplate
     * @param tmnInfo
     * @return
     */
    @Override
    public int commParaModify(JdbcTemplate jdbcTemplate, TmnInfo tmnInfo) {
        int result = -1;
        try {
            result = jdbcTemplate.update(UPDATE_COMM_PARA, new Object[]{
                tmnInfo.getComm_type(),
                tmnInfo.getDial_name(),
                tmnInfo.getDial_pwd(),
                tmnInfo.getTerminal_id()
            });
        } catch (DataAccessException e) {
            logger.error("Terminal_id:" + tmnInfo.getTerminal_id(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -2;
        }
        return result;
    }

    /**
     * 绑定账户
     *
     * @param jdbcTemplate
     * @param tmnInfo
     * @return
     */
    @Override
    public int bindAccount(JdbcTemplate jdbcTemplate, TmnInfo tmnInfo) {
        int result = -1;
        try {
            result = jdbcTemplate.update(UPDATE_ACCOUNT_ID, new Object[]{
                tmnInfo.getAccount_id(),
                tmnInfo.getTerminal_id()
            });
        } catch (DataAccessException e) {
            logger.error("Terminal_id:" + tmnInfo.getTerminal_id(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -2;
        }
        return result;
    }

    /**
     * 根据区县、起始终端编号更新升级标记
     *
     * @param jdbcTemplate
     * @param city_id
     * @param begin_terminal_id
     * @param end_terminal_id
     * @return
     */
    @Override
    public int updateUpgradeMark(JdbcTemplate jdbcTemplate, String city_id, String begin_terminal_id, String end_terminal_id, int upgradeMark) {
        int result = -1;
        try {
            result = jdbcTemplate.update(CLEAR_UPGRADE_MARK, new Object[]{
                upgradeMark,
                city_id,
                city_id,
                city_id,
                begin_terminal_id,
                begin_terminal_id,
                end_terminal_id,
                end_terminal_id
            });
            logger.info("清除升级标记的数据条数：" + result);
        } catch (DataAccessException e) {
            logger.error("begin_terminal_id:" + begin_terminal_id + ",end_terminal_id" + end_terminal_id, e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -2;
        }
        return result;
    }

    /**
     * 根据区县、起始终端编号查询符合条件的终端
     *
     * @param jdbcTemplate
     * @param city_id
     * @param begin_terminal_id
     * @param end_terminal_id
     * @return
     */
    @Override
    public List<TmnInfo> getTmnListByConditions(JdbcTemplate jdbcTemplate, String city_id, String begin_terminal_id, String end_terminal_id) {
        return this.queryForList(jdbcTemplate, SELECT_TMN_INFO_LIST_BY_CONDITIONS, new Object[]{
            city_id,
            city_id,
            city_id,
            begin_terminal_id,
            begin_terminal_id,
            end_terminal_id,
            end_terminal_id
        }, TmnInfo.class);
    }

    /**
     * 查询软件版本一致的所有终端
     *
     * @param jdbcTemplate
     * @param city_id
     * @param begin_terminal_id
     * @param end_terminal_id
     * @return
     */
    @Override
    public List<TmnInfo> getTmnListVersionEqual(JdbcTemplate jdbcTemplate, String city_id, String begin_terminal_id, String end_terminal_id) {
        return this.queryForList(jdbcTemplate, SELECT_TMN_INFO_LIST_VERSION_EQUAL, new Object[]{
            city_id,
            city_id,
            city_id,
            begin_terminal_id,
            begin_terminal_id,
            end_terminal_id,
            end_terminal_id
        }, TmnInfo.class);
    }

    /**
     * 查询软件版本不一致的所有终端
     *
     * @param jdbcTemplate
     * @param city_id
     * @param begin_terminal_id
     * @param end_terminal_id
     * @return
     */
    @Override
    public List<TmnInfo> getTmnListVersionNotEqual(JdbcTemplate jdbcTemplate, String city_id,
            String begin_terminal_id, String end_terminal_id) {
        return this.queryForList(jdbcTemplate, SELECT_TMN_INFO_LIST_VERSION_NOT_EQUAL, new Object[]{
            city_id,
            city_id,
            city_id,
            begin_terminal_id,
            begin_terminal_id,
            end_terminal_id,
            end_terminal_id
        }, TmnInfo.class);
    }

    /**
     * 根据物理投注机号查询投注机信息
     *
     * @param jdbcTemplate
     * @param terminalPhyId
     * @return
     */
    @Override
    public TmnInfo getTmnInfoByPhyId(JdbcTemplate jdbcTemplate, int terminalPhyId) {
        return this.queryForObject(jdbcTemplate, SELECT_TMNINFO_BY_PHY_ID, new Object[]{terminalPhyId}, TmnInfo.class);
    }

    /**
     * 注册电话投注投注机
     *
     * @param jdbcTemplate
     * @param tmn
     * @param agentFeeRate
     * @return
     */
    @Override
    public int registGambTmn(JdbcTemplate jdbcTemplate, final TmnInfo tmn, final BigDecimal agentFeeRate) {
        try {
            String sql = "{call p_gamb_tmn_regist(?,?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?, ?,?,?,?)}";
            Integer errorcode = (Integer) jdbcTemplate.execute(sql, new CallableStatementCallback() {
                @Override
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                    int i = 1;
                    cs.setInt(i++, tmn.getTerminal_id());
                    cs.setInt(i++, tmn.getTerminal_phy_id());
                    cs.setInt(i++, tmn.getTerminal_type());
                    cs.setString(i++, tmn.getStation_name());
                    cs.setString(i++, tmn.getStation_phone());
                    cs.setInt(i++, tmn.getCity_id());
                    cs.setInt(i++, tmn.getDistrict_id());
                    cs.setString(i++, tmn.getOwner_phone());
                    cs.setString(i++, tmn.getOwner_name());
                    cs.setString(i++, tmn.getLinkman_phone());
                    cs.setString(i++, tmn.getLinkman());
                    cs.setString(i++, tmn.getTerminal_address());
                    cs.setInt(i++, tmn.getSoftware_id());
                    cs.setInt(i++, tmn.getComm_type());
                    cs.setString(i++, tmn.getDial_name());
                    cs.setString(i++, tmn.getDial_pwd());
                    cs.setString(i++, tmn.getDealer_id());
                    cs.setBigDecimal(i++, agentFeeRate);
                    cs.registerOutParameter(i++, Types.INTEGER);
                    cs.registerOutParameter(i++, Types.VARCHAR);
                    cs.execute();
                    int errorcode = cs.getInt(i - 2);
                    if (errorcode != 0) {
                        logger.error("call P_GAMB_TMN_REGIST error,error_code:" + errorcode + ",error_msg:" + cs.getString(i - 1));
                    }
                    return Integer.valueOf(errorcode);
                }
            });
            return errorcode;
        } catch (DataAccessException e) {
            logger.error("", e);
            return TerminalResultCode.daoExceError;
        }
    }
}
