package com.bestinfo.controller.qrcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class DownloadFile {
       public void filePublicKeyDownload(HttpServletResponse response,String sj){  
        int sjlen =sj.length();
        String syear="";
        String smonth="";
        String sday="";
        if(sjlen==8){
            syear=sj.substring(0, 4);
            smonth=sj.substring(4, 6);
            sday=sj.substring(6, 8);
        }
        System.out.println("ss:"+syear+"ssss:"+smonth+"ssssss:"+sday);   
        if("1".equals(sj)){
            String timefile ="allKeyAndRemark";///lottery/dat/tmnclpkey/allKeyAndRemark/TerminalPublicKey.data
            String publicKeyFilePath = "E:\\20170118\\TerminalPublicKey.data";
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型  
            response.setContentType("multipart/form-data");  
            //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)  
            response.setHeader("Content-Disposition", "attachment;fileName="+"TerminalPublicKey.data");  
            ServletOutputStream out;  
            //通过文件路径获得File对象(假如此路径中有一个download.pdf文件)  
            File filepublicKey = new File(publicKeyFilePath); 
            //File filezhaiyao = new File(zhaiyaoFilePath); 
            try {  
                FileInputStream inputStream = new FileInputStream(filepublicKey);  

                //3.通过response获取ServletOutputStream对象(out)  
                out = response.getOutputStream();  

                int b = 0;  
                byte[] buffer = new byte[512];  
                while (b != -1){  
                    b = inputStream.read(buffer);  
                    //4.写到输出流(out)中  
                    out.write(buffer,0,b);  
                }  
                inputStream.close();  
                out.close();  
                out.flush();  

            } catch (IOException e) {  
                e.printStackTrace();  
            }           
        }
}
}
