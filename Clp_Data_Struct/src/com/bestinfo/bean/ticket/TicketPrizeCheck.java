package com.bestinfo.bean.ticket;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * 验奖
 */
public class TicketPrizeCheck {

    private String cipher;   //彩票密码
    private BigInteger sequence_id;//序列号
    private Integer game_id;  //游戏编号
    private Integer draw_id;    //总期号
    private Integer keno_draw_id;  //快开期号
    private Integer terminal_id;   //投注机号
    private Integer serial_no;   //彩票流水号
    private BigDecimal bet_money = BigDecimal.ZERO; //投注金额
    private BigDecimal total_prize = BigDecimal.ZERO; //总奖金
    private Integer check_mark;//验票标识 0未验，1已验
    private Date check_time;//验票时间
    private Integer city_id;//地市编号
    private Integer user_id;//用户编号

    /**
     * @return the cipher
     */
    public String getCipher() {
        return cipher;
    }

    /**
     * @param cipher the cipher to set
     */
    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    /**
     * @return the sequence_id
     */
    public BigInteger getSequence_id() {
        return sequence_id;
    }

    /**
     * @param sequence_id the sequence_id to set
     */
    public void setSequence_id(BigInteger sequence_id) {
        this.sequence_id = sequence_id;
    }

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
     * @return the draw_id
     */
    public Integer getDraw_id() {
        return draw_id;
    }

    /**
     * @param draw_id the draw_id to set
     */
    public void setDraw_id(Integer draw_id) {
        this.draw_id = draw_id;
    }

    /**
     * @return the keno_draw_id
     */
    public Integer getKeno_draw_id() {
        return keno_draw_id;
    }

    /**
     * @param keno_draw_id the keno_draw_id to set
     */
    public void setKeno_draw_id(Integer keno_draw_id) {
        this.keno_draw_id = keno_draw_id;
    }

    /**
     * @return the terminal_id
     */
    public Integer getTerminal_id() {
        return terminal_id;
    }

    /**
     * @param terminal_id the terminal_id to set
     */
    public void setTerminal_id(Integer terminal_id) {
        this.terminal_id = terminal_id;
    }

    /**
     * @return the serial_no
     */
    public Integer getSerial_no() {
        return serial_no;
    }

    /**
     * @param serial_no the serial_no to set
     */
    public void setSerial_no(Integer serial_no) {
        this.serial_no = serial_no;
    }

    /**
     * @return the bet_money
     */
    public BigDecimal getBet_money() {
        return bet_money;
    }

    /**
     * @param bet_money the bet_money to set
     */
    public void setBet_money(BigDecimal bet_money) {
        this.bet_money = bet_money;
    }

    /**
     * @return the total_prize
     */
    public BigDecimal getTotal_prize() {
        return total_prize;
    }

    /**
     * @param total_prize the total_prize to set
     */
    public void setTotal_prize(BigDecimal total_prize) {
        this.total_prize = total_prize;
    }

    /**
     * @return the check_mark
     */
    public Integer getCheck_mark() {
        return check_mark;
    }

    /**
     * @param check_mark the check_mark to set
     */
    public void setCheck_mark(Integer check_mark) {
        this.check_mark = check_mark;
    }

    /**
     * @return the check_time
     */
    public Date getCheck_time() {
        return check_time;
    }

    /**
     * @param check_time the check_time to set
     */
    public void setCheck_time(Date check_time) {
        this.check_time = check_time;
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
     * @return the user_id
     */
    public Integer getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "TicketPrizeCheck{" + "cipher=" + cipher + ", sequence_id=" + sequence_id + ", game_id=" + game_id + ", draw_id=" + draw_id + ", keno_draw_id=" + keno_draw_id + ", terminal_id=" + terminal_id + ", serial_no=" + serial_no + ", bet_money=" + bet_money + ", total_prize=" + total_prize + ", check_mark=" + check_mark + ", check_time=" + check_time + ", city_id=" + city_id + ", user_id=" + user_id + '}';
    }

}
