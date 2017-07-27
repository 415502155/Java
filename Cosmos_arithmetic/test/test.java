//
//import bestinfo.protocols.arithmetic.Crypt3;
//import bestinfo.protocols.arithmetic.Crypt3.CipherMeltInfo;
//import java.io.File;
//import java.util.Date;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
///**
// *
// * @author YangRong
// */
//public class test {
//
//    public static void main(String[] args) {
//
//        Crypt3 crypt = new Crypt3();
//        int mn = 0, en = 0;
//        int tn = 1;
//        String es = "";
//        for (int j = 0; j < tn; j++) {
//            Date now;
////            Calendar cal = Calendar.getInstance();
//
////            cal.set(Calendar.YEAR, 14);
////            cal.set(Calendar.MONTH, 9);
////            cal.set(Calendar.DAY_OF_MONTH, 1 + j % 30);
////            cal.set(Calendar.HOUR_OF_DAY, j % 24);
////            cal.set(Calendar.MINUTE, j % 60);
////            cal.set(Calendar.SECOND, j % 60);
////            cal.set(Calendar.MILLISECOND, 0);
////           now = cal.getTime();
//            now=new Date();
////            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////            try {
////                now = s.parse("2001-12-31 00:00:00");
////            } catch (Exception e) {
////                return;
////            }
//           
//            
//            int[] cipher = crypt.make_ticket_cipher(44399999, j % 32, j, j * 5, now);
//
//            CipherMeltInfo cmi = crypt.melt_ticket_cipher(cipher);
//            if (cmi.getGame_id() == 32) {
//                cmi.setGame_id(0);
//            }
//
//            if (cmi == null || cmi.getGame_id() != j % 32 || cmi.getNode() != 399999 || cmi.getSeq_no() != j * 5
//                    || cmi.getPeriod() != j || cmi.getTm_time().getSeconds() != now.getSeconds()
//                    || cmi.getTm_time().getMinutes() != now.getMinutes() || cmi.getTm_time().getHours() != now.getHours()) {
//                
//                System.out.println("fail to make and melt cipher ");
//                mn++;
//            }
//            int d = 3;
//            es = crypt.encrypt_ticket_cipher(0, "ABCDEFG", cipher);
//            
//            int[] cipher2 = crypt.decrypt_ticket_cipher("ABCDEFG", es);
//            if (cipher[0] != cipher2[0] || cipher[1] != cipher2[1] || cipher[2] != cipher2[2] || cipher[3] != cipher2[3]) {
//                System.out.println("fail to encry and decrypt cipher");
//                en++;
//            }
//        }
//        System.out.println("ticket cipher:"+es);
//        System.out.println("test num:" + tn + " make fail num:" + mn + " cryp fail num:" + en);
//        //long hsh = crypt.fnv_64_buf(es.getBytes(), 0);
////        es="1452755b325bea698169b9486c2fed5adaa4e15e4c37ff4c40c0c42c7d2d9caa334f9e0d97c876430a17d144e0fb74f94fd4de6d1c8d08d1325dca1bb2ca76ff0d1452755b325bea698169b9486c2fed5adaa4";
//        byte[] f = es.getBytes();
//        long hsh1 = crypt.fnv_64a_buf(es.getBytes(), 0);
//        long hsh2= crypt.fnv_64_buf(es.getBytes(), 0);
//        System.out.println("encryp string:" + es);
//        System.out.println("64 hash value:" + Long.toHexString(hsh2));
//        System.out.println("64a hash value:" + Long.toHexString(hsh1));
//    
//        
//        
//        //System.out.println("llll:"+Long.toHexString(hsh2));
//
//    }
//}
