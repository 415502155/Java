package com.bestinfo.bean.openPrize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 普通游戏
 *
 * @author lvchangrong
 */
public class LuckyOpen implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8410388930395635896L;
	private Integer openId;
    private String baseNum; //基本号码
    private String specialNum; //特殊号码
    private BigDecimal luckyTotalMoney;  //本期中奖总额
    private List<LuckyDetail> listLuckyDetail;  //中奖结果

    /**
     * @return the baseNum
     */
    public String getBaseNum() {
        return baseNum;
    }

    /**
     * @param baseNum the baseNum to set
     */
    public void setBaseNum(String baseNum) {
        this.baseNum = baseNum;
    }

    /**
     * @return the specialNum
     */
    public String getSpecialNum() {
        return specialNum;
    }

    /**
     * @param specialNum the specialNum to set
     */
    public void setSpecialNum(String specialNum) {
        this.specialNum = specialNum;
    }

    /**
     * @return the listLuckyDetail
     */
    public List<LuckyDetail> getListLuckyDetail() {
        return listLuckyDetail;
    }

    /**
     * @param listLuckyDetail the listLuckyDetail to set
     */
    public void setListLuckyDetail(List<LuckyDetail> listLuckyDetail) {
        this.listLuckyDetail = listLuckyDetail;
    }

    /**
     * @return the openId
     */
    public Integer getOpenId() {
        return openId;
    }

    /**
     * @param openId the openId to set
     */
    public void setOpenId(Integer openId) {
        this.openId = openId;
    }

    /**
     * @return the luckyTotalMoney
     */
    public BigDecimal getLuckyTotalMoney() {
        return luckyTotalMoney;
    }

    /**
     * @param luckyTotalMoney the luckyTotalMoney to set
     */
    public void setLuckyTotalMoney(BigDecimal luckyTotalMoney) {
        this.luckyTotalMoney = luckyTotalMoney;
    }
}
