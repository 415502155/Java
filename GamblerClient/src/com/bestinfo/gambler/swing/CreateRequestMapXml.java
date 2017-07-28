/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.gambler.swing;

import com.bestinfo.bean.encoding.TChargeType;
import com.bestinfo.bean.gambler.TGamblerInfo;
import com.bestinfo.define.filepath.FilePath;
import com.bestinfo.gambler.all.CommTool;
import com.bestinfo.gambler.bet.Bet;
import com.bestinfo.gambler.bet.UserSelect;
import com.bestinfo.gambler.createBetNumber.Count;
import com.bestinfo.protocols.bet.PBetSchemeRequst;
import com.bestinfo.util.FileUtil;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import sun.misc.VMSupport;

/**
 *
 * @author Administrator
 */
public class CreateRequestMapXml {
    static final Logger logger =Logger.getLogger(CreateRequestMapXml.class);
    public static void main(String args[]) {
        try {
            getRequestXml(20000);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(CreateRequestMapXml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static int getRequestXml(int client_num) throws IOException{
        String FilePath ="F:\\request\\request.xml"; 
        int a=FileUtil.deleteFile(FilePath);
        String request1="<pkgC>\n" +
                        "<schemeInfo>\n" +
                        "<dealer_serial>";
        String request2="</dealer_serial>\n" +
                        "<scheme_type>1</scheme_type>\n" +
                        "<scheme_title>testing</scheme_title>\n" +
                        "<secrecy_level>1</secrecy_level>\n" +
                        "<create_time>2017-05-17 14:22:16</create_time>\n" +
                        "<city_id>99</city_id>\n" +
                        "<game_id>1</game_id>\n" +
                        "<draw_id>133</draw_id>\n" +
                        "<keno_draw_id>0</keno_draw_id>\n" +
                        "<play_id>1</play_id>\n" +
                        "<bet_method>0</bet_method>\n" +
                        "<bet_mode>1</bet_mode>\n" +
                        "<bet_multiple>1</bet_multiple>\n" +
                        "<bet_money>2.0</bet_money>\n" +
                        "<betInfo num=\"7\" group=\"2\">\n" +
                        "<bet_line>01060102030405060103##</bet_line>\n" +
                        "</betInfo>\n" +
                        "</schemeInfo>\n" +
                        "<gamblerInfo>\n" +
                        "<gambler_name>test</gambler_name>\n" +
                        "<gambler_id_no>232103199812275889</gambler_id_no>\n" +
                        "<chargeInfo isSettled=\"1\"><charge_id>1</charge_id><account_type>13</account_type></chargeInfo></gamblerInfo>\n" +
                        "</pkgC>";
        int serial =321000;
        StringBuffer sb = new StringBuffer();
        for (int index = 0; index < client_num; index++) { //模拟多个客户端   
            serial++;                               
            String dealer_serial ="14968030"+serial;//14938030960985 
            String request =request1+dealer_serial+request2+",";
            sb.append(request);
        }
        String allRequest=sb.substring(0, sb.length()-1);
        FileOutputStream out = null;        
        FileOutputStream outpublickey = null;
        try {
            outpublickey = new FileOutputStream(FileUtil.makeNewFile(FilePath)); // 输出公钥记录文件路径  
            outpublickey.write(allRequest.getBytes());  
            outpublickey.close();
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreateRequestMapXml.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }
        
}
