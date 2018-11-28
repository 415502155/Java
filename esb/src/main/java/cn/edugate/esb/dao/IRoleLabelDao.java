package cn.edugate.esb.dao;

import cn.edugate.esb.entity.RoleLabel;

public interface IRoleLabelDao extends BaseDAO<RoleLabel>{
	
	/**
	 * 根据机构类型和角色名称查询
	 * @param orgType
	 * @param roleName
	 * @return
	 */
	public abstract RoleLabel getRoleLabel(int orgType, String roleName);
}
