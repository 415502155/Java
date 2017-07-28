package com.bestinfo.bean.fs;

import java.io.Serializable;

/**
 *
 * @author yangyuefu
 */
public class QueryEtlPrizeProgress implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3421396324453017590L;

	/**
     * meta库总中奖票数量
     */
    private int meta_total_ticket_num;

    /**
     * meta库当前入库中奖票数量
     */
    private int meta_cur_ticket_num;

    /**
     * term库总中奖票数量
     */
    private int term_total_ticket_num;

    /**
     * term库当前入库中奖票数量
     */
    private int term_cur_ticket_num;

    /**
     * gamb库总中奖票数量
     */
    private int gamb_total_ticket_num;

    /**
     * gamb库当前入库中奖票数量
     */
    private int gamb_cur_ticket_num;

    public int getTerm_total_ticket_num() {
        return term_total_ticket_num;
    }

    public void setTerm_total_ticket_num(int term_total_ticket_num) {
        this.term_total_ticket_num = term_total_ticket_num;
    }

    public int getTerm_cur_ticket_num() {
        return term_cur_ticket_num;
    }

    public void setTerm_cur_ticket_num(int term_cur_ticket_num) {
        this.term_cur_ticket_num = term_cur_ticket_num;
    }

    public int getMeta_total_ticket_num() {
        return meta_total_ticket_num;
    }

    public void setMeta_total_ticket_num(int meta_total_ticket_num) {
        this.meta_total_ticket_num = meta_total_ticket_num;
    }

    public int getMeta_cur_ticket_num() {
        return meta_cur_ticket_num;
    }

    public void setMeta_cur_ticket_num(int meta_cur_ticket_num) {
        this.meta_cur_ticket_num = meta_cur_ticket_num;
    }

    public int getGamb_total_ticket_num() {
        return gamb_total_ticket_num;
    }

    public void setGamb_total_ticket_num(int gamb_total_ticket_num) {
        this.gamb_total_ticket_num = gamb_total_ticket_num;
    }

    public int getGamb_cur_ticket_num() {
        return gamb_cur_ticket_num;
    }

    public void setGamb_cur_ticket_num(int gamb_cur_ticket_num) {
        this.gamb_cur_ticket_num = gamb_cur_ticket_num;
    }

}
