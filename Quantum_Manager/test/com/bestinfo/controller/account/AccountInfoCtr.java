package com.bestinfo.controller.account;

import com.bestinfo.bean.account.AccountInfo;
import com.bestinfo.bean.encoding.AccountType;
import com.bestinfo.ehcache.AccountTypeCache;
import com.bestinfo.service.account.IAccountInfoSer;
import com.bestinfo.util.TimeUtil;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 资金帐户统一汇总信息
 *
 * @author hhhh
 */
@Controller
@RequestMapping(value = "/accountInfo")
public class AccountInfoCtr {

    private final Logger logger = Logger.getLogger(AccountInfoCtr.class);

    @Resource
    private IAccountInfoSer accountInfoSer;

    @Resource
    private AccountTypeCache accountTypeCache;

    @RequestMapping(value = "/2add")
    public String toadd() {
        return "/account/accRegister";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addAccountInfo(HttpServletRequest request, Model resModel, AccountInfo ai) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            String credit_time_str = request.getParameter("credit_time_str");
            if (credit_time_str == null || "".equals(credit_time_str)) {
                resMap.put("result", "临时信用额度有效时间异常");
                return resMap;
            }
            ai.setCredit_time(TimeUtil.parseDate_YMDT(credit_time_str));
            ai.setSum_bet_money(BigDecimal.ZERO);
            ai.setSum_undo_money(BigDecimal.ZERO);
            ai.setSum_prize_money(BigDecimal.ZERO);
            ai.setSum_hand_in(BigDecimal.ZERO);
            ai.setSum_hand_out(BigDecimal.ZERO);
            ai.setSum_pay_out(BigDecimal.ZERO);
            ai.setSum_agent_fee(BigDecimal.ZERO);
            ai.setSum_cash_fee(BigDecimal.ZERO);
            ai.setAcc_balance(BigDecimal.ZERO);
            ai.setPrize_balance(BigDecimal.ZERO);
            ai.setAccount_serial_no(Long.valueOf(0));

            int re = accountInfoSer.addAccountInfo(ai);
            logger.error("insert account info into DB re:" + re);

            if (re < 0) {
                resMap.put("result", "开户失败");
            } else {
                resMap.put("result", "开户成功");
            }
            return resMap;
        } catch (Exception e) {
            resMap.put("result", "网络异常");
        }
        return resMap;
    }

    /**
     * 从缓存中获取账户类型列表数据
     */
    @RequestMapping(value = "/selectAccountTypeList", method = RequestMethod.POST)
    @ResponseBody
    public List<AccountType> selectAccountTypeList(HttpServletRequest request, Model resModel) {
        logger.info("select AccountType list from cache");
        List<AccountType> list = accountTypeCache.getAccountTypeList();
        return list;
    }

}
