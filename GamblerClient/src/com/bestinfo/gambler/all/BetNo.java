package com.bestinfo.gambler.all;

import com.bestinfo.gambler.createBetNumber.BetNumberXmlControlInfo;
import com.bestinfo.gambler.entity.BetNumber;
import com.bestinfo.gambler.entity.ControlNumber;
import com.bestinfo.gambler.protocols.ActionID;
import com.bestinfo.protocols.agents.AgentAbstractUserResList;
import com.bestinfo.protocols.agents.AgentRechargeUserReqResList;
import com.bestinfo.protocols.bet.PBetSchemeResponse;
import com.bestinfo.protocols.message.APPMessage;
import com.bestinfo.protocols.users.UserQueryReqRes;
import com.bestinfo.protocols.users.UserSummaryInfoReqRes;
import com.bestinfo.protocols.xml.client.XmlFactoryClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.http.client.CookieStore;
import org.apache.log4j.Logger;

/**
 * 报注报文数据层
 *
 * @author chenliping
 */
public class BetNo {

    private static final Logger logger = Logger.getLogger(BetNo.class);

    /**
     * 从文件获取游戏号码，并存于号码对象中
     *
     * @param controlNumber
     * @param albn
     */
    private void getnumobject(ControlNumber controlNumber, ArrayList<BetNumber> albn) {
        List<String> la = new CommTool().getIndicateLines(controlNumber.getFilename(), controlNumber.getStartline(), controlNumber.getEndline());//从文件取号码信息
        if (la.isEmpty()) {
            logger.error("读到号码信息0行");
            return;
        }
        for (String string : la) {
            String eachone[] = string.split("\t");
            BetNumber bt = new BetNumber();
            bt.setGameid(Integer.parseInt(eachone[0]));
            bt.setPlaytype(Integer.parseInt(eachone[1]));
            bt.setBetmod(Integer.parseInt(eachone[2]));
            bt.setStake(Integer.parseInt(eachone[3]));
            bt.setMoney(Float.parseFloat(eachone[4]));
            bt.setNumber(eachone[5]);
            bt.setOnlynum(Integer.parseInt(eachone[6]));
            bt.setMaxmulti(Integer.parseInt(eachone[7]));
            albn.add(bt);
        }
    }

    /**
     * 从文件获取游戏的投注号码信息
     *
     * @param gameid
     * @param playid
     * @param betmod
     * @param controlfilename
     * @return
     */
    public ArrayList<BetNumber> getBetNumber(int gameid, int playid, int betmod, String controlfilename) {
        ArrayList<BetNumber> albn = new ArrayList<BetNumber>();
        ArrayList<ControlNumber> tn = new BetNumberXmlControlInfo().selectticketnum(gameid, playid, betmod, controlfilename);
        if (tn == null) {
            return albn;
        }
        for (ControlNumber controlNumber : tn) {
            getnumobject(controlNumber, albn);
        }
        return albn;
    }

    public ArrayList<BetNumber> getBetNumber2(int gameid, int playid, int betmod, String controlfilename) {
        ArrayList<BetNumber> albn = new ArrayList<BetNumber>();
//        String strs = getNumber();
//        String[] strss=strs.split("/");
        for (int i = 0; i < 5; i++) {
            String str = "0701060304050207";
            StringBuilder sb = new StringBuilder(str);
            sb.setCharAt(sb.length() - 3, String.valueOf(ThreadLocalRandom.current().nextInt(6) + 1).charAt(0));
            sb.setCharAt(sb.length() - 5, String.valueOf(ThreadLocalRandom.current().nextInt(6) + 1).charAt(0));
            sb.setCharAt(sb.length() - 7, String.valueOf(ThreadLocalRandom.current().nextInt(6) + 1).charAt(0));
            sb.setCharAt(sb.length() - 9, String.valueOf(ThreadLocalRandom.current().nextInt(6) + 1).charAt(0));
            BetNumber bt = new BetNumber();
            bt.setGameid(1);
            bt.setPlaytype(1);
            bt.setBetmod(1);
            bt.setStake(1);
            bt.setMoney(Float.parseFloat("2.0"));
            bt.setNumber(str);
//            if (i>=strss.length) {
//                bt.setNumber(strss[strss.length-1]);
//            }else{
//                 bt.setNumber(strss[i].trim());
//            }
            bt.setOnlynum(7);
            bt.setMaxmulti(7);
            albn.add(bt);
        }
        return albn;
    }

