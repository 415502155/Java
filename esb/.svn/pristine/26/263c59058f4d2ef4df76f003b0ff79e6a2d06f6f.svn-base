package cn.edugate.esb.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.edugate.esb.service.UserService;

@Controller
public class ResourceController {
	static Logger logger=LoggerFactory.getLogger(ResourceController.class);
	
	@Autowired
	@Qualifier("fanoutChannel")
	MessageChannel messageChannel;
	
	@Autowired
	@Qualifier("p2p-pollable-channel")
	MessageChannel channel;
	
	@Autowired
	@Qualifier("toRabbit")
	MessageChannel channel2;
	
	@SuppressWarnings("unused")
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	
	@RequestMapping(value = "/resource/404", method = RequestMethod.GET)
	@ResponseBody
	public void resource404(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=UTF-8");
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		String uri = request.getRequestURI();
		String suffixes="avi|mpeg|3gp|mp3|mp4|wav|jpeg|gif|jpg|png|apk|exe|pdf|rar|zip|docx|doc";  
        Pattern pat=Pattern.compile("[\\w]+[\\.]("+suffixes+")");//正则判断  
        Matcher mc=pat.matcher(uri);//条件匹配  
        while(mc.find()){  
	        String substring = mc.group();//截取文件名后缀名  
	        logger.info("substring:", substring);  
	    }
		
		PrintWriter out = response.getWriter();		
		out.print("找不到该文件");
		out.close();
	}
	
	@RequestMapping(value = "/resource/error404", method = RequestMethod.GET)
	@ResponseBody
	public void error404(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html;charset=UTF-8");
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		String uri = request.getRequestURI();
		String suffixes="avi|mpeg|3gp|mp3|mp4|wav|jpeg|gif|jpg|png|apk|exe|pdf|rar|zip|docx|doc";  
        Pattern pat=Pattern.compile("[\\w]+[\\.]("+suffixes+")");//正则判断  
        Matcher mc=pat.matcher(uri);//条件匹配  
        while(mc.find()){  
	        String substring = mc.group();//截取文件名后缀名  
	        logger.info("substring:", substring);  
	    }
		
		PrintWriter out = response.getWriter();		
		out.print("找不到该文件");
		out.close();
	}
}
