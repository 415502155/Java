package com.bestinfo.bean.ticket;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * 彩票-中奖数据明细(T_ticket_prize)
 */
public class TicketPrize implements Serializable {

    private String cipher;//彩票密码
    private BigInteger sequence_id; //序列号

    private Integer game_id;   //游戏编号
    private Integer draw_id; //总期号
    private Integer keno_draw_id; //快开期号

    private Integer terminal_id; //投注机号
    private Integer serial_no; //彩票流水号
    private Integer operator_id;  //操作用户
    private Integer play_id;  //玩法编号
    private Integer bet_method;//投注方法

    private Date bet_time; // 投注时间
    private Integer bet_mode;   //投注方式
    private Integer bet_stakes;//投注总注数
    private BigDecimal bet_money = BigDecimal.ZERO; //投注金额
    private Integer bet_multiple; //倍数

    private String bet_line; //号码内容
    private Integer bet_num;//投注号码总个数
    private Integer bet_section; //投注号码总段数
    private Integer cashed_mark; //兑奖标志
    private Integer max_prize_class; //最大奖级

    private String prize_detail; //一次中奖明细
    private Integer cash_draw_id; // 兑奖期号
    private Integer cash_terminal_id;//兑奖投注机号
    private Date cash_time; //兑奖时间
    private BigDecimal common_prize = BigDecimal.ZERO; //常规开奖总金额

    private Integer multi_prize_mark; //是否多次中奖
    private BigDecimal total_prize = BigDecimal.ZERO; //总中奖金额含常规多次
    private BigDecimal tax_fee = BigDecimal.ZERO; //缴纳税金
    private String ticket_prize_md5; //信息Md5;
    private BigDecimal cash_fee = BigDecimal.ZERO; //兑奖代销费
    
    
    private String gambler_serial_no; // 交易流水号   add by lgq 2015-12-31 (生成中奖文件)
    
    private String draw_name; // 期名   add by lgq 2015-12-31 (中奖分布,后修改)
    
    private Integer betMode;   //投注方式
    
    private String betLine; //号码内容 与方案表一致

    public String getBetLine() {
        return betLine;
    }

    public void setBetLine(String betLine) {
        this.betLine = betLine;
    }

    public Integer getBetMode() {
        return betMode;
    }

    public void setBetMode(Integer betMode) {
        this.betMode = betMode;
    }

    public String getDraw_name() {
        return draw_name;
    }

    public void setDraw_name(String draw_name) {
        this.draw_name = draw_name;
    }

    public String getGambler_serial_no() {
        return gambler_serial_no;
    }

    public void setGambler_serial_no(String gambler_serial_no) {
        this.gambler_serial_no = gambler_serial_no;
    }

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
     * @return the operator_id
     */
    public Integer getOperator_id() {
        return operator_id;
    }

    /**
     * @param operator_id the operator_id to set
     */
    public void setOperator_id(Integer operator_id) {
        this.operator_id = operator_id;
    }

    /**
     * @return the play_id
     */
    public Integer getPlay_id() {
        return play_id;
    }

    /**
     * @param play_id the play_id to set
     */
    public void setPlay_id(Integer play_id) {
        this.play_id = play_id;
    }

    /**
     * @return the bet_method
     */
    public Integer getBet_method() {
        return bet_method;
    }

    /**
     * @param bet_method the bet_method to set
     */
    public void setBet_method(Integer bet_method) {
        this.bet_method = bet_method;
    }

    /**
     * @return the bet_time
     */
    public Date getBet_time() {
        return bet_time;
    }

    /**
     * @param bet_time the bet_time to set
     */
    public void setBet_time(Date bet_time) {
        this.bet_time = bet_time;
    }

    /**
     * @return the bet_mode
     */
    public Integer getBet_mode() {
        return bet_mode;
    }

    /**
     * @param bet_mode the bet_mode to set
     */
    public void setBet_mode(Integer bet_mode) {
        this.bet_mode = bet_mode;
    }

    /**
     * @return the bet_stakes
     */
    public Integer getBet_stakes() {
        return bet_stakes;
    }

