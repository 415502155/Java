package com.shihy.springboot.dao;

import com.shihy.springboot.entity.Role2Menu;
import com.shihy.springboot.entity.User;
import com.shihy.springboot.entity.UserExample;
import com.shihy.springboot.excelpojo.UserPojo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
@Mapper
@Repository
@Component
public interface UserMapper {
	
	@Select("select * from t_user ")
	List<User> getUserList();
	
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExampleWithRowbounds(UserExample example, RowBounds rowBounds);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> getUserListByPage( @Param("pageStartSize") Integer pageStartSize, @Param("pageSize") Integer pageSize, @Param("order")  String order) throws Exception;

    Map<String, Object> getUserInfoByUserId(@Param("user_id") Integer userId);
    
    @SuppressWarnings("rawtypes")
	Map<String, Object> getUserInfoByMap(Map map);
    
    Map<String, Object> getUserRoleMenuInfo(@Param("user_id") Integer userId);
    
    List<Map<String, Object>> getList(@Param("user_name") String userName);
    
    void insertByBatch(@Param("list")List<UserPojo> list);
    
    User getUserByUserId(@Param ("user_id") Integer userId, @Param ("user_name") String userName, @Param ("sex") Integer sex, @Param ("birthday") Date birthday);

    Map<String, Object> getUserMapByUserId(@Param ("user_id") Integer userId, @Param ("user_name") String userName, @Param ("sex") Integer sex, @Param ("birthday") Date birthday);

    public List<Map<String, Object>> getUserRoleMenuListInfo(@Param ("user_id") Integer userId);
    
    public List<Map<String, Object>> getUserRoleMenuByRoleId(@Param ("role_id") Integer roleId);
    
    void updateUserMenuInfo(@Param ("is_del") Integer isDel, @Param ("role_id") Integer roleId, @Param ("menu_id") Integer menuId);

    public List<Map<String, Object>> getRoleMenuInfoByRoleIdAndMenuId(@Param ("is_del") Integer isDel, @Param ("role_id") Integer roleId, @Param ("menu_id") Integer menuId);
    
    void insertRoleAndMenuInfo(@Param("role2Menu") Role2Menu role2Menu);//@Param("role2Menu") 
}