package com.bestinfo.controller.clpdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class ReadWriteFile {
     /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
    public static String readFile()
    {
        StringBuffer sb=new StringBuffer();
        String tempstr=null;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sf.format(new Date());
        String year = time.substring(0, 4);
        String month = time.substring(5, 7);
        String day = time.substring(8, time.length());
        String timefile =year+month+day;
        try
        {
            String path="e:/"+timefile+"/MaxSequenceIdString.data";
            File file=new File(path);
            if(!file.exists())
                throw new FileNotFoundException();            
//            BufferedReader br=new BufferedReader(new FileReader(file));            
//            while((tempstr=br.readLine())!=null)
//                sb.append(tempstr);    
            //另一种读取方式
            FileInputStream fis=new FileInputStream(file);
            BufferedReader br=new BufferedReader(new InputStreamReader(fis));
            while((tempstr=br.readLine())!=null)
                sb.append(tempstr);
        }
        catch(IOException ex)
        {
            System.out.println(ex.getStackTrace());
        }
        return sb.toString();
    }
    public static void writeFile(String MaxSequenceIdString){
         FileOutputStream out = null;
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String time = sf.format(new Date());
            String year = time.substring(0, 4);
            String month = time.substring(5, 7);
            String day = time.substring(8, time.length());
            String timefile =year+month+day;
            try {  

                File file =new File("e:/"+timefile);    
                //如果文件夹不存在则创建    
                if  (!file .exists()  && !file .isDirectory())      
                {       
                    System.out.println("//不存在");  
                    file .mkdir();    
                     out = new FileOutputStream("e:/"+timefile+"/MaxSequenceIdString.data",false); // MaxSequenceIdString存放地址 false为覆盖原来文本
                } else   
                {  
                    System.out.println("//目录存在"); 
                    out = new FileOutputStream("e:/"+timefile+"/MaxSequenceIdString.data",false); // MaxSequenceIdString存放地址    
                }  
                
                out.write(MaxSequenceIdString.getBytes());    
                out.close();
            } catch (Exception e) {  
                
            } 
           
    }
    
    
     public static String readFile1()
    {
        StringBuffer sb=new StringBuffer();
        String tempstr=null;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sf.format(new Date());
        String year = time.substring(0, 4);
        String month = time.substring(5, 7);
        String day = time.substring(8, time.length());
        String timefile =year+month+day;
        try
        {
            String path="e:/"+timefile+"/TacketBetInfo.txt";
            File file=new File(path);
            if(!file.exists())
                throw new FileNotFoundException();            
//            BufferedReader br=new BufferedReader(new FileReader(file));            
//            while((tempstr=br.readLine())!=null)
//                sb.append(tempstr);    
            //另一种读取方式
            FileInputStream fis=new FileInputStream(file);
            BufferedReader br=new BufferedReader(new InputStreamReader(fis));
            while((tempstr=br.readLine())!=null)
                sb.append(tempstr);
        }
        catch(IOException ex)
        {
            System.out.println(ex.getStackTrace());
        }
        return sb.toString();
    }
    public static void writeFile1(String TacketBetInfo){
        //System.out.println(TacketBetInfo);
        //String s="123";
         FileOutputStream out = null;
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String time = sf.format(new Date());
            String year = time.substring(0, 4);
            String month = time.substring(5, 7);
            String day = time.substring(8, time.length());
            String timefile =year+'-'+month+'-'+day;
            try {  

                File file =new File("e:/"+timefile);    
                //如果文件夹不存在则创建    
                if  (!file .exists()  && !file .isDirectory())      
                {       
                    System.out.println("//不存在");  
                    file .mkdir();    
                     out = new FileOutputStream("e:/"+timefile+"/TacketBetInfo.txt",true); // 每个线程查询数据存放存放地址 true为在原来已存在文本继续写入
                } else   
                {  
                    System.out.println("//目录存在"); 
                    out = new FileOutputStream("e:/"+timefile+"/TacketBetInfo.txt",true);  
                }  
                try {
                    out.write(TacketBetInfo.getBytes());   
                    System.out.println("write finish");
                } catch (Exception e) {
                    System.out.println("shen me yi chang");
                }
                                
                out.close();
            } catch (Exception e) {  
                
            } 
           
    }
    public static void delFile1()
    {
        System.out.println("del file");
        FileOutputStream out = null;
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String time = sf.format(new Date());
            String year = time.substring(0, 4);
            String month = time.substring(5, 7);
            String day = time.substring(8, time.length());
            String timefile =year+'-'+month+'-'+day;
            try {  

                File file =new File("e:/"+timefile);    
                //如果文件夹不存在则创建    
                if  (!file .exists()  && !file .isDirectory())      
                {       
                    System.out.println("//不存在");  
                    file .mkdir();    
                     out = new FileOutputStream("e:/"+timefile+"/TacketBetInfo.txt",false); 
                } else   
                {  
                    System.out.println("//目录存在"); 
                    out = new FileOutputStream("e:/"+timefile+"/TacketBetInfo.txt",false);  
                }  
                
                out.write(new String("").getBytes());    
                out.close();
            } catch (Exception e) {  
                
            } 
    }
    public static void main(String [] args){
        //delFile1();
//        ReadWriteFile aFile= new ReadWriteFile();
//        String maxSequenceID=aFile.readFile1();
//        System.out.println("dhfjk:"+maxSequenceID);
         writeFile1("1234567890987654321");
    }
}
