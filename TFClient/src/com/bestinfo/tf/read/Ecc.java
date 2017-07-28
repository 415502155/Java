package com.bestinfo.tf.read;

import com.fmkeyjni.key_api;

import java.util.Arrays;
import java.util.Scanner;

public class Ecc{
	public static int TEST_ECC_NUM = 128;
	public static int FM_ALG_SM2_1 = 0x00000003;
	public static int FM_HKEY_TO_HOST = 0x01ffffff;
	public static int FM_ALG_SM1 = 0x00000002;
	public static int FM_ALGMODE_ECB = 0x00000000;
	
	public static void ECC_GenKeyPair()throws Exception{
	    int ret;
	    int[] keyID = new int[1];
	    int[] tmpKeyID = new int[1];
	    int alg;
	    byte[] eccPubkey = new byte[68];
	    byte[] eccPrikey = new byte[36];
	    Scanner sc = new Scanner(System.in);
	    
	    System.out.printf("Please input ECC keyID(0~%d):", TEST_ECC_NUM - 1);
	    keyID[0] = sc.nextInt();
	    if (keyID[0] < 0 || keyID[0] >= TEST_ECC_NUM){
	        System.out.print("ECC keyID out of range!\n");
	        return;
	    }

	    alg = FM_ALG_SM2_1;

	    tmpKeyID[0] = FM_HKEY_TO_HOST;
	    ret = key_api.FM_SIC_GenECCKeypair(key_api.hDev[0], alg, tmpKeyID, eccPubkey, eccPrikey);
		if (ret != 0){
			System.out.printf("FM_SIC_GenECCKeypair err, ret = %02X\n", ret);
	        return;
	    }
		
	    ret = key_api.FM_SIC_ImportECCKeypair(key_api.hDev[0], keyID, eccPubkey, eccPrikey);
		if (ret != 0){
			System.out.printf("FM_SIC_ImportECCKeypair err, ret = %02X\n", ret);
	        return;
	    }

	    System.out.print("Generate ECC keypair OK!\n");
	}

	public static void ECC_DelKeyPair()throws Exception{
	    int ret;
	    int keyID;
	    Scanner sc = new Scanner(System.in);
	    
	    System.out.printf("Please input keyID(0~%d):", TEST_ECC_NUM - 1);
        keyID = sc.nextInt();
	    if (keyID < 0 || keyID >= TEST_ECC_NUM){
	        System.out.printf("ECC keyID out of range!\n");
	        return;
	    }

	    ret = key_api.FM_SIC_DelECCKeypair(key_api.hDev[0], keyID);
		if (ret != 0){
			System.out.printf("FM_SIC_DelECCKeypair err, ret = %02X\n", ret);
	        return;
	    }
		else{
			System.out.println("success!");
		}
	}
	
	public static void ECC_EnDecryptTest()throws Exception{
	    int i;
		int ret;
	    int keyID;
	    int inLen;
	    int[] outLen = new int[1];
	    byte[] inBuf = new byte[128];
	    byte[] cipher = new byte[260];
	    byte[] outBuf = new byte[128];
	    Scanner sc = new Scanner(System.in);

	    System.out.printf("Please input ECC keyID(0~%d):", TEST_ECC_NUM - 1);
	    keyID = sc.nextInt();
	    if (keyID < 0 || keyID >= TEST_ECC_NUM){
	        System.out.print("ECC keyID out of range\n");
	        return;
	    }

	    //Generate input data
	    for(i = 0; i < 128; i++){
	        inBuf[i] = (byte)(Math.random()*255);
	    }
	    inLen = 128;

	    //Encrypt
	    ret = key_api.FM_SIC_ECCEncrypt(key_api.hDev[0], FM_ALG_SM2_1, keyID, inBuf, inLen, null, cipher);
		if (ret != 0){
			System.out.printf("FM_SIC_ECCEncrypt err, ret = %02X\n", ret);
	        return;
	    }

	    //Decrypt
		ret = key_api.FM_SIC_ECCDecrypt(key_api.hDev[0], FM_ALG_SM2_1, keyID, cipher, null, outBuf, outLen);
		if (ret != 0){
			System.out.printf("FM_SIC_ECCDecrypt err, ret = %02X\n", ret);
	        return;
	    }

	    //Compare result
	    if (Arrays.equals(inBuf, outBuf) == true){
	        System.out.printf("Test OK!\n");
	    }
	    else {
	    	System.out.print("Test error, encrypt and decrypt result mismatch!\n");
	    }
	    	
	}

