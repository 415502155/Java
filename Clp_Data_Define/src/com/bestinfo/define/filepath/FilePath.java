package com.bestinfo.define.filepath;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取文件路径
 */
public class FilePath {
    
    /**
     * 管理系统类别，省/地市
     */
    public static String managerSystem;

    /**
     * 顶级目录（业务数据环境变量）
     */
    public static String rootPath;

    /**
     * 顶级目录（快开开奖日志文件环境变量）
     */
    public static String rootLogPath;

    /**
     * 顶级目录（自动开奖服务号码文件环境变量--ftp使用）
     */
    public static String rootNoPath;

    /**
     * 一级目录
     */
//    public static String firstclass;
    /**
     * 二级目录----上传中彩数据
     */
    public static String clpcenter;

    /**
     * 二级目录----游戏参数
     */
    public static String game;

    /**
     * 四级目录----游戏参数
     */
    public static String game2;

    /**
     * 省通知、地市通知、终端通知二级目录
     */
    public static String issue;

    /**
     * 三级目录----省通知
     */
//    public static String issuep;
    /**
     * 三级目录----地市通知
     */
    public static String issuec;

    /**
     * 三级目录----终端通知
     */
    public static String issuet;

    /**
     * 二级目录----开奖号码
     */
    public static String luckyno;

    /**
     * 二级目录----开奖公告
     */
    public static String prize;

    /**
     * 二级目录----期信息
     */
    public static String drawinfo;

    /**
     * 二级目录----销售文件
     */
    public static String saleStat;

    /**
     * 二级目录----中奖文件
     */
    public static String prizeStat;

    /**
     * 二级目录----新冠恒朋票文件
     */
    public static String TICKET_DATA;

    /**
     * 二级目录----投注机软件包
     */
    public static String soft;

    /**
     * 二级目录----实时缴款对账文件、对账文件签名文件
     */
    public static String bank;

    /**
     * 二级目录----彩票公告文件
     */
    public static String bulletin;

    /**
     * 二级目录----站点游戏销售统计文件
     */
    public static String natureTmnDay;

    /**
     * 快开游戏自动开奖日志目录
     */
    public static String KENO_LUCKY_LOG;

    /**
     * 各服务证书目录
     */
    public static String cert;

    /**
     * 终端接入证书目录
     */
    public static String terminalServer;

    /**
     * 自动开奖服务端证书目录
     */
    public static String rServer;

    /**
     * 自动开奖客户端证书目录
     */
    public static String rClient;

    /**
     * 终端接入证书名称
     */
    public static String terminalP12;

    /**
     * 4D号码格式文件生成目录
     */
    public static String d4LuckyNo;

    /**
     * 4D号码文件生成目录--打印使用
     */
    public static String d4LuckyNoPrint;

    /**
     * 4D号码文件模板目录--打印使用
     */
    public static String d4LuckyNoPrintTemplate;

    /**
     * K3号码格式文件生成目录
     */
    public static String K3LuckyNo;

    /**
     * K80号码格式文件生成目录
     */
    public static String K80LuckyNo;

    /**
     * S10号码格式文件生成目录
     */
    public static String S10LuckyNo;

    /**
     * 号码文件模板目录--打印使用
     */
    public static String luckyNoPrintTemplate;

    /**
     * 时时乐号码文件生成目录--打印使用
     */
    public static String S10LuckyNoPrint;

    /**
     * 二级目录----上传文件
     */
    public static String tmnlog;

    /**
     * RSA密钥路径
     */
    public static String storePath;

    /**
     * store密码
     */
    public static String storepwd;

    public static String alias;

    public static String aliaspwd;

    public static String publickeypath;

    /**
     * 电话投注文件生成
     */
    public static String gambler;

    /**
     * 电话投注游戏规则文件生成
     */
    public static String rule;

    /**
     * 电话投注对账文件生成
     */
    public static String account;
    /**
     * 电话投注中奖文件生成
     */
    public static String win;

    /*中奖文件下载nginx标志名称*/
    public static String winName;

    /*游戏规则下载nginx标志名称*/
    public static String ruleName;

