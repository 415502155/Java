package com.bestinfo.bean.game;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author yangyuefu
 */
public class GameInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 627531352182548861L;
    private Integer game_id;//游戏编号
    private String game_type;//游戏类型 分类(C,K,B,S.N.H)
    private String game_name;//游戏名称
    private String short_name;//游戏简称
    private String game_code;//游戏代码
    private Integer play_num;//玩法个数
    private Integer repeat_select;//开奖号码可重复 1可重复;0不可重复
    private Integer open_min_no;//开奖最小号码
    private Integer open_max_no;//开奖最大号码
    private Integer open_min_blue_no;//二区开奖最小号码
    private Integer open_max_blue_no;//二区开奖最大号码
    private Integer open_basic_num;//开奖基本号码个数
    private Integer open_special_num;//开奖特别号码个数
    private Integer open_blue_num;//开奖蓝球号码个数
    private Integer lucky_no_group;//开奖号码组数
    private Integer open_num;//开奖最大次数
    private Integer prize_class_number;//奖级个数
    private Integer fix_prize_class_number;//固定奖级个数
    private Integer center_max_cash_class;//一级中心最大可兑奖级别
    private BigDecimal center_max_cash_money;//一级中心兑奖上限金额(不含)
    private Integer department_max_cash_class;//二级中心最大可兑奖级别
    private BigDecimal department_max_cash_money;//二级中心兑奖上限金额(不含)
    private Integer terminal_max_cash_class;//投注机最大可兑奖级别
    private BigDecimal terminal_max_cash_money;//投注机兑奖上限金额(不含)
    private Integer cur_draw_id;//当前期号
    private Integer draw_period_type;//开奖频度类型
    private String draw_period;//每期开奖日
    private String draw_time;//期结束时间
    private Integer cash_period_day;//兑奖期天数
    private Integer bet_line_way;//注信息存储方法   YangRong 8.10 加
    private Integer single_stake_num;//单式票最多注数  YangRong 8.10 加
    private Integer multi_draw_number;//多期期数
    private Integer union_type;//联合销售类型
    private Integer used_mark;//游戏在用标志
    private Integer undo_permit;//注销许可标志(
    private Integer sale_mark;//销售许可标志
    private Integer cash_mark;//兑奖许可标志
    private Integer data_save_day;//过期中奖数据保存时间
    private String game_version;//本游戏系统版本号
    private BigDecimal terminal_bet_money;//投注机单票默认最大金额
    private Integer game_control_type;//游戏风险控制方式 default 2,对于有风险控制的游戏进行号码注数和投注金额的控制
    private Integer control_group_num;//风险控制组数
    private Integer bind_game_id;//相关游戏
    private Integer cash_method;//兑奖方式 按奖级兑奖或者按奖金
    private Integer prize_precision;//派奖奖金精度
    private String init_time;//自动新期时间
    private String stat_time;//自动结算时间
    private String begin_time;//日销售开始时间
    private String end_time;//日销售结束时间
    private Integer keno_game;//快开游戏标记 1快开，0普通
    private Integer keno_draw_num;//keno期数 快开游戏有期数，普通游戏期数为0
    private Integer draw_length;//每期销售时间长度
    private Integer multi_keno_num;//KENO销售多期期数
    private Integer next_draw_time;//下期开始时间(
    private Integer bulletin_time;//发布公告时间 销售结束后预计开奖公告发布时间(单位：秒，当前设置：20s)
    private Integer re_bulletin_time;//公告间隔 重复取公告间隔(单位：秒，当前：5s)
    private Integer calc_method;//派奖计算方法
    private Integer jackpot_method;//奖池计算方法
    private Integer openprize_method;//开奖公告计算方法
    private Integer prep_draw_num;//预备期数
    private Integer open_configure_id;//开奖过程配置编号
    private Integer auto_open;//普通游戏自动开奖标识 ---add by clp 2015年1月24日11:02:49
    private Integer luckyno_time;//发布号码时间 ----add by clp 2014年9月26日19:47:08 

    public Integer getAuto_open() {
        return auto_open;
    }

    public void setAuto_open(Integer auto_open) {
        this.auto_open = auto_open;
    }
    
    /**
     * 发布号码时间
     *
     * @return
     */
    public Integer getLuckyno_time() {
        return luckyno_time;
    }

    /**
     * 发布号码时间
     *
     * @param luckyno_time
     */
    public void setLuckyno_time(Integer luckyno_time) {
        this.luckyno_time = luckyno_time;
    }
    //外用字段
    private BigDecimal cash_fee_rate;//默认兑奖代销费比例或金额

    public Integer getPrep_draw_num() {
        return prep_draw_num;
    }

    public void setPrep_draw_num(Integer prep_draw_num) {
        this.prep_draw_num = prep_draw_num;
    }

    public Integer getOpen_configure_id() {
        return open_configure_id;
    }

    public void setOpen_configure_id(Integer open_configure_id) {
        this.open_configure_id = open_configure_id;
    }

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    public String getGame_type() {
        return game_type;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getGame_code() {
        return game_code;
    }

    public void setGame_code(String game_code) {
        this.game_code = game_code;
    }

    public Integer getPlay_num() {
        return play_num;
    }

    public void setPlay_num(Integer play_num) {
        this.play_num = play_num;
    }

    public Integer getRepeat_select() {
        return repeat_select;
    }

    public void setRepeat_select(Integer repeat_select) {
        this.repeat_select = repeat_select;
    }

    public Integer getOpen_min_no() {
        return open_min_no;
    }

    public void setOpen_min_no(Integer open_min_no) {
        this.open_min_no = open_min_no;
    }

    public Integer getOpen_max_no() {
        return open_max_no;
    }

    public void setOpen_max_no(Integer open_max_no) {
        this.open_max_no = open_max_no;
    }

    public Integer getOpen_min_blue_no() {
        return open_min_blue_no;
    }

    public void setOpen_min_blue_no(Integer open_min_blue_no) {
        this.open_min_blue_no = open_min_blue_no;
    }

    public Integer getOpen_max_blue_no() {
        return open_max_blue_no;
    }

    public void setOpen_max_blue_no(Integer open_max_blue_no) {
        this.open_max_blue_no = open_max_blue_no;
    }

    public Integer getOpen_basic_num() {
        return open_basic_num;
    }

    public void setOpen_basic_num(Integer open_basic_num) {
        this.open_basic_num = open_basic_num;
    }

    public Integer getOpen_special_num() {
        return open_special_num;
    }

    public void setOpen_special_num(Integer open_special_num) {
        this.open_special_num = open_special_num;
    }

    public Integer getOpen_blue_num() {
        return open_blue_num;
    }

    public void setOpen_blue_num(Integer open_blue_num) {
        this.open_blue_num = open_blue_num;
    }

    public Integer getLucky_no_group() {
        return lucky_no_group;
    }

    public void setLucky_no_group(Integer lucky_no_group) {
        this.lucky_no_group = lucky_no_group;
    }

    public Integer getPrize_class_number() {
        return prize_class_number;
    }

    public void setPrize_class_number(Integer prize_class_number) {
        this.prize_class_number = prize_class_number;
    }

    public Integer getFix_prize_class_number() {
        return fix_prize_class_number;
    }

    public void setFix_prize_class_number(Integer fix_prize_class_number) {
        this.fix_prize_class_number = fix_prize_class_number;
    }

    public Integer getCenter_max_cash_class() {
        return center_max_cash_class;
    }

    public void setCenter_max_cash_class(Integer center_max_cash_class) {
        this.center_max_cash_class = center_max_cash_class;
    }

    public BigDecimal getCenter_max_cash_money() {
        return center_max_cash_money;
    }

    public void setCenter_max_cash_money(BigDecimal center_max_cash_money) {
        this.center_max_cash_money = center_max_cash_money;
    }

    public Integer getDepartment_max_cash_class() {
        return department_max_cash_class;
    }

    public void setDepartment_max_cash_class(Integer department_max_cash_class) {
        this.department_max_cash_class = department_max_cash_class;
    }

    public BigDecimal getDepartment_max_cash_money() {
        return department_max_cash_money;
    }

    public void setDepartment_max_cash_money(BigDecimal department_max_cash_money) {
        this.department_max_cash_money = department_max_cash_money;
    }

    public Integer getTerminal_max_cash_class() {
        return terminal_max_cash_class;
    }

    public void setTerminal_max_cash_class(Integer terminal_max_cash_class) {
        this.terminal_max_cash_class = terminal_max_cash_class;
    }

    public BigDecimal getTerminal_max_cash_money() {
        return terminal_max_cash_money;
    }

    public void setTerminal_max_cash_money(BigDecimal terminal_max_cash_money) {
        this.terminal_max_cash_money = terminal_max_cash_money;
    }

    public Integer getCur_draw_id() {
        return cur_draw_id;
    }

    public void setCur_draw_id(Integer cur_draw_id) {
        this.cur_draw_id = cur_draw_id;
    }

    public Integer getDraw_period_type() {
        return draw_period_type;
    }

    public void setDraw_period_type(Integer draw_period_type) {
        this.draw_period_type = draw_period_type;
    }

    public String getDraw_period() {
        return draw_period;
    }

    public void setDraw_period(String draw_period) {
        this.draw_period = draw_period;
    }

    public String getDraw_time() {
        return draw_time;
    }

    public void setDraw_time(String draw_time) {
        this.draw_time = draw_time;
    }

    public Integer getCash_period_day() {
        return cash_period_day;
    }

    public void setCash_period_day(Integer cash_period_day) {
        this.cash_period_day = cash_period_day;
    }

    public Integer getMulti_draw_number() {
        return multi_draw_number;
    }

    public void setMulti_draw_number(Integer multi_draw_number) {
        this.multi_draw_number = multi_draw_number;
    }

    public Integer getUnion_type() {
        return union_type;
    }

    public void setUnion_type(Integer union_type) {
        this.union_type = union_type;
    }

    public Integer getUsed_mark() {
        return used_mark;
    }

    public void setUsed_mark(Integer used_mark) {
        this.used_mark = used_mark;
    }

    public Integer getUndo_permit() {
        return undo_permit;
    }

    public void setUndo_permit(Integer undo_permit) {
        this.undo_permit = undo_permit;
    }

    public Integer getSale_mark() {
        return sale_mark;
    }

    public void setSale_mark(Integer sale_mark) {
        this.sale_mark = sale_mark;
    }

    public Integer getCash_mark() {
        return cash_mark;
    }

    public void setCash_mark(Integer cash_mark) {
        this.cash_mark = cash_mark;
    }

    public Integer getData_save_day() {
        return data_save_day;
    }

    public void setData_save_day(Integer data_save_day) {
        this.data_save_day = data_save_day;
    }

    public String getGame_version() {
        return game_version;
    }

    public void setGame_version(String game_version) {
        this.game_version = game_version;
    }

    public BigDecimal getTerminal_bet_money() {
        return terminal_bet_money;
    }

    public void setTerminal_bet_money(BigDecimal terminal_bet_money) {
        this.terminal_bet_money = terminal_bet_money;
    }

    public Integer getGame_control_type() {
        return game_control_type;
    }

    public void setGame_control_type(Integer game_control_type) {
        this.game_control_type = game_control_type;
    }

    public Integer getBind_game_id() {
        return bind_game_id;
    }

    public void setBind_game_id(Integer bind_game_id) {
        this.bind_game_id = bind_game_id;
    }

    public Integer getCash_method() {
        return cash_method;
    }

    public void setCash_method(Integer cash_method) {
        this.cash_method = cash_method;
    }

    public Integer getPrize_precision() {
        return prize_precision;
    }

    public void setPrize_precision(Integer prize_precision) {
        this.prize_precision = prize_precision;
    }

    public String getInit_time() {
        return init_time;
    }

    public void setInit_time(String init_time) {
        this.init_time = init_time;
    }

    public String getStat_time() {
        return stat_time;
    }

    public void setStat_time(String stat_time) {
        this.stat_time = stat_time;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public Integer getKeno_game() {
        return keno_game;
    }

    public void setKeno_game(Integer keno_game) {
        this.keno_game = keno_game;
    }

    public Integer getKeno_draw_num() {
        return keno_draw_num;
    }

    public void setKeno_draw_num(Integer keno_draw_num) {
        this.keno_draw_num = keno_draw_num;
    }

    public Integer getDraw_length() {
        return draw_length;
    }

    public void setDraw_length(Integer draw_length) {
        this.draw_length = draw_length;
    }

    public Integer getMulti_keno_num() {
        return multi_keno_num;
    }

    public void setMulti_keno_num(Integer multi_keno_num) {
        this.multi_keno_num = multi_keno_num;
    }

    public Integer getNext_draw_time() {
        return next_draw_time;
    }

    public void setNext_draw_time(Integer next_draw_time) {
        this.next_draw_time = next_draw_time;
    }

    public Integer getBulletin_time() {
        return bulletin_time;
    }

    public void setBulletin_time(Integer bulletin_time) {
        this.bulletin_time = bulletin_time;
    }

    public Integer getRe_bulletin_time() {
        return re_bulletin_time;
    }

    public void setRe_bulletin_time(Integer re_bulletin_time) {
        this.re_bulletin_time = re_bulletin_time;
    }

    public Integer getOpen_num() {
        return open_num;
    }

    public void setOpen_num(Integer open_num) {
        this.open_num = open_num;
    }

    public Integer getControl_group_num() {
        return control_group_num;
    }

    public void setControl_group_num(Integer control_group_num) {
        this.control_group_num = control_group_num;
    }

    public void setSingle_stake_num(Integer single_stake_num) {
        this.single_stake_num = single_stake_num;
    }

    public Integer getSingle_stake_num() {
        return single_stake_num;
    }

    public void setBet_line_way(Integer bet_line_way) {
        this.bet_line_way = bet_line_way;
    }

    public Integer getBet_line_way() {
        return bet_line_way;
    }

    public Integer getCalc_method() {
        return calc_method;
    }

    public void setCalc_method(Integer calc_method) {
        this.calc_method = calc_method;
    }

    public Integer getJackpot_method() {
        return jackpot_method;
    }

    public void setJackpot_method(Integer jackpot_method) {
        this.jackpot_method = jackpot_method;
    }

    public Integer getOpenprize_method() {
        return openprize_method;
    }

    public void setOpenprize_method(Integer openprize_method) {
        this.openprize_method = openprize_method;
    }

    public BigDecimal getCash_fee_rate() {
        return cash_fee_rate;
    }

    public void setCash_fee_rate(BigDecimal cash_fee_rate) {
        this.cash_fee_rate = cash_fee_rate;
    }

}