	public static void ECC_SignVerifyTest()throws Exception{
	    int ret;
	    int keyID;
        byte[] inBuf = new byte[32];
	    int inLen;
	    byte[] signature = new byte[64];
	    int i;
	    Scanner sc = new Scanner(System.in);

	    System.out.printf("Please input ECC keyID(0~%d):", TEST_ECC_NUM - 1);
	    keyID = sc.nextInt();
	    if (keyID < 0 || keyID >= TEST_ECC_NUM){
	        System.out.print("ECC keyID out of range\n");
	        return;
	    }

	    //Generate input data
	    for (i = 0; i < 32; i++){
	        inBuf[i] = (byte)(Math.random()*255);
	    }
	    inLen = 32;

	    //Sign
	    ret = key_api.FM_SIC_ECCSign(key_api.hDev[0], FM_ALG_SM2_1, keyID, inBuf, inLen, null, signature);
		if (ret != 0){
			System.out.printf("FM_SIC_ECCSign err, ret = %02X\n", ret);
	        return;
	    }

	    //Verify
		ret = key_api.FM_SIC_ECCVerify(key_api.hDev[0], FM_ALG_SM2_1, keyID, null, inBuf, inLen, signature);
		if (ret != 0){
			System.out.printf("FM_SIC_ECCVerify err, ret = %02X\n", ret);
	        return;
	    }
	    else{
	    	System.out.print("Test OK!\n");
	    }
	}
	
	public static void ECC_AgreementDataTest()throws Exception{
	    int ret;
	    int keyID;
	    int[] tmpKeyID = new int[1];
	    byte[] sponsorPubkey = new byte[68];
	    byte[] sponsorTmpPubkey = new byte[68];
	    byte[] responsorPubkey = new byte[68];
	    byte[] responsorTmpPubkey = new byte[68];
	    byte[] IDA = new byte[32];
	    byte[] IDB = new byte[32];
	    int[] hKeySponsor = new int[1];
	    int[] hKeyResponser = new int[1];

	    byte[] inBuf = new byte[16];
	    int inBufLen;
	    byte[] cipher = new byte[16];
	    int[] cipherLen = new int[1];
	    byte[] plainText = new byte[16];
	    int[] plainTextLen = new int[1];
		int[] hAgreement = new int[1];
	    int i;
	    IDB[0] = 1;

	    tmpKeyID[0] = 0;
	    ret = key_api.FM_SIC_GenECCKeypair(key_api.hDev[0], FM_ALG_SM2_1, tmpKeyID, null, null);
		if (ret != 0){
			System.out.printf("FM_SIC_GenECCKeypair err, ret = %02X\n", ret);
	        return;
	    }

		tmpKeyID[0] = 1;
		ret = key_api.FM_SIC_GenECCKeypair(key_api.hDev[0], FM_ALG_SM2_1, tmpKeyID, null, null);
		if (ret != 0){
			System.out.printf("FM_SIC_GenECCKeypair err, ret = %02X\n", ret);
	        return;
	    }

	    keyID = 0;
	    ret = key_api.FM_SIC_GenerateAgreementDataWithECC(key_api.hDev[0],  0, keyID, 32, IDA, 32, sponsorPubkey,
	    		sponsorTmpPubkey, hAgreement);
		if (ret != 0){
			System.out.printf("FM_SIC_GenerateAgreementDataWithECC err, ret = %02X\n", ret);
	        return;
	    }

		keyID = 0;
		ret = key_api.FM_SIC_GenerateAgreementDataAndKeyWithECC(key_api.hDev[0], 0, keyID, 32, IDB, 32, IDA, 32, 
				sponsorPubkey, sponsorTmpPubkey, responsorPubkey, responsorTmpPubkey, hKeyResponser);
		if (ret != 0){
			System.out.printf("FM_SIC_GenerateAgreementDataAndKeyWithECC err, ret = %02X\n", ret);
	        return;
	    }
		
		ret = key_api.FM_SIC_GenerateKeyWithECC(key_api.hDev[0], IDB, 32,
				responsorPubkey, responsorTmpPubkey, hAgreement[0], hKeySponsor);
		if (ret != 0){
			System.out.printf("FM_SIC_GenerateKeyWithECC err, ret = %02X\n", ret);
	        return;
	    }

	    for (i = 0; i < 16; i++)
	    {
	        inBuf[i] = (byte)(Math.random()*255);
	    }
	    inBufLen = 16;

	    ret = key_api.FM_SIC_Encrypt(key_api.hDev[0], hKeySponsor[0], FM_ALG_SM1, FM_ALGMODE_ECB, inBuf, inBufLen, cipher,
		       cipherLen, null, 0, null, 0);
		if (ret != 0){
			System.out.printf("FM_SIC_Encrypt err, ret = %02X\n", ret);
	        return;
	    }
		
		ret = key_api.FM_SIC_Decrypt(key_api.hDev[0], hKeySponsor[0], FM_ALG_SM1, FM_ALGMODE_ECB, cipher, cipherLen[0],
		        plainText, plainTextLen, null, 0, null, 0);
		if (ret != 0){
			System.out.printf("FM_SIC_Decrypt err, ret = %02X\n", ret);
	        return;
	    }

	    //Compare result
	    if (Arrays.equals(inBuf, plainText) == true){
	        System.out.print("Test OK!\n");
	    }
	    else{
	    	System.out.print("Test error!\n");
	    }    
	}
	
