/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.gambler.swing;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

/**
 *
 * @author Administrator
 */
public class test {
    public static void main(String[] args) { 
        Properties props = new Properties();
        InputStream in=null;
        String filePath ="F:\\gamblerClient\\config\\dealer.properties";
        try{
            in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
//            String value = props.getProperty("name");    
//            System.out.println("sss"+value);
            Iterator<String> it=props.stringPropertyNames().iterator();
             while(it.hasNext()){
                 String key=it.next();
                 String value=props.getProperty(key);
                 value=new String(value.getBytes("ISO-8859-1"),"utf-8");
                 System.out.println(key+":"+value);
             }
             in.close();
             
             ///保存属性到b.properties文件
//             FileOutputStream oFile = new FileOutputStream("b.properties", true);//true表示追加打开
//             props.setProperty("phone", "10086");
//             props.store(oFile, "The New properties file");
//             oFile.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                in.close();//-----------------------------------important
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
     }
}
