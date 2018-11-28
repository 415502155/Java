package cn.edugate.esb.dao;

import java.util.List;

import cn.edugate.esb.entity.Campus;

public interface CampusDAO extends BaseDAO<Campus>{

	public abstract int saveCampus(Campus campus);
	
	public abstract List<Campus> getCampusList(int orgID);
	
	public abstract int saveOrUpdateCampus(Campus campus);
	
	public abstract int updateCampus(Campus campus);
	
	public abstract List<Campus> getMainCampus(int orgID);
	
}
