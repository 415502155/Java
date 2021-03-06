package sng.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sng.dao.BaseSetCampusManageDao;
import sng.dao.BaseSetCategoryManageDao;
import sng.dao.BaseSetClassRoomManageDao;
import sng.dao.BaseSetCooperativeManageDao;
import sng.dao.BaseSetRuleManageDao;
import sng.dao.BaseSetSubjectManageDao;
import sng.dao.BaseSetTermManageDao;
import sng.entity.Campus;
import sng.entity.Category;
import sng.entity.Classroom;
import sng.entity.Cooperative;
import sng.entity.Rulelist;
import sng.entity.Subject;
import sng.entity.Term;
import sng.pojo.CategoryAndSubjectPojo;
import sng.pojo.ClassRoomAndClassesPojo;
import sng.pojo.ParamObj;
import sng.pojo.Result;
import sng.pojo.TermInfoPojo;
import sng.service.BaseSetService;
import sng.util.Paging;
import sng.util.RedisUtil;

/**
 * @author magq
 * @version 1.0
 * @desc 基础设置模块Service层接口实现(校区管理 、 学期管理 、 科目管理 、 教师管理 、 合作机构 、 规则设置)
 * @data 2018-10-26
 */
@Component
@Transactional("transactionManager")
public class BaseSetServiceImpl implements BaseSetService {

	private Logger log = LoggerFactory.getLogger(getClass());
	// 校区
	@Autowired
	private BaseSetCampusManageDao baseSetCampusManageDao;

	// 教室
	@Autowired
	private BaseSetClassRoomManageDao baseSetClassRoomManageDao;

	// 学期
	@Autowired
	private BaseSetTermManageDao baseSetTermManageDao;

	// 类目
	@Autowired
	private BaseSetCategoryManageDao categoryManageDao;

	// 科目
	@Autowired
	private BaseSetSubjectManageDao baseSetSubjectManageDao;

	// 合作机构
	@Autowired
	private BaseSetCooperativeManageDao baseSetCooperativeManageDao;

	// 规则设置
	@Autowired
	private BaseSetRuleManageDao baseSetRuleManageDao;
	
	@Resource
	private RedisUtil redisUtil;

	/*
	 * 创建更新校区
	 *
	 * @see sng.service.BaseSetService#createAndUpdateCampus(sng.entity.Campus)
	 */
	@Override
	public Result createAndUpdateCampus(Campus campus) {
		Result result = new Result();

		if (null == campus || null == campus.getOrg_id()) {
			result.setSuccess(false);
			result.setMessage("创建编辑校区参数有误!");
			return result;
		}

		if (StringUtils.isBlank(campus.getCam_name())) {
			result.setSuccess(false);
			result.setMessage("校区名称不能为空!");
			return result;
		}
		Integer cam_id = campus.getCam_id();
		ParamObj paramObj = new ParamObj();
		paramObj.setNeedCount(false);
		paramObj.setBlurQuery(false);
		paramObj.setCam_name(campus.getCam_name());
		paramObj.setCam_id(cam_id);
		paramObj.setOrg_id(campus.getOrg_id());
		paramObj.setExits(true);// 用于拼接sql作为拼接条件使用
		Paging<Campus> paging = baseSetCampusManageDao.queryCampusListInfo(paramObj);
		if (paging != null && paging.getData() != null && !paging.getData().isEmpty() && paging.getData().size() > 0) {
			result.setSuccess(false);
			result.setMessage("创建编辑校区名字已经存在");
			return result;
		}

		if (cam_id == null) {
			campus.setInsert_time(new Date());
			campus.setIs_del(0);
			baseSetCampusManageDao.save(campus);
			result.setMessage("创建校区成功");
		} else {
			// 1:判断当前校区在其他的业务数据中是否使用如果使用了，则不能修改
			Paging paging_ = baseSetClassRoomManageDao.queryCampusAndClassRoomInfo(paramObj);
			if (paging_ != null && paging_.getData() != null && !paging_.getData().isEmpty()) {
				List<Map<String, Object>> list = paging_.getData();
				if (list != null) {
					String cam_name = (String) list.get(0).get("cam_name");
					if (!campus.getCam_name().equals(cam_name)) {
						result.setSuccess(false);
						result.setMessage("系统当前校区存在业务数据，不能修改校区名称，请联系技术支持");
						return result;
					}
				}
			}
			campus.setUpdate_time(new Date());
			campus.setIs_del(0);
			baseSetCampusManageDao.update(campus);
			result.setMessage("编辑校区成功");
		}
		result.setSuccess(true);

		return result;
	}

