package com.bestinfo.dao.impl.terminal;

import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.terminal.ITerminalRegisterDao;
import com.bestinfo.define.returncode.TerminalResultCode;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 投注机注册DAO
 *
 * @author YangRong
 */
@Repository
public class TerminalRegisterDaoImpl extends BaseDaoImpl implements ITerminalRegisterDao {

//    private final Logger logger = Logger.getLogger(TerminalRegisterDaoImpl.class);
    /**
     * 根据投注终端逻辑id，更新投注机号表T_TMN_INFO的投注机初始化时间、加密芯片、公钥
     */
    private static final String UPDATE_TMNINFO_REGISTER = "update t_tmn_info set terminal_initial_time=?,safe_card_id=?,public_key=?  where terminal_id=?";

    /**
     * 根据投注终端逻辑id，更新投注机号表T_TMN_INFO的投注机初始化时间、加密芯片、公钥、升级标记
     */
    private static final String UPDATE_TMNINFO = "update t_tmn_info set terminal_initial_time=?,safe_card_id=?,public_key=?,upgrade_mark=?  where terminal_id=?";

//    private static final String UPDATE_TMNSOFTVERSION = "update t_tmn_info set software_version=?,upgrade_mark=? where terminal_id=?";
    
    private static final String UPDATE_TMNSOFTVERSION = "update t_tmn_info set software_version=? where terminal_id=?";

    /**
     * 将终端CA插入证书表
     */
    private static final String INSERT_TERMCERT = "";

    private static final String GET_REGISTER_TERMINAL_LIST=" select terminal_serial_no, terminal_id, terminal_phy_id,terminal_initial_time, safe_card_id, city_id, district_id, station_name, terminal_address, station_phone, owner_name, owner_phone, linkman, linkman_phone, regist_date, software_id, upgrade_mark, software_version, terminal_type, terminal_status, agentfee_type, tmn_sale_deduct, tmn_cash_deduct, comm_type, dial_name, dial_pwd, account_id, dealer_id, terminal_syn_no,terminal_value,local_terminal_id,public_key,notice_syn_no,station_rank  from t_tmn_info where terminal_initial_time>0 ";
    /**
     * 根据终端id获取终端信息sql
     */
    private static final String GET_TMNINFO_BY_TERMINAL_ID = "select terminal_serial_no, terminal_id, terminal_phy_id,terminal_initial_time, safe_card_id, city_id, district_id, station_name, terminal_address, station_phone, owner_name, owner_phone, linkman, linkman_phone, regist_date, software_id, upgrade_mark, software_version, terminal_type, terminal_status, agentfee_type, tmn_sale_deduct, tmn_cash_deduct, comm_type, dial_name, dial_pwd, account_id, dealer_id, terminal_syn_no,terminal_value,local_terminal_id,public_key,notice_syn_no,station_rank from t_tmn_info where terminal_id=?";

    /**
     * 根据物理卡号查询终端信息 --clp
     */
    private static final String GET_TMNINFO_BY_TMNPHYID = "select terminal_serial_no, terminal_id, terminal_phy_id,terminal_initial_time, safe_card_id, city_id, district_id, station_name, terminal_address, station_phone, owner_name, owner_phone, linkman, linkman_phone, regist_date, software_id, upgrade_mark, software_version, terminal_type, terminal_status, agentfee_type, tmn_sale_deduct, tmn_cash_deduct, comm_type, dial_name, dial_pwd, account_id, dealer_id, terminal_syn_no,terminal_value,local_terminal_id,public_key,notice_syn_no,station_rank from t_tmn_info where terminal_phy_id=?";

