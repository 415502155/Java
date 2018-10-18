

import com.bestinfo.bean.test.TestLuckyNo;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.test.ITestLuckyNo;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 游戏投注方式实现类
 *
 * @author shange
 */
@Repository
public class TestLuckyNoDaoImpl extends BaseDaoImpl implements ITestLuckyNo {

    private final Logger logger = Logger.getLogger(TestLuckyNoDaoImpl.class);

    private String GET_LUCKYNO = "SELECT * FROM t_test_luckyno";

    @Override
    /**
     * 获取开奖号码
     *
     * @param jdbcTemplate
     * @return
     */
    public TestLuckyNo getLuckyNo(JdbcTemplate jdbcTemplate) {
        return queryForObject(jdbcTemplate, GET_LUCKYNO, null, TestLuckyNo.class);
    }

}