    /**
     * @param bet_stakes the bet_stakes to set
     */
    public void setBet_stakes(Integer bet_stakes) {
        this.bet_stakes = bet_stakes;
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
     * @return the bet_multiple
     */
    public Integer getBet_multiple() {
        return bet_multiple;
    }

    /**
     * @param bet_multiple the bet_multiple to set
     */
    public void setBet_multiple(Integer bet_multiple) {
        this.bet_multiple = bet_multiple;
    }

    /**
     * @return the bet_line
     */
    public String getBet_line() {
        return bet_line;
    }

    /**
     * @param bet_line the bet_line to set
     */
    public void setBet_line(String bet_line) {
        this.bet_line = bet_line;
    }

    /**
     * @return the bet_num
     */
    public Integer getBet_num() {
        return bet_num;
    }

    /**
     * @param bet_num the bet_num to set
     */
    public void setBet_num(Integer bet_num) {
        this.bet_num = bet_num;
    }

    /**
     * @return the bet_section
     */
    public Integer getBet_section() {
        return bet_section;
    }

    /**
     * @param bet_section the bet_section to set
     */
    public void setBet_section(Integer bet_section) {
        this.bet_section = bet_section;
    }

    /**
     * @return the cashed_mark
     */
    public Integer getCashed_mark() {
        return cashed_mark;
    }

    /**
     * @param cashed_mark the cashed_mark to set
     */
    public void setCashed_mark(Integer cashed_mark) {
        this.cashed_mark = cashed_mark;
    }

    /**
     * @return the max_prize_class
     */
    public Integer getMax_prize_class() {
        return max_prize_class;
    }

    /**
     * @param max_prize_class the max_prize_class to set
     */
    public void setMax_prize_class(Integer max_prize_class) {
        this.max_prize_class = max_prize_class;
    }

    /**
     * @return the prize_detail
     */
    public String getPrize_detail() {
        return prize_detail;
    }

    /**
     * @param prize_detail the prize_detail to set
     */
    public void setPrize_detail(String prize_detail) {
        this.prize_detail = prize_detail;
    }

    /**
     * @return the cash_draw_id
     */
    public Integer getCash_draw_id() {
        return cash_draw_id;
    }

    /**
     * @param cash_draw_id the cash_draw_id to set
     */
    public void setCash_draw_id(Integer cash_draw_id) {
        this.cash_draw_id = cash_draw_id;
    }

    /**
     * @return the cash_terminal_id
     */
    public Integer getCash_terminal_id() {
        return cash_terminal_id;
    }

    /**
     * @param cash_terminal_id the cash_terminal_id to set
     */
    public void setCash_terminal_id(Integer cash_terminal_id) {
        this.cash_terminal_id = cash_terminal_id;
    }

    /**
     * @return the cash_time
     */
    public Date getCash_time() {
        return cash_time;
    }

    /**
     * @param cash_time the cash_time to set
     */
    public void setCash_time(Date cash_time) {
        this.cash_time = cash_time;
    }

    /**
     * @return the common_prize
     */
    public BigDecimal getCommon_prize() {
        return common_prize;
    }

    /**
     * @param common_prize the common_prize to set
     */
    public void setCommon_prize(BigDecimal common_prize) {
        this.common_prize = common_prize;
    }

    /**
     * @return the multi_prize_mark
     */
    public Integer getMulti_prize_mark() {
        return multi_prize_mark;
    }

    /**
     * @param multi_prize_mark the multi_prize_mark to set
     */
    public void setMulti_prize_mark(Integer multi_prize_mark) {
        this.multi_prize_mark = multi_prize_mark;
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
     * @return the tax_fee
     */
    public BigDecimal getTax_fee() {
        return tax_fee;
    }

    /**
     * @param tax_fee the tax_fee to set
     */
    public void setTax_fee(BigDecimal tax_fee) {
        this.tax_fee = tax_fee;
    }

    /**
     * @return the ticket_prize_md5
     */
    public String getTicket_prize_md5() {
        return ticket_prize_md5;
    }

    /**
     * @param ticket_prize_md5 the ticket_prize_md5 to set
     */
    public void setTicket_prize_md5(String ticket_prize_md5) {
        this.ticket_prize_md5 = ticket_prize_md5;
    }

    /**
     * @return the cash_fee
     */
    public BigDecimal getCash_fee() {
        return cash_fee;
    }

    /**
     * @param cash_fee the cash_fee to set
     */
    public void setCash_fee(BigDecimal cash_fee) {
        this.cash_fee = cash_fee;
    }

}
