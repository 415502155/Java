package cn.edugate.esb.amqp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;

import cn.edugate.esb.EduConfig;
import cn.edugate.esb.entity.Department;
import cn.edugate.esb.entity.Group;
import cn.edugate.esb.service.DepartmentService;
import cn.edugate.esb.service.GroupService;
import cn.edugate.esb.service.ResourcesService;

public class SyncGroupActivator {

	private static Log logger = LogFactory.getLog(SyncGroupActivator.class);
	
	@Autowired
	private ResourcesService resourcesService;
	
	@Autowired
	private EduConfig eduConfig;
	
	private GroupService groupService;
	
	private DepartmentService departmentService;	
	
	@Autowired
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Autowired
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public void syncGroup(Message<String> msg) throws Exception {

		String msgStr = msg.getPayload();
		String[] msgarray = msgStr.split("\\_");
		String groupid = msgarray[0];
		String hx_groupid = msgarray[1];
		String type = msgarray[2];
		try{
			switch (type) {
			case "addgp":
				Group agroup = this.groupService.getById(Integer.parseInt(groupid));
				agroup.setHx_groupid(hx_groupid);
				this.groupService.saveGroup(agroup);
				break;
			case "deletegp":
				Group dgroup = this.groupService.getById(Integer.parseInt(groupid));
				dgroup.setHx_groupid("");
				this.groupService.saveGroup(dgroup);
				break;
			case "adddt":
				Department adt = this.departmentService.getDepById(Integer.parseInt(groupid));
				adt.setHx_groupid(hx_groupid);
				this.departmentService.update(adt);
				break;
			case "deletedt":
				Department ddt = this.departmentService.getDepById(Integer.parseInt(groupid));
				ddt.setHx_groupid("");
				this.departmentService.update(ddt);
				break;
	
			default:
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
	
		}
		logger.info("===========syncGroup==========="+msgStr);

	}
}
