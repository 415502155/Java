package com.bestinfo.tf.read;

import com.fmkeyjni.key_api;

import java.util.Scanner;

public class AdminUser{
	public static int FM_PIN_ADMIN = 1;
	public static int FM_PIN_OPER = 2;
	public static int FM_PIN_CHANGEOPER  = 1;
	public static int FM_PIN_CHANGEADMIN = 2;
	public static int FM_PIN_UNBLOCKOPER = 3;
	
	public static void UserManagement_MainMenu() throws Exception{
		int ret;
		String pin;
		int pinLen;
		int[] retrynum = new int[]{0};
		byte[] oldOperPin= new byte[]{0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x00};
		byte[] newOperPin= new byte[]{0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x00};
		byte[] oldAdminPin= new byte[]{0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x00};
		byte[] newAdminPin= new byte[]{0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x31, 0x00};
		int oldPinLen = 8;
		int newPinLen = 8;
		Scanner sc = new Scanner(System.in);
	    
	    while (true)
	    {
			System.out.print("\n");
			System.out.print("####################################################\n");
			System.out.print("#      1. User Login                               #\n");
			System.out.print("#      2. Admin Login                              #\n");
			System.out.print("#      3. Change User Password                     #\n");
			System.out.print("#      4. Change Admin Password                    #\n");
			System.out.print("#      5. Logout                                   #\n");
			System.out.print("#      0. return                                   #\n");
			System.out.print("####################################################\n");
			System.out.print("\n");

			System.out.print("Please select:");
			int select = sc.nextInt();
			
	        if (select == 1){
	        	System.out.print("input password:");
	        	pin = sc.nextLine();
	        	pin = sc.nextLine();
	        	pinLen = pin.length();
	        	ret = key_api.FM_SIC_USER_Login(key_api.hDev[0], FM_PIN_OPER, pin, pinLen, retrynum);
	    		if (ret != 0){
	    			System.out.printf("FM_SIC_USER_Login err, ret = %02X\n", ret);
	    	        return;
	    	    }
				else{
					System.out.print("succeed\n");
	            }
	        }
	        else if (select == 2){
	        	System.out.print("input password:");
	        	pin = sc.nextLine();
	        	pin = sc.nextLine();
	        	pinLen = pin.length();
	        	ret = key_api.FM_SIC_USER_Login(key_api.hDev[0], FM_PIN_ADMIN, pin, pinLen, retrynum);
	    		if (ret != 0){
	    			System.out.printf("FM_SIC_USER_Login err, ret = %02X, retrynum = %d\n", ret, retrynum);
	    	        return;
	    	    }
				else{
					System.out.print("succeed\n");
	            }
	        }
			else if (select == 3){
	        	ret = key_api.FM_SIC_USER_ChangePin(key_api.hDev[0], FM_PIN_CHANGEOPER, oldOperPin, oldPinLen, newOperPin, newPinLen, retrynum);
	    		if (ret != 0){
	    			System.out.printf("FM_SIC_USER_ChangePin err, ret = %02X\n, retrynum = %d", ret, retrynum);
	    	        return;
	    	    }
				else{
					System.out.print("succeed\n");
	            }
	        }
			else if (select == 4){
	        	ret = key_api.FM_SIC_USER_ChangePin(key_api.hDev[0], FM_PIN_CHANGEADMIN, oldAdminPin, oldPinLen, newAdminPin, newPinLen, retrynum);
	    		if (ret != 0){
	    			System.out.printf("FM_SIC_USER_ChangePin err, ret = %02X\n, retrynum = %d", ret, retrynum);
	    	        return;
	    	    }
				else{
					System.out.print("succeed\n");
	            }
	        }
			else if (select == 5){
				ret = key_api.FM_SIC_USER_Logout(key_api.hDev[0]);
	    		if (ret != 0){
	    			System.out.printf("FM_SIC_USER_Logout err, ret = %02X\n,", ret);
	    	        return;
	    	    }
				else{
					System.out.print("succeed\n");
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