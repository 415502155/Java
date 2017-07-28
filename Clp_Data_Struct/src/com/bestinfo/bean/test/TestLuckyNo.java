package com.bestinfo.bean.test;

import java.io.Serializable;

/**
 * 固定快3的开奖号码
 *
 * @author yangyuefu
 */
public class TestLuckyNo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -777931559786610137L;
	
	private String lucky_no;

    public String getLucky_no() {
        return lucky_no;
    }

    public void setLucky_no(String lucky_no) {
        this.lucky_no = lucky_no;
    }

}