    /**
     * 从表中获取指定中奖用例游戏号码
     *
     * @param gameid 游戏编号
     * @param playid 玩法编号
     * @param betmod
     * @param id
     * @return 规则对象
     */
    public ArrayList<BetNumber> getGameLuckyNumber(int gameid, int playid, int betmod, int id) {
        try {
            ArrayList<BetNumber> albn = new ArrayList<BetNumber>();
            BaseJDBCDao bCDao = new BaseJDBCDao("derby");
            Connection conn = bCDao.getConnection(true, "virtual");
            Statement s = conn.createStatement();
            String sql = "select * from TEST.T_LUCKY_NUMBER where gameid=" + gameid;
            if (playid != 0) {
                sql = sql + " and playtype=" + playid;
            }
            if (betmod != 0) {
                sql += " and gametype=" + betmod;
            }
            if (id != 0) {
                sql += " and id=" + id;
            }
            ResultSet rs = s.executeQuery(sql);
            int count = 0;
            while (rs.next()) {
                count += 1;
                BetNumber tbreq = new BetNumber();
                tbreq.setGameid(gameid);
                tbreq.setPlaytype(rs.getInt("playtype"));
                tbreq.setBetmod(rs.getInt("gametype"));
                tbreq.setTestdesc(rs.getString("TESTDESC"));
                tbreq.setStake(rs.getInt("STAKES"));//号码组个数
                tbreq.setMaxmulti(rs.getInt("MULTI"));
                tbreq.setMoney(rs.getFloat("MONEY"));
                tbreq.setOnlynum(rs.getInt("NONUM"));
                tbreq.setNumber(rs.getString("NUMBER"));
                albn.add(tbreq);
            }
            rs.close();
            s.close();
            conn.close();
            if (count == 0) {
                logger.error("没有错误号码！");
                return null;
            } else {
                return albn;
            }
        } catch (SQLException e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 从表中获取错误游戏号码
     *
     * @param gameid 游戏编号
     * @param playid 玩法编号
     * @param betmod
     * @return 规则对象
     */
    public ArrayList<BetNumber> getGameErrorNumber(int gameid, int playid, int betmod) {
        try {
            ArrayList<BetNumber> tpi = new ArrayList<BetNumber>();
            BaseJDBCDao bCDao = new BaseJDBCDao("derby");
            Connection conn = bCDao.getConnection(true, "virtual");
            Statement s = conn.createStatement();
            String sql = "select * from TEST.T_ABNORMAL_NUMBER where gameid=" + gameid + " and playtype=" + playid + " and gametype=" + betmod;
            ResultSet rs = s.executeQuery(sql);
            int count = 0;
            while (rs.next()) {
                count += 1;
                BetNumber tbreq = new BetNumber();
                tbreq.setGameid(gameid);
                tbreq.setPlaytype(rs.getInt("playtype"));
                tbreq.setBetmod(rs.getInt("gametype"));
                tbreq.setTestdesc(rs.getString("TESTDESC"));
                tbreq.setStake(rs.getInt("STAKE_NUM"));//号码组个数
                tbreq.setMaxmulti(rs.getInt("multi"));
                tbreq.setMoney(rs.getFloat("MONEY"));
                tbreq.setOnlynum(rs.getInt("NUM"));
                tbreq.setNumber(rs.getString("BETNUMBER"));
                tpi.add(tbreq);
            }
            rs.close();
            s.close();
            conn.close();
            if (count == 0) {
                logger.error("没有错误号码！");
                return null;
            } else {
                return tpi;
            }
        } catch (SQLException e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 发送投注报文，并处理回复报文,发送公共部分,不写文件
     *
     * @param xml
     * @return
     */
    public int send(String xml) {
        String responsexml = HttpUtil.httpSend(xml, StaticVariable.SERVERURL, false);
        //logger.info("返回xml： " + responsexml);
        if (responsexml == null || responsexml.equals("") ) {
            return -1;
        } else if (responsexml.equals("sendError")) {
            return -5;
        }
        APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET).parseXML(responsexml);
        if (ap == null) {
            return -3;
        }
        PBetSchemeResponse tbrr = (PBetSchemeResponse) ap.getContent();
        int resultcode = tbrr.getResult().getResultCode();

        if (resultcode != 0) {
            logger.error("rescode:" + resultcode + "\tresultDes:" + tbrr.getResult().getResultDes() + "\txml:" + xml);
            //JOptionPane.showMessageDialog(null, "rescode: " + resultcode + " \tresultDes: " + tbrr.getResult().getResultDes(), "操作", JOptionPane.INFORMATION_MESSAGE);
            if (resultcode == 1618) {
                return -4;
            }

            return -2;
        }
        StaticVariable.serino = tbrr.getDealerSerial().trim();
        StaticVariable.cifer = tbrr.getCipher().replace(" ", "");
        return 0;
    }

    /**
     * 发送投注报文，并处理回复报文,发送公共部分,不写文件
     *
     * @param xml
     * @return
     */
    public int Rechargesend(String xml) {
        String responsexml = HttpUtil.httpSend(xml, StaticVariable.SERVERURL, false);
        logger.info("返回xml： " + responsexml);
        if (responsexml == null) {
            return -1;
        } else if (responsexml.equals("sendError")) {
            return -5;
        }
        APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.DEALER_AGENTRECHARGE).parseXML(responsexml);
        if (ap == null) {
            return -3;
        }
        AgentRechargeUserReqResList tbrr = (AgentRechargeUserReqResList) ap.getContent();
        for (int i = 0; i < tbrr.getAgentUsers().size(); i++) {
            int str = Integer.valueOf(tbrr.getAgentUsers().get(i).getResult());
            int resultcode = tbrr.getAppResResult().getResultCode();

            if (resultcode != 0 || str != 0) {
                logger.error("rescode:" + resultcode + "\tresultDes:" + tbrr.getAppResResult().getResultCode() + "\txml:" + xml);
                //JOptionPane.showMessageDialog(null, "rescode: " + resultcode + " \tresultDes: " + tbrr.getResult().getResultDes(), "操作", JOptionPane.INFORMATION_MESSAGE);
                if (resultcode == 1412) {
                    return -4;
                }

                return -2;
            }
        }
        return 0;
    }

    /**
     * 发送投注报文，并处理回复报文,发送公共部分,不写文件
     *
     * @param xml
     * @return
     */
    public int Abstractsend(String xml) {
        String responsexml = HttpUtil.httpSend(xml, StaticVariable.SERVERURL, false);
        //logger.info("返回xml： "+responsexml);
        if (responsexml == null) {
            return -1;
        } else if (responsexml.equals("sendError")) {
            return -5;
        }
        APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.DEALER_PROXYABSTRACT).parseXML(responsexml);
        if (ap == null) {
            return -3;
        }
        AgentAbstractUserResList tbrr = (AgentAbstractUserResList) ap.getContent();
        for (int i = 0; i < tbrr.getList().size(); i++) {
            int str = Integer.valueOf(tbrr.getList().get(i).getResult());
            int resultcode = tbrr.getResult().getResultCode();

            if (resultcode != 0 || str != 0) {
                logger.error("rescode:" + resultcode + "\tresultDes:" + tbrr.getResult().getResultCode() + "\txml:" + xml);
                //JOptionPane.showMessageDialog(null, "rescode: " + resultcode + " \tresultDes: " + tbrr.getResult().getResultDes(), "操作", JOptionPane.INFORMATION_MESSAGE);
                if (resultcode == 1618) {
                    return -4;
                }

                return -2;
            }
        }
        return 0;
    }

    /**
     * 发送投注报文，并处理回复报文,发送公共部分,不写文件
     *
     * @param xml
     * @return
     */
    public int UserQuerysend(String xml) {
        String responsexml = HttpUtil.httpSend(xml, StaticVariable.SERVERURL, false);
        //logger.info("返回xml： "+responsexml);
        if (responsexml == null) {
            return -1;
        } else if (responsexml.equals("sendError")) {
            return -5;
        }
        APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.GAMBLER_QUERY).parseXML(responsexml);
        if (ap == null) {
            return -3;
        }
        UserQueryReqRes tbrr = (UserQueryReqRes) ap.getContent();
//        for(int i=0;i<tbrr.getList().size();i++){
//            int str=Integer.valueOf(tbrr.getList().get(i).getResult());
        int resultcode = tbrr.getAppResResult().getResultCode();

        if (resultcode != 0) {
            logger.error("rescode:" + resultcode + "\tresultDes:" + tbrr.getAppResResult().getResultCode() + "\txml:" + xml);
            //JOptionPane.showMessageDialog(null, "rescode: " + resultcode + " \tresultDes: " + tbrr.getResult().getResultDes(), "操作", JOptionPane.INFORMATION_MESSAGE);
            if (resultcode == 1412) {
                return -4;
            }

            return -2;
        }
        return 0;
    }

