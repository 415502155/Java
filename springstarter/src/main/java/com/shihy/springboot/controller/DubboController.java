/*package com.shihy.springboot.controller;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shihy.springboot.service.DubboService;
import com.shihy.springboot.utils.ReturnResult;
*//***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 
 * @data 2019年4月3日 上午11:38:23
 *
 *//*
@RestController
@RequestMapping(value = "/dubbo")
public class DubboController {
	@Resource
	DubboService dubboService;
	@RequestMapping(value = "/info")
	public ReturnResult getDubboInfo() {
		Map<String, Object> dubboInfo = dubboService.getDubboInfo();
		return ReturnResult.success(dubboInfo);
	}
}
*/