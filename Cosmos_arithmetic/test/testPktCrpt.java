//
//import bestinfo.protocols.arithmetic.Crypt3;
//import bestinfo.protocols.arithmetic.EncrypDES3;
//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
//import javax.crypto.SecretKey;
//import org.apache.commons.codec.binary.Hex;
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
//public class testPktCrpt {
//    public static void main(String[] args) {
//         
//         try{
//        EncrypDES3 eds = new EncrypDES3();
// 
//        String ke15="57874471407a87af928199595d070f5b";
////        ke15=ke15+ke15.substring(0,16);
//        String dcsss= "BC4E5AC06FE4D81408A0834B613648EA93C4B17C4B3DCE33B28C5E9F061EC8723567713FFED3AB019F63B34771E3AD43C40AA43C50E13435307C42C1309EAD554B5A98372C8EDAF7102EA725504F35792703FB61AC672B24111AB9852F94C61651D3BBE43F2A4F7B9BB9E1E70696AE0F45C6FFBE82CB2466D3480A24B30956C613D468A2A095A592E75A9D55B2F65E73276DF3ED73A1650802C0034781AB79ED4EE96ED5C68753C5F95E3A67B7D36E4CAE8DCCB667E878CD";
//        byte[] sk=Hex.decodeHex(ke15.toCharArray());
//        byte[] buff= Hex.decodeHex(dcsss.toCharArray());
//        byte[] dd23=eds.Decryptor(buff, sk);
//        String ss= new String(dd23);
//        int j=0;
//         }catch(Exception e){}
//        
//        SecretKey sessionKey1 = new EncrypDES3().genSessionKey();
//        byte[] sessionKey = sessionKey1.getEncoded();
//        String sk64=Base64.encode(sessionKey);
//        String sealog= sessionKey1.getAlgorithm();
//        
//        String msgBright = new Crypt3().RandomString(100);
//        String randomKey = "12345678901234567890123456789012";//32字节
//        
//        byte[] encryptmsg1=new EncrypDES3().Encrytor(randomKey.getBytes(), sessionKey1);
//        byte[] encryptmsg2=new EncrypDES3().Encrytor(randomKey.getBytes(), sessionKey);
//        
//        String decrypmsg1= new String(new EncrypDES3().Decryptor(encryptmsg1, sessionKey));
//        String decrypmsg2= new String(new EncrypDES3().Decryptor(encryptmsg2,0,encryptmsg2.length, sessionKey1));
//        
//        
//        Crypt3 crypt3 = new Crypt3();
//        byte[] encryptMsg = crypt3.pkt_encrypt_test(msgBright.getBytes(), randomKey.getBytes(), sessionKey);
//        
//        byte[] decryptMsg = crypt3.pkt_decrypt(encryptMsg, sessionKey);
//        
//        String randomKeyBright = new String(crypt3.getMsgKeyBright());
//        System.out.println("msgKeyBright:"+randomKeyBright);
//        
//        String decryptMsgS = new String(decryptMsg);
//        System.out.println("source string:"+msgBright);
//        System.out.println("decrpt string:"+decryptMsgS);
//    }
//}