	/*
	 * 校区管理列表
	 *
	 * @see sng.service.BaseSetService#queryCampusListInfo(sng.pojo.ParamObj)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Result queryCampusListInfo(ParamObj paramObj) {
		Result result = new Result();
		if (paramObj == null || paramObj.getOrg_id() == null) {
			result.setSuccess(false);
			result.setMessage("校区管理列表请求参数有误");
			return result;
		}
		Paging<Campus> paging = baseSetCampusManageDao.queryCampusListInfo(paramObj);
		result.setSuccess(true);
		result.setMessage("获取校区管理列表成功");
		result.setData(paging);
		return result;
	}

	/*
	 * 删除校区
	 *
	 * @see sng.service.BaseSetService#deleteCampus(sng.pojo.ParamObj)
	 */
	@Override
	public Result deleteCampus(ParamObj paramObj) {
		// TODO Auto-generated method stub
		Result result = new Result();
		if (paramObj == null || paramObj.getCam_id() == null || paramObj.getOrg_id() == null) {
			result.setMessage("删除校区参数有误");
			result.setSuccess(false);
			return result;
		}

		// 1:判断当前校区在其他的业务数据中是否使用如果使用了，则不能删除
		paramObj.setNeedCount(false);
		Paging paging = baseSetClassRoomManageDao.queryCampusAndClassRoomInfo(paramObj);
		if (paging != null && paging.getData() != null && !paging.getData().isEmpty()) {
			result.setSuccess(false);
			result.setMessage("要删除的校区在其他业务中有使用不能删除");
			return result;
		}

		// 如果在其他的业务中么有用到此校区id则可以删除
		baseSetCampusManageDao.deleteCampus(paramObj);
		result.setMessage("逻辑删除校区成功");
		result.setSuccess(true);
		return result;
	}

	/*
	 * 创建学期
	 *
	 * @see sng.service.BaseSetService#createAndUpdateTerm(sng.entity.Term)
	 */
	@Override
	public Result createAndUpdateTerm(Term term) {
		// TODO Auto-generated method stub
		Result result = new Result();
		if (term == null || term.getOrg_id() == null || StringUtils.isBlank(term.getTerm_name())
				|| StringUtils.isBlank(term.getCur_year()) || term.getTerm_type() == null
				|| term.getStart_time() == null) {
			result.setSuccess(false);
			result.setMessage("创建编辑学期请求参数有误");
			return result;
		}

		Integer term_id = term.getTerm_id();
		
		// 判断是否有重名，直接单表操作，不涉及其他业务
		ParamObj paramObj = new ParamObj();
		paramObj.setOrg_id(term.getOrg_id());
		paramObj.setTerm_name(term.getTerm_name());
		paramObj.setTerm_id(term_id);
		paramObj.setCur_year(term.getCur_year());
		paramObj.setExits(true);// 用于拼接sql作为拼接条件使用
		int count = baseSetTermManageDao.filterTermName(paramObj);
		if (count > 0) {
			result.setSuccess(false);
			result.setMessage("该学期已存在!");
			return result;
		}

		
		if (term_id == null) {
			term.setInsert_time(new Date());
			term.setIs_del(0);
			baseSetTermManageDao.save(term);
			result.setMessage("创建学期成功");
		} else {
			// 判断在其他的数据库中是否有业务数据，如果有业务数据则不能编辑
			count = baseSetTermManageDao.isDelOrUpdateTerminfo(paramObj);
			if (count > 0) {
				result.setSuccess(false);
				result.setMessage("更新学期与其他有业务数据关联不能编辑");
				return result;
			}
			term.setUpdate_time(new Date());
			term.setIs_del(0);
			baseSetTermManageDao.update(term);
			result.setMessage("编辑学期成功");
		}
		result.setSuccess(true);
		return result;
	}

	/*
	 * 查询学期信息列表
	 *
	 * @see sng.service.BaseSetService#queryTermListInfo(sng.pojo.ParamObj)
	 */
	@Override
	public Result queryTermListInfo(ParamObj paramObj) {
		// TODO Auto-generated method stub
		Result result = new Result();
		if (paramObj == null || paramObj.getOrg_id() == null) {
			result.setSuccess(false);
			result.setMessage("查询学期信息列表请求参有误");
			return result;
		}
	
		Paging paging = baseSetTermManageDao.queryTermListInfo(paramObj);
		if (paging != null && paging.getData() != null) {
			if (!paging.getData().isEmpty()) {
				List<Map<String, Object>> list = baseSetTermManageDao.queryTermAndOldTerminfo(paramObj);
				List<Map<String, Object>> maps = paging.getData();
				Map<String, Object> map_ = list.get(0);
				Integer camId = (Integer) map_.get("term_id");
				for (Map<String, Object> map : maps) {
					Integer cam_id = (Integer) map.get("term_id");
					if(camId.intValue() != cam_id.intValue()) {
						continue;
					}else {
						map.put("falg", "true");
					}
				}
			}
		}
		result.setData(paging);
		result.setSuccess(true);
		return result;
	}

