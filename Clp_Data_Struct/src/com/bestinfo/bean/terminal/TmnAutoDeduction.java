
package com.bestinfo.bean.terminal;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 投注机-终端自动扣除资金设置表(T_tmn_auto_deduction)
 * 
 * @author hhhh
 */
public class TmnAutoDeduction implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 300014385288530692L;

	/**
     * 终端编号
     */
    private Long terminal_id;
    
    /**
     * 交易类型
     */
    private Integer trade_type;
    
    /**
     * 扣款金额
     */
    private BigDecimal deduct_money;
   
    /**
     * 使用状态
     */
    private Integer work_status;

    public Long getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(Long terminal_id) {
        this.terminal_id = terminal_id;
    }

    public Integer getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(Integer trade_type) {
        this.trade_type = trade_type;
    }

    public BigDecimal getDeduct_money() {
        return deduct_money;
    }

    public void setDeduct_money(BigDecimal deduct_money) {
        this.deduct_money = deduct_money;
    }

    public Integer getWork_status() {
        return work_status;
    }

    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }
    
}
