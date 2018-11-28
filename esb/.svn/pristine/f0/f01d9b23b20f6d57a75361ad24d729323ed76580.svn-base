package cn.edugate.esb.web.manage;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.read.biff.BiffException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.edugate.esb.EduConfig;
import cn.edugate.esb.ResultDwz;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.pojo.BackMenuPojo;
import cn.edugate.esb.service.CacheService;
import cn.edugate.esb.service.ManageService;
import cn.edugate.esb.service.OrganizationService;
import cn.edugate.esb.service.UserService;
import cn.edugate.esb.util.ExcelUtil;
import cn.edugate.esb.redis.CacheInfo;
import cn.edugate.esb.redis.CacheObject;

/**
 * 
 * @Name: 后台管理controller
 * @Description: 后台管理。
 * @date  2017-4-20
 * @version V1.0
 */
@Controller
@RequestMapping("/manage")
public class ManageController {

	static Logger logger=LoggerFactory.getLogger(ManageController.class);
	private ManageService manageService;
	private EduConfig eduConfig;
	private CacheService cacheService;
	private UserService userService;	
	private OrganizationService organizationService;
	
	
	
	@Autowired
	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	@Autowired
	public void setEduConfig(EduConfig eduConfig) {
		this.eduConfig = eduConfig;
	}

	@Autowired
	public void setManageService(ManageService manageService) {
		this.manageService = manageService;
	}

	@RequestMapping(value = "/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView("/manage/login");
		return mav;
	}
	
	@RequestMapping(value = "/index")
	public ModelAndView mindex() {
		ModelAndView mav = new ModelAndView("/index");
		return mav;
	}
	@RequestMapping(value = "/main/index")
	public ModelAndView main() {
		ModelAndView mav = new ModelAndView("/main/index");	
//		List<Entry> entrys = this.manageService.getEntrys();
		mav.addObject("apptitle", this.eduConfig.getEduconfig().getWebName());

		return mav;
	}
	@RequestMapping(value="/main/getTree")	
	public @ResponseBody List<BackMenuPojo> getTree() {
		logger.info("/manage/main/getTree===========================");	
		List<BackMenuPojo> menus = this.manageService.getBackMenus();
		return menus;
	}
	
	@RequestMapping(value = "/redis/list")
	public ModelAndView redislist(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/redis/list");
		List<CacheInfo<?>> list = cacheService.getCacheInfos();
		List<Organization> orgs = this.organizationService.getOrgList();
		mav.addObject("orgs", orgs);
		mav.addObject("list", list);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}
	
	@RequestMapping(value = "/redis/refresh")
	@ResponseBody
	public ResultDwz redisRefresh() {
		ResultDwz result = new ResultDwz();
		List<CacheInfo<?>> infos = this.cacheService.getCacheInfos();
		for (CacheInfo<?> cacheInfo : infos) {
			cacheInfo.refreshAll();
		}
		result.setStatusCode("200");
		result.setMessage("操作成功");
		result.setNavTabId("redis_list");
		return result;
	}
	@RequestMapping(value = "/redis/refreshBykey")
	@ResponseBody
	public ResultDwz refreshBykey(@RequestParam String cacheKey) {		
		ResultDwz result = new ResultDwz();		
		CacheObject<?> info = this.cacheService.getCacheInfo(cacheKey);
		info.refreshAll();
		result.setStatusCode("200");
		result.setMessage("操作成功");
		result.setNavTabId("redis_list");
		return result;
	}
	
	@RequestMapping(value = "/redis/refreshOrgBykey")
	@ResponseBody
	public ResultDwz refreshOrgBykey(@RequestParam String cacheKey,@RequestParam String org_id) {		
		ResultDwz result = new ResultDwz();		
		CacheObject<?> info = this.cacheService.getCacheInfo(cacheKey);
		info.refreshOrg(org_id);
		result.setStatusCode("200");
		result.setMessage("操作成功");
		result.setNavTabId("redis_list");
		return result;
	}
	
	@RequestMapping(value = "/synclianzhi",method = RequestMethod.GET)
	public ModelAndView synclianzhiget() {
		ModelAndView mav = new ModelAndView("/user/synclianzhi");
		return mav;
	}
	@RequestMapping(value = "/synclianzhi",method = RequestMethod.POST)
	@ResponseBody
	public ResultDwz synclianzhi(HttpServletRequest request,HttpServletResponse response) throws IOException, BiffException {		
		ResultDwz result = new ResultDwz();	
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("uploadData");
		if (file==null||file.isEmpty()) {
			result.setMessage("导入失败");
			return result;
		} 
        
		InputStream fin = file.getInputStream(); 
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		map.put("手机号","user_mobile");
		map.put("连枝号","user_number");
		
		List<OrgUser> ls = ExcelUtil.excelToList(fin, OrgUser.class, map);	
		for (OrgUser orgUser : ls) {
			if(orgUser.getUser_mobile()!=null&&!"".equals(orgUser.getUser_mobile())){
				List<OrgUser> ous = this.userService.getOrgUserByMobile(orgUser.getUser_mobile());
				for (OrgUser ou : ous) {
					ou.setUser_number(orgUser.getUser_number());
					this.userService.SaveOrgUser(ou);
				}
			}
		}
		result.setStatusCode("200");
		result.setMessage("操作成功");
		
		return result;
	}
	
	
}
