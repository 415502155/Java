package com.bestinfo.define.Draw;

/**
 * 期状态数据
 *
 * @author chenliping
 */
public class DrawProStatus {

    public static int PROCESS_STATUS = 0;   //期状态
    /**
     * 期数据准备
     */
    public static int FIRSTPROCESS = 10;
    /**
     * 预售期
     */
    public static int PRESALE = 20;

    /**
     * 当前期
     */
    public static int SALING = 30;
    /**
     * 重新开奖
     */
    public static int RETRYLUCKY = 35;
    /**
     * 下期新期
     */
    public static int NEWDRAW = 80;
    /**
     * 同步后台
     */
    public static int SYNCHBACK = 110;
    /**
     * 终端封机
     */
//    public static int TERMINALGAMEOVER = 120;
    /**
     * 数据校验
     */
    public static int DATACHECK = 130;
    /**
     * 抽奖数据
     */
    public static int DRAWDATA = 140;
    /**
     * 合并数据
     */
    public static int MEGARDATA = 150;
    /**
     * 期结算
     */
    public static int STATISTICS = 160;
    /**
     * 期结算报表
     */
//    public static int STATISTICSEXCEL = 170;

    /**
     * 生成中彩数据
     */
    public static int LOTTERY_DATA = 180;

    /**
     * 中彩报表
     */
//    public static int CENTERLOTTERYEXCEL = 190;
    /**
     * 输入号码
     */
    public static int INPUTLUCKYNUMBER = 210;

    /**
     * 电子号码
     */
    public static int ELECTRONICNUMBER = 215;
    /**
     * 读取号码
     */
    public static int READERLUCKYNUMBER = 220;
    /**
     * 抽奖检索
     */
    public static int DRAWLUCKY = 230;
    /**
     * 抽奖结果
     */
//    public static int DRAWRESULT = 240;
    /**
     * 计算奖金
     */
    public static int CALCULATEMONEY = 250;
    /**
     * 读取奖金
     */
    public static int INPUTMONEY = 255;
    /**
     * 多次号码
     */
    public static int MORELUCKYNUMBER = 260;
    /**
     * 重复号码
     */
    public static int REPEATLUCKYNUMBER = 265;
    /**
     * 多次检索
     */
    public static int MOREDRAW = 270;
    /**
     * 结果处理
     */
//    public static int MORERESULT = 280;
    /**
     * 多次奖金
     */
    public static int MOREMONEY = 290;
    /**
     * 中奖分布
     */
    public static int LUCKYDISTRIBUTE = 300;
    /**
     * 多次分布
     */
    public static int MORELUCKYDISTRIBUTE = 310;
    /**
     * 注销处理
     */
    public static int DODESTROY = 320;
    /**
     * 多次注销
     */
    public static int MOREDESTROY = 330;
    /**
     * 中奖数据
     */
    public static int LUCKYDATA = 410;
    /**
     * 多次中奖
     */
    public static int MORELUCKY = 420;
    /**
     * 开奖结束
     */
    public static int TROPHYOVER = 430;
    /**
     * 兑奖期
     */
    public static int GETMONEY = 500;
    /**
     * 兑奖结算
     */
    public static int GETMONEYSTA = 510;
    /**
     * 数据备份
     */
    public static int DATABACKY = 520;
    /**
     * 数据清理
     */
    public static int DATACLEAR = 530;
    /**
     * 期归档
     */
    public static int DRAWBACKUP = 600;

}
