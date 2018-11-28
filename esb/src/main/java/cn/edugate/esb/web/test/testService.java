package cn.edugate.esb.web.test;

import java.util.List;
import java.util.Map;

import cn.edugate.esb.entity.OrgUser;

public interface testService {
	
	List<OrgUser> getPrgUserList(Integer orgId);
	
	List<Map> getPrgUserList1(Integer orgId);
	
	List<Map> getPrgUserList2(Integer orgId, PageInfo page);

}
