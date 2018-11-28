package cn.edugate.esb.web.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edugate.esb.EduConfig;
import cn.edugate.esb.service.ManageService;

/**
 * 
 * @Name: 应用管理controller
 * @Description: 管理第三方应用。
 * @date  2017-4-20
 * @version V1.0
 */
@Controller
@RequestMapping("/manage/application")
public class MapplicationController {
	static Logger logger=LoggerFactory.getLogger(MapplicationController.class);
	
	@SuppressWarnings("unused")
	private ManageService manageService;
	@SuppressWarnings("unused")
	private EduConfig eduConfig;
	@Autowired
	public void setEduConfig(EduConfig eduConfig) {
		this.eduConfig = eduConfig;
	}

	@Autowired
	public void setManageService(ManageService manageService) {
		this.manageService = manageService;
	}

	@RequestMapping(value = "/list")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView("/application/list");
		return mav;
	}
	
	
	
}
