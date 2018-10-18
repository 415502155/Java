//
//import bestinfo.fsclient.protocols.body.syn.SyncDrawInfoImpl;
//import bestinfo.fsclient.protocols.body.syn.SyncGameInfoImpl;
//import bestinfo.fsclient.protocols.struct.AllMessage;
//import bestinfo.fsclient.protocols.struct.MsgHead;
//import bestinfo.protoClass.body.SyncDrawInfo;
//import com.google.protobuf.ByteString;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
///**
// *
// * @author YangRong
// */
//public class ProbufTest {
//    private static int protocol = 3604;
//    private static String bytesToHexString(byte[] src) {
//        StringBuilder stringBuilder = new StringBuilder("");
//        if (src == null || src.length <= 0) {
//            return null;
//        }
//        for (int i = 0; i < src.length; i++) {
//            int v = src[i] & 0xFF;
//            String hv = Integer.toHexString(v);
//            if (hv.length() < 2) {
//                stringBuilder.append(0);
//            }
//            stringBuilder.append(hv + " ");
//        }
//        return stringBuilder.toString();
//    }
//    static void saveFile(byte[] bm,String fn ){
//        try{
//        FileOutputStream fio = new FileOutputStream(fn);
//        fio.write(bm);
//        fio.close();
//        System.out.println("----------  save file success,filename:"+fn);
//        }catch(Exception e){
//          System.out.println("--------- save file fail,filename:"+fn);  
//        }
//    }
//    static byte[] assembleMsg(){
//        //SyncGameInfoImpl sgi = new SyncGameInfoImpl();
//        //sgi.setGameid(1);
//        SyncDrawInfoImpl sgi = new SyncDrawInfoImpl();
//        sgi.setDrawId(2555);
//        sgi.setGameId(255);
//        MsgHead mhead = new MsgHead();
//         mhead.setPkt_type(20);
////        mhead.setPkt_id(3604);
////        mhead.setMajor_ver_num(10);
////        mhead.setMinor_ver_num(12);
////        mhead.setIdt_type(12);
////        mhead.setIdt_id(12);
////        mhead.setCred_id(12);
////        mhead.setSend_time("20140910111213");
////        mhead.setResp_code(12);
////        mhead.setReserve("r");
////        mhead.setPkt_len(567);
//        byte[] ba = sgi.AssembleMsg(mhead);
//        System.out.println("assemble msg:"+bytesToHexString(ba));
//        return ba;
//        
//    }
//   public static void parseMsgFromFile(String fn){
//       try{
//       FileInputStream fio = new FileInputStream(fn);
//       byte[] bf = new byte[1000];
//       int bl = fio.read(bf);
//       byte[] ba = new byte[bl];
//       System.arraycopy(bf, 0, ba, 0, bl);
//       parseMsg(ba);
//       }catch(Exception e){
//           System.out.println("parse exception");
//       }
//    }
//   public static void parseMsg(byte[] ba){
//       try{
//       AllMessage am = new AllMessage();
//       SyncDrawInfoImpl sgi = new SyncDrawInfoImpl();
//      // SyncGameInfoImpl sgi = new SyncGameInfoImpl();
//       ByteString body =sgi.getMsgObj(am, ba);
//       System.out.println("msg head:"+"   protocol id = "+am.getHeader().getPkt_id()+" major_num = "+am.getHeader().getMajor_ver_num()+
//                  " reserve = "+am.getHeader().getReserve()+" response code="+ am.getHeader().getResp_code()+" idt_type = "+am.getHeader().getIdt_type());
//       System.out.println("begin parse body!");
//       byte[] bds = body.toByteArray();
//       System.out.println("msg body hex:"+bytesToHexString(bds));
//       //SyncGameInfo.SyncGameInfoRequest sgir = SyncGameInfo.SyncGameInfoRequest.parseFrom(body);
//       SyncDrawInfo.SyncDrawInfoRequest sgir = SyncDrawInfo.SyncDrawInfoRequest.parseFrom(body);
//       System.out.println("msg body: game_id = "+sgir.getGameId());
//      System.out.println("msg body: draw_id = "+sgir.getDrawId());
//       }catch(Exception e){
//           System.out.println("parse exception");
//       }
//    }
//   
//   public static void main(String args[]) {
//      
//       byte[] ba = assembleMsg();
//       System.out.println("-------- parse from bytearray -----------");
//       parseMsg(ba);
//       saveFile(ba,"d:/3606.dat");
//       System.out.println("-------- parse from file protofilet -----------");
//       parseMsgFromFile("d:/3606(2).pdat");
//       System.out.println("-------- parse from file 3606.dat -----------");
//       parseMsgFromFile("d:/3606.dat");
//   }
//   
//
//}
