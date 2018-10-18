package com.bestinfo.tf.read;

import static com.bestinfo.tf.read.AdminUser.FM_PIN_OPER;
import static com.bestinfo.tf.read.Dev.GetDeviceInfo;
import static com.bestinfo.tf.read.Rsa.RSA_DelKeyPair;
import static com.bestinfo.tf.read.Rsa.RSA_EnDecryptTest;
import static com.bestinfo.tf.read.Rsa.RSA_GenKeyPair;
import static com.bestinfo.tf.read.Rsa.RSA_Gen_Import_Export_Test;
import static com.bestinfo.tf.read.Rsa.RSA_SignVerifyTest;
import com.fmkeyjni.key_api;

import java.util.Scanner;

public class JniTestMain{
	public static int FM_OPEN_MULTITHREAD = 0x00000002;
	public static int FM_OPEN_MULTIPROCESS = 0x00000004;
	
	public static void MainMenu() throws Exception{
		Scanner sc = new Scanner(System.in);
	    while (true){
	        System.out.print("\n");
	        System.out.print("         ####################################################\n");
	        System.out.print("         #      1. Device Management                        #\n");
	        System.out.print("         #      2. User Management                          #\n");
	        System.out.print("         #      3. SYM Algorithm                            #\n");
	        System.out.print("         #      4. RSA Algorithm                            #\n");
	        System.out.print("         #      5. ECC Algorithm                            #\n");
	        System.out.print("         #      6. Hash Algorithm                           #\n");
	        System.out.print("         #      7. File Operate                             #\n");
	        System.out.print("         #      8. Cert Operate                             #\n");
	        System.out.print("         #      0. EXIT                                     #\n");
	        System.out.print("         ####################################################\n");
	        System.out.print("\n");

			System.out.print("Please select:");
	        int select = sc.nextInt();

	        if (select == 1){
	        	Dev.DeviceManagement_MainMenu();
	        }
	        else if (select == 2){
	        	User.UserManagement_MainMenu();
	        }
	        else if (select == 3){
	        	Sym.SYM_MainMenu();
	        }
	        else if (select == 4){
	        	Rsa.RSA_MainMenu();
	        }
	        else if (select == 5){
	        	Ecc.ECC_MainMenu();
	        }
	        else if (select == 6){
	        	Hash.HASH_MainMenu();
	        }
	        else if (select == 7){
	        	File.File_MainMenu();
	        }
	        else if (select == 8){
	        	Cert.Cert_MainMenu();
	        }
	        else if (select == 0){
	            return;
	        }
	        else{
	        	System.out.println("select err, please select again!");
	        }
	    }
	}
	
	public static void main(String[] args) throws Exception{
             //System.getProperties().get("java.library.path")
//               System.out.println(System.getProperties().get("java.library.path").toString());
//            
//		int ret;
//		byte[] index = new byte[]{0};
//	
//		ret = key_api.FM_SIC_OpenDevice(index, 0, FM_OPEN_MULTITHREAD|FM_OPEN_MULTIPROCESS, key_api.hDev);
//                System.err.println("ret:"+ret);
//		if(ret != 0){
//			System.out.printf("FM_SIC_OpenDevice err, ret = %02X\n", ret);
//			return;
//		}
//		else{
//			System.out.println("open device succeed");
//		}

		//MainMenu();
                //Dev.DeviceManagement_MainMenu();// 1. Device Management 
                //GetDeviceInfo();
                //User.UserManagement_MainMenu(); //2. User Management  
	          //RSA_GenKeyPair();
                  //RSA_SignVerifyTest();
	          //RSA_DelKeyPair();
	          //RSA_EnDecryptTest();
	          //RSA_SignVerifyTest();
	          //RSA_Gen_Import_Export_Test();

//                Sym.SYM_MainMenu();             //3. SYM Algorithm  
//                Rsa.RSA_MainMenu();             //4. RSA Algorithm  
//                Ecc.ECC_MainMenu();             //5. ECC Algorithm 
//                Hash.HASH_MainMenu();           //6. Hash Algorithm
//                File.File_MainMenu();           //7. File Operate  
//                  Cert.Cert_MainMenu();           //8. Cert Operate
//		ret = key_api.FM_SIC_CloseDevice(key_api.hDev[0]);
//                System.err.println("ret @2:"+ret);
//		if(ret != 0){
//			System.out.printf("FM_SIC_CloseDevice err, ret = %02X\n", ret);
//			return;
//		}
 // TODO add your handling code here:        
        int ret;
        byte[] index = new byte[]{0};
        //开启驱动
        ret = key_api.FM_SIC_OpenDevice(index, 0, FM_OPEN_MULTITHREAD|FM_OPEN_MULTIPROCESS, key_api.hDev);
        System.err.println("ret1:"+ret);
        if(ret != 0){
            System.out.printf("FM_SIC_OpenDevice err, ret = %02X\n", ret);
            return;
        }
        else{
            System.out.println("open device succeed");
        }
        //普通用户登录
        String pin = "12345678";
        int pinLen;
        int[] retrynum = new int[]{0};
        pinLen = pin.length();
        ret = key_api.FM_SIC_USER_Login(key_api.hDev[0], FM_PIN_OPER, pin, pinLen, retrynum);
        if (ret != 0){
            System.out.printf("FM_SIC_USER_Login err, ret = %02X\n", ret);
            return;
        }else{
            System.out.print("succeed\n");
        }
        
//管理员登陆        
//        String pin="11111111";
//        int pinLen;
//        int[] retrynum = new int[]{0};
//        pinLen = pin.length();
//        ret = key_api.FM_SIC_USER_Login(key_api.hDev[0], FM_PIN_ADMIN, pin, pinLen, retrynum);
//        if (ret != 0){
//                System.out.printf("FM_SIC_USER_Login err, ret = %02X, retrynum = %d\n", ret, retrynum);
//            return;
//        }else{
//                System.out.print("succeed\n");
//        }
        
        
        //生成GenRSAKeypair
        byte[] Pubkey = new byte[516];
        byte[] Prikey = new byte[1412];
        byte[] Pubkey2 = new byte[516];
        byte[] Prikey2 = new byte[1412];
        byte[] tmpPubkey = new byte[516];
        byte[] tmpPrikey = new byte[1412];
        int[] keyID = new int[1];
        keyID [0] = 1;
        int keyBits=1024;
        try {
            ret = key_api.FM_SIC_GenRSAKeypair(key_api.hDev[0], keyBits, keyID, Pubkey, Prikey);
            if (ret != 0){
                System.out.printf("FM_SIC_GenRSAKeypair err, ret = %02X\n", ret);
	        return;
	    }else{
                System.err.println("FM_SIC_GenRSAKeypair success");
            }            
        } catch (Exception ex) {
            System.out.println("Exception ex:"+ex);
        }
        
        System.arraycopy(Pubkey, 0, Pubkey2, 0, 516);
        System.arraycopy(Prikey, 0, Prikey2, 0, 1412);
        
        
        
	}
        
}