package sng.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;
import org.cleverframe.fastdfs.client.StorageClient;
import org.cleverframe.fastdfs.model.StorePath;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class UpLoadUtil {
	private static StorageClient storageClient;
	
	public static String upload(MultipartHttpServletRequest multipartRequest,String fileName){
		String filePathName = "";
		BufferedImage bi;
		try {
			MultipartFile file = multipartRequest.getFile(fileName);
			if (file!=null&&!file.isEmpty()) {
				bi = ImageIO.read(file.getInputStream());
				int width = 0;
				int height = 0;
				width = bi.getWidth();
				height = bi.getHeight();
				String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
				InputStream fin = file.getInputStream(); 
				Long length = file.getSize();
				/**
				 * 资源图片来源类型：云平台
				 * public static final Integer PICTURE_TYPE = 3;
				 */
				//filePathName = savePicture(Constant.PICTURE_TYPE,Constant.YES,fin,length.intValue(),ext,width,height);
				filePathName = savePicture(3,Constant.YES,fin,length.intValue(),ext,width,height);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePathName;
	}
	
	public static String savePicture(Integer type, Integer user_id, InputStream inputStream, Integer length, String ext, Integer width,
			Integer height) {
		// TODO Auto-generated method stub
		StorePath storePath = storageClient.uploadFile("group1", inputStream, length, ext);
		if((width==null||width.equals(0))&&(height==null||height.equals(0))){
			String path = "/etc";//this.eduConfig.getEduconfig().getPicPath();
	        String picpath = path+"/res/pic/"; 
	        String localfilepath = picpath+Utils.getPathById(1);//picture.getId()
	        File file=new File(localfilepath+1+"."+"png");//picture.getExt()
	        File targetfile = null;
	        if(!file.isFile()) {
	        	File dir = new File(localfilepath);
        		if (!dir.isDirectory()) {
        			dir.mkdirs();
        		}
				try {
					//URL url = new URL(this.eduConfig.getEduconfig().getFdsurl()+picture.getGroup_name()+"/"+picture.getPath());
					URL url = new URL("");
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
	                conn.setRequestMethod("GET");   
	                conn.setConnectTimeout(5 * 1000);   
	                InputStream inStream = conn.getInputStream();   
	                byte[] data = readInputStream(inStream);   
	                //FileOutputStream outStream = new FileOutputStream(localfilepath+picture.getId()+"."+picture.getExt());   
	                FileOutputStream outStream = new FileOutputStream(localfilepath+"21332"+"."+"jpg");   
	                outStream.write(data);   
	                outStream.close();              
	                //targetfile=new File(localfilepath+picture.getId()+"."+picture.getExt());
	                targetfile=new File(localfilepath+"12347"+"."+"jpg");

				} catch (Exception e) {
				}   
                
	        }else{
	        	targetfile = file;    	        	
	        }
	        BufferedImage bi = null;  
            boolean imgwrong=false;      	            
            try {  
                //读取图片  
                bi = javax.imageio.ImageIO.read(targetfile);  
                try{  
                    //判断文件图片是否能正常显示,有些图片编码不正确  
                    @SuppressWarnings("unused")
					int i = bi.getType();  
                    imgwrong=true;  
                }catch(Exception e){  
                    imgwrong=false;  
                }  
            } catch (Exception ex) {  
                ex.printStackTrace();  
            }
            if(imgwrong){  
            	width = bi.getWidth(); //获得 宽度  
            	height = bi.getHeight(); //获得 高度  
            }else{  
            	width=-1;  
            	height=-1;
            }	        
		}	
		
		//String fileName = picture.getId() + "." + ext;
		String fileName = "aa" + "." + ext;

		return fileName;
	}
	
	private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
