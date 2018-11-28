package cn.edugate.esb.file;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import cn.edugate.esb.EduConfig;
import cn.edugate.esb.entity.Picture;
import cn.edugate.esb.service.ResourcesService;
import cn.edugate.esb.util.ExtUtils;
import cn.edugate.esb.util.ImageHepler;
import cn.edugate.esb.util.Utils;

public class FilePicture extends HttpServlet {
	
	static Logger logger=LoggerFactory.getLogger(FilePicture.class);	
	private EduConfig eduConfig;	
	private ResourcesService resourcesService;
	
	public EduConfig getEduConfig() {
		return eduConfig;
	}

	public void setEduConfig(EduConfig eduConfig) {
		this.eduConfig = eduConfig;
	}

	public ResourcesService getResourcesService() {
		return resourcesService;
	}

	public void setResourcesService(ResourcesService resourcesService) {
		this.resourcesService = resourcesService;
	}

	private static Map<String, String> extendmap = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;
		{ 
	        put( "small" , "180X180" ); 
	        put( "middle" , "423X423" );
	        put( "big" , "1080X1080" ); 
		}
	};	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public FilePicture() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		proccessPicture(request, response); 
	}

	private void proccessPicture(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
//		PrintWriter out = response.getWriter();
		String path = this.eduConfig.getEduconfig().getPicPath();
        String picpath = path+"/res/pic/";        
        String uri = request.getRequestURI();        
        logger.info("================"+picpath+"============="+uri);
        
		String suffixes="avi|mpeg|3gp|mp3|mp4|wav|jpeg|JPEG|gif|GIF|jpg|JPG|png|PNG|apk|exe|pdf|rar|zip|docx|doc|txt|swf";  
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
        	response.setStatus(404);      
        }else{
        	try {
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
	    	        		
	    	        		logger.info("===========localfilepath========"+localfilepath);
//							String filepath = this.resourcesService.getRomoteFile(picture.getGroup_name(),picture.getPath(),localfilepath+id+"."+ext); 
	    	                URL url = new URL(this.eduConfig.getEduconfig().getFdsurl()+picture.getGroup_name()+"/"+picture.getPath());   
	    	                HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
	    	                conn.setRequestMethod("GET");   
	    	                conn.setConnectTimeout(5 * 1000);   
	    	                InputStream inStream = conn.getInputStream();   
	    	                byte[] data = readInputStream(inStream);   
	    	                FileOutputStream outStream = new FileOutputStream(localfilepath+id+"."+ext);   
	    	                outStream.write(data);   
	    	                outStream.close();
	    	        		logger.info("===========filepath========"+localfilepath+id+"."+ext);
	    	        		
	    	        		FileInputStream inputStream = new FileInputStream(localfilepath+id+"."+ext);
	    	        		byte[] btImg = readInputStream(inputStream);// 得到图片的二进制数据	    	              
	    	                response.setHeader("Content-Type",ExtUtils.getExt(ext));
	    	                response.getOutputStream().write(btImg);
	    	                response.flushBuffer();
	    	        	}else{
	    	        		response.setStatus(404);
	    	        	}
    	        	}else{
    	        		Picture picture= resourcesService.getResPicture(id);
	    	        	if(picture!=null&&ext.equals(picture.getExt())){
	    	        		File sourcefile = new File(localfilepath+id+"."+ext);
	    	        		File targetfile = new File(localfilepath+substring);
	    	        		if(!sourcefile.isFile())
	    	    	        {
//	    	        			this.resourcesService.getRomoteFile(picture.getGroup_name(),picture.getPath(),localfilepath+id+"."+ext);
	    	        			URL url = new URL(this.eduConfig.getEduconfig().getFdsurl()+picture.getGroup_name()+"/"+picture.getPath());   
		    	                HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
		    	                conn.setRequestMethod("GET");   
		    	                conn.setConnectTimeout(5 * 1000);   
		    	                InputStream inStream = conn.getInputStream();   
		    	                byte[] data = readInputStream(inStream);   
		    	                FileOutputStream outStream = new FileOutputStream(localfilepath+id+"."+ext);   
		    	                outStream.write(data);   
		    	                outStream.close();
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
	    	        	}
    	        	}
    	        }else{
    	        	response.setHeader("Content-Type",ExtUtils.getExt(ext));
    	        	FileInputStream inputStream = new FileInputStream(file);
	        		byte[] btImg = readInputStream(inputStream);// 得到图片的二进制数据
	                response.getOutputStream().write(btImg);
	                response.flushBuffer();
    	        }
			} catch (Exception e) {
				response.setStatus(404);
			}        	
        }
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//		out.println("<HTML>");
//		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
//		out.println("  <BODY>");
//		out.print("    This is ");
//		out.print(this.getClass());
//		out.println(", using the POST method");
//		out.println("  </BODY>");
//		out.println("</HTML>");
//		out.flush();
//		out.close();
		proccessPicture(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());  
        this.eduConfig = (EduConfig) applicationContext.getBean("eduConfig"); 
        this.resourcesService = (ResourcesService) applicationContext.getBean("resourcesServiceImpl");
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