    /*销售对账文件下载nginx标志名称*/
    public static String accountName;

    /*中奖文件路径*/
    public static String winUrl;

    /*游戏规则路径*/
    public static String ruleUrl;

    /*销售对账文件路径*/
    public static String accountUrl;
    
    /*终端key文件路径*/
    public static String tmnclpkey;
    /**
     * 获取环境变量所对应的路径
     *
     * @return
     */
    public static String getRootPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(rootPath).append(System.getProperty("file.separator"));
        return sb.toString();
    }

    /**
     * 创建上传文件名称
     *
     * @param terminalId
     * @param cmdId
     * @param fileName
     * @return
     */
    public static String createUploadFileName(int terminalId, String cmdId, String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.tmnlog).append(System.getProperty("file.separator"));
        sb.append(terminalId);
        sb.append(System.getProperty("file.separator"));
        sb.append(terminalId);
        sb.append("_");
        sb.append(cmdId);
        sb.append("_");
        sb.append(fileName);
        return sb.toString();
    }

    /**
     * 获取上传中彩数据路径
     *
     * @return
     */
    public static String getZCDataPath() {
        StringBuilder sb = new StringBuilder();
//        sb.append(rootPath + System.getProperty("file.separator"));
//        sb.append(firstclass + System.getProperty("file.separator"));
        sb.append(clpcenter);
        return sb.toString();
    }

    /**
     * 获取游戏参数文件路径
     *
     * @param gameId 游戏编号
     * @return
     */
    public static String getGameParaFileName(int gameId) {
        StringBuilder sb = new StringBuilder();
//        sb.append(rootPath + System.getProperty("file.separator"));
//        sb.append(firstclass + System.getProperty("file.separator"));
        sb.append(game).append(System.getProperty("file.separator"));
        sb.append("game").append(gameId).append(System.getProperty("file.separator"));
        sb.append(game2).append(System.getProperty("file.separator"));
        sb.append("game.g").append(gameId).append(".xml.gz");
        return sb.toString();
    }

//    /**
//     * 获取省通知的文件路径
//     *
//     * @param year 年号(如：2014)
//     * @param date 日期(如：20140901)，可以使用月份划分(如：201409)
//     * @param noticeId 通知编号
//     * @param noticeName 通知名称
//     * @return
//     */
//    public static String getProvinceNoticeFileName(String year, String date, int noticeId, String noticeName) {
//        StringBuffer sb = new StringBuffer();
////        sb.append(rootPath + System.getProperty("file.separator"));
//        sb.append(firstclass + System.getProperty("file.separator"));
//        sb.append(issue + System.getProperty("file.separator"));
//        sb.append(issuep + System.getProperty("file.separator"));
//        sb.append(year + System.getProperty("file.separator"));
//        sb.append(date + ".issue" + noticeId + noticeName);
//        sb.append(".xml.gz");
//        return sb.toString();
//    }
    /**
     * 获取省通知或地市通知的文件路径
     *
     * @param cityId 地市编号（cityId=0为省通知）
     * @param year 年号(如：2014)
     * @param date 日期(如：20140901)，可以使用月份划分(如：201409)
     * @param noticeId 通知编号
     * @param noticeName 通知名称
     * @return
     */
    public static String getProvinceOrCityNoticeFileName(int cityId, String year, String date, int noticeId, String noticeName) {
        StringBuilder sb = new StringBuilder();
//        sb.append(rootPath + System.getProperty("file.separator"));
//        sb.append(firstclass + System.getProperty("file.separator"));
        sb.append(issue).append(System.getProperty("file.separator"));
        sb.append(issuec).append(System.getProperty("file.separator"));
        sb.append("city").append(cityId).append(System.getProperty("file.separator"));
        sb.append(year).append(System.getProperty("file.separator"));
        sb.append(date).append(".issue").append(noticeId).append(noticeName);
        sb.append(".xml.gz");
        return sb.toString();
    }

    /**
     * 获取终端通知的文件路径
     *
     * @param tmnId 终端编号
     * @param year 年号(如：2014)
     * @param date 日期(如：20140901)，可以使用月份划分(如：201409)
     * @param noticeId 通知编号
     * @param noticeName 通知名称
     * @return
     */
    public static String getTmnNoticeFileName(int tmnId, String year, String date, int noticeId, String noticeName) {
        StringBuilder sb = new StringBuilder();
//        sb.append(rootPath + System.getProperty("file.separator"));
//        sb.append(firstclass + System.getProperty("file.separator"));
        sb.append(issue).append(System.getProperty("file.separator"));
        sb.append(issuet).append(System.getProperty("file.separator"));
        sb.append("tmn").append(tmnId).append(System.getProperty("file.separator"));
        sb.append(year).append(System.getProperty("file.separator"));
        sb.append("tmn").append(tmnId).append(".");
        sb.append(date).append(".issue").append(noticeId).append(noticeName);
        sb.append(".xml.gz");
        return sb.toString();
    }

    /**
     * 获取开奖号码或开奖公告的文件路径
     *
     * @param type 1开奖号码 2开奖公告
     * @param gameId 游戏编号
     * @param drawId 期号
     * @param kdrawId 快开期号(非快开游戏为0)
     * @param OpenPrizeCount 开奖次数
     * @param noticeStyleId 通知格式编号
     * @return
     */
