package cn.edugate.esb.web.manage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.edugate.esb.EduConfig;
import cn.edugate.esb.ResultDwz;
import cn.edugate.esb.entity.Campus;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.KeyValue;
import cn.edugate.esb.entity.OrgTree;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.redis.dao.RedisOrgUserDao;
import cn.edugate.esb.redis.dao.RedisOrganizationDao;
import cn.edugate.esb.service.KeyValueService;
import cn.edugate.esb.service.OrganizationService;
import cn.edugate.esb.service.ResourcesService;
import cn.edugate.esb.service.UserService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Paging;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

/**
 * 
 * @Name: 机构管理controller
 * @Description:
 * @date 2017-5-19
 * @version V1.0
 */
@Controller
@RequestMapping("/manage/organization")
public class MorganizationController {

	static Logger logger = LoggerFactory.getLogger(MorganizationController.class);

	@Autowired
	private OrganizationService orgService;

	@Autowired
	private UserService userService;

	@Autowired
	private RedisOrgUserDao redisOrgUserDao;

	@Autowired
	private RedisOrganizationDao redisOrganizationDao;

	@Autowired
	private EduConfig eduConfig;

	@Autowired
	private ResourcesService resourcesService;

	@Autowired
	private KeyValueService keyValueService;

	@Autowired
	private Util util;
	/**
	 * 机构查询分页+搜索
	 * @param numPerPage
	 * @param pageNum
	 * @param orgName
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
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
		ModelAndView mav = new ModelAndView("/organization/list");
		mav.addObject("orgList", paging);
		mav.addObject("org", organization);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}
	
	/**
	 * 跳转到添加机构页面
	 * @return
	 */
	@RequestMapping(value = "/goToAddOrgPage")
	public ModelAndView add() {
		ModelAndView mav = new ModelAndView("/organization/add");
		List<KeyValue> uiList = keyValueService.getListByType(1);
		mav.addObject("uiList", uiList);
		return mav;
	}
	
