package com.bestinfo.dao.terminal;

import com.bestinfo.bean.business.TmnInfo;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author YangRong
 */
public interface ITerminalRegisterDao {

    /**
     * 更新投注机软件版本
     *
     * @param jdbcTemplate
     * @param terminalid
     * @param softversion
     * @param upgrademark
     * @return
     */
//    public int updateTerminalSoftVersion(JdbcTemplate jdbcTemplate, int terminalid, String softversion, int upgrademark);
    
    /**
     * 更新投注机软件版本
     *
     * @param jdbcTemplate
     * @param terminalid
     * @param softversion
     * @param upgrademark
     * @return
     */
    public int updateTerminalSoftVersion(JdbcTemplate jdbcTemplate, int terminalid, String softversion);

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
    public int updateTerminalInfoRegister(JdbcTemplate jdbcTemplate, int regFlag, String safeCardId, String publicKey, int terminalId);

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
    public int updateTerminalInfo(JdbcTemplate jdbcTemplate, int regFlag, String safeCardId, String publicKey, int upgradeMark, int terminalId);

    /**
     * 根据terminal_id查询terminal info
     *
     *
     *
     * @param jdbcTemplate
     * @param terminalId 投注终端逻辑id
     * @return TmnInfo
     *
     */
    public TmnInfo getTmnInfoByTmnId(JdbcTemplate jdbcTemplate, int terminalId);

    /**
     * 将三个证书插入证书表
     *
     * @param jdbcTemplate
     * @param terminalCert 投注终端证书
     * @param terminalId 投注终端逻辑id
     * @return 0成功
     *
     */
    public int insertTermCert(JdbcTemplate jdbcTemplate, String terminalCert, int terminalId);

    /**
     * 根据物理机号查询terminal info -- clp
     *
     * @param jdbcTemplate
     * @param tmnphyid
     * @return TmnInfo
     *
     */
    public TmnInfo getTmnInfoByTmnPhyId(JdbcTemplate jdbcTemplate, int tmnphyid);
    /**
     * 获取注册的投注机列表
     * @param jdbcTemplate
     * @return 
     */
    public List<TmnInfo> getRegTmnIdList(JdbcTemplate jdbcTemplate);

}
