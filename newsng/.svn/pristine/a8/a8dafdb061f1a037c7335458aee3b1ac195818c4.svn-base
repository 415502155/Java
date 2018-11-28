package sng.controller.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import sng.service.BaseAdminService;
import sng.service.TokenService;
import sng.util.Base64;
import sng.util.RedisUtil;

@Controller
@RequestMapping("/admin/")
public class MainController extends BaseAction{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	
	@Autowired
	RedisUtil redisUtil;
	
	private BaseAdminService baseAdminService;	

	@Autowired
	public void setBaseAdminService(BaseAdminService baseAdminService) {
		this.baseAdminService = baseAdminService;
	}

	@RequestMapping(value = "/fileupload.htm")
	@ResponseBody
	public Map<String, Object> fileupload(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String token = request.getParameter("token");
		String udid = request.getParameter("udid");
		String org_id = request.getParameter("org_id");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file");
		String filename = "";
		if(file.getSize()>0){
			BufferedImage bi = ImageIO.read(file.getInputStream());
			int width = 0;
	        int height = 0;
			width = bi.getWidth();
	        height = bi.getHeight();
			String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);		
			ByteArrayOutputStream out = new ByteArrayOutputStream(); 
			ImageIO.write(bi, ext, out); 
	        byte[] b = out.toByteArray(); 
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ext", ext);
			map.put("width", width);
			map.put("height", height);
			map.put("file", Base64.encode(b));
			if(token!=null&&!"".equals(token)){
				map.put("token", token);
			}
			if(udid!=null&&!"".equals(udid)){
				map.put("udid", udid);
			}
			if(org_id!=null&&!"".equals(org_id)){
				map.put("org_id", org_id);
			}
			filename = this.baseAdminService.uploadBase64NoSession(request, "/uploadBase64", map);			
		}
		Map<String, Object> error = new HashMap<String, Object>();
		error.put("code", 102);
		error.put("message", "Failed to open output stream");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("jsonrpc", "2.0");
		result.put("result", filename);
		result.put("id", filename);
		return result;
	}
	
}
