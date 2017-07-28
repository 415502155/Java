/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.gambler.all;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author chenliping
 */
public class RhClientProperties {
//    private static Logger logger = Logger.getLogger(RhClientProperties.class);
    
    public synchronized static Properties getpro() throws Exception {
        Properties prop = new Properties();
        //属性集合对象
//        FileInputStream fis = new FileInputStream(StaticVariable.RHCLIENTPROPERTIES);//属性文件输入流      
//        prop.load(fis);//将属性文件流装载到Properties对象中 
//        fis.close();//关闭流
        try {
            InputStream in = RhClientProperties.class.getResourceAsStream(StaticVariable.RHCLIENTPROPERTIES);
            prop.load(in);
            in.close();
        } catch (IOException e) {
            System.out.printf("PropertiesUtil.init(String fileName) ->", e);
            return null;
        }
        return prop;
    }
    
    public synchronized static void setpro(String key,String value) throws Exception{
        Properties prop = getpro();
        prop.setProperty(key,value);      
        FileOutputStream fos = new FileOutputStream(StaticVariable.RHCLIENTPROPERTIES); 
        //将Properties集合保存到流中 
        prop.store(fos, "Copyright");
        fos.close();//关闭流
        
    }
    
    public static void main(String[] args) throws Exception { 
        
    }
}
