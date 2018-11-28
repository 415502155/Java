package cn.edugate.esb.web.manage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edugate.esb.entity.Feedback;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.service.FeedbackService;
import cn.edugate.esb.service.OrganizationService;
import cn.edugate.esb.util.Paging;


@Controller
@RequestMapping("/manage/feedback")
public class MFeedbackController {
	
	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * 查询带分页的意见反馈列表
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,
			@RequestParam(value = "org_name", defaultValue="") String org_name,
			@RequestParam(value = "org_id", defaultValue="") String org_id,
			@RequestParam(value="start",defaultValue="0") Integer start,
			@RequestParam(value="pageNum",defaultValue="1") Integer pageNum,
			@RequestParam(value="numPerPage",defaultValue="25") Integer numPerPage,
			HttpServletResponse response, HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView("/feedback/index");
		Paging<Feedback> page = new Paging<Feedback>();
		page.setPage(pageNum);
		page.setStart(start);
		page.setLimit(numPerPage);	
		Feedback feedback = new Feedback();
		feedback.setOrg_name(org_name);
		if(StringUtils.isNotBlank(org_id)){
			feedback.setOrg_id(Integer.parseInt(org_id));
		}
		page = feedbackService.getList(feedback,page);
		List<Organization> orgs = this.organizationService.getOrgList();
		mav.addObject("orgs", orgs);
		mav.addObject("data", page);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}
}
