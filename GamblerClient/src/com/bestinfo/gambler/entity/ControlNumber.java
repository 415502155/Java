/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.gambler.entity;

/**
 * 控制文件xml的类
 * @author chenliping
 */
public class ControlNumber {
    private int gameid;
    private int playtype;
    private int betmod;
    private String filename;
    private int startline;
    private int endline;

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public int getPlaytype() {
        return playtype;
    }

    public void setPlaytype(int playtype) {
        this.playtype = playtype;
    }

    public int getBetmod() {
        return betmod;
    }

    public void setBetmod(int betmod) {
        this.betmod = betmod;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getStartline() {
        return startline;
    }

    public void setStartline(int startline) {
        this.startline = startline;
    }

    public int getEndline() {
        return endline;
    }

    public void setEndline(int endline) {
        this.endline = endline;
    }
    
    
}
