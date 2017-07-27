package com.bestinfo.bean.h5.comparison;

import com.bestinfo.bean.h5.monitor.*;

/**
 *
 * @author Administrator
 */
public class WeekReportGame {
    private Integer game_id;      //游戏id
    private String  game_name;    //游戏名称
    private Integer col_no;
    private String  col_title;
    private Integer hand_input;
    private Integer ticket_type;
    private Integer work_status;

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
     * @return the game_name
     */
    public String getGame_name() {
        return game_name;
    }

    /**
     * @param game_name the game_name to set
     */
    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    /**
     * @return the col_no
     */
    public Integer getCol_no() {
        return col_no;
    }

    /**
     * @param col_no the col_no to set
     */
    public void setCol_no(Integer col_no) {
        this.col_no = col_no;
    }

    /**
     * @return the col_title
     */
    public String getCol_title() {
        return col_title;
    }

    /**
     * @param col_title the col_title to set
     */
    public void setCol_title(String col_title) {
        this.col_title = col_title;
    }

    /**
     * @return the hand_input
     */
    public Integer getHand_input() {
        return hand_input;
    }

    /**
     * @param hand_input the hand_input to set
     */
    public void setHand_input(Integer hand_input) {
        this.hand_input = hand_input;
    }

    /**
     * @return the ticket_type
     */
    public Integer getTicket_type() {
        return ticket_type;
    }

    /**
     * @param ticket_type the ticket_type to set
     */
    public void setTicket_type(Integer ticket_type) {
        this.ticket_type = ticket_type;
    }

    /**
     * @return the work_status
     */
    public Integer getWork_status() {
        return work_status;
    }

    /**
     * @param work_status the work_status to set
     */
    public void setWork_status(Integer work_status) {
        this.work_status = work_status;
    }
  
}