	public static void ECC_Gen_Import_Export_Test()throws Exception{
	    int ret;
		byte[] eccPubkey = new byte[68];
		byte[] eccPrikey = new byte[36];
		byte[] tmpEccPubkey = new byte[68];
		byte[] tmpEccPrikey = new byte[36];
		int[] keyID = new int[1];
		keyID[0] = FM_HKEY_TO_HOST;
		
		ret = key_api.FM_SIC_GenECCKeypair(key_api.hDev[0], FM_ALG_SM2_1, keyID, eccPubkey, eccPrikey);
		if (ret != 0){
			System.out.printf("FM_SIC_GenECCKeypair err, ret = %02X\n", ret);
	        return;
	    }
		
		keyID[0] = 0;
	    ret = key_api.FM_SIC_ImportECCKeypair(key_api.hDev[0], keyID, eccPubkey, eccPrikey);
		if (ret != 0){
			System.out.printf("FM_SIC_ImportECCKeypair err, ret = %02X\n", ret);
	        return;
	    }
		
		ret = key_api.FM_SIC_ExportECCKeypair(key_api.hDev[0], keyID[0], tmpEccPubkey, tmpEccPrikey);
		if (ret != 0){
			System.out.printf("FM_SIC_ExportECCKeypair err, ret = %02X\n", ret);
	        return;
	    }
		
	  	if ((Arrays.equals(eccPubkey, tmpEccPubkey) != true) || (Arrays.equals(eccPrikey, tmpEccPrikey) != true))
	    {
	        System.out.print("test err!");
	        return;
	    }

	    ret = key_api.FM_SIC_DelECCKeypair(key_api.hDev[0], keyID[0]);
		if (ret != 0){
			System.out.printf("FM_SIC_DelECCKeypair err, ret = %02X\n", ret);
	        return;
	    }
		
	    System.out.print("Test OK!\n");
	}
	
	public static void ECC_MainMenu()throws Exception{
	    Scanner sc = new Scanner(System.in);
	    while (true){
	        System.out.print("\n");
	        System.out.print("     ####################################################\n");
	        System.out.print("     #      1. Generate ECC Keypair                     #\n");
	        System.out.print("     #      2. Delete ECC Keypair                       #\n");
	        System.out.print("     #      3. ECC Encrypt/Decrypt Test                 #\n");
	        System.out.print("     #      4. ECC Sign/Verify Test                     #\n");
	        System.out.print("     #      5. ECC AgreementKey Test                    #\n");
	        System.out.print("     #      6. ECC Gen_Import_Export Test               #\n");
	        System.out.print("     #      0. Return                                   #\n");
	        System.out.print("     ####################################################\n");
	        System.out.print("\n");

	        System.out.print("Please select:");
	        int select = sc.nextInt();

	        if (select == 1){
	            ECC_GenKeyPair();
	        }
	        else if (select == 2){
	            ECC_DelKeyPair();
	        }
	        else if (select == 3){
	            ECC_EnDecryptTest();
	        }
	        else if (select == 4){
	            ECC_SignVerifyTest();
	        }
	        else if (select == 5){
	            ECC_AgreementDataTest();
	        }
	        else if (select == 6){
	        	ECC_Gen_Import_Export_Test();
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