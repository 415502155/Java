package com.bestinfo.tf.read;

import com.fmkeyjni.key_api;

import java.util.Arrays;
import java.util.Scanner;

public class Rsa{
	public static int TEST_RSA_NUM = 128;
	public static int FM_HKEY_TO_HOST = 0x01ffffff;
	public static int HEADER_LENGTH = 10;
	
	//RSA�򲹶�
	public static byte[] FMRSAPadding(byte[] indata, int inOff, int inLen, int bitSize){
			
        if (inLen > 117)  {  
            throw new IllegalArgumentException("input data too large");
        }
        
        byte[]  block = new byte[(bitSize + 7) / 8];

        //ǩ��
    	block[0] = 0x00;
        block[1] = 0x01;                        // type code 1

        for (int i = 2; i != block.length - inLen - 1; i++)    {
            block[i] = (byte)0xFF;
        }

        block[block.length - inLen - 1] = 0x00;       // mark the end of the padding
        System.arraycopy(indata, inOff, block, block.length - inLen, inLen);
        
        return block;
	}

	//RSA�ⲹ��
	public static byte[] FMRSAUnPadding(byte[] block, int inOff, int inLen, int bitSize) {
		
       if (inLen < (bitSize + 7) / 8)     {
            throw new IllegalArgumentException("block truncated");
       }

        byte type = 1;
        
      /*  if (type != 1 && type != 2)      {
            throw new IllegalArgumentException("unknown block type");
        }*/

        if (inLen != (bitSize + 7) / 8)   {
            throw new IllegalArgumentException("block incorrect size");
        }
        
        // find and extract the message block.
        int start;
        
        for (start = 2; start != inLen; start++)  {
            byte pad = block[start];
            
            if (pad == 0)   {
                break;
            }
            if (type == 1 && pad != (byte)0xff)    {
                throw new IllegalArgumentException("block padding incorrect");
            }
        }

        start++;        

        if (start > inLen || start < HEADER_LENGTH)	{
            throw new IllegalArgumentException("no data in block");
        }

        byte[]  result = new byte[inLen - start];
        System.arraycopy(block, start, result, 0, result.length);
        return result;
	}
	
	//��ϣ
	public static byte[] hash(byte[] indata){
		int rv = 0;

		//��ʼ��
		rv = key_api.FM_SIC_HashInit(key_api.hDev[0], key_api.FM_ALG_SHA1, null, 0);	
		if(rv != 0){
			System.out.println("FM_SIC_HashInit is error");
		}
		
		//Update
   		rv = key_api.FM_SIC_HashUpdate(key_api.hDev[0], key_api.FM_ALG_SHA1, indata, indata.length);
		if(rv != 0){
			System.out.println("FM_SIC_HashUpdate is error");
		}
   		
   		//Final
   		byte[] res = new byte[20];
   		int[] hashlen = new int[1];
   		hashlen[0] = 20;
   		rv = key_api.FM_SIC_HashFinal(key_api.hDev[0], key_api.FM_ALG_SHA1, res, hashlen);
   		if (rv != 0)  {
   			System.out.println("FM_SIC_HashFinal is error");
   		}

   		return res;
	}

	public static void RSA_GenKeyPair()throws Exception{
	    int ret;
	    int[] keyID = new int[1];
	    int keyBits;
	    byte[] Pubkey = new byte[516];
	    byte[] Prikey = new byte[1412];
	    Scanner sc = new Scanner(System.in);	    
	    
	    System.out.printf("Please input RSA keyBits(1024/2048):");
	    keyBits = sc.nextInt();
	    if (keyBits != 1024 && keyBits != 2048){
	        System.out.print("RSA keyBits not support!\n");
	        return;
	    }
	    
	    System.out.printf("Please input RSA keyID(0~%d):", TEST_RSA_NUM - 1);
	    keyID[0] = sc.nextInt();
	    if (keyID[0] < 0 || keyID[0] >= TEST_RSA_NUM){
	        System.out.print("RSA keyID out of range!\n");
	        return;
	    }
	    
	    ret = key_api.FM_SIC_GenRSAKeypair(key_api.hDev[0], keyBits, keyID, Pubkey, Prikey);
		if (ret != 0){
			System.out.printf("FM_SIC_GenRSAKeypair err, ret = %02X\n", ret);
	        return;
	    }
	
	    System.out.print("Generate RSA keypair OK!\n");
	}

