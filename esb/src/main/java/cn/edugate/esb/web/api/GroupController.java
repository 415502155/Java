package cn.edugate.esb.web.api;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.edugate.esb.Result;
import cn.edugate.esb.comparator.GroupSorterBySchoolType;
import cn.edugate.esb.comparator.GroupToSortComparator;
import cn.edugate.esb.comparator.GroupSorter;
import cn.edugate.esb.eduEnum.EnumMessage;
import cn.edugate.esb.entity.Course;
import cn.edugate.esb.entity.Department;
import cn.edugate.esb.entity.Group;
import cn.edugate.esb.entity.GroupMember;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.params.EduJsonFilter;
import cn.edugate.esb.params.EduJsonFilters;
import cn.edugate.esb.params.filter.GroupFilter;
import cn.edugate.esb.params.filter.GroupMemberMoreFilter;
import cn.edugate.esb.params.filter.OrganizationForGroupMemberFilter;
import cn.edugate.esb.params.filter.StudentForGroupMemberFilter;
import cn.edugate.esb.params.filter.TeacherForGroupMemberFilter;
import cn.edugate.esb.redis.dao.RedisDepartmentDao;
import cn.edugate.esb.redis.dao.RedisGroupDao;
import cn.edugate.esb.redis.dao.RedisGroupMemberDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.service.GroupMemberService;
import cn.edugate.esb.service.GroupService;
import cn.edugate.esb.service.StudentService;
import cn.edugate.esb.service.TeacherService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;

@Controller
@RequestMapping("/api/group")
public class GroupController {
	@Autowired
	private RedisGroupDao redisGroupDao;
	@Autowired
	private RedisGroupMemberDao redisGroupMemberDao;
	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupMemberService groupMemberService;
	@Autowired
	private RedisTeacherDao redisTeacherDao;
	@Autowired
	private RedisDepartmentDao redisDepartmentDao;
	@Autowired
	private StudentService studentService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private Util util;
	
	/**
	 * 创建组
	 * @param groupName 【必填】组名称
	 * @param orgId     【必填】机构主键
	 * @param groupType 【必填】组类型
	 * @param teacherId 【必填】创建教师主键
	 * @param note      [选填] 备注说明
	 * @param request
	 * @return 创建完成的组
	 */
	@RequestMapping(value = "/addGroup",method = RequestMethod.POST)
	@EduJsonFilters(value={@EduJsonFilter(mixin=GroupFilter.class, target=Group.class),
			@EduJsonFilter(mixin=GroupMemberMoreFilter.class, target=GroupMember.class),
			@EduJsonFilter(mixin=StudentForGroupMemberFilter.class, target=Student.class),
			@EduJsonFilter(mixin=TeacherForGroupMemberFilter.class, target=Teacher.class),
			@EduJsonFilter(mixin=OrganizationForGroupMemberFilter.class, target=Organization.class)})
	public @ResponseBody Result<Map<String, Object>> addGroup(
			@RequestParam(value="group_name") String groupName,
			@RequestParam(value="org_id") Integer orgId,
			@RequestParam(value="group_type") Integer groupType,
			@RequestParam(value="tech_id") Integer teacherId,
			@RequestParam(value="note",defaultValue="") String note,
			@RequestParam(value="type") Integer type,
			@RequestParam(value="mem_ids") String memIds,
			HttpServletRequest request){
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		//创建组
		Group group = new Group();
		group.setGroup_name(groupName);
		group.setOrg_id(orgId);
		group.setGroup_type(groupType);
		group.setTech_id(teacherId);
		group.setNote(note);
		groupService.add(group);
		//为组添加成员
		if(!"0".endsWith(memIds)){
			groupMemberService.replaceMembersOfGroup(group.getGroup_id(),type,memIds,orgId);
		}
		boolean needOrgInfo = Constant.GROUP_TYPE_OUTTER == group.getGroup_type();
		Map<String, Object> groupMembersMap = redisGroupMemberDao.getMembers(group.getGroup_id(),needOrgInfo);
		//返回结果
		data.put("group", group);
		data.put("groupMembers", groupMembersMap);
		result.setData(data);
		result.setCode(EnumMessage.success.getCode());
		result.setMessage(EnumMessage.success.getMessage());
		result.setSuccess(true);
		return result;
	}
	
