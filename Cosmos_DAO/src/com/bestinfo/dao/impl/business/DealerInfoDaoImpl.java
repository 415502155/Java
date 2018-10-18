package com.bestinfo.dao.impl.business;

import com.bestinfo.bean.business.DealerInfo;
import com.bestinfo.dao.business.IDealerInfoDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 代销商基本信息
 *
 * @author chenliping
 */
@Repository
public class DealerInfoDaoImpl extends BaseDaoImpl implements IDealerInfoDao {

    private static final String INSERT_DEALERINFO = "insert into t_dealer_info (dealer_id, dealer_name, dealer_type, dealer_address, owner_name, owner_phone, link_name, link_phone, dealer_phone, province_id, city_id, email, phone_no, id_type_id, id_no, bank_id, account_name, account_card, note, terminal_condition, regist_time, work_status) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    private static final String SELECT_ALLDEALERINFO = "select * from t_dealer_info";
    private static final String SELECT_DEALERINFO_BY_ID = "select dealer_id,dealer_name,dealer_type,dealer_address,owner_name,owner_phone,link_name,link_phone,dealer_phone,province_id,city_id,email,phone_no,id_type_id,id_no,bank_id,account_name,account_card,note,terminal_condition,regist_time,work_status from t_dealer_info where dealer_id = ?";
    private static final String UPDATE_DEALERINFO = "update t_dealer_info set dealer_name = ?, dealer_type = ?, dealer_address = ?, owner_name = ?, owner_phone = ?, link_name = ?, link_phone = ?, dealer_phone = ?, province_id = ?, city_id = ?, email = ?, phone_no = ?, id_type_id = ?, id_no = ?, bank_id = ?, account_name = ?, account_card = ?, note = ?, terminal_condition = ?, regist_time = ?, work_status = ? where dealer_id = ?";

    /**
     * *
     * 新增代销商信息
     *
     * @param jdbcTemplate
     * @param dealer
     * @return
     */
    @Override
    public int insertDealerInfo(JdbcTemplate jdbcTemplate, DealerInfo dealer) {
        int re = -1;
        String sql = INSERT_DEALERINFO;
        try {
            re = jdbcTemplate.update(sql, new Object[]{
                dealer.getDealer_id(), dealer.getDealer_name(),
                dealer.getDealer_type(), dealer.getDealer_address(),
                dealer.getOwner_name(), dealer.getOwner_phone(),
                dealer.getLink_name(), dealer.getLink_phone(),
                dealer.getDealer_phone(), dealer.getProvince_id(),
                dealer.getCity_id(), dealer.getEmail(),
                dealer.getPhone_no(), dealer.getId_type_id(),
                dealer.getId_no(), dealer.getBank_id(),
                dealer.getAccount_name(), dealer.getAccount_card(),
                dealer.getNote(), dealer.getTerminal_condition(),
                dealer.getRegist_time(), dealer.getWork_status()
            });
            return re;
        } catch (Exception e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 查询所有的代销商信息
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<DealerInfo> getAllDealerInfo(JdbcTemplate jdbcTemplate) {
        String sql = SELECT_ALLDEALERINFO;
        return this.queryForList(jdbcTemplate, sql, null, DealerInfo.class);
    }

    /**
     * 修改代销商信息数据
     *
     * @param jdbcTemplate
     * @param di
     * @return
     */
    @Override
    public int updateDealerInfo(JdbcTemplate jdbcTemplate, DealerInfo di) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_DEALERINFO, new Object[]{
                di.getDealer_name(),
                di.getDealer_type(),
                di.getDealer_address(),
                di.getOwner_name(),
                di.getOwner_phone(),
                di.getLink_name(),
                di.getLink_phone(),
                di.getDealer_phone(),
                di.getProvince_id(),
                di.getCity_id(),
                di.getEmail(),
                di.getPhone_no(),
                di.getId_type_id(),
                di.getId_no(),
                di.getBank_id(),
                di.getAccount_name(),
                di.getAccount_card(),
                di.getNote(),
                di.getTerminal_condition(),
                di.getRegist_time(),
                di.getWork_status(),
                di.getDealer_id()
            });
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 根据代销商Id查询代销商信息
     *
     * @param jdbcTemplate
     * @param dealerId
     * @return
     */
    @Override
    public DealerInfo getDealerInfoById(JdbcTemplate jdbcTemplate, String dealerId) {
        return queryForObject(jdbcTemplate, SELECT_DEALERINFO_BY_ID, new Object[]{dealerId}, DealerInfo.class);
    }
}
