package com.bestinfo.gambler.createBetNumber;

import com.bestinfo.bean.game.GameInfo;
import com.bestinfo.bean.game.GamePlayInfoGamb;
import com.bestinfo.bean.game.PlayBetMode;
import com.bestinfo.bean.game.PlayBetModeGamb;
import com.bestinfo.gambler.all.CommTool;
import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.bet.GameInfoGC;
import com.bestinfo.gameg.GGameFactory;
import com.bestinfo.gameg.GGameImpl;
import com.bestinfo.protocols.bet.PBetSchemeRequst;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

/**
 *
 * @author chenliping
 */
public class CreateBetNumberData {

    private static final Logger logger = Logger.getLogger(CreateBetNumberData.class);

    private void assemblyFixed(int gameid, int playid, int betmod, int stakenum, float money, String sb, int onlynum, int maxmulti, StringBuilder bn) {
        bn.append("游戏编号： ");
        bn.append(gameid);
        bn.append("\t");
        bn.append("游戏玩法： ");
        bn.append(playid);
        bn.append("\t");
        bn.append("投注方式： ");
        bn.append(betmod);
        bn.append("\t");
        bn.append("号码组数： ");
        bn.append(stakenum);
        bn.append("\t");
        bn.append("金额： ");
        bn.append(money);
        bn.append("\t");
        bn.append("投注号码: ");
        bn.append(sb);
        bn.append("\t");
        bn.append("有效号码个数: ");
        bn.append(onlynum);
        bn.append("\t");
        bn.append("最大个数: ");
        bn.append(maxmulti);
    }

    /**
     * 所有游戏号码生成
     *
     * @param tpi
     * @param bet_mode 投注方式
     * @param ticketnum 票个数
     * @param maxmoney 单张票金额
     * @return
     */
    private ArrayList<String> GameNumber(String gameType, GamePlayInfoGamb tpi, int bet_mode, int ticketnum, float maxmoney) {
        ArrayList<String> number = new ArrayList<String>();
        for (int i = 0; i < ticketnum; i++) {
            PBetSchemeRequst tbetreq = new PBetSchemeRequst();//存放生成的号码等信息            
            //每一张票的号码  
            GGameImpl gi = GGameFactory.getInstance().getGame(gameType);
            GamePlayInfoGamb tpig = new GamePlayInfoGamb();
//            try {
//                BeanUtils.copyProperties(tpig, tpi);
//            } catch (Exception e) {
//                logger.error("BeanUtils.copyProperties(tpig, tpi)error");
//            }

            tpig.setGame_id(tpi.getGame_id());
            tpig.setPlay_id(tpi.getPlay_id());
            tpig.setPlay_name(tpi.getPlay_name());
            tpig.setPlay_type(tpi.getPlay_type());
            tpig.setStakes_price(tpi.getStakes_price());
            tpig.setMax_multiple(tpi.getMax_multiple());
            tpig.setBet_no_num(tpi.getBet_no_num());
            tpig.setBet_min_no(tpi.getBet_min_no());
            tpig.setBet_max_no(tpi.getBet_max_no());
            tpig.setBlue_no_num(tpi.getBlue_no_num());
            tpig.setBlue_min_no(tpi.getBlue_min_no());
            tpig.setBlue_max_no(tpi.getBlue_max_no());
            tpig.setNo_order(tpi.getNo_order());
            tpig.setWork_status(tpi.getWork_status());
            int re = gi.CreateBetNumber(bet_mode, tpig, tbetreq, maxmoney);
            if (re != 0) {
                return null;
            }
            StringBuilder allSB = new StringBuilder();
            assemblyFixed(tpi.getGame_id(), tpi.getPlay_id(), bet_mode, tbetreq.getBet_section(), tbetreq.getBet_money().floatValue(), tbetreq.getBet_line(), tbetreq.getBet_num(), tbetreq.getBet_multiple(), allSB);
            number.add(allSB.toString());
        }
        return number;
    }

