package cn.edugate.esb.web.manage;


import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.pojo.Data;
import cn.edugate.esb.service.DataService;
import cn.edugate.esb.service.OrganizationService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.ExcelUtil;


/**
 * 数据统计
 * Title: MDataController
 * Description: 
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年5月13日上午11:37:24
 */
@Controller
@RequestMapping("/manage/data")
public class MDataController {
	
	static Logger logger=LoggerFactory.getLogger(MDataController.class);

	@Autowired
	private DataService dataService;
	@Autowired
	private OrganizationService organizationService;
	
	@RequestMapping(value = "/index")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/data/index");
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}

	/**
	 * 导出数据统计
	 */
	@RequestMapping(value = "/download")
 	public void downloadChargeDetails(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		String type = request.getParameter("type");
		String area = request.getParameter("area");
		String ids = request.getParameter("ids");
		List<Data> list = dataService.getList(Integer.parseInt(type), area, ids);
		LinkedHashMap<String,String> map = Constant.getExcelMap(type);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fileName = getExcelName(type);
		String finalFileName = "";
		final String userAgent = request.getHeader("USER-AGENT"); 
        if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器  
	        finalFileName = URLEncoder.encode(fileName,"UTF8");  
	    }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器  
	        finalFileName = new String(fileName.getBytes(), "ISO8859-1");  
	    }else{  
	        finalFileName = URLEncoder.encode(fileName,"UTF8");//其他浏览器  
	    } 
		if(null!=list&&list.size()!=0){
			ExcelUtil.listToExcel(list, map, getExcelName(type)+sdf.format(new Date()), response, finalFileName+sdf.format(new Date()));
		} else {
			List<Data> newList = new ArrayList<Data>();
			Data data = new Data();
			data.setId(0);
			newList.add(data);
			ExcelUtil.listToExcel(newList,  map, getExcelName(type)+sdf.format(new Date()), response, finalFileName+sdf.format(new Date()));
		}
	}

	/**
	 * 去下载页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goDownload",method = RequestMethod.GET)
	public ModelAndView goUserAdd(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/data/download");
		String type = request.getParameter("type");
		mav.addObject("ctx", request.getContextPath());
		List<Organization> orgs = this.organizationService.getOrgList();
		mav.addObject("orgs", new JSONArray(orgs));
		mav.addObject("type", type);
		return mav;
	}
	
	private String getExcelName(String type){
		String name = "统计文件";
		switch (type) {
		case "1":
			name = "班级关注率";
			break;
		case "2":
			name = "班级博客";
			break;
		case "3":
			name = "工资发布";
			break;
		case "4":
			name = "校园通知";
			break;
		case "5":
			name = "学校综合关注情况";
			break;
		case "6":
			name = "学校支付情况";
			break;
		case "7":
			name = "学生家长关注详情";
			break;
		case "8":
			name = "教师关注详情";
			break;
		case "9":
			name = "角色教师关注统计";
			break;
		default:
			break;
		}
		return name;
	}
}
