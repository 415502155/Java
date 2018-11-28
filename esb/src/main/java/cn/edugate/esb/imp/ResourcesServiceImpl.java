package cn.edugate.esb.imp;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.cleverframe.fastdfs.client.StorageClient;
import org.cleverframe.fastdfs.client.TrackerClient;
import org.cleverframe.fastdfs.model.StorePath;
import org.cleverframe.fastdfs.protocol.storage.callback.DownloadFileWriter;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.EduConfig;
import cn.edugate.esb.dao.IExcelResDao;
import cn.edugate.esb.dao.IPictureDao;
import cn.edugate.esb.entity.ExcelRes;
import cn.edugate.esb.entity.Picture;
import cn.edugate.esb.file.EduDownloadFileWriter;
import cn.edugate.esb.service.ResourcesService;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

/**
 * 资源实现类 Title:ResourcesServiceImpl Description: Company:SJWY
 * 
 * @author:Huangcf
 * @Date:2017年6月11日上午10:19:41
 */
@Component
@Transactional("transactionManager")
public class ResourcesServiceImpl implements ResourcesService {

	private static Logger logger = Logger.getLogger(ResourcesServiceImpl.class);

	private IPictureDao pictureDao;

	private StorageClient storageClient;

	private TrackerClient trackerClient;

	private Util util;

	private IExcelResDao excelResDao;
	
	private EduConfig eduConfig;	
	
	@Autowired
	public void setEduConfig(EduConfig eduConfig) {
		this.eduConfig = eduConfig;
	}

	@Autowired
	public void setExcelResDao(IExcelResDao excelResDao) {
		this.excelResDao = excelResDao;
	}

	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Autowired
	@Qualifier("fastDfsStorageClient")
	public void setStorageClient(StorageClient storageClient) {
		this.storageClient = storageClient;
	}

	@Autowired
	@Qualifier("fastDfsTrackerClient")
	public void setTrackerClient(TrackerClient trackerClient) {
		this.trackerClient = trackerClient;
	}

	@Autowired
	public void setPictureDao(IPictureDao pictureDao) {
		this.pictureDao = pictureDao;
	}

	@Override
	public String uploadfile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Picture getResPicture(Integer id) {
		// TODO Auto-generated method stub
		return this.pictureDao.get(Picture.class, id);
	}

	@Override
	public String getRomoteFile(String group_name, String path, String localfilepath) {
		// TODO Auto-generated method stub
		EduDownloadFileWriter<String> downloadFileWriter = new EduDownloadFileWriter<String>(localfilepath);
		
		String filePath = storageClient.downloadFile(group_name, path, downloadFileWriter);
		return filePath;
	}

	@Override
	public String savePicture(Integer type, Integer user_id, InputStream inputStream, Integer length, String ext, Integer width,
			Integer height) {
		// TODO Auto-generated method stub
		StorePath storePath = storageClient.uploadFile("group1", inputStream, length, ext);

		Picture picture = new Picture();
		picture.setType(type);
		picture.setUser_id(user_id);
		picture.setGroup_name(storePath.getGroup());
		picture.setPath(storePath.getPath());
		picture.setExt(ext);
		picture.setWidth(width);
		picture.setHeight(height);
		picture.setDeleted(false);
		picture.setCreated_time(new Date());
		picture.setUpdated_time(new Date());
		this.pictureDao.save(picture);
		if((width==null||width.equals(0))&&(height==null||height.equals(0))){
			String path = this.eduConfig.getEduconfig().getPicPath();
	        String picpath = path+"/res/pic/"; 
	        String localfilepath = picpath+Utils.getPathById(picture.getId());
	        File file=new File(localfilepath+picture.getId()+"."+picture.getExt());
	        File targetfile = null;
	        if(!file.isFile()) {
	        	File dir = new File(localfilepath);
        		if (!dir.isDirectory()) {
        			dir.mkdirs();
        		}
				try {
					URL url = new URL(this.eduConfig.getEduconfig().getFdsurl()+picture.getGroup_name()+"/"+picture.getPath());
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
	                conn.setRequestMethod("GET");   
	                conn.setConnectTimeout(5 * 1000);   
	                InputStream inStream = conn.getInputStream();   
	                byte[] data = readInputStream(inStream);   
	                FileOutputStream outStream = new FileOutputStream(localfilepath+picture.getId()+"."+picture.getExt());   
	                outStream.write(data);   
	                outStream.close();              
	                targetfile=new File(localfilepath+picture.getId()+"."+picture.getExt());
				} catch (Exception e) {
					logger.error("===========downloadfile===error========="+picture.getId());
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
	        picture.setWidth(width);
	        picture.setHeight(height);  	        
		}	
		
		
		
		this.pictureDao.save(picture);

		String fileName = picture.getId() + "." + ext;

		return fileName;
	}

	@Override
	public ExcelRes getExcelRes(Integer id) {
		return this.excelResDao.get(ExcelRes.class, id);
	}

	@Override
	public String saveExcel(Integer type, Integer user_id, InputStream inputStream, Integer length, String ext, Integer org_id,
			String message, Byte proce_type,Boolean finished) {
		// TODO Auto-generated method stub
		StorePath storePath = storageClient.uploadFile("group1", inputStream, length, ext);

		ExcelRes excelRes = new ExcelRes();
		excelRes.setType(type);
		excelRes.setUser_id(user_id);
		excelRes.setGroup_name(storePath.getGroup());
		excelRes.setPath(storePath.getPath());
		excelRes.setExt(ext);
		excelRes.setResult_id(0);
		excelRes.setFinished(finished);
		excelRes.setProce_type(proce_type);
		excelRes.setOrg_id(org_id);
		excelRes.setMessage(Utils.getValue(message));
		excelRes.setDeleted(false);
		excelRes.setUpdated_time(new Date());
		excelRes.setCreated_time(new Date());
		this.excelResDao.save(excelRes);

		String fileName = excelRes.getId() + "." + ext;

		return fileName;
	}

	@Override
	public void updateExcelResEntity(ExcelRes excelResEntity) {
		excelResDao.update(excelResEntity);
	}

	@Override
	public List<ExcelRes> getExcelResList(int type, int user_id, int org_id, int proce_type) {
		return excelResDao.getExcelResList(type, user_id, org_id, proce_type);
	}

	@Override
	public List<Picture> getPictures() {
		// TODO Auto-generated method stub
		Criterion criterion = Restrictions.conjunction();
		return this.pictureDao.list(Picture.class, criterion, Order.desc("id"));
	}

	@Override
	public void savePicture(Picture picture) {
		// TODO Auto-generated method stub
		this.pictureDao.saveOrUpdate(picture);
	}
	
	private byte[] readInputStream(InputStream inStream) throws Exception {
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