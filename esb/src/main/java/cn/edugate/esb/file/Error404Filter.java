package cn.edugate.esb.file;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.edugate.esb.EduConfig;
import cn.edugate.esb.entity.Picture;
import cn.edugate.esb.service.ResourcesService;
import cn.edugate.esb.util.ExtUtils;
import cn.edugate.esb.util.ImageHepler;
import cn.edugate.esb.util.Utils;

@Component
public class Error404Filter implements Filter {
	static Logger logger=LoggerFactory.getLogger(Error404Filter.class);	
	private EduConfig eduConfig;	
	
	private static Map<String, String> extendmap = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;
		{ 
	        put( "small" , "180X180" ); 
	        put( "middle" , "423X423" );
	        put( "big" , "1080X1080" ); 
		}
	};
	
	@Autowired
	public void setEduConfig(EduConfig eduConfig) {
		this.eduConfig = eduConfig;
	}

	private ResourcesService resourcesService;
	
	@Autowired
	public void setResourcesService(ResourcesService resourcesService) {
		this.resourcesService = resourcesService;
	}

	@Override  
    public void destroy() {  
    }  
  
    @Override  
    public void init(FilterConfig arg0) throws ServletException {  
    }  
  
    @Override  
    public void doFilter(ServletRequest req, ServletResponse rep,  
            FilterChain chain) throws IOException, ServletException {  
        HttpServletRequest request = (HttpServletRequest) req;  
        HttpServletResponse response = (HttpServletResponse) rep;
        
        Error404ResponseWrapper responseWrapper = new Error404ResponseWrapper(this, response);  
        String path = this.eduConfig.getPath();
        String picpath = path+"/res/pic/";        
        String uri = request.getRequestURI();        
        logger.info("================"+picpath+"============="+uri);
        
		String suffixes="avi|mpeg|3gp|mp3|mp4|wav|jpeg|gif|jpg|png|apk|exe|pdf|rar|zip|docx|doc|txt|swf";  
        Pattern pat=Pattern.compile("([\\w]+)[\\.]("+suffixes+")");//正则判断  
        Matcher mc=pat.matcher(uri);//条件匹配  
        String substring = "";
        String filename = "";
        String ext = "";
        while(mc.find()){  
	        substring = mc.group();//截取文件名后缀名
	        filename = mc.group(1);
	        ext = mc.group(2);
	    }
        if("".equals(substring)){
        	response.setContentType("text/html;charset=UTF-8");
        	PrintWriter out = response.getWriter();
        	out.print("找不到该文件");
        }else{
//        	String[] nameArray = substring.split("\\.");
        	
        	try {
//        		String aa = "16X16";
        		int index = filename.indexOf("_");
        		String idstr = "";
        		String extend = "";
        		
        		if(index>0){
        			idstr = filename.substring(0,index);
        			extend = filename.substring(index+1);
        		}else{
        			idstr = filename;
        		}
        		
        		Integer id = Integer.parseInt(idstr);
            	String localfilepath = picpath+Utils.getPathById(id);
    	        File file=new File(localfilepath+substring);
    	        Map<String,Integer> extmap = new HashMap<String,Integer>();
    	        
    	        if(!file.isFile())    
    	        {
    	        	if(!this.hasExtend(extend,extmap)){
	//    	        	String ext = nameArray[1];
	    	        	Picture picture= resourcesService.getResPicture(id);
	    	        	if(picture!=null&&ext.equals(picture.getExt())){
	    	        		// 下载文件
	    	        		File dir = new File(localfilepath);
	    	        		if (!dir.isDirectory()) {
	    	        			dir.mkdirs();
	    	        		}
	    	        		@SuppressWarnings("unused")
							String filepath = this.resourcesService.getRomoteFile(picture.getGroup_name(),picture.getPath(),localfilepath+id+"."+ext);
	    	        		FileInputStream inputStream = new FileInputStream(localfilepath+id+"."+ext);
	    	        		byte[] btImg = readInputStream(inputStream);// 得到图片的二进制数据	    	              
	    	                response.setHeader("Content-Type",ExtUtils.getExt(ext));
	    	                response.getOutputStream().write(btImg);
	    	                response.flushBuffer();
	    	        	}else{
	    	        		response.setStatus(404);
	    	        		response.setContentType("text/html;charset=UTF-8");
//	    	            	PrintWriter out = response.getWriter();
//	    	            	out.print("找不到该文件");
	    	        	}
    	        	}else{
    	        		Picture picture= resourcesService.getResPicture(id);
	    	        	if(picture!=null&&ext.equals(picture.getExt())){
	    	        		File sourcefile = new File(localfilepath+id+"."+ext);
	    	        		File targetfile = new File(localfilepath+substring);
	    	        		if(!sourcefile.isFile())
	    	    	        {
	    	        			this.resourcesService.getRomoteFile(picture.getGroup_name(),picture.getPath(),localfilepath+id+"."+ext);
	    	    	        }
    	    	        	BufferedImage bitmap = ImageIO.read(sourcefile);
    	    	        	ImageHepler.zoom(bitmap, extmap.get("width"), extmap.get("height"), ext, targetfile);	    	    	        
	    	        		response.setHeader("Content-Type",ExtUtils.getExt(ext));
	    	        		FileInputStream inputStream = new FileInputStream(targetfile);
	    	        		byte[] btImg = readInputStream(inputStream);// 得到图片的二进制数据
	    	                response.getOutputStream().write(btImg);
	    	                response.flushBuffer();
	    	        	}else{
	    	        		response.setStatus(404);
	    	        		response.setContentType("text/html;charset=UTF-8");
//	    	            	PrintWriter out = response.getWriter();
//	    	            	out.print("找不到该文件");
	    	        	}
    	        	}
    	        }else{
    	        	chain.doFilter(request, responseWrapper);
    	        }
			} catch (Exception e) {
				response.setStatus(404);
				response.setContentType("text/html;charset=UTF-8");
//            	PrintWriter out = response.getWriter();
//            	out.print("找不到该文件");
			}        	
        } 
    }
    
    private boolean hasExtend(String extend,Map<String,Integer> extmap) {
		// TODO Auto-generated method stub.
    	Integer width=0;
    	Integer height=0;
    	if("".equals(extend)){
    		return false;
    	}else{
    		if(extendmap.containsKey(extend)){
    			extend = extendmap.get(extend);
    		}
    		Pattern pat=Pattern.compile("([\\d]+)X([\\d]+)");//正则判断  
	        Matcher mc=pat.matcher(extend);//条件匹配  
	        while(mc.find()){  
	        	width = Integer.parseInt(mc.group(1)) ;//截取文件名后缀名
	        	height = Integer.parseInt(mc.group(2));
		    }
	        if(width>0&&height>0){
		        extmap.put("width", width);
		    	extmap.put("height", height);
		    	return true;
	        }	    	
    	}
    	return false;
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
