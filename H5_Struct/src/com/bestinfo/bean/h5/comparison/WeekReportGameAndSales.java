package com.bestinfo.bean.h5.comparison;
import java.math.BigDecimal;

/**
 *
 * @author Administrator
 */
public class WeekReportGameAndSales {
    private String  game_name;    //游戏名称
    private BigDecimal sale_money1 ; //年销售量
    private Integer year_id;//年id
    private BigDecimal sale_money ; //周销售量
    private Integer week_id;//周id
    private Integer d;//年id+（周id+10）
    /**
     * @return the game_name
     */
    public String getGame_name() {
        return game_name;
    }

    /**
     * @param game_name the game_name to set
     */
    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    /**
     * @return the sale_money1
     */
    public BigDecimal getSale_money1() {
        return sale_money1;
    }

    /**
     * @param sale_money1 the sale_money1 to set
     */
    public void setSale_money1(BigDecimal sale_money1) {
        this.sale_money1 = sale_money1;
    }

    /**
     * @return the year_id
     */
    public Integer getYear_id() {
        return year_id;
    }

    /**
     * @param year_id the year_id to set
     */
    public void setYear_id(Integer year_id) {
        this.year_id = year_id;
    }

    /**
     * @return the sale_money
     */
    public BigDecimal getSale_money() {
        return sale_money;
    }

    /**
     * @param sale_money the sale_money to set
     */
    public void setSale_money(BigDecimal sale_money) {
        this.sale_money = sale_money;
    }

    /**
     * @return the week_id
     */
    public Integer getWeek_id() {
        return week_id;
    }

    /**
     * @param week_id the week_id to set
     */
    public void setWeek_id(Integer week_id) {
        this.week_id = week_id;
    }

    /**
     * @return the d
     */
    public Integer getD() {
        return d;
    }

    /**
     * @param d the d to set
     */
    public void setD(Integer d) {
        this.d = d;
    }


    
}
