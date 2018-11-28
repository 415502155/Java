package cn.edugate.esb.imp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import cn.edugate.esb.EduConfig;
import cn.edugate.esb.dao.IGroupDao;
import cn.edugate.esb.dao.IGroupMemberDao;
import cn.edugate.esb.eduEnum.EnumRoleLabel;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Group;
import cn.edugate.esb.entity.GroupMember;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Role;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TeacherRole;
import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.redis.dao.RedisGroupDao;
import cn.edugate.esb.redis.dao.RedisGroupMemberDao;
import cn.edugate.esb.redis.dao.RedisOrgUserDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.service.GroupService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.ImportExcelUtil;
import cn.edugate.esb.util.Paging;
import cn.edugate.esb.util.Utils;

/**
 * 组服务实现类
 * Title:GroupImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午9:33:24
 */
@Component
@Transactional("transactionManager")
public class GroupImpl implements GroupService {

	private static Logger logger = Logger.getLogger(GroupImpl.class);
	
	private IGroupDao groupDao;
	@Autowired
	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}
	@Autowired
	private EduConfig eduConfig;
	@Autowired  
    private ApplicationContext ctx;
	
	@Autowired
	private RedisTeacherDao redisTeacherDao;
	@Autowired
	private RedisOrgUserDao redisOrgUserDao;
	@Autowired
	private IGroupMemberDao groupMemberDao;
	
	@Autowired
	private RedisGroupMemberDao redisGroupMemberDao;
	
	@Autowired
	private RedisGroupDao redisGroupDao;
	
	
	@Override
	public void add(Group group) {
		group.setInsert_time(new Date());
		group.setIs_del(Constant.IS_DEL_NO);
		try {
			this.groupDao.save(group);
		} catch (Exception e) {
			logger.error("Group Add error:"+e.getMessage());
		}
	}

	@Override
	public Integer delete(Integer groupId) {
		Group group =  groupDao.get(Group.class, groupId);
		
		group.setIs_del(Constant.IS_DEL_YES);
		group.setDel_time(new Date());
		try {
			groupDao.update(group);
			return groupId;
		} catch (Exception e) {
			logger.error("Group delete error:"+e.getMessage());
			return null;
		}
	}

	@Override
	public Group update(Group group) {
		group.setUpdate_time(new Date());
		try {
			groupDao.update(group);
			return group;
		} catch (Exception e) {
			logger.error("Group update error:"+e.getMessage());
			return null;
		}
	}

	@Override
	public Group getById(Integer groupId) {
		Group group = new Group();
		try {
			group = groupDao.get(Group.class,groupId);
			return group;
		} catch (Exception e) {
			logger.error("Group getById error:"+e.getMessage());
			return null;
		}
	}

	@Override
	public List<Group> getAllList(Group group) {
		try {
			return groupDao.getAllList(group);
		} catch (Exception e) {
			logger.error("Group getAllList error:"+e.getMessage());
			return null;
		}
	}

	@Override
	public Paging<Group> getAllList(Group group, Paging<Group> paging) {
		try {
			return groupDao.getAllList(group, paging);
		} catch (Exception e) {
			logger.error("Group getAllList(Paging) error:"+e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<Group> getGroupListByUserID(Integer currentUserId) {
		try {
			return groupDao.getGroupListByUserID(currentUserId);
		} catch (Exception e) {
			logger.error("Group getGroupListByUserID error:"+e.getMessage());
			return null;
		}
	}

	@Override
	public Paging<Group> getGroupListByUserID(Integer currentUserId, Paging<Group> paging) {
		try {
			return groupDao.getGroupListByUserID(currentUserId, paging);
		} catch (Exception e) {
			logger.error("Group getGroupListByUserID error:"+e.getMessage());
			return null;
		}
	}

	@Override
	public List<Group> getAll() {
		//Criterion criterion = Restrictions.conjunction();
		Criterion criterion = Restrictions.and(Restrictions.eq("is_del", Constant.IS_DEL_NO));
		try {
			return groupDao.list(Group.class, criterion);
		} catch (Exception e) {
			logger.error("Group getAll error:"+e.getMessage());
			return null;
		}
	}

	@Override
	public List<Object> getGroupList(Integer orgId, Integer groupType) {
		try {
			return groupDao.getGroupList(orgId,groupType);
		} catch (Exception e) {
			logger.error("Group getGroupList error:"+e.getMessage());
			return null;
		}
	}

	@Override
	public void saveGroup(Group group) {
		// TODO Auto-generated method stub
		this.groupDao.saveOrUpdate(group);
	}

	@Override
	public List<Object> getGroupsWithMembers(Integer orgId, Integer groupType, Integer memberId, Integer type) {
		return groupDao.getGroupsWithMembers(orgId,groupType,memberId,type);
	}

	@Override
	public List<Object> getGroupsWithoutMembers(Integer orgId, Integer groupType) {
		return groupDao.getGroupsWithoutMembers(orgId,groupType);
	}

	@Override
	public List<Object> getGroupsWithMembers(String user_ids) {
		return groupDao.getGroupsWithMembers(user_ids);
	}

	@Override
	public List<Map<String, String>> getTechGroupsOfOrg(int org_id) {
		// TODO Auto-generated method stub
		return groupDao.getTechGroupsOfOrg(org_id);
	}

	@Override
	public Map<String, Object> batchTechGroup(int org_id,MultipartFile multiFile) {
		String errorMessage = "";
        Map<String,Object> map = new HashMap<String,Object>();
		try {		
			String basePath = this.eduConfig.getPath();
			String tempPath = basePath + File.separator + "res" + File.separator + "temp" + File.separator;
			map.put("tempPath", tempPath);
			Workbook wb = new HSSFWorkbook(multiFile.getInputStream());
			if (wb != null) {
				//获取单元格表头 列名
				String[] columnName4ImportTechGroup = ImportExcelUtil.getTechGroupExcelHeadLine(wb);
				map.put("columnName", columnName4ImportTechGroup);
				if (columnName4ImportTechGroup!=null) {//Excel文件标题正确
					//从Excel中读取导入的信息
					List<String[]> excelRowArray = ImportExcelUtil.getImportTechGroupFromExcel(wb);
					wb = null;
					if (!excelRowArray.isEmpty()) {//Excel不为空
						//保存DB
						List<String[]> errorList = this.validateBatchTechGroup(org_id, excelRowArray);
						map.put("errorList", errorList);
						
					} else {//Excel为空
						errorMessage = "未在导入文件中发现数据！";
						// 将错误信息写入excel
						List<String[]> errorList = new ArrayList<String[]>();
						String[] errorInfo = new String[5];
						errorInfo[0] = errorMessage;
						errorList.add(errorInfo);
						map.put("errorList", errorList);
					
					}
				} else {//Excel文件标题 错误
					errorMessage = "excel标题行错误！";
					// 将错误信息写入excel
					List<String[]> errorList = new ArrayList<String[]>();
					String[] errorInfo = new String[5];
					errorInfo[0] = errorMessage;
					errorList.add(errorInfo);
					map.put("errorList", errorList);
					
				}
			}
		} catch (Exception ex) {
			errorMessage = ex.getMessage();
		}
		return map;
	}

	
	@Override
	public List<String[]> validateBatchTechGroup(Integer orgID,List<String[]> excelRowArray) {
		//存放年级信息 key:年级名称 val：年级id
		Map<String,String> groupMap = new HashMap<String,String>();

		//存放表头信息
		String[] title = excelRowArray.get(0);
		List<String[]> resultArray = new ArrayList<String[]>();
		//第0行是表头  从第一行开始读
		for (int i = 0; i < excelRowArray.size(); i++) {
			String[] t = excelRowArray.get(i);
			// 用于保存错误信息的数组
			String[] excelRow = new String[t.length + 1];
			int errorIdx = t.length;

			excelRow[0] = t[0];//原教师组
			excelRow[1] = t[1];//教师姓名
			excelRow[2] = t[2];//教师手机号
			excelRow[3] = t[3];//新教师组
			
            /***
             * 逐行验证excel里 信息 有错误放入 错误信息数组  
             * ****/
			try {
				if (!(StringUtils.isNotBlank(excelRow[1]) && excelRow[1].length() <= 20)) {
					throw new Exception("教师名称字数应在20字以内！");
				}
				Pattern mobilePattern = Pattern.compile("^1[0-9][0-9]\\d{8}$");
				if (!(StringUtils.isNotBlank(excelRow[2]) && mobilePattern.matcher(excelRow[2]).matches())) {
					throw new Exception("手机号码为必填项，并请输入正确格式的手机号码！");
				}
			} catch (Exception ex) {
				excelRow[errorIdx] = ex.getMessage();
			}
			
			
			// 不存在错误则进行数据库保存
			if (StringUtils.isBlank(excelRow[errorIdx])) {
				// 手动控制事务
				HibernateTransactionManager txManager = (HibernateTransactionManager) ctx.getBean("transactionManager");
				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
				def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
				TransactionStatus txStatus = txManager.getTransaction(def);// 获得事务状态
				int saveOrUpdateCount = 0;
				
				try {
					// 通过基本验证
					//OrgUser ou = orgUserDao.getOrgUser(orgID, 1, excelRow[1]);
					OrgUser ou = redisOrgUserDao.getUserByLoginNameWX(excelRow[2],new Integer(orgID),Constant.IDENTITY_TEACHER.toString());
					if (ou == null || ou.getUser_id().intValue() == 0) {
						throw new Exception("手机号码为【" + excelRow[2] + "】的人员在org_user表中不存在！");
					} else {
						// 验证已存在的人的姓名和excel中是否一致
						// 已存在，则根据userID查询teacher
						Teacher teacher = redisTeacherDao.getByUserId(ou.getUser_id().toString());
						if (teacher != null && StringUtils.isNotBlank(teacher.getTech_name())) {
							if (teacher.getTech_name().trim().equals(Utils.getValue(excelRow[1]))) {// 名称相同														
								
								// 删除教师所在组的关系
								List<GroupMember> trList = groupMemberDao.getGroupMemberByMid(teacher.getTech_id());
								//List<GroupMember> trList = redisGroupMemberDao.getGroupMemberByID(teacher.getTech_id());
								for (GroupMember groupMember : trList) {
									groupMember.setIs_del(Constant.IS_DEL_YES);
									groupMember.setDel_time(new Date());
									groupMemberDao.saveOrUpdate(groupMember);
									saveOrUpdateCount++;
								}
								
								//处理 教师 新组
								if (StringUtils.isNotBlank(excelRow[3])) {
									String[] groupArray = excelRow[3].split(",");
									if (groupArray != null && groupArray.length > 0) {
										for (String groupName : groupArray) {
											if (StringUtils.isNotBlank(groupName)) {
												//讲 班级信息存入 bjMap
												Group group =null;
												if(groupMap.get(groupName)!=null){
													group = new Group();
													int group_id = Integer.parseInt(groupMap.get(groupName));
													group.setGroup_id(group_id);
												}else{
													group = groupDao.getGroupEntity(orgID, groupName);
													if (group != null && group.getGroup_id() != null && group.getGroup_id().intValue() > 0){ 
														groupMap.put(groupName, group.getGroup_id().toString());
													}else{
														throw new Exception("组名称为【" + groupName + "】未找到！");
													}
												}
												//保存DB
												if (group != null && group.getGroup_id() != null && group.getGroup_id().intValue() > 0){ 
													GroupMember tr = new GroupMember();
													tr.setGroup_id(group.getGroup_id());
													tr.setMem_id(teacher.getTech_id());
													tr.setType(Constant.GROUPMEMBER_TYPE_TEACHER);
													tr.setInsert_time(new Date());
													tr.setIs_del(0);
													groupMemberDao.save(tr);
													saveOrUpdateCount++;
												}
											}
										}
									}
								}
								
							} else {
								// 名称不同，进行提示
								throw new Exception("数据库中手机号码为【" + ou.getUser_mobile() + "】的人员姓名与excel中填写的【" + excelRow[1] + "】不一致！");
							}
						} else {
							// 存在这个号码的人员，但不在教师表中(暂无此种情况，不处理)
							throw new Exception("手机号码为【" + ou.getUser_mobile() + "】的人员在org_user表中存在，但在teacher表中未找到！");
						}
					}
					// 所有处理完成后，提交事务
					txManager.commit(txStatus);
				} catch (Exception ex) {
					
					if (ex.getMessage() != null && StringUtils.isNotBlank(ex.getMessage())) {
						excelRow[errorIdx] = ex.getMessage();
					} else {
						ex.printStackTrace();
						excelRow[errorIdx] = "validateBatchTechGroup方法执行中出现异常！";
					}
					// 根据saveOrUpdateCount变量的值来确定是否需要进行回滚操作
					if (saveOrUpdateCount > 0) {
						txManager.rollback(txStatus);
					}
				}
			}
			// 将数据放入返回结果List中
			if (StringUtils.isNotBlank(excelRow[errorIdx])) {
				resultArray.add(excelRow);
			}
		}
		return resultArray;
	}
	
}
