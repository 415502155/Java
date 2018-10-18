package com.bestinfo.bean.h5.monitor;

/**
 * 各个地区的投注机的总量
 */
public class CityTmnCount {
     private Integer tmn_num;        //投注机数量
     private String city_name;       //城市名称

    /**
     * @return the tmn_num
     */
    public Integer getTmn_num() {
        return tmn_num;
    }

    /**
     * @param tmn_num the tmn_num to set
     */
    public void setTmn_num(Integer tmn_num) {
        this.tmn_num = tmn_num;
    }

    /**
     * @return the city_name
     */
    public String getCity_name() {
        return city_name;
    }

    /**
     * @param city_name the city_name to set
     */
    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