    /**
     * 更新终端软件版本
     *
     * @param jdbcTemplate
     * @param terminalid
     * @param softversion
     * @return
     */
    @Override
//    public int updateTerminalSoftVersion(JdbcTemplate jdbcTemplate, int terminalid, String softversion, int upgrademark) 
    public int updateTerminalSoftVersion(JdbcTemplate jdbcTemplate, int terminalid, String softversion) {
        int result;
        try {
//            result = jdbcTemplate.update(UPDATE_TMNSOFTVERSION, new Object[]{softversion, upgrademark, terminalid});
            result = jdbcTemplate.update(UPDATE_TMNSOFTVERSION, new Object[]{softversion, terminalid});
            if (result < 1) {
                logger.error("update tmn info(software_version) error:" + result + " where terminalid = " + terminalid);
                return -TerminalResultCode.tmnVersionUpdateForDBFail;
            }
            return TerminalResultCode.success;
        } catch (DataAccessException e) {
            logger.error("when update tmn info(software_version) occur error where terminalid = " + terminalid, e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -TerminalResultCode.daoExceError;
        }
    }

    /**
     * 更新投注机号表T_TMN_INFO的投注机初始化时间、加密芯片、公钥
     *
     * @param jdbcTemplate
     * @param regFlag 终端口令
     * @param terminalId 投注终端逻辑id
     * @param safeCardId 安全卡号/加密卡信息
     * @param publicKey 公钥
     * @return 0成功
     */
    @Override
    public int updateTerminalInfoRegister(JdbcTemplate jdbcTemplate, int regFlag, String safeCardId, String publicKey, int terminalId) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_TMNINFO_REGISTER, new Object[]{regFlag, safeCardId, publicKey, terminalId});
            if (result < 1) {
                logger.error("update tmn info error:" + result + " where terminalId = " + terminalId);
                return -TerminalResultCode.tmnUpdateForDBFail;
            }
            return TerminalResultCode.success;
        } catch (DataAccessException e) {
            logger.error("when update tmn info occur error where terminalId = " + terminalId, e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -TerminalResultCode.daoExceError;
        }
    }

    /**
     * 更新投注机号表T_TMN_INFO的投注机初始化时间、加密芯片、公钥、升级标记
     *
     * @param jdbcTemplate
     * @param regFlag 终端口令
     * @param terminalId 投注终端逻辑id
     * @param safeCardId 安全卡号/加密卡信息
     * @param publicKey 公钥
     * @param upgradeMark 升级标记
     * @return 0成功
     */
    @Override
    public int updateTerminalInfo(JdbcTemplate jdbcTemplate, int regFlag, String safeCardId, String publicKey, int upgradeMark, int terminalId) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_TMNINFO, new Object[]{regFlag, safeCardId, publicKey, upgradeMark, terminalId});
            if (result < 1) {
                logger.error("update tmn info error:" + result + " where terminalId = " + terminalId);
                return -1;
            }
            return 0;
        } catch (DataAccessException e) {
            logger.error("when update tmn info occur error where terminalId = " + terminalId, e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -2;
        }

    }

    /**
     * 根据逻辑机号查询terminal info
     *
     * @param terminalId 投注终端逻辑id
     * @return TmnInfo
     *
     */
    @Override
    public TmnInfo getTmnInfoByTmnId(JdbcTemplate jdbcTemplate, int terminalId) {
        return queryForObject(jdbcTemplate, GET_TMNINFO_BY_TERMINAL_ID, new Object[]{terminalId}, TmnInfo.class);
    }

    @Override
    public List<TmnInfo> getRegTmnIdList(JdbcTemplate jdbcTemplate){
        return this.queryForList(jdbcTemplate, GET_REGISTER_TERMINAL_LIST, null, TmnInfo.class); 
    }
    
    
    /**
     * 根据物理机号查询terminal info -- clp
     *
     * @param jdbcTemplate
     * @param tmnphyid
     * @return TmnInfo
     *
     */
    @Override
    public TmnInfo getTmnInfoByTmnPhyId(JdbcTemplate jdbcTemplate, int tmnphyid) {
        return queryForObject(jdbcTemplate, GET_TMNINFO_BY_TMNPHYID, new Object[]{tmnphyid}, TmnInfo.class);
    }

    /**
     * 将投注终端证书插入证书表
     *
     *
     * @param terminalCert 投注终端证书
     * @param terminalId 投注终端逻辑id
     * @return 0成功
     *
     */
    //证书表结构未知,sql语句为空 8/26
    @Override
    public int insertTermCert(JdbcTemplate jdbcTemplate, String terminalCert, int terminalId) {
        int result;
        try {
            result = jdbcTemplate.update(INSERT_TERMCERT, new Object[]{terminalCert, terminalId});
            if (result < 1) {
                return -1;
            }
            return 0;
        } catch (DataAccessException e) {
            logger.error("when insert term cert occur error where terminalId = " + terminalId, e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -1;
        }
    }

}
