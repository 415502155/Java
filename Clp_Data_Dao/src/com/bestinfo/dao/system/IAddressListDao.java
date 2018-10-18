package com.bestinfo.dao.system;

import com.bestinfo.bean.sysUser.AddressList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 地址列表
 *
 * @author hhhh
 */
public interface IAddressListDao {

    /**
     * 查询地址信息的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    public List<AddressList> selectAddressList(JdbcTemplate jdbcTemplate);
}
