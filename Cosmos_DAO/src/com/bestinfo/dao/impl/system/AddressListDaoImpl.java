package com.bestinfo.dao.impl.system;

import com.bestinfo.bean.sysUser.AddressList;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.system.IAddressListDao;
import com.bestinfo.define.system.WorkStatusDefine;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 地址列表
 *
 * @author hhhh
 */
@Repository
public class AddressListDaoImpl extends BaseDaoImpl implements IAddressListDao {

    /**
     * 查询地址列表信息
     */
    private static final String SELECT_ADRESS_LIST = "select app_id,app_name,app_url,app_type,center_type,work_status from t_application_url where work_status = ?";

    /**
     * 查询地址列表下载的数据
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    @Override
    public List<AddressList> selectAddressList(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, SELECT_ADRESS_LIST, new Object[]{WorkStatusDefine.work}, AddressList.class);
    }

}
