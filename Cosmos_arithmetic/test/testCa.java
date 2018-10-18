//
//import bestinfo.protocols.arithmetic.CaTool;
//import bestinfo.protocols.arithmetic.Crypt3;
//import bestinfo.protocols.arithmetic.DataChange;
//import java.io.FileInputStream;
//import java.security.PrivateKey;
//import java.security.PublicKey;
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
//public class testCa {
//    public static void main(String[] args) {
//        try{
//         
//        CaTool ct = new CaTool();
////        FileInputStream fis = new FileInputStream("f:/dlt.p12");
////        String pems=ct.privatekeyToPemString("f:/dlt.p12", "ipcc@95598","root","ipcc@95598" );
////        System.out.print(pems);
//        
//        
////        PublicKey pk = ct.getPublicKey("f:/dlt.p12", "ipcc@95598", "root");
////        PrivateKey prk = ct.getPrivateKey("f:/dlt.p12", "ipcc@95598", "root", "ipcc@95598");
////        String s = "abcdefg12345";
////        byte[] src=s.getBytes();
////        byte[] src_enc = ct.encryptByPublicKey(src, pk);
////        byte[] src_dec = ct.decryptByPrivateKey(src_enc, prk);
////        String s_dec = new String(src_dec);
////        System.out.println("原字符串:"+s );
////        System.out.println("公钥加密私钥解密后得到的字符串:"+s_dec);
////        src_enc = ct.encryptByPrivateKey(src, prk);
////        src_dec = ct.decryptByPublicKey(src_enc, pk);
////        String s_dec2 = new String(src_dec);
////        System.out.println("私钥加密公钥解密后得到的字符串:"+s_dec2);
////        
////        byte[] sign = ct.sign(src, prk, "MD5withRSA");
////        boolean vf = ct.verify(src, sign, pk, "MD5withRSA");
////        System.out.println("签名验证结果:"+vf);
//        
//        //1024bit密钥
//        PublicKey pk = ct.getPublicKey("f:/cash.p12", "119745", "cosmos");
//        PrivateKey prk = ct.getPrivateKey("f:/cash.p12", "119745", "cosmos", "119745");
//        String s =  new Crypt3().RandomString(117);
//        System.out.println("length:"+s.length());
//
//        byte[] src=s.getBytes();
//        byte[] src_enc = ct.encryptByPublicKey(src, pk);//rsa加密 ,明文长度不能超过117字节,若>117,返回null
//        byte[] src_dec = ct.decryptByPrivateKey(src_enc, prk);
//        String s_dec = new String(src_dec);
//        System.out.println("原字符串:"+s );
//        System.out.println("公钥加密私钥解密后得到的字符串:"+s_dec);
//        src_enc = ct.encryptByPrivateKey(src, prk);
//        System.out.println("encrypt string:"+new DataChange().Bytes2HexString(src_enc));
//        System.out.print(ct.publickeyToPemString(pk));
//        src_dec = ct.decryptByPublicKey(src_enc, pk);
//        String s_dec2 = new String(src_dec);
//        System.out.println("私钥加密公钥解密后得到的字符串:"+s_dec2);
//        src_dec = ct.decryptByPublicKey(new DataChange().HexString2Bytes( new DataChange().Bytes2HexString(src_enc)),ct.loadPublicKeyFromPem(ct.publickeyToPemString(pk)));
//        s_dec2 = new String(src_dec);
//        System.out.println("私钥加密,公钥转化成pem string, 再读出 , 解密后得到的字符串:"+s_dec2);
//        
//        ct.savePublickeyToPemFile(pk,"f:/pk.pem");
//        PublicKey pkf = ct.loadPublicKeyFromPemFile("f:/pk.pem");
//        src_dec = ct.decryptByPublicKey(src_enc, pkf);
//        s_dec2 = new String(src_dec);
//        System.out.println("私钥加密 读pem文件公钥解密后得到的字符串:"+s_dec2);
//        
//        byte[] sign = ct.sign(src, prk, "MD5withRSA");//不受输入串长度影响
//        boolean vf = ct.verify(src, sign, pk, "MD5withRSA");
//        System.out.println("签名验证结果:"+vf);
//        
//        String log = new Crypt3().RandomString(64);
//        String signLog = ct.logSign(log, prk);
//        int signLogLen  = signLog.length();
//        System.out.println("log string:"+log);
//        System.out.println("sign log string:"+signLog);
//        boolean logVerify = ct.logVerify(log, signLog, pk);
//        System.out.println("sign verify result:"+logVerify);
//        log = log.substring(1);
//        logVerify = ct.logVerify(log, signLog, pk);
//        System.out.println("去掉log第一个字符后 sign verify result:"+logVerify);
//         
//        
//      //  System.out.print(pems);
//        FileInputStream fis2 = new FileInputStream("f:/dlt2.p12");
//        String pems2=ct.publickeyToPemString(fis2, "ipcc@95598","p12client" );
////        String pems3=ct.privatekeyToPemString("f:/dlt2.p12", "ipcc@95598","p12client","ipcc@95598");
////        System.out.print(pems3);
//        System.out.print(pems2);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//}
