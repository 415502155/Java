package tk.mybatis.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import tk.mybatis.springboot.model.MenuInfo;
import tk.mybatis.springboot.model.RoleInfo;
import tk.mybatis.springboot.model.RoleMenuInfo;
import tk.mybatis.springboot.model.UserInfo;

@Mapper
public interface RoleMapper {
	
	public List<RoleInfo> getRoleList();
	
	public int updateRole(@Param("user")UserInfo userInfo);
	
	public List<MenuInfo> getMenuId();
	
	public List<RoleMenuInfo> getMRId(@Param("param")RoleMenuInfo param);
	//将所有的菜单选项赋值给超级管理员
	public int addAll(@Param("roleM")List<RoleMenuInfo> roleM);
}