	/**
	 * 重新封装queryTermByOrgIdAndCamIds返回的数据
	 * 
	 * @param paging
	 * @return
	 */
	private List<TermInfoPojo> assembleData(Paging paging) {
		Map<String, TermInfoPojo> map = null;
		List<TermInfoPojo> list = null;
		if (paging != null && paging.getData() != null && !paging.getData().isEmpty()) {
			map = new HashMap<String, TermInfoPojo>();
			list = new ArrayList<>();
			for (int i = 0; i < paging.getData().size(); i++) {
				Map<String, Object> map_ = (Map<String, Object>) paging.getData().get(i);
				String year = (String) map_.get("cur_year");
				Integer term_id = (Integer) map_.get("term_id");
				String term_name = (String) map_.get("term_name");
				Integer org_id = (Integer) map_.get("org_id");
				Integer term_type = (Integer) map_.get("term_type");
				Term term2 = new Term();
				term2.setCur_year(year);
				term2.setTerm_id(term_id);
				term2.setTerm_name(term_name);
				term2.setOrg_id(org_id);
				term2.setTerm_type(term_type);
				if (!map.containsKey(year)) {
					TermInfoPojo term = new TermInfoPojo();
					term.setCur_year(year);
					term.setTerm_id(term_id);
					term.getList().add(term2);
					map.put(year, term);
				} else {
					map.get(year).getList().add(term2);
				}
			}

			for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<String, TermInfoPojo> entry = (Entry<String, TermInfoPojo>) iterator.next();
				list.add(entry.getValue());
			}

			Calendar cal = Calendar.getInstance();
			for (int i = 0; i < list.size(); i++) {
				TermInfoPojo infoPojo = list.get(i);
				int year_ = Integer.valueOf(infoPojo.getCur_year());
				int year = cal.get(Calendar.YEAR);
				if (year_ == year) {
					list.remove(infoPojo);
					list.add(0, infoPojo);
				}
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)根据机构ID，多个校区ID返回当前年所对应的校区
	 * 
	 * @see sng.service.BaseSetService#queryTermByOrgIdAndCamIds(sng.pojo.ParamObj)
	 */
	@Override
	public Result queryTermByOrgIdAndCamIds(ParamObj paramObj) {
		Result result = new Result();
		if (paramObj == null || paramObj.getOrg_id() == null) {
			// || StringUtils.isBlank(paramObj.getCam_ids())) {
			result.setSuccess(false);
			result.setMessage("根据机构ID，多个校区ID返回当前年所对应的校区请求参有误");
			return result;
		}
		// 不需要分页
		paramObj.setNeedCount(false);
		Paging paging = baseSetTermManageDao.queryTermListInfo(paramObj);
		List<TermInfoPojo> list = assembleData(paging);
		result.setData(list);
		result.setSuccess(true);
		return result;
	}

	/*
	 * 删除学期信息
	 *
	 * @see sng.service.BaseSetService#deleteTermByTerm(sng.pojo.ParamObj)
	 */
	@Override
	public Result deleteTermByTerm(ParamObj paramObj) {
		// TODO Auto-generated method stub
		Result result = new Result();
		if (paramObj == null || paramObj.getOrg_id() == null || paramObj.getTerm_id() == null) {
			result.setMessage("删除学期信息请求参数有误");
			result.setSuccess(false);
			return result;
		}
		// 判断在其他的数据库中是否有业务数据，如果有业务数据则不能编辑
		int count = 0;
		count = baseSetTermManageDao.isDelOrUpdateTerminfo(paramObj);
		if (count > 0) {
			result.setSuccess(false);
			result.setMessage("删除学期与其他有业务数据关联不能删除");
			return result;
		}
		baseSetTermManageDao.deleteTermByTerm(paramObj);
		result.setMessage("删除学期成功");
		result.setSuccess(true);
		return result;
	}

	/*
	 * 创建/编辑类目
	 *
	 * @see
	 * sng.service.BaseSetService#createAndUpdateCategoryInfo(sng.entity.Category)
	 */
	@Override
	public Result createAndUpdateCategoryInfo(Category category) {
		// TODO Auto-generated method stub
		Result result = new Result();

		// 如果类目名字为空不允许添加类目名称（更新也是如此）
		if (category == null || category.getOrg_id() == null || StringUtils.isBlank(category.getCategory_name())) {
			result.setSuccess(false);
			result.setMessage("创建编辑类目参数有误");
			return result;
		}

		// 创建和更新类目之前先判断名称是否有重复，如果有重复不允许更新和添加
		ParamObj paramObj = new ParamObj();
		paramObj.setOrg_id(category.getOrg_id());
		paramObj.setBlurQuery(false);
		paramObj.setCategory_name(category.getCategory_name());
		paramObj.setCategory_id(category.getCategory_id());
		paramObj.setExits(true);// 用于拼接sql作为拼接条件使用
		List<Category> list = categoryManageDao.queryCategoryListInfo(paramObj);
		if (list != null && !list.isEmpty()) {
			result.setSuccess(false);
			result.setMessage("类目有重名");
			return result;
		}

		// 如果类目ID为空，则新添加类目
		if (category.getCategory_id() == null) {
			category.setInsert_time(new Date());
			category.setIs_del(0);
			categoryManageDao.save(category);
			result.setMessage("添加成功");
		} else {
			// 如果类目id不为空，则更新类目信息
			category.setUpdate_time(new Date());
			category.setIs_del(0);
			categoryManageDao.update(category);
			result.setMessage("更新成功");
		}
		result.setSuccess(true);
		return result;
	}

	/*
	 * 查询类目信息
	 *
	 * @see sng.service.BaseSetService#queryCategoryListInfo(sng.pojo.ParamObj)
	 */
	@Override
	public Result queryCategoryListInfo(ParamObj paramObj) {
		Result result = new Result();
		// 如果类目名字为空不允许添加类目名称（更新也是如此）
		if (paramObj.getOrg_id() == null) {
			result.setSuccess(false);
			result.setMessage("获取类目信息参数有误");
			return result;
		}
		paramObj.setIsDel(paramObj.getIsDel()); // 0:否 1:是
		List<Category> list = categoryManageDao.queryCategoryListInfo(paramObj);
		result.setSuccess(true);
		result.setData(list);
		return result;
	}

	/*
	 * 创建科目信息
	 *
	 * @see
	 * sng.service.BaseSetService#createAndUpdateSubjectInfo(sng.entity.Subject)
	 */
	@Override
	public Result createAndUpdateSubjectInfo(Subject subject) {
		Result result = new Result();

		if (subject == null || subject.getOrg_id() == null) {
			result.setMessage("创建更新科目参数错误");
			result.setSuccess(false);
			return result;
		}

		if (subject.getCategory_id() == null || StringUtils.isBlank(subject.getSubject_name())) {
			String errorMsg = "";
			if (subject.getCategory_id() == null) {
				errorMsg = "请选择科目所属类目！";
			} else {
				errorMsg = "请填写科目名称！";
			}
			result.setMessage(errorMsg);
			result.setSuccess(false);
			return result;
		}

		Category category = categoryManageDao.get(Category.class, subject.getCategory_id());
		if (category == null || category.getCategory_id() == null) {
			result.setMessage("创建科目时传的类目ID不存在");
			result.setSuccess(false);
			return result;
		}

		// 同一类目下科目名称不能相同判断逻辑
		ParamObj paramObj = new ParamObj();
		paramObj.setOrg_id(subject.getOrg_id());
		paramObj.setBlurQuery(false);
		paramObj.setCategory_id(subject.getCategory_id());
		paramObj.setSubject_name(subject.getSubject_name());
		paramObj.setSubject_id(subject.getSubject_id());
		paramObj.setExits(true);// 用于拼接sql作为拼接条件使用
		List<Subject> list = baseSetSubjectManageDao.querySubjectListInfo(paramObj);
		if (list != null && !list.isEmpty()) { // 说明同机构 同类目 有相同的科目名字
			result.setMessage("创建更新科目名字重复");
			result.setSuccess(false);
			return result;
		}

		// 如果在同机构 同类目 没有相同的科目名称则可以添加 和更新
		if (subject.getSubject_id() == null) { // 科目id没有添加 否则更新
			subject.setInsert_time(new Date());
			subject.setIs_del(0);
			baseSetSubjectManageDao.save(subject);
			result.setMessage("科目创建成功");
		} else {
			
			//先拿到要更新的这条科目信息，然后更具这个条信息中的科目ID 和类目ID去编辑表查询 如果在班级表中查询，如果有业务数据则不能更新
			Subject sub = baseSetSubjectManageDao.get(Subject.class, subject.getSubject_id());
			
			paramObj = new ParamObj();
			paramObj.setOrg_id(sub.getOrg_id());
			paramObj.setSubject_id(sub.getSubject_id());
			paramObj.setCategory_id(sub.getCategory_id());
			List<Map<String,Object>> list_ = baseSetSubjectManageDao.querySubjectAndClassesInfo(paramObj);
			boolean flag =false;
			for(Map<String,Object> map : list_) {
				Integer category_id = (Integer) map.get("category_id");
				//更新的时候老类目id与传进来的类目ID做对比，如果相等则更新，如果不相等则提示有业务数据不能更新
				if(subject.getCategory_id().intValue() != category_id.intValue()) {
					flag = true;
				}
			}
			if(flag) { //不能更新
				//result.setMessage("在班级管理业务中存在类目不能更新");
				result.setSuccess(false);
				return result;
			}else {
				subject.setUpdate_time(new Date());
				subject.setIs_del(0);
				baseSetSubjectManageDao.upateSubjectInfo(subject);
				result.setMessage("科目更新成功");
			}
			
		}
		result.setSuccess(true);
		return result;
	}

	/*
	 * 科目管理列表
	 *
	 * @see sng.service.BaseSetService#querySubjectListInfo(sng.pojo.ParamObj)
	 */
	@Override
	public Result querySubjectListInfo(ParamObj paramObj) {
		Result result = new Result();
		if (paramObj.getOrg_id() == null) {
			result.setMessage("科目管理列表请求参数有误");
			result.setSuccess(false);
			return result;
		}

		if (StringUtils.isNotEmpty(paramObj.getIsQueryConditions())
				&& "true".equalsIgnoreCase(paramObj.getIsQueryConditions())) {
			paramObj.setNeedCount(false);
		}
		
		Paging<CategoryAndSubjectPojo> list = baseSetSubjectManageDao.getCategoryAndSubjecListInfo(paramObj);
		if (StringUtils.isNotEmpty(paramObj.getIsQueryConditions())
				&& "true".equalsIgnoreCase(paramObj.getIsQueryConditions())) {
			List<CategoryAndSubjectPojo> map = filterCategoryAndSubjectListInfo(list);
			result.setData(map);
			result.setMessage("科目类目搜索条件");
		} else {
			result.setData(list);
			result.setMessage(" 科目管理列表");
		}
		result.setSuccess(true);
		return result;
	}

	/**
	 * 主要用于搜索条件的过滤
	 *
	 * @param paramlist
	 * @return
	 */
	private List<CategoryAndSubjectPojo> filterCategoryAndSubjectListInfo(Paging<CategoryAndSubjectPojo> paramlist) {
		List<CategoryAndSubjectPojo> list = null;
		if (paramlist != null && paramlist.getData() != null && !paramlist.getData().isEmpty()) {
			list = new ArrayList<>();
			Map<Integer, CategoryAndSubjectPojo> map = new HashMap<>();
			for (CategoryAndSubjectPojo pojo : paramlist.getData()) {
				if (!map.containsKey(pojo.getCategory_id())) {
					CategoryAndSubjectPojo cp = new CategoryAndSubjectPojo();
					cp.setCategory_id(pojo.getCategory_id());
					cp.setCategory_name(pojo.getCategory_name());
					cp.getList().add(pojo);
					map.put(pojo.getCategory_id(), cp);
				} else {
					CategoryAndSubjectPojo cp = map.get(pojo.getCategory_id());
					cp.getList().add(pojo);
				}
			}
			for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<Integer, CategoryAndSubjectPojo> entry = (Map.Entry<Integer, CategoryAndSubjectPojo>) iterator
						.next();
				list.add(entry.getValue());
			}
		}
		return list;
	}

