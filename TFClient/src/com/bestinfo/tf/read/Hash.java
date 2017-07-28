package com.bestinfo.tf.read;

import com.fmkeyjni.key_api;

import java.util.Arrays;
import java.util.Scanner;

public class Hash{
	public static int TEST_HASH_DATALEN = 28 * 1024;
	
	public static int SM3_Test()throws Exception{
	    int ret;
	    byte[] plainText = new byte[TEST_HASH_DATALEN];
	    int plainTextLen = TEST_HASH_DATALEN;

	    byte[] eccPubkey = new byte[]{
	        0x00,0x01,0x00,0x00,(byte)0xDB,(byte)0xB0,(byte)0xF8,0x41,0x77,(byte)0xA0,0x4F,0x03,(byte)0xCD,
	        0x05,0x3A,(byte)0xC2,0x69,0x5A,0x70,0x42,(byte)0x8C,(byte)0xAF,(byte)0xEC,0x2B,0x20,(byte)0x8A,
	        0x52,(byte)0x80,(byte)0x86,(byte)0xFA,0x44,0x49,0x47,(byte)0xA7,(byte)0x86,0x4A,(byte)0xAE,0x4E,0x74,
	        0x69,0x69,(byte)0xB0,(byte)0xD8,(byte)0xC6,(byte)0xE4,0x4A,(byte)0xE4,0x67,(byte)0xDC,(byte)0xB7,(byte)0xB0,(byte)0xC2,
	        0x65,(byte)0x82,0x36,0x1E,(byte)0xD1,0x2C,(byte)0xD3,0x45,(byte)0xC2,(byte)0x87,(byte)0xC6,(byte)0xD7,0x6C,
	        (byte)0x81,0x7C,(byte)0x84
		};

	    byte[] ID = new byte[]{
	        0x41,0x4C,0x49,0x43,0x45,0x31,0x32,0x33,0x40,0x59,0x41,0x48,0x4F,
	        0x4F,0x2E,0x43,0x4F,0x4D
	    };
        byte[] hashResult = new byte[32];
        int[] resultLen = new int[1];

	    byte[] standardResult = new byte[]{
	        0x23,(byte)0xd9,(byte)0xda,(byte)0xec,(byte)0xf2,0x59,(byte)0x86,(byte)0xfc,(byte)0x97,0x28,0x36,(byte)0x96,0x38,
	        (byte)0x8e,(byte)0xa4,(byte)0xfd,(byte)0x9c,(byte)0x84,0x59,(byte)0x83,(byte)0xe2,0x4c,(byte)0xef,0x4e,(byte)0x92,(byte)0xbb,
	        0x78,0x4a,(byte)0xc3,(byte)0xc8,0x39,(byte)0xb7
	    };
	    
	    ret = key_api.FM_SIC_SM3Init(key_api.hDev[0], eccPubkey, ID, 18);
		if (ret != 0){
			System.out.printf("FM_SIC_SM3Init err, ret = %02X\n", ret);
	        return 1;
	    }
		
		ret = key_api.FM_SIC_SM3Update(key_api.hDev[0], plainText, plainTextLen);
		if (ret != 0){
			System.out.printf("FM_SIC_SM3Update err, ret = %02X\n", ret);
	        return 1;
	    }

	    //Note: u32ResultLen must be assigned to 20, 24 or 32
	    resultLen[0] = 32;
	    ret = key_api.FM_SIC_SM3Final(key_api.hDev[0], hashResult, resultLen);
		if (ret != 0){
			System.out.printf("FM_SIC_SM3Final err, ret = %02X\n", ret);
	        return 1;
	    }

	    //Compare result
	  	if ((Arrays.equals(hashResult, standardResult) == true)){
	        System.out.print("test OK!");
	    }

		return 0;
	}
	
	public static int SHA1_Test()throws Exception{
	    int ret;
	    byte[] plainText = new byte[TEST_HASH_DATALEN];
	    byte[] standardResult = new byte[]{
	            (byte)0xb4, 0x4c, (byte)0xcc, 0x7f,
	            0x7d, 0x51, (byte)0x93, 0x52,
	            0x42, 0x2e, 0x59, (byte)0xee,
	            (byte)0x8b, 0x0b, (byte)0xdb, (byte)0xac,
	            (byte)0x88, 0x17, 0x68, (byte)0xa7
	        };

	    //��ʼ��
  		ret = key_api.FM_SIC_HashInit(key_api.hDev[0], key_api.FM_ALG_SHA1, null, 0);	
  		if(ret != 0){
  			System.out.printf("FM_SIC_HashInit is error, ret = %02X\n", ret);
  			return 1;
  		}
  		
  		//Update
  		ret = key_api.FM_SIC_HashUpdate(key_api.hDev[0], key_api.FM_ALG_SHA1, plainText, plainText.length);
  		if(ret != 0){
  			System.out.printf("FM_SIC_HashUpdate is error, ret = %02X\n", ret);
  			return 1;
  		}
     		
 		//Final
 		byte[] res = new byte[20];
 		int[] hashlen = new int[1];
 		hashlen[0] = 20;
 		ret = key_api.FM_SIC_HashFinal(key_api.hDev[0], key_api.FM_ALG_SHA1, res, hashlen);
 		if (ret != 0)  {
 			System.out.printf("FM_SIC_HashFinal is error, ret = %02X\n", ret);
 			return 1;
 		}

	    //Compare result
	  	if ((Arrays.equals(res, standardResult) != true)){
	        return 1;
	    }

		return 0;
	}

	public static void HASH_MainMenu()throws Exception{
	    int ret;
	    Scanner sc = new Scanner(System.in);

	    while (true){
	        System.out.print("\n");
	        System.out.print("     ####################################################\n");
	        System.out.print("     #      1. SM3 Test                                 #\n");
	        System.out.print("     #      2. SHA1 Test                                #\n");
	        System.out.print("     #      0. Return                                   #\n");
	        System.out.print("     ####################################################\n");
	        System.out.print("\n");

	        System.out.print("Please select:");
	        int select = sc.nextInt();

	        if (select == 1){
				ret = SM3_Test();
	            if (ret != 0){
	                System.out.print("Test error!\n");
	            }
	            else{
	            	System.out.print("Test OK!\n");
	            }
	        }
	        else if (select == 2){
				ret = SHA1_Test();
	            if (ret != 0){
	                System.out.print("Test error!\n");
	            }
	            else{
	            	System.out.print("Test OK!\n");
	            }
	        }
	        else if (select == 0){
	            return;
	        }
	        else{
	        	System.out.println("select err, please select again!");
	        }
	    }
	}

}