package com.bestinfo.tf.read;

import com.fmkeyjni.key_api;

import java.util.Scanner;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;

public class Cert{
	public static int FM_CERT_ENC = 1;
	public static int FM_KEYNUM_ENC = 3;
	public static void Cert_MainMenu()throws Exception{
		int ret;
		int byteread;
		Scanner sc = new Scanner(System.in);
		String fileName;
		String containerName;
		byte[] certDataTmp = new byte[2048];
		byte[] keyID = new byte[4];
		int[] len = new int[1];
		byte[] containerNames = new byte[1024];
		int[] containerCount = new int[1];
		int i;
	    
	    while (true){
			System.out.print("\n");
			System.out.print("####################################################\n");
			System.out.print("#      1. Write Cert                               #\n");
			System.out.print("#      2. Read Cert                                #\n");
			System.out.print("#      3. Delete Container                         #\n");
			System.out.print("#      4. Enum Container                           #\n");
			System.out.print("#      0. Return                                   #\n");
			System.out.print("####################################################\n");
			System.out.print("\n");
			
			System.out.print("Please select:");
			int select = sc.nextInt();

	        if (select == 1){
				System.out.print("input cert type(0:RSA/1:ECC):");
				int certType = sc.nextInt();
				System.out.print("input cert file path:");
				fileName = sc.nextLine();
				fileName = sc.nextLine();
				System.out.print("input Container name:");
				containerName = sc.nextLine();
									
				FileInputStream fis = new FileInputStream(fileName);
				int fLength = fis.available();
				byte[] certData = new byte[fLength];
				fis.read(certData, 0, fLength);
				fis.close();				
				
				if(certType == 1)
				{
					//����֤��
					ret = key_api.FM_SIC_ContainerWriteECCCert(key_api.hDev[0],FM_CERT_ENC, containerName, certData, certData.length);
					if (ret != 0){
						System.out.printf("FM_SIC_ContainerWriteECCCert err, ret = %02X\n", ret);
				        return;
				    }
					else{
		                System.out.print("succeed\n");
		            }
				}
				else if(certType == 0)
				{
					//����֤��
					ret = key_api.FM_SIC_ContainerWrite(key_api.hDev[0],FM_CERT_ENC, containerName, certData, certData.length);
					if (ret != 0){
						System.out.printf("FM_SIC_ContainerWrite err, ret = %02X\n", ret);
				        return;
				    }
					else{
		                System.out.printf("succeed!certData.length = %d\n", certData.length);
		            }
				}
				
	        }
	        else if (select == 2){
	        	System.out.print("input Container name:");
	        	containerName = sc.nextLine();
	        	containerName = sc.nextLine();
	        	ret = key_api.FM_SIC_ContainerRead(key_api.hDev[0], FM_CERT_ENC, containerName, certDataTmp, len);
				if (ret != 0){
					System.out.printf("FM_SIC_ContainerRead err, ret = %02X\n", ret);
			        return;
			    }
	            else{
	                System.out.printf("succeed!certData len:%d\n", len[0]);
	            }
				
				String path = System.getenv("SystemRoot");
				path = path + "\\TmpCert.cer";
				
				try{
			        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(path)));   
			        outputStream.write(certDataTmp);   
			        outputStream.flush();   
			        outputStream.close(); 
				}catch(Exception e){
					e.printStackTrace();
				}
				//��Ĭ�ϳ�����ļ�
				Runtime.getRuntime().exec("cmd.exe /c"+path);				
	        }
			else if (select == 3){
	        	System.out.print("input Container name:");
	        	containerName = sc.nextLine();
	        	containerName = sc.nextLine();
			    ret = key_api.FM_SIC_ContainerDelete(key_api.hDev[0], containerName);
				if (ret != 0){
					System.out.printf("FM_SIC_ContainerDelete err, ret = %02X\n", ret);
			        return;
			    }
				else{
	                System.out.print("succeed\n");
	            }
	        }
			else if (select == 4){
				ret = key_api.FM_SIC_ContainerEnum(key_api.hDev[0], containerNames, containerCount);
				if (ret != 0){
					System.out.printf("FM_SIC_ContainerEnum err, ret = %02X\n", ret);
			        return;
			    }
				else{
					System.out.print(" succeed\n");
					for(i = 0; i < containerCount[0]; i++)
					{
						if(containerNames[i] == 0)
							System.out.print(" ");
						else
							System.out.printf("%c", containerNames[i]);
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