//    public static String getOpenNumFileName(int type, int gameId, int drawId, int kdrawId, int OpenPrizeCount, int noticeStyleId) {
//        StringBuilder sb = new StringBuilder();
////        sb.append(rootPath + System.getProperty("file.separator"));
////        sb.append(firstclass + System.getProperty("file.separator"));
//        if (type == 1) {
//            sb.append(luckyno).append(System.getProperty("file.separator"));
//        }
//        if (type == 2) {
//            sb.append(prize).append(System.getProperty("file.separator"));
//        }
//        sb.append("game").append(gameId).append(System.getProperty("file.separator"));
//        sb.append("draw").append(drawId).append(System.getProperty("file.separator"));
//        sb.append("prize.g").append(gameId);
//        sb.append(".d").append(drawId);
//        sb.append(".k").append(kdrawId);
//        sb.append(".n").append(OpenPrizeCount);
//        sb.append(".fmt").append(noticeStyleId);
//        sb.append(".gz");
//        return sb.toString();
//    }
    /**
     * 获取投注机软件包的文件路径
     *
     * @param softwareType 软件种类
     * @param softwareId 软件编号
     * @param version 软件版本
     * @return
     */
    public static String getTmnPkg(int softwareType, int softwareId, String version) {
        StringBuilder sb = new StringBuilder();
//        sb.append(rootPath + System.getProperty("file.separator"));
//        sb.append(firstclass + System.getProperty("file.separator"));
        sb.append(soft).append(System.getProperty("file.separator"));
        sb.append("ltmnpkg");
        sb.append(String.format("%03d", softwareType)).append("-");
        sb.append(String.format("%02d", softwareId));
        sb.append("-bet-");
        sb.append(version).append(".gz");
        return sb.toString();
    }

    /**
     * 得到实时缴款对账文件或对账文件签名文件路径（不包括文件名，只有文件的相对路径）
     *
     * @param bankCode 银行编码（如：ICBC）
     * @return
     */
    public static String getPayInFilePath(String bankCode) {
        StringBuilder sb = new StringBuilder();
        sb.append(getRootPath());
        sb.append(bank).append(System.getProperty("file.separator"));
        sb.append("bank_").append(bankCode).append(System.getProperty("file.separator"));
        return sb.toString();
    }

    /**
     * 得到实时缴款对账文件名路径
     *
     * @param bankCode 银行编码（如：ICBC）
     * @param year 年份（如：2014）
     * @param month 月（如：09）
     * @param day 日（如：26）
     * @return
     */
    public static String getPayInFileNamePath(String bankCode, String year, String month, String day) {
        StringBuilder sb = new StringBuilder();
        sb.append(getPayInFilePath(bankCode));
        sb.append(bankCode).append(year).append(month).append(day).append(".dat");
        return sb.toString();
    }

    /**
     * 得到对账文件签名文件名路径
     *
     * @param bankCode 银行编码（如：ICBC）
     * @param year 年份（如：2014）
     * @param month 月（如：09）
     * @param day 日（如：26）
     * @return
     */
    public static String getAccChkSignFilePath(String bankCode, String year, String month, String day) {
        return getPayInFileNamePath(bankCode, year, month, day) + ".sign";
    }

    /**
     * 得到彩票公告文件名相对路径
     *
     * @return
     */
    public static String getLotteryBulletinPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(bulletin).append(System.getProperty("file.separator"));
        sb.append("bulletin.xml.gz");
        return sb.toString();
    }

    /**
     * 得到开奖日志文件名称
     *
     * @param game_id
     * @param draw_id
     * @param keno_draw_name 当是快开游戏时，指的是小期期名；当是普通游戏时，指的是大期名
     * @return
     */
    public static String createKenoLuckyFileName(int game_id, int draw_id, String keno_draw_name) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootLogPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.KENO_LUCKY_LOG).append(System.getProperty("file.separator")).append("game");
        sb.append(game_id);
        sb.append(System.getProperty("file.separator")).append("draw");
        sb.append(draw_id);
        sb.append(System.getProperty("file.separator"));
        sb.append(keno_draw_name);
        return sb.toString();
    }

    public static String createKenoLuckyFileName(int game_id, String draw_name, String keno_draw_name) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.luckyno).append(System.getProperty("file.separator")).append("game");
        sb.append(game_id);
        sb.append(System.getProperty("file.separator"));
        sb.append(draw_name);
        sb.append(System.getProperty("file.separator"));
        sb.append(keno_draw_name);
        return sb.toString();
    }

    /**
     * 得到开奖错误日志文件名称
     *
     * @param game_id
     * @param draw_id
     * @param keno_draw_name 当是快开游戏时，指的是小期期名；当是普通游戏时，指的是大期名
     * @return
     */
    public static String createKenoLuckyErrorFileName(int game_id, int draw_id, String keno_draw_name) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootLogPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.KENO_LUCKY_LOG).append(System.getProperty("file.separator")).append("game");
        sb.append(game_id);
        sb.append(System.getProperty("file.separator")).append("draw");
        sb.append(draw_id);
        sb.append(System.getProperty("file.separator"));
        sb.append(keno_draw_name);
        sb.append("_error");
        return sb.toString();
    }

    /**
     * 得到开奖号码日志文件名称
     *
     * @param game_id
     * @param draw_id
     * @return
     */
    public static String createKenoLuckyNoFileName(int game_id, int draw_id) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootLogPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.KENO_LUCKY_LOG).append(System.getProperty("file.separator")).append("game");
        sb.append(game_id);
        sb.append(System.getProperty("file.separator")).append("draw");
        sb.append(draw_id);
        sb.append(System.getProperty("file.separator"));
        sb.append("luckyno");
        return sb.toString();
    }

    /**
     * 得到开奖号码签名日志文件名称
     *
     * @param game_id
     * @param draw_id
     * @return
     */
    public static String createKenoLuckyNoSignFileName(int game_id, int draw_id) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootLogPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.KENO_LUCKY_LOG).append(System.getProperty("file.separator")).append("game");
        sb.append(game_id);
        sb.append(System.getProperty("file.separator")).append("draw");
        sb.append(draw_id);
        sb.append(System.getProperty("file.separator"));
        sb.append("luckyno_sign");
        return sb.toString();
    }

    /**
     * 得到时时乐开奖号码规则文件的名称<br>
     * /lottery/no/S10_win_number/draw大期名draw20150316/小期号码文件WNO_SSL_20150316_002.txt
     *
     * @param draw_name
     * @param keno_draw_name
     * @return
     */
    public static String createS10LuckyNoRuleFileName(String draw_name, String keno_draw_name) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootNoPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.S10LuckyNo).append(System.getProperty("file.separator"));
        sb.append("draw").append(draw_name).append(System.getProperty("file.separator"));
        sb.append("WNO_SSL_");
        sb.append(keno_draw_name.substring(0, 8));
        sb.append("_");
        sb.append(keno_draw_name.substring(8));
        sb.append(".txt");
        return sb.toString();
    }

    /**
     * 得到快3开奖号码规则文件的名称<br>
     * /lottery/no/K3_win_number/号码文件Quick3_results_MM-dd-yy.dat
     *
     * @param game_id
     * @param draw_name 20150316
     * @return
     */
    public static String createK3LuckyNoRuleFileName(int game_id, String draw_name) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootNoPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.K3LuckyNo).append(System.getProperty("file.separator"));
        sb.append("Quick3_results_");
        sb.append(draw_name.substring(4, 6)).append("-");
        sb.append(draw_name.substring(6)).append("-");
        sb.append(draw_name.substring(2, 4));
        sb.append(".dat");
        return sb.toString();
    }

    /**
     * 得到基诺开奖号码规则文件的名称<br>
     * /lottery/no/K80_win_number/号码文件Keno_results_MM-dd-yy.dat
     *
     * @param game_id
     * @param draw_name 20150316
     * @return
     */
    public static String createK80LuckyNoRuleFileName(int game_id, String draw_name) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootNoPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.K80LuckyNo).append(System.getProperty("file.separator"));
        sb.append("Keno_results_");
        sb.append(draw_name.substring(4, 6)).append("-");
        sb.append(draw_name.substring(6)).append("-");
        sb.append(draw_name.substring(2, 4));
        sb.append(".dat");
        return sb.toString();
    }

    /**
     * 得到普通游戏开奖公告文件名称
     *
     * @param game_id
     * @param draw_name
     * @return
     */
    public static String createBulletinFileName(int game_id, String draw_name) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.prize).append(System.getProperty("file.separator")).append("game");
        sb.append(game_id);
        sb.append(System.getProperty("file.separator"));
        sb.append("fc");
        sb.append(draw_name);
        return sb.toString();
    }

    /**
     * 创建无纸化开奖文件名
     *
     * @param game_id
     * @param draw_name
     * @return
     */
    public static String createPlasPrizeFileName(int game_id, String draw_name) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.prize).append(System.getProperty("file.separator")).append("game");
        sb.append(game_id);
        sb.append(System.getProperty("file.separator"));
        sb.append(draw_name);
        return sb.toString();
    }

    /**
     * 得到快开游戏开奖公告文件名称
     *
     * @param game_id
     * @param draw_id
     * @param keno_draw_name
     * @return
     */
    public static String createKenoBulletinFileName(int game_id, int draw_id, String keno_draw_name) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.prize).append(System.getProperty("file.separator")).append("game");
        sb.append(game_id);
        sb.append(System.getProperty("file.separator")).append("draw");
        sb.append(draw_id);
        sb.append(System.getProperty("file.separator"));
        sb.append(keno_draw_name);
        return sb.toString();
    }

    public static String createKenoBulletinFileName(int game_id, String draw_name, String keno_draw_name) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.prize).append(System.getProperty("file.separator")).append("game");
        sb.append(game_id);
        sb.append(System.getProperty("file.separator"));
        sb.append(draw_name);
        sb.append(System.getProperty("file.separator"));
        sb.append(keno_draw_name);
