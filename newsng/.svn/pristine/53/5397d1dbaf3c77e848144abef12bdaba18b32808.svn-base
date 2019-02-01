package sng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sng.dao.DomainOrgDao;
import sng.entity.DomainOrg;
import sng.pojo.base.Organization;

@Service
@Transactional
public class LoginService {

	@Autowired
	private DomainOrgDao domainOrgDao;
	
	
	public DomainOrg getDomainOrgRecord(String domainName) {
		return domainOrgDao.getDomainOrgRecord(domainName);
	}
	
	public Organization getOrgInfo4Web(String domainName) {
		return domainOrgDao.getOrgInfo4Web(domainName);
	}
}
