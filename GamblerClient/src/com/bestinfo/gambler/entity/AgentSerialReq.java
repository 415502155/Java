/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.gambler.entity;


import java.util.List;

/**
 *
 * @author zhen
 */
public class AgentSerialReq {

    @Override
    public String toString() {
        return "AgentSerialReq{" + "gambler_num=" + gambler_num + ", applySerials=" + applySerials + '}';
    }

    public AgentSerialReq() {
    }

    public AgentSerialReq(int gambler_num, List<ApplySerial> applySerials) {
        this.gambler_num = gambler_num;
        this.applySerials = applySerials;
    }
    //彩民个数
    private int gambler_num;
    //用户名+序列号个数的请求集合
    private List<ApplySerial> applySerials;

    public int getGambler_num() {
        return gambler_num;
    }

    public void setGambler_num(int gambler_num) {
        this.gambler_num = gambler_num;
    }

    public List<ApplySerial> getApplySerials() {
        return applySerials;
    }

    public void setApplySerials(List<ApplySerial> applySerials) {
        this.applySerials = applySerials;
    }

    //内部类
    public class ApplySerial {

        @Override
        public String toString() {
            return "ApplySerial{" + "gambler_name=" + gambler_name + ", num=" + num + '}';
        }

        public ApplySerial() {
        }

        public ApplySerial(String gambler_name, int num) {
            this.gambler_name = gambler_name;
            this.num = num;
        }
        //用户名
        private String gambler_name;
        //序列号个数
        private int num;

        public String getGambler_name() {
            return gambler_name;
        }

        public void setGambler_name(String gambler_name) {
            this.gambler_name = gambler_name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }

    public static void main(String[] args) {
        AgentSerialReq.ApplySerial as = null;
    }
}
