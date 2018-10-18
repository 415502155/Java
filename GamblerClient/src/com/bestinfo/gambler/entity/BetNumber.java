package com.bestinfo.gambler.entity;

/**
 * 号码信息类
 * @author chenliping
 */
public class BetNumber {
    private int gameid;
    private int playtype;
    private int betmod;
    private int stake;
    private String number;
    private float money; //总长度 4*5+255
    private int onlynum;//有效号码个数
    private int maxmulti;
    private String testdesc;

    public String getTestdesc() {
        return testdesc;
    }

    public void setTestdesc(String testdesc) {
        this.testdesc = testdesc;
    }
    
    

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public int getPlaytype() {
        return playtype;
    }

    public void setPlaytype(int playtype) {
        this.playtype = playtype;
    }

    public int getBetmod() {
        return betmod;
    }

    public void setBetmod(int betmod) {
        this.betmod = betmod;
    }

    public int getStake() {
        return stake;
    }

    public void setStake(int stake) {
        this.stake = stake;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getOnlynum() {
        return onlynum;
    }

    public void setOnlynum(int onlynum) {
        this.onlynum = onlynum;
    }

    public int getMaxmulti() {
        return maxmulti;
    }

    public void setMaxmulti(int maxmulti) {
        this.maxmulti = maxmulti;
    }
    
    
}