	public static void RSA_DelKeyPair()throws Exception{
	    int ret;
	    int keyID;
	    Scanner sc = new Scanner(System.in);
	    
	    System.out.printf("Please input keyID(0~%d):", TEST_RSA_NUM - 1);
        keyID = sc.nextInt();
	    if (keyID < 0 || keyID >= TEST_RSA_NUM){
	        System.out.printf("RSA keyID out of range!\n");
	        return;
	    }

	    ret = key_api.FM_SIC_DelRSAKeypair(key_api.hDev[0], keyID);
		if (ret != 0){
			System.out.printf("FM_SIC_DelRSAKeypair err, ret = %02X\n", ret);
	        return;
	    }
		else{
			System.out.println("success!");
		}
	}
	
	public static void RSA_EnDecryptTest()throws Exception{
	    int i;
		int ret;
	    int keyID, keyBits;
	    byte[] inBuf = new byte[1024];
		byte[] OutBuff = new byte[256];
		byte[] tmpOutBuff = new byte[256];
    	int[] OutLen  = new int[1];
    	OutLen[0] = 256;
	    Scanner sc = new Scanner(System.in);
	    
	    System.out.printf("Please input RSA keyBits(1024/2048):");
	    keyBits = sc.nextInt();
	    if (keyBits != 1024 && keyBits != 2048){
	        System.out.print("RSA keyBits not support!\n");
	        return;
	    }

	    System.out.printf("Please input keyID(0~%d):", TEST_RSA_NUM - 1);
	    keyID = sc.nextInt();
	    if (keyID < 0 || keyID >= TEST_RSA_NUM){
	        System.out.print("RSA keyID out of range\n");
	        return;
	    }
	    
	    //Generate input data
	    for(i = 0; i < inBuf.length; i++){
	        inBuf[i] = (byte)(Math.random()*255);
	    }
    	
		byte[] hashData = hash(inBuf);	

    	//�򲹶�
		byte[] paddingData = FMRSAPadding(hashData, 0, hashData.length, keyBits);
				
		//����
		ret = key_api.FM_SIC_RSAEncrypt(key_api.hDev[0], keyID, paddingData, paddingData.length, tmpOutBuff, OutLen, null);
		if(ret != 0){
			System.out.printf("FM_SIC_RSAEncrypt is error, ret = %02X\n", ret);
		}
		
		//����
		ret = key_api.FM_SIC_RSADecrypt(key_api.hDev[0], keyID, tmpOutBuff, OutLen[0], OutBuff, OutLen, null);
		if(ret != 0){
			System.out.printf("FM_SIC_RSADecrypt is error, ret = %02X\n", ret);
		}
				
		
		//�ⲹ��
		byte[] unpaddingData = FMRSAUnPadding(OutBuff, 0, OutLen[0], keyBits); 
		
		//���Ƚ�
	    if (Arrays.equals(hashData, unpaddingData) == true){
	        System.out.printf("Test OK!\n");
	    }
	    else {
	    	System.out.print("Test error, encrypt and decrypt result mismatch!\n");
	    }
	    	
	}