    /**
     * 生成所有游戏指定票数的号码信息
     *
     * @param ticketnum
     * @param controlfilename
     * @return 返回null表示异常，正常为“完成”
     * @throws Exception
     */
    public String Createbetnumber(int ticketnum, String controlfilename) throws Exception {
        ArrayList<GameInfo> allgameinfo = GameInfoGC.getAllGameInfo();
        if (allgameinfo.isEmpty()) {
            logger.error("找不到游戏，请同步游戏信息！");
            return null;
        }
        for (GameInfo tGameInfo : allgameinfo) {
            ArrayList<GamePlayInfoGamb> gameplayinfo = GameInfoGC.getGamePlayInfo(tGameInfo.getGame_id());
            if (gameplayinfo.isEmpty()) {
                logger.error(tGameInfo.getGame_name() + "玩法信息找不到，请同步游戏玩法信息！");
                return null;
            }
            for (GamePlayInfoGamb tGamePlayInfo : gameplayinfo) {
                ArrayList<PlayBetModeGamb> gameplaybetmode = GameInfoGC.getPlayBetModeInfo(tGamePlayInfo.getGame_id(), tGamePlayInfo.getPlay_id());
                if (gameplaybetmode.isEmpty()) {
                    logger.error(tGameInfo.getGame_name() + "投注方式信息找不到，请同步信息！");
                    return null;
                }
                comm(gameplaybetmode, tGameInfo.getGame_type(), tGamePlayInfo, ticketnum, tGameInfo.getTerminal_bet_money().floatValue(), controlfilename);
            }
        }
        return "完成";
    }

