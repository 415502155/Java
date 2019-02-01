package com.shihy.springboot.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shihy.springboot.utils.ReturnMsg;
import com.shihy.springboot.utils.ReturnResult;

import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 文件上传(单文件或多文件上传)
 * @data 2019年1月15日 下午2:13:11
 *
 */
@Slf4j
@Controller
@RequestMapping(value = "/upload")
public class FileUploadController {
	
	@Value("${FILE_MAX_SIZE}")
	private String FILE_MAX_SIZE;
	
	@Value("${UPLOAD_FILE_URL}")
	private String UPLOAD_FILE_URL;
	
	/***
	 * 
	 * @Description: 访问上传页面
	 * @return   
	 * @return String  
	 * @throws @throws
	 */
	@RequestMapping(value = "/index")
	public String UploadPage() {
		return "index";
	}
	/***
	 * 
	 * @Description: 单文件上传
	 * @param file
	 * @return
	 * @throws IOException   
	 * @return ReturnResult  
	 * @throws @throws
	 */
	@PostMapping("/file1")
    @ResponseBody
    public ReturnResult upload1(@RequestParam("file") MultipartFile file) throws IOException {
		if (file == null) {
        	return ReturnResult.error(ReturnMsg.UPLOAD_FILE_EX1.getCode(), ReturnMsg.UPLOAD_FILE_EX1.getMsg());
        }
		log.info("[文件类型] - [{}]", file.getContentType());
        log.info("[文件名称] - [{}]", file.getOriginalFilename());
        log.info("[文件大小] - [{}]", file.getSize());
        if (file.getSize() > Integer.parseInt(FILE_MAX_SIZE)) {
        	return ReturnResult.error(ReturnMsg.UPLOAD_FILE_EX2.getCode(), ReturnMsg.UPLOAD_FILE_EX2.getMsg() + FILE_MAX_SIZE);
        }
        file.transferTo(new File(UPLOAD_FILE_URL + file.getOriginalFilename()));
        Map<String, String> result = new HashMap<>(16);
        result.put("contentType", file.getContentType());
        result.put("fileName", file.getOriginalFilename());
        result.put("fileSize", file.getSize() + "");
        return ReturnResult.success(result);
    }
	/***
	 * 
	 * @Description: 多文件上传
	 * @param files
	 * @return
	 * @throws IOException   
	 * @return ReturnResult  
	 * @throws @throws
	 */
    @PostMapping("/file2")
    @ResponseBody
    public ReturnResult upload2(@RequestParam("file") MultipartFile[] files) throws IOException {
        if (files == null || files.length == 0) {
        	return ReturnResult.error(ReturnMsg.UPLOAD_FILE_EX1.getCode(), ReturnMsg.UPLOAD_FILE_EX1.getMsg());
        }
        List<Map<String, String>> results = new ArrayList<>();
        for (MultipartFile file : files) {
        	if (file.getSize() > Integer.parseInt(FILE_MAX_SIZE)) {
            	return ReturnResult.error(ReturnMsg.UPLOAD_FILE_EX2.getCode(), ReturnMsg.UPLOAD_FILE_EX2.getMsg() + FILE_MAX_SIZE);
            }
            file.transferTo(new File(UPLOAD_FILE_URL + file.getOriginalFilename()));
            Map<String, String> map = new HashMap<>(16);
            map.put("contentType", file.getContentType());
            map.put("fileName", file.getOriginalFilename());
            map.put("fileSize", file.getSize() + "");
            results.add(map);
        }
        return ReturnResult.success(results);
    }
}
