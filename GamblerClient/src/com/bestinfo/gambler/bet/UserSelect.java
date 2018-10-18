/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.gambler.bet;

//import cn.com.bestinfo.core.entity.TChargeType;
//import cn.com.bestinfo.core.entity.TDealerInfo;
//import cn.com.bestinfo.core.entity.TGamblerInfo;
import com.bestinfo.bean.business.DealerInfo;
import com.bestinfo.bean.encoding.TChargeType;
import com.bestinfo.bean.gambler.TGamblerInfo;
import com.bestinfo.define.system.SendType;
import com.bestinfo.gambler.all.BaseJDBCDao;
import com.bestinfo.gambler.all.HttpUtil;
import com.bestinfo.gambler.all.OracleJDBCDao;
import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.createBetNumber.SerialNo;
import com.bestinfo.protocols.agents.AgentRechargeQueryReq;
import com.bestinfo.protocols.message.AppHeader;
import com.bestinfo.protocols.users.UserLoginReq;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息相关查询
 *
 * @author chenliping
 */
public class UserSelect {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserSelect.class);

    /**
     * 从数据库中获取指定数量的指定角色用户
     *
     * @param userno 用户个数
     * @param gamblerRole 用户角色
     * @param dealerid 运营商id
     * @param alluser
     */
    public static void getUser(int userno, int gamblerRole, String dealerid, List<UserLoginReq> alluser) {
        BaseJDBCDao bCDao = new BaseJDBCDao("derby");
        Connection conn = bCDao.getConnection(true, BaseJDBCDao.javaDBName);
        String sql = "select gambler_name,gambler_pwd from gamb.T_GAMBLER_INFO where gambler_role =" + gamblerRole + " and dealer_id = '" + dealerid + "'";
        logger.info("获取用户");
        int count = 0;
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                count++;
                UserLoginReq ur = new UserLoginReq();
                ur.setGamblerName(rs.getString("gambler_name"));
//                ur.setGambler_pwd(HttpUtil.encryptPwd("123456", null));
                ur.setGamblerPwd("123456");
                alluser.add(ur);
                if (count == userno) {
                    break;
                }
            }
            rs.close();
            s.close();
            conn.close();
        } catch (SQLException e) {
            logger.error("", e);
        }
        if (count != 0) {
            if (count < userno) {
                logger.error("用户数不够！实际得到用户个数：" + count);
            }
        } else {
            logger.error("用户数据为0！");
        }
    }

    /**
     * 根据运营商与用户名，查出此用户一些其它信息
     *
     * @param gamblername
     * @param dealerid
     * @return
     */
    public static TGamblerInfo getUser(String gamblername, String dealerid) {
        OracleJDBCDao ojdbc = new OracleJDBCDao();
        Connection conn = ojdbc.createConnection();
//        BaseJDBCDao bCDao = new BaseJDBCDao("derby");
//        Connection conn = bCDao.getConnection(true, BaseJDBCDao.javaDBName);
        String sql = "select gambler_name,gambler_pwd,account_card,mobile_phone,charge_type_id,account_type_id,city_id "
                + "from T_GAMBLER_INFO where  dealer_id = '" + dealerid + "' and gambler_name='" + gamblername + "'";
        logger.info("获取用户"+sql);
        TGamblerInfo ur = new TGamblerInfo();
        int count = 0;
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                count++;
                ur.setGambler_name(rs.getString("gambler_name"));
                ur.setGambler_pwd(rs.getString("gambler_pwd"));
                ur.setAccount_card(rs.getString("account_card"));
                ur.setMobile_phone(rs.getString("mobile_phone"));
                ur.setCharge_type_id(rs.getInt("charge_type_id"));
                ur.setAccount_type_id(rs.getInt("account_type_id"));
                ur.setCity_id(rs.getInt("city_id"));
            }
            rs.close();
            s.close();
            conn.close();
        } catch (SQLException e) {
            logger.error("", e);
            return null;
        }
        if (count == 0) {
            logger.error("get 0 user");
            return null;
        }
        return ur;
    }

    /**
     * 从数据库中获取指定数量的指定角色用户
     *
     * @param userno
     * @param role
     * @param dealerid
     * @return
     */
    public static List<TGamblerInfo> getUser(int userno, int role, String dealerid) {
         OracleJDBCDao ojdbc = new OracleJDBCDao();
        Connection conn = ojdbc.createConnection();
        String sql = "select gambler_name,gambler_pwd,account_card,mobile_phone,charge_type_id,account_type_id,city_id,id_type_id,id_no "
                + "from T_GAMBLER_INFO where gambler_role = " + role + " and dealer_id = '" + dealerid + "'";
        int count = 0;
        List<TGamblerInfo> alluser = new ArrayList<TGamblerInfo>();
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                count++;
                TGamblerInfo ur = new TGamblerInfo();
                ur.setGambler_name(rs.getString("gambler_name"));
                ur.setGambler_pwd("123456");
                ur.setAccount_card(rs.getString("account_card"));
                ur.setMobile_phone(rs.getString("mobile_phone"));
                ur.setCharge_type_id(rs.getInt("charge_type_id"));
                ur.setAccount_type_id(rs.getInt("account_type_id"));
                ur.setCity_id(rs.getInt("city_id"));
                ur.setId_type_id(rs.getInt("id_type_id"));
                ur.setId_no(rs.getString("id_no"));
                alluser.add(ur);
                if (count == userno) {
                    break;
                }
            }
            rs.close();
            s.close();
            conn.close();
        } catch (SQLException e) {
            logger.error("", e);
            return null;
        }
        if (count != 0) {
            if (count < userno) {
                logger.error("用户数不够！实际得到用户个数：" + count);
                return null;
            }
            return alluser;
        } else {
            logger.error("用户数据为0！");
            return null;
        }
    }

    /**
     * 查业务编号
     *
     * @param dealerid
     * @return
     */
    public static ArrayList<TChargeType> getChargeType(String dealerid) {
        OracleJDBCDao ojdbc = new OracleJDBCDao();
        Connection conn = ojdbc.createConnectionMeta();
        ArrayList<TChargeType> atchtype = new ArrayList<TChargeType>();
        String sql = "select charge_type_id,charge_type_name,dealer_id,work_status from T_charge_type where dealer_id='" + dealerid + "' "
                + "and (charge_type_name LIKE '%话费%' or charge_type_name LIKE '%返奖%' or  charge_type_name LIKE '%支付%')";
//        BaseJDBCDao bCDao = new BaseJDBCDao("derby");
//        Connection conn = bCDao.getConnection(true, BaseJDBCDao.javaDBName);
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                TChargeType tch = new TChargeType();
                tch.setChargeTypeId(rs.getInt("charge_type_id"));
                tch.setChargeTypeName(rs.getString("charge_type_name"));
                tch.setDealerId(rs.getString("dealer_id"));
                tch.setWorkStatus(rs.getInt("work_status"));
                atchtype.add(tch);
            }
            rs.close();
            s.close();
            conn.close();
        } catch (Exception e) {
            logger.error("", e);
        }
        return atchtype;
    }

    /**
     * 检测运营商是否有物理终端机
     *
     * @param dealerid
     */
    public static int checkDealer(String dealerid) throws Exception {
        BaseJDBCDao bCDao = new BaseJDBCDao("derby");
        Connection conn = bCDao.getConnection(true, BaseJDBCDao.javaDBName);
        Statement s = conn.createStatement();
        String sql = "select terminal_condition from T_dealer_info where dealer_id='" + dealerid + "'";
        ResultSet rs = s.executeQuery(sql);
        int condition = 0;
        while (rs.next()) {
            condition = rs.getInt("terminal_condition");
        }
        rs.close();
        s.close();
        conn.close();
        return condition;
    }

    /**
     * 获取运营商的物理终端机号，有证书的即取出来了
     *
     * @param dealerid
     * @return
     * @throws Exception
     */
    private static ArrayList<String> getDealerinfoFromLocal(String dealerid) {
        try {
            ArrayList<String> li = new ArrayList<String>();
            BaseJDBCDao bCDao = new BaseJDBCDao("derby");
            Connection conn = bCDao.getConnection(true, BaseJDBCDao.javaDBName);
            Statement s = conn.createStatement();
            String sesql = "select a.dealer_id,a.terminal_id,b.terminal_phy_id from T_dealer_tmn_info a,T_tmn_info b where a.dealer_id='" + dealerid + "' and a.terminal_id=b.terminal_id and b.terminal_status=0";
            ResultSet sers = s.executeQuery(sesql);
            while (sers.next()) {
                String tphyid = sers.getString("terminal_phy_id");
                String paphy = StaticVariable.KEYPATH + tphyid + ".p12";
                File f = new File(paphy);
                if (f.exists()) {
                    li.add(tphyid);
                }
            }
            sers.close();
            s.close();
            conn.close();
            return li;
        } catch (SQLException e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 检测并获取运营商的物理终端机号
     *
     * @param dealerid
     * @return
     */
    public static ArrayList<String> checkandgetTmnPhy(String dealerid) {
        ArrayList<String> ait = null;
        try {
            int tmnif = checkDealer(dealerid);
            if (tmnif != 0) {
                ait = getDealerinfoFromLocal(dealerid);
                if (ait.isEmpty()) {
                    logger.error("没有该运营商物理终端，请同步运营商信息！");
                    return null;
                }
            }
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
        return ait;
    }

    /**
     * 获取运营商的信息
     *
     * @param dealerId
     * @return
     */
    public static DealerInfo getDealerinfo(String dealerId) {
        try {
            BaseJDBCDao bCDao = new BaseJDBCDao("derby");
            Connection conn = bCDao.getConnection(true, BaseJDBCDao.javaDBName);
            Statement s = conn.createStatement();
            String sql = "SELECT owner_name,id_no,account_card,terminal_condition from  T_dealer_info where dealer_id = '" + dealerId + "'";
            ResultSet rs = s.executeQuery(sql);
            DealerInfo dealerInfo = new DealerInfo();
            while (rs.next()) {
                dealerInfo.setOwner_name(rs.getString("owner_name").trim());
                dealerInfo.setId_no(rs.getString("id_no").trim());
                dealerInfo.setAccount_card(rs.getString("account_card").trim());
                dealerInfo.setTerminal_condition(rs.getInt("terminal_condition"));
            }
            rs.close();
            s.close();
            conn.close();
            return dealerInfo;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

//    /**
//     * 获取运营商的信息
//     *
//     * @param arurr
//     * @param dealerid
//     * @return
//     * @throws Exception
//     */
//    public static int getDealerinfo(AgentAbstractUserReqList arurr, String dealerid) throws Exception {
//        BaseJDBCDao bCDao = new BaseJDBCDao("derby");
//        Connection conn = bCDao.getConnection(true, BaseJDBCDao.javaDBName);
//        Statement s = conn.createStatement();
//        String sql = "SELECT owner_name,id_no,account_card,terminal_condition from  T_dealer_info where dealer_id = '" + dealerid + "'";
//        ResultSet rs = s.executeQuery(sql);
//        int terminal = -1;
//        while (rs.next()) {
//            arurr.setOwner_name(rs.getString("owner_name").trim());
//            arurr.setIDNo(rs.getString("id_no").trim());
//            arurr.setBankCard(rs.getString("account_card").trim());
//            terminal = rs.getInt("terminal_condition");
//        }
//        rs.close();
//        s.close();
//        conn.close();
//        return terminal;
//    }
    private static void getPhoneUser(List<UserLoginReq> alluser, int userno, String dealerid) {
        List<TGamblerInfo> lt = UserSelect.getUser(userno, 21, dealerid);
        for (TGamblerInfo tg : lt) {
            UserLoginReq uq = new UserLoginReq();
            uq.setGamblerName(tg.getGambler_name());
            uq.setGamblerPwd("123456");
            alluser.add(uq);
        }
    }

    private static void getphyUser(List<UserLoginReq> alluser, int userno, String dealerid) {
        ArrayList<String> als = UserSelect.checkandgetTmnPhy(dealerid);
        if (als == null) {
            return;
        }
        int i = 0;
        for (; i < als.size(); i++) {
            String name = als.get(i);
            UserLoginReq uq = new UserLoginReq();
            uq.setGamblerName(name);
//            uq.setGambler_pwd(HttpUtil.encryptPwd(name));
            uq.setGamblerPwd("123456");
            alluser.add(uq);
            if ((i + 1) == userno) {
                break;
            }
        }
        if (i + 1 < userno) {
            logger.info("实际物理终端数不够，得到：" + i + "\t\t实际需求数：" + userno);
        }
    }

    /**
     * 获取需求的登录用户（各个业务具有的登录权限的人不一样）
     *
     * @param head
     * @param userno
     * @return
     */
    public static List<UserLoginReq> getUserobj(AppHeader head, int userno) {
        List<UserLoginReq> alluser = new ArrayList<UserLoginReq>();
        if (head.getType() == SendType.PHONE) {
            UserSelect.getUser(userno, SerialNo.USERMANAGER, head.getDealer_id(), alluser);//获取管理员进行登录
//            if (alluser.isEmpty()) {
//                return alluser;
//            }
        } else if (head.getType() == SendType.HELPUS) {//使用物理终端登录
            getphyUser(alluser, userno, head.getDealer_id());
        } else if (head.getType() == SendType.MOBLICLLI) {//使用用户名登录
            getPhoneUser(alluser, userno, head.getDealer_id());
        } else {
            logger.error("发送方类型错误");
        }
        return alluser;
    }

    /**
     * 获取运营商代理充值与提款的数据
     *
     * @param no 用户个数
     * @param type
     * @param allar
     */
    public static int getProxyQueryNO(int no, int type, List<AgentRechargeQueryReq> allar) {
        BaseJDBCDao bCDao = new BaseJDBCDao("derby");
        Connection conn = bCDao.getConnection(true, BaseJDBCDao.javaDBName);
        String sql = "select GAMBLER_ID,BANK_SERIAL_NO from TEST.T_GAMBLER_ACCOUNT_DETAIL where TRADE_TYPE =" + type;
        int count = 0;
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                count++;
                AgentRechargeQueryReq ur = new AgentRechargeQueryReq();
                ur.setGamblerName(rs.getString("GAMBLER_ID"));
                ur.setDealerSerial(rs.getString("BANK_SERIAL_NO"));
                allar.add(ur);
                if (count == no) {
                    break;
                }
            }
            rs.close();
            s.close();
            conn.close();
        } catch (SQLException e) {
            logger.error("", e);
            return -1;
        }
        if (count != 0) {
            if (count < no) {
                logger.error("流水号数不够！实际得到流水号个数：" + count);
                return -2;
            }
        } else {
            logger.error("流水号数据为0！");
            return -1;
        }
        return 0;
    }

    //将用户分组
    public static List<List<TGamblerInfo>> getUserGroup(int userno, int role, String dealerid) {
//        BaseJDBCDao bCDao = new BaseJDBCDao("derby");
//        Connection conn = bCDao.getConnection(true, BaseJDBCDao.javaDBName);
//        String sql = "select city_id,count(*) from TEST.T_GAMBLER_INFO where gambler_role = " + role + " and dealer_id = '" + dealerid + "' group by city_id";
//        logger.info(sql);
//        List<Integer> gamNamCount = new ArrayList<Integer>();
//        List<TGamblerInfo> users = null;
//        List<List<TGamblerInfo>> allusers = new ArrayList<List<TGamblerInfo>>();
//        int count = 0;
//        try {
//            Statement s = conn.createStatement();
//            ResultSet rs = s.executeQuery(sql);
//            while (rs.next()) {
//                count++;
//                gamNamCount.add(rs.getInt(2));                
//                users = UserSelect.getUserForCity(role, dealerid, rs.getInt(1));                
//                allusers.add(users);
//                if (count == userno) {
//                    break;
//                }
//            }
//            rs.close();
//            s.close();
//            conn.close();
//            return allusers;
//        } catch (SQLException e) {
//            logger.error("", e);
//            return null;
//        }
        OracleJDBCDao ojdbc = new OracleJDBCDao();
        Connection conn = ojdbc.createConnection();
        String sql = "select city_id,count(*) from T_GAMBLER_INFO where gambler_role = " + role + " and dealer_id = '" + dealerid + "' group by city_id";
        System.out.println(sql);
        logger.info(sql);
        List<Integer> gamNamCount = new ArrayList<Integer>();
        List<TGamblerInfo> users = null;
        List<List<TGamblerInfo>> allusers = new ArrayList<List<TGamblerInfo>>();
        int count = 0;
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                count++;
                gamNamCount.add(rs.getInt(2));
                users = UserSelect1.getUserForCity(role, dealerid, rs.getInt(1));
                allusers.add(users);
                if (count == userno) {
                    break;
                }
            }
            rs.close();
            s.close();
            conn.close();
            return allusers;
        } catch (SQLException e) {
            logger.error("", e);
            return null;
        }

    }

    public static List<TGamblerInfo> getUserForCity(int role, String dealerid, int cityId) {
        BaseJDBCDao bCDao = new BaseJDBCDao("derby");
        Connection conn = bCDao.getConnection(true, BaseJDBCDao.javaDBName);
        String sql = "select gambler_name,gambler_pwd,account_card,mobile_phone,charge_type_id,account_type_id,city_id,id_type_id,id_no from TEST.T_GAMBLER_INFO where gambler_role = " + role + " and dealer_id = '" + dealerid + "' and city_id = " + cityId + "";
        logger.info(sql);
        ArrayList<TGamblerInfo> users = new ArrayList<TGamblerInfo>();
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                Statement s1 = conn.createStatement();
                ResultSet rs1 = s1.executeQuery(sql);
                while (rs1.next()) {
                    TGamblerInfo ur = new TGamblerInfo();
//                    logger.info("gambler_name:  "+ rs.getString("gambler_name"));
                    ur.setGambler_name(rs.getString("gambler_name"));
                    ur.setGambler_pwd("123456");
                    ur.setAccount_card(rs.getString("account_card"));
                    ur.setMobile_phone(rs.getString("mobile_phone"));
                    ur.setCharge_type_id(rs.getInt("charge_type_id"));
                    ur.setAccount_type_id(rs.getInt("account_type_id"));
                    ur.setCity_id(rs.getInt("city_id"));
                    ur.setId_type_id(rs.getInt("id_type_id"));
                    ur.setId_no(rs.getString("id_no"));
                    users.add(ur);
                }
            }
            rs.close();
            s.close();
            conn.close();
            return users;
        } catch (SQLException e) {
            logger.error("", e);
            return null;
        }
    }
}
