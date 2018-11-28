package cn.edugate.esb.web.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import cn.edugate.esb.EduConfig;
import cn.edugate.esb.ResultDwz;
import cn.edugate.esb.entity.App;
import cn.edugate.esb.service.AppService;
import cn.edugate.esb.util.Paging;

/**
 * 
 * @Name: 应用管理controller
 * @Description: 管理第三方应用。
 * @date  2017-4-20
 * @version V1.0
 */
@Controller
@RequestMapping("/manage/app")
public class MappController {
	static Logger logger=LoggerFactory.getLogger(MappController.class);
	
	private AppService appService;
	@SuppressWarnings("unused")
	private EduConfig eduConfig;
	@Autowired
	public void setEduConfig(EduConfig eduConfig) {
		this.eduConfig = eduConfig;
	}
	
	@Autowired
	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam(value = "start", defaultValue = "0") Integer start,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "numPerPage", defaultValue = "25") Integer numPerPage) {
		ModelAndView mav = new ModelAndView("/app/list");
		Paging<App> pages = new Paging<App>();
		pages.setPage(pageNum);
		pages.setStart(start);
		pages.setLimit(numPerPage);
		this.appService.getAppWithPaging(pages);		
		mav.addObject("apps", pages);		
		return mav;
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.GET)
	public ModelAndView goadd() {
		ModelAndView mav = new ModelAndView("/app/add");		
		return mav;
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public @ResponseBody ResultDwz add(@RequestParam String name,@RequestParam String key,@RequestParam String sessionkey
			,@RequestParam String index,@RequestParam String login
			,@RequestParam String loginProcess,@RequestParam String logout
			,@RequestParam String setSession){
		App app = new App();
		app.setName(name);
		app.setKey(key);
		app.setSessionkey(sessionkey);
		app.setIndex(index);
		app.setLogin(login);
		app.setLoginProcess(loginProcess);
		app.setLogout(logout);
		app.setSetSession(setSession);
		this.appService.add(app);
		ResultDwz result = new ResultDwz();
		result.setStatusCode("200");
		result.setMessage("操作成功");
		result.setNavTabId("app_list");
		result.setCallbackType("closeCurrent");
		return result;
	}

	@RequestMapping(value = "/edit",method = RequestMethod.GET)
	public ModelAndView goEdit(@RequestParam Integer appId) {
		ModelAndView mav = new ModelAndView("/app/edit");
		App app = this.appService.getById(appId);		
		mav.addObject("app", app);
		return mav;
	}
	
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	public @ResponseBody ResultDwz editsave(@RequestParam Integer appId,@RequestParam String name,@RequestParam String key,@RequestParam String sessionkey
			,@RequestParam String index,@RequestParam String login
			,@RequestParam String loginProcess,@RequestParam String logout
			,@RequestParam String setSession) {
		App app = this.appService.getById(appId);	
		app.setName(name);
		app.setKey(key);
		app.setSessionkey(sessionkey);
		app.setIndex(index);
		app.setLogin(login);
		app.setLoginProcess(loginProcess);
		app.setLogout(logout);
		app.setSetSession(setSession);
		this.appService.update(app);
		ResultDwz result = new ResultDwz();
		result.setStatusCode("200");
		result.setMessage("操作成功");
		result.setNavTabId("app_list");
		result.setCallbackType("closeCurrent");
		return result;
	}
	
	
	
}
