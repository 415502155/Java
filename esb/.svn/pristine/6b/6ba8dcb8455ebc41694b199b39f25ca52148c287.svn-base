package cn.edugate.esb.dao;
 
 
import java.util.List;

import cn.edugate.esb.entity.OrgTree;
/**
 * 机构树DAO接口
 * Title:IOrgTreeDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月27日下午1:35:57
 */
public interface IOrgTreeDao extends BaseDAO<OrgTree>{
	
	public abstract int saveOrgTree(OrgTree ot);
	
	public abstract int deleteOrgTree(OrgTree ot);
	
	public abstract int deleteAllLowerOrg(int orgID);
	
	public abstract List<OrgTree> getAllLowerOrg(int orgID);
	
}