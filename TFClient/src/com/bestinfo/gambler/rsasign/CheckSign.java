package com.bestinfo.gambler.rsasign;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import sun.misc.BASE64Encoder;
/**
 * 校验签名内容
 * @author Administrator
 */
public class CheckSign {
    private static String hexString = "0123456789ABCDEF";
    public static void main(String args[]) throws Exception {
         CheckSignFile();
    }
    /*
    * 将16进制数字解码成字符串,适用于所有字符（包括中文）
    */
   public static String decode(String bytes) {
       ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
       // 将每2位16进制整数组装成一个字节
       for (int i = 0; i < bytes.length(); i += 2)
       baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
                       .indexOf(bytes.charAt(i + 1))));
       return new String(baos.toByteArray());
   }
    public static PublicKey geneneratePublicKey(byte[] key) throws InvalidKeySpecException, NoSuchAlgorithmException{  
        KeySpec keySpec = new X509EncodedKeySpec(key);  
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
        return keyFactory.generatePublic(keySpec);  
    }  
    // 使用N、e值还原公钥
    public static PublicKey getPublicKey(String modulus, String
    publicExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        BigInteger bigIntModulus = new BigInteger(modulus,16);
        BigInteger bigIntPrivateExponent = new BigInteger(publicExponent,16);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus,
    bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
    /** 
     * 十六进制的字符串转换为byte数组 
     * **/  
    public static byte[] conver16HexToByte(String hex16Str)  
    {  
        char [] c = hex16Str.toCharArray();  
        byte [] b = new byte[c.length/2];  
        for(int i = 0;i<b.length;i++)  
        {  
            int pos = i * 2;  
            b[i] = (byte)("0123456789ABCDEF".indexOf(c[pos]) << 4 | "0123456789ABCDEF".indexOf(c[pos+1]));  
        }  
        return b;  
    } 
    public static String CheckSignFile() throws Exception{
        String n="F4 87 6A DC C1 2D F0 FA 21 40 84 A4 C6 47 AF CC 9F ED 65 05 C6 5A B9 2A 8E 2D 01 59 7C 4D 98 17 3F 69 1B 85 12 BD AB 8C F3 49 EC 52 C5 77 D5 20 19 56 61 52 39 9F 6C 51 23 30 99 EC 9D D7 5C 73 1D 52 E5 BC 5E D2 FD 7F C3 B7 AD 2A 05 B1 63 D1 FE 81 1A 5F 0A 0B 7D 11 88 D0 26 FA 81 90 A3 C2 DE 40 AE A9 3A A5 81 50 A7 6D EC BB 9A 9C 98 38 FB 43 DC 2E C3 4A 32 10 24 23 98 20 2D D3 0D 91";        
        String e="00 01 00 01";
        n = n.replaceAll(" ", "");
        e = e.replaceAll(" ", "");
        System.err.println("nnnnn:"+n);
        //byte [] bn=conver16HexToByte(n);
        //byte [] be=conver16HexToByte(e);
        //n =decode(n);//将16进制数字解码成字符串,获取签名内容
        //e =decode(e);//将16进制数字解码成字符串,获取签名内容
        //n=new String(bn);
        //e=new String(be);
        System.err.println("n:"+n+"e:"+e);
        PublicKey publickey=getPublicKey(n,e);
        byte[] keyBytes = publickey.getEncoded();
        String gyzfc = (new BASE64Encoder()).encode(keyBytes);
        System.err.println("pk str:"+gyzfc);
        String sign=" 1D 15 E5 A0 54 5A A4 5E 28 2C 4D 46 57 83 4E 32 2D 58 FB F0 05 85 02 A8 DB 23 B5 EE 19 7B FB EB ED 4A A8 E1 74 E5 9D 14 C8 7F CF D3 E4 A6 1A 3D 66 96 68 47 81 D1 5D F5 82 AB 0B 8E DD A2 06 E1 10 22 FB EC 4E 87 EA 89 D0 14 71 26 1E A8 25 1A 46 73 EB 9B C0 DC 67 2B C9 7B C4 F4 EE 29 0B 4A A4 6A ED 84 2A 96 B0 3A F9 A8 A4 A4 60 EF 59 49 F3 A8 DE C5 58 2C 1C C8 59 FC AB 7A EE C2 15 9B";
        sign = sign.replaceAll(" ", "");
        byte[] bsign=conver16HexToByte(sign);
        String subsign="";
        System.err.println("ssss:"+bsign);
        byte[] decodedData=null;
        try {
            decodedData =RSAUtils.decryptByPublicKey(bsign,gyzfc);//decryptByPublicKey1(bsign, pk);
            int aI=decodedData.length;
            System.err.println("aI:"+aI);
            String jm=bytesToHexString(decodedData);
            System.err.println("jm length:"+jm.length());
            subsign=jm.substring(jm.length()-40, jm.length());
            System.err.println("jm:"+jm+"</br>"+"/subsign:"+subsign);
        } catch (Exception ex) {            
            System.out.println("eeeeee:"+ex);
        }
        String str="abcdef0123456789";
        String shastr=SHA1.getSha1(str);
        byte []a16=conver16HexToByte(shastr);
        System.err.println("wwww:"+shastr);
        System.err.println("a16:"+new String(a16));
        return "success";
    }    
    public static String bytesToHexString(byte[] src){   
    StringBuilder stringBuilder = new StringBuilder("");   
    if (src == null || src.length <= 0) {   
        return null;   
    }   
    for (int i = 0; i < src.length; i++) {   
        int v = src[i] & 0xFF;   
        String hv = Integer.toHexString(v);   
        if (hv.length() < 2) {   
            stringBuilder.append(0);   
        }   
        stringBuilder.append(hv);   
    }   
    return stringBuilder.toString();   
}   
}
