package com.bestinfo.tf.read;

import com.fmkeyjni.key_api;

import java.util.Arrays;
import java.util.Scanner;

public class Sym{
	public static int TEST_SYM_NUM = 256;
	public static int FM_ALG_SM1 = 0x00000002;
	public static int FM_ALGMODE_ECB = 0x00000000;
	public static int FM_ALGMODE_CBC = 0x00000001;
	public static int TEST_SYM_DATALEN = 1536;
	public static int FM_HKEY_FROM_HOST = 0x02ffffff ;
	
	public static void SYM_GenKey() throws Exception{
	    int ret;
	    int keyLen;
	    int[] keyID = new int[1];	    
	    byte[] keyData = new byte[32];
	    Scanner sc = new Scanner(System.in);
	    
	    System.out.printf("Please input keyID(0~%d):", TEST_SYM_NUM - 1);
	    keyID[0] = sc.nextInt();
	    if (keyID[0] < 0 || keyID[0] >= TEST_SYM_NUM)
	    {
	    	System.out.print("Sym keyID out of range!\n");
	        return;
	    }

	    System.out.print("Please input key length(<=32):");
	    keyLen = sc.nextInt();
	    if (keyLen <= 0 || keyLen > 32)
	    {
	    	System.out.print("Key lenth error!\n");
	        return;
	    }

	    ret = key_api.FM_SIC_GenKey(key_api.hDev[0], FM_ALG_SM1, keyLen, keyID, keyData);
		if (ret != 0){
			System.out.printf("FM_SIC_GenKey err, ret = %02X\n", ret);
	        return;
	    }
		else{
			System.out.print("succeed\n");
        }
	}
	
	public static void SYM_DelKey() throws Exception{
		int ret;
		int keyID;
		Scanner sc = new Scanner(System.in);
	    System.out.printf("Please input keyID(0~%d):", TEST_SYM_NUM - 1);

	    keyID = sc.nextInt();
	    if (keyID < 0 || keyID >= TEST_SYM_NUM)
	    {
	        System.out.print("Sym keyID out of range!\n");
	        return;
	    }

	    ret = key_api.FM_SIC_DelKey(key_api.hDev[0], keyID);
		if (ret != 0){
			System.out.printf("FM_SIC_DelKey err, ret = %02X\n", ret);
	        return;
	    }
		else{
			System.out.print("succeed\n");
        }
	}
	