	/*
	 * 删除科目
	 *
	 * @see sng.service.BaseSetService#deleteSubjectInfo(sng.pojo.ParamObj)
	 */
	@Override
	public Result deleteSubjectInfo(ParamObj paramObj) {
		Result result = new Result();
		if (paramObj.getSubject_id() == null || paramObj.getOrg_id() == null) {
			result.setMessage("删除科目参数有误");
			result.setSuccess(false);
			return result;
		}
		// 先判断当前科目在班级里是否有业务数据，如果有业务数据则不能删除，否则才能删除
		List<Map<String,Object>> list = baseSetSubjectManageDao.querySubjectAndClassesInfo(paramObj);
		if (list != null && !list.isEmpty()) {
			result.setMessage("所选择科目已经在班级表中存在不能删除");
			result.setSuccess(false);
			return result;
		}

		// 逻辑删除
		baseSetSubjectManageDao.delSubjectInfo(paramObj);
		result.setMessage("逻辑删除成功");
		result.setSuccess(true);
		return result;
	}

	/*
	 * 创建/编辑教室
	 *
	 * @see
	 * sng.service.BaseSetService#createAndUpdateClassRoomInfo(sng.entity.Classroom)
	 */
	@Override
	public Result createAndUpdateClassRoomInfo(ParamObj paramObj) {
		Result result = new Result();
		if (paramObj == null || paramObj.getOrg_id() == null || paramObj.getCam_id() == null
				|| StringUtils.isBlank(paramObj.getClassroom_name()) || StringUtils.isBlank(paramObj.getBuilding())
				|| StringUtils.isBlank(paramObj.getFloor())) {
			result.setMessage("创建/编辑教室参数有误");
			result.setSuccess(false);
			return result;
		}

		Campus campus = baseSetCampusManageDao.get(Campus.class, paramObj.getCam_id());
		if (campus == null || campus.getCam_id() == null) {
			result.setMessage("创建/编辑教室校区ID不存在");
			result.setSuccess(false);
			return result;
		}

		// 教室名称在同一教学楼同一层内名称唯一
		paramObj.setNeedCount(false);
		paramObj.setBlurQuery(false);
		paramObj.setClassroom_id(paramObj.getClassroom_id());
		paramObj.setExits(true);// 用于拼接sql作为拼接条件使用
		Paging paging = baseSetClassRoomManageDao.queryCampusAndClassRoomInfo(paramObj);
		if (paging != null && paging.getData() != null && !paging.getData().isEmpty()) {
			result.setMessage("创建/编辑教室名称有重名");
			result.setSuccess(false);
			return result;
		}

		// 如果没有重名就新增或者更新
		Classroom classroom = new Classroom();
		classroom.setCam_id(paramObj.getCam_id());
		classroom.setBuilding(paramObj.getBuilding());
		classroom.setClassroom_name(paramObj.getClassroom_name());
		classroom.setFloor(paramObj.getFloor());
		classroom.setIs_del(paramObj.getIsDel());
		if (paramObj.getClassroom_id() == null) {
			classroom.setInsert_time(new Date());
			classroom.setIs_del(0);
			baseSetClassRoomManageDao.save(classroom);
			result.setMessage("创建教室成功");
		} else {
			classroom.setClassroom_id(paramObj.getClassroom_id());
			classroom.setUpdate_time(new Date());
			classroom.setIs_del(0);
			baseSetClassRoomManageDao.update(classroom);
			result.setMessage("更新教室成功");
		}
		result.setSuccess(true);
		return result;
	}

