package com.bestinfo.bean.h5.comparison;

import com.bestinfo.bean.h5.monitor.*;
import java.math.BigDecimal;

/**
 *
 * @author Administrator
 */
public class WeekReportSales {
    private Integer game_id;      //游戏id
    private Integer city_id;      //城市id
    private Integer year_id;      //年份id
    private Integer week_id;      //周id
    private BigDecimal sale_money ; //年销售量

    /**
     * @return the game_id
     */
    public Integer getGame_id() {
        return game_id;
    }

    /**
     * @param game_id the game_id to set
     */
    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    /**
     * @return the city_id
     */
    public Integer getCity_id() {
        return city_id;
    }

    /**
     * @param city_id the city_id to set
     */
    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
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

}
