package sng.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sng.dao.BaseDaoEx;
import sng.dao.TeacherDao;
import sng.pojo.SessionInfo;

@Service
@Transactional
public class ReportTecherServiceImpl {
	@Resource
	private BaseDaoEx baseDaoEx;
	@Resource
	private TeacherDao teacherDao; 
	
	/**
	 * 教师排名前10统计
	 * @throws Exception 
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Map<String, Object>> reportTecherTop10(SessionInfo sessionInfo,int termId,String camId) throws Exception {
		int orgId=sessionInfo.getOrgId();
		List<Map<String, Object>> list=teacherDao.reportTecherTop10(orgId, termId, camId);
		return list;
	}
	
	/**
	 * 所有教师统计列表
	 * @throws Exception 
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<Map<String, Object>> reportTecherAll(SessionInfo sessionInfo,int termId,String camId,String techName) throws Exception {
		int orgId=sessionInfo.getOrgId();
		List<Map<String, Object>> list=teacherDao.reportTecherAll(orgId, termId, camId, techName);
		return list;
	}
	

	
	
}
