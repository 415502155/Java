/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.define.gambler;

/**
 *
 * @author chenliping
 */
public class SchemeStatus {
    /**定制成功*/
    public static final int SUCCESS = 1;
    /**方案提交*/
    public static final int SUBMIT = 2;
    /**重新投注*/
    public static final int REBET= 3;
    /**方案撤单*/
    public static final int CANCEL = 4;
    /**交易完成*/
    public static final int FINISH = 10;
    /**未中奖*/
    public static final int NOPRIZE = 20;
    /**中小奖*/
    public static final int SMALLPRIZE = 21;
    /**中大奖*/
    public static final int BIGPRIZE = 22;
    
    
    public static String getSchemeStatusName(int schemeStatus){
        String schemeStatusName=null;
        switch(schemeStatus){
            case SUCCESS:
                schemeStatusName="定制成功";
                break;
            case SUBMIT:
                schemeStatusName="定制成功";
                break;
            case REBET:
                schemeStatusName="定制成功";
                break;
            case CANCEL:
                schemeStatusName="交易撤单";
                break;
            case FINISH:
                schemeStatusName="交易完成";
                break;
            case NOPRIZE:
                schemeStatusName="未中奖";
                break;
            case SMALLPRIZE:
                schemeStatusName="中小奖";
                break;
            case BIGPRIZE:
                schemeStatusName="中大奖";
                break;
            default:
                return null;
        }
        
        return schemeStatusName;
    }
}
