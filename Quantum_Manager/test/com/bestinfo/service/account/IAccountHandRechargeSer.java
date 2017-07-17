/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.service.account;

/**
 *
 * @author shange
 */
public interface IAccountHandRechargeSer {

    /**
     * 手工充值
     *
     * @param terminalId
     * @param rechargeMoney
     * @return
     */
    public int procHandRecharge(String terminalId, double rechargeMoney);
}