    /**
     * 生成所有游戏指定票数的号码信息
     *
     * @param ticketnum
     * @param controlfilename
     * @return 返回null表示异常，正常为“完成”
     * @throws Exception
     */
    public String CreatebetnumberMorethread(final int ticketnum, final String controlfilename) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semp = new Semaphore(30);
        ArrayList<GameInfo> allgameinfo = GameInfoGC.getAllGameInfo();
        if (allgameinfo.isEmpty()) {
            logger.error("找不到游戏，请同步游戏信息！");
            return null;
        }
        for (final GameInfo tGameInfo : allgameinfo) {
            ArrayList<GamePlayInfoGamb> gameplayinfo = GameInfoGC.getGamePlayInfo(tGameInfo.getGame_id());
            if (gameplayinfo.isEmpty()) {
                logger.error(tGameInfo.getGame_name() + "玩法信息找不到，请同步游戏玩法信息！");
                return null;
            }
            for (final GamePlayInfoGamb tGamePlayInfo : gameplayinfo) {
                final ArrayList<PlayBetModeGamb> gameplaybetmode = GameInfoGC.getPlayBetModeInfo(tGamePlayInfo.getGame_id(), tGamePlayInfo.getPlay_id());
                if (gameplaybetmode.isEmpty()) {
                    logger.error(tGameInfo.getGame_name() + "投注方式信息找不到，请同步信息！");
                    return null;
                }
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            semp.acquire();// 获取许可
                            comm(gameplaybetmode, tGameInfo.getGame_type(), tGamePlayInfo, ticketnum, tGameInfo.getTerminal_bet_money().floatValue(), controlfilename);
                            semp.release();
                        } catch (Exception e) {
                            logger.error(e);
                        }
                    }
                });
            }
        }

        exec.shutdown();
        while (!exec.isTerminated()) {
            exec.awaitTermination(500, TimeUnit.MILLISECONDS);
        }
        return "完成";
    }

    private void comm(ArrayList<PlayBetModeGamb> gameplaybetmode, String gametype, GamePlayInfoGamb tGamePlayInfo, int ticketnum, float maxmoney, String controlfilename) throws Exception {

        for (PlayBetModeGamb tplaybetmode : gameplaybetmode) {
            logger.info("游戏" + tGamePlayInfo.getGame_id() + "\t玩法" + tGamePlayInfo.getPlay_id() + "\t投注方式" + tplaybetmode.getBet_mode() + "......正在生成号码");
            logger.info("篮球最小号码： "+tGamePlayInfo.getBlue_min_no()+" 篮球最大号码"+tGamePlayInfo.getBlue_max_no());
            ArrayList<String> number = GameNumber(gametype, tGamePlayInfo, tplaybetmode.getBet_mode(), ticketnum, maxmoney);
            if (number == null || number.isEmpty()) {
                logger.error("游戏" + tGamePlayInfo.getGame_id() + "\t玩法"
                        + tGamePlayInfo.getPlay_id() + "\t投注方式" + tplaybetmode.getBet_mode() + "没有生成号码");
                continue;
            }
            String filename = StaticVariable.NUMBERDIRECTORY + tGamePlayInfo.getGame_id() + tGamePlayInfo.getPlay_id() + tplaybetmode.getBet_mode() + String.format("%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date()) + ".txt";
            logger.info("文件名称" + filename);
            //new BetNumberXmlControlInfo().modifyxmlfile(tGamePlayInfo.getGame_id(), tGamePlayInfo.getPlay_id(), tplaybetmode.getBet_mode(), filename, 1, ticketnum, controlfilename);//控制文件信息写入
            logger.info("号码写入");
            CommTool.WriteStringTofile(number, filename);//号码写入   
            logger.info("游戏" + tGamePlayInfo.getGame_id() + "\t玩法"
                    + tGamePlayInfo.getPlay_id() + "\t投注方式" + tplaybetmode.getBet_mode() + "完成");
        }
    }

    /**
     * 生成投注号码,并写入文件，控制文件信息一并写入
     *
     * @param gameid
     * @param playid
     * @param betmod 为0表示查所有投注方式
     * @param ticketnum
     * @param maxmoney
     * @param controlfilename
     * @return 返回null表示异常，正常为“完成”
     * @throws Exception
     */
    public String Createbetnumber(int gameid, int playid, int betmode, int ticketnum, String controlfilename) throws Exception {
        GameInfo gameinfo = GameInfoGC.getAllGameInfo(gameid);
        if (gameinfo == null) {
            logger.error("找不到游戏，请同步游戏信息！或者是参数错误，找不到游戏");
            return null;
        }
        GamePlayInfoGamb tpi = GameInfoGC.getGamePlayInfo(gameid, playid);
        logger.info("tpitpi.getBlue_min_no is: "+tpi.getBlue_min_no()+" tpi.getBlue_max_no is: "+tpi.getBlue_max_no());
        if (tpi == null) {
            logger.info("找不到游戏，请同步游戏玩法信息或者参数错误，找不到游戏id：" + gameid + ",玩法:" + playid);
            return null;
        }
        ArrayList<PlayBetModeGamb> gameplaybetmode = null;
        if (betmode == 0) {
            gameplaybetmode = GameInfoGC.getPlayBetModeInfo(gameid, playid);
            if (gameplaybetmode == null || gameplaybetmode.isEmpty()) {
                logger.info("找不到游戏投注方式，请同步游戏玩法投注方式信息或者参数错误，找不到游戏id：" + gameid + ",玩法:" + playid);
                return null;
            }
        } else {
            gameplaybetmode = GameInfoGC.getPlayBetModeInfo(gameid, playid, betmode);
            if (gameplaybetmode == null || gameplaybetmode.isEmpty()) {
                logger.info("找不到游戏，请同步游戏玩法投注方式信息或者参数错误，找不到游戏id：" + gameid + ",玩法:" + playid + "投注方式：" + betmode);
                return null;
            }
        }
        comm(gameplaybetmode, gameinfo.getGame_type(), tpi, ticketnum, gameinfo.getTerminal_bet_money().floatValue(), controlfilename);
        return "完成";
    }

    /**
     * 生成投注号码,并写入文件，控制文件信息一并写入
     *
     * @param gameid
     * @param playid
     * @param betmod
     * @param ticketnum
     * @param maxmoney
     * @throws Exception 返回null表示异常，正常为“完成”
     */
    public String Createbetnumber(int gameid, int playid, int ticketnum, String controlfilename) throws Exception {
//        logger.info("执行游戏："+gameid+",玩法："+playid);
        return Createbetnumber(gameid, playid, 0, ticketnum, controlfilename);
    }

    public String Createbetnumber(int gameid, int ticketnum, String controlfilename) throws Exception {
        GameInfo tGameInfo = GameInfoGC.getAllGameInfo(gameid);
        if (tGameInfo == null) {
            logger.error("找不到游戏，请同步游戏信息！或者是参数错误，找不到游戏");
            return null;
        }
        ArrayList<GamePlayInfoGamb> gameplayinfo = GameInfoGC.getGamePlayInfo(tGameInfo.getGame_id());
        if (gameplayinfo.isEmpty()) {
            logger.error(tGameInfo.getGame_name() + "玩法信息找不到，请同步游戏玩法信息！");
            return null;
        }
        for (GamePlayInfoGamb tGamePlayInfo : gameplayinfo) {
            ArrayList<PlayBetModeGamb> gameplaybetmode = GameInfoGC.getPlayBetModeInfo(tGamePlayInfo.getGame_id(), tGamePlayInfo.getPlay_id());
            if (gameplaybetmode.isEmpty()) {
                logger.error(tGameInfo.getGame_name() + "投注方式信息找不到，请同步信息！");
                return null;
            }
            comm(gameplaybetmode, tGameInfo.getGame_type(), tGamePlayInfo, ticketnum, tGameInfo.getTerminal_bet_money().floatValue(), controlfilename);
        }
        return "完成";
    }
}
