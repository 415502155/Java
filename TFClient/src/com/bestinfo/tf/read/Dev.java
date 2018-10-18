package com.bestinfo.tf.read;

import com.fmkeyjni.key_api;

import java.util.Scanner;

public class Dev{
	public static int FM_OPEN_SEREL = 0x00000040;
	public static void GetDeviceInfo() throws Exception{
		int ret;
		byte[] devInfo = new byte[144];		
		ret = key_api.FM_SIC_GetDeviceInfo(key_api.hDev[0],devInfo);
		if (ret != 0){
			System.out.printf("FM_SIC_GetDeviceInfo err, ret = %02X\n", ret);
	        return;
	    }
		else{
			System.out.println("FM_SIC_GetDeviceInfo success!");
		}
	}
	
	public static void GetRadom() throws Exception{
		int ret;
		int len;
		byte[] random = new byte[10240];
		Scanner sc = new Scanner(System.in);	
		System.out.print("input the leng:");
		len = sc.nextInt(); 

		ret = key_api.FM_SIC_GenRandom(key_api.hDev[0], len, random);
		if (ret != 0){
			System.out.printf("FM_SIC_GenRandom err, ret = %02X\n", ret);
	        return;
	    }
		
		for(int i = 0; i < len; i++){
			System.out.printf("0x%02x,", random[i]);
			if (i != 0 && 0 == (i+1)%20){
				System.out.print("\n");
			}
		}
			
		System.out.print("\n");
	}
	
	public static void OpenDevbyName() throws Exception{
		int ret;
		byte index = 0;
		String ID;
		byte[] Id = new byte[16];
		Scanner sc = new Scanner(System.in);	
		System.out.print("input dev name:");
		ID = sc.nextLine();
		Id = getBytes(ID);
		
	    ret = key_api.FM_SIC_OpenByName(Id, 0, index, key_api.hDev);
		if (ret != 0){
			System.out.printf("FM_SIC_OpenByName err, ret = %02X\n", ret);
	        return;
	    }
		else{
			System.out.print("succeed!\n");
		}
	}
	
	public static byte[] getBytes(String s){
		int len = s.length();
		byte[] tmp = new byte[len+1];

		for (int i=0; i<len; i++){
			tmp[i] = (byte)s.charAt(i);
		}
		return tmp;
	}

	public static void OpenDevbySeri() throws Exception{
		int ret;
		byte index = 0;
		String ID;
		byte[] Id = new byte[17];
		Scanner sc = new Scanner(System.in);	
		System.out.print("input dev serial num:");
		ID = sc.nextLine();
		Id = getBytes(ID);
		
		ret = key_api.FM_SIC_OpenBySerial(Id, 0, index, key_api.hDev);
		if (ret != 0){
			System.out.printf("FM_SIC_OpenBySerial err, ret = %02X\n", ret);
	        return;
	    }
		else{
			System.out.print("succeed!\n");
		}
	}
	
	public static void EnumDev() throws Exception{
		int ret;
		byte[] resultBuf = new byte[300];
		int[] resultBufLen = new int[1];

		ret = key_api.FM_SIC_EnumDevice(FM_OPEN_SEREL, resultBuf, resultBufLen);
		if (ret != 0){
			System.out.printf("FM_SIC_EnumDevice err, ret = %02X\n", ret);
	        return;
	    }
		else{
			for(int i = 0; i < 300; i++){
				if( resultBuf[i] != 0){
					System.out.printf("%c", resultBuf[i]);
				}
				else{
					System.out.print(" ");
				}

				
			}
		}
	}

	public static void DeviceManagement_MainMenu() throws Exception{;
		Scanner sc = new Scanner(System.in);	    
	    while (true){
	    	System.out.print("\n");
	    	System.out.print("####################################################\n");
	    	System.out.print("#      1. Get Device Information                   #\n");
	    	System.out.print("#      2. Get Random                               #\n");
	    	System.out.print("#      3. Open Dev by Name                         #\n");
	    	System.out.print("#      4. Open Dev by Serial Num                   #\n");
	    	System.out.print("#      5. Enum Dev                                 #\n");
	    	System.out.print("#      0. Return                                   #\n");
	    	System.out.print("####################################################\n");
	    	System.out.print("\n");

	    	System.out.print("Please select:");
	    	int select = sc.nextInt();	            
	        if (select == 1){
	        	GetDeviceInfo();
	        }
	        else if (select == 2){
	        	GetRadom();
	        }
	        else if (select == 3){
	        	OpenDevbyName();
	        }
	        else if (select == 4){
	        	OpenDevbySeri();
	        }
	        else if (select == 5){
	        	EnumDev();
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