package com.bestinfo.bean.task;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 统计汇总表-自然日站点统计(T_natural_tmn_day)
 *
 * @author LH
 */
public class NatureTmnDay implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6677928590290427703L;

    /**
     * 地市名称
     */
    private String city_name;
    /**
     * 终端编号
     */
    private Integer terminal_id;
    /**
     * 设备编号
     */
    private Integer equipment_id;
    /**
     * 设备类型
     */
    private String equipment_type;
    /**
     * 游戏名称
     */
    private String game_name;
    /**
     * 销售额
     */
    private BigDecimal sale_money;
    /**
     * 兑奖额
     */
    private BigDecimal cash_money;
    /**
     * 注销额
     */
    private BigDecimal undo_money;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public Integer getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(Integer terminal_id) {
        this.terminal_id = terminal_id;
    }

    public Integer getEquipment_id() {
        return equipment_id;
    }

    public void setEquipment_id(Integer equipment_id) {
        this.equipment_id = equipment_id;
    }

    public String getEquipment_type() {
        return equipment_type;
    }

    public void setEquipment_type(String equipment_type) {
        this.equipment_type = equipment_type;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public BigDecimal getSale_money() {
        return sale_money;
    }

    public void setSale_money(BigDecimal sale_money) {
        this.sale_money = sale_money;
    }

    public BigDecimal getCash_money() {
        return cash_money;
    }

    public void setCash_money(BigDecimal cash_money) {
        this.cash_money = cash_money;
    }

    public BigDecimal getUndo_money() {
        return undo_money;
    }

    public void setUndo_money(BigDecimal undo_money) {
        this.undo_money = undo_money;
    }

}
