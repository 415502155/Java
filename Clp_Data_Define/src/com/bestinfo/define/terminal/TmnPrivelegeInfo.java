package com.bestinfo.define.terminal;

import java.math.BigDecimal;

/**
 *
 * 终端特权默认值
 * @author hhhh
 */
public class TmnPrivelegeInfo {

    /**
     * 单票最小金额
     */
    public static BigDecimal min_bet_money = new BigDecimal(2);
    /**
     * 单期最大金额
     */
    public static BigDecimal max_sales_money = new BigDecimal(1000000);
}