	public static void SYM_Test()throws Exception{
		int ret;
		int keyID;
		int workMode;
		int alg, ivlen;
		int i;
		byte[] inbuf = new byte[TEST_SYM_DATALEN];
		byte[] cipher = new byte[TEST_SYM_DATALEN];
		byte[] plainData = new byte[TEST_SYM_DATALEN];
		int [] cipherLen = new int[1];
		int [] plianDataLen = new int[1];
		byte[] iv = new byte[32];
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.print("\n");
		    System.out.print("     ####################################################\n");
		    System.out.print("     #                                                  #\n");
		    System.out.print("     #      1. SM1 Algorithm                            #\n");
		    System.out.print("     #                                                  #\n");
		    System.out.print("     #      0. return                                   #\n");
		    System.out.print("     #                                                  #\n");
		    System.out.print("     ####################################################\n");
		    System.out.print("\n");
	
			System.out.print("Please select:");
		    int select = sc.nextInt();
	
		    if(select == 1){
		        alg = FM_ALG_SM1;
		        ivlen = 16;
		    }
		    else if(select == 0){
		        return;
		    }
		    else{
		    	System.out.println("select err, please select again!");
		    	continue;
		    }
	
		    System.out.printf("Please input keyID(0~%d):", TEST_SYM_NUM - 1);
		    keyID = sc.nextInt();
		    if (keyID < 0 || keyID >= TEST_SYM_NUM){
		        System.out.print("Sym keyID out of range!\n");
		        return;
		    }

		    System.out.print("Please select the work mode(1.ECB 2.CBC):");
		    select = sc.nextInt();
		    if (select < 1 || select >= 3){
		        System.out.print("wordMode err!\n");
		        return;
		    }
		    if (select == 1){
		        workMode = FM_ALGMODE_ECB;
		    }
		    else{
		    	workMode = FM_ALGMODE_CBC;
		    }
	
		    //Generate input data
		    for (i = 0; i < TEST_SYM_DATALEN; i++){
		    	inbuf[i] = (byte)(Math.random()*255);
		    }
	
		    //Generate IV data
		    for (i = 0; i < 16; i++){
		        iv[i] = (byte)(Math.random()*255);
		    }
		    
		    ret = key_api.FM_SIC_Encrypt(key_api.hDev[0], keyID, alg, workMode, inbuf, TEST_SYM_DATALEN, cipher,
		    		cipherLen, null, 0, iv, ivlen);
			if (ret != 0){
				System.out.printf("FM_SIC_Encrypt err, ret = %02X\n", ret);
		        return;
		    }
			
			ret = key_api.FM_SIC_Decrypt(key_api.hDev[0], keyID, alg, workMode, cipher, cipherLen[0], plainData, 
					plianDataLen, null, 0, iv, ivlen);
			if (ret != 0){
				System.out.printf("FM_SIC_Decrypt err, ret = %02X\n", ret);
		        return;
		    }
	
		    //Compare result
		    if (Arrays.equals(inbuf, plainData) == true){
		        System.out.print("Test OK!\n");
		    }
		    else{
		    	System.out.print("Test error, encrypt and decrypt result mismatch!\n");
		    }
		}
	}
	
	public static byte[]intArrayToByteArray(int[] intArray, int arrayLength) {
        byte[] byteArrayData = new byte[arrayLength*4];
        for(int i = 0; i < arrayLength; i++){
            byteArrayData[i*4 + 0] = (byte) (0xff & intArray[i]);
            byteArrayData[i*4 + 1] = (byte) (0xff & (intArray[i] >> 8));
            byteArrayData[i*4 + 2] = (byte) (0xff & (intArray[i] >> 16));
            byteArrayData[i*4 + 3] = (byte) (0xff & (intArray[i] >> 24));
        }

        return byteArrayData;
    }
	
	public static void SYM_SM1ValidityTest() throws Exception{
	    int ret;
	    int keyLen = 16;
	    int ivLen = 16;
	    byte[] SM1Key = new byte[]{
	    	0x40,(byte)0xbb,0x12,(byte)0xdd,0x6a,(byte)0x82,0x73,(byte)0x86,
	    	0x7f,0x35,0x29,(byte)0xd3,0x54,(byte)0xb4,(byte)0xa0,0x26
	    };
	    byte[] iv = new byte[]{
	    		(byte)0xe8,0x3d,0x17,0x15,(byte)0xac,(byte)0xf3,0x48,0x63,
	    		(byte)0xac,(byte)0xeb,(byte)0x93,(byte)0xe0,(byte)0xe5,(byte)0xab,(byte)0x8b,(byte)0x90
	    };

	    int inLen = 1536;
	    int[] outLen = new int[1];
	    int workMode = FM_ALGMODE_CBC;
	    int alg = FM_ALG_SM1;

	    int i;
	    int keyID = FM_HKEY_FROM_HOST;

	    int[] au32TmpSM1CBCBuf = new int[]{
	        0x58619e32, 0xf2eecb00, 0x29846b03, 0x14b6e429, 0x1e255c62, 0x45271368,
	        0x9b4054b9, 0xe37c8a60, 0x5e15580a, 0x3a9d4ea8, 0x43a4b916, 0x091e807d,
	        0xabd883d4, 0x33e558be, 0xfd8794a3, 0x7f187e1b, 0xd816abd9, 0x5a87ffa6,
	        0x5fa180a7, 0x38028bdf, 0x7004b23c, 0xc46451ff, 0x98b9476d, 0x3d71e020,
	        0xa2ff0efe, 0xd00526e1, 0xffba057d, 0xade0a639, 0x32ded199, 0x7a659da5,
	        0x0d65cf26, 0x0d835edf, 0x577aa0c8, 0xc91b4379, 0x737bf6c4, 0xf84387b1,
	        0x83dcc070, 0x6dfc25d9, 0x05db50ca, 0x1dd51bbe, 0xf20e63bf, 0x7b0c44a4,
	        0xb4451108, 0x1a5e09c2, 0x61067e3b, 0xea99bfa3, 0xbc534b6d, 0x7285848d,
	        0x2a077d82, 0x895fa9b2, 0xcd74d8e2, 0xc6fe72f8, 0xc56ad3a4, 0x968dc725,
	        0x84c10ca0, 0x0b1bf1f2, 0x035aa083, 0x95ee7e78, 0x8c13ee34, 0xa98388c6,
	        0x42d6c318, 0x79bf39b7, 0x2379b9e2, 0x38ceb954, 0x4cef98bb, 0x0bc7c744,
	        0x2ebc1d00, 0x538512f8, 0x77397e6f, 0x9851151e, 0x14b864d7, 0x68b9f626,
	        0x501a0057, 0x883d0ae1, 0x82afa033, 0x2575e0d7, 0x45e5d410, 0xa9c8a096,
	        0x9b2937b8, 0xfb27da10, 0x58d9be69, 0xe7ad4274, 0x9d530dc8, 0xb7fa438a,
	        0xdbd8705c, 0xf8e7ad01, 0xf92128ed, 0xba41a324, 0xc1d79710, 0xf62b8329,
	        0x121fe9b0, 0x67c05ff3, 0xb3c662e0, 0x43d11c0c, 0x6fd9e8b8, 0x2d825a5e,
	        0xeb39c9ce, 0xea8bf819, 0x89924d36, 0x8e60ff31, 0x456ecd5b, 0x06461dd7,
	        0x1d9891a9, 0x24b1934e, 0x1026f723, 0x46e014b3, 0x6a24c321, 0x5007bb57,
	        0xd3ede22c, 0xe14fc9cc, 0x7fbd0d7f, 0x8e0e62e0, 0x217bacd4, 0x5b6408bc,
	        0x08a5a92b, 0xda573ed1, 0x56fc4c24, 0x5b588941, 0x92fee8ad, 0x1a031cba,
	        0x4412ca82, 0xd06bc566, 0x2a5a0e89, 0xfdd601f3, 0x07e60f4e, 0xc43e151a,
	        0xdea78070, 0x6cf38a09, 0xc334053a, 0xd8453640, 0x0f501a4a, 0x2508c7b9,
	        0x3ed27671, 0x63fb3fae, 0x26cb365e, 0x0aaac80d, 0xcae66d40, 0x77353267,
	        0x67bd1b50, 0xed84f386, 0x37c9e75e, 0xcb30ffcc, 0x8ad7aa3f, 0x7ad593ba,
	        0x80859b3b, 0xcd399708, 0xcd543551, 0x07ec5cd9, 0x5890607a, 0x738d473f,
	        0xe6ebf82b, 0x826a0877, 0x8c478850, 0xd4309051, 0x3c5f24d3, 0x27acdf1c,
	        0xd1b4beae, 0x4b13c6e9, 0x20573791, 0x8320da33, 0xff036e0b, 0xb6ae2917,
	        0xbd9db82f, 0xe1abd4ec, 0x31056f98, 0x1e490b8b, 0xdb824e2c, 0x2726d31d,
	        0x06d906bf, 0xeb40e94a, 0x0cbf8a18, 0x89209510, 0x87a3fb1d, 0x2f7c7267,
	        0x380adc0e, 0x0c068cc3, 0x6c1375c1, 0x9ea428a7, 0x16b191ec, 0x52ab7e2b,
	        0xf4583bd5, 0xa170b8a3, 0x62efe6e0, 0xa0b88297, 0x60ead161, 0x3fc34859,
	        0xc1431da3, 0xb343a8d5, 0x99e974a1, 0xc694da30, 0xb0a3c8fd, 0x451abc39,
	        0x26ee782d, 0xb9d43d5b, 0xea9d6de6, 0x04e4742d, 0x410d21fe, 0x90b8a983,
	        0x43cdcebf, 0x7d3bcc6b, 0xeb210cc2, 0x563d1d51, 0x83114cf7, 0x61a1f851,
	        0xe5231911, 0x9b46f50a, 0xdf46d184, 0xcc5841cc, 0xfd8fc3af, 0xf2374a56,
	        0xb15ea365, 0xdc44b889, 0xe3402ed8, 0x16f0c5a7, 0x6e40d427, 0x4d24e5d9,
	        0x50ad2806, 0xb98b55d3, 0xfc385c35, 0x4dd31cac, 0xbf1bd012, 0xbb2e6c27,
	        0x971e33c4, 0xa10d4b1e, 0xb06da75a, 0xfe3dc784, 0x77f4e99b, 0xa14882f2,
	        0x84fe56dd, 0x09466204, 0xf0d383cb, 0x8d82703d, 0xdaf02339, 0xa21f04d6,
	        0x11049daf, 0x25189843, 0x66d3e4fc, 0x82262f11, 0xa753f0e1, 0x0e92331d,
	        0x4907f7ef, 0xb1b07485, 0x9b018ac7, 0x708403e4, 0x6631f35a, 0x03d0aaad,
	        0x4d55d541, 0x288f1428, 0x620a3589, 0x1ad0e318, 0x7c3ccac3, 0xe0b3b007,
	        0x2fec6b24, 0xf204f9b9, 0xcd6a8bbc, 0x6baa9df8, 0x0fe920a1, 0xcb4931bc,
	        0xb3172166, 0xb09ad2ec, 0xe0b14f06, 0x752dedb4, 0x2b32f93b, 0x82edb4df,
	        0xeaa143bc, 0x6ca90400, 0x0b4f38df, 0x5b16203b, 0x9fa2c27a, 0xef5b5299,
	        0x20447373, 0x56be8584, 0xd86c2256, 0xf52ccb9d, 0xebb0c40d, 0x3e053c75,
	        0xac0f335f, 0xfaac93cf, 0x7598298d, 0xb89aead3, 0x8e6be45c, 0x5baa8215,
	        0xd4ab9464, 0x08bd6388, 0x6ddc28b3, 0x7c492134, 0x258b76a6, 0x15302a92,
	        0xf6ccb28a, 0x40dbc70f, 0x4eef61e7, 0xbdc4a7d0, 0x4c038681, 0xdb9fcdca,
	        0x5218ea09, 0x11cab258, 0xd0dd913d, 0xf83eb807, 0x6732747c, 0xfd41d30c,
	        0x8e09df39, 0x86fb9ad7, 0x46dbcd69, 0x71a30db3, 0xb54b0d24, 0xca7639f0,
	        0x5fa6fb4d, 0xfbd05e7f, 0x3b0cb87a, 0xbdbf5140, 0x10bcbd8c, 0xb5832db2,
	        0x1924cd5a, 0x636d60d3, 0xb404b3de, 0x942cd1b5, 0x39688826, 0x398402f5,
	        0xed955c5f, 0xf4ce923a, 0xf8900844, 0xf4ae2e35, 0x77650732, 0x91891dfe,
	        0x8ed67c7d, 0x5a780266, 0x3cb4f669, 0x0f835e9e, 0xf3cdd341, 0x83d3d77e,
	        0x4eeb5a39, 0x05824a60, 0x2f6c4102, 0xb2f42bc2, 0x1bb4274f, 0x98a84a64,
	        0x15e1de4c, 0x0984fd62, 0x5260aacf, 0xebdb1f35, 0x8f089a62, 0x6e9ef223,
	        0xfeac38a0, 0x423f37f8, 0x569e716d, 0x1ea2f338, 0x5a3b96c0, 0x1a73149b,
	        0x02fa7b1f, 0xf9701991, 0x3c9f0dee, 0x9c500972, 0xa46541c4, 0xfc31afb1,
	        0xde253fb4, 0x36e6d247, 0xb448ae89, 0x1434c965, 0xc3007fa1, 0x88abe8e7,
	        0x575d47c0, 0x8af5fba4, 0x8baa0c97, 0x1feccbea, 0x6633ccdf, 0x99338a07,
	        0xebd74c99, 0x0e3b181b, 0x59ef6385, 0x4fc62866, 0x084d2b6e, 0xb0de243b,
	        0x006d4ba7, 0xe10ee75d, 0xd4cb0c07, 0xbc2e52e3, 0x7e562534, 0xdf7d93e4,
	        0x3fa42b98, 0x73ba2752, 0xe70c7435, 0x72ea491d, 0x29414fdf, 0xfc42b01b
	    };

	    int[] inBuf = new int[384];
	    int[] outBuf = new int[384];

	    for (i = 0; i < inLen/4; i++){
	    	inBuf[i] = i;
	    }

	    for (i = 0; i < inLen/4; i++)
	    {
	    	outBuf[i] = 0x12345678;
	    }
	    
	    byte[] inbuf = intArrayToByteArray(inBuf, 384);
	    byte[] outbuf = intArrayToByteArray(outBuf, 384);
	    byte[] tmpSM1CBCBuf = intArrayToByteArray(au32TmpSM1CBCBuf, 384);
	    
	    /******** �ԳƼ��ܲ��� *******/
	    ret = key_api.FM_SIC_Encrypt(key_api.hDev[0], keyID, alg, workMode, inbuf, inLen, outbuf,
	    		outLen, SM1Key, keyLen, iv, ivLen);
		if (ret != 0){
			System.out.printf("FM_SIC_Decrypt err, ret = %02X\n", ret);
	        return;
	    }
	    
	    if (Arrays.equals(outbuf, tmpSM1CBCBuf) == true){
	        System.out.print("Test OK!\n");
	    }
	    else{
	    	System.out.print("Test failed!\n");
	    }
	}


	public static void SYM_MainMenu() throws Exception{
		Scanner sc = new Scanner(System.in);

	    while (true){
	        System.out.print("\n");
	        System.out.print("     ####################################################\n");
	        System.out.print("     #      1. Generate SYM Key                         #\n");
	        System.out.print("     #      2. Delete SYM Key                           #\n");
	        System.out.print("     #      3. SYM Encrypt And Decrypt Test             #\n");
	        System.out.print("     #      4. SM1 Result Test                          #\n");
	        System.out.print("     #      0. Return                                   #\n");
	        System.out.print("     ####################################################\n");
	        System.out.print("\n");

	        System.out.print("Please select:");
            int select = sc.nextInt();

	        if (select == 1){
	        	SYM_GenKey();
	        }
	        else if (select == 2){
	        	SYM_DelKey();
	        }
	        else if (select == 3){
	        	SYM_Test(); 
	        }
	        else if (select == 4){
	        	SYM_SM1ValidityTest();
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