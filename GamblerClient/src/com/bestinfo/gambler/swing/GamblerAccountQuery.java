/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.gambler.swing;

import com.bestinfo.arithmetic.NewSign;
import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.all.HttpSend;
import com.bestinfo.gambler.batch.BatchAccountQueryTask;
import com.bestinfo.gambler.batch.BatchQueryTask;
import com.bestinfo.gambler.batch.BetTicketPac;
import com.bestinfo.gambler.batch.GamblerInfo;
import com.bestinfo.gambler.batch.GamblerInfoDao;
import com.bestinfo.gambler.batch.TaskManager;
import com.bestinfo.gambler.batch.TmnSelectDao;
import com.bestinfo.gambler.cache.CacheManager;
import com.bestinfo.gambler.createBetNumber.Count;
import com.bestinfo.gambler.protocols.ActionID;
import com.bestinfo.protocols.message.APPMessage;
import com.bestinfo.protocols.message.AppHeader;
import com.bestinfo.protocols.users.UserSummaryInfoReq;
import com.bestinfo.protocols.users.UserSummaryInfoReqRes;
import com.bestinfo.protocols.client.users.PUserSumQueryClient;
import com.bestinfo.protocols.users.UserQueryReq;
import com.bestinfo.redis.login.DealerLoginRedis;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author J
 */