    /**
     * 发送投注报文，并处理回复报文,发送公共部分,不写文件
     *
     * @param xml
     * @return
     */
    public int UserSumsend(String xml) {
        String responsexml = HttpUtil.httpSend(xml, StaticVariable.SERVERURL, false);
        //logger.info("返回xml： "+responsexml);
        if (responsexml == null) {
            return -1;
        } else if (responsexml.equals("sendError")) {
            return -5;
        }
        APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.ACCOUNT_QUERY).parseXML(responsexml);
        if (ap == null) {
            return -3;
        }
        UserSummaryInfoReqRes tbrr = (UserSummaryInfoReqRes) ap.getContent();
//        for(int i=0;i<tbrr.getList().size();i++){
//            int str=Integer.valueOf(tbrr.getList().get(i).getResult());
        int resultcode = tbrr.getAppResResult().getResultCode();

        if (resultcode != 0) {
            logger.error("rescode:" + resultcode + "\tresultDes:" + tbrr.getAppResResult().getResultCode() + "\txml:" + xml);
            //JOptionPane.showMessageDialog(null, "rescode: " + resultcode + " \tresultDes: " + tbrr.getResult().getResultDes(), "操作", JOptionPane.INFORMATION_MESSAGE);
            if (resultcode == 1412) {
                return -4;
            }

            return -2;
        }
        return 0;
    }

    /**
     * 发送投注报文，并处理回复报文,发送公共部分,不写文件
     *
     * @param xml
     * @param cookieStore
     * @return
     */
    public int send(String xml, CookieStore cookieStore) {
        //logger.info("StaticVariable.SERVERURL is ----- " + StaticVariable.SERVERURL);
        String responsexml = HttpUtil.httpSend(xml, StaticVariable.SERVERURL, cookieStore);
        //logger.info("返回Xml： " + responsexml);
        if (responsexml == null) {
            return -1;
        } else if (responsexml.equals("sendError")) {
            return -5;
        }
        APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET).parseXML(responsexml);
        if (ap == null) {
            return -3;
        }
        PBetSchemeResponse tbrr = (PBetSchemeResponse) ap.getContent();
        int resultcode = tbrr.getResult().getResultCode();
        if (resultcode != 0) {
            logger.error("rescode:" + resultcode + "\tresultDes:" + tbrr.getResult().getResultDes() + "\txml:" + xml);
            // JOptionPane.showMessageDialog(null, "rescode: " + resultcode + " \tresultDes: " + tbrr.getResult().getResultDes(), "操作", JOptionPane.INFORMATION_MESSAGE);

            if (resultcode == 1618) {
                return -4;
            }

            return -2;
        }
        StaticVariable.serino = tbrr.getDealerSerial().trim();
        StaticVariable.cifer = tbrr.getCipher().replace(" ", "");
        return 0;
    }

    private String getNumber() {
        String fileName = "D:" + File.separator + "ggg" + File.separator + "hello.txt";
        File f = new File(fileName);
        String str = null;
        try {
            InputStream in = new FileInputStream(f);
            byte[] b = new byte[(int) f.length()];
            int len = in.read(b);
            int count = 0;
            int temp = 0;
            while ((temp = in.read()) != (-1)) {
                b[count++] = (byte) temp;
            }
            in.close();
            str = new String(b, 0, len);
            System.out.println("BB" + str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
