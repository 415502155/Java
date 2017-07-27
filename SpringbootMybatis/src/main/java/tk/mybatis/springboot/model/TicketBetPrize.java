package tk.mybatis.springboot.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * 彩票数据明细
 *
 */
public class TicketBetPrize implements Serializable {

	private static final long serialVersionUID = 1L;
	private String cipher;   //彩票密码
    private BigInteger sequence_id;//序列号
    private Integer game_id;  //游戏编号
    private Integer draw_id;    //总期号
    private Integer keno_draw_id;  //快开期号
    private Integer terminal_id;   //投注机号
    private Integer serial_no;   //彩票流水号
    private Integer operator_id;  //操作用户
    private Integer play_id;  //玩法编号
    private Integer bet_method;//投注方法
    private Date bet_time; // 投注时间
    private Integer bet_mode;   //投注方式
    private Integer bet_stakes;//投注总注数
    private BigDecimal bet_money; //投注金额
    private Integer bet_multiple; //倍数
    private String bet_line; //号码内容
    private Integer bet_num;//投注号码总个数
    private Integer bet_section; //投注号码总段数
    private String bet_line_md5;  //投注内容MD5
    private String prize_detail;
    private BigDecimal common_prize;
    private String draw_name;

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
     * @return the bet_line_md5
     */
    public String getBet_line_md5() {
        return bet_line_md5;
    }

    /**
     * @param bet_line_md5 the bet_line_md5 to set
     */
    public void setBet_line_md5(String bet_line_md5) {
        this.bet_line_md5 = bet_line_md5;
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
     * @return the draw_name
     */
    public String getDraw_name() {
        return draw_name;
    }

    /**
     * @param draw_name the draw_name to set
     */
    public void setDraw_name(String draw_name) {
        this.draw_name = draw_name;
    }
    
}
