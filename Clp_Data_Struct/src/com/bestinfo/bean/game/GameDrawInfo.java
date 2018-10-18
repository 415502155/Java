package com.bestinfo.bean.game;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 游戏期信息表
 *
 * @author yangyuefu
 */
public class GameDrawInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4147596971542473634L;
    private Integer game_id;//游戏编号
    private Integer draw_id;//总期号
    private String draw_name;//总期号
    private Integer draw_type;//本期类型    

    private Date sales_begin;//开始销售时间
    private Date sales_end;//结束销售时间
    private Date cash_begin;//兑奖开始时间
    private Date cash_end;//兑奖结束时间
    private Integer process_status;//期状态
    private Integer keno_draw_num;//keno期数
    private Integer begin_keno_draw_id;//本期开始keno期号
    private Integer end_keno_draw_id;//本期结束keno期号

    private String gameName;//页面显示使用，游戏名称
    private String drawProStatusName;//页面显示使用，期状态名称

    private List<GameKDrawInfo> kdrawList;//该期下的所有keno期数据
    
    private int currentDraw;//是否是当前期
    private int no;//序号显示（为4D监控页面准备）

    public String getDrawProStatusName() {
        return drawProStatusName;
    }

    public void setDrawProStatusName(String drawProStatusName) {
        this.drawProStatusName = drawProStatusName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    public Integer getDraw_id() {
        return draw_id;
    }

    public void setDraw_id(Integer draw_id) {
        this.draw_id = draw_id;
    }

    public String getDraw_name() {
        return draw_name;
    }

    public void setDraw_name(String draw_name) {
        this.draw_name = draw_name;
    }

    public Integer getDraw_type() {
        return draw_type;
    }

    public void setDraw_type(Integer draw_type) {
        this.draw_type = draw_type;
    }

    public Date getSales_begin() {
        return sales_begin;
    }

    public void setSales_begin(Date sales_begin) {
        this.sales_begin = sales_begin;
    }

    public Date getSales_end() {
        return sales_end;
    }

    public void setSales_end(Date sales_end) {
        this.sales_end = sales_end;
    }

    public Date getCash_begin() {
        return cash_begin;
    }

    public void setCash_begin(Date cash_begin) {
        this.cash_begin = cash_begin;
    }

    public Date getCash_end() {
        return cash_end;
    }

    public void setCash_end(Date cash_end) {
        this.cash_end = cash_end;
    }

    public Integer getProcess_status() {
        return process_status;
    }

    public void setProcess_status(Integer process_status) {
        this.process_status = process_status;
    }

    public Integer getKeno_draw_num() {
        return keno_draw_num;
    }

    public void setKeno_draw_num(Integer keno_draw_num) {
        this.keno_draw_num = keno_draw_num;
    }

    public Integer getBegin_keno_draw_id() {
        return begin_keno_draw_id;
    }

    public void setBegin_keno_draw_id(Integer begin_keno_draw_id) {
        this.begin_keno_draw_id = begin_keno_draw_id;
    }

    public Integer getEnd_keno_draw_id() {
        return end_keno_draw_id;
    }

    public void setEnd_keno_draw_id(Integer end_keno_draw_id) {
        this.end_keno_draw_id = end_keno_draw_id;
    }

    public List<GameKDrawInfo> getKdrawList() {
        return kdrawList;
    }

    public void setKdrawList(List<GameKDrawInfo> kdrawList) {
        this.kdrawList = kdrawList;
    }

    public int getCurrentDraw() {
        return currentDraw;
    }

    public void setCurrentDraw(int currentDraw) {
        this.currentDraw = currentDraw;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

}
