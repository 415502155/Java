package cn.edugate.esb.web.test;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.entity.OrgUser;
@Service
@Component
@Transactional("transactionManager")
public class testServiceImpl implements testService {

	final Logger log = LoggerFactory.getLogger(this.getClass());
	@Value("${TEST_NAME}")
	private String TEST_NAME;
	
	@Autowired testDao dao;
	
	@Override
	public List<OrgUser> getPrgUserList(Integer orgId) {
		// TODO Auto-generated method stub
		log.info("TEST_NAME :" + TEST_NAME);
		List<OrgUser> list = dao.getOrgUserList(orgId);
		list.get(0).setUser_loginname(TEST_NAME);
		return list;
	}

	@Override
	public List<Map> getPrgUserList1(Integer orgId) {
		log.info("TEST_NAME :" + TEST_NAME);
		List<Map> list = dao.getOrgUserList1(orgId);
		Map map = list.get(0);
		map.put("user_loginname", TEST_NAME);
		return list;
	}

	@Override
	public List<Map> getPrgUserList2(Integer orgId, PageInfo page) {
		List<Map> list = dao.getOrgUserList2(orgId, page);
		Map map = list.get(0);
		return list;
	}

}
