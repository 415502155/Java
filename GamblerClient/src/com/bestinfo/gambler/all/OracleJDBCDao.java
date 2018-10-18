/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.gambler.all;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author J
 */
public class OracleJDBCDao {

    private String user = "gamb";
    private String pass = "oracle";
    private String url = "jdbc:oracle:thin:@10.44.3.10:1521/fengcai";

    private Connection conn = null;//连接对象  

    /**
     * 连接数据库
     *
     * @return
     */
    public Connection createConnection() {

        String sDBDriver = "oracle.jdbc.driver.OracleDriver";

        try {
            Class.forName(sDBDriver).newInstance();
            conn = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println("数据库连接失败");
            e.printStackTrace();
        }
        return conn;
    }
     public Connection createConnectionMeta() {

        String sDBDriver = "oracle.jdbc.driver.OracleDriver";

        try {
            Class.forName(sDBDriver).newInstance();
            conn = DriverManager.getConnection(url,"meta", pass);
        } catch (Exception e) {
            System.out.println("数据库连接失败");
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭数据库
     *
     * @param conn
     */
    public void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("数据库关闭失败");
            e.printStackTrace();
        }
    }

}
