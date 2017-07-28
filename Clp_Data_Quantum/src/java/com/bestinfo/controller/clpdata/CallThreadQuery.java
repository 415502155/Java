package com.bestinfo.controller.clpdata;
/**
 *
 * @author Administrator
 */
public class CallThreadQuery {
    public static void call(Integer game_id,Integer draw_id){
        game_id=5;
        draw_id=30;
        ReadWriteFile.delFile1();
        TransmissionThread tt = new TransmissionThread();
        tt.setDrawId(draw_id);
        tt.setGameId(game_id);
        Thread t = new Thread(tt); 
        t.start();
       
    }
//    private static StringBuffer sb ;
    public static String receiveStr(String str){  
//        System.out.println(str); 
//       sb=new StringBuffer();
//        sb.append(str);
//        System.out.println("sb："+sb);
        return str;
    } 
}
