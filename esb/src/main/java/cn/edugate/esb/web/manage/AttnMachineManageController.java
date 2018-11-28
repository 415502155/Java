package cn.edugate.esb.web.manage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edugate.esb.Result;
import cn.edugate.esb.entity.AttendanceMachine;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.service.AttnMachineManageService;
import cn.edugate.esb.service.OrganizationService;
import cn.edugate.esb.util.Paging;

@Controller
@RequestMapping("/manage/attnMachineManage")
public class AttnMachineManageController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrganizationService orgService;
	
	@Autowired
	private AttnMachineManageService attnMachineManageService;
	
	

	/**
	 * 考勤机管理机构列表
	 * @param numPerPage
	 * @param pageNum
	 * @param orgName
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(@RequestParam(value = "numPerPage", defaultValue = "25") Integer numPerPage,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "orgName", defaultValue = "") String orgName, HttpServletRequest request) {
		
		Paging<Organization> paging = new Paging<Organization>();
		System.out.println(paging.getLimit());

		paging.setLimit(numPerPage.intValue());
		paging.setPage(pageNum.intValue());

		System.out.println(orgName);
		
		Organization organization = new Organization();
		organization.setOrg_name_cn(orgName);
		paging = orgService.getOrgListWithPaging(paging, organization);

		ModelAndView mav = new ModelAndView("/attnMachineManage/list");
		mav.addObject("orgList", paging);
		mav.addObject("orgName", orgName);
		return mav;
	}
	
	/**
	 * 显示机构下的考勤机信息
	 * @param orgId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/attnMachineList")
	public ModelAndView attnMachineList(@RequestParam(value = "orgId") Integer orgId, HttpServletRequest request) {
		
		List<AttendanceMachine> attnMachineList = null;
		Organization org = orgService.getOrgById(orgId);
		if (org != null && org.getOrg_id() != null && org.getOrg_id().intValue() > 0) {
			if (!org.getIs_del()) {
				// 查询考勤
				attnMachineList = attnMachineManageService.getAttnMachineList(orgId);
			}
		}

		ModelAndView mav = new ModelAndView("/attnMachineManage/attnMachineList");
		mav.addObject("orgEntity", org);
		mav.addObject("attnMachineList", attnMachineList);
		return mav;
	}
	
	@RequestMapping(value="/openAddAttnMachineDialog")
	public ModelAndView openAddAttnMachineDialog(@RequestParam(value = "orgId") Integer orgId, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/attnMachineManage/addAttnMachine");
		mav.addObject("orgId", orgId);
		
		return mav;
	}
	
	
	/**
	 * 为机构添加考勤机
	 * @param machine
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addAttnMachine", method=RequestMethod.POST)
	@ResponseBody
	public Result addAttnMachine(AttendanceMachine machine, HttpServletRequest request) {
		Result result = new Result();
		
		try {
			int orgId = machine.getOrg_id().intValue();
			if (orgId <= 0) {
				throw new Exception("请传递正确的机构Id！");
			}
			
			String deviceCode = machine.getSerial_number();
			if (StringUtils.isBlank(deviceCode)) {
				throw new Exception("请输入正确的考勤机ID！");
			}
			
			String place = machine.getPlace();
			if (StringUtils.isBlank(place)) {
				throw new Exception("请输入考勤机存放地点！");
			}
			
			// 查询设备编号是否已经存在
			int count = attnMachineManageService.getCountOfDeviceCode(deviceCode);
			if (count > 0) {
				throw new Exception("输入的考勤机ID已经存在！");
			}
			
			// 先查询是否已经存在考勤机，如果不存在则新建考勤机后需要配置默认的年级考勤，如果已经存在考勤机，则不需要再配置默认考勤设置
			List<AttendanceMachine> attnMachineList = attnMachineManageService.getAttnMachineList(orgId);
			if (attnMachineList != null && attnMachineList.size() > 0) {
				int saveResult = attnMachineManageService.saveAttnMachine(machine, true);
			} else {
				int saveResult = attnMachineManageService.saveAttnMachine(machine, false);
			}
			
			
			result.setSuccess(true);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setSuccess(false);
			result.setMessage(ex.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value="/deleteAttnMachine")
	@ResponseBody
	public Result deleteAttnMachine(@RequestParam(value = "orgId") Integer orgId, @RequestParam(value = "recordId") Integer recordId, HttpServletRequest request) {
		Result result = new Result();
		
		try {
			if (orgId <= 0) {
				throw new Exception("请传递正确的机构Id！");
			}
			
			if (recordId <= 0) {
				throw new Exception("请传递正确的考勤机记录Id！");
			}
			
			attnMachineManageService.deleteAttnMachine(orgId, recordId);
			
			result.setSuccess(true);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setSuccess(false);
			result.setMessage(ex.getMessage());
		}
		
		return result;
	}
	
	
}