	/*
	 * 获取教室信息
	 *
	 * @see sng.service.BaseSetService#queryClassRoomListInfo(sng.pojo.ParamObj)
	 */
	@Override
	public Result queryClassRoomListInfo(ParamObj paramObj) {
		Result result = new Result();
		if (paramObj.getOrg_id() == null) {
			result.setMessage("获取教室信息参数有误");
			result.setSuccess(false);
			return result;
		}
		Paging paging = baseSetClassRoomManageDao.queryCampusAndClassRoomInfo(paramObj);
		result.setMessage("获取教室信息");
		result.setData(paging);
		result.setSuccess(true);
		return result;
	}

	/*
	 * 删除教室
	 *
	 * @see sng.service.BaseSetService#deleteClassRoomInfo(sng.pojo.ParamObj)
	 */
	@Override
	public Result deleteClassRoomInfo(ParamObj paramObj) {
		Result result = new Result();
		if (paramObj == null || paramObj.getOrg_id() == null || paramObj.getClassroom_id() == null) {
			result.setMessage("删除教室参数有误");
			result.setSuccess(false);
			return result;
		}
		// 判断当前教室在班级中是否有业务数据，如果有业务数据则不能删除，否则可以删除
		List<ClassRoomAndClassesPojo> list = baseSetClassRoomManageDao.queryClassRoomAndClassesInfo(paramObj);
		if (list != null && !list.isEmpty()) {
			result.setMessage("教室在班级中存在业务数据不能删除");
			result.setSuccess(false);
			return result;
		}
		// 逻辑删除教室数据
		baseSetClassRoomManageDao.deleteClassRoomInfo(paramObj);
		result.setMessage("教室删除成功");
		result.setSuccess(true);
		return result;
	}

