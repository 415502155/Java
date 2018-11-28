package cn.edugate.esb.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import cn.edugate.esb.entity.ExcelRes;
import cn.edugate.esb.service.ResourcesService;
import cn.edugate.esb.util.ExtUtils;
import cn.edugate.esb.util.Utils;

@Component
public class Excel404Filter implements Filter {
	static Logger logger=LoggerFactory.getLogger(Excel404Filter.class);	
	private EduConfig eduConfig;	
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
        Error404ResponseWrapper responseWrapper = new Error404ResponseWrapper(null, response);  
        String path = this.eduConfig.getPath();
        String picpath = path+"/res/excel/";
        String uri = request.getRequestURI();
		String suffixes="xlsx|xls";
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
        	response.setContentType("text/html;charset=UTF-8");
//        	PrintWriter out = response.getWriter();
//        	out.print("找不到该文件");
        }else{
//        	String[] nameArray = substring.split("\\.");
        	
        	try {
        		Integer id = Integer.parseInt(filename);
            	String localfilepath = picpath+Utils.getPathById(id);
    	        File file=new File(localfilepath+substring);
    	        if(!file.isFile())    
    	        {
    	        	ExcelRes picture= this.resourcesService.getExcelRes(id);
    	        	if(picture!=null&&ext.equals(picture.getExt())){
    	        		// 下载文件
    	        		File dir = new File(localfilepath);
    	        		if (!dir.isDirectory()) {
    	        			dir.mkdirs();
    	        		}
    	        		String filepath = this.resourcesService.getRomoteFile(picture.getGroup_name(),picture.getPath(),localfilepath+substring);
    	        		logger.info(filepath);
    	        		FileInputStream inputStream = new FileInputStream(localfilepath+id+"."+ext);
    	        		byte[] btImg = readInputStream(inputStream);// 得到图片的二进制数据
    	                response.setHeader("Content-Type",ExtUtils.getExt(ext));
    	                response.getOutputStream().write(btImg);
    	                response.flushBuffer();
    	        	}else{
    	        		response.setStatus(404);
    	        		response.setContentType("text/html;charset=UTF-8");
//    	            	PrintWriter out = response.getWriter();
//    	            	out.print("找不到该文件");
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
