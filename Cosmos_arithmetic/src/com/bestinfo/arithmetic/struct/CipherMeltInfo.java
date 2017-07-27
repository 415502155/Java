package com.bestinfo.arithmetic.struct;

/**
 * 存放由cipher解析出来的信息
 *
 * @author chenliping
 */
public class CipherMeltInfo {

    private int pro_id;//省id号
    private int node;//站点 完整
    private int game_id;//游戏id 完整
    private int period;//期信息 完整
    private int seq_no;//流水号 完整
//        private Date tm_time;//交易时间,Year是原值%50+2000
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;
    private int check_code;//校验码 

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getCheck_code() {
        return check_code;
    }

    public void setCheck_code(int check_code) {
        this.check_code = check_code;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setSeq_no(int seq_no) {
        this.seq_no = seq_no;
    }

//        public void setTm_time(Date tm_time) {
//            this.tm_time = tm_time;
//        }
    public int getPro_id() {
        return pro_id;
    }

    public int getNode() {
        return node;
    }

    public int getGame_id() {
        return game_id;
    }

    public int getPeriod() {
        return period;
    }

    public int getSeq_no() {
        return seq_no;
    }

//        public Date getTm_time() {
//            return tm_time;
//        }
}