	/**
	 * 创建更新合作机构
	 */
	@Override
	public Result createAndUpdateCooperativeInfo(Cooperative cooperative) {
		Result result = new Result();
		if (cooperative == null || cooperative.getOrg_id() == null || StringUtils.isBlank(cooperative.getName())) {
			result.setMessage("创建更新合作机构参数有误");
			result.setSuccess(false);
			return result;
		}

		// 添加合作机构是否有重名
		ParamObj paramObj = new ParamObj();
		paramObj.setOrg_id(cooperative.getOrg_id());
		paramObj.setCooperative_name(cooperative.getName());
		paramObj.setNeedCount(false);
		paramObj.setBlurQuery(false);
		paramObj.setIsDel(0);
		paramObj.setCooperative_id(cooperative.getCooperative_id());
		paramObj.setExits(true);// 用于拼接sql作为拼接条件使用
		Paging<Cooperative> paging = baseSetCooperativeManageDao.queryCooperativeListInfo(paramObj);
		if (paging != null && paging.getData() != null && !paging.getData().isEmpty()) {
			result.setMessage("创建更新合作机构名字已存在");
			result.setSuccess(false);
			return result;
		}

		// 当合作机构为空床架否则更新
		if (cooperative.getCooperative_id() == null) {
			cooperative.setInsert_time(new Date());
			cooperative.setIs_del(0);
			baseSetCooperativeManageDao.save(cooperative);
			result.setMessage("创建成功");
		} else {
			cooperative.setUpdate_time(new Date());
			cooperative.setIs_del(0);
			baseSetCooperativeManageDao.upateCooperativeInfo(cooperative);
			result.setMessage("更新成功");
		}
		result.setSuccess(true);
		return result;
	}

