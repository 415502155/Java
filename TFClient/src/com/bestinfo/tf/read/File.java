package com.bestinfo.tf.read;

import com.fmkeyjni.key_api;

import java.util.Scanner;

public class File{
	public static int SIC_MAX_DIR_NUM = 64;
	public static int SIC_FILE_NAME_LEN = 4;
	public static int SIC_MAX_FILE_NUM = 256;
	public static void File_MainMenu()throws Exception{	
		Scanner sc = new Scanner(System.in);
		int ret;
		int[] len = new int[1];
		String enumDirName;
		String fileName;
		byte[] enumDirName2 = new byte[SIC_MAX_DIR_NUM * (SIC_FILE_NAME_LEN + 1)];
		byte[] enumFileName = new byte[SIC_MAX_FILE_NUM * (SIC_FILE_NAME_LEN + 1)];
		String fileBuf;
		byte[] filebuf = new byte[64];
		byte[] fileInfo = new byte[12];
		int i;
	    
	    while (true){
			System.out.print("\n");
			System.out.print("####################################################\n");
			System.out.print("#      1. Init File System                         #\n");
			System.out.print("#      2. Refresh Dir                              #\n");
			System.out.print("#      3. Creat Dir                                #\n");
			System.out.print("#      4. Creat File                               #\n");
			System.out.print("#      5. Delete Dir                               #\n");
			System.out.print("#      6. Delete File                              #\n");
			System.out.print("#      7. Write File                               #\n");
			System.out.print("#      8. Read File                                #\n");
			System.out.print("#      9. Get File Info                            #\n");
			System.out.print("#      0. Return                                   #\n");
			System.out.print("####################################################\n");
			System.out.print("\n");

			System.out.print("Please select:");
			int select = sc.nextInt();
			
	        if (select == 1){
				ret = key_api.FM_SIC_FILE_Init(key_api.hDev[0]);
				if (ret != 0){
					System.out.printf("FM_SIC_FILE_Init err, ret = %02X\n", ret);
			        return;
			    }

				ret = key_api.FM_SIC_FILE_CreateDir(key_api.hDev[0], "\\root\\cert", 0);
				if (ret != 0){
					System.out.printf("FM_SIC_FILE_CreateDir err, ret = %02X\n", ret);
			        return;
			    }
				else{
	                System.out.print("succeed\n");
	            }
	        }
	        else if (select == 2){
	        	System.out.print("input dir name:");
	        	enumDirName = sc.nextLine();
	        	enumDirName = sc.nextLine();

				len[0] = SIC_MAX_DIR_NUM * (SIC_FILE_NAME_LEN + 1);
				ret = key_api.FM_SIC_FILE_EnmuDir(key_api.hDev[0], enumDirName, len, enumDirName2, null);
				if (ret != 0){
					System.out.printf("FM_SIC_FILE_EnmuDir err, ret = %02X\n", ret);
			        return;
			    }
				else{
					for(i = 0; i < len[0]; i++)
					{
						if(enumDirName2[i] == 0)
							System.out.print("/ ");
						else
							System.out.printf("%c", enumDirName2[i]);
					}
					
				}
				
				len[0] = SIC_MAX_FILE_NUM * (SIC_FILE_NAME_LEN + 1);
				ret = key_api.FM_SIC_FILE_EnmuFile(key_api.hDev[0], enumDirName,len, enumFileName, null);
				if (ret != 0){
					System.out.printf("FM_SIC_FILE_EnmuFile err, ret = %02X\n", ret);
			        return;
			    }
				else{
					for(i = 0; i < len[0]; i++)
					{
						if(enumFileName[i] == 0)
							System.out.print(" ");
						else
							System.out.printf("%c", enumFileName[i]);
					}
					System.out.print("\n");
				}
	            System.out.print("succeed\n");
	        }
			else if (select == 3){
	        	System.out.print("input dir name:");
	        	enumDirName = sc.nextLine();
	        	enumDirName = sc.nextLine();
	        	ret = key_api.FM_SIC_FILE_CreateDir(key_api.hDev[0], enumDirName, 0);
				if (ret != 0){
					System.out.printf("FM_SIC_FILE_CreateDir err, ret = %02X\n", ret);
			        return;
			    }
				else{
	                System.out.print("succeed\n");
	            }
	        }
			else if (select == 4){
	        	System.out.print("input dir name:");
	        	enumDirName = sc.nextLine();
	        	enumDirName = sc.nextLine();
	        	System.out.print("input file name:");
	        	fileName = sc.nextLine();
				ret = key_api.FM_SIC_FILE_CreateFile(key_api.hDev[0], enumDirName, fileName, 64, 0);
				if (ret != 0){
					System.out.printf("FM_SIC_FILE_CreateFile err, ret = %02X\n", ret);
			        return;
			    }
				else{
	                System.out.print("succeed\n");
	            }
	        }
			else if (select == 5){
	        	System.out.print("input dir name:");
	        	enumDirName = sc.nextLine();
	        	enumDirName = sc.nextLine();
				ret = key_api.FM_SIC_FILE_DeleteDir(key_api.hDev[0], enumDirName);
				if (ret != 0){
					System.out.printf("FM_SIC_FILE_DeleteDir err, ret = %02X\n", ret);
			        return;
			    }
				else{
	                System.out.print("succeed\n");
	            }
	        }
			else if (select == 6){
	        	System.out.print("input dir name:");
	        	enumDirName = sc.nextLine();
	        	enumDirName = sc.nextLine();
	        	System.out.print("input file name:");
	        	fileName = sc.nextLine();
				ret = key_api.FM_SIC_FILE_DeleteFile(key_api.hDev[0], enumDirName, fileName);
				if (ret != 0){
					System.out.printf("FM_SIC_FILE_DeleteFile err, ret = %02X\n", ret);
			        return;
			    }
				else{
	                System.out.print("succeed\n");
	            }
	        }
			else if (select == 7){
	        	System.out.print("input dir name:");
	        	enumDirName = sc.nextLine();
	        	enumDirName = sc.nextLine();
	        	System.out.print("input file name:");
	        	fileName = sc.nextLine();
	        	System.out.print("input string(<=64):");
	        	fileBuf = sc.nextLine();
	        	len[0] = fileBuf.length();
	        	filebuf = fileBuf.getBytes();
	        	
				ret = key_api.FM_SIC_FILE_WriteFile(key_api.hDev[0], enumDirName, fileName, 0, len[0], filebuf);
				if (ret != 0){
					System.out.printf("FM_SIC_FILE_WriteFile err, ret = %02X\n", ret);
			        return;
			    }
				else{
	                System.out.print("succeed\n");
	            }
			
	        }
			else if (select == 8){
	        	System.out.print("input dir name:");
	        	enumDirName = sc.nextLine();
	        	enumDirName = sc.nextLine();
	        	System.out.print("input file name:");
	        	fileName = sc.nextLine();
	        	ret = key_api.FM_SIC_FILE_ReadFile(key_api.hDev[0], enumDirName, fileName, 0, 64, filebuf);
				if (ret != 0){
					System.out.printf("FM_SIC_FILE_ReadFile err, ret = %02X\n", ret);
			        return;
			    }
				else{
					for(i = 0; i<64; i++)
					{
						System.out.printf("0x%x,", filebuf[i]);
						if(i != 0 && (i+1)%20 == 0)
						{
							System.out.print("\n");
						}	
					}
					System.out.print("\n");
				}
	        }
			else if(select == 9){	
	        	System.out.print("input dir name:");
	        	enumDirName = sc.nextLine();
	        	enumDirName = sc.nextLine();
	        	System.out.print("input file name:");
	        	fileName = sc.nextLine();
	        	ret = key_api.FM_SIC_FILE_GetInfo(key_api.hDev[0], enumDirName, fileName, fileInfo);
				if (ret != 0){
					System.out.printf("FM_SIC_FILE_GetInfo err, ret = %02X\n", ret);
			        return;
			    }
				else{
					System.out.printf("file name:");
					for(i = 0; i<4; i++){
						if(fileInfo[i] != 0){
							System.out.printf("%c", fileInfo[i]);
						}				
					}
					System.out.print("\n");
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