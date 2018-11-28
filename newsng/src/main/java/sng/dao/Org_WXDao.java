package sng.dao;

import java.util.List;

import sng.entity.OrgWXEntity;

public interface Org_WXDao extends BaseDao<OrgWXEntity> {

	public abstract List<OrgWXEntity> getAllRecord();
	
}