	/**
	 * 添加机构
	 * @param organization
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addOrganization")
	@ResponseBody
	public ResultDwz addOrganization(Organization organization, HttpServletRequest request) {
		
		List<Grade> gradeList = new ArrayList<Grade>();
		if (organization.getType() == 0) {
			String grade4AddOrg = request.getParameter("grade4AddOrg");
			if (StringUtils.isNotBlank(grade4AddOrg)) {
				String [] gradeArray = grade4AddOrg.split("_");
				
				if (gradeArray != null && gradeArray.length > 0) {
					for (int i = 0; i < gradeArray.length; i++) {
						String gradeName = gradeArray[i];
						Grade grade = Utils.getGradeEntity(gradeName);
						if (grade != null) {
							gradeList.add(grade);
						}
					}
				}
			}
		}
		organization.setGradeList(gradeList);
		
		String mainCampusName = request.getParameter("main_campus_name");
		String mainCampusAddress = request.getParameter("main_campus_address");
		
		String[] campusNameArray = request.getParameterValues("campus_name");
		String[] campusAddressArray = request.getParameterValues("campus_address");
		
		List<Campus> campusList = new ArrayList<Campus>();
		
		// 教育局类型的机构不建立校区
		if (organization.getType() == 0 || organization.getType() == 2) {
			if (StringUtils.isBlank(mainCampusName)) {
				mainCampusName = "主校区";
			} else {
				mainCampusName = mainCampusName.trim();
			}
			Campus campus = new Campus();
			campus.setCam_name(mainCampusName);
			campus.setCam_note(Utils.getValue(mainCampusAddress));
			campus.setCam_type(0);
			campusList.add(campus);
			
			if (campusNameArray != null && campusNameArray.length > 0) {
				// 处理页面填写的校区信息
				for (int i=0;i<campusNameArray.length;i++) {
					String name = campusNameArray[i];
					String adress = campusAddressArray[i];
					
					if (StringUtils.isNotBlank(name)) {
						Campus c = new Campus();
						c.setCam_name(name.trim());
						c.setCam_note(Utils.getValue(adress));
						campus.setCam_type(1);
						campusList.add(c);
					}
				}
			}
			organization.setIs_campus(1);
		} else {
			organization.setIs_campus(0);
		}
		organization.setCampusList(campusList);
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String welcome = util.uploadSWF(multipartRequest, "org_welcome_upload");
		if(StringUtils.isNotEmpty(welcome)){
			organization.setWelcome(welcome);
		}
		
		// 进行保存机构操作
		String result = "";
		//try {
			result = orgService.saveOrganization(organization);
		//} catch (Exception ex) {
		//	result = ex.getMessage();
		//}
		
		
		if (result.startsWith("success")) {
			String orgID = result.substring(8, result.length());
			organization.setOrg_id(Integer.valueOf(orgID));
			String errorMsg = "";
			// 先进行数据库保存，如果保存成功则进行组织的logo上传（因为需要得到组织的ID)
			// 检查form是否有enctype="multipart/form-data"
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				MultipartFile logoFile  = multiRequest.getFile("org_logo_upload");
				List<MultipartFile> bgFileList  = multiRequest.getFiles("org_bg_upload");
				
				System.out.println(logoFile);
				System.out.println(bgFileList.size());
				
				
				boolean logoFlag = false;
				int bgFlag = 0;
				
				if (logoFile != null) {
					// 上传机构logo
					try {
						BufferedImage bi = ImageIO.read(logoFile.getInputStream());
						int width = 0;
						int height = 0;
						width = bi.getWidth();
						height = bi.getHeight();
						String ext = logoFile.getOriginalFilename().substring(logoFile.getOriginalFilename().lastIndexOf(".") + 1);
						InputStream fin = logoFile.getInputStream();
						Long length = logoFile.getSize();
						String fileName = this.resourcesService.savePicture(Constant.PICTURE_TYPE, Integer.parseInt(orgID), fin, length.intValue(), ext, width, height);
						
						organization.setLogo(fileName);
						int updateResult = orgService.updateOrganizationLogo(organization);
						logoFlag = true;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if (!logoFlag) {
						errorMsg = "上传机构Logo";
					}
					
				}
				if (bgFileList != null && bgFileList.size() > 0) {
					String fileNameStr = "";
					for (MultipartFile file : bgFileList) {
						try {
							BufferedImage bi = ImageIO.read(file.getInputStream());
							int width = 0;
							int height = 0;
							width = bi.getWidth();
							height = bi.getHeight();
							String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
							InputStream fin = file.getInputStream();
							Long length = file.getSize();
							String fileName = this.resourcesService.savePicture(Constant.PICTURE_TYPE, Integer.parseInt(orgID), fin, length.intValue(), ext, width, height);
							
							fileNameStr += fileName+";";
							bgFlag++;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							break;
						}
					}
					
					if (bgFileList.size() == bgFlag) {
						fileNameStr = fileNameStr.substring(0, fileNameStr.length() - 1);
						organization.setBg(fileNameStr);
						int updateResult = orgService.updateOrganizationBackground(organization);
					}
					
					if (bgFileList.size() != bgFlag) {
						if (errorMsg.length() > 0) {
							errorMsg += "和机构背景图时出现错误";
						} else {
							errorMsg = "上传机构背景图时出现错误";
						}
					}
				}
			}
			
			// 执行批量导入数据的存储过程，暂时忽略失败的情况
			//orgService.executeBatchImport(organization);
			
			ResultDwz resultDWZ = new ResultDwz();
			resultDWZ.setStatusCode("200");
			if (errorMsg.length() > 0) {
				result = "机构【" + organization.getOrg_name_cn() + "】添加成功！但"+errorMsg+"，请在编辑机构时重新上传！";
			} else {
				result = "机构【" + organization.getOrg_name_cn() + "】添加成功！";
			}
			resultDWZ.setMessage(result);
			//resultDWZ.setNavTabId("role");
			resultDWZ.setCallbackType("closeCurrent");
			return resultDWZ;
		} else {
			ResultDwz resultDWZ = new ResultDwz();
			resultDWZ.setStatusCode("300");
			resultDWZ.setMessage(result);
			//resultDWZ.setNavTabId("role");
			resultDWZ.setCallbackType("closeCurrent");
			return resultDWZ;
		}
	}
	
	@RequestMapping(value = "/delete")
	public @ResponseBody ResultDwz delete(@RequestParam(value="id") Integer id) {
		Organization org = redisOrganizationDao.getByOrgId(id.toString(), null);
		org.setIs_del(true);
		org.setDel_time(new Date());
		orgService.update(org);
		List<OrgUser> list = redisOrgUserDao.getUserByOrgId(id);
		userService.deleteOrgUser(list);
		ResultDwz result = new ResultDwz();
		result.setStatusCode("200");
		result.setMessage("删除成功");
		result.setNavTabId("org");
		return result;
	}
	
	/**
	 * 跳转到机构编辑页面
	 * @param orgID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goToEditOrgPage")
	public ModelAndView goToEditOrgPage(@RequestParam Integer orgID, HttpServletRequest request) {
		Organization orgEntity = orgService.getOrgAllInfo(orgID);
		
		if (StringUtils.isNotBlank(orgEntity.getLogo())) {
			orgEntity.setLogoUrl(util.getPathByPicName(orgEntity.getLogo()));
		} else {
			orgEntity.setLogoUrl("");
		}
		
		List<String> bgUrlList = new ArrayList<String>();
		
		if (StringUtils.isNotBlank(orgEntity.getBg())) {
			String[] bgNameArray = orgEntity.getBg().split(";");
			for (String bgName : bgNameArray) {
				String bgUrl = util.getPathByPicName(bgName);
				bgUrlList.add(bgUrl);
			}
		}
		orgEntity.setBgUrlList(bgUrlList);
		
		
		
		
		
		
		ModelAndView mav = new ModelAndView("/organization/edit");
		List<KeyValue> uiList = keyValueService.getListByType(1);
		mav.addObject("uiList", uiList);
		if (orgEntity != null) {
			mav.addObject("successFlag", "success");
			mav.addObject("orgEntity", orgEntity);
		} else {
			mav.addObject("successFlag", "fail");
			mav.addObject("orgEntity", orgEntity);
		}
		return mav;
	}
	
	@RequestMapping(value="/editOrganzation")
	@ResponseBody
	public ResultDwz editOrganzation(Organization organization, HttpServletRequest request) {
		
		List<Grade> gradeList = new ArrayList<Grade>();
		if (organization.getType() == 0) {
			String grade4AddOrg = request.getParameter("grade4AddOrg");

			if (StringUtils.isNotBlank(grade4AddOrg)) {
				String[] gradeArray = grade4AddOrg.split("_");

				if (gradeArray != null && gradeArray.length > 0) {
					for (int i = 0; i < gradeArray.length; i++) {
						String gradeName = gradeArray[i];
						Grade grade = Utils.getGradeEntity(gradeName);
						if (grade != null) {
							gradeList.add(grade);
						}
					}
				}
			}
		}
		organization.setGradeList(gradeList);

		String mainCampusID = request.getParameter("main_campus_id");
		String mainCampusName = request.getParameter("main_campus_name");
		String mainCampusAddress = request.getParameter("main_campus_address");
		
		
		String[] campusIDArray = request.getParameterValues("campus_id");
		String[] campusNameArray = request.getParameterValues("campus_name");
		String[] campusAddressArray = request.getParameterValues("campus_address");

		List<Campus> campusList = new ArrayList<Campus>();
		
		// 教育局类型的机构不建立校区
		if (organization.getType() == 0 || organization.getType() == 2) {
			// 处理主校区
			Campus campus = new Campus();
			if(StringUtils.isNotEmpty(mainCampusID))
				campus.setCam_id(Integer.valueOf(mainCampusID));
			if (StringUtils.isBlank(mainCampusName)) {
				mainCampusName = "主校区";
			} else {
				mainCampusName = mainCampusName.trim();
			}
			campus.setCam_name(mainCampusName);
			campus.setCam_note(Utils.getValue(mainCampusAddress));
			campus.setCam_type(0);

			campusList.add(campus);

			// 处理分校区
			if (campusNameArray != null && campusNameArray.length > 0) {
				for (int i = 0; i < campusNameArray.length; i++) {
					String id = campusIDArray[i];

					String name = campusNameArray[i];
					String adress = campusAddressArray[i];

					if (StringUtils.isNotBlank(id)) {
						// 存在ID，是已经保存过的校区信息
						campus = new Campus();
						campus.setCam_id(Integer.valueOf(id));
						campus.setCam_name(name.trim());
						campus.setCam_note(adress);
						campus.setCam_type(1);

						campusList.add(campus);
					} else {
						if (StringUtils.isNotBlank(name)) {
							campus = new Campus();
							campus.setCam_name(name.trim());
							campus.setCam_note(adress);
							campus.setCam_type(1);

							campusList.add(campus);
						}
					}
				}
			}
			organization.setIs_campus(1);
		} else {
			organization.setIs_campus(0);
		}
		
		organization.setCampusList(campusList);

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String welcome = util.uploadSWF(multipartRequest, "org_welcome_upload");
		if(StringUtils.isNotEmpty(welcome)){
			organization.setWelcome(welcome);
			orgService.updateOrganizationWelCome(organization);
		}
		
		// 进行编辑机构的保存
		String result = orgService.updateOrganization(organization);
		
		if ("success".equals(result)) {
			
			String errorMsg = "";
			// 先进行数据库保存，如果保存成功则进行组织的logo上传（因为需要得到组织的ID)
			// 检查form是否有enctype="multipart/form-data"
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				MultipartFile logoFile  = multiRequest.getFile("org_logo_upload");
				
				List<MultipartFile> bgFileList  = multiRequest.getFiles("org_bg_upload");
				
				System.out.println(logoFile);
				System.out.println(bgFileList.size());
				
				
				boolean logoFlag = false;
				int bgFlag = 0;
				
				if (logoFile != null) {
					// 上传机构logo
					try {
						BufferedImage bi = ImageIO.read(logoFile.getInputStream());
						int width = 0;
						int height = 0;
						width = bi.getWidth();
						height = bi.getHeight();
						String ext = logoFile.getOriginalFilename().substring(logoFile.getOriginalFilename().lastIndexOf(".") + 1);
						InputStream fin = logoFile.getInputStream();
						Long length = logoFile.getSize();
						String fileName = this.resourcesService.savePicture(3, organization.getOrg_id(), fin, length.intValue(), ext, width, height);
						
						organization.setLogo(fileName);
						int updateResult = orgService.updateOrganizationLogo(organization);
						logoFlag = true;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if (!logoFlag) {
						errorMsg = "上传机构Logo";
					}
					
				}
				
				if (bgFileList != null && bgFileList.size() > 0) {
					String fileNameStr = "";
					for (MultipartFile file : bgFileList) {
						try {
							BufferedImage bi = ImageIO.read(file.getInputStream());
							int width = 0;
							int height = 0;
							width = bi.getWidth();
							height = bi.getHeight();
							String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
							InputStream fin = file.getInputStream();
							Long length = file.getSize();
							String fileName = this.resourcesService.savePicture(3, organization.getOrg_id(), fin, length.intValue(), ext, width, height);
							
							fileNameStr += fileName+";";
							bgFlag++;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							break;
						}
					}
					
					if (bgFileList.size() == bgFlag) {
						fileNameStr = fileNameStr.substring(0, fileNameStr.length() - 1);
						organization.setBg(fileNameStr);
						int updateResult = orgService.updateOrganizationBackground(organization);
					}
					
					if (bgFileList.size() != bgFlag) {
						if (errorMsg.length() > 0) {
							errorMsg += "和机构背景图时出现错误";
						} else {
							errorMsg = "上传机构背景图时出现错误";
						}
					}
				}
			}
			
			ResultDwz resultDWZ = new ResultDwz();
			resultDWZ.setStatusCode("200");
			if (errorMsg.length() > 0) {
				result = "机构【" + organization.getOrg_name_cn() + "】保存成功！但"+errorMsg+"，请在编辑机构时重新上传！";
			} else {
				result = "机构【" + organization.getOrg_name_cn() + "】保存成功！";
			}
			resultDWZ.setMessage(result);
			//resultDWZ.setNavTabId("role");
			resultDWZ.setCallbackType("closeCurrent");
			return resultDWZ;
		} else {
			ResultDwz resultDWZ = new ResultDwz();
			resultDWZ.setStatusCode("300");
			resultDWZ.setMessage(result);
			//resultDWZ.setNavTabId("role");
			resultDWZ.setCallbackType("closeCurrent");
			return resultDWZ;
		}
		
	}

	@RequestMapping(value = "/goToOrgPage")
	public ModelAndView goToOrgPage() {
		ModelAndView mav = new ModelAndView("/organization/list");
		
		return mav;
	}
	
	@RequestMapping(value="goToSetLowerOrgPage")
	public ModelAndView goToSetLowerOrgPage(@RequestParam Integer orgID, HttpServletRequest request) {
		Organization orgEntity = orgService.getOrgById(orgID);
		
		// 取得全部组织
		List<Organization> allOrgList = orgService.getOrgList();
		
		if (allOrgList != null && allOrgList.size() > 0) {
			// 将全部机构中的自身移除
			Iterator iterator = allOrgList.iterator();
			while(iterator.hasNext()) {
				Organization org = (Organization) iterator.next();
				if (org.getOrg_id().intValue() == orgID.intValue()) {
					iterator.remove();
					break;
				}
			}
		} else {
			allOrgList = new ArrayList<Organization>();
		}
		
		List<Organization> lowerOrgList = orgService.getLowerOrgList(orgID);
		if (lowerOrgList != null && lowerOrgList.size() > 0) {
			orgEntity.setLowerOrgList(lowerOrgList);
			
			// 把全部组织中当前机构的下级机构移除
			if (allOrgList != null && allOrgList.size() > 0) {
				// 遍历把已存在的下级机构和当前机构从全部选中列表中去除
				Iterator iterator = allOrgList.iterator();
				while(iterator.hasNext()) {
					Organization org = (Organization) iterator.next();
					
					for (Organization o : lowerOrgList) {
						if (o.getOrg_id().intValue() == org.getOrg_id().intValue()) {
							iterator.remove();
							continue;
						}
					}
				}
			}
		} else {
			lowerOrgList = new ArrayList<Organization>();
			orgEntity.setLowerOrgList(lowerOrgList);
		}
		ModelAndView mav = new ModelAndView("/organization/setLowerOrg");
		
		if (orgEntity != null) {
			mav.addObject("successFlag", "success");
			mav.addObject("orgEntity", orgEntity);
			mav.addObject("allOrgList", allOrgList);
		} else {
			mav.addObject("successFlag", "fail");
			//mav.addObject("orgEntity", orgEntity);
		}
		return mav;
	}
	
	@RequestMapping(value="saveLowerOrg")
	@ResponseBody
	public ResultDwz saveLowerOrg(HttpServletRequest request) {
		String orgID = request.getParameter("org_id");
		String selectedLowerOrgIDStr = request.getParameter("selectedLowerOrg");
		String[] selectedLowerOrgIDArray = selectedLowerOrgIDStr.split("_");
		
		List<OrgTree> lowerOrgList = new ArrayList<OrgTree>();
		if (StringUtils.isNotBlank(selectedLowerOrgIDStr) && selectedLowerOrgIDArray != null
				&& selectedLowerOrgIDArray.length > 0) {
			for (String id : selectedLowerOrgIDArray) {
				OrgTree ot = new OrgTree();
				ot.setOrg_id(Integer.valueOf(orgID));
				ot.setLower_id(Integer.valueOf(id));

				lowerOrgList.add(ot);
			}
		}
		
		
		try {
			orgService.saveLowerOrg(Integer.valueOf(orgID), lowerOrgList);
			
			ResultDwz resultDWZ = new ResultDwz();
			resultDWZ.setStatusCode("200");
			resultDWZ.setMessage("保存成功！");
			//resultDWZ.setNavTabId("role");
			resultDWZ.setCallbackType("closeCurrent");
			return resultDWZ;
		} catch (Exception ex) {
			ResultDwz resultDWZ = new ResultDwz();
			resultDWZ.setStatusCode("300");
			resultDWZ.setMessage(ex.getMessage());
			//resultDWZ.setNavTabId("role");
			resultDWZ.setCallbackType("closeCurrent");
			return resultDWZ;
		}
	}
	
	@RequestMapping(value="/deleteOrgData")
	public void deleteOrgData(HttpServletRequest request) {
		String org_id = request.getParameter("org_id");
		
		orgService.deleteOrgData(Integer.valueOf(org_id));
	}
	
	
	@RequestMapping(value="/initGradeType4Schools")
	public void initGradeType4Schools(HttpServletRequest request) {
		String org_id = request.getParameter("org_id");
		
		if (StringUtils.isNotBlank(org_id)) {
			orgService.initGradeType4Schools(org_id);
		}
	}
	
	
	@RequestMapping(value="/testClassUpgrade")
	public void testClassUpgrade(HttpServletRequest request) {
		String org_id = request.getParameter("org_id");
		
		if (StringUtils.isNotBlank(org_id)) {
			//List<String> resultList = orgService.classUpgrade(Integer.valueOf(org_id));
			//System.out.println(resultList);
		}
	}
	
}