	/*
	 * (non-Javadoc) 获取合作机构信息列表
	 *
	 * @see sng.service.BaseSetService#queryCooperativeListInfo(sng.pojo.ParamObj)
	 */
	@Override
	public Result queryCooperativeListInfo(ParamObj paramObj) {
		Result result = new Result();
		if (paramObj == null || paramObj.getOrg_id() == null) {
			result.setMessage("查询合作机构信息列表参数有误");
			result.setSuccess(false);
			return result;
		}

		// 增加搜索条件--逻辑删除状态为0的
		paramObj.setIsDel(0);
		Paging<Cooperative> paging = baseSetCooperativeManageDao.queryCooperativeListInfo(paramObj);
		result.setData(paging);
		result.setSuccess(true);
		result.setMessage("合作机构信息列表");
		return result;
	}

	/*
	 * (non-Javadoc) 删除合作机构信息
	 *
	 * @see sng.service.BaseSetService#deleteCooperativeInfo(sng.pojo.ParamObj)
	 */
	@Override
	public Result deleteCooperativeInfo(ParamObj paramObj) {
		Result result = new Result();
		if (paramObj.getOrg_id() == null || paramObj.getCooperative_id() == null) {
			result.setMessage("删除合作机构信息参数有误");
			result.setSuccess(false);
			return result;
		}
		// 删除合作机构信息，判断合作机构在班级中是否使用如果使用了则不能删除
		List<Map<String, Object>> list = baseSetCooperativeManageDao.queryCooperativeAndClassesInfo(paramObj);
		if (list != null && !list.isEmpty()) {
			result.setMessage("删除合作机构信息在班级中有业务数据不能删除");
			result.setSuccess(false);
			return result;
		}
		// 删除
		baseSetCooperativeManageDao.deleteCooperativeInfo(paramObj);
		result.setMessage("删除合作机构信息成功");
		result.setSuccess(true);
		return result;
	}

	/*
	 * 缴费新建/编辑保存接口(non-Javadoc)
	 *
	 * @see
	 * sng.service.BaseSetService#createAndUpdatePaySetInfo(sng.entity.Rulelist)
	 */
	@Override
	public Result createAndUpdatePaySetInfo(Rulelist ruleList) {
		Result result = new Result();
		if (ruleList == null || ruleList.getOrg_id() == null) {
			result.setMessage("创建缴费规则信息参数有误");
			result.setSuccess(false);
			return result;
		}
		ParamObj paramObj = new ParamObj();
		paramObj.setOrg_id(ruleList.getOrg_id());
		paramObj.setIsDel(0);
		List<Rulelist> list = baseSetRuleManageDao.queryPaySetListInfo(paramObj);
		// 如果规则表中没有任何数据，则创建，否则更新
		if (list == null || list.isEmpty()) {
			ruleList.setInsert_time(new Date());
			ruleList.setIs_del(0);
			baseSetRuleManageDao.save(ruleList);
			result.setMessage("创建缴费成功");
		} else {
			Rulelist rulelist2 = list.get(0);
			ruleList.setUpdate_time(new Date());
			ruleList.setRule_id(rulelist2.getRule_id());
			int num = baseSetRuleManageDao.updateRuleInfo(ruleList);
			result.setMessage("更新成功");
		}
		
		//将规则表中的缴费有效时间放入redis
		Integer paymentPeriod=ruleList.getPayment_period();
		if(paymentPeriod!=null) {
			redisUtil.set(ApplyServiceImpl.expired_times_pre+ruleList.getOrg_id(), String.valueOf(paymentPeriod));
		}
		
		result.setSuccess(true);
		return result;
	}

