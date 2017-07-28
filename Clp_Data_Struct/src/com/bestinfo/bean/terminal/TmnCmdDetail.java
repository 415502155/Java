/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.bean.terminal;

import java.io.Serializable;
import java.util.Date;

/**
 * 终端命令详情
 *
 * @author YangRong
 */
public class TmnCmdDetail implements Serializable{

    private static final long serialVersionUID = 3036140052881360692L;
    private Integer workingStaus;//状态 0:stop  1:running 
    private Integer terminalId;//终端id
    private String commandId;//命令id
    private Date startTime;//启动时间
    private Date stopTime;//结束时间
    private Integer validTime;//命令有效时间 单位:s  0:无限制
    private String command;//终端要执行的命令
    private String responseMsg;//终端响应信息

    @Override
    public String toString() {
        return "TmnCmdDetail{" + "workingStaus=" + workingStaus + ", terminalId=" + terminalId + ", commandId=" + commandId + ", startTime=" + startTime + ", stopTime=" + stopTime + ", validTime=" + validTime + ", command=" + command + ", responseMsg=" + responseMsg + '}';
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    public Integer getWorkingStaus() {
        return workingStaus;
    }

    public void setWorkingStaus(Integer workingStaus) {
        this.workingStaus = workingStaus;
    }

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

}
