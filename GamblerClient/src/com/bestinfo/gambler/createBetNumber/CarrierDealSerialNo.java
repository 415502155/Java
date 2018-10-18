package com.bestinfo.gambler.createBetNumber;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Logger;

public class CarrierDealSerialNo {

//    private static Logger logger = Logger.getLogger(CarrierDealSerialNo.class);
//    private static CarrierDealSerialNo dealSerialNo = null;
//    private static String path = null;
//    private static int serialAmount = 0;
//
//    private CarrierDealSerialNo() {
////        String paths = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
//        path = "properties/SystemConfig.properties";//SystemConfig.properties文件放在dist7/properties下
//        //  path = paths.substring(0, paths.indexOf("build")) + "src/SystemConfig.properties";//这个路径用于main函数测试用的
//        Properties pro = new Properties();
//        InputStream infile = null;
//        try {
//            infile = new BufferedInputStream(new FileInputStream(path));
//            pro.load(infile);
//            Enumeration e = pro.propertyNames();
//            while (e.hasMoreElements()) {
//                String key = (String) e.nextElement();//获取此文件中的key
//                String value = (String) pro.get(key);
//                //System.out.println("key:" + key);
//                // System.out.println("value:" + value);
//                serialAmount = Integer.parseInt(value);
//            }
//        } catch (IOException e) {
//            logger.error("infile error ", e);
//
//        } finally {
//            try {
//                infile.close();
//            } catch (IOException e) {
//                logger.error("infile 错误 ", e);
//            }
//        }
//    }
////初始化
//    public static CarrierDealSerialNo getInstance() {
//        if (dealSerialNo == null) {
//            dealSerialNo = new CarrierDealSerialNo();
//        }
//        return dealSerialNo;
//    }
//
//    /**
//     * 此方法产生运营商交易流水号
//     *
//     * @param agentNumber 代理方编号
//     * @param dealType 交易类型
//     * @param flag 标记 
//     * 交易流水号=代理方编号+交易类型+日期类型（yyyyMMddHHmmss）+三位标记+7位计数器
//     */
//    public static synchronized String getCarrierDealSerialNo(String agentNumber, String dealType, String flag) {
//        Date dt = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        String str = sdf.format(dt);//生成格式例如：20140226142603         
//        String strCount = String.format("%07d", (serialAmount++));//不足七位计数器前面补零作为计数器       
//        String dealSerialNo = agentNumber + "@" + dealType + "@" + str + "@" + flag + strCount;
//        return dealSerialNo;
//    }
//
//    /**
//     * 此方法将最终的数据数据写入文件里
//     *
//     */
//    public static void lastNumberTowriteFile() {
//        OutputStream outFile = null;
//        try {
//            outFile = new BufferedOutputStream(new FileOutputStream(path));
//            outFile.write(("serialNo=" + serialAmount).getBytes());
//        } catch (IOException e) {
//            logger.error("outFile error ", e);
//        } finally {
//            try {
//                outFile.close();
//            } catch (IOException e) {
//                logger.error("outFile 错误 ", e);
//            }
//        }
//
//    }
//
//    //写一个main方法，来验证一下  
//    public static void main(String[] args) throws Exception {
//        CarrierDealSerialNo d1 = CarrierDealSerialNo.getInstance();
//        for (int i = 0; i < 10; i++) {
//            String str = d1.getCarrierDealSerialNo("141", "01", "l21");
//            System.out.println(str);
//        }
//        d1.lastNumberTowriteFile();
//    }
}
