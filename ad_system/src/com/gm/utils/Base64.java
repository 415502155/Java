package com.gm.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
      
/** 
 *      
* @ClassName: Base64  
* @Description: TODO(BASE64加密解密类)  
* @date 2013-2-25 下午4:57:32  
* 
 */  
public class Base64       
{       
      
    /** 
     *               
    * @Title: decryptBASE64  
    * @Description: TODO(Base64解密方法)  
    * @param @param key 
    * @param @return 
    * @param @throws Exception    设定文件  
    * @return byte[]    返回类型  
    * @throws 
     */  
    public static byte[] decryptBASE64(String key) throws Exception {                 
        return (new BASE64Decoder()).decodeBuffer(key);                 
    }                 
                    
    /** 
     *             
    * @Title: encryptBASE64  
    * @Description: TODO(Base64加密方法)  
    * @param @param key 
    * @param @return 
    * @param @throws Exception    设定文件  
    * @return String    返回类型  
    * @throws 
     */  
    public static String encryptBASE64(byte[] key) throws Exception {                 
        return (new BASE64Encoder()).encodeBuffer(key);                 
    }  
      
    public static void main(String[] args) throws Exception       
    {       
        String data = Base64.encryptBASE64("test".getBytes());       
        System.out.println("加密前："+data);  
          
        byte[] byteArray = Base64.decryptBASE64(data);  
        System.out.println("解密后："+new String(byteArray));       
    }       
}      