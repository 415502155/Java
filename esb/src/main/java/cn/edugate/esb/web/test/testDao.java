package cn.edugate.esb.web.test;

import java.util.List;
import java.util.Map;

import cn.edugate.esb.dao.BaseDAO;
import cn.edugate.esb.entity.OrgUser;

public interface testDao extends BaseDAO<OrgUser> {
	
	List<OrgUser> getOrgUserList(Integer orgId);
	
	List<Map> getOrgUserList1(Integer orgId);
	
	List<Map> getOrgUserList2(Integer orgId, PageInfo page);

}