	public static void RSA_SignVerifyTest()throws Exception{
	    int i, ret;
	    int keyID, keyBits;
        byte[] inBuf = new byte[1024];	
		byte[] outBuf = new byte[256];
    	int[] outLen  = new int[1];
    	outLen[0] = 256;
    	int signLen = 0;
	    Scanner sc = new Scanner(System.in);
	    
	    System.out.printf("Please input RSA keyBits(1024/2048):");
	    keyBits = sc.nextInt();
	    if (keyBits != 1024 && keyBits != 2048){
	        System.out.print("RSA keyBits not support!\n");
	        return;
	    }

	    System.out.printf("Please input RSA keyID(0~%d):", TEST_RSA_NUM - 1);
	    keyID = sc.nextInt();
	    if (keyID < 0 || keyID >= TEST_RSA_NUM){
	        System.out.print("RSA keyID out of range\n");
	        return;
	    }

	    //Generate input data
	    for (i = 0; i < inBuf.length; i++){
	        inBuf[i] = (byte)(Math.random()*255);
	    }

	  //ǩ��
		ret = key_api.FM_SIC_RSASign(key_api.hDev[0], keyID, key_api.FM_ALG_SHA1, 
				inBuf, inBuf.length, outBuf, outLen, null);
		if(ret != 0){
			System.out.printf("FM_SIC_RSASign is error, ret = %02X\n", ret);
		}
		
		//��ǩ
		signLen = outLen[0];
		ret = key_api.FM_SIC_RSAVerify(key_api.hDev[0], keyID, key_api.FM_ALG_SHA1, 
				inBuf, inBuf.length, outBuf, signLen, null);
		if(ret != 0){
			System.out.printf("FM_SIC_RSAVerify is error, ret = %02X\n", ret);
		}else{
			System.out.print("Test OK!\n");
		}
	}
	
	public static void RSA_Gen_Import_Export_Test()throws Exception{
	    int ret;
	    int keyBits = 2048;
		byte[] Pubkey = new byte[516];
		byte[] Prikey = new byte[1412];
		byte[] Pubkey2 = new byte[516];
		byte[] Prikey2 = new byte[1412];
		byte[] tmpPubkey = new byte[516];
		byte[] tmpPrikey = new byte[1412];
		int[] keyID = new int[1];
		
		ret = key_api.FM_SIC_GenRSAKeypair(key_api.hDev[0], keyBits, keyID, Pubkey, Prikey);
		if (ret != 0){
			System.out.printf("FM_SIC_GenRSAKeypair err, ret = %02X\n", ret);
	        return;
	    }
		
		System.arraycopy(Pubkey, 0, Pubkey2, 0, 516);
		System.arraycopy(Prikey, 0, Prikey2, 0, 1412);
	    ret = key_api.FM_SIC_ImportRSAKeypair(key_api.hDev[0], keyID, Pubkey, Prikey);
		if (ret != 0){
			System.out.printf("FM_SIC_ImportRSAKeypair err, ret = %02X\n", ret);
	        return;
	    }

		ret = key_api.FM_SIC_ExportRSAKeypair(key_api.hDev[0], keyID[0], tmpPubkey, tmpPrikey);
		if (ret != 0){
			System.out.printf("FM_SIC_ExportRSAKeypair err, ret = %02X\n", ret);
	        return;
	    }
		
	  	if ((Arrays.equals(Pubkey2, tmpPubkey) != true) || (Arrays.equals(Prikey2, tmpPrikey) != true))
	    {
	        System.out.print("test err!");
	        return;
	    }

	    ret = key_api.FM_SIC_DelRSAKeypair(key_api.hDev[0], keyID[0]);
		if (ret != 0){
			System.out.printf("FM_SIC_DelRSAKeypair err, ret = %02X\n", ret);
	        return;
	    }
		
	    System.out.print("Test OK!\n");
	}
	
	public static void RSA_MainMenu()throws Exception{
	    Scanner sc = new Scanner(System.in);
	    while (true){
	        System.out.print("\n");
	        System.out.print("     ####################################################\n");
	        System.out.print("     #      1. Generate RSA Keypair                     #\n");
	        System.out.print("     #      2. Delete RSA Keypair                       #\n");
	        System.out.print("     #      3. RSA Encrypt/Decrypt Test                 #\n");
	        System.out.print("     #      4. RSA Sign/Verify Test                     #\n");
	        System.out.print("     #      5. RSA Gen_Import_Export Test               #\n");
	        System.out.print("     #      0. Return                                   #\n");
	        System.out.print("     ####################################################\n");
	        System.out.print("\n");

	        System.out.print("Please select:");
	        int select = sc.nextInt();

	        if (select == 1){
	            RSA_GenKeyPair();
	        }
	        else if (select == 2){
	            RSA_DelKeyPair();
	        }
	        else if (select == 3){
	            RSA_EnDecryptTest();
	        }
	        else if (select == 4){
	            RSA_SignVerifyTest();
	        }
	        else if (select == 5){
	        	RSA_Gen_Import_Export_Test();
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