package sng.controller.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sng.controller.common.BaseAction;
import sng.pojo.Result;
import sng.pojo.ResultEx;
import sng.service.impl.TestServiceImpl;
import sng.util.Paging;

@Controller
@RequestMapping("/testzz")
public class TestController extends BaseAction{
	@Resource
	private TestServiceImpl testServiceImpl;

	@RequestMapping("/test1")
	@ResponseBody
	public Result test1() {
		Paging page=testServiceImpl.test2();
		return ResultEx.success(page);
	}

	

}