	/**
	 * 删除组
	 * @param groupId 【必填】要删除的组的主键
	 * @param request
	 * @return 删除的组的主键
	 */
	@RequestMapping(value = "/deleteGroup")
	public @ResponseBody Result<Map<String, String>> deleteGroup(
			@RequestParam(value="group_id") Integer groupId,
			HttpServletRequest request){
		Result<Map<String, String>> result = new Result<Map<String, String>>();
		try {
			Group group = redisGroupDao.getGroupById(groupId,null);
			if(null==group){
				result.setSuccess(false);
				result.setMessage(EnumMessage.group_not_found.getMessage());
				result.setCode(EnumMessage.group_not_found.getCode());
				return result;
			}
			//删除组下的成员
			groupMemberService.replaceMembersOfGroup(groupId,null,"",null);
			//删除组
			groupService.delete(groupId);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(EnumMessage.group_not_found.getMessage());
			result.setCode(EnumMessage.group_not_found.getCode());
			return result;
		}
		result.setMessage(groupId.toString());
		result.setSuccess(true);
		result.setCode(EnumMessage.success.getCode());
		return result;
	}
	
	/**
	 * 更新组
	 * @param groupId   【必填】组主键
	 * @param groupName [选填] 组名称
	 * @param orgId     [选填] 机构主键
	 * @param groupType [选填] 组类型
	 * @param note      [选填] 备注
	 * @param request
	 * @return 更新后的组
	 */
	@RequestMapping(value = "/updateGroup",method = RequestMethod.POST)
	@EduJsonFilters(value={@EduJsonFilter(mixin=GroupFilter.class, target=Group.class),
			@EduJsonFilter(mixin=GroupMemberMoreFilter.class, target=GroupMember.class),
			@EduJsonFilter(mixin=StudentForGroupMemberFilter.class, target=Student.class),
			@EduJsonFilter(mixin=TeacherForGroupMemberFilter.class, target=Teacher.class),
			@EduJsonFilter(mixin=OrganizationForGroupMemberFilter.class, target=Organization.class)})
	public @ResponseBody Result<Map<String, Object>> updateGroup(
			@RequestParam(value="group_id") Integer groupId,
			@RequestParam(value="group_name") String groupName,
			@RequestParam(value="mem_ids") String memIds,
			@RequestParam(value="mem_type") Integer type,
			@RequestParam(value="note",defaultValue="sjwy==Note NullPoint==sjwy") String note,
			HttpServletRequest request){
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Group group = redisGroupDao.getGroupById(groupId,null);
		if(null==group){
			result.setSuccess(false);
			result.setMessage(EnumMessage.group_not_found.getMessage());
			result.setCode(EnumMessage.group_not_found.getCode());
			return result;
		}
		if(StringUtils.isNotEmpty(groupName)){
			group.setGroup_name(groupName);
		}
		if(!"sjwy==Note NullPoint==sjwy".equals(note)){
			group.setNote(note);
		}
		groupService.update(group);
		group = redisGroupDao.getGroupById(group.getGroup_id(),null);
		//为组添加成员
		if(!"0".equals(memIds)){
			groupMemberService.replaceMembersOfGroup(group.getGroup_id(),type,memIds,group.getOrg_id());
		}
		//返回更新后的结构
		boolean needOrgInfo = Constant.GROUP_TYPE_OUTTER == group.getGroup_type();
		Map<String, Object> groupMembersMap = redisGroupMemberDao.getMembers(group.getGroup_id(),needOrgInfo);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("group", group);
		data.put("groupMembers", groupMembersMap);
		result.setData(data);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	/**
	 * 根据组主键获取组
	 * @param groupId 【必填】组主键
	 * @param request
	 * @return 查到的组
	 */
	@RequestMapping(value = "/getGroup")
	@EduJsonFilters(value={@EduJsonFilter(mixin=GroupFilter.class, target=Group.class),
			@EduJsonFilter(mixin=GroupMemberMoreFilter.class, target=GroupMember.class),
			@EduJsonFilter(mixin=StudentForGroupMemberFilter.class, target=Student.class),
			@EduJsonFilter(mixin=TeacherForGroupMemberFilter.class, target=Teacher.class),
			@EduJsonFilter(mixin=OrganizationForGroupMemberFilter.class, target=Organization.class)})
	public @ResponseBody Result<Map<String, Object>> getGroup(
			@RequestParam(value="group_id") Integer groupId,
			HttpServletRequest request){
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		//获取组信息
		Group group = redisGroupDao.getGroupById(groupId,null);
		if(null==group){
			result.setSuccess(false);
			result.setMessage(EnumMessage.group_not_found.getMessage());
			result.setCode(EnumMessage.group_not_found.getCode());
			return result;
		}
		// 获取组成员集合
		Map<String,Object> groupMembersMap = new HashMap<String,Object>();
		boolean needOrgInfo = Constant.GROUP_TYPE_OUTTER == group.getGroup_type();
		groupMembersMap = redisGroupMemberDao.getMembers(groupId,needOrgInfo);
		//返回信息
		if(groupMembersMap!=null && groupMembersMap.size()>0){
			data.put("groupMembers", groupMembersMap.values());
		}
		data.put("group", group);
		result.setData(data);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}
	
	/**
	 * 根据组主键获取组
	 * @param groupId 【必填】组主键
	 * @param request
	 * @return 查到的组
	 */
	@RequestMapping(value = "/getGroupMembersForSelect")
	@EduJsonFilters(value={@EduJsonFilter(mixin=GroupFilter.class, target=Group.class),
			@EduJsonFilter(mixin=StudentForGroupMemberFilter.class, target=Student.class),
			@EduJsonFilter(mixin=TeacherForGroupMemberFilter.class, target=Teacher.class)})
	public @ResponseBody Result<Map<String, Object>> getGroupMembersForSelect(
			@RequestParam(value="org_id") String orgId,
			@RequestParam(value="group_id") String groupId,
			HttpServletRequest request){
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		//获取组信息
		Group group = redisGroupDao.getGroupById(Integer.parseInt(groupId),null);
		if(null==group){
			result.setSuccess(false);
			result.setMessage(EnumMessage.group_not_found.getMessage());
			result.setCode(EnumMessage.group_not_found.getCode());
			return result;
		}
		if(Constant.GROUP_TYPE_STUDENT==group.getGroup_type()){
			List<Object> memberData = new ArrayList<Object>();
			List<Object> list = studentService.getStudentsWithGradeClass(group.getOrg_id());
			Map<Integer, GroupMember> map = redisGroupMemberDao.getStudentGroupMembersById(groupId);
			for (Object obj : list) {
				Object[] objs = (Object[]) obj;
				if((map.containsKey((Integer)objs[6]))){
					objs[9]="1";
				}
				memberData.add(objs);
			}
			data.put("memberData", memberData);
		}else if(Constant.GROUP_TYPE_INNER==group.getGroup_type()){
			LinkedHashMap<String,Teacher> groupMember = redisGroupMemberDao.getTeacherMembers(groupId);
			List<Department> list = redisDepartmentDao.getDepsByOrgId(orgId);
			for (Department dep : list) {  
				List<Teacher> techList = redisTeacherDao.getTechsByDepId(dep.getDep_id()+"",null);
				if(null!=techList&&techList.size()!=0){
					for (Teacher teacher : techList) {
						if(null!=groupMember&&groupMember.values().size()!=0&&groupMember.get(teacher.getTech_id().toString()) instanceof Teacher){
							teacher.setIs_selected(Constant.YES);
						}else{
							teacher.setIs_selected(Constant.NO);
						}
					}
				}
				dep.setTeachers(techList);
			}
			data.put("teacherList", list);
		}else if(Constant.GROUP_TYPE_OUTTER==group.getGroup_type()){
			List<Object> list = teacherService.getOrgTreeTeacherMembers(orgId,groupId);
			data.put("memberData", list);
			
		}
		result.setData(data);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}
	
	@RequestMapping(value = "/getGroupMembers",method = RequestMethod.POST)
	@EduJsonFilters(value={@EduJsonFilter(mixin=GroupFilter.class, target=Group.class),
			@EduJsonFilter(mixin=StudentForGroupMemberFilter.class, target=Student.class),
			@EduJsonFilter(mixin=TeacherForGroupMemberFilter.class, target=Teacher.class)})
	public @ResponseBody Result<Map<String, Object>> getGroupMembers(
			@RequestParam(value="org_id") String orgId,
			@RequestParam(value="group_id") String groupId,
			HttpServletRequest request){
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		//获取组信息
		Group group = redisGroupDao.getGroupById(Integer.parseInt(groupId),null);
		if(null==group){
			result.setSuccess(false);
			result.setMessage(EnumMessage.group_not_found.getMessage());
			result.setCode(EnumMessage.group_not_found.getCode());
			return result;
		}
		if(Constant.GROUP_TYPE_OUTTER==group.getGroup_type()){
			List<Object> list = teacherService.getTeacherMembers(orgId,groupId);
			data.put("memberData", list);
		}
		result.setData(data);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	/**
	 * 根据查询条件获取组，查询条件可以是任意组合
	 * @param orgId     [选填] 机构主键
	 * @param groupType [选填] 组类型
	 * @param memberId  [选填,与type联合使用] 成员主键
	 * @param type      [选填,与memberId联合使用] 成员类型
	 * @param request
	 * @return 符合条件的组的集合，带成员，带机构信息
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getGroups")
	@EduJsonFilters(value={@EduJsonFilter(mixin=GroupFilter.class, target=Group.class),
			@EduJsonFilter(mixin=GroupMemberMoreFilter.class, target=GroupMember.class),
			@EduJsonFilter(mixin=StudentForGroupMemberFilter.class, target=Student.class),
			@EduJsonFilter(mixin=TeacherForGroupMemberFilter.class, target=Teacher.class),
			@EduJsonFilter(mixin=OrganizationForGroupMemberFilter.class, target=Organization.class)})
	public @ResponseBody Result<Map<String, Object>> getGroups(
			@RequestParam(value="org_id",defaultValue="-1") Integer orgId,
			@RequestParam(value="group_type",defaultValue="-1") Integer groupType,
			@RequestParam(value="mem_id",defaultValue="-1") Integer memberId,
			@RequestParam(value="mem_type",defaultValue="-1") Integer type,
			@RequestParam(value="mem_info",defaultValue="") String memInfo,
			HttpServletRequest request){
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> objs = new ArrayList<Map<String,Object>>();
		if(StringUtils.isEmpty(memInfo)||"yes".equals(memInfo)){
			List<Object> list = groupService.getGroupsWithMembers(orgId,groupType,memberId,type);
			Map<String, Map<String,Object>> groupMap = new HashMap<String,Map<String,Object>>();
			for (Object object : list) {
				Object[] obj = (Object[]) object; 
				if(!groupMap.containsKey(obj[1].toString())){
					Map<String,Object> objmap = new HashMap<String,Object>();
					try {
						objmap.put("group_id", obj[1].toString());
						objmap.put("group_name", obj[2].toString());	
						objmap.put("group_type", obj[3].toString());
					} catch (Exception e) {
						continue;
					}
					objmap.put("group_note", obj[4]);
					objmap.put("hx_groupid", obj[5]);
					objmap.put("memberData", new ArrayList<Map<String,Object>>());
					groupMap.put(obj[1].toString(), objmap);
				}
			}
			for (Object o : list) {
				Object[] obj = (Object[]) o; 
				Map<String,Object> objmap = groupMap.get(obj[1].toString());
				List<Map<String,Object>> memberList = (List<Map<String,Object>>) objmap.get("memberData");
				Map<String,Object> memberInfo = new HashMap<String,Object>();
				try {
					memberInfo.put("group_member_id", obj[6].toString());
					memberInfo.put("mem_id", obj[7].toString());
					memberInfo.put("mem_name", obj[8].toString());
					memberInfo.put("user_id", obj[11].toString());
				} catch (Exception e) {
					continue;
				}
				memberInfo.put("mobile", obj[9]);
				memberInfo.put("mem_nick", obj[10]);
				memberInfo.put("tech_head", obj[12]);
				if(null!=util){
					if (null!=obj[12]&&!"".equals(obj[12].toString())) {
						memberInfo.put("headurl", util.getPathByPicName(obj[12].toString()));
					} else {
						memberInfo.put("headurl", "");
					}
				}
        	    if(StringUtils.isEmpty(memInfo)||"yes".equals(memInfo)){
        	    	memberInfo.put("org_id", obj[13]);
        	    	memberInfo.put("org_name", obj[14]);
                }
        	    memberInfo.put("sorter", obj[0]);
        	    memberList.add(memberInfo);
        	    objmap.put("memberData", memberList);
        	    groupMap.put(obj[1].toString(), objmap);
			}
			for (Map<String,Object> m : groupMap.values()) {
				List<Map<String,Object>> memberList = (List<Map<String,Object>>) m.get("memberData");
				GroupSorterBySchoolType mcg = new GroupSorterBySchoolType();  
				Collections.sort(memberList,mcg);
				objs.add(m);
			}
		}else{
			List<Object> list = groupService.getGroupsWithoutMembers(orgId,groupType);
			for(Object o : list) {
				Object[] obj = (Object[]) o; 
				Map<String,Object> objmap = new HashMap<String,Object>();
				objmap.put("group_id", obj[1]);
				objmap.put("group_name", obj[2]);	
				objmap.put("group_type", obj[3]);
				objmap.put("group_note", obj[4]);
				objmap.put("hx_groupid", obj[5]);
				objs.add(objmap);
			}
		}
		GroupToSortComparator msc = new GroupToSortComparator();  
		Collections.sort(objs,msc);
		map.put("groupData", objs);
		result.setData(map);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
		
		
		
		/*
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String,Group> groupsMap = new HashMap<String,Group>();
		if(!"-1".equals(memberId.toString())&&!"-1".equals(type.toString())){
			groupsMap = redisGroupDao.getGroupsByMember(memberId, type);
		}else{
			if("-1".equals(orgId.toString())){
				orgId = null;
			}
			if("-1".equals(groupType.toString())){
				groupType = null;
			}
			groupsMap = redisGroupDao.getGroups(orgId,groupType);
		}
		if(null==groupsMap){
			result.setSuccess(false);
			result.setMessage(EnumMessage.group_not_found.getMessage());
			result.setCode(EnumMessage.group_not_found.getCode());
			return result;
		}
		List<Group> groups = new ArrayList<Group>(groupsMap.values()) ;
		GroupToSortComparator mcg = new GroupToSortComparator();  
        Collections.sort(groups,mcg);
		
		List<Object> objs = new ArrayList<Object>();
		for(Group group : groups) {
			Map<String,Object> objmap = new HashMap<String,Object>();
//			Group group = groupMap.getValue();
			boolean needOrgInfo = Constant.GROUP_TYPE_OUTTER == group.getGroup_type();
			objmap.put("group_id", group.getGroup_id());
			objmap.put("group_name", group.getGroup_name());	
			objmap.put("group_type", group.getGroup_type());
			objmap.put("group_note", group.getNote());
			objmap.put("hx_groupid", group.getHx_groupid());
			if(StringUtils.isEmpty(memInfo)||"yes".equals(memInfo)){
				Map<String,Object> groupMember = redisGroupMemberDao.getMembers(group.getGroup_id(),needOrgInfo);
				if(groupMember!=null && groupMember.size()>0){
					@SuppressWarnings({ "unchecked", "rawtypes" })
					List<Map> list = new ArrayList<Map>((Collection<? extends Map>) groupMember.values());
					MapToSortComparator mc = new MapToSortComparator();  
			        Collections.sort(list,mc);
					objmap.put("memberData",list);
				}else{
					objmap.put("memberData", null);
				}
			}
			objs.add(objmap);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("groupData", objs);
		result.setData(map);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;*/
	}

	/**
	 * 为BaseUI提供组查询接口
	 */
	@RequestMapping(value = "/getGroupList")
	@EduJsonFilters(value={@EduJsonFilter(mixin=GroupFilter.class, target=Group.class),
			@EduJsonFilter(mixin=GroupMemberMoreFilter.class, target=GroupMember.class),
			@EduJsonFilter(mixin=StudentForGroupMemberFilter.class, target=Student.class),
			@EduJsonFilter(mixin=TeacherForGroupMemberFilter.class, target=Teacher.class),
			@EduJsonFilter(mixin=OrganizationForGroupMemberFilter.class, target=Organization.class)})
	public @ResponseBody Result<List<Object>> getGroupList(
			@RequestParam(value="org_id") Integer orgId,
			@RequestParam(value="group_type") Integer groupType,
			HttpServletRequest request){
		Result<List<Object>> result = new Result<List<Object>>();
		List<Object> groupsMap = new ArrayList<Object>();
		groupsMap = groupService.getGroupList(orgId,groupType);
		Collections.sort(groupsMap, new GroupSorter());
		result.setData(groupsMap);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	/**
	 * 添加组成员
	 */
	@RequestMapping(value = "/addGroupMembers")
	public @ResponseBody Result<Object> addGroupMembers(
			@RequestParam(value="group_id") Integer group_id,
			@RequestParam(value="mem_ids") String mem_ids,
			HttpServletRequest request){
		Result<Object> result = new Result<Object>();
		try{
			Group group = redisGroupDao.getGroupById(group_id, null);
			if(null!=group){
				if(group.getGroup_type()==Constant.GROUP_TYPE_STUDENT){
					groupMemberService.addGroupMembers(group_id,mem_ids,Constant.GROUPMEMBER_TYPE_STUDENT);
				}else{
					groupMemberService.addGroupMembers(group_id,mem_ids,Constant.GROUPMEMBER_TYPE_TEACHER);
				}
			}
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());
		}catch(Exception e){
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			result.setCode(EnumMessage.fail.getCode());
		}
		return result;
	}
	
	/**
	 * 更新组内成员
	 * @param group_id
	 * @param mem_ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateGroupMembers")
	@ResponseBody
	public Result<Object> updateGroupMembers(@RequestParam(value = "group_id") Integer group_id,
			@RequestParam(value = "mem_ids") String mem_ids, HttpServletRequest request) {
		Result<Object> result = new Result<Object>();
		Group group = redisGroupDao.getGroupById(group_id, null);
		if (null != group) {
			groupMemberService.updateGroupMembers(group_id, mem_ids, group.getGroup_type());
			
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());
			return result;
		} else {
			result.setSuccess(false);
			result.setMessage(EnumMessage.fail.getMessage());
			result.setCode(EnumMessage.fail.getCode());
			return result;
		}
	}

	/**
	 * 删除组成员
	 */
	@RequestMapping(value = "/deleteGroupMembers")
	public @ResponseBody Result<Object> deleteGroupMember(
			@RequestParam(value="group_id") Integer group_id,
			@RequestParam(value="mem_ids") String mem_ids,
			HttpServletRequest request){
		Result<Object> result = new Result<Object>();
		Group group = redisGroupDao.getGroupById(group_id, null);
		if(null!=group){
			if(group.getGroup_type()==Constant.GROUP_TYPE_STUDENT){
				groupMemberService.deleteGroupMembers(group_id,mem_ids,Constant.GROUPMEMBER_TYPE_STUDENT);
			}else{
				groupMemberService.deleteGroupMembers(group_id,mem_ids,Constant.GROUPMEMBER_TYPE_TEACHER);
			}
		}
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}
	
	
	/**
	 * 查询机构下 教师信息及所在组
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/getTechGroupsOfOrg")
	@EduJsonFilters(value={@EduJsonFilter(mixin=GroupFilter.class, target=Group.class),
			@EduJsonFilter(mixin=GroupMemberMoreFilter.class, target=GroupMember.class),
			@EduJsonFilter(mixin=StudentForGroupMemberFilter.class, target=Student.class),
			@EduJsonFilter(mixin=TeacherForGroupMemberFilter.class, target=Teacher.class),
			@EduJsonFilter(mixin=OrganizationForGroupMemberFilter.class, target=Organization.class)})
	public @ResponseBody Result<List<Map<String, String>>> getTechGroupsOfOrg(
			@RequestParam(value="org_id") Integer orgId,
			HttpServletRequest request){
		Result<List<Map<String, String>>> result = new Result<List<Map<String, String>>>();
		List<Map<String, String>> groupsMap = new ArrayList<Map<String, String>>();
		groupsMap = groupService.getTechGroupsOfOrg(orgId);
		//Collections.sort(groupsMap, new GroupSorter());
		result.setData(groupsMap);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}
	
	
	@RequestMapping(value = "/uploadExcelTechGroups", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Result<Map<String, Object>> uploadExcelTechGroups(@RequestParam(value = "org_id") int org_id,
			@RequestParam(value = "file") MultipartFile file,
			@RequestParam(value = "fileName") String fileName) throws IOException {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		try {
			fileName = URLDecoder.decode(fileName, "UTF-8");
			Map<String,Object> resultMap = this.groupService.batchTechGroup(org_id,file);
			result.setData(resultMap);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	
	
}