	/*
	 * 获取缴费设置信息(non-Javadoc)
	 *
	 * @see sng.service.BaseSetService#queryPaySetInfo(sng.pojo.ParamObj)
	 */
	@Override
	public Result queryPaySetInfo(ParamObj paramObj) {
		Result result = new Result();
		if (paramObj.getOrg_id() == null) {
			result.setMessage("获取缴费设置信息请求参数有误");
			result.setSuccess(false);
			return result;
		}
		paramObj.setIsDel(0);
		List<Rulelist> list = baseSetRuleManageDao.queryPaySetListInfo(paramObj);
		if (list == null || list.isEmpty()) {

			result.setSuccess(true);
			return result;
		}
		result.setMessage("获取缴费设置信息成功");
		result.setData(list.get(0));
		result.setSuccess(true);
		return result;
	}

	/*
	 * 认证设置/编辑保存(non-Javadoc)
	 *
	 * @see
	 * sng.service.BaseSetService#createAndUpdateAuthSetInfo(sng.entity.Rulelist)
	 */
	@Override
	public Result createAndUpdateAuthSetInfo(Rulelist ruleList) {
		Result result = new Result();
		if (ruleList == null || ruleList.getOrg_id() == null) {
			result.setMessage("创建认证规则信息参数有误");
			result.setSuccess(false);
			return result;
		}
		ParamObj paramObj = new ParamObj();
		paramObj.setOrg_id(ruleList.getOrg_id());
		paramObj.setIsDel(0);
		List<Rulelist> list = baseSetRuleManageDao.queryPaySetListInfo(paramObj);
		// 如果规则表中没有任何数据，则创建，否则更新
		if (list == null || list.isEmpty()) {
			ruleList.setInsert_time(new Date());
			ruleList.setIs_del(0);
			baseSetRuleManageDao.save(ruleList);
			result.setMessage("认证创建成功");
		} else {
			Rulelist rulelist2 = list.get(0);
			if (rulelist2 == null || rulelist2.getRule_id() == null) {
				result.setMessage("认证更新失败,ruleID为空");
				result.setSuccess(false);
				return result;
			}
			ruleList.setUpdate_time(new Date());
			ruleList.setRule_id(rulelist2.getRule_id());
			int num = baseSetRuleManageDao.updateRuleInfo(ruleList);
			result.setMessage("认证更新成功");
		}
		result.setSuccess(true);
		return result;
	}

	/*
	 * 获取认证设置信息(non-Javadoc)
	 *
	 * @see sng.service.BaseSetService#queryAuthSetInfo(sng.pojo.ParamObj)
	 */
	@Override
	public Result queryAuthSetInfo(ParamObj paramObj) {
		Result result = new Result();
		if (paramObj.getOrg_id() == null) {
			result.setMessage("获取认证设置信息请求参数有误");
			result.setSuccess(false);
			return result;
		}
		paramObj.setIsDel(0);
		List<Rulelist> list = baseSetRuleManageDao.queryPaySetListInfo(paramObj);
		if (list == null || list.isEmpty()) {
			result.setMessage("获取认证设置信息为空");
			result.setSuccess(true);
			return result;
		}
		result.setMessage("获取认证设置信息成功");
		result.setData(list.get(0));
		result.setSuccess(true);
		return result;
	}

	@Override
	public Result queryTermAndOldTerminfo(ParamObj paramObj) {
		Result result = new Result();
		if (paramObj.getOrg_id() == null) {
			result.setSuccess(false);
			return result;

		}
		List<Map<String, Object>> map = baseSetTermManageDao.queryTermAndOldTerminfo(paramObj);
		result.setData(map);
		result.setSuccess(true);
		return result;
	}

	@Override
	public Rulelist getByOrgId(Integer org_id) {
		return baseSetRuleManageDao.getByOrgId(org_id);
	}

}
