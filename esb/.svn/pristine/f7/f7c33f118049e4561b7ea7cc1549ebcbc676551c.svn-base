package cn.edugate.esb.web.manage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.edugate.esb.Result;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.UpgradeHistory;
import cn.edugate.esb.pojo.OrgClassUpgradeInfo;
import cn.edugate.esb.redis.dao.RedisGradeDao;
import cn.edugate.esb.service.ClassUpgradeService;
import cn.edugate.esb.util.ImportExcelUtil;
import cn.edugate.esb.util.Paging;
import cn.edugate.esb.util.Utils;

@Controller
@RequestMapping("/manage/classUpgrade")
public class ClassUpgradeController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ClassUpgradeService classUpgradeService;
	
	@Autowired
	private RedisGradeDao redisGradeDao;
	
	
	@RequestMapping(value="/list")
	public ModelAndView list(@RequestParam(value = "numPerPage", defaultValue = "25") Integer numPerPage,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "orgName", defaultValue = "") String orgName,
			@RequestParam(value = "prov", defaultValue = "") String prov,
			@RequestParam(value = "city", defaultValue = "") String city,
			@RequestParam(value = "dist", defaultValue = "") String dist,
			@RequestParam(value = "prov4Paging", defaultValue = "") String prov4Paging,
			@RequestParam(value = "city4Paging", defaultValue = "") String city4Paging,
			@RequestParam(value = "dist4Paging", defaultValue = "") String dist4Paging,
			@RequestParam(value = "upgradeStatus", defaultValue = "") String upgradeStatus, HttpServletRequest request) {
		Paging<OrgClassUpgradeInfo> paging = new Paging<OrgClassUpgradeInfo>();
		System.out.println(paging.getLimit());

		paging.setLimit(numPerPage.intValue());
		paging.setPage(pageNum.intValue());

		System.out.println(orgName);

		List<OrgClassUpgradeInfo> upgradeInfoList = null;
		
		if ("null".equals(prov) && "null".equals(city) && "null".equals(dist)) {
			// 是点击的分页标签
			prov = prov4Paging;
			city = city4Paging;
			dist = dist4Paging;
		} else {
			prov4Paging = prov;
			city4Paging = city;
			dist4Paging = dist;
		}

		String area4Query = "";
		if (StringUtils.isNotBlank(dist) && !"null".equals(dist)) {
			area4Query = prov + "_" + city + "_" + dist;
		} else if (StringUtils.isNotBlank(city) && !"null".equals(city)) {
			area4Query = prov + "_" + city;
		} else if (StringUtils.isNotBlank(prov) && !"null".equals(prov)) {
			area4Query = prov;
		}

		// 查询机构是否在当前年份已升级
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);

		int totalNum = classUpgradeService.getTotalRecordNum4ClassUpgrade(0, orgName, area4Query, upgradeStatus, year);

		if (totalNum > 0) {
			int offset = (paging.getPage() - 1) * paging.getLimit();
			int rows = paging.getLimit();
			List<Organization> orgList = classUpgradeService.getOrgList4Paging(0, orgName, area4Query, upgradeStatus, year,
					offset, rows);
			if (orgList != null && orgList.size() > 0) {
				upgradeInfoList = new ArrayList<OrgClassUpgradeInfo>(orgList.size());

				for (Organization org : orgList) {
					OrgClassUpgradeInfo info = new OrgClassUpgradeInfo();
					// BeanUtils.copyProperties(org, info);
					info.setOrg_id(org.getOrg_id());
					info.setOrg_name_cn(org.getOrg_name_cn());
					String areaInDB = org.getArea();
					if (StringUtils.isNotBlank(areaInDB)) {
						String[] temp = areaInDB.split("_");
						if ("null".equals(temp[2])) {
							info.setCity(temp[0]);
							info.setDistrict(temp[1]);
						} else {
							info.setProvince(temp[0]);
							info.setCity(temp[1]);
							info.setDistrict(temp[2]);
						}
					}

					// 获取学校中的所有年级，显示学校的年级类型
					List<Grade> gradeList = redisGradeDao.getGrades(org.getOrg_id());
					if (gradeList != null && gradeList.size() > 0) {
						Set<Integer> typeSet = new HashSet<Integer>();
						for (Grade g : gradeList) {
							typeSet.add(g.getGrade_type());
						}

						if (typeSet.size() > 0) {
							String gradeType4Display = "";
							for (Integer type : typeSet) {
								switch (type) {
									case 1:
										gradeType4Display += "幼儿园/";
										break;
									case 2:
										gradeType4Display += "小学/";
										break;
									case 3:
										gradeType4Display += "初中/";
										break;
									case 4:
										gradeType4Display += "高中/";
										break;
								}
							}
							gradeType4Display = gradeType4Display.substring(0, gradeType4Display.length() - 1);
							info.setGradeType(gradeType4Display);
						}
					}

					UpgradeHistory upgradeHistory = classUpgradeService.getUpgradeHistory(org.getOrg_id(), year);
					if (upgradeHistory != null && upgradeHistory.getId() != null && upgradeHistory.getId().intValue() > 0) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
						info.setUpgradeDate(sdf.format(upgradeHistory.getUpgrade_date()));
						info.setUpgradeStatus("已升级");
					} else {
						info.setUpgradeDate("--");
						info.setUpgradeStatus("未升级");
					}

					upgradeInfoList.add(info);
				}
			}
		} else {
			upgradeInfoList = new ArrayList<OrgClassUpgradeInfo>(0);
		}

		paging.setData(upgradeInfoList);
		paging.setSuccess(true);
		paging.setTotal(totalNum);

		ModelAndView mav = new ModelAndView("/classupgrade/list");
		mav.addObject("orgList", paging);
		mav.addObject("orgName", orgName);
		mav.addObject("upgradeStatus", upgradeStatus);
		mav.addObject("prov", prov);
		mav.addObject("city", city);
		mav.addObject("dist", dist);
		mav.addObject("prov4Paging", prov4Paging);
		mav.addObject("city4Paging", city4Paging);
		mav.addObject("dist4Paging", dist4Paging);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}
	
	
	/**
	 * 批量升级
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/batchUpgrade", method=RequestMethod.POST)
	@ResponseBody
	public Result<Map<String, List<String>>> batchUpgrade(HttpServletRequest request) {
		Result<Map<String, List<String>>> result = new Result<Map<String, List<String>>>();
		String orgIds = request.getParameter("orgIds");
		
		if (StringUtils.isNotBlank(orgIds)) {
			String[] orgIdArr = orgIds.split(",");
			Map<String, List<String>> orgUpgradeInfoMap = classUpgradeService.batchUpgrade(orgIdArr);
			
			result.setData(orgUpgradeInfoMap);
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
			result.setMessage("orgIds参数不能为空！");
		}
		
		return result;
	}
	
	/**
	 * 导出页面搜索条件下的所有学校
	 * @param numPerPage
	 * @param pageNum
	 * @param orgName
	 * @param prov
	 * @param city
	 * @param dist
	 * @param prov4Paging
	 * @param city4Paging
	 * @param dist4Paging
	 * @param upgradeStatus
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportSchoolList")
	public void exportSchoolList(@RequestParam(value = "numPerPage", defaultValue = "25") Integer numPerPage,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "orgName", defaultValue = "") String orgName,
			@RequestParam(value = "prov", defaultValue = "") String prov,
			@RequestParam(value = "city", defaultValue = "") String city,
			@RequestParam(value = "dist", defaultValue = "") String dist,
			@RequestParam(value = "prov4Paging", defaultValue = "") String prov4Paging,
			@RequestParam(value = "city4Paging", defaultValue = "") String city4Paging,
			@RequestParam(value = "dist4Paging", defaultValue = "") String dist4Paging,
			@RequestParam(value = "upgradeStatus", defaultValue = "") String upgradeStatus, HttpServletRequest request,
			HttpServletResponse response) {

		if ("null".equals(prov) && "null".equals(city) && "null".equals(dist)) {
			// 是点击的分页标签
			prov = prov4Paging;
			city = city4Paging;
			dist = dist4Paging;
		} else {
			prov4Paging = prov;
			city4Paging = city;
			dist4Paging = dist;
		}

		String area4Query = "";
		if (StringUtils.isNotBlank(dist) && !"null".equals(dist)) {
			area4Query = prov + "_" + city + "_" + dist;
		} else if (StringUtils.isNotBlank(city) && !"null".equals(city)) {
			area4Query = prov + "_" + city;
		} else if (StringUtils.isNotBlank(prov) && !"null".equals(prov)) {
			area4Query = prov;
		}

		// 查询机构是否在当前年份已升级
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);

		List<Organization> orgList = null;
		List<String[]> excelData = null;
		int totalNum = classUpgradeService.getTotalRecordNum4ClassUpgrade(0, orgName, area4Query, upgradeStatus, year);
		if (totalNum > 0) {
			orgList = classUpgradeService.getOrgList(0, orgName, area4Query, upgradeStatus, year);
			if (orgList != null && orgList.size() > 0) {
				excelData = new ArrayList<String[]>(orgList.size());

				for (Organization org : orgList) {
					String[] excelRow = new String[3];
					excelRow[0] = String.valueOf(org.getOrg_id().intValue());
					excelRow[1] = org.getOrg_name_cn();

					UpgradeHistory upgradeHistory = classUpgradeService.getUpgradeHistory(org.getOrg_id(), year);
					if (upgradeHistory != null && upgradeHistory.getId() != null && upgradeHistory.getId().intValue() > 0) {
						excelRow[2] = "已升级";
					} else {
						excelRow[2] = "未升级";
					}

					excelData.add(excelRow);
				}
			}
		}

		// 如果没有学校数据则只生成表头
		String fileName = year + "年" + totalNum + "所学校升级导出表.xls";
		String sheetName = "学校列表";
		String[] columnName = { "学校ID", "学校标准名称", "升级状态" };

		// 生产下载的文件
		try {
			Workbook wb = Utils.makeWorkbook(sheetName, columnName, excelData, "xls");
			Utils.makeAndExportExcelFile(response, wb, fileName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("生成excel时发生错误！" + e.toString());
		}
	}
	
	
	/*
	 * 打开上传excel页面
	 */
	@RequestMapping(value="/openImportExcelDialog")
	public ModelAndView openImportExcelDialog(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/classupgrade/import");
		
		return mav;
	}
	
	/**
	 * 进行上传
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "doImportExcel")
	@ResponseBody
	public Result<Map<String, List<String>>> doImportExcel(HttpServletRequest request) {
		Result<Map<String, List<String>>> result = new Result<Map<String, List<String>>>();
		MultipartFile file = Utils.getExcelFileFromRequest(request);

		String[] standardColumnName = { "学校ID", "学校标准名称" };
		// String[] resultColumnName = { "学校ID", "学校标准名称", "升级结果信息" };

		Workbook wb = null;
		String returnInfo = null;
		try {
			if (file != null) {// 若有上传的文件，则将Excel中的信息封装为mapList，并返回
				wb = new HSSFWorkbook(file.getInputStream());
				if (wb != null) {
					// 验证标题行
					String headLineCheckResult = ImportExcelUtil.verificationImportExcelHeadLine(wb, standardColumnName);
					if (StringUtils.isBlank(headLineCheckResult)) {
						List<String[]> excelData = ImportExcelUtil.getUpgradeDataFromExcel(wb);
						wb = null;

						if (excelData != null && excelData.size() > 0) {
							Map<String, List<String>> resultMap = classUpgradeService.validateAndUpgrade(excelData);

							/*
							 * List<String[]> exportData = new ArrayList<String[]>(excelData.size()); for (Map.Entry<String,
							 * List<String>> entry : resultMap.entrySet()) { // System.out.println(entry.getKey() + ":" +
							 * entry.getValue()); String key = entry.getKey(); List<String> messageList = entry.getValue();
							 * String[] rowData = new String[3]; rowData[0] = key.split("_")[0]; rowData[1] = key.split("_")[1];
							 * String message = ""; if (messageList != null && messageList.size() > 0) { StringBuilder
							 * messageBuilder = new StringBuilder(""); for (String str : messageList) {
							 * messageBuilder.append(str).append("\r\n"); } message = messageBuilder.substring(0,
							 * messageBuilder.length()-2); } rowData[2] = message; exportData.add(rowData); }
							 */

							result.setSuccess(true);
							result.setData(resultMap);
						} else {
							returnInfo = "没有在上传文件中发现有效数据行！";
						}
					} else {
						returnInfo = headLineCheckResult;
					}
				} else {
					returnInfo = "上传文件转换成Workbook对象时出错！";
				}
			} else {
				returnInfo = "无法取得上传的Excel文件！";
			}

			if (StringUtils.isNotBlank(returnInfo)) {
				result.setSuccess(false);
				result.setMessage(returnInfo);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}
	
	
	@RequestMapping(value="/openClassesDegradeDialog")
	public ModelAndView openClassesDegradeDialog() {
		ModelAndView mav = new ModelAndView("/classupgrade/classesDegrade");
		return mav;
	}
	
	
	@RequestMapping(value="/classesDegrade", method=RequestMethod.POST)
	@ResponseBody
	public Result<List<String>> classesDegrade(HttpServletRequest request) {
		Result<List<String>> result = new Result<List<String>>();
		
		String org_id = request.getParameter("org_id");
		String degradeClassesInfo = request.getParameter("degradeClassesInfo");
		
		if (StringUtils.isNotBlank(org_id) && StringUtils.isNotBlank(degradeClassesInfo)) {
			String[] degradeClassInfoArr = degradeClassesInfo.split(";");
			if (degradeClassInfoArr != null && degradeClassInfoArr.length > 0) {
				List<String> messageList = classUpgradeService.classesDegrade(Integer.valueOf(org_id), degradeClassInfoArr);
				result.setData(messageList);
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}
		}
		
		return result;
	}
}
