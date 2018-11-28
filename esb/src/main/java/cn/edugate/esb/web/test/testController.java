package cn.edugate.esb.web.test;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.edugate.esb.entity.Field;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.UserAccount;
import cn.edugate.esb.redis.dao.RedisFieldDao;

/***
 * 
 * @Company sjwy
 * @Title: testController.java
 * @Description:
 * @author: shy
 * @date: 2018年10月11日 下午5:12:45
 */

@Controller
@RequestMapping(value = "/web/app")
public class testController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	testService service;
	@Autowired
	RedisFieldDao fileDao;
	@Autowired
	testRedisDao testRedisDao;

	/***
	 * 
	 * @Description: 測試程序
	 * @author: shy
	 * @date: 2018年10月11日 下午5:09:48
	 * @param request
	 * @param orgId
	 *            機構
	 * @return
	 */
	@RequestMapping(value = "/list1")
	@ResponseBody
	public ReturnResult getUserList(HttpServletRequest request,
			@RequestParam(value = "orgId", required = true) Integer orgId) {
		// Integer orgId = 389;
		// Integer orgId = Integer.parseInt(request.getParameter("orgId"));
		List<OrgUser> list = null;
		try {
			list = service.getPrgUserList(orgId);
		} catch (Exception e) {
			log.info("Exception e :" + e);
			return ReturnResult.error(ReturnMsg.ERROR.getCode(), ReturnMsg.ERROR.getMsg());
		}
		log.info("OrgUser List :" + list);
		return ReturnResult.success(list);
	}

	/***
	 * 
	 * @Description:
	 * @author: shy
	 * @date: 2018年10月11日 下午5:12:12
	 * @param request
	 * @param orgId
	 *            机构Id
	 * @return
	 */
	@RequestMapping(value = "/list4")
	@ResponseBody
	public ReturnResult getUserList4(HttpServletRequest request, Integer orgId) {
		// Integer orgId = 389;
		// Integer orgId = Integer.parseInt(request.getParameter("orgId"));
		@SuppressWarnings("rawtypes")
		List<Map> list = null;
		try {
			list = service.getPrgUserList1(orgId);
		} catch (Exception e) {
			log.info("Exception e :" + e);
			return ReturnResult.error(ReturnMsg.ERROR.getCode(), ReturnMsg.ERROR.getMsg());
		}
		log.info("OrgUser List :" + list);
		return ReturnResult.success(list);
	}

	/**
	 * @Description:
	 * @author: shy
	 * @date: 2018年10月11日 下午5:22:08
	 * @return List<OrgUsers>
	 */
	@RequestMapping(value = "/list2")
	@ResponseBody
	public List<OrgUser> getUserList2() {
		Integer orgId = 389;
		List<OrgUser> list = null;
		try {
			list = service.getPrgUserList(orgId);
		} catch (Exception e) {
			log.info("Exception e :" + e);
			return null;
		}
		log.info("OrgUser List :" + list);
		return list;
	}

	@RequestMapping(value = "/list3")
	@ResponseBody
	public ReturnResult getList(int orgID, int fieldType) {

		List<Field> fList = fileDao.getFieldList(orgID, fieldType);
		@SuppressWarnings("unused")
		JSONObject obj = new JSONObject();
		return ReturnResult.success(fList);
	}

	/**
	 * @Description:
	 * @author: shy
	 * @date: 2018年10月11日 下午5:24:09
	 * @param targetid
	 * @param version
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/list5")
	@ResponseBody
	public ReturnResult getData(@RequestParam(value = "targetid") String targetid,
			@RequestParam(value = "version") String version, @RequestParam(value = "type") String type) {

		UserAccount userAccount = testRedisDao.getUserAccount(targetid, version, type);
		return ReturnResult.success(userAccount);
	}
	
	/***
	 * 
	 *  @Description: 
	 *	@author: shy
	 *  @date: 2018年10月12日 上午8:53:36 
	 *  @return
	 */
	@RequestMapping(value = "/list6")
	@ResponseBody
	public ReturnResult getPageInfo(@RequestParam(value = "pageNum") Integer pageNum,
			@RequestParam(value = "pageSize") Integer pageSize,
			@RequestParam(value = "orgId") Integer orgId) {
		PageInfo page = new PageInfo();
		page.setStart(page.getStartSize(pageNum, pageSize));
		//Oracle
		//page.setEnd(page.getEndSize(pageNum, pageSize));
		//Mysql
		page.setEnd(pageSize);
		Integer totalSize = 125;
		page.setTotalPage(page.getSumPage(totalSize, pageSize));
		@SuppressWarnings("rawtypes")
		List<Map> list = null;
		try {
			list = service.getPrgUserList2(orgId, page);
		} catch (Exception e) {
			log.info("Exception e :" + e);
			return ReturnResult.error(ReturnMsg.ERROR.getCode(), ReturnMsg.ERROR.getMsg());
		}
		log.info("");
		log.info("OrgUser List :" + list.toString());
		return ReturnResult.success(totalSize, page.getTotalPage(), list);
	}
	
}