//        sb.append(".xml");
        return sb.toString();
    }

    /**
     * 得到终端接入的证书文件路径
     *
     * @return
     */
    public static String getTerminalCertP12FilePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.cert).append(System.getProperty("file.separator"));
        sb.append(FilePath.terminalServer).append(System.getProperty("file.separator"));
        sb.append(FilePath.terminalP12);
        return sb.toString();
    }

    /**
     * 得到自动开奖的p12证书文件路径
     *
     * @return
     */
    public static String getAutoDrawCertP12FilePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.cert).append(System.getProperty("file.separator"));
        sb.append(FilePath.rClient).append(System.getProperty("file.separator"));
        return sb.toString();
    }

    /**
     * 得到号码服务器主服务的证书文件路径
     *
     * @return
     */
    public static String getAutoDrawMasterCRTFilePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.cert).append(System.getProperty("file.separator"));
        sb.append(FilePath.rServer).append(System.getProperty("file.separator"));
        return sb.toString();
    }

    /**
     * 得到号码服务器备服务的证书文件路径
     *
     * @return
     */
    public static String getAutoDrawSlaveCRTFilePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.cert).append(System.getProperty("file.separator"));
        sb.append(FilePath.rServer).append(System.getProperty("file.separator"));
        return sb.toString();
    }

    /**
     * 获得天天彩4的号码格式文件名称
     *
     * @param drawName
     * @return
     */
    public static String get4DLuckyNoFilePath(String drawName) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootNoPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.d4LuckyNo).append(System.getProperty("file.separator"));
        sb.append("Results_Pick4_").append(drawName).append(".txt");
        return sb.toString();
    }

    /**
     * 获得天天彩4的号码格式文件名称--打印使用
     *
     * @param drawName
     * @return
     */
    public static String get4DLuckyNoPrintFilePath(String drawName) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootNoPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.d4LuckyNoPrint).append(System.getProperty("file.separator"));
        sb.append("Pick4_Number_Print").append(drawName).append(".html");
        return sb.toString();
    }

    /**
     * 获得天天彩4的号码格式文件模板目录--打印使用
     *
     * @return
     */
    public static String get4DLuckyNoPrintTemplateFileDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootNoPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.d4LuckyNoPrintTemplate);
        return sb.toString();
    }

    /**
     * 获得时时乐的号码格式文件名称--打印使用
     *
     * @param draw_name
     * @param kenoDrawName
     * @return
     */
    public static String getS10LuckyNoPrintFilePath(String draw_name, String kenoDrawName) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootNoPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.S10LuckyNoPrint).append(System.getProperty("file.separator"));
        sb.append(draw_name).append(System.getProperty("file.separator"));
        sb.append("Number_Print").append(kenoDrawName).append(".html");
        return sb.toString();
    }

    /**
     * 获得号码格式文件模板目录--打印使用
     *
     * @return
     */
    public static String getLuckyNoPrintTemplateFileDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootNoPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.luckyNoPrintTemplate);
        return sb.toString();
    }

    /**
     * 获得站点游戏销售统计文件名
     *
     * @param day
     * @return
     */
    public static String getNatureTmnDayFilePath(String day) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.natureTmnDay).append(System.getProperty("file.separator"));
        sb.append("sales_gds").append(day).append(".csv");
        return sb.toString();
    }

    /**
     * 获得站点游戏销售统计文件名集合
     *
     * @param dayList
     * @return
     */
    public static List<String> getNatureTmnDayFilePathList(List<String> dayList) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.natureTmnDay).append(System.getProperty("file.separator"));
        sb.append("sales_gds");
        String headFileName = sb.toString();
        List<String> filePathList = new ArrayList<String>();
        for (String day : dayList) {
            StringBuilder footSB = new StringBuilder(headFileName);
            filePathList.add(footSB.append(day).append(".csv").toString());
        }
        return filePathList;
    }

    /**
     * 获取普通游戏的中奖号码文件（一次开奖）
     *
     * @param game_id
     * @param drawName
     * @return
     */
    public static String getLuckyNumberFilePath(int game_id, String drawName) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.luckyno).append(System.getProperty("file.separator"));
        sb.append("game").append(game_id).append(System.getProperty("file.separator"));
        sb.append(drawName);
        return sb.toString();
    }

    /**
     * 获取普通游戏的中奖号码文件（二次开奖）
     *
     * @param game_id
     * @param drawName
     * @return
     */
    public static String getLuckyNumber2FilePath(int game_id, String drawName) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.luckyno).append(System.getProperty("file.separator"));
        sb.append("game").append(game_id).append(System.getProperty("file.separator"));
        sb.append(drawName).append(".xml");
        return sb.toString();
    }

    /**
     * 得到普通或快开的期信息文件（无纸化）
     *
     * @param game_id
     * @return
     */
    public static String getDrawInfoFilePath(int game_id) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.drawinfo).append(System.getProperty("file.separator"));
        sb.append("game").append(game_id);
        return sb.toString();
    }

    /**
     * 获得维赛特上传的销售文件的文件名
     *
     * @param game_id
     * @param drawName
     * @param system_id
     * @return
     */
    public static String getSVCSaleStatFilePath(int game_id, String drawName, int system_id) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.saleStat).append(System.getProperty("file.separator"));
        sb.append("game").append(game_id).append(System.getProperty("file.separator"));
        sb.append(system_id).append(System.getProperty("file.separator"));
        sb.append(drawName);
        return sb.toString();
    }

    /**
     * 获得维赛特上传的中奖文件的文件名
     *
     * @param game_id
     * @param drawName
     * @param system_id
     * @return
     */
    public static String getSVCPrizeStatFilePath(int game_id, String drawName, int system_id) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.prizeStat).append(System.getProperty("file.separator"));
        sb.append("game").append(game_id).append(System.getProperty("file.separator"));
        sb.append(system_id).append(System.getProperty("file.separator"));
        sb.append(drawName);
        return sb.toString();
    }

    /**
     * 获得新冠恒朋票数据文件<br>
     *
     * @param gameId
     * @param systemId
     * @param drawName
     * @return
     */
    public static String getPlusTicketFilePath(String gameId, String systemId, String drawName) {
        // /lottery/dat/ticketData/game1/3104/drawname	新冠3104，恒朋3105
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(FilePath.TICKET_DATA).append(System.getProperty("file.separator"));
        sb.append("game").append(gameId).append(System.getProperty("file.separator"));
        sb.append(systemId).append(System.getProperty("file.separator"));
        sb.append(drawName);
        return sb.toString();
    }

    /**
     * 电话投注游戏规则 dat/gambler/game_id/draw_id/
     *
     * @param gameId 游戏编号
     * @return
     */
    public static String getGamblerRuleFile(int gameId) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(gambler).append(System.getProperty("file.separator"));
        sb.append(rule).append(System.getProperty("file.separator"));
        sb.append("game").append(gameId).append(".xml");
        return sb.toString();
    }

    /**
     * 电话投注中奖纪录 dat/gambler/game_id/draw_id/
     *
     * @param gameId
     * @param drawId
     * @return
     */
    public static String getGamblerWinFile(int gameId, int drawId, String dealerId) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(gambler).append(System.getProperty("file.separator"));
        sb.append(win).append(System.getProperty("file.separator"));
        sb.append(dealerId).append("_g").append(gameId).append("_d").append(drawId).append(".xml");
        return sb.toString();
    }

    /**
     * 电话投注销售对账
     *
     * @param gameId
     * @param drawId
     * @param dealerId
     * @return
     */
    public static String getGamblerAccountFile(int gameId, int drawId, String dealerId) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(gambler).append(System.getProperty("file.separator"));
        sb.append(account).append(System.getProperty("file.separator"));
        sb.append("sale_").append(dealerId).append("_g").append(gameId).append("_d").append(drawId).append(".xml");
        return sb.toString();
    }

    public static String getPublickeypath(int dealerid) {
        StringBuilder sb = new StringBuilder();
        sb.append(publickeypath).append(System.getProperty("file.separator"));
        sb.append(dealerid);
        sb.append(".cer");
        return sb.toString();
    }

    public static String getStorePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.storePath);
        return sb.toString();
    }

    public static String getStorepwd() {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.storepwd);
        return sb.toString();
    }

    public static String getAlias() {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.alias);
        return sb.toString();
    }

    public static String getAliaspwd() {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.aliaspwd);
        return sb.toString();
    }
    
    /**
     * 获取上传终端tmnclpkey文件路径
     *
     * @param timefile
     * @param fileName
     * @return
     */
    public static String getTmnClpKeyFilePath(String timefile,String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilePath.rootPath).append(System.getProperty("file.separator"));
        sb.append(tmnclpkey).append(System.getProperty("file.separator"));
        sb.append(timefile).append(System.getProperty("file.separator"));
        sb.append(fileName);
        return sb.toString();
    }
}