public class GamblerAccountQuery extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(GamblerAccountQuery.class);

    @Resource
    private DealerLoginRedis dealerlogin;

    /**
     * Creates new form UserLogin
     */
    public GamblerAccountQuery() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        sendtype = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        dealer_id = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        terminal_id = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Mobile = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        GamblerName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        GamblerPwd = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        Phone = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        Phone1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        Phone2 = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        eachnum5 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        eachnum2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel1.setText("发送方类型");

        sendtype.setText("4");
        sendtype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendtypeActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel2.setText("运营商ID");

        dealer_id.setText("146");
        dealer_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dealer_idActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel3.setText("终端机号");

        terminal_id.setText("14610001");
        terminal_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                terminal_idActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel4.setText("手机号");

        Mobile.setText("0");
        Mobile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MobileActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel5.setText("用户名");

        GamblerName.setText("asda");
        GamblerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GamblerNameActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel6.setText("密码");

        GamblerPwd.setText("sdasd");

        jButton1.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jButton1.setText("查询彩民账户信息");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
                login(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel7.setText("from");

        Phone.setText("14610001");
        Phone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PhoneActionPerformed(evt);
            }
        });

        jButton2.setText("批量查询");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel8.setText("电话号码");

        Phone1.setText("0");
        Phone1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Phone1ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel9.setText("to");

        Phone2.setText("14610100");
        Phone2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Phone2ActionPerformed(evt);
            }
        });

        jLabel57.setText("成功数目：");

        eachnum5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eachnum5ActionPerformed(evt);
            }
        });

        jLabel55.setText("用时");

        eachnum2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eachnum2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(GamblerName, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(Mobile))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(terminal_id, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                                .addComponent(sendtype)
                                .addComponent(dealer_id)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jButton1)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(GamblerPwd))))
                    .addComponent(Phone1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(Phone2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Phone, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel57)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(eachnum5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel55)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(eachnum2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(37, 37, 37))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(37, 37, 37)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(413, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sendtype)
                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(eachnum5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dealer_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55)
                    .addComponent(eachnum2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(terminal_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Mobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Phone, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Phone1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Phone2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GamblerName, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GamblerPwd, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(39, 39, 39)
                .addComponent(jButton1)
                .addContainerGap(69, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(160, 160, 160)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(236, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendtypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendtypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sendtypeActionPerformed

    private void terminal_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_terminal_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_terminal_idActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void login(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_login
        try {
            int actionID = ActionID.ACCOUNT_QUERY;
            AppHeader head = new AppHeader();
            head.setType(Integer.parseInt(sendtype.getText()));//head.setType(1);//发送方类型
            head.setAction(actionID);//协议编号
            head.setVersion(0);//version版本号(填0)
            head.setDealer_id(dealer_id.getText());//head.setDealer_id("111");//运营商ID
            head.setTerminal_id(Integer.parseInt(terminal_id.getText()));//head.setTerminal_id("111");//终端机号
            head.setMobile(Mobile.getText());//手机号(填0)head.setMobile("15101105612");
            head.setPhone(Phone.getText());//电话号码(填0)   head.setPhone("15101105612");
            head.setSent_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//发送时间head.setSent_time("2015-07-29 12:12:12");
            // head.setSign(new byte[0]);//head.setSign("1231321".getBytes());//报文体签名

            //组装报文内容<pkgC>
            UserSummaryInfoReq req = new UserSummaryInfoReq();
            req.setGamblerName(GamblerName.getText());
            req.setGamblerPwd(GamblerPwd.getText());
             //把报文转换成XML

            PUserSumQueryClient gamblerAccountQuery = new PUserSumQueryClient();
            String requestxml = gamblerAccountQuery.generateXML(head, req);

            //String requestxml = XmlFactoryClient.getInstance().getAppClientXF(actionID).generateXML(head, ui);
//            String str_key = null;
//            DealerLogin dl = dealerlogin.getDealerLoginInfo(Integer.parseInt(head.getDealer_id()), head.getTerminal_id());
//            if (dl != null && dl.getSessionkey() != null && !dl.getSessionkey().isEmpty()) {
//                str_key = dl.getSessionkey();
//                logger.error("Sessionkey:" + str_key);
//            }
//            
//            System.out.println("key:" + str_key);
//            String sessionKey = (String) CacheManager.getCacheInfo("sessionKey").getValue();
//            logger.info("sessionKey is: " + sessionKey);
//            if (sessionKey == null) {
//                logger.info("sessionKey is null");
//            }
//            byte[] ret_key = NewSign.GetSign(requestxml, "d:/gdclient.p12", "gdtest@2015", "cosmos", sessionKey);
            //byte[] ret_key = NewSign.GetSign(s, FilePath.getStorePath(), FilePath.getAliaspwd(), FilePath.getAlias(), str_key);
           byte[] ret_key = NewSign.GetSign(requestxml, StaticVariable.privateKey, StaticVariable.SESSION);
            head.setBody_sign(ret_key);
            requestxml = gamblerAccountQuery.generateXML(head, req);

            //拼接发送XML
            String xml = "action=" + actionID + "&" + requestxml;
            System.out.println(xml);
            //发送xml并取回回复XML
            String responsexml = HttpSend.httpSend(StaticVariable.SERVERURL, xml);
            // String responsexml = HttpUtil.httpSend(xml, StaticVariable.SERVERURL, true);//StaticVariable.SERVERURL服务器地址
            //解析XML
            APPMessage ap = gamblerAccountQuery.parseXML(responsexml);
            //APPMessage ap =  XmlFactoryClient.getInstance().getAppClientXF(actionID).parseXML(responsexml);
            //回复报文
            UserSummaryInfoReqRes tbrr = (UserSummaryInfoReqRes) ap.getContent();//得到返回内容
            int resultcode = tbrr.getAppResResult().getResultCode();
            //输出结果
            if (resultcode == 0) {
                System.out.println("send successfull");
            } else {
                System.out.println(resultcode + "\t" + tbrr.getAppResResult().getResultDes());
            }
            JOptionPane.showMessageDialog(this, "返回码:" + resultcode + "结果:" + tbrr.getAppResResult().getResultDes(), "操作", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }//GEN-LAST:event_login

    private void MobileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MobileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MobileActionPerformed

    private void GamblerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GamblerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GamblerNameActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
             Count count = new Count();
            int actionID = ActionID.ACCOUNT_QUERY;
            int dealerid = Integer.parseInt(dealer_id.getText().trim());
            if (dealerid == 145 || dealerid == 146) {
                Set<Integer> set = new HashSet<>();
                int fromInt = Integer.parseInt(Phone.getText().trim());
                int toInt = Integer.parseInt(Phone2.getText().trim());
                try {
                    //查询t_tmn_info表中的终端机号
                    TmnSelectDao.getTerminalId(dealerid, fromInt, toInt, set);
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
                logger.info("查询t_tmn_info表中的终端机号结果为 ：" + set.toString());
                long startTime = System.currentTimeMillis();
                for (Integer terminalid : set) {
                    AppHeader head = new AppHeader();
                    head.setType(Integer.parseInt(sendtype.getText()));//head.setType(1);//发送方类型
                    head.setAction(actionID);//协议编号
                    head.setVersion(0);//version版本号(填0)
                    head.setDealer_id(dealer_id.getText());//head.setDealer_id("111");//运营商ID
                    head.setTerminal_id(terminalid);//head.setTerminal_id("111");//终端机号
                    head.setMobile(Mobile.getText());//手机号(填0)head.setMobile("15101105612");
                    head.setPhone(Phone.getText());//电话号码(填0)   head.setPhone("15101105612");
                    head.setSent_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//发送时间head.setSent_time("2015-07-29 12:12:12");
                    // head.setSign(new byte[0]);//head.setSign("1231321".getBytes());//报文体签名

                    //组装报文内容<pkgC>
                    UserSummaryInfoReq req = new UserSummaryInfoReq();
                    req.setGamblerName(GamblerName.getText());
                    req.setGamblerPwd(GamblerPwd.getText());

                    TaskManager.getInstance().execute(new BatchAccountQueryTask(head, req));
                }
                long endtTime = System.currentTimeMillis();
                logger.info("共用时间是多少呢？？？:" + (endtTime - startTime) / 1000F + "秒");
            } else {//140\141无自助终端
                List list = null;
                int from = Integer.parseInt(Phone.getText().trim());
                int to = Integer.parseInt(Phone2.getText().trim());
                AppHeader head = new AppHeader();
                head.setType(Integer.parseInt(sendtype.getText()));//head.setType(1);//发送方类型
                head.setAction(actionID);//协议编号
                head.setVersion(0);//version版本号(填0)
                head.setDealer_id(dealer_id.getText());//head.setDealer_id("111");//运营商ID
                head.setTerminal_id(Integer.parseInt(terminal_id.getText()));//head.setTerminal_id("111");//终端机号
                head.setMobile(Mobile.getText());//手机号(填0)head.setMobile("15101105612");
                head.setPhone(Phone.getText());//电话号码(填0)   head.setPhone("15101105612");
                head.setSent_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//发送时间head.setSent_time("2015-07-29 12:12:12");

                BetTicketPac bt = new BetTicketPac();
                bt.execUserSumBatch(from, to, count, head);
                 eachnum2.setText(StaticVariable.TASTE_TIME + "");
                //  logger.info("共用时间为" + (endtTime - stsrtTime) / 1000F);
                eachnum5.setText(count.getCount() + "");
              //  UserSummaryInfoReq req = new UserSummaryInfoReq();
//                    req.setGamblerName(((GamblerInfo) gamblerInfo).getGambler_name());
//                    req.setGamblerPwd(((GamblerInfo) gamblerInfo).getPwd());

//                try {
//                    //查询用户名列表
//                    list = GamblerInfoDao.getGambInfo(dealer_id.getText());
//                } catch (Exception ex) {
//                    java.util.logging.Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                logger.info("查询t_tmn_info表中的终端机号结果为 ：" + list.toString());
//                long startTime = System.currentTimeMillis();
//                for (Object gamblerInfo : list) {
//                    AppHeader head = new AppHeader();
//                    head.setType(Integer.parseInt(sendtype.getText()));//head.setType(1);//发送方类型
//                    head.setAction(actionID);//协议编号
//                    head.setVersion(0);//version版本号(填0)
//                    head.setDealer_id(dealer_id.getText());//head.setDealer_id("111");//运营商ID
//                    head.setTerminal_id(Integer.parseInt(terminal_id.getText()));//head.setTerminal_id("111");//终端机号
//                    head.setMobile(Mobile.getText());//手机号(填0)head.setMobile("15101105612");
//                    head.setPhone(Phone.getText());//电话号码(填0)   head.setPhone("15101105612");
//                    head.setSent_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//发送时间head.setSent_time("2015-07-29 12:12:12");
//
//                    UserSummaryInfoReq req = new UserSummaryInfoReq();
//                    req.setGamblerName(((GamblerInfo) gamblerInfo).getGambler_name());
//                    req.setGamblerPwd(((GamblerInfo) gamblerInfo).getPwd());
//
//                    TaskManager.getInstance().execute(new BatchAccountQueryTask(head, req));
//                }
//                long endtTime = System.currentTimeMillis();
//                logger.info("共用时间是多少呢？？？:" + (endtTime - startTime) / 1000F + "秒");
            }
        } catch (Exception ex) {
            logger.error(ex);
        }        // TODO add your hand
    }//GEN-LAST:event_jButton2ActionPerformed

    private void PhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PhoneActionPerformed

    private void Phone2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Phone2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Phone2ActionPerformed

    private void Phone1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Phone1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Phone1ActionPerformed

    private void dealer_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dealer_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dealer_idActionPerformed

    private void eachnum5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eachnum5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eachnum5ActionPerformed

    private void eachnum2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eachnum2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eachnum2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GamblerAccountQuery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GamblerAccountQuery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GamblerAccountQuery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GamblerAccountQuery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GamblerAccountQuery().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField GamblerName;
    private javax.swing.JTextField GamblerPwd;
    private javax.swing.JTextField Mobile;
    private javax.swing.JTextField Phone;
    private javax.swing.JTextField Phone1;
    private javax.swing.JTextField Phone2;
    private javax.swing.JTextField dealer_id;
    private javax.swing.JTextField eachnum2;
    private javax.swing.JTextField eachnum5;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField sendtype;
    private javax.swing.JTextField terminal_id;
    // End of variables declaration//GEN-END:variables
}
