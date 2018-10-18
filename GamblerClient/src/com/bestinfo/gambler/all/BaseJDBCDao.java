package com.bestinfo.gambler.all;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author bestinfo-hq
 */
public class BaseJDBCDao {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(BaseJDBCDao.class);
    private static final ThreadLocal<Connection> connLocal = new ThreadLocal<Connection>();

    public static final String javaDBName = "virtual";
    private static final String javaDBusername = "test";
    private static final String javaDBpassword = "test";

    public static String SYBASECONNECT = "jdbc:sybase:Tds:192.168.0.248:5000/lottery?JCONNECT_VERSION=3";
    public static String SYBASEUSER = "fileuser";
    public static String SYBASEPWD = "fileuser";
    
    public static String MYSQLCONNECT;
    public static String MYSQLUSER;
    public static String MYSQLPWD;
    
    /**
     * 填写数据库类型
     *
     * @param dbTypeName
     */
    public BaseJDBCDao(String dbTypeName) {
        try {
            if (dbTypeName.equals("mysql")) {
                Class.forName("com.mysql.jdbc.Driver");
            } else if (dbTypeName.equals("sybase")) {
                Class.forName("com.sybase.jdbc3.jdbc.SybDataSource");
            }
        } catch (ClassNotFoundException e) {
            logger.error("", e);
        }
    }

    private Connection getAlsoConnection(String dbName) {
        try {
            if (dbName.equals("lottery")) {
                return DriverManager.getConnection(SYBASECONNECT, SYBASEUSER, SYBASEPWD);
            } else if (dbName.equals("richdb")) {
                return DriverManager.getConnection(MYSQLCONNECT, MYSQLUSER, MYSQLPWD);
            } else if (dbName.equals(javaDBName)) {
                Properties props = new Properties(); // connection properties
                props.put("user", javaDBusername);
                props.put("password", javaDBpassword);
                return DriverManager.getConnection("jdbc:derby:" + javaDBName + ";create=true", props);
            }
        } catch (SQLException e) {
            logger.error("", e);
            return null;
        }
        return null;
    }

    public Connection getConnection(boolean autoCommit, String dbName) {
        Connection con = connLocal.get();
        if (con == null) {
            con = this.getAlsoConnection(dbName);
            try {
                con.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                logger.error("", ex);
            }
        }
        return con;
    }

    public void closeConnection() {
        Connection con = this.connLocal.get();
        connLocal.remove();
        connLocal.set(null);
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                logger.error("", ex);
            }
        }
    }
}
