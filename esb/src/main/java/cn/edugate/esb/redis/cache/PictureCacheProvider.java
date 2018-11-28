package cn.edugate.esb.redis.cache;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.EduConfig;
import cn.edugate.esb.entity.Picture;
import cn.edugate.esb.redis.Cache;
import cn.edugate.esb.redis.CacheProvider;
import cn.edugate.esb.redis.dao.RedisPictureDao;
import cn.edugate.esb.service.ResourcesService;
import cn.edugate.esb.util.Utils;

/**
 * 年级缓存
 * Title:GradeCacheProvider
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月23日下午6:06:14
 */
@Cache(name="图片缓存",entities={Picture.class})
public class PictureCacheProvider implements CacheProvider<Picture>,java.io.Serializable {

	private static final long serialVersionUID = 1098076108225687840L;

	static Logger logger=LoggerFactory.getLogger(PictureCacheProvider.class);
	
	private RedisPictureDao redisPictureDao;
	
	private ResourcesService resourcesService;
	
	private EduConfig eduConfig;	
	
	@Autowired
	public void setEduConfig(EduConfig eduConfig) {
		this.eduConfig = eduConfig;
	}

	@Autowired
	public void setResourcesService(ResourcesService resourcesService) {
		this.resourcesService = resourcesService;
	}

	@Autowired
	public void setRedisPictureDao(RedisPictureDao redisPictureDao) {
		this.redisPictureDao = redisPictureDao;
	}	
	
	@Override
	public void add(Picture picture) {
		logger.info("pictureRedis===add====",picture.getId());
		this.redisPictureDao.add(picture);
	}

	@Override
	public void update(Picture picture) {
		logger.info("gradeRedis===update====",picture.getId());
		Picture oldPicture = this.redisPictureDao.getPictureById(picture.getId(),null);
		if(oldPicture!=null)
			this.redisPictureDao.delete(oldPicture);
		this.redisPictureDao.add(picture);
	}

	@Override
	public void delete(Picture grade) {
		logger.info("gradeRedis===delete====",grade);
		this.redisPictureDao.delete(grade);
	}

	@Override
	public void refreshAll() {
		this.redisPictureDao.deleteAll();
		List<Picture> pictures = this.resourcesService.getPictures();
		for (Picture picture : pictures) {
			if((picture.getWidth()==null||picture.getWidth().equals(0))&&(picture.getHeight()==null||picture.getHeight().equals(0))){
				String path = this.eduConfig.getEduconfig().getPicPath();
		        String picpath = path+"/res/pic/"; 
		        String localfilepath = picpath+Utils.getPathById(picture.getId());
    	        File file=new File(localfilepath+picture.getId()+"."+picture.getExt());
    	        File targetfile = null;
    	        int width = 0;
		        int height = 0;
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
    	        this.resourcesService.savePicture(picture);    	        
			}
		}
		this.redisPictureDao.add(pictures);	
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

	@Override
	public void refreshOrg(String org_id) {
		this.refreshAll();
	}

}
