package sng.controller.web;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sng.controller.common.BaseAction;
import sng.entity.Vouchermodel;
import sng.pojo.Result;
import sng.pojo.ResultEx;
import sng.pojo.SessionInfo;
import sng.service.ClassService;
import sng.service.VoucherModelService;
import sng.util.Constant;

/***
 * 
 *  @Company sjwy
 *  @Title: VoucherModelController.java 
 *  @Description: 凭证模板管理
 *	@author: shy
 *  @date: 2018年11月1日 下午2:47:18
 */
@Controller
@RequestMapping("/voucher/model")
public class VoucherModelController extends BaseAction {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VoucherModelService voucherModelService;
	@Autowired
	private ClassService classService;
	/***
	 * 
	 *  @Description:  添加或修改 凭证模板内容
	 *  @param request
	 *  @param orgId
	 *  @param voucherContent 凭证模板内容
	 *  @param picName 图片名称
	 *  @param length 长度
	 *  @param width  宽度
	 *  @param voucherModelId 模板id
	 *  @param response
	 *  @return
	 *  @throws ParseException
	 */
	@RequestMapping(value = "/addOrUpdate.htm")
	@ResponseBody
	public Result<?> addVoucherModel(HttpServletRequest request, Integer org_id, String voucherContent, 
			String picName, Integer length, Integer width, Integer voucherModelId, HttpServletResponse response) throws ParseException {
		SessionInfo sessionInfo = this.getSessionInfo(request);
		Integer orgId = org_id;
		if (null == orgId) {
			orgId = sessionInfo.getOrgId();
		}
		Integer num = voucherModelService.VouModelNum(sessionInfo, orgId);
		try {
			if (num == 0) {
				voucherModelService.save(orgId, picName, voucherContent, length, width);
			} else {
				voucherModelService.update(orgId, picName, voucherContent, voucherModelId, length, width);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("addVoucherModel Ex :" + e);
			return ResultEx.error(Constant.ERROR_CODE, Constant.ERROR_MSG, "");
		}
		return ResultEx.success("");
	}
	
	/***
	 * 
	 *  @Description: 上传模板图片
	 *  @param request
	 *  @param voucherContent
	 *  @param response
	 *  @return
	 *  @throws ParseException
	 */
	@RequestMapping(value = "/upload.htm")
	@ResponseBody
	public Result<?> uploadVoucherModelImg(HttpServletRequest request, String voucherContent, HttpServletResponse response) throws ParseException {
//		SessionInfo sessionInfo=this.getSessionInfo(request);
		String filePathName = "";
//		try {
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//			filePathName = UpLoadUtil.upload(multipartRequest, "voucher");
//		} catch (Exception e) {
//			log.info("uploadVoucherModelImg Ex :" + e);
//			ReturnResult.error();
//		}
		return ResultEx.success(filePathName);
	}
	
	/***
	 * 
	 *  @Description: 获取凭证模板信息
	 *  @param request
	 *  @param orgId
	 *  @param response
	 *  @return
	 *  @throws ParseException
	 */
	@RequestMapping(value = "/info.htm")
	@ResponseBody
	public Result<?> getVoucher(HttpServletRequest request, Integer org_id, HttpServletResponse response) throws ParseException {
		SessionInfo sessionInfo = this.getSessionInfo(request);
		Integer orgId = org_id;
		if (null == orgId) {
			orgId = sessionInfo.getOrgId();
		}
		List<Vouchermodel>  vouList = null;
		Vouchermodel vou = null;
		try {
			vouList = voucherModelService.getVouModel(sessionInfo, orgId);
			/**
			 * 获取模板
			 */
			if (null != vouList && vouList.size() > 0) {
				vou = vouList.get(0);
			} else {
				return ResultEx.success("模板数据为空,请先设置凭证模板", null);
			}
		} catch (Exception e) {
			log.info("getList Ex :" + e);
			return ResultEx.error(Constant.ERROR_CODE, Constant.ERROR_MSG, "");
		}
		return ResultEx.success(vou);
	}
	
	/***
	 * 
	 *  @Description: 我的凭证
	 *  @param request
	 *  @param orgId
	 *  @param phone
	 *  @param response
	 *  @return
	 *  @throws ParseException
	 */
	@RequestMapping(value = "/vocher/vlist.htm")
	@ResponseBody
	public Result<?> getVoucherList(HttpServletRequest request, Integer org_id, String phone, HttpServletResponse response) throws ParseException {
		SessionInfo sessionInfo=this.getSessionInfo(request);
		Integer orgId = org_id;
		if (null == orgId) {
			orgId = sessionInfo.getOrgId();
		}
		Integer isMain = 1;//1:主家长 0：不是
		List<Map<String, Object>>  vouList = null;
		try {
			vouList = voucherModelService.getVoucherList(phone, isMain, orgId);
		} catch (Exception e) {
			log.info("getVoucherList Ex :" + e);
			e.printStackTrace();
			return ResultEx.error(Constant.ERROR_CODE, Constant.ERROR_MSG, "");
		}
		return ResultEx.success(vouList);
	}
	
	
	/***
	 * 
	 *  @Description: 我的凭证
	 *  @param request
	 *  @param orgId
	 *  @param phone
	 *  @param response
	 *  @return
	 *  @throws ParseException
	 */
	@RequestMapping(value = "/myVoucher.htm")
	@ResponseBody
	public Result<?> getMyVoucher(HttpServletRequest request, 
			Integer org_id, Integer studId, String userIdnumber, String techName, Integer clasId) {
		SessionInfo sessionInfo = this.getSessionInfo(request);
		Integer orgId = org_id;
		if (null == orgId) {
			orgId = sessionInfo.getOrgId();
		}
		Map<String, Object> myCoucher = null;
		try {
			myCoucher = classService.getMyCoucher(orgId, studId, userIdnumber, techName, clasId);
			if (myCoucher == null) {
				return ResultEx.success("该学员无法打印凭证;","");
			}
		} catch (Exception e) {
			log.info("getMyVoucher Ex :" + e);
			e.printStackTrace();
			return ResultEx.error(Constant.ERROR_CODE, Constant.ERROR_MSG, "");
		}
		return ResultEx.success(myCoucher);
	}
	
	
	/***
	 * 
	 *  @Description: 我的凭证(批量)
	 *  @param request
	 *  @param orgId
	 *  @param phone
	 *  @param response
	 *  @return
	 * @throws Exception 
	 *  @throws ParseException
	 */
	@RequestMapping(value = "/batchVoucher.htm")
	@ResponseBody
	public Result<?> getBatchMyVoucher(HttpServletRequest request, Integer org_id, String studClasIds) throws Exception {
		SessionInfo sessionInfo = this.getSessionInfo(request);
		Integer orgId = org_id;
		if (null == orgId) {
			orgId = sessionInfo.getOrgId();
		}
		List<Map<String, Object>> myCoucherList = classService.getMyCoucherList(orgId, studClasIds);
		if (myCoucherList != null && myCoucherList.size() > 0 ) {
			return ResultEx.success(myCoucherList);
		} else {
			return ResultEx.error(Constant.ERROR_CODE, "请先设置凭证模板。", "");
		}
	}
}
