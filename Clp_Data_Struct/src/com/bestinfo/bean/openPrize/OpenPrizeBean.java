package com.bestinfo.bean.openPrize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 普通游戏 开奖公告数据结构
 */
public class OpenPrizeBean implements Serializable {

    private static final long serialVersionUID = 4528271390821959644L;
    private Integer isKeno;
    private Integer gameId;
    private String gameName;
    private String gameCode;
    private Integer drawId;
    private String drawName;  //期名
    private Integer kdrawId;   //快开期号
    private String kdrawName; //快开期名  
    private BigDecimal betTotalMoney;  //本期投注总额
    private BigDecimal betLotteryMoney;  //风采系统投注总额
    private List<LuckyOpen> listLuckOpen;  //中奖结果
    private BigDecimal endPool;  //奖池资金累计金额
    private Integer cashPeriodDay; //兑奖期天数

    //added by yfyang，20160615，七乐彩加奖奖池，给无纸化用的
    private BigDecimal plusEndPool;//加奖奖池
    private Integer openConfigureId;//开奖步骤，3是正常开奖，13是加奖

    //added by yfyang,20161012,双色球加奖奖池，给无纸化用的
    private String note;//三种情况1000@500  -0.01@500  1000@-0.01

    /**
     * @return the isKeno
     */
    public Integer getIsKeno() {
        return isKeno;
    }

    /**
     * @param isKeno the isKeno to set
     */
    public void setIsKeno(Integer isKeno) {
        this.isKeno = isKeno;
    }

    /**
     * @return the gameId
     */
    public Integer getGameId() {
        return gameId;
    }

    /**
     * @param gameId the gameId to set
     */
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    /**
     * @return the gameName
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * @param gameName the gameName to set
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    /**
     * @return the drawId
     */
    public Integer getDrawId() {
        return drawId;
    }

    /**
     * @param drawId the drawId to set
     */
    public void setDrawId(Integer drawId) {
        this.drawId = drawId;
    }

    /**
     * @return the drawName
     */
    public String getDrawName() {
        return drawName;
    }

    /**
     * @param drawName the drawName to set
     */
    public void setDrawName(String drawName) {
        this.drawName = drawName;
    }

    /**
     * @return the kdrawId
     */
    public Integer getKdrawId() {
        return kdrawId;
    }

    /**
     * @param kdrawId the kdrawId to set
     */
    public void setKdrawId(Integer kdrawId) {
        this.kdrawId = kdrawId;
    }

    /**
     * @return the kdrawName
     */
    public String getKdrawName() {
        return kdrawName;
    }

    /**
     * @param kdrawName the kdrawName to set
     */
    public void setKdrawName(String kdrawName) {
        this.kdrawName = kdrawName;
    }

    /**
     * @return the betTotalMoney
     */
    public BigDecimal getBetTotalMoney() {
        return betTotalMoney;
    }

    /**
     * @param betTotalMoney the betTotalMoney to set
     */
    public void setBetTotalMoney(BigDecimal betTotalMoney) {
        this.betTotalMoney = betTotalMoney;
    }

    /**
     * @return the betLotteryMoney
     */
    public BigDecimal getBetLotteryMoney() {
        return betLotteryMoney;
    }

    /**
     * @param betLotteryMoney the betLotteryMoney to set
     */
    public void setBetLotteryMoney(BigDecimal betLotteryMoney) {
        this.betLotteryMoney = betLotteryMoney;
    }

    /**
     * @return the listLuckOpen
     */
    public List<LuckyOpen> getListLuckOpen() {
        return listLuckOpen;
    }

    /**
     * @param listLuckOpen the listLuckOpen to set
     */
    public void setListLuckOpen(List<LuckyOpen> listLuckOpen) {
        this.listLuckOpen = listLuckOpen;
    }

    /**
     * @return the endPool
     */
    public BigDecimal getEndPool() {
        return endPool;
    }

    /**
     * @param endPool the endPool to set
     */
    public void setEndPool(BigDecimal endPool) {
        this.endPool = endPool;
    }

    /**
     * @return the cashPeriodDay
     */
    public Integer getCashPeriodDay() {
        return cashPeriodDay;
    }

    /**
     * @param cashPeriodDay the cashPeriodDay to set
     */
    public void setCashPeriodDay(Integer cashPeriodDay) {
        this.cashPeriodDay = cashPeriodDay;
    }

    public BigDecimal getPlusEndPool() {
        return plusEndPool;
    }

    public void setPlusEndPool(BigDecimal plusEndPool) {
        this.plusEndPool = plusEndPool;
    }

    public Integer getOpenConfigureId() {
        return openConfigureId;
    }

    public void setOpenConfigureId(Integer openConfigureId) {
        this.openConfigureId = openConfigureId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